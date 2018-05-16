/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.settle.server.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hoomsun.after.api.model.AfterLoanBal;
import com.hoomsun.after.dao.AfterLoanBalMapper;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.core.dao.BankinfoMapper;
import com.hoomsun.core.dao.SysContractMapper;
import com.hoomsun.core.dao.SysEmployeeMapper;
import com.hoomsun.core.model.Bankinfo;
import com.hoomsun.core.model.SysContract;
import com.hoomsun.core.model.SysEmployee;
import com.hoomsun.core.model.SysRepaymentPlan;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.vo.NoticeMsg;
import com.hoomsun.csas.sales.dao.UserApplyMapper;
import com.hoomsun.csas.sales.util.JumpTaskCmd;
import com.hoomsun.csas.settle.dao.LoanRecordMapper;
import com.hoomsun.csas.settle.model.LoanRecord;
import com.hoomsun.csas.settle.model.vo.LoanVo;
import com.hoomsun.csas.settle.server.inter.LoanServerI;
import com.hoomsun.message.server.inter.NoticeServerI;

/**
 * 作者：liming <br>
 * 创建时间：2017年12月16日 <br>
 * 描述：放款业务层
 */

@Service("loanServer")
public class LoanServerImpl implements LoanServerI {
	@Autowired
	private LoanRecordMapper recordMapper;
	@Autowired
	private UserApplyMapper userApplyMapper;
	@Autowired
	private TaskService taskService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private SysContractMapper sysContractMapper;
	@Autowired
	private NoticeServerI noticeServer;
	@Autowired
	private AfterLoanBalMapper afterLoanBalMapper;
	@Autowired
	private BankinfoMapper bankinfoMapper;
	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;
	@Value("${HSOADB_NAME}")
	private String hsoaDB;

	public LoanServerImpl() {
	}

