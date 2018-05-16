package com.hoomsun.core.model;

import java.util.List;

public class SysRole {
	private String roleId;

	private String roleName;

	private String addDate;

	private String addEmp;

	private String modifyDate;

	private String modifyEmp;

	private String roleDesc;

	private String ascription;
	
	private List<SysResources> resources;

	private List<SysComponents> components;

	public SysRole() {
	}

	public SysRole(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId == null ? null : roleId.trim();
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName == null ? null : roleName.trim();
	}

	public String getAddDate() {
		return addDate;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate == null ? null : addDate.trim();
	}

	public String getAddEmp() {
		return addEmp;
	}

	public void setAddEmp(String addEmp) {
		this.addEmp = addEmp == null ? null : addEmp.trim();
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate == null ? null : modifyDate.trim();
	}

	public String getModifyEmp() {
		return modifyEmp;
	}

	public void setModifyEmp(String modifyEmp) {
		this.modifyEmp = modifyEmp == null ? null : modifyEmp.trim();
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc == null ? null : roleDesc.trim();
	}

	public List<SysResources> getResources() {
		return resources;
	}

	public void setResources(List<SysResources> resources) {
		this.resources = resources;
	}

	public List<SysComponents> getComponents() {
		return components;
	}

	public void setComponents(List<SysComponents> components) {
		this.components = components;
	}
	
	public String getAscription() {
        return ascription;
    }

    public void setAscription(String ascription) {
        this.ascription = ascription == null ? null : ascription.trim();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysRole other = (SysRole) obj;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SysRole [roleId=" + roleId + ", roleName=" + roleName + ", addDate=" + addDate + ", addEmp=" + addEmp + ", modifyDate=" + modifyDate + ", modifyEmp=" + modifyEmp + ", roleDesc=" + roleDesc + ", resources=" + resources
				+ ", components=" + components + "]";
	}

}