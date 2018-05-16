package com.hoomsun.app.api.controller.credit;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.app.api.enums.ApiEnum;
import com.hoomsun.app.api.enums.IpUrlEnum;
import com.hoomsun.app.api.model.AfreshCareerInfo;
import com.hoomsun.app.api.model.AfreshContacterInfo;
import com.hoomsun.app.api.model.AfreshProtoinfo;
import com.hoomsun.app.api.server.inter.AfreshCareerInfoServerI;
import com.hoomsun.app.api.server.inter.AfreshContacterInfoMapperServerI;
import com.hoomsun.app.api.server.inter.AfreshProtoinfoServerI;
import com.hoomsun.common.util.HttpClientUtil;
import com.hoomsun.csas.sales.api.model.NameAuthentication;
import com.hoomsun.csas.sales.api.server.inter.NameAuthenticationServerI;


/**
 * 
 * @author 刘栋梁
 * @date 2017-08-15
 * @resource 信息获取接口 
 *           1. 个人信息
 *           192.168.3.19:8080/app-admin/web/msg/getpersonmsg.do?ID=&Type= 
 *           2.数据中心 
 *           192.168.3.19:8080/app-admin/web/msg/getdatamsg.do?idcode=&Type=phone=&Type= 
 *           3.取所有认证银行(信用卡 )信息
 *           192.168.3.19:8080/app-admin/web/msg/getallbankmsg.do?idcode=
 *           4.判断储蓄卡是否认证 
 *           192.168.3.19:8080/app-admin/web/msg/judgesavings.do?cardNumber=&bankNumber
 *           5.储蓄卡判断是否推送状态 
 *           192.168.3.19:8080/app-admin/web/msg/approvesavings.do?cardNumber=
 *           
 *           
 */

@RequestMapping("web/msg")
@Controller
public class MsgController {

	private static IpUrlEnum ip=IpUrlEnum.DATA_IP;
	
	private static String findCallurl = ip.getIpUrl()+ApiEnum.FINDCALLURL.getApiUrl(); // 通话详单
																			

	private static String findCHSIurl = ip.getIpUrl()+ApiEnum.FINDCHSIURL.getApiUrl(); // 学信接口
																			

	private static String socialSecurityurl =ip.getIpUrl()+ ApiEnum.SOCIALSECURITYURL.getApiUrl(); // 社保

	private static String accumulationFundurl = ip.getIpUrl()+ApiEnum.ACCUMULATIONFUNDURL.getApiUrl(); // 根据身份证获取公积金
																							

	private static String creditInvestigationurl = ip.getIpUrl()+ApiEnum.CREDITINVESTIGATIONURL.getApiUrl(); // 根据身份证获取征信记录
																									

	private static String findEmailurl =ip.getIpUrl()+ ApiEnum.FINDEMAILURL.getApiUrl(); // 查邮箱
																				

	private static String findTaobaourl = ip.getIpUrl()+ApiEnum.FINDTAOBAOURL.getApiUrl(); // 查淘宝
																				

	private static String findCompanyEmaiurl = ip.getIpUrl()+ApiEnum.FINDCOMPANYEMAIURL.getApiUrl(); // 查公司邮箱
																							

	private static String GetbankBillFlowByidcard = ip.getIpUrl()+ApiEnum.GETBANKBILLFLOWBYIDCARD.getApiUrl(); // 查行用卡账单
																									

	private static String GetallBankBillFlowByidcard =ip.getIpUrl()+ ApiEnum.GETALLBANKBILLFLOWBYIDCARD.getApiUrl(); // 查行用卡账单
	 
	private static String getallDepBankByidcard =ip.getIpUrl()+ ApiEnum.GETALLDEPBANKBYIDCARD.getApiUrl(); // 查行所有储蓄卡信息  (好像作废)  
	
	private static String getallDepBankSavings  =ip.getIpUrl()+ ApiEnum.GETALLDEPBANKSAVINGS.getApiUrl(); // 查行所有储蓄卡流水信息    
																											
	private static String getJudgeSavings  =ip.getIpUrl()+ ApiEnum.GETJUDGESAVINGS.getApiUrl(); // 查询储蓄卡是否认证 
	
	private static String getApproveSavings  =ip.getIpUrl()+ ApiEnum.GETAPPROVESAVINGS.getApiUrl(); // 储蓄卡判断是否推送状态接 


	@Autowired
	private AfreshCareerInfoServerI AfreshCareerServer;

	@Autowired
	private AfreshContacterInfoMapperServerI AfreshContacterServer;

	@Autowired
	private AfreshProtoinfoServerI ProtoinfoServerI;

	@Autowired
	private NameAuthenticationServerI NameAuthentServer;

	private Logger logger = LoggerFactory.getLogger(getClass());

	
	/**
	 * @author        作者：liudongliang <br>
	 * @Description   获取个人信息
	 * @param phone
	 * @return Map
	 * @Date 2017-12-04
	 * @LoggerAnnotation(moduleName = "信息获取接口", option = "获取个人信息")
	 */
	
