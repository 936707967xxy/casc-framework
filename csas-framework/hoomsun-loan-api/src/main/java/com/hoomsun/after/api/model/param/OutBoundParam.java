/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

import java.util.Date;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年4月25日 <br>
 * 描述：
 */
public class OutBoundParam {

	// 进件编号
	private String loanId;

	// 合同编号
	private String conNo;

	// 申请地址
	private String outboundAddress;

	// 催收状态
	private String overdueStatus;

	// 建议上门时间
	private Date outboundDate;

	// 催收详情
	private String overdueDesc;

	// 申请备注
	private String applyDesc;

	// 外访人员id
	private String outboundId;

	// 外访人员name
	private String outboundName;

	// 外访情况说明
	private String outboundDesc;

	// 申请单号
	private String applyId;

	// 申请类型
	private String applyType;

	// 客戶姓名
	private String castName;

	// 身份証號
	private String cardNo;

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getConNo() {
		return conNo;
	}

	public void setConNo(String conNo) {
		this.conNo = conNo;
	}

	public String getOutboundAddress() {
		return outboundAddress;
	}

	public void setOutboundAddress(String outboundAddress) {
		this.outboundAddress = outboundAddress;
	}

	public String getOverdueStatus() {
		return overdueStatus;
	}

	public void setOverdueStatus(String overdueStatus) {
		this.overdueStatus = overdueStatus;
	}

	public Date getOutboundDate() {
		return outboundDate;
	}

	public void setOutboundDate(Date outboundDate) {
		this.outboundDate = outboundDate;
	}

	public String getOverdueDesc() {
		return overdueDesc;
	}

	public void setOverdueDesc(String overdueDesc) {
		this.overdueDesc = overdueDesc;
	}

	public String getApplyDesc() {
		return applyDesc;
	}

	public void setApplyDesc(String applyDesc) {
		this.applyDesc = applyDesc;
	}

	public String getOutboundId() {
		return outboundId;
	}

	public void setOutboundId(String outboundId) {
		this.outboundId = outboundId;
	}

	public String getOutboundName() {
		return outboundName;
	}

	public void setOutboundName(String outboundName) {
		this.outboundName = outboundName;
	}

	public String getOutboundDesc() {
		return outboundDesc;
	}

	public void setOutboundDesc(String outboundDesc) {
		this.outboundDesc = outboundDesc;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getCastName() {
		return castName;
	}

	public void setCastName(String castName) {
		this.castName = castName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

}
