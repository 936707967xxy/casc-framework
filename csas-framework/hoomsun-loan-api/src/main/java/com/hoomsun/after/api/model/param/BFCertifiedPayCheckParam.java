/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

/**
 * 作者：zwLiu <br>
 * 创建时间：2018年5月15日 <br>
 * 描述：
 */
public class BFCertifiedPayCheckParam {
	/**
	 * 配置文件路径
	 */
	private String path;
	/**
	 * 验证码
	 */
	private String smsCode;
	/**
	 * 业务流水号
	 */
	private String businessNo;
	
	/**
	 * 线上线下标识
	 */
	private String flag;
	
	/**
	 * 订单号
	 */
	private String orderNo;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public String toString() {
		return "BFCertifiedPayCheckParam [path=" + path + ", smsCode=" + smsCode + ", businessNo=" + businessNo + ", flag=" + flag + ", orderNo=" + orderNo + "]";
	}
}
