/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.vo;

import java.io.Serializable;

import com.hoomsun.after.api.util.excel.secode.annotation.ExcelField;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年5月4日 <br>
 * 描述：外访申请分批响应参数
 */
public class VistTaskResult implements Serializable{

	private static final long serialVersionUID = -5737105044219747803L;
	private String loanId;
	private String applyId;
	private String conNo;
	private  String cardNo;
	private  String tel;
	private String castName;
	private String loanPeriod;
	private String currentPeriod;
	private String loanMoney;
	private String dalayFlag;
	private String managerCast;
	private String loanManagerCast;
	private String mSection;
	private String statementDate;
	private String conMoney;
	private String outboundAddress;
	private String overdueStatus;
	private String outboundDate;
	private String overdueDesc;
	private String applyDesc;
	private String outboundStatus;
	private String outboundId;
	private String outboundName;
	private String outboundDesc;
	private String createTime;
	private String updateTime;
	
	@ExcelField(title="进件编号", align=1, sort=1)
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	@ExcelField(title="申请编号", align=1, sort=2)
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	@ExcelField(title="合同编号", align=1, sort=3)
	public String getConNo() {
		return conNo;
	}
	public void setConNo(String conNo) {
		this.conNo = conNo;
	}
	@ExcelField(title="身份证号", align=1, sort=4)
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	@ExcelField(title="电话号码", align=1, sort=5)
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	@ExcelField(title="客户姓名", align=1, sort=6)
	public String getCastName() {
		return castName;
	}
	public void setCastName(String castName) {
		this.castName = castName;
	}
	@ExcelField(title="贷款期数", align=1, sort=7)
	public String getLoanPeriod() {
		return loanPeriod;
	}
	public void setLoanPeriod(String loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	@ExcelField(title="当前期次", align=1, sort=8)
	public String getCurrentPeriod() {
		return currentPeriod;
	}
	public void setCurrentPeriod(String currentPeriod) {
		this.currentPeriod = currentPeriod;
	}
	@ExcelField(title="放款金额", align=1, sort=9)
	public String getLoanMoney() {
		return loanMoney;
	}
	public void setLoanMoney(String loanMoney) {
		this.loanMoney = loanMoney;
	}
	@ExcelField(title="是否逾期", align=1, sort=10)
	public String getDalayFlag() {
		return dalayFlag;
	}
	public void setDalayFlag(String dalayFlag) {
		this.dalayFlag = dalayFlag;
	}
	@ExcelField(title="前线客服姓名", align=1, sort=11)
	public String getManagerCast() {
		return managerCast;
	}
	public void setManagerCast(String managerCast) {
		this.managerCast = managerCast;
	}
	@ExcelField(title="贷后客服姓名", align=1, sort=12)
	public String getLoanManagerCast() {
		return loanManagerCast;
	}
	public void setLoanManagerCast(String loanManagerCast) {
		this.loanManagerCast = loanManagerCast;
	}
	@ExcelField(title="M段", align=1, sort=13)
	public String getmSection() {
		return mSection;
	}
	public void setmSection(String mSection) {
		this.mSection = mSection;
	}
	@ExcelField(title="账单日", align=1, sort=14)
	public String getStatementDate() {
		return statementDate;
	}
	public void setStatementDate(String statementDate) {
		this.statementDate = statementDate;
	}
	@ExcelField(title="合同金额", align=1, sort=15)
	public String getConMoney() {
		return conMoney;
	}
	public void setConMoney(String conMoney) {
		this.conMoney = conMoney;
	}
	@ExcelField(title="申请地址", align=1, sort=16)
	public String getOutboundAddress() {
		return outboundAddress;
	}
	public void setOutboundAddress(String outboundAddress) {
		this.outboundAddress = outboundAddress;
	}
	@ExcelField(title="催收状态", align=1, sort=17)
	public String getOverdueStatus() {
		return overdueStatus;
	}
	public void setOverdueStatus(String overdueStatus) {
		this.overdueStatus = overdueStatus;
	}
	@ExcelField(title="建议上门时间", align=1, sort=18)
	public String getOutboundDate() {
		return outboundDate;
	}
	public void setOutboundDate(String outboundDate) {
		this.outboundDate = outboundDate;
	}
	@ExcelField(title="催收详情", align=1, sort=19)
	public String getOverdueDesc() {
		return overdueDesc;
	}
	public void setOverdueDesc(String overdueDesc) {
		this.overdueDesc = overdueDesc;
	}
	@ExcelField(title="申请备注", align=1, sort=20)
	public String getApplyDesc() {
		return applyDesc;
	}
	public void setApplyDesc(String applyDesc) {
		this.applyDesc = applyDesc;
	}
	@ExcelField(title="外访申请状态", align=1, sort=21)
	public String getOutboundStatus() {
		return outboundStatus;
	}
	public void setOutboundStatus(String outboundStatus) {
		this.outboundStatus = outboundStatus;
	}
	@ExcelField(title="外访人员编号", align=1, sort=22)
	public String getOutboundId() {
		return outboundId;
	}
	public void setOutboundId(String outboundId) {
		this.outboundId = outboundId;
	}
	@ExcelField(title="外访人员姓名", align=1, sort=23)
	public String getOutboundName() {
		return outboundName;
	}
	public void setOutboundName(String outboundName) {
		this.outboundName = outboundName;
	}
	@ExcelField(title="外访情况说明", align=1, sort=24)
	public String getOutboundDesc() {
		return outboundDesc;
	}
	public void setOutboundDesc(String outboundDesc) {
		this.outboundDesc = outboundDesc;
	}
	@ExcelField(title="申请时间", align=1, sort=25)
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@ExcelField(title="分配时间", align=1, sort=26)
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
