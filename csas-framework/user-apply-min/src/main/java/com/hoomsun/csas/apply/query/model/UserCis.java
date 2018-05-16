package com.hoomsun.csas.apply.query.model;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月3日 <br>
 * 描述： 上海资信
 *
 */
public class UserCis {
    private String cisId;

    private String addDate;

    private String lynzxcxs;//6月内资信查询次数

    private String zgyqQs;//最高逾期期数

    private String zgyqJe;//最高逾期金额

    private String dqyqZe;//当前逾期总额

    private String dkye;//贷款余额

    private String wjqbs;//未结清贷款笔数

    private String applyId;

    public String getCisId() {
        return cisId;
    }

    public void setCisId(String cisId) {
        this.cisId = cisId == null ? null : cisId.trim();
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate == null ? null : addDate.trim();
    }

    public String getLynzxcxs() {
        return lynzxcxs;
    }

    public void setLynzxcxs(String lynzxcxs) {
        this.lynzxcxs = lynzxcxs == null ? null : lynzxcxs.trim();
    }

    public String getZgyqQs() {
        return zgyqQs;
    }

    public void setZgyqQs(String zgyqQs) {
        this.zgyqQs = zgyqQs == null ? null : zgyqQs.trim();
    }

    public String getZgyqJe() {
        return zgyqJe;
    }

    public void setZgyqJe(String zgyqJe) {
        this.zgyqJe = zgyqJe == null ? null : zgyqJe.trim();
    }

    public String getDqyqZe() {
        return dqyqZe;
    }

    public void setDqyqZe(String dqyqZe) {
        this.dqyqZe = dqyqZe == null ? null : dqyqZe.trim();
    }

    public String getDkye() {
        return dkye;
    }

    public void setDkye(String dkye) {
        this.dkye = dkye == null ? null : dkye.trim();
    }

    public String getWjqbs() {
        return wjqbs;
    }

    public void setWjqbs(String wjqbs) {
        this.wjqbs = wjqbs == null ? null : wjqbs.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }
}