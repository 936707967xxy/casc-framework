/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.model.vo;

import java.util.Date;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年12月25日 <br>
 * 描述：
 */
public class RollBackInfoVo {
	
	private Date handleTime;
	
	private String handleRemark;

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public String getHandleRemark() {
		return handleRemark;
	}

	public void setHandleRemark(String handleRemark) {
		this.handleRemark = handleRemark;
	}
	
	
}
