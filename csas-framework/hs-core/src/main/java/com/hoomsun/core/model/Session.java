/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hoomsun.common.model.Menu;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月10日 <br>
 * 描述：用户的登陆信息
 */
public class Session implements Serializable {
	private static final long serialVersionUID = 8220912244627626080L;
	public static final String KEY = "HSFS_SYSTEM_LOGIN_SESSION_KEY";

	private String empId;
	private String empName;
	private String empWorkNum;
	private Integer empStatus;
	private String deptId;
	private String deptName;
	private Integer deptStatus;
	private String comId;
	private String comName;
	private Integer comStatus;
	private List<SysRole> roles = new ArrayList<SysRole>();
	private List<String> actions = new ArrayList<String>();
	private List<Menu> menus = new ArrayList<Menu>();

	public void addRole(SysRole role) {
		this.roles.add(role);
	}

	public void addAction(String name) {
		this.actions.add(name);
	}

	public void addMenu(Menu menu) {
		this.menus.add(menu);
	}

	public Session() {

	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getComId() {
		return comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}

	public List<String> getActions() {
		return actions;
	}

	public void setActions(List<String> actions) {
		this.actions = actions;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpWorkNum() {
		return empWorkNum;
	}

	public void setEmpWorkNum(String empWorkNum) {
		this.empWorkNum = empWorkNum;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public Integer getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(Integer empStatus) {
		this.empStatus = empStatus;
	}

	public Integer getDeptStatus() {
		return deptStatus;
	}

	public void setDeptStatus(Integer deptStatus) {
		this.deptStatus = deptStatus;
	}

	public Integer getComStatus() {
		return comStatus;
	}

	public void setComStatus(Integer comStatus) {
		this.comStatus = comStatus;
	}

	public List<SysRole> getRoles() {
		return roles;
	}
}
