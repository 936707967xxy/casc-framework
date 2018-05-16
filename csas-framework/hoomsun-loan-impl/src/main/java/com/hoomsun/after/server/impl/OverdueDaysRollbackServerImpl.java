/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.server.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.after.api.model.param.OverdueRecordUpdateParmas;
import com.hoomsun.after.api.model.table.Loanbal;
import com.hoomsun.after.api.model.table.OverdueRecord;
import com.hoomsun.after.api.model.table.RepaymentPlan;
import com.hoomsun.after.api.model.vo.OverdueRecordUpdateVo;
import com.hoomsun.after.api.server.OverdueDaysRollbackServer;
import com.hoomsun.after.dao.LoanbalMapper;
import com.hoomsun.after.dao.OverdueRecordMapper;
import com.hoomsun.after.dao.RepaymentPlanMapper;

/**
 * 作者：jinshiqiang <br>
 * 创建时间：2018年4月3日 <br>
 * 描述：逾期天数批量回退功能
 */
@Service("overdueDaysRollbackServer")
public class OverdueDaysRollbackServerImpl implements OverdueDaysRollbackServer {

	@Autowired
	private OverdueRecordMapper overdueRecordMapper;

	@Autowired
	private LoanbalMapper loanbalMapper;

	@Autowired
	private RepaymentPlanMapper repaymentPlanMapper;

