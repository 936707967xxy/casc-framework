/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.model.param;

/**
 * 作者：zwLiu <br>
 * 创建时间：2018年4月27日 <br>
 * 描述：富友订单查证传参
 */
public class FyQrytransParam {
	/**
	 * 业务代码
	 */
	private String busicd;
	/**
	 * 原请求流水
	 */
	private String orderno;
	/**
	 * 开始日期yyyyMMdd
	 */
	private String startdt;
	/**
	 * 结束日期 yyyyMMdd
	 */
	private String enddt;
	/**
	 * 交易状态
	 */
	private String transst;
	
	/**
	 * 线上线下状态
	 */
	private String UpdownStatus;
	
	/**
	 * 文件项目路径
	 */
	private String path;
	
	public String getBusicd() {
		return busicd;
	}
	public void setBusicd(String busicd) {
		this.busicd = busicd;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getStartdt() {
		return startdt;
	}
	public void setStartdt(String startdt) {
		this.startdt = startdt;
	}
	public String getEnddt() {
		return enddt;
	}
	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}
	public String getTransst() {
		return transst;
	}
	public void setTransst(String transst) {
		this.transst = transst;
	}
	public String getUpdownStatus() {
		return UpdownStatus;
	}
	public void setUpdownStatus(String updownStatus) {
		UpdownStatus = updownStatus;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
	
}
