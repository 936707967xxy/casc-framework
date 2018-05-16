/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.vo;

import java.util.Date;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月21日 <br>
 * 描述：客户减免
 */
public class CustomerOverdueResult {

	//
	private String id;
	//申请人id
	private String applicationCastId;
	//申请人name
	private String applicationCastName;
	//申请备注
	private String applicationDesc;
	//审批人员id
	private String approveId;
	//审批人员name
	private String approveName;
	//审批备注
	private String approveDesc;
	//审批意见（0同意  1拒绝）
	private String approveOpinion;
	//申请日期
	private String applicationDate;
	//审批日期
	private String approveDate;
	//二级审批人员id
	private String approveId2;
	//二级审批人员name
	private String approveName2;
	//二级审批备注
	private String approveDesc2;
	//二级审批意见（0同意  1拒绝）
	private String approveOpinion2;
	//二级审批日期
	private Date approveDate2;
	//审批状态 (0:等待一级审批  1:等待二级审批  2:一级审批拒绝   3:二级审批拒绝   4: 审批通过)
	private String overdueStatus;
	//客户姓名
	private String castName;
	//身份证号
	private String cardNo;
	//申请单号
	private String applicationId;
	//申请类型
	private String applyType;
	//减免金额
	private String reductMoney;
	//有效日期
	private String disposaDate;
	//合同编号
	private String conNo;
	//进件编号
	private String loanId;
	//创建时间
	private String createTime;
	//修改时间
	private String updateDate;
	//手机号码
	private String tel;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApplicationCastId() {
		return applicationCastId;
	}
	public void setApplicationCastId(String applicationCastId) {
		this.applicationCastId = applicationCastId;
	}
	public String getApplicationCastName() {
		return applicationCastName;
	}
	public void setApplicationCastName(String applicationCastName) {
		this.applicationCastName = applicationCastName;
	}
	public String getApplicationDesc() {
		return applicationDesc;
	}
	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}
	public String getApproveId() {
		return approveId;
	}
	public void setApproveId(String approveId) {
		this.approveId = approveId;
	}
	public String getApproveName() {
		return approveName;
	}
	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}
	public String getApproveDesc() {
		return approveDesc;
	}
	public void setApproveDesc(String approveDesc) {
		this.approveDesc = approveDesc;
	}
	public String getApproveOpinion() {
		return approveOpinion;
	}
	public void setApproveOpinion(String approveOpinion) {
		this.approveOpinion = approveOpinion;
	}
	public String getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}
	public String getApproveId2() {
		return approveId2;
	}
	public void setApproveId2(String approveId2) {
		this.approveId2 = approveId2;
	}
	public String getApproveName2() {
		return approveName2;
	}
	public void setApproveName2(String approveName2) {
		this.approveName2 = approveName2;
	}
	public String getApproveDesc2() {
		return approveDesc2;
	}
	public void setApproveDesc2(String approveDesc2) {
		this.approveDesc2 = approveDesc2;
	}
	public String getApproveOpinion2() {
		return approveOpinion2;
	}
	public void setApproveOpinion2(String approveOpinion2) {
		this.approveOpinion2 = approveOpinion2;
	}
	public Date getApproveDate2() {
		return approveDate2;
	}
	public void setApproveDate2(Date approveDate2) {
		this.approveDate2 = approveDate2;
	}
	public String getOverdueStatus() {
		return overdueStatus;
	}
	public void setOverdueStatus(String overdueStatus) {
		this.overdueStatus = overdueStatus;
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
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public String getReductMoney() {
		return reductMoney;
	}
	public void setReductMoney(String reductMoney) {
		this.reductMoney = reductMoney;
	}
	public String getDisposaDate() {
		return disposaDate;
	}
	public void setDisposaDate(String disposaDate) {
		this.disposaDate = disposaDate;
	}
	public String getConNo() {
		return conNo;
	}
	public void setConNo(String conNo) {
		this.conNo = conNo;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
}