	@Override
	public void saveOverdueDaysRollback(OverdueRecordUpdateParmas overdueRecordUpdateParmas) {

		List<OverdueRecord> overdueRecords = overdueRecordUpdateParmas.getOverdueRecords();

		String nomal = overdueRecordUpdateParmas.getNomal();

		String loanId = overdueRecordUpdateParmas.getOverdueRecords().get(0).getLoanId();

		Loanbal lbal = loanbalMapper.selectByLoanId(loanId);

		for (OverdueRecord overdueRollback : overdueRecords) {

			Integer days = overdueRollback.getRealOverDays();
			Integer stream = overdueRollback.getOverdueNum();

			// 线上线下标识0线下1线上
			String UpdownStatus = lbal.getUpdownStatus();
			// 合同金额
			BigDecimal conMoney = lbal.getConMoney();
			// 贷款期次
			Integer loanPeriod = lbal.getLoanPeriod();

			/**
			 * 查询还款明细表
			 */
			Map<String, Object> repaymentPlanparams = new HashMap<String, Object>();
			repaymentPlanparams.put("loanId", loanId);
			repaymentPlanparams.put("shouldTerm", stream);
			RepaymentPlan repaymentPlan = repaymentPlanMapper.selByLoanIdTerm(repaymentPlanparams);

			// 应还本金
			BigDecimal shouldCapi = repaymentPlan.getShouldCapi();
			// 应还利息
			BigDecimal shouldInte = repaymentPlan.getShouldInte();
			// 月还款额
			BigDecimal shouldAmt = repaymentPlan.getShouldAmt();

			// 应收违约金
			BigDecimal receivePenalty = null;
			if (days == 0) {
				receivePenalty = new BigDecimal(0);
			} else {
				receivePenalty = shouldAmt.multiply(new BigDecimal(0.1));
			}

			// 应收罚息
			BigDecimal receiveInterest = null;
			// 应收违罚金
			BigDecimal receivePenaltyInterest = null;
			// 应收总额
			BigDecimal receiveMoney = null;

			if ("0".equals(UpdownStatus)) {
				// 线下：合同金额*0.05%*逾期天数
				receiveInterest = conMoney.multiply(new BigDecimal(0.0005)).multiply(new BigDecimal(days));
				receivePenaltyInterest = receivePenalty.add(receiveInterest);
				receiveMoney = shouldAmt.add(receivePenaltyInterest);

			} else if ("1".equals(UpdownStatus)) {
				// 线上：月还*（剩余期次+1)*0.05%*逾期天数
				Integer shengyvqicijiayi = loanPeriod - stream + 1;
				receiveInterest = shouldAmt.multiply(new BigDecimal(shengyvqicijiayi)).multiply(new BigDecimal(0.0005)).multiply(new BigDecimal(days));
				receivePenaltyInterest = receivePenalty.add(receiveInterest);
				receiveMoney = shouldAmt.add(receivePenaltyInterest);
			}

			Map<String, Object> overParams = new HashMap<String, Object>();
			overParams.put("loanId", loanId);
			overParams.put("stream", stream);

			OverdueRecord overdueRecord = overdueRecordMapper.selectByLoanIdAndStream(overParams);

			overdueRecord.setOverdueDays(days);

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

			// 修改时间
			overdueRecord.setUpdateDate(new Date());

			overdueRecordMapper.updateByPrimaryKeySelective(overdueRecord);

			/**
			 * 查询最小逾期期次逾期数据，修改M段
			 */
			OverdueRecord nextOverdueRecord = overdueRecordMapper.selectMinByLoanId(loanId);

			Integer mSection = (nextOverdueRecord.getOverdueDays() / 30) + 1;

			Map<String, Object> mSessionParams = new HashMap<String, Object>();

			mSessionParams.put("mSection", mSection);
			mSessionParams.put("loanId", loanId);
			mSessionParams.put("delayFlag", "1");

			loanbalMapper.updateMSectionDelayFlagByLoanId(mSessionParams);
		}

		/**
		 * 是否有逾期数据回退正常
		 */
		if ("1".equals(nomal)) {
			Integer stream;
			OverdueRecord overdueRecord = overdueRecordMapper.selectMaxDaysZeroByLoanId(loanId);
			if (overdueRecord == null) {

				return;
			}

			stream = overdueRecord.getOverdueNum();
			boolean isnomal = false;
			/**
			 * 计算应该回退到的期次
			 */
			while (true) {
				Map<String, Object> overParams = new HashMap<String, Object>();
				overParams.put("loanId", loanId);
				overParams.put("stream", stream - 1);

				OverdueRecord overdueRecord2 = overdueRecordMapper.selectByLoanIdAndStream(overParams);
				if (overdueRecord2 == null) {
					isnomal = true;
					break;
				}

				stream = overdueRecord2.getOverdueNum();
				Integer overDays = overdueRecord2.getOverdueDays();
				if (overDays > 0) {
					break;
				}

			}

			/**
			 * 根据期次删除此其次以及大于此其次的逾期数据
			 */
			Map<String, Object> rollbackoverdueRecordParam = new HashMap<String, Object>();
			rollbackoverdueRecordParam.put("loanId", loanId);
			rollbackoverdueRecordParam.put("stream", stream);
			overdueRecordMapper.deleteRollbackByLoanIdAndStream(rollbackoverdueRecordParam);

			/**
			 * 查询还款明细表
			 */
			Map<String, Object> repaymentPlanparams = new HashMap<String, Object>();
			repaymentPlanparams.put("loanId", loanId);
			repaymentPlanparams.put("shouldTerm", stream);
			RepaymentPlan repaymentPlan2 = repaymentPlanMapper.selByLoanIdTerm(repaymentPlanparams);
			/**
			 * 回退Loanbal
			 */
			Map<String, Object> loanbalparams = new HashMap<String, Object>();
			loanbalparams.put("loanId", loanId);
			loanbalparams.put("repatDate", repaymentPlan2.getShouldDate());
			loanbalparams.put("currentPeriod", repaymentPlan2.getShouldTerm());
			loanbalMapper.updateNextPaymentByLoanId(loanbalparams);

			/**
			 * 转为正常客户
			 */
			if (isnomal) {

				Map<String, Object> mSessionParams = new HashMap<String, Object>();

				mSessionParams.put("mSection", 0);
				mSessionParams.put("loanId", loanId);
				mSessionParams.put("delayFlag", "1");

				loanbalMapper.updateMSectionDelayFlagByLoanId(mSessionParams);

				Map<String, Object> customerOrLoanParams = new HashMap<String, Object>();
				customerOrLoanParams.put("loanId", loanId);
				customerOrLoanParams.put("customerOrLoan", "0");
				loanbalMapper.updatecustomerOrLoanByloanId(customerOrLoanParams);
			} else {
				OverdueRecord overdueRecordMin = overdueRecordMapper.selectMinByLoanId(loanId);

				Integer mSession = (overdueRecordMin.getOverdueDays() / 30) + 1;

				Map<String, Object> mSessionParams = new HashMap<String, Object>();

				mSessionParams.put("mSection", mSession);
				mSessionParams.put("loanId", loanId);
				mSessionParams.put("delayFlag", "1");

				loanbalMapper.updateMSectionDelayFlagByLoanId(mSessionParams);

			}

		}

	}

