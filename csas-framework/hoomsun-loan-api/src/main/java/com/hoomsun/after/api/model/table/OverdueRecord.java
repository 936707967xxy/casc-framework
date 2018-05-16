package com.hoomsun.after.api.model.table;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 作者：屈楠 <br>
 * 创建时间：2018年2月27日 <br>
 * 描述：HS_AFTER_OVERDUE_RECORD客户逾期记录表
 *
 */
public class OverdueRecord {

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
	 * 逾期天数
	 */
	private Integer overdueDays;

	/**
	 * 应收违约金
	 */
	private BigDecimal receivePenalty;

	/**
	 * 应收罚息
	 */
	private BigDecimal receiveInterest;

	/**
	 * 应收违罚金（应收违约金+应收罚息）
	 */
	private BigDecimal receivePenaltyInterest;

	/**
	 * 应还本金
	 */
	private BigDecimal receiveCorpus;

	/**
	 * 应还利息
	 */
	private BigDecimal receiveShouldinte;

	/**
	 * 月还款额
	 */
	private BigDecimal amt;

	/**
	 * 应收总额（违罚金+月还款额）
	 */
	private BigDecimal receiveMoney;

	/**
	 * 逾期期数
	 */
	private Integer overdueNum;

	/**
	 * 结清标识
	 */
	private String settleFlag;

	/**
	 * 是否标红(1标红)
	 */
	private String toRed;

	/**
	 * 修改时间
	 */
	private Date updateDate;

	/**
	 * 创建日期
	 */
	private Date createTime;

	/**
	 * 实际逾期天数(催收任务，逾期天数回退列表使用...)
	 */
	private Integer realOverDays;

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

	public Integer getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(Integer overdueDays) {
		this.overdueDays = overdueDays;
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

	public BigDecimal getReceiveMoney() {
		return receiveMoney;
	}

	public void setReceiveMoney(BigDecimal receiveMoney) {
		this.receiveMoney = receiveMoney;
	}

	public Integer getOverdueNum() {
		return overdueNum;
	}

	public void setOverdueNum(Integer overdueNum) {
		this.overdueNum = overdueNum;
	}

	public String getSettleFlag() {
		return settleFlag;
	}

	public void setSettleFlag(String settleFlag) {
		this.settleFlag = settleFlag == null ? null : settleFlag.trim();
	}

	public String getToRed() {
		return toRed;
	}

	public void setToRed(String toRed) {
		this.toRed = toRed == null ? null : toRed.trim();
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getRealOverDays() {
		return realOverDays;
	}

	public void setRealOverDays(Integer realOverDays) {
		this.realOverDays = realOverDays;
	}

}