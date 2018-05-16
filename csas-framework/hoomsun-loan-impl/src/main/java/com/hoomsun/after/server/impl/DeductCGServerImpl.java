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

import com.hoomsun.after.api.model.param.CGParam;
import com.hoomsun.after.api.model.table.Deduct;
import com.hoomsun.after.api.model.table.Loanbal;
import com.hoomsun.after.api.model.table.OverdueRecord;
import com.hoomsun.after.api.model.table.PublicSave;
import com.hoomsun.after.api.model.table.UserCount;
import com.hoomsun.after.api.model.vo.DeductServerResult;
import com.hoomsun.after.api.model.vo.RepaymentServerResult;
import com.hoomsun.after.api.server.DeductCGServer;
import com.hoomsun.after.api.server.RepaymentServer;
import com.hoomsun.after.dao.ApplyMapper;
import com.hoomsun.after.dao.DeductMapper;
import com.hoomsun.after.dao.LoanbalMapper;
import com.hoomsun.after.dao.OverdueRecordMapper;
import com.hoomsun.after.dao.PublicSaveMapper;
import com.hoomsun.after.dao.SwitchOutMapper;
import com.hoomsun.after.dao.UserCountMapper;
import com.hoomsun.common.util.PrimaryKeyUtil;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年4月28日 <br>
 * 描述：
 */
@Service("deductCGServer")
public class DeductCGServerImpl implements DeductCGServer {

	@Autowired
	private PublicSaveMapper publicSaveMapper;

	@Autowired
	private RepaymentServer repaymentServer;

	@Autowired
	private ApplyMapper applyMapper;

	@Autowired
	private LoanbalMapper loanbalMapper;

	@Autowired
	private OverdueRecordMapper overdueRecordMapper;

	@Autowired
	private UserCountMapper userCountMapper;

	@Autowired
	private DeductMapper deductMapper;
	@Autowired
	private SwitchOutMapper switchOutMapper;

