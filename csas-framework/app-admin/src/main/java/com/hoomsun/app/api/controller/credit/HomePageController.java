package com.hoomsun.app.api.controller.credit;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.app.api.enums.ApiEnum;
import com.hoomsun.app.api.enums.IpUrlEnum;
import com.hoomsun.app.api.help.HttpClientController;
import com.hoomsun.app.api.model.AfreshProtoinfo;
import com.hoomsun.app.api.model.Borrower;
import com.hoomsun.app.api.model.CarrearInfo;
import com.hoomsun.app.api.server.inter.AfreshContacterInfoMapperServerI;
import com.hoomsun.app.api.server.inter.AfreshProtoinfoServerI;
import com.hoomsun.app.api.server.inter.BorrowerServerI;
import com.hoomsun.app.api.server.inter.CarrearInfoServerI;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.core.model.SysProductType;
import com.hoomsun.core.server.inter.SysProductTypeServerI;
import com.hoomsun.csas.sales.api.model.NameAuthentication;
import com.hoomsun.csas.sales.api.server.inter.NameAuthenticationServerI;
import com.hoomsun.csas.sales.api.server.inter.UserApplyServerI;


/**
 * 
 * @author 刘栋梁
 * @date 2017-08-15
 * @resource 首页所需接口 1.获取额度--没产品
 *           192.168.3.19:8190/app-admin/web/homepage/getgrade.do?ID= 2.获取额度--有产品
 *           192.168.3.19:8190/app-admin/web/homepage/getgradepro.do?ID=&PRODUCT=
 *           3.获取认证状态
 *           192.168.3.19:8190/app-admin/web/homepage/getcheck.do?PHONE=&ID=&UUID
 */

