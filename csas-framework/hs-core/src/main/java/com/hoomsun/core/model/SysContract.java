package com.hoomsun.core.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.hoomsun.common.util.DateUtil;

public class SysContract {
	private String conId;// 主键
	private String conNo;// 合同编号
	private String custName;
	private String idNumber;
	private String loanId;// 申请编号
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;// 创建时间
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
	private Integer signType;// 签约类型 0：电子  1：纸质
	private Integer productPeriod;// 签约产品期限
	private String productFeeRate;// 签约产品费率 json
	private BigDecimal productRate;// 签约产品利率 合同利率
	private String productName;// 签约产品名称
	private String productId;// 签约产品
	private BigDecimal surplus;// 剩余债权
	private BigDecimal debt;// 债权值
	private BigDecimal loanAmount;// 放款金额
	private BigDecimal conAmount;// 合同金额
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date endTime;// 结束日期
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date startTime;// 开始日期
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date signTime;// 签约日期
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
	private String proId;//省ID
    private String proName;//省名称
    private String cityId;//城市ID
    private String cityName;//城市名称
    private Integer isOnline;//线上线下（0---线下，1----线上）
    private String createEmpId;
    private String createEmpName;
    private BigDecimal realRate;//费率
    private BigDecimal irrVal;//综合年化成本
    
	private List<SysRepaymentPlan> repaymentPlans;
	
	public SysContract() {
		
	}
	
	public String getConId() {
		return conId;
	}

	public void setConId(String conId) {
		this.conId = conId == null ? null : conId.trim();
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId == null ? null : loanId.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getConStatus() {
		return conStatus;
	}

	public void setConStatus(Integer conStatus) {
		this.conStatus = conStatus;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank == null ? null : bank.trim();
	}

	public String getSignAddr() {
		return signAddr;
	}

	public void setSignAddr(String signAddr) {
		this.signAddr = signAddr == null ? null : signAddr.trim();
	}

	public String getSignStore() {
		return signStore;
	}

	public void setSignStore(String signStore) {
		this.signStore = signStore == null ? null : signStore.trim();
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo == null ? null : bankNo.trim();
	}

	public Integer getProductPay() {
		return productPay;
	}

	public void setProductPay(Integer productPay) {
		this.productPay = productPay;
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
		this.productFeeRate = productFeeRate == null ? null : productFeeRate.trim();
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
		this.productName = productName == null ? null : productName.trim();
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId == null ? null : productId.trim();
	}

	public BigDecimal getSurplus() {
		return surplus;
	}

	public void setSurplus(BigDecimal surplus) {
		this.surplus = surplus;
	}

	public BigDecimal getDebt() {
		return debt;
	}

	public void setDebt(BigDecimal debt) {
		this.debt = debt;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getConNo() {
		return conNo;
	}

	public void setConNo(String conNo) {
		this.conNo = conNo == null ? null : conNo.trim();
	}

	public BigDecimal getConAmount() {
		return conAmount;
	}

	public void setConAmount(BigDecimal conAmount) {
		this.conAmount = conAmount;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getStrEndDate() {
		if (this.endTime == null) {
			return null;
		}
		return DateUtil.yyyyMMdd.format(this.endTime);
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public String getStrStartDate() {
		if (this.startTime == null) {
			return null;
		}
		return DateUtil.yyyyMMdd.format(this.startTime);
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getSignTime() {
		return signTime;
	}

	public String getStrSignTime() {
		if (this.signTime == null) {
			return null;
		}
		return DateUtil.yyyyMMdd.format(this.signTime);
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId == null ? null : applyId.trim();
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate == null ? null : billDate.trim();
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate == null ? null : payDate.trim();
	}

	public String getProductPayVal() {
		return productPayVal;
	}

	public void setProductPayVal(String productPayVal) {
		this.productPayVal = productPayVal == null ? null : productPayVal.trim();
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee == null ? null : serviceFee.trim();
	}

	public List<SysRepaymentPlan> getRepaymentPlans() {
		return repaymentPlans;
	}

	public void setRepaymentPlans(List<SysRepaymentPlan> repaymentPlans) {
		this.repaymentPlans = repaymentPlans;
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

	public String getBankPhoneNo() {
		return bankPhoneNo;
	}

	public void setBankPhoneNo(String bankPhoneNo) {
		this.bankPhoneNo = bankPhoneNo;
	}

	public BigDecimal getBankManagerFee() {
		return bankManagerFee;
	}

	public void setBankManagerFee(BigDecimal bankManagerFee) {
		this.bankManagerFee = bankManagerFee;
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

	public BigDecimal getMonthMoney() {
		return monthMoney;
	}

	public void setMonthMoney(BigDecimal monthMoney) {
		this.monthMoney = monthMoney;
	}

	public String getProdAlias() {
		return prodAlias;
	}

	public void setProdAlias(String prodAlias) {
		this.prodAlias = prodAlias;
	}

	public BigDecimal getSumFee() {
		return sumFee;
	}

	public void setSumFee(BigDecimal sumFee) {
		this.sumFee = sumFee;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	public String getCreateEmpId() {
		return createEmpId;
	}

	public void setCreateEmpId(String createEmpId) {
		this.createEmpId = createEmpId;
	}

	public String getCreateEmpName() {
		return createEmpName;
	}

	public void setCreateEmpName(String createEmpName) {
		this.createEmpName = createEmpName;
	}

	public BigDecimal getRealRate() {
		return realRate;
	}

	public void setRealRate(BigDecimal realRate) {
		this.realRate = realRate;
	}

	public BigDecimal getIrrVal() {
		return irrVal;
	}

	public void setIrrVal(BigDecimal irrVal) {
		this.irrVal = irrVal;
	}
}