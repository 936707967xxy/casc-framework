package com.hoomsun.csas.sales.api.model;

import java.sql.Timestamp;

/**
 * 作者：ygzhao <br>
 * 创建时间：2017年11月23日 <br>
 * 描述：职业信息表
 */
public class OccupationalInfo {
    private String ocinfoPkId;//主键

    private String fullWorkName;//工作单位全称
    
/*  1---农、林、牧、渔业，2---采矿业，3---制造业，4----电力、燃气及水的生产和供应，
    5----建筑业，6----交通运输、仓储和邮政，7---信息传输、计算机服务和软件业，
    8----批发和零售，9----住宿和餐饮业，10----金融业，11----房地产业，
    12----租赁和商务服务，13----科学研究、技术服务和地质勘查，
    14----水利、环境和公共设施管理，15----居民服务和其他服务，16----教育，
    17----卫生、社会保障和社会福利，18----文化、体育和娱乐业，19----公共管理和社会组织，20----国际组织*/
    private Integer industryIn;//所属行业
    
    private String industryInVal;//所属行业值
    
    private String companyAddress;//单位地址

    private String companyProvCode;//单位地址所在省份id

    private String companyProvName;//单位地址所在省份名称

    private String companyCityCode;//单位地址所在市id

    private String companyCityName;//单位地址所在市名称

    private String companyAreaCode;//单位地址所在区id

    private String companyAreaName;//单位地址所在区名称

    private String companyAddressDetail;//单位地址所在明细地址

    private String companyZipCode;//邮编

    private String companyTel;//单位电话区号+电话号

    private String companyTelCode;//单位电话区号

    private String companyTelValue;//单位电话号

    private String companyTelExt;//单位电话分机号

    /**
     * 公司规模
	  <id="01">10人以下</>  
	  <id="02">10-100人</> 
	  <id="03">100-500人</> 
	  <id="04">500人以上</> 
     */
    private Integer companySize;

    private String companySizeVal;//公司规模值

   /**
    * 单位性质 
      <id="01">政府机构</>  
	  <id="02">事业单位</>  
	  <id="03">军/警</>  
	  <id="04">国企</>  
	  <id="05">外企</>  
	  <id="06">合资企业</>  
	  <id="07">上市公司</>  
	  <id="08">私企</>                                                          
	  <id="09">个体</>                                                        
	  <id="10">其他</>
    */
    private Integer companyKind;

    private String companyKindVa;//单位性质值

    private String companyKindTxt;//如果为其他，单位性质值

    /**
     * 职级 
	  <id="01">法人</>  
	  <id="02">股东</>  
	  <id="03">高级管理人员</>  
	  <id="04">一般管理人员</>  
	  <id="05">一般正式员工</>  
	  <id="06">非正式员工</>    
	  <id="07">其他</> 
     */
    private Integer position;

    private String positionVal;//职级值

    private String positionTxt;//职级其他值

    private String workDept;//所在部门

    private Timestamp startWorkTime;//进入现单位时间

    private Object payDayMonthlyVal;//每月发薪日

    /**
     * 社保情况
	  <id="01">有社保</>  
	  <id="02">有社保和公积金</> 
	  <id="03">无</> 
     */
    private Integer socialSecurity;

    private String socialSecurityVal;//社保情况值

    private String jobTitle;//职务名称 

    private String othterIncomeMonthly;//其他收入（月均，单位：元）

    private String salaryMonthly;//每月薪金

   /**
    * 发薪方式 
	  <id="01">代发</>  
	  <id="02">转账</> 
	  <id="03">现金</> 
    */
    private Integer payMethod;

    private String payMethodVal;//发薪方式值

    /**
     * 私营企业类型
     *<id="0">无</>
	  <id="1">股份有限公司</>  
	  <id="2">有限责任公司 </>  
	  <id="3">合伙企业</>  
	  <id="4">个体 </>  
	  <id="5">其他</>
     */
    private Integer privateType;

    private String privateTypeVal;//私企值

    private String privateTypeText;//为其他值

    private Timestamp companyRegtime;//企业成立时间

    private String percentageShares;//股份占比

    private String employeesNum;//员工人数

    /**
     * 经营场所 
	  <id="01">租用</>  
	  <id="02">自有房产</> 
     */
    private Integer premises;

    private String premisesVal;//经营场所值

    private String applyId;//申请表ID

    private Timestamp createTime;//创建时间

    public String getOcinfoPkId() {
        return ocinfoPkId;
    }

    public void setOcinfoPkId(String ocinfoPkId) {
        this.ocinfoPkId = ocinfoPkId == null ? null : ocinfoPkId.trim();
    }

    public String getFullWorkName() {
        return fullWorkName;
    }

