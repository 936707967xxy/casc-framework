package com.hoomsun.app.api.enums;

public enum IpUrlEnum {

	//DATA_IP("http://117.34.70.217:8080"), 
	DATA_IP("http://117.34.70.217:8080"), 
	//DATA_MODE_IP("http://117.34.70.217:8089"), 
	DATA_MODE_IP("http://117.34.70.217:8089"),
	// http://10.1.1.12:8089
	//HSFS_IP("http://192.168.3.19:8080");
	HSFS_IP("http://113.200.105.35:9093");
	
	
	private IpUrlEnum(String ipUrl) {
		this.ipUrl = ipUrl;
	}

	private String ipUrl;

	public String getIpUrl() {
		return ipUrl;
	}

	public void setIpUrl(String ipUrl) {
		this.ipUrl = ipUrl;
	}
	
	
	
}
