/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 作者：liusong <br>
 * 创建时间：2017年10月11日 <br>
 * 描述：创建切面拦截实体
 */
@Document(collection = "OPERATING_LOG")
public class SystemLoggger {
	@Id
	private String id;// 主键
	private String loginName;// 登录账号
	private String empName;// 登录人
	private String loginDate;// 登录时间
	private String loginIP;// 登录IP
	private String loginClient;// 登录终端
	private String methodName;// 拦截的方法名称
	private String targets;// 拦截的类
	private String args;// 拦截的方法参数
	private String moduleName;// 拦截的模块名
	private String option;// 拦截的操作内容
	private String backParam;// 备份数据 删除 修改
	private String optionType;// 操作类型

	public SystemLoggger() {
	}

	public SystemLoggger(String id, String loginName, String empName, String loginDate, String loginIP, String loginClient, String methodName, String targets, String args, String moduleName, String option) {
		this.id = id;
		this.loginName = loginName;
		this.empName = empName;
		this.loginDate = loginDate;
		this.loginIP = loginIP;
		this.loginClient = loginClient;
		this.methodName = methodName;
		this.targets = targets;
		this.args = args;
		this.moduleName = moduleName;
		this.option = option;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	public String getLoginClient() {
		return loginClient;
	}

	public void setLoginClient(String loginClient) {
		this.loginClient = loginClient;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getTargets() {
		return targets;
	}

	public void setTargets(String targets) {
		this.targets = targets;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getBackParam() {
		return backParam;
	}

	public void setBackParam(String backParam) {
		this.backParam = backParam;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	@Override
	public String toString() {
		return "SystemLoggger [id=" + id + ", loginName=" + loginName + ", empName=" + empName + ", loginDate=" + loginDate + ", loginIP=" + loginIP + ", loginClient=" + loginClient + ", methodName=" + methodName + ", targets=" + targets
				+ ", args=" + args + ", moduleName=" + moduleName + ", option=" + option + ", backParam=" + backParam + ", optionType=" + optionType + "]";
	}

}
