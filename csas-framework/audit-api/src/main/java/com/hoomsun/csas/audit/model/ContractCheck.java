package com.hoomsun.csas.audit.model;

import java.util.Date;
/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月12日 <br>
 * 描述： 合同审核
 */
public class ContractCheck {
    private String checkId;

    private String applyId;

    private String procInstId;

    private String taskId;

    private String checkEmp;

    private String checkEmpName;

    private Integer handleOpinion;

    private String handleOpinionVal;

    private String handleRemarks;

    private Date checkDate;

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId == null ? null : checkId.trim();
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

    public String getCheckEmp() {
        return checkEmp;
    }

    public void setCheckEmp(String checkEmp) {
        this.checkEmp = checkEmp == null ? null : checkEmp.trim();
    }

    public String getCheckEmpName() {
        return checkEmpName;
    }

    public void setCheckEmpName(String checkEmpName) {
        this.checkEmpName = checkEmpName == null ? null : checkEmpName.trim();
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


    public String getHandleRemarks() {
		return handleRemarks;
	}

	public void setHandleRemarks(String handleRemarks) {
		this.handleRemarks = handleRemarks;
	}

	public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }
}