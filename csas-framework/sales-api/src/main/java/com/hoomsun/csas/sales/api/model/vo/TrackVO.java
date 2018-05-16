/**
 * Copyright www.idawn.org 邹益伟 idawnorg@gmail.com
 */
package com.hoomsun.csas.sales.api.model.vo;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月24日 <br>
 * 描述：流程追踪
 */
public class TrackVO {
	private String taskId;
	private String title;
	private String description;
	private String handelTime;
	private String handelOption;
	private String nodeStatus;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHandelTime() {
		return handelTime;
	}

	public void setHandelTime(String handelTime) {
		this.handelTime = handelTime;
	}

	public String getNodeStatus() {
		return nodeStatus;
	}

	public void setNodeStatus(String nodeStatus) {
		this.nodeStatus = nodeStatus;
	}

	public String getHandelOption() {
		return handelOption;
	}

	public void setHandelOption(String handelOption) {
		this.handelOption = handelOption;
	}

}
