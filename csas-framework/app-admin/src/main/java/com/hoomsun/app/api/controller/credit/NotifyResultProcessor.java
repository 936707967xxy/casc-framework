package com.hoomsun.app.api.controller.credit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.bull.javamelody.internal.common.LOG;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.app.api.help.Base64Img;
import com.hoomsun.app.api.help.MD5Utils;
import com.hoomsun.app.api.model.AuthenticationUrl;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.common.util.UploadPathUtil;
import com.hoomsun.csas.sales.api.model.NameAuthenticationInfoWithBLOBs;
import com.hoomsun.csas.sales.api.server.inter.NameAuthenticationInfoServerI;



/**
 * 
 * @author 作者：liudongliang <br>
 * @Date 创建时间：2017年12月01日 <br>
 * @Description 描述： 异步通知结果接收接口 ，实名认证回调接口
 *              192.168.3.19:8080/app-admin/web/youdun/getvalue.do 人脸识别
 *              192.168.3.19:8080/app-admin/web/youdun/getvaluesign.do 签约记录
 */
@Controller
@RequestMapping("web/youdun")
public class NotifyResultProcessor {
	/**
	 * TODO 获取商户开户的pub_key
	 */
	static final String PUB_KEY = "efd55f09-aae5-4a0d-8aab-e2bf8144422e";
	/**
	 * TODO 获取商户开户的security_key
	 */
	static final String SECURITY_KEY = "5a238a75-44ca-4d7e-8b48-379dd7c97b77";

	static final String CHARSET_UTF_8 = "UTF-8";
	static final boolean IS_DEBUG = true;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	public NameAuthenticationInfoServerI nameAuthInfoServerI;

	@Autowired
	private UploadPathUtil uploadPathUtil;
	

	/**
	 * 生成MD5签名
	 *
	 * @param pub_key
	 *            商户公钥
	 * @param partner_order_id
	 *            商户订单号
	 * @param sign_time
	 *            签名时间
	 * @param security_key
	 *            商户私钥
	 */
	public static String getMD5Sign(String pub_key, String partner_order_id, String sign_time, String security_key) throws UnsupportedEncodingException {
		String signStr = String.format("pub_key=%s|partner_order_id=%s|sign_time=%s|security_key=%s", pub_key, partner_order_id, sign_time, security_key);
		System.out.println("signField：" + signStr + SECURITY_KEY);
		return MD5Utils.MD5Encrpytion(signStr.getBytes("UTF-8"));
	}

	
	/**
	 * @Description 接收实名认证异步通知
	 * @param 
	 * @return Map
	 * @Date 2017-11-30
	 * @LoggerAnnotation(moduleName = "实名认证", option = "实名认证云盾回调函数")
	 */
	
	@RequestMapping(value = "/getvalue.do")
	public void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final JSONObject reqObject = getRequestJson(request);

