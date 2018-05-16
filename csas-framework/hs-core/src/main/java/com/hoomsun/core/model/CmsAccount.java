package com.hoomsun.core.model;

public class CmsAccount {
    private String id;

    private String accnbr;

    private String bbknbr;

    private String cBbknbr;

    private String lgnnam;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAccnbr() {
        return accnbr;
    }

    public void setAccnbr(String accnbr) {
        this.accnbr = accnbr == null ? null : accnbr.trim();
    }

    public String getBbknbr() {
        return bbknbr;
    }

    public void setBbknbr(String bbknbr) {
        this.bbknbr = bbknbr == null ? null : bbknbr.trim();
    }

    public String getcBbknbr() {
        return cBbknbr;
    }

    public void setcBbknbr(String cBbknbr) {
        this.cBbknbr = cBbknbr == null ? null : cBbknbr.trim();
    }

    public String getLgnnam() {
        return lgnnam;
    }

    public void setLgnnam(String lgnnam) {
        this.lgnnam = lgnnam == null ? null : lgnnam.trim();
    }
}