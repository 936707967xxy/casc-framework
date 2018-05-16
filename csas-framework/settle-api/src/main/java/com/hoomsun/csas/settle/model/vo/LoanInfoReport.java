/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.settle.model.vo;

import java.math.BigDecimal;


/**
 * 作者：liming <br>
 * 创建时间：2018年1月23日 <br>
 * 描述：放款报表
 */
public class LoanInfoReport {
	
	private String conId;// 主键
	
	private String conNo;// 合同编号
	
	private String custName;//客户姓名
	
	private String idNumber;//身份证号
	
	private String loanId;// 申请编号
	
	private Integer conStatus;// 合同状态   0 未放款 1 放款 2 结清  3  未结清   4 提前结清    5 逾期 
	
	private String bankId;//银行表主键id
	
	private String bank;// 开户行
	
	private String backBranch;// 支行名称
	
	private String backBranchAddr;// 支行详细地址
	
	private String signAddr;// 签约地
	
	private String signStore;// 签约门店
	
	private String bankNo;// 银行账户
	
	private String bankPhoneNo;// 银行卡预留手机号
	
	private Integer productPay;// 还款方式
	
	private String productPayVal;// 还款方式
	
	private Integer signType;// 签约类型
	
	private Integer productPeriod;// 签约产品期限
	
	private String productFeeRate;// 签约产品费率 json
	
	private BigDecimal productRate;// 签约产品利率
	
	private String productName;// 签约产品名称
	
	private String productId;// 签约产品
	
	private BigDecimal loanAmount;// 放款金额
	
	private BigDecimal conAmount;// 合同金额
	
	private String loanDate;//放款日期
	
	private String endTime;// 结束日期
	
	private String startTime;// 开始日期
	
	private String signTime;// 签约日期
	
	private String applyId;// 申请id
	
	private String billDate;// 账单日
	
	private String payDate;// 还款日
	
	private String serviceFee;// 服务费用
	
	private BigDecimal sumFee;//费用合计
	
	private BigDecimal bankManagerFee;// 账户管理费
	
	private BigDecimal monthMoney;//月还款
	
	private BigDecimal applyMoney;//申请金额
	
	private BigDecimal interest;//利率
	
	private String prodAlias;//产品别名

	private Integer isOnline;//线上线下（0---线下，1----线上）
	
	private String handleEmp;//操作人员id

	private String handleEmpName;//操作人员
	
	private Integer handleOption;//处理意见id
	
	private String handleOptionVal;//处理意见

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

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public Integer getConStatus() {
		return conStatus;
	}

	public void setConStatus(Integer conStatus) {
		this.conStatus = conStatus;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBackBranch() {
		return backBranch;
	}

	public void setBackBranch(String backBranch) {
		this.backBranch = backBranch;
	}

	public String getBackBranchAddr() {
		return backBranchAddr;
	}

	public void setBackBranchAddr(String backBranchAddr) {
		this.backBranchAddr = backBranchAddr;
	}

	public String getSignAddr() {
		return signAddr;
	}

	public void setSignAddr(String signAddr) {
		this.signAddr = signAddr;
	}

	public String getSignStore() {
		return signStore;
	}

	public void setSignStore(String signStore) {
		this.signStore = signStore;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankPhoneNo() {
		return bankPhoneNo;
	}

	public void setBankPhoneNo(String bankPhoneNo) {
		this.bankPhoneNo = bankPhoneNo;
	}

	public Integer getProductPay() {
		return productPay;
	}

	public void setProductPay(Integer productPay) {
		this.productPay = productPay;
	}

	public String getProductPayVal() {
		return productPayVal;
	}

	public void setProductPayVal(String productPayVal) {
		this.productPayVal = productPayVal;
	}

	public Integer getSignType() {
		return signType;
	}

	public void setSignType(Integer signType) {
		this.signType = signType;
	}

	public Integer getProductPeriod() {
		return productPeriod;
	}

	public void setProductPeriod(Integer productPeriod) {
		this.productPeriod = productPeriod;
	}

	public String getProductFeeRate() {
		return productFeeRate;
	}

	public void setProductFeeRate(String productFeeRate) {
		this.productFeeRate = productFeeRate;
	}

	public BigDecimal getProductRate() {
		return productRate;
	}

	public void setProductRate(BigDecimal productRate) {
		this.productRate = productRate;
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

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getConAmount() {
		return conAmount;
	}

	public void setConAmount(BigDecimal conAmount) {
		this.conAmount = conAmount;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}

	public BigDecimal getSumFee() {
		return sumFee;
	}

	public void setSumFee(BigDecimal sumFee) {
		this.sumFee = sumFee;
	}

	public BigDecimal getBankManagerFee() {
		return bankManagerFee;
	}

	public void setBankManagerFee(BigDecimal bankManagerFee) {
		this.bankManagerFee = bankManagerFee;
	}

	public BigDecimal getMonthMoney() {
		return monthMoney;
	}

	public void setMonthMoney(BigDecimal monthMoney) {
		this.monthMoney = monthMoney;
	}

	public BigDecimal getApplyMoney() {
		return applyMoney;
	}

	public void setApplyMoney(BigDecimal applyMoney) {
		this.applyMoney = applyMoney;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public String getProdAlias() {
		return prodAlias;
	}

	public void setProdAlias(String prodAlias) {
		this.prodAlias = prodAlias;
	}

	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	public String getHandleEmp() {
		return handleEmp;
	}

	public void setHandleEmp(String handleEmp) {
		this.handleEmp = handleEmp;
	}

	public String getHandleEmpName() {
		return handleEmpName;
	}

	public void setHandleEmpName(String handleEmpName) {
		this.handleEmpName = handleEmpName;
	}

	public Integer getHandleOption() {
		return handleOption;
	}

	public void setHandleOption(Integer handleOption) {
		this.handleOption = handleOption;
	}

	public String getHandleOptionVal() {
		return handleOptionVal;
	}

	public void setHandleOptionVal(String handleOptionVal) {
		this.handleOptionVal = handleOptionVal;
	}

	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	
	
	
	
}
