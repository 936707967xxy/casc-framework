/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.vo;

import java.io.Serializable;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年5月7日 <br>
 * 描述：外访记录响应参数
 */
public class VisitRecordResult implements Serializable{

	private static final long serialVersionUID = 4131574320356701634L;
	
	private String id;
	private String loanId;
	private String castName;
	private String visitCreateTime;
	private String visitCastAddress;
	private String visitNote;
	private String collectionCastName;
	private String visitCastName;
	private String applicationId;
	private String collectionCastId;
	private String visitCastId;
	private String hostIp;
	private String oprationId;
	private String oprationName;
	private String applyId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getCastName() {
		return castName;
	}
	public void setCastName(String castName) {
		this.castName = castName;
	}
	public String getVisitCreateTime() {
		return visitCreateTime;
	}
	public void setVisitCreateTime(String visitCreateTime) {
		this.visitCreateTime = visitCreateTime;
	}
	public String getVisitCastAddress() {
		return visitCastAddress;
	}
	public void setVisitCastAddress(String visitCastAddress) {
		this.visitCastAddress = visitCastAddress;
	}
	public String getVisitNote() {
		return visitNote;
	}
	public void setVisitNote(String visitNote) {
		this.visitNote = visitNote;
	}
	public String getCollectionCastName() {
		return collectionCastName;
	}
	public void setCollectionCastName(String collectionCastName) {
		this.collectionCastName = collectionCastName;
	}
	public String getVisitCastName() {
		return visitCastName;
	}
	public void setVisitCastName(String visitCastName) {
		this.visitCastName = visitCastName;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getCollectionCastId() {
		return collectionCastId;
	}
	public void setCollectionCastId(String collectionCastId) {
		this.collectionCastId = collectionCastId;
	}
	public String getVisitCastId() {
		return visitCastId;
	}
	public void setVisitCastId(String visitCastId) {
		this.visitCastId = visitCastId;
	}
	public String getHostIp() {
		return hostIp;
	}
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	public String getOprationId() {
		return oprationId;
	}
	public void setOprationId(String oprationId) {
		this.oprationId = oprationId;
	}
	public String getOprationName() {
		return oprationName;
	}
	public void setOprationName(String oprationName) {
		this.oprationName = oprationName;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
}
