package com.hoomsun.csas.sales.api.model;

public class UserPbccrcHtml {
    private String phId;

    private String applyId;

    private String crId;

    private String htmlData;

    public String getPhId() {
        return phId;
    }

    public void setPhId(String phId) {
        this.phId = phId == null ? null : phId.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getCrId() {
        return crId;
    }

    public void setCrId(String crId) {
        this.crId = crId == null ? null : crId.trim();
    }

    public String getHtmlData() {
        return htmlData;
    }

    public void setHtmlData(String htmlData) {
        this.htmlData = htmlData == null ? null : htmlData.trim();
    }
}