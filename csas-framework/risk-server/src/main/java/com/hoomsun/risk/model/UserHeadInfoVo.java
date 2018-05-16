package com.hoomsun.risk.model;

import java.util.Date;

/**
 * 
 * @author ygzhao
 * @date 2018-02-02
 * 描述： 用户头像信息表
 */
public class UserHeadInfoVo {
    private String poId;//主键

    private String custName;//姓名

    private String custSex;//性别

    private String idNumber;//身份证

    private Integer custAge;//年龄

    private String isLocalPerson;//是否本地人

    private String forecastLiveTimeTd;//同盾预测本地居住时长

    private String forecastLiveTimeZx;//资信预测本地居住时长

    private String forecastLiveTimePq;//爬取预测本地居住时长

    private String maritalStatusCust;//客户填-婚姻状况

    private String maritalStatusTd;//同盾-婚姻状况

    private String maritalStatusZx;//上海资信-婚姻状况

    private String addressCust;//客户填-住址

    private String addressPq;//爬取-住址

    private String addressTd;//同盾-住址

    private String addressZx;//资信-住址

    private String addressXsAdd;//信审添加-住址

    private String companyNameCust;//客户填-单位名称

    private String companyNameSb;//社保网站-单位名称

    private String companyNameTd;//同盾-单位名称

    private String companyNameZx;//资信-单位名称

    private String jobTitle;//职位名称

    private String position;//职级

    private String industryIn;//行业

    private String companyKind;//单位性质

    private String inComeAverage;//平均值--收入

    private String inComeModel;//中位数--收入

    private String inComeCoef;//变异系数--收入

    private String inComeCust;//客户填--收入

    private String liabilitiesLoan;//贷款-负债

    private String liabilitiesCard;//信用卡-负债

    private String liabilitiesCurrent;//当前负债金额

    private String propertyTypeZx;//征信--房产类型

    private String propertyTypeCust;//客户填--房产类型

    private String applyId;//申请表id

    private Date createdTime;//创建时间

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId == null ? null : poId.trim();
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public String getCustSex() {
        return custSex;
    }

