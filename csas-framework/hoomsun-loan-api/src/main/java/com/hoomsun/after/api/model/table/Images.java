package com.hoomsun.after.api.model.table;

import java.util.Date;

public class Images {
	private String id;
	/**
	 * 申请单号
	 */
	private String applicationId;
	private String applyId;
	
	/**
	 * 申请类型
	 */
	private String applyType;
	/**
	 * 影像资料类型 A1 身份证 B1 银行卡 C1 减免凭证
	 */
	private String imageType;
	/**
	 * 图片名称
	 */
	private String imageName;
	/**
	 * 访问路径
	 */
	private String imageUrl;
	/**
	 * 磁盘路径
	 */
	private String diskUrl;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}





	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType == null ? null : applyType.trim();
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType == null ? null : imageType.trim();
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName == null ? null : imageName.trim();
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl == null ? null : imageUrl.trim();
	}

	public String getDiskUrl() {
		return diskUrl;
	}

	public void setDiskUrl(String diskUrl) {
		this.diskUrl = diskUrl == null ? null : diskUrl.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getApplyId() {
		return applicationId;
	}
}