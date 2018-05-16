package com.hoomsun.csas.sales.api.model;

import com.alibaba.fastjson.JSONObject;

/**
 * 作者：liushuai <br>
 * 创建时间：2018年2月11日 <br>
 * 描述： 征信可视化
 */
public class PbccrcChartView {
	private String pbViewId; // 主键
    private String applyId; //applyId
    private String overdueinfo; //逾期比例信息<视图1>
    private String cardLimitInfo; // 信用额度信息<视图2>
    private String cardUsedTimeInfo; //信用时长信息<视图3>
    private String cardUsedInfo; // 信用使用详情信息<视图4>
    private String accountInfo; // 账户信息<视图5>
    private String loanPurposeInfo; // 贷款用途详情<视图6>
    private String loanMoneyInfo; //贷款金额区间笔数<视图7>
    private String loanStatusInfo; // 贷款状态笔数<视图8>
    private String loanInstitutionInfo; //贷款机构发放比例 <视图9> （笔数）
    private String selectInfo; //征信查询 最近三个月查询次数 <视图10> 
    private String selectWithLoanInfo; //征信查询 最近三个月内贷款审批查询次数 <视图11>
	public String getPbViewId() {
		return pbViewId;
	}
	public String getApplyId() {
		return applyId;
	}
	public String getOverdueinfo() {
		return overdueinfo;
	}
	public String getCardLimitInfo() {
		return cardLimitInfo;
	}
	public String getCardUsedTimeInfo() {
		return cardUsedTimeInfo;
	}
	public String getCardUsedInfo() {
		return cardUsedInfo;
	}
	public String getAccountInfo() {
		return accountInfo;
	}
	public String getLoanPurposeInfo() {
		return loanPurposeInfo;
	}
	public String getLoanMoneyInfo() {
		return loanMoneyInfo;
	}
	public String getLoanStatusInfo() {
		return loanStatusInfo;
	}
	public String getLoanInstitutionInfo() {
		return loanInstitutionInfo;
	}
	public String getSelectInfo() {
		return selectInfo;
	}
	public String getSelectWithLoanInfo() {
		return selectWithLoanInfo;
	}
	public void setPbViewId(String pbViewId) {
		this.pbViewId = pbViewId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public void setOverdueinfo(String overdueinfo) {
		this.overdueinfo = overdueinfo;
	}
	public void setCardLimitInfo(String cardLimitInfo) {
		this.cardLimitInfo = cardLimitInfo;
	}
	public void setCardUsedTimeInfo(String cardUsedTimeInfo) {
		this.cardUsedTimeInfo = cardUsedTimeInfo;
	}
	public void setCardUsedInfo(String cardUsedInfo) {
		this.cardUsedInfo = cardUsedInfo;
	}
	public void setAccountInfo(String accountInfo) {
		this.accountInfo = accountInfo;
	}
	public void setLoanPurposeInfo(String loanPurposeInfo) {
		this.loanPurposeInfo = loanPurposeInfo;
	}
	public void setLoanMoneyInfo(String loanMoneyInfo) {
		this.loanMoneyInfo = loanMoneyInfo;
	}
	public void setLoanStatusInfo(String loanStatusInfo) {
		this.loanStatusInfo = loanStatusInfo;
	}
	public void setLoanInstitutionInfo(String loanInstitutionInfo) {
		this.loanInstitutionInfo = loanInstitutionInfo;
	}
	public void setSelectInfo(String selectInfo) {
		this.selectInfo = selectInfo;
	}
	public void setSelectWithLoanInfo(String selectWithLoanInfo) {
		this.selectWithLoanInfo = selectWithLoanInfo;
	}

}