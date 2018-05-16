package com.hoomsun.after.api.model.table;

import java.math.BigDecimal;
import java.util.Date;

public class RepaymentPlan {
	
	private String planId;
	
	/**
	 *  还款状态
	 */
	private Integer repayStatus;
	
	/**
	 *  提前还清减免渠道服务费2（z2）
	 */
	private BigDecimal preretuamtCredit;
	
	/**
	 *  提前还清减免红小宝平台服务费
	 */
	private BigDecimal preretuamtHxb;
	
	/**
	 *  提前还清减免渠道服务费1（z1）给至信
	 */
	private BigDecimal preretuamtChannel;
	
	/**
	 *  期初剩余本金
	 */
	private BigDecimal bal;
	
	/**
	 * 
	 */
	private Date billsDate;
	
	/**
	 *  应还日期
	 */
	private Date shouldDate;
	
	/**
	 *  应还利息
	 */
	private BigDecimal shouldInte;
	
	/**
	 *  应还本金
	 */
	
	private BigDecimal shouldCapi;
	
	/**
	 *  应还金额
	 */
	private BigDecimal shouldAmt;
	
	/**
	 *  应还期数
	 */
	private Integer shouldTerm;
	
	/**
	 *  申请id
	 */
	private String applyId;

	/**
	 *  合同id
	 */
	private String conId;
	
	/**
	 *  申请编号
	 */
	private String loanId;
	
	/**
	 *  提前还款应还
	 */
	private BigDecimal advanceShould;
	
	/**
	 * 实际提前还款
	 */
	private BigDecimal advanceMoney;
	
	/**
	 *  提前还款减免
	 */
	private BigDecimal advanceReduce;
	
	/**
	 *  期末提前还款减免
	 */
	private BigDecimal endBal;
	
	public String getPlanId() {
	
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public Integer getRepayStatus() {
		return repayStatus;
	}
	public void setRepayStatus(Integer repayStatus) {
		this.repayStatus = repayStatus;
	}
	public BigDecimal getPreretuamtCredit() {
		return preretuamtCredit;
	}
	public void setPreretuamtCredit(BigDecimal preretuamtCredit) {
		this.preretuamtCredit = preretuamtCredit;
	}
	public BigDecimal getPreretuamtHxb() {
		return preretuamtHxb;
	}
	public void setPreretuamtHxb(BigDecimal preretuamtHxb) {
		this.preretuamtHxb = preretuamtHxb;
	}
	public BigDecimal getPreretuamtChannel() {
		return preretuamtChannel;
	}
	public void setPreretuamtChannel(BigDecimal preretuamtChannel) {
		this.preretuamtChannel = preretuamtChannel;
	}
	public BigDecimal getBal() {
		return bal;
	}
	public void setBal(BigDecimal bal) {
		this.bal = bal;
	}
	public Date getBillsDate() {
		return billsDate;
	}
	public void setBillsDate(Date billsDate) {
		this.billsDate = billsDate;
	}
	public Date getShouldDate() {
		return shouldDate;
	}
	public void setShouldDate(Date shouldDate) {
		this.shouldDate = shouldDate;
	}
	public BigDecimal getShouldInte() {
		return shouldInte;
	}
	public void setShouldInte(BigDecimal shouldInte) {
		this.shouldInte = shouldInte;
	}
	public BigDecimal getShouldCapi() {
		return shouldCapi;
	}
	public void setShouldCapi(BigDecimal shouldCapi) {
		this.shouldCapi = shouldCapi;
	}
	public BigDecimal getShouldAmt() {
		return shouldAmt;
	}
	public void setShouldAmt(BigDecimal shouldAmt) {
		this.shouldAmt = shouldAmt;
	}
	public Integer getShouldTerm() {
		return shouldTerm;
	}
	public void setShouldTerm(Integer shouldTerm) {
		this.shouldTerm = shouldTerm;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getConId() {
		return conId;
	}
	public void setConId(String conId) {
		this.conId = conId;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public BigDecimal getAdvanceShould() {
		return advanceShould;
	}
	public void setAdvanceShould(BigDecimal advanceShould) {
		this.advanceShould = advanceShould;
	}
	public BigDecimal getAdvanceMoney() {
		return advanceMoney;
	}
	public void setAdvanceMoney(BigDecimal advanceMoney) {
		this.advanceMoney = advanceMoney;
	}
	public BigDecimal getAdvanceReduce() {
		return advanceReduce;
	}
	public void setAdvanceReduce(BigDecimal advanceReduce) {
		this.advanceReduce = advanceReduce;
	}
	public BigDecimal getEndBal() {
		return endBal;
	}
	public void setEndBal(BigDecimal endBal) {
		this.endBal = endBal;
	}
	
	
	
	
	

}