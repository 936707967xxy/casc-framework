package com.hoomsun.core.model;

public class SysComponents {
	private String cptId;

	private String path;

	private String component;

	private String name;

	private String iconcls;

	private Integer hidden;

	private String parentId;

	private String ascription;

	private Integer sort;

	private String redirect;

	private Integer dropdown;

	private String cptType;
	
	private String cptValue;

	public String getCptId() {
		return cptId;
	}

	public void setCptId(String cptId) {
		this.cptId = cptId == null ? null : cptId.trim();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path == null ? null : path.trim();
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component == null ? null : component.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getIconcls() {
		return iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls == null ? null : iconcls.trim();
	}

	public Integer getHidden() {
		return hidden;
	}

	public void setHidden(Integer hidden) {
		this.hidden = hidden;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId == null ? null : parentId.trim();
	}

	public String getAscription() {
		return ascription;
	}

	public void setAscription(String ascription) {
		this.ascription = ascription == null ? null : ascription.trim();
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect == null ? null : redirect.trim();
	}

	public Integer getDropdown() {
		return dropdown;
	}

	public void setDropdown(Integer dropdown) {
		this.dropdown = dropdown;
	}

	public String getCptType() {
		return cptType;
	}

	public void setCptType(String cptType) {
		this.cptType = cptType == null ? null : cptType.trim();
	}

	public String getCptValue() {
		return cptValue;
	}

	public void setCptValue(String cptValue) {
		this.cptValue = cptValue;
	}
}