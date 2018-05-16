package com.hoomsun.csas.sales.api.model;

public class NameAuthenticationInfo {
    private String authinfoPkId;

    private String fkId;

    private String verifyStatus;

    private String similarity;

    private String authResult;

    private String failReason;

    private String idName;

    private String idNumber;

    private String validityPeriod;

    private String issuingAuthority;

    private String nation;

    private String gender;

    private String birthday;

    private String age;

    private String address;

    public String getAuthinfoPkId() {
        return authinfoPkId;
    }

    public void setAuthinfoPkId(String authinfoPkId) {
        this.authinfoPkId = authinfoPkId == null ? null : authinfoPkId.trim();
    }

    public String getFkId() {
        return fkId;
    }

    public void setFkId(String fkId) {
        this.fkId = fkId == null ? null : fkId.trim();
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus == null ? null : verifyStatus.trim();
    }

    public String getSimilarity() {
        return similarity;
    }

    public void setSimilarity(String similarity) {
        this.similarity = similarity == null ? null : similarity.trim();
    }

    public String getAuthResult() {
        return authResult;
    }

    public void setAuthResult(String authResult) {
        this.authResult = authResult == null ? null : authResult.trim();
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason == null ? null : failReason.trim();
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName == null ? null : idName.trim();
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod == null ? null : validityPeriod.trim();
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority == null ? null : issuingAuthority.trim();
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age == null ? null : age.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
}