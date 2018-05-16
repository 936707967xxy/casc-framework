package com.hoomsun.app.api.model;

public class House {
	
	    private String houseId;

	    private String provinceid;

	    private String province;

	    private String city;

	    private String uniqueKey;   //标识

	    private String loginType;  //登陆方式

	    private String note;

	    private String crawlerSign;
	    
	    private String img_url;

	    private String login_url;
	    
	    

		public House() {
			super();
		}

		public String getHouseId() {
			return houseId;
		}

		public void setHouseId(String houseId) {
			this.houseId = houseId;
		}

		public String getProvinceid() {
			return provinceid;
		}

		public void setProvinceid(String provinceid) {
			this.provinceid = provinceid;
		}

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getUniqueKey() {
			return uniqueKey;
		}

		public void setUniqueKey(String uniqueKey) {
			this.uniqueKey = uniqueKey;
		}

		public String getLoginType() {
			return loginType;
		}

		public void setLoginType(String loginType) {
			this.loginType = loginType;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		public String getCrawlerSign() {
			return crawlerSign;
		}

		public void setCrawlerSign(String crawlerSign) {
			this.crawlerSign = crawlerSign;
		}

		public String getImg_url() {
			return img_url;
		}

		public void setImg_url(String img_url) {
			this.img_url = img_url;
		}

		public String getLogin_url() {
			return login_url;
		}

		public void setLogin_url(String login_url) {
			this.login_url = login_url;
		}
	    
	    
	    
}
