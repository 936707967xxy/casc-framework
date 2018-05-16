/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

/**
 * 作者：zwLiu <br>
 * 创建时间：2018年5月14日 <br>
 * 描述：宝付绑卡传参
 */
public class BFCertifiedBKParam {
	/**
	 * 客户姓名
	 */
	private String custName;
	/**
	 * 身份证号
	 */	
	private String idCard;
	/**
	 * 预留手机号
	 */	
	private String phoneNumber;
	/**
	 * 开户行名称
	 */	
	private String bankName;
	/**
	 * 银行账号
	 */	
	private String bankAccount;
	/**
	 * 银行代码
	 */	
	private String bankCode;
	
	/**
	 * 线上线下标识
	 */
	private String flag;
	/**
	 * 配置文件路径
	 */
	private String path;
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
