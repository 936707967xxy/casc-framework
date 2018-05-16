package com.hoomsun.app.api.model;

import java.util.Date;

public class Borrower {
    private String id;

    private String nameBefore;  //曾用名

    private String graduateInstitutions; //毕业院校

    private String raisePerson;  //供养人数

    private String childNumber;  //子女数目

    private Integer maritalStatus; //婚姻状况 （1：未婚，2：已婚，，4：离异，3：丧偶，5：其他）

    private String maritalStatusVal; //婚姻值

    private String email;  //邮箱

    private Date startResidenceDate; // 来借款时间

    private Date comeHereDate;// 来申请地时间

    private Integer eduBackground;// 学历ID 1:硕士及以上,2:本科,3:大专,4:高中及以下

    private String eduBackgroundVal;// 学历值

    private String qqWechat;// QQ/微信

    private String rresidenceAddress;// 户口所在地

    private String rresidenceProvCode;// 户口所在省份id

    private String rresidenceProvName;// 户口所在省份名称

    private String rresidenceCityCode;// 户口所在市id

    private String rresidenceCityName;// 户口所在市名称

    private String rresidenceAreaCode;// 户口所在区id

    private String rresidenceAreaName;// 户口所在区名称

    private String rresidenceAddressDetail;// 户口所在详细地址
    
    private String rresidenceZipCode;// 户口邮政编码

    private String houseAddress;//// 现居住地 

    private String houseProvCode;// 现居住地省份id

    private String houseProvName;// 现居住地省份名称

    private String houseCityName;// 现居住地市名称

    private String houseCityCode;// 现居住地市id

    private String houseAreaName;// 现居住地市名称

    private String houseAreaCode;// 现居住地区id

    private String houseAddressDetail;// 户口所在详细地址

    private String houseZipCode;// 户口邮政编码
    
    private Integer liveConditions;// 居住状况ID(1:按揭商品房,2:无按揭商品房,3:父母/配偶房产住房,4:单位住房,5:自建房,6:租用)

	private String liveConditionsVal;// 居住状况值
	
	private String rentMonthlyPay;// 如果是租用租金

    private Integer validMailAddr;// 有效邮寄地址id（1:同户口地址，2:同居住地址，3:同单位地址，4:同房产地址，5:其他）

    private String validMailAddrVal;// 有效邮寄地址val

    private String validMailAddrTxt;// 地址具体内容

    private String fkId; //用户id
    
    private Integer carHava; //

    private String carHavaVal;

    private Integer propertyType;  //

    private String propertyTypeVal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getNameBefore() {
        return nameBefore;
    }

    public void setNameBefore(String nameBefore) {
        this.nameBefore = nameBefore == null ? null : nameBefore.trim();
    }

    public String getGraduateInstitutions() {
        return graduateInstitutions;
    }

    public void setGraduateInstitutions(String graduateInstitutions) {
        this.graduateInstitutions = graduateInstitutions == null ? null : graduateInstitutions.trim();
    }

    public String getRaisePerson() {
        return raisePerson;
    }

    public void setRaisePerson(String raisePerson) {
        this.raisePerson = raisePerson == null ? null : raisePerson.trim();
    }

    public String getChildNumber() {
        return childNumber;
    }

