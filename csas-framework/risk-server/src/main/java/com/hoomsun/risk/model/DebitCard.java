/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月6日 <br>
 * 描述：储蓄卡
 */
@Document(collection = "RK_DEBIT_CARD")
public class DebitCard implements Serializable {
	private static final long serialVersionUID = -1440631660032437654L;
	@Id
	private String id;
	@Indexed
	private String cardNumber;
	@Indexed
	private String idNumber;
	private String bankName;
	private String bankCode;
	private Integer accountType;// 账户类型 "1": "工资卡", "2": "私营业主"
	private String accountTypeVal;
	private BigDecimal currentBalance = BigDecimal.ZERO;// 当前余额
	private BigDecimal interestOne = BigDecimal.ZERO;// 结息
	private BigDecimal interestTwo = BigDecimal.ZERO;
	private Integer spendHabitType;// 支出习惯
	private String spendHabitTypeVal;
	private Integer incomeStabilityVal;
	private String incomeStability;// 收入稳定性
	private Integer revenueTrendVal;
	private String revenueTrend;// 收入趋势
	private BigDecimal stability = BigDecimal.ZERO;// 稳定性 值 变异系数
	private BigDecimal trend = BigDecimal.ZERO;// 趋势 值 中位数
	private Date createDate;
	private Date claimDate;
	@Indexed
	private String applyId;

	private List<DebitCardBill> debitCardBills;

	public void addDebitCardBills(DebitCardBill cardBill) {
		if (null == debitCardBills && debitCardBills.size() < 1) {
			debitCardBills = new ArrayList<DebitCardBill>();
		}
		debitCardBills.add(cardBill);
	}

	public Date getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
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
		this.accountTypeVal = accountTypeVal;
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
		return spendHabitTypeVal;
	}

	public void setSpendHabitTypeVal(String spendHabitTypeVal) {
		this.spendHabitTypeVal = spendHabitTypeVal;
	}

	public Integer getIncomeStabilityVal() {
		return incomeStabilityVal;
	}

	public void setIncomeStabilityVal(Integer incomeStabilityVal) {
		this.incomeStabilityVal = incomeStabilityVal;
	}

	public String getIncomeStability() {
		return incomeStability;
	}

	public void setIncomeStability(String incomeStability) {
		this.incomeStability = incomeStability;
	}

	public Integer getRevenueTrendVal() {
		return revenueTrendVal;
	}

	public void setRevenueTrendVal(Integer revenueTrendVal) {
		this.revenueTrendVal = revenueTrendVal;
	}

	public String getRevenueTrend() {
		return revenueTrend;
	}

	public void setRevenueTrend(String revenueTrend) {
		this.revenueTrend = revenueTrend;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<DebitCardBill> getDebitCardBills() {
		return debitCardBills;
	}

	public void setDebitCardBills(List<DebitCardBill> debitCardBills) {
		this.debitCardBills = debitCardBills;
	}

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

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月6日 <br>
	 * 描述： 账单
	 */
	public static class DebitCardBill implements Serializable {
		private static final long serialVersionUID = 7237916508437187539L;

		private Integer streamVal;// 流水的类型 1工资进账 2对公进账 3总计
		private String streamName;// 流水的类型 工资进账 对公进账 总计
		private BigDecimal theAverage = BigDecimal.ZERO;// 平均值
		private BigDecimal coefficintVariation = BigDecimal.ZERO;// 变异系数
		private BigDecimal total = BigDecimal.ZERO;// 流水合计
		private BigDecimal oneMonth = BigDecimal.ZERO;// 近第一月
		private BigDecimal twoMonth = BigDecimal.ZERO;// 近二月
		private BigDecimal threeMonth = BigDecimal.ZERO;// 近三月
		private BigDecimal fourMonth = BigDecimal.ZERO;// 近四月
		private BigDecimal fiveMonth = BigDecimal.ZERO;// 近五月
		private BigDecimal sixMonth = BigDecimal.ZERO;// 近六月
		private BigDecimal standard = BigDecimal.ZERO;// 标准差
		private BigDecimal variance = BigDecimal.ZERO;// 方差
		private BigDecimal median = BigDecimal.ZERO;// 中位数

		public Integer getStreamVal() {
			return streamVal;
		}

		public void setStreamVal(Integer streamVal) {
			this.streamVal = streamVal;
		}

		public String getStreamName() {
			return streamName;
		}

		public void setStreamName(String streamName) {
			this.streamName = streamName;
		}

		public BigDecimal getTheAverage() {
			return theAverage;
		}

		public void setTheAverage(BigDecimal theAverage) {
			this.theAverage = theAverage;
		}

		public BigDecimal getCoefficintVariation() {
			return coefficintVariation;
		}

		public void setCoefficintVariation(BigDecimal coefficintVariation) {
			this.coefficintVariation = coefficintVariation;
		}

		public BigDecimal getTotal() {
			return total;
		}

		public void setTotal(BigDecimal total) {
			this.total = total;
		}

		public BigDecimal getOneMonth() {
			return oneMonth;
		}

		public void setOneMonth(BigDecimal oneMonth) {
			this.oneMonth = oneMonth;
		}

		public BigDecimal getTwoMonth() {
			return twoMonth;
		}

		public void setTwoMonth(BigDecimal twoMonth) {
			this.twoMonth = twoMonth;
		}

		public BigDecimal getThreeMonth() {
			return threeMonth;
		}

		public void setThreeMonth(BigDecimal threeMonth) {
			this.threeMonth = threeMonth;
		}

		public BigDecimal getFourMonth() {
			return fourMonth;
		}

		public void setFourMonth(BigDecimal fourMonth) {
			this.fourMonth = fourMonth;
		}

		public BigDecimal getFiveMonth() {
			return fiveMonth;
		}

		public void setFiveMonth(BigDecimal fiveMonth) {
			this.fiveMonth = fiveMonth;
		}

		public BigDecimal getSixMonth() {
			return sixMonth;
		}

		public void setSixMonth(BigDecimal sixMonth) {
			this.sixMonth = sixMonth;
		}

		public BigDecimal getStandard() {
			return standard;
		}

		public void setStandard(BigDecimal standard) {
			this.standard = standard;
		}

		public BigDecimal getVariance() {
			return variance;
		}

		public void setVariance(BigDecimal variance) {
			this.variance = variance;
		}

		public BigDecimal getMedian() {
			return median;
		}

		public void setMedian(BigDecimal median) {
			this.median = median;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

	}
}
