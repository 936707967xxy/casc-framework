package com.hoomsun.app.api.controller.credit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.NameValuePair;
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
import com.hoomsun.app.api.model.BankInterface;
import com.hoomsun.app.api.server.inter.BankInterfaceServerI;


/**
 * 作者：刘栋梁 <br>
 * 创建时间：2017年9月18日 <br>
 * 描述：app-银行卡--爬虫接口管理查询 
 * 
 * 2.查询信用卡认证时间
 * http://192.168.3.19:8080/app-admin/web/bankinterface/getcrecardtime.do?cardNumber
 * 3. 数据中心银行图片 
 * http://192.168.3.19:8080/app-admin/web/bankinterface/bankurl.do
 * 4.开放的银行查询--信用卡
 * http://192.168.3.19:8080/app-admin/web/bankinterface/crebank.do?idcode=
 * 5.开放的银行查询--储蓄卡
 * http://192.168.3.19:8080/app-admin/web/bankinterface/depbank.do 
 * 
 */
@Controller
@RequestMapping("web/bankinterface")
public class BankApiController {

	@Autowired
	private BankInterfaceServerI bankInterfaceServer;

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private IpUrlEnum ip = IpUrlEnum.HSFS_IP;
	
	
	private  String crecardtimeurl=IpUrlEnum.DATA_IP.getIpUrl()+ ApiEnum.CRECARDTIMEURL.getApiUrl(); // 最高总额度
	
	private  String crecardt=IpUrlEnum.DATA_IP.getIpUrl()+ ApiEnum.CRECARDT.getApiUrl(); // 所有已认证的信用卡标示
	
	
	/**
	 * @Description 开放银行---信用卡
	 * @param 
	 * @return Map
	 * @Date 2017-11-30
	 * @LoggerAnnotation(moduleName = "信用卡查询", option = "银行卡接口查询")
	 */
	@RequestMapping(value = "crebank.do")
	@ResponseBody
	public JSONObject creBank(HttpServletRequest request, HttpServletResponse response) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String idcode=request.getParameter("idcode");		
		JSONObject object = new JSONObject();
		try {
			IpUrlEnum ip = IpUrlEnum.HSFS_IP;
			List<BankInterface> list = bankInterfaceServer.findAppCreAllData();

			// 查询以认证的银行卡标示用来在列表中去掉 
			NameValuePair[] data = {new NameValuePair("idcode", idcode) };
			String str = HttpClientController.postcheck(crecardt, data);
			logger.info("======返回值========" + str);
			JSONObject json = JSON.parseObject(str);
			String info=json.getString("data");
			JSONArray crebankList = JSON.parseArray(info);
			
			JSONArray array = new JSONArray();
			if(info==null){
				for (BankInterface obj : list) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("bankinterId", obj.getBankinterId());
					jsonObject.put("bankName", obj.getBankName());
					jsonObject.put("bankUrl", ip.getIpUrl() + obj.getBankUrl());
					jsonObject.put("creditcardItf", JSONObject.parse(obj.getCreditcardItf()));
					jsonObject.put("creditcardItfOpen", obj.getCreditcardItfOpen());
					jsonObject.put("depositcardItf", JSONObject.parse(obj.getDepositcardItf()));
					jsonObject.put("depositcardItfOpen", obj.getDepositcardItfOpen());
					array.add(jsonObject);					
				}
			}else{
				for (BankInterface obj : list) {
					if(!crebankList.contains(obj.getBankNum())){
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("bankinterId", obj.getBankinterId());
						jsonObject.put("bankName", obj.getBankName());
						jsonObject.put("bankUrl", ip.getIpUrl() + obj.getBankUrl());
						jsonObject.put("creditcardItf", JSONObject.parse(obj.getCreditcardItf()));
						jsonObject.put("creditcardItfOpen", obj.getCreditcardItfOpen());
						jsonObject.put("depositcardItf", JSONObject.parse(obj.getDepositcardItf()));
						jsonObject.put("depositcardItfOpen", obj.getDepositcardItfOpen());
						array.add(jsonObject);
					}
					
				}
			}
			
			
			