	@Override
	public Pager<LoanVo> findAllData(Integer page, Integer rows, String custName, String custMobile, String conCode, String idNumber, String empId, String deptId, String nodeStatus) {
		Map<String, Object> param = new HashMap<String, Object>();
		// boolean isGroup = hasPermission(empId);
		// if (!isGroup) {
		// throw new AuditException("您没有权限操作！不是该审批组人员!");
		// }

		if (page == null || rows == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		rows = rows > 100 ? 50 : rows;

		param.put("empId", empId);
		param.put("pageIndex", page);
		param.put("pageSize", rows);
		if (!StringUtils.isBlank(custName)) {
			param.put("custName", "%" + custName + "%");
		}
		if (!StringUtils.isBlank(custMobile)) {
			param.put("custMobile", "%" + custMobile + "%");
		}
		if (!StringUtils.isBlank(idNumber)) {
			param.put("idNumber", "%" + idNumber + "%");
		}
		// [{'id':'-1','text':'全部'},{'id':'0','text':'待审核'},{'id':'1','text':'已审核'}]
		if (null != nodeStatus) {
			param.put("nodeStatus", nodeStatus);
		} else {
			param.put("nodeStatus", 0);// 默认未审核的信息
		}

		if (!StringUtils.isBlank(conCode)) {
			param.put("conCode", "%" + conCode + "%");
		}
		List<LoanVo> LoanInfoVoList = recordMapper.findPager(param);

		int count = recordMapper.findPagerCount(param);
		Pager<LoanVo> pager = new Pager<LoanVo>(LoanInfoVoList, count);
		return pager;
	}

	// 验证当前人是否有权限 该审批组权限
	public boolean hasPermission(String loginEmp) {
		List<Group> groups = identityService.createGroupQuery().groupMember(loginEmp).list();
		boolean isGroup = false;
		for (Group group : groups) {
			if ("CUSTOMER_SERVICE_GROUP".equalsIgnoreCase(group.getId())) {
				isGroup = true;
			}
		}
		return isGroup;
	}

	@Override
	public Json completeTask(LoanRecord record, SessionRouter session) {
		String applyId = record.getApplyId();
		String empId = session.getEmpId();
		if (StringUtils.isBlank(applyId) || StringUtils.isBlank(empId)) {
			return new Json(false, "参数有误!");
		}
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("loanAudit").taskAssignee(empId).singleResult();
		// Task task =
		// taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("loanAudit").singleResult();
		if (task == null) {
			return new Json(false, "无权处理任务!");
		}
		taskService.complete(task.getId());
		String processId = task.getProcessInstanceId();
		Integer result = recordMapper.checkByApplyId(applyId);
		record.setHandleDate(DateUtil.getSqlDate());
		if (result < 1) {
			record.setLoanId(PrimaryKeyUtil.getPrimaryKey());
			record.setHandleEmp(empId);
			record.setHandleEmpName(session.getEmpName());
			record.setHandleOption(4);
			record.setTaskId(task.getId());
			record.setProcInstId(processId);
			record.setHandleOptionVal("通过");
			record.setPayChannel(3);
			record.setPayChannelVal("线下");
			insertData(applyId, record);
			recordMapper.insertSelective(record);
		} else {
			record.setHandleEmp(empId);
			record.setHandleEmpName(session.getEmpName());
			record.setTaskId(task.getId());
			record.setProcInstId(processId);
			record.setHandleOption(4);
			record.setHandleOptionVal("通过");
			record.setPayChannel(3);
			record.setPayChannelVal("线下");
			insertData(applyId, record);
			recordMapper.updateByPrimaryKeySelective(record);
		}

		// -------插入贷后放款数据-------

		Json jason = pushAfterLoanBal(applyId);
		if (!jason.getSuccess()) {
			return jason;
		}

		// 修改申请主表状态
		List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(applyId).processInstanceId(processId).list();
		if (tasks == null || tasks.size() < 1) {
			userApplyMapper.updateProcessInstance(applyId, processId, "success", "撮配成功");
			// 修改合同表状态conStatus 1
			sysContractMapper.updateConStatus(applyId);
			// 放款成功推送
			// String productName = record.getProductName();// 产品
			String loanAmount = record.getLoanAmount().toString();

			NoticeMsg noticeMsg = userApplyMapper.selectProAndAmountByApplyId(applyId);
			String agreeProAlias = noticeMsg.getAgreeProAlias();// 产品别名
			String message = "您申请的" + agreeProAlias + "已成功放款，放款金额：" + loanAmount + "，您可以在收款银行账户中查询明细。";// 推送消息
			noticeServer.sendMsg(applyId, message, 1);
			return new Json(true, "处理成功!");
		}
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
		// 修改合同表状态conStatus 1
		sysContractMapper.updateConStatus(applyId);

		// 放款成功推送
		NoticeMsg noticeMsg = userApplyMapper.selectProAndAmountByApplyId(applyId);
		String agreeProAlias = noticeMsg.getAgreeProAlias();// 产品别名
		String loanAmount = record.getLoanAmount().toString();
		String message = "您申请的" + agreeProAlias + "已成功放款，放款金额：" + loanAmount + "，您可以在收款银行账户中查询明细。";// 推送消息
		noticeServer.sendMsg(applyId, message, 1);
		return new Json(true, "处理成功!");
	}

	@Override
	public Json rollback(LoanRecord record, SessionRouter session) {
		String applyId = record.getApplyId();
		String empId = session.getEmpId();
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数有误!");
		}
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("loanAudit").singleResult();
		if (null == task) {
			return new Json(false, "不能退回！任务已不在当前节点!");
		}

		String processId = task.getProcessInstanceId();
		Integer result = recordMapper.checkByApplyId(applyId);
		record.setHandleDate(DateUtil.getSqlDate());
		if (result == null || result < 1) {
			record.setLoanId(PrimaryKeyUtil.getPrimaryKey());
			record.setHandleEmp(empId);
			record.setHandleEmpName(session.getEmpName());
			record.setHandleOption(2);
			record.setHandleOptionVal("退回");
			record.setTaskId(task.getId());
			record.setProcInstId(processId);
			record.setPayChannel(3);
			record.setPayChannelVal("线下");
			insertData(applyId, record);
			recordMapper.insertSelective(record);
		} else {
			record.setHandleEmp(empId);
			record.setHandleEmpName(session.getEmpName());
			record.setHandleOption(2);
			record.setHandleOptionVal("退回");
			record.setTaskId(task.getId());
			record.setProcInstId(processId);
			record.setPayChannel(3);
			record.setPayChannelVal("线下");
			insertData(applyId, record);
			recordMapper.updateByPrimaryKeySelective(record);
		}

		JumpTaskCmd cmd = new JumpTaskCmd(task, "conAudit", JumpTaskCmd.ROLLBACK);
		TaskServiceImpl taskServiceImpl = (TaskServiceImpl) taskService;
		taskServiceImpl.getCommandExecutor().execute(cmd);

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
		return new Json(true, "退回成功!");
	}

