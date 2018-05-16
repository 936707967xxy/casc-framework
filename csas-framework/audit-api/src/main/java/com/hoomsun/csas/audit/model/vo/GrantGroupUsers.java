/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.model.vo;


import java.util.List;

import org.activiti.engine.identity.User;
/**
 * 作者：liushuai <br>
 * 创建时间：2017年12月11日 <br>
 * 描述：
 */
public class GrantGroupUsers {
	
	private String groupId;
	
	private List<User> users;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
