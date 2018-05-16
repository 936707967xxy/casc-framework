package com.hoomsun.app.api.controller.credit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthorizeRequest;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditScoreGetRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditScoreGetResponse;
import com.hoomsun.app.api.enums.ApiEnum;
import com.hoomsun.app.api.enums.IpUrlEnum;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.common.util.HttpClientUtil;
import com.hoomsun.csas.sales.api.model.NameAuthentication;
import com.hoomsun.csas.sales.api.server.inter.NameAuthenticationServerI;


/**
 * 
 * @author 作者：liudongliang <br>
 * @Date 创建时间：2017年11月30日 <br>
 * @Description 1. 芝麻分授权
 *              http://192.168.3.19:8080/hsfs-web/web/zhima/zmcxUrl.do?name=刘栋梁&cardNo=142726199402252135&ID=4
 *              1. 芝麻回调
 *              http://192.168.3.19:8080/hsfs-web/web/zhima/zmcx.do
 */
@Controller
@RequestMapping("web/zhima")
public class ZhiMaController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	// 芝麻开放平台地址
	private String gatewayUrl = "https://zmopenapi.zmxy.com.cn/openapi.do";
	// 商户应用 Id
	private String appId;

	// 商户 RSA 私钥
	private String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALNOsOjBV/cB8aipVFq7HrwlW2TJ86AXnB9WtpvE+qbxz5fIZ0UrkI55fQC20iHi1IyJLKxhoBGQ7kBXwV3VQQGNmVMI81tMxD/1wRlmBMSG52M+5sPbVNVlDo4ERPP2odSC4u2CGVJ9wnZSCnCKorwdp/WDMQdswRQzY6JVLfYbAgMBAAECgYA5PUCaPMZQ8szrM4rbH+rS0vYW+aUNERxkoLDQjxQY4Q6M/IuC+EJASSYPaU0xRhDmABBDwzJvCR4Vai4vPxlt1R+Y/FkDmp9cQUCqTUh6fpadrqH55JjjSVlLBGI6BToUIB192R7WvVFU5jZTfWFZIeuoCpjXpLWhoRBqzNCUMQJBAOr5gdXKsUchUcP61vuM/ZpGhUADDdgVBrRe6WifOH3Sh2UozqgVJaQNGpX5n14EzB4MUth6kGx1L25WmIxqqVMCQQDDWgfyV5E5GogV42/NPmDYt95V0mPQI9kvq2R32kVcDYHgqMgmAkScn3buAyovrZTrOXmACIH4djQNxZ2JiD8ZAkEA6kAZrsAmYcoW0LEmV5skTAQX3UvuJkg36eNg//sTf2Q9tJytASYr41No+LDlrv5LW31frg7qIiXlRjOwWvQOnwJAc2Ri/UushMafTA6ko+AM5Pb3Zkp4myIR0I61MfbEsbNo3IPWdl0wmjO31LAZ4z/tj2TL2E0uOM3ZwajRfcP1QQJAGI5UH0ZpymcCDGEi4HhxbqTxQY+Hf7LWXARUDjOBrQCGx90rJk5zmyEEd3EoJZnLXag//mVmKAC51RO+A6FYbA==";
	// 芝麻 RSA 公钥
	private String zhimaPublicKey;

	private static final String CHARSET = "UTF-8";

	private String zhimaUrl =IpUrlEnum.DATA_IP.getIpUrl()+ ApiEnum.ZHIMAURL.getApiUrl(); // 推送芝麻分

	@Autowired
	private NameAuthenticationServerI hsServerI;

	
	// 初始化13位数字流水号
	private final AtomicInteger counter = new AtomicInteger(0);

	/**
	 * @Description  芝麻分授权   刘栋梁
	 * @param        
	 * @return       Map
	 * @Date         2017-12-01
	 * @LoggerAnnotation(moduleName = "芝麻接口", option = "芝麻分授权")
	 */	
	
	@RequestMapping(value = "zmcxUrl.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> zmcxUrl(HttpServletRequest req) {
		// 获取开始时间
		long startTime = System.currentTimeMillis(); 
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String name = req.getParameter("name");
			String cardNo = req.getParameter("cardNo");
			// 用户id
			String loanId = req.getParameter("ID"); 

			if (loanId != null && !"".equals(loanId)) {
				// 芝麻授权返回的url
				String url = testZhimaAuthInfoAuthorize(name, cardNo, loanId);
				result.put("errorInfo", url);
				result.put("errorCode", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "数据查询失败！");
			result.put("errorCode", 1);
		} finally {
			// 获取结束时间
			long endTime = System.currentTimeMillis(); 
			logger.info("跳转芝麻认证界面：" + (endTime - startTime));
		}
		return result;
	}

	/**
	 * @Description 芝麻授权回调接口，获取分值   刘栋梁
	 * @param        
	 * @return       Map
	 * @Date         2017-12-01
	 * @LoggerAnnotation(moduleName = "芝麻接口", option = "芝麻授权回调接口，获取分值")
	 */	
	
	@RequestMapping(value = "zmxy.do", method = RequestMethod.GET)
	public String zmxy(HttpServletRequest req) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String redirecturl = "";
		try {
			String sign = req.getParameter("sign");
			String params = req.getParameter("params");
			Map zmsqMap = getResult(params, sign);
			String loanId = "";
			String openId = "";
			String zhimasorce = "";
			if (zmsqMap.get("loanId") != null && !"".equals(zmsqMap.get("loanId")) && zmsqMap.get("openId") != null && !"".equals(zmsqMap.get("openId"))) {
				loanId = zmsqMap.get("loanId").toString();
				openId = zmsqMap.get("openId").toString();
				zhimasorce = testZhimaCreditScoreGet(openId);
			}

			logger.info("loanId============" + loanId);
			logger.info("zhimasorce============" + zhimasorce);
			NameAuthentication nameAuth = hsServerI.selectByPrimaryKey(loanId);
			String url = zhimaUrl; // 服务码
			// 填入各个表单域的值
			Map<String, Object> data=new HashMap<String, Object>();
			data.put("cardNumber", nameAuth.getPaperid());
			data.put("grade", zhimasorce);
			String str = HttpClientUtil.doPost(url, null, null, data);
			logger.info("添加芝麻认证 给数据中心推送信息========" + str);
			
			JSONObject jsonObject = JSON.parseObject(str); // 阿里的对象转换
			if (!"0000".equals(jsonObject.get("errorCode").toString())) {
				return "zhima/failmsg";
			}
			nameAuth.setIssesame("1");
			nameAuth.setIssesameVal("已芝麻认证");
			nameAuth.setSesameGradeval(zhimasorce);
			nameAuth.setSesameTime(DateUtil.getCurrentTime());
			int i = hsServerI.updateByPrimaryKeySelective(nameAuth);
			logger.info("芝麻认证修改条数=" + i);
			if (i == 0) {
				redirecturl = "zhima/failmsg";
			} else {
				redirecturl = "zhima/success";
			}

		} catch (Exception e) {
			redirecturl = "zhima/failmsg";
			e.printStackTrace();
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("获取芝麻分界面：" + (endTime - startTime));
		}
		return redirecturl;
	}

	/**
	 * 
	 * 用户授权芝麻返回获取openId
	 * 
	 * @throws Exception
	 */
	public Map getResult(String params, String sign) throws Exception {

		// 判断串中是否有%，有则需要decode
		appId = "1002253";
		zhimaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEmYp6pP5z3EzHPK8kf6KW7ls9cNgAsezDGyILNiuZfxvtV/Lm2+LbBajoko9N9bqR7ZnsdyGtuiVTK8J83BCwcz2edsfJL65rci09ntzz9KDjF6Mgx/6XnHU4bCkvnr+vxGZ7aaBizGVYVH8VRnd9rtQfil///tv2AitxQHNopwIDAQAB";
		String openId = "";
		String loanId = "";
		if (params.indexOf("%") != -1) {
			params = URLDecoder.decode(params, CHARSET);
		}

		if (sign.indexOf("%") != -1) {
			sign = URLDecoder.decode(sign, CHARSET);
		}

		DefaultZhimaClient client = new DefaultZhimaClient(gatewayUrl, appId, privateKey, zhimaPublicKey);
		Map zmsqMap = new HashMap();
		try {
			String result = client.decryptAndVerifySign(params, sign);
			System.out.println("result++++" + result);
			if (result != null && !"".equals(result)) {
				String status[] = result.split("&");
				for (int i = 0; i < status.length; i++) {
					if (status[i].contains("open_id")) {
						String resultOpenId[] = status[i].split("=");
						openId = resultOpenId[1];

					}
					if (status[i].contains("state")) {
						String resultState[] = status[i].split("=");
						loanId = resultState[1];
					}
				}

			}
			zmsqMap.put("openId", openId);
			zmsqMap.put("loanId", loanId);
		} catch (ZhimaApiException e) {
			e.printStackTrace();
		}
		return zmsqMap;
	}

	/**
	 * 
	 * 芝麻信用评分
	 * 
	 */
	public String testZhimaCreditScoreGet(String openId) {
		appId = "1002253";
		zhimaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEmYp6pP5z3EzHPK8kf6KW7ls9cNgAsezDGyILNiuZfxvtV/Lm2+LbBajoko9N9bqR7ZnsdyGtuiVTK8J83BCwcz2edsfJL65rci09ntzz9KDjF6Mgx/6XnHU4bCkvnr+vxGZ7aaBizGVYVH8VRnd9rtQfil///tv2AitxQHNopwIDAQAB";
		String score = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String date = sdf.format(new Date());
		System.out.println(date);

		String number = String.format("%13d", counter.incrementAndGet()).replace(' ', '0');
		System.out.println(date + number);
		ZhimaCreditScoreGetRequest req = new ZhimaCreditScoreGetRequest();
		req.setPlatform("zmop");
		req.setTransactionId(date + number);// 必要参数
		req.setProductCode("w1010100100000000001");// 必要参数
		req.setOpenId(openId);// 必要参数
		DefaultZhimaClient client = new DefaultZhimaClient(gatewayUrl, appId, privateKey, zhimaPublicKey);
		try {
			ZhimaCreditScoreGetResponse response = client.execute(req);
			score = response.getZmScore();
			/*
			 * System.out.println(response.isSuccess());
			 * System.out.println(response.getErrorCode());
			 * System.out.println(response.getErrorMessage());
			 * System.out.println(response.getZmScore());
			 */
		} catch (ZhimaApiException e) {
			e.printStackTrace();
		}
		return score;
	}

	

	/**
	 * 芝麻信用用户授权
	 * 
	 */
	public String testZhimaAuthInfoAuthorize(String loanName, String cardNo, String loanId) {
		appId = "1002253";
		zhimaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEmYp6pP5z3EzHPK8kf6KW7ls9cNgAsezDGyILNiuZfxvtV/Lm2+LbBajoko9N9bqR7ZnsdyGtuiVTK8J83BCwcz2edsfJL65rci09ntzz9KDjF6Mgx/6XnHU4bCkvnr+vxGZ7aaBizGVYVH8VRnd9rtQfil///tv2AitxQHNopwIDAQAB";
		String url = "";
		ZhimaAuthInfoAuthorizeRequest req = new ZhimaAuthInfoAuthorizeRequest();
		req.setChannel("apppc");
		req.setPlatform("zmop");
		req.setIdentityType("2");// 必要参数 M_H5 app Html5页面 M_APPPC_CERT apppc
									// pc页面
		req.setIdentityParam("{\"name\":\"" + loanName + "\",\"certType\":\"IDENTITY_CARD\",\"certNo\":\"" + cardNo + "\"}");// 必要参数
		req.setBizParams("{\"auth_code\":\"M_H5\",\"channelType\":\"app\",\"state\":\"" + loanId + "\"}");//
		DefaultZhimaClient client = new DefaultZhimaClient(gatewayUrl, appId, privateKey, zhimaPublicKey);
		try {
			url = client.generatePageRedirectInvokeUrl(req);
			System.out.println(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}

}