    public void setCustSex(String custSex) {
        this.custSex = custSex == null ? null : custSex.trim();
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public Integer getCustAge() {
        return custAge;
    }

    public void setCustAge(Integer custAge) {
        this.custAge = custAge;
    }

    public String getIsLocalPerson() {
        return isLocalPerson;
    }

    public void setIsLocalPerson(String isLocalPerson) {
        this.isLocalPerson = isLocalPerson == null ? null : isLocalPerson.trim();
    }

    public String getForecastLiveTimeTd() {
        return forecastLiveTimeTd;
    }

    public void setForecastLiveTimeTd(String forecastLiveTimeTd) {
        this.forecastLiveTimeTd = forecastLiveTimeTd == null ? null : forecastLiveTimeTd.trim();
    }

    public String getForecastLiveTimeZx() {
        return forecastLiveTimeZx;
    }

    public void setForecastLiveTimeZx(String forecastLiveTimeZx) {
        this.forecastLiveTimeZx = forecastLiveTimeZx == null ? null : forecastLiveTimeZx.trim();
    }

    public String getForecastLiveTimePq() {
        return forecastLiveTimePq;
    }

    public void setForecastLiveTimePq(String forecastLiveTimePq) {
        this.forecastLiveTimePq = forecastLiveTimePq == null ? null : forecastLiveTimePq.trim();
    }

    public String getMaritalStatusCust() {
        return maritalStatusCust;
    }

    public void setMaritalStatusCust(String maritalStatusCust) {
        this.maritalStatusCust = maritalStatusCust == null ? null : maritalStatusCust.trim();
    }

    public String getMaritalStatusTd() {
        return maritalStatusTd;
    }

    public void setMaritalStatusTd(String maritalStatusTd) {
        this.maritalStatusTd = maritalStatusTd == null ? null : maritalStatusTd.trim();
    }

    public String getMaritalStatusZx() {
        return maritalStatusZx;
    }

    public void setMaritalStatusZx(String maritalStatusZx) {
        this.maritalStatusZx = maritalStatusZx == null ? null : maritalStatusZx.trim();
    }

    public String getAddressCust() {
        return addressCust;
    }

    public void setAddressCust(String addressCust) {
        this.addressCust = addressCust == null ? null : addressCust.trim();
    }

    public String getAddressPq() {
        return addressPq;
    }

    public void setAddressPq(String addressPq) {
        this.addressPq = addressPq == null ? null : addressPq.trim();
    }

    public String getAddressTd() {
        return addressTd;
    }

    public void setAddressTd(String addressTd) {
        this.addressTd = addressTd == null ? null : addressTd.trim();
    }

    public String getAddressZx() {
        return addressZx;
    }

    public void setAddressZx(String addressZx) {
        this.addressZx = addressZx == null ? null : addressZx.trim();
    }

    public String getAddressXsAdd() {
        return addressXsAdd;
    }

    public void setAddressXsAdd(String addressXsAdd) {
        this.addressXsAdd = addressXsAdd == null ? null : addressXsAdd.trim();
    }

    public String getCompanyNameCust() {
        return companyNameCust;
    }

    public void setCompanyNameCust(String companyNameCust) {
        this.companyNameCust = companyNameCust == null ? null : companyNameCust.trim();
    }

    public String getCompanyNameSb() {
        return companyNameSb;
    }

    public void setCompanyNameSb(String companyNameSb) {
        this.companyNameSb = companyNameSb == null ? null : companyNameSb.trim();
    }

    public String getCompanyNameTd() {
        return companyNameTd;
    }

    public void setCompanyNameTd(String companyNameTd) {
        this.companyNameTd = companyNameTd == null ? null : companyNameTd.trim();
    }

    public String getCompanyNameZx() {
        return companyNameZx;
    }

    public void setCompanyNameZx(String companyNameZx) {
        this.companyNameZx = companyNameZx == null ? null : companyNameZx.trim();
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle == null ? null : jobTitle.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getIndustryIn() {
        return industryIn;
    }

    public void setIndustryIn(String industryIn) {
        this.industryIn = industryIn == null ? null : industryIn.trim();
    }

    public String getCompanyKind() {
        return companyKind;
    }

    public void setCompanyKind(String companyKind) {
        this.companyKind = companyKind == null ? null : companyKind.trim();
    }

    public String getInComeAverage() {
        return inComeAverage;
    }

    public void setInComeAverage(String inComeAverage) {
        this.inComeAverage = inComeAverage == null ? null : inComeAverage.trim();
    }

    public String getInComeModel() {
        return inComeModel;
    }

    public void setInComeModel(String inComeModel) {
        this.inComeModel = inComeModel == null ? null : inComeModel.trim();
    }

    public String getInComeCoef() {
        return inComeCoef;
    }

    public void setInComeCoef(String inComeCoef) {
        this.inComeCoef = inComeCoef == null ? null : inComeCoef.trim();
    }

    public String getInComeCust() {
        return inComeCust;
    }

    public void setInComeCust(String inComeCust) {
        this.inComeCust = inComeCust == null ? null : inComeCust.trim();
    }

    public String getLiabilitiesLoan() {
        return liabilitiesLoan;
    }

    public void setLiabilitiesLoan(String liabilitiesLoan) {
        this.liabilitiesLoan = liabilitiesLoan == null ? null : liabilitiesLoan.trim();
    }

    public String getLiabilitiesCard() {
        return liabilitiesCard;
    }

    public void setLiabilitiesCard(String liabilitiesCard) {
        this.liabilitiesCard = liabilitiesCard == null ? null : liabilitiesCard.trim();
    }

    public String getLiabilitiesCurrent() {
        return liabilitiesCurrent;
    }

    public void setLiabilitiesCurrent(String liabilitiesCurrent) {
        this.liabilitiesCurrent = liabilitiesCurrent == null ? null : liabilitiesCurrent.trim();
    }

    public String getPropertyTypeZx() {
        return propertyTypeZx;
    }

    public void setPropertyTypeZx(String propertyTypeZx) {
        this.propertyTypeZx = propertyTypeZx == null ? null : propertyTypeZx.trim();
    }

    public String getPropertyTypeCust() {
        return propertyTypeCust;
    }

    public void setPropertyTypeCust(String propertyTypeCust) {
        this.propertyTypeCust = propertyTypeCust == null ? null : propertyTypeCust.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}