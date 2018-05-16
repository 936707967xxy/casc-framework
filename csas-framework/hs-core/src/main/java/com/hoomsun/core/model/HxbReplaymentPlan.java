package com.hoomsun.core.model;

import java.math.BigDecimal;
import java.util.Date;

public class HxbReplaymentPlan {
    private String planId;//主键id

    private String applyId;//申请id

    private Integer phaseNumber;//期序

    private BigDecimal amount;//当期应还本息

    private BigDecimal principal;//当期应还本金

    private BigDecimal inRepayTotalAmount;//提前还清应收总额:本期利息+剩余本金

    private BigDecimal channelServiceFee;//提前还清减免渠道服务费1（z1）给至信

    private BigDecimal creditServiceFee;//提前还清减免渠道服务费2（z2）

    private BigDecimal hxbServiceFee;//提前还清减免红小宝平台服务费（y）

    private BigDecimal interest;//当期应还利息

    private Date repayDate;//当期应还款日

    private BigDecimal initialPrincipal;//当期期初剩余本金

    private BigDecimal finalPrincipal;//当期期末剩余本金
    
    private String conId;//合同ID

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId == null ? null : planId.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public Integer getPhaseNumber() {
        return phaseNumber;
    }

    public void setPhaseNumber(Integer phaseNumber) {
        this.phaseNumber = phaseNumber;
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

    public BigDecimal getInRepayTotalAmount() {
        return inRepayTotalAmount;
    }

    public void setInRepayTotalAmount(BigDecimal inRepayTotalAmount) {
        this.inRepayTotalAmount = inRepayTotalAmount;
    }

    public BigDecimal getChannelServiceFee() {
        return channelServiceFee;
    }

    public void setChannelServiceFee(BigDecimal channelServiceFee) {
        this.channelServiceFee = channelServiceFee;
    }

    public BigDecimal getCreditServiceFee() {
        return creditServiceFee;
    }

    public void setCreditServiceFee(BigDecimal creditServiceFee) {
        this.creditServiceFee = creditServiceFee;
    }

    public BigDecimal getHxbServiceFee() {
        return hxbServiceFee;
    }

    public void setHxbServiceFee(BigDecimal hxbServiceFee) {
        this.hxbServiceFee = hxbServiceFee;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    public BigDecimal getInitialPrincipal() {
        return initialPrincipal;
    }

    public void setInitialPrincipal(BigDecimal initialPrincipal) {
        this.initialPrincipal = initialPrincipal;
    }

    public BigDecimal getFinalPrincipal() {
        return finalPrincipal;
    }

    public void setFinalPrincipal(BigDecimal finalPrincipal) {
        this.finalPrincipal = finalPrincipal;
    }

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}
    
    
}