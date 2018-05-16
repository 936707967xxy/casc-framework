/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.vo;

/**
 * 作者：金世强 <br>
 * 创建时间：2018年3月13日 <br>
 * 描述：还款server返回值
 */
public class DeductServerResult {

	private boolean success;

	private String msg;

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