	@Override
	public OverdueRecordUpdateVo selectOverdueAll2(OverdueRecordUpdateParmas overdueRecordUpdateParmas) {

		Date nowDate = overdueRecordUpdateParmas.getNowDate();

		int rollbackdays = (int) ((new Date().getTime() - nowDate.getTime()) / (24 * 60 * 60 * 1000)) + 1;

		List<OverdueRecord> overdueRecords = overdueRecordUpdateParmas.getOverdueRecords();

		String nomal = "0";

		for (OverdueRecord overdueRecord : overdueRecords) {

			Integer days = overdueRecord.getOverdueDays() - rollbackdays;

			if (days < 0) {
				days = 0;
			}
			if (days == 0) {
				nomal = "1";
			}

			String loanId = overdueRecord.getLoanId();

			Integer stream = overdueRecord.getOverdueNum();

			Loanbal lbal = loanbalMapper.selectByLoanId(loanId);

			// 线上线下标识0线下1线上
			String UpdownStatus = lbal.getUpdownStatus();
			// 合同金额
			BigDecimal conMoney = lbal.getConMoney();
			// 贷款期次
			Integer loanPeriod = lbal.getLoanPeriod();

			// 应还本金
			BigDecimal shouldCapi = overdueRecord.getReceiveCorpus();
			// 应还利息
			BigDecimal shouldInte = overdueRecord.getReceiveShouldinte();
			// 月还款额
			BigDecimal shouldAmt = overdueRecord.getAmt();

			// 应收违约金
			BigDecimal receivePenalty = null;
			if (days == 0) {
				receivePenalty = new BigDecimal(0);
			} else {
				receivePenalty = shouldAmt.multiply(new BigDecimal(0.1));
			}

			// 应收罚息
			BigDecimal receiveInterest = null;
			// 应收违罚金
			BigDecimal receivePenaltyInterest = null;
			// 应收总额
			BigDecimal receiveMoney = null;

			if ("0".equals(UpdownStatus)) {
				// 线下：合同金额*0.05%*逾期天数
				receiveInterest = conMoney.multiply(new BigDecimal(0.0005)).multiply(new BigDecimal(days));
				receivePenaltyInterest = receivePenalty.add(receiveInterest);
				receiveMoney = shouldAmt.add(receivePenaltyInterest);

			} else if ("1".equals(UpdownStatus)) {
				// 线上：月还*（剩余期次+1)*0.05%*逾期天数
				Integer shengyvqicijiayi = loanPeriod - stream + 1;
				receiveInterest = shouldAmt.multiply(new BigDecimal(shengyvqicijiayi)).multiply(new BigDecimal(0.0005)).multiply(new BigDecimal(days));
				receivePenaltyInterest = receivePenalty.add(receiveInterest);
				receiveMoney = shouldAmt.add(receivePenaltyInterest);
			}

			overdueRecord.setRealOverDays(days);

			// 应收违约金
			overdueRecord.setReceivePenalty(receivePenalty.setScale(2, BigDecimal.ROUND_HALF_UP));
			// 应收罚息
			overdueRecord.setReceiveInterest(receiveInterest.setScale(2, BigDecimal.ROUND_HALF_UP));
			// 应收违罚金（应收违约金+应收罚息）
			overdueRecord.setReceivePenaltyInterest(receivePenaltyInterest.setScale(2, BigDecimal.ROUND_HALF_UP));
			// 应还本金
			overdueRecord.setReceiveCorpus(shouldCapi.setScale(2, BigDecimal.ROUND_HALF_UP));
			// 应还利息
			overdueRecord.setReceiveShouldinte(shouldInte.setScale(2, BigDecimal.ROUND_HALF_UP));
			// 月还款额
			overdueRecord.setAmt(shouldAmt.setScale(2, BigDecimal.ROUND_HALF_UP));
			// 应收总额（违罚金+月还款额）
			overdueRecord.setReceiveMoney(receiveMoney.setScale(2, BigDecimal.ROUND_HALF_UP));

		}

		OverdueRecordUpdateVo overdueRecordUpdateVo = new OverdueRecordUpdateVo();
		overdueRecordUpdateVo.setOverdueRecords(overdueRecords);
		overdueRecordUpdateVo.setNomal(nomal);

		return overdueRecordUpdateVo;
	}

	@Override
	public List<OverdueRecord> selectOverdueAll(String loanId) {

		List<OverdueRecord> overdueRecords = overdueRecordMapper.selectByLoanId(loanId);

		for (OverdueRecord overdueRecord : overdueRecords) {
			overdueRecord.setRealOverDays(overdueRecord.getOverdueDays());

		}

		return overdueRecords;
	}

}
