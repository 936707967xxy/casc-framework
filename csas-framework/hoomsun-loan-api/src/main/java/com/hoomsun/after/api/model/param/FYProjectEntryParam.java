/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

import java.math.BigDecimal;

/**
 * 作者：zwLiu <br>
 * 创建时间：2018年4月26日 <br>
 * 描述：富友项目录入传参
 */


public class FYProjectEntryParam {
	
	public FYProjectEntryParam() {

	}	
	
	public FYProjectEntryParam(String updownStatus, BigDecimal projectAmt, String contractNm, String projectDeadline, String borNm, String idNo, String cardNo, String mobileNo) {
		super();
		UpdownStatus = updownStatus;
		this.projectAmt = projectAmt;
		this.contractNm = contractNm;
		this.projectDeadline = projectDeadline;
		this.borNm = borNm;
		this.idNo = idNo;
		this.cardNo = cardNo;
		this.mobileNo = mobileNo;
	}
	/**
	 * 线上线下标识（0：线下，1：线上）
	 */
	private String UpdownStatus;
	/**
	 * 项目金额
	 */
	private BigDecimal projectAmt;
	/**
	 * 项目合同编号
	 */
	private String contractNm;
	/**
	 * 项目期限（单位：天）
	 */
	private String projectDeadline;
	/**
	 * 借款人姓名
	 */
	private String borNm;
	/**
	 * 借款人证件号码
	 */
	private String idNo;
	/**
	 * 借款人卡号
	 */
	private String cardNo;
	/**
	 * 借款人手机号码(银行预留)
	 */
	private String mobileNo;
	/**
	 * xml模板路径
	 */
	private String path;
	
	
	public String getUpdownStatus() {
		return UpdownStatus;
	}
	public void setUpdownStatus(String updownStatus) {
		UpdownStatus = updownStatus;
	}
	public BigDecimal getProjectAmt() {
		return projectAmt;
	}
	public void setProjectAmt(BigDecimal projectAmt) {
		this.projectAmt = projectAmt;
	}
	public String getContractNm() {
		return contractNm;
	}
	public void setContractNm(String contractNm) {
		this.contractNm = contractNm;
	}
	public String getProjectDeadline() {
		return projectDeadline;
	}
	public void setProjectDeadline(String projectDeadline) {
		this.projectDeadline = projectDeadline;
	}
	public String getBorNm() {
		return borNm;
	}
	public void setBorNm(String borNm) {
		this.borNm = borNm;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Override
	public String toString() {
		return "FYProjectEntryParam [线上线下标识=" + UpdownStatus + ", 项目金额=" + projectAmt + ", 项目合同编号=" + contractNm + ", 项目期限=" + projectDeadline + ", 借款人姓名=" + borNm + ", 借款人证件号码=" + idNo + ", 借款人卡号=" + cardNo
				+ ", 借款人手机号码=" + mobileNo + "]";
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
