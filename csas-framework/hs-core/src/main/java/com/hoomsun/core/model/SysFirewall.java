package com.hoomsun.core.model;

import java.util.Date;

public class SysFirewall {
    private String fwId;

    private String ipAddr;

    private String macAddr;

    private Integer fwType;

    private String fwDesc;

    private Date createTime;

    private String createEmp;

    public String getFwId() {
        return fwId;
    }

    public void setFwId(String fwId) {
        this.fwId = fwId == null ? null : fwId.trim();
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr == null ? null : ipAddr.trim();
    }

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr == null ? null : macAddr.trim();
    }

    public Integer getFwType() {
        return fwType;
    }

    public void setFwType(Integer fwType) {
        this.fwType = fwType;
    }

    public String getFwDesc() {
        return fwDesc;
    }

    public void setFwDesc(String fwDesc) {
        this.fwDesc = fwDesc == null ? null : fwDesc.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateEmp() {
        return createEmp;
    }

    public void setCreateEmp(String createEmp) {
        this.createEmp = createEmp == null ? null : createEmp.trim();
    }
}