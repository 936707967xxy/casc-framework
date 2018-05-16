/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

import java.math.BigDecimal;

/**
 * 作者：zwLiu <br>
 * 创建时间：2018年5月14日 <br>
 * 描述：宝付认证支付传参
 */
public class BfBFCertifiedPayParam {
	/**
	 * 绑定标识号
	 */
	private String bindId;
	/**
	 * 划扣金额
	 */
	private BigDecimal txnAmt;
	/**
	 * 风控参数
	 */
	private String clientIp;
	/**
	 * 线上线下标识
	 */
	private String flag;
	/**
	 * 路径
	 */
	private String path;

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public BigDecimal getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(BigDecimal txnAmt) {
		this.txnAmt = txnAmt;
	}



	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
