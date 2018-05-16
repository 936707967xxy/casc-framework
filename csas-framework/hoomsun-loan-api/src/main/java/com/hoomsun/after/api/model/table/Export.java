package com.hoomsun.after.api.model.table;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 作者：屈楠 <br>
 * 创建时间：2018年2月27日 <br>
 * 描述：HS_AFTER_EXPORT客户账户支出记录表
 *
 */
public class Export {
	private String id;

	/**
	 * 进件编号
	 */
	private String loanId;

	/**
	 * 合同编号
	 */
	private String conNo;

	/**
	 * 客户姓名
	 */
	private String castName;

	/**
	 * 支出类型(1正常月还支出,2提前还款支出，3逾期月还支出)
	 */
	private String expendType;

	/**
	 * 支出类型值
	 */
	private String expendTypeVal;

	/**
	 * 支出金额
	 */
	private BigDecimal expendMoney;

	/**
	 * 支出时间
	 */
	private Date expendDate;

	/**
	 * 减免金额
	 */
	private BigDecimal subMoney;

	/**
	 * 支出期次
	 */
	private Integer expendStream;

	/**
	 * 当期应还本金
	 */
	private BigDecimal receiveCorpus;

	/**
	 * 当期应还利息
	 */
	private BigDecimal receiveShouldinte;

	/**
	 * 当期月还款额（应还本金+应还利息）
	 */
	private BigDecimal amt;

	/**
	 * 当期应收违约金
	 */
	private BigDecimal receivePenalty;

	/**
	 * 当期应收罚息
	 */
	private BigDecimal receiveInterest;

	/**
	 * 当期应收违罚金（应收违约金+应收罚息）
	 */
	private BigDecimal receivePenaltyInterest;

	/**
	 * 当期应收总额（违罚金+月还款额）
	 */
	private BigDecimal receiveMoney;

	/**
	 * 支出划扣客服ID
	 */
	private String applicationCastId;

	/**
	 * 支出划扣客服NAME
	 */
	private String applicationCastName;

	/**
	 * 创建日期
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date updateDate;

	/**
	 * 提前结清应还金额
	 */
	private BigDecimal receiveAdvance;

	/**
	 * 提前结清退还服务费
	 */
	private BigDecimal channelServiceFee;

	/**
	 * 客户ID
	 */
	private String castId;

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

	public String getExpendType() {
		return expendType;
	}

	public void setExpendType(String expendType) {
		this.expendType = expendType == null ? null : expendType.trim();
	}

	public String getExpendTypeVal() {
		return expendTypeVal;
	}

	public void setExpendTypeVal(String expendTypeVal) {
		this.expendTypeVal = expendTypeVal == null ? null : expendTypeVal.trim();
	}

	public BigDecimal getExpendMoney() {
		return expendMoney;
	}

	public void setExpendMoney(BigDecimal expendMoney) {
		this.expendMoney = expendMoney;
	}

	public Date getExpendDate() {
		return expendDate;
	}

	public void setExpendDate(Date expendDate) {
		this.expendDate = expendDate;
	}

	public BigDecimal getSubMoney() {
		return subMoney;
	}

	public void setSubMoney(BigDecimal subMoney) {
		this.subMoney = subMoney;
	}

	public Integer getExpendStream() {
		return expendStream;
	}

	public void setExpendStream(Integer expendStream) {
		this.expendStream = expendStream;
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

	public String getApplicationCastId() {
		return applicationCastId;
	}

	public void setApplicationCastId(String applicationCastId) {
		this.applicationCastId = applicationCastId == null ? null : applicationCastId.trim();
	}

	public String getApplicationCastName() {
		return applicationCastName;
	}

	public void setApplicationCastName(String applicationCastName) {
		this.applicationCastName = applicationCastName == null ? null : applicationCastName.trim();
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

	public String getCastId() {
		return castId;
	}

	public void setCastId(String castId) {
		this.castId = castId;
	}

}