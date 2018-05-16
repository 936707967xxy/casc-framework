/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.CreateSerialNo;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.common.util.HttpClientUtil;
import com.hoomsun.common.util.JumpTaskCmd;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.core.dao.SysContractMapper;
import com.hoomsun.core.dao.SysEmployeeMapper;
import com.hoomsun.core.dao.SystemParamMapper;
import com.hoomsun.core.enums.SystemParamEnum;
import com.hoomsun.core.model.SysContract;
import com.hoomsun.core.model.SystemParam;
import com.hoomsun.csas.audit.dao.BPMNMapper;
import com.hoomsun.csas.audit.dao.ContractCheckMapper;
import com.hoomsun.csas.audit.dao.HxbRecordMapper;
import com.hoomsun.csas.audit.exception.AuditException;
import com.hoomsun.csas.audit.model.ContractCheck;
import com.hoomsun.csas.audit.model.HxbRecord;
import com.hoomsun.csas.audit.model.vo.UserApplyConVO;
import com.hoomsun.csas.audit.server.inter.ContractAuditServerI;
import com.hoomsun.csas.audit.util.ActivitiUtils;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.dao.UserApplyMapper;
import com.hoomsun.message.server.inter.NoticeServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月12日 <br>
 * 描述：合同管理审核
 */
@Service("contractAuditServer")
public class ContractAuditServerImpl implements ContractAuditServerI {
	private static final Logger log = LoggerFactory.getLogger(ContractAuditServerImpl.class);
	@Autowired
	private ContractCheckMapper contractCheckMapper;
	@Autowired
	private TaskService taskService;
	@Autowired
	private UserApplyMapper userApplyMapper;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private SysContractMapper sysContractMapper;
	@Autowired
	private SystemParamMapper systemParamMapper;
	@Autowired
	private HxbRecordMapper hxbRecordMapper;
	@Autowired
	private BPMNMapper bpmnMapper;
	@Autowired
	private NoticeServerI noticeServer;
	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;// 员工表
	@Value("${LEND_PASS}")
	private String lendPass;