	@RequestMapping(value = "getpersonmsg.do")
	@ResponseBody
	public Map<String, Object> getMsg(HttpServletRequest req) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Map<String, Object> result = new HashMap<String, Object>();
		// 数据类型
		String Type = req.getParameter("Type");
		try {
			if (Type.equals("contacterInfo")) {
				logger.info("获取联系人信息 ");
				String fk_id = req.getParameter("ID");
				List<AfreshContacterInfo> ContacterInfo = AfreshContacterServer.selectByFkid(fk_id);
				result.put("data", ContacterInfo);
				result.put("errorInfo", "联系人信息获取成功！！");
				result.put("errorCode", "0000");
			} else if (Type.equals("careerinfo")) {
				logger.info("获取单位信息 ");
				String fk_id = req.getParameter("ID");
				AfreshCareerInfo CareerInfo = AfreshCareerServer.selectByFkid(fk_id);
				result.put("data", CareerInfo);
				result.put("errorInfo", "获取单位信息！！");
				result.put("errorCode", "0000");
			} else if (Type.equals("protoinfo")) {
				logger.info("获取银行卡信息 ");
				String fk_id = req.getParameter("ID");
				List<AfreshProtoinfo>  Protoinfo = ProtoinfoServerI.selectByFkid(fk_id);
				result.put("data", Protoinfo);
				result.put("errorInfo", "获取银行卡信息 ！！");
				result.put("errorCode", "0000");
			} else if (Type.equals("zhimainfo")) {
				logger.info("获取芝麻信息 ");
				String id = req.getParameter("ID");
				NameAuthentication nameAuth = NameAuthentServer.selectByPrimaryKey(id);
				Map<String, Object> loginMap = new HashMap<String, Object>();
				if (loginMap != null) {
					loginMap.put("CUSTNAME", nameAuth.getCustname());
					loginMap.put("SESAME_GRADEVAL", nameAuth.getSesameGradeval());
					loginMap.put("SESAME_TIME", nameAuth.getSesameTime());
					result.put("data", loginMap);
				} else {
					result.put("data", new HashMap<String, Object>());
				}
				result.put("errorInfo", "获取芝麻信息 ！！");
				result.put("errorCode", "0000");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "数据查询失败！");
			result.put("errorCode", "1111");
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("获取个人信息界面数据：" + (endTime - startTime));
		}
		return result;
	}

	/**
	 * @author        作者：liudongliang <br>
	 * @Description   获取数据认证的信息
	 * @param phone
	 * @return Map
	 * @Date 2017-12-04
	 * @LoggerAnnotation(moduleName = "信息获取接口", option = "获取个人信息")
	 */
	@RequestMapping(value = "getdatamsg.do")
	@ResponseBody
	public JSONObject getDataMsg(HttpServletRequest req) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String result = null;
		// 数据类型
		String Type = req.getParameter("Type");
		try {
			if (Type.equals("findCallurl")) {
				logger.info("获取联系人信息 ");
				// 手机号
				String phone = req.getParameter("phone");
				result = dataMsg(findCallurl, "phone", phone);
			} else if (Type.equals("socialSecurityurl")) {
				logger.info("获取社保信息 ");
				// 身份证
				String idcode = req.getParameter("idcode");
				result = dataMsg(socialSecurityurl, "idcode", idcode);
			} else if (Type.equals("accumulationFundurl")) {
				logger.info("根据身份证获取公积金    ");
				// 身份证
				String idcode = req.getParameter("idcode");
				result = dataMsg(accumulationFundurl, "idcode", idcode);
			} else if (Type.equals("creditInvestigationurl")) {
				logger.info("根据身份证获取征信记录    ");
				// 身份证
				String idcode = req.getParameter("idcode");
				result = dataMsg(creditInvestigationurl, "idcode", idcode);
			} else if (Type.equals("findCHSIurl")) {
				logger.info("根据身份证获取学信网   ");
				// 身份证
				String idcode = req.getParameter("idcode");
				result = dataMsg(findCHSIurl, "cardNumber", idcode);
			} else if (Type.equals("findEmailurl")) {
				logger.info("根据身份证获取邮箱   ");
				// 身份证
				String idcode = req.getParameter("idcode");
				result = dataMsg(findEmailurl, "cardNumber", idcode);
			} else if (Type.equals("findTaobaourl")) {
				logger.info("根据身份证获取淘宝   ");
				// 身份证
				String idcode = req.getParameter("idcode");
				result = dataMsg(findTaobaourl, "cardNumber", idcode);
			} else if (Type.equals("findCompanyEmaiurl")) {
				logger.info("根据身份证获取公司邮箱     ");
				// 身份证
				String idcode = req.getParameter("idcode");
				result = dataMsg(findCompanyEmaiurl, "cardNumber", idcode);
			} else if (Type.equals("GetbankBillFlowByidcard")) {
				logger.info("根据身份证获取信用卡帐单     ");
				// 身份证
				String idcode = req.getParameter("idcode");
				result = dataMsg(GetbankBillFlowByidcard, "idcode", idcode);
			}else if (Type.equals("getallDepBankByidcard")) {
				logger.info("根据身份证获取储蓄卡帐单--好像作废     ");
				// 身份证
				String idcode = req.getParameter("idcode");
				result = dataMsg(getallDepBankByidcard, "cardNumber", idcode);
			}else if(Type.equals("getallDepBankSavings")){
				logger.info("根据身份证获取储蓄卡帐单 ---新    ");
				// 身份证
				String idcode = req.getParameter("idcode");
				result = dataMsg(getallDepBankSavings, "cardNumber", idcode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("获取个人信息界面数据：" + (endTime - startTime));
		}
		JSONObject jsonObject = JSON.parseObject(result);
		return jsonObject;

	}

	/**
	 * @author        作者：liudongliang <br>
	 * @Description   获取所有认证银行信息
	 * @param phone
	 * @return Map
	 * @Date 2017-12-04
	 * @LoggerAnnotation(moduleName = "信息获取接口", option = "获取所有认证银行信息")
	 */
	
	@RequestMapping(value = "getallbankmsg.do")
	@ResponseBody
	public JSONObject getAllBankMsg(HttpServletRequest req) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String result = null;
		// 数据类型
		String idcode = req.getParameter("idcode");
		try {
			logger.info("获取所有认证银行信息 ");
			// 身份证
			result = dataMsg(GetallBankBillFlowByidcard, "idcode", idcode);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("获取所有认证银行信息：" + (endTime - startTime));
		}
		JSONObject jsonObject = JSON.parseObject(result);
		return jsonObject;

	}

	
	/**
	 * @author        作者：liudongliang <br>
	 * @Description   储蓄卡是否认证
	 * @param phone
	 * @return Map
	 * @Date 2018-01-31
	 * @LoggerAnnotation(moduleName = "信息获取接口", option = "储蓄卡是否认证")
	 */
	
	@RequestMapping(value = "judgesavings.do")
	@ResponseBody
	public JSONObject judgeSavings  (HttpServletRequest req) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String result = null;
		// 数据类型
		String cardNumber = req.getParameter("cardNumber");
		String bankNumber = req.getParameter("bankNumber");
		try {
			logger.info("储蓄卡是否认证 ");
			Map<String, Object> statudata=new HashMap<String, Object>();
			statudata.put("cardNumber", cardNumber);
			statudata.put("bankNumber",bankNumber);
			result = HttpClientUtil.doPost(getJudgeSavings, null, null, statudata);					

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("储蓄卡是否认证信息：" + (endTime - startTime));
		}
		JSONObject jsonObject = JSON.parseObject(result);
		return jsonObject;

	}
	
	
	/**
	 * @author        作者：liudongliang <br>
	 * @Description   储蓄卡判断是否推送状态接
	 * @param phone
	 * @return Map
	 * @Date 2018-01-31
	 * @LoggerAnnotation(moduleName = "信息获取接口", option = "储蓄卡判断是否推送状态接")
	 */
	
	@RequestMapping(value = "approvesavings.do")
	@ResponseBody
	public JSONObject approveSavings(HttpServletRequest req) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String result = null;
		// 数据类型
		String cardNumber = req.getParameter("cardNumber");
		try {
			logger.info("储蓄卡判断是否推送状态 ");
			Map<String, Object> statudata=new HashMap<String, Object>();
			statudata.put("cardNumber", cardNumber);
			result = HttpClientUtil.doPost(getApproveSavings, null, null, statudata);					

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("储蓄卡判断是否推送状态：" + (endTime - startTime));
		}
		JSONObject jsonObject = JSON.parseObject(result);
		return jsonObject;

	}
	
	public static String dataMsg(String url, String arg, String parmar) {

		String posturl = url; //
		PostMethod postMethod = new PostMethod(posturl);
		// 填入各个表单域的值
		NameValuePair[] paramdata = { new NameValuePair(arg, parmar) };
		String str = "";
		// 将表单的值放入postMethod�?commons-httpclient.jar
		postMethod.setRequestBody(paramdata);
		postMethod.getParams().setContentCharset("utf-8");
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		// 执行postMethod
		int statusCode = 0;
		try {
			statusCode = new HttpClient().executeMethod(postMethod);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转
		// 301或302
		if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
			// 从头中取出转向的地址
			Header locationHeader = postMethod.getResponseHeader("location");
			String location = null;
			if (locationHeader != null) {
				location = locationHeader.getValue();
			} else {
				System.err.println("Location field value is null.");
			}
			return null;
		} else {
			try {
				str = postMethod.getResponseBodyAsString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		postMethod.releaseConnection();

		return str;
	}

}
