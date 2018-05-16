package com.hoomsun.csas.apply.query.model;

import java.sql.Timestamp;

/**
 * 作者：ygzhao <br>
 * 创建时间：2017年11月23日 <br>
 * 描述：资产信息表
 */
public class AssetInfo {
    private String asinfoPkId;//主键

    private Integer propertyType;//房产类型ID（1：商业按揭购房，2：持证抵押房，3：公积金按揭购房，4：商业/公积金组合按揭购房，5：无按揭购房）

    private String propertyTypeVal;//房产类型值

    private Timestamp proBuyTime;//房产取得时间

    private String proBuyPrice;//购买/建造总价

    private String gfa;//房产建筑面积

    private String proLoanLife;//借款年限

    private String proMortgageMonthly;//月还款金额

    private String proLoans;//借款余额

    private String proCreditPeriod;//剩余期限

    private String propertyAddress;//房产地址 

    private String propertyProvCode;//房产所在省份id

    private String propertyProvName;//房产所在省份名称

    private String propertyCityCode;//房产所在市id

    private String propertyCityName;//房产所在市名称

    private String propertyAreaCode;//房产所在区id

    private String propertyAreaName;//房产所在区名称

    private String propertyAddressDetail;//房产明细地址

    private String propertyZipCode;//房产地址邮政编码

    private Integer isHomeAddress;//是否同住宅（0：否，1：是）

    private String isHomeAddressVal;//是否同住宅值

    private Integer carHava;//拥有车辆（1：无，2：全款购买，3：按揭购买）

    private String carHavaVal;//拥有车辆值

    private String priceCar;//购买价（万）

    private String carAge;//车龄

    private Timestamp carLoanDate;//贷款发放日期

    private String carMortgageMonthly;//按揭月供（元）

    private String carLoanCeiling;//贷款总额（万）

    private String carBanlanceVal;//贷款余额

    private String carPeriodVal;//剩余期限

    private String applyId;//申请表ID

    private Timestamp createTime;//创建时间

    public String getAsinfoPkId() {
        return asinfoPkId;
    }

    public void setAsinfoPkId(String asinfoPkId) {
        this.asinfoPkId = asinfoPkId == null ? null : asinfoPkId.trim();
    }

    public Integer getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(Integer propertyType) {
        this.propertyType = propertyType;
    }

    public String getPropertyTypeVal() {
        return propertyTypeVal;
    }

    public void setPropertyTypeVal(String propertyTypeVal) {
        this.propertyTypeVal = propertyTypeVal == null ? null : propertyTypeVal.trim();
    }

    public Timestamp getProBuyTime() {
        return proBuyTime;
    }

    public void setProBuyTime(Timestamp proBuyTime) {
        this.proBuyTime = proBuyTime;
    }

    public String getProBuyPrice() {
        return proBuyPrice;
    }

    public void setProBuyPrice(String proBuyPrice) {
        this.proBuyPrice = proBuyPrice == null ? null : proBuyPrice.trim();
    }

    public String getGfa() {
        return gfa;
    }

    public void setGfa(String gfa) {
        this.gfa = gfa == null ? null : gfa.trim();
    }

    public String getProLoanLife() {
        return proLoanLife;
    }

    public void setProLoanLife(String proLoanLife) {
        this.proLoanLife = proLoanLife == null ? null : proLoanLife.trim();
    }

    public String getProMortgageMonthly() {
        return proMortgageMonthly;
    }

    public void setProMortgageMonthly(String proMortgageMonthly) {
        this.proMortgageMonthly = proMortgageMonthly == null ? null : proMortgageMonthly.trim();
    }

    public String getProLoans() {
        return proLoans;
    }

    public void setProLoans(String proLoans) {
        this.proLoans = proLoans == null ? null : proLoans.trim();
    }

    public String getProCreditPeriod() {
        return proCreditPeriod;
    }

    public void setProCreditPeriod(String proCreditPeriod) {
        this.proCreditPeriod = proCreditPeriod == null ? null : proCreditPeriod.trim();
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress == null ? null : propertyAddress.trim();
    }

    public String getPropertyProvCode() {
        return propertyProvCode;
    }

    public void setPropertyProvCode(String propertyProvCode) {
        this.propertyProvCode = propertyProvCode == null ? null : propertyProvCode.trim();
    }

    public String getPropertyProvName() {
        return propertyProvName;
    }

    public void setPropertyProvName(String propertyProvName) {
        this.propertyProvName = propertyProvName == null ? null : propertyProvName.trim();
    }

    public String getPropertyCityCode() {
        return propertyCityCode;
    }

    public void setPropertyCityCode(String propertyCityCode) {
        this.propertyCityCode = propertyCityCode == null ? null : propertyCityCode.trim();
    }

    public String getPropertyCityName() {
        return propertyCityName;
    }

    public void setPropertyCityName(String propertyCityName) {
        this.propertyCityName = propertyCityName == null ? null : propertyCityName.trim();
    }

