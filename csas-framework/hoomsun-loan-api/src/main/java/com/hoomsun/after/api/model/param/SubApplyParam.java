/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

import java.util.Date;
import java.util.List;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年4月23日 <br>
 * 描述：减免申请实体类
 */
public class SubApplyParam {
	/**
	 * 进件编号
	 */
	private String loanId;

	/**
	 * 申请单号
	 */
	private String applyId;

	/**
	 * 申请类型(联系电话变更 telChange , 银行四要素变更 bankChange,逾期单月减免申请overdueSingle
	 * ,逾期多月减免申请 overdueAll,外访申请 outBound)
	 */
	private String applyType;

	/**
	 * 承诺还款日期
	 */
	private Date subDate;

	/**
	 * 申请备注
	 */
	private String applicationDesc;

	/**
	 * 减免详情
	 */
	private List<SubParams> subparams;

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public Date getSubDate() {
		return subDate;
	}

	public void setSubDate(Date subDate) {
		this.subDate = subDate;
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

	public List<SubParams> getSubparams() {
		return subparams;
	}

	public void setSubparams(List<SubParams> subparams) {
		this.subparams = subparams;
	}

	
}
