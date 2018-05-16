package com.hoomsun.app.api.model;


/*
 * 作者：liushuai <br>
 * 创建时间：2017年9月13日 <br>
 * 描述：banner javabean
 * 
 */
public class Banner {
    private String id;

    private String time;

    private String content;

    private String bannerurl;

    private String isopen;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
        
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getBannerurl() {
        return bannerurl;
    }

    public void setBannerurl(String bannerurl) {
        this.bannerurl = bannerurl == null ? null : bannerurl.trim();
    }

    public String getIsopen() {
        return isopen;
    }

    public void setIsopen(String isopen) {
        this.isopen = isopen == null ? null : isopen.trim();
    }
}