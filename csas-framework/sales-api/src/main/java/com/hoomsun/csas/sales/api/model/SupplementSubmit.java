package com.hoomsun.csas.sales.api.model;

import java.sql.Timestamp;

/**
 * 补充资料提交model
 * @author ygzhao
 *
 */
public class SupplementSubmit {
	private String subPkId;//主键
	
    private String submitTypeText;//提交内容

    private String submitEmpId;//提交员工

    private String submitEmpName;//提交姓名

    private String applyId;//申请表主键

    private Timestamp submitDate;//提交时间

    private String procInstId;//流程实例id

    private String taskId;//任务id
    
    private Integer handleOpinion;//处理id

    private String handleOpinionVal;//处理值

    public String getSubmitTypeText() {
        return submitTypeText;
    }

    public void setSubmitTypeText(String submitTypeText) {
        this.submitTypeText = submitTypeText == null ? null : submitTypeText.trim();
    }

    public String getSubmitEmpId() {
        return submitEmpId;
    }

    public void setSubmitEmpId(String submitEmpId) {
        this.submitEmpId = submitEmpId == null ? null : submitEmpId.trim();
    }

    public String getSubmitEmpName() {
        return submitEmpName;
    }

    public void setSubmitEmpName(String submitEmpName) {
        this.submitEmpName = submitEmpName == null ? null : submitEmpName.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public Timestamp getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Timestamp submitDate) {
        this.submitDate = submitDate;
    }

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getSubPkId() {
		return subPkId;
	}

	public void setSubPkId(String subPkId) {
		this.subPkId = subPkId;
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
		this.handleOpinionVal = handleOpinionVal;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	
}