/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.model.vo;

import java.math.BigDecimal;

/**
 * 作者：liming <br>
 * 创建时间：2017年12月21日 <br>
 * 描述：客户经理报表 
 */
public class SalesmanReportVo {
	
	private String id;
	
	private String bigAreaId; // 事务部
	
	private String bitAreaName; //事务部
	
	private String custName;// 客户姓名
	
	private String areaId; // 大区
	
	private String areaName; // 大区名字
	
	private String cityId; // 城市编号(分部)
	
	private String cityName; // 城市编号(分部)
	
	private String storeId; // 营业部（门店）
	
	private String storeManage;//门店经理
	
	private String storeName; //门店名称
	
	private String teamId;
	
	private String teamName;//团队名称
	
	private String teamManager;//团队经理
	
	private String appoperId;//客户经理id
	
	private String appoperName;//客户经理id

	private String mob;
	
	private Integer record;//放款数 
	
	private BigDecimal loanAmount;//放款额
	
	private BigDecimal conAmount;//合同金额
	
	private Integer loanCount;//进件数  	
	
	private Integer finalBack;//退件数
	
	private Integer  finalAudit;//批核数
	
	private BigDecimal approvedAmount;//批核额 
	
	private BigDecimal  finalConAmount;//批核合同额
	
	private Integer deniedLoans; //拒贷数
	
	private Integer  giveNum;//放弃数
	
	private BigDecimal giveMoney;//放弃额
	
	private BigDecimal giveConAmount; //放弃合同额	

	private String empId;//登陆人员id
	
	private String empname;//登陆人员name
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getMob() {
		return mob;
	}

	public void setMob(String mob) {
		this.mob = mob;
	}

	public Integer getRecord() {
		return record;
	}

	public void setRecord(Integer record) {
		this.record = record;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getConAmount() {
		return conAmount;
	}

	public void setConAmount(BigDecimal conAmount) {
		this.conAmount = conAmount;
	}
	
	
	public Integer getLoanCount() {
		return loanCount;
	}

	public void setLoanCount(Integer loanCount) {
		this.loanCount = loanCount;
	}

	public Integer getFinalBack() {
		return finalBack;
	}

	public void setFinalBack(Integer finalBack) {
		this.finalBack = finalBack;
	}

	public Integer getFinalAudit() {
		return finalAudit;
	}

	public void setFinalAudit(Integer finalAudit) {
		this.finalAudit = finalAudit;
	}

	

	public BigDecimal getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(BigDecimal approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	public BigDecimal getFinalConAmount() {
		return finalConAmount;
	}

	public void setFinalConAmount(BigDecimal finalConAmount) {
		this.finalConAmount = finalConAmount;
	}

	public Integer getDeniedLoans() {
		return deniedLoans;
	}

	public void setDeniedLoans(Integer deniedLoans) {
		this.deniedLoans = deniedLoans;
	}

	public Integer getGiveNum() {
		return giveNum;
	}

	public void setGiveNum(Integer giveNum) {
		this.giveNum = giveNum;
	}

	public BigDecimal getGiveMoney() {
		return giveMoney;
	}

	public void setGiveMoney(BigDecimal giveMoney) {
		this.giveMoney = giveMoney;
	}

	public BigDecimal getGiveConAmount() {
		return giveConAmount;
	}

	public void setGiveConAmount(BigDecimal giveConAmount) {
		this.giveConAmount = giveConAmount;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	
	public String getAppoperId() {
		return appoperId;
	}

	public void setAppoperId(String appoperId) {
		this.appoperId = appoperId;
	}

	public String getAppoperName() {
		return appoperName;
	}

	public void setAppoperName(String appoperName) {
		this.appoperName = appoperName;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreManage() {
		return storeManage;
	}

	public void setStoreManage(String storeManage) {
		this.storeManage = storeManage;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getTeamManager() {
		return teamManager;
	}

	public void setTeamManager(String teamManager) {
		this.teamManager = teamManager;
	}

	public String getBigAreaId() {
		return bigAreaId;
	}

	public void setBigAreaId(String bigAreaId) {
		this.bigAreaId = bigAreaId;
	}

	public String getBitAreaName() {
		return bitAreaName;
	}

	public void setBitAreaName(String bitAreaName) {
		this.bitAreaName = bitAreaName;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}
	
}
