package com.hoomsun.after.server.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.after.api.model.param.ApplyExamineParam;
import com.hoomsun.after.api.model.param.SubApplyParam;
import com.hoomsun.after.api.model.param.SubParams;
import com.hoomsun.after.api.model.table.ApplyDetail;
import com.hoomsun.after.api.model.table.ApplyFo;
import com.hoomsun.after.api.model.table.Loanbal;
import com.hoomsun.after.api.model.table.OverdueRecord;
import com.hoomsun.after.api.model.table.RepaymentPlan;
import com.hoomsun.after.api.model.table.Sub;
import com.hoomsun.after.api.server.SubApplyServer;
import com.hoomsun.after.api.server.UpdateOverdueMoneyServer;
import com.hoomsun.after.api.util.IDGenerator;
import com.hoomsun.after.dao.ApplyDetailMapper;
import com.hoomsun.after.dao.ApplyFoMapper;
import com.hoomsun.after.dao.LoanbalMapper;
import com.hoomsun.after.dao.OverdueRecordMapper;
import com.hoomsun.after.dao.RepaymentPlanMapper;
import com.hoomsun.after.dao.SubMapper;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.core.dao.SysEmployeeMapper;
import com.hoomsun.core.model.SysEmployee;

@Service("subApplyServer")
public class SubApplyServerImpl implements SubApplyServer {

	@Autowired
	private OverdueRecordMapper overdueRecordMapper;

	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;

	@Autowired
	private ApplyFoMapper applyFoMapper;

	@Autowired
	private LoanbalMapper loanbalMapper;
	@Autowired
	private RepaymentPlanMapper repaymentPlanMapper;
	@Autowired
	private SubMapper subMapper;
	@Autowired
	private ApplyDetailMapper applyDetailMapper;
	@Autowired
	private UpdateOverdueMoneyServer updateOverdueMoneyServer;

	@Override
	public OverdueRecord getMinOverdue(String loanId) {

		return overdueRecordMapper.selectMinByLoanId(loanId);
	}

	@Override
	public List<OverdueRecord> getAllOverdue(String loanId) {

		return overdueRecordMapper.selectByLoanId(loanId);
	}

