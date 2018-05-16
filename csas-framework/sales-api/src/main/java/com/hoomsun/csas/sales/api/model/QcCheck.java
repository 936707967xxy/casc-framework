package com.hoomsun.csas.sales.api.model;

import java.util.Date;

public class QcCheck {
    private String qcId;

    private String applyId;

    private String procInstId;

    private String taskId;

    private String qcEmp;

    private String qcEmpName;

    private Short handleOpinion;

    private String handleOpinionVal;

    private String remarks;

    private Date qcDate;

    public String getQcId() {
        return qcId;
    }

    public void setQcId(String qcId) {
        this.qcId = qcId == null ? null : qcId.trim();
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

    public String getQcEmp() {
        return qcEmp;
    }

    public void setQcEmp(String qcEmp) {
        this.qcEmp = qcEmp == null ? null : qcEmp.trim();
    }

    public String getQcEmpName() {
        return qcEmpName;
    }

    public void setQcEmpName(String qcEmpName) {
        this.qcEmpName = qcEmpName == null ? null : qcEmpName.trim();
    }

    public Short getHandleOpinion() {
        return handleOpinion;
    }

    public void setHandleOpinion(Short handleOpinion) {
        this.handleOpinion = handleOpinion;
    }

    public String getHandleOpinionVal() {
        return handleOpinionVal;
    }

    public void setHandleOpinionVal(String handleOpinionVal) {
        this.handleOpinionVal = handleOpinionVal == null ? null : handleOpinionVal.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Date getQcDate() {
        return qcDate;
    }

    public void setQcDate(Date qcDate) {
        this.qcDate = qcDate;
    }
}