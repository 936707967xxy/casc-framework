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

import com.hoomsun.after.api.model.param.FYdeductModel;
import com.hoomsun.after.api.model.table.Deduct;
import com.hoomsun.after.api.model.table.Loanbal;
import com.hoomsun.after.api.model.table.OverdueRecord;
import com.hoomsun.after.api.model.table.UserCount;
import com.hoomsun.after.api.model.vo.DeductResult;
import com.hoomsun.after.api.model.vo.DeductServerResult;
import com.hoomsun.after.api.model.vo.RepaymentServerResult;
import com.hoomsun.after.api.server.DeductFUServer;
import com.hoomsun.after.api.server.RepaymentServer;
import com.hoomsun.after.api.util.FYutil.FYdeductUtil;
import com.hoomsun.after.dao.ApplyMapper;
import com.hoomsun.after.dao.DeductMapper;
import com.hoomsun.after.dao.LoanbalMapper;
import com.hoomsun.after.dao.OverdueRecordMapper;
import com.hoomsun.after.dao.SwitchOutMapper;
import com.hoomsun.after.dao.UserCountMapper;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年5月4日 <br>
 * 描述：
 */
@Service("deductFUServer")
public class DeductFUServerImpl implements DeductFUServer {

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
	public DeductServerResult saveNomalDeduct(String loanId, Integer stream, BigDecimal deductMoney, String path, String applicationCastId, String applicationCastName) {
		DeductServerResult deductServerResult = new DeductServerResult();
		/**
		 * 判断正常月还功能是否开启
		 */
		if ("1".equals(switchOutMapper.selectByPrimaryKey("nomdueDuct").getStatus())) {
			deductServerResult.setSuccess(false);
			deductServerResult.setMsg("网络异常！nomdueDuct...");
			return deductServerResult;
		}

		/**
		 * 判断传入的划扣金额不能小于100元
		 */

		if (deductMoney == null || deductMoney.compareTo(new BigDecimal(100)) < 0) {
			deductServerResult.setSuccess(false);
			deductServerResult.setMsg("单次划扣金额不能小于100元");
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

		// 线上线下标识
		String updownStatus = loanbal.getUpdownStatus();

		/**
		 * 根据线上线下标识选择不同的划扣通道，划扣成功！修改账户余额
		 */

		FYdeductModel fYdeduct = new FYdeductModel();

		fYdeduct.setAccntno(userCount.getBankAccount());// 账号
		fYdeduct.setAccntnm(userCount.getBank());// 账户名称
		fYdeduct.setAmt(deductMoney);// 划扣金额
		fYdeduct.setMobile(userCount.getBankPhone());// 手机号码
		fYdeduct.setCertno(userCount.getCardNo());// 身份证号
		fYdeduct.setBankno(userCount.getBankCode2());// 开户行代码
		fYdeduct.setVerifyCode(null);// 短信验证码
		fYdeduct.setPrijectid(loanbal.getProductId());// 项目ID
		fYdeduct.setPath(path);// path

		if ("0".equals(updownStatus)) {
			// 线上线下标识
			fYdeduct.setUpdownStatus("0");

		} else if ("1".equals(updownStatus)) {
			// 线上线下标识
			fYdeduct.setUpdownStatus("1");
		} else {
			deductServerResult.setSuccess(false);
			deductServerResult.setMsg("无法确认此客户划扣通道标识，请联系相关技术人员！");
			return deductServerResult;
		}

		DeductResult deductResult = FYdeductUtil.FYdeduct(fYdeduct);
		// 划扣状态(0000 成功，1111 失败,2222查证)
		String deductStatus = deductResult.getDeductStatus();
		String deductstatus_01;
		// 订单号
		String transId = deductResult.getTransId();
		// 划扣时间
		Date deductDate = deductResult.getDeductDate();
		// 返回码
		String respCode = deductResult.getRespCode();
		// 返回信息
		String respMsg = deductResult.getRespMsg();
		// 划扣金额
		BigDecimal bfdeductMoney = deductResult.getDeductMoney();
		// 划扣类型
		String additionalInfo = deductResult.getAdditionalInfo();
		// 划扣手续费
		BigDecimal feesMoney = deductResult.getFeesMoney();
		// 富友查证code
		String fuczCode = deductResult.getFyCheck();
		/**
		 * 保存划扣记录
		 */
		if ("0000".equals(deductStatus)) {
			// 划扣成功
			deductstatus_01 = "0";
			Map<String, Object> userCountparams = new HashMap<String, Object>();

			userCountparams.put("custId", custId);
			userCountparams.put("bal", userCount.getBal().add(bfdeductMoney));
			userCountMapper.updatebal(userCountparams);

		} else if ("1111".equals(deductStatus)) {
			// 划扣失败
			deductstatus_01 = "1";

		} else if ("2222".equals(deductStatus)) {
			// 划扣结果待定
			deductstatus_01 = "2";
			// 划扣挂起
			Map<String, Object> balparams = new HashMap<String, Object>();
			balparams.put("loanId", loanId);
			balparams.put("hangUpDeduct", "1");
			balparams.put("hangUpDeductDate", new Date());

			loanbalMapper.updateHangUpDeductByLoanId(balparams);

		} else {
			deductServerResult.setSuccess(false);
			deductServerResult.setMsg("划扣结果异常！");
			return deductServerResult;
		}
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
		// 划扣渠道（线上/线下宝付，线上/线下富友，存公等）
		deduct.setDeductChannel(additionalInfo);
		// 划扣金额
		deduct.setDeductMoney(bfdeductMoney);
		// 划扣状态（成功0，失败1，待定2）
		deduct.setDeductState(deductstatus_01);
		// 划扣结果信息
		deduct.setDeductStateVal(respMsg);
		// 划扣结果CODE
		deduct.setDeductStateCode(respCode);
		// 富友查证code
		deduct.setDeductStateCzCode(fuczCode);
		// 划扣手续费
		deduct.setDeductServerMoney(feesMoney);
		// 划扣时客户所处期次
		deduct.setDedutStream(loanbal.getCurrentPeriod());
		// 划扣类型 1 2 3 4
		deduct.setDedutType("1");
		// 划扣类型 值：正常月还，提前还款，逾期月还 余额充值
		deduct.setDedutTypeVal("正常月还");
		// 划扣时间
		deduct.setDedutDate(deductDate);
		// 查证时间
		deduct.setCheckDate(null);
		// 订单号
		deduct.setOrderNo(transId);
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
		deductServerResult.setSuccess("0".equals(deductstatus_01));
		deductServerResult.setMsg(respMsg + ",划扣金额：" + deductMoney);
		return deductServerResult;
	}

	@Override
	public DeductServerResult saveOverdueDeduct(String loanId, Integer stream, BigDecimal deductMoney, String path, String applicationCastId, String applicationCastName) {

		DeductServerResult deductServerResult = new DeductServerResult();

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

		// 挂起标识（划扣挂起）
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

		// 线上线下标识
		String updownStatus = loanbal.getUpdownStatus();

		/**
		 * 根据线上线下标识选择不同的划扣通道，划扣成功！修改账户余额
		 */

		FYdeductModel fYdeduct = new FYdeductModel();

		fYdeduct.setAccntno(userCount.getBankAccount());// 账号
		fYdeduct.setAccntnm(userCount.getBank());// 账户名称
		fYdeduct.setAmt(deductMoney);// 划扣金额
		fYdeduct.setMobile(userCount.getBankPhone());// 手机号码
		fYdeduct.setCertno(userCount.getCardNo());// 身份证号
		fYdeduct.setBankno(userCount.getBankCode2());// 开户行代码
		fYdeduct.setVerifyCode(null);// 短信验证码
		fYdeduct.setPrijectid(loanbal.getProductId());// 项目ID
		fYdeduct.setPath(path);// path

		if ("0".equals(updownStatus)) {
			// 线上线下标识
			fYdeduct.setUpdownStatus("0");

		} else if ("1".equals(updownStatus)) {
			// 线上线下标识
			fYdeduct.setUpdownStatus("1");
		} else {
			deductServerResult.setSuccess(false);
			deductServerResult.setMsg("无法确认此客户划扣通道标识，请联系相关技术人员！");
			return deductServerResult;
		}

		DeductResult deductResult = FYdeductUtil.FYdeduct(fYdeduct);
		// 划扣状态(0000 成功，1111 失败,2222查证)
		String deductStatus = deductResult.getDeductStatus();
		String deductstatus_01;
		// 订单号
		String transId = deductResult.getTransId();
		// 划扣时间
		Date deductDate = deductResult.getDeductDate();
		// 返回码
		String respCode = deductResult.getRespCode();
		// 返回信息
		String respMsg = deductResult.getRespMsg();
		// 划扣金额
		BigDecimal bfdeductMoney = deductResult.getDeductMoney();
		// 划扣类型
		String additionalInfo = deductResult.getAdditionalInfo();
		// 划扣手续费
		BigDecimal feesMoney = deductResult.getFeesMoney();
		// 富友查证code
		String fuczCode = deductResult.getFyCheck();
		/**
		 * 保存划扣记录
		 */
		if ("0000".equals(deductStatus)) {
			// 划扣成功
			deductstatus_01 = "0";
			Map<String, Object> userCountparams = new HashMap<String, Object>();

			userCountparams.put("custId", custId);
			userCountparams.put("bal", userCount.getBal().add(bfdeductMoney));
			userCountMapper.updatebal(userCountparams);

		} else if ("1111".equals(deductStatus)) {
			// 划扣失败
			deductstatus_01 = "1";

		} else if ("2222".equals(deductStatus)) {
			// 划扣结果待定
			deductstatus_01 = "2";
			// 逾期挂起
			Map<String, Object> balparams = new HashMap<String, Object>();
			balparams.put("loanId", loanId);
			balparams.put("hangUp", "1");
			balparams.put("hangUpDate", new Date());
			loanbalMapper.updateHangUpByLoanId(balparams);

			// 划扣挂起
			Map<String, Object> balparams2 = new HashMap<String, Object>();
			balparams2.put("loanId", loanId);
			balparams2.put("hangUpDeduct", "1");
			balparams2.put("hangUpDeductDate", new Date());

			loanbalMapper.updateHangUpDeductByLoanId(balparams2);

		} else {
			deductServerResult.setSuccess(false);
			deductServerResult.setMsg("划扣结果异常！");
			return deductServerResult;
		}
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
		// 划扣渠道（线上/线下宝付，线上/线下富友，存公等）
		deduct.setDeductChannel(additionalInfo);
		// 划扣金额
		deduct.setDeductMoney(bfdeductMoney);
		// 划扣状态（成功0，失败1，待定2）
		deduct.setDeductState(deductstatus_01);
		// 划扣结果信息
		deduct.setDeductStateVal(respMsg);
		// 划扣结果CODE
		deduct.setDeductStateCode(respCode);
		// 富友查证code
		deduct.setDeductStateCzCode(fuczCode);
		// 划扣手续费
		deduct.setDeductServerMoney(feesMoney);
		// 划扣时客户所处期次
		deduct.setDedutStream(currentPeriod);
		// 划扣类型 1 2 3 4
		deduct.setDedutType("3");
		// 划扣类型 值：正常月还，提前还款，逾期月还 余额充值
		deduct.setDedutTypeVal("逾期月还");
		// 划扣时间
		deduct.setDedutDate(deductDate);
		// 查证时间
		deduct.setCheckDate(null);
		// 订单号
		deduct.setOrderNo(transId);
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
		deductServerResult.setSuccess("0".equals(deductstatus_01));
		deductServerResult.setMsg(respMsg + ",划扣金额：" + deductMoney);

		return deductServerResult;
	}

	@Override
	public DeductServerResult saveAdvanceDeduct(String loanId, Integer stream, BigDecimal deductMoney, String path, String applicationCastId, String applicationCastName) {

		DeductServerResult deductServerResult = new DeductServerResult();

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

		// 线上线下标识
		String updownStatus = loanbal.getUpdownStatus();
		/**
		 * 根据线上线下标识选择不同的划扣通道，划扣成功！修改账户余额
		 */

		FYdeductModel fYdeduct = new FYdeductModel();

		fYdeduct.setAccntno(userCount.getBankAccount());// 账号
		fYdeduct.setAccntnm(userCount.getBank());// 账户名称
		fYdeduct.setAmt(deductMoney);// 划扣金额
		fYdeduct.setMobile(userCount.getBankPhone());// 手机号码
		fYdeduct.setCertno(userCount.getCardNo());// 身份证号
		fYdeduct.setBankno(userCount.getBankCode2());// 开户行代码
		fYdeduct.setVerifyCode(null);// 短信验证码
		fYdeduct.setPrijectid(loanbal.getProductId());// 项目ID
		fYdeduct.setPath(path);// path

		if ("0".equals(updownStatus)) {
			// 线上线下标识
			fYdeduct.setUpdownStatus("0");

		} else if ("1".equals(updownStatus)) {
			// 线上线下标识
			fYdeduct.setUpdownStatus("1");
		} else {
			deductServerResult.setSuccess(false);
			deductServerResult.setMsg("无法确认此客户划扣通道标识，请联系相关技术人员！");
			return deductServerResult;
		}

		DeductResult deductResult = FYdeductUtil.FYdeduct(fYdeduct);
		// 划扣状态(0000 成功，1111 失败,2222查证)
		String deductStatus = deductResult.getDeductStatus();
		String deductstatus_01;
		// 订单号
		String transId = deductResult.getTransId();
		// 划扣时间
		Date deductDate = deductResult.getDeductDate();
		// 返回码
		String respCode = deductResult.getRespCode();
		// 返回信息
		String respMsg = deductResult.getRespMsg();
		// 划扣金额
		BigDecimal bfdeductMoney = deductResult.getDeductMoney();
		// 划扣类型
		String additionalInfo = deductResult.getAdditionalInfo();
		// 划扣手续费
		BigDecimal feesMoney = deductResult.getFeesMoney();
		// 富友查证code
		String fuczCode = deductResult.getFyCheck();
		/**
		 * 保存划扣记录
		 */
		if ("0000".equals(deductStatus)) {
			// 划扣成功
			deductstatus_01 = "0";
			Map<String, Object> userCountparams = new HashMap<String, Object>();

			userCountparams.put("custId", custId);
			userCountparams.put("bal", userCount.getBal().add(bfdeductMoney));
			userCountMapper.updatebal(userCountparams);

		} else if ("1111".equals(deductStatus)) {
			// 划扣失败
			deductstatus_01 = "1";

		} else if ("2222".equals(deductStatus)) {
			// 划扣结果待定
			deductstatus_01 = "2";

			// 划扣挂起
			Map<String, Object> balparams = new HashMap<String, Object>();
			balparams.put("loanId", loanId);
			balparams.put("hangUpDeduct", "1");
			balparams.put("hangUpDeductDate", new Date());

			loanbalMapper.updateHangUpDeductByLoanId(balparams);
		} else {
			deductServerResult.setSuccess(false);
			deductServerResult.setMsg("划扣结果异常！");
			return deductServerResult;
		}
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
		// 划扣渠道（线上/线下宝付，线上/线下富友，存公等）
		deduct.setDeductChannel(additionalInfo);
		// 划扣金额
		deduct.setDeductMoney(bfdeductMoney);
		// 划扣状态（成功0，失败1，待定2）
		deduct.setDeductState(deductstatus_01);
		// 划扣结果信息
		deduct.setDeductStateVal(respMsg);
		// 划扣结果CODE
		deduct.setDeductStateCode(respCode);
		// 富友查证code
		deduct.setDeductStateCzCode(fuczCode);
		// 划扣手续费
		deduct.setDeductServerMoney(feesMoney);
		// 划扣时客户所处期次
		deduct.setDedutStream(loanbal.getCurrentPeriod());
		// 划扣类型 1 2 3 4
		deduct.setDedutType("2");
		// 划扣类型 值：正常月还，提前还款，逾期月还 余额充值
		deduct.setDedutTypeVal("提前还款");
		// 划扣时间
		deduct.setDedutDate(deductDate);
		// 查证时间
		deduct.setCheckDate(null);
		// 订单号
		deduct.setOrderNo(transId);
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
		deductServerResult.setSuccess("0".equals(deductstatus_01));
		deductServerResult.setMsg(respMsg + ",划扣金额：" + deductMoney);
		return deductServerResult;
	}

}
