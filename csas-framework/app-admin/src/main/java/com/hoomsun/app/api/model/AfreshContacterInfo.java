package com.hoomsun.app.api.model;

import java.sql.Timestamp;

public class AfreshContacterInfo {
    private String cninfoPkId;  //主键

    private String fkId;     //登陆用户主键

    private Timestamp  contacterTime; //录入时间

    private String poContactName;  //姓名

    private String poMobile;    //手机号

    private String poRelationship; //关系

    private String poRelationshipVal;  //关系值 
    
    private String isKnow;         //是否知晓  0 否               1 是

    private String isKnowVal;       //是否知晓

    private String poCompany;

    private String qsoneAddress;

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

    public String getPoRelationship() {
        return poRelationship;
    }

    public void setPoRelationship(String poRelationship) {
        this.poRelationship = poRelationship == null ? null : poRelationship.trim();
    }

    public String getPoRelationshipVal() {
        return poRelationshipVal;
    }

    public void setPoRelationshipVal(String poRelationshipVal) {
        this.poRelationshipVal = poRelationshipVal == null ? null : poRelationshipVal.trim();
    }

	public String getIsKnow() {
		return isKnow;
	}

	public void setIsKnow(String isKnow) {
		this.isKnow = isKnow;
	}

	public String getIsKnowVal() {
		return isKnowVal;
	}

	public void setIsKnowVal(String isKnowVal) {
		this.isKnowVal = isKnowVal;
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
}