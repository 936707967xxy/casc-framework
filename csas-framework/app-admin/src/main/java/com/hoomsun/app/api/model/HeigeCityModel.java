/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.app.api.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年11月6日 <br>
 * 描述：获取省的所有门店(爬虫和第三方结合)
 */
public class HeigeCityModel {
	private String province;
	
	private String city;
	
	private String uniqueKey;
	
	private List<String> loginType = new ArrayList<String>();
	
	private String note;
	
	private boolean isSupport = true;
	
	private String cityCode; // 添加用于爬虫
	
	private String crawlerSign; // 添加用户爬虫
	
	
	public HeigeCityModel() {
	
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public List<String> getLoginType() {
		return loginType;
	}

	public void setLoginType(List<String> loginType) {
		this.loginType = loginType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public boolean isIsSupport() {
		return isSupport;
	}

	public void setIsSupport(boolean isSupport) {
		this.isSupport = isSupport;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCrawlerSign() {
		return crawlerSign;
	}

	public void setCrawlerSign(String crawlerSign) {
		this.crawlerSign = crawlerSign;
	}
	
}
