package com.hoomsun.app.api.model;

import java.util.Date;

public class Income {
    private String incomeid;

    private String fkId;

    private String incomepath;

    private Date addData;

    public String getIncomeid() {
        return incomeid;
    }

    public void setIncomeid(String incomeid) {
        this.incomeid = incomeid == null ? null : incomeid.trim();
    }

    public String getFkId() {
        return fkId;
    }

    public void setFkId(String fkId) {
        this.fkId = fkId == null ? null : fkId.trim();
    }

    public String getIncomepath() {
        return incomepath;
    }

    public void setIncomepath(String incomepath) {
        this.incomepath = incomepath == null ? null : incomepath.trim();
    }

    public Date getAddData() {
        return addData;
    }

    public void setAddData(Date addData) {
        this.addData = addData;
    }
}