			object.put("data", array);
			object.put("errorInfo", "查询成功！！");
			object.put("errorCode", 0);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			object.put("errorInfo", "网路异常，请稍后！！");
			object.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("开放银行信息：" + (endTime - startTime));
		}

		logger.info("开放银行信息:" + object);
		return object;
	}
	
	
	/**
	 * @Description 开放银行---储蓄卡
	 * @param 
	 * @return Map
	 * @Date 2017-11-30
	 * @LoggerAnnotation(moduleName = "储蓄卡查询", option = "银行卡接口查询")
	 */
	
	@RequestMapping(value = "depbank.do")
	@ResponseBody
	public JSONObject depBank(HttpServletRequest request, HttpServletResponse response) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		JSONObject object = new JSONObject();
		try {
			List<BankInterface> list = bankInterfaceServer.findAppDepAllData();

			JSONArray array = new JSONArray();
			for (BankInterface obj : list) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("bankinterId", obj.getBankinterId());
				jsonObject.put("bankName", obj.getBankName());
				jsonObject.put("bankUrl", ip.getIpUrl() + obj.getBankUrl());
				jsonObject.put("creditcardItf", JSONObject.parse(obj.getCreditcardItf()));
				jsonObject.put("creditcardItfOpen", obj.getCreditcardItfOpen());
				jsonObject.put("depositcardItf", JSONObject.parse(obj.getDepositcardItf()));
				jsonObject.put("depositcardItfOpen", obj.getDepositcardItfOpen());
				array.add(jsonObject);
			}

			object.put("data", array);
			object.put("errorInfo", "查询成功！！");
			object.put("errorCode", 0);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			object.put("errorInfo", "网路异常，请稍后！！");
			object.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("开放银行信息：" + (endTime - startTime));
		}

		logger.info("开放银行信息:" + object);
		return object;
	}

	
	

	
	/**
	 * @Description 开放银行---数据中心银行图片
	 * @param 
	 * @return Map
	 * @Date 2017-11-30
	 * @LoggerAnnotation(moduleName = "信用卡查询", option = "数据中心银行图片查询")
	 */	
	
	@RequestMapping(value = "bankurl.do")
	@ResponseBody
	public Map<String, Object> bankurl(HttpServletRequest request, HttpServletResponse response) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Map<String, Object> object = new HashMap<String, Object>();
		try {
			
			List<BankInterface> list = bankInterfaceServer.findAppAllData();
			Map<String, Object> data = new HashMap<String, Object>();
			for (BankInterface obj : list) {
				data.put(obj.getBankNum(), ip.getIpUrl()  + obj.getBankUrl());
				data.put(obj.getBankNum() + "NAME", obj.getBankName());
			}
			object.put("data", data);
			object.put("errorInfo", "查询成功！！");
			object.put("errorCode", 0);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			object.put("errorInfo", "网路异常，请稍后！！");
			object.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("开放银行信息：" + (endTime - startTime));
		}

		logger.info("开放银行信息:" + object);
		return object;
	}

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-09-15
	 * @resource 查询信用卡认证时间
	 * 
	 *
	 */
	
	@RequestMapping(value = "/getcrecardtime.do")
	@ResponseBody
	public JSONObject getCrecardTime(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String cardNumber = request.getParameter("cardNumber");
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jsonObject = null;
		try {
			if (cardNumber != null && !cardNumber.isEmpty()) {
				
				// 填入各个表单域的值
				NameValuePair[] data = { new NameValuePair("idcode", cardNumber) };
				String str = HttpClientController.postcheck(crecardtimeurl, data);
				logger.info("======返回值========" + str);
				jsonObject = JSON.parseObject(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "网络异常，请稍后！");
			map.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("查询信用卡认证时间 ：" + (endTime - startTime));
		}
		logger.info("查询信用卡认证时间  " + map);
		
		return jsonObject;
	}
	public static void main(String[] args) {
		String str = "{'BANKCARD_CODE':'http://113.200.105.34:8086/bankController/getZXImageCode','MSG_SUBMIT':'http://113.200.105.34:8086/bankController/loadZX','PHONE_CODE':'http://113.200.105.34:8086/bankController/sendPhoneCode','LOGIN':'http://113.200.105.34:8086/bankController/getDetailMes'}";
		JSONObject jsonObject = JSON.parseObject(str);
		System.out.println(jsonObject.toString());
	}
}