	@Override
	public Map<String, Object> getSingelReductionApplyMoney(String loanId, Date subDate, BigDecimal custNenghuan) {

		OverdueRecord overdueRecord = overdueRecordMapper.selectMinByLoanId(loanId);
		Loanbal loanbal = loanbalMapper.selectByLoanId(loanId);

		Integer currentPeriod = overdueRecord.getOverdueNum();

		// 线上线下标识0线下1线上
		String UpdownStatus = loanbal.getUpdownStatus();
		// 合同金额
		BigDecimal conMoney = loanbal.getConMoney();
		// 贷款期次
		Integer loanPeriod = loanbal.getLoanPeriod();

		/**
		 * 查询还款明细表
		 */
		Map<String, Object> repaymentPlanparams = new HashMap<String, Object>();
		repaymentPlanparams.put("loanId", loanId);
		repaymentPlanparams.put("shouldTerm", currentPeriod);
		RepaymentPlan repaymentPlan = repaymentPlanMapper.selByLoanIdTerm(repaymentPlanparams);

		// 应还本金
		BigDecimal shouldCapi = repaymentPlan.getShouldCapi();
		// 应还利息
		BigDecimal shouldInte = repaymentPlan.getShouldInte();
		// 月还款额
		BigDecimal shouldAmt = repaymentPlan.getShouldAmt();
		// 应收违约金
		BigDecimal receivePenalty = shouldAmt.multiply(new BigDecimal(0.1));
		// 应收罚息
		BigDecimal receiveInterest = null;
		// 应收违罚金
		BigDecimal receivePenaltyInterest = null;
		// 应收总额
		BigDecimal receiveMoney = null;
		// 计算逾期天数
		Integer Overdays = getDategap(repaymentPlan.getShouldDate(), subDate);

		if ("0".equals(UpdownStatus)) {
			// 线下：合同金额*0.05%*逾期天数
			receiveInterest = conMoney.multiply(new BigDecimal(0.0005)).multiply(new BigDecimal(Overdays));
			receivePenaltyInterest = receivePenalty.add(receiveInterest);
			receiveMoney = shouldAmt.add(receivePenaltyInterest);

		} else if ("1".equals(UpdownStatus)) {
			// 线上：月还*（剩余期次+1)*0.05%*逾期天数
			Integer shengyvqicijiayi = loanPeriod - currentPeriod + 1;
			receiveInterest = shouldAmt.multiply(new BigDecimal(shengyvqicijiayi)).multiply(new BigDecimal(0.0005)).multiply(new BigDecimal(Overdays));
			receivePenaltyInterest = receivePenalty.add(receiveInterest);
			receiveMoney = shouldAmt.add(receivePenaltyInterest);
		}

		// 逾期天数
		overdueRecord.setOverdueDays(Overdays);
		// 应收违约金
		overdueRecord.setReceivePenalty(receivePenalty);
		// 应收罚息
		overdueRecord.setReceiveInterest(receiveInterest);
		// 应收违罚金（应收违约金+应收罚息）
		overdueRecord.setReceivePenaltyInterest(receivePenaltyInterest);
		// 应还本金
		overdueRecord.setReceiveCorpus(shouldCapi);
		// 应还利息
		overdueRecord.setReceiveShouldinte(shouldInte);
		// 月还款额
		overdueRecord.setAmt(shouldAmt);
		// 应收总额（违罚金+月还款额）
		overdueRecord.setReceiveMoney(receiveMoney);
		// 逾期期数
		overdueRecord.setOverdueNum(currentPeriod);

		// 总减免金额= 应收总额-客户能还
		BigDecimal reduction = receiveMoney.subtract(custNenghuan);

		if (custNenghuan.compareTo(shouldAmt) < 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", false);
			map.put("msg", "客户实还金额过小，减免金额过大，不得发起减免申请！");

			return map;

		}

		// 减免罚息
		BigDecimal subInterest = new BigDecimal(0);
		// 减免违约金
		BigDecimal subPenalty = new BigDecimal(0);

		if (reduction.compareTo(receiveInterest) < 0) {
			subInterest = reduction;

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", true);
			map.put("msg", "");
			map.put("overdays", Overdays);// 逾期天数
			map.put("currentPeriod", currentPeriod);// 逾期期次
			map.put("receivePenalty", receivePenalty.setScale(2, BigDecimal.ROUND_HALF_UP));// 应收违约金
			map.put("receiveInterest", receiveInterest.setScale(2, BigDecimal.ROUND_HALF_UP));// 应收罚息
			map.put("receivePenaltyInterest", receivePenaltyInterest.setScale(2, BigDecimal.ROUND_HALF_UP));// 应收违法金
			map.put("shouldCapi", shouldCapi.setScale(2, BigDecimal.ROUND_HALF_UP));// 应收本金
			map.put("shouldInte", shouldInte.setScale(2, BigDecimal.ROUND_HALF_UP));// 应收利息
			map.put("shouldAmt", shouldAmt.setScale(2, BigDecimal.ROUND_HALF_UP));// 应收月还
			map.put("receiveMoney", receiveMoney.setScale(2, BigDecimal.ROUND_HALF_UP));// 应收总额

			map.put("subPenalty", subInterest.setScale(2, BigDecimal.ROUND_HALF_UP));// 减免罚息
			map.put("subInterest", subPenalty.setScale(2, BigDecimal.ROUND_HALF_UP));// 减免违约金
			map.put("reduction", reduction.setScale(2, BigDecimal.ROUND_HALF_UP));// 减免总额
			map.put("shInterest", receivePenalty.subtract(subPenalty).setScale(2, BigDecimal.ROUND_HALF_UP)); // 实还违约金
			map.put("shPenalty", receiveInterest.subtract(subInterest).setScale(2, BigDecimal.ROUND_HALF_UP)); // 实还罚息
			map.put("shshouldAmt", shouldAmt.setScale(2, BigDecimal.ROUND_HALF_UP)); // 实还月还
			map.put("shreduction", receiveMoney.subtract(reduction).setScale(2, BigDecimal.ROUND_HALF_UP)); // 实还总额

			return map;

		}

		if (reduction.compareTo(receiveInterest) > 0) {

			subInterest = receiveInterest;
			subPenalty = reduction.subtract(receiveInterest);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", true);
			map.put("msg", "");
			map.put("overdays", Overdays);// 逾期天数
			map.put("currentPeriod", currentPeriod);// 逾期期次
			map.put("receivePenalty", receivePenalty.setScale(2, BigDecimal.ROUND_HALF_UP));// 应收违约金
			map.put("receiveInterest", receiveInterest.setScale(2, BigDecimal.ROUND_HALF_UP));// 应收罚息
			map.put("receivePenaltyInterest", receivePenaltyInterest.setScale(2, BigDecimal.ROUND_HALF_UP));// 应收违法金
			map.put("shouldCapi", shouldCapi.setScale(2, BigDecimal.ROUND_HALF_UP));// 应收本金
			map.put("shouldInte", shouldInte.setScale(2, BigDecimal.ROUND_HALF_UP));// 应收利息
			map.put("shouldAmt", shouldAmt.setScale(2, BigDecimal.ROUND_HALF_UP));// 应收月还
			map.put("receiveMoney", receiveMoney.setScale(2, BigDecimal.ROUND_HALF_UP));// 应收总额

			map.put("subPenalty", subInterest.setScale(2, BigDecimal.ROUND_HALF_UP));// 减免罚息
			map.put("subInterest", subPenalty.setScale(2, BigDecimal.ROUND_HALF_UP));// 减免违约金
			map.put("reduction", reduction.setScale(2, BigDecimal.ROUND_HALF_UP));// 减免总额
			map.put("shInterest", receivePenalty.subtract(subPenalty).setScale(2, BigDecimal.ROUND_HALF_UP)); // 实还违约金
			map.put("shPenalty", receiveInterest.subtract(subInterest).setScale(2, BigDecimal.ROUND_HALF_UP)); // 实还罚息
			map.put("shshouldAmt", shouldAmt.setScale(2, BigDecimal.ROUND_HALF_UP)); // 实还月还
			map.put("shreduction", receiveMoney.subtract(reduction).setScale(2, BigDecimal.ROUND_HALF_UP)); // 实还总额

			return map;

		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("msg", "请核实减免申请信息，重新发起申请！");

		return map;

	}

	@Override
	public Json getAllReductionApplyMoney(String loanId, Date subDate, BigDecimal repaymentMoney) {

		// 查询loanbal表
		Loanbal loanbal = loanbalMapper.selectByLoanId(loanId);
		// 查询客户的所有逾期数据
		List<OverdueRecord> overdueRecords = overdueRecordMapper.selectByLoanId(loanId);

		// 应收总额
		BigDecimal sum_receiveMoney = new BigDecimal(0);
		// 应收违约金总额
		BigDecimal sum_receivePenalty = new BigDecimal(0);
		// 应收罚息总额
		BigDecimal sum_receiveInterest = new BigDecimal(0);
		// 总应收违罚金
		BigDecimal sum_receivePenaltyInterest = new BigDecimal(0);

		for (OverdueRecord overdueRecord : overdueRecords) {

			Integer currentPeriod = overdueRecord.getOverdueNum();

			// 线上线下标识0线下1线上
			String UpdownStatus = loanbal.getUpdownStatus();
			// 合同金额
			BigDecimal conMoney = loanbal.getConMoney();
			// 贷款期次
			Integer loanPeriod = loanbal.getLoanPeriod();

			/**
			 * 查询还款明细表
			 */
			Map<String, Object> repaymentPlanparams = new HashMap<String, Object>();
			repaymentPlanparams.put("loanId", loanId);
			repaymentPlanparams.put("shouldTerm", currentPeriod);
			RepaymentPlan repaymentPlan = repaymentPlanMapper.selByLoanIdTerm(repaymentPlanparams);

			// 应还本金
			BigDecimal shouldCapi = repaymentPlan.getShouldCapi();
			// 应还利息
			BigDecimal shouldInte = repaymentPlan.getShouldInte();
			// 月还款额
			BigDecimal shouldAmt = repaymentPlan.getShouldAmt();

			// 应收违约金
			BigDecimal receivePenalty = shouldAmt.multiply(new BigDecimal(0.1));
			// 应收罚息
			BigDecimal receiveInterest = null;
			// 应收违罚金
			BigDecimal receivePenaltyInterest = null;
			// 应收总额
			BigDecimal receiveMoney = null;
			// 计算逾期天数
			Integer Overdays = getDategap(repaymentPlan.getShouldDate(), subDate);

			if ("0".equals(UpdownStatus)) {
				// 线下：合同金额*0.05%*逾期天数
				receiveInterest = conMoney.multiply(new BigDecimal(0.0005)).multiply(new BigDecimal(Overdays));
				receivePenaltyInterest = receivePenalty.add(receiveInterest);
				receiveMoney = shouldAmt.add(receivePenaltyInterest);

			} else if ("1".equals(UpdownStatus)) {
				// 线上：月还*（剩余期次+1)*0.05%*逾期天数
				Integer shengyvqicijiayi = loanPeriod - currentPeriod + 1;
				receiveInterest = shouldAmt.multiply(new BigDecimal(shengyvqicijiayi)).multiply(new BigDecimal(0.0005)).multiply(new BigDecimal(Overdays));
				receivePenaltyInterest = receivePenalty.add(receiveInterest);
				receiveMoney = shouldAmt.add(receivePenaltyInterest);
			}

			// 逾期天数
			overdueRecord.setOverdueDays(Overdays);

			// 应收违约金
			overdueRecord.setReceivePenalty(receivePenalty);
			sum_receivePenalty = sum_receivePenalty.add(receivePenalty);

			// 应收罚息
			overdueRecord.setReceiveInterest(receiveInterest);
			sum_receiveInterest = sum_receiveInterest.add(receiveInterest);

			// 应收违罚金（应收违约金+应收罚息）
			overdueRecord.setReceivePenaltyInterest(receivePenaltyInterest);
			sum_receivePenaltyInterest = sum_receivePenaltyInterest.add(receivePenaltyInterest);

			// 应还本金
			overdueRecord.setReceiveCorpus(shouldCapi);
			// 应还利息
			overdueRecord.setReceiveShouldinte(shouldInte);
			// 月还款额
			overdueRecord.setAmt(shouldAmt);
			// 应收总额（违罚金+月还款额）
			overdueRecord.setReceiveMoney(receiveMoney);

			sum_receiveMoney = sum_receiveMoney.add(receiveMoney);

			// 逾期期数
			overdueRecord.setOverdueNum(currentPeriod);

		}

		// 减免总额
		BigDecimal zongjianmian = sum_receiveMoney.subtract(repaymentMoney);

		if (repaymentMoney.compareTo(sum_receiveMoney.subtract(sum_receivePenaltyInterest)) < 0) {
			return new Json(false, "客户实还金额过小，减免金额过大，不得发起减免申请！", null);
		}

		/**
		 * 计算每期应当减免金额
		 */
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

		// 减免违约金总和
		BigDecimal sumsubreceivePenalty = new BigDecimal(0);
		// 减免罚息总和
		BigDecimal sumsubreceiveInterest = new BigDecimal(0);

		for (OverdueRecord overdueRecord : overdueRecords) {
			Map<String, Object> map = new HashMap<String, Object>();

			BigDecimal receiveMoney2 = overdueRecord.getReceiveMoney();// 应收总额

			map.put("receivePenalty", overdueRecord.getReceivePenalty().setScale(2, BigDecimal.ROUND_HALF_UP));// 应收违约金
			map.put("receiveInterest", overdueRecord.getReceiveInterest().setScale(2, BigDecimal.ROUND_HALF_UP));// 应收罚息
			map.put("receivePenaltyInterest", overdueRecord.getReceivePenaltyInterest().setScale(2, BigDecimal.ROUND_HALF_UP));// 应收违罚金
			map.put("shouldCapi", overdueRecord.getReceiveCorpus().setScale(2, BigDecimal.ROUND_HALF_UP));// 应收本金
			map.put("shouldInte", overdueRecord.getReceiveShouldinte().setScale(2, BigDecimal.ROUND_HALF_UP));// 应收利息
			map.put("shouldAmt", overdueRecord.getAmt().setScale(2, BigDecimal.ROUND_HALF_UP));// 应收AMT
			map.put("receiveMoney", overdueRecord.getReceiveMoney().setScale(2, BigDecimal.ROUND_HALF_UP));// 应收总额
			map.put("currentPeriod", overdueRecord.getOverdueNum());// 逾期期次
			map.put("overdays", overdueRecord.getOverdueDays());// 逾期天数

			// 罚息总额够
			BigDecimal subreceiveInterest = new BigDecimal(0);
			BigDecimal subreceivePenalty = new BigDecimal(0);
			if (sum_receiveInterest.compareTo(zongjianmian) >= 0) {
				subreceiveInterest = (overdueRecord.getReceiveInterest()).multiply(zongjianmian).divide(sum_receiveInterest, 2);
				subreceivePenalty = new BigDecimal(0);
			}
			// 罚息总额不够
			if (sum_receiveInterest.compareTo(zongjianmian) < 0) {
				subreceiveInterest = overdueRecord.getReceiveInterest();

				subreceivePenalty = (overdueRecord.getReceivePenalty()).multiply(zongjianmian.subtract(sum_receiveInterest)).divide(sum_receivePenalty, 2);
			}

			sumsubreceivePenalty = sumsubreceivePenalty.add(subreceivePenalty);
			sumsubreceiveInterest = sumsubreceiveInterest.add(subreceiveInterest);

			map.put("subInterest", subreceivePenalty.setScale(2, BigDecimal.ROUND_HALF_UP));// 减免违约金
			map.put("subPenalty", subreceiveInterest.setScale(2, BigDecimal.ROUND_HALF_UP));// 减免罚息
			map.put("reduction", subreceivePenalty.add(subreceiveInterest).setScale(2, BigDecimal.ROUND_HALF_UP));// 减免总额

			map.put("shInterest", overdueRecord.getReceivePenalty().subtract(subreceivePenalty).setScale(2, BigDecimal.ROUND_HALF_UP));// 实还违约金
			map.put("shPenalty", overdueRecord.getReceiveInterest().subtract(subreceiveInterest).setScale(2, BigDecimal.ROUND_HALF_UP));// 实还罚息

			map.put("shshouldAmt", overdueRecord.getAmt().setScale(2, BigDecimal.ROUND_HALF_UP));// 实还月还

			map.put("SSmoney", (overdueRecord.getReceivePenalty().subtract(subreceivePenalty)).add(overdueRecord.getReceiveInterest().subtract(subreceiveInterest)).setScale(2, BigDecimal.ROUND_HALF_UP));// 实还总违罚金
			System.out.println(receiveMoney2 + "-" + subreceivePenalty + "-" + subreceiveInterest + "=" + receiveMoney2.subtract(subreceivePenalty.add(subreceiveInterest)));
			map.put("shreduction", receiveMoney2.subtract(subreceivePenalty.add(subreceiveInterest)).setScale(2, BigDecimal.ROUND_HALF_UP));// 实还总额

			results.add(map);

		}

		Collections.sort(results, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				if (Integer.parseInt(o1.get("currentPeriod") + "") > Integer.parseInt(o2.get("currentPeriod") + "")) {

					return 1;
				}
				if (Integer.parseInt(o1.get("currentPeriod") + "") < Integer.parseInt(o2.get("currentPeriod") + "")) {

					return -1;
				}

				return 0;

			}
		});

