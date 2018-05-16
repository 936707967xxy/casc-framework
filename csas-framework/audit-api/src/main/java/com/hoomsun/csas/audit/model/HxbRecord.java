package com.hoomsun.csas.audit.model;

import java.math.BigDecimal;
import java.util.Date;

public class HxbRecord {
    private String recordId;

    private String applyId;

    private BigDecimal channelAmount;

    private BigDecimal contractAmount;

    private Date recordTime;

    private String productName;

    private String productId;

    private String custName;

    private Integer productMonth;

    private String pushType;//  1----借款导入接口     2---确认招标接口      3---满标放款推送接口    4---流标推送接口

    private String fialReson;

    private String pushTypeVal;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public BigDecimal getChannelAmount() {
        return channelAmount;
    }

    public void setChannelAmount(BigDecimal channelAmount) {
        this.channelAmount = channelAmount;
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    public Integer getProductMonth() {
        return productMonth;
    }

    public void setProductMonth(Integer productMonth) {
        this.productMonth = productMonth;
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType == null ? null : pushType.trim();
    }

    public String getFialReson() {
        return fialReson;
    }

    public void setFialReson(String fialReson) {
        this.fialReson = fialReson == null ? null : fialReson.trim();
    }

    public String getPushTypeVal() {
        return pushTypeVal;
    }

    public void setPushTypeVal(String pushTypeVal) {
        this.pushTypeVal = pushTypeVal == null ? null : pushTypeVal.trim();
    }
}