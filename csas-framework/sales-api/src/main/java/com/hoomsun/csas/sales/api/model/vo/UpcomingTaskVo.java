/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.model.vo;

import java.util.Date;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月22日 <br>
 * 描述：待办任务列表
 */
public class UpcomingTaskVo {
	private String taskId;// 任务id
	private String businessKey;// 业务id
	private String taskKey;// 任务Key
	private String taskName;// 任务名称
	private String custName;// 客户名称
	private String idNumber;// 证件号
	private String loanId;// 进件编号
	private Date applyDate;// 申请时间
	private String procInstId;
	private String assignee;// 签收人

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

}
