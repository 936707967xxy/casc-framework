/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.server.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.CreateSerialNo;
import com.hoomsun.common.util.HttpClientUtil;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.common.util.UploadPathUtil;
import com.hoomsun.core.dao.SysContractMapper;
import com.hoomsun.core.dao.SysEmployeeMapper;
import com.hoomsun.core.dao.SystemParamMapper;
import com.hoomsun.core.enums.SystemParamEnum;
import com.hoomsun.core.model.SysContract;
import com.hoomsun.core.model.SystemParam;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.DateUtil;
import com.hoomsun.csas.sales.api.model.Annex;
import com.hoomsun.csas.sales.api.model.MakingOpinion;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.vo.MakingVo;
import com.hoomsun.csas.sales.api.server.inter.MakingServerI;
import com.hoomsun.csas.sales.dao.ActivitiMapper;
import com.hoomsun.csas.sales.dao.AnnexMapper;
import com.hoomsun.csas.sales.dao.MakingMapper;
import com.hoomsun.csas.sales.dao.MakingOpinionMapper;
import com.hoomsun.csas.sales.dao.UserApplyMapper;
import com.hoomsun.csas.sales.util.ActivitiUtils;
import com.hoomsun.csas.sales.util.JumpTaskCmd;

/**
 * 作者：liusong <br>
 * 创建时间：2017年12月4日 <br>
 * 描述：
 */
@Service("makingServer")
public class MakingServerImpl implements MakingServerI {
	private final static Logger log = LoggerFactory.getLogger(MakingServerImpl.class);

	@Autowired
	private MakingMapper makingMapper;
	@Autowired
	private UserApplyMapper userApplyMapper;
	@Autowired
	private TaskService taskService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private MakingOpinionMapper opinionMapper;
	@Autowired
	private AnnexMapper annexMapper;// 上传表
	@Autowired
	private UploadPathUtil uploadPathUtil;
	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;
	@Autowired
	private ActivitiMapper bpmnMapper;
	@Autowired
	private SysContractMapper sysContractMapper;
	@Autowired
	private SystemParamMapper systemParamMapper;
	@Value("${LEND_PASS}")
	private String lendPass;

	@Value("${HSOADB_NAME}")
	private String hsoaDB;

