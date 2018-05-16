package com.hoomsun.csas.sales.api.model;

public class NameAuthenticationInfoWithBLOBs extends NameAuthenticationInfo {
    private String idcardFrontPhoto;

    private String idcardPortraitPhoto;

    private String idcardBackPhoto;

    private String idcardLivingPhoto;

    public String getIdcardFrontPhoto() {
        return idcardFrontPhoto;
    }

    public void setIdcardFrontPhoto(String idcardFrontPhoto) {
        this.idcardFrontPhoto = idcardFrontPhoto == null ? null : idcardFrontPhoto.trim();
    }

    public String getIdcardPortraitPhoto() {
        return idcardPortraitPhoto;
    }

    public void setIdcardPortraitPhoto(String idcardPortraitPhoto) {
        this.idcardPortraitPhoto = idcardPortraitPhoto == null ? null : idcardPortraitPhoto.trim();
    }

    public String getIdcardBackPhoto() {
        return idcardBackPhoto;
    }

    public void setIdcardBackPhoto(String idcardBackPhoto) {
        this.idcardBackPhoto = idcardBackPhoto == null ? null : idcardBackPhoto.trim();
    }

    public String getIdcardLivingPhoto() {
        return idcardLivingPhoto;
    }

    public void setIdcardLivingPhoto(String idcardLivingPhoto) {
        this.idcardLivingPhoto = idcardLivingPhoto == null ? null : idcardLivingPhoto.trim();
    }
}