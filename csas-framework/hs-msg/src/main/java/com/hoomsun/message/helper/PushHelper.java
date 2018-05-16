package com.hoomsun.message.helper;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;

public class PushHelper {

	private static final Logger log = LoggerFactory.getLogger(PushHelper.class);
	//private static final String appKey = "1ece3bf510fe07c87affc6da";
	//private static final String masterSecret = "1cb4c001ef94da403aca353e";
	private static final String appKey = "e445e606b80a30deef7f8a98";
	private static final String masterSecret = "decffed14caeb0f25bd41e36";
	
	public static void main(String[] args) {

		List<String> list = new ArrayList<String>();
		list.add("1104a89792a398d9da7");
		SendPushWithTitle(list, "您的批核额度为1000.00元、批核产品为“土豪贷B”，请及时签约。", "审核消息",
				"1");

	}

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-12-19
	 * @resource 带有标题的提示信息---自定义,带有返回标示
	 */
	public static void SendPushWithTitle(List<String> list, String ALERT,
			String title, String flag) {
		ClientConfig config = ClientConfig.getInstance();
		config.setPushHostName("https://api.jpush.cn");

		JPushClient jpushClient = new JPushClient(masterSecret, appKey, null,
				config);
		PushPayload payload = buildPushObject_all_alias_alert_title(list,
				ALERT, title, flag);

		try {
			PushResult result = jpushClient.sendPush(payload);
			log.info("【极光推送】Got result - " + result);

		} catch (APIConnectionException e) {
			log.error("【极光推送】Connection error. Should retry later. " + e);

		} catch (APIRequestException e) {
			log.error("【极光推送】Error response from JPush server. Should review and fix it. "
					+ e);
			log.error("【极光推送】HTTP Status: " + e.getStatus());
			log.error("【极光推送】Error Code: " + e.getErrorCode());
			log.error("【极光推送】Error Message: " + e.getErrorMessage());
			log.error("【极光推送】Msg ID: " + e.getMsgId());
		}
	}

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-12-19
	 * @resource 不带有标题的提示信息---无返回标示
	 */
	public static void SendPushWithCustomConfig(List<String> list, String ALERT) {
		ClientConfig config = ClientConfig.getInstance();
		config.setPushHostName("https://api.jpush.cn");

		JPushClient jpushClient = new JPushClient(masterSecret, appKey, null,
				config);
		PushPayload payload = buildPushObject_all_alias_alert(list, ALERT);
		try {
			PushResult result = jpushClient.sendPush(payload);
			log.info("【极光推送】Got result - " + result);

		} catch (APIConnectionException e) {
			log.error("【极光推送】Connection error. Should retry later. " + e);

		} catch (APIRequestException e) {
			log.error("【极光推送】Error response from JPush server. Should review and fix it. "
					+ e);
			log.error("【极光推送】HTTP Status: " + e.getStatus());
			log.error("【极光推送】Error Code: " + e.getErrorCode());
			log.error("【极光推送】Error Message: " + e.getErrorMessage());
			log.error("【极光推送】Msg ID: " + e.getMsgId());
		}
	}

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-12-19
	 * @resource 不带有标题的提示信息
	 */
	public static PushPayload buildPushObject_all_alias_alert(
			List<String> list, String ALERT) {
		return PushPayload.newBuilder().setPlatform(Platform.all())
				.setAudience(Audience.registrationId(list))
				.setNotification(Notification.alert(ALERT)).build();
	}

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-12-19
	 * @resource 带有标题的提示信息
	 */
	public static PushPayload buildPushObject_all_alias_alert_title(
			List<String> list, String alert, String title, String flag) {
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.all())
				.setAudience(Audience.registrationId(list))
				.setNotification(
						Notification
								.newBuilder()
								.setAlert(alert)
								.addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).addExtra("flag", flag).build())
								.build())

				.build();
	}

}
