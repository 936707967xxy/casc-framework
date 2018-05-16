package com.hoomsun.csas.sales.api.model;

/**
 * 认证所有信息表
 * @author ygzhao
 *
 */
public class UserAllAuthInfo {
	/**
	 * 0:已认证，1：未认证，2：认证失败，3：认证中，4：认证过期
	 */
    private String allAuthPk;//主键

    private Integer zhimaCode;//芝麻分

    private Integer accumulationFund;//公积金

    private Integer callLog;//运营商

    private Integer protoinfoCode;//绑定银行卡

    private Integer savings;//储蓄卡认证

    private Integer incomeCode;//收入认证

    private Integer taobao;//淘宝

    private Integer chsi;//学信网

    private Integer socialSecurity;//社保

    private Integer bankBillFlow;//信用卡

    private Integer errorCode;//实名认证

    private Integer creditInvestigation;//征信

    private Integer borrowerCode;//个人信息认证

    private Integer contacterCode;//单位联系人认证

    private Integer careerCode;//职业信息认证

    private String applyId;//申请表id

    /**
     * 动态获取值
     * @return
     */
    public Integer getValue(String authName){
    	Integer authValue = null;
    	switch (authName) {
			case "zhimaCode":
				authValue = this.zhimaCode;
				break;
			case "accumulationFund":
				authValue = this.accumulationFund;
				break;
			case "callLog":
				authValue = this.callLog;
				break;
			case "protoinfoCode":
				authValue = this.protoinfoCode;
				break;
			case "savings":
				authValue = this.savings;
				break;
			case "incomeCode":
				authValue = this.incomeCode;
				break;
			case "taobao":
				authValue = this.taobao;
				break;
			case "chsi":
				authValue = this.chsi;
				break;
			case "socialSecurity":
				authValue = this.socialSecurity;
				break;
			case "bankBillFlow":
				authValue = this.bankBillFlow;
				break;
			case "errorCode":
				authValue = this.errorCode;
				break;
			case "creditInvestigation":
				authValue = this.creditInvestigation;
				break;
			case "borrowerCode":
				authValue = this.borrowerCode;
				break;
			case "contacterCode":
				authValue = this.contacterCode;
				break;
			case "careerCode":
				authValue = this.careerCode;
				break;				
				
		}
    	return authValue;
    }
    public String getAllAuthPk() {
        return allAuthPk;
    }

    public void setAllAuthPk(String allAuthPk) {
        this.allAuthPk = allAuthPk == null ? null : allAuthPk.trim();
    }


    public Integer getZhimaCode() {
		return zhimaCode;
	}

	public void setZhimaCode(Integer zhimaCode) {
		this.zhimaCode = zhimaCode;
	}

	public Integer getAccumulationFund() {
		return accumulationFund;
	}

	public void setAccumulationFund(Integer accumulationFund) {
		this.accumulationFund = accumulationFund;
	}

	public Integer getCallLog() {
		return callLog;
	}

	public void setCallLog(Integer callLog) {
		this.callLog = callLog;
	}

	public Integer getProtoinfoCode() {
		return protoinfoCode;
	}

	public void setProtoinfoCode(Integer protoinfoCode) {
		this.protoinfoCode = protoinfoCode;
	}

	public Integer getSavings() {
		return savings;
	}

	public void setSavings(Integer savings) {
		this.savings = savings;
	}

	public Integer getIncomeCode() {
		return incomeCode;
	}

	public void setIncomeCode(Integer incomeCode) {
		this.incomeCode = incomeCode;
	}

	public Integer getTaobao() {
		return taobao;
	}

	public void setTaobao(Integer taobao) {
		this.taobao = taobao;
	}

	public Integer getChsi() {
		return chsi;
	}

	public void setChsi(Integer chsi) {
		this.chsi = chsi;
	}

	public Integer getSocialSecurity() {
		return socialSecurity;
	}

	public void setSocialSecurity(Integer socialSecurity) {
		this.socialSecurity = socialSecurity;
	}

	public Integer getBankBillFlow() {
		return bankBillFlow;
	}

	public void setBankBillFlow(Integer bankBillFlow) {
		this.bankBillFlow = bankBillFlow;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public Integer getCreditInvestigation() {
		return creditInvestigation;
	}

	public void setCreditInvestigation(Integer creditInvestigation) {
		this.creditInvestigation = creditInvestigation;
	}

	public Integer getBorrowerCode() {
		return borrowerCode;
	}

	public void setBorrowerCode(Integer borrowerCode) {
		this.borrowerCode = borrowerCode;
	}

	public Integer getContacterCode() {
		return contacterCode;
	}

	public void setContacterCode(Integer contacterCode) {
		this.contacterCode = contacterCode;
	}

	public Integer getCareerCode() {
		return careerCode;
	}

	public void setCareerCode(Integer careerCode) {
		this.careerCode = careerCode;
	}

	public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }
}