    public void setChildNumber(String childNumber) {
        this.childNumber = childNumber == null ? null : childNumber.trim();
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMaritalStatusVal() {
        return maritalStatusVal;
    }

    public void setMaritalStatusVal(String maritalStatusVal) {
        this.maritalStatusVal = maritalStatusVal == null ? null : maritalStatusVal.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getStartResidenceDate() {
        return startResidenceDate;
    }

    public void setStartResidenceDate(Date startResidenceDate) {
        this.startResidenceDate = startResidenceDate;
    }

    public Date getComeHereDate() {
        return comeHereDate;
    }

    public void setComeHereDate(Date comeHereDate) {
        this.comeHereDate = comeHereDate;
    }

    public Integer getEduBackground() {
        return eduBackground;
    }

    public void setEduBackground(Integer eduBackground) {
        this.eduBackground = eduBackground;
    }

    public String getEduBackgroundVal() {
        return eduBackgroundVal;
    }

    public void setEduBackgroundVal(String eduBackgroundVal) {
        this.eduBackgroundVal = eduBackgroundVal == null ? null : eduBackgroundVal.trim();
    }

    public String getQqWechat() {
        return qqWechat;
    }

    public void setQqWechat(String qqWechat) {
        this.qqWechat = qqWechat == null ? null : qqWechat.trim();
    }

    public String getRresidenceAddress() {
        return rresidenceAddress;
    }

    public void setRresidenceAddress(String rresidenceAddress) {
        this.rresidenceAddress = rresidenceAddress == null ? null : rresidenceAddress.trim();
    }

    public String getRresidenceProvCode() {
        return rresidenceProvCode;
    }

    public void setRresidenceProvCode(String rresidenceProvCode) {
        this.rresidenceProvCode = rresidenceProvCode == null ? null : rresidenceProvCode.trim();
    }

    public String getRresidenceProvName() {
        return rresidenceProvName;
    }

    public void setRresidenceProvName(String rresidenceProvName) {
        this.rresidenceProvName = rresidenceProvName == null ? null : rresidenceProvName.trim();
    }

    public String getRresidenceCityCode() {
        return rresidenceCityCode;
    }

    public void setRresidenceCityCode(String rresidenceCityCode) {
        this.rresidenceCityCode = rresidenceCityCode == null ? null : rresidenceCityCode.trim();
    }

    public String getRresidenceCityName() {
        return rresidenceCityName;
    }

    public void setRresidenceCityName(String rresidenceCityName) {
        this.rresidenceCityName = rresidenceCityName == null ? null : rresidenceCityName.trim();
    }

    public String getRresidenceAreaCode() {
        return rresidenceAreaCode;
    }

    public void setRresidenceAreaCode(String rresidenceAreaCode) {
        this.rresidenceAreaCode = rresidenceAreaCode == null ? null : rresidenceAreaCode.trim();
    }

    public String getRresidenceAreaName() {
        return rresidenceAreaName;
    }

    public void setRresidenceAreaName(String rresidenceAreaName) {
        this.rresidenceAreaName = rresidenceAreaName == null ? null : rresidenceAreaName.trim();
    }

    public String getRresidenceAddressDetail() {
        return rresidenceAddressDetail;
    }

    public void setRresidenceAddressDetail(String rresidenceAddressDetail) {
        this.rresidenceAddressDetail = rresidenceAddressDetail == null ? null : rresidenceAddressDetail.trim();
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress == null ? null : houseAddress.trim();
    }

    public String getRresidenceZipCode() {
        return rresidenceZipCode;
    }

    public void setRresidenceZipCode(String rresidenceZipCode) {
        this.rresidenceZipCode = rresidenceZipCode == null ? null : rresidenceZipCode.trim();
    }

    public String getHouseProvCode() {
        return houseProvCode;
    }

    public void setHouseProvCode(String houseProvCode) {
        this.houseProvCode = houseProvCode == null ? null : houseProvCode.trim();
    }

    public String getHouseProvName() {
        return houseProvName;
    }

    public void setHouseProvName(String houseProvName) {
        this.houseProvName = houseProvName == null ? null : houseProvName.trim();
    }

    public String getHouseCityName() {
        return houseCityName;
    }

    public void setHouseCityName(String houseCityName) {
        this.houseCityName = houseCityName == null ? null : houseCityName.trim();
    }

    public String getHouseCityCode() {
        return houseCityCode;
    }

    public void setHouseCityCode(String houseCityCode) {
        this.houseCityCode = houseCityCode == null ? null : houseCityCode.trim();
    }

    public String getHouseAreaName() {
        return houseAreaName;
    }

    public void setHouseAreaName(String houseAreaName) {
        this.houseAreaName = houseAreaName == null ? null : houseAreaName.trim();
    }

    public String getHouseAreaCode() {
        return houseAreaCode;
    }

    public void setHouseAreaCode(String houseAreaCode) {
        this.houseAreaCode = houseAreaCode == null ? null : houseAreaCode.trim();
    }

    public String getHouseAddressDetail() {
        return houseAddressDetail;
    }

    public void setHouseAddressDetail(String houseAddressDetail) {
        this.houseAddressDetail = houseAddressDetail == null ? null : houseAddressDetail.trim();
    }

    public String getHouseZipCode() {
        return houseZipCode;
    }

    public void setHouseZipCode(String houseZipCode) {
        this.houseZipCode = houseZipCode == null ? null : houseZipCode.trim();
    }

    public Integer getValidMailAddr() {
        return validMailAddr;
    }

    public void setValidMailAddr(Integer validMailAddr) {
        this.validMailAddr = validMailAddr;
    }

    public String getValidMailAddrVal() {
        return validMailAddrVal;
    }

    public void setValidMailAddrVal(String validMailAddrVal) {
        this.validMailAddrVal = validMailAddrVal == null ? null : validMailAddrVal.trim();
    }

    public String getValidMailAddrTxt() {
        return validMailAddrTxt;
    }

    public void setValidMailAddrTxt(String validMailAddrTxt) {
        this.validMailAddrTxt = validMailAddrTxt == null ? null : validMailAddrTxt.trim();
    }

    public String getFkId() {
        return fkId;
    }

    public void setFkId(String fkId) {
        this.fkId = fkId == null ? null : fkId.trim();
    }

	public Integer getLiveConditions() {
		return liveConditions;
	}

	public void setLiveConditions(Integer liveConditions) {
		this.liveConditions = liveConditions;
	}

	public String getLiveConditionsVal() {
		return liveConditionsVal;
	}

	public void setLiveConditionsVal(String liveConditionsVal) {
		this.liveConditionsVal = liveConditionsVal;
	}

	public String getRentMonthlyPay() {
		return rentMonthlyPay;
	}

	public void setRentMonthlyPay(String rentMonthlyPay) {
		this.rentMonthlyPay = rentMonthlyPay;
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
		this.carHavaVal = carHavaVal;
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
		this.propertyTypeVal = propertyTypeVal;
	}
    
}