package com.hoomsun.app.api.model;

public class AuthenticationUrl {
    private String id;

    private String fkId;

    private String urlPhotoget;

    private String urlBackcard;

    private String urlPhotoliving;

    private String urlFrontcard;

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

    public String getUrlPhotoget() {
        return urlPhotoget;
    }

    public void setUrlPhotoget(String urlPhotoget) {
        this.urlPhotoget = urlPhotoget == null ? null : urlPhotoget.trim();
    }

    public String getUrlBackcard() {
        return urlBackcard;
    }

    public void setUrlBackcard(String urlBackcard) {
        this.urlBackcard = urlBackcard == null ? null : urlBackcard.trim();
    }

    public String getUrlPhotoliving() {
        return urlPhotoliving;
    }

    public void setUrlPhotoliving(String urlPhotoliving) {
        this.urlPhotoliving = urlPhotoliving == null ? null : urlPhotoliving.trim();
    }

    public String getUrlFrontcard() {
        return urlFrontcard;
    }

    public void setUrlFrontcard(String urlFrontcard) {
        this.urlFrontcard = urlFrontcard == null ? null : urlFrontcard.trim();
    }
}