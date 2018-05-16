/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.settle.model.vo;

import java.math.BigDecimal;

/**
 * 作者：liming <br>
 * 创建时间：2018年1月16日 <br>
 * 描述：应还款客户统计报表(线上)
 */
public class ShouldRepayReport {

private String custType;//客户类型

private String postype;//pos类型

private String conNo;//合同编号

private String serialId;//进件编号

private String castName;//客户姓名

private String cardNo;//身份证号

private String custSex;//性别

private String loanUse;//借款用途id

private String loanUseVal;//借款用途

private String department;// （所属机构） 

private String storeId;// 门店id

private String productId;//产品id

private String productName;//产品

private String updownStatus;//0线下1线上

private String loanPeriod;// 贷款期数

private Integer currentPeriod;//当前期数

private Integer surplusPeriod;//剩余期数

private  Integer repayDate;//还款期次

//private String  onlieDate;//线上实际打款日

//private String onlieReplaymentDate;//线上还款日期

private String loanDate;//放款日期

private String replaymentDate;//还款日期

private String endTime; // 到期日期

private BigDecimal productRate;// 月利率

private BigDecimal contractAccount;//合同金额

private BigDecimal loanMoney;// 放款金额

private BigDecimal shouldCapi;//本期本金

private BigDecimal shouldInte;//本期利息

//private String a;// 累计拖欠罚息

//private BigDecimal b;//累计拖欠违约金

private BigDecimal amt;//本期应收金额

//private  BigDecimal c;//提前结清所需费用

private String repayType;//还款状态

//private BigDecimal  d;//提前结清退回服务费

private String bank;//所属银行（例：招商银行）

private String bankAccount;// 开户行账号

private String bankPhone;// 银行预留手机号

private String bigArea;//区域

//private String fenqu;//分区

private String fenzhongxin;//分中心

private String storeName;//营业部

private String salesEmpId;// 销售ID

private String salesEmpName;// 销售姓名

private String teamLeader;//团队经理姓名

private String teamNO;//团队经理工号

private String statementDate;//账单日


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


public String getCustType() {
	return custType;
}

public void setCustType(String custType) {
	this.custType = custType;
}

public String getPostype() {
	return postype;
}

public void setPostype(String postype) {
	this.postype = postype;
}

public String getSerialId() {
	return serialId;
}

public void setSerialId(String serialId) {
	this.serialId = serialId;
}


public String getCustSex() {
	return custSex;
}

public void setCustSex(String custSex) {
	this.custSex = custSex;
}

public String getLoanUse() {
	return loanUse;
}

public void setLoanUse(String loanUse) {
	this.loanUse = loanUse;
}

public String getLoanUseVal() {
	return loanUseVal;
}

public void setLoanUseVal(String loanUseVal) {
	this.loanUseVal = loanUseVal;
}

public String getStoreId() {
	return storeId;
}

public void setStoreId(String storeId) {
	this.storeId = storeId;
}

public Integer getCurrentPeriod() {
	return currentPeriod;
}

public void setCurrentPeriod(Integer currentPeriod) {
	this.currentPeriod = currentPeriod;
}

public Integer getSurplusPeriod() {
	return surplusPeriod;
}

public void setSurplusPeriod(Integer surplusPeriod) {
	this.surplusPeriod = surplusPeriod;
}



public Integer getRepayDate() {
	return repayDate;
}

public void setRepayDate(Integer repayDate) {
	this.repayDate = repayDate;
}

public String getLoanDate() {
	return loanDate;
}

public void setLoanDate(String loanDate) {
	this.loanDate = loanDate;
}

public String getReplaymentDate() {
	return replaymentDate;
}

public void setReplaymentDate(String replaymentDate) {
	this.replaymentDate = replaymentDate;
}

public BigDecimal getProductRate() {
	return productRate;
}

public void setProductRate(BigDecimal productRate) {
	this.productRate = productRate;
}

public BigDecimal getContractAccount() {
	return contractAccount;
}

public void setContractAccount(BigDecimal contractAccount) {
	this.contractAccount = contractAccount;
}

public BigDecimal getLoanMoney() {
	return loanMoney;
}

public void setLoanMoney(BigDecimal loanMoney) {
	this.loanMoney = loanMoney;
}

public BigDecimal getShouldCapi() {
	return shouldCapi;
}

public void setShouldCapi(BigDecimal shouldCapi) {
	this.shouldCapi = shouldCapi;
}

public BigDecimal getShouldInte() {
	return shouldInte;
}

public void setShouldInte(BigDecimal shouldInte) {
	this.shouldInte = shouldInte;
}

public BigDecimal getAmt() {
	return amt;
}

public void setAmt(BigDecimal amt) {
	this.amt = amt;
}

public String getBank() {
	return bank;
}

public void setBank(String bank) {
	this.bank = bank;
}

public String getBankAccount() {
	return bankAccount;
}

public void setBankAccount(String bankAccount) {
	this.bankAccount = bankAccount;
}

public String getBankPhone() {
	return bankPhone;
}

public void setBankPhone(String bankPhone) {
	this.bankPhone = bankPhone;
}

public String getBigArea() {
	return bigArea;
}

public void setBigArea(String bigArea) {
	this.bigArea = bigArea;
}

public String getFenzhongxin() {
	return fenzhongxin;
}

public void setFenzhongxin(String fenzhongxin) {
	this.fenzhongxin = fenzhongxin;
}

public String getStoreName() {
	return storeName;
}

public void setStoreName(String storeName) {
	this.storeName = storeName;
}

public String getSalesEmpId() {
	return salesEmpId;
}

public void setSalesEmpId(String salesEmpId) {
	this.salesEmpId = salesEmpId;
}

public String getSalesEmpName() {
	return salesEmpName;
}

public void setSalesEmpName(String salesEmpName) {
	this.salesEmpName = salesEmpName;
}

public String getTeamLeader() {
	return teamLeader;
}

public void setTeamLeader(String teamLeader) {
	this.teamLeader = teamLeader;
}

public String getTeamNO() {
	return teamNO;
}

public void setTeamNO(String teamNO) {
	this.teamNO = teamNO;
}

public String getStatementDate() {
	return statementDate;
}

public void setStatementDate(String statementDate) {
	this.statementDate = statementDate;
}

public String getRepayType() {
	return repayType;
}

public void setRepayType(String repayType) {
	this.repayType = repayType;
}



}
