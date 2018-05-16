package com.hoomsun.core.model;

import java.math.BigDecimal;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月26日 <br>
 * 描述： 提前还款设置
 */
public class SysProductAdvance {
    private String id;
    private String prodId;
    private Integer startPeriods;//开始期限
    private Integer endPeriods;//结束期限
    private BigDecimal rate;//计算比例
    private Integer computeType;//计算类型   0：剩余本金   1：总费用  2：合同金额  3：放款金额

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
    }

    public Integer getStartPeriods() {
		return startPeriods;
	}

	public void setStartPeriods(Integer startPeriods) {
		this.startPeriods = startPeriods;
	}

	public Integer getEndPeriods() {
		return endPeriods;
	}

	public void setEndPeriods(Integer endPeriods) {
		this.endPeriods = endPeriods;
	}

	public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getComputeType() {
        return computeType;
    }

    public void setComputeType(Integer computeType) {
        this.computeType = computeType;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((computeType == null) ? 0 : computeType.hashCode());
		result = prime * result + ((endPeriods == null) ? 0 : endPeriods.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((prodId == null) ? 0 : prodId.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result + ((startPeriods == null) ? 0 : startPeriods.hashCode());
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
		SysProductAdvance other = (SysProductAdvance) obj;
		if (computeType == null) {
			if (other.computeType != null)
				return false;
		} else if (!computeType.equals(other.computeType))
			return false;
		if (endPeriods == null) {
			if (other.endPeriods != null)
				return false;
		} else if (!endPeriods.equals(other.endPeriods))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (prodId == null) {
			if (other.prodId != null)
				return false;
		} else if (!prodId.equals(other.prodId))
			return false;
		if (rate == null) {
			if (other.rate != null)
				return false;
		} else if (!rate.equals(other.rate))
			return false;
		if (startPeriods == null) {
			if (other.startPeriods != null)
				return false;
		} else if (!startPeriods.equals(other.startPeriods))
			return false;
		return true;
	}
	
}