package com.hoomsun.core.model;

import java.math.BigDecimal;
import java.util.Date;

import com.hoomsun.common.util.DateUtil;

public class SysRepaymentPlan {
	private String planId;//主键
	private Integer repayStatus;// 还款状态
	private BigDecimal preretuamtCredit;// 提前还清减免渠道服务费2（z2）
	private BigDecimal preretuamtHxb;// 提前还清减免红小宝平台服务费
	private BigDecimal preretuamtChannel;// 提前还清减免渠道服务费1（z1）给至信
	private BigDecimal bal;// 剩余本金(期初)
	private Date billsDate;//账单日
	private Date shouldDate;// 应还日期
	private BigDecimal shouldInte;// 应还利息
	private BigDecimal shouldCapi;// 应还本金
	private BigDecimal shouldAmt;// 应还金额
	private Integer shouldTerm;// 应还期数
	private String applyId;// 申请id
	private String conId;// 合同id
	private String loanId;// 申请编号
	private BigDecimal advanceShould;// 提前还款应还
	private BigDecimal advanceMoney;// 实际提前还款(一次性还款)
	private BigDecimal advanceReduce;// 提前还款减免
	private BigDecimal endBal;// 剩余本金(期末)
	
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId == null ? null : planId.trim();
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

	public Date getShouldDate() {
		return shouldDate;
	}

	public String getStrShouldDate() {
		if (this.shouldDate == null) {
			return null;
		}
		return DateUtil.yyyyMMdd.format(this.shouldDate);
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
		this.applyId = applyId == null ? null : applyId.trim();
	}

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId == null ? null : conId.trim();
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId == null ? null : loanId.trim();
	}

	public Date getBillsDate() {
		return billsDate;
	}

	public void setBillsDate(Date billsDate) {
		this.billsDate = billsDate;
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