@Controller
@RequestMapping("web/homepage")
public class HomePageController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private NameAuthenticationServerI hsServerI;

	@Autowired
	public void setHsServerI(NameAuthenticationServerI hsServerI) {
		this.hsServerI = hsServerI;
	}

	@Autowired
	private AfreshContacterInfoMapperServerI AfreshContacterServer;

	@Autowired
	private AfreshProtoinfoServerI afreshProtoServer;
	
	@Autowired
	private CarrearInfoServerI carrearInfoServerI;
	
	@Autowired
	private BorrowerServerI borrowerServerI;

	@Autowired
	private UserApplyServerI userApplyServer;
	
	@Autowired
	private SysProductTypeServerI sysProductTypeServerI;

	private static String amountmoneyurl =IpUrlEnum.DATA_IP.getIpUrl()+ ApiEnum.AMOUNTMONEY.getApiUrl(); // 最高总额度

	private static String amtprourlurl = IpUrlEnum.DATA_IP.getIpUrl()+ApiEnum.AMTPROURL.getApiUrl();    // 产品额度

	private static String ischeclurl = IpUrlEnum.DATA_IP.getIpUrl()+ApiEnum.ISCHECK.getApiUrl();       // 认证状态
	
	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-09-15
	 * @resource 查询分值 给首页提供额度 -----------基于没有选择产品      废弃 
	 * @LoggerAnnotation(moduleName = "APP首页所需接口", option = "基于未选择产品查询分值")
	 * @RequestMapping(value = "/getgrade.do")
	   @ResponseBody
	 */
	
	
	public Map<String, Object> getGrade(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String ID = request.getParameter("ID");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (ID != null && !ID.isEmpty()) {
				// 查询当前用户信息
				NameAuthentication login = hsServerI.selectByPrimaryKey(ID);
				String cardNumber = login.getPaperid();
				// --------------------
				
				// 填入各个表单域的值
				NameValuePair[] data = { new NameValuePair("cardNumber", cardNumber) };
				String str = HttpClientController.postcheck(amountmoneyurl, data);
				logger.info("======返回值========" + str);
				Map Msgmap = JSONObject.fromObject(str);
				String AmountMoney = Msgmap.get("AmountMoney").toString();
				Object basicCause = Msgmap.get("basicCause");
				String INS_CURRENTSTATUS = Msgmap.get("examineState").toString();
				String Leval = Msgmap.get("rank").toString();
				String Rate = Msgmap.get("rate").toString();
				String product = Msgmap.get("product").toString();
				// 求当前总金额
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("INS_CURRENTSTATUS", INS_CURRENTSTATUS);
				result.put("Leval", Leval);
				result.put("Rate", Rate);
				result.put("product", product);
				result.put("basicCause", basicCause);
				Map<String, Object> moneyMap = userApplyServer.selectUserHasused(ID);
				
				/*
				Map<String, Object> moneyMap = userApplyServer.selectUserHasusedSum(ID);
				String amtsum=AmountMoney;
				String balance = "0";
				if (moneyMap == null) {
					balance = AmountMoney;
					amtsum=AmountMoney;
				} else {
					
					logger.info("申请的单子=" + moneyMap.get("amt")+"===审批的"+ moneyMap.get("agreedamt"));
					amtsum=(Double.parseDouble(AmountMoney)
							- Double.parseDouble(moneyMap.get("amt").toString())
							+Double.parseDouble(moneyMap.get("agreedamt").toString()))+"";
					
					Double balancelimit = Double.parseDouble(amtsum)
							- Double.parseDouble(moneyMap.get("amt").toString());
					if (balancelimit <= 0) {
						balance = "0";
					} else {
						balance = "" + balancelimit;
					}
				}*/
				
				String amtsum=AmountMoney;
				String balance = "0";
				if (moneyMap == null) {
					balance = AmountMoney;
					amtsum=AmountMoney;
				} else {					
					logger.info("申请的单子=" + moneyMap.get("amt"));
					Double balancelimit = Double.parseDouble(amtsum)
							- Double.parseDouble(moneyMap.get("amt").toString());
					if (balancelimit <= 0) {
						balance = "0";
					} else {
						balance = "" + balancelimit;
					}
				}
				result.put("Amt", amtsum+"");
				result.put("balance", balance);
				map.put("errorInfo", "查询成功");
				map.put("errorCode", 0);
				map.put("data", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "网络异常，请稍后！");
			map.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("头部查询分值时间  ：" + (endTime - startTime));
		}
		logger.info("头部查询分值  " + map);
		return map;
	}

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-09-15
	 * @resource 查询分值 给首页提供额度 -----------基于选择产品
	 * @LoggerAnnotation(moduleName = "APP首页所需接口", option = "基于选择产品查询分值")
	 *
	 */
	
	@RequestMapping(value = "/getgradepro.do")
	@ResponseBody
	public Map<String, Object> getGradePro(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String ID = request.getParameter("ID");
		String PRODUCT = request.getParameter("PRODUCT");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (ID != null && !ID.isEmpty()) {
				// 查询当前用户信息
				NameAuthentication login = hsServerI.selectByPrimaryKey(ID);
				String cardNumber = login.getPaperid();
				// --------------------
				// 填入各个表单域的值
				NameValuePair[] data = { new NameValuePair("cardNumber", cardNumber), new NameValuePair("product", PRODUCT) };
				String str = HttpClientController.postcheck(amtprourlurl, data);
				logger.info("======返回值========" + str);
				Map Msgmap = JSONObject.fromObject(str);
				String AmountMoney = Msgmap.get("AmountMoney").toString();
				String INS_CURRENTSTATUS = Msgmap.get("examineState").toString();
				Object basicCause = Msgmap.get("basicCause");
				String Leval = Msgmap.get("rank").toString();
				String Rate = Msgmap.get("rate").toString();
				String product = Msgmap.get("product").toString();

				
				//产品信息
				SysProductType   SysProductType=sysProductTypeServerI.findProTypebyCode(product);
				
				// 求当前总金额
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("INS_CURRENTSTATUS", INS_CURRENTSTATUS);
				result.put("Leval", Leval);
				result.put("Rate", Rate);
				result.put("product", product);
				result.put("maxcreditamt", SysProductType.getMaxCreditAmt());
				result.put("mixcreditamt", SysProductType.getMixCreditAmt());
				result.put("maxrate", SysProductType.getMaxRate());
				result.put("Amt", AmountMoney);
				result.put("basicCause", basicCause);
				Map<String, Object> moneyMap = userApplyServer.selectUserHasused(ID);
				String balance = "0";
				if (moneyMap == null) {
					balance = AmountMoney;
				} else {
					logger.info("申请的单子=" + moneyMap.get("amt"));
					Double balancelimit = Double.parseDouble(AmountMoney)
							- Double.parseDouble(moneyMap.get("AMT").toString());
					if (balancelimit <= 0) {
						balance = "0";
					} else {
						balance = "" + balancelimit;
					}
				}

				

				result.put("balance", balance);
				map.put("errorInfo", "查询成功");
				map.put("errorCode", 0);
				map.put("data", result);

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("==",e);
			map.put("errorInfo", "网络异常，请稍后！");
			map.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("查询分值选择产品 时间  ：" + (endTime - startTime));
		}
		logger.info("查询分值选择产品   " + map);
		return map;
	}

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-08-15
	 * @resource 认证状态
	 * @LoggerAnnotation(moduleName = "APP首页所需接口", option = "获取认证状态")
	 */
	
	@RequestMapping("getcheck.do")
	@ResponseBody
	public Map<String, Object> getCheck(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String PHONE = request.getParameter("PHONE");
		String ID = request.getParameter("ID");
		String UUID = request.getParameter("UUID"); // 设备码
		NameAuthentication checkmap = new NameAuthentication();
		checkmap.setPhone(PHONE);
		checkmap.setUuid(UUID);
		Map<String, Object> result = new HashMap<String, Object>();
		logger.info("认证状态" + UUID + "----" + PHONE + "-----------" + ID);
		try {
			NameAuthentication nameth = hsServerI.selectUuid(checkmap);
			if (nameth == null) {
				// 单设备登陆
				result.put("errorInfo", "其他设备已登陆");
				result.put("errorCode", 1003);
				return result;
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();

			NameAuthentication login = hsServerI.selectByPrimaryKey(ID);
			// 收入图片认证
			if ("0".equals(login.getIncome())) {
				dataMap.put("incomeCode", 1);
			} else {
				dataMap.put("incomeCode", 0);
			}

			// 实名认证 芝麻认证 职业信息认证 联系人认证
			if ("0".equals(login.getIsauthentication())) {
				dataMap.put("errorInfo", "客户未实名认证");
				dataMap.put("errorCode", 1);
			} else {
				dataMap.put("errorInfo", "客户已实名认证");
				dataMap.put("errorCode", 0);
			}

			if ("0".equals(login.getIssesame())) {
				dataMap.put("zhimaCode", 1);
			} else {
				String endstr = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
				String startstr = login.getSesameTime();
				int datadiff = DateUtil.getDay(startstr, endstr);
				logger.info("芝麻认证时间----------：" + datadiff);
				if (datadiff >= 30) {
					dataMap.put("zhimaInfo", Integer.parseInt(login.getSesameGradeval()));
					dataMap.put("zhimaCode", 1);
				} else {
					dataMap.put("zhimaInfo", Integer.parseInt(login.getSesameGradeval()));
					dataMap.put("zhimaCode", 0);
				}

			}
			
			Borrower borrower=borrowerServerI.selectByFkid(ID);
			if(borrower==null){
				dataMap.put("BorrowerCode", 1);
			}else{
				dataMap.put("BorrowerCode", 0);
			}

			//联系人信息
			if ("0".equals(login.getContact())) {
				dataMap.put("ContacterCode", 1);
			} else {				
				dataMap.put("ContacterCode", 0);
			}

			CarrearInfo CareerInfo = carrearInfoServerI.selectByfkId(ID);
			if (CareerInfo == null) {				
				dataMap.put("CareerCode", 1);
			} else {
				// dataMap.put("BorrowerInfo", "客户已录职业信息");
				dataMap.put("CareerCode", 0);

			}

			List<AfreshProtoinfo>  Protoinfo = afreshProtoServer.selectByFkid(ID);
			if (Protoinfo.size()==0) {
				// dataMap.put("ContacterInfo", "客户未录银行信息");
				dataMap.put("protoinfoCode", 1);
			} else {
				// dataMap.put("ContacterInfo", "客户已录银行信息");
				dataMap.put("protoinfoCode", 0);
			}

			// --------------------认证状态,数据中心 0 已认证 1未认证 2认证失败 3认证中
			String cardNumber = login.getPaperid();
			if (cardNumber == null) {
				dataMap.put("CHSI", 1); // 学信网
				dataMap.put("socialSecurity", 1); // 社保
				dataMap.put("accumulationFund", 1); // 公积金
				dataMap.put("creditInvestigation", 1); // 征信
				dataMap.put("callLog", 1); // 通话详单
				// dataMap.put("mailBill", 1); //邮箱账单
				dataMap.put("taoBao", 1); // 淘宝
				dataMap.put("CompanyEmal", 1); // 公司邮箱
				dataMap.put("bankBillFlow", 1); // 信用卡
				//savings    储蓄卡证明 
				dataMap.put("savings", 1); 
				result.put("errorInfo", "未实名认证!!");
				result.put("errorCode", 0);
				result.put("data", dataMap);

				return result;
			}
	
			// 填入各个表单域的值
			NameValuePair[] data = { new NameValuePair("cardNumber", cardNumber) };
			String str = HttpClientController.postcheck(ischeclurl, data);
			logger.info("======返回值========" + str);
			Map Msgmap = JSONObject.fromObject(str);
			String dataStr = Msgmap.get("data").toString();
			if (!"null".equals(dataStr)) {
				Map datamap = JSONObject.fromObject(dataStr);
				dataMap.put("CHSI", datamap.get("CHSI")); // 学信网
				dataMap.put("socialSecurity", datamap.get("socialSecurity")); // 社保
				dataMap.put("accumulationFund", datamap.get("accumulationFund")); // 公积金
				dataMap.put("creditInvestigation", datamap.get("creditInvestigation")); // 征信
				dataMap.put("callLog", datamap.get("callLog")); // 通话详单
				// dataMap.put("mailBill", datamap.get("mailBill")); //邮箱账单
				dataMap.put("taoBao", datamap.get("TaoBao")); // 淘宝
				dataMap.put("CompanyEmal", datamap.get("CompanyEmal")); // 公司邮箱
				dataMap.put("bankBillFlow", datamap.get("bankBillFlow")); // 信用卡
				dataMap.put("savings", datamap.get("savings"));              //savings    储蓄卡证明 
				result.put("data", dataMap);
			} else {
				dataMap.put("CHSI", 1); // 学信网
				dataMap.put("socialSecurity", 1); // 社保
				dataMap.put("accumulationFund", 1); // 公积金
				dataMap.put("creditInvestigation", 1); // 征信
				dataMap.put("callLog", 1); // 通话详单
				// dataMap.put("mailBill", 1); //邮箱账单
				dataMap.put("taoBao", 1); // 淘宝
				dataMap.put("CompanyEmal", 1); // 公司邮箱
				dataMap.put("bankBillFlow", 1); // 信用卡
				dataMap.put("savings", 1);              //savings    储蓄卡证明 
				result.put("data", dataMap);
			}
			result.put("errorInfo", "实名认证 !!");
			result.put("errorCode", 0);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("==",e);
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("认证状态使用时间：" + (endTime - startTime));
		}
		logger.info("认证状态:" + result);

		return result;
	}

}
