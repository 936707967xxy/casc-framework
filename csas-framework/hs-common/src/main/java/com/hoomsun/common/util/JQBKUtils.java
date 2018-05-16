/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.common.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;


import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 作者：liusong <br>
 * 创建时间：2017年9月20日 <br>
 * 描述：鉴权绑卡
 */
public class JQBKUtils {
	private static final Logger log = LoggerFactory.getLogger(JQBKUtils.class);
	
	/**
	 * 作者：liusong <br>
	 * 创建时间：2017年9月20日 <br>
	 * 描述： 鉴权绑卡第一步，输入客户信息
	 * @param custName 客户姓名
	 * @param idNo 证件号
	 * @param cardNo 银行卡号
	 * @param phone 银行预留手机号
	 * @param GGcustomerId 客户号
	 * @return
	 * @throws Exception
	 */
	public static TreeMap<String, String> jqbka(String custName,String idNo,String cardNo,String phone,String GGcustomerId) throws Exception{
		String format = "XML";
		String channel = "PMP";
		String tranCode = "HF1011";
		String businessId = "cs0213";// 商户号（测试环境）
		String message = "";//返回状态   0成功      1失败
		// 将要签名的数据传给map
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("businessId", businessId);
		map.put("cardNo", cardNo);
		map.put("custName", URLEncoder.encode(custName, "utf-8"));
		map.put("idNo", idNo);
		map.put("GGcustomerId", GGcustomerId);
		map.put("phone", phone);
		// 恒丰验签
		String signdata = HFUtils.signway(map);
		map.put("eamssign", signdata);
		map.put("format", format);
		map.put("channel", channel);
		map.put("tranCode", tranCode);
		// 请求url
		String url = HFUtils.urlway(tranCode, map);
		String result = HFUtils.check(url);
		log.info("鉴权绑卡====" + result);
		// 拿到验签的数据
		Document document = DocumentHelper.parseText(result);
		Map<String, Object> resultmap = XmlUtil.XmltoMap(document);
		String errorCode = (String) resultmap.get("errorCode");
		String errorMessage = (String) resultmap.get("errorMessage");
		@SuppressWarnings("unchecked")
		Map<String, Object> msg = (Map<String, Object>) resultmap.get("cd");
		String accreditCode = (String) msg.get("accreditCode");
		String eamssign = (String) msg.get("eamssign");
		TreeMap<String, String> map1 = new TreeMap<String, String>();
		map1.put("accreditCode", accreditCode);
		map1.put("errorCode", errorCode);
		map1.put("errorMessage", URLEncoder.encode(errorMessage, "utf-8"));
		// 商户验签
		String status = HFUtils.checksign(eamssign.toString(), map1);
		// 拿到验签的数据
		log.info("=====验签状态======" + status);
		if (status.equals("0")) {
			log.info("===验签成功=====");
		}
		if("0000".equals(errorCode) && "0".equals(accreditCode) && "0".equals(status)){
			message = "0";//鉴权成功
		}else{
			message = "1";//鉴权失败
		}
		map1.put("message", message);
		map1.put("errorMessage", URLDecoder.decode(map1.get("errorMessage"), "UTF-8"));
		return map1;
	}
	
	/**
	 * 作者：liusong <br>
	 * 创建时间：2017年9月20日 <br>
	 * 描述： 鉴权绑卡第二步，输入短信验证码
	 * @param custName 客户姓名
	 * @param idNo 证件号
	 * @param cardNo 银行卡号
	 * @param phone 银行预留手机号
	 * @param GGcustomerId 客户号
	 * @param code  验证码
	 * @return
	 * @throws Exception
	 */
	public static TreeMap<String, String> authentication(String custName,String idNo,String cardNo,String phone,String GGcustomerId,String code) throws Exception{
		String format = "XML";
		String channel = "PMP";
		String tranCode = "HF1012";
		String businessId = "cs0213";// 商户号（测试环境）
		String message = "";//返回状态   0成功      1失败
		// 将要签名的数据传给map
		TreeMap<String, String> map = new TreeMap<String, String>();
		map.put("businessId", businessId);
		map.put("cardNo", cardNo);
		map.put("custName", URLEncoder.encode(custName, "utf-8"));
		map.put("idNo", idNo);
		map.put("GGcustomerId", GGcustomerId);
		map.put("phone", phone);
		map.put("code", code);

		// 恒丰验签
		String signdata = HFUtils.signway(map);
		map.put("eamssign", signdata);
		map.put("format", format);
		map.put("channel", channel);
		map.put("tranCode", tranCode);

		// 请求url
		String url = HFUtils.urlway(tranCode, map);
		String result = HFUtils.check(url);
		log.info("鉴权绑卡短信认证====" + result);
		/* 
		 * xml
		 */
		Document document = DocumentHelper.parseText(result);
		Map<String, Object> resultmap = XmlUtil.XmltoMap(document);
		@SuppressWarnings("unchecked")
		Map<String, Object> msg = (Map<String, Object>) resultmap.get("cd");
		String accreditCode = (String) msg.get("accreditCode");
		String eamssign = (String) msg.get("eamssign");
		String errorCode = (String) resultmap.get("errorCode");
		String errorMessage = (String) resultmap.get("errorMessage");
		TreeMap<String, String> map1 = new TreeMap<String, String>();
		map1.put("accreditCode", accreditCode);
		map1.put("errorCode", errorCode);
		map1.put("errorMessage", URLEncoder.encode(errorMessage, "utf-8"));
		// 商户验签
		String status = HFUtils.checksign(eamssign.toString(), map1);
		// 拿到验签的数据
		log.info("=====验签状态======" + status);
		if (status.equals("0")) {
			log.info("===验签成功=====");
			// 做数据处理 // transStat 0 成功 1交易中需查证 2失败 3可疑 需查证
		}
		if("0000".equals(errorCode) && "0".equals(accreditCode) && "0".equals(status)){
			message = "0";
		}else{
			message = "1";
		}
		map1.put("message", message);
		map1.put("errorMessage", URLDecoder.decode(map1.get("errorMessage"), "UTF-8"));
		return map1;
	}

}
