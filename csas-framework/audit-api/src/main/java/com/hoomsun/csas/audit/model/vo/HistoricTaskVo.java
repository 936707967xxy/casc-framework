/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.model.vo;

import java.util.Date;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月21日 <br>
 * 描述：
 */
public class HistoricTaskVo {
	private String taskId;
	private String procInstId;
	private String businessKey;
	private String taskDefKey;
	private String procDefId;
	private String executionId;
	private String name;
	private String parentId;
	private String assignee;
	private Date startTime;
	private Date claitmTime;
	private Date endTime;
	private String duration;
	private String deleteReason;
	private Integer handleOption;
	private String handleOptionVal;
	private String remarks;
	private String money;
	private String product;
	private String empId;
	private String empName;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getClaitmTime() {
		return claitmTime;
	}

	public void setClaitmTime(Date claitmTime) {
		this.claitmTime = claitmTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	public String getHandleOptionVal() {
		return handleOptionVal;
	}

	public void setHandleOptionVal(String handleOptionVal) {
		this.handleOptionVal = handleOptionVal;
	}

	public void setHandleOption(Integer handleOption) {
		this.handleOption = handleOption;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Integer getHandleOption() {
		return handleOption;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

}
