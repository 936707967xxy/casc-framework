package com.hoomsun.csas.apply.query.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 作者：ygzhao <br>
 * 创建时间：2017年11月23日 <br>
 * 描述：申请表
 */
public class UserApply {

	private String applyId;// 主键

	private String signconfirmVal;// 0 未人脸识别/未人脸识别-推送 ,1 人脸识别通过

	private Integer signconfirm;// 签约标识 （0 1）

	private String storeName;// 门店名称

	private String storeId;// 门店id

	private String particlesBorrow;// 微粒贷

	private String creditSesame;// 芝麻信用

	private Timestamp applyDate;// 申请时间

	private String uuid;// 提交设备id

	private Integer auditType;// 审批方式 （0 初审 1 终审）

	private Integer sources;// 来源：1 app 2 门店 3 主动拜访 4 客户推荐 5 派单 6 推广活动 7 其他

	private String precessStatusVal;// 流程状态值

	private String precessStatus;// 流程状态ID

	private String precessId;// 流程实例id

	private BigDecimal suggestRate;// 建议利率

	private BigDecimal suggestAmount;// 建议额度

	private String productPayVal;// 申请产品还款方式值

	private Integer productPay;// 申请产品还款方式ID

	private BigDecimal productFeeRate;// 申请产品费率

	private BigDecimal productRate;// 申请产品利率

	private Integer applyPeriod;// 申请期限

	private BigDecimal applyAmount;// 申请金额

	private String agreedProduct;// 同意产品名称

	private BigDecimal agreedAmount;// 同意额度

	private String productName;// 产品名称

	private String productId;// 申请产品

	private String idAddr;// 身份证地址

	private String comTypeVal;// 单位性质值

	private Integer comType;// 单位性质

	private BigDecimal incom;// 月收入

	private String comTel;// 公司电话

	private String detailedAddr;// 单位详细地址

	private String custComAddr;// 单位地址

	private String custCom;// 工作单位

	private String custMobile;// 联系电话

	private Integer custAge;// 年龄

	private String custSex;// 性别

	private String idTypeVal;// 证件类型值

	private Integer idType;// 证件类型id

	private String idNumber;// 证件号

	private String custName;// 客户姓名

	private String custId;// 引用主表id

	private String loanId;// 客户唯一编号

	private String invescode;// 邀请码(员工编号)

	/**
	 * 1：日常生活消费，2：装修，3：医疗，4：购车，5：教育支出 ） 6：资金周转，7：支付员工工资
	 * ，8：扩大生产/经营，9：购买货物/原材料/设备，10：其他
	 */
	private Integer loanUse;// 借款用途ID

	private String sourcesText;// 客户来源如果为其他的值

	private String teamManagerId;// 销售ID

	private String teamManagerName;// 销售姓名

	private String teamManagerDeptId;// 销售所在部门ID

	private String teamManagerDeptName;// 销售所在部门名称

	private String nameBefore;// 曾用名

	private String graduateInstitutions;// 毕业院校

	private String raisePerson;// 供养人数

	private String childNumber;// 子女数目

	private String houseTel;// 住宅电话

	private Integer maritalStatus;// 婚姻状况id（1：未婚，2：已婚，，4：离异，3：丧偶，5：其他）

	private String maritalStatusVal;// 婚姻值

	private String maritalStatusText;// 婚姻状况其他值

	private String email;// 电子邮箱

	private Timestamp startResidenceDate;// 来借款申请地时间

	private Integer eduBackground;// 学历ID 1:硕士及以上,2:本科,3:大专,4:高中及以下

	private String eduBackgroundVal;// 学历值

	private String qqWechat;// QQ/微信

	private Timestamp comeHereDate;// 现居住地居住时间

	private String rresidenceAddress;// 户口所在地

	private String rresidenceProvCode;// 户口所在省份id

	private String rresidenceProvName;// 户口所在省份名称

	private String rresidenceCityCode;// 户口所在市id

	private String rresidenceCityName;// 户口所在市名称

	private String rresidenceAreaCode;// 户口所在区id

	private String rresidenceAreaName;// 户口所在区名称

	private String rresidenceAddressDetail;// 户口所在详细地址

	private String rresidenceZipCode;// 户口邮政编码

	private String houseAddress;// 现居住地

	private String houseProvCode;// 现居住地省份id

	private String houseProvName;// 现居住地省份名称

	private String houseCityCode;// 现居住地市id

	private String houseCityName;// 现居住地市名称

