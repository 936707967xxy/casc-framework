package com.hoomsun.core.model;

public class Bankinfo {
    private String pid;    //主见  

    private String bankid; //恒丰标实

    private String bankname;//银行名

    private Double limitamt;//限额

    private String remark; //标实

    private String isHf;  //是否支持恒丰

    private String isBf;  //是否支持宝富

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid == null ? null : bankid.trim();
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname == null ? null : bankname.trim();
    }

    public Double getLimitamt() {
        return limitamt;
    }

    public void setLimitamt(Double limitamt) {
        this.limitamt = limitamt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIsHf() {
        return isHf;
    }

    public void setIsHf(String isHf) {
        this.isHf = isHf == null ? null : isHf.trim();
    }

    public String getIsBf() {
        return isBf;
    }

    public void setIsBf(String isBf) {
        this.isBf = isBf == null ? null : isBf.trim();
    }
}