    public void setFullWorkName(String fullWorkName) {
        this.fullWorkName = fullWorkName == null ? null : fullWorkName.trim();
    }

    public Integer getIndustryIn() {
        return industryIn;
    }

    public void setIndustryIn(Integer industryIn) {
        this.industryIn = industryIn;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress == null ? null : companyAddress.trim();
    }

    public String getCompanyProvCode() {
        return companyProvCode;
    }

    public void setCompanyProvCode(String companyProvCode) {
        this.companyProvCode = companyProvCode == null ? null : companyProvCode.trim();
    }

    public String getCompanyProvName() {
        return companyProvName;
    }

    public void setCompanyProvName(String companyProvName) {
        this.companyProvName = companyProvName == null ? null : companyProvName.trim();
    }

    public String getCompanyCityCode() {
        return companyCityCode;
    }

    public void setCompanyCityCode(String companyCityCode) {
        this.companyCityCode = companyCityCode == null ? null : companyCityCode.trim();
    }

    public String getCompanyCityName() {
        return companyCityName;
    }

    public void setCompanyCityName(String companyCityName) {
        this.companyCityName = companyCityName == null ? null : companyCityName.trim();
    }

    public String getCompanyAreaCode() {
        return companyAreaCode;
    }

    public void setCompanyAreaCode(String companyAreaCode) {
        this.companyAreaCode = companyAreaCode == null ? null : companyAreaCode.trim();
    }

    public String getCompanyAreaName() {
        return companyAreaName;
    }

    public void setCompanyAreaName(String companyAreaName) {
        this.companyAreaName = companyAreaName == null ? null : companyAreaName.trim();
    }

    public String getCompanyAddressDetail() {
        return companyAddressDetail;
    }

    public void setCompanyAddressDetail(String companyAddressDetail) {
        this.companyAddressDetail = companyAddressDetail == null ? null : companyAddressDetail.trim();
    }

    public String getCompanyZipCode() {
        return companyZipCode;
    }

