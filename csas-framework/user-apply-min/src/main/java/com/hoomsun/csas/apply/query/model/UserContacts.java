package com.hoomsun.csas.apply.query.model;

import java.sql.Timestamp;;

/**
 * @author ygzhao
 * 联系人model
 *
 */
public class UserContacts {
    private String phoneid;//主键

    private String applyId;//申请表id

    private String name;//姓名

    private String phone;//手机号

    private Timestamp addDate;//添加时间

    private Integer callcounts;//通话记录数

    private String callTime;//通话时长

    private String mobileaddress;//归属地

    private Integer poRelationship;//关系ID

    private String poRelationshipVal;//关系val

    private String poCompany;//联系人所在公司

    private String qsoneAddress;//联系人所在地址

    private Integer isFamilyKnow;//联系人是否知晓

    private String isFamilyKnowVal;//联系人是否知晓值

    public String getPhoneid() {
        return phoneid;
    }

    public void setPhoneid(String phoneid) {
        this.phoneid = phoneid == null ? null : phoneid.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Timestamp getAddDate() {
        return addDate;
    }

    public void setAddDate(Timestamp addDate) {
        this.addDate = addDate;
    }

    public Integer getCallcounts() {
        return callcounts;
    }

    public void setCallcounts(Integer callcounts) {
        this.callcounts = callcounts;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime == null ? null : callTime.trim();
    }

    public String getMobileaddress() {
        return mobileaddress;
    }

    public void setMobileaddress(String mobileaddress) {
        this.mobileaddress = mobileaddress == null ? null : mobileaddress.trim();
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
}