	@Override
	public Pager<MakingVo> findPage(Integer page, Integer rows, String custName, String custMobile, String loanId, String empId, String deptId, String storeId, String nodeStatus, String node) {
		Map<String, Object> param = new HashMap<String, Object>();

		// boolean isGroup = hasPermission(empId);
		// if (!isGroup) {
		// throw new AuditException("您没有权限操作！不是该审批组人员!");
		// }
		// 验证当前登录人是不是部门负责人
		String mgrId = sysEmployeeMapper.findDeptManager(deptId);
		Integer isMgr = 0;//
		if (empId.equals(mgrId)) {// 是部门的负责人
			isMgr = 1;
		}

		param.put("empId", empId);
		param.put("storeId", storeId);
		param.put("isMgr", isMgr);
		param.put("deptId", deptId);
		if (page == null || rows == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		rows = rows > 100 ? 100 : rows;

		if (!StringUtils.isBlank(node)) {
			param.put("node", node);// 默认未审核的信息
		}
		param.put("empId", empId);
		param.put("pageIndex", page);
		param.put("pageSize", rows);
		if (!StringUtils.isBlank(custName)) {
			param.put("custName", "%" + custName + "%");
		}
		if (!StringUtils.isBlank(custMobile)) {
			param.put("custMobile", "%" + custMobile + "%");
		}
		// [{'id':'0','text':'待签收'},{'id':'1','text':'待审核'},{'id':'2','text':'已审核'},{'id':'3','text':'全部'}]
		if (!StringUtils.isBlank(nodeStatus)) {
			param.put("nodeStatus", nodeStatus);
		} else {
			param.put("nodeStatus", 1);// 待审核
		}

		if (!StringUtils.isBlank(loanId)) {
			param.put("loanId", loanId);
		}
		List<MakingVo> findAllData = makingMapper.findPage(param);
		int applyCount = makingMapper.pageCount(param);
		Pager<MakingVo> mapPager = new Pager<MakingVo>(findAllData, applyCount);
		return mapPager;
	}

	@Override
	public MakingVo selectByApplyId(String applyId) {
		return makingMapper.selectByApplyId(applyId);
	}

	// 验证当前人是否有权限 该审批组权限
	public boolean hasPermission(String loginEmp) {
		List<Group> groups = identityService.createGroupQuery().groupMember(loginEmp).list();
		boolean isGroup = false;
		for (Group group : groups) {
			if ("CUSTOMER_SERVICE_GROUP".equalsIgnoreCase(group.getId())) {
				isGroup = true;
				break;
			}
		}
		return isGroup;
	}

	@Override
	public Json completeTask(String applyId, SessionRouter session, String remark) {
		String empId = session.getEmpId();
		if (StringUtils.isBlank(applyId) || StringUtils.isBlank(empId)) {
			return new Json(false, "参数有误!");
		}
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("makeCon").taskAssignee(empId).singleResult();
		if (task == null) {
			return new Json(false, "无权处理任务!");
		}

		/*
		 * 
		 * 红小宝确认招标接口推送数据
		 * 
		 */
		SystemParam systemParam = systemParamMapper.findParamByKey(SystemParamEnum.PUSH_ONLINE.getKey());
		String paramValue = systemParam.getParamValue();
		if (paramValue.equals("1")) {
			Map<String, Object> map = lendPasses(applyId);
			Integer status = (Integer) map.get("status");
			String errMsg = (String) map.get("errMsg");
			if (status == 1000) {
				taskService.complete(task.getId());
			} else {
				return new Json(false, "红小宝招标失败！！！" + errMsg);
			}
		} else {
			taskService.complete(task.getId());
		}

		String processId = task.getProcessInstanceId();
		// 修改申请主表状态
		List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(applyId).processInstanceId(processId).list();
		String precessStatus = "";
		String precessStatusVal = "";
		boolean isFirst = true;
		for (Task tk : tasks) {
			if (isFirst) {
				precessStatus = tk.getTaskDefinitionKey();
				precessStatusVal = tk.getName();
				isFirst = false;
			} else {
				precessStatus = precessStatus + "," + tk.getTaskDefinitionKey();
				precessStatusVal = precessStatusVal + "," + tk.getName();
			}
		}
		userApplyMapper.updateProcessInstance(applyId, processId, precessStatus, precessStatusVal);
		// SysContract sysContract = sysContractMapper.selectByApplyId(applyId);
		// String proName = sysContract.getProdAlias();
		// noticeServer.sendMsg(applyId,"您签订的"+ proName +
		// "借款协议已通过审核，请耐心等待放款。",1);
		// 添加操作记录
		MakingOpinion opinion = new MakingOpinion();
		opinion.setMakingId(PrimaryKeyUtil.getPrimaryKey());
		opinion.setApplyId(applyId);
		opinion.setEmpId(empId);
		opinion.setEmpName(session.getEmpName());
		opinion.setMakingTimes(new Date());
		opinion.setProcInstId(processId);
		opinion.setPrecessStatus(precessStatus);
		opinion.setPrecessStatusVal(precessStatusVal);
		opinion.setHandleOpinion(4);
		opinion.setHandleOpinionVal("通过");
		opinion.setTaskId(task.getId());
		opinion.setRemarks(remark);
		opinionMapper.insertSelective(opinion);
		return new Json(true, "处理成功!");
	}

	@Override
	public Json claimTask(String applyId, String empId) {
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("makeCon").singleResult();
		if (task == null) {
			return new Json(false, "不能处理,任务在其他节点!");
		}

		String assignee = task.getAssignee();
		if (!StringUtils.isBlank(assignee)) {//
			if (assignee.equals(empId)) {
				return new Json(true, "任务已签收成功!");
			} else {
				return new Json(false, "不能处理,任务已被他人签收!");
			}
		} else {
			if (ActivitiUtils.getClaimTaskSign(bpmnMapper, empId)) {
				taskService.claim(task.getId(), empId);
			} else {
				return new Json(false, "不能签收，请先处理完手中的任务!");
			}
		}
		return new Json(true, "任务签收成功!");
	}

	@Override
	public Json checkClaim(String applyId, String empId) {
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("makeCon").singleResult();
		if (null == task) {
			return new Json(false, "不能处理！任务已不在当前节点!");
		}

		String assignee = task.getAssignee();
		if (StringUtils.isBlank(assignee)) {// 没有签收 签收任务
			if (ActivitiUtils.getClaimTaskSign(bpmnMapper, empId)) {
				taskService.claim(task.getId(), empId);
				return new Json(true, "签收成功!");
			} else {
				return new Json(false, "不能签收，请先处理完手中的任务!");
			}
		} else {
			if (assignee.equals(empId)) {// 相同的人处理
				return new Json(true, "你已经签收成功!");
			} else {
				return new Json(false, "任务已经被他人签收!");
			}
		}
	}

	@Override
	public Json reject(String applyId, SessionRouter session, String remark) {
		String empId = session.getEmpId();
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数有误!");
		}
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("makeCon").singleResult();
		if (null == task) {
			return new Json(false, "不能拒贷！任务已不在当前节点!");
		}

		JumpTaskCmd cmd = new JumpTaskCmd(task, "endEvent", JumpTaskCmd.REJECT);
		TaskServiceImpl taskServiceImpl = (TaskServiceImpl) taskService;
		taskServiceImpl.getCommandExecutor().execute(cmd);
		// SysContract sysContract =
		// sysContractMapper.findinfoByApplyId(applyId);
		// String proName = sysContract.getProdAlias();
		// noticeServer.sendMsg(applyId,"您提交的"+ proName +
		// "借款申请未通过审核被拒贷，拒贷原因："+remark+"。",1);
		String processId = task.getProcessInstanceId();
		String precessStatus = "";
		String precessStatusVal = "";
		// 修改申请主表状态
		List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(applyId).processInstanceId(processId).list();
		if (tasks == null || tasks.size() < 1) {
			userApplyMapper.updateProcessInstance(applyId, processId, JumpTaskCmd.REJECT, "拒贷");
			precessStatus = "0";
			precessStatusVal = "拒贷";
		} else {
			boolean isFirst = true;
			for (Task tk : tasks) {
				if (isFirst) {
					precessStatus = tk.getTaskDefinitionKey();
					precessStatusVal = tk.getName();
					isFirst = false;
				} else {
					precessStatus = precessStatus + "," + tk.getTaskDefinitionKey();
					precessStatusVal = precessStatusVal + "," + tk.getName();
				}
			}
			userApplyMapper.updateProcessInstance(applyId, processId, precessStatus, precessStatusVal);
		}

		// 添加操作记录
		MakingOpinion opinion = new MakingOpinion();
		opinion.setMakingId(PrimaryKeyUtil.getPrimaryKey());
		opinion.setApplyId(applyId);
		opinion.setEmpId(empId);
		opinion.setEmpName(session.getEmpName());
		opinion.setMakingTimes(new Date());
		opinion.setProcInstId(processId);
		opinion.setPrecessStatus(precessStatus);
		opinion.setPrecessStatusVal(precessStatusVal);
		opinion.setHandleOpinion(0);
		opinion.setHandleOpinionVal("拒贷");
		opinion.setTaskId(task.getId());
		opinion.setRemarks(remark);
		opinionMapper.insertSelective(opinion);
		return new Json(true, "拒贷成功!");
	}

