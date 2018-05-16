/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

/**
 * 作者：zwLiu <br>
 * 创建时间：2018年4月17日 <br>
 * 描述：影像资料上传model
 */
public class ImageModel {
	/**
	 * 申请单号
	 */
	private String applyId;
	/**
	 * 影像资料类型
	 */
	private String imageType;
	/**
	 *申请类型
	 */
	private String applyType;

	/**
	 *影像资料ID 
	 */
	private String imageId;
	
	/**
	 * 影像资料物理路径
	 */
	private String diskPath;
	
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getDiskPath() {
		return diskPath;
	}
	public void setDiskPath(String diskPath) {
		this.diskPath = diskPath;
	}

}