		JSONObject respJson = new JSONObject();
		// 验签
		String sign = reqObject.getString("sign");
		String sign_time = reqObject.getString("sign_time");
		String partner_order_id = reqObject.getString("partner_order_id");
		logger.info("sign：" + sign);
		logger.info("partner_order_id：" + partner_order_id);
		String signMD5 = getMD5Sign(PUB_KEY, partner_order_id, sign_time, SECURITY_KEY);
		logger.info("signMD5：" + signMD5);
		if (!sign.equals(signMD5)) {
			logger.info("异步通知签名错误");
			respJson.put("code", "0");
			respJson.put("message", "签名错误");

		} else {
			logger.info("收到商户异步通知");
			respJson.put("code", "1");
			respJson.put("message", "收到通知");
			// TODO 异步执行商户自己的业务逻辑(以免阻塞返回导致通知多次)
			Thread asyncThread = new Thread("asyncDataHandlerThread") {
				@Override
				public void run() {
					logger.info("异步执行商户自己的业务逻辑...");
					try {

						String id_name = reqObject.getString("id_name"); // 身份证姓名
						String id_number = reqObject.getString("id_number"); // 身份证号
						String partner_order_id = reqObject.getString("partner_order_id"); // 主键
						String verify_status = reqObject.getString("verify_status"); // 结果     1   一致  2  不一致 3  查无结果

						String validity_period = reqObject.getString("validity_period"); // 有效期限
						String issuing_authority = reqObject.getString("issuing_authority"); // 签发机关，如：杭州市公安 局西湖分局
						String nation = reqObject.getString("nation"); // 民族
						String gender = reqObject.getString("gender"); // 性别
						String birthday = reqObject.getString("birthday"); // 生日
						String age = reqObject.getString("age"); // 年龄
						String address = reqObject.getString("address"); // 地址
						String idcard_front_photo = reqObject.getString("idcard_front_photo"); // 身份证正面照      base64
						String idcard_portrait_photo = reqObject.getString("idcard_portrait_photo"); // 身份证证件正面头像照     base64>
						String idcard_back_photo = reqObject.getString("idcard_back_photo"); // 身份证背面照    base64
						String idcard_living_photo = reqObject.getString("living_photo"); // 身份证活体
						
						String similarity = reqObject.getString("similarity"); // 相似度
						String auth_result = reqObject.getString("auth_result"); // 比对结果    T-认证通过
						String fail_reason = reqObject.getString("fail_reason"); // 不通过原 因
						//if (verify_status.equals("1")) {
						if (true) {
							
							NameAuthenticationInfoWithBLOBs NameAuth = new NameAuthenticationInfoWithBLOBs();
							NameAuth.setFkId(partner_order_id);
							NameAuth.setAuthinfoPkId(PrimaryKeyUtil.getPrimaryKey()); // 主键
							NameAuth.setVerifyStatus(verify_status);
							NameAuth.setSimilarity(similarity);
							NameAuth.setAuthResult(auth_result);
							NameAuth.setFailReason(fail_reason);
							NameAuth.setIdName(id_name);
							NameAuth.setIdNumber(id_number);
							NameAuth.setValidityPeriod(validity_period);
							NameAuth.setIssuingAuthority(issuing_authority);
							NameAuth.setNation(nation);
							NameAuth.setGender(gender);
							NameAuth.setBirthday(birthday);
							NameAuth.setAge(age);
							NameAuth.setAddress(address);
							NameAuth.setIdcardFrontPhoto(idcard_front_photo);
							NameAuth.setIdcardPortraitPhoto(idcard_portrait_photo);
							NameAuth.setIdcardBackPhoto(idcard_back_photo);
							NameAuth.setIdcardLivingPhoto(idcard_living_photo);
							logger.info("存储实名认证。。。。");
							AuthenticationUrl  authenticationUrl=saveImgUrl(NameAuth); // 身份证上传的图片Url
							
							nameAuthInfoServerI.saveNameAuthInfoAndUrlInfo(partner_order_id, NameAuth, authenticationUrl);
						} else {
							System.out.println("实名认证信息不符合");
						}

					} catch (Exception e) {
						e.printStackTrace();
					} finally {

					}
				}
			};
			asyncThread.start();
		}

