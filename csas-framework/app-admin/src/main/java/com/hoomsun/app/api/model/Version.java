package com.hoomsun.app.api.model;

public class Version {
    private String id;

    private String code;

    private String number;

    private String type;

    private String mintype;

    private String approval;

    private String url;

    private String apksize;

    private String md5code;

    private String updatedes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getMintype() {
        return mintype;
    }

    public void setMintype(String mintype) {
        this.mintype = mintype == null ? null : mintype.trim();
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval == null ? null : approval.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getApksize() {
        return apksize;
    }

    public void setApksize(String apksize) {
        this.apksize = apksize == null ? null : apksize.trim();
    }

    public String getMd5code() {
        return md5code;
    }

    public void setMd5code(String md5code) {
        this.md5code = md5code == null ? null : md5code.trim();
    }

    public String getUpdatedes() {
        return updatedes;
    }

    public void setUpdatedes(String updatedes) {
        this.updatedes = updatedes == null ? null : updatedes.trim();
    }
}