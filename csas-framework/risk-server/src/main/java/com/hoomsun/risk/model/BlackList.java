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
 * 描述：黑名单 
 */
@Document(collection = "RK_BLACK_LIST")
public class BlackList {
	@Id
	private String pkId;
	private String custName;
	@Indexed
	private String idNumber;
	@Indexed
	private String mobile;
	private String comName;
	private String comAlias;
	private String comCode;
	private String comAddr;
	private String comTel;
	private String housAddr;
	private String housTel;

	public String getPkId() {
		return pkId;
	}

	public void setPkId(String pkId) {
		this.pkId = pkId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getComAlias() {
		return comAlias;
	}

	public void setComAlias(String comAlias) {
		this.comAlias = comAlias;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getComAddr() {
		return comAddr;
	}

	public void setComAddr(String comAddr) {
		this.comAddr = comAddr;
	}

	public String getComTel() {
		return comTel;
	}

	public void setComTel(String comTel) {
		this.comTel = comTel;
	}

	public String getHousAddr() {
		return housAddr;
	}

	public void setHousAddr(String housAddr) {
		this.housAddr = housAddr;
	}

	public String getHousTel() {
		return housTel;
	}

	public void setHousTel(String housTel) {
		this.housTel = housTel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
