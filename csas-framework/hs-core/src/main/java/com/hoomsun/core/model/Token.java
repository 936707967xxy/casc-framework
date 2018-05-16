/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.model;

import java.util.Date;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月17日 <br>
 * 描述：模块还登录的信息
 */
public class Token {
	private String userId;// 用户ID
	private String userName;// 用户姓名
	private String userCode;
	private Date iat;// 签发时间
	private Date exp;// 过期时间
	private String aud;// 接受方 只认可IP和域名

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getIat() {
		return iat;
	}

	public void setIat(Date iat) {
		this.iat = iat;
	}

	public Date getExp() {
		return exp;
	}

	public void setExp(Date exp) {
		this.exp = exp;
	}

	public String getAud() {
		return aud;
	}

	public void setAud(String aud) {
		this.aud = aud;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
}
