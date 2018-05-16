package com.hoomsun.message.model;

public class Notice {
    private String noticeid;

    private String custid;

    private String applyid;

    private String flag;       // 1  2  3  4  5  6

    private String flagVal;   //审核消息  还款消息  系统消息    额度消息  精选活动  认证消息
 
    private String consult;

    private Object noticeData;

    private String message;

    public String getNoticeid() {
        return noticeid;
    }

    public void setNoticeid(String noticeid) {
        this.noticeid = noticeid == null ? null : noticeid.trim();
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid == null ? null : custid.trim();
    }

    public String getApplyid() {
        return applyid;
    }

    public void setApplyid(String applyid) {
        this.applyid = applyid == null ? null : applyid.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getFlagVal() {
        return flagVal;
    }

    public void setFlagVal(String flagVal) {
        this.flagVal = flagVal == null ? null : flagVal.trim();
    }

    public String getConsult() {
        return consult;
    }

    public void setConsult(String consult) {
        this.consult = consult == null ? null : consult.trim();
    }

    public Object getNoticeData() {
		return noticeData;
	}

	public void setNoticeData(Object noticeData) {
		this.noticeData = noticeData;
	}

	public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }
}