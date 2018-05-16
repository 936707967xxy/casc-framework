package com.hoomsun.after.api.model.table;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 作者：屈楠 <br>
 * 创建时间：2018年2月27日 <br>
 * 描述：HS_AFTER_LOANBOOK客户还款记录表
 *
 */
public class Loanbook {

	private String id;

	/**
	 *  进件编号
	 */
	private String loanId;

	/**
	 * 合同编号
	 */
	private String conNo;

	/**
	 *  客户姓名
	 */
	private String castName;

	/**
	 *  应还日期
	 */
	private Date repayDate;

	/**
	 *  应还期次
	 */
	private Integer stream;

	/**
	 *  当期应还本金
	 */
	private BigDecimal receiveCorpus;

	/**
	 *  当期应还利息
	 */
	private BigDecimal receiveShouldinte;

	/**
	 *  当期月还款额（应还本金+应还利息）
	 */
	private BigDecimal amt;

	/**
	 *  当期应收违约金
	 */
	private BigDecimal receivePenalty;

	/**
	 *  当期应收罚息
	 */
	private BigDecimal receiveInterest;

	/**
	 *  当期应收违罚金（应收违约金+应收罚息）
	 */
	private BigDecimal receivePenaltyInterest;

	/**
	 *  当期应收总额（违罚金+月还款额）
	 */
	private BigDecimal receiveMoney;

	/**
	 *  提前结清应还金额
	 */
	private BigDecimal receiveAdvance;

	/**
	 *  提前还清减免渠道服务费
	 */
	private BigDecimal channelServiceFee;

	/**
	 *  逾期天数
	 */
	private Integer overdueDays;

	/**
	 *  减免金额
	 */
	private BigDecimal subMoney;

	/**
	 *  扣除金额
	 */
	private BigDecimal actualMoney;

	/**
	 *  创建日期
	 */
	private Date createTime;

	/**
	 *  修改时间
	 */
	private Date updateDate;

	/**
	 *  还款类型(1正常月还,2提前还款，3逾期月还)
	 */
	private String loanbookType;

	/**
	 *  还款类型值(1正常月还,2提前还款，3逾期月还)
	 */
	private String loanbookTypeVal;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId == null ? null : loanId.trim();
	}

	public String getConNo() {
		return conNo;
	}

	public void setConNo(String conNo) {
		this.conNo = conNo == null ? null : conNo.trim();
	}

	public String getCastName() {
		return castName;
	}

	public void setCastName(String castName) {
		this.castName = castName == null ? null : castName.trim();
	}

	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	public Integer getStream() {
		return stream;
	}

	public void setStream(Integer stream) {
		this.stream = stream;
	}

	public BigDecimal getReceiveCorpus() {
		return receiveCorpus;
	}

	public void setReceiveCorpus(BigDecimal receiveCorpus) {
		this.receiveCorpus = receiveCorpus;
	}

	public BigDecimal getReceiveShouldinte() {
		return receiveShouldinte;
	}

	public void setReceiveShouldinte(BigDecimal receiveShouldinte) {
		this.receiveShouldinte = receiveShouldinte;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public BigDecimal getReceivePenalty() {
		return receivePenalty;
	}

	public void setReceivePenalty(BigDecimal receivePenalty) {
		this.receivePenalty = receivePenalty;
	}

	public BigDecimal getReceiveInterest() {
		return receiveInterest;
	}

	public void setReceiveInterest(BigDecimal receiveInterest) {
		this.receiveInterest = receiveInterest;
	}

	public BigDecimal getReceivePenaltyInterest() {
		return receivePenaltyInterest;
	}

	public void setReceivePenaltyInterest(BigDecimal receivePenaltyInterest) {
		this.receivePenaltyInterest = receivePenaltyInterest;
	}

	public BigDecimal getReceiveMoney() {
		return receiveMoney;
	}

	public void setReceiveMoney(BigDecimal receiveMoney) {
		this.receiveMoney = receiveMoney;
	}

	public BigDecimal getReceiveAdvance() {
		return receiveAdvance;
	}

	public void setReceiveAdvance(BigDecimal receiveAdvance) {
		this.receiveAdvance = receiveAdvance;
	}

	public BigDecimal getChannelServiceFee() {
		return channelServiceFee;
	}

	public void setChannelServiceFee(BigDecimal channelServiceFee) {
		this.channelServiceFee = channelServiceFee;
	}

	public Integer getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(Integer overdueDays) {
		this.overdueDays = overdueDays;
	}

	public BigDecimal getSubMoney() {
		return subMoney;
	}

	public void setSubMoney(BigDecimal subMoney) {
		this.subMoney = subMoney;
	}

	public BigDecimal getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(BigDecimal actualMoney) {
		this.actualMoney = actualMoney;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getLoanbookType() {
		return loanbookType;
	}

	public void setLoanbookType(String loanbookType) {
		this.loanbookType = loanbookType;
	}

	public String getLoanbookTypeVal() {
		return loanbookTypeVal;
	}

	public void setLoanbookTypeVal(String loanbookTypeVal) {
		this.loanbookTypeVal = loanbookTypeVal;
	}
	
	
}