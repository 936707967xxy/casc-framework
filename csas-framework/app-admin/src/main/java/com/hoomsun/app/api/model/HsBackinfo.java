package com.hoomsun.app.api.model;

public class HsBackinfo {
    private String id;

    private String fkId;

    private Object addData;

    private String backInfo;

    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFkId() {
        return fkId;
    }

    public void setFkId(String fkId) {
        this.fkId = fkId == null ? null : fkId.trim();
    }

    public Object getAddData() {
        return addData;
    }

    public void setAddData(Object addData) {
        this.addData = addData;
    }

    public String getBackInfo() {
        return backInfo;
    }

    public void setBackInfo(String backInfo) {
        this.backInfo = backInfo == null ? null : backInfo.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
}