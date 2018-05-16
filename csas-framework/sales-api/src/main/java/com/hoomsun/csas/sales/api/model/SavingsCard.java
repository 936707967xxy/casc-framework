package com.hoomsun.csas.sales.api.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 银行流水
 */
public class SavingsCard {
	private String scId;
	private String cardNo;// 卡号
	private String bankName;// 开户行
	private Integer accountType;// 账户类型 "1": "工资卡", "2": "私营业主"
	private String accountTypeVal;
	private BigDecimal currentBalance;// 当前余额
	private BigDecimal interestOne;// 结息
	private BigDecimal interestTwo;
	private Integer spendHabitType;// 支出习惯
	private String spendHabitTypeVal;
	private String incomeStability;// 收入稳定性
	private String revenueTrend;// 收入趋势
	private BigDecimal stability;// 稳定性 值
	private BigDecimal trend;// 趋势 值
	private Integer incomeStabilityVal;
	private Integer revenueTrendVal;

	private String applyId;
	private Date createDate;
	private String empId;
	private String empName;

	private List<SavingsBills> savingsBills = new ArrayList<SavingsBills>();
	
	public void addSavingsBills(SavingsBills bills) {
		this.savingsBills.add(bills);
	}
	
	public String getScId() {
		return scId;
	}

	public void setScId(String scId) {
		this.scId = scId == null ? null : scId.trim();
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo == null ? null : cardNo.trim();
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public String getAccountTypeVal() {
		return accountTypeVal;
	}

	public void setAccountTypeVal(String accountTypeVal) {
		this.accountTypeVal = accountTypeVal == null ? null : accountTypeVal.trim();
	}

	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}

	public BigDecimal getInterestOne() {
		return interestOne;
	}

	public void setInterestOne(BigDecimal interestOne) {
		this.interestOne = interestOne;
	}

	public BigDecimal getInterestTwo() {
		return interestTwo;
	}

	public void setInterestTwo(BigDecimal interestTwo) {
		this.interestTwo = interestTwo;
	}

	public Integer getSpendHabitType() {
		return spendHabitType;
	}

	public void setSpendHabitType(Integer spendHabitType) {
		this.spendHabitType = spendHabitType;
	}

	public String getSpendHabitTypeVal() {
		if(StringUtils.isNotBlank(spendHabitTypeVal)) {
			if(1 == this.spendHabitType) {
				setSpendHabitTypeVal("发放后一次性取出");
			}else if(2 == this.spendHabitType) {
				setSpendHabitTypeVal("逐步取出或消费");
			}else {
				setSpendHabitTypeVal("未知");
			}
		}
		return spendHabitTypeVal;
	}

	public void setSpendHabitTypeVal(String spendHabitTypeVal) {
		this.spendHabitTypeVal = spendHabitTypeVal == null ? null : spendHabitTypeVal.trim();
	}

	public String getIncomeStability() {
		return incomeStability;
	}

	public void setIncomeStability(String incomeStability) {
		this.incomeStability = incomeStability == null ? null : incomeStability.trim();
	}

	public String getRevenueTrend() {
		return revenueTrend;
	}

	public void setRevenueTrend(String revenueTrend) {
		this.revenueTrend = revenueTrend == null ? null : revenueTrend.trim();
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId == null ? null : applyId.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId == null ? null : empId.trim();
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName == null ? null : empName.trim();
	}

	public List<SavingsBills> getSavingsBills() {
		return savingsBills;
	}

	public void setSavingsBills(List<SavingsBills> savingsBills) {
		this.savingsBills = savingsBills;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public BigDecimal getStability() {
		return stability;
	}

	public void setStability(BigDecimal stability) {
		this.stability = stability;
	}

	public BigDecimal getTrend() {
		return trend;
	}

	public void setTrend(BigDecimal trend) {
		this.trend = trend;
	}

	public Integer getIncomeStabilityVal() {
		return incomeStabilityVal;
	}

	public void setIncomeStabilityVal(Integer incomeStabilityVal) {
		this.incomeStabilityVal = incomeStabilityVal;
	}

	public Integer getRevenueTrendVal() {
		return revenueTrendVal;
	}

	public void setRevenueTrendVal(Integer revenueTrendVal) {
		this.revenueTrendVal = revenueTrendVal;
	}

}