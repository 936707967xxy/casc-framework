package com.hoomsun.after.api.model.table;

import java.util.Date;

public class ChangeInfo {

	private String id;

	// 申请单号
	private String applyId;

	// 申请类型(联系电话变更 telChange , 银行四要素变更 bankChange)
	private String applyType;

	// 客户ID
	private String custId;

	// 客户性别
	private String sex;

	// 客户姓名
	private String castName;

	// 客户身份证号
	private String cardNo;

	// 旧是手机号
	private String oldTel;

	// 新手机号
	private String newTel;

	// 就银行名称
	private String oldBank;

	// 旧银行预留手机号
	private String oldBankPhone;

	// 旧银行卡号
	private String oldBankAccount;

	// 旧银行CODE
	private String oldBankCode;

	// 新银行名称
	private String newBank;

	// 新银行预留手机号
	private String newBankPhone;

	// 新银行卡号
	private String newBankAccount;

	// 新银行CODE
	private String newBankCode;

	// 信息变更申请状态（0通过，1失效，2申请中）
	private String changeinfoStatus;

	// 创建日期
	private Date createTime;

	// 修改日期
	private Date updateDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId == null ? null : applyId.trim();
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType == null ? null : applyType.trim();
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId == null ? null : custId.trim();
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex == null ? null : sex.trim();
	}

	public String getCastName() {
		return castName;
	}

	public void setCastName(String castName) {
		this.castName = castName == null ? null : castName.trim();
	}

	public String getOldTel() {
		return oldTel;
	}

	public void setOldTel(String oldTel) {
		this.oldTel = oldTel == null ? null : oldTel.trim();
	}

	public String getNewTel() {
		return newTel;
	}

	public void setNewTel(String newTel) {
		this.newTel = newTel == null ? null : newTel.trim();
	}

	public String getOldBank() {
		return oldBank;
	}

	public void setOldBank(String oldBank) {
		this.oldBank = oldBank == null ? null : oldBank.trim();
	}

	public String getOldBankPhone() {
		return oldBankPhone;
	}

	public void setOldBankPhone(String oldBankPhone) {
		this.oldBankPhone = oldBankPhone == null ? null : oldBankPhone.trim();
	}

	public String getOldBankAccount() {
		return oldBankAccount;
	}

	public void setOldBankAccount(String oldBankAccount) {
		this.oldBankAccount = oldBankAccount == null ? null : oldBankAccount.trim();
	}

	public String getOldBankCode() {
		return oldBankCode;
	}

	public void setOldBankCode(String oldBankCode) {
		this.oldBankCode = oldBankCode == null ? null : oldBankCode.trim();
	}

	public String getNewBank() {
		return newBank;
	}

	public void setNewBank(String newBank) {
		this.newBank = newBank == null ? null : newBank.trim();
	}

	public String getNewBankPhone() {
		return newBankPhone;
	}

	public void setNewBankPhone(String newBankPhone) {
		this.newBankPhone = newBankPhone == null ? null : newBankPhone.trim();
	}

	public String getNewBankAccount() {
		return newBankAccount;
	}

	public void setNewBankAccount(String newBankAccount) {
		this.newBankAccount = newBankAccount == null ? null : newBankAccount.trim();
	}

	public String getNewBankCode() {
		return newBankCode;
	}

	public void setNewBankCode(String newBankCode) {
		this.newBankCode = newBankCode == null ? null : newBankCode.trim();
	}

	public String getChangeinfoStatus() {
		return changeinfoStatus;
	}

	public void setChangeinfoStatus(String changeinfoStatus) {
		this.changeinfoStatus = changeinfoStatus == null ? null : changeinfoStatus.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

}