package com.hoomsun.csas.audit.model;

import java.math.BigDecimal;
import java.util.Date;

public class ExcessAudit {
	private String excessId;
	private Date auditDate;
	private String auditEmp;
	private String auditEmpName;
	private String inHandleOpinion;
	private Integer handleOpinion;
	private String handleOpinionVal;
	private BigDecimal monthPay;
	private BigDecimal approvedAmount;
	private String productPayVal;
	private Integer productPay;
	private BigDecimal productRate;
	private Integer productPeriod;
	private BigDecimal productFeeRate;
	private String productName;
	private String productId;
	private String applyId;
	private Integer preStatus;
	private String procInstId;
	private String taskId;
	private Integer excessStatus;

	public String getExcessId() {
		return excessId;
	}

	public void setExcessId(String excessId) {
		this.excessId = excessId == null ? null : excessId.trim();
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getAuditEmp() {
		return auditEmp;
	}

	public void setAuditEmp(String auditEmp) {
		this.auditEmp = auditEmp == null ? null : auditEmp.trim();
	}

	public String getInHandleOpinion() {
		return inHandleOpinion;
	}

	public void setInHandleOpinion(String inHandleOpinion) {
		this.inHandleOpinion = inHandleOpinion == null ? null : inHandleOpinion.trim();
	}

	public Integer getHandleOpinion() {
		return handleOpinion;
	}

	public void setHandleOpinion(Integer handleOpinion) {
		this.handleOpinion = handleOpinion;
	}

	public String getHandleOpinionVal() {
		return handleOpinionVal;
	}

	public void setHandleOpinionVal(String handleOpinionVal) {
		this.handleOpinionVal = handleOpinionVal;
	}

	public BigDecimal getMonthPay() {
		return monthPay;
	}

	public void setMonthPay(BigDecimal monthPay) {
		this.monthPay = monthPay;
	}

	public BigDecimal getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(BigDecimal approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	public String getProductPayVal() {
		return productPayVal;
	}

	public void setProductPayVal(String productPayVal) {
		this.productPayVal = productPayVal == null ? null : productPayVal.trim();
	}

	public Integer getProductPay() {
		return productPay;
	}

	public void setProductPay(Integer productPay) {
		this.productPay = productPay;
	}

	public BigDecimal getProductRate() {
		return productRate;
	}

	public void setProductRate(BigDecimal productRate) {
		this.productRate = productRate;
	}

	public Integer getProductPeriod() {
		return productPeriod;
	}

	public void setProductPeriod(Integer productPeriod) {
		this.productPeriod = productPeriod;
	}

	public BigDecimal getProductFeeRate() {
		return productFeeRate;
	}

	public void setProductFeeRate(BigDecimal productFeeRate) {
		this.productFeeRate = productFeeRate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName == null ? null : productName.trim();
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId == null ? null : productId.trim();
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId == null ? null : applyId.trim();
	}

	public Integer getPreStatus() {
		return preStatus;
	}

	public void setPreStatus(Integer preStatus) {
		this.preStatus = preStatus;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getAuditEmpName() {
		return auditEmpName;
	}

	public void setAuditEmpName(String auditEmpName) {
		this.auditEmpName = auditEmpName;
	}

	public Integer getexcessStatus() {
		return excessStatus;
	}

	public void setexcessStatus(Integer excessStatus) {
		this.excessStatus = excessStatus;
	}

}