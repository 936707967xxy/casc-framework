package com.hoomsun.app.api.model;

public class hsCall {
    private String id;

    private Integer oponval;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getOponval() {
        return oponval;
    }

    public void setOponval(Integer oponval) {
        this.oponval = oponval;
    }
}