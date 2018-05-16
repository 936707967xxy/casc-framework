/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.model.vo;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年12月29日 <br>
 * 描述：
 */
public class NoticeMsg {

	private String proName;
	
	private String proAlias; // 申请产品别名
	
	private String agreeMount;
	
	private String agreeProAlias; // 同意产品别名
	
	private String remark;

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getAgreeMount() {
		return agreeMount;
	}

	public void setAgreeMount(String agreeMount) {
		this.agreeMount = agreeMount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProAlias() {
		return proAlias;
	}

	public void setProAlias(String proAlias) {
		this.proAlias = proAlias;
	}

	public String getAgreeProAlias() {
		return agreeProAlias;
	}

	public void setAgreeProAlias(String agreeProAlias) {
		this.agreeProAlias = agreeProAlias;
	}
	
	
}
