/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.CreateSerialNo;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.common.util.HttpClientUtil;
import com.hoomsun.common.util.JumpTaskCmd;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.core.dao.HxbRateMapper;
import com.hoomsun.core.dao.HxbReplaymentPlanMapper;
import com.hoomsun.core.dao.SysEmployeeMapper;
import com.hoomsun.core.dao.SystemParamMapper;
import com.hoomsun.core.enums.SystemParamEnum;
import com.hoomsun.core.model.HxbRate;
import com.hoomsun.core.model.HxbReplaymentPlan;
import com.hoomsun.core.model.SysProduct;
import com.hoomsun.core.model.SystemParam;
import com.hoomsun.core.server.inter.SysProductServerI;
import com.hoomsun.csas.audit.dao.BPMNMapper;
import com.hoomsun.csas.audit.dao.ExcessAuditMapper;
import com.hoomsun.csas.audit.dao.FinalAuditMapper;
import com.hoomsun.csas.audit.dao.HxbRecordMapper;
import com.hoomsun.csas.audit.exception.AuditException;
import com.hoomsun.csas.audit.model.ExcessAudit;
import com.hoomsun.csas.audit.model.FinalAudit;
import com.hoomsun.csas.audit.model.HxbRecord;
import com.hoomsun.csas.audit.model.vo.UserApplyVO;
import com.hoomsun.csas.audit.server.inter.ExcessAuditServerI;
import com.hoomsun.csas.audit.util.ActivitiUtils;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.UserContacts;
import com.hoomsun.csas.sales.api.model.vo.NoticeMsg;
import com.hoomsun.csas.sales.dao.UserApplyMapper;
import com.hoomsun.message.server.inter.NoticeServerI;

/**
 * 作者：liusong <br>
 * 创建时间：2017年9月14日 <br>
 * 描述：超额审批
 */
@Service("excessAuditServer")
public class ExcessAuditServerImpl implements ExcessAuditServerI {
	@Autowired
	private UserApplyMapper userApplyMapper;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ExcessAuditMapper excessAuditMapper;
	@Autowired
	private BPMNMapper bpmnMapper;
	@Autowired
	private NoticeServerI noticeServer;
	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;// 员工表
	@Autowired
	private FinalAuditMapper finalAuditMapper;
	@Autowired
	private SystemParamMapper systemParamMapper;
	@Autowired
	private HxbReplaymentPlanMapper hxbReplaymentPlanMapper;
	@Autowired
	private HxbRecordMapper hxbRecordMapper;
	@Autowired
	private HxbRateMapper hxbRateMapper;
	@Autowired
	private SysProductServerI productServer;
	@Value("${LEND_IMPORT}")
	private String lendImport;

	@Override
	public Json createExcessAudit(ExcessAudit excessAudit) {
		if (StringUtils.isBlank(excessAudit.getExcessId())) {
			excessAudit.setExcessId(PrimaryKeyUtil.getPrimaryKey());
		}
		int result = excessAuditMapper.insertSelective(excessAudit);
		if (result > 0) {
			return new Json(true, "终审添加成功!");
		} else {
			return new Json(false, "终审添加失败!");
		}
	}

	@Override
	public Json updateExcessAudit(ExcessAudit excessAudit) {
		int result = excessAuditMapper.updateByPrimaryKey(excessAudit);
		if (result > 0) {
			return new Json(true, "终审修改成功!");
		} else {
			return new Json(false, "终审修改失败!");
		}
	}

	@Override
	public Json deleteExcessAudit(String excessId) {
		int result = excessAuditMapper.deleteByPrimaryKey(excessId);
		if (result > 0) {
			return new Json(true, "终审删除成功!");
		} else {
			return new Json(false, "终审删除失败!");
		}
	}

	@Override
	public ExcessAudit findById(String excessId) {
		return excessAuditMapper.selectByPrimaryKey(excessId);
	}

