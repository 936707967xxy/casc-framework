package com.hoomsun.app.api.help;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

import com.hoomsun.app.api.restDemo.client.JsonReqClient;
import com.hoomsun.app.api.restDemo.client.XmlReqClient;


/**
 * 
 * @author liudongliang
 * @date 2017-08-15
 * @resource 此类是针对发送短信设置
 *
 */
public class SmsauController {

	
	private  Logger logger = LoggerFactory.getLogger(getClass());
	
	
	/**
	 * 发短信接口方法
	 * 
	 * @param b
	 * @param accountSid
	 * @param authToken
	 * @param appId
	 * @param templateId
	 * @param to
	 * @param param
	 * @return
	 */
	public static String TemplateSMS(boolean b, String accountSid, String authToken, String appId, String templateId, String to, String param) {

		String result = null;
		if (b) {
			result = new JsonReqClient().templateSMS(accountSid, authToken, appId, templateId, to, param);
		} else {
			result = new XmlReqClient().templateSMS(accountSid, authToken, appId, templateId, to, param);
		}

		// 发送短信
		JSONObject object = JSONObject.fromObject(result);
		JSONObject resp = (JSONObject) object.get("resp");
		String code = resp.get("respCode").toString();
		
		// 发送状态
		String flag = null;
		if ("000000".equals(code)) { // 成功
			flag = "0";
		} else { // 失败
			flag = "1";
		}
		return flag;
	}

	/**
	 * 语音发送 接口方法
	 * 
	 * @param request
	 *            accountSid 企业id authToken 企业标识 appId 模板id to 手机号 verifyCode
	 *            验证码
	 * @return
	 */
	public static String voiceCode(String accountSid, String authToken, String appId, String to, String verifyCode) {
		// TODO Auto-generated method stub
		String result = null;
		result = new JsonReqClient().voiceCode(accountSid, authToken, appId, to, verifyCode);
		return result;
	}

	/**
	 * 语音发送 刘栋梁
	 * 
	 * @param request
	 * @return
	 */
	public static void main(String[] args) {

		String phone = "15735921213";
		// String verifyCode=(String)request.getSession().getAttribute(phone);
		String verifyCode = "123456";
		String accountSid = "138bc91472ac5b5192195669d9246d71";
		// Auth Token
		String token = "b85406e6f8c9ba1a6dec6d34a66bb52e";
		// appId
		String appId = "975c95443bf24c4fa6b8782557800df1";

		String resultString = voiceCode(accountSid, token, appId, phone, verifyCode);
		System.out.println(resultString);

	}

	/*public static void main(String[] args) {
		boolean json = true;
		// Account Sid
		String accountSid = "138bc91472ac5b5192195669d9246d71";
		// Auth Token
		String token = "b85406e6f8c9ba1a6dec6d34a66bb52e";
		// appId
		String appId = "775db3d12adf4bcf9e9d5f576e11d74d";
		// 短信模板templateId
		String templateId = "129022";
		// 参数
		String to = "15735921213";
		String para =  "123465";
		
		// 发短信
		String flag = SmsauController.TemplateSMS(json, accountSid, token, appId, templateId, to, para);
		
	}*/

}
