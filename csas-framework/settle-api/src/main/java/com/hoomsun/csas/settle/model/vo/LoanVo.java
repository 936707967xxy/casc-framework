/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.settle.model.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 作者：liming <br>
 * 创建时间：2017年12月16日 <br>
 * 描述：放款信息
 */
public class LoanVo {
	private String conId;
	private String applyId;// 主键
	private String custName;// 客户姓名
	private String custMobile;// 手机号
	private String idNumber;// 证件号
	private String productId;// 产品id
	private String productName;// 产品名称
	private BigDecimal conAmount;// 合同金额
	private BigDecimal loanAmount;// 放款金额
	private Date signDate;// 签约时间
	private Integer period;// 批准期数
	private String conCode;// 合同编号
	private String procStatusVal;// 流程状态值
	private String procStatus;// 流程状态ID

	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
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

	public BigDecimal getConAmount() {
		return conAmount;
	}

	public void setConAmount(BigDecimal conAmount) {
		this.conAmount = conAmount;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public String getConCode() {
		return conCode;
	}

	public void setConCode(String conCode) {
		this.conCode = conCode;
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

}
