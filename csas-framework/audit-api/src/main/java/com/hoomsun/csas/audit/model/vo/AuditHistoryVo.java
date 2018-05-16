/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.model.vo;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月13日 <br>
 * 描述：审核历史 AUDIT_HISTORY_VIEW SELECT QC_ID CK_ID,APPLY_ID ,TASK_ID ,QC_EMP_NAME
 * EMP_NAME,HANDLE_OPINION_VAL,REMARKS FROM HS_QC_CHECK UNION SELECT PRE_ID
 * CK_ID,APPLYID APPLY_ID,TASKID TASK_ID, EMPNAME EMP_NAME,TRIAL_OPTIONVAL
 * HANDLE_OPINION_VAL,REMARK REMARKS FROM HS_PRE_AUDIT UNION SELECT FINAL_ID
 * CK_ID,APPLY_ID,TASK_ID,AUDIT_EMP_NAME
 * EMP_NAME,HANDLE_OPINION_VAL,IN_HANDLE_OPINION REMARKS FROM HS_FINAL_AUDIT
 * UNION SELECT MAKING_ID
 * CK_ID,APPLY_ID,TASK_ID,EMP_NAME,HANDLE_OPINION_VAL,REMARKS FROM HS_MAKING
 * UNION SELECT CHECK_ID CK_ID,APPLY_ID,TASK_ID,CHECK_EMP_NAME
 * EMP_NAME,HANDLE_OPINION_VAL,REMARKS FROM HS_CONTRACT_CHECK;
 */
public class AuditHistoryVo {
	private String taskId;// 任务ID
	private String taskName;
	private String processId;// 流程ID
	private String empId;
	private String auditEmp;// 审核人
	private Integer option;// 处理意见
	private String optionVal;// 处理意见
	private String remark;// 备注
	private String startTime;// 开始时间
	private String assignee;// 签收时间
	private String endTime;// 结束时间
	private String applyId;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getAuditEmp() {
		return auditEmp;
	}

	public void setAuditEmp(String auditEmp) {
		this.auditEmp = auditEmp;
	}

	public Integer getOption() {
		return option;
	}

	public void setOption(Integer option) {
		this.option = option;
	}

	public String getOptionVal() {
		return optionVal;
	}

	public void setOptionVal(String optionVal) {
		this.optionVal = optionVal;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}
