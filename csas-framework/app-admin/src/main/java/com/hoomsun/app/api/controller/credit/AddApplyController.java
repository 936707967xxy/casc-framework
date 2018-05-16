package com.hoomsun.app.api.controller.credit;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.app.api.enums.ApiEnum;
import com.hoomsun.app.api.enums.IpUrlEnum;
import com.hoomsun.app.api.help.HttpClientController;
import com.hoomsun.app.api.model.AfreshContacterInfo;
import com.hoomsun.app.api.model.Borrower;
import com.hoomsun.app.api.model.CarrearInfo;
import com.hoomsun.app.api.server.inter.AfreshContacterInfoMapperServerI;
import com.hoomsun.app.api.server.inter.AfreshProtoinfoServerI;
import com.hoomsun.app.api.server.inter.BorrowerServerI;
import com.hoomsun.app.api.server.inter.CarrearInfoServerI;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.common.util.HttpClientUtil;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.common.util.SystemUtils;
import com.hoomsun.core.model.SysDepartment;
import com.hoomsun.core.model.SysProduct;
import com.hoomsun.core.model.SysProductType;
import com.hoomsun.core.server.inter.SerialNumberServerI;
import com.hoomsun.core.server.inter.SysDepartmentServerI;
import com.hoomsun.core.server.inter.SysProductServerI;
import com.hoomsun.core.server.inter.SysProductTypeServerI;
import com.hoomsun.csas.sales.api.model.AssetInfo;
import com.hoomsun.csas.sales.api.model.CreditCard;
import com.hoomsun.csas.sales.api.model.CreditCardBills;
import com.hoomsun.csas.sales.api.model.CreditCardBillsInfo;
import com.hoomsun.csas.sales.api.model.NameAuthentication;
import com.hoomsun.csas.sales.api.model.NameAuthenticationInfoWithBLOBs;
import com.hoomsun.csas.sales.api.model.OccupationalInfo;
import com.hoomsun.csas.sales.api.model.PbccrcChartView;
import com.hoomsun.csas.sales.api.model.UserAllAuthInfo;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.UserCallDetail;
import com.hoomsun.csas.sales.api.model.UserCallVisual;
import com.hoomsun.csas.sales.api.model.UserChsi;
import com.hoomsun.csas.sales.api.model.UserCis;
import com.hoomsun.csas.sales.api.model.UserContacts;
import com.hoomsun.csas.sales.api.model.UserHouseFund;
import com.hoomsun.csas.sales.api.model.UserPbccrcHtml;
import com.hoomsun.csas.sales.api.model.UserPbccre;
import com.hoomsun.csas.sales.api.model.UserSocialsecurity;
import com.hoomsun.csas.sales.api.model.UserTaoBaoAddress;
import com.hoomsun.csas.sales.api.model.UserTaobao;
import com.hoomsun.csas.sales.api.model.UserTongDun;
import com.hoomsun.csas.sales.api.server.inter.NameAuthenticationInfoServerI;
import com.hoomsun.csas.sales.api.server.inter.NameAuthenticationServerI;
import com.hoomsun.csas.sales.api.server.inter.RulesManageServerI;
import com.hoomsun.csas.sales.api.server.inter.UserApplyServerI;
import com.hoomsun.message.server.inter.NoticeServerI;

/**
 * @author 刘栋梁 作者：刘栋梁 <br>
 *         创建时间：2017年9月16日 <br>
 *         描述：添加申请单信息
 *         http://192.168.3.19:8080/app-admin/web/addapply/addApply.do
 * 
 *         数据中心校验规则
 *         http://192.168.3.19:8080/app-admin/web/addapply/regularsearch.do?IDCARD=&PRODUCTNAME
 * 
 *         本地校验规则
 *         http://192.168.3.19:8080/app-admin/web/addapply/rulesmanage.do?IDCARD=
 *         
 *         征信图表信息
 *         http://192.168.3.19:8080/app-admin/web/addapply/pbccchartinfo.do
 */
@Controller
@RequestMapping("web/addapply")
public class AddApplyController {

	@Autowired
	private NameAuthenticationServerI nameAuthentServer;

	@Autowired
	private NameAuthenticationInfoServerI nameAuthenticationInfoServer;

	@Autowired
	private AfreshProtoinfoServerI afreshProtoinfoServer;

	@Autowired
	private UserApplyServerI userApplyServer;
	
	@Autowired
	private SysProductTypeServerI sysProductTypeServerI;
	
	@Autowired
	private SysProductServerI sysProductServerI;

	@Autowired
	private SerialNumberServerI serialNumberServerI;
	
	@Autowired
	private CarrearInfoServerI  carrearInfoServerI;
	
	@Autowired
	private AfreshContacterInfoMapperServerI  afreshContacterInfoServerI;
	
	@Autowired
	private NoticeServerI  noticeServer;
	
	@Autowired
	private BorrowerServerI  borrowerServerI;
	
	@Autowired
	private RulesManageServerI  rulesManageServerI;
	
	@Autowired
	private SysDepartmentServerI sysDepartmentServerI;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	private static String chsiurl = IpUrlEnum.DATA_IP.getIpUrl() + ApiEnum.CHSIURL.getApiUrl();

	private static String cisurl = IpUrlEnum.DATA_IP.getIpUrl() + ApiEnum.CISURL.getApiUrl();

	private static String taobaourl = IpUrlEnum.DATA_IP.getIpUrl() + ApiEnum.TAOBAOURL.getApiUrl();

	private static String creditcardurl = IpUrlEnum.DATA_IP.getIpUrl() + ApiEnum.CREDITCARDURL.getApiUrl(); // 信用卡

	private static String pbccreurl = IpUrlEnum.DATA_IP.getIpUrl() + ApiEnum.PBCCREURL.getApiUrl(); // 征信

	private static String houseurl = IpUrlEnum.DATA_IP.getIpUrl() + ApiEnum.HOUSEURL.getApiUrl(); // 公积金

	private static String socialurl = IpUrlEnum.DATA_IP.getIpUrl() + ApiEnum.SOCIALURL.getApiUrl(); // 社保

	private static String contactinfourl = IpUrlEnum.DATA_IP.getIpUrl() + ApiEnum.CONTACTINFOURL.getApiUrl(); // 通话
	
	private static String dataregularintro_search_url =IpUrlEnum.DATA_IP.getIpUrl()+ ApiEnum.DATAREGULARINTRO_SEARCH_URL.getApiUrl(); // 数据中心拒件 
	
	private static String usercallvisual_url =IpUrlEnum.DATA_MODE_IP.getIpUrl()+ ApiEnum.CALLVISUAL.getApiUrl(); // 通话详单可视化
	
	private static String shareshield_url =IpUrlEnum.DATA_IP.getIpUrl()+ ApiEnum.SHARESHIELD.getApiUrl(); // 同盾
	
	private static String ischeclurl = IpUrlEnum.DATA_IP.getIpUrl()+ApiEnum.ISCHECK.getApiUrl();    // 认证状态
	
	/**
	 * 数据中心校验规则 2017-10-24 1.1 刘栋梁
	 * 
	 * @param request
	 * @return
	 * @LoggerAnnotation(moduleName = "单子申请相关", option = "数据中心校验规则")
	 */
	
	@RequestMapping("regularsearch.do")
	@ResponseBody
	public Map<String, Object> regulaRsearch(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String PAPERID = request.getParameter("IDCARD");
		String PRODUCTNAME = request.getParameter("PRODUCTNAME");

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			logger.info("传值" + PAPERID);
			String dataurl = dataregularintro_search_url;
			// 填入各个表单域的值
			NameValuePair[] pra = { new NameValuePair("idcode", PAPERID), new NameValuePair("product", PRODUCTNAME) };
			String datastr = HttpClientController.postcheck(dataurl, pra);
			logger.info("数据中心校验规则========" + datastr);
			Map datajason = JSON.parseObject(datastr); // 阿里的对象转换
			String wholeStauts = datajason.get("wholeStauts").toString();
			if ("0".equals(wholeStauts)) {
				result.put("errorInfo",datajason.get("basicCause"));
				result.put("errorCode", 1);
				return result;
			}else{
				result.put("errorInfo", "通过");
				result.put("errorCode", 0);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("数据中心校验规则使用时间：" + (endTime - startTime));
		}
		logger.info("数据中心校验规则:" + result);

		return result;
	}

	
	/**
	 * 本地校验规则     2017-12-29 
	 * 
	 * @param request
	 * @return
	 */
	
