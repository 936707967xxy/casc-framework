package com.hoomsun.core.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SysProduct {

	private String prodId;//产品id
	private String prodCode;//产品编号
	private String prodName;//名称
	private Integer payType;//还款方式（1：等额本息 2：等额本金  3 ：到期一次性结清   4：按期预付利息到期还本）
	private String payTypeVal;//还款方式
	private Integer sterm;//期限
	private BigDecimal monthRate;//月利率
	private BigDecimal yearRate;//年利率
	private BigDecimal punishRate;//罚息率
	private BigDecimal defaultRate;//违约率
	private BigDecimal urgentRate;//加急费率
	private Integer guarantee;//担保方式
	private BigDecimal overUp;//逾期上浮
	private Integer computeType;//计算方式（0：日、1：自然月、2：30天月）
	private Integer earningInterest;//收息方式（0：预收:1：后收）
	private Integer prodState;//产品状态
	private Date createTime;//创建时间
	private String createEmp;//创建人
	private Integer delFlag;//删除标识
	private String prodType;//产品类型
	private String prodAliasType; // 产品类型别名
	private BigDecimal mixCreditAmt;//最小批核金额
	private BigDecimal maxCreditAmt;//最大批核金额
	private BigDecimal riskRate;//风险值
	private BigDecimal realMonthRate;//实际月利率
	private String prodAlias; // 产品别名(用于app中)
	
	private List<SysProductRate> productRates;//费率计算
	private List<SysProductAdvance> productAdvances;//提前还款设置
	
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId == null ? null : prodId.trim();
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode == null ? null : prodCode.trim();
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName == null ? null : prodName.trim();
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getPayTypeVal() {
		return payTypeVal;
	}

	public void setPayTypeVal(String payTypeVal) {
		this.payTypeVal = payTypeVal == null ? null : payTypeVal.trim();
	}

	public Integer getSterm() {
		return sterm;
	}

	public void setSterm(Integer sterm) {
		this.sterm = sterm;
	}

	public BigDecimal getMonthRate() {
		return monthRate;
	}

	public void setMonthRate(BigDecimal monthRate) {
		this.monthRate = monthRate;
	}

	public BigDecimal getYearRate() {
		return yearRate;
	}

	public void setYearRate(BigDecimal yearRate) {
		this.yearRate = yearRate;
	}

	public BigDecimal getPunishRate() {
		return punishRate;
	}

	public void setPunishRate(BigDecimal punishRate) {
		this.punishRate = punishRate;
	}

	public BigDecimal getDefaultRate() {
		return defaultRate;
	}

	public void setDefaultRate(BigDecimal defaultRate) {
		this.defaultRate = defaultRate;
	}

	public BigDecimal getUrgentRate() {
		return urgentRate;
	}

	public void setUrgentRate(BigDecimal urgentRate) {
		this.urgentRate = urgentRate;
	}

	public Integer getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(Integer guarantee) {
		this.guarantee = guarantee;
	}

	public BigDecimal getOverUp() {
		return overUp;
	}

	public void setOverUp(BigDecimal overUp) {
		this.overUp = overUp;
	}

	public Integer getComputeType() {
		return computeType;
	}

	public void setComputeType(Integer computeType) {
		this.computeType = computeType;
	}

	public Integer getEarningInterest() {
		return earningInterest;
	}

	public void setEarningInterest(Integer earningInterest) {
		this.earningInterest = earningInterest;
	}

	public Integer getProdState() {
		return prodState;
	}

	public void setProdState(Integer prodState) {
		this.prodState = prodState;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateEmp() {
		return createEmp;
	}

	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp == null ? null : createEmp.trim();
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType == null ? null : prodType.trim();
	}

	public BigDecimal getMixCreditAmt() {
		return mixCreditAmt;
	}

	public void setMixCreditAmt(BigDecimal mixCreditAmt) {
		this.mixCreditAmt = mixCreditAmt;
	}

	public BigDecimal getMaxCreditAmt() {
		return maxCreditAmt;
	}

	public void setMaxCreditAmt(BigDecimal maxCreditAmt) {
		this.maxCreditAmt = maxCreditAmt;
	}
	
	
	public BigDecimal getRiskRate() {
		return riskRate;
	}

	public void setRiskRate(BigDecimal riskRate) {
		this.riskRate = riskRate;
	}

	public BigDecimal getRealMonthRate() {
		return realMonthRate;
	}

	public void setRealMonthRate(BigDecimal realMonthRate) {
		this.realMonthRate = realMonthRate;
	}

	public List<SysProductRate> getProductRates() {
		return productRates;
	}

	public void setProductRates(List<SysProductRate> productRates) {
		this.productRates = productRates;
	}

	public List<SysProductAdvance> getProductAdvances() {
		return productAdvances;
	}

	public void setProductAdvances(List<SysProductAdvance> productAdvances) {
		this.productAdvances = productAdvances;
	}

	public String getProdAlias() {
		return prodAlias;
	}

	public void setProdAlias(String prodAlias) {
		this.prodAlias = prodAlias;
	}

	public String getProdAliasType() {
		return prodAliasType;
	}

	public void setProdAliasType(String prodAliasType) {
		this.prodAliasType = prodAliasType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((computeType == null) ? 0 : computeType.hashCode());
		result = prime * result + ((defaultRate == null) ? 0 : defaultRate.hashCode());
		result = prime * result + ((delFlag == null) ? 0 : delFlag.hashCode());
		result = prime * result + ((earningInterest == null) ? 0 : earningInterest.hashCode());
		result = prime * result + ((guarantee == null) ? 0 : guarantee.hashCode());
		result = prime * result + ((maxCreditAmt == null) ? 0 : maxCreditAmt.hashCode());
		result = prime * result + ((mixCreditAmt == null) ? 0 : mixCreditAmt.hashCode());
		result = prime * result + ((monthRate == null) ? 0 : monthRate.hashCode());
		result = prime * result + ((overUp == null) ? 0 : overUp.hashCode());
		result = prime * result + ((payType == null) ? 0 : payType.hashCode());
		result = prime * result + ((payTypeVal == null) ? 0 : payTypeVal.hashCode());
		result = prime * result + ((prodAlias == null) ? 0 : prodAlias.hashCode());
		result = prime * result + ((prodCode == null) ? 0 : prodCode.hashCode());
		result = prime * result + ((prodName == null) ? 0 : prodName.hashCode());
		result = prime * result + ((prodType == null) ? 0 : prodType.hashCode());
		result = prime * result + ((productAdvances == null) ? 0 : productAdvances.hashCode());
		result = prime * result + ((productRates == null) ? 0 : productRates.hashCode());
		result = prime * result + ((punishRate == null) ? 0 : punishRate.hashCode());
		result = prime * result + ((realMonthRate == null) ? 0 : realMonthRate.hashCode());
		result = prime * result + ((riskRate == null) ? 0 : riskRate.hashCode());
		result = prime * result + ((sterm == null) ? 0 : sterm.hashCode());
		result = prime * result + ((urgentRate == null) ? 0 : urgentRate.hashCode());
		result = prime * result + ((yearRate == null) ? 0 : yearRate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysProduct other = (SysProduct) obj;
		if (computeType == null) {
			if (other.computeType != null)
				return false;
		} else if (!computeType.equals(other.computeType))
			return false;
		if (defaultRate == null) {
			if (other.defaultRate != null)
				return false;
		} else if (!defaultRate.equals(other.defaultRate))
			return false;
		if (delFlag == null) {
			if (other.delFlag != null)
				return false;
		} else if (!delFlag.equals(other.delFlag))
			return false;
		if (earningInterest == null) {
			if (other.earningInterest != null)
				return false;
		} else if (!earningInterest.equals(other.earningInterest))
			return false;
		if (guarantee == null) {
			if (other.guarantee != null)
				return false;
		} else if (!guarantee.equals(other.guarantee))
			return false;
		if (maxCreditAmt == null) {
			if (other.maxCreditAmt != null)
				return false;
		} else if (!maxCreditAmt.equals(other.maxCreditAmt))
			return false;
		if (mixCreditAmt == null) {
			if (other.mixCreditAmt != null)
				return false;
		} else if (!mixCreditAmt.equals(other.mixCreditAmt))
			return false;
		if (monthRate == null) {
			if (other.monthRate != null)
				return false;
		} else if (!monthRate.equals(other.monthRate))
			return false;
		if (overUp == null) {
			if (other.overUp != null)
				return false;
		} else if (!overUp.equals(other.overUp))
			return false;
		if (payType == null) {
			if (other.payType != null)
				return false;
		} else if (!payType.equals(other.payType))
			return false;
		if (payTypeVal == null) {
			if (other.payTypeVal != null)
				return false;
		} else if (!payTypeVal.equals(other.payTypeVal))
			return false;
		if (prodAlias == null) {
			if (other.prodAlias != null)
				return false;
		} else if (!prodAlias.equals(other.prodAlias))
			return false;
		if (prodCode == null) {
			if (other.prodCode != null)
				return false;
		} else if (!prodCode.equals(other.prodCode))
			return false;
		if (prodName == null) {
			if (other.prodName != null)
				return false;
		} else if (!prodName.equals(other.prodName))
			return false;
		if (prodType == null) {
			if (other.prodType != null)
				return false;
		} else if (!prodType.equals(other.prodType))
			return false;
		if (productAdvances == null) {
			if (other.productAdvances != null)
				return false;
		} else if (!productAdvances.equals(other.productAdvances))
			return false;
		if (productRates == null) {
			if (other.productRates != null)
				return false;
		} else if (!productRates.equals(other.productRates))
			return false;
		if (punishRate == null) {
			if (other.punishRate != null)
				return false;
		} else if (!punishRate.equals(other.punishRate))
			return false;
		if (realMonthRate == null) {
			if (other.realMonthRate != null)
				return false;
		} else if (!realMonthRate.equals(other.realMonthRate))
			return false;
		if (riskRate == null) {
			if (other.riskRate != null)
				return false;
		} else if (!riskRate.equals(other.riskRate))
			return false;
		if (sterm == null) {
			if (other.sterm != null)
				return false;
		} else if (!sterm.equals(other.sterm))
			return false;
		if (urgentRate == null) {
			if (other.urgentRate != null)
				return false;
		} else if (!urgentRate.equals(other.urgentRate))
			return false;
		if (yearRate == null) {
			if (other.yearRate != null)
				return false;
		} else if (!yearRate.equals(other.yearRate))
			return false;
		return true;
	}
	
}