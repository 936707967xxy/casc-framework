/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.server.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.after.api.model.table.Loanbal;
import com.hoomsun.after.api.model.table.OverdueRecord;
import com.hoomsun.after.api.model.table.RepaymentPlan;
import com.hoomsun.after.api.server.UpdateOverdueMoneyServer;
import com.hoomsun.after.dao.LoanbalMapper;
import com.hoomsun.after.dao.OverdueRecordMapper;
import com.hoomsun.after.dao.RepaymentPlanMapper;
import com.hoomsun.after.dao.UserCountMapper;
import com.hoomsun.core.dao.SysContractMapper;
import com.hoomsun.core.util.PrimaryKeyUtil;
import com.hoomsun.message.server.inter.NoticeServerI;

/**
 * 作者：金世强 <br>
 * 创建时间：2018年3月7日 <br>
 * 描述：逾期数据核对修改，没夸账期以及夸一个账期调度一次 每加一个账期多调度一次。多调无碍.
 */
@Service("updateOverdueMoneyServer")
public class UpdateOverdueMoneyServerImpl implements UpdateOverdueMoneyServer {

	@Autowired
	private LoanbalMapper loanbalMapper;
	@Autowired
	private UserCountMapper userCountMapper;
	@Autowired
	private RepaymentPlanMapper repaymentPlanMapper;

	@Autowired
	private OverdueRecordMapper overdueRecordMapper;
	@Autowired
	private SysContractMapper sysContractMapper;
	@Autowired
	private NoticeServerI noticeServer;

	@Override
	public String updateOverdueMoney(String loanId, Date date) {

		Loanbal lbal = loanbalMapper.selectByLoanId(loanId);
		if (lbal.getRepayDate().getTime() < date.getTime()) {

			// 当前期次
			Integer currentPeriod = lbal.getCurrentPeriod();

			// 贷款期次
			Integer loanPeriod = lbal.getLoanPeriod();

			String productalias = lbal.getProductalias();

			String applyId = lbal.getApplyId();

			/**
			 * 加入逾期记录表，并修改逾期标识
			 */
			OverdueRecord overdueRecord = new OverdueRecord();
			overdueRecord.setId(PrimaryKeyUtil.getPrimaryKey());
			// 进件编号
			overdueRecord.setLoanId(loanId);
			// 合同编号
			overdueRecord.setConNo(lbal.getConNo());
			// 客户姓名
			overdueRecord.setCastName(lbal.getCastName());
			// 逾期天数
			overdueRecord.setOverdueDays(1);
			// 逾期期数
			overdueRecord.setOverdueNum(currentPeriod);
			// 结清标识
			overdueRecord.setSettleFlag("0");
			// 是否标红(1标红)
			overdueRecord.setToRed("0");
			// 修改时间
			overdueRecord.setUpdateDate(null);
			// 创建日期
			overdueRecord.setCreateTime(new Date());

			overdueRecordMapper.insert(overdueRecord);

			// loanbalMapper.updateDelayFlagByLoanId(loanId);

			/**
			 * 贷后逻辑表loanbal到下一账期，(如果当前是最后一期则修改最后一期标识),并修改合同表，以及APP方推送消息,
			 * 清除未申请留安贷后客服
			 */

			if (currentPeriod == loanPeriod) {
				loanbalMapper.updateSettleFlagByLoanId(loanId);

			} else {

				Map<String, Object> repaymentPlanparams2 = new HashMap<String, Object>();
				repaymentPlanparams2.put("loanId", loanId);
				repaymentPlanparams2.put("shouldTerm", currentPeriod + 1);
				RepaymentPlan repaymentPlan2 = repaymentPlanMapper.selByLoanIdTerm(repaymentPlanparams2);

				Map<String, Object> loanbalparams = new HashMap<String, Object>();
				loanbalparams.put("loanId", loanId);
				loanbalparams.put("repatDate", repaymentPlan2.getShouldDate());
				loanbalparams.put("currentPeriod", repaymentPlan2.getShouldTerm());
				loanbalMapper.updateNextPaymentByLoanId(loanbalparams);
			}

			loanbalMapper.updateLoanManagerTonull(loanId);

			Map<String, String> constatus = new HashMap<String, String>();
			constatus.put("applyId", applyId);
			constatus.put("status", "5");// 2结清，3未结清，4提前结清，5逾期
			sysContractMapper.updateConStatusByApplyId(constatus);

			String msg = "您的" + productalias + "还款已逾期，请及时还款以免影响征信。";
			noticeServer.sendMsg(applyId, msg, 2); // 消息推送

		}

		List<OverdueRecord> overdueRecords = overdueRecordMapper.selectByLoanId(loanId);

		for (OverdueRecord overdueRecord : overdueRecords) {

			// String loanId = overdueRecord.getLoanId();

			String conNo = overdueRecord.getConNo();

			String castName = overdueRecord.getCastName();

			Integer currentPeriod = overdueRecord.getOverdueNum();

			String toRed = overdueRecord.getToRed();

			/**
			 * 查询loanbal表
			 */

			Loanbal loanbal = loanbalMapper.selectByLoanId(loanId);
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
			Integer Overdays = getDategap(repaymentPlan.getShouldDate(), date);

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
			// 进件编号
			overdueRecord.setLoanId(loanId);
			// 合同编号
			overdueRecord.setConNo(conNo);
			// 客户姓名
			overdueRecord.setCastName(castName);
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

			// 结清标识
			overdueRecord.setSettleFlag("0");

			// 是否标红(1标红)
			overdueRecord.setToRed(toRed);

			// 修改时间
			overdueRecord.setUpdateDate(new Date());
			// 创建日期
			overdueRecord.setCreateTime(new Date());

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
		return "0";
	}

	/**
	 * 得到两个日期的日期差
	 */

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

}
