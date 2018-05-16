/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

import java.math.BigDecimal;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年4月24日 <br>
 * 描述：减免详情参数
 */
public class SubParams {

	// 逾期天数
	private Integer overdays;
	// 逾期期次
	private Integer currentPeriod;

	// 应收违约金
	private BigDecimal receivePenalty;
	// 应收罚息
	private BigDecimal receiveInterest;
	// 应收违法金
	private BigDecimal receivePenaltyInterest;
	// 应收本金
	private BigDecimal shouldCapi;
	// 应收利息
	private BigDecimal shouldInte;
	// 应收月还
	private BigDecimal shouldAmt;
	// 应收总额
	private BigDecimal receiveMoney;

	// 减免违约金
	private BigDecimal subInterest;
	// 减免罚息
	private BigDecimal subPenalty;
	// 减免总额
	private BigDecimal reduction;

	// 实还违约金
	private BigDecimal shInterest;
	// 实还罚息
	private BigDecimal shPenalty;
	// 实还月还
	private BigDecimal shshouldAmt;
	// 实还总额
	private BigDecimal shreduction;

	public Integer getOverdays() {
		return overdays;
	}

	public void setOverdays(Integer overdays) {
		this.overdays = overdays;
	}

	public Integer getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(Integer currentPeriod) {
		this.currentPeriod = currentPeriod;
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

	public BigDecimal getSubInterest() {
		return subInterest;
	}

	public void setSubInterest(BigDecimal subInterest) {
		this.subInterest = subInterest;
	}

	public BigDecimal getSubPenalty() {
		return subPenalty;
	}

	public void setSubPenalty(BigDecimal subPenalty) {
		this.subPenalty = subPenalty;
	}

	public BigDecimal getReduction() {
		return reduction;
	}

	public void setReduction(BigDecimal reduction) {
		this.reduction = reduction;
	}

	public BigDecimal getShInterest() {
		return shInterest;
	}

	public void setShInterest(BigDecimal shInterest) {
		this.shInterest = shInterest;
	}

	public BigDecimal getShPenalty() {
		return shPenalty;
	}

	public void setShPenalty(BigDecimal shPenalty) {
		this.shPenalty = shPenalty;
	}

	public BigDecimal getShshouldAmt() {
		return shshouldAmt;
	}

	public void setShshouldAmt(BigDecimal shshouldAmt) {
		this.shshouldAmt = shshouldAmt;
	}

	public BigDecimal getShreduction() {
		return shreduction;
	}

	public void setShreduction(BigDecimal shreduction) {
		this.shreduction = shreduction;
	}

	public BigDecimal getReceivePenaltyInterest() {
		return receivePenaltyInterest;
	}

	public void setReceivePenaltyInterest(BigDecimal receivePenaltyInterest) {
		this.receivePenaltyInterest = receivePenaltyInterest;
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

}