	@Override
	public ExcessAudit findByApplyId(String applyId) {
		ExcessAudit ExcessAudit = excessAuditMapper.findByApplyId(applyId);
		if (ExcessAudit == null) {
			return null;
		}
		return ExcessAudit;
	}

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
		// if ("EXCESS_AUDIT_GROUP".equalsIgnoreCase(group.getId())) {
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
		List<UserApplyVO> userApplyVOs = excessAuditMapper.findPager(param);
		Integer total = excessAuditMapper.findExcessAuditCount(param);
		return new Pager<>(userApplyVOs, total);
	}

	@Override
	public Json uploadAnnex(String applyId, MultipartFile[] multipartFiles, String empId) {
		return null;
	}

	@Override
	public Json checkClaim(String applyId, String empId) {
		if (StringUtils.isBlank(applyId) || StringUtils.isBlank(empId)) {
			return new Json(false, "员工未登录!");
		}
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("excessAudit").singleResult();
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
	public Json claimTask(String applyId, String empId) {
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("excessAudit").singleResult();
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

	public Json saveAudit(ExcessAudit excessAudit, Task task) {
		if (StringUtils.isBlank(excessAudit.getApplyId())) {
			return new Json(false, "参数有误!");
		}

		if (task == null) {
			return new Json(false, "无权处理!");
		}
		String processId = task.getProcessInstanceId();

		FinalAudit audit = finalAuditMapper.findByApplyId(excessAudit.getApplyId());

		// String productId = ExcessAudit.getProductId();// 申请产品
		// SysProduct pro = productServer.findByTypeIdFinalAudit(productId);
		excessAudit.setProductId(audit.getProductId());
		excessAudit.setProductName(audit.getProductName()); // 名称
		excessAudit.setProductPeriod(audit.getProductPeriod());// 期限
		excessAudit.setProductRate(audit.getProductRate()); // 年利率
		excessAudit.setProductFeeRate(audit.getProductFeeRate());// 月费率
		excessAudit.setProductPay(audit.getProductPay());
		excessAudit.setProductPayVal(audit.getProductPayVal());

		excessAudit.setAuditDate(new Date());
		// 未绑定用户
		excessAudit.setProcInstId(processId);
		excessAudit.setTaskId(task.getId());
		excessAudit.setexcessStatus(0);
		excessAudit.setAuditDate(DateUtil.getSqlDate());

		ExcessAudit excessAuditOld = excessAuditMapper.selectIdByProcess(excessAudit.getApplyId(), task.getId(), task.getProcessInstanceId());

		int result = 0;
		if (null == excessAuditOld) {// 没有数据 添加
			excessAudit.setExcessId(PrimaryKeyUtil.getPrimaryKey());
			result += excessAuditMapper.insertSelective(excessAudit);
		} else {// 修改
			excessAudit.setExcessId(excessAuditOld.getExcessId());
			excessAudit.setTaskId(task.getId());
			excessAudit.setProcInstId(task.getProcessInstanceId());
			result += excessAuditMapper.updateByPrimaryKey(excessAudit);
		}

		if (result > 0) {
			return new Json(true, "保存成功!");
		} else {
			return new Json(false, "保存失败!");
		}
	}

	@Override
	public Json completeTask(String applyId, ExcessAudit excessAudit) {
		String empId = excessAudit.getAuditEmp();
		if (StringUtils.isBlank(applyId) || StringUtils.isBlank(empId)) {
			return new Json(false, "参数有误!");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("excessAudit").taskAssignee(empId).singleResult();

		if (task == null) {
			return new Json(false, "无权处理任务!");
		}
		Integer handleOpinion = excessAudit.getHandleOpinion();

		saveAudit(excessAudit, task);

		switch (handleOpinion) {
		case 0:// 拒贷
			((TaskServiceImpl) taskService).getCommandExecutor().execute(new JumpTaskCmd(task, "endEvent", JumpTaskCmd.REJECT));
			break;
		case 4:// 通过
				// Map<String, Object> var = new HashMap<String, Object>();
				// var.put("finalsupp", "0");
			SystemParam systemParam = systemParamMapper.findParamByKey(SystemParamEnum.PUSH_ONLINE.getKey());
			String paramValue = systemParam.getParamValue();
			if (StringUtils.isBlank(paramValue)) {
				paramValue = "0";
			}

			if (paramValue.equals("1")) {
				Map<String, Object> map = lendImports(applyId);
				Integer status = (Integer) map.get("status");
				String errMsg = (String) map.get("errMsg");
				if (status == 1000) {
					taskService.complete(task.getId());
				} else {
					return new Json(false, "红小宝推标失败！！！" + errMsg);
				}
			} else {
				taskService.complete(task.getId());
			}
			NoticeMsg msg = userApplyMapper.selectProAndAmountByApplyId(applyId);
			noticeServer.sendMsg(applyId, "您提交的" + msg.getProAlias() + "申请已通过信审的最终审核，最终签约产品为" + msg.getAgreeProAlias() + "，最终可申请额度为" + msg.getAgreeMount() + "，请您及时进行线上签约，确认本次申请。", 7);
			break;
		case 2:// 退回
				// 验证审批类型
			Integer auditType = userApplyMapper.selectAuditType(applyId);
			if (auditType != null && auditType == 1) {// 直接到终审的流程 不能退回
				return new Json(false, "秒批类型,不能退回!");
			}
			((TaskServiceImpl) taskService).getCommandExecutor().execute(new JumpTaskCmd(task, "finalAudit", JumpTaskCmd.ROLLBACK));
			break;
		default:
			break;
		}

		String processId = task.getProcessInstanceId();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processId).processInstanceBusinessKey(applyId, "creditSaleAuditSystem").singleResult();

		String precessStatus = "";
		String precessStatusVal = "";
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
			precessStatus = handleOpinion == 0 ? "reject" : "loan";
			precessStatusVal = handleOpinion == 0 ? "拒贷" : "审批结束";
		}

		userApplyMapper.updateProcessInstance(applyId, processId, precessStatus, precessStatusVal);
		return new Json(true, "处理成功!");
	}

	// 退回
	@Override
	public Json rollback(String applyId, String empId) {
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskAssignee(empId).taskDefinitionKey("excessAudit").singleResult();
		if (null == task) {
			return new Json(false, "无权退回!");
		}

		((TaskServiceImpl) taskService).getCommandExecutor().execute(new JumpTaskCmd(task, "finalAudit", JumpTaskCmd.ROLLBACK));
		List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(applyId).processInstanceId(task.getProcessInstanceId()).list();
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
		userApplyMapper.updateProcessInstance(applyId, task.getProcessInstanceId(), precessStatus, precessStatusVal);
		return new Json(true, "退回成功!");
	}

	@Override
	public Json withdraw(String applyId, String empId) {
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("makeCon").singleResult();
		if (null == task) {
			return new Json(false, "无权撤销!");
		}
		String assignee = task.getAssignee();
		if (!StringUtils.isBlank(assignee)) {
			return new Json(false, "任务已被签收，不能撤回！！！");
		}
		((TaskServiceImpl) taskService).getCommandExecutor().execute(new JumpTaskCmd(task, "excessAudit", JumpTaskCmd.WITHDRAW));
		List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(applyId).processInstanceId(task.getProcessInstanceId()).list();
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
		userApplyMapper.updateProcessInstance(applyId, task.getProcessInstanceId(), precessStatus, precessStatusVal);
		return new Json(true, "撤销成功!");
	}

	@Override
	public ExcessAudit selectByExcessAu(String applyId) {
		return excessAuditMapper.selectByExcessAu(applyId);
	}

	// 借款导入接口
	public Map<String, Object> lendImports(String applyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		UserApply userApply = userApplyMapper.findApplyById(applyId);
		FinalAudit finalAudit = finalAuditMapper.findByApplyId(applyId);
		SysProduct pro = productServer.findByTypeIdFinalAudit(finalAudit.getProductId());
		JSONObject object = new JSONObject();
		Map<String, Object> lend = new HashMap<String, Object>();// lend(loan,user)
		Map<String, Object> loan = new HashMap<String, Object>();// loan
		Map<String, Object> user = new HashMap<String, Object>();// user
		// loan(必填参数)
		String loanRefId = userApply.getApplyId();// 借款的唯一编号
		String title = userApply.getLoanUseVal();// 借款标题
		String channelAmount = finalAudit.getApprovedAmount().toString();// 期望借款金额,用户实际到手的借款金额
		Integer months = finalAudit.getProductPeriod();// 借款周期（月）
		String description = "贷款用于" + title + "，以上信息已考察认证，同时，经审核借款人所提供资料真实有效，符合红小宝的借款审核标准。";// 描述
		String contractCode = "HXB_" + loanRefId;// 选填，借款签约时的合同编号，合同编号唯一；若推送则使用渠道传递编号，若不推送则根据规则自动生成
		String productName = finalAudit.getProductName();// 产品名(测试为至信)
															// finalAudit.getProductName()
		String monthlyFee = (pro.getRealMonthRate().multiply(new BigDecimal(100))).toString();// 渠道方产品费率(缺失---------)
		String graduation = userApply.getEduBackgroundVal();// 学历
		String university = userApply.getGraduateInstitutions();// 毕业学校
		String marriageStatus = userApply.getMaritalStatusVal();// 婚姻状态 未婚:0,
																// 已婚:1,离异:2,丧偶:3
		if (marriageStatus.equals("未婚")) {
			marriageStatus = "0";
		} else if (marriageStatus.equals("已婚")) {
			marriageStatus = "1";
		} else if (marriageStatus.equals("离异")) {
			marriageStatus = "2";
		} else if (marriageStatus.equals("丧偶")) {
			marriageStatus = "3";
		} else {
			marriageStatus = "0";
		}
		String hasChild = userApply.getChildNumber();// 是否有子女:1,0
		if (hasChild.equals("0")) {
			hasChild = "0";
		} else {
			hasChild = "1";
		}
		// （0:无房产,1：商业按揭购房，2：持证抵押房，3：公积金按揭购房，4：商业/公积金组合按揭购房，5：无按揭购房(全款购房),）
		String hasHouse = userApply.getAssetInfo().getPropertyType().toString();// 是否有房:1,0
		String hasHouseLoan = "";// 是否有房贷:1,0
		if ("1".equals(hasHouse) || "2".equals(hasHouse) || "3".equals(hasHouse) || "4".equals(hasHouse)) {
			hasHouse = "1";
			hasHouseLoan = "1";
		} else if ("0".equals(hasHouse)) {
			hasHouse = "0";
			hasHouseLoan = "0";
		} else if ("5".equals(hasHouse)) {
			hasHouse = "1";
			hasHouseLoan = "0";
		}
		String hasCar = userApply.getAssetInfo().getCarHava().toString();// 是否有车:1,0
		String hasCarLoan = "";// 是否有车贷1,0
		if ("1".equals(hasCar)) {
			hasCar = "0";
			hasCarLoan = "0";
		} else if ("2".equals(hasCar)) {
			hasCar = "1";
			hasCarLoan = "0";
		} else if ("3".equals(hasCar)) {
			hasCar = "1";
			hasCarLoan = "1";
		}
		String companyIndustry = userApply.getUserOccupationalInfo().getIndustryVal();// 公司所属行业
		String homeTown = userApply.getRresidenceProvName();// 籍贯所在省
		String accountLocation = userApply.getRresidenceCityName();// 籍贯所在城市
		String residenceTel = userApply.getCustMobile();// 居住地电话
		String residence = userApply.getHouseAddressDetail();// 居住地址
		String companyName = userApply.getUserOccupationalInfo().getFullWorkName();// 单位名称
		String jobStatus = userApply.getUserOccupationalInfo().getJobTitle();// 职业状态
		String companyLocation = userApply.getUserOccupationalInfo().getCompanyCityName();// 公司所在城市
		String companyProvince = userApply.getUserOccupationalInfo().getCompanyProvName();// 公司所在省份
		String companyAddress = userApply.getUserOccupationalInfo().getCompanyAddressDetail();// 公司详细地址
		String companyCategory = userApply.getUserOccupationalInfo().getCompanyKindVa();// 公司类别
		String companyPost = userApply.getUserOccupationalInfo().getPositionVal();// 公司职位
		String monthlyIncome = userApply.getUserOccupationalInfo().getSalaryMonthly();// 工作月收入
		List<UserContacts> userContacts = userApply.getUserContacts();
		if (userContacts != null && userContacts.size() > 1) {
			String immediateName = userApply.getUserContacts().get(0).getName();// 直系亲属名称
			String immediateRelationShip = userApply.getUserContacts().get(0).getRelationshipVal();// 直系亲属关系
			String immediateTel = userApply.getUserContacts().get(0).getPhone();// 直系亲属电话
			String otherRelationName = userApply.getUserContacts().get(1).getName();// 其他联系人名称
			String otherRelationShip = userApply.getUserContacts().get(1).getRelationshipVal();// 其他联系人关系
			String otherRelationTel = userApply.getUserContacts().get(1).getPhone();// 其他联系人电话
			loan.put("immediateName", immediateName);
			loan.put("immediateRelationShip", immediateRelationShip);
			loan.put("immediateTel", immediateTel);
			loan.put("otherRelationName", otherRelationName);
			loan.put("otherRelationShip", otherRelationShip);
			loan.put("otherRelationTel", otherRelationTel);
		} else if (userContacts != null && userContacts.size() == 1) {
			String immediateName = userApply.getUserContacts().get(0).getName();// 直系亲属名称
			String immediateRelationShip = userApply.getUserContacts().get(0).getRelationshipVal();// 直系亲属关系
			String immediateTel = userApply.getUserContacts().get(0).getPhone();// 直系亲属电话
			String otherRelationName = "暂无";// 其他联系人名称
			String otherRelationShip = "暂无";// 其他联系人关系
			String otherRelationTel = "暂无";// 其他联系人电话
			loan.put("immediateName", immediateName);
			loan.put("immediateRelationShip", immediateRelationShip);
			loan.put("immediateTel", immediateTel);
			loan.put("otherRelationName", otherRelationName);
			loan.put("otherRelationShip", otherRelationShip);
			loan.put("otherRelationTel", otherRelationTel);
		}
		loan.put("loanRefId", loanRefId);
		loan.put("title", title);
		loan.put("channelAmount", channelAmount);
		loan.put("months", months);
		loan.put("description", description);
		loan.put("contractCode", contractCode);
		loan.put("productName", productName);
		loan.put("monthlyFee", monthlyFee);
		loan.put("graduation", graduation);
		loan.put("university", university);
		loan.put("marriageStatus", marriageStatus);
		loan.put("hasChild", hasChild);
		loan.put("hasHouse", hasHouse);
		loan.put("hasHouseLoan", hasHouseLoan);
		loan.put("hasCar", hasCar);
		loan.put("hasCarLoan", hasCarLoan);
		loan.put("companyIndustry", companyIndustry);
		loan.put("homeTown", homeTown);
		loan.put("accountLocation", accountLocation);
		loan.put("residenceTel", residenceTel);
		loan.put("residence", residence);
		loan.put("companyName", companyName);
		loan.put("jobStatus", jobStatus);
		loan.put("companyLocation", companyLocation);
		loan.put("companyProvince", companyProvince);
		loan.put("companyAddress", companyAddress);
		loan.put("companyCategory", companyCategory);
		loan.put("companyPost", companyPost);
		loan.put("monthlyIncome", monthlyIncome);
		// user(必填参数)
		String userRefId = userApply.getApplyId();// 用户的唯一编号
		String mail = userApply.getEmail();// 邮箱
		String mobile = userApply.getCustMobile();// 手机号码
		String realName = userApply.getCustName();// 身份证真实姓名
		String idCard = userApply.getIdNumber();// 身份证号,只支持新版18位
		String address = userApply.getRresidenceAddressDetail();// 身份证地址(userApply.getIdAddr()-----目前没有数据)
		String gender = userApply.getCustSex();// 1:男,0:女
		if (gender.equals("男")) {
			gender = "1";
		}
		if (gender.equals("女")) {
			gender = "0";
		}
		user.put("userRefId", userRefId);
		user.put("mail", mail);
		user.put("mobile", mobile);
		user.put("realName", realName);
		user.put("idCard", idCard);
		user.put("address", address);
		user.put("gender", gender);
		lend.put("loan", loan);
		lend.put("user", user);
		object.put("lend", lend);
		object.put("generalInfo", CreateSerialNo.sign());

		// 存入记录表信息
		HxbRecord record = new HxbRecord();
		record.setApplyId(applyId);
		BigDecimal channelAmounts = new BigDecimal(channelAmount);
		record.setChannelAmount(channelAmounts);
		record.setCustName(realName);
		record.setRecordTime(new Date());
		record.setProductName(productName);
		record.setProductId(finalAudit.getProductId());
		record.setProductMonth(months);
		record.setPushType("1");
		record.setPushTypeVal("借款导入接口");
		record.setRecordId(PrimaryKeyUtil.getPrimaryKey());
		String json = object.toJSONString();

		// 链接红小宝测试路径
		// String url = "http://123.126.19.2:8040/lend/import/";//测试地址
		// String url = "http://lend.hoomxb.com/lend/import/";//正式地址
		String url = lendImport;// 正式地址
		String result = HttpClientUtil.doPostJson(url, null, null, json);

		JSONObject resultObj = JSONObject.parseObject(result);
		// 验签
		boolean verify = CreateSerialNo.decode(resultObj.getJSONObject("generalInfo"));
		if (!verify) {
			map.put("status", resultObj.getIntValue("status"));
			map.put("errMsg", "红小宝验签失败！");
			return map;
		}
		int status = resultObj.getIntValue("status");
		if (status == 1000) {
			// 合同借款金额
			String contractAmount = resultObj.getJSONObject("lendResult").getString("amount");
			BigDecimal contract = new BigDecimal(contractAmount);
			record.setContractAmount(contract);
			// 存入记录表数据
			hxbRecordMapper.insertSelective(record);
			// 平台服务费（y）
			String hxbServiceFees = resultObj.getJSONObject("lendResult").getString("hxbServiceFee");
			BigDecimal hxbServiceFee = new BigDecimal(hxbServiceFees);
			// 渠道服务费（z1）
			String channelServiceFees = resultObj.getJSONObject("lendResult").getString("channelServiceFee");
			BigDecimal channelServiceFee = new BigDecimal(channelServiceFees);
			// 渠道服务费（z2）
			String creditServiceFees = resultObj.getJSONObject("lendResult").getString("creditServiceFee");
			BigDecimal creditServiceFee = new BigDecimal(creditServiceFees);
			/**
			 * 
			 * 线上服务费存库
			 * 
			 * 
			 */
			HxbRate rate = new HxbRate();
			rate.setApplyId(applyId);
			rate.setChannelServiceFee(channelServiceFee);
			rate.setCreditServiceFee(creditServiceFee);
			rate.setHoomxbId(PrimaryKeyUtil.getPrimaryKey());
			rate.setHxbServiceFee(hxbServiceFee);
			hxbRateMapper.insertSelective(rate);
			// 获取一个json,再获取list,添加还款明细表
			JSONObject lendResult = resultObj.getJSONObject("lendResult");
			JSONArray repaymentPlanList = lendResult.getJSONArray("repaymentPlanList");
			Iterator<Object> jsonIterator = repaymentPlanList.iterator();
			/*
			 * 
			 * 
			 * 存入数据库之前先删除还款明细表，防止出现重复数据
			 * 
			 * 
			 */
			int countByApplyId = hxbReplaymentPlanMapper.countByApplyId(applyId);
			if (countByApplyId > 0) {
				hxbReplaymentPlanMapper.deleteByApplyId(applyId);
			}
			// 处理还款明细表数据
			while (jsonIterator.hasNext()) {
				JSONObject object1 = (JSONObject) jsonIterator.next();
				// 期序
				Integer phaseNumber = object1.getInteger("phaseNumber");
				// 当期应还本息
				double amount = object1.getDouble("amount");
				BigDecimal amountDecimal = BigDecimal.valueOf(amount);
				// 当期应还本金
				double principal = object1.getDouble("principal");
				BigDecimal principalDecimal = BigDecimal.valueOf(principal);
				// 提前还清应收总额:本期利息+剩余本金
				double inRepayTotalAmount = object1.getDouble("inRepayTotalAmount");
				BigDecimal inRepayTotalAmountDecimal = BigDecimal.valueOf(inRepayTotalAmount);
				// 提前还清减免渠道服务费1（z1）给至信
				double inRepayDerateChannelServiceFee = object1.getDouble("inRepayDerateChannelServiceFee");
				BigDecimal channel = BigDecimal.valueOf(inRepayDerateChannelServiceFee);
				// 提前还清减免渠道服务费2（z2）
				double inRepayDerateCreditServiceFee = object1.getDouble("inRepayDerateCreditServiceFee");
				BigDecimal credit = BigDecimal.valueOf(inRepayDerateCreditServiceFee);
				// 提前还清减免红小宝平台服务费（y）
				double inRepayDerateHxbServiceFee = object1.getDouble("inRepayDerateHxbServiceFee");
				BigDecimal hxb = BigDecimal.valueOf(inRepayDerateHxbServiceFee);
				// 当期应还利息
				double interest = object1.getDouble("interest");
				BigDecimal interestDecimal = BigDecimal.valueOf(interest);
				// 当期期初剩余本金
				double initialPrincipal = object1.getDouble("initialPrincipal");
				BigDecimal initialPrincipalDecimal = BigDecimal.valueOf(initialPrincipal);
				// 当期期末剩余本金
				double finalPrincipal = object1.getDouble("finalPrincipal");
				BigDecimal finalPrincipalDecimal = BigDecimal.valueOf(finalPrincipal);

				/**
				 * 存入线上还款明细表
				 */
				HxbReplaymentPlan plan = new HxbReplaymentPlan();
				plan.setPlanId(PrimaryKeyUtil.getPrimaryKey());
				plan.setApplyId(applyId);
				plan.setPhaseNumber(phaseNumber);
				plan.setAmount(amountDecimal);
				plan.setPrincipal(principalDecimal);
				plan.setInRepayTotalAmount(inRepayTotalAmountDecimal);
				plan.setChannelServiceFee(channel);
				plan.setCreditServiceFee(credit);
				plan.setHxbServiceFee(hxb);
				plan.setInterest(interestDecimal);
				plan.setInitialPrincipal(initialPrincipalDecimal);
				plan.setFinalPrincipal(finalPrincipalDecimal);
				hxbReplaymentPlanMapper.insertSelective(plan);
				/**
				 * 存入线下还款明细表(是因为合同展示以线下日期为准，所以线下还款明细表也存一份红小宝还款计划，以便于后期展示数据)
				 */
				/*
				 * SysRepaymentPlan replay = new SysRepaymentPlan();
				 * replay.setPlanId(PrimaryKeyUtil.getPrimaryKey());
				 * replay.setApplyId(applyId); replay.setRepayStatus(0);
				 * replay.setPreretuamtCredit(credit);// 提前还清减免渠道服务费2（z2）
				 * replay.setPreretuamtHxb(hxb);// 提前还清减免红小宝平台服务费
				 * replay.setPreretuamtChannel(channel);// 提前还清减免渠道服务费1（z1）给至信
				 * replay.setBal(initialPrincipalDecimal);// 剩余本金
				 * replay.setShouldInte(interestDecimal);// 应还利息
				 * replay.setShouldCapi(principalDecimal);// 应还本金
				 * replay.setShouldAmt(amountDecimal);// 应还金额=应还利息+应还本金
				 * replay.setShouldTerm(phaseNumber);// 应还期数
				 * replay.setAdvanceShould(inRepayTotalAmountDecimal);// 提前还款应还
				 * replay.setAdvanceMoney(inRepayTotalAmountDecimal);
				 * replaymentPlanMapper.insertSelective(replay);
				 */
			}
		}
		map.put("status", resultObj.getIntValue("status"));
		map.put("errMsg", resultObj.get("errMsg"));
		return map;
	}

	@Override
	public Json saveOrUpdateAudit(ExcessAudit excessAudit) {
		String applyId = excessAudit.getApplyId();
		String empId = excessAudit.getAuditEmp();
		if (StringUtils.isAllBlank(applyId, empId)) {
			return new Json(false, "参数异常!applyId/empId is null!");
		}
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("excessAudit").taskAssignee(empId).singleResult();

		if (task == null) {
			return new Json(false, "无权处理任务!");
		}
		saveAudit(excessAudit, task);
		return new Json(true, "保存成功!");
	}

}
