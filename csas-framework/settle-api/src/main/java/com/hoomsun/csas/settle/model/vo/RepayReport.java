/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.settle.model.vo;

import java.math.BigDecimal;

/**
 * 作者：liming <br>
 * 创建时间：2018年1月16日 <br>
 * 描述：客户还款报表
 */
public class RepayReport {
private String startTime;//起始还款日

private String conNo;//合同编号

private String castName;//客户姓名

private String cardNo;//身份证号

private String department;// 所属分部（名称）

private String productId;//产品id

private String productName;//产品

private String updownStatus;//0线下1线上

private String loanPeriod;// 贷款期数

private String endTime; // 结束还款日期

private BigDecimal monthRate;//月利率

private String currentPeriod;//当前期数

private String repayDate;// 还款期次

private BigDecimal receiveCorpus;//应还本金

private BigDecimal receiveInterest;//应还利息

private BigDecimal overduePenalty;//Y_应还违约金

private BigDecimal overdueInterest;//y_应还罚息

private BigDecimal actualMoney;//本期实收金

private String repayType;//状态


public String getStartTime() {
	return startTime;
}

public void setStartTime(String startTime) {
	this.startTime = startTime;
}

public String getConNo() {
	return conNo;
}

public void setConNo(String conNo) {
	this.conNo = conNo;
}

public String getCastName() {
	return castName;
}

public void setCastName(String castName) {
	this.castName = castName;
}

public String getCardNo() {
	return cardNo;
}

public void setCardNo(String cardNo) {
	this.cardNo = cardNo;
}

public String getDepartment() {
	return department;
}

public void setDepartment(String department) {
	this.department = department;
}

public String getProductId() {
	return productId;
}

public void setProductId(String productId) {
	this.productId = productId;
}

public String getProductName() {
	return productName;
}

public void setProductName(String productName) {
	this.productName = productName;
}


public String getUpdownStatus() {
	return updownStatus;
}

public void setUpdownStatus(String updownStatus) {
	this.updownStatus = updownStatus;
}

public String getLoanPeriod() {
	return loanPeriod;
}

public void setLoanPeriod(String loanPeriod) {
	this.loanPeriod = loanPeriod;
}

public String getEndTime() {
	return endTime;
}

public void setEndTime(String endTime) {
	this.endTime = endTime;
}


public BigDecimal getMonthRate() {
	return monthRate;
}

public void setMonthRate(BigDecimal monthRate) {
	this.monthRate = monthRate;
}

public String getCurrentPeriod() {
	return currentPeriod;
}

public void setCurrentPeriod(String currentPeriod) {
	this.currentPeriod = currentPeriod;
}


public String getRepayDate() {
	return repayDate;
}

public void setRepayDate(String repayDate) {
	this.repayDate = repayDate;
}


public BigDecimal getReceiveCorpus() {
	return receiveCorpus;
}

public void setReceiveCorpus(BigDecimal receiveCorpus) {
	this.receiveCorpus = receiveCorpus;
}

public BigDecimal getReceiveInterest() {
	return receiveInterest;
}

public void setReceiveInterest(BigDecimal receiveInterest) {
	this.receiveInterest = receiveInterest;
}

public BigDecimal getOverduePenalty() {
	return overduePenalty;
}

public void setOverduePenalty(BigDecimal overduePenalty) {
	this.overduePenalty = overduePenalty;
}

public BigDecimal getOverdueInterest() {
	return overdueInterest;
}

public void setOverdueInterest(BigDecimal overdueInterest) {
	this.overdueInterest = overdueInterest;
}

public BigDecimal getActualMoney() {
	return actualMoney;
}

public void setActualMoney(BigDecimal actualMoney) {
	this.actualMoney = actualMoney;
}

public String getRepayType() {
	return repayType;
}

public void setRepayType(String repayType) {
	this.repayType = repayType;
}



}
