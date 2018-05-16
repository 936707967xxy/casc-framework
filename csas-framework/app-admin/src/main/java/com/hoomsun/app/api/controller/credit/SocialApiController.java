package com.hoomsun.app.api.controller.credit;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.app.api.enums.IpUrlEnum;
import com.hoomsun.app.api.help.HttpClientController;
import com.hoomsun.app.api.model.HeigeCityModel;
import com.hoomsun.app.api.model.ProvincesHege;
import com.hoomsun.app.api.model.Social;
import com.hoomsun.app.api.server.inter.ProvincesHegeServerI;
import com.hoomsun.app.api.server.inter.SocialServerI;
import com.hoomsun.common.util.HttpClientUtil;
import com.hoomsun.csas.sales.api.model.NameAuthentication;
import com.mysql.fabric.xmlrpc.base.Array;


/**
 * 
 * @author 作者：liudongliang <br>
 * @Date 创建时间：2017年12月05日 <br>
 * @Description 描述： 社保接口查询 
 *              http://192.168.3.19:8080/app-admin/web/socialapi/hegeprovince.do               所有省
 *              http://192.168.3.19:8080/app-admin/web/socialapi/logintype.do?province_id=     登陆方式/数据来源
 *              http://192.168.3.19:8080/app-admin/web/socialapi/getsocialapi.do?uniqueKey=    爬虫接口值
 *              http://192.168.3.19:8080/app-admin/web/socialapi/logincode.do?login_type=&unique_key&login_name  验证码
 *              http://192.168.3.19:8080/app-admin/web/socialapi/logincheck.do?params=&login_type=&unique_key    授权码
 *              http://192.168.3.19:8080/app-admin/web/socialapi/getquery.do?query_code=&cardNumber              获取数据
 */
@Controller
@RequestMapping("web/socialapi")
public class SocialApiController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public static String apix_key = "91a1f130c7fb400b4f2a12e773f62d61";

	private static IpUrlEnum ip = IpUrlEnum.DATA_IP;
	
	private static String shebaoUrl = ip.getIpUrl()+  "/HSDC/person/socialSecurityInsert";

	private static String statusurl = ip.getIpUrl()+"/HSDC/authcode/AutherizedOffline";
	
	

	@Autowired
	private ProvincesHegeServerI provincesHegeServer;

	@Autowired
	private SocialServerI socialServerI;
	
	
	
	/**
	 * @Description    获取所有支持省 2017-10-26 刘栋梁
	 * @param 
	 * @return  Map
	 * @Date    2017-12-04
	 * @LoggerAnnotation(moduleName = "社保接口", option = "获取所有支持省 ")
	 */	
	@RequestMapping("hegeprovince.do")
	@ResponseBody
	public JSONObject hegeProvince(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		JSONObject jsonObject = new JSONObject();
		try {
			List<ProvincesHege> list = provincesHegeServer.selectStoreCitysData(); // 现在只查询含有门店的省份
			jsonObject.put("errorInfo", "查询成功");
			jsonObject.put("errorCode", 0);
			jsonObject.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("errorInfo", "查询失败");
			jsonObject.put("errorCode", 1);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("获取所有支持省：" + (endTime - startTime));
		}
		logger.info("获取所有支持省:" + jsonObject.toJSONString());

		return jsonObject;
	}
	
	
	/**
	 * 获取登陆方式 2017-09-11 1.1 刘栋梁
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	 * @LoggerAnnotation(moduleName = "社保接口", option = "获取登陆方式")
	 */
	
	@RequestMapping("logintype.do")
	@ResponseBody
	public Map<String, Object>  loginType(HttpServletRequest request) throws IOException {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String province_Id = request.getParameter("province_id");
		Map<String, Object> result = new HashMap<String, Object>();
		List<HeigeCityModel>  data=new ArrayList<HeigeCityModel>();
		
        List<Social>  list=socialServerI.selectByProvince(province_Id);
        String url = "http://e.apix.cn/apixanalysis/shebao/cities?apix-key=" + apix_key + "&province_id=" + province_Id;
        String jasonStr = HttpClientController.check(url);
        Map<String, Object> heigeCitys=JSON.parseObject(jasonStr);	
        List<HeigeCityModel> heigelist= JSONArray.parseArray(heigeCitys.get("data").toString(), HeigeCityModel.class);
        
        for(Social social:list){
        	HeigeCityModel   model=new HeigeCityModel();
        	//如果是0 ----   数据来源是爬虫
        	if("0".equals(social.getCrawlerSign())){
        		List<String> loginType = new ArrayList<String>();
        		loginType.add(social.getLoginType());
        		model.setCity( social.getCity());
        		model.setUniqueKey( social.getUniqueKey());
        		model.setProvince(social.getProvince());
        		model.setLoginType(loginType);
        		model.setNote(social.getNote());
        		model.setCrawlerSign("0");
        		//isSupport 默认值
        	}else if("1".equals(social.getCrawlerSign())){
        		for(HeigeCityModel  heige:heigelist){
        			//查找对应的第三方数据 
        			if(social.getUniqueKey().equals(heige.getUniqueKey())){
        				model=heige;
        				model.setCrawlerSign("1");
        			}
        		}	
        	}
        	data.add(model);
        }
        result.put("errorCode", 0);
        result.put("errorInfo", "数据查询成功！！ ");
        result.put("data", data);
		long endTime=System.currentTimeMillis(); //获取结束时间  
		logger.info("获取登陆方式："+(endTime-startTime));  
		return result;
	}
	
    /***************  爬虫接口获取  ******************/
	
	/**
	 * 获取爬虫接口    2017-12-06    1.1 刘栋梁
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	 * @LoggerAnnotation(moduleName = "社保接口", option = "获取登陆方式")
	 */
	
	@RequestMapping("getsocialapi.do")
	@ResponseBody
	public Map<String, Object>  getSocialApi(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String uniqueKey = request.getParameter("uniqueKey");
		Map<String, Object> result = new HashMap<String, Object>();		
		try {
			Social  social=socialServerI.selectApiByUniqueKey(uniqueKey);	
			result.put("errorCode", 0);
	        result.put("errorInfo", "数据查询成功！！ ");
	        result.put("data", social);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			// 获取结束时间
			long endTime = System.currentTimeMillis();
			logger.info("获取爬虫接口使用时间：" + (endTime - startTime));
		}
		logger.info("客户是否已注册:" + result);
		return result;
	}
	
	
	
	/***************  第三方数据获取步骤  ******************/
	
	/**
	 * 获取社保验证码 2017-09-11 1.1 刘栋梁
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("logincode.do")
	@ResponseBody
	public JSONObject loginCode(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String login_type = request.getParameter("login_type");
		String unique_key = request.getParameter("unique_key");
		String login_name = request.getParameter("login_name");
		String str = "";
		try {
			String url = "http://e.apix.cn/apixanalysis/shebao/capcha?apix-key=" + apix_key + "&login_type=" + login_type + "&unique_key=" + unique_key + "&login_name=" + login_name;
			str = HttpClientController.check(url);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("获取社保验证码 ：" + (endTime - startTime));
		}
		logger.info("获取社保验证码 :" + str);
		JSONObject jsonObject = JSON.parseObject(str);
		return jsonObject;
	}

	/**
	 * 社保密码授权登陆 2017-09-11 1.1 刘栋梁
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("logincheck.do")
	@ResponseBody
	public JSONObject loginCheck(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String login_type = request.getParameter("login_type");
		String unique_key = request.getParameter("unique_key");
		String params = request.getParameter("params");

		String str = "";
		try {
			// params = new String(params.getBytes("ISO-8859-1"), "UTF-8");
			logger.info("================" + params);
			params = URLEncoder.encode(params, "UTF-8");
			logger.info("================" + params);
			String url = "http://e.apix.cn/apixanalysis/shebao/login?apix-key=" + apix_key + "&login_type=" + login_type + "&unique_key=" + unique_key + "&params=" + params;
			str = HttpClientController.check(url);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info(" 社保密码授权登陆：" + (endTime - startTime));
		}
		logger.info(" 社保密码授权登陆:" + str);
		JSONObject jsonObject = JSON.parseObject(str);
		return jsonObject;
	}

	/**
	 * 社保密码授权登陆 2017-09-11 1.1 刘栋梁
	 * 
	 * @param request
	 * @return
	 * @LoggerAnnotation(moduleName = "社保接口", option = "根据token查原始数据")
	 */
	
	@RequestMapping("getquery.do")
	@ResponseBody
	public JSONObject getQuery(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String query_code = request.getParameter("query_code");
		String cardNumber = request.getParameter("cardNumber");
		IpUrlEnum ip = IpUrlEnum.DATA_IP;
		
		String str = "";
		String result = "";
		try {
			// -------------添加认证中状态
			System.out.println("公积金数据推" + cardNumber);
			// 填入各个表单域的值
			Map<String, Object> statudata=new HashMap<String, Object>();
			statudata.put("cardNumber", cardNumber);
			statudata.put("approveName", "socialSecurity");
			statudata.put("approveState", "100");
			String statString = HttpClientUtil.doPost(statusurl, null, null, statudata);
			
			// -------------数据推送接口
			String errorCode = "30003";
			int i=0;
			do {
				i++;
				Thread.sleep(10000);
				String url = "http://e.apix.cn/apixanalysis/shebao/query?apix-key=" + apix_key + "&query_code=" + query_code;
				str = HttpClientController.check(url);
				logger.info("result1=" + str);
				Map   document = JSON.parseObject(str);
				errorCode = document.get("errorCode") + "";
				logger.info("根据token查询原始数据document=" + document);
			} while (errorCode.equals("30003")     &&   i<=15);
			
			
			// 填入各个表单域的值
			Map<String, Object> data=new HashMap<String, Object>();
			data.put("data", str);
			data.put("cardNumber", cardNumber);
			result = HttpClientUtil.doPost(shebaoUrl, null, null, data);

			// -------------添加认证完成状态
			JSONObject resultObject = JSON.parseObject(result);
			if (!"0000".equals(resultObject.get("errorCode"))) {
				Map<String, Object> statudataTwo=new HashMap<String, Object>();
				statudataTwo.put("cardNumber", cardNumber);
				statudataTwo.put("approveName", "socialSecurity");
				statudataTwo.put("approveState", "200");
				String statusTwoString = HttpClientUtil.doPost(statusurl, null, null, statudataTwo);
			} else {
				Map<String, Object> statudataTwo=new HashMap<String, Object>();
				statudataTwo.put("cardNumber", cardNumber);
				statudataTwo.put("approveName", "socialSecurity");
				statudataTwo.put("approveState", "300");
				String statusTwoString = HttpClientUtil.doPost(statusurl, null, null, statudataTwo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("社保数据 查询：根token查：" + (endTime - startTime));
		}
		logger.info("社保数据 查询：根token查:" + str);

		JSONObject jsonObject = JSON.parseObject(result);
		return jsonObject;
	}
	
}