	public void insertData(String applyId, LoanRecord record) {
		SysContract contract = sysContractMapper.selectByApplyId(applyId);
		// FinalAudit finalAudit = finalAuditMapper.findByApplyId(applyId);
		BigDecimal loanAmount = contract.getLoanAmount();
		BigDecimal conAmount = contract.getConAmount();
		Integer loanTerm = contract.getProductPeriod();
		record.setLoanId(PrimaryKeyUtil.getPrimaryKey());
		record.setLoanAmount(loanAmount);
		record.setConAmount(conAmount);
		record.setLoanTerm(loanTerm);
		record.setHandleDate(DateUtil.getSqlDate());
		record.setProductId(contract.getProductId());
		record.setProductName(contract.getProductName());
		record.setApplyId(applyId);
		record.setConId(contract.getConId());
		record.setLoanCode(contract.getConNo());
	}

	@Override
	public LoanVo selectByApplyId(String applyId) {
		return recordMapper.selectByApplyId(applyId);
	}

	@Override
	public Json claimTask(String applyId, String empId) {
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("loanAudit").singleResult();
		if (task == null) {
			return new Json(false, "不能处理,任务在其他节点!");
		}

		String assignee = task.getAssignee();
		if (!StringUtils.isBlank(assignee)) {
			if (assignee.equals(empId)) {
				return new Json(true, "任务已签收成功!");
			} else {
				return new Json(false, "不能处理,任务已被他人签收!");
			}
		} else {
			taskService.claim(task.getId(), empId);
		}
		return new Json(true, "任务签收成功!");
	}

	@Override
	public Json checkClaim(String applyId, String empId) {
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("loanAudit").singleResult();
		if (null == task) {
			return new Json(false, "不能处理！任务已不在当前节点!");
		}

		String assignee = task.getAssignee();
		if (StringUtils.isBlank(assignee)) {// 没有签收 签收任务
			taskService.claim(task.getId(), empId);
			return new Json(true, "签收成功!");
		} else {
			if (assignee.equals(empId)) {// 相同的人处理
				return new Json(true, "你已经签收成功!");
			} else {
				return new Json(false, "任务已经被他人签收!");
			}
		}
	}

	@Override
	public LoanRecord findByConId(String conId) {
		if (StringUtils.isBlank(conId)) {
			return null;
		}
		return recordMapper.findByConId(conId);
	}

	private Json pushAfterLoanBal(String applyId) {

		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数有误!");
		}

		UserApply userApply = userApplyMapper.selectApplyTableByAppId(applyId);
		if (userApply == null) {
			return new Json(false, "未找到申请信息!");
		}

		AfterLoanBal afterLoanBal = new AfterLoanBal();
		afterLoanBal.setId(PrimaryKeyUtil.getPrimaryKey());
		afterLoanBal.setLoanId(userApply.getApplyId()); // 主键(对应APPLYID)
		afterLoanBal.setCastName(userApply.getCustName()); // 客户姓名
		afterLoanBal.setTel(userApply.getCustMobile()); // 联系电话
		afterLoanBal.setCardNo(userApply.getIdNumber()); // 身份证号
		afterLoanBal.setSex(userApply.getCustSex()); // 性别
		afterLoanBal.setCastSource(userApply.getSourcesValue()); // 客户来源

		SysContract sysContract = sysContractMapper.findByApplyId(applyId);
		if (sysContract == null) {
			return new Json(false, "未找到合同信息!");
		}
		// 根据合同表的银行名称查询银行简写编码如CCB
		Bankinfo bankinfo = bankinfoMapper.selectByBankName(sysContract.getBank());
		if (bankinfo == null) {
			return new Json(false, "未找到银行信息!");
		}
		afterLoanBal.setBank(sysContract.getBank()); // 所属银行（例：招商银行）
		afterLoanBal.setBankPhone(sysContract.getBankPhoneNo());// 银行预留手机号
		afterLoanBal.setBankAccount(sysContract.getBankNo());// 银行账号
		afterLoanBal.setBankCode(bankinfo.getRemark()); // 开户行编码（例：ICBC，ABC）
		afterLoanBal.setPosType("0");// POS类型
		afterLoanBal.setProductId(sysContract.getProductId()); // 产品id
		afterLoanBal.setProductName(sysContract.getProductName());// 产品名称
		afterLoanBal.setContractAccount(sysContract.getConAmount());// 合同金额
		afterLoanBal.setLoanMoney(sysContract.getLoanAmount()); // 放款金额
		afterLoanBal.setLoanDate(DateUtil.getTimestamp()); // 放款日期
		afterLoanBal.setAmt(sysContract.getMonthMoney()); // 月还金额
		afterLoanBal.setStartTime(sysContract.getStartTime()); // 起始还款日期
		afterLoanBal.setEndTime(sysContract.getEndTime()); // 结束还款日期
		afterLoanBal.setLoanPeriod(sysContract.getProductPeriod());// 贷款期数
		afterLoanBal.setSurplusPeriod(sysContract.getProductPeriod());// 剩余期数
		afterLoanBal.setStatementDate(new BigDecimal(sysContract.getPayDate())); // 账单日

