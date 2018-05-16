package com.hoomsun.core.model;

public class SysRoleCpt {
	private String roleId;

	private String cptId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId == null ? null : roleId.trim();
	}

	public String getCptId() {
		return cptId;
	}

	public void setCptId(String cptId) {
		this.cptId = cptId;
	}

}