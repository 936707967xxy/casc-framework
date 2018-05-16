/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.model.vo;

import java.math.BigDecimal;
import java.sql.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月12日 <br>
 * 描述：合同列表数据
 */
public class UserApplyConVO {
	private String applyId;// 主键
	private String storeName;// 门店名称
	private String storeId;// 门店id
	private String custMobile;// 联系电话
	private String idNumber;// 证件号
	private String custName;// 客户姓名
	private Integer sources;// 来源 来源：1 app 2 门店 3 主动拜访 4 客户推荐 5 派单 6 推广活动 7 其他
	private String sourcesVal;
	private String procStatusVal;// 流程状态值
	private String procStatus;// 流程状态ID
	private String procInstId;// 流程实例id
	private String productName;// 产品名称
	private String productId;// 申请产品
	private String loanId;//
	private BigDecimal applyAmount;// 申请金额
	private String conId;
	private String conNo;
	private String conAmount;
	private String loanAmount;
	private String monthMoney;
	private String signProductName;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date signTime;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	private Integer productPeriod;
	private String assignee;

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

	public String getSignProductName() {
		return signProductName;
	}

	public void setSignProductName(String signProductName) {
		this.signProductName = signProductName;
	}

	public void setSources(Integer sources) {
		this.sources = sources;
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

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}

	public String getConNo() {
		return conNo;
	}

	public void setConNo(String conNo) {
		this.conNo = conNo;
	}

	public String getConAmount() {
		return conAmount;
	}

	public void setConAmount(String conAmount) {
		this.conAmount = conAmount;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
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

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public Integer getProductPeriod() {
		return productPeriod;
	}

	public void setProductPeriod(Integer productPeriod) {
		this.productPeriod = productPeriod;
	}

	public String getSourcesVal() {
		return sourcesVal;
	}

	public void setSourcesVal(String sourcesVal) {
		this.sourcesVal = sourcesVal;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMonthMoney() {
		return monthMoney;
	}

	public void setMonthMoney(String monthMoney) {
		this.monthMoney = monthMoney;
	}

}
