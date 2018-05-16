/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月16日 <br>
 * 描述：公共电话库
 */
@Document(collection = "RK_COMMON_PHONE")
public class CommonPhone {
	@Id
	private String phoneNumber;
	private String commType;// 1审核 2 催收 3服务
	private String commTypeVal;
	private String orgName;
	private String orgType;// 1银行 2小贷 3:p2p 4:法院 5:警察局 6:税务局 7:医院  8:其他行政机构 9:其他
	private Date createDate;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCommType() {
		return commType;
	}

	public void setCommType(String commType) {
		this.commType = commType;
	}

	public String getCommTypeVal() {
		return commTypeVal;
	}

	public void setCommTypeVal(String commTypeVal) {
		this.commTypeVal = commTypeVal;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
