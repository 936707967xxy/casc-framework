/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.model.vo;

import java.util.List;

import com.hoomsun.csas.sales.api.model.UserContacts;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月7日 <br>
 * 描述：通话详单
 */
public class CallBillsVo {
	//联系人
	private List<UserContacts> contacts;
	//电话核查数据
	
	public List<UserContacts> getContacts() {
		return contacts;
	}

	public void setContacts(List<UserContacts> contacts) {
		this.contacts = contacts;
	}

}