		SysRepaymentPlan repaymentPlan = sysContract.getRepaymentPlans().get(0);
		afterLoanBal.setCurrentPeriod(repaymentPlan.getShouldTerm()); // 当前期数（默认：1）
		afterLoanBal.setShouldCapi(repaymentPlan.getShouldCapi()); // 应还本金
		afterLoanBal.setShouldInte(repaymentPlan.getShouldInte()); // 应还利息
		afterLoanBal.setSurplusPrincipal(repaymentPlan.getBal()); // 剩余本金
		afterLoanBal.setReplaymentDate(repaymentPlan.getShouldDate()); // 应还款日期
		afterLoanBal.setAdvanceShould(repaymentPlan.getAdvanceShould());// 提前还款应还总额

		// 根据申请表的客服id查询员工工号
		SysEmployee employee = sysEmployeeMapper.selectByPrimaryKey(sysContract.getCreateEmpId());
		if (employee == null) {
			return new Json(false, "未找到员工表的信息!");
		}
		afterLoanBal.setBigArea("0"); // 所属大区（名称）
		afterLoanBal.setDepartment("0"); // 所属分部（名称）
		afterLoanBal.setSalesDeptment(userApply.getStoreId()); // 销售营业部id
		afterLoanBal.setTeamLeader("0"); // 团队经理姓名（名称）
		afterLoanBal.setCustomerOrLoan(userApply.getSalesEmpName()); // 客户经理姓名（名称）
		afterLoanBal.setBusinessDepartment(null);// 营业部所属部门ID
		afterLoanBal.setReviceCast("0");// 签约客服ID
		afterLoanBal.setManagerCast(sysContract.getCreateEmpName());// 管理客服姓名(合同表提交员工id)
		afterLoanBal.setManagerCastId(employee.getEmpWorkNum());// 管理客服工号
		afterLoanBal.setPublicAccountFour("8888");// 对应公户后四位
		afterLoanBal.setSettleFlag("0");// 结清标识 （0：未结清，1已结清）
		afterLoanBal.setDelayFlag("0");// 逾期标识（0：未逾期，1：已逾期）
		afterLoanBal.setUpdownStatus("0");// 线上线下标识（线下：0，线上：1）
		afterLoanBal.setmSection(0);// M段（默认0）
		afterLoanBal.setCustomerOrLoan("0");// 此单子在客服还是贷后手中（客服：0，贷后：1）
		afterLoanBal.setLoanManagerCastId("");// 贷后客服ID
		afterLoanBal.setLoanManagerCast("");// 贷后客服Name
		afterLoanBal.setBal(new BigDecimal(0));// 账户余额（默认0）
		afterLoanBal.setHangUp("0");// 是否挂起（默认0）
		NoticeMsg noticeMsg = userApplyMapper.selectProAndAmountByApplyId(applyId);
		afterLoanBal.setProductalias(noticeMsg.getAgreeProAlias());// 产品别名
		afterLoanBal.setConNo(sysContract.getConNo());// 合同编号
		afterLoanBal.setSerialId(userApply.getLoanId());// 进件编号
		int i = afterLoanBalMapper.insertSelective(afterLoanBal);

		if (i == 0) {
			return new Json(false, "推送失败!!!");
		} else {
			return new Json(true, "推送成功!!!");
		}

	}

	@Override
	public LoanRecord findApplyIds(String applyId) {
		// TODO Auto-generated method stub
		return recordMapper.findApplyIds(applyId);
	}

	@Override
	public int insert(LoanRecord record) {
		// TODO Auto-generated method stub
		return recordMapper.insert(record);
	}

	@Override
	public int insertSelective(LoanRecord record) {
		// TODO Auto-generated method stub
		return recordMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(LoanRecord record) {
		// TODO Auto-generated method stub
		return recordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(LoanRecord record) {
		// TODO Auto-generated method stub
		return recordMapper.updateByPrimaryKey(record);
	}
}
