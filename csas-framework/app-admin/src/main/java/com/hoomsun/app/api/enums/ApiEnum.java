/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.app.api.enums;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月26日 <br>
 * 描述：接口的路径定义
 */
public enum ApiEnum {
	
	
	/**
	 *  数据拒件查询----AddApplyController
	 */
	DATAREGULARINTRO_SEARCH_URL("/HSDC/refuse/refuseForOffline"), 
	
	/**
	 *  额度不带产品查询接----HomePageController
	 */
	AMOUNTMONEY("/HSDC/authcode/amountMoney"), 
	/**
	 *  额度不带产品查询接----HomePageController
	 */
	AMTPROURL("/HSDC/authcode/amountMoneyPro"),
	/**
	 *  认证结果----HomePageController
	 */
	ISCHECK("/HSDC/authcode/approveDetails"),
	/**
	 * 通话详单     数据中心----MsgController
	 */
	FINDCALLURL("/HSDC/grade/findCall"),
	/**
	 * 学信接口      数据中心----MsgController
	 */
	FINDCHSIURL("/HSDC/grade/findCHSI"),
	/**
	 * 社保             数据中心----MsgController
	 */
	SOCIALSECURITYURL("/HSDC/person/GetsocialSecurityByidcard"),
	
	/**
	 * 公积金         数据中心----MsgController
	 */
	ACCUMULATIONFUNDURL("/HSDC/person/GetaccumulationFundByidcard"),
	
	/**
	 * 获取征信记录    数据中心----MsgController
	 */
	CREDITINVESTIGATIONURL("/HSDC/person/GetcreditInvestigationByidcard"),
	/**
	 * 邮箱   数据中心----MsgController
	 */
	FINDEMAILURL("/HSDC/grade/findEmail"),
	/**
	 * 查淘宝   数据中心----MsgController
	 */
	FINDTAOBAOURL("/HSDC/grade/findTaoBao"),
	/**
	 * 查公司邮箱   数据中心----MsgController
	 */
	FINDCOMPANYEMAIURL("/HSDC/grade/findCompanyEmail"),
	/**
	 * 查行用卡账单    数据中心----MsgController
	 */
	GETBANKBILLFLOWBYIDCARD("/HSDC/person/GetbankBillFlowByidcard"),
	/**
	 * 查行所有信用卡认证信息    数据中心----MsgController(好像作废)
	 */
	GETALLBANKBILLFLOWBYIDCARD("/HSDC/BillFlow/FindBankInfoById"),
	/**
	 * 查行所有储蓄卡认证信息    数据中心----MsgController
	 */
	GETALLDEPBANKSAVINGS("/HSDC/grade/findSavings"),
	/**
	 * 判断储蓄卡是否认证     数据中心----MsgController
	 */
	GETJUDGESAVINGS("/HSDC/grade/judgeSavings"),
	/**
	 * 储蓄卡判断是否推送状态     数据中心----MsgController
	 */
	GETAPPROVESAVINGS("/HSDC/grade/approveSavings"),
	
	/**
	 * 查行所有储蓄卡信息    数据中心----MsgController
	 */
	GETALLDEPBANKBYIDCARD ("/HSDC/savings/findBank"),
	/**
	 *  基本信息获取     数据中心----AddApplyController
	 */
	BASEMSG("/HSDC/grade/dataPush"),
	/**
	 *  推送经纬度淘宝对比      数据中心----AddApplyController
	 */
	LOCATIONTAOBAOURL("/HSDC/grade/location"),
	/**
	 *  联系人信息      数据中心----AddApplyController
	 */
	CONTACTINFOURL("/HSDC/Contact/ContactInfo"),
	/**
	 *  实名认证信息推送      数据中心----InvestController
	 */
	VALIDATIONURL("/HSDC/authcode/authentication"),
	/**
	 *  实名认证推送经纬度      数据中心----InvestController
	 */
	LOCATIONURL("/HSDC/grade/location"),
	/**
	 *  推送芝麻分       数据中心----ZhiMaController
	 */
	ZHIMAURL("/HSDC/grade/sesame"),
	/**
	 *  信用卡认证时间       数据中心----BankApiController
	 */
	CRECARDTIMEURL("/HSDC/BillFlow/FindBankTimeByIdcode"),
	/**
	 *  信用卡认证       数据中心----BankApiController
	 */
	CRECARDT("/HSDC/BillFlow/FindAuthenticatedBankInfoById"),
	/**
	 *  学信数据        AddApplyController
	 */
	CHSIURL("/HSDC/onlineFind/chsi"),
	/**
	 *  资信数据        AddApplyController
	 */
	CISURL("/HSDC/onlineFind/consult"),
	/**
	 *  淘宝数据        AddApplyController
	 */
	TAOBAOURL("/HSDC/onlineFind/taoBao"),
	/**
	 *  信用卡数据        AddApplyController
	 */
	CREDITCARDURL("/HSDC/onlineFind/findCard"),
	/**
	 *  征信数据        AddApplyController
	 */
	PBCCREURL("/HSDC/onlineFind/findCreditInvestigation"),
	/**
	 *  公积金数据        AddApplyController
	 */
	HOUSEURL("/HSDC/onlineFind/findAccumulation"),
	/**
	 *  社保数据        AddApplyController
	 */
	SOCIALURL("/HSDC/onlineFind/findSocial"),
	/**
	 *  通话详单可视化        AddApplyController
	 */
	CALLVISUAL("/HsDataMode/calculate/getVisualModel"),
	/**
	 *  同盾数据        AddApplyController
	 */
	SHARESHIELD("/HSDC/onlineFind/ShareShield"),
	;    
	
	
	private String apiUrl;

	private ApiEnum() {
	}
	
	private ApiEnum(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

}
