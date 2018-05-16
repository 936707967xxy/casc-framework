/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

import java.util.Date;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年4月27日 <br>
 * 描述：留案申请提交参数
 */
public class LeaveApplyParams {

	/**
	 * 进件编号
	 */
	private String loanId;

	/**
	 * 申请单号
	 */
	private String applyId;

	/**
	 	申请类型(联系电话变更 telChange , 银行四要素变更 bankChange，逾期单月减免申请overdueSingle
 		逾期多月减免申请 overdueAll,外访申请 outBound，电催留案申请overdueLeave  外访留案申请outBoundLeave)
	 */
	private String applyType;


	/**
	 * 申请备注
	 */
	private String applicationDesc;


	public String getLoanId() {
		return loanId;
	}


	public void setLoanId(String loanId) {
		this.loanId = loanId;
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


	public String getApplicationDesc() {
		return applicationDesc;
	}


	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}
	
	
	
}
