package com.hoomsun.after.api.model.table;

public class PublicSend {
    private String id;

    //推送日期
    private String snedDate;

    //记录数
    private Integer todayCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSnedDate() {
        return snedDate;
    }

    public void setSnedDate(String snedDate) {
        this.snedDate = snedDate == null ? null : snedDate.trim();
    }

    public Integer getTodayCount() {
        return todayCount;
    }

    public void setTodayCount(Integer todayCount) {
        this.todayCount = todayCount;
    }
}