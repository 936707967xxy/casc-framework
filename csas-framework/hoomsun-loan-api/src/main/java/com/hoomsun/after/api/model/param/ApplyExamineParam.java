/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

/**
 * 作者：金世强 <br>
 * 创建时间：2018年4月18日 <br>
 * 描述：流程审批参数
 */
public class ApplyExamineParam {

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
	 * 审批意见（0通过，1拒绝）
	 */
	private String approveOpinion;
	/**
	 * 审批备注
	 */
	private String approveDesc;

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

	public String getApproveOpinion() {
		return approveOpinion;
	}

	public void setApproveOpinion(String approveOpinion) {
		this.approveOpinion = approveOpinion;
	}

	public String getApproveDesc() {
		return approveDesc;
	}

	public void setApproveDesc(String approveDesc) {
		this.approveDesc = approveDesc;
	}

}
