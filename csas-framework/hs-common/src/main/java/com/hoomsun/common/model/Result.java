/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.common.model;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月30日 <br>
 * 描述：接口返回值
 */
public class Result {
	private Integer code;
	private String msg;
	private Object data;

	public Result() {
	}

	public Result(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Result(Integer code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
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
