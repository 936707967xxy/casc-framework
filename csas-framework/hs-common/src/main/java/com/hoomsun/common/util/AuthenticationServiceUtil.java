package com.hoomsun.common.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author liuzhiwen
 * @resource 天行银联四要素鉴权
 * @date 2017-11-20
 *
 */
public class AuthenticationServiceUtil  {
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationServiceUtil.class);
	
	
	
	

	/**
	 * 去天行请求银行卡信息
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, String> requestAccInformation(String accno)
			throws Exception {
		LOG.info("进入了天行查询银行卡信息接口，查询卡号为：" + accno);
		Map<String, String> result = new HashMap<String, String>();
		// 获取授权码
		Map AccesstokenBack = new HashMap();
		Map resultSend = new HashMap();
		// 获取授权码所需参数
		resultSend.put("account", "hsjr");
		resultSend.put("signature", "49f12d56335d466da447591605e3087b");
		String jsonAccesstoken = HttpClientSendUtilValueNew.sendService("http://tianxingshuke.com/api/rest/common/organization/auth", resultSend);
		JSONObject jsonAccesstokenObject = JSONObject
				.parseObject(jsonAccesstoken);
		AccesstokenBack = jsonAccesstokenObject;
		if (AccesstokenBack != null) {
			if ("true".equals(checkString(AccesstokenBack.get("success")))) {
				Map resultData = new HashMap();
				JSONObject jsonDataObject = JSONObject
						.parseObject(checkString(AccesstokenBack.get("data")));
				resultData = jsonDataObject;
				String accessToken = checkString(resultData.get("accessToken"));
				LOG.info("银行卡信息查询授权码获取成功！");
				// 去请求天行银行卡信息
				Map bankInformationBack = new HashMap();
				Map<String, Object> resultSend1 = new HashMap();
				resultSend1.put("account", "hsjr");
				resultSend1.put("accessToken", accessToken);
				resultSend1.put("accountNO", accno);
				String jsonBankInfo = HttpClientUtil.doGet("http://tianxingshuke.com/api/rest/unionpay/depositBank", null, resultSend1);
				LOG.info("银行卡信息查询请求成功！");
				JSONObject jsonBankObject = JSONObject
						.parseObject(jsonBankInfo);
				bankInformationBack = jsonBankObject;
				if (bankInformationBack != null) {
					if ("true".equals(checkString(bankInformationBack
							.get("success")))) {
						Map bankInformationBack1 = new HashMap();
						JSONObject jsonBankObject1 = JSONObject
								.parseObject(checkString(bankInformationBack
										.get("data")));
						bankInformationBack1 = jsonBankObject1;
						if ("EXIST".equals(checkString(bankInformationBack1
								.get("checkStatus")))) {
							result.put("ACCBANKNAME",
									checkString(bankInformationBack1
											.get("bankName")));
							result.put("ACCBANKTYPE",
									checkString(bankInformationBack1
											.get("cardType")));
						} else {
							throw new RuntimeException("银行卡类型不存在，请核实之后重试！");
						}
					} else {
						throw new RuntimeException("请求银行卡参数失败，请重试！");
					}
				} else {
					LOG.info("银行卡信息查询请求失败！");
					throw new RuntimeException("请求银行卡参数失败，请重试！");
				}
			} else {
				throw new RuntimeException("授权码请求失败，请重新尝试！");
			}
		} else {
			LOG.info("银行卡信息查询授权码请求失败！");
			throw new RuntimeException("授权码请求异常，请重新尝试！");
		}
		return result;
	}


	


	
	public static String checkString(Object str) {
		if (str == null) {
			return "";
		}

		String temp = "";
		try {
			temp = str.toString();
		} catch (Exception localException) {
		}
		return temp;
	}
	public static void main(String[] args) throws Exception {
		 Map<String, String>  aa = new HashMap<String, String>();
		 aa =requestAccInformation("6217997900006696330");
		 System.out.println(aa);
		 
	}
	

}
