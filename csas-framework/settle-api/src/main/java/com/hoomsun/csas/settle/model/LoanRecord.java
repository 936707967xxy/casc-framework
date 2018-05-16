package com.hoomsun.csas.settle.model;

import java.math.BigDecimal;
import java.util.Date;

public class LoanRecord {
	private String loanId;//放款记录主键

	private String loanCode;//合同编号

	private BigDecimal loanAmount;//批准金额

	private BigDecimal conAmount;//合同金额

	private Integer loanTerm;//批准期限

	private BigDecimal interestRate;//利率

	// @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8") //取日期时使用
	// @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")//存日期时使用
	private Date loanDate;//放款日期

	private String handleEmp;//操作人员id

	private String handleEmpName;//操作人员

	private Date handleDate;//放款日期

	private String handleRemark;//放款备注

	private String productId;//产品id

	private String productName;//产品名称

	private Integer payType;//还款类型

	private String payAccount;//还款账户

	private Date payDate;//每月还款日

	private String payResult;//还款结果

	private String conId;//合同主键

	private String applyId;//申请主键

	private Integer handleOption;//处理意见id
	
	private String handleOptionVal;//处理意见
	
	private String taskId;//流程id
	
	private String procInstId;

	private Integer payChannel;//支付渠道 -- 0:线上BEAT(测试) 1:线上V1.0(特殊) 2:线上V2.0（普通） 3:线下 4:银行 5信托 6保险 7其他
	private String payChannelVal;
	
	
	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId == null ? null : loanId.trim();
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode == null ? null : loanCode.trim();
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getConAmount() {
		return conAmount;
	}

	public void setConAmount(BigDecimal conAmount) {
		this.conAmount = conAmount;
	}

	public Integer getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public String getHandleEmp() {
		return handleEmp;
	}

	public void setHandleEmp(String handleEmp) {
		this.handleEmp = handleEmp == null ? null : handleEmp.trim();
	}

	public String getHandleEmpName() {
		return handleEmpName;
	}

	public void setHandleEmpName(String handleEmpName) {
		this.handleEmpName = handleEmpName == null ? null : handleEmpName.trim();
	}

	public Date getHandleDate() {
		return handleDate;
	}

	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
	}

	public String getHandleRemark() {
		return handleRemark;
	}

	public void setHandleRemark(String handleRemark) {
		this.handleRemark = handleRemark == null ? null : handleRemark.trim();
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId == null ? null : productId.trim();
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName == null ? null : productName.trim();
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount == null ? null : payAccount.trim();
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getPayResult() {
		return payResult;
	}

	public void setPayResult(String payResult) {
		this.payResult = payResult == null ? null : payResult.trim();
	}

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public Integer getHandleOption() {
		return handleOption;
	}

	public void setHandleOption(Integer handleOption) {
		this.handleOption = handleOption;
	}

	public String getHandleOptionVal() {
		return handleOptionVal;
	}

	public void setHandleOptionVal(String handleOptionVal) {
		this.handleOptionVal = handleOptionVal;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	
	public Integer getPayChannel() {
	        return payChannel;
	}

    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

	public String getPayChannelVal() {
		return payChannelVal;
	}

	public void setPayChannelVal(String payChannelVal) {
		this.payChannelVal = payChannelVal;
	}
    
}