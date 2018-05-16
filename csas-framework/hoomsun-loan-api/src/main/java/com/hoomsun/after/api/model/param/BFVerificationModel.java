/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

/**
 * 作者：zwLiu<br>
 * 创建时间：2018年3月16日 <br>
 * 描述：宝付查证传入参数
 */
public class BFVerificationModel {
	/**
	 * 原始订单号
	 */
	private String origTransId;
	/**
	 * 原始交易时间
	 */
	private String origTradeDate;
	/**
	 *线上线下标识
	 *0  线下
	 *1 线上
	 */
	private String flag;
	
	/**
	 * 是否充值
	 */
	private String bfzcFlag;
	/**
	 *配置文件路径 
	 */
	private String path;
	
	public String getOrigTransId() {
		return origTransId;
	}
	public void setOrigTransId(String origTransId) {
		this.origTransId = origTransId;
	}
	public String getOrigTradeDate() {
		return origTradeDate;
	}
	public void setOrigTradeDate(String origTradeDate) {
		this.origTradeDate = origTradeDate;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "BFVerificationModel [origTransId=" + origTransId + ", origTradeDate=" + origTradeDate + ", flag=" + flag + ", path=" + path + "]";
	}
	public String getBfzcFlag() {
		return bfzcFlag;
	}
	public void setBfzcFlag(String bfzcFlag) {
		this.bfzcFlag = bfzcFlag;
	}

}
