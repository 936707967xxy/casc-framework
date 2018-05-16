/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.model;

import java.util.Date;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月29日 <br>
 * 描述：质检复合记录
 */
public class QualityAudit {
	private String qaId;// 主键
	private Date auditDate;// 审核日期
	private String comment;// 批注信息
	private String taskId;// 任务ID
	private String processId;// 流程实例ID
	private Integer status;// 审核状态 0失效 1有效

	public String getQaId() {
		return qaId;
	}

	public void setQaId(String qaId) {
		this.qaId = qaId;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
