/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.vo;

/**
 * 作者：zwLiu <br>
 * 创建时间：2018年5月14日 <br>
 * 描述：宝付绑卡参数返回
 */
public class BFCertifiedBKVo {
	/**
	 * 绑定状态(0 成功，-1失败)
	 */
	private String bindStatus;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 绑定id
	 */
	private String bindId;
	/**
	 * 状态信息
	 */
	private String statusMsg;

	public String getBindStatus() {
		return bindStatus;
	}

	public void setBindStatus(String bindStatus) {
		this.bindStatus = bindStatus;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
}
