package com.hoomsun.csas.audit.model;

import java.util.Date;

public class ReviewAudit {
    private String reviewId;

    private String applyId;

    private String procInstId;

    private String taskId;

    private String auditEmp;

    private String auditEmpName;

    private Date auditDate;

    private Integer handleOpinion;

    private String handleOpinionVal;

    private String reviewRemark;

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId == null ? null : reviewId.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId == null ? null : procInstId.trim();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public String getAuditEmp() {
        return auditEmp;
    }

    public void setAuditEmp(String auditEmp) {
        this.auditEmp = auditEmp == null ? null : auditEmp.trim();
    }

    public String getAuditEmpName() {
        return auditEmpName;
    }

    public void setAuditEmpName(String auditEmpName) {
        this.auditEmpName = auditEmpName == null ? null : auditEmpName.trim();
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public Integer getHandleOpinion() {
        return handleOpinion;
    }

    public void setHandleOpinion(Integer handleOpinion) {
        this.handleOpinion = handleOpinion;
    }

    public String getHandleOpinionVal() {
        return handleOpinionVal;
    }

    public void setHandleOpinionVal(String handleOpinionVal) {
        this.handleOpinionVal = handleOpinionVal == null ? null : handleOpinionVal.trim();
    }

    public String getReviewRemark() {
        return reviewRemark;
    }

    public void setReviewRemark(String reviewRemark) {
        this.reviewRemark = reviewRemark == null ? null : reviewRemark.trim();
    }
}