package com.hoomsun.core.model;

import java.sql.Timestamp;

/**
 * 
 * 作者：liming<br>
 * 创建时间：2017年12月5日 <br>
 * 描述： 黑名单信息表
 *
 */
public class BlackInfo {
    private String id;

    private String custname;//姓名	

    private String phone;//手机

    private String sex;//性别

    private String sexVal;

    private String idnumber;//身份证

    private String operator;//录入人员

    private String isblackflag;//是否黑名单{0否,1是}

    private String custstate;//客户状态{0正常,1黑名单转入申请,2黑名单转出申请}

    private String custstateVal;//客户状态

    private String custtype;

    private String custtypeVal;//客户类型

    private Timestamp rollinDate;//转入时间

    private String rollinCause;//转入原因

    private Timestamp rolloutDate;//转出时间

    private String rolloutCause;//转出原因

    private String blackRemark;//备注
    
    private String companyName;//公司名称

    private String companyShortName;//公司简称

    private String orgcode;//公司编码
    
    private String rollinPerson;//转入人员id

    private String rollinPersonVal;//转入人员

    private String rolloutPerosn;//转出人员id

    private String rolloutPerosnVal;//转出人员
    
    private String houseAddress;//住宅地址

    private String houseProvCode;//住宅地址所在省份id

    private String houseProvName;//住宅地址所在省份名称

    private String houseCityCode;//住宅地址所在市id

    private String houseCityName;//住宅地址所在市名称

    private String houseAreaCode;//住宅地址所在区id

    private String houseAreaName;//住宅地址所在区名称

    private String houseAddressDetail;//住宅详细地址

    private String houseAddressPhone;//住宅电话

    private String companyAddress;//公司地址

    private String companyProvCode;//公司地址所在省份id

    private String companyProvName;//公司地址所在省份名称

    private String companyCityCode;//公司地址所在市id

    private String companyCityName;//公司地址所在市名称

    private String companyAreaCode;//公司地址所在区id

    private String companyAreaName;//公司地址所在区名称

    private String companyAddressDetail;//公司地址所在详细地址

    private String companyPhone;//公司电话  
    
    private String legalPersonId;//公司法人id

    private String legalPersonName;//公司法人名字
    
    private String dealopinions;//审批意见{0不同意,1同意转入,2同意转出}

    private String dealopinionsval;//审批意见val
    
    private String dealPersonId;//审批人员id

    private String dealPersonName;//审批人员名字

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname == null ? null : custname.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getSexVal() {
        return sexVal;
    }

    public void setSexVal(String sexVal) {
        this.sexVal = sexVal == null ? null : sexVal.trim();
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber == null ? null : idnumber.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getIsblackflag() {
        return isblackflag;
    }

    public void setIsblackflag(String isblackflag) {
        this.isblackflag = isblackflag == null ? null : isblackflag.trim();
    }

    public String getCuststate() {
        return custstate;
    }

    public void setCuststate(String custstate) {
        this.custstate = custstate == null ? null : custstate.trim();
    }

    public String getCuststateVal() {
        return custstateVal;
    }

    public void setCuststateVal(String custstateVal) {
        this.custstateVal = custstateVal == null ? null : custstateVal.trim();
    }

    public String getCusttype() {
        return custtype;
    }

    public void setCusttype(String custtype) {
        this.custtype = custtype == null ? null : custtype.trim();
    }

    public String getCusttypeVal() {
        return custtypeVal;
    }

    public void setCusttypeVal(String custtypeVal) {
        this.custtypeVal = custtypeVal == null ? null : custtypeVal.trim();
    }

    public Timestamp getRollinDate() {
        return rollinDate;
    }

    public void setRollinDate(Timestamp rollinDate) {
        this.rollinDate = rollinDate;
    }

    public String getRollinCause() {
        return rollinCause;
    }

    public void setRollinCause(String rollinCause) {
        this.rollinCause = rollinCause == null ? null : rollinCause.trim();
    }

    public Timestamp getRolloutDate() {
        return rolloutDate;
    }

    public void setRolloutDate(Timestamp rolloutDate) {
        this.rolloutDate = rolloutDate;
    }

    public String getRolloutCause() {
        return rolloutCause;
    }

    public void setRolloutCause(String rolloutCause) {
        this.rolloutCause = rolloutCause == null ? null : rolloutCause.trim();
    }

    public String getBlackRemark() {
        return blackRemark;
    }

    public void setBlackRemark(String blackRemark) {
        this.blackRemark = blackRemark == null ? null : blackRemark.trim();
    }
    
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName == null ? null : companyShortName.trim();
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode == null ? null : orgcode.trim();
    }
    
    public String getRollinPerson() {
        return rollinPerson;
    }

    public void setRollinPerson(String rollinPerson) {
        this.rollinPerson = rollinPerson == null ? null : rollinPerson.trim();
    }

