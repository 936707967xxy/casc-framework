package com.hoomsun.app.api.controller.credit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.app.api.enums.IpUrlEnum;
import com.hoomsun.app.api.model.hsCall;
import com.hoomsun.app.api.server.inter.hsCallServerI;
import com.hoomsun.app.api.util.HttpURLConection;

@Controller
@RequestMapping("web/call")
public class CallController {
	Logger logger = Logger.getLogger(CallController.class);
	
	private static IpUrlEnum ip = IpUrlEnum.DATA_IP;

	private static String url = ip.getIpUrl() + "/HSDC/";
	
	@Autowired
	private hsCallServerI hsCallServer;

	/**
	 * 是否开启第三方通话详单获取
	 * 
	 * @param request
	 * @param phone
	 * @return
	 * @LoggerAnnotation(moduleName = "通话相关", option = "是否开启第三方通话详单获取")
	 */
	
	@RequestMapping("isopen.do")
	@ResponseBody
	public JSONObject isOpen(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		JSONObject jsonObject = new JSONObject();

		hsCall hscall = hsCallServer.selectByPrimaryKey("0010d4fe578d4e1eafddb8a1f412cfc4");
		try {
			jsonObject.put("errorInfo", "查询成功");
			jsonObject.put("errorCode", 0);
			jsonObject.put("data", hscall.getOponval());
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("errorInfo", "数据异常！！");
			jsonObject.put("errorCode", 1001);
		} finally {

			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("是否开启第三方通话详单获取：" + (endTime - startTime));
		}
		logger.info("是否开启第三方通话详单获取:" + jsonObject.toJSONString());

		return jsonObject;
	}

	/**
	 * 获取登录方式
	 * 
	 * @param request
	 * @param phone
	 * @return
	 * @LoggerAnnotation(moduleName = "通话相关", option = "获取登录方式")
	 */
	
	@RequestMapping("loginWay.do")
	@ResponseBody
	public JSONObject getH5Page(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String phone = request.getParameter("phone");
		String str = "";
		try {

			str = sendGet("http://e.apix.cn/apixanalysis/mobile/yys/phone/capcha?phone_no=" + phone);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("获取登陆方式：" + (endTime - startTime));
		}
		logger.info("获取登陆方式:" + str);
		JSONObject jsonObject = JSON.parseObject(str);
		return jsonObject;
	}

	/**
	 * 运营商登录
	 * 
	 * @param request
	 * @param phone
	 * @return
	 * @LoggerAnnotation(moduleName = "通话相关", option = "运营商登录")
	 */
	
