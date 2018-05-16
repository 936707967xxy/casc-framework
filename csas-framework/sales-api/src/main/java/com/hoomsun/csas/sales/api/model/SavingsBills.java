package com.hoomsun.csas.sales.api.model;

import java.math.BigDecimal;

/**
 * 储蓄卡账单
 */
public class SavingsBills {
	private String sbId;
	private String scId;// 外键
	private Integer streamVal;// 流水的类型 1工资进账 2对公进账 3总计
	private String streamName;// 流水的类型 工资进账 对公进账 总计
	private BigDecimal theAverage;// 平均值
	private BigDecimal coefficintVariation;// 变异系数
	private BigDecimal total;// 流水合计
	private BigDecimal oneMonth;// 近第一月
	private BigDecimal twoMonth;// 近二月
	private BigDecimal threeMonth;// 近三月
	private BigDecimal fourMonth;// 近四月
	private BigDecimal fiveMonth;// 近五月
	private BigDecimal sixMonth;// 近六月
	private BigDecimal standard;// 标准差
	private BigDecimal variance;// 方差
	private BigDecimal median;// 中位数
	
	public SavingsBills(Integer streamVal, String streamName) {
		this.streamVal = streamVal;
		this.streamName = streamName;
	}

	public SavingsBills() {
	}

	public String getSbId() {
		return sbId;
	}

	public void setSbId(String sbId) {
		this.sbId = sbId == null ? null : sbId.trim();
	}

	public String getScId() {
		return scId;
	}

	public void setScId(String scId) {
		this.scId = scId == null ? null : scId.trim();
	}

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
}