    public String getRollinPersonVal() {
        return rollinPersonVal;
    }

    public void setRollinPersonVal(String rollinPersonVal) {
        this.rollinPersonVal = rollinPersonVal == null ? null : rollinPersonVal.trim();
    }

    public String getRolloutPerosn() {
        return rolloutPerosn;
    }

    public void setRolloutPerosn(String rolloutPerosn) {
        this.rolloutPerosn = rolloutPerosn == null ? null : rolloutPerosn.trim();
    }

    public String getRolloutPerosnVal() {
        return rolloutPerosnVal;
    }

    public void setRolloutPerosnVal(String rolloutPerosnVal) {
        this.rolloutPerosnVal = rolloutPerosnVal == null ? null : rolloutPerosnVal.trim();
    }
    

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress == null ? null : houseAddress.trim();
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

    public String getHouseCityCode() {
        return houseCityCode;
    }

    public void setHouseCityCode(String houseCityCode) {
        this.houseCityCode = houseCityCode == null ? null : houseCityCode.trim();
    }

    public String getHouseCityName() {
        return houseCityName;
    }

    public void setHouseCityName(String houseCityName) {
        this.houseCityName = houseCityName == null ? null : houseCityName.trim();
    }

    public String getHouseAreaCode() {
        return houseAreaCode;
    }

    public void setHouseAreaCode(String houseAreaCode) {
        this.houseAreaCode = houseAreaCode == null ? null : houseAreaCode.trim();
    }

    public String getHouseAreaName() {
        return houseAreaName;
    }

    public void setHouseAreaName(String houseAreaName) {
        this.houseAreaName = houseAreaName == null ? null : houseAreaName.trim();
    }

    public String getHouseAddressDetail() {
        return houseAddressDetail;
    }

    public void setHouseAddressDetail(String houseAddressDetail) {
        this.houseAddressDetail = houseAddressDetail == null ? null : houseAddressDetail.trim();
    }

    public String getHouseAddressPhone() {
        return houseAddressPhone;
    }

    public void setHouseAddressPhone(String houseAddressPhone) {
        this.houseAddressPhone = houseAddressPhone == null ? null : houseAddressPhone.trim();
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress == null ? null : companyAddress.trim();
    }

    public String getCompanyProvCode() {
        return companyProvCode;
    }

    public void setCompanyProvCode(String companyProvCode) {
        this.companyProvCode = companyProvCode == null ? null : companyProvCode.trim();
    }

    public String getCompanyProvName() {
        return companyProvName;
    }

    public void setCompanyProvName(String companyProvName) {
        this.companyProvName = companyProvName == null ? null : companyProvName.trim();
    }

    public String getCompanyCityCode() {
        return companyCityCode;
    }

    public void setCompanyCityCode(String companyCityCode) {
        this.companyCityCode = companyCityCode == null ? null : companyCityCode.trim();
    }

    public String getCompanyCityName() {
        return companyCityName;
    }

    public void setCompanyCityName(String companyCityName) {
        this.companyCityName = companyCityName == null ? null : companyCityName.trim();
    }

    public String getCompanyAreaCode() {
        return companyAreaCode;
    }

    public void setCompanyAreaCode(String companyAreaCode) {
        this.companyAreaCode = companyAreaCode == null ? null : companyAreaCode.trim();
    }

    public String getCompanyAreaName() {
        return companyAreaName;
    }

    public void setCompanyAreaName(String companyAreaName) {
        this.companyAreaName = companyAreaName == null ? null : companyAreaName.trim();
    }

    public String getCompanyAddressDetail() {
        return companyAddressDetail;
    }

    public void setCompanyAddressDetail(String companyAddressDetail) {
        this.companyAddressDetail = companyAddressDetail == null ? null : companyAddressDetail.trim();
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone == null ? null : companyPhone.trim();
    }

    public String getLegalPersonId() {
        return legalPersonId;
    }

    public void setLegalPersonId(String legalPersonId) {
        this.legalPersonId = legalPersonId == null ? null : legalPersonId.trim();
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName == null ? null : legalPersonName.trim();
    }
    
    public String getDealopinions() {
        return dealopinions;
    }

    public void setDealopinions(String dealopinions) {
        this.dealopinions = dealopinions == null ? null : dealopinions.trim();
    }

    public String getDealopinionsval() {
        return dealopinionsval;
    }

    public void setDealopinionsval(String dealopinionsval) {
        this.dealopinionsval = dealopinionsval == null ? null : dealopinionsval.trim();
    }
    
    public String getDealPersonId() {
        return dealPersonId;
    }

    public void setDealPersonId(String dealPersonId) {
        this.dealPersonId = dealPersonId == null ? null : dealPersonId.trim();
    }

    public String getDealPersonName() {
        return dealPersonName;
    }

    public void setDealPersonName(String dealPersonName) {
        this.dealPersonName = dealPersonName == null ? null : dealPersonName.trim();
    }
}