package com.hoomsun.after.api.model.vo;

import java.math.BigDecimal;
import java.util.Date;

public class AppInterfaceLoanData {
    private String loanId;

    private BigDecimal receiveMoney;

    private BigDecimal shouldAmt;

    private BigDecimal receivePenaltyInterest;

    private BigDecimal receivePenalty;

    private String settleFlag;

    private Integer currentPeriod;

    private String delayFlag;

    private Date shouldDate;

    private BigDecimal shouldInte;
    
    private String repayments;// 是否必须还款
    
    private String orderNo;// 查证订单号
    
    private String state;// 状态

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId == null ? null : loanId.trim();
    }

    public BigDecimal getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(BigDecimal receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public BigDecimal getShouldAmt() {
        return shouldAmt;
    }

    public void setShouldAmt(BigDecimal shouldAmt) {
        this.shouldAmt = shouldAmt;
    }


    public BigDecimal getReceivePenaltyInterest() {
        return receivePenaltyInterest;
    }

    public void setReceivePenaltyInterest(BigDecimal receivePenaltyInterest) {
        this.receivePenaltyInterest = receivePenaltyInterest;
    }

    public BigDecimal getReceivePenalty() {
        return receivePenalty;
    }

    public void setReceivePenalty(BigDecimal receivePenalty) {
        this.receivePenalty = receivePenalty;
    }

    public String getSettleFlag() {
        return settleFlag;
    }

    public void setSettleFlag(String settleFlag) {
        this.settleFlag = settleFlag == null ? null : settleFlag.trim();
    }

    public Integer getCurrentPeriod() {
        return currentPeriod;
    }

    public void setCurrentPeriod(Integer currentPeriod) {
        this.currentPeriod = currentPeriod;
    }

    public String getDelayFlag() {
        return delayFlag;
    }

    public void setDelayFlag(String delayFlag) {
        this.delayFlag = delayFlag == null ? null : delayFlag.trim();
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

	public String getRepayments() {
		return repayments;
	}

	public void setRepayments(String repayments) {
		this.repayments = repayments;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
    
    
}