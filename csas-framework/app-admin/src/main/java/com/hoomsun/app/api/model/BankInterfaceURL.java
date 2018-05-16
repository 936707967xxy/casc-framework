/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.app.api.model;



/**
 * 作者：liushuai <br>
 * 创建时间：2017年9月27日 <br>
 * 描述：主要用于银行接口中的所有URL地址
 */
public class BankInterfaceURL {
	private String bankCardCode;
	private String magSubmit;
	private String phoneCode;
	private String login;
	
	public BankInterfaceURL(){
		
	}
	
	private BankInterfaceURL(String bankCardCode, String magSubmit, String phoneCode, String login) {
		this.bankCardCode = bankCardCode;
		this.magSubmit = magSubmit;
		this.phoneCode = phoneCode;
		this.login = login;
	}


	public String getBankCardCode() {
		return bankCardCode;
	}
	
	public void setBankCardCode(String bankCardCode) {
		this.bankCardCode = bankCardCode;
	}
	
	public String getMagSubmit() {
		return magSubmit;
	}
	
	public void setMagSubmit(String magSubmit) {
		this.magSubmit = magSubmit;
	}
	
	public String getPhoneCode() {
		return phoneCode;
	}
	
	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}  
	
	@Override
	public String toString() {
		return "BankInterfaceURL [bankCardCode=" + bankCardCode + ", magSubmit=" + magSubmit + ", phoneCode=" + phoneCode + ", login=" + login + "]";
	}
}
