package com.hoomsun.csas.audit.model;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 作者：liming<br>
 * 创建时间：2017年12月9日 <br>
 * 描述： 初审意见表
 */
public class PreAudit {
	private String preId;
	private String productId;
	private String productName;
	private Integer handleOption;
	private String handleOptionval;
	private String creditRemark;
	private String financeRemark;
	private String otherRemark;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date handleTime;
	private String empId;
	private String empName;
	private String taskId;
	private String procInstId;
	private String applyId;
	private Integer preStatus;
	private BigDecimal handleMoney;
	private String innerRemark;
	private String remark;

	public String getPreId() {
		return preId;
	}

	public void setPreId(String preId) {
		this.preId = preId;
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

	public Integer getHandleOption() {
		return handleOption;
	}

	public void setHandleOption(Integer handleOption) {
		this.handleOption = handleOption;
	}

	public String getHandleOptionval() {
		return handleOptionval;
	}

	public void setHandleOptionval(String handleOptionval) {
		this.handleOptionval = handleOptionval;
	}

	public String getCreditRemark() {
		return creditRemark;
	}

	public void setCreditRemark(String creditRemark) {
		this.creditRemark = creditRemark;
	}

	public String getFinanceRemark() {
		return financeRemark;
	}

	public void setFinanceRemark(String financeRemark) {
		this.financeRemark = financeRemark;
	}

	public String getOtherRemark() {
		return otherRemark;
	}

	public void setOtherRemark(String otherRemark) {
		this.otherRemark = otherRemark;
	}

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public Integer getPreStatus() {
		return preStatus;
	}

	public void setPreStatus(Integer preStatus) {
		this.preStatus = preStatus;
	}

	public BigDecimal getHandleMoney() {
		return handleMoney;
	}

	public void setHandleMoney(BigDecimal handleMoney) {
		this.handleMoney = handleMoney;
	}

	public String getInnerRemark() {
		return innerRemark;
	}

	public void setInnerRemark(String innerRemark) {
		this.innerRemark = innerRemark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}