    public String getPropertyAreaCode() {
        return propertyAreaCode;
    }

    public void setPropertyAreaCode(String propertyAreaCode) {
        this.propertyAreaCode = propertyAreaCode == null ? null : propertyAreaCode.trim();
    }

    public String getPropertyAreaName() {
        return propertyAreaName;
    }

    public void setPropertyAreaName(String propertyAreaName) {
        this.propertyAreaName = propertyAreaName == null ? null : propertyAreaName.trim();
    }

    public String getPropertyAddressDetail() {
        return propertyAddressDetail;
    }

    public void setPropertyAddressDetail(String propertyAddressDetail) {
        this.propertyAddressDetail = propertyAddressDetail == null ? null : propertyAddressDetail.trim();
    }

    public String getPropertyZipCode() {
        return propertyZipCode;
    }

    public void setPropertyZipCode(String propertyZipCode) {
        this.propertyZipCode = propertyZipCode == null ? null : propertyZipCode.trim();
    }

    public Integer getIsHomeAddress() {
        return isHomeAddress;
    }

    public void setIsHomeAddress(Integer isHomeAddress) {
        this.isHomeAddress = isHomeAddress;
    }

    public String getIsHomeAddressVal() {
        return isHomeAddressVal;
    }

    public void setIsHomeAddressVal(String isHomeAddressVal) {
        this.isHomeAddressVal = isHomeAddressVal == null ? null : isHomeAddressVal.trim();
    }

    public Integer getCarHava() {
        return carHava;
    }

    public void setCarHava(Integer carHava) {
        this.carHava = carHava;
    }

    public String getCarHavaVal() {
        return carHavaVal;
    }

    public void setCarHavaVal(String carHavaVal) {
        this.carHavaVal = carHavaVal == null ? null : carHavaVal.trim();
    }

    public String getPriceCar() {
        return priceCar;
    }

    public void setPriceCar(String priceCar) {
        this.priceCar = priceCar == null ? null : priceCar.trim();
    }

    public String getCarAge() {
        return carAge;
    }

    public void setCarAge(String carAge) {
        this.carAge = carAge == null ? null : carAge.trim();
    }

    public Timestamp getCarLoanDate() {
        return carLoanDate;
    }

    public void setCarLoanDate(Timestamp carLoanDate) {
        this.carLoanDate = carLoanDate;
    }

    public String getCarMortgageMonthly() {
        return carMortgageMonthly;
    }

    public void setCarMortgageMonthly(String carMortgageMonthly) {
        this.carMortgageMonthly = carMortgageMonthly == null ? null : carMortgageMonthly.trim();
    }

    public String getCarLoanCeiling() {
        return carLoanCeiling;
    }

    public void setCarLoanCeiling(String carLoanCeiling) {
        this.carLoanCeiling = carLoanCeiling == null ? null : carLoanCeiling.trim();
    }

    public String getCarBanlanceVal() {
        return carBanlanceVal;
    }

    public void setCarBanlanceVal(String carBanlanceVal) {
        this.carBanlanceVal = carBanlanceVal == null ? null : carBanlanceVal.trim();
    }

    public String getCarPeriodVal() {
        return carPeriodVal;
    }

    public void setCarPeriodVal(String carPeriodVal) {
        this.carPeriodVal = carPeriodVal == null ? null : carPeriodVal.trim();
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

	@Override
	public String toString() {
		return "AssetInfo [asinfoPkId=" + asinfoPkId + ", propertyType=" + propertyType + ", propertyTypeVal=" + propertyTypeVal + ", proBuyTime=" + proBuyTime + ", proBuyPrice=" + proBuyPrice + ", gfa=" + gfa + ", proLoanLife="
				+ proLoanLife + ", proMortgageMonthly=" + proMortgageMonthly + ", proLoans=" + proLoans + ", proCreditPeriod=" + proCreditPeriod + ", propertyAddress=" + propertyAddress + ", propertyProvCode=" + propertyProvCode
				+ ", propertyProvName=" + propertyProvName + ", propertyCityCode=" + propertyCityCode + ", propertyCityName=" + propertyCityName + ", propertyAreaCode=" + propertyAreaCode + ", propertyAreaName=" + propertyAreaName
				+ ", propertyAddressDetail=" + propertyAddressDetail + ", propertyZipCode=" + propertyZipCode + ", isHomeAddress=" + isHomeAddress + ", isHomeAddressVal=" + isHomeAddressVal + ", carHava=" + carHava + ", carHavaVal="
				+ carHavaVal + ", priceCar=" + priceCar + ", carAge=" + carAge + ", carLoanDate=" + carLoanDate + ", carMortgageMonthly=" + carMortgageMonthly + ", carLoanCeiling=" + carLoanCeiling + ", carBanlanceVal=" + carBanlanceVal
				+ ", carPeriodVal=" + carPeriodVal + ", applyId=" + applyId + ", createTime=" + createTime + "]";
	}
    
}