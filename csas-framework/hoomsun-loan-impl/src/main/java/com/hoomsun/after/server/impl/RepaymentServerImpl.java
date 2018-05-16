/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.server.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.after.api.model.table.Export;
import com.hoomsun.after.api.model.table.Loanbal;
import com.hoomsun.after.api.model.table.Loanbook;
import com.hoomsun.after.api.model.table.OverdueRecord;
import com.hoomsun.after.api.model.table.RepaymentPlan;
import com.hoomsun.after.api.model.table.Sub;
import com.hoomsun.after.api.model.table.UserCount;
import com.hoomsun.after.api.model.vo.RepaymentServerResult;
import com.hoomsun.after.api.server.RepaymentServer;
import com.hoomsun.after.dao.ExportMapper;
import com.hoomsun.after.dao.LoanbalMapper;
import com.hoomsun.after.dao.LoanbookMapper;
import com.hoomsun.after.dao.OutBoundMapper;
import com.hoomsun.after.dao.OverdueRecordMapper;
import com.hoomsun.after.dao.RepaymentPlanMapper;
import com.hoomsun.after.dao.SubMapper;
import com.hoomsun.after.dao.UserCountMapper;
import com.hoomsun.core.dao.SysContractMapper;
import com.hoomsun.core.util.PrimaryKeyUtil;
import com.hoomsun.message.server.inter.NoticeServerI;

/**
 * 作者：金世强 <br>
 * 创建时间：2018年2月27日 <br>
 * 描述：
 */
@Service("repaymentServer")
public class RepaymentServerImpl implements RepaymentServer {

	@Autowired
	private UserCountMapper userCountMapper;
	@Autowired
	private LoanbalMapper loanbalMapper;
	@Autowired
	private LoanbookMapper loanbookMapper;
	@Autowired
	private RepaymentPlanMapper repaymentPlanMapper;
	@Autowired
	private ExportMapper exportMapper;
	@Autowired
	private SysContractMapper sysContractMapper;
	@Autowired
	private NoticeServerI noticeServer;
	@Autowired
	private OverdueRecordMapper overdueRecordMapper;
	@Autowired
	private SubMapper subMapper;
	@Autowired
	private OutBoundMapper outBoundMapper;