	@Override
	public Json withdraw(String applyId, SessionRouter session, String remark) {
		String empId = session.getEmpId();
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数有误!");
		}
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("conAudit").singleResult();
		if (null == task) {
			return new Json(false, "不能撤销！任务已不在审批节点!");
		}
		String assignee = task.getAssignee();
		if (!StringUtils.isBlank(assignee)) {
			return new Json(false, "任务已被签收，不能撤回！！！");
		}
		JumpTaskCmd cmd = new JumpTaskCmd(task, "makeCon", JumpTaskCmd.WITHDRAW);
		TaskServiceImpl taskServiceImpl = (TaskServiceImpl) taskService;
		taskServiceImpl.getCommandExecutor().execute(cmd);
		String processId = task.getProcessInstanceId();
		// 修改申请主表状态
		List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(applyId).processInstanceId(processId).list();
		String precessStatus = "";
		String precessStatusVal = "";
		boolean isFirst = true;
		for (Task tk : tasks) {
			if (isFirst) {
				precessStatus = tk.getTaskDefinitionKey();
				precessStatusVal = tk.getName();
				isFirst = false;
			} else {
				precessStatus = precessStatus + "," + tk.getTaskDefinitionKey();
				precessStatusVal = precessStatusVal + "," + tk.getName();
			}
		}
		userApplyMapper.updateProcessInstance(applyId, processId, precessStatus, precessStatusVal);
		// 添加操作记录
		MakingOpinion opinion = opinionMapper.selectByApplyIdAndTaskId(applyId, task.getId());
		if (opinion == null) {
			opinion = new MakingOpinion();
			opinion.setMakingId(PrimaryKeyUtil.getPrimaryKey());
			opinion.setApplyId(applyId);
			opinion.setEmpId(empId);
			opinion.setEmpName(session.getEmpName());
			opinion.setMakingTimes(new Date());
			opinion.setProcInstId(processId);
			opinion.setPrecessStatus(precessStatus);
			opinion.setPrecessStatusVal(precessStatusVal);
			opinion.setHandleOpinion(3);
			opinion.setHandleOpinionVal("撤销");
			opinion.setTaskId(task.getId());
			opinion.setRemarks(remark);
			opinionMapper.insertSelective(opinion);
		} else {
			opinion.setMakingId(PrimaryKeyUtil.getPrimaryKey());
			opinion.setApplyId(applyId);
			opinion.setEmpId(empId);
			opinion.setEmpName(session.getEmpName());
			opinion.setMakingTimes(new Date());
			opinion.setProcInstId(processId);
			opinion.setPrecessStatus(precessStatus);
			opinion.setPrecessStatusVal(precessStatusVal);
			opinion.setHandleOpinion(3);
			opinion.setHandleOpinionVal("撤销");
			opinion.setTaskId(task.getId());
			opinion.setRemarks(remark);
			opinionMapper.updateByPrimaryKeySelective(opinion);
		}
		return new Json(true, "撤销成功!");
	}

	@Override
	public Json rollback(String applyId, SessionRouter session, String remark) {
		String empId = session.getEmpId();
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数有误!");
		}
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("makeCon").singleResult();
		if (null == task) {
			return new Json(false, "不能退回！任务已不在当前节点!");
		}

		JumpTaskCmd cmd = new JumpTaskCmd(task, "finalAudit", JumpTaskCmd.ROLLBACK);
		TaskServiceImpl taskServiceImpl = (TaskServiceImpl) taskService;
		taskServiceImpl.getCommandExecutor().execute(cmd);
		String processId = task.getProcessInstanceId();
		// 修改申请主表状态
		List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(applyId).processInstanceId(processId).list();
		String precessStatus = "";
		String precessStatusVal = "";
		boolean isFirst = true;
		for (Task tk : tasks) {
			if (isFirst) {
				precessStatus = tk.getTaskDefinitionKey();
				precessStatusVal = tk.getName();
				isFirst = false;
			} else {
				precessStatus = precessStatus + "," + tk.getTaskDefinitionKey();
				precessStatusVal = precessStatusVal + "," + tk.getName();
			}
		}
		userApplyMapper.updateProcessInstance(applyId, processId, precessStatus, precessStatusVal);
		MakingOpinion opinion = opinionMapper.selectByApplyIdAndTaskId(applyId, task.getId());
		if (opinion == null) {
			opinion = new MakingOpinion();
			opinion.setMakingId(PrimaryKeyUtil.getPrimaryKey());
			opinion.setApplyId(applyId);
			opinion.setEmpId(empId);
			opinion.setEmpName(session.getEmpName());
			opinion.setMakingTimes(new Date());
			opinion.setProcInstId(processId);
			opinion.setPrecessStatus(precessStatus);
			opinion.setPrecessStatusVal(precessStatusVal);
			opinion.setHandleOpinion(2);
			opinion.setHandleOpinionVal("退回");
			opinion.setTaskId(task.getId());
			opinion.setRemarks(remark);
			opinionMapper.insertSelective(opinion);
		} else {
			opinion.setMakingId(PrimaryKeyUtil.getPrimaryKey());
			opinion.setApplyId(applyId);
			opinion.setEmpId(empId);
			opinion.setEmpName(session.getEmpName());
			opinion.setMakingTimes(new Date());
			opinion.setProcInstId(processId);
			opinion.setPrecessStatus(precessStatus);
			opinion.setPrecessStatusVal(precessStatusVal);
			opinion.setHandleOpinion(2);
			opinion.setHandleOpinionVal("退回");
			opinion.setTaskId(task.getId());
			opinion.setRemarks(remark);
			opinionMapper.updateByPrimaryKeySelective(opinion);
		}
		return new Json(true, "退回成功!");
	}

	@Override
	public Json waiver(String applyId, SessionRouter session, String remark) {
		String empId = session.getEmpId();
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数有误!");
		}
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("makeCon").singleResult();
		if (null == task) {
			return new Json(false, "不能客户放弃！任务已不在当前节点!");
		}

		JumpTaskCmd cmd = new JumpTaskCmd(task, "endEvent", JumpTaskCmd.WAIVER);
		TaskServiceImpl taskServiceImpl = (TaskServiceImpl) taskService;
		taskServiceImpl.getCommandExecutor().execute(cmd);
		String processId = task.getProcessInstanceId();
		String precessStatus = "";
		String precessStatusVal = "";
		// 修改申请主表状态
		List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(applyId).processInstanceId(processId).list();
		if (tasks == null || tasks.size() < 1) {
			userApplyMapper.updateProcessInstance(applyId, processId, JumpTaskCmd.WAIVER, "客户放弃");
			precessStatus = "1";
			precessStatusVal = "客户放弃";
		} else {

			boolean isFirst = true;
			for (Task tk : tasks) {
				if (isFirst) {
					precessStatus = tk.getTaskDefinitionKey();
					precessStatusVal = tk.getName();
					isFirst = false;
				} else {
					precessStatus = precessStatus + "," + tk.getTaskDefinitionKey();
					precessStatusVal = precessStatusVal + "," + tk.getName();
				}
			}
			userApplyMapper.updateProcessInstance(applyId, processId, precessStatus, precessStatusVal);
		}
		MakingOpinion opinion = opinionMapper.selectByApplyIdAndTaskId(applyId, task.getId());
		if (opinion == null) {
			opinion = new MakingOpinion();
			opinion.setMakingId(PrimaryKeyUtil.getPrimaryKey());
			opinion.setApplyId(applyId);
			opinion.setEmpId(empId);
			opinion.setEmpName(session.getEmpName());
			opinion.setMakingTimes(new Date());
			opinion.setProcInstId(processId);
			opinion.setPrecessStatus(precessStatus);
			opinion.setPrecessStatusVal(precessStatusVal);
			opinion.setHandleOpinion(1);
			opinion.setHandleOpinionVal("客户放弃");
			opinion.setTaskId(task.getId());
			opinion.setRemarks(remark);
			opinionMapper.insertSelective(opinion);
		} else {
			opinion.setMakingId(PrimaryKeyUtil.getPrimaryKey());
			opinion.setApplyId(applyId);
			opinion.setEmpId(empId);
			opinion.setEmpName(session.getEmpName());
			opinion.setMakingTimes(new Date());
			opinion.setProcInstId(processId);
			opinion.setPrecessStatus(precessStatus);
			opinion.setPrecessStatusVal(precessStatusVal);
			opinion.setHandleOpinion(1);
			opinion.setHandleOpinionVal("客户放弃");
			opinion.setTaskId(task.getId());
			opinion.setRemarks(remark);
			opinionMapper.updateByPrimaryKeySelective(opinion);
		}
		// 添加操作记录

		return new Json(true, "放弃成功!");
	}

	public Json selectTask(String applyId) {
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("makeCon").singleResult();
		if (null == task) {
			return new Json(false, "不是当前节点，不能上传资料");
		} else {
			return new Json(true, "ok");
		}
	}

	@Override
	public Json upload(MultipartFile file, String applyId, Integer imgId, String imgIdVal, String loginEmp, String loginName, String context) {
		if (null == file || file.isEmpty() || StringUtils.isBlank(applyId)) {
			return new Json(false, "上传失败,参数不能为空!");
		}

		String fileName = file.getOriginalFilename();
		if (StringUtils.isBlank(fileName)) {
			return new Json(false, "上传失败,参数不能为空!");
		}

		String fileType = fileName.substring(fileName.lastIndexOf("."));
		if (!StringUtils.isBlank(fileType) && (!".jpg".equalsIgnoreCase(fileType) && !".png".equalsIgnoreCase(fileType) && !".gif".equalsIgnoreCase(fileType) && !".bmp".equalsIgnoreCase(fileType) && !".jpeg".equalsIgnoreCase(fileType))) {
			return new Json(false, "图片格式不正确");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskAssignee(loginEmp).singleResult();
		Timestamp currentTime = DateUtil.getTimestamp();
		Annex annex = new Annex();
		String id = PrimaryKeyUtil.getPrimaryKey();
		annex.setAnxId(id);
		annex.setActNode(task.getTaskDefinitionKey());
		annex.setApplyId(applyId);
		annex.setEmpId(loginEmp);
		annex.setFileSize(file.getSize());
		annex.setCreateDate(currentTime);
		annex.setFileType(fileType);
		annex.setFileName(fileName);
		annex.setPreViewHost(context);
		annex.setApplyTypeId(imgId);

		annex.setApplyTypeVal(imgIdVal);

		File savefile = new File(uploadPathUtil.saveContract() + File.separator + applyId);
		if (!savefile.exists()) {
			savefile.mkdirs();
		}

		String saveName = new Date().getTime() + fileType;// 时间戳
		annex.setSaveName(saveName);
		// 文件保存路径
		String savePath = savefile.getAbsolutePath() + File.separator + saveName;
		// 映射地址
		String preView = uploadPathUtil.contractUrl() + "/" + applyId + "/" + saveName;
		// 转存文件
		try {
			file.transferTo(new File(savePath));
			annex.setSavePath(savePath);
			annex.setPreView(preView);
			annexMapper.insertSelective(annex);
			JSONObject obj = new JSONObject();
			obj.put("preView", preView);
			obj.put("anxId", id);
			return new Json(true, "保存成功！", obj);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Json(false, "上传失败！");
	}

	@Override
	public Integer findByapplyIdAndAnnexType(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			return null;
		}
		return annexMapper.findByApplyIdImgTypeMulti(applyId);
	}

	public Json deleteAnnex(String id) {
		int result = annexMapper.deleteByPrimaryKey(id);
		if (result > 0) {
			return new Json(true, "附件删除成功!");
		} else {
			return new Json(false, "附件删除失败!");
		}
	}

	// 确认招标接口
	public Map<String, Object> lendPasses(String applyId) {
		log.info("【确认招标推送 协议拟制 开始】");
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject object = new JSONObject();
		UserApply apply = userApplyMapper.selectByAply(applyId);
		SysContract contract = sysContractMapper.findByApplyId(applyId);
		String loanRefId = apply.getLoanId();// 信贷唯一编号
		String cardNumber = contract.getBankNo();// 银行卡号6222022604008656610
		String bankType = contract.getBank();// 开户行代码(需满足红小宝16家银行)
		String bankAddress = contract.getBackBranchAddr();// 支行详细名称
		String province = contract.getProName();// 开户省份
		String city = contract.getCityName();// 开户城市
		String preMobile = contract.getBankPhoneNo();// 银行卡手机预留号
		Boolean onlyCreateAccount = true;// true 只开户绑卡，不确认招标 false,既开户绑卡，有确认招标
		Map<String, Object> loanMap = new HashMap<String, Object>();
		loanMap.put("loanRefId", loanRefId);
		Map<String, Object> bankMap = new HashMap<String, Object>();
		bankMap.put("cardNumber", cardNumber);
		bankMap.put("bankType", bankType);
		bankMap.put("bankAddress", bankAddress);
		bankMap.put("province", province);
		bankMap.put("city", city);
		bankMap.put("preMobile", preMobile);
		object.put("loan", loanMap);
		object.put("bank", bankMap);
		object.put("generalInfo", CreateSerialNo.sign());
		object.put("onlyCreateAccount", onlyCreateAccount);
		String json = object.toJSONString();
		// String url = "http://123.126.19.2:8040/lend/pass/";//红小宝测试地址
		// String url = "http://lend.hoomxb.com/lend/pass/";//红小宝正式地址
		String url = lendPass;// 红小宝正式地址
		log.info("【确认招标推送 协议拟制 推送数据】", json);
		String result = HttpClientUtil.doPostJson(url, null, null, json);
		log.info("【确认招标推送 协议拟制 推送返回值】", result);
		// 处理还款明细表数据
		JSONObject resultObj = JSONObject.parseObject(result);
		boolean verify = CreateSerialNo.decode(resultObj.getJSONObject("generalInfo"));
		if (!verify) {
			map.put("status", resultObj.getIntValue("status"));
			map.put("errMsg", "验签失败！");
			return map;
		}
		map.put("status", resultObj.getIntValue("status"));
		map.put("errMsg", resultObj.get("errMsg"));
		return map;
	}

	@Override
	public Json multiUpload(MultipartFile[] files, String applyId, Integer imgId, String imgIdVal, String loginEmp, String loginName, String context) {
		if (files == null || files.length < 1) {
			return new Json(false, "参数有误");
		}
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "上传失败,参数不能为空!");
		}
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskAssignee(loginEmp).singleResult();
		JSONArray result = new JSONArray();
		for (MultipartFile file : files) {
			if (null == file || file.isEmpty() || StringUtils.isBlank(applyId)) {
				return new Json(false, "上传失败,参数不能为空!");
			}

			String fileName = file.getOriginalFilename();
			if (StringUtils.isBlank(fileName)) {
				return new Json(false, "上传失败,参数不能为空!");
			}

			String fileType = fileName.substring(fileName.lastIndexOf("."));
			if (!StringUtils.isBlank(fileType)
					&& (!".jpg".equalsIgnoreCase(fileType) && !".png".equalsIgnoreCase(fileType) && !".gif".equalsIgnoreCase(fileType) && !".bmp".equalsIgnoreCase(fileType) && !".jpeg".equalsIgnoreCase(fileType))) {
				return new Json(false, "图片格式不正确");
			}

			
			Timestamp currentTime = DateUtil.getTimestamp();
			Annex annex = new Annex();
			String id = PrimaryKeyUtil.getPrimaryKey();
			annex.setAnxId(id);
			annex.setActNode(task.getTaskDefinitionKey());
			annex.setApplyId(applyId);
			annex.setEmpId(loginEmp);
			annex.setFileSize(file.getSize());
			annex.setCreateDate(currentTime);
			annex.setFileType(fileType);
			annex.setFileName(fileName);
			annex.setPreViewHost(context);
			annex.setApplyTypeId(imgId);

			annex.setApplyTypeVal(imgIdVal);

			File savefile = new File(uploadPathUtil.saveContract() + File.separator + applyId);
			if (!savefile.exists()) {
				savefile.mkdirs();
			}

			String saveName = UUID.randomUUID().toString().replace("-", "") + fileType;// 时间戳
			annex.setSaveName(saveName);
			// 文件保存路径
			String savePath = savefile.getAbsolutePath() + File.separator + saveName;
			// 映射地址
			String preView = uploadPathUtil.contractUrl() + "/" + applyId + "/" + saveName;
			// 转存文件
			try {
				file.transferTo(new File(savePath));
				annex.setSavePath(savePath);
				annex.setPreView(preView);
				annexMapper.insertSelective(annex);
				JSONObject obj = new JSONObject();
				obj.put("preView", preView);
				obj.put("anxId", id);
				obj.put("saveName", saveName);
				obj.put("oldName", fileName);
				result.add(obj);
			} catch (IllegalStateException e) {
				e.printStackTrace();
				log.error("【协议拟制 文件上传异常 批量上传】", e);
			} catch (IOException e) {
				e.printStackTrace();
				log.error("【协议拟制 文件上传异常 批量上传】", e);
			}
		}
		return new Json(true, "保存成功！", result);
	}

}
