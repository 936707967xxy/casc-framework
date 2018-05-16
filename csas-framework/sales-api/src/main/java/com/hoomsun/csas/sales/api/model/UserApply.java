package com.hoomsun.csas.sales.api.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.hoomsun.core.model.SysContract;

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

	private Integer auditType;// 审批方式 （0 初审 1 终审）--

	private Integer sources;// 来源：1 app 2 门店 3 主动拜访 4 客户推荐 5 派单 6 推广活动 7 其他

	private String procStatusVal;// 流程状态值--

	private String procStatus;// 流程状态ID--

	private String procInstId;// 流程实例id--

	private BigDecimal suggestRate;// 建议利率（实际利率）

	private BigDecimal suggestAmount;// 建议额度

	private String productPayVal;// 申请产品还款方式值

	private Integer productPay;// 申请产品还款方式ID-

	private BigDecimal productFeeRate;// 年利率

	private BigDecimal productRate;// 月利率

	private Integer applyPeriod;// 申请期限

	private BigDecimal applyAmount;// 申请金额

	private String agreedProduct;// 同意产品名称

	private BigDecimal agreedAmount;// 同意额度

	private String productName;// 小产品名称

	private String productId;// 小申请产品
	
	private String productNameAlias;// 小产品别名
	
	private String agreedProductAlias;// 同意产品别名

	private String idAddr;// 身份证地址

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

	private String salesEmpId;// 销售ID

	private String salesEmpName;// 销售姓名

	private String salesEmpDeptId;// 销售所在部门ID

	private String salesEmpDeptName;// 销售所在部门名称

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

	private String productTypeId;// 申请大产品主键

	private String productTypeName;// 申请大产品名称

	private String productTypeNameAlias;// 申请大产品别名
	
	private String lastUpdateEmpId;// 最后一个修改人员工Id

	private String lastUpdateEmpName;// 最后一个修改人员工姓名

	private Timestamp lastUpdateTime;// 最后修改时间

	private String submitTypeText;// 提交文本

	private String submitEmpId;// 提交员工

	private String submitEmpName;// 提交员工姓名

	private Timestamp submitDate;// 提交日期
	
	private Integer agreePeriod;    //同意期数

    private String agreeProductId;  //同意产品id
    
    private String cityDept;//城市部门

    private String bigAreaDept;//大区部门

    private String caustDept;//事业部
    
    private Integer isTemp;//是否为草稿 0否，1是

    private String isTempVal;
    
    private Integer isApp;//是否app单子，1:app,2:门店,3:线下

    private String isAppVal;
    
    private Integer suggestResultId;//建议结果id

    private String suggestResultVal;//建议结果值

    private String suggestResultCause;//建议结果原因
    
    private String contractsTime;//通话详单认证时间
    
    private String applyAddress;//申请地址
    
    private String ipAddress;//Ip地址
    
    private Integer custTypeId;//客户类型ID，0:新客户，1:老客户，2:循环贷

    private String custTypeVal;//客户类型值

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getApplyAddress() {
		return applyAddress;
	}

	public void setApplyAddress(String applyAddress) {
		this.applyAddress = applyAddress;
	}

	public String getContractsTime() {
		return contractsTime;
	}

	public void setContractsTime(String contractsTime) {
		this.contractsTime = contractsTime;
	}

	private UserCis userCis;// 上海资信
	private UserTaobao userTaobao;// 淘宝
	private UserChsi userChsi;// 学信
	private UserPbccre userPbccre;// 征信
	private UserHouseFund userHouseFund;// 公积金
	private UserSocialsecurity userSocialsecurity;// 社保
	private List<CreditCard> creditCards;// 银行卡账单

	private NameAuthentication nameAuthentication;// 申请人主表

	private List<UserContacts> userContacts;// 联系人
	private UserCallVisual  userCallVisual;// 联系人可视化信息
	private AssetInfo assetInfo;// 资产信息
	private OccupationalInfo userOccupationalInfo;// 职业信息

	private List<SysContract> contract;
	
	private UserAllAuthInfo userAllAuthInfo;     //认证标示
	
	private UserTongDun  userTongDun;    //同盾
	
	private PbccrcChartView pbccrcChartView; // 征信可视化
	
	private SysContract con;
	private String assignee;

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

	public String getProcStatusVal() {
		return procStatusVal;
	}

	public void setProcStatusVal(String procStatusVal) {
		this.procStatusVal = procStatusVal;
	}

	public String getProcStatus() {
		return procStatus;
	}

	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
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

	public List<CreditCard> getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(List<CreditCard> creditCards) {
		this.creditCards = creditCards;
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

	public List<UserContacts> getUserContacts() {
		return userContacts;
	}

	public void setUserContacts(List<UserContacts> userContacts) {
		this.userContacts = userContacts;
	}

	public String getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public List<SysContract> getContract() {
		return contract;
	}

	public void setContract(List<SysContract> contract) {
		this.contract = contract;
	}

	public String getLastUpdateEmpId() {
		return lastUpdateEmpId;
	}

	public void setLastUpdateEmpId(String lastUpdateEmpId) {
		this.lastUpdateEmpId = lastUpdateEmpId;
	}

	public String getLastUpdateEmpName() {
		return lastUpdateEmpName;
	}

	public void setLastUpdateEmpName(String lastUpdateEmpName) {
		this.lastUpdateEmpName = lastUpdateEmpName;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getSubmitTypeText() {
		return submitTypeText;
	}

	public void setSubmitTypeText(String submitTypeText) {
		this.submitTypeText = submitTypeText;
	}

	public String getSubmitEmpId() {
		return submitEmpId;
	}

	public void setSubmitEmpId(String submitEmpId) {
		this.submitEmpId = submitEmpId;
	}

	public String getSubmitEmpName() {
		return submitEmpName;
	}

	public void setSubmitEmpName(String submitEmpName) {
		this.submitEmpName = submitEmpName;
	}

	public Timestamp getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Timestamp submitDate) {
		this.submitDate = submitDate;
	}

	
	public Integer getAgreePeriod() {
		return agreePeriod;
	}

	public void setAgreePeriod(Integer agreePeriod) {
		this.agreePeriod = agreePeriod;
	}

	public String getAgreeProductId() {
		return agreeProductId;
	}

	public void setAgreeProductId(String agreeProductId) {
		this.agreeProductId = agreeProductId;
	}

	public String getCityDept() {
		return cityDept;
	}

	public void setCityDept(String cityDept) {
		this.cityDept = cityDept;
	}

	public String getBigAreaDept() {
		return bigAreaDept;
	}

	public void setBigAreaDept(String bigAreaDept) {
		this.bigAreaDept = bigAreaDept;
	}

	public String getCaustDept() {
		return caustDept;
	}

	public void setCaustDept(String caustDept) {
		this.caustDept = caustDept;
	}

	public SysContract getCon() {
		return con;
	}

	public void setCon(SysContract con) {
		this.con = con;
	}

	public Integer getIsTemp() {
		return isTemp;
	}

	public void setIsTemp(Integer isTemp) {
		this.isTemp = isTemp;
	}

	public String getIsTempVal() {
		return isTempVal;
	}

	public void setIsTempVal(String isTempVal) {
		this.isTempVal = isTempVal;
	}

	public Integer getIsApp() {
		return isApp;
	}

	public void setIsApp(Integer isApp) {
		this.isApp = isApp;
	}

	public String getIsAppVal() {
		return isAppVal;
	}

	public void setIsAppVal(String isAppVal) {
		this.isAppVal = isAppVal;
	}

	public String getProductNameAlias() {
		return productNameAlias;
	}

	public void setProductNameAlias(String productNameAlias) {
		this.productNameAlias = productNameAlias;
	}

	public String getAgreedProductAlias() {
		return agreedProductAlias;
	}

	public void setAgreedProductAlias(String agreedProductAlias) {
		this.agreedProductAlias = agreedProductAlias;
	}

	public String getProductTypeNameAlias() {
		return productTypeNameAlias;
	}

	public void setProductTypeNameAlias(String productTypeNameAlias) {
		this.productTypeNameAlias = productTypeNameAlias;
	}

	public UserCallVisual getUserCallVisual() {
		return userCallVisual;
	}

	public void setUserCallVisual(UserCallVisual userCallVisual) {
		this.userCallVisual = userCallVisual;
	}

	public String getSalesEmpId() {
		return salesEmpId;
	}

	public void setSalesEmpId(String salesEmpId) {
		this.salesEmpId = salesEmpId;
	}

	public String getSalesEmpName() {
		return salesEmpName;
	}

	public void setSalesEmpName(String salesEmpName) {
		this.salesEmpName = salesEmpName;
	}

	public String getSalesEmpDeptId() {
		return salesEmpDeptId;
	}

	public void setSalesEmpDeptId(String salesEmpDeptId) {
		this.salesEmpDeptId = salesEmpDeptId;
	}

	public String getSalesEmpDeptName() {
		return salesEmpDeptName;
	}

	public void setSalesEmpDeptName(String salesEmpDeptName) {
		this.salesEmpDeptName = salesEmpDeptName;
	}

	public UserAllAuthInfo getUserAllAuthInfo() {
		return userAllAuthInfo;
	}

	public void setUserAllAuthInfo(UserAllAuthInfo userAllAuthInfo) {
		this.userAllAuthInfo = userAllAuthInfo;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public Integer getSuggestResultId() {
		return suggestResultId;
	}

	public void setSuggestResultId(Integer suggestResultId) {
		this.suggestResultId = suggestResultId;
	}

	public String getSuggestResultVal() {
		return suggestResultVal;
	}

	public void setSuggestResultVal(String suggestResultVal) {
		this.suggestResultVal = suggestResultVal;
	}

	public String getSuggestResultCause() {
		return suggestResultCause;
	}

	public void setSuggestResultCause(String suggestResultCause) {
		this.suggestResultCause = suggestResultCause;
	}

	public UserTongDun getUserTongDun() {
		return userTongDun;
	}

	public void setUserTongDun(UserTongDun userTongDun) {
		this.userTongDun = userTongDun;
	}

	public PbccrcChartView getPbccrcChartView() {
		return pbccrcChartView;
	}

	public void setPbccrcChartView(PbccrcChartView pbccrcChartView) {
		this.pbccrcChartView = pbccrcChartView;
	}

	public Integer getCustTypeId() {
		return custTypeId;
	}

	public void setCustTypeId(Integer custTypeId) {
		this.custTypeId = custTypeId;
	}

	public String getCustTypeVal() {
		return custTypeVal;
	}

	public void setCustTypeVal(String custTypeVal) {
		this.custTypeVal = custTypeVal;
	}
	
}