	@Override
	public RepaymentServerResult saveNomalRepayment(String custId, String loanId, Integer stream, String applicationCastId, String applicationCastName) {

		RepaymentServerResult repaymentServerResult = new RepaymentServerResult();
		/**
		 * 查询账户表
		 */

		UserCount userCount = userCountMapper.selectByCustId(custId);

		// 账户余额
		BigDecimal bal = userCount.getBal();

		/**
		 * 查询loanbal
		 */
		Loanbal loanbal = loanbalMapper.selectByLoanId(loanId);

		String applyId = loanbal.getApplyId();
		// loanbal当前期数
		Integer currentPeriod = loanbal.getCurrentPeriod();
		// 正常还款减免金额
		BigDecimal normalSubMoney = loanbal.getNormalSubMoney();
		// 正常还款减免日期
		Date normalSubDate = loanbal.getNormalSubDate();
		// 正常还款减免期次
		Integer normalSubStream = loanbal.getNormalSubStream();

		// 挂起标识
		String hangUpDeduct = loanbal.getHangUpDeduct();

		// 逾期标识
		String delayFlag = loanbal.getDelayFlag();

		// 最后一期标识
		String settleFlag = loanbal.getSettleFlag();
		// 贷款期次
		Integer loanPeriod = loanbal.getLoanPeriod();
		// 线上线下标识
		String updownStatus = loanbal.getUpdownStatus();

		/**
		 * 判断是否满足正常月还客户条件
		 */
		if ("1".equals(hangUpDeduct)) {
			repaymentServerResult.setStream(stream);
			repaymentServerResult.setStatus("2");
			repaymentServerResult.setMsg("此客户在挂起中，暂时不能进行划扣");
			return repaymentServerResult;

		}

		if (stream != currentPeriod) {
			repaymentServerResult.setStream(stream);
			repaymentServerResult.setStatus("2");
			repaymentServerResult.setMsg("还款期次与客户当前所处期次不符，余额扣除失败");
			return repaymentServerResult;
		}

		if ("1".equals(delayFlag)) {
			repaymentServerResult.setStream(stream);
			repaymentServerResult.setStatus("2");
			repaymentServerResult.setMsg("此客户为逾期客户，不能正常月还");
			return repaymentServerResult;
		}

		if ("1".equals(settleFlag)) {
			repaymentServerResult.setStream(stream);
			repaymentServerResult.setStatus("2");
			repaymentServerResult.setMsg("此客户账单已到最后一期，不能正常月还");
			return repaymentServerResult;
		}
		/**
		 * 得到应该扣除的余额
		 */
		Map<String, Object> repaymentPlanparams = new HashMap<String, Object>();
		repaymentPlanparams.put("loanId", loanId);
		repaymentPlanparams.put("shouldTerm", stream);
		RepaymentPlan repaymentPlan = repaymentPlanMapper.selByLoanIdTerm(repaymentPlanparams);
		// 月还款额
		BigDecimal amt = repaymentPlan.getShouldAmt();
		// 提前还款应还
		BigDecimal advanceShould = repaymentPlan.getAdvanceShould();

		BigDecimal preretuamt = null;

		if ("1".equals(updownStatus)) {
			// 提前还款退还服务费hxb
			preretuamt = repaymentPlan.getPreretuamtHxb();
		} else if ("0".equals(updownStatus)) {
			// 提前还款退还服务费zx
			preretuamt = repaymentPlan.getPreretuamtChannel();
		}

		// 支出金额
		BigDecimal expendMoney = amt;
		if (normalSubMoney != null && normalSubMoney.compareTo(new BigDecimal(0)) > 0 && normalSubStream == stream && normalSubDate.getTime() > new Date().getTime()) {
			expendMoney = amt.subtract(normalSubMoney);
		}

		if (bal.compareTo(expendMoney) < 0) {
			repaymentServerResult.setStream(stream);
			repaymentServerResult.setStatus("2");
			repaymentServerResult.setMsg("余额不足");
			return repaymentServerResult;
		}

		/**
		 * 保存支出记录
		 */
		Export export = new Export();
		export.setId(PrimaryKeyUtil.getPrimaryKey());
		export.setLoanId(loanId);
		export.setConNo(loanbal.getCardNo());
		export.setCastName(loanbal.getCastName());
		// 支出类型(1正常月还支出,2提前还款支出，3逾期月还支出)
		export.setExpendType("1");
		export.setExpendTypeVal("正常月还支出");
		// 支出金额
		export.setExpendMoney(expendMoney);
		export.setExpendDate(new Date());
		// 减免金额
		export.setSubMoney(normalSubMoney == null ? new BigDecimal(0) : normalSubMoney);
		// 支出期次
		export.setExpendStream(stream);
		// 当期应还本金
		export.setReceiveCorpus(repaymentPlan.getShouldCapi());
		// 当期应还利息
		export.setReceiveShouldinte(repaymentPlan.getShouldInte());
		// 当期月还款额（应还本金+应还利息）
		export.setAmt(amt);
		// 当期应收违约金
		export.setReceivePenalty(new BigDecimal(0));
		// 当期应收罚息
		export.setReceiveInterest(new BigDecimal(0));
		// 当期应收违罚金（应收违约金+应收罚息）
		export.setReceivePenaltyInterest(new BigDecimal(0));
		// 当期应收总额（违罚金+月还款额）
		export.setReceiveMoney(amt);
		export.setApplicationCastId(applicationCastId);
		export.setApplicationCastName(applicationCastName);
		export.setCreateTime(new Date());

		export.setUpdateDate(null);
		// 提前结清应还金额
		export.setReceiveAdvance(advanceShould);
		// 提前还清减免渠道服务费
		export.setChannelServiceFee(preretuamt);
		// 账户ID
		export.setCastId(custId);
		exportMapper.insertSelective(export);

		/**
		 * 保存还款记录
		 */
		Loanbook loanbook = new Loanbook();
		loanbook.setId(PrimaryKeyUtil.getPrimaryKey());
		loanbook.setLoanId(loanId);
		loanbook.setConNo(loanbal.getCardNo());
		loanbook.setCastName(loanbal.getCastName());
		// 应还日期
		loanbook.setRepayDate(loanbal.getRepayDate());
		// 应还期次
		loanbook.setStream(stream);
		// 当期应还本金
		loanbook.setReceiveCorpus(repaymentPlan.getShouldCapi());
		// 当期应还利息
		loanbook.setReceiveShouldinte(repaymentPlan.getShouldInte());
		// 当期月还款额（应还本金+应还利息）
		loanbook.setAmt(amt);
		// 当期应收违约金
		loanbook.setReceivePenalty(new BigDecimal(0));
		// 当期应收罚息
		loanbook.setReceiveInterest(new BigDecimal(0));
		// 当期应收违罚金（应收违约金+应收罚息）
		loanbook.setReceivePenaltyInterest(new BigDecimal(0));
		// 当期应收总额（违罚金+月还款额）
		loanbook.setReceiveMoney(amt);
		// 提前结清应还金额
		loanbook.setReceiveAdvance(advanceShould);
		// 提前还清减免渠道服务费
		loanbook.setChannelServiceFee(preretuamt);
		// 逾期天数
		loanbook.setOverdueDays(0);
		// 减免金额
		loanbook.setSubMoney(normalSubMoney);
		// 扣除金额
		loanbook.setActualMoney(expendMoney);
		// 还款类型(1正常月还,2提前还款，3逾期月还)
		loanbook.setLoanbookType("1");

		loanbook.setLoanbookTypeVal("正常月还");

		loanbook.setCreateTime(new Date());
		loanbook.setUpdateDate(null);

		loanbookMapper.insertSelective(loanbook);

		/**
		 * 扣除余额，并且归0正常还款减免金额 日期 期次
		 */
		BigDecimal currentBal = bal.subtract(expendMoney);
		Map<String, Object> userCountparams = new HashMap<String, Object>();
		userCountparams.put("custId", custId);
		userCountparams.put("bal", currentBal);
		userCountMapper.updatebal(userCountparams);

		loanbalMapper.updateNomalSubToNull(loanId);

		/**
		 * 贷后逻辑表loanbal到下一账期，(如果当前是最后一期则结清,并修改合同表，以及APP方推送消息)
		 */

		Map<String, Object> repaymentPlanparams2 = new HashMap<String, Object>();
		repaymentPlanparams2.put("loanId", loanId);
		repaymentPlanparams2.put("shouldTerm", stream + 1);
		RepaymentPlan repaymentPlan2 = repaymentPlanMapper.selByLoanIdTerm(repaymentPlanparams2);
		if (stream == loanPeriod) {
			loanbalMapper.updateSettleFlagByLoanId(loanId);

			Map<String, String> constatus = new HashMap<String, String>();
			constatus.put("applyId", applyId);
			constatus.put("status", "2");// 2结清，3未结清，4提前结清，5逾期
			sysContractMapper.updateConStatusByApplyId(constatus);

			String msg = "恭喜，您的" + loanbal.getProductName() + "借款已全部还清，您可以继续申请其他产品。";
			noticeServer.sendMsg(applyId, msg, 2); // 消息推送

		} else {
			Map<String, Object> loanbalparams = new HashMap<String, Object>();
			loanbalparams.put("loanId", loanId);
			loanbalparams.put("repatDate", repaymentPlan2.getShouldDate());
			loanbalparams.put("currentPeriod", stream + 1);

			loanbalMapper.updateNextPaymentByLoanId(loanbalparams);
		}

		repaymentServerResult.setDedutBal(expendMoney);
		repaymentServerResult.setStream(stream);
		repaymentServerResult.setStatus("1");
		repaymentServerResult.setMsg("本期正常还款成功");
		return repaymentServerResult;
	}