	@Override
	public List<PublicSave> getDeductList(String rpynam, String rpyacc, String naryur, String corporateBankAccount, Date transactionDate) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("rpynam", rpynam);
		params.put("rpyacc", rpyacc);
		params.put("naryur", naryur);
		params.put("corporateBankAccount", corporateBankAccount);
		params.put("transactionDate", transactionDate);
		return publicSaveMapper.getDeductList(params);
	}

	@Override
	public DeductServerResult saveNomalDeduct(CGParam cGParam, String applicationCastId, String applicationCastName) {
		DeductServerResult deductServerResult = new DeductServerResult();

		String loanId = cGParam.getLoanId();

		Integer stream = cGParam.getStream();

		String corporateBankAccount = cGParam.getCorporateBankAccount();

		List<String> ids = cGParam.getId();

		/**
		 * 判断正常月还功能是否开启
		 */
		if ("1".equals(switchOutMapper.selectByPrimaryKey("nomdueDuct").getStatus())) {
			deductServerResult.setSuccess(false);
			deductServerResult.setMsg("网络异常！nomdueDuct...");
			return deductServerResult;
		}

		//
		Loanbal loanbal = loanbalMapper.selectByLoanId(loanId);
		// 客户账户Id
		String custId = loanbal.getCastId();

		// 查询账户表
		UserCount userCount = userCountMapper.selectByCustId(custId);

		// 挂起标识（划扣挂起）
		String hangUpDeduct = loanbal.getHangUpDeduct();
		if ("1".equals(hangUpDeduct)) {
			deductServerResult.setSuccess(false);
			deductServerResult.setMsg("此客户处于挂起状态，无法发起划扣，请稍后再试！");
			return deductServerResult;
		}

		// 当前期次
		Integer currentPeriod = loanbal.getCurrentPeriod();
		if (currentPeriod != stream) {
			deductServerResult.setSuccess(false);
			deductServerResult.setMsg("客户当前所处期次与，提交划扣期次不符，请核实后再试！");
			return deductServerResult;
		}

		/**
		 * 存公成功！ 认领存公金额修改账户余额
		 */

		List<PublicSave> publicSaves = publicSaveMapper.selectByPrimaryKeys(ids);

		// 存公总金额
		BigDecimal cgMoney = new BigDecimal(0);
		for (PublicSave publicSave : publicSaves) {
			cgMoney = cgMoney.add(publicSave.getTrsamtc());
		}

		for (PublicSave publicSave : publicSaves) {
			publicSave.setVerificationStatus("1");
			publicSave.setCastId(userCount.getCastId());
			publicSave.setCastName(userCount.getCastName());
			publicSave.setVerificationPeopleId(applicationCastId);
			publicSave.setVerificationPeopleName(applicationCastName);
			publicSave.setVerificationPeopleDate(new Date());
			publicSave.setLoanId(loanId);
			publicSave.setStream(stream);
			publicSaveMapper.updateByPrimaryKeySelective(publicSave);
		}

		// 线上线下标识
		String updownStatus = loanbal.getUpdownStatus();

		/**
		 * 修改账户余额
		 */
		Map<String, Object> userCountparams = new HashMap<String, Object>();
		userCountparams.put("custId", custId);
		userCountparams.put("bal", userCount.getBal().add(cgMoney));
		userCountMapper.updatebal(userCountparams);

		/**
		 * 保存划扣记录
		 */
		Deduct deduct = new Deduct();
		deduct.setId(PrimaryKeyUtil.getPrimaryKey());
		// 进件编号
		deduct.setLoanId(loanId);
		// 合同编号
		deduct.setConNo(loanbal.getConNo());
		// 客户姓名
		deduct.setCastName(userCount.getCastName());
		// 身份证号码
		deduct.setCardNo(userCount.getCardNo());
		// 银行名称
		deduct.setBank(userCount.getBank());
		// 银行预留手机号
		deduct.setBankPhone(userCount.getBankPhone());
		// 银行卡号
		deduct.setBankAccount(userCount.getBankAccount());
		// 银行编码
		deduct.setBankCode(userCount.getBankCode());
		// 划扣渠道（宝付，富友，存公等）
		deduct.setDeductChannel("0".equals(updownStatus) ? "线下存公" + corporateBankAccount : "线上存公" + corporateBankAccount);
		// 划扣金额
		deduct.setDeductMoney(cgMoney);
		// 划扣状态（成功0，失败1，待定2）
		deduct.setDeductState("0");
		// 划扣结果信息
		deduct.setDeductStateVal("成功");
		// 划扣结果CODE
		deduct.setDeductStateCode("");
		// 划扣手续费
		deduct.setDeductServerMoney(null);
		// 划扣时客户所处期次
		deduct.setDedutStream(loanbal.getCurrentPeriod());
		// 划扣类型 1 2 3 4
		deduct.setDedutType("1");
		// 划扣类型 值：正常月还，提前还款，逾期月还 余额充值
		deduct.setDedutTypeVal("正常月还");
		// 划扣时间
		deduct.setDedutDate(new Date());
		// 查证时间
		deduct.setCheckDate(null);
		// 订单号
		deduct.setOrderNo(null);
		// 划扣人员ID
		deduct.setApplicationCastId(applicationCastId);
		// 划扣人员Name
		deduct.setApplicationCastName(applicationCastName);
		// 创建日期
		deduct.setCreateTime(new Date());
		// 修改时间
		deduct.setUpdateDate(null);
		// 划扣前账户存在余额
		deduct.setDeductBal(userCount.getBal());
		// 客户ID
		deduct.setCastId(custId);
		deductMapper.insert(deduct);

		/**
		 * 调用正常月还
		 */

		RepaymentServerResult repaymentServerResult = repaymentServer.saveNomalRepayment(custId, loanId, stream, applicationCastId, applicationCastName);
		deductServerResult.setSuccess(true);
		deductServerResult.setMsg("存公金额：" + cgMoney + "已经处理");
		return deductServerResult;
	}

	@Override
	public DeductServerResult saveOverdueDeduct(CGParam cGParam, String applicationCastId, String applicationCastName) {

		DeductServerResult deductServerResult = new DeductServerResult();

		String loanId = cGParam.getLoanId();

		Integer stream = cGParam.getStream();

		String corporateBankAccount = cGParam.getCorporateBankAccount();

		List<String> ids = cGParam.getId();
		/**
		 * 判断逾期月还功能是否开启
		 */
		if ("1".equals(switchOutMapper.selectByPrimaryKey("overdueDuct").getStatus())) {
			deductServerResult.setSuccess(false);
			deductServerResult.setMsg("网络异常！overdueDuct...");
			return deductServerResult;
		}

		Loanbal loanbal = loanbalMapper.selectByLoanId(loanId);

		// 客户账户Id
		String custId = loanbal.getCastId();

		// 查询账户表
		UserCount userCount = userCountMapper.selectByCustId(custId);

		OverdueRecord overduerecord = overdueRecordMapper.selectMinByLoanId(loanId);

		// 挂起标识（，划扣挂起）
		String hangUpDeduct = loanbal.getHangUpDeduct();
		if ("1".equals(hangUpDeduct)) {
			deductServerResult.setSuccess(false);
			deductServerResult.setMsg("此客户处于挂起状态，无法发起划扣，请稍后再试！");
			return deductServerResult;
		}

		// 逾期最小期次
		Integer currentPeriod = overduerecord.getOverdueNum();
		if (currentPeriod != stream) {
			deductServerResult.setSuccess(false);
			deductServerResult.setMsg("客户当前所处期次与，提交划扣期次不符，请核实后再试！");
			return deductServerResult;
		}

		/**
		 * 存公成功！ 认领存公金额修改账户余额
		 */

		List<PublicSave> publicSaves = publicSaveMapper.selectByPrimaryKeys(ids);
		// 存公总金额
		BigDecimal cgMoney = new BigDecimal(0);
		for (PublicSave publicSave : publicSaves) {
			cgMoney = cgMoney.add(publicSave.getTrsamtc());
		}

		for (PublicSave publicSave : publicSaves) {
			publicSave.setVerificationStatus("1");
			publicSave.setCastId(userCount.getCastId());
			publicSave.setCastName(userCount.getCastName());
			publicSave.setVerificationPeopleId(applicationCastId);
			publicSave.setVerificationPeopleName(applicationCastName);
			publicSave.setVerificationPeopleDate(new Date());
			publicSave.setLoanId(loanId);
			publicSave.setStream(stream);
			publicSaveMapper.updateByPrimaryKeySelective(publicSave);
		}

		// 线上线下标识
		String updownStatus = loanbal.getUpdownStatus();

		/**
		 * 修改账户余额
		 */
		Map<String, Object> userCountparams = new HashMap<String, Object>();
		userCountparams.put("custId", custId);
		userCountparams.put("bal", userCount.getBal().add(cgMoney));
		userCountMapper.updatebal(userCountparams);

		/**
		 * 保存划扣记录
		 */
		Deduct deduct = new Deduct();
		deduct.setId(PrimaryKeyUtil.getPrimaryKey());
		// 进件编号
		deduct.setLoanId(loanId);
		// 合同编号
		deduct.setConNo(loanbal.getConNo());
		// 客户姓名
		deduct.setCastName(userCount.getCastName());
		// 身份证号码
		deduct.setCardNo(userCount.getCardNo());
		// 银行名称
		deduct.setBank(userCount.getBank());
		// 银行预留手机号
		deduct.setBankPhone(userCount.getBankPhone());
		// 银行卡号
		deduct.setBankAccount(userCount.getBankAccount());
		// 银行编码
		deduct.setBankCode(userCount.getBankCode());
		// 划扣渠道（宝付，富友，存公等）
		deduct.setDeductChannel("0".equals(updownStatus) ? "线下存公" + corporateBankAccount : "线上存公" + corporateBankAccount);
		// 划扣金额
		deduct.setDeductMoney(cgMoney);
		// 划扣状态（成功0，失败1，待定2）
		deduct.setDeductState("0");
		// 划扣结果信息
		deduct.setDeductStateVal("成功");
		// 划扣结果CODE
		deduct.setDeductStateCode("");
		// 划扣手续费
		deduct.setDeductServerMoney(null);
		// 划扣时客户所处期次
		deduct.setDedutStream(currentPeriod);
		// 划扣类型 1 2 3 4
		deduct.setDedutType("3");
		// 划扣类型 值：正常月还，提前还款，逾期月还 余额充值
		deduct.setDedutTypeVal("逾期月还");
		// 划扣时间
		deduct.setDedutDate(new Date());
		// 查证时间
		deduct.setCheckDate(null);
		// 订单号
		deduct.setOrderNo("");
		// 划扣人员ID
		deduct.setApplicationCastId(applicationCastId);
		// 划扣人员Name
		deduct.setApplicationCastName(applicationCastName);
		// 创建日期
		deduct.setCreateTime(new Date());
		// 修改时间
		deduct.setUpdateDate(null);
		// 划扣前账户存在余额
		deduct.setDeductBal(userCount.getBal());
		// 客户ID
		deduct.setCastId(custId);
		deductMapper.insert(deduct);

		RepaymentServerResult repaymentServerResult = repaymentServer.saveOverdueRepayment(custId, loanId, stream, applicationCastId, applicationCastName);
		deductServerResult.setSuccess(true);
		deductServerResult.setMsg("存公金额：" + cgMoney + "已经处理");
		return deductServerResult;
	}

	@Override
	public DeductServerResult saveAdvanceDeduct(CGParam cGParam, String applicationCastId, String applicationCastName) {

		DeductServerResult deductServerResult = new DeductServerResult();

		String loanId = cGParam.getLoanId();

		Integer stream = cGParam.getStream();

		String corporateBankAccount = cGParam.getCorporateBankAccount();

		List<String> ids = cGParam.getId();

		/**
		 * 判断正常月还功能是否开启
		 */
		if ("1".equals(switchOutMapper.selectByPrimaryKey("advanceDuct").getStatus())) {
			deductServerResult.setSuccess(false);
			deductServerResult.setMsg("网络异常！nomdueDuct...");
			return deductServerResult;
		}

		Loanbal loanbal = loanbalMapper.selectByLoanId(loanId);
		// 客户账户Id
		String custId = loanbal.getCastId();

		// 查询账户表
		UserCount userCount = userCountMapper.selectByCustId(custId);

		// 挂起标识（1逾期跑批挂起,2，划扣挂起）
		String hangUpDeduct = loanbal.getHangUpDeduct();
		if ("1".equals(hangUpDeduct)) {
			deductServerResult.setSuccess(false);
			deductServerResult.setMsg("此客户处于挂起状态，无法发起划扣，请稍后再试！");
			return deductServerResult;
		}

		// 当前期次
		Integer currentPeriod = loanbal.getCurrentPeriod();
		if (currentPeriod != stream) {
			deductServerResult.setSuccess(false);
			deductServerResult.setMsg("客户当前所处期次与，提交划扣期次不符，请核实后再试！");
			return deductServerResult;
		}

		/**
		 * 存公成功！ 认领存公金额修改账户余额
		 */

		List<PublicSave> publicSaves = publicSaveMapper.selectByPrimaryKeys(ids);

		// 存公总金额
		BigDecimal cgMoney = new BigDecimal(0);
		for (PublicSave publicSave : publicSaves) {
			cgMoney = cgMoney.add(publicSave.getTrsamtc());
		}

		for (PublicSave publicSave : publicSaves) {
			publicSave.setVerificationStatus("1");
			publicSave.setCastId(userCount.getCastId());
			publicSave.setCastName(userCount.getCastName());
			publicSave.setVerificationPeopleId(applicationCastId);
			publicSave.setVerificationPeopleName(applicationCastName);
			publicSave.setVerificationPeopleDate(new Date());
			publicSave.setLoanId(loanId);
			publicSave.setStream(stream);
			publicSaveMapper.updateByPrimaryKeySelective(publicSave);
		}

		// 线上线下标识
		String updownStatus = loanbal.getUpdownStatus();

		/**
		 * 保存划扣记录
		 */

		Map<String, Object> userCountparams = new HashMap<String, Object>();

		userCountparams.put("custId", custId);
		userCountparams.put("bal", userCount.getBal().add(cgMoney));
		userCountMapper.updatebal(userCountparams);

		Deduct deduct = new Deduct();

		deduct.setId(PrimaryKeyUtil.getPrimaryKey());
		// 进件编号
		deduct.setLoanId(loanId);
		// 合同编号
		deduct.setConNo(loanbal.getConNo());
		// 客户姓名
		deduct.setCastName(userCount.getCastName());
		// 身份证号码
		deduct.setCardNo(userCount.getCardNo());
		// 银行名称
		deduct.setBank(userCount.getBank());
		// 银行预留手机号
		deduct.setBankPhone(userCount.getBankPhone());
		// 银行卡号
		deduct.setBankAccount(userCount.getBankAccount());
		// 银行编码
		deduct.setBankCode(userCount.getBankCode());
		// 划扣渠道（宝付，富友，存公等）
		deduct.setDeductChannel("0".equals(updownStatus) ? "线下存公" + corporateBankAccount : "线上存公" + corporateBankAccount);
		// 划扣金额
		deduct.setDeductMoney(cgMoney);
		// 划扣状态（成功0，失败1，待定2）
		deduct.setDeductState("0");
		// 划扣结果信息
		deduct.setDeductStateVal("成功");
		// 划扣结果CODE
		deduct.setDeductStateCode("");
		// 划扣手续费
		deduct.setDeductServerMoney(null);
		// 划扣时客户所处期次
		deduct.setDedutStream(loanbal.getCurrentPeriod());
		// 划扣类型 1 2 3 4
		deduct.setDedutType("2");
		// 划扣类型 值：正常月还，提前还款，逾期月还 余额充值
		deduct.setDedutTypeVal("提前还款");
		// 划扣时间
		deduct.setDedutDate(new Date());
		// 查证时间
		deduct.setCheckDate(null);
		// 订单号
		deduct.setOrderNo("");
		// 划扣人员ID
		deduct.setApplicationCastId(applicationCastId);
		// 划扣人员Name
		deduct.setApplicationCastName(applicationCastName);
		// 创建日期
		deduct.setCreateTime(new Date());
		// 修改时间
		deduct.setUpdateDate(null);
		// 划扣前账户存在余额
		deduct.setDeductBal(userCount.getBal());
		// 客户ID
		deduct.setCastId(custId);
		deductMapper.insert(deduct);

		RepaymentServerResult repaymentServerResult = repaymentServer.saveAdvanceRepayment(custId, loanId, stream, applicationCastId, applicationCastName);
		deductServerResult.setSuccess(true);
		deductServerResult.setMsg("存公金额：" + cgMoney + "已经处理");
		return deductServerResult;
	}

}
