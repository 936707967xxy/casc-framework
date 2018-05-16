package com.hoomsun.csas.sales.api.model;

import java.math.BigDecimal;

public class CmrateProduct {
    private String cmrateId;

    private String effdate;

    private String currsign;

    private String currsignVal;

    private String ratecode;

    private String prodid;

    private String prodname;

    private BigDecimal basicinterate;

    private BigDecimal basicinterateyear;

    private BigDecimal basicfinerate;

    private BigDecimal defaultrate;

    private BigDecimal mindefaultrate;

    private BigDecimal urgentrate;

    private String fileno;

    private BigDecimal prodfee;

    private BigDecimal auditfeepro;

    private BigDecimal servfeepro;

    private BigDecimal consfeepro;

    private String moreterm;

    private BigDecimal refundpro;

    private String effflag;

    private String effflagVal;

    private Long mixCreditAmt;

    private Long maxCreditAmt;

    private BigDecimal promonthrate;

    private BigDecimal riskRate;

    private String newStatus;

    private String newStatusVal;

    private String sterm;

    public String getCmrateId() {
        return cmrateId;
    }

    public void setCmrateId(String cmrateId) {
        this.cmrateId = cmrateId == null ? null : cmrateId.trim();
    }

    public String getEffdate() {
        return effdate;
    }

    public void setEffdate(String effdate) {
        this.effdate = effdate == null ? null : effdate.trim();
    }

    public String getCurrsign() {
        return currsign;
    }

    public void setCurrsign(String currsign) {
        this.currsign = currsign == null ? null : currsign.trim();
    }

    public String getCurrsignVal() {
        return currsignVal;
    }

    public void setCurrsignVal(String currsignVal) {
        this.currsignVal = currsignVal == null ? null : currsignVal.trim();
    }

    public String getRatecode() {
        return ratecode;
    }

    public void setRatecode(String ratecode) {
        this.ratecode = ratecode == null ? null : ratecode.trim();
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid == null ? null : prodid.trim();
    }

    public String getProdname() {
        return prodname;
    }

    public void setProdname(String prodname) {
        this.prodname = prodname == null ? null : prodname.trim();
    }

    public BigDecimal getBasicinterate() {
        return basicinterate;
    }

    public void setBasicinterate(BigDecimal basicinterate) {
        this.basicinterate = basicinterate;
    }

    public BigDecimal getBasicinterateyear() {
        return basicinterateyear;
    }

    public void setBasicinterateyear(BigDecimal basicinterateyear) {
        this.basicinterateyear = basicinterateyear;
    }

    public BigDecimal getBasicfinerate() {
        return basicfinerate;
    }

    public void setBasicfinerate(BigDecimal basicfinerate) {
        this.basicfinerate = basicfinerate;
    }

    public BigDecimal getDefaultrate() {
        return defaultrate;
    }

    public void setDefaultrate(BigDecimal defaultrate) {
        this.defaultrate = defaultrate;
    }

    public BigDecimal getMindefaultrate() {
        return mindefaultrate;
    }

    public void setMindefaultrate(BigDecimal mindefaultrate) {
        this.mindefaultrate = mindefaultrate;
    }

    public BigDecimal getUrgentrate() {
        return urgentrate;
    }

    public void setUrgentrate(BigDecimal urgentrate) {
        this.urgentrate = urgentrate;
    }

    public String getFileno() {
        return fileno;
    }

    public void setFileno(String fileno) {
        this.fileno = fileno == null ? null : fileno.trim();
    }

    public BigDecimal getProdfee() {
        return prodfee;
    }

    public void setProdfee(BigDecimal prodfee) {
        this.prodfee = prodfee;
    }

    public BigDecimal getAuditfeepro() {
        return auditfeepro;
    }

    public void setAuditfeepro(BigDecimal auditfeepro) {
        this.auditfeepro = auditfeepro;
    }

    public BigDecimal getServfeepro() {
        return servfeepro;
    }

    public void setServfeepro(BigDecimal servfeepro) {
        this.servfeepro = servfeepro;
    }

    public BigDecimal getConsfeepro() {
        return consfeepro;
    }

    public void setConsfeepro(BigDecimal consfeepro) {
        this.consfeepro = consfeepro;
    }

    public String getMoreterm() {
        return moreterm;
    }

    public void setMoreterm(String moreterm) {
        this.moreterm = moreterm == null ? null : moreterm.trim();
    }

    public BigDecimal getRefundpro() {
        return refundpro;
    }

    public void setRefundpro(BigDecimal refundpro) {
        this.refundpro = refundpro;
    }

    public String getEffflag() {
        return effflag;
    }

    public void setEffflag(String effflag) {
        this.effflag = effflag == null ? null : effflag.trim();
    }

    public String getEffflagVal() {
        return effflagVal;
    }

    public void setEffflagVal(String effflagVal) {
        this.effflagVal = effflagVal == null ? null : effflagVal.trim();
    }

    public Long getMixCreditAmt() {
        return mixCreditAmt;
    }

    public void setMixCreditAmt(Long mixCreditAmt) {
        this.mixCreditAmt = mixCreditAmt;
    }

    public Long getMaxCreditAmt() {
        return maxCreditAmt;
    }

    public void setMaxCreditAmt(Long maxCreditAmt) {
        this.maxCreditAmt = maxCreditAmt;
    }

    public BigDecimal getPromonthrate() {
        return promonthrate;
    }

    public void setPromonthrate(BigDecimal promonthrate) {
        this.promonthrate = promonthrate;
    }

    public BigDecimal getRiskRate() {
        return riskRate;
    }

    public void setRiskRate(BigDecimal riskRate) {
        this.riskRate = riskRate;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus == null ? null : newStatus.trim();
    }

    public String getNewStatusVal() {
        return newStatusVal;
    }

    public void setNewStatusVal(String newStatusVal) {
        this.newStatusVal = newStatusVal == null ? null : newStatusVal.trim();
    }

    public String getSterm() {
        return sterm;
    }

    public void setSterm(String sterm) {
        this.sterm = sterm == null ? null : sterm.trim();
    }
}