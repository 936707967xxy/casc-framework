package com.hoomsun.core.model;

public class SysProductType {
    private String prodId;

    private String prodCode;

    private String prodName;

    private String effflagVal;

    private String mixCreditAmt;

    private String maxCreditAmt;

    private String productdesc;

    private String amtlimit;

    private String producturl;

    private String maxRate;

    private Short isopen;

    private String isopenVal;

    private String gotourl;

    private Short isonline; //1线上，0线下

    private Short deleteSign;

    private String prodAlias;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode == null ? null : prodCode.trim();
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName == null ? null : prodName.trim();
    }

    public String getEffflagVal() {
        return effflagVal;
    }

    public void setEffflagVal(String effflagVal) {
        this.effflagVal = effflagVal == null ? null : effflagVal.trim();
    }

    public String getMixCreditAmt() {
        return mixCreditAmt;
    }

    public void setMixCreditAmt(String mixCreditAmt) {
        this.mixCreditAmt = mixCreditAmt == null ? null : mixCreditAmt.trim();
    }

    public String getMaxCreditAmt() {
        return maxCreditAmt;
    }

    public void setMaxCreditAmt(String maxCreditAmt) {
        this.maxCreditAmt = maxCreditAmt == null ? null : maxCreditAmt.trim();
    }

    public String getProductdesc() {
        return productdesc;
    }

    public void setProductdesc(String productdesc) {
        this.productdesc = productdesc == null ? null : productdesc.trim();
    }

    public String getAmtlimit() {
        return amtlimit;
    }

    public void setAmtlimit(String amtlimit) {
        this.amtlimit = amtlimit == null ? null : amtlimit.trim();
    }

    public String getProducturl() {
        return producturl;
    }

    public void setProducturl(String producturl) {
        this.producturl = producturl == null ? null : producturl.trim();
    }

    public String getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(String maxRate) {
        this.maxRate = maxRate == null ? null : maxRate.trim();
    }

    public Short getIsopen() {
        return isopen;
    }

    public void setIsopen(Short isopen) {
        this.isopen = isopen;
    }

    public String getIsopenVal() {
        return isopenVal;
    }

    public void setIsopenVal(String isopenVal) {
        this.isopenVal = isopenVal == null ? null : isopenVal.trim();
    }

    public String getGotourl() {
        return gotourl;
    }

    public void setGotourl(String gotourl) {
        this.gotourl = gotourl == null ? null : gotourl.trim();
    }

    public Short getIsonline() {
        return isonline;
    }

    public void setIsonline(Short isonline) {
        this.isonline = isonline;
    }

    public Short getDeleteSign() {
        return deleteSign;
    }

    public void setDeleteSign(Short deleteSign) {
        this.deleteSign = deleteSign;
    }

    public String getProdAlias() {
        return prodAlias;
    }

    public void setProdAlias(String prodAlias) {
        this.prodAlias = prodAlias == null ? null : prodAlias.trim();
    }
}