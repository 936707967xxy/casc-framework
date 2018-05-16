/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.common.util.JumpTaskCmd;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.common.util.UploadPathUtil;
import com.hoomsun.core.dao.SysEmployeeMapper;
import com.hoomsun.core.enums.AnnexType;
import com.hoomsun.csas.audit.dao.BPMNMapper;
import com.hoomsun.csas.audit.dao.PhoneVerifyMapper;
import com.hoomsun.csas.audit.dao.PreAuditMapper;
import com.hoomsun.csas.audit.exception.AuditException;
import com.hoomsun.csas.audit.model.PhoneVerify;
import com.hoomsun.csas.audit.model.PreAudit;
import com.hoomsun.csas.audit.model.vo.UserApplyVO;
import com.hoomsun.csas.audit.server.inter.PreAuditServerI;
import com.hoomsun.csas.audit.util.ActivitiUtils;
import com.hoomsun.csas.sales.api.model.Annex;
import com.hoomsun.csas.sales.api.model.CreditCard;
import com.hoomsun.csas.sales.api.model.UserChsi;
import com.hoomsun.csas.sales.api.model.UserCis;
import com.hoomsun.csas.sales.api.model.UserHouseFund;
import com.hoomsun.csas.sales.api.model.UserPbccre;
import com.hoomsun.csas.sales.api.model.UserSocialsecurity;
import com.hoomsun.csas.sales.api.model.UserTaoBaoAddress;
import com.hoomsun.csas.sales.api.model.UserTaobao;
import com.hoomsun.csas.sales.api.model.vo.CreditVo;
import com.hoomsun.csas.sales.api.model.vo.FinancialVo;
import com.hoomsun.csas.sales.api.model.vo.OtherVo;
import com.hoomsun.csas.sales.api.model.vo.RollBackInfoVo;
import com.hoomsun.csas.sales.dao.AnnexMapper;
import com.hoomsun.csas.sales.dao.CreditCardMapper;
import com.hoomsun.csas.sales.dao.UserApplyMapper;
import com.hoomsun.csas.sales.dao.UserChsiMapper;
import com.hoomsun.csas.sales.dao.UserCisMapper;
import com.hoomsun.csas.sales.dao.UserHouseFundMapper;
import com.hoomsun.csas.sales.dao.UserPbccreMapper;
import com.hoomsun.csas.sales.dao.UserSocialsecurityMapper;
import com.hoomsun.csas.sales.dao.UserTaoBaoAddressMapper;
import com.hoomsun.csas.sales.dao.UserTaobaoMapper;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月4日 <br>
 * 描述:初审审核的业务实现
 */
@Service("preAuditServer")
public class PreAuditServerImpl implements PreAuditServerI {
	private final static Logger log = LoggerFactory.getLogger(PreAuditServerImpl.class);
	
	@Autowired
	private PreAuditMapper preAuditMapper;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private UserCisMapper userCisMapper;
	@Autowired
	private UserTaobaoMapper userTaobaoMapper;
	@Autowired
	private AnnexMapper annexMapper;// 上传表
	@Autowired
	private UploadPathUtil uploadPathUtil;
	@Autowired
	private UserTaoBaoAddressMapper userTaobaoAddrMapper;
	@Autowired
	private UserChsiMapper userChsiMapper;
	@Autowired
	private UserPbccreMapper userPbccreMapper;
	@Autowired
	private UserHouseFundMapper userHouseFundMapper;
	@Autowired
	private UserSocialsecurityMapper userSocialsecurityMapper;
	@Autowired
	private CreditCardMapper creditCardMapper;
	@Autowired
	private PhoneVerifyMapper phoneVerifyMapper;
	@Autowired
	private UserApplyMapper userApplyMapper;
	@Autowired
	private BPMNMapper bpmnMapper;
	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;// 员工表

