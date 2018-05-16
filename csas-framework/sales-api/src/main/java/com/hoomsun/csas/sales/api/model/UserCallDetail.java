package com.hoomsun.csas.sales.api.model;

public class UserCallDetail {
    private String pdid;

    private String contId;     //手机帐单id

    private String callmoney;   //花费        (0.19)

    private String calladdress;//通话地址  (西安市)

    private String calltype;   //通话方式   (国内被叫)

    private String calltime;   //通话时间 (09-01 21:43:15)

    private String callduration;//通话时长(2分57秒)

    private String callway;     //通话类型   (被叫)

    private String callnumber;  //手机号

    public String getPdid() {
        return pdid;
    }

    public void setPdid(String pdid) {
        this.pdid = pdid == null ? null : pdid.trim();
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

	public String getContId() {
		return contId;
	}

	public void setContId(String contId) {
		this.contId = contId;
	}
}