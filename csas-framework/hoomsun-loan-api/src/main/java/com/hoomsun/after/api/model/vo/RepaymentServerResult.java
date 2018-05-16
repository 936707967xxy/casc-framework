/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.vo;

import java.math.BigDecimal;

/**
 * 作者：金世强 <br>
 * 创建时间：2018年2月27日 <br>
 * 描述：还款相关操作接口返回实体
 */
public class RepaymentServerResult {

	/**
	 * 还款期次
	 */
	private Integer Stream;

	/**
	 * 扣除余额
	 */
	private BigDecimal dedutBal;

	/**
	 * 扣除状态（1成功，2失败）
	 */
	private String status;

	/**
	 * 扣除信息
	 */
	private String msg;

	public Integer getStream() {
		return Stream;
	}

	public void setStream(Integer stream) {
		Stream = stream;
	}

	public BigDecimal getDedutBal() {
		return dedutBal;
	}

	public void setDedutBal(BigDecimal dedutBal) {
		this.dedutBal = dedutBal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
