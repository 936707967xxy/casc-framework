/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.model.vo;

import java.util.Date;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月30日 <br>
 * 描述：
 */
public class UserApplyVo {
	private String applyId;
	private String loanId;
	private String idNumber;
	private String custName;
	private Date applyDate;
	private String graduateInstitutions;// 毕业院校
	private Integer raisePerson;// 供养人数
	private Integer childNumber;// 子女数目
	private String maritalStatusVal;// 婚姻状况
	private Date startResidenceDate;// 来借款申请地时间
	private Date comeHereDate;// 现居住地居住时间
	private String eduBackgroundVal;// 教育程度 EDU_BACKGROUND_VAL
	private String rresidenceAddress;// 户籍所在地 RRESIDENCE_ADDRESS
	private String houseAddress;// 现居住地 HOUSE_ADDRESS
	private String liveConditionsVal;// 居住状况 LIVE_CONDITIONS_VAL
	private String propertyTypeVal;// 房产信息PROPERTY_TYPE_VAL
	private String carHavaVal;// 拥有车辆CAR_HAVA_VAL
	private String fullWorkName;// 工作单位全称FULL_WORK_NAME
	private String industryInVal;// 所属行业INDUSTRY_IN_VAL
	private String companyAddress;// 单位地址COMPANY_ADDRESS
	private String companyKindVa;// 单位性质COMPANY_KIND_VA
	private String positionVal;// 职级POSITION_VAL
	private String jobTitle;// 职务名称JOB_TITLE
	private String startWorkTime;// 进入现单位时间START_WORK_TIME
	private String socialSecurityVal;// 社保情况SOCIAL_SECURITY_VAL
	private String salaryMonthly;// 每月薪金SALARY_MONTHLY
	private String othterIncomeMonthly;// 其他收入（月均）OTHTER_INCOME_MONTHLY
	private String privateTypeVal;// 私营企业类型PRIVATE_TYPE_VAL
	private String companyRegtime;// 企业成立时间COMPANY_REGTIME
	private String percentageShares;// 股份占比PERCENTAGE_SHARES
	private String employeesNum;// 员工人数EMPLOYEES_NUM
	private String premisesVal;// 经营场所PREMISES_VAL
	private String applyAddress;// 申请地

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getGraduateInstitutions() {
		return graduateInstitutions;
	}

	public void setGraduateInstitutions(String graduateInstitutions) {
		this.graduateInstitutions = graduateInstitutions;
	}

	public Integer getRaisePerson() {
		return raisePerson;
	}

	public void setRaisePerson(Integer raisePerson) {
		this.raisePerson = raisePerson;
	}

	public Integer getChildNumber() {
		return childNumber;
	}

	public void setChildNumber(Integer childNumber) {
		this.childNumber = childNumber;
	}

	public String getMaritalStatusVal() {
		return maritalStatusVal;
	}

	public void setMaritalStatusVal(String maritalStatusVal) {
		this.maritalStatusVal = maritalStatusVal;
	}

	public Date getStartResidenceDate() {
		return startResidenceDate;
	}

	public void setStartResidenceDate(Date startResidenceDate) {
		this.startResidenceDate = startResidenceDate;
	}

	public Date getComeHereDate() {
		return comeHereDate;
	}

	public void setComeHereDate(Date comeHereDate) {
		this.comeHereDate = comeHereDate;
	}

	public String getEduBackgroundVal() {
		return eduBackgroundVal;
	}

	public void setEduBackgroundVal(String eduBackgroundVal) {
		this.eduBackgroundVal = eduBackgroundVal;
	}

	public String getRresidenceAddress() {
		return rresidenceAddress;
	}

	public void setRresidenceAddress(String rresidenceAddress) {
		this.rresidenceAddress = rresidenceAddress;
	}

	public String getHouseAddress() {
		return houseAddress;
	}

	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}

	public String getLiveConditionsVal() {
		return liveConditionsVal;
	}

	public void setLiveConditionsVal(String liveConditionsVal) {
		this.liveConditionsVal = liveConditionsVal;
	}

	public String getPropertyTypeVal() {
		return propertyTypeVal;
	}

	public void setPropertyTypeVal(String propertyTypeVal) {
		this.propertyTypeVal = propertyTypeVal;
	}

	public String getCarHavaVal() {
		return carHavaVal;
	}

	public void setCarHavaVal(String carHavaVal) {
		this.carHavaVal = carHavaVal;
	}

	public String getFullWorkName() {
		return fullWorkName;
	}

	public void setFullWorkName(String fullWorkName) {
		this.fullWorkName = fullWorkName;
	}

	public String getIndustryInVal() {
		return industryInVal;
	}

	public void setIndustryInVal(String industryInVal) {
		this.industryInVal = industryInVal;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyKindVa() {
		return companyKindVa;
	}

	public void setCompanyKindVa(String companyKindVa) {
		this.companyKindVa = companyKindVa;
	}

	public String getPositionVal() {
		return positionVal;
	}

	public void setPositionVal(String positionVal) {
		this.positionVal = positionVal;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getStartWorkTime() {
		return startWorkTime;
	}

	public void setStartWorkTime(String startWorkTime) {
		this.startWorkTime = startWorkTime;
	}

	public String getSocialSecurityVal() {
		return socialSecurityVal;
	}

	public void setSocialSecurityVal(String socialSecurityVal) {
		this.socialSecurityVal = socialSecurityVal;
	}

	public String getSalaryMonthly() {
		return salaryMonthly;
	}

	public void setSalaryMonthly(String salaryMonthly) {
		this.salaryMonthly = salaryMonthly;
	}

	public String getOthterIncomeMonthly() {
		return othterIncomeMonthly;
	}

	public void setOthterIncomeMonthly(String othterIncomeMonthly) {
		this.othterIncomeMonthly = othterIncomeMonthly;
	}

	public String getPrivateTypeVal() {
		return privateTypeVal;
	}

	public void setPrivateTypeVal(String privateTypeVal) {
		this.privateTypeVal = privateTypeVal;
	}

	public String getCompanyRegtime() {
		return companyRegtime;
	}

	public void setCompanyRegtime(String companyRegtime) {
		this.companyRegtime = companyRegtime;
	}

	public String getPercentageShares() {
		return percentageShares;
	}

	public void setPercentageShares(String percentageShares) {
		this.percentageShares = percentageShares;
	}

	public String getEmployeesNum() {
		return employeesNum;
	}

	public void setEmployeesNum(String employeesNum) {
		this.employeesNum = employeesNum;
	}

	public String getPremisesVal() {
		return premisesVal;
	}

	public void setPremisesVal(String premisesVal) {
		this.premisesVal = premisesVal;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getApplyAddress() {
		return applyAddress;
	}

	public void setApplyAddress(String applyAddress) {
		this.applyAddress = applyAddress;
	}
}
