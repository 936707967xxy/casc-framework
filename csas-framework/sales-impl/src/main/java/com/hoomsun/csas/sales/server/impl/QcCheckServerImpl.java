/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.server.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.core.dao.SysEmployeeMapper;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.csas.sales.api.model.QcCheck;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.vo.RollBackInfoVo;
import com.hoomsun.csas.sales.api.server.inter.QcCheckServerI;
import com.hoomsun.csas.sales.dao.ActivitiMapper;
import com.hoomsun.csas.sales.dao.QcCheckMapper;
import com.hoomsun.csas.sales.dao.UserApplyMapper;
import com.hoomsun.csas.sales.util.ActivitiUtils;
import com.hoomsun.csas.sales.util.JumpTaskCmd;
import com.hoomsun.message.server.inter.NoticeServerI;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年12月4日 <br>
 * 描述：质检复核server层
 */
@Service("qcCheckServer")
public class QcCheckServerImpl implements QcCheckServerI {

	private QcCheckMapper qcCheckMapper;

	private static final String[] handleVal = new String[] { "拒贷", "客户放弃", "退回", "补充资料", "通过" };

	private Logger log = LoggerFactory.getLogger(QcCheckServerImpl.class);

	@Autowired
	private UserApplyMapper userApplyMapper;// 用户申请表

	@Autowired
	private TaskService taskService;

	private IdentityService identityService;
	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;// 员工表

	@Autowired
	private NoticeServerI noticeServer;

	@Autowired
	private ActivitiMapper bpmnMapper;

	@Value("${HSOADB_NAME}")
	private String hsoaDB;

	@Autowired
	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	@Autowired
	public void setQcCheckMapper(QcCheckMapper qcCheckMapper) {
		this.qcCheckMapper = qcCheckMapper;
	}

