/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.model.vo;

/**
 * 作者：liushuai <br>
 * 创建时间：2018年1月8日 <br>
 * 描述：
 */
public class AnnexTypeVo {
	private int type;
	
	private String name;
	
	public AnnexTypeVo() {
		
	}

	
	public AnnexTypeVo(int type, String name) {
		this.type = type;
		this.name = name;
	}

	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
