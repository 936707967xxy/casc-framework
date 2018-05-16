package com.hoomsun.csas.sales.api.model;

import java.sql.Timestamp;

/**
 * 
 * 作者：liming<br>
 * 创建时间：2017年12月8日 <br>
 * 描述： 回访
 *
 */
public class Visitor {
    private String visId;

    private String visFkid;

    private Timestamp visTime;

    private String visRecord;

    public String getVisId() {
        return visId;
    }

    public void setVisId(String visId) {
        this.visId = visId == null ? null : visId.trim();
    }

    public String getVisFkid() {
        return visFkid;
    }

    public void setVisFkid(String visFkid) {
        this.visFkid = visFkid == null ? null : visFkid.trim();
    }

    public Timestamp getVisTime() {
        return visTime;
    }

    public void setVisTime(Timestamp visTime) {
        this.visTime = visTime;
    }

    public String getVisRecord() {
        return visRecord;
    }

    public void setVisRecord(String visRecord) {
        this.visRecord = visRecord == null ? null : visRecord.trim();
    }
}