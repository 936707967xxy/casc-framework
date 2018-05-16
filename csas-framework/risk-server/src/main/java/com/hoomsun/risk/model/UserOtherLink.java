/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月4日 <br>
 * 描述：其他联系人
 */
@Document(collection = "RK_USER_OTHER_LINK")
public class UserOtherLink {
	@Id
	private String linkId;
	private String linkName;
	@Indexed
	private String linkPhone;
	private String attribution;
	private String comName;
	private String comAddr;
	private String housAddr;
	@Indexed
	private String applyId;
	
	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getAttribution() {
		return attribution;
	}

	public void setAttribution(String attribution) {
		this.attribution = attribution;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getComAddr() {
		return comAddr;
	}

	public void setComAddr(String comAddr) {
		this.comAddr = comAddr;
	}

	public String getHousAddr() {
		return housAddr;
	}

	public void setHousAddr(String housAddr) {
		this.housAddr = housAddr;
	}
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	public void copyFrom(UserContact userContact) {
		this.linkId = userContact.getLinkId();
		this.linkName = userContact.getLinkName();
		this.linkPhone = userContact.getLinkPhone();
		this.attribution = userContact.getAttribution();
		this.comName = userContact.getComName();
		this.comAddr = userContact.getComAddr();
		this.housAddr = userContact.getHousAddr();
		this.applyId = userContact.getApplyId();
	}

}
