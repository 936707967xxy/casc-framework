/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年4月18日 <br>
 * 描述：催收记录、通话详单
 */
public class CollectingReceivingCallResult implements Serializable{

	private static final long serialVersionUID = -4118361192303546076L;
	public String id;
	public String createTime;
	public Date createTimeDate;
	public Date updateTime;
	public String loanId;
	/**
	 * 催收时间
	 */
	public String collectingDate;
	/**
	 * 姓名
	 */
	public String collectingName;
	/**
	 * 关系
	 */
	public String relationship;
	/**
	 * 接听状态
	 */
	public String receivingState;
	/**
	 * 提醒状态
	0:已提醒
	1:未提醒
	 */
	public String remindingState;
	/**
	 * 未醒天数
	 */
	public String noMndingNum;
	/**
	 * 提醒天数
	 */
	public String mindingNum;
	/**
	 * 记录人
	 */
	public String noteTaker;
	/**
	 * 下次提醒时间
	 */
	public String nextRemindingTime;
	public Date nextRemindingTimeDate;
	/**
	 * 备注
	 */
	public String remarks;
	/**
	 * 电话
	 */
	public String collectingTel;
	
	public String getCollectingDate() {
		return collectingDate;
	}
	public void setCollectingDate(String collectingDate) {
		this.collectingDate = collectingDate;
	}
	public String getCollectingName() {
		return collectingName;
	}
	public void setCollectingName(String collectingName) {
		this.collectingName = collectingName;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getReceivingState() {
		return receivingState;
	}
	public void setReceivingState(String receivingState) {
		this.receivingState = receivingState;
	}
	public String getRemindingState() {
		return remindingState;
	}
	public void setRemindingState(String remindingState) {
		this.remindingState = remindingState;
	}
	public String getNoMndingNum() {
		return noMndingNum;
	}
	public void setNoMndingNum(String noMndingNum) {
		this.noMndingNum = noMndingNum;
	}
	public String getNoteTaker() {
		return noteTaker;
	}
	public void setNoteTaker(String noteTaker) {
		this.noteTaker = noteTaker;
	}
	public String getNextRemindingTime() {
		return nextRemindingTime;
	}
	public void setNextRemindingTime(String nextRemindingTime) {
		this.nextRemindingTime = nextRemindingTime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCollectingTel() {
		return collectingTel;
	}
	public void setCollectingTel(String collectingTel) {
		this.collectingTel = collectingTel;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Date getCreateTimeDate() {
		return createTimeDate;
	}
	public void setCreateTimeDate(Date createTimeDate) {
		this.createTimeDate = createTimeDate;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getMindingNum() {
		return mindingNum;
	}
	public void setMindingNum(String mindingNum) {
		this.mindingNum = mindingNum;
	}
	public Date getNextRemindingTimeDate() {
		return nextRemindingTimeDate;
	}
	public void setNextRemindingTimeDate(Date nextRemindingTimeDate) {
		this.nextRemindingTimeDate = nextRemindingTimeDate;
	}
}
