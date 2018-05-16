/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

import java.math.BigDecimal;

/**
 * 作者：zwLiu <br>
 * 创建时间：2018年3月14日 <br>
 * 描述：此类是宝付划扣传参
 */
public class BFdeductModel {
	/**
	 * 配置文件路径
	 */
	private String path;
	/**
	 * 银行卡编码
	 */
	private String payCode;
	/**
	 * 银行卡号
	 */
	private String accNo;
	/**
	 * 身份证号
	 */
	private String idCard;
	/**
	 * 持卡人姓名
	 */
	private String idHolder;
	/**
	 * 银行卡预留手机号
	 */
	private String mobile;
	/**
	 * 交易金额
	 */
	private BigDecimal txnAmt;
	
	/**
	 *线上线下标识
	 *0  线下
	 *1 线上
	 */
	private String flag;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getIdHolder() {
		return idHolder;
	}
	public void setIdHolder(String idHolder) {
		this.idHolder = idHolder;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public BigDecimal getTxnAmt() {
		return txnAmt;
	}
	public void setTxnAmt(BigDecimal txnAmt) {
		this.txnAmt = txnAmt;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