		// logger.info("返回结果：" + respJson.toJSONString());
		response.setCharacterEncoding(CHARSET_UTF_8);
		response.setContentType("application/json; charset=utf-8");
		response.getOutputStream().write(respJson.toJSONString().getBytes(CHARSET_UTF_8));

	}

	/**
	 * 获取请求json对象
	 */
	private JSONObject getRequestJson(HttpServletRequest request) throws IOException {
		InputStream in = request.getInputStream();
		byte[] b = new byte[10240];
		int len;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((len = in.read(b)) > 0) {
			baos.write(b, 0, len);
		}
		String bodyText = new String(baos.toByteArray(), CHARSET_UTF_8);
		JSONObject json = (JSONObject) JSONObject.parse(bodyText);
		if (IS_DEBUG) {
			logger.info("received notify message:");
			logger.info(JSON.toJSONString(json, true));
		}
		return json;
	}

	
	/**
	 * 
	 */
	private AuthenticationUrl saveImgUrl(NameAuthenticationInfoWithBLOBs NameAuth){
		AuthenticationUrl  authenticationUrl = new AuthenticationUrl();
		String viewPath = uploadPathUtil.saveIdcardPath(); // 放到用户信息中
		String staticViewPath = uploadPathUtil.userUrl(); // static数据
		String id = NameAuth.getFkId();
		// 身份证头像
		String url_photogetsavepath = viewPath + File.separator + id + File.separator + "idcard" + File.separator + "url_photoget.jpg"; // 完整本地路径
		String static_url_photogetsavepath= staticViewPath +"/"+ id + "/" + "idcard" + "/" + "url_photoget.jpg"; // 完整映射路径
		
		// 身份证正面
		String url_frontcardsavepath = viewPath + File.separator + id + File.separator + "idcard" + File.separator + "url_frontcard.jpg";
		String static_url_frontcardsavepath = staticViewPath +"/"+ id + "/" + "idcard" + "/" + "url_frontcard.jpg";
		
		// 身份证反面
		String url_backcardsavepath = viewPath + File.separator + id + File.separator + "idcard" + File.separator + "url_backcard.jpg";
		String static_url_backcardsavepath = staticViewPath +"/"+ id + "/" + "idcard" + "/" + "url_backcard.jpg";
		
		// 直接拍照
		String url_photolivingsavepath = viewPath + File.separator + id + File.separator + "idcard" + File.separator + "url_photoliving.jpg";
		String static_url_photolivingsavepath = staticViewPath +"/"+ id + "/" + "idcard" + "/" + "url_photoliving.jpg";
		
		File f = new File(uploadPathUtil.saveIdcardPath() + File.separator + id + File.separator + "idcard");
		if (!f.exists()) {
			f.mkdirs();
		}
		logger.info("实名认证路径" + f);
		
		Base64Img.GenerateImage(NameAuth.getIdcardPortraitPhoto(), url_photogetsavepath);
		Base64Img.GenerateImage(NameAuth.getIdcardFrontPhoto(), url_frontcardsavepath);
		Base64Img.GenerateImage(NameAuth.getIdcardBackPhoto(), url_backcardsavepath);
		Base64Img.GenerateImage(NameAuth.getIdcardLivingPhoto(), url_photolivingsavepath);
		
		authenticationUrl.setId(PrimaryKeyUtil.getPrimaryKey()); // 自己的主键
		authenticationUrl.setFkId(id); // 关联主键
		authenticationUrl.setUrlPhotoget(static_url_photogetsavepath);
		authenticationUrl.setUrlFrontcard(static_url_frontcardsavepath);
		authenticationUrl.setUrlBackcard(static_url_backcardsavepath);
		authenticationUrl.setUrlPhotoliving(static_url_photolivingsavepath);
		return authenticationUrl;
	}
	
	/**
	 * @Description 接收实名认证异步通知--签约
	 * @param 
	 * @return Map
	 * @Date 2017-11-30
	 * @LoggerAnnotation(moduleName = "实名认证", option = "接收实名认证异步通知")
	 */
	
	@RequestMapping(value = "/getvaluesign")
	public void processTwo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final JSONObject reqObject = getRequestJson(request);

		JSONObject respJson = new JSONObject();
		// 验签
		String sign = reqObject.getString("sign");
		String sign_time = reqObject.getString("sign_time");
		String partner_order_id = reqObject.getString("partner_order_id");
		logger.info("sign：" + sign);
		logger.info("partner_order_id：" + partner_order_id);
		String signMD5 = getMD5Sign(PUB_KEY, partner_order_id, sign_time, SECURITY_KEY);
		logger.info("signMD5：" + signMD5);
		if (!sign.equals(signMD5)) {
			logger.info("异步通知签名错误22");
			respJson.put("code", "0");
			respJson.put("message", "签名错误");

		} else {
			logger.info("收到商户异步通知22");
			respJson.put("code", "1");
			respJson.put("message", "收到通知");
			// TODO 异步执行商户自己的业务逻辑(以免阻塞返回导致通知多次)
			Thread asyncThread = new Thread("asyncDataHandlerThread") {
				@Override
				public void run() {
             		logger.info("异步执行商户自己的业务逻辑22...");
				}
			};
			asyncThread.start();
		}
		// logger.info("返回结果：" + respJson.toJSONString());
		response.setCharacterEncoding(CHARSET_UTF_8);
		response.setContentType("application/json; charset=utf-8");
		response.getOutputStream().write(respJson.toJSONString().getBytes(CHARSET_UTF_8));

	}

}
