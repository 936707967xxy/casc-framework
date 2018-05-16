package com.hoomsun.after.api.model.table;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 作者：金世强 <br>
 * 创建时间：2018年3月19日 <br>
 * 描述： 客户账户表
 *
 */
public class UserCount {

	/**
	 * 客户账户表主键
	 */
	private String id;

	/**
	 * 客户id
	 */
	private String castId;

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
	 * 客户来源
	 */
	private String castSource;

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
	/**
	 * 银行编码2
	 */
	private String bankCode2;
	/**
	 * 银行编码3
	 */
	private String bankCode3;

	/**
	 * 账户余额
	 */
	private BigDecimal bal;

	/**
	 * 修改时间
	 */
	private Date updateDate;

	/**
	 * 创建日期
	 */
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getCastId() {
		return castId;
	}

	public void setCastId(String castId) {
		this.castId = castId == null ? null : castId.trim();
	}

	public String getCastName() {
		return castName;
	}

	public void setCastName(String castName) {
		this.castName = castName == null ? null : castName.trim();
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex == null ? null : sex.trim();
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel == null ? null : tel.trim();
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo == null ? null : cardNo.trim();
	}

	public String getCastSource() {
		return castSource;
	}

	public void setCastSource(String castSource) {
		this.castSource = castSource == null ? null : castSource.trim();
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank == null ? null : bank.trim();
	}

	public String getBankPhone() {
		return bankPhone;
	}

	public void setBankPhone(String bankPhone) {
		this.bankPhone = bankPhone == null ? null : bankPhone.trim();
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount == null ? null : bankAccount.trim();
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode == null ? null : bankCode.trim();
	}

	public BigDecimal getBal() {
		return bal;
	}

	public void setBal(BigDecimal bal) {
		this.bal = bal;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBankCode2() {
		return bankCode2;
	}

	public void setBankCode2(String bankCode2) {
		this.bankCode2 = bankCode2;
	}

	public String getBankCode3() {
		return bankCode3;
	}

	public void setBankCode3(String bankCode3) {
		this.bankCode3 = bankCode3;
	}

}