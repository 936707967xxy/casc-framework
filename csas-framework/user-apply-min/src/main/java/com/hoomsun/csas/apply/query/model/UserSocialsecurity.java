package com.hoomsun.csas.apply.query.model;
/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月3日 <br>
 * 描述： 社保
 *
 */
public class UserSocialsecurity {
    private String siId;

    private String addDate;

    private String siCom;//缴费单位

    private String siBase;//当年缴费基数

    private String siSumAmount;//当前个人账户累积存储额

    private String siSumMonths;//社保断缴累计月数

    private String siMonths;//社保缴纳总月数

    private String siStatus;//缴费状态

    private String queryStatus;//是否查询成功

    private String applyId;

    public String getSiId() {
        return siId;
    }

    public void setSiId(String siId) {
        this.siId = siId == null ? null : siId.trim();
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate == null ? null : addDate.trim();
    }

    public String getSiCom() {
        return siCom;
    }

    public void setSiCom(String siCom) {
        this.siCom = siCom == null ? null : siCom.trim();
    }

    public String getSiBase() {
        return siBase;
    }

    public void setSiBase(String siBase) {
        this.siBase = siBase == null ? null : siBase.trim();
    }

    public String getSiSumAmount() {
        return siSumAmount;
    }

    public void setSiSumAmount(String siSumAmount) {
        this.siSumAmount = siSumAmount == null ? null : siSumAmount.trim();
    }

    public String getSiSumMonths() {
        return siSumMonths;
    }

    public void setSiSumMonths(String siSumMonths) {
        this.siSumMonths = siSumMonths == null ? null : siSumMonths.trim();
    }

    public String getSiMonths() {
        return siMonths;
    }

    public void setSiMonths(String siMonths) {
        this.siMonths = siMonths == null ? null : siMonths.trim();
    }

    public String getSiStatus() {
        return siStatus;
    }

    public void setSiStatus(String siStatus) {
        this.siStatus = siStatus == null ? null : siStatus.trim();
    }

    public String getQueryStatus() {
        return queryStatus;
    }

    public void setQueryStatus(String queryStatus) {
        this.queryStatus = queryStatus == null ? null : queryStatus.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }
}