package com.hoomsun.app.api.model;

public class HotHeadline {
    private String hotId;    //主键

    private String headline; //标题

    private String contentUrl;  //链接

    private Integer visitCount; //浏览次数 

    private String time;        //  发布/更新时间

    private String type;       //类型

    private String isopen;     //是否开启

    private String content;   //内容 

    public String getHotId() {
        return hotId;
    }

    public void setHotId(String hotId) {
        this.hotId = hotId == null ? null : hotId.trim();
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline == null ? null : headline.trim();
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl == null ? null : contentUrl.trim();
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getIsopen() {
        return isopen;
    }

    public void setIsopen(String isopen) {
        this.isopen = isopen == null ? null : isopen.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}