	private String houseAreaCode;// 现居住地区id

	private String houseAreaName;// 现居住地区名称

	private String houseAddressDetail;// 现居住地详细地址

	private String houseZipCode;// 现居住地邮政编码

	private Integer liveConditions;// 居住状况ID(1:按揭商品房,2:无按揭商品房,3:父母/配偶房产住房,4:单位住房,5:自建房,6:租用)

	private String liveConditionsVal;// 居住状况值

	private String rentMonthlyPay;// 如果是租用租金

	private Integer validMailAddr;// 有效邮寄地址id（1:同户口地址，2:同居住地址，3:同单位地址，4:同房产地址，5:其他）

	private String validMailAddrVal;// 有效邮寄地址val

	private String validMailAddrTxt;// 地址具体内容

	private String validPostCode;// 有效邮寄地址邮编

	private String sourcesValue;// 客户来源值

	private String createEmployee;// 录入人员ID

	private String createEmployeeVal;// 录入人员姓名

	private String loanUseVal;// 借款用途值

	private String loanUseText;// 借款用途为其他值

	private Integer delStatue;// 删除状态，1：删除，0：未删除；

	private UserCis userCis;// 上海资信
	private UserTaobao userTaobao;// 淘宝
	private UserChsi userChsi;// 学信
	private UserPbccre userPbccre;// 征信
	private UserHouseFund userHouseFund;// 公积金
	private UserSocialsecurity userSocialsecurity;// 社保
	private NameAuthentication nameAuthentication;// 申请人主表
	private List<UserContacts> contacts;
	private AssetInfo assetInfo;// 资产信息
	private OccupationalInfo userOccupationalInfo;// 职业信息

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId == null ? null : applyId.trim();
	}

	public String getSignconfirmVal() {
		return signconfirmVal;
	}

	public void setSignconfirmVal(String signconfirmVal) {
		this.signconfirmVal = signconfirmVal == null ? null : signconfirmVal.trim();
	}

	public Integer getSignconfirm() {
		return signconfirm;
	}

	public void setSignconfirm(Integer signconfirm) {
		this.signconfirm = signconfirm;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName == null ? null : storeName.trim();
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId == null ? null : storeId.trim();
	}

	public String getParticlesBorrow() {
		return particlesBorrow;
	}

	public void setParticlesBorrow(String particlesBorrow) {
		this.particlesBorrow = particlesBorrow == null ? null : particlesBorrow.trim();
	}

	public String getCreditSesame() {
		return creditSesame;
	}

	public void setCreditSesame(String creditSesame) {
		this.creditSesame = creditSesame == null ? null : creditSesame.trim();
	}

	public Timestamp getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Timestamp applyDate) {
		this.applyDate = applyDate;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid == null ? null : uuid.trim();
	}

	public Integer getAuditType() {
		return auditType;
	}

	public void setAuditType(Integer auditType) {
		this.auditType = auditType;
	}

	public Integer getSources() {
		return sources;
	}

	public void setSources(Integer sources) {
		this.sources = sources;
	}

	public String getPrecessStatusVal() {
		return precessStatusVal;
	}

	public void setPrecessStatusVal(String precessStatusVal) {
		this.precessStatusVal = precessStatusVal == null ? null : precessStatusVal.trim();
	}

	public String getPrecessStatus() {
		return precessStatus;
	}

	public void setPrecessStatus(String precessStatus) {
		this.precessStatus = precessStatus == null ? null : precessStatus.trim();
	}

	public String getPrecessId() {
		return precessId;
	}

	public void setPrecessId(String precessId) {
		this.precessId = precessId == null ? null : precessId.trim();
	}

	public BigDecimal getSuggestRate() {
		return suggestRate;
	}

	public void setSuggestRate(BigDecimal suggestRate) {
		this.suggestRate = suggestRate;
	}

	public BigDecimal getSuggestAmount() {
		return suggestAmount;
	}

	public void setSuggestAmount(BigDecimal suggestAmount) {
		this.suggestAmount = suggestAmount;
	}

	public String getProductPayVal() {
		return productPayVal;
	}

	public void setProductPayVal(String productPayVal) {
		this.productPayVal = productPayVal == null ? null : productPayVal.trim();
	}

	public Integer getProductPay() {
		return productPay;
	}

	public void setProductPay(Integer productPay) {
		this.productPay = productPay;
	}

	public BigDecimal getProductFeeRate() {
		return productFeeRate;
	}

	public void setProductFeeRate(BigDecimal productFeeRate) {
		this.productFeeRate = productFeeRate;
	}

	public BigDecimal getProductRate() {
		return productRate;
	}

	public void setProductRate(BigDecimal productRate) {
		this.productRate = productRate;
	}

	public Integer getApplyPeriod() {
		return applyPeriod;
	}

	public void setApplyPeriod(Integer applyPeriod) {
		this.applyPeriod = applyPeriod;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public String getAgreedProduct() {
		return agreedProduct;
	}

	public void setAgreedProduct(String agreedProduct) {
		this.agreedProduct = agreedProduct == null ? null : agreedProduct.trim();
	}

	public BigDecimal getAgreedAmount() {
		return agreedAmount;
	}

	public void setAgreedAmount(BigDecimal agreedAmount) {
		this.agreedAmount = agreedAmount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName == null ? null : productName.trim();
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId == null ? null : productId.trim();
	}

	public String getIdAddr() {
		return idAddr;
	}

	public void setIdAddr(String idAddr) {
		this.idAddr = idAddr == null ? null : idAddr.trim();
	}

	public String getComTypeVal() {
		return comTypeVal;
	}

	public void setComTypeVal(String comTypeVal) {
		this.comTypeVal = comTypeVal == null ? null : comTypeVal.trim();
	}

	public Integer getComType() {
		return comType;
	}

	public void setComType(Integer comType) {
		this.comType = comType;
	}

	public BigDecimal getIncom() {
		return incom;
	}

	public void setIncom(BigDecimal incom) {
		this.incom = incom;
	}

	public String getComTel() {
		return comTel;
	}

	public void setComTel(String comTel) {
		this.comTel = comTel == null ? null : comTel.trim();
	}

	public String getDetailedAddr() {
		return detailedAddr;
	}

	public void setDetailedAddr(String detailedAddr) {
		this.detailedAddr = detailedAddr == null ? null : detailedAddr.trim();
	}

	public String getCustComAddr() {
		return custComAddr;
	}

	public void setCustComAddr(String custComAddr) {
		this.custComAddr = custComAddr == null ? null : custComAddr.trim();
	}

	public String getCustCom() {
		return custCom;
	}

	public void setCustCom(String custCom) {
		this.custCom = custCom == null ? null : custCom.trim();
	}

	public String getCustMobile() {
		return custMobile;
	}

	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile == null ? null : custMobile.trim();
	}

	public Integer getCustAge() {
		return custAge;
	}

	public void setCustAge(Integer custAge) {
		this.custAge = custAge;
	}

	public String getCustSex() {
		return custSex;
	}

	public void setCustSex(String custSex) {
		this.custSex = custSex == null ? null : custSex.trim();
	}

	public String getIdTypeVal() {
		return idTypeVal;
	}

	public void setIdTypeVal(String idTypeVal) {
		this.idTypeVal = idTypeVal == null ? null : idTypeVal.trim();
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber == null ? null : idNumber.trim();
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName == null ? null : custName.trim();
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId == null ? null : loanId.trim();
	}

	public String getInvescode() {
		return invescode;
	}

	public void setInvescode(String invescode) {
		this.invescode = invescode == null ? null : invescode.trim();
	}

	public Integer getLoanUse() {
		return loanUse;
	}

	public void setLoanUse(Integer loanUse) {
		this.loanUse = loanUse;
	}

	public String getSourcesText() {
		return sourcesText;
	}

	public void setSourcesText(String sourcesText) {
		this.sourcesText = sourcesText == null ? null : sourcesText.trim();
	}

	public String getTeamManagerId() {
		return teamManagerId;
	}

	public void setTeamManagerId(String teamManagerId) {
		this.teamManagerId = teamManagerId == null ? null : teamManagerId.trim();
	}

	public String getTeamManagerName() {
		return teamManagerName;
	}

	public void setTeamManagerName(String teamManagerName) {
		this.teamManagerName = teamManagerName == null ? null : teamManagerName.trim();
	}

	public String getTeamManagerDeptId() {
		return teamManagerDeptId;
	}

	public void setTeamManagerDeptId(String teamManagerDeptId) {
		this.teamManagerDeptId = teamManagerDeptId == null ? null : teamManagerDeptId.trim();
	}

	public String getTeamManagerDeptName() {
		return teamManagerDeptName;
	}

	public void setTeamManagerDeptName(String teamManagerDeptName) {
		this.teamManagerDeptName = teamManagerDeptName == null ? null : teamManagerDeptName.trim();
	}

	public UserCis getUserCis() {
		return userCis;
	}

	public void setUserCis(UserCis userCis) {
		this.userCis = userCis;
	}

	public UserTaobao getUserTaobao() {
		return userTaobao;
	}

	public void setUserTaobao(UserTaobao userTaobao) {
		this.userTaobao = userTaobao;
	}

	public UserChsi getUserChsi() {
		return userChsi;
	}

	public void setUserChsi(UserChsi userChsi) {
		this.userChsi = userChsi;
	}

	public UserPbccre getUserPbccre() {
		return userPbccre;
	}

	public void setUserPbccre(UserPbccre userPbccre) {
		this.userPbccre = userPbccre;
	}

	public UserHouseFund getUserHouseFund() {
		return userHouseFund;
	}

	public void setUserHouseFund(UserHouseFund userHouseFund) {
		this.userHouseFund = userHouseFund;
	}

	public UserSocialsecurity getUserSocialsecurity() {
		return userSocialsecurity;
	}

	public void setUserSocialsecurity(UserSocialsecurity userSocialsecurity) {
		this.userSocialsecurity = userSocialsecurity;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getNameBefore() {
		return nameBefore;
	}

	public void setNameBefore(String nameBefore) {
		this.nameBefore = nameBefore;
	}

	public String getGraduateInstitutions() {
		return graduateInstitutions;
	}

	public void setGraduateInstitutions(String graduateInstitutions) {
		this.graduateInstitutions = graduateInstitutions;
	}

	public String getRaisePerson() {
		return raisePerson;
	}

	public void setRaisePerson(String raisePerson) {
		this.raisePerson = raisePerson;
	}

	public String getChildNumber() {
		return childNumber;
	}

	public void setChildNumber(String childNumber) {
		this.childNumber = childNumber;
	}

	public String getHouseTel() {
		return houseTel;
	}

	public void setHouseTel(String houseTel) {
		this.houseTel = houseTel;
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
		this.maritalStatusVal = maritalStatusVal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getStartResidenceDate() {
		return startResidenceDate;
	}

	public void setStartResidenceDate(Timestamp startResidenceDate) {
		this.startResidenceDate = startResidenceDate;
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
		this.eduBackgroundVal = eduBackgroundVal;
	}

	public String getQqWechat() {
		return qqWechat;
	}

	public void setQqWechat(String qqWechat) {
		this.qqWechat = qqWechat;
	}

	public Timestamp getComeHereDate() {
		return comeHereDate;
	}

	public void setComeHereDate(Timestamp comeHereDate) {
		this.comeHereDate = comeHereDate;
	}

	public String getRresidenceAddress() {
		return rresidenceAddress;
	}

	public void setRresidenceAddress(String rresidenceAddress) {
		this.rresidenceAddress = rresidenceAddress;
	}

	public String getRresidenceProvCode() {
		return rresidenceProvCode;
	}

	public void setRresidenceProvCode(String rresidenceProvCode) {
		this.rresidenceProvCode = rresidenceProvCode;
	}

	public String getRresidenceProvName() {
		return rresidenceProvName;
	}

	public void setRresidenceProvName(String rresidenceProvName) {
		this.rresidenceProvName = rresidenceProvName;
	}

	public String getRresidenceCityCode() {
		return rresidenceCityCode;
	}

	public void setRresidenceCityCode(String rresidenceCityCode) {
		this.rresidenceCityCode = rresidenceCityCode;
	}

	public String getRresidenceCityName() {
		return rresidenceCityName;
	}

	public void setRresidenceCityName(String rresidenceCityName) {
		this.rresidenceCityName = rresidenceCityName;
	}

	public String getRresidenceAreaCode() {
		return rresidenceAreaCode;
	}

	public void setRresidenceAreaCode(String rresidenceAreaCode) {
		this.rresidenceAreaCode = rresidenceAreaCode;
	}

	public String getRresidenceAreaName() {
		return rresidenceAreaName;
	}

	public void setRresidenceAreaName(String rresidenceAreaName) {
		this.rresidenceAreaName = rresidenceAreaName;
	}

	public String getRresidenceAddressDetail() {
		return rresidenceAddressDetail;
	}

	public void setRresidenceAddressDetail(String rresidenceAddressDetail) {
		this.rresidenceAddressDetail = rresidenceAddressDetail;
	}

	public String getRresidenceZipCode() {
		return rresidenceZipCode;
	}

	public void setRresidenceZipCode(String rresidenceZipCode) {
		this.rresidenceZipCode = rresidenceZipCode;
	}

	public String getHouseAddress() {
		return houseAddress;
	}

	public void setHouseAddress(String houseAddress) {
		this.houseAddress = houseAddress;
	}

	public String getHouseProvCode() {
		return houseProvCode;
	}

	public void setHouseProvCode(String houseProvCode) {
		this.houseProvCode = houseProvCode;
	}

	public String getHouseProvName() {
		return houseProvName;
	}

	public void setHouseProvName(String houseProvName) {
		this.houseProvName = houseProvName;
	}

	public String getHouseCityCode() {
		return houseCityCode;
	}

	public void setHouseCityCode(String houseCityCode) {
		this.houseCityCode = houseCityCode;
	}

	public String getHouseCityName() {
		return houseCityName;
	}

	public void setHouseCityName(String houseCityName) {
		this.houseCityName = houseCityName;
	}

	public String getHouseAreaCode() {
		return houseAreaCode;
	}

	public void setHouseAreaCode(String houseAreaCode) {
		this.houseAreaCode = houseAreaCode;
	}

	public String getHouseAreaName() {
		return houseAreaName;
	}

	public void setHouseAreaName(String houseAreaName) {
		this.houseAreaName = houseAreaName;
	}

	public String getHouseAddressDetail() {
		return houseAddressDetail;
	}

	public void setHouseAddressDetail(String houseAddressDetail) {
		this.houseAddressDetail = houseAddressDetail;
	}

	public String getHouseZipCode() {
		return houseZipCode;
	}

	public void setHouseZipCode(String houseZipCode) {
		this.houseZipCode = houseZipCode;
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
		this.validMailAddrVal = validMailAddrVal;
	}

	public String getValidMailAddrTxt() {
		return validMailAddrTxt;
	}

	public void setValidMailAddrTxt(String validMailAddrTxt) {
		this.validMailAddrTxt = validMailAddrTxt;
	}

	public String getValidPostCode() {
		return validPostCode;
	}

	public void setValidPostCode(String validPostCode) {
		this.validPostCode = validPostCode;
	}

	public String getSourcesValue() {
		return sourcesValue;
	}

	public void setSourcesValue(String sourcesValue) {
		this.sourcesValue = sourcesValue;
	}

	public String getCreateEmployee() {
		return createEmployee;
	}

	public void setCreateEmployee(String createEmployee) {
		this.createEmployee = createEmployee;
	}

	public String getCreateEmployeeVal() {
		return createEmployeeVal;
	}

	public void setCreateEmployeeVal(String createEmployeeVal) {
		this.createEmployeeVal = createEmployeeVal;
	}

	public String getLoanUseVal() {
		return loanUseVal;
	}

	public void setLoanUseVal(String loanUseVal) {
		this.loanUseVal = loanUseVal;
	}

	public String getLoanUseText() {
		return loanUseText;
	}

	public void setLoanUseText(String loanUseText) {
		this.loanUseText = loanUseText;
	}

	public String getMaritalStatusText() {
		return maritalStatusText;
	}

	public void setMaritalStatusText(String maritalStatusText) {
		this.maritalStatusText = maritalStatusText;
	}

	public NameAuthentication getNameAuthentication() {
		return nameAuthentication;
	}

	public void setNameAuthentication(NameAuthentication nameAuthentication) {
		this.nameAuthentication = nameAuthentication;
	}

	public AssetInfo getAssetInfo() {
		return assetInfo;
	}

	public void setAssetInfo(AssetInfo assetInfo) {
		this.assetInfo = assetInfo;
	}

	public OccupationalInfo getUserOccupationalInfo() {
		return userOccupationalInfo;
	}

	public void setUserOccupationalInfo(OccupationalInfo userOccupationalInfo) {
		this.userOccupationalInfo = userOccupationalInfo;
	}

	public Integer getDelStatu() {
		return delStatue;
	}

	public void setDelStatu(Integer delStatu) {
		this.delStatue = delStatu;
	}

	public Integer getDelStatue() {
		return delStatue;
	}

	public void setDelStatue(Integer delStatue) {
		this.delStatue = delStatue;
	}

	public List<UserContacts> getContacts() {
		return contacts;
	}

	public void setContacts(List<UserContacts> contacts) {
		this.contacts = contacts;
	}

}