	// 列表
	@Override
	public Pager<UserApplyVO> findPager(Integer page, Integer rows, String custName, String idNumber, String loanId, String empId, String nodeStatus, String node, String deptId) {
		if (StringUtils.isBlank(empId) || StringUtils.isBlank(deptId)) {
			throw new AuditException("无法获取你的登录信息,请尝试重新登录!");
		}

		Map<String, Object> param = new HashMap<String, Object>();
		// List<Group> groups =
		// identityService.createGroupQuery().groupMember(empId).list();
		//
		// boolean isGroup = false;
		// for (Group group : groups) {
		// if ("PRE_AUDIT_GROUP".equalsIgnoreCase(group.getId())) {
		// isGroup = true;
		// break;
		// }
		// }
		//
		// if (!isGroup) {
		// throw new AuditException("您没有权限操作！不是该审批组人员!");
		// }

		// 验证当前登录人是不是部门负责人
		String mgrId = sysEmployeeMapper.findDeptManager(deptId);
		Integer isMgr = 0;//
		if (empId.equals(mgrId)) {// 是部门的负责人
			isMgr = 1;
		}

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
		param.put("isMgr", isMgr);
		param.put("deptId", deptId);
		param.put("pageIndex", page);
		param.put("pageSize", rows);

		if (!StringUtils.isBlank(custName)) {
			param.put("custName", "%" + custName + "%");
		}

		if (!StringUtils.isBlank(idNumber)) {
			param.put("idNumber", idNumber);
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

		List<UserApplyVO> userApplyVOs = preAuditMapper.findPager(param);
		Integer total = preAuditMapper.findPreAuditCount(param);
		return new Pager<>(userApplyVOs, total);
	}

	// 签收任务
	@Override
	public Json claimTask(String applyId, String empId) {
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("preAudit").singleResult();
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

	// 处理任务 提交
	@Override
	public Json completeTask(String applyId, String empId) {
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("preAudit").taskAssignee(empId).singleResult();
		if (task == null) {
			return new Json(false, "不能处理,任务在其他节点!");
		}

		taskService.complete(task.getId());
		String processId = task.getProcessInstanceId();
		preAuditMapper.updatePreStatus(applyId, task.getId(), processId, 1);
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
		return new Json(true, "处理成功!");
	}

	// 退回
	@Override
	public Json rollbackTask(String applyId, String empId) {
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数有误!");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("preAudit").singleResult();
		if (null == task) {
			return new Json(false, "不能撤销！任务已不在当前节点!");
		}

		JumpTaskCmd cmd = new JumpTaskCmd(task, "addData", JumpTaskCmd.ROLLBACK);
		TaskServiceImpl taskServiceImpl = (TaskServiceImpl) taskService;
		taskServiceImpl.getCommandExecutor().execute(cmd);
		return new Json(true, "退回成功!");
	}

	// 撤回
	@Override
	public Json withdrawTask(String applyId, String empId) {
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数有误!");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("finalAudit").singleResult();
		if (null == task) {
			return new Json(false, "不能撤销！任务已不在终审节点!");
		}
		String assignee = task.getAssignee();
		if (!StringUtils.isBlank(assignee)) {
			return new Json(false, "任务已被签收，不能撤回！！！");
		}

		JumpTaskCmd cmd = new JumpTaskCmd(task, "preAudit", JumpTaskCmd.WITHDRAW);
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
		return new Json(true, "撤销成功!");
	}

	@Override
	public Json claimTask(String loginEmp, String toEmpId, String applyId) {
		if (StringUtils.isBlank(applyId)) {
			throw new AuditException("参数异常！applyId is null!");
		}
		if (StringUtils.isBlank(toEmpId)) {
			throw new AuditException("参数异常！toEmpId is null!");
		}
		if (StringUtils.isBlank(loginEmp)) {
			throw new AuditException("参数异常！loginEmp is null!");
		}

		if (loginEmp.equals(toEmpId)) {
			return new Json(false, "任务已经在您的节点！无需转办！");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("preAudit").singleResult();
		if (task == null) {
			return new Json(false, "不能处理,任务在其他节点!");
		}

		taskService.claim(task.getId(), toEmpId);

		return new Json(true, "任务转办成功!");
	}

	@Override
	public Json savePreAudit(PreAudit audit, String loginEmp) {
		if (audit == null || StringUtils.isBlank(loginEmp)) {
			return new Json(false, "参数异常！");
		}

		String applyId = audit.getApplyId();

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("preAudit").singleResult();
		if (null == task) {
			return new Json(false, "不能处理！任务已不在当前节点!");
		}

		// 首先判断审核数据时候已经存在
		String taskId = task.getId();
		String processId = task.getProcessInstanceId();
		audit.setHandleTime(DateUtil.getSqlDate());
		audit.setApplyId(applyId);
		audit.setTaskId(taskId);
		audit.setProcInstId(task.getProcessInstanceId());
		PreAudit preInfo = preAuditMapper.selectIdByProcess(applyId, taskId);
		if (preInfo != null) {// 修改
			preInfo.setEmpId(audit.getEmpId());
			preInfo.setEmpName(audit.getEmpName());
			preInfo.setInnerRemark(audit.getInnerRemark());
			preInfo.setRemark(audit.getRemark());
			preInfo.setTaskId(taskId);
			preInfo.setProcInstId(processId);
			preInfo.setCreditRemark(audit.getCreditRemark());
			preInfo.setFinanceRemark(audit.getFinanceRemark());
			preInfo.setOtherRemark(audit.getOtherRemark());
			preInfo.setHandleOption(audit.getHandleOption());
			preInfo.setHandleOptionval(audit.getHandleOptionval());
			preInfo.setProductId(audit.getProductId());
			preInfo.setProductName(audit.getProductName());
			preInfo.setApplyId(applyId);
			preInfo.setHandleTime(DateUtil.getSqlDate());
			preInfo.setHandleMoney(audit.getHandleMoney());
			preAuditMapper.updateByPrimaryKeySelective(preInfo);
		} else {
			audit.setPreId(PrimaryKeyUtil.getPrimaryKey());
			preAuditMapper.insertSelective(audit);
		}

		Integer option = audit.getHandleOption();
		// String proAlias = userApplyMapper.selectProNameByApplyId(applyId);
		Map<String, Object> var = new HashMap<String, Object>();
		switch (option) {
		case 0:// 拒贷(拒贷跳到终审)
			// JumpTaskCmd cmd = new JumpTaskCmd(task, "endEvent",
			// JumpTaskCmd.REJECT);
			// TaskServiceImpl taskServiceImpl = (TaskServiceImpl) taskService;
			// taskServiceImpl.getCommandExecutor().execute(cmd);
			var.put("presupp", 0);
			taskService.complete(task.getId(), var);

			// noticeServer.sendMsg(applyId,"您提交的"+ proAlias + "申请未通过初审审核，原因：" +
			// audit.getInnerRemark(),1);
			break;
		case 1:// 客户放弃
			JumpTaskCmd cmd = new JumpTaskCmd(task, "endEvent", JumpTaskCmd.WAIVER);
			TaskServiceImpl taskServiceImpl = (TaskServiceImpl) taskService;
			taskServiceImpl.getCommandExecutor().execute(cmd);
			break;
		case 2:// 退回 质检复核
			// cmd = new JumpTaskCmd(task, "qcCheck", JumpTaskCmd.ROLLBACK);
			// taskServiceImpl = (TaskServiceImpl) taskService;
			// taskServiceImpl.getCommandExecutor().execute(cmd);
			// break;
			var.put("presupp", 1);
			var.put("goto", "preaudit");
			taskService.complete(task.getId(), var);
			break;
		case 3:// 补充资料
			var.put("presupp", 1);
			var.put("goto", "preaudit");
			taskService.complete(task.getId(), var);
			// noticeServer.sendMsg(applyId,"您提交的"+ proAlias +
			// "申请资料不全，请及时前往门店补充资料",1);
			break;
		case 4:// 通过
			var.put("presupp", 0);
			taskService.complete(task.getId(), var);
			// noticeServer.sendMsg(applyId,"您提交的"+ proAlias +
			// "申请已通过信审的初步审核，请等待信审部门终审",1);
			break;
		default:
			break;
		}

		List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(applyId).processInstanceId(processId).list();
		String precessStatus = "";
		String precessStatusVal = "";
		if (tasks != null && tasks.size() > 0) {
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
		} else {
			switch (option) {
			case 0:// 拒贷
				precessStatus = option == 1 ? JumpTaskCmd.REJECT : "error";
				precessStatusVal = option == 1 ? "拒贷" : "未知";
				break;
			case 1:// 客户放弃
				precessStatus = option == 1 ? JumpTaskCmd.WAIVER : "error";
				precessStatusVal = option == 1 ? "客户放弃" : "未知";
				break;
			default:
				precessStatus = "error";
				precessStatusVal = "未知";
				break;
			}
		}

		userApplyMapper.updateProcessInstance(applyId, processId, precessStatus, precessStatusVal);
		return new Json(true, "保存成功!");
	}

	@Override
	public Json saveCredit(CreditVo creditVo, String loginEmp) {
		if (creditVo == null || creditVo.getPbccre() == null || StringUtils.isBlank(loginEmp)) {
			throw new AuditException("参数异常！params is null ！");
		}

		String applyId = creditVo.getPbccre().getApplyId();
		if (StringUtils.isBlank(applyId)) {
			throw new AuditException("参数异常！applyId is null ！");
		}

		UserPbccre pbccre = creditVo.getPbccre();
		if (pbccre != null) {
			// 征信数据 首先查看是否有征信 若无则添加 有则修改
			Integer result = userPbccreMapper.checkByApplyId(applyId);
			pbccre.setApplyId(applyId);
			if (result > 0) {
				userPbccreMapper.updateByPrimaryKeySelective(pbccre);
			} else {
				pbccre.setCrId(PrimaryKeyUtil.getPrimaryKey());
				userPbccreMapper.insertSelective(pbccre);
			}
		}

		// 上海资信
		UserCis userCis = creditVo.getUserCis();
		if (userCis != null) {
			Integer result = userCisMapper.checkByApplyId(applyId);
			userCis.setApplyId(applyId);
			if (result > 0) {
				userCisMapper.updateByPrimaryKeySelective(userCis);
			} else {
				userCis.setCisId(PrimaryKeyUtil.getPrimaryKey());
				userCisMapper.insertSelective(userCis);

			}
		}
		return new Json(true, "征信数据保存成功！");
	}

	@Override
	public Json savePbccre(CreditVo creditVo, String loginEmp) {
		if (creditVo == null || creditVo.getPbccre() == null || StringUtils.isBlank(loginEmp)) {
			throw new AuditException("参数异常！params is null ！");
		}

		String applyId = creditVo.getPbccre().getApplyId();
		if (StringUtils.isBlank(applyId)) {
			throw new AuditException("参数异常！applyId is null ！");
		}

		UserPbccre pbccre = creditVo.getPbccre();
		if (pbccre != null) {
			// 征信数据 首先查看是否有征信 若无则添加 有则修改
			Integer result = userPbccreMapper.checkByApplyId(applyId);
			pbccre.setApplyId(applyId);
			if (result > 0) {
				userPbccreMapper.updateByPrimaryKeySelective(pbccre);
			} else {
				pbccre.setCrId(PrimaryKeyUtil.getPrimaryKey());
				userPbccreMapper.insertSelective(pbccre);
			}
		}
		return new Json(true, "征信数据保存成功！");
	}

	@Override
	public Json saveCis(CreditVo creditVo, String loginEmp) {
		if (creditVo == null || creditVo.getPbccre() == null || StringUtils.isBlank(loginEmp)) {
			throw new AuditException("参数异常！params is null ！");
		}

		String applyId = creditVo.getPbccre().getApplyId();
		if (StringUtils.isBlank(applyId)) {
			throw new AuditException("参数异常！applyId is null ！");
		}

		// 上海资信
		UserCis userCis = creditVo.getUserCis();
		if (userCis != null) {
			Integer result = userCisMapper.checkByApplyId(applyId);
			userCis.setApplyId(applyId);
			if (result > 0) {
				userCisMapper.updateByPrimaryKeySelective(userCis);
			} else {
				userCis.setCisId(PrimaryKeyUtil.getPrimaryKey());
				userCisMapper.insertSelective(userCis);

			}
		}
		return new Json(true, "征信数据保存成功！");
	}

	// 财务数据
	@Override
	public Json saveFinancial(FinancialVo financialVo, String applyId, String loginEmp) {
		if (financialVo == null || StringUtils.isBlank(loginEmp) || StringUtils.isBlank(applyId)) {
			throw new AuditException("参数异常！params is null ！");
		}

		// 信用卡账单
		List<CreditCard> creditCards = financialVo.getCreditCards();
		if (creditCards != null && creditCards.size() > 0) {
			// Integer result = creditCardBillsMapper.checkByApplyId(applyId);
			creditCardMapper.deleteByApplyId(applyId);
			for (CreditCard cc : creditCards) {
				cc.setCcId(PrimaryKeyUtil.getPrimaryKey());
				cc.setApplyId(applyId);
				creditCardMapper.insertSelective(cc);
			}
		}

		return new Json(true, "数据保存成功！");
	}

	// 保存电核信息
	@Override
	public Json saveTelAudit(PhoneVerify telAudit, String applyId, String loginEmp) {
		if (telAudit == null || StringUtils.isBlank(loginEmp) || StringUtils.isBlank(applyId)) {
			throw new AuditException("参数异常！params is null ！");
		}

		String pvId = telAudit.getPvId();
		if (StringUtils.isBlank(pvId)) {
			telAudit.setPvId(PrimaryKeyUtil.getPrimaryKey());
			phoneVerifyMapper.insertSelective(telAudit);
		} else {
			phoneVerifyMapper.updateByPrimaryKeySelective(telAudit);
		}
		return new Json(true, "保存成功！");
	}

	@Override
	public Json saveOthor(OtherVo otherVo, String applyId, String loginEmp) {

		if (otherVo == null || StringUtils.isBlank(loginEmp) || StringUtils.isBlank(applyId)) {
			throw new AuditException("参数异常！params is null ！");
		}

		// 学历
		UserChsi chsi = otherVo.getUserChsi();
		if (chsi != null) {
			Integer result = userChsiMapper.checkByApplyId(applyId);
			if (result > 0) {
				userChsiMapper.updateByPrimaryKey(chsi);
			} else {
				chsi.setChsiId(PrimaryKeyUtil.getPrimaryKey());
				chsi.setApplyId(applyId);
				userChsiMapper.insertSelective(chsi);
			}
		}

		// 公积金
		UserHouseFund houseFund = otherVo.getHouseFund();
		if (houseFund != null) {
			Integer result = userHouseFundMapper.checkByApplyId(applyId);
			if (result > 0) {
				userHouseFundMapper.updateByPrimaryKey(houseFund);
			} else {
				houseFund.setHfId(PrimaryKeyUtil.getPrimaryKey());
				houseFund.setApplyId(applyId);
				userHouseFundMapper.insertSelective(houseFund);
			}
		}

		// 社保
		UserSocialsecurity socialsecurity = otherVo.getSocialsecurity();
		if (socialsecurity != null) {
			Integer result = userSocialsecurityMapper.checkByApplyId(applyId);
			if (result > 0) {
				userSocialsecurityMapper.updateByPrimaryKey(socialsecurity);
			} else {
				socialsecurity.setSiId(PrimaryKeyUtil.getPrimaryKey());
				socialsecurity.setApplyId(applyId);
				userSocialsecurityMapper.insertSelective(socialsecurity);
			}
		}

		// 淘宝
		UserTaobao taobao = otherVo.getTaobao();
		if (socialsecurity != null) {
			Integer result = userTaobaoMapper.checkByApplyId(applyId);
			String tbId = taobao.getTbId();
			if (result > 0 && StringUtils.isNotBlank(tbId)) {
				userTaobaoMapper.updateByPrimaryKey(taobao);
				userTaobaoAddrMapper.deleteByFKId(tbId);
			} else {
				tbId = PrimaryKeyUtil.getPrimaryKey();
				taobao.setTbId(tbId);
				taobao.setApplyId(applyId);
				userTaobaoMapper.insertSelective(taobao);
			}

			List<UserTaoBaoAddress> addresses = taobao.getAddresses();
			if (null != addresses && addresses.size() > 0) {
				for (UserTaoBaoAddress address : addresses) {
					address.setTbAddressId(PrimaryKeyUtil.getPrimaryKey());
					address.setTbFkid(tbId);
					userTaobaoAddrMapper.insertSelective(address);
				}
			}
		}
		return new Json(true, "保存成功！");
	}

	// 验证签收
	@Override
	public Json checkClaim(String applyId, String empId) {
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("preAudit").singleResult();
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

	// 历史任务
	@Override
	public List<HistoricTaskInstance> findAuditHistory(String applyId) {
		List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(applyId).orderByTaskCreateTime().asc().list();
		return historicTaskInstances;
	}

	@Override
	public PreAudit finByApplyId(String applyId) {
		return preAuditMapper.findByApplyId(applyId);
	}

	@Override
	public PreAudit selectByEcxxess(String applyId) {
		return preAuditMapper.findByApplyId(applyId);
	}

	@Override
	public Json upload(MultipartFile file, String applyId, String loginEmp, String loginName, String context) {
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
		annex.setApplyTypeId(AnnexType.ANNEX_AUDIT.getType());
		annex.setApplyTypeVal(AnnexType.ANNEX_AUDIT.getName());

		File savefile = new File(uploadPathUtil.saveAudit() + File.separator + applyId);
		if (!savefile.exists()) {
			savefile.mkdirs();
		}

		String saveName = UUID.randomUUID().toString().replace("-", "") + fileType;// 时间戳
		annex.setSaveName(saveName);
		// 文件保存路径
		String savePath = savefile.getAbsolutePath() + File.separator + saveName;
		// 映射地址
		String preView = uploadPathUtil.auditUrl() + "/" + applyId + "/" + saveName;
		// 转存文件
		try {
			file.transferTo(new File(savePath));
			annex.setSavePath(savePath);
			annex.setPreView(preView);
			annexMapper.insertSelective(annex);
			JSONObject obj = new JSONObject();
			obj.put("preView", preView);
			obj.put("uid", id);
			return new Json(true, "保存成功！", obj);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Json(false, "上传失败！");
	}

	@Override
	public Json deleteAnnex(String id) {
		int result = annexMapper.deleteByPrimaryKey(id);
		if (result > 0) {
			return new Json(true, "附件删除成功!");
		} else {
			return new Json(false, "附件删除失败!");
		}
	}

	@Override
	public List<Annex> findByapplyIdAndAnnexType(String applyId, Integer anxType) {
		if (StringUtils.isBlank(applyId)) {
			return null;
		}
		return annexMapper.findByApplyIdImgType(applyId, anxType);
	}

	@Override
	public List<RollBackInfoVo> findPreRollBackHis(String applyId) {
		return preAuditMapper.selectRollBackInfo(applyId);
	}

	@Override
	public Json saveAuditInfo(PreAudit audit, String loginEmp) {
		if (audit == null || StringUtils.isBlank(loginEmp)) {
			return new Json(false, "参数异常！");
		}

		String applyId = audit.getApplyId();

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("preAudit").singleResult();
		if (null == task) {
			return new Json(false, "不能处理！任务已不在当前节点!");
		}

		// 首先判断审核数据时候已经存在
		String taskId = task.getId();
		audit.setHandleTime(DateUtil.getSqlDate());
		audit.setApplyId(applyId);
		audit.setTaskId(taskId);
		audit.setProcInstId(task.getProcessInstanceId());
		int result = 0;
		PreAudit preInfo = preAuditMapper.selectIdByProcess(applyId, taskId);
		if (preInfo != null) {// 修改
			preInfo.setEmpId(audit.getEmpId());
			preInfo.setEmpName(audit.getEmpName());
			preInfo.setInnerRemark(audit.getInnerRemark());
			preInfo.setRemark(audit.getRemark());
			preInfo.setTaskId(taskId);
			preInfo.setProcInstId(task.getProcessInstanceId());
			preInfo.setCreditRemark(audit.getCreditRemark());
			preInfo.setFinanceRemark(audit.getFinanceRemark());
			preInfo.setOtherRemark(audit.getOtherRemark());
			preInfo.setHandleOption(audit.getHandleOption());
			preInfo.setHandleOptionval(audit.getHandleOptionval());
			preInfo.setProductId(audit.getProductId());
			preInfo.setProductName(audit.getProductName());
			preInfo.setApplyId(applyId);
			preInfo.setHandleTime(DateUtil.getSqlDate());
			preInfo.setHandleMoney(audit.getHandleMoney());
			result += preAuditMapper.updateByPrimaryKeySelective(preInfo);
		} else {
			audit.setPreId(PrimaryKeyUtil.getPrimaryKey());
			result += preAuditMapper.insertSelective(audit);
		}
		
		if (result > 0) {
			return new Json(true, "保存草稿成功!");
		} else {
			return new Json(true, "保存草稿失败!");
		}
	}

	@Override
	public Json multiUpload(MultipartFile[] files, String applyId, String loginEmp, String loginName, String context) {
		if (files == null || files.length < 1) {
			return new Json(false, "参数有误");
		}
		
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
			annex.setApplyTypeId(AnnexType.ANNEX_AUDIT.getType());
			annex.setApplyTypeVal(AnnexType.ANNEX_AUDIT.getName());

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