	@Override
	public Pager<UserApplyConVO> findPager(Integer page, Integer rows, String custName, String idNumber, String loanId, String empId, String nodeStatus, String node, String deptId) {
		if (StringUtils.isBlank(empId) || StringUtils.isBlank(deptId)) {
			throw new AuditException("无法获取你的登录信息,请尝试重新登录!");
		}

		// List<Group> groups =
		// identityService.createGroupQuery().groupMember(empId).list();
		// boolean isGroup = false;
		// for (Group group : groups) {
		// if ("CONTRACT_AUDIT_GROUP".equalsIgnoreCase(group.getId())) {
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

		Map<String, Object> param = new HashMap<String, Object>();
		if (page == null || rows == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		rows = rows > 100 ? 100 : rows;

		param.put("empId", empId);
		param.put("isMgr", isMgr);
		param.put("deptId", deptId);
		param.put("pageIndex", page);
		param.put("pageSize", rows);

		if (!StringUtils.isBlank(custName)) {
			param.put("custName", "%" + custName + "%");
		}

		if (!StringUtils.isBlank(node)) {
			param.put("node", node);// 默认未审核的信息
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

		List<UserApplyConVO> userApplyVOs = contractCheckMapper.findPager(param);
		Integer total = contractCheckMapper.findPagerCount(param);
		return new Pager<>(userApplyVOs, total);
	}

	@Override
	public Json claimTask(String applyId, String empId) {
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("conAudit").singleResult();
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
	public Json completeTask(String applyId, String empId) {
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("conAudit").taskAssignee(empId).singleResult();
		if (task == null) {
			return new Json(false, "不能处理,任务在其他节点!");
		}

		taskService.complete(task.getId());
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
		return new Json(true, "处理成功!");
	}

	@Override
	public Json rollbackTask(String applyId, String emp) {
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数有误!");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("conAudit").singleResult();
		if (null == task) {
			return new Json(false, "不能撤销！任务已不在当前节点!");
		}

		JumpTaskCmd cmd = new JumpTaskCmd(task, "makeCon", JumpTaskCmd.ROLLBACK);
		TaskServiceImpl taskServiceImpl = (TaskServiceImpl) taskService;
		taskServiceImpl.getCommandExecutor().execute(cmd);
		return new Json(true, "退回成功!");
	}

	@Override
	public Json withdrawTask(String applyId, String emp) {
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数有误!");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("loanAudit").singleResult();
		if (null == task) {
			return new Json(false, "不能撤销！已经放款!");
		}
		String assignee = task.getAssignee();
		if (!StringUtils.isBlank(assignee)) {
			return new Json(false, "任务已被签收，不能撤回！！！");
		}

		JumpTaskCmd cmd = new JumpTaskCmd(task, "conAudit", JumpTaskCmd.WITHDRAW);
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

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("conAudit").singleResult();
		if (task == null) {
			return new Json(false, "不能处理,任务在其他节点!");
		}

		taskService.claim(task.getId(), toEmpId);

		return new Json(true, "任务转办成功!");
	}

	@Override
	public Json saveAudit(ContractCheck audit, String loginEmp) {
		if (audit == null || StringUtils.isBlank(loginEmp)) {
			return new Json(false, "参数异常！");
		}

		String applyId = audit.getApplyId();

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("conAudit").singleResult();
		if (null == task) {
			return new Json(false, "不能处理！任务已不在当前节点!");
		}

		// 首先判断审核数据时候已经存在
		String taskId = task.getId();
		String processId = task.getProcessInstanceId();
		ContractCheck result = contractCheckMapper.checkConAudit(applyId, taskId);
		audit.setCheckDate(DateUtil.getSqlDate());
		if (result != null) {// 修改
			result.setTaskId(taskId);
			result.setProcInstId(processId);
			result.setCheckDate(DateUtil.getSqlDate());
			result.setHandleOpinion(audit.getHandleOpinion());
			result.setHandleOpinionVal(audit.getHandleOpinionVal());
			result.setHandleRemarks(audit.getHandleRemarks());
			contractCheckMapper.updateByPrimaryKey(result);
		} else {
			audit.setCheckId(PrimaryKeyUtil.getPrimaryKey());
			audit.setApplyId(applyId);
			audit.setTaskId(taskId);
			audit.setProcInstId(task.getProcessInstanceId());
			contractCheckMapper.insertSelective(audit);
		}

		Map<String, Object> var = new HashMap<String, Object>();
		SysContract sysContract = sysContractMapper.selectByApplyId(applyId);
		Integer option = audit.getHandleOpinion();
		switch (option) {
		case 0:// 拒贷
			JumpTaskCmd cmd = new JumpTaskCmd(task, "endEvent", JumpTaskCmd.REJECT);
			TaskServiceImpl taskServiceImpl = (TaskServiceImpl) taskService;
			taskServiceImpl.getCommandExecutor().execute(cmd);
			String proName = sysContract.getProdAlias();
			String handleRemarks = audit.getHandleRemarks();
			if (!handleRemarks.equals("")) {
				handleRemarks = audit.getHandleRemarks();
			} else {
				handleRemarks = "拒贷";
			}
			noticeServer.sendMsg(applyId, "您签订的" + proName + "协议未通过审核，原因：" + handleRemarks, 1);
			break;
		case 1:// 客户放弃
			cmd = new JumpTaskCmd(task, "endEvent", JumpTaskCmd.WAIVER);
			taskServiceImpl = (TaskServiceImpl) taskService;
			taskServiceImpl.getCommandExecutor().execute(cmd);
			break;
		case 2:// 退回
			cmd = new JumpTaskCmd(task, "makeCon", JumpTaskCmd.ROLLBACK);
			taskServiceImpl = (TaskServiceImpl) taskService;
			taskServiceImpl.getCommandExecutor().execute(cmd);
			break;
		case 3:// 补充资料
			var.put("checksupp", 1);
			var.put("goto", "conaudit");
			taskService.complete(task.getId(), var);
			String proNamea = sysContract.getProdAlias();
			noticeServer.sendMsg(applyId, "您签订的" + proNamea + "协议资料不全，请及时前往门店补充资料。", 1);
			break;
		case 4:// 通过
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
					var.put("checksupp", "0");
					taskService.complete(task.getId(), var);
					String proNamed = sysContract.getProdAlias();
					noticeServer.sendMsg(applyId, "您签订的" + proNamed + "协议已通过审核，请耐心等待。", 1);
				} else {
					return new Json(false, "红小宝招标失败！！！" + errMsg);
				}
			} else {
				var.put("checksupp", "0");
				taskService.complete(task.getId(), var);
				String proNamed = sysContract.getProdAlias();
				noticeServer.sendMsg(applyId, "您签订的" + proNamed + "协议已通过审核，请耐心等待。", 1);
			}
			break;
		default:
			break;
		}

		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processId).processInstanceBusinessKey(applyId, "creditSaleAuditSystem").singleResult();

		String precessStatus = "";
		String precessStatusVal = "";
		// 只有流程结束pi才是null (拒贷和客户放弃是结束流程)
		if (pi != null) {
			List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(applyId).processInstanceId(processId).list();
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
				precessStatus = JumpTaskCmd.REJECT;
				precessStatusVal = "拒贷";
				break;
			case 1:// 客户放弃
				precessStatus = JumpTaskCmd.WAIVER;
				precessStatusVal = "客户放弃";
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

	// 验证签收
	@Override
	public Json checkClaim(String applyId, String empId) {
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("conAudit").singleResult();
		if (null == task) {
			return new Json(false, "不能处理！任务已不在当前节点!");
		}

		String assignee = task.getAssignee();
		if (StringUtils.isBlank(assignee)) {// 没有签收 签收任务
			return claimTask(applyId, empId);
		} else {
			if (assignee.equals(empId)) {// 相同的人处理
				return new Json(true, "你已经签收成功!");
			} else {
				return new Json(false, "任务已经被他人签收!");
			}
		}
	}

	@Override
	public ContractCheck findByApplyId(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			return null;
		}
		return contractCheckMapper.findByApplyId(applyId);
	}

	// 确认招标接口
	public Map<String, Object> lendPasses(String applyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject object = new JSONObject();
		UserApply apply = userApplyMapper.selectByAply(applyId);
		SysContract contract = sysContractMapper.findByApplyId(applyId);
//		String loanRefId = contract.getApplyId();// 信贷唯一编号
		String loanRefId = apply.getLoanId();// 信贷唯一编号
		String cardNumber = contract.getBankNo();// 银行卡号6222022604008656610
		String bankType = contract.getBank();// 开户行代码(需满足红小宝16家银行)
		String bankAddress = contract.getBackBranchAddr();// 支行详细名称
		String province = contract.getProName();// 开户省份
		String city = contract.getCityName();// 开户城市
		String preMobile = contract.getBankPhoneNo();// 银行卡手机预留号
		// String onlyCreateAccount1 = "0";
		Boolean onlyCreateAccount = false;// true 只开户绑卡，不确认招标 false,既开户绑卡，有确认招标
		// if ("0".equals(onlyCreateAccount1)) {
		// onlyCreateAccount = false;
		// }
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
		
		log.info("确认招标请求数据："+json);
//		String url = "http://123.126.19.2:8040/lend/pass/";//红小宝测试地址
//		String url = "http://lend.hoomxb.com/lend/pass/";//红小宝正式地址
		String url = lendPass;//红小宝正式地址
		String result = HttpClientUtil.doPostJson(url, null, null, json);
		System.out.println(result);
		// 处理还款明细表数据
		JSONObject resultObj = JSONObject.parseObject(result);
		log.info("确认招标返回数据："+resultObj);
		boolean verify = CreateSerialNo.decode(resultObj.getJSONObject("generalInfo"));
		if (!verify) {
			map.put("status", resultObj.getIntValue("status"));
			map.put("errMsg", "验签失败！");
			return map;
		}
		int status = resultObj.getIntValue("status");
		if (status == 1000) {
			HxbRecord record = new HxbRecord();
			record.setApplyId(applyId);
			record.setRecordTime(new Date());
			record.setPushType("2");
			record.setPushTypeVal("确认招标接口");
			record.setRecordId(PrimaryKeyUtil.getPrimaryKey());
			hxbRecordMapper.insertSelective(record);
			
		}
		map.put("status", resultObj.getIntValue("status"));
		map.put("errMsg", resultObj.get("errMsg"));
		return map;
	}
	/*
	 * public Json lendPassUrl(String applyId) { JSONObject object =
	 * lendPass(applyId); String json = object.toJSONString(); String url =
	 * "http://123.126.19.2:8040/lend/pass/"; String result =
	 * HttpClientUtil.doPostJson(url, null, null, json);
	 * System.out.println(result); //处理还款明细表数据 JSONObject resultObj = new
	 * JSONObject(); resultObj = resultObj.parseObject(result); boolean verify =
	 * CreateSerialNo.decode(resultObj.getJSONObject("generalInfo")); if
	 * (!verify) { return new Json(false,"确认招标接口,验签失败！"); } int status =
	 * resultObj.getIntValue("status");
	 * 
	 * if (status != 1000) { return new Json(false,"确认招标失败！" + status); } return
	 * new Json(true,"招标成功！！！"); }
	 */

	@Override
	public List<ContractCheck> findRollBack(String applyId) {
		// TODO Auto-generated method stub
		return contractCheckMapper.findRollBack(applyId);
	}

}
