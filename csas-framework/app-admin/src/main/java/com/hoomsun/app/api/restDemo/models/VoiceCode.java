/**
 * @author Glan.duanyj
 * @date 2014-05-12
 * @project rest_demo
 */
package com.hoomsun.app.api.restDemo.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "voiceCode")
public class VoiceCode {
	private String appId;
	private String verifyCode;
	private String to;
	private String displayNum;
	private String playTimes;
	private String captchaCode;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getDisplayNum() {
		return displayNum;
	}
	public void setDisplayNum(String displayNum) {
		this.displayNum = displayNum;
	}
	public String getPlayTimes() {
		return playTimes;
	}
	public void setPlayTimes(String playTimes) {
		this.playTimes = playTimes;
	}
	public String getCaptchaCode() {
		return captchaCode;
	}
	public void setCaptchaCode(String captchaCode) {
		this.captchaCode = captchaCode;
	}
	
}