	@RequestMapping("rulesmanage.do")
	@ResponseBody
	public Map<String, Object> rulesmanage(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String idCard = request.getParameter("IDCARD");
		String IS_APP = request.getParameter("IS_APP");
		
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			logger.info("传值" + idCard);
			//1.线上、线下产品均不允许同时申请，即客户一旦申请一笔产品，在该订单结束之前不允许申请第二笔产品。
			Map<String, Object> IsCredit=rulesManageServerI.IsCredit(idCard);
			if(!"0".equals(IsCredit.get("errorCode")+"")){
				return IsCredit;
			}
			//   查询 门店有放款成功的单子  未结清的不让贷 	
			Map<String, Object> CreditIsFinish=rulesManageServerI.StoreCreditIsFinish(idCard);
			if(!"0".equals(CreditIsFinish.get("errorCode")+"")){
				return CreditIsFinish;
			}
			//4.线上产品逾期，则也不能进件
			Map<String, Object> AppCreditIsFinish=rulesManageServerI.AppCreditIsFinish(idCard);
			if(!"0".equals(AppCreditIsFinish.get("errorCode")+"")){
				return AppCreditIsFinish;
			}
			//身份证判断是否黑名单    /姓名,身份证,手机号	
			Map<String, Object> blackInfo=rulesManageServerI.BlackByIdCard(idCard);
			if(!"0".equals(blackInfo.get("errorCode")+"")){
				return blackInfo;
			}
			//封闭期-------线下产品才有封闭期,有线下产品据贷   90天
			Map<String, Object> CloseperiodInfo=rulesManageServerI.Closeperiod(idCard);
			if(!"0".equals(CloseperiodInfo.get("errorCode")+"")){
				return CloseperiodInfo;
			}
			
			//申请门店产品时 判断是否提交过app未结清 和 在审
			if("2".equals(IS_APP)){
				Map<String, Object> AppIsExistinfo=rulesManageServerI.AppIsExist(idCard);
				if(!"0".equals(AppIsExistinfo.get("errorCode")+"")){
					return AppIsExistinfo;
				}
			}
			result.put("errorInfo", "本地校验规则 通过!!!");
			result.put("errorCode", 0);		
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("本地校验规则     使用时间：" + (endTime - startTime));
		}
		logger.info("本地校验规则     :" + result);

