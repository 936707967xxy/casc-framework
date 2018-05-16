/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.hxb;

import java.math.BigDecimal;

/**
 * 作者：zwLiu <br>
 * 创建时间：2018年5月3日 <br>
 * 描述：红小宝代偿请求传参
 */
public class BadRepayParam {
	/**
	 * 至信借款的唯一编号
	 */
	private String loanRefId;
	/**
	 * 期序(提前还款时为空)
	 */
	private Integer periodNumber;
	/**
	 * 还款类型：IN_REPAY(提前还清)，COMMON_REPAY(正常还款)
	 */
	private String repaidType;
	/**
	 * 是否逾期,是:1, 否:0
	 */
	private String overdueFlag;
	/**
	 * 借款端应还款时间
	 */
	private String repayTimePlan;
	/**
	 * 借款端至信方实际还款时间
	 */
	private String repayTimeActual;
	/**
	 * 借款端本息(本金+利息), 仅记录, 红小宝不使用此金额
	 */
	private BigDecimal amount;
	/**
	 * 借款端本金, 仅记录, 红小宝不使用此金额
	 */
	private BigDecimal principal;
	/**
	 * 借款端利息, 仅记录, 红小宝不使用此金额
	 */
	private BigDecimal interest;
	/**
	 * 逾期罚息（只有在逾期还款或还代偿款时需传入，减免后, 仅记录, 红小宝不使用此金额）
	 */
	private BigDecimal overdueInterestFee;
	/**
	 * 贷后服务费（只有在逾期还款或还代偿款时需传入，减免后, 仅记录, 红小宝不使用此金额）
	 */
	private BigDecimal overdueServiceFee;
	/**
	 * 贷后管理费（指逾期违约金，只有在逾期还款或还代偿款时需传入，减免后, 仅记录, 红小宝不使用此金额）
	 */
	private BigDecimal overdueMgmtFee;
	
	
	public String getLoanRefId() {
		return loanRefId;
	}
	public void setLoanRefId(String loanRefId) {
		this.loanRefId = loanRefId;
	}
	public Integer getPeriodNumber() {
		return periodNumber;
	}
	public void setPeriodNumber(Integer periodNumber) {
		this.periodNumber = periodNumber;
	}
	public String getRepaidType() {
		return repaidType;
	}
	public void setRepaidType(String repaidType) {
		this.repaidType = repaidType;
	}
	public String getOverdueFlag() {
		return overdueFlag;
	}
	public void setOverdueFlag(String overdueFlag) {
		this.overdueFlag = overdueFlag;
	}
	public String getRepayTimePlan() {
		return repayTimePlan;
	}
	public void setRepayTimePlan(String repayTimePlan) {
		this.repayTimePlan = repayTimePlan;
	}
	public String getRepayTimeActual() {
		return repayTimeActual;
	}
	public void setRepayTimeActual(String repayTimeActual) {
		this.repayTimeActual = repayTimeActual;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getPrincipal() {
		return principal;
	}
	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}
	public BigDecimal getInterest() {
		return interest;
	}
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	public BigDecimal getOverdueInterestFee() {
		return overdueInterestFee;
	}
	public void setOverdueInterestFee(BigDecimal overdueInterestFee) {
		this.overdueInterestFee = overdueInterestFee;
	}
	public BigDecimal getOverdueServiceFee() {
		return overdueServiceFee;
	}
	public void setOverdueServiceFee(BigDecimal overdueServiceFee) {
		this.overdueServiceFee = overdueServiceFee;
	}
	public BigDecimal getOverdueMgmtFee() {
		return overdueMgmtFee;
	}
	public void setOverdueMgmtFee(BigDecimal overdueMgmtFee) {
		this.overdueMgmtFee = overdueMgmtFee;
	}
	@Override
	public String toString() {
		return "{\"loanRefId\":" + loanRefId + ", \"periodNumber\":" + periodNumber + ", \"repaidType\":" + repaidType + ", \"overdueFlag\":" + overdueFlag + ", \"repayTimePlan\":" + repayTimePlan + ", \"repayTimeActual\":" + repayTimeActual
				+ ", \"amount\":" + amount + ", \"principal\":" + principal + ", \"interest\":" + interest + ", \"overdueInterestFee\":" + overdueInterestFee + ", \"overdueServiceFee\":" + overdueServiceFee + ", \"overdueMgmtFee\":" + overdueMgmtFee + "}";
	}


}
