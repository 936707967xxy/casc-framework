/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.vo;

/**
 * 作者：金世强 <br>
 * 创建时间：2018年4月18日 <br>
 * 描述：客户行卡 信息
 */
public class BankVo {

	/**
	 * 客户姓名
	 */
	private String castName;

	/**
	 * 客户性别
	 */
	private String sex;

	/**
	 * 联系电话
	 */
	private String tel;

	/**
	 * 身份证号码
	 */
	private String cardNo;

	/**
	 * 银行名称
	 */
	private String bank;

	/**
	 * 银行预留手机号
	 */
	private String bankPhone;

	/**
	 * 银行卡号
	 */
	private String bankAccount;

	/**
	 * 银行编码
	 */
	private String bankCode;

	public String getCastName() {
		return castName;
	}

	public void setCastName(String castName) {
		this.castName = castName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankPhone() {
		return bankPhone;
	}

	public void setBankPhone(String bankPhone) {
		this.bankPhone = bankPhone;
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

	
	
}
