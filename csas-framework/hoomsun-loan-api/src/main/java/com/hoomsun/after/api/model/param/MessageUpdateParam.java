/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

/**
 * 作者：金世强 <br>
 * 创建时间：2018年4月18日 <br>
 * 描述：信息变更申请提交参数
 */
public class MessageUpdateParam {

	private String applyId;

	/**
	 * 申请类型(联系电话变更 telChange , 银行四要素变更 bankChange，逾期单月减免申请overdueSingle
 		逾期多月减免申请 overdueAll,外访申请 outBound，电催留案申请overdueLeave  外访留案申请outBoundLeave)
	 */
	private String applyType;

	private String custId;

	private String sex;

	private String castName;

	private String cardNo;

	private String oldTel;

	private String newTel;

	private String oldBank;

	private String oldBankPhone;

	private String oldBankAccount;

	private String oldBankCode;

	private String newBank;

	private String newBankPhone;

	private String newBankAccount;

	private String newBankCode;
	
	/**
	 * 申请备注
	 */
	private String applicationDesc;

	
	
	
	public String getApplicationDesc() {
		return applicationDesc;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCastName() {
		return castName;
	}

	public void setCastName(String castName) {
		this.castName = castName;
	}

	public String getOldTel() {
		return oldTel;
	}

	public void setOldTel(String oldTel) {
		this.oldTel = oldTel;
	}

	public String getNewTel() {
		return newTel;
	}

	public void setNewTel(String newTel) {
		this.newTel = newTel;
	}

	public String getOldBank() {
		return oldBank;
	}

	public void setOldBank(String oldBank) {
		this.oldBank = oldBank;
	}

	public String getOldBankPhone() {
		return oldBankPhone;
	}

	public void setOldBankPhone(String oldBankPhone) {
		this.oldBankPhone = oldBankPhone;
	}

	public String getOldBankAccount() {
		return oldBankAccount;
	}

	public void setOldBankAccount(String oldBankAccount) {
		this.oldBankAccount = oldBankAccount;
	}

	public String getOldBankCode() {
		return oldBankCode;
	}

	public void setOldBankCode(String oldBankCode) {
		this.oldBankCode = oldBankCode;
	}

	public String getNewBank() {
		return newBank;
	}

	public void setNewBank(String newBank) {
		this.newBank = newBank;
	}

	public String getNewBankPhone() {
		return newBankPhone;
	}

	public void setNewBankPhone(String newBankPhone) {
		this.newBankPhone = newBankPhone;
	}

	public String getNewBankAccount() {
		return newBankAccount;
	}

	public void setNewBankAccount(String newBankAccount) {
		this.newBankAccount = newBankAccount;
	}

	public String getNewBankCode() {
		return newBankCode;
	}

	public void setNewBankCode(String newBankCode) {
		this.newBankCode = newBankCode;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	
}
