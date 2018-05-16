/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年4月28日 <br>
 * 描述：放款后推送给贷后的数据包实体
 */
public class ToAfterParam {

	/**
	 * 客户id
	 */
	private String castId;

	/**
	 * 客户姓名
	 */
	private String castName;

	/**
	 * 客户性别
	 */
	private String sex;

	/**
	 * 联系电话
	 */
	private String tel;

	/**
	 * 身份证号码
	 */
	private String cardNo;

	/**
	 * 客户来源
	 */
	private String castSource;

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
	 * 银行编码2
	 */
	private String bankCode2;
	/**
	 * 银行编码3
	 */
	private String bankCode3;

	/**
	 * 账户余额
	 */
	private BigDecimal bal;

	/**
	 * 进件编号
	 */
	private String loanId;

	/**
	 * 合同编号
	 */
	private String conNo;

	/**
	 * APPLY_ID
	 */
	private String applyId;

	/**
	 * 应还日期
	 */
	private Date repayDate;

	/**
	 * 当前期数
	 */
	private Integer currentPeriod;

	/**
	 * 贷款期数
	 */
	private Integer loanPeriod;

	/**
	 * 放款金额
	 */
	private BigDecimal loanMoney;

	/**
	 * 放款日期
	 */
	private Date loanDate;

	/**
	 * 线上线下标识
	 */
	private String updownStatus;

	/**
	 * POST类型
	 */
	private String posType;

	/**
	 * 产品名称
	 */
	private String productName;

	/**
	 * 产品别名
	 */
	private String productalias;

	/**
	 * 前线客服ID
	 */
	private String managerCastId;

	/**
	 * 前线客服
	 */
	private String managerCast;

	/**
	 * 销售营业部ID
	 */
	private String salesDeptment;

	/**
	 * 账单日
	 */
	private Integer statementDate;

	/**
	 * 合同金额
	 */
	private BigDecimal conMoney;

	/**
	 * 富友项目ID
	 */
	private String productId;

	public String getCastId() {
		return castId;
	}

	public void setCastId(String castId) {
		this.castId = castId;
	}

	public String getCastName() {
		return castName;
	}

	public void setCastName(String castName) {
		this.castName = castName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCastSource() {
		return castSource;
	}

	public void setCastSource(String castSource) {
		this.castSource = castSource;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankPhone() {
		return bankPhone;
	}

	public void setBankPhone(String bankPhone) {
		this.bankPhone = bankPhone;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public BigDecimal getBal() {
		return bal;
	}

	public void setBal(BigDecimal bal) {
		this.bal = bal;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getConNo() {
		return conNo;
	}

	public void setConNo(String conNo) {
		this.conNo = conNo;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	public Integer getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(Integer currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public Integer getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(Integer loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public BigDecimal getLoanMoney() {
		return loanMoney;
	}

	public void setLoanMoney(BigDecimal loanMoney) {
		this.loanMoney = loanMoney;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public String getUpdownStatus() {
		return updownStatus;
	}

	public void setUpdownStatus(String updownStatus) {
		this.updownStatus = updownStatus;
	}

	public String getPosType() {
		return posType;
	}

	public void setPosType(String posType) {
		this.posType = posType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductalias() {
		return productalias;
	}

	public void setProductalias(String productalias) {
		this.productalias = productalias;
	}

	public String getManagerCastId() {
		return managerCastId;
	}

	public void setManagerCastId(String managerCastId) {
		this.managerCastId = managerCastId;
	}

	public String getManagerCast() {
		return managerCast;
	}

	public void setManagerCast(String managerCast) {
		this.managerCast = managerCast;
	}

	public String getSalesDeptment() {
		return salesDeptment;
	}

	public void setSalesDeptment(String salesDeptment) {
		this.salesDeptment = salesDeptment;
	}

	public Integer getStatementDate() {
		return statementDate;
	}

	public void setStatementDate(Integer statementDate) {
		this.statementDate = statementDate;
	}

	public BigDecimal getConMoney() {
		return conMoney;
	}

	public void setConMoney(BigDecimal conMoney) {
		this.conMoney = conMoney;
	}

	public String getBankCode2() {
		return bankCode2;
	}

	public void setBankCode2(String bankCode2) {
		this.bankCode2 = bankCode2;
	}

	public String getBankCode3() {
		return bankCode3;
	}

	public void setBankCode3(String bankCode3) {
		this.bankCode3 = bankCode3;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
