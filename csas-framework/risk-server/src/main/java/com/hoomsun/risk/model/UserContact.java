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
 * 创建时间：2018年1月4日 <br>
 * 描述：用户联系人 通话记录前20名 通话频次
 */
@Document(collection = "RK_USER_CONTACT")
public class UserContact {
	@Id
	private String linkId;
	private String linkName;
	@Indexed
	private String linkPhone;
	private String attribution;
	private String comName;
	private String comAddr;
	private String housAddr;
	private Integer linkType;
	private String linkTypeVal;
	private Integer callCounts;// 通话频次
	private String callTime;// 通话时长
	private Integer source;// 0:自动获取 1:客户填写
	private String sourceVal;
	private Integer phoneType;
	private String phoneTypeVal; // 银行 1 小贷 2 p2p 3 其他 4 默认 0
	@Indexed
	private String applyId;

	private Integer relationship;// 联系人关系 1：配偶 2：直系 3：同事 4：其他
	private String relationshipVal;
	private Integer know;
	private String knowVal;
	private Date addDate;

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

	public Integer getLinkType() {
		return linkType;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	public String getLinkTypeVal() {
		return linkTypeVal;
	}

	public void setLinkTypeVal(String linkTypeVal) {
		this.linkTypeVal = linkTypeVal;
	}

	public String getCallTime() {
		return callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(Integer phoneType) {
		this.phoneType = phoneType;
	}

	public String getPhoneTypeVal() {
		return phoneTypeVal;
	}

	public void setPhoneTypeVal(String phoneTypeVal) {
		this.phoneTypeVal = phoneTypeVal;
	}

	public Integer getCallCounts() {
		return callCounts;
	}

	public void setCallCounts(Integer callCounts) {
		this.callCounts = callCounts;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public Integer getRelationship() {
		return relationship;
	}

	public void setRelationship(Integer relationship) {
		this.relationship = relationship;
	}

	public String getRelationshipVal() {
		return relationshipVal;
	}

	public void setRelationshipVal(String relationshipVal) {
		this.relationshipVal = relationshipVal;
	}

	public String getSourceVal() {
		return sourceVal;
	}

	public void setSourceVal(String sourceVal) {
		this.sourceVal = sourceVal;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Integer getKnow() {
		return know;
	}

	public void setKnow(Integer know) {
		this.know = know;
	}

	public String getKnowVal() {
		return knowVal;
	}

	public void setKnowVal(String knowVal) {
		this.knowVal = knowVal;
	}
}