	@Override
	public RepaymentServerResult saveOverdueRepayment(String custId, String loanId, Integer stream, String applicationCastId, String applicationCastName) {

		RepaymentServerResult repaymentServerResult = new RepaymentServerResult();
		/**
		 * 查询账户表
		 */
		UserCount userCount = userCountMapper.selectByCustId(custId);

		// 账户余额
		BigDecimal bal = userCount.getBal();

		/**
		 * 查询loanbal
		 */
		Loanbal loanbal = loanbalMapper.selectByLoanId(loanId);

		String applyId = loanbal.getApplyId();

		// 逾期还款减免金额
		BigDecimal overdueSubMoney = loanbal.getOverdueSubMoney();
		// 逾期还款减免日期
		Date overdueSubDate = loanbal.getOverdueSubDate();
		// 逾期还款减免期次
		Integer overdueSubStream = loanbal.getOverdueSubStream();
		// loanbal当前期数
		// Integer currentPeriod = loanbal.getCurrentPeriod();
		// 挂起标识
		String hangUpDeduct = loanbal.getHangUpDeduct();

		// 逾期标识
		String delayFlag = loanbal.getDelayFlag();

		// 最后一期标识
		String settleFlag = loanbal.getSettleFlag();
		// 贷款期次
		Integer loanPeriod = loanbal.getLoanPeriod();
		// 线上线下标识
		String updownStatus = loanbal.getUpdownStatus();

		/**
		 * 查询逾期表最小逾期期次
		 */
		OverdueRecord overdueRecord = overdueRecordMapper.selectMinByLoanId(loanId);

		// 逾期天数
		Integer overdueDays = overdueRecord.getOverdueDays();
		// 应收违约金
		BigDecimal receivePenalty = overdueRecord.getReceivePenalty();
		// 应收罚息
		BigDecimal receiveInterest = overdueRecord.getReceiveInterest();
		// 应收违罚金（应收违约金+应收罚息）
		BigDecimal receivePenaltyInterest = overdueRecord.getReceivePenaltyInterest();
		// 应还本金
		BigDecimal receiveCorpus = overdueRecord.getReceiveCorpus();
		// 应还利息
		BigDecimal receiveShouldinte = overdueRecord.getReceiveShouldinte();
		// 月还款额
		BigDecimal amt = overdueRecord.getAmt();
		// 应收总额（违罚金+月还款额）
		BigDecimal receiveMoney = overdueRecord.getReceiveMoney();
		// 逾期期数
		Integer overdueNum = overdueRecord.getOverdueNum();
		// 结清标识
		// String SettleFlag = overdueRecord.getSettleFlag();
		// 是否标红(1标红)
		// String toRed = overdueRecord.getToRed();

		/**
		 * 查询还款明细表
		 */
		Map<String, Object> repaymentPlanparams = new HashMap<String, Object>();
		repaymentPlanparams.put("loanId", loanId);
		repaymentPlanparams.put("shouldTerm", stream);
		RepaymentPlan repaymentPlan = repaymentPlanMapper.selByLoanIdTerm(repaymentPlanparams);

		// 提前还款应还
		BigDecimal advanceShould = repaymentPlan.getAdvanceShould();

		BigDecimal preretuamt = null;

		if ("1".equals(updownStatus)) {
			// 提前还款退还服务费hxb
			preretuamt = repaymentPlan.getPreretuamtHxb();
		} else if ("0".equals(updownStatus)) {
			// 提前还款退还服务费zx
			preretuamt = repaymentPlan.getPreretuamtChannel();
		}

		/**
		 * 判断是否满足正常月还客户条件
		 */
		if ("1".equals(hangUpDeduct)) {
			repaymentServerResult.setStream(stream);
			repaymentServerResult.setStatus("2");
			repaymentServerResult.setMsg("此客户在挂起中，暂时不能进行划扣");
			return repaymentServerResult;

		}

		if (stream != overdueNum) {
			repaymentServerResult.setStream(stream);
			repaymentServerResult.setStatus("2");
			repaymentServerResult.setMsg("还款期次与客户当前逾期期次不符，余额扣除失败");
			return repaymentServerResult;
		}

		if ("0".equals(delayFlag)) {
			repaymentServerResult.setStream(stream);
			repaymentServerResult.setStatus("2");
			repaymentServerResult.setMsg("此客户为正常客户，不能逾期月还");
			return repaymentServerResult;
		}

		if ("1".equals(settleFlag) && "0".equals(delayFlag)) {
			repaymentServerResult.setStream(stream);
			repaymentServerResult.setStatus("2");
			repaymentServerResult.setMsg("此客户已结清，不能发起还款");
			return repaymentServerResult;
		}

		/**
		 * 得到应该扣除的余额
		 */

		// 支出金额
		BigDecimal expendMoney = receiveMoney;
		if (overdueSubMoney != null && overdueSubMoney.compareTo(new BigDecimal(0)) > 0 && overdueSubStream == stream && overdueSubDate.getTime() > new Date().getTime()) {
			expendMoney = amt.subtract(overdueSubMoney);
		}

		if (bal.compareTo(expendMoney) < 0) {
			repaymentServerResult.setStream(stream);
			repaymentServerResult.setStatus("2");
			repaymentServerResult.setMsg("余额不足");
			return repaymentServerResult;
		}

		/**
		 * 保存支出记录
		 */
		Export export = new Export();
		export.setId(PrimaryKeyUtil.getPrimaryKey());
		export.setLoanId(loanId);
		export.setConNo(loanbal.getCardNo());
		export.setCastName(loanbal.getCastName());
		// 支出类型(1正常月还支出,2提前还款支出，3逾期月还支出)
		export.setExpendType("3");
		export.setExpendTypeVal("逾期月还支出");
		// 支出金额
		export.setExpendMoney(expendMoney);
		export.setExpendDate(new Date());
		// 减免金额
		export.setSubMoney(overdueSubMoney == null ? new BigDecimal(0) : overdueSubMoney);
		// 支出期次
		export.setExpendStream(stream);
		// 当期应还本金
		export.setReceiveCorpus(receiveCorpus);
		// 当期应还利息
		export.setReceiveShouldinte(receiveShouldinte);
		// 当期月还款额（应还本金+应还利息）
		export.setAmt(amt);
		// 当期应收违约金
		export.setReceivePenalty(receivePenalty);
		// 当期应收罚息
		export.setReceiveInterest(receiveInterest);
		// 当期应收违罚金（应收违约金+应收罚息）
		export.setReceivePenaltyInterest(receivePenaltyInterest);
		// 当期应收总额（违罚金+月还款额）
		export.setReceiveMoney(receiveMoney);
		export.setApplicationCastId(applicationCastId);
		export.setApplicationCastName(applicationCastName);
		export.setCreateTime(new Date());
		export.setUpdateDate(null);
		// 提前结清应还金额
		export.setReceiveAdvance(advanceShould);
		// 提前还清减免渠道服务费
		export.setChannelServiceFee(preretuamt);
		// 账户ID
		export.setCastId(custId);
		exportMapper.insertSelective(export);

		/**
		 * 保存还款记录
		 */
		Loanbook loanbook = new Loanbook();
		loanbook.setId(PrimaryKeyUtil.getPrimaryKey());
		loanbook.setLoanId(loanId);
		loanbook.setConNo(loanbal.getCardNo());
		loanbook.setCastName(loanbal.getCastName());
		// 应还日期
		loanbook.setRepayDate(loanbal.getRepayDate());
		// 应还期次
		loanbook.setStream(stream);
		// 当期应还本金
		loanbook.setReceiveCorpus(receiveCorpus);
		// 当期应还利息
		loanbook.setReceiveShouldinte(receiveShouldinte);
		// 当期月还款额（应还本金+应还利息）
		loanbook.setAmt(amt);
		// 当期应收违约金
		loanbook.setReceivePenalty(receivePenalty);
		// 当期应收罚息
		loanbook.setReceiveInterest(receiveInterest);
		// 当期应收违罚金（应收违约金+应收罚息）
		loanbook.setReceivePenaltyInterest(receivePenaltyInterest);
		// 当期应收总额（违罚金+月还款额）
		loanbook.setReceiveMoney(receiveMoney);
		// 提前结清应还金额
		loanbook.setReceiveAdvance(advanceShould);
		// 提前还清减免渠道服务费
		loanbook.setChannelServiceFee(preretuamt);
		// 逾期天数
		loanbook.setOverdueDays(overdueDays);
		// 减免金额
		loanbook.setSubMoney(overdueSubMoney == null ? new BigDecimal(0) : overdueSubMoney);
		// 扣除金额
		loanbook.setActualMoney(expendMoney);
		// 还款类型(1正常月还,2提前还款，3逾期月还)
		loanbook.setLoanbookType("3");

		loanbook.setLoanbookTypeVal("逾期月还");

		loanbook.setCreateTime(new Date());
		loanbook.setUpdateDate(null);

		loanbookMapper.insertSelective(loanbook);

		/**
		 * 扣除余额
		 */
		BigDecimal currentBal = bal.subtract(expendMoney);
		Map<String, Object> userCountparams = new HashMap<String, Object>();
		userCountparams.put("custId", custId);
		userCountparams.put("bal", currentBal);
		userCountMapper.updatebal(userCountparams);

		/**
		 * 查询是否下一期还有逾期月还减免 修改 逾期减免金额
		 */
		Map<String, Object> subParmas = new HashMap<String, Object>();
		subParmas.put("loanId", loanId);
		subParmas.put("subStream", stream + 1);

		Sub sub = subMapper.selectByLoanIdAndStream(subParmas);
		if (sub != null) {
			Map<String, Object> overdue = new HashMap<String, Object>();

			overdue.put("loanId", loanId);
			overdue.put("overdueSubMoney", sub.getSubSum());
			overdue.put("overdueSubDate", sub.getSubDate());
			overdue.put("overdueSubStream", sub.getSubStream());
			loanbalMapper.updateOverduelSub(overdue);
		} else {
			loanbalMapper.updateOverduelSubToNull(loanId);
		}

		/**
		 * 修改当前减免金额已使用
		 */
		if (overdueSubMoney != null) {
			Map<String, Object> subparam = new HashMap<String, Object>();
			subparam.put("loanId", loanId);
			subparam.put("subStream", stream);
			subparam.put("subStatus", 3);

			subMapper.updateSubStatusByLoanIdAndSubStream(subparam);
		}

		/**
		 * 结清本期逾期
		 */

		Map<String, Object> overParams = new HashMap<String, Object>();
		overParams.put("loanId", loanId);
		overParams.put("stream", stream);

		overdueRecordMapper.updateSettleByLoanIdAndStream(overParams);

		/**
		 * 查询最小逾期
		 */
		OverdueRecord nextOverdueRecord = overdueRecordMapper.selectMinByLoanId(loanId);
		if (nextOverdueRecord == null) {

			Map<String, Object> mSessionParams = new HashMap<String, Object>();

			mSessionParams.put("mSection", 0);
			mSessionParams.put("loanId", loanId);
			mSessionParams.put("delayFlag", "0");

			loanbalMapper.updateMSectionDelayFlagByLoanId(mSessionParams);

			Map<String, Object> customerOrLoanParams = new HashMap<String, Object>();
			customerOrLoanParams.put("loanId", loanId);
			customerOrLoanParams.put("customerOrLoan", "0");

			// loanbalMapper.updatecustomerOrLoanByloanId(customerOrLoanParams);

			// 修改外放失效
			outBoundMapper.updateOutBoundStatusToOne(loanId);

			Map<String, String> constatus = new HashMap<String, String>();
			constatus.put("applyId", applyId);
			constatus.put("status", "3");// 2结清，3未结清，4提前结清，5逾期
			sysContractMapper.updateConStatusByApplyId(constatus);

			if (stream == loanPeriod) {

				loanbalMapper.updateSettleFlagByLoanId(loanId);

				Map<String, String> constatus2 = new HashMap<String, String>();
				constatus2.put("applyId", applyId);
				constatus2.put("status", "2");// 2结清，3未结清，4提前结清，5逾期
				sysContractMapper.updateConStatusByApplyId(constatus2);

				String msg = "恭喜，您的" + loanbal.getProductName() + "借款已全部还清，您可以继续申请其他产品。";
				noticeServer.sendMsg(applyId, msg, 2); // 消息推送
			}

		} else {
			Integer mSession = (nextOverdueRecord.getOverdueDays() / 30) + 1;

			Map<String, Object> mSessionParams = new HashMap<String, Object>();

			mSessionParams.put("mSection", mSession);
			mSessionParams.put("loanId", loanId);
			mSessionParams.put("delayFlag", "1");

			loanbalMapper.updateMSectionDelayFlagByLoanId(mSessionParams);

			Map<String, String> constatus = new HashMap<String, String>();
			constatus.put("applyId", applyId);
			constatus.put("status", "5");// 2结清，3未结清，4提前结清，5逾期
			sysContractMapper.updateConStatusByApplyId(constatus);

		}

		repaymentServerResult.setDedutBal(expendMoney);
		repaymentServerResult.setStream(stream);
		repaymentServerResult.setStatus("1");
		repaymentServerResult.setMsg("本期逾期还款成功");
		return repaymentServerResult;
	}

