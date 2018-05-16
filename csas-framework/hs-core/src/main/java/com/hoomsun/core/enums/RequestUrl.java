/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.enums;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年9月12日 <br>
 * 描述：存储请求URL的枚举类     app提交时添加图片路径
 * 
 */
public enum RequestUrl {

	 
	//IP2("hsfsSource", "http://117.34.70.212:8080");
	IP2("hsfsSource","http://113.200.105.35:9093");
	private RequestUrl(String ip, String path) {
		this.ip = ip;
		this.path = path;
	}
	
	private String ip;
    private String path;
	
    public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