    public void setCompanyZipCode(String companyZipCode) {
        this.companyZipCode = companyZipCode == null ? null : companyZipCode.trim();
    }

    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel == null ? null : companyTel.trim();
    }

    public String getCompanyTelCode() {
        return companyTelCode;
    }

    public void setCompanyTelCode(String companyTelCode) {
        this.companyTelCode = companyTelCode == null ? null : companyTelCode.trim();
    }

    public String getCompanyTelValue() {
        return companyTelValue;
    }

    public void setCompanyTelValue(String companyTelValue) {
        this.companyTelValue = companyTelValue == null ? null : companyTelValue.trim();
    }

    public String getCompanyTelExt() {
        return companyTelExt;
    }

    public void setCompanyTelExt(String companyTelExt) {
        this.companyTelExt = companyTelExt == null ? null : companyTelExt.trim();
    }

    public Integer getCompanySize() {
        return companySize;
    }

    public void setCompanySize(Integer companySize) {
        this.companySize = companySize;
    }

    public String getCompanySizeVal() {
        return companySizeVal;
    }

    public void setCompanySizeVal(String companySizeVal) {
        this.companySizeVal = companySizeVal == null ? null : companySizeVal.trim();
    }

    public Integer getCompanyKind() {
        return companyKind;
    }

    public void setCompanyKind(Integer companyKind) {
        this.companyKind = companyKind ;
    }

    public String getCompanyKindVa() {
        return companyKindVa;
    }

    public void setCompanyKindVa(String companyKindVa) {
        this.companyKindVa = companyKindVa == null ? null : companyKindVa.trim();
    }

    public String getCompanyKindTxt() {
        return companyKindTxt;
    }

    public void setCompanyKindTxt(String companyKindTxt) {
        this.companyKindTxt = companyKindTxt == null ? null : companyKindTxt.trim();
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position ;
    }

    public String getPositionVal() {
        return positionVal;
    }

    public void setPositionVal(String positionVal) {
        this.positionVal = positionVal == null ? null : positionVal.trim();
    }

    public String getPositionTxt() {
        return positionTxt;
    }

    public void setPositionTxt(String positionTxt) {
        this.positionTxt = positionTxt == null ? null : positionTxt.trim();
    }

    public String getWorkDept() {
        return workDept;
    }

    public void setWorkDept(String workDept) {
        this.workDept = workDept == null ? null : workDept.trim();
    }

    public Timestamp getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(Timestamp startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public Object getPayDayMonthlyVal() {
        return payDayMonthlyVal;
    }

    public void setPayDayMonthlyVal(Object payDayMonthlyVal) {
        this.payDayMonthlyVal = payDayMonthlyVal;
    }

    public Integer getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(Integer socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public String getSocialSecurityVal() {
        return socialSecurityVal;
    }

    public void setSocialSecurityVal(String socialSecurityVal) {
        this.socialSecurityVal = socialSecurityVal == null ? null : socialSecurityVal.trim();
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle == null ? null : jobTitle.trim();
    }

    public String getOthterIncomeMonthly() {
        return othterIncomeMonthly;
    }

    public void setOthterIncomeMonthly(String othterIncomeMonthly) {
        this.othterIncomeMonthly = othterIncomeMonthly == null ? null : othterIncomeMonthly.trim();
    }

    public String getSalaryMonthly() {
        return salaryMonthly;
    }

    public void setSalaryMonthly(String salaryMonthly) {
        this.salaryMonthly = salaryMonthly == null ? null : salaryMonthly.trim();
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getPayMethodVal() {
        return payMethodVal;
    }

    public void setPayMethodVal(String payMethodVal) {
        this.payMethodVal = payMethodVal == null ? null : payMethodVal.trim();
    }

    public Integer getPrivateType() {
        return privateType;
    }

    public void setPrivateType(Integer privateType) {
        this.privateType = privateType;
    }

    public String getPrivateTypeVal() {
        return privateTypeVal;
    }

    public void setPrivateTypeVal(String privateTypeVal) {
        this.privateTypeVal = privateTypeVal == null ? null : privateTypeVal.trim();
    }

    public String getPrivateTypeText() {
        return privateTypeText;
    }

    public void setPrivateTypeText(String privateTypeText) {
        this.privateTypeText = privateTypeText == null ? null : privateTypeText.trim();
    }

    public Timestamp getCompanyRegtime() {
        return companyRegtime;
    }

    public void setCompanyRegtime(Timestamp companyRegtime) {
        this.companyRegtime = companyRegtime;
    }

    public String getPercentageShares() {
        return percentageShares;
    }

    public void setPercentageShares(String percentageShares) {
        this.percentageShares = percentageShares == null ? null : percentageShares.trim();
    }

    public String getEmployeesNum() {
        return employeesNum;
    }

    public void setEmployeesNum(String employeesNum) {
        this.employeesNum = employeesNum == null ? null : employeesNum.trim();
    }

    public Integer getPremises() {
        return premises;
    }

    public void setPremises(Integer premises) {
        this.premises = premises;
    }

    public String getPremisesVal() {
        return premisesVal;
    }

    public void setPremisesVal(String premisesVal) {
        this.premisesVal = premisesVal == null ? null : premisesVal.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

	@Override
	public String toString() {
		return "OccupationalInfo [ocinfoPkId=" + ocinfoPkId + ", fullWorkName=" + fullWorkName + ", industryIn=" + industryIn + ", companyAddress=" + companyAddress + ", companyProvCode=" + companyProvCode + ", companyProvName="
				+ companyProvName + ", companyCityCode=" + companyCityCode + ", companyCityName=" + companyCityName + ", companyAreaCode=" + companyAreaCode + ", companyAreaName=" + companyAreaName + ", companyAddressDetail="
				+ companyAddressDetail + ", companyZipCode=" + companyZipCode + ", companyTel=" + companyTel + ", companyTelCode=" + companyTelCode + ", companyTelValue=" + companyTelValue + ", companyTelExt=" + companyTelExt
				+ ", companySize=" + companySize + ", companySizeVal=" + companySizeVal + ", companyKind=" + companyKind + ", companyKindVa=" + companyKindVa + ", companyKindTxt=" + companyKindTxt + ", position=" + position
				+ ", positionVal=" + positionVal + ", positionTxt=" + positionTxt + ", workDept=" + workDept + ", startWorkTime=" + startWorkTime + ", payDayMonthlyVal=" + payDayMonthlyVal + ", socialSecurity=" + socialSecurity
				+ ", socialSecurityVal=" + socialSecurityVal + ", jobTitle=" + jobTitle + ", othterIncomeMonthly=" + othterIncomeMonthly + ", salaryMonthly=" + salaryMonthly + ", payMethod=" + payMethod + ", payMethodVal=" + payMethodVal
				+ ", privateType=" + privateType + ", privateTypeVal=" + privateTypeVal + ", privateTypeText=" + privateTypeText + ", companyRegtime=" + companyRegtime + ", percentageShares=" + percentageShares + ", employeesNum="
				+ employeesNum + ", premises=" + premises + ", premisesVal=" + premisesVal + ", applyId=" + applyId + ", createTime=" + createTime + "]";
	}

	public String getIndustryVal() {
		return industryInVal;
	}

	public void setIndustryVal(String industryVal) {
		this.industryInVal = industryVal;
	}


    
}