/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月4日 <br>
 * 描述：申请信息
 * 
 * @Id -
 *     文档的唯一标识，在mongodb中为ObjectId，它是唯一的，通过时间戳+机器标识+进程ID+自增计数器（确保同一秒内产生的Id不会冲突）构成。
 * 
 * @Document -
 *           把一个java类声明为mongodb的文档，可以通过collection参数指定这个类对应的文档。@Document(collection="mongodb")
 *           mongodb对应表
 * 
 * @DBRef -
 *        声明类似于关系数据库的关联关系。ps：暂不支持级联的保存功能，当你在本实例中修改了DERef对象里面的值时，单独保存本实例并不能保存DERef引用的对象，它要另外保存，如下面例子的Person和Account。
 * 
 * @Indexed - 声明该字段需要索引，建索引可以大大的提高查询效率。
 * 
 * @CompoundIndex - 复合索引的声明，建复合索引可以有效地提高多字段的查询效率。
 * 
 * @GeoSpatialIndexed - 声明该字段为地理信息的索引。
 * 
 * @Transient - 映射忽略的字段，该字段不会保存到mongodb。
 * 
 * @PersistenceConstructor -
 *                         声明构造函数，作用是把从数据库取出的数据实例化为对象。该构造函数传入的值为从DBObject中取出的数据
 */
@Document(collection = "RK_USER_APPLY")
public class UserApply {
	@Id
	private String applyId;// 申请编号
	private String applyCode;// 申请编号
	private String custId;
	private String custName;// 客户姓名
	@Indexed
	private String idNumber;// 证件号
	@Indexed
	private String mobile;// 手机号
	private String attribution;// 归属地
	private String houseTel;// 住宅电话
	private String houseAddr;// 住宅地址
	private String estate;//房产地址
	private String comName;// 单位名称
	private String comTel;// 单位电话
	private String comAddr;// 单位地址
	private Integer social;// 是否有社保
	private String socialVal;// 是否有社保
	
	private String storeId;// 门店ID
	private String storeName;// 门店名称
	private String salesId;// 销售
	private String salesName;//
	private Date applyDate;// 申请时间
	private Integer position;// 职位
	private String positionVal;//
	private Integer industry;// 行业
	private String industryVal;//
	private Integer comScale;// 规模
	private String comScaleVal;//
	private Integer marital;//婚姻
	private String maritalVal;
	private String procStatus;//审核状态
	private String procStatusVal;
	private Integer education;//学历
	private String educationVal;
	
	private Integer raisePerson;//供养人数
	private Integer childNumber;//子女人数
	private String applyAddress;//申请地
	
	@Transient
	private List<UserContact> contacts;

	public String getApplyId() {

		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getApplyCode() {
		return applyCode;
	}

	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAttribution() {
		return attribution;
	}

	public void setAttribution(String attribution) {
		this.attribution = attribution;
	}

	public String getHouseTel() {
		return houseTel;
	}

	public void setHouseTel(String houseTel) {
		this.houseTel = houseTel;
	}

	public String getHouseAddr() {
		return houseAddr;
	}

	public void setHouseAddr(String houseAddr) {
		this.houseAddr = houseAddr;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getComTel() {
		return comTel;
	}

	public void setComTel(String comTel) {
		this.comTel = comTel;
	}

	public String getComAddr() {
		return comAddr;
	}

	public void setComAddr(String comAddr) {
		this.comAddr = comAddr;
	}

	public Integer getSocial() {
		return social;
	}

	public void setSocial(Integer social) {
		this.social = social;
	}

	public String getSocialVal() {
		return socialVal;
	}

	public void setSocialVal(String socialVal) {
		this.socialVal = socialVal;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getSalesId() {
		return salesId;
	}

	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getPositionVal() {
		return positionVal;
	}

	public void setPositionVal(String positionVal) {
		this.positionVal = positionVal;
	}

	public Integer getIndustry() {
		return industry;
	}

	public void setIndustry(Integer industry) {
		this.industry = industry;
	}

	public String getIndustryVal() {
		return industryVal;
	}

	public void setIndustryVal(String industryVal) {
		this.industryVal = industryVal;
	}

	public Integer getComScale() {
		return comScale;
	}

	public void setComScale(Integer comScale) {
		this.comScale = comScale;
	}

	public String getComScaleVal() {
		return comScaleVal;
	}

	public void setComScaleVal(String comScaleVal) {
		this.comScaleVal = comScaleVal;
	}

	public Integer getMarital() {
		return marital;
	}

	public void setMarital(Integer marital) {
		this.marital = marital;
	}

	public String getMaritalVal() {
		return maritalVal;
	}

	public void setMaritalVal(String maritalVal) {
		this.maritalVal = maritalVal;
	}

	public List<UserContact> getContacts() {
		return contacts;
	}

	public void setContacts(List<UserContact> contacts) {
		this.contacts = contacts;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getProcStatus() {
		return procStatus;
	}

	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
	}

	public String getProcStatusVal() {
		return procStatusVal;
	}

	public void setProcStatusVal(String procStatusVal) {
		this.procStatusVal = procStatusVal;
	}

	public Integer getEducation() {
		return education;
	}

	public void setEducation(Integer education) {
		this.education = education;
	}

	public String getEducationVal() {
		return educationVal;
	}

	public void setEducationVal(String educationVal) {
		this.educationVal = educationVal;
	}

	public Integer getRaisePerson() {
		return raisePerson;
	}

	public void setRaisePerson(Integer raisePerson) {
		this.raisePerson = raisePerson;
	}

	public Integer getChildNumber() {
		return childNumber;
	}

	public void setChildNumber(Integer childNumber) {
		this.childNumber = childNumber;
	}

	
	public String getEstate() {
		return estate;
	}

	public void setEstate(String estate) {
		this.estate = estate;
	}

	public String getApplyAddress() {
		return applyAddress;
	}

	public void setApplyAddress(String applyAddress) {
		this.applyAddress = applyAddress;
	}

	@Override
	public String toString() {
		return "UserApply [applyId=" + applyId + ", applyCode=" + applyCode + ", custId=" + custId + ", custName=" + custName + ", idNumber=" + idNumber + ", mobile=" + mobile + ", attribution=" + attribution + ", houseTel=" + houseTel
				+ ", houseAddr=" + houseAddr + ", comName=" + comName + ", comTel=" + comTel + ", comAddr=" + comAddr + ", social=" + social + ", socialVal=" + socialVal + ", storeId=" + storeId + ", storeName=" + storeName + ", salesId="
				+ salesId + ", salesName=" + salesName + ", applyDate=" + applyDate + ", position=" + position + ", positionVal=" + positionVal + ", industry=" + industry + ", industryVal=" + industryVal + ", comScale=" + comScale
				+ ", comScaleVal=" + comScaleVal + ", marital=" + marital + ", maritalVal=" + maritalVal + ", contacts=" + contacts + "]";
	}

}