		return result;
	}

	

	@RequestMapping("addApply.do")
	@ResponseBody
	public Map<String, Object> addApply(HttpServletRequest request) throws IOException, ParseException {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Map<String, Object> result = new HashMap<String, Object>();
		String ID = request.getParameter("ID"); // 用户id
		String PRODUCT_TYPE_ID = request.getParameter("PRODUCT_TYPE_ID");  //大产品ID
		String PRODUCT_ID = request.getParameter("PRODUCT_ID");  //小产品ID
		String APPLE_AMOUNT = request.getParameter("APPLE_AMOUNT"); // 金额
		String SUGGEST_AMOUNT = request.getParameter("SUGGEST_AMOUNT"); // 建议 // 额度(剩余额度)																		
		String AUDIT_TYPE = request.getParameter("AUDIT_TYPE"); // 审批方式    0 初审     1 终审
																
		String STORE_ID = request.getParameter("STORE_ID"); // 门店ID
		String STORE_NAME = request.getParameter("STORE_NAME"); // 部门NAME
		String INVESCODE = request.getParameter("INVESCODE"); // 邀请码		
		String TEAM_MANAGER_ID = request.getParameter("TEAM_MANAGER_ID"); // 销售经理ID
		String TEAM_MANAGER_NAME = request.getParameter("TEAM_MANAGER_NAME"); // 销售经理NAME
		String TEAM_MANAGER_DEPT_ID = request.getParameter("TEAM_MANAGER_DEPT_ID"); // 销售组ID
		String TEAM_MANAGER_DEPT_NAME = request.getParameter("TEAM_MANAGER_DEPT_NAME"); // 销售组NAME
		
		if(TEAM_MANAGER_DEPT_NAME.contains("客服")){
			TEAM_MANAGER_NAME = TEAM_MANAGER_NAME + "-客服";
		}
		
		String UUID = request.getParameter("UUID");          // UUID
		String LOAN_USE = request.getParameter("LOAN_USE"); // 借款用途
		String LOAN_USEVAL = request.getParameter("LOAN_USEVAL"); // 借款用途
		String IS_APP = request.getParameter("IS_APP");     // 1  APP     0   门店
		String IS_APP_VAL = request.getParameter("IS_APP_VAL");    
	   
		
		NameAuthenticationInfoWithBLOBs NameAuth = nameAuthenticationInfoServer.selectByFkid(ID);
		NameAuthentication NameAuthent = nameAuthentServer.selectByPrimaryKey(ID);
		SysProductType sysProductType=sysProductTypeServerI.selectByPrimaryKey(PRODUCT_TYPE_ID);
		SysProduct sysProduct = sysProductServerI.findproductbyId(PRODUCT_ID);
		CarrearInfo carrearInfo=carrearInfoServerI.selectByfkId(ID);
	    Borrower  borrower=borrowerServerI.selectByFkid(ID);
		List<AfreshContacterInfo> Contacterlist=afreshContacterInfoServerI.selectByFkid(ID);
		SysDepartment sysDepartment=sysDepartmentServerI.selectByPrimaryKey(STORE_ID);
		
	    
	    String applyId = PrimaryKeyUtil.getPrimaryKey();
	    
		//String applyId = "123456";
		UserApply apply = new UserApply();
		JSONArray imageUrlList =null;   //学信图像 
		apply.setApplyId(applyId);

		if("1".equals(IS_APP)){
			if (SUGGEST_AMOUNT != null) {
				apply.setSuggestAmount(new BigDecimal(SUGGEST_AMOUNT)); // 建议额度(剩余额度)
			} else if (Double.parseDouble(SUGGEST_AMOUNT) < Double.parseDouble(APPLE_AMOUNT)) {
				result.put("errorInfo", "申请额度大于当前剩余额度 ！！");
				result.put("errorCode", 1);
				return result;
			}		
		}
		
		

		if (NameAuth != null) {
			apply.setIdNumber(NameAuth.getIdNumber()); // 关联身份证
			apply.setCustName(NameAuth.getIdName()); // 申请人
			apply.setCustSex(NameAuth.getGender()); // 性别
			apply.setCustAge(Integer.parseInt(NameAuth.getAge())); // 年龄
			apply.setIdAddr(NameAuth.getAddress()); // 身份证地址

		}
		if(sysProductType!=null){
			apply.setProductTypeId(sysProductType.getProdId()); // 大产品ID
			apply.setProductTypeName(sysProductType.getProdName()); // 大产品NAME
			apply.setProductTypeNameAlias(sysProductType.getProdAlias());// 大产品别名
		}
		
		if (sysProduct != null) {	
			apply.setProductId(PRODUCT_ID); // 产品ID
			apply.setProductPay(sysProduct.getPayType());       // 申请产品还款方式
			apply.setProductPayVal(sysProduct.getPayTypeVal()); // 申请产品还款方式
			apply.setProductName(sysProduct.getProdName()); // 产品
			apply.setApplyPeriod(sysProduct.getSterm()); // 周期
			apply.setProductRate(sysProduct.getMonthRate()); // 月利率
			apply.setProductFeeRate(sysProduct.getYearRate()); // 年利率
			apply.setSuggestRate(sysProduct.getRealMonthRate()); // 建议利率
			apply.setProductNameAlias(sysProduct.getProdAlias()); //终审同意别名 
		}
		
		if (NameAuthent != null) {
			apply.setIdNumber(NameAuthent.getPaperid()); // 关联身份证
			apply.setCustName(NameAuthent.getCustname()); // 申请人
			apply.setCreditSesame(NameAuthent.getSesameGradeval()); // 芝麻分
			apply.setCustMobile(NameAuthent.getPhone()); // 电话
		}
		if(borrower!=null){
			apply.setNameBefore(borrower.getNameBefore());  //曾用名
			apply.setGraduateInstitutions(borrower.getGraduateInstitutions()); //毕业院校
			apply.setRaisePerson(borrower.getRaisePerson());//供养人数
			apply.setChildNumber(borrower.getChildNumber());//子女数目
			apply.setMaritalStatus(borrower.getMaritalStatus());//婚姻状况 （1：未婚，2：已婚，，4：离异，3：丧偶，5：其他）
			apply.setMaritalStatusVal(borrower.getMaritalStatusVal());
			apply.setEmail(borrower.getEmail());  //婚姻值
			if(borrower.getStartResidenceDate()!=null){
				apply.setStartResidenceDate(new Timestamp(borrower.getStartResidenceDate().getTime())); // 来借款申请地时间
			}
			if(borrower.getComeHereDate()!=null){
				apply.setComeHereDate(new Timestamp(borrower.getComeHereDate().getTime())); // 来申请地时间
			}
			apply.setEduBackground(borrower.getEduBackground());// 学历ID 1:硕士及以上,2:本科,3:大专,4:高中及以下
			apply.setEduBackgroundVal(borrower.getEduBackgroundVal());// 学历值
			apply.setQqWechat(borrower.getQqWechat());// QQ/微信
			apply.setRresidenceAddress(borrower.getRresidenceAddress());// 户口所在地
			apply.setRresidenceProvCode(borrower.getRresidenceProvCode());// 户口所在省份id
			apply.setRresidenceProvName(borrower.getRresidenceProvName());// 户口所在省份名称
			apply.setRresidenceCityCode(borrower.getRresidenceCityCode());// 户口所在市id
			apply.setRresidenceCityName(borrower.getRresidenceCityName());// 户口所在市名称
			apply.setRresidenceAreaCode(borrower.getRresidenceAreaCode()); // 户口所在区id
			apply.setRresidenceAreaName(borrower.getRresidenceAreaName());// 户口所在区名称
			apply.setRresidenceAddressDetail(borrower.getRresidenceAddressDetail());// 户口所在详细地址
			apply.setRresidenceZipCode(borrower.getRresidenceZipCode());// 户口邮政编码
			apply.setHouseAddress(borrower.getHouseAddress());// 现居住地 
			apply.setHouseProvCode(borrower.getHouseProvCode()); // 现居住地省份id
			apply.setHouseProvName(borrower.getHouseProvName());// 现居住地省份名称
			apply.setHouseCityCode(borrower.getHouseCityCode());// 现居住地市id
			apply.setHouseCityName(borrower.getHouseCityName());// 现居住地市名称
			apply.setHouseAreaCode(borrower.getHouseAreaCode());// 现居住地区id
			apply.setHouseAreaName(borrower.getHouseAreaName());// 现居住地市名称
			apply.setHouseAddressDetail(borrower.getHouseAddressDetail());// 户口所在详细地址
			apply.setHouseZipCode(borrower.getHouseZipCode());// 户口邮政编码
			apply.setLiveConditions(borrower.getLiveConditions());  //居住状况ID(1:按揭商品房,2:无按揭商品房,3:父母/配偶房产住房,4:单位住房,5:自建房,6:租用)
			apply.setLiveConditionsVal(borrower.getLiveConditionsVal());  //
			apply.setRentMonthlyPay(borrower.getRentMonthlyPay());     //月租
			apply.setValidMailAddr(borrower.getValidMailAddr());// 有效邮寄地址id（1:同户口地址，2:同居住地址，3:同单位地址，4:同房产地址，5:其他）			
			apply.setValidMailAddrVal(borrower.getValidMailAddrVal());// 有效邮寄地址val
			apply.setValidMailAddrTxt(borrower.getValidMailAddrTxt());  // 地址具体内容

			
		}
		
		AssetInfo AssetInfo =new AssetInfo();
		if(borrower!=null){
			AssetInfo.setCarHava(borrower.getCarHava());
			AssetInfo.setCarHavaVal(borrower.getCarHavaVal());
			AssetInfo.setPropertyType(borrower.getPropertyType());
			AssetInfo.setPropertyTypeVal(borrower.getPropertyTypeVal());
		}
		AssetInfo.setAsinfoPkId(PrimaryKeyUtil.getPrimaryKey());
		apply.setAssetInfo(AssetInfo);
		
		OccupationalInfo userOccupationalInfo=new OccupationalInfo();
		userOccupationalInfo.setOcinfoPkId(PrimaryKeyUtil.getPrimaryKey());
		if(carrearInfo!=null){
			userOccupationalInfo.setFullWorkName(carrearInfo.getFullWorkName()); //工作单位全称
			userOccupationalInfo.setIndustryIn(carrearInfo.getIndustryIn()); //所属行业   
			userOccupationalInfo.setIndustryVal(carrearInfo.getIndustryVal());  //所属行业值
			userOccupationalInfo.setCompanyAddress(carrearInfo.getCompanyAddress());   //单位地址 
			userOccupationalInfo.setCompanyProvCode(carrearInfo.getCompanyProvCode());  //单位地址所在省份id
			userOccupationalInfo.setCompanyProvName(carrearInfo.getCompanyProvName());//单位地址所在省份名称
			userOccupationalInfo.setCompanyCityCode(carrearInfo.getCompanyCityCode()); //单位地址所在市id   
			userOccupationalInfo.setCompanyCityName(carrearInfo.getCompanyCityName());  //单位地址所在市名称
			userOccupationalInfo.setCompanyAreaCode(carrearInfo.getCompanyAreaCode()); //单位地址所在区id  
			userOccupationalInfo.setCompanyAreaName(carrearInfo.getCompanyAreaName());//单位地址所在区名称
			userOccupationalInfo.setCompanyAddressDetail(carrearInfo.getCompanyAddressDetail());//单位地址所在明细地址
			userOccupationalInfo.setCompanyZipCode(carrearInfo.getCompanyZipCode());//邮编
			userOccupationalInfo.setCompanyTel(carrearInfo.getCompanyTel());        //单位电话区号+电话号(不用)
			//userOccupationalInfo.setCompanyTelCode(carrearInfo.getCompanyTelCode());//单位电话区号(不用)
			//userOccupationalInfo.setCompanyTelValue(carrearInfo.getCompanyTelValue());//单位电话号
			//userOccupationalInfo.setCompanyTelExt(carrearInfo.getCompanyTelValue());//单位电话分机号(不用)
			userOccupationalInfo.setCompanySize(carrearInfo.getCompanySize());//公司规模
			userOccupationalInfo.setCompanySizeVal(carrearInfo.getCompanySizeVal());//公司规模值
			userOccupationalInfo.setCompanyKind(carrearInfo.getCompanyKind()); //单位性质 
			userOccupationalInfo.setCompanyKindVa(carrearInfo.getCompanyKindVa()); //单位性质值
			userOccupationalInfo.setCompanyKindTxt(carrearInfo.getCompanyKindTxt());//如果为其他，单位性质值
			userOccupationalInfo.setPosition(carrearInfo.getPosition());  //职级  
			userOccupationalInfo.setPositionVal(carrearInfo.getPositionVal());////职级值
			userOccupationalInfo.setPositionTxt(carrearInfo.getPositionTxt());////职级其他值
			userOccupationalInfo.setWorkDept(carrearInfo.getWorkDept());  //所在部门 
			if(carrearInfo.getStartWorkTime()!=null){
				userOccupationalInfo.setStartWorkTime(new Timestamp(carrearInfo.getStartWorkTime().getTime()));//进入现单位时间  (时间类型)
			}			
			userOccupationalInfo.setPayDayMonthlyVal(carrearInfo.getPayDayMonthlyVal()); //每月发薪日
			userOccupationalInfo.setSocialSecurity(carrearInfo.getSocialSecurity());//社保情况
			userOccupationalInfo.setSocialSecurityVal(carrearInfo.getSocialSecurityVal());//社保情况值
			userOccupationalInfo.setJobTitle(carrearInfo.getJobTitle());   //职务名称 
			userOccupationalInfo.setOthterIncomeMonthly(carrearInfo.getOthterIncomeMonthly());//其他收入（月均，单位：元）
			userOccupationalInfo.setSalaryMonthly(carrearInfo.getSalaryMonthly()); //每月薪金   
			userOccupationalInfo.setPayMethod(carrearInfo.getPayMethod());//发薪方式 
			userOccupationalInfo.setPayMethodVal(carrearInfo.getPayMethodVal());//发薪方式值
			userOccupationalInfo.setPrivateType(carrearInfo.getPrivateType());//私营企业类型
			userOccupationalInfo.setPrivateTypeVal(carrearInfo.getPrivateTypeVal());//私企值
			userOccupationalInfo.setPositionTxt(carrearInfo.getPositionTxt());   //为其他    值
			if(carrearInfo.getCompanyRegtime()!=null){
				userOccupationalInfo.setCompanyRegtime(new Timestamp(carrearInfo.getCompanyRegtime().getTime()));//企业成立时间  (时间类型)
			}			
			userOccupationalInfo.setPercentageShares(carrearInfo.getPercentageShares());//股份占比
			userOccupationalInfo.setEmployeesNum(carrearInfo.getEmployeesNum());//员工人数
			userOccupationalInfo.setPremises(carrearInfo.getPremises());
			userOccupationalInfo.setPremisesVal(carrearInfo.getPremisesVal());
			if(carrearInfo.getCreateTime()!=null){
				userOccupationalInfo.setCreateTime(new Timestamp(carrearInfo.getCreateTime().getTime()));//创建时间 (时间)
			}
			    
		}
		apply.setUserOccupationalInfo(userOccupationalInfo);
		
		
		
		
		
		//客户填写的联系人
		List<UserContacts> Contactslist=new ArrayList<UserContacts>();
		if(Contacterlist.size()>0){
			for(AfreshContacterInfo afreshContacterInfo:Contacterlist ){
				UserContacts userContacts=new UserContacts();
				userContacts.setContId(PrimaryKeyUtil.getPrimaryKey()); // 主键
				userContacts.setName(afreshContacterInfo.getPoContactName());
				userContacts.setPhone(afreshContacterInfo.getPoMobile());
				userContacts.setAddDate(afreshContacterInfo.getContacterTime());
				userContacts.setRelationship(Integer.parseInt(afreshContacterInfo.getPoRelationship()));
				userContacts.setRelationshipVal(afreshContacterInfo.getPoRelationshipVal());
				//添加的字段   2018-02-05               防止报错判断 
				if(afreshContacterInfo.getIsKnow()!=null){
					userContacts.setIsKnow(Integer.parseInt(afreshContacterInfo.getIsKnow()));
					userContacts.setIsKnowVal(afreshContacterInfo.getIsKnowVal());
				}else{
					userContacts.setIsKnow(0);
					userContacts.setIsKnowVal("否");
				}
				userContacts.setIsFillIn(1);
				userContacts.setIsFillInVal("是");
				userContacts.setAddDate(DateUtil.getTimestamp());
				Contactslist.add(userContacts);
			}
		}
		
		
		
		String prefix="";
		if(sysDepartment.getDeptNo()!=null){
			prefix+=sysDepartment.getDeptNo();			
		}else{
			prefix="APP";
		}
		
		String LOANID = serialNumberServerI.createNumber("2", prefix);

		apply.setCustId(ID);
		apply.setApplyDate(DateUtil.getTimestamp());
		apply.setLoanId(LOANID);                       // LOANID
		apply.setApplyAmount(new BigDecimal(APPLE_AMOUNT));
		apply.setAgreedAmount(new BigDecimal("0"));    //审批额度
		apply.setAgreedProduct("0");                   //审批产品
		apply.setAgreePeriod(0);
		apply.setAgreeProductId("0");
		apply.setAgreedProductAlias("0");              //别名
		apply.setIdType(0);                            // 证件类型
		apply.setIdTypeVal("身份证");                    // 证件类型
		apply.setSources(Integer.parseInt(IS_APP));    // 来源 
		apply.setSourcesValue(IS_APP_VAL);             // 来源 
		apply.setIsApp(Integer.parseInt(IS_APP));
		apply.setIsAppVal(IS_APP_VAL);                 //是否是app单子     1   是   0 不是
		apply.setAuditType(Integer.parseInt(AUDIT_TYPE)); // //审批方式 0 初审 1 终审
		apply.setUuid(UUID);
		apply.setInvescode(INVESCODE);
		apply.setLoanUse(Integer.parseInt(LOAN_USE));	
		apply.setLoanUseVal(LOAN_USEVAL);
        if(LOAN_USE.equals("10")){
        	apply.setLoanUseText("其他");
		}
		apply.setStoreId(STORE_ID);
		apply.setStoreName(STORE_NAME);
		apply.setSalesEmpId(TEAM_MANAGER_ID);
		apply.setSalesEmpName(TEAM_MANAGER_NAME);
		apply.setSalesEmpDeptId(TEAM_MANAGER_DEPT_ID);
		apply.setSalesEmpDeptName(TEAM_MANAGER_DEPT_NAME);
		apply.setDelStatue(0);    //删除标识 
		apply.setSignconfirm(0);
		apply.setSignconfirmVal("未人脸识别");
		//apply.setCreateEmployee("1F379959A6BA45A597CB1E29D1C4033D");
		//apply.setCreateEmployeeVal("宋丽");
		if("1".equals(IS_APP)){
			apply.setIsTemp(0);//草稿标志
			apply.setIsTempVal("否");//草稿结果
		}else{
			apply.setIsTemp(1);//草稿标志
			apply.setIsTempVal("是");//草稿结果
		}
		
		try {

			// ----- 资信
			Map<String, Object> hsCisPara = new HashMap<String, Object>();
			hsCisPara.put("cardNumber", NameAuthent.getPaperid());
			String hsCisData = HttpClientUtil.doPost(cisurl, null, null, hsCisPara);
			Map<String, Object> hsCisJson = JSON.parseObject(hsCisData); // 阿里的对象转换
			if (!"0000".equals(hsCisJson.get("errorCode").toString())) {
				result.put("errorInfo", "资信信息添加失败！！");
				result.put("errorCode", 1);
				return result;
			}
			// UserCis
			// CIS=JSON.parseObject(hsCisJson.get("data").toString(),UserCis.class);
			UserCis CIS = null;
			Map HS_CIS = (Map) hsCisJson.get("consult");
			if (HS_CIS != null) {
				CIS = new UserCis();
				CIS.setCisId(PrimaryKeyUtil.getPrimaryKey());
				CIS.setWjqbs(HS_CIS.get("loan_count") + ""); // 未结清笔数
				CIS.setDkye(HS_CIS.get("loan_balance") + ""); // 贷款余额 //贷款余额
				CIS.setDqyqZe(HS_CIS.get("loan_amount_past_due") + ""); // 当前逾期总额
				CIS.setZgyqJe(HS_CIS.get("overdue_amount") + ""); // 最高逾期金额
				CIS.setZgyqQs(HS_CIS.get("overdue_times") + ""); // 最高逾期期数
				CIS.setLynzxcxs(HS_CIS.get("queryNum") + ""); // 6月内资信查询次数
				CIS.setHtmlUrl(HS_CIS.get("htmlUrl") + "");  //地址
				CIS.setForecastLiveTimeZx(HS_CIS.get("forecastLiveTimeZx") + "");//预测本地居住时长： 不低于**年  （
				CIS.setMaritalStatusZx(HS_CIS.get("maritalStatusZx") + "");//婚姻状况：未婚/已婚/离婚/再婚/丧偶/
				CIS.setAddressZx(HS_CIS.get("addressZx") + "");            //住址
				CIS.setCompanyNameZx(HS_CIS.get("companyNameZx") + "");    //单位名称
				CIS.setAddDate(DateUtil.getCurrentTime()); // 时间
			}
			apply.setUserCis(CIS);

			// ----- 淘宝----少子集表
			Map<String, Object> taoBaoPara = new HashMap<String, Object>();
			taoBaoPara.put("cardNumber", NameAuthent.getPaperid());
			String taoBaoData = HttpClientUtil.doPost(taobaourl, null, null, taoBaoPara);
			Map<String, Object> taoBaoJson = JSON.parseObject(taoBaoData); // 阿里的对象转换
			if (!"0000".equals(taoBaoJson.get("errorCode").toString())) {
				result.put("errorInfo", "淘宝信息添加失败！！");
				result.put("errorCode", 1);
				return result;
			}
			// UserTaobao
			// userTaobao=JSON.parseObject(taoBaoJson.get("data").toString(),UserTaobao.class);
			Map HS_TAOBO = (Map) taoBaoJson.get("data"); // 淘宝
			UserTaobao TAOBO = null;
			if (HS_TAOBO != null) {
				TAOBO = new UserTaobao();
				TAOBO.setAddDate(DateUtil.getCurrentTime()); // 时间
				String TbId = PrimaryKeyUtil.getPrimaryKey();
				TAOBO.setTbId(TbId); // 主键
				TAOBO.setHbed(HS_TAOBO.get("antAmount") + ""); // 花呗可用额度
				TAOBO.setZfbzhye(HS_TAOBO.get("accountAmount") + ""); // 支付宝账户余额
				TAOBO.setBnyebljsy(HS_TAOBO.get("accumulatedAmount") + ""); // 余额宝累计6个月收益
				TAOBO.setShdzzs(HS_TAOBO.get("addressNum") + ""); // 收货地址总数
				TAOBO.setJsyzrje(HS_TAOBO.get("income") + ""); // 近3个月转入
				TAOBO.setJsyzcje(HS_TAOBO.get("expend") + ""); // 近3个月支出金额
				TAOBO.setJsypjhk(""); // 近3个月平均还款金额(未提供)
				TAOBO.setJsyxfzb(""); // 近3个月消费占比(未提供)
				//TAOBO.setAdrss(HS_TAOBO.get("address") + ""); // 地址
				List<UserTaoBaoAddress> addresses = new ArrayList<UserTaoBaoAddress>();
				if (HS_TAOBO.get("address") != null) {
					JSONArray addresslist = JSON.parseArray(HS_TAOBO.get("address").toString());
					for (int i = 0; i < addresslist.size(); i++) {
						UserTaoBaoAddress userTaoBaoAddress = new UserTaoBaoAddress();
						String mapstr = addresslist.get(i).toString();
						Map<String, Object> map = JSON.parseObject(mapstr);
						userTaoBaoAddress.setTbAddressId(PrimaryKeyUtil.getPrimaryKey()); // 主键
						userTaoBaoAddress.setTbFkid(TbId); // 外键
						userTaoBaoAddress.setPhone(map.get("phone") + "");
						userTaoBaoAddress.setLocation(map.get("location") + "");
						userTaoBaoAddress.setDetailedaddress(map.get("detailedAddress") + "");
						userTaoBaoAddress.setTakeman(map.get("takeMan") + "");
						userTaoBaoAddress.setPostcode(map.get("postcode") + "");
						addresses.add(userTaoBaoAddress);
					}
				}
				TAOBO.setAddresses(addresses);
			}
			apply.setUserTaobao(TAOBO);

			// ----- 学信
			Map<String, Object> chsiPara = new HashMap<String, Object>();
			chsiPara.put("cardNumber", NameAuthent.getPaperid());
			String chsiData = HttpClientUtil.doPost(chsiurl, null, null, chsiPara);
			Map<String, Object> chsiJson = JSON.parseObject(chsiData); // 阿里的对象转换
			if (!"0000".equals(chsiJson.get("errorCode").toString())) {
				result.put("errorInfo", "学信信息添加失败！！");
				result.put("errorCode", 1);
				return result;
			}
			// UserChsi
			// userChsi=JSON.parseObject(chsiJson.get("data").toString(),UserChsi.class);
			Map HS_CHSI = (Map) chsiJson.get("data"); // 学信
			//学信图像
			Object imageUrl = chsiJson.get("imageUrl");
			if(imageUrl!=null){
				imageUrlList = JSON.parseArray(imageUrl.toString());
			}
								
			UserChsi CHSI = null;
			if (HS_CHSI != null) {
				CHSI = new UserChsi();
				CHSI.setChsiId(PrimaryKeyUtil.getPrimaryKey());
				CHSI.setAddDate(DateUtil.getCurrentTime()); // 时间
				CHSI.setShool(HS_CHSI.get("schoolName") + ""); // 院校
				CHSI.setMajor(HS_CHSI.get("domain") + ""); // 专业
				CHSI.setGraduation(HS_CHSI.get("graduateTime") + ""); // 毕业年份
				CHSI.setBirthdayTime(HS_CHSI.get("birthdayTime") + ""); // 出生日期
				CHSI.setNationality(HS_CHSI.get("nationality") + ""); // 民族
				CHSI.setCardNumber(HS_CHSI.get("cardNumber") + ""); // 身份证号
				CHSI.setClassGrade(HS_CHSI.get("classGrade") + ""); // 班级
				CHSI.setStudentNumber(HS_CHSI.get("studentNumber") + ""); // 学号
				CHSI.setJsonTime(HS_CHSI.get("joinTime") + ""); // 入学时间
				CHSI.setBranchCourts(HS_CHSI.get("branchCourts") + ""); // 分院
				//按照类型解析为标示
				String level=HS_CHSI.get("level")+"";
				if(level.contains("专")){
					CHSI.setMaxEdu("1");     // 最高学历
				}else if(level.contains("本")){
					CHSI.setMaxEdu("2"); // 最高学历
				}else if(level.contains("硕") || level.contains("研")){
					CHSI.setMaxEdu("3"); // 最高学历
				}else if(level.contains("博")){
					CHSI.setMaxEdu("4"); // 最高学历
				}else{
					CHSI.setMaxEdu("0");    // 核对
				}
				
				//不在籍(毕业)    在籍（注册学籍）
				String schoolStatus=HS_CHSI.get("schoolStatus")+"";
				if(schoolStatus.contains("毕") || schoolStatus.contains("业")){
					CHSI.setStatus("1"); // 学籍状态     不在籍(毕业)
				}else if(schoolStatus.contains("注") || schoolStatus.contains("册")){
					CHSI.setStatus("2"); // 学籍状态    在籍（注册学籍）
				}else{
					CHSI.setStatus("0");  // 核对
				}
				
				//普通全日制、全日制、网络教育,业余、开放教育
				String learningType=HS_CHSI.get("learningType")+"";
				if(learningType.contains("网") || learningType.contains("络") ){
					CHSI.setLearningType("1"); // 学习形式
				}else if((learningType.contains("全") || learningType.contains("日")) && !HS_CHSI.get("learningType").toString().contains("普")){
					CHSI.setLearningType("2"); // 学习形式  全日制
				}else if((learningType.contains("全") || learningType.contains("日")) && learningType.contains("普")){
					CHSI.setLearningType("3"); // 学习形式  普通全日制
				}else if(learningType.contains("业") || learningType.contains("余")){
					CHSI.setLearningType("4"); // 学习形式  业余
				}else if(learningType.contains("开") || learningType.contains("放") ){
					CHSI.setLearningType("5"); // 学习形式  开放教育
				}else{
					CHSI.setLearningType("0");  // 核对
				}
				
				//学历类别      网络教育    普通    研究生
				String QualificationsType=HS_CHSI.get("QualificationsType")+"";
				if(QualificationsType.contains("网") || QualificationsType.contains("络") ){
					CHSI.setQualificationType("1");
				}else if(QualificationsType.contains("普") || QualificationsType.contains("通")){
					CHSI.setQualificationType("2");
				}else if(QualificationsType.contains("研") || QualificationsType.contains("究") ){
					CHSI.setQualificationType("3");
				}else{
					CHSI.setLearningType("0");  // 核对
				}
				 

			}
			apply.setUserChsi(CHSI);

			// ----- 信用卡
			Map<String, Object> creditCardPara = new HashMap<String, Object>();
			creditCardPara.put("cardNumber", NameAuthent.getPaperid());
			String creditCardData = HttpClientUtil.doPost(creditcardurl, null, null, creditCardPara);

			JSONObject creditCardJson = JSON.parseObject(creditCardData);
			if (!"0000".equals(creditCardJson.get("errorCode").toString())) {
				result.put("errorInfo", "信用卡信息添加失败！！");
				result.put("errorCode", 1);
				return result;
			}

			JSONObject HS_CREDIT_CARD_BILLS = creditCardJson.getJSONObject("data"); // 信用卡账单
			List<CreditCard> CreditCardlist = new ArrayList<CreditCard>();
			if (HS_CREDIT_CARD_BILLS != null) {				
				JSONArray list = HS_CREDIT_CARD_BILLS.getJSONArray("CardList");
				if (list != null) {
					for (Object obj : list) {
						CreditCard creditCard = new CreditCard();
						String ccId = PrimaryKeyUtil.getPrimaryKey(); // 主键
						JSONObject CreditCardMap = JSON.parseObject(obj.toString());
						String accountName = ""; // 开户名
						String cardNumber = CreditCardMap.get("userAccount") + ""; // 卡号
						String limitMoney = ""; // 额度
						String openingBank = CreditCardMap.get("bankName") + ""; // 开户行
						String openTime = ""; // 开户时间
						creditCard.setAccountName(accountName);
						creditCard.setApplyId(applyId);
						creditCard.setCardNumber(cardNumber);
						creditCard.setLimitMoney(limitMoney);
						creditCard.setOpeningBank(openingBank);
						creditCard.setOpenTime(openTime);
						creditCard.setCcId(ccId);
						// 二级帐单 6个月
						JSONArray billList = JSON.parseArray(CreditCardMap.get("bankList").toString());
						List<CreditCardBills> CreditCardBillsList = new ArrayList<CreditCardBills>(); // 6个月
						for (int i = 0; i < billList.size(); i++) {
							String cbId = PrimaryKeyUtil.getPrimaryKey();
							CreditCardBills creditCardBills = new CreditCardBills();
							String mapstr = billList.get(i).toString();
							Map<String, Object> map = JSON.parseObject(mapstr);
							String AccountSummary = map.get("AccountSummary") + "";
							String payRecord = map.get("payRecord").toString();
							creditCardBills.setCbFkid(ccId); // 外键
							creditCardBills.setCbId(cbId); // 主键
							Map<String, Object> accountSummaryMap = JSON.parseObject(AccountSummary);
							creditCardBills.setBillsDate(accountSummaryMap.get("StatementDate") + ""); // 账单日
							creditCardBills.setRepaymentDate(accountSummaryMap.get("PaymentDueDate") + ""); // 还款日
							creditCardBills.setCurrentstermShouldmoney(accountSummaryMap.get("RMBCurrentAmountDue") + ""); // 当期应还款额
							creditCardBills.setMinniMoney(accountSummaryMap.get("RMBMinimumAmountDue") + ""); // 最小还款额
							creditCardBills.setRepaymentStatus(accountSummaryMap.get("overdue") + ""); // 是否逾期状态
							creditCardBills.setCreditLimit(accountSummaryMap.get("CreditLimit") + ""); // 额度
							creditCardBills.setDatas(payRecord); // 三级数据
							// 三级帐单 CREDITLIMIT
							List<CreditCardBillsInfo> CreditCardBillsInfoList = new ArrayList<CreditCardBillsInfo>();

							JSONArray billinfoList = JSON.parseArray(payRecord);
							for (int j = 0; j < billinfoList.size(); j++) {
								CreditCardBillsInfo creditCardBillsInfo = new CreditCardBillsInfo();
								String billmapstr = billinfoList.get(j).toString();
								Map<String, Object> billmap = JSON.parseObject(billmapstr);
								creditCardBillsInfo.setHccbiFkid(cbId); // 外键
								creditCardBillsInfo.setHccbiId(PrimaryKeyUtil.getPrimaryKey()); // 主键
								creditCardBillsInfo.setPostAmt(billmap.get("post_amt") + ""); // 消费金额
								creditCardBillsInfo.setTranDate(billmap.get("tran_date") + ""); // 时间
								creditCardBillsInfo.setTranDesc(billmap.get("tran_desc") + ""); // 地点
								CreditCardBillsInfoList.add(creditCardBillsInfo);
							}
							creditCardBills.setCardBillsInfos(CreditCardBillsInfoList); // 每个月流水放入二级对象
							CreditCardBillsList.add(creditCardBills); // 二级帐单存放到list
						}
						creditCard.setCardBills(CreditCardBillsList); // 6个月详情放入一级对象
						CreditCardlist.add(creditCard); // 一级帐单存放到list
					}

				}

			}
			apply.setCreditCards(CreditCardlist);
			
			
			
			// -----征信
			Map<String, Object> userPbccreUrlPara = new HashMap<String, Object>();
			userPbccreUrlPara.put("cardNumber", NameAuthent.getPaperid());
			String userPbccreData = HttpClientUtil.doPost(pbccreurl, null, null, userPbccreUrlPara);

			Map<String, Object> userPbccreJson = JSON.parseObject(userPbccreData); // 阿里的对象转换

			if (!"0000".equals(userPbccreJson.get("errorCode").toString())) {
				result.put("errorInfo", "征信信息添加失败！！");
				result.put("errorCode", 1);
				return result;
			}

			UserPbccre PBCCRC = null;
			String CR_ID = PrimaryKeyUtil.getPrimaryKey();
			Map HS_PBCCRC = (Map) userPbccreJson.get("data"); // 征信详情
			if (HS_PBCCRC != null) {
				PBCCRC=JSON.parseObject(userPbccreJson.get("data").toString(),UserPbccre.class);
				PBCCRC.setCrId(CR_ID);
				if(PBCCRC.getUserPbccrcHtml()!=null){
					UserPbccrcHtml pbccrcHtml=PBCCRC.getUserPbccrcHtml();
					pbccrcHtml.setApplyId(applyId);
					pbccrcHtml.setCrId(CR_ID);	
					pbccrcHtml.setPhId(PrimaryKeyUtil.getPrimaryKey());
					PBCCRC.setUserPbccrcHtml(pbccrcHtml);
					
				}
				
			}
			apply.setUserPbccre(PBCCRC);
			PbccrcChartView pbccrcChartView = null;
			Map HS_PBCCRC_VIEW = (Map) userPbccreJson.get("visualModel"); // 征信可视化数据
			if (HS_PBCCRC_VIEW != null) {
				Map<String, Object> pbccChartViewMap = JSON.parseObject(userPbccreJson.get("visualModel").toString());
				if (pbccChartViewMap != null) {
					pbccrcChartView = JSON.parseObject(userPbccreJson.get("visualModel").toString(),PbccrcChartView.class);;
					pbccrcChartView.setPbViewId(PrimaryKeyUtil.getPrimaryKey());
					pbccrcChartView.setApplyId(applyId);
					pbccrcChartView.setLoanPurposeInfo( pbccChartViewMap.get("loanPurposeInfo").toString() );
					pbccrcChartView.setCardLimitInfo( pbccChartViewMap.get("cardLimitInfo").toString() );
					pbccrcChartView.setLoanStatusInfo( pbccChartViewMap.get("loanStatusInfo").toString() );
					pbccrcChartView.setCardUsedTimeInfo( pbccChartViewMap.get("cardUsedTimeInfo").toString() );
					pbccrcChartView.setSelectWithLoanInfo( pbccChartViewMap.get("selectWithLoanInfo").toString() );
					pbccrcChartView.setCardUsedInfo( pbccChartViewMap.get("cardUsedInfo").toString() );
					pbccrcChartView.setAccountInfo( pbccChartViewMap.get("accountInfo").toString() );
					pbccrcChartView.setLoanMoneyInfo( pbccChartViewMap.get("loanMoneyInfo").toString() );
					pbccrcChartView.setLoanInstitutionInfo( pbccChartViewMap.get("loanInstitutionInfo").toString() );
					pbccrcChartView.setSelectInfo( pbccChartViewMap.get("selectInfo").toString() );
					pbccrcChartView.setOverdueinfo( pbccChartViewMap.get("overDueInfo").toString() );
				}
			}
			
			apply.setPbccrcChartView(pbccrcChartView);

			// -----社保 UserSocialsecurity
			Map<String, Object> userSocialPara = new HashMap<String, Object>();
			userSocialPara.put("cardNumber", NameAuthent.getPaperid());
			String userSocialData = HttpClientUtil.doPost(socialurl, null, null, userSocialPara);

			Map<String, Object> userSocialJson = JSON.parseObject(userSocialData); // 阿里的对象转换
			if (!"0000".equals(userSocialJson.get("errorCode").toString())) {
				result.put("errorInfo", "社保信息添加失败！！");
				result.put("errorCode", 1);
				return result;
			}

			Map HS_SOCIALSECURITY = (Map) userSocialJson.get("data");
			UserSocialsecurity SOCIALSECURITY = null;
			if (HS_SOCIALSECURITY != null) {
				SOCIALSECURITY = new UserSocialsecurity();
				SOCIALSECURITY.setAddDate(DateUtil.getCurrentTime()); // 时间
				SOCIALSECURITY.setApplyId(applyId); // 编号
				SOCIALSECURITY.setSiId(PrimaryKeyUtil.getPrimaryKey()); // 主键
				Map HS_SOCIALSECURITYDETIL = (Map) HS_SOCIALSECURITY.get("detailInfo");
				// //是否查询成功
				SOCIALSECURITY.setQueryStatus("1");
				SOCIALSECURITY.setSiStatus(HS_SOCIALSECURITYDETIL.get("SocialStatus") + ""); // 缴费状态
				SOCIALSECURITY.setSiMonths(HS_SOCIALSECURITYDETIL.get("allMonthCount") + ""); // 社保缴纳总月数
				SOCIALSECURITY.setSiSumMonths(HS_SOCIALSECURITYDETIL.get("brokeMonthCount") + ""); // 社保断缴累计月数
				SOCIALSECURITY.setSiSumAmount(HS_SOCIALSECURITYDETIL.get("amount") + ""); // 当前个人账户累积存储额
				SOCIALSECURITY.setSiBase(HS_SOCIALSECURITYDETIL.get("basicMoney") + ""); // 当年缴费基数
				SOCIALSECURITY.setSiCom(""); // 缴费单位
			}
			apply.setUserSocialsecurity(SOCIALSECURITY);

			// -----公积金 UserHouseFund
			Map<String, Object> userHousePara = new HashMap<String, Object>();
			userHousePara.put("cardNumber", NameAuthent.getPaperid());
			String userHouseData = HttpClientUtil.doPost(houseurl, null, null, userHousePara);

			Map<String, Object> userHouseJson = JSON.parseObject(userHouseData); // 阿里的对象转换
			if (!"0000".equals(userHouseJson.get("errorCode").toString())) {
				result.put("errorInfo", "公积金 信息添加失败！！");
				result.put("errorCode", 1);
				return result;
			}

			Map HS_HOUSEFUND = (Map) userHouseJson.get("data"); // 公积金
			UserHouseFund HOUSEFUND = null;
			if (HS_HOUSEFUND != null) {
				HOUSEFUND = new UserHouseFund();
				HOUSEFUND.setAddDate(DateUtil.getCurrentTime()); // 时间
				HOUSEFUND.setApplyId(applyId); // 编号
				HOUSEFUND.setHfId(PrimaryKeyUtil.getPrimaryKey()); // 主键
				HOUSEFUND.setSiCom(HS_HOUSEFUND.get("unitName") + ""); // 缴费单位
				Map HOUSEFUNDDETIL = (Map) HS_HOUSEFUND.get("detailInfo"); // 征信详情
				// 是否查询成功
				HOUSEFUND.setQueryStatus("1"); // 是否查询成功
				HOUSEFUND.setHsStatus(HOUSEFUNDDETIL.get("accumulationStatus") + ""); // 当前缴费状态
				HOUSEFUND.setHfMonths(HOUSEFUNDDETIL.get("allMonthCount") + ""); // 公积金实际缴纳月数
				HOUSEFUND.setHfSumMonths(HOUSEFUNDDETIL.get("brokeMonthCount") + ""); // 公积金断缴累计月数
				HOUSEFUND.setSiSumAmount(HOUSEFUNDDETIL.get("amount") + ""); // 缴纳总金额
				HOUSEFUND.setSiBase(HOUSEFUNDDETIL.get("BasicMoney") + ""); // 月缴基数
				HOUSEFUND.setSiBalance(HOUSEFUNDDETIL.get("balance") + ""); // 余额
				HOUSEFUND.setSiComType(HOUSEFUNDDETIL.get("thirdParties") + ""); // 缴纳单位是否为第三方机构

			}
			apply.setUserHouseFund(HOUSEFUND);

			// -----

			NameValuePair[] contactdata = { new NameValuePair("cardNumber", NameAuthent.getPaperid())};
			String contactreult = HttpClientController.postcheck(contactinfourl, contactdata);
			logger.info("通讯录信息" + contactreult);
			JSONObject contactjason = JSON.parseObject(contactreult);
			/*if ("2222".equals(contactjason.get("errorCode").toString())) {
				result.put("errorInfo", "通讯录校验失败,请使用自己手机");
				result.put("errorCode", 1);
				return result;
			}*/
			if ("1111".equals(contactjason.get("errorCode").toString())) {
				result.put("errorInfo", "通话详单录入失败！！");
				result.put("errorCode", 1);
				return result;
			}
			
			List<UserContacts> contacts = null;
			if("0000".equals(contactjason.get("errorCode").toString())){
				if (contactjason.get("data") != null) {
					contacts = new ArrayList<UserContacts>();
					JSONArray list = JSON.parseArray(contactjason.get("data").toString());
					// System.out.println(list.size());
					// List<UserContacts> contacts = new ArrayList<UserContacts>();
					for (int i = 0; i < list.size(); i++) {
						String mapstr = list.get(i).toString();
						JSONObject map = JSON.parseObject(mapstr);
						String PHONE = map.get("phone")+"";
						String CALLCOUNTS = map.get("callCounts")+"";
						String calltime = map.get("callTime")+"";
						String NAME = map.get("name")+"";
						String MOBILEADDRESS = map.get("MobileAddress")+"";
						String phoneid = PrimaryKeyUtil.getPrimaryKey();
						UserContacts phone = new UserContacts();

						phone.setContId(phoneid);
						phone.setPhone(PHONE);
						//phone.setName(URLEncoder.encode(NAME, "utf-8"));
						phone.setName(NAME);
						phone.setCallcounts(StringUtils.isBlank(CALLCOUNTS) ? 0 : Integer.parseInt(CALLCOUNTS));
						phone.setCallTime(calltime);
						phone.setMobileAddress(MOBILEADDRESS);
						phone.setRelationship(4);
						phone.setRelationshipVal("其他");
						phone.setIsKnow(0);
						phone.setIsKnowVal("否");
						phone.setIsFillIn(0);
						phone.setIsFillInVal("否");
						phone.setAddDate(DateUtil.getTimestamp());
						phone.setApplyId(applyId);

						String callMap = map.get("callMap").toString();
						JSONArray detaillist = JSON.parseArray(callMap);
						List<UserCallDetail> userCallDetails = new ArrayList<UserCallDetail>();
						for (int z = 0; z < detaillist.size(); z++) {
							String phonedetail = detaillist.get(z).toString();
							// System.out.println("======"+phonedetail);
							JSONObject detail = JSON.parseObject(phonedetail);
							String CALLMONEY = detail.get("CallMoney")+"";
							String CALLADDRESS = detail.get("CallAddress")+"";
							String CALLTYPE = detail.get("CallType")+"";
							String CALLTIME = detail.get("CallTime")+"";
							String CALLDURATION = detail.get("CallDuration")+"";
							String CALLWAY = detail.get("CallWay")+"";
							String CALLNUMBER = detail.get("CallNumber")+"";

							UserCallDetail callDetail = new UserCallDetail();
							callDetail.setPdid(PrimaryKeyUtil.getPrimaryKey());
							callDetail.setContId(phoneid);
							callDetail.setCallmoney(CALLMONEY);
							callDetail.setCalladdress(CALLADDRESS);
							callDetail.setCalltype(CALLTYPE);
							callDetail.setCalltime(CALLTIME);
							callDetail.setCallduration(CALLDURATION);
							callDetail.setCallway(CALLWAY);
							callDetail.setCallnumber(CALLNUMBER);
							userCallDetails.add(callDetail);
						}
						phone.setCallDetails(userCallDetails);

						contacts.add(phone);
					}
				}
				
				
				
				
				//据贷原因   {"refuse":{"reason":"","isRefuse":1}
				String refuse=contactjason.get("refuse").toString();
				JSONObject JSONRefuse = JSON.parseObject(refuse);
				String suggestResultId=JSONRefuse.getString("isRefuse");
				String suggestResultCause=JSONRefuse.getString("reason");
				apply.setSuggestResultId(Integer.parseInt(suggestResultId));
				apply.setSuggestResultCause(suggestResultCause);
				if("1".equals(suggestResultId)){
					apply.setSuggestResultVal("通过");				
				}else{
					apply.setSuggestResultVal("拒贷");
				}
				
				//认证时间  
				apply.setContractsTime(contactjason.getString("contractsTime"));
			}			
			apply.setUserContacts(contacts);
			
			
					
			//通讯可视化信息添加   
			Map<String, Object> userCallVisualPara = new HashMap<String, Object>();
			userCallVisualPara.put("cardNumber",  NameAuthent.getPaperid());
			String userCallVisualData = HttpClientUtil.doPost(usercallvisual_url, null, null, userCallVisualPara);
			Map<String, Object> userCallVisualJson = JSON.parseObject(userCallVisualData); // 阿里的对象转换

			
			UserCallVisual userCallVisual=null;
			if (!"0000".equals(userCallVisualJson.get("errorCode").toString())) {
				result.put("errorInfo", "通讯可视化信息添加失败！！");
				result.put("errorCode", 1);
				return result;
			}else{
			    userCallVisual=JSON.parseObject(userCallVisualData,UserCallVisual.class);
				userCallVisual.setApplyId(applyId);
				userCallVisual.setAddData(DateUtil.getTimestamp());
				userCallVisual.setId(PrimaryKeyUtil.getPrimaryKey());
			}
			apply.setUserCallVisual(userCallVisual);
			
			
			//同盾信息添加
			Map<String, Object> userTongdunPara = new HashMap<String, Object>();
			userTongdunPara.put("cardNumber",  NameAuthent.getPaperid());
			String userTongdunData = HttpClientUtil.doPost(shareshield_url, null, null, userTongdunPara);
			Map<String, Object> userTongdunJson = JSON.parseObject(userTongdunData); // 阿里的对象转换
			
			UserTongDun userTongdun=null;
			if (!"0000".equals(userTongdunJson.get("errorCode").toString())) {
				result.put("errorInfo", "同盾信息添加失败   ！！");
				result.put("errorCode", 1);
				return result;
			}else{
				userTongdun=JSON.parseObject(userTongdunJson.get("data")+"",UserTongDun.class);
				userTongdun.setApplyId(applyId);
				userTongdun.setAddDate(DateUtil.getCurrentTime());
				userTongdun.setId(PrimaryKeyUtil.getPrimaryKey());
			}
			apply.setUserTongDun(userTongdun);
			
			
			//认证信息           0:已认证，1：未认证，2：认证失败，3：认证中，4：认证过期
			UserAllAuthInfo  UserAllAuthInfo=new UserAllAuthInfo();
			// 填入各个表单域的值
			Map<String, Object> userAllAuthPara = new HashMap<String, Object>();
			userAllAuthPara.put("cardNumber",  NameAuthent.getPaperid());
			String str = HttpClientUtil.doPost(ischeclurl, null, null, userAllAuthPara);
			logger.info("======返回值========" + str);
			Map<String, Object> Msgmap = JSON.parseObject(str); // 阿里的对象转换
			String dataStr = Msgmap.get("data").toString();
			
			UserAllAuthInfo.setApplyId(applyId);
			UserAllAuthInfo.setAllAuthPk(PrimaryKeyUtil.getPrimaryKey());
			
			UserAllAuthInfo.setErrorCode(0);         //实名认证
			UserAllAuthInfo.setContacterCode(0);     //联系人认证
			UserAllAuthInfo.setProtoinfoCode(0);     //个人还款卡认证
			UserAllAuthInfo.setCareerCode(0);        //职业信息认证
			UserAllAuthInfo.setBorrowerCode(0);      //个人信息认证
			
			
			// 收入图片认证
			if ("0".equals(NameAuthent.getIncome())) {
				UserAllAuthInfo.setIncomeCode(1);  //未认证
			} else {
				UserAllAuthInfo.setIncomeCode(0);
			}
			
			
			if ("0".equals(NameAuthent.getIssesame())) {
				UserAllAuthInfo.setZhimaCode(1);
			} else {
				String endstr = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
				String startstr = NameAuthent.getSesameTime();
				int datadiff = DateUtil.getDay(startstr, endstr);
				logger.info("芝麻认证时间----------：" + datadiff);
				if (datadiff >= 30) {
					UserAllAuthInfo.setZhimaCode(4);
				} else {
					UserAllAuthInfo.setZhimaCode(0);
				}

			}
			
			if (!"null".equals(dataStr)) {
				Map<String, Object> datamap = JSON.parseObject(dataStr);
				UserAllAuthInfo.setChsi(Integer.parseInt(datamap.get("CHSI").toString()));		 // 学信网
				UserAllAuthInfo.setSocialSecurity(Integer.parseInt(datamap.get("socialSecurity").toString()));// 社保
				UserAllAuthInfo.setAccumulationFund(Integer.parseInt(datamap.get("accumulationFund").toString())); // 公积金
				UserAllAuthInfo.setCreditInvestigation(Integer.parseInt(datamap.get("creditInvestigation").toString()));// 征信
				UserAllAuthInfo.setCallLog(Integer.parseInt(datamap.get("callLog").toString()));// 通话详单
				UserAllAuthInfo.setTaobao(Integer.parseInt(datamap.get("TaoBao").toString()));// 淘宝
				UserAllAuthInfo.setBankBillFlow(Integer.parseInt(datamap.get("bankBillFlow").toString())); // 信用卡数据
				UserAllAuthInfo.setSavings(Integer.parseInt(datamap.get("savings").toString())); //savings    储蓄卡数据					
			} else {
				UserAllAuthInfo.setChsi(1);		 // 学信网
				UserAllAuthInfo.setSocialSecurity(1);// 社保
				UserAllAuthInfo.setAccumulationFund(1); // 公积金
				UserAllAuthInfo.setCreditInvestigation(1);// 征信
				UserAllAuthInfo.setCallLog(1);// 通话详单
				UserAllAuthInfo.setTaobao(1);// 淘宝
				UserAllAuthInfo.setBankBillFlow(1); // 信用卡数据
				UserAllAuthInfo.setSavings(1); //savings    储蓄卡数据	
			}
			apply.setUserAllAuthInfo(UserAllAuthInfo);
			
			// 存入门店以及IP
			String ipAddr = SystemUtils.getIpAddr(request);
			apply.setIpAddress(ipAddr);
			String deptWorkAddr = sysDepartment.getDeptWorkAddr();
			apply.setApplyAddress(deptWorkAddr);
			
			String context = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			Json json = userApplyServer.saveAuthData(apply,Contactslist,imageUrlList,context);
			if (json.getSuccess()) {
				String Msg="";
				 if("2".equals(IS_APP) ){
					Msg="您提交的"+sysProductType.getProdAlias()+"申请提交成功，请等待客服联系补充申请资料";
				}			
				noticeServer.sendMsg(applyId,Msg,1);  //申请成功推送
				result.put("errorInfo", "数据添加成功！！！！");
				result.put("errorCode", 0);
			} else {
				result.put("errorInfo", "数据添加失败！！！！");
				result.put("errorCode", 1001);
			}

		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("添加申请单信息 使用时间：" + (endTime - startTime));
		}
		logger.info("添加申请单信息 :" + result);
		return result;
	}
	
	
}
