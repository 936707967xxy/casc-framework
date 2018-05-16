package com.hoomsun.csas.sales.api.model;

import java.util.Date;

public class Annex {
	private String anxId;
	private String applyId;
	private String fileName;
	private String saveName;
	private String savePath;
	private String preView;
	private Long fileSize;
	private String fileType;
	private Date createDate;
	private String empId;
	private String actNode;
	private Integer applyTypeId;
	private String applyTypeVal;
	private String preViewHost;

	public String getAnxId() {
		return anxId;
	}

	public void setAnxId(String anxId) {
		this.anxId = anxId == null ? null : anxId.trim();
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId == null ? null : applyId.trim();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName == null ? null : fileName.trim();
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName == null ? null : saveName.trim();
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath == null ? null : savePath.trim();
	}

	public String getPreView() {
		return preView;
	}

	public void setPreView(String preView) {
		this.preView = preView == null ? null : preView.trim();
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType == null ? null : fileType.trim();
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId == null ? null : empId.trim();
	}

	public String getActNode() {
		return actNode;
	}

	public void setActNode(String actNode) {
		this.actNode = actNode == null ? null : actNode.trim();
	}

	public Integer getApplyTypeId() {
		return applyTypeId;
	}

	public void setApplyTypeId(Integer applyTypeId) {
		this.applyTypeId = applyTypeId;
	}

	public String getApplyTypeVal() {
		return applyTypeVal;
	}

	public void setApplyTypeVal(String applyTypeVal) {
		this.applyTypeVal = applyTypeVal == null ? null : applyTypeVal.trim();
	}

	public String getPreViewHost() {
		return preViewHost;
	}

	public void setPreViewHost(String preViewHost) {
		this.preViewHost = preViewHost;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

}