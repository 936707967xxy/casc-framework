/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.hoomsun.core.model.SysRole;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月20日 <br>
 * 描述：路由Session
 */
public class SessionRouter {
	public static final String KEY = "AUDIT_SYSTEM_LOGIN_SESSION_KEY";
	
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
	private String storeId; // 营业部ID
	private String storeName; // 营业部名称
	private String deptNo;
	private String storeCityName; // 营业部城市名称
	
	
	private List<SysRole> roles = new ArrayList<SysRole>();
	private List<String> menuRouters = new ArrayList<String>();
	private List<String> actions = new ArrayList<String>();

	public void addMenuRouter(String meun) {
		this.menuRouters.add(meun);
	}

	public void addAction(String action) {
		this.actions.add(action);
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

	public String getEmpWorkNum() {
		return empWorkNum;
	}

	public void setEmpWorkNum(String empWorkNum) {
		this.empWorkNum = empWorkNum;
	}

	public Integer getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(Integer empStatus) {
		this.empStatus = empStatus;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getDeptStatus() {
		return deptStatus;
	}

	public void setDeptStatus(Integer deptStatus) {
		this.deptStatus = deptStatus;
	}

	public String getComId() {
		return comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public Integer getComStatus() {
		return comStatus;
	}

	public void setComStatus(Integer comStatus) {
		this.comStatus = comStatus;
	}
	
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public List<String> getMenuRouters() {
		return menuRouters;
	}

	public void setMenuRouters(List<String> menuRouters) {
		this.menuRouters = menuRouters;
	}

	public List<String> getActions() {
		return actions;
	}

	public void setActions(List<String> actions) {
		this.actions = actions;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	
	public String getStoreCityName() {
		return storeCityName;
	}

	public void setStoreCityName(String storeCityName) {
		this.storeCityName = storeCityName;
	}

	@Override
	public String toString() {
		return "SessionRouter [empId=" + empId + ", empName=" + empName + ", empWorkNum=" + empWorkNum + ", empStatus=" + empStatus + ", deptId=" + deptId + ", deptName=" + deptName + ", deptStatus=" + deptStatus + ", comId=" + comId
				+ ", comName=" + comName + ", comStatus=" + comStatus + ", storeId=" + storeId + ", storeName=" + storeName + ", deptNo=" + deptNo + ", storeCityName=" + storeCityName + ", roles=" + roles + ", menuRouters=" + menuRouters
				+ ", actions=" + actions + "]";
	}
	

}
