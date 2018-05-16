package com.hoomsun.core.model;

import java.util.Date;
import java.util.List;

public class SysEmployee {
	private String empId;

	private String empName;

	private String empWorkNum;

	private String empPwd;

	private String empSex;

	private Long empAge;

	private String empCert;

	private String empEmail;

	private String empMobile;

	private String empAddress;

	private Integer empStatus; // 0伪删除，1离职,2 停用,3正常

	private String addDate;

	private String addEmp;

	private String modifyDate;

	private String modifyEmp;

	private String deptId;

	private String comId;

	private String jobId;

	private String empStatusVal;

	private String oaId;

	private Date leaveDate;

	private Date entryDate;

	private Date lockDate;

	private String empScope; // 员工作用域
	// 增加用于回显
	private String comName;
	// 增加用于回显
	private String deptName;
	private String deptNo;
	// 增加用于回显
	private String jobName;
	// 增加用于显示作用域名称
	private String empScopeName;

	private List<SysRole> roles;
	private SysCompany company;
	private SysDepartment department;
	private SysJob job;

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId == null ? null : empId.trim();
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName == null ? null : empName.trim();
	}

	public String getEmpWorkNum() {
		return empWorkNum;
	}

	public void setEmpWorkNum(String empWorkNum) {
		this.empWorkNum = empWorkNum == null ? null : empWorkNum.trim();
	}

	public String getEmpPwd() {
		return empPwd;
	}

	public void setEmpPwd(String empPwd) {
		this.empPwd = empPwd == null ? null : empPwd.trim();
	}

	public String getEmpSex() {
		return empSex;
	}

	public void setEmpSex(String empSex) {
		this.empSex = empSex == null ? null : empSex.trim();
	}

	public Long getEmpAge() {
		return empAge;
	}

	public void setEmpAge(Long empAge) {
		this.empAge = empAge;
	}

	public String getEmpCert() {
		return empCert;
	}

	public void setEmpCert(String empCert) {
		this.empCert = empCert == null ? null : empCert.trim();
	}

	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail == null ? null : empEmail.trim();
	}

	public String getEmpMobile() {
		return empMobile;
	}

	public void setEmpMobile(String empMobile) {
		this.empMobile = empMobile == null ? null : empMobile.trim();
	}

	public String getEmpAddress() {
		return empAddress;
	}

	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress == null ? null : empAddress.trim();
	}

	public Integer getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(Integer empStatus) {
		this.empStatus = empStatus;
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

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId == null ? null : deptId.trim();
	}

	public String getComId() {
		return comId;
	}

	public void setComId(String comId) {
		this.comId = comId == null ? null : comId.trim();
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId == null ? null : jobId.trim();
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName == null ? null : deptName.trim();
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName == null ? null : comName.trim();
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName == null ? null : jobName.trim();
	}

	public String getEmpStatusVal() {
		return empStatusVal;
	}

	public void setEmpStatusVal(String empStatusVal) {
		this.empStatusVal = empStatusVal == null ? null : empStatusVal.trim();
	}

	public String getOaId() {
		return oaId;
	}

	public void setOaId(String oaId) {
		this.oaId = oaId == null ? null : oaId.trim();
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Date getLockDate() {
		return lockDate;
	}

	public void setLockDate(Date lockDate) {
		this.lockDate = lockDate;
	}

	public String getEmpScope() {
		return empScope;
	}

	public void setEmpScope(String empScope) {
		this.empScope = empScope == null ? null : empScope.trim();
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public SysCompany getCompany() {
		return company;
	}

	public void setCompany(SysCompany company) {
		this.company = company;
	}

	public SysDepartment getDepartment() {
		return department;
	}

	public void setDepartment(SysDepartment department) {
		this.department = department;
	}

	public SysJob getJob() {
		return job;
	}

	public void setJob(SysJob job) {
		this.job = job;
	}

	public String getEmpScopeName() {
		return empScopeName;
	}

	public void setEmpScopeName(String empScopeName) {
		this.empScopeName = empScopeName;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
}