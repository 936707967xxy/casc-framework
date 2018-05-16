/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.model.vo;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

/**
 * 作者：liusong <br>
 * 创建时间：2017年12月4日 <br>
 * 描述：协议拟制数据展示
 */
public class MakingVo {
	private String id;
	private String applyId;// 主键
	private String custName;// 客户姓名
	private String applyProductId;// 申请产品id
	private String applyProductName;// 申请产品名称
	private BigDecimal applyAmount;// 申请金额
	private String productId;// 审批产品id
	private String productName;// 审批产品
	private String prodAlias;// 产品别名
	private BigDecimal approvedAmount;// 审批金额
	private Integer productPeriod;// 批准期数
	private BigDecimal realRate;
	private String loanId;// 进件编号
	private String idNumber;// 证件号
	private Integer loanUse;// 借款用途ID
	private String loanUseVal;// 借款用途值
	private String precessStatusVal;// 流程状态值
	private String precessStatus;// 流程状态ID
	private String precessId;// 流程实例id
	private String createEmployee;// 录入人员ID
	private String createEmployeeVal;// 录入人员姓名
	private String salesEmpId;// 销售ID
	private String salesEmpName;// 销售姓名
	private String custMobile;
	private String isOnline;
	private String isOnlineVal;
	private String assignee;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Integer getLoanUse() {
		return loanUse;
	}

	public void setLoanUse(Integer loanUse) {
		this.loanUse = loanUse;
	}

	public String getLoanUseVal() {
		return loanUseVal;
	}

	public void setLoanUseVal(String loanUseVal) {
		this.loanUseVal = loanUseVal;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getApplyProductId() {
		return applyProductId;
	}

	public void setApplyProductId(String applyProductId) {
		this.applyProductId = applyProductId;
	}

	public String getApplyProductName() {
		return applyProductName;
	}

	public void setApplyProductName(String applyProductName) {
		this.applyProductName = applyProductName;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(BigDecimal approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	public Integer getProductPeriod() {
		return productPeriod;
	}

	public void setProductPeriod(Integer productPeriod) {
		this.productPeriod = productPeriod;
	}

	public String getPrecessStatusVal() {
		return precessStatusVal;
	}

	public void setPrecessStatusVal(String precessStatusVal) {
		this.precessStatusVal = precessStatusVal;
	}

	public String getPrecessStatus() {
		return precessStatus;
	}

	public void setPrecessStatus(String precessStatus) {
		this.precessStatus = precessStatus;
	}

	public String getPrecessId() {
		return precessId;
	}

	public void setPrecessId(String precessId) {
		this.precessId = precessId;
	}

	public String getCreateEmployee() {
		return createEmployee;
	}

	public void setCreateEmployee(String createEmployee) {
		this.createEmployee = createEmployee;
	}

	public String getCreateEmployeeVal() {
		return createEmployeeVal;
	}

	public void setCreateEmployeeVal(String createEmployeeVal) {
		this.createEmployeeVal = createEmployeeVal;
	}

	public String getSalesEmpId() {
		return salesEmpId;
	}

	public void setSalesEmpId(String salesEmpId) {
		this.salesEmpId = salesEmpId;
	}

	public String getSalesEmpName() {
		return salesEmpName;
	}

	public void setSalesEmpName(String salesEmpName) {
		this.salesEmpName = salesEmpName;
	}

	public String getCustMobile() {
		return custMobile;
	}

	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}

	public String getProdAlias() {
		return prodAlias;
	}

	public void setProdAlias(String prodAlias) {
		this.prodAlias = prodAlias;
	}

	public String getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

	public String getIsOnlineVal() {
		if (StringUtils.isBlank(isOnline)) {
			isOnlineVal = "未知";
		}else {
			if (isOnline.equals("0")) {
				isOnlineVal = "线下";
			}else if (isOnline.equals("1")) {
				isOnlineVal = "线上";
			}else {
				isOnlineVal = "未知";
			}
		}
		return isOnlineVal;
	}

	public void setIsOnlineVal(String isOnlineVal) {
		this.isOnlineVal = isOnlineVal;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public BigDecimal getRealRate() {
		return realRate;
	}

	public void setRealRate(BigDecimal realRate) {
		this.realRate = realRate;
	}

}