	@Override
	public RepaymentServerResult saveAdvanceRepayment(String custId, String loanId, Integer stream, String applicationCastId, String applicationCastName) {

		RepaymentServerResult repaymentServerResult = new RepaymentServerResult();
		/**
		 * 查询账户表
		 */
		UserCount userCount = userCountMapper.selectByCustId(custId);

		// 账户余额
		BigDecimal bal = userCount.getBal();

		/**
		 * 查询loanbal
		 */
		Loanbal loanbal = loanbalMapper.selectByLoanId(loanId);
		String applyId = loanbal.getApplyId();

		// 提前还款减免金额
		BigDecimal advancedSubMoney = loanbal.getAdvancedSubMoney();
		// 提前还款减免日期
		Date advancedSubDate = loanbal.getAdvancedSubDate();
		// 提前还款减免期次
		Integer advancedSubStream = loanbal.getAdvancedSubStream();
		// loanbal当前期数
		Integer currentPeriod = loanbal.getCurrentPeriod();
		// 挂起标识
		String hangUpDeduct = loanbal.getHangUpDeduct();

		// 逾期标识
		String delayFlag = loanbal.getDelayFlag();

		// 最后一期标识
		String settleFlag = loanbal.getSettleFlag();
		// 贷款期次
		// Integer loanPeriod = loanbal.getLoanPeriod();
		// 线上线下标识
		String updownStatus = loanbal.getUpdownStatus();

		/**
		 * 判断是否满足提前还款客户条件
		 */
		if ("1".equals(hangUpDeduct)) {
			repaymentServerResult.setStream(stream);
			repaymentServerResult.setStatus("2");
			repaymentServerResult.setMsg("此客户在挂起中，暂时不能进行划扣");
			return repaymentServerResult;

		}

		if (stream != currentPeriod) {
			repaymentServerResult.setStream(stream);
			repaymentServerResult.setStatus("2");
			repaymentServerResult.setMsg("还款期次与客户当前所处期次不符，余额扣除失败");
			return repaymentServerResult;
		}

		if ("1".equals(delayFlag)) {
			repaymentServerResult.setStream(stream);
			repaymentServerResult.setStatus("2");
			repaymentServerResult.setMsg("此客户为逾期客户，不能提前");
			return repaymentServerResult;
		}

		if ("1".equals(settleFlag)) {
			repaymentServerResult.setStream(stream);
			repaymentServerResult.setStatus("2");
			repaymentServerResult.setMsg("此客户账单已到最后一期，不能提前月还");
			return repaymentServerResult;
		}
		/**
		 * 得到应该扣除的余额
		 */
		Map<String, Object> repaymentPlanparams = new HashMap<String, Object>();
		repaymentPlanparams.put("loanId", loanId);
		repaymentPlanparams.put("shouldTerm", stream);
		RepaymentPlan repaymentPlan = repaymentPlanMapper.selByLoanIdTerm(repaymentPlanparams);
		// 月还款额
		BigDecimal amt = repaymentPlan.getShouldAmt();
		// 提前还款应还
		BigDecimal advanceMoney = repaymentPlan.getAdvanceMoney();

		BigDecimal preretuamt = null;

		if ("1".equals(updownStatus)) {
			// 提前还款退还服务费hxb
			preretuamt = repaymentPlan.getPreretuamtHxb();
		} else if ("0".equals(updownStatus)) {
			// 提前还款退还服务费zx
			preretuamt = repaymentPlan.getPreretuamtChannel();
		}

		// 支出金额
		BigDecimal expendMoney = advanceMoney;
		if (advancedSubMoney != null && advancedSubMoney.compareTo(new BigDecimal(0)) > 0 && advancedSubStream == stream && advancedSubDate.getTime() > new Date().getTime()) {
			expendMoney = advanceMoney.subtract(advancedSubMoney);
		}

		if (bal.compareTo(expendMoney) < 0) {
			repaymentServerResult.setStream(stream);
			repaymentServerResult.setStatus("2");
			repaymentServerResult.setMsg("余额不足");
			return repaymentServerResult;
		}

		/**
		 * 保存支出记录
		 */
		Export export = new Export();
		export.setId(PrimaryKeyUtil.getPrimaryKey());
		export.setLoanId(loanId);
		export.setConNo(loanbal.getCardNo());
		export.setCastName(loanbal.getCastName());
		// 支出类型(1正常月还支出,2提前还款支出，3逾期月还支出)
		export.setExpendType("2");
		export.setExpendTypeVal("提前还款支出");
		// 支出金额
		export.setExpendMoney(expendMoney);
		export.setExpendDate(new Date());
		// 减免金额
		export.setSubMoney(advancedSubMoney == null ? new BigDecimal(0) : advancedSubMoney);
		// 支出期次
		export.setExpendStream(stream);
		// 当期应还本金
		export.setReceiveCorpus(repaymentPlan.getShouldCapi());
		// 当期应还利息
		export.setReceiveShouldinte(repaymentPlan.getShouldInte());
		// 当期月还款额（应还本金+应还利息）
		export.setAmt(amt);
		// 当期应收违约金
		export.setReceivePenalty(new BigDecimal(0));
		// 当期应收罚息
		export.setReceiveInterest(new BigDecimal(0));
		// 当期应收违罚金（应收违约金+应收罚息）
		export.setReceivePenaltyInterest(new BigDecimal(0));
		// 当期应收总额（违罚金+月还款额）
		export.setReceiveMoney(amt);
		export.setApplicationCastId(applicationCastId);
		export.setApplicationCastName(applicationCastName);
		export.setCreateTime(new Date());

		export.setUpdateDate(null);
		// 提前结清应还金额
		export.setReceiveAdvance(advanceMoney);
		// 提前还清减免渠道服务费
		export.setChannelServiceFee(preretuamt);
		// 账户ID
		export.setCastId(custId);
		exportMapper.insertSelective(export);

		/**
		 * 保存还款记录
		 */
		Loanbook loanbook = new Loanbook();
		loanbook.setId(PrimaryKeyUtil.getPrimaryKey());
		loanbook.setLoanId(loanId);
		loanbook.setConNo(loanbal.getCardNo());
		loanbook.setCastName(loanbal.getCastName());
		// 应还日期
		loanbook.setRepayDate(loanbal.getRepayDate());
		// 应还期次
		loanbook.setStream(stream);
		// 当期应还本金
		loanbook.setReceiveCorpus(repaymentPlan.getShouldCapi());
		// 当期应还利息
		loanbook.setReceiveShouldinte(repaymentPlan.getShouldInte());
		// 当期月还款额（应还本金+应还利息）
		loanbook.setAmt(amt);
		// 当期应收违约金
		loanbook.setReceivePenalty(new BigDecimal(0));
		// 当期应收罚息
		loanbook.setReceiveInterest(new BigDecimal(0));
		// 当期应收违罚金（应收违约金+应收罚息）
		loanbook.setReceivePenaltyInterest(new BigDecimal(0));
		// 当期应收总额（违罚金+月还款额）
		loanbook.setReceiveMoney(amt);
		// 提前结清应还金额
		loanbook.setReceiveAdvance(advanceMoney);
		// 提前还清减免渠道服务费
		loanbook.setChannelServiceFee(preretuamt);
		// 逾期天数
		loanbook.setOverdueDays(0);
		// 减免金额
		loanbook.setSubMoney(advancedSubMoney);
		// 扣除金额
		loanbook.setActualMoney(expendMoney);
		// 还款类型(1正常月还,2提前还款，3逾期月还)
		loanbook.setLoanbookType("2");

		loanbook.setLoanbookTypeVal("提前还款");

		loanbook.setCreateTime(new Date());
		loanbook.setUpdateDate(null);

		loanbookMapper.insertSelective(loanbook);

		/**
		 * 扣除余额，并且归0正常还款减免金额 日期 期次
		 */
		BigDecimal currentBal = bal.subtract(expendMoney);
		Map<String, Object> userCountparams = new HashMap<String, Object>();
		userCountparams.put("custId", custId);
		userCountparams.put("bal", currentBal);
		userCountMapper.updatebal(userCountparams);
		loanbalMapper.updateAdvanceSubToNull(loanId);

		/**
		 * 贷后逻辑表结清，(并修改合同表，以及APP方推送消息)
		 */

		loanbalMapper.updateSettleFlagByLoanId(loanId);

		Map<String, String> constatus = new HashMap<String, String>();
		constatus.put("applyId", applyId);
		constatus.put("status", "4");// 2结清，3未结清，4提前结清，5逾期
		sysContractMapper.updateConStatusByApplyId(constatus);

		String msg = "恭喜，您的" + loanbal.getProductName() + "借款已全部还清，您可以继续申请其他产品。";
		noticeServer.sendMsg(applyId, msg, 2); // 消息推送

		repaymentServerResult.setDedutBal(expendMoney);
		repaymentServerResult.setStream(stream);
		repaymentServerResult.setStatus("1");
		repaymentServerResult.setMsg("本期提前结清成功");
		return repaymentServerResult;
	}

}