	@Override
	public Pager<UserApply> findPagerData(Integer page, Integer rows, String custName, String idNumber, String loanId, String nodeStatus, String salesEmpName, String custMobile, String node, SessionRouter session) {
		String empId = session.getEmpId();
		String storeId = session.getStoreId();
		String deptId = session.getDeptId();
//		if (StringUtils.isBlank(empId) || StringUtils.isBlank(storeId) || StringUtils.isBlank(deptId)) {
//			throw new AuditException("无法获取你的登录信息,请尝试重新登录!");
//		}

//		boolean isGroup = hasPermission(empId);
//		if (!isGroup) {
//			throw new AuditException("您没有权限操作！不是该审批组人员!");
//		}

		Map<String, Object> param = new HashMap<String, Object>();

		// 验证当前登录人是不是部门负责人
		String mgrId = sysEmployeeMapper.findDeptManager(deptId);
		Integer isMgr = 0;//
		if (empId.equals(mgrId)) {// 是部门的负责人
			isMgr = 1;
		}

		param.put("empId", empId);
		param.put("isMgr", isMgr);
		param.put("deptId", deptId);

		if (page != null && rows != null) {
			rows = rows > 100 ? 100 : rows;
		} else {
			page = 1;
			rows = 10;
		}

		if (!StringUtils.isBlank(node)) {
			param.put("node", node);// 默认未审核的信息
		}

		// [{'id':'0','text':'待签收'},{'id':'1','text':'待审核'},{'id':'2','text':'已审核'},{'id':'3','text':'全部'}]
		if (!StringUtils.isBlank(nodeStatus)) {
			param.put("nodeStatus", nodeStatus);
		} else {
			param.put("nodeStatus", 1);// 待审核
		}

		param.put("pageIndex", page);
		param.put("pageSize", rows);
		if (!StringUtils.isBlank(custName)) {
			param.put("custName", "%" + custName + "%");
		}
		if (!StringUtils.isBlank(idNumber)) {
			param.put("idNumber", idNumber);
		}
		if (!StringUtils.isBlank(loanId)) {
			param.put("loanId", loanId);
		}
		if (!StringUtils.isBlank(salesEmpName)) {
			param.put("salesEmpName", salesEmpName);
		}
		if (!StringUtils.isBlank(custMobile)) {
			param.put("custMobile", custMobile);
		}
		if (!StringUtils.isBlank(storeId)) {
			param.put("storeId", storeId);
		}

		List<UserApply> qcCheckList = qcCheckMapper.findPager(param);
		int count = qcCheckMapper.findPagerCount(param);
		return new Pager<UserApply>(qcCheckList, count);
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
	public UserApply findById(String applyId) {
		return qcCheckMapper.findApplyById(applyId);
	}

	@Override
	public Json checkClaim(String applyId, String empId) {
		if (StringUtils.isBlank(applyId) || StringUtils.isBlank(empId)) {
			return new Json(false, "员工未登录!");
		}
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("qcCheck").singleResult();
		if (null == task) {
			return new Json(false, "不能处理！任务已不在当前节点!");
		}

		String assignee = task.getAssignee();
		if (StringUtils.isBlank(assignee)) {// 没有签收 签收任务
			if (ActivitiUtils.getClaimTaskSign(bpmnMapper, empId)) {
				taskService.claim(task.getId(), empId);
				return new Json(true, "不能签收，请先处理完手中的任务!");
			}else {
				return new Json(false, "你已经签收成功!");
			}
		} else {
			if (assignee.equals(empId)) {// 相同的人处理
				return new Json(true, "你已经签收成功!");
			} else {
				return new Json(false, "任务已经被他人签收!");
			}
		}
	}

	// 签收
	@Override
	public Json claimTask(String applyId, String empId) {
		if (StringUtils.isBlank(applyId) || StringUtils.isBlank(empId)) {
			return new Json(false, "参数有误!");
		}
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("qcCheck").singleResult();
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

	// 通过
	@Override
	public Json completeTask(String applyId, String empId, String empName, String remarks) {
		if (StringUtils.isBlank(applyId) || StringUtils.isBlank(empId)) {
			return new Json(false, "参数有误!");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("qcCheck").taskAssignee(empId).singleResult();
		if (task == null) {
			return new Json(false, "无权处理任务!");
		}
		// TODO 跳转补充资料
		Map<String, Object> var = new HashMap<String, Object>();
		var.put("qctoadd", "0");
		// var.put("goto", "qccheck");
		taskService.complete(task.getId(), var);
		String processId = task.getProcessInstanceId();
		updateApplyTable(applyId, processId);

		// 添加质检复核信息到业务数据库
		Json creatQcCheck = creatQcCheck(applyId, task.getId(), processId, remarks, 4, empId, empName); // TODO

		// 获取产品名称
		String proName = userApplyMapper.selectProNameByApplyId(applyId);
		noticeServer.sendMsg(applyId, "您提交的" + proName + "申请已通过质检审核，请等待信审审核", 1);

		// 这里为做返回值判断
		if (!creatQcCheck.getSuccess()) {
			log.error(creatQcCheck.getMsg());
		}
		return new Json(true, "处理成功!");

	}

	// 撤销
	@Override
	public Json withdraw(String applyId, String empId) {
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数有误!");
		}
		// TODO 撤销补充资料
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("preAudit").singleResult();
		if (null == task) {
			return new Json(false, "不能撤销！任务已不在初审节点!");
		}
		String assignee = task.getAssignee();
		if (!StringUtils.isBlank(assignee)) {
			return new Json(false, "任务已被签收，不能撤回！！！");
		}
		String processId = task.getProcessInstanceId();

		JumpTaskCmd cmd = new JumpTaskCmd(task, "qcCheck", JumpTaskCmd.WITHDRAW);
		TaskServiceImpl taskServiceImpl = (TaskServiceImpl) taskService;
		taskServiceImpl.getCommandExecutor().execute(cmd);

		// qcCheckMapper.deleteWhenWithDrow(applyId, processId); //
		// 当选择撤回的时候删掉旧的数据

		updateApplyTable(applyId, processId);
		return new Json(true, "撤销成功!");
	}

	// 退回
	@Override
	public Json rollback(String applyId, String empId, String empName, String remarks) {
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数有误!");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("qcCheck").singleResult();
		if (null == task) {
			return new Json(false, "不能退回！任务已不在质检复核节点!");
		}
		String processId = task.getProcessInstanceId();

		Map<String, Object> var = new HashMap<String, Object>();
		var.put("qctoadd", 1);
		var.put("goto", "qccheck");
		taskService.complete(task.getId(), var);
		// 消息推送
		String proName = userApplyMapper.selectProNameByApplyId(applyId);
		noticeServer.sendMsg(applyId, "您提交的" + proName + "申请资料不全，请及时前往门店补充资料", 1); // 这里补充资料
																					// 门店？？
																					// TODO

		updateApplyTable(applyId, processId);
		// 添加质检复核信息到业务数据库
		Json creatQcCheck = creatQcCheck(applyId, task.getId(), processId, remarks, 2, empId, empName); // TODO
																										// 这里为做返回值判断
		if (!creatQcCheck.getSuccess()) {
			log.error(creatQcCheck.getMsg());
		}
		return new Json(true, "退回成功!");
	}

	// 拒贷
	@Override
	public Json reject(String applyId, String empId, String empName, String remarks) {
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数有误!");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("qcCheck").singleResult();
		if (null == task) {
			return new Json(false, "不能拒贷！任务已不在质检复核节点!");
		}
		String processId = task.getProcessInstanceId();

		JumpTaskCmd cmd = new JumpTaskCmd(task, "endEvent", JumpTaskCmd.REJECT);
		TaskServiceImpl taskServiceImpl = (TaskServiceImpl) taskService;
		taskServiceImpl.getCommandExecutor().execute(cmd);
		// 消息推送
		String proName = userApplyMapper.selectProNameByApplyId(applyId);
		noticeServer.sendMsg(applyId, "您提交的" + proName + "申请未通过质检复核，原因：" + remarks, 1);
		// 修改申请表状态
		userApplyMapper.updateProcessInstance(applyId, processId, JumpTaskCmd.REJECT, "拒贷");

		// 添加质检复核信息到业务数据库
		Json creatQcCheck = creatQcCheck(applyId, task.getId(), processId, remarks, 0, empId, empName); // TODO
																										// 这里为做返回值判断
		if (!creatQcCheck.getSuccess()) {
			log.error(creatQcCheck.getMsg());
		}
		return new Json(true, "拒贷成功!");
	}

	// 客户放弃
	@Override
	public Json waiver(String applyId, String empId, String empName, String remarks) {
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数有误!");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("qcCheck").singleResult();
		if (null == task) {
			return new Json(false, "不能执行客户放弃！任务已不在质检复核节点!");
		}
		String processId = task.getProcessInstanceId();

		JumpTaskCmd cmd = new JumpTaskCmd(task, "endEvent", JumpTaskCmd.WAIVER);
		TaskServiceImpl taskServiceImpl = (TaskServiceImpl) taskService;
		taskServiceImpl.getCommandExecutor().execute(cmd);

		userApplyMapper.updateProcessInstance(applyId, processId, JumpTaskCmd.WAIVER, "客户放弃");
		// 添加质检复核信息到业务数据库
		Json creatQcCheck = creatQcCheck(applyId, task.getId(), processId, remarks, 1, empId, empName); // TODO
																										// 这里为做返回值判断
		if (!creatQcCheck.getSuccess()) {
			log.error(creatQcCheck.getMsg());
		}
		return new Json(true, "客户放弃成功!");
	}

	// 质检复核表数据插入
	private Json creatQcCheck(String applyId, String taskId, String processId, String remarks, int handleIndex, String empId, String empName) {
		QcCheck qc = new QcCheck();
		qc.setApplyId(applyId);
		qc.setQcId(PrimaryKeyUtil.getPrimaryKey());
		qc.setQcDate(new Date());
		qc.setQcEmp(empId);
		qc.setQcEmpName(empName);
		qc.setProcInstId(processId);
		qc.setTaskId(taskId);
		qc.setHandleOpinion((short) handleIndex);
		qc.setHandleOpinionVal(handleVal[handleIndex]);
		qc.setRemarks(remarks);
		int count = qcCheckMapper.insertSelective(qc);
		if (count > 0) {
			return new Json(true, "添加质检复核意见成功!");
		} else {
			return new Json(false, "添加质检复核意见失败！");
		}
	}

	// 申请表更新
	private void updateApplyTable(String applyId, String processId) {
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
	}

	// 以后用于复合代码
	public Json otherHandleFunc(String applyId, String empId, String empName, String remarks, String msg, String jumpTaskCmd) {
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数有误!");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("qcCheck").singleResult();
		if (null == task) {
			return new Json(false, "不能" + msg + "！任务已不在质检复核节点!");
		}
		String processId = task.getProcessInstanceId();

		JumpTaskCmd cmd = new JumpTaskCmd(task, "qcCheck", jumpTaskCmd);
		TaskServiceImpl taskServiceImpl = (TaskServiceImpl) taskService;
		taskServiceImpl.getCommandExecutor().execute(cmd);
		// 添加质检复核信息到业务数据库
		Json creatQcCheck = creatQcCheck(applyId, task.getId(), processId, remarks, 1, empId, empName); // TODO
																										// 这里为做返回值判断
		if (!creatQcCheck.getSuccess()) {
			log.error(creatQcCheck.getMsg());
		}
		return new Json(true, msg + "成功!");
	}

	@Override
	public QcCheck findByApplyId(String applyId) {
		return qcCheckMapper.findByApplyId(applyId);
	}

	// 获取初审退回的list
	@Override
	public List<RollBackInfoVo> findPreRollBackHis(String applyId) {
		return qcCheckMapper.selectRollBackInfo(applyId);
	}

}
