/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.vo;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月29日 <br>
 * 描述：客户还款记录明细
 */
public class LoanBookCustomerResult {

	//进件编号
	private String loanId;
	//客户姓名
	private String castName;
	//应还日期
	private String repayDate;
	//应还期次
	private String stream;
	//当期应还本金
	private String receiveCorpus;
	//当期应还利息
	private String receiveShouldinte;
	//当期月还款额（应还本金+应还利息）
	private String amt;
	//当期应收违约金
	private String receivePenalty;
	//当期应收罚息
	private String receiveInterest;
	//当期应收违罚金（应收违约金+应收罚息）
	private String receivePenaltyInterest;
	//当期应收总额（违罚金+月还款额）
	private String receiveMoney;
	//提前结清应还金额
	private String receiveAdvance;
	//提前还清减免渠道服务费
	private String channelServiceFee;
	//逾期天数
	private String overdueDays;
	//减免金额
	private String subMoney;
	//扣除金额
	private String actualMoney;
	//还款类型(1正常月还,2提前还款，3逾期月还)
	private String loanBookType;
	//还款类型值(1正常月还,2提前还款，3逾期月还)
	private String loanBookTypeVal;
	public String getCastName() {
		return castName;
	}
	public void setCastName(String castName) {
		this.castName = castName;
	}
	public String getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}
	public String getStream() {
		return stream;
	}
	public void setStream(String stream) {
		this.stream = stream;
	}
	public String getReceiveCorpus() {
		return receiveCorpus;
	}
	public void setReceiveCorpus(String receiveCorpus) {
		this.receiveCorpus = receiveCorpus;
	}
	public String getReceiveShouldinte() {
		return receiveShouldinte;
	}
	public void setReceiveShouldinte(String receiveShouldinte) {
		this.receiveShouldinte = receiveShouldinte;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getReceivePenalty() {
		return receivePenalty;
	}
	public void setReceivePenalty(String receivePenalty) {
		this.receivePenalty = receivePenalty;
	}
	public String getReceiveInterest() {
		return receiveInterest;
	}
	public void setReceiveInterest(String receiveInterest) {
		this.receiveInterest = receiveInterest;
	}
	public String getReceiveMoney() {
		return receiveMoney;
	}
	public void setReceiveMoney(String receiveMoney) {
		this.receiveMoney = receiveMoney;
	}
	public String getReceiveAdvance() {
		return receiveAdvance;
	}
	public void setReceiveAdvance(String receiveAdvance) {
		this.receiveAdvance = receiveAdvance;
	}
	public String getChannelServiceFee() {
		return channelServiceFee;
	}
	public void setChannelServiceFee(String channelServiceFee) {
		this.channelServiceFee = channelServiceFee;
	}
	public String getOverdueDays() {
		return overdueDays;
	}
	public void setOverdueDays(String overdueDays) {
		this.overdueDays = overdueDays;
	}
	public String getSubMoney() {
		return subMoney;
	}
	public void setSubMoney(String subMoney) {
		this.subMoney = subMoney;
	}
	public String getActualMoney() {
		return actualMoney;
	}
	public void setActualMoney(String actualMoney) {
		this.actualMoney = actualMoney;
	}
	public String getLoanBookType() {
		return loanBookType;
	}
	public void setLoanBookType(String loanBookType) {
		this.loanBookType = loanBookType;
	}
	public String getLoanBookTypeVal() {
		return loanBookTypeVal;
	}
	public void setLoanBookTypeVal(String loanBookTypeVal) {
		this.loanBookTypeVal = loanBookTypeVal;
	}
	public String getReceivePenaltyInterest() {
		return receivePenaltyInterest;
	}
	public void setReceivePenaltyInterest(String receivePenaltyInterest) {
		this.receivePenaltyInterest = receivePenaltyInterest;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
}
