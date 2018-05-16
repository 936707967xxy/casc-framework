/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月31日 <br>
 * 描述：近n月的通话电话号码
 */
@Document(collection = "RK_NEAR_MONTH_CALL")
public class NearNMonthCall {
	@Id
	private String id;
	@Indexed
	private String idNumber;
	@Indexed
	private String applyId;
	@Indexed
	private String crId;
	private Date createDate;
	private Date claimDate;
	private String[] phones;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String[] getPhones() {
		return phones;
	}

	public void setPhones(String[] phones) {
		this.phones = phones;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCrId() {
		return crId;
	}

	public void setCrId(String crId) {
		this.crId = crId;
	}

}
