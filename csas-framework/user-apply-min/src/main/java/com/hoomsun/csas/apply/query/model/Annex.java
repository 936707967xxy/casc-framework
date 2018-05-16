package com.hoomsun.csas.apply.query.model;

import java.sql.Timestamp;

public class Annex {
	private String anxId;

	private String applyId;

	private String fileName;

	private String saveName;

	private String savePath;

	private String preView;

	private Long fileSize;

	private String fileType;

	private Timestamp createDate;

	private String empId;

	private String actNode;

	private Integer applyTypeId;

	private String applyTypeVal;

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

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
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
}