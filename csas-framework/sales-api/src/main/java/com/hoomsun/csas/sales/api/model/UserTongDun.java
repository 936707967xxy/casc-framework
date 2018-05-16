package com.hoomsun.csas.sales.api.model;

public class UserTongDun {
    private String id;

    private String applyId;

    private String addDate;

    private String forecastLiveTimeTd;  //居住时间

    private String maritalStatusTd; //婚姻状况

    private String addressTd;   //住址

    private String companyNameTd;  //单位名称

    private String scoreTd; //评分

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

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate == null ? null : addDate.trim();
    }

    public String getForecastLiveTimeTd() {
        return forecastLiveTimeTd;
    }

    public void setForecastLiveTimeTd(String forecastLiveTimeTd) {
        this.forecastLiveTimeTd = forecastLiveTimeTd == null ? null : forecastLiveTimeTd.trim();
    }

    public String getMaritalStatusTd() {
        return maritalStatusTd;
    }

    public void setMaritalStatusTd(String maritalStatusTd) {
        this.maritalStatusTd = maritalStatusTd == null ? null : maritalStatusTd.trim();
    }

    public String getAddressTd() {
        return addressTd;
    }

    public void setAddressTd(String addressTd) {
        this.addressTd = addressTd == null ? null : addressTd.trim();
    }

    public String getCompanyNameTd() {
        return companyNameTd;
    }

    public void setCompanyNameTd(String companyNameTd) {
        this.companyNameTd = companyNameTd == null ? null : companyNameTd.trim();
    }

    public String getScoreTd() {
        return scoreTd;
    }

    public void setScoreTd(String scoreTd) {
        this.scoreTd = scoreTd == null ? null : scoreTd.trim();
    }
}