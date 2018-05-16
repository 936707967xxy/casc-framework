package com.hoomsun.csas.sales.api.model;

import java.sql.Timestamp;
import java.util.List;;

/**
 * @author ygzhao 联系人model
 *
 */
/**
 * @author Administrator
 *
 */
public class UserContacts {
	private String contId;// 主键

	private String applyId;// 申请表id

	private String name;// 姓名

	private String phone;// 手机号

	private Timestamp addDate;// 添加时间

	private Integer callcounts;// 通话记录数

	private String callTime;// 通话时长

	private String mobileAddress;// 归属地

	/*value: 1,label: '配偶'value: 2,label: '亲属'value: 3,label: '同事'value: 4,label: '其他'*/
	private Integer relationship;// 关系ID						

	private String relationshipVal;// 关系val

	private String companyName;// 联系人所在公司

	private String contactAddress;// 联系人所在地址

	private Integer isKnow;// 联系人是否知晓 1是，0否

	private String isKnowVal;// 联系人是否知晓值
	
	private Integer isFillIn;// 0:自动获取  1:客户填写

	private String isFillInVal;
	
	private Integer phoneType;    

	private String phoneTypeVal;  // 银行 1    小贷  2   p2p 3   其他  4         默认 0

	private Integer isLocal;     //是否是本地   0:否  1:是

	private String isLocalVal;

	private List<UserCallDetail> callDetails;

	public String getContId() {
		return contId;
	}

	public void setContId(String contId) {
		this.contId = contId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Timestamp getAddDate() {
		return addDate;
	}

	public void setAddDate(Timestamp addDate) {
		this.addDate = addDate;
	}

	public Integer getCallcounts() {
		return callcounts;
	}

	public void setCallcounts(Integer callcounts) {
		this.callcounts = callcounts;
	}

	public String getCallTime() {
		return callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}

	public String getMobileAddress() {
		return mobileAddress;
	}

	public void setMobileAddress(String mobileAddress) {
		this.mobileAddress = mobileAddress;
	}

	public Integer getRelationship() {
		return relationship;
	}

	public void setRelationship(Integer relationship) {
		this.relationship = relationship;
	}

	public String getRelationshipVal() {
		return relationshipVal;
	}

	public void setRelationshipVal(String relationshipVal) {
		this.relationshipVal = relationshipVal;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public Integer getIsKnow() {
		return isKnow;
	}

	public void setIsKnow(Integer isKnow) {
		this.isKnow = isKnow;
	}

	public String getIsKnowVal() {
		return isKnowVal;
	}

	public void setIsKnowVal(String isKnowVal) {
		this.isKnowVal = isKnowVal;
	}

	public Integer getIsFillIn() {
		return isFillIn;
	}

	public void setIsFillIn(Integer isFillIn) {
		this.isFillIn = isFillIn;
	}

	public String getIsFillInVal() {
		return isFillInVal;
	}

	public void setIsFillInVal(String isFillInVal) {
		this.isFillInVal = isFillInVal;
	}

	public Integer getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(Integer phoneType) {
		this.phoneType = phoneType;
	}

	public String getPhoneTypeVal() {
		return phoneTypeVal;
	}

	public void setPhoneTypeVal(String phoneTypeVal) {
		this.phoneTypeVal = phoneTypeVal;
	}

	public Integer getIsLocal() {
		return isLocal;
	}

	public void setIsLocal(Integer isLocal) {
		this.isLocal = isLocal;
	}

	public String getIsLocalVal() {
		return isLocalVal;
	}

	public void setIsLocalVal(String isLocalVal) {
		this.isLocalVal = isLocalVal;
	}

	public List<UserCallDetail> getCallDetails() {
		return callDetails;
	}

	public void setCallDetails(List<UserCallDetail> callDetails) {
		this.callDetails = callDetails;
	}
	
}