package com.hoomsun.core.model;

import java.math.BigDecimal;

public class HxbRate {
    private String hoomxbId;//主键

    private String applyId;//申请id

    private BigDecimal hxbServiceFee;//红小宝服务费

    private BigDecimal channelServiceFee;//至信服务费

    private BigDecimal creditServiceFee;//第三方服务费

    public String getHoomxbId() {
        return hoomxbId;
    }

    public void setHoomxbId(String hoomxbId) {
        this.hoomxbId = hoomxbId == null ? null : hoomxbId.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public BigDecimal getHxbServiceFee() {
        return hxbServiceFee;
    }

    public void setHxbServiceFee(BigDecimal hxbServiceFee) {
        this.hxbServiceFee = hxbServiceFee;
    }

    public BigDecimal getChannelServiceFee() {
        return channelServiceFee;
    }

    public void setChannelServiceFee(BigDecimal channelServiceFee) {
        this.channelServiceFee = channelServiceFee;
    }

    public BigDecimal getCreditServiceFee() {
        return creditServiceFee;
    }

    public void setCreditServiceFee(BigDecimal creditServiceFee) {
        this.creditServiceFee = creditServiceFee;
    }
}