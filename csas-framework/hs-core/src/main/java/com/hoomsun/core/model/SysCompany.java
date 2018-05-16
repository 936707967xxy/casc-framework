package com.hoomsun.core.model;

public class SysCompany {
    private String comId;

    private String comDesc; // 公司描述

    private String comName;

    private Integer comStatus;

    private String comWorkAddr;

    private String comParent;
    
    // 增加用于回显
    private String comParentName;
    
    public SysCompany() {
	}

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId == null ? null : comId.trim();
    }

    public String getComDesc() {
        return comDesc;
    }

    public void setComDesc(String comDesc) {
        this.comDesc = comDesc == null ? null : comDesc.trim();
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

    public Integer getComStatus() {
        return comStatus;
    }

    public void setComStatus(Integer comStatus) {
        this.comStatus = comStatus;
    }

    public String getComWorkAddr() {
        return comWorkAddr;
    }

    public void setComWorkAddr(String comWorkAddr) {
        this.comWorkAddr = comWorkAddr == null ? null : comWorkAddr.trim();
    }

    public String getComParent() {
        return comParent;
    }

    public void setComParent(String comParent) {
        this.comParent = comParent == null ? null : comParent.trim();
    }

	public String getComParentName() {
		return comParentName;
	}

	public void setComParentName(String comParentName) {
		this.comParentName = comParentName;
	}
    
}