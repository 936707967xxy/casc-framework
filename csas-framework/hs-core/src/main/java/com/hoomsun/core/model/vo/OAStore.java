/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.model.vo;

import java.math.BigDecimal;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月13日 <br>
 * 描述：门店的信息
 */
public class OAStore {
	private String storeId;// 门店的ID
	private String storeName;// 门店的名称
	private String shortCode;// 门店的简码
	private String storeCode;// 门店编码
	private String storeAdd;// 门店地址
	private BigDecimal longitude;// 经度
	private BigDecimal latitude;// 纬度
	private String deptNo; // 门店编码（用于生成合同编码的）
	private String storeCityName;
	
	
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getStoreAdd() {
		return storeAdd;
	}

	public void setStoreAdd(String storeAdd) {
		this.storeAdd = storeAdd;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getStoreCityName() {
		return storeCityName;
	}

	public void setStoreCityName(String storeCityName) {
		this.storeCityName = storeCityName;
	}
	
}
