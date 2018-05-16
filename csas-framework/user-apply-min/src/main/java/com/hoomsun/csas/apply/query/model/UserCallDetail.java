package com.hoomsun.csas.apply.query.model;

public class UserCallDetail {
    private String pdid;

    private String phoneid;

    private String callmoney;

    private String calladdress;

    private String calltype;

    private String calltime;

    private String callduration;

    private String callway;

    private String callnumber;

    public String getPdid() {
        return pdid;
    }

    public void setPdid(String pdid) {
        this.pdid = pdid == null ? null : pdid.trim();
    }

    public String getPhoneid() {
        return phoneid;
    }

    public void setPhoneid(String phoneid) {
        this.phoneid = phoneid == null ? null : phoneid.trim();
    }

    public String getCallmoney() {
        return callmoney;
    }

    public void setCallmoney(String callmoney) {
        this.callmoney = callmoney == null ? null : callmoney.trim();
    }

    public String getCalladdress() {
        return calladdress;
    }

    public void setCalladdress(String calladdress) {
        this.calladdress = calladdress == null ? null : calladdress.trim();
    }

    public String getCalltype() {
        return calltype;
    }

    public void setCalltype(String calltype) {
        this.calltype = calltype == null ? null : calltype.trim();
    }

    public String getCalltime() {
        return calltime;
    }

    public void setCalltime(String calltime) {
        this.calltime = calltime == null ? null : calltime.trim();
    }

    public String getCallduration() {
        return callduration;
    }

    public void setCallduration(String callduration) {
        this.callduration = callduration == null ? null : callduration.trim();
    }

    public String getCallway() {
        return callway;
    }

    public void setCallway(String callway) {
        this.callway = callway == null ? null : callway.trim();
    }

    public String getCallnumber() {
        return callnumber;
    }

    public void setCallnumber(String callnumber) {
        this.callnumber = callnumber == null ? null : callnumber.trim();
    }
}