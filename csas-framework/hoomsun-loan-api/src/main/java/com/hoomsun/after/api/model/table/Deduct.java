package com.hoomsun.after.api.model.table;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 作者：屈楠 <br>
 * 创建时间：2018年2月27日 <br>
 * 描述：HS_AFTER_DEDUCT贷后客户划扣记录表
 *
 */
public class Deduct {
	private String id;

	/**
	 * 进件编号
	 */
	private String loanId;

	/**
	 * 合同编号
	 */
	private String conNo;

	/**
	 * 客户姓名
	 */
	private String castName;

	/**
	 * 身份证号码
	 */
	private String cardNo;

	/**
	 * 银行名称
	 */
	private String bank;

	/**
	 * 银行预留手机号
	 */
	private String bankPhone;

	/**
	 * 银行卡号
	 */
	private String bankAccount;

	/**
	 * 银行编码
	 */
	private String bankCode;

	/**
	 * 划扣渠道（线下宝付，线下富友，线下存公，线上宝付，线上富友，线上存公等）
	 */
	private String deductChannel;

	/**
	 * 划扣金额
	 */
	private BigDecimal deductMoney;

	/**
	 * 划扣状态（成功0，失败1，待定2）
	 */
	private String deductState;

	/**
	 * 划扣结果信息
	 */
	private String deductStateVal;

	/**
	 * 划扣结果CODE
	 */
	private String deductStateCode;

	/**
	 * 划扣结果富友CODE
	 */
	private String deductStateCzCode;

	/**
	 * 划扣手续费
	 */
	private BigDecimal deductServerMoney;

	/**
	 * 划扣时客户所处期次
	 */
	private Integer dedutStream;

	/**
	 * 划扣类型 1 2 3 4
	 */
	private String dedutType;

	/**
	 * 划扣类型 值：正常月还，提前还款，逾期月还 余额充值
	 */
	private String dedutTypeVal;

	/**
	 * 划扣时间
	 */
	private Date dedutDate;

	/**
	 * 查证时间
	 */
	private Date checkDate;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 划扣人员ID
	 */
	private String applicationCastId;

	/**
	 * 划扣人员Name
	 */
	private String applicationCastName;

	/**
	 * 创建日期
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date updateDate;

	/**
	 * 划扣前账户存在余额
	 */
	private BigDecimal deductBal;

	/**
	 * 线上线下标识
	 */
	private String updownStatus;

	/**
	 * 客户ID
	 */
	private String castId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId == null ? null : loanId.trim();
	}

	public String getConNo() {
		return conNo;
	}

	public void setConNo(String conNo) {
		this.conNo = conNo == null ? null : conNo.trim();
	}

	public String getCastName() {
		return castName;
	}

	public void setCastName(String castName) {
		this.castName = castName == null ? null : castName.trim();
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo == null ? null : cardNo.trim();
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank == null ? null : bank.trim();
	}

	public String getBankPhone() {
		return bankPhone;
	}

	public void setBankPhone(String bankPhone) {
		this.bankPhone = bankPhone == null ? null : bankPhone.trim();
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount == null ? null : bankAccount.trim();
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode == null ? null : bankCode.trim();
	}

	public String getDeductChannel() {
		return deductChannel;
	}

	public void setDeductChannel(String deductChannel) {
		this.deductChannel = deductChannel == null ? null : deductChannel.trim();
	}

	public BigDecimal getDeductMoney() {
		return deductMoney;
	}

	public void setDeductMoney(BigDecimal deductMoney) {
		this.deductMoney = deductMoney;
	}

	public String getDeductState() {
		return deductState;
	}

	public void setDeductState(String deductState) {
		this.deductState = deductState == null ? null : deductState.trim();
	}

	public String getDeductStateVal() {
		return deductStateVal;
	}

	public void setDeductStateVal(String deductStateVal) {
		this.deductStateVal = deductStateVal == null ? null : deductStateVal.trim();
	}

	public String getDeductStateCode() {
		return deductStateCode;
	}

	public void setDeductStateCode(String deductStateCode) {
		this.deductStateCode = deductStateCode == null ? null : deductStateCode.trim();
	}

	public BigDecimal getDeductServerMoney() {
		return deductServerMoney;
	}

	public void setDeductServerMoney(BigDecimal deductServerMoney) {
		this.deductServerMoney = deductServerMoney;
	}

	public Integer getDedutStream() {
		return dedutStream;
	}

	public void setDedutStream(Integer dedutStream) {
		this.dedutStream = dedutStream;
	}

	public String getDedutType() {
		return dedutType;
	}

	public void setDedutType(String dedutType) {
		this.dedutType = dedutType == null ? null : dedutType.trim();
	}

	public String getDedutTypeVal() {
		return dedutTypeVal;
	}

	public void setDedutTypeVal(String dedutTypeVal) {
		this.dedutTypeVal = dedutTypeVal == null ? null : dedutTypeVal.trim();
	}

	public Date getDedutDate() {
		return dedutDate;
	}

	public void setDedutDate(Date dedutDate) {
		this.dedutDate = dedutDate;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	public String getApplicationCastId() {
		return applicationCastId;
	}

	public void setApplicationCastId(String applicationCastId) {
		this.applicationCastId = applicationCastId == null ? null : applicationCastId.trim();
	}

	public String getApplicationCastName() {
		return applicationCastName;
	}

	public void setApplicationCastName(String applicationCastName) {
		this.applicationCastName = applicationCastName == null ? null : applicationCastName.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public BigDecimal getDeductBal() {
		return deductBal;
	}

	public void setDeductBal(BigDecimal deductBal) {
		this.deductBal = deductBal;
	}

	public String getUpdownStatus() {
		return updownStatus;
	}

	public void setUpdownStatus(String updownStatus) {
		this.updownStatus = updownStatus;
	}

	public String getCastId() {
		return castId;
	}

	public void setCastId(String castId) {
		this.castId = castId;
	}

	public String getDeductStateCzCode() {
		return deductStateCzCode;
	}

	public void setDeductStateCzCode(String deductStateCzCode) {
		this.deductStateCzCode = deductStateCzCode;
	}
	
	

}