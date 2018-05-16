package com.hoomsun.core.model;

import java.math.BigDecimal;

public class SysProductRate {
	
	private String prId;//主键

    private String prName;//费率名称

    private String prType;//费率类型

    private Integer charging;//收费方式（0:预收、1后收）

    private Integer compute;//计费方式（0本金、1笔）

    private BigDecimal proportion;//计算比例（月）

    private Integer isperiod;//是否按期收取(0：false 1:true)

    private Integer iscapital;//是否作为本金(0:false 1:true)

    private String prodId;//产品id

    public String getPrId() {
        return prId;
    }

    public void setPrId(String prId) {
        this.prId = prId == null ? null : prId.trim();
    }

    public String getPrName() {
        return prName;
    }

    public void setPrName(String prName) {
        this.prName = prName == null ? null : prName.trim();
    }

    public String getPrType() {
        return prType;
    }

    public void setPrType(String prType) {
        this.prType = prType == null ? null : prType.trim();
    }

    public Integer getCharging() {
        return charging;
    }

    public void setCharging(Integer charging) {
        this.charging = charging;
    }

    public Integer getCompute() {
        return compute;
    }

    public void setCompute(Integer compute) {
        this.compute = compute;
    }

    public BigDecimal getProportion() {
        return proportion;
    }

    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }

    public Integer getIsperiod() {
        return isperiod;
    }

    public void setIsperiod(Integer isperiod) {
        this.isperiod = isperiod;
    }

    public Integer getIscapital() {
        return iscapital;
    }

    public void setIscapital(Integer iscapital) {
        this.iscapital = iscapital;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((charging == null) ? 0 : charging.hashCode());
		result = prime * result + ((compute == null) ? 0 : compute.hashCode());
		result = prime * result + ((iscapital == null) ? 0 : iscapital.hashCode());
		result = prime * result + ((isperiod == null) ? 0 : isperiod.hashCode());
		result = prime * result + ((prId == null) ? 0 : prId.hashCode());
		result = prime * result + ((prName == null) ? 0 : prName.hashCode());
		result = prime * result + ((prType == null) ? 0 : prType.hashCode());
		result = prime * result + ((prodId == null) ? 0 : prodId.hashCode());
		result = prime * result + ((proportion == null) ? 0 : proportion.hashCode());
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
		SysProductRate other = (SysProductRate) obj;
		if (charging == null) {
			if (other.charging != null)
				return false;
		} else if (!charging.equals(other.charging))
			return false;
		if (compute == null) {
			if (other.compute != null)
				return false;
		} else if (!compute.equals(other.compute))
			return false;
		if (iscapital == null) {
			if (other.iscapital != null)
				return false;
		} else if (!iscapital.equals(other.iscapital))
			return false;
		if (isperiod == null) {
			if (other.isperiod != null)
				return false;
		} else if (!isperiod.equals(other.isperiod))
			return false;
		if (prId == null) {
			if (other.prId != null)
				return false;
		} else if (!prId.equals(other.prId))
			return false;
		if (prName == null) {
			if (other.prName != null)
				return false;
		} else if (!prName.equals(other.prName))
			return false;
		if (prType == null) {
			if (other.prType != null)
				return false;
		} else if (!prType.equals(other.prType))
			return false;
		if (prodId == null) {
			if (other.prodId != null)
				return false;
		} else if (!prodId.equals(other.prodId))
			return false;
		if (proportion == null) {
			if (other.proportion != null)
				return false;
		} else if (!proportion.equals(other.proportion))
			return false;
		return true;
	}
    
}