	@RequestMapping("register.do")
	@ResponseBody
	public JSONObject register(HttpServletRequest request) {
		// Document result=new Document();
		/*
		 * log.info("运营商登录---------------"); log.info("拿到的手机号码是="+phone);
		 * log.info("拿到的密码是="+passwd); log.info("拿到的验证码是="+capcha); String
		 * url1="http://e.apix.cn/apixanalysis/mobile/yys/phone/login?phone_no="
		 * +phone+"&passwd="+passwd+"&capcha="+capcha; String result1
		 * =sendGet(url1); log.info("运营商登录url="+url1); //JSONObject object =
		 * JSONObject.fromObject(result); Document
		 * document=Document.parse(result1);
		 * log.info("运营商登录document="+document); return document;
		 */
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String phone = request.getParameter("phone");
		String passwd = request.getParameter("passwd");
		String capcha = request.getParameter("capcha");
		logger.info("运营商登录---------------");
		logger.info("拿到的手机号码是=" + phone);
		logger.info("拿到的密码是=" + passwd);
		logger.info("拿到的验证码是=" + capcha);
		String str = "";
		try {
			String url1 = "http://e.apix.cn/apixanalysis/mobile/yys/phone/login?phone_no=" + phone + "&passwd=" + passwd + "&capcha=" + capcha;
			str = sendGet(url1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("运营商登录 ：" + (endTime - startTime));
		}
		logger.info("运营商登录 :" + str);
		JSONObject jsonObject = JSON.parseObject(str);
		return jsonObject;
	}

	/**
	 * 获取详单图片验证码
	 * 
	 * @param request
	 * @param phone
	 * @return
	 * @LoggerAnnotation(moduleName = "通话相关", option = "获取详单图片验证码")
	 */
	
	@RequestMapping("imageCode.do")
	@ResponseBody
	public JSONObject imageCode(HttpServletRequest request) {
		/*
		 * //Document result=new Document();
		 * log.info("获取详单图片验证码---------------"); log.info("拿到的手机号码是="+phone);
		 * String result1 =sendGet(
		 * "http://e.apix.cn/apixanalysis/mobile/yys/phone/third/image?phone_no="
		 * +phone); //JSONObject object = JSONObject.fromObject(result);
		 * Document document=Document.parse(result1);
		 * log.info("获取详单图片验证码document="+document); return document;
		 */
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String phone = request.getParameter("phone");
		logger.info("获取详单图片验证码拿到的手机号码是=" + phone);
		String str = "";
		try {
			str = sendGet("http://e.apix.cn/apixanalysis/mobile/yys/phone/third/image?phone_no=" + phone);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("获取详单图片验证码 ：" + (endTime - startTime));
		}
		logger.info("获取详单图片验证码 :" + str);
		JSONObject jsonObject = JSON.parseObject(str);
		return jsonObject;
	}

	/**
	 * 请求短信验证码授权
	 * 
	 * @param request
	 * @param phone
	 * @return
	 * @LoggerAnnotation(moduleName = "通话相关", option = "请求短信验证码授权")
	 */
	
	@RequestMapping("noteCode.do")
	@ResponseBody
	public JSONObject noteCode(HttpServletRequest request) {
		/*
		 * //Document result=new Document();
		 * log.info("请求短信验证码授权---------------"); log.info("拿到的手机号码是="+phone);
		 * String result1 =sendGet(
		 * "http://e.apix.cn/apixanalysis/mobile/yys/phone/smsCode/getSms?phone_no="
		 * +phone); //JSONObject object = JSONObject.fromObject(result);
		 * Document document=Document.parse(result1);
		 * log.info("请求短信验证码授权document="+document); return document;
		 */
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String phone = request.getParameter("phone");
		logger.info("请求短信验证码授权拿到的手机号码是=" + phone);
		String str = "";
		try {
			str = sendGet("http://e.apix.cn/apixanalysis/mobile/yys/phone/smsCode/getSms?phone_no=" + phone);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("请求短信验证码授权 ：" + (endTime - startTime));
		}
		logger.info("请求短信验证码授权 :" + str);
		JSONObject jsonObject = JSON.parseObject(str);
		return jsonObject;
	}

	/**
	 * 详单短信验证码验证
	 * 
	 * @param request
	 * @param phone
	 * @return
	 * @LoggerAnnotation(moduleName = "通话相关", option = "通话详单短信验证码验证")
	 */
	
	@RequestMapping("verifyNoteCode.do")
	@ResponseBody
	public JSONObject verifyNoteCode(HttpServletRequest request) {
		// Document result=new Document();
		/*
		 * log.info("详单短信验证码验证---------------"); log.info("拿到的手机号码是="+phone);
		 * log.info("拿到的验证码是="+sms_code); log.info("拿到的姓名是="+name);
		 * log.info("拿到的身份证是="+cert_no); log.info("拿到的图形验证码是="+capcha);
		 * //JSONObject object = JSONObject.fromObject(result); Document
		 * document=null; try { String requestUrl=
		 * "http://e.apix.cn/apixanalysis/mobile/yys/phone/smsCode/verify?phone_no="
		 * +phone+"&sms_code="+sms_code; if(!name.equals("")){ name=
		 * URLEncoder.encode(name, "UTF-8");
		 * requestUrl=requestUrl+"&name="+name; } if(!cert_no.equals("")){
		 * requestUrl=requestUrl+"&cert_no="+cert_no; } if(!capcha.equals("")){
		 * requestUrl=requestUrl+"&capcha="+capcha; }
		 * log.info("请求地址是="+requestUrl); String result1 =sendGet(requestUrl);
		 * document = Document.parse(result1);
		 * System.out.println("详单短信验证码验证document="+document); } catch
		 * (UnsupportedEncodingException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } return document;
		 */
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String phone = request.getParameter("phone");
		String sms_code = request.getParameter("sms_code");
		String name = request.getParameter("name");
		String cert_no = request.getParameter("cert_no");
		String capcha = request.getParameter("capcha");
		logger.info("详单短信验证码验证---------------");
		logger.info("拿到的手机号码是=" + phone);
		logger.info("拿到的验证码是=" + sms_code);
		logger.info("拿到的姓名是=" + name);
		logger.info("拿到的身份证是=" + cert_no);
		logger.info("拿到的图形验证码是=" + capcha);
		String str = "";
		try {
			String requestUrl = "http://e.apix.cn/apixanalysis/mobile/yys/phone/smsCode/verify?phone_no=" + phone + "&sms_code=" + sms_code;
			if (!name.equals("")) {
				name = URLEncoder.encode(name, "UTF-8");
				requestUrl = requestUrl + "&name=" + name;
			}
			if (!cert_no.equals("")) {
				requestUrl = requestUrl + "&cert_no=" + cert_no;
			}
			if (!capcha.equals("")) {
				requestUrl = requestUrl + "&capcha=" + capcha;
			}
			// str = HttpClientController.check(requestUrl);
			str = sendGet(requestUrl);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("详单短信验证码验证 ：" + (endTime - startTime));
		}
		logger.info("详单短信验证码验证 :" + str);
		JSONObject jsonObject = JSON.parseObject(str);
		return jsonObject;
	}

	/**
	 * 根据token查询原始数据
	 * 
	 * @param request
	 * @param phone
	 * @return
	 * @LoggerAnnotation(moduleName = "通话相关", option = "根据token查询原始数据")
	 */
	
	@RequestMapping("callData.do")
	@ResponseBody
	public JSONObject callData(HttpServletRequest request) {
		// Document result=new Document();
		/*
		 * System.out.println("根据token查询原始数据---------cardNumber------");
		 * System.out.println("拿到的手机号码是="+phone);
		 * System.out.println("拿到的token是="+token);
		 * System.out.println("拿到的身份证是="+cardNumber); Map<String,String>
		 * statusMap=new HashMap<String, String>(); statusMap.put("cardNumber",
		 * cardNumber); statusMap.put("approveName", "callLog");
		 * statusMap.put("approveState", "100");
		 * HttpURLConection.sendPost(statusMap,
		 * url+"authcode/AutherizedOffline"); //根据手机号判断运营商类型 String
		 * operator="移动"; String resultType
		 * =sendGetType("http://a.apix.cn/apixlife/phone/phone?phone="+phone);//
		 * 请求接口地址 Document address = Document.parse(resultType);
		 * if(address!=null){ String error_code =
		 * address.get("error_code").toString(); if(error_code.equals("0")){
		 * Map<String,Object> addresMap =(Map<String, Object>)
		 * address.get("data"); operator=addresMap.get("operator").toString(); }
		 * } Document result=new Document(); Document document=null; try {
		 * String status="0"; do { Thread.sleep(8000); String result1 =sendGet(
		 * "http://e.apix.cn/apixanalysis/mobile/retrieve/phone/data/query?query_code="
		 * +token); log.info("result1="+result1);
		 * document=Document.parse(result1); status=document.get("status")+"";
		 * System.out.println("根据token查询原始数据document="+document); } while
		 * (status.equals("0")); //JSONObject object =
		 * JSONObject.fromObject(result); document.append("passwd",passwd);
		 * document.append("phone",phone); //推送数据到数据中心 Map<String, String>
		 * par=new HashMap<String, String>(); par.put("data",
		 * document.toJson()); String back=null; if(operator.indexOf("移动")>-1){
		 * back=HttpURLConection.sendPost(par,
		 * url+"message/HGmobileCallRecord"); }else
		 * if(operator.indexOf("联通")>-1){ back=HttpURLConection.sendPost(par,
		 * url+"message/HGlinkCallRecord"); }else{//电信
		 * back=HttpURLConection.sendPost(par,
		 * url+"message/HGtelecomCallRecord"); } if(back!=null){ Document
		 * backDoc=Document.parse(back);
		 * if(backDoc.get("errorCode").equals("0000")){
		 * result.append("errorCode", "0000"); result.append("errorMsg", "成功");
		 * statusMap.put("approveState", "300"); }else{
		 * result.append("errorCode", "1111");
		 * result.append("errorMsg",backDoc.get("errorInfo"));
		 * statusMap.put("approveState", "200"); } }else{
		 * result.append("errorCode", "1111"); result.append("errorMsg",
		 * "数据异常"); statusMap.put("approveState", "200"); }
		 * HttpURLConection.sendPost(statusMap,
		 * url+"authcode/AutherizedOffline"); } catch (Exception e) { // TODO
		 * Auto-generated catch block e.printStackTrace();
		 * result.append("errorCode", "1111"); result.append("errorMsg",
		 * "数据异常"); statusMap.put("approveName", "200");
		 * HttpURLConection.sendPost(statusMap,
		 * url+"authcode/AutherizedOffline"); } return result;
		 */
		String phone = request.getParameter("phone");
		String token = request.getParameter("token");
		String passwd = request.getParameter("passwd");
		String cardNumber = request.getParameter("cardNumber");
		String longitude = request.getParameter("longitude"); // 精度
		String latitude = request.getParameter("latitude"); // 纬度
		logger.info(longitude + "=========----------" + latitude);
		Map<String, String> statusMap = new HashMap<String, String>();
		statusMap.put("cardNumber", cardNumber);
		statusMap.put("approveName", "callLog");
		statusMap.put("approveState", "100");
		HttpURLConection.sendPost(statusMap, url + "authcode/AutherizedOffline");

		JSONObject document = null;
		JSONObject result = new JSONObject();

		try {
			// 根据手机号判断运营商类型
			String operator = "移动";
			String resultType = sendGetType("http://a.apix.cn/apixlife/phone/phone?phone=" + phone);// 请求接口地址
			JSONObject address = JSON.parseObject(resultType);
			if (address != null) {
				String error_code = address.get("error_code").toString();
				if (error_code.equals("0")) {
					Map<String, Object> addresMap = (Map<String, Object>) address.get("data");
					operator = addresMap.get("operator").toString();
				}
			}

			String status = "0";
			do {
				Thread.sleep(8000);
				String result1 = sendGet("http://e.apix.cn/apixanalysis/mobile/retrieve/phone/data/query?query_code=" + token);
				logger.info("result1=" + result1);
				document = JSON.parseObject(result1);
				status = document.get("status") + "";
				logger.info("根据token查询原始数据document=" + document);
			} while (status.equals("0"));
			// JSONObject object = JSONObject.fromObject(result);
			document.put("passwd", passwd);
			document.put("phone", phone);
			if (longitude == null || latitude == null) {
				longitude = "";
				latitude = "";
			}
			document.put("longitude", longitude);
			document.put("latitude", latitude);

			// 推送数据到数据中心
			Map<String, String> par = new HashMap<String, String>();
			par.put("data", document.toString());
			String back = null;
			if (operator.indexOf("移动") > -1) {
				back = HttpURLConection.sendPost(par, url + "message/HGmobileCallRecord");
			} else if (operator.indexOf("联通") > -1) {
				back = HttpURLConection.sendPost(par, url + "message/HGlinkCallRecord");
			} else {// 电信
				back = HttpURLConection.sendPost(par, url + "message/HGtelecomCallRecord");
			}
			if (back != null) {
				JSONObject backDoc = JSON.parseObject(back);
				if (backDoc.get("errorCode").equals("0000")) {
					result.put("errorCode", "0000");
					result.put("errorMsg", "成功");
					statusMap.put("approveState", "300");
				} else {
					result.put("errorCode", "1111");
					result.put("errorMsg", backDoc.get("errorInfo"));
					statusMap.put("approveState", "200");
				}
			} else {
				result.put("errorCode", "1111");
				result.put("errorMsg", "数据异常");
				statusMap.put("approveState", "200");
			}
			HttpURLConection.sendPost(statusMap, url + "authcode/AutherizedOffline");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("errorCode", "1111");
			result.put("errorMsg", "数据异常");
			statusMap.put("approveName", "200");
			HttpURLConection.sendPost(statusMap, url + "authcode/AutherizedOffline");
		}
		return result;

	}

	/*
	 * 运营商通话详单get请求
	 */
	public static String sendGet(String src) {
		try {
			URL url = new URL(src); // 把字符串转换为URL请求地址
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
			// connection.setRequestProperty("Charset", "UTF-8");
			connection.addRequestProperty("apix-key", "9be6831078d54f5f500e285d5fcf542a");
			connection.connect();// 连接会话
			// 获取输入流
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {// 循环读取流
				sb.append(line);
			}
			br.close();// 关闭流
			connection.disconnect();// 断开连接
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("失败!");
		}
		return null;
	}

	/**
	 * 运营商类型确认接口
	 */
	public static String sendGetType(String src) {
		try {
			URL url = new URL(src); // 把字符串转换为URL请求地址
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
			connection.addRequestProperty("apix-key", "bf0bfb8730cb43ea5f76e53c79e6b063");
			connection.connect();// 连接会话
			// 获取输入流
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {// 循环读取流
				sb.append(line);
			}
			br.close();// 关闭流
			connection.disconnect();// 断开连接
			System.out.println(sb.toString());
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("失败!");
		}
		return null;
	}

}