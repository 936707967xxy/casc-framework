/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.model.vo;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年12月22日 <br>
 * 描述：
 */
public class ParentDeptInfoVo {
	
	private String id;//部门id
	
	private String deptName;//部门名称
	
	private String deptManager;//部门经理

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptManager() {
		return deptManager;
	}

	public void setDeptManager(String deptManager) {
		this.deptManager = deptManager;
	}
	
}
