/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.common.model;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月8日 <br>
 * 描述：处理结果返回值
 */
public class Json {
	/** 成功与否 */
	private Boolean success = false;
	/** 说明信息 */
	private String msg;
	/** 附加数据 */
	private Object data;

	public Json() {
	}

	public Json(Boolean success, String msg) {
		this.success = success;
		this.msg = msg;
	}
	
	public Json(Boolean success, String msg, Object data) {
		this.success = success;
		this.msg = msg;
		this.data = data;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