		return new Json(true, "", results);

	}

	@Override
	public List<ApplyFo> getExamineList(Integer page, Integer pageSize, Integer status,
		String castName,String cardNo,String applyType,String loanId,String conNo,
		String conditionCustID,String conditionCustName,Date createTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("pageSize", pageSize);
		params.put("status", status);
		params.put("castName", castName);
		params.put("cardNo", cardNo);
		params.put("applyType", applyType);
		params.put("loanId", loanId);
		params.put("conNo", conNo);
		params.put("conditionCustID", conditionCustID);
		params.put("conditionCustName", conditionCustName);
		params.put("createTime", createTime);
		
		return applyFoMapper.selectSubApplyByStstus(params);

	}

	@Override
	public int getExamineListSize(Integer status,String castName,String cardNo,String
			applyType,String loanId,String conNo,String conditionCustID,
			String conditionCustName,Date createTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		params.put("castName", castName);
		params.put("cardNo", cardNo);
		params.put("applyType", applyType);
		params.put("loanId", loanId);
		params.put("conNo", conNo);
		params.put("conditionCustID", conditionCustID);
		params.put("conditionCustName", conditionCustName);
		params.put("createTime", createTime);
		
		
		
		return applyFoMapper.selectSubApplyByStstusCount(params);

	}

	@Override
	public List<ApplyFo> getExamineList2(Integer page, Integer pageSize, Integer status, String applicationCastId, 
		String applicationCastName, String empId,String castName,String cardNo,String applyType,String loanId
		,String conNo,String conditionCustID,String conditionCustName,Date createTime) {
		List<SysEmployee> users = sysEmployeeMapper.findDeptEmp(empId);
		List<String> userIds = new ArrayList<String>();
		for (SysEmployee sysEmployee : users) {
			userIds.add(sysEmployee.getEmpWorkNum());

		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("pageSize", pageSize);
		params.put("status", status);
		params.put("userIds", userIds);
		params.put("castName", castName);
		params.put("cardNo", cardNo);
		params.put("applyType", applyType);
		params.put("loanId", loanId);
		params.put("conNo", conNo);
		params.put("conditionCustID", conditionCustID);
		params.put("conditionCustName", conditionCustName);
		params.put("createTime", createTime);
	
		
		
		
		return applyFoMapper.selectSubApplyByStstusAndUsers(params);

	}

	@Override
	public int getExamineListSize2(Integer status, String applicationCastId, 
			String applicationCastName, String empId,String castName,String cardNo,
			String applyType,String loanId,String conNo,String conditionCustID,
			String conditionCustName,Date createTime) {
		List<SysEmployee> users = sysEmployeeMapper.findDeptEmp(empId);
		List<String> userIds = new ArrayList<String>();
		for (SysEmployee sysEmployee : users) {
			userIds.add(sysEmployee.getEmpWorkNum());

		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		params.put("userIds", userIds);
		params.put("castName", castName);
		params.put("cardNo", cardNo);
		params.put("applyType", applyType);
		params.put("loanId", loanId);
		params.put("conNo", conNo);
		params.put("conditionCustID", conditionCustID);
		params.put("conditionCustName", conditionCustName);
		params.put("createTime", createTime);
		return applyFoMapper.selectSubApplyByStstusAndUsersCount(params);

	}

	public int getDategap(Date smdate, Date bdate) {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);

			return Integer.parseInt(String.valueOf(between_days));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Json saveSubapply(SubApplyParam subApplyParam, String applicationCastId, String applicationCastName) {
		String applyType = subApplyParam.getApplyType();

		if ("overdueSingle".equals(applyType)) {
			Json json = this.overdueSingle(subApplyParam, applicationCastId, applicationCastName);
			return json;
		} else if ("overdueAll".equals(applyType)) {
			Json json = this.overdueAll(subApplyParam, applicationCastId, applicationCastName);

			return json;
		}
		return new Json(false, "申请类型有误，请重新申请！");

	}

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月24日 <br>
	 * 描述：逾期单月减免
	 * 
	 * @param subApplyParam
	 * @param applicationCastId
	 * @param applicationCastName
	 */
	private Json overdueSingle(SubApplyParam subApplyParam, String applicationCastId, String applicationCastName) {

		String loanId = subApplyParam.getLoanId();

		OverdueRecord overdueRecord = overdueRecordMapper.selectMinByLoanId(loanId);
		Loanbal loanbal = loanbalMapper.selectByLoanId(loanId);

		// 逾期期次
		Integer currentPeriod = overdueRecord.getOverdueNum();
		// 线上线下标识0线下1线上
		String UpdownStatus = loanbal.getUpdownStatus();
		// 合同金额
		BigDecimal conMoney = loanbal.getConMoney();
		// 贷款期次
		Integer loanPeriod = loanbal.getLoanPeriod();

		/**
		 * 1.判断是否可以发起申请
		 */
		List<Sub> subs = subMapper.selectByLoanId(loanId);
		if (subs != null && subs.size() > 0) {

			for (Sub sub : subs) {
				if ("2".equals(sub.getSubStatus())) {
					return new Json(false, "此客户已经有一笔减免申请不可再次发起！");
				}

			}

		}

		Map<String, Object> subParmas = new HashMap<String, Object>();
		subParmas.put("loanId", loanId);
		subParmas.put("subStream", currentPeriod);
		Sub sub2 = subMapper.selectByLoanIdAndStream(subParmas);

		if (sub2 != null) {
			return new Json(false, "此客户此期减免已经存在通过的申请，如需再次申请请联系相关人员！");
		}

		List<SubParams> subParams = subApplyParam.getSubparams();
		String applyId = subApplyParam.getApplyId();
		if (applyId == null || "".equals(applyId)) {
			applyId = IDGenerator.getNextID("UPIMG");
		}

		Date subDate = subApplyParam.getSubDate();

		/**
		 * 1.修改逾期金额为承诺还款日的金额
		 */

		// 查询还款明细表
		Map<String, Object> repaymentPlanparams = new HashMap<String, Object>();
		repaymentPlanparams.put("loanId", loanId);
		repaymentPlanparams.put("shouldTerm", currentPeriod);
		RepaymentPlan repaymentPlan = repaymentPlanMapper.selByLoanIdTerm(repaymentPlanparams);

		// 应还本金
		BigDecimal shouldCapi = repaymentPlan.getShouldCapi();
		// 应还利息
		BigDecimal shouldInte = repaymentPlan.getShouldInte();
		// 月还款额
		BigDecimal shouldAmt = repaymentPlan.getShouldAmt();
		// 应收违约金
		BigDecimal receivePenalty = shouldAmt.multiply(new BigDecimal(0.1));
		// 应收罚息
		BigDecimal receiveInterest = null;
		// 应收违罚金
		BigDecimal receivePenaltyInterest = null;
		// 应收总额
		BigDecimal receiveMoney = null;
		// 计算逾期天数
		Integer Overdays = getDategap(repaymentPlan.getShouldDate(), subDate);

		if ("0".equals(UpdownStatus)) {
			// 线下：合同金额*0.05%*逾期天数
			receiveInterest = conMoney.multiply(new BigDecimal(0.0005)).multiply(new BigDecimal(Overdays));
			receivePenaltyInterest = receivePenalty.add(receiveInterest);
			receiveMoney = shouldAmt.add(receivePenaltyInterest);

		} else if ("1".equals(UpdownStatus)) {
			// 线上：月还*（剩余期次+1)*0.05%*逾期天数
			Integer shengyvqicijiayi = loanPeriod - currentPeriod + 1;
			receiveInterest = shouldAmt.multiply(new BigDecimal(shengyvqicijiayi)).multiply(new BigDecimal(0.0005)).multiply(new BigDecimal(Overdays));
			receivePenaltyInterest = receivePenalty.add(receiveInterest);
			receiveMoney = shouldAmt.add(receivePenaltyInterest);
		}

		// 逾期天数
		overdueRecord.setOverdueDays(Overdays);
		// 应收违约金
		overdueRecord.setReceivePenalty(receivePenalty);
		// 应收罚息
		overdueRecord.setReceiveInterest(receiveInterest);
		// 应收违罚金（应收违约金+应收罚息）
		overdueRecord.setReceivePenaltyInterest(receivePenaltyInterest);
		// 应还本金
		overdueRecord.setReceiveCorpus(shouldCapi);
		// 应还利息
		overdueRecord.setReceiveShouldinte(shouldInte);
		// 月还款额
		overdueRecord.setAmt(shouldAmt);
		// 应收总额（违罚金+月还款额）
		overdueRecord.setReceiveMoney(receiveMoney);
		overdueRecord.setUpdateDate(new Date());
		overdueRecordMapper.updateByPrimaryKeySelective(overdueRecord);

		/**
		 * 2.插入申请数据
		 */
		for (SubParams subParam : subParams) {

			Sub sub = new Sub();
			sub.setId(PrimaryKeyUtil.getPrimaryKey());
			sub.setApplyId(applyId);
			sub.setApplyType("overdueSingle");// 减免类型
			sub.setLoanId(loanId); // 进件编号
			sub.setConNo(loanbal.getConNo()); // 合同编号
			sub.setCastName(loanbal.getCastName());// 客户姓名
			sub.setSubPenalty(subParam.getSubPenalty());// 减免罚息
			sub.setSubInterest(subParam.getSubInterest());// 减免违约金
			sub.setSubCorpus(new BigDecimal("0"));// 减免本金
			sub.setSubShouldinte(new BigDecimal("0"));// 减免利息
			sub.setSubSum(subParam.getReduction());// 减免总额
			sub.setSubStream(subParam.getCurrentPeriod());// 减免期数
			sub.setSubDate(subDate); // 减免失效时间
			sub.setSubStatus("2"); // 减免申请状态（0可用，1失效，2申请中）
			sub.setCreateTime(new Date()); // 创建日期
			sub.setUpdateDate(null);// 修改时间
			sub.setShPenalty(subParam.getShInterest()); // 实还违约金
			sub.setShInterest(subParam.getShPenalty());// 实还罚息
			sub.setShCorpus(subParam.getShouldCapi()); // 实还本金
			sub.setShShouldinte(subParam.getShouldInte()); // 实还利息
			sub.setShSum(subParam.getShreduction());// 实还总额
			subMapper.insert(sub);
		}
		/**
		 * 插入申请单表
		 */
		ApplyFo applyFo = new ApplyFo();
		applyFo.setId(PrimaryKeyUtil.getPrimaryKey());
		applyFo.setApplyId(applyId);
		applyFo.setApplyType("overdueSingle");
		applyFo.setApplicationCastId(applicationCastId);
		applyFo.setApplicationCastName(applicationCastName);
		applyFo.setApplicationDesc(subApplyParam.getApplicationDesc());
		applyFo.setCastName(loanbal.getCastName());
		applyFo.setCardNo(loanbal.getCardNo());
		applyFo.setConNo(loanbal.getConNo());
		applyFo.setLoanId(loanbal.getLoanId());
		applyFo.setApplyStatus("2");
		applyFo.setApplyReturn(null);
		applyFo.setCreateTime(new Date());
		applyFo.setUpdateDate(null);
		applyFoMapper.insert(applyFo);

		/**
		 * 插入流程环节表
		 */
		ApplyDetail applyDetail = new ApplyDetail();
		applyDetail.setId(PrimaryKeyUtil.getPrimaryKey());
		applyDetail.setApplyId(applyId);
		applyDetail.setApplyType("overdueSingle");
		applyDetail.setApplyStatus("2");
		applyDetail.setApproveId(applicationCastId);
		applyDetail.setApproveName(applicationCastName);
		applyDetail.setApproveDesc(subApplyParam.getApplicationDesc());
		applyDetail.setApproveOpinion("0");
		applyDetail.setCreateTime(new Date());
		applyDetailMapper.insert(applyDetail);

		/**
		 * 将客户挂起
		 */

		Map<String, Object> balparams = new HashMap<String, Object>();
		balparams.put("loanId", loanId);
		balparams.put("hangUp", "1");
		balparams.put("hangUpDate", new Date());

		loanbalMapper.updateHangUpByLoanId(balparams);
		return new Json(true, "单月减免申请成功！");
	}

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月24日 <br>
	 * 描述：逾期多月减免
	 * 
	 * @param subApplyParam
	 * @param applicationCastId
	 * @param applicationCastName
	 */
	private Json overdueAll(SubApplyParam subApplyParam, String applicationCastId, String applicationCastName) {
		String loanId = subApplyParam.getLoanId();
		Date subDate = subApplyParam.getSubDate();

		List<SubParams> subParams = subApplyParam.getSubparams();

		Loanbal loanbal = loanbalMapper.selectByLoanId(loanId);

		/**
		 * 1.判断是否满足减免申请条件
		 */
		List<Sub> subs = subMapper.selectByLoanId(loanId);
		if (subs != null && subs.size() > 0) {

			for (Sub sub : subs) {
				if ("2".equals(sub.getSubStatus())) {
					return new Json(false, "此客户已经有一笔减免申请不可再次发起！");
				}

			}

		}

		if (subApplyParam.getSubDate().getTime() > loanbal.getRepayDate().getTime()) {
			return new Json(false, "减免实还日期不得跨越账单日！");
		}

		for (SubParams subParam : subParams) {

			Map<String, Object> subParmas = new HashMap<String, Object>();
			subParmas.put("loanId", loanId);
			subParmas.put("subStream", subParam.getCurrentPeriod());
			Sub sub2 = subMapper.selectByLoanIdAndStream(subParmas);

			if (sub2 != null) {
				return new Json(false, "此客户此期减免已经存在通过的申请，如需再次申请请联系相关人员！");
			}

		}

		String applyId = subApplyParam.getApplyId();
		if (applyId == null || "".equals(applyId)) {
			applyId = IDGenerator.getNextID("UPIMG");
		}

		/**
		 * 2.修改逾期数据
		 */
		updateOverdueMoneyServer.updateOverdueMoney(loanId, subApplyParam.getSubDate());

		/**
		 * 3.插入申请数据
		 */

		for (SubParams subParam : subParams) {

			Sub sub = new Sub();
			sub.setId(PrimaryKeyUtil.getPrimaryKey());
			sub.setApplyId(applyId);
			sub.setApplyType("overdueSingle");// 减免类型
			sub.setLoanId(loanId); // 进件编号
			sub.setConNo(loanbal.getConNo()); // 合同编号
			sub.setCastName(loanbal.getCastName());// 客户姓名
			sub.setSubPenalty(subParam.getSubPenalty());// 减免罚息
			sub.setSubInterest(subParam.getSubInterest());// 减免违约金
			sub.setSubCorpus(new BigDecimal("0"));// 减免本金
			sub.setSubShouldinte(new BigDecimal("0"));// 减免利息
			sub.setSubSum(subParam.getReduction());// 减免总额
			sub.setSubStream(subParam.getCurrentPeriod());// 减免期数
			sub.setSubDate(subDate); // 减免失效时间
			sub.setSubStatus("2"); // 减免申请状态（0可用，1失效，2申请中）
			sub.setCreateTime(new Date()); // 创建日期
			sub.setUpdateDate(null);// 修改时间
			sub.setShPenalty(subParam.getShInterest()); // 实还违约金
			sub.setShInterest(subParam.getShPenalty());// 实还罚息
			sub.setShCorpus(subParam.getShouldCapi()); // 实还本金
			sub.setShShouldinte(subParam.getShouldInte()); // 实还利息
			sub.setShSum(subParam.getShreduction());// 实还总额
			subMapper.insert(sub);
		}

		/**
		 * 插入申请单表
		 */
		ApplyFo applyFo = new ApplyFo();
		applyFo.setId(PrimaryKeyUtil.getPrimaryKey());
		applyFo.setApplyId(applyId);
		applyFo.setApplyType("overdueAll");
		applyFo.setApplicationCastId(applicationCastId);
		applyFo.setApplicationCastName(applicationCastName);
		applyFo.setApplicationDesc(subApplyParam.getApplicationDesc());
		applyFo.setCastName(loanbal.getCastName());
		applyFo.setCardNo(loanbal.getCardNo());
		applyFo.setConNo(loanbal.getConNo());
		applyFo.setLoanId(loanbal.getLoanId());
		applyFo.setApplyStatus("2");
		applyFo.setApplyReturn(null);
		applyFo.setCreateTime(new Date());
		applyFo.setUpdateDate(null);
		applyFoMapper.insert(applyFo);

		/**
		 * 插入流程环节表
		 */
		ApplyDetail applyDetail = new ApplyDetail();
		applyDetail.setId(PrimaryKeyUtil.getPrimaryKey());
		applyDetail.setApplyId(applyId);
		applyDetail.setApplyType("overdueAll");
		applyDetail.setApplyStatus("2");
		applyDetail.setApproveId(applicationCastId);
		applyDetail.setApproveName(applicationCastName);
		applyDetail.setApproveDesc(subApplyParam.getApplicationDesc());
		applyDetail.setApproveOpinion("0");
		applyDetail.setCreateTime(new Date());
		applyDetailMapper.insert(applyDetail);

		/**
		 * 将客户挂起
		 */

		Map<String, Object> balparams = new HashMap<String, Object>();
		balparams.put("loanId", loanId);
		balparams.put("hangUp", "1");
		balparams.put("hangUpDate", new Date());

		loanbalMapper.updateHangUpByLoanId(balparams);
		return new Json(true, "多月减免申请成功！");
	}

	@Override
	public List<Sub> getSub(String applyId) {

		return subMapper.selectByApplyId(applyId);
	}

	@Override
	public void saveSubapply1(ApplyExamineParam subExamineParam, String applicationCastId, String applicationCastName) {
		String approveOpinion = subExamineParam.getApproveOpinion();
		// 获取申请单表
		ApplyFo applyFo = applyFoMapper.selectByApplyId(subExamineParam.getApplyId());
		// 获取减免申请详情表
		List<Sub> subs = subMapper.selectByApplyId(subExamineParam.getApplyId());

		if ("0".equals(approveOpinion)) {
			/**
			 * 通过，修改申请单到下一节点
			 */
			applyFo.setApplyStatus("3");
			applyFo.setUpdateDate(new Date());
			applyFoMapper.updateByPrimaryKeySelective(applyFo);
			/**
			 * 添加流程环节
			 */
			ApplyDetail applyDetail = new ApplyDetail();
			applyDetail.setId(PrimaryKeyUtil.getPrimaryKey());
			applyDetail.setApplyId(subExamineParam.getApplyId());
			applyDetail.setApplyType(subExamineParam.getApplyType());
			applyDetail.setApplyStatus("3");
			applyDetail.setApproveId(applicationCastId);
			applyDetail.setApproveName(applicationCastName);
			applyDetail.setApproveDesc(subExamineParam.getApproveDesc());
			applyDetail.setApproveOpinion("0");
			applyDetail.setCreateTime(new Date());
			applyDetailMapper.insert(applyDetail);

		} else {
			/**
			 * 拒绝，修改申请单已拒绝
			 */
			applyFo.setApplyStatus("1111");
			applyFo.setApplyReturn("3");
			applyFo.setUpdateDate(new Date());
			applyFoMapper.updateByPrimaryKeySelective(applyFo);

			/**
			 * 插入流程环节
			 */
			ApplyDetail applyDetail = new ApplyDetail();
			applyDetail.setId(PrimaryKeyUtil.getPrimaryKey());
			applyDetail.setApplyId(subExamineParam.getApplyId());
			applyDetail.setApplyType(subExamineParam.getApplyType());
			applyDetail.setApplyStatus("3");
			applyDetail.setApproveId(applicationCastId);
			applyDetail.setApproveName(applicationCastName);
			applyDetail.setApproveDesc(subExamineParam.getApproveDesc());
			applyDetail.setApproveOpinion("1");
			applyDetail.setCreateTime(new Date());
			applyDetailMapper.insert(applyDetail);

			/**
			 * 减免拒绝拒绝
			 */

			for (Sub sub : subs) {
				sub.setSubStatus("1");
				sub.setUpdateDate(new Date());
				subMapper.updateByPrimaryKeySelective(sub);
			}

			/**
			 * 修改违罚金
			 */
			updateOverdueMoneyServer.updateOverdueMoney(applyFo.getLoanId(), new Date());

			/**
			 * 取消客户挂起(逾期挂起)
			 */

			Map<String, Object> balparams = new HashMap<String, Object>();
			balparams.put("loanId", applyFo.getLoanId());
			balparams.put("hangUp", "0");
			balparams.put("hangUpDate", null);

			loanbalMapper.updateHangUpByLoanId(balparams);

		}
	}

	@Override
	public void saveSubapply2(ApplyExamineParam subExamineParam, String applicationCastId, String applicationCastName) {
		String approveOpinion = subExamineParam.getApproveOpinion();
		// 获取申请单表
		ApplyFo applyFo = applyFoMapper.selectByApplyId(subExamineParam.getApplyId());

		// 获取减免申请详情表
		List<Sub> subs = subMapper.selectByApplyId(subExamineParam.getApplyId());

		if ("0".equals(approveOpinion)) {
			/**
			 * 通过
			 */
			/**
			 * 通过，修改申请单已通过
			 */
			applyFo.setApplyStatus("0000");
			applyFo.setApplyReturn("4");
			applyFo.setUpdateDate(new Date());
			applyFoMapper.updateByPrimaryKeySelective(applyFo);

			/**
			 * 添加流程环节
			 */
			ApplyDetail applyDetail = new ApplyDetail();
			applyDetail.setId(PrimaryKeyUtil.getPrimaryKey());
			applyDetail.setApplyId(subExamineParam.getApplyId());
			applyDetail.setApplyType(subExamineParam.getApplyType());
			applyDetail.setApplyStatus("4");
			applyDetail.setApproveId(applicationCastId);
			applyDetail.setApproveName(applicationCastName);
			applyDetail.setApproveDesc(subExamineParam.getApproveDesc());
			applyDetail.setApproveOpinion("0");
			applyDetail.setCreateTime(new Date());
			applyDetailMapper.insert(applyDetail);

			/**
			 * 减免通过
			 */

			for (Sub sub : subs) {
				sub.setSubStatus("0");
				sub.setUpdateDate(new Date());
				subMapper.updateByPrimaryKeySelective(sub);
			}

			/**
			 * 修改loanBal
			 */
			String loanId = applyFo.getLoanId();
			Sub sub = subMapper.selectByLoanIdMin(loanId);

			Map<String, Object> overdue = new HashMap<String, Object>();
			overdue.put("loanId", loanId);
			overdue.put("overdueSubMoney", sub.getSubSum());
			overdue.put("overdueSubDate", sub.getSubDate());
			overdue.put("overdueSubStream", sub.getSubStream());
			loanbalMapper.updateOverduelSub(overdue);

		} else {

			/**
			 * 拒绝，修改申请单已拒绝
			 */
			applyFo.setApplyStatus("1111");
			applyFo.setApplyReturn("4");
			applyFo.setUpdateDate(new Date());
			applyFoMapper.updateByPrimaryKeySelective(applyFo);

			/**
			 * 插入流程环节
			 */
			ApplyDetail applyDetail = new ApplyDetail();
			applyDetail.setId(PrimaryKeyUtil.getPrimaryKey());
			applyDetail.setApplyId(subExamineParam.getApplyId());
			applyDetail.setApplyType(subExamineParam.getApplyType());
			applyDetail.setApplyStatus("4");
			applyDetail.setApproveId(applicationCastId);
			applyDetail.setApproveName(applicationCastName);
			applyDetail.setApproveDesc(subExamineParam.getApproveDesc());
			applyDetail.setApproveOpinion("1");
			applyDetail.setCreateTime(new Date());
			applyDetailMapper.insert(applyDetail);

			/**
			 * 减免拒绝拒绝
			 */

			for (Sub sub : subs) {
				sub.setSubStatus("1");
				sub.setUpdateDate(new Date());
				subMapper.updateByPrimaryKeySelective(sub);
			}

			/**
			 * 修改违罚金
			 */
			updateOverdueMoneyServer.updateOverdueMoney(applyFo.getLoanId(), new Date());

			/**
			 * 取消客户挂起（逾期挂起）
			 */

			Map<String, Object> balparams = new HashMap<String, Object>();
			balparams.put("loanId", applyFo.getLoanId());
			balparams.put("hangUp", "0");
			balparams.put("hangUpDate", null);

			loanbalMapper.updateHangUpByLoanId(balparams);

		}

	}

	@Override
	public List<ApplyFo> getSubapplyHistory(Integer page, Integer pageSize, String deptId,
			String applicationCastId,String castName,String cardNo,String applyType,
			String applyStatus,String loanId,String conNo,Date createTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("pageSize", pageSize);
		params.put("applicationCastId", applicationCastId);
		params.put("castName", castName);
		params.put("cardNo", cardNo);
		params.put("applyType", applyType);
		params.put("applyStatus", applyStatus);
		params.put("loanId", loanId);
		params.put("conNo", conNo);
		params.put("createTime", createTime);
		if ("593".equals(deptId)) {
			return applyFoMapper.getSubapplyHistoryAll(params);
		} else {

			return applyFoMapper.getSubapplyHistory(params);
		}
	}

	@Override
	public int getSubapplyHistoryCount(String deptId, String applicationCastId,
			String castName,String cardNo,String applyType,String applyStatus,String loanId,
			String conNo,Date createTime) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("applicationCastId", applicationCastId);
		params.put("castName", castName);
		params.put("cardNo", cardNo);
		params.put("applyType", applyType);
		params.put("applyStatus", applyStatus);
		params.put("loanId", loanId);
		params.put("conNo", conNo);
		params.put("createTime", createTime);
		
		
		if ("593".equals(deptId)) {
			return applyFoMapper.getSubapplyHistoryAllCount(params);
		} else {

			return applyFoMapper.getSubapplyHistoryCount(params);
		}
	}

	@Override
	public Json saveVoteDown(ApplyExamineParam subExamineParam, String applicationCastId, String applicationCastName) {

		// 获取申请单表
		ApplyFo applyFo = applyFoMapper.selectByApplyId(subExamineParam.getApplyId());

		if ("1111".equals(applyFo.getApplyStatus())) {
			return new Json(false, "减免申请已拒绝，不能再次拒绝！");
		}

		// 获取减免申请详情表
		List<Sub> subs = subMapper.selectByApplyId(subExamineParam.getApplyId());

		/**
		 * 拒绝，修改申请单已拒绝
		 */
		applyFo.setApplyStatus("1111");
		applyFo.setApplyReturn("5");
		applyFo.setUpdateDate(new Date());
		applyFoMapper.updateByPrimaryKeySelective(applyFo);

		/**
		 * 插入流程环节
		 */
		ApplyDetail applyDetail = new ApplyDetail();
		applyDetail.setId(PrimaryKeyUtil.getPrimaryKey());
		applyDetail.setApplyId(subExamineParam.getApplyId());
		applyDetail.setApplyType(subExamineParam.getApplyType());
		applyDetail.setApplyStatus("5");
		applyDetail.setApproveId(applicationCastId);
		applyDetail.setApproveName(applicationCastName);
		applyDetail.setApproveDesc(subExamineParam.getApproveDesc());
		applyDetail.setApproveOpinion("1");
		applyDetail.setCreateTime(new Date());
		applyDetailMapper.insert(applyDetail);

		/**
		 * 减免拒绝拒绝
		 */

		for (Sub sub : subs) {
			sub.setSubStatus("1");
			sub.setUpdateDate(new Date());
			subMapper.updateByPrimaryKeySelective(sub);
		}

		/**
		 * 修改loanbal减免金额为0
		 */

		loanbalMapper.updateOverduelSubToNull(applyFo.getLoanId());

		/**
		 * 修改违罚金
		 */
		updateOverdueMoneyServer.updateOverdueMoney(applyFo.getLoanId(), new Date());

		/**
		 * 取消客户挂起
		 */

		Map<String, Object> balparams = new HashMap<String, Object>();
		balparams.put("loanId", applyFo.getLoanId());
		balparams.put("hangUp", "0");
		balparams.put("hangUpDate", null);

		loanbalMapper.updateHangUpByLoanId(balparams);
		return new Json(true, "减免申请已拒绝！");
	}
}
