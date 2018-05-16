package com.hoomsun.core.model;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月5日 <br>
 * 描述： 系统参数
 */
public class SystemParam {
	private String id;
	private String paramKey;// 参数的key
	private String paramValue;// 参数值
	private String paramRemark;// 参数描述

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey == null ? null : paramKey.trim();
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue == null ? null : paramValue.trim();
	}

	public String getParamRemark() {
		return paramRemark;
	}

	public void setParamRemark(String paramRemark) {
		this.paramRemark = paramRemark == null ? null : paramRemark.trim();
	}

	@Override
	public String toString() {
		return "SystemParam [id=" + id + ", paramKey=" + paramKey + ", paramValue=" + paramValue + ", paramRemark=" + paramRemark + "]";
	}

}