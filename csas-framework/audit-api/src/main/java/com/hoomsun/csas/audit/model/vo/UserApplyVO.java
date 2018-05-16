/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.model.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月4日 <br>
 * 描述：初审、终审列表展示数据
 */
public class UserApplyVO {
	private String applyId;// 主键
	private String storeName;// 门店名称
	private String storeId;// 门店id
	private String custMobile;// 联系电话
	private String idNumber;// 证件号
	private String custName;// 客户姓名
	private Integer sources;// 来源 来源：1 app 2 门店 3 主动拜访 4 客户推荐 5 派单 6 推广活动 7 其他
	private String procStatusVal;// 流程状态值
	private String procStatus;// 流程状态ID
	private String procInstId;// 流程实例id
	private String productName;// 产品名称
	private String productId;// 申请产品
	private String loanId;//
	private BigDecimal applyAmount;// 申请金额
	private Date applyDate;
	private Date startTime;
	private Date claimTime;
	private Date endTime;
	private String assignee;
	private String salesEmpId;// 销售ID
	private String salesEmpName;// 销售姓名
	private String salesEmpDeptId;// 销售所在部门ID
	private String salesEmpDeptName;// 销售所在部门名称
	
	
	
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getCustMobile() {
		return custMobile;
	}

	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Integer getSources() {
		return sources;
	}

	public void setSources(Integer sources) {
		this.sources = sources;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public String getProcStatusVal() {
		return procStatusVal;
	}

	public void setProcStatusVal(String procStatusVal) {
		this.procStatusVal = procStatusVal;
	}

	public String getProcStatus() {
		return procStatus;
	}

	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getClaimTime() {
		return claimTime;
	}

	public void setClaimTime(Date claimTime) {
		this.claimTime = claimTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
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

	public String getSalesEmpDeptId() {
		return salesEmpDeptId;
	}

	public void setSalesEmpDeptId(String salesEmpDeptId) {
		this.salesEmpDeptId = salesEmpDeptId;
	}

	public String getSalesEmpDeptName() {
		return salesEmpDeptName;
	}

	public void setSalesEmpDeptName(String salesEmpDeptName) {
		this.salesEmpDeptName = salesEmpDeptName;
	}
	
	
}
