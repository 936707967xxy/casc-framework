package com.hoomsun.csas.apply.query.model;

import java.sql.Timestamp;


/**
 * 作者：ygzhao <br>
 * 创建时间：2017年11月23日 <br>
 * 描述：联系人信息表
 */
public class AfreshContacterInfo {
    private String cninfoPkId;//主键

    private String fkId;//外键（主表id）

    private Timestamp contacterTime;//添加时间

    private String poContactName;//联系人姓名

    private String poMobile;//手机号

    private Integer poRelationship;//联系人关系id(1:配偶,2:直系亲属,3:同事,4:其他)

    private String poRelationshipVal;//联系人关系值

    private String poCompany;//联系人所在公司

    private String qsoneAddress;//联系人地址

    private Integer isFamilyKnow;//是否知晓借款id，1：是，0否

    private String isFamilyKnowVal;//是否知晓借款值

    private String applyId;//申请单id

    public String getCninfoPkId() {
        return cninfoPkId;
    }

    public void setCninfoPkId(String cninfoPkId) {
        this.cninfoPkId = cninfoPkId == null ? null : cninfoPkId.trim();
    }

    public String getFkId() {
        return fkId;
    }

    public void setFkId(String fkId) {
        this.fkId = fkId == null ? null : fkId.trim();
    }

    public Timestamp getContacterTime() {
        return contacterTime;
    }

    public void setContacterTime(Timestamp contacterTime) {
        this.contacterTime = contacterTime;
    }

    public String getPoContactName() {
        return poContactName;
    }

    public void setPoContactName(String poContactName) {
        this.poContactName = poContactName == null ? null : poContactName.trim();
    }

    public String getPoMobile() {
        return poMobile;
    }

    public void setPoMobile(String poMobile) {
        this.poMobile = poMobile == null ? null : poMobile.trim();
    }

    public Integer getPoRelationship() {
        return poRelationship;
    }

    public void setPoRelationship(Integer poRelationship) {
        this.poRelationship = poRelationship;
    }

    public String getPoRelationshipVal() {
        return poRelationshipVal;
    }

    public void setPoRelationshipVal(String poRelationshipVal) {
        this.poRelationshipVal = poRelationshipVal == null ? null : poRelationshipVal.trim();
    }

    public String getPoCompany() {
        return poCompany;
    }

    public void setPoCompany(String poCompany) {
        this.poCompany = poCompany == null ? null : poCompany.trim();
    }

    public String getQsoneAddress() {
        return qsoneAddress;
    }

    public void setQsoneAddress(String qsoneAddress) {
        this.qsoneAddress = qsoneAddress == null ? null : qsoneAddress.trim();
    }

    public Integer getIsFamilyKnow() {
        return isFamilyKnow;
    }

    public void setIsFamilyKnow(Integer isFamilyKnow) {
        this.isFamilyKnow = isFamilyKnow;
    }

    public String getIsFamilyKnowVal() {
        return isFamilyKnowVal;
    }

    public void setIsFamilyKnowVal(String isFamilyKnowVal) {
        this.isFamilyKnowVal = isFamilyKnowVal == null ? null : isFamilyKnowVal.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

	@Override
	public String toString() {
		return "AfreshContacterInfo [cninfoPkId=" + cninfoPkId + ", fkId=" + fkId + ", contacterTime=" + contacterTime + ", poContactName=" + poContactName + ", poMobile=" + poMobile + ", poRelationship=" + poRelationship
				+ ", poRelationshipVal=" + poRelationshipVal + ", poCompany=" + poCompany + ", qsoneAddress=" + qsoneAddress + ", isFamilyKnow=" + isFamilyKnow + ", isFamilyKnowVal=" + isFamilyKnowVal + ", applyId=" + applyId + "]";
	}
    
}