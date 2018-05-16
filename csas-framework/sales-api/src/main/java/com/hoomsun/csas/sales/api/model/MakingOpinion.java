package com.hoomsun.csas.sales.api.model;

import java.util.Date;

public class MakingOpinion {
    private String makingId;

    private String applyId;

    private String procInstId;

    private String precessStatus;

    private String precessStatusVal;

    private String empId;

    private Date makingTimes;

    private Integer handleOpinion;

    private String handleOpinionVal;

    private String remarks;

    private String taskId;

    private String empName;

    public String getMakingId() {
        return makingId;
    }

    public void setMakingId(String makingId) {
        this.makingId = makingId == null ? null : makingId.trim();
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

    public String getPrecessStatus() {
        return precessStatus;
    }

    public void setPrecessStatus(String precessStatus) {
        this.precessStatus = precessStatus == null ? null : precessStatus.trim();
    }

    public String getPrecessStatusVal() {
        return precessStatusVal;
    }

    public void setPrecessStatusVal(String precessStatusVal) {
        this.precessStatusVal = precessStatusVal == null ? null : precessStatusVal.trim();
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId == null ? null : empId.trim();
    }

    public Date getMakingTimes() {
        return makingTimes;
    }

    public void setMakingTimes(Date makingTimes) {
        this.makingTimes = makingTimes;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName == null ? null : empName.trim();
    }
}