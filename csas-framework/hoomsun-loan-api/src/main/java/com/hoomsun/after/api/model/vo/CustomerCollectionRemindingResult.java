/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年4月18日 <br>
 * 描述：客户催收提醒返回
 */
public class CustomerCollectionRemindingResult implements Serializable{
	
	private static final long serialVersionUID = 7040890632189845822L;
	
	public String castName;
	public String sex;
	public String cardNo;
	public String tel;
	public String bankName;
	public String bankAcount;
	public String homeAddress;
	public String unitName;
	public String unitAddress;
	public String unitTel;
	public String productName;
	public String loanPeriod;
	public String remainderPeriod;
	public String loanMoney;
	public String loanDate;
	public String statementDate;
	public String shouldAmt;
	public String replaymentDate;
	public String overdueTotalAmt;
	public String overduePeriod;
	public String currentPayMoney;
	public String loanManagerCastId;
	public String loanManagerCast;
	public String oprationId;
	public String oprationName;
	public String currentPeriod;
	public String collectionCastId;
	public String collectionCastName;
	public String visitCastId;
	public String visitCastName;
	public List<CollectionRecordResult>overdueDetailsList ;
	public List<CollectingReceivingCallResult>collectingHistoryList ;
	public List<CollectingReceivingCallResult>callDetailsList ;
	public List<VisitRecordResult>visitRecordList;
	
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
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAcount() {
		return bankAcount;
	}
	public void setBankAcount(String bankAcount) {
		this.bankAcount = bankAcount;
	}
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getUnitAddress() {
		return unitAddress;
	}
	public void setUnitAddress(String unitAddress) {
		this.unitAddress = unitAddress;
	}
	public String getUnitTel() {
		return unitTel;
	}
	public void setUnitTel(String unitTel) {
		this.unitTel = unitTel;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getLoanPeriod() {
		return loanPeriod;
	}
	public void setLoanPeriod(String loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	public String getRemainderPeriod() {
		return remainderPeriod;
	}
	public void setRemainderPeriod(String remainderPeriod) {
		this.remainderPeriod = remainderPeriod;
	}
	public String getLoanMoney() {
		return loanMoney;
	}
	public void setLoanMoney(String loanMoney) {
		this.loanMoney = loanMoney;
	}
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	public String getStatementDate() {
		return statementDate;
	}
	public void setStatementDate(String statementDate) {
		this.statementDate = statementDate;
	}
	public String getShouldAmt() {
		return shouldAmt;
	}
	public void setShouldAmt(String shouldAmt) {
		this.shouldAmt = shouldAmt;
	}
	public String getReplaymentDate() {
		return replaymentDate;
	}
	public void setReplaymentDate(String replaymentDate) {
		this.replaymentDate = replaymentDate;
	}
	public String getOverdueTotalAmt() {
		return overdueTotalAmt;
	}
	public void setOverdueTotalAmt(String overdueTotalAmt) {
		this.overdueTotalAmt = overdueTotalAmt;
	}
	public String getOverduePeriod() {
		return overduePeriod;
	}
	public void setOverduePeriod(String overduePeriod) {
		this.overduePeriod = overduePeriod;
	}
	public String getCurrentPayMoney() {
		return currentPayMoney;
	}
	public void setCurrentPayMoney(String currentPayMoney) {
		this.currentPayMoney = currentPayMoney;
	}
	public List<CollectionRecordResult> getOverdueDetailsList() {
		return overdueDetailsList;
	}
	public void setOverdueDetailsList(List<CollectionRecordResult> overdueDetailsList) {
		this.overdueDetailsList = overdueDetailsList;
	}
	public List<CollectingReceivingCallResult> getCollectingHistoryList() {
		return collectingHistoryList;
	}
	public void setCollectingHistoryList(List<CollectingReceivingCallResult> collectingHistoryList) {
		this.collectingHistoryList = collectingHistoryList;
	}
	public List<CollectingReceivingCallResult> getCallDetailsList() {
		return callDetailsList;
	}
	public void setCallDetailsList(List<CollectingReceivingCallResult> callDetailsList) {
		this.callDetailsList = callDetailsList;
	}
	public String getLoanManagerCastId() {
		return loanManagerCastId;
	}
	public void setLoanManagerCastId(String loanManagerCastId) {
		this.loanManagerCastId = loanManagerCastId;
	}
	public String getOprationId() {
		return oprationId;
	}
	public void setOprationId(String oprationId) {
		this.oprationId = oprationId;
	}
	public String getOprationName() {
		return oprationName;
	}
	public void setOprationName(String oprationName) {
		this.oprationName = oprationName;
	}
	public String getCurrentPeriod() {
		return currentPeriod;
	}
	public void setCurrentPeriod(String currentPeriod) {
		this.currentPeriod = currentPeriod;
	}
	public String getLoanManagerCast() {
		return loanManagerCast;
	}
	public void setLoanManagerCast(String loanManagerCast) {
		this.loanManagerCast = loanManagerCast;
	}
	public List<VisitRecordResult> getVisitRecordList() {
		return visitRecordList;
	}
	public void setVisitRecordList(List<VisitRecordResult> visitRecordList) {
		this.visitRecordList = visitRecordList;
	}
	public String getCollectionCastId() {
		return collectionCastId;
	}
	public void setCollectionCastId(String collectionCastId) {
		this.collectionCastId = collectionCastId;
	}
	public String getCollectionCastName() {
		return collectionCastName;
	}
	public void setCollectionCastName(String collectionCastName) {
		this.collectionCastName = collectionCastName;
	}
	public String getVisitCastId() {
		return visitCastId;
	}
	public void setVisitCastId(String visitCastId) {
		this.visitCastId = visitCastId;
	}
	public String getVisitCastName() {
		return visitCastName;
	}
	public void setVisitCastName(String visitCastName) {
		this.visitCastName = visitCastName;
	}
}
