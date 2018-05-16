/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

import java.io.Serializable;
import java.util.Date;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年4月20日 <br>
 * 描述：任务配件
 */
public class DeliveryHistoryReq implements Serializable{

	private static final long serialVersionUID = 6386622598656190730L;
	private String id;
	private String appointPeopleId;
	private String appointPeopleName;
	private String optionId;
	private String optionName;
	private Date createTime;
	private String loanId;
	private String deliveryType;
	private Date updateDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppointPeopleId() {
		return appointPeopleId;
	}
	public void setAppointPeopleId(String appointPeopleId) {
		this.appointPeopleId = appointPeopleId;
	}
	public String getAppointPeopleName() {
		return appointPeopleName;
	}
	public void setAppointPeopleName(String appointPeopleName) {
		this.appointPeopleName = appointPeopleName;
	}
	public String getOptionId() {
		return optionId;
	}
	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
