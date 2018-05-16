/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.audit.model.vo.GrantGroupUsers;
import com.hoomsun.csas.audit.server.inter.AuditGroupServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月14日 <br>
 * 描述：审批组业务的实现
 */
@Service("groupServer")
public class AuditGroupServerImpl implements AuditGroupServerI {

	private IdentityService identityService;

	@Autowired
	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	@Override
	public Pager<Group> findPage(Integer page, Integer rows, String gropName) {
		GroupQuery groupQuery = identityService.createGroupQuery();
		if (!StringUtils.isBlank(gropName)) {
			groupQuery.groupNameLike(gropName);
		}
		List<Group> groups = groupQuery.listPage((page - 1) * rows, rows);
		int total = (int) groupQuery.count();

		return new Pager<Group>(groups, total);
	}

	@Override
	public Json createGroup(String groupKey, String groupName, String groupType) {
		if (StringUtils.isAllBlank(groupKey, groupName)) {
			return new Json(false, "参数异常!");
		}
		Group group = identityService.newGroup(groupKey);
		group.setName(groupName);
		group.setType(groupType);
		identityService.saveGroup(group);
		return new Json(true, "添加成功!");
	}

	@Override
	public Json updateGroup(String groupKey, String groupName, String groupType) {
		if (StringUtils.isAllBlank(groupKey, groupName)) {
			return new Json(false, "修改失败!参数异常");
		}
		Group group = identityService.createGroupQuery().groupId(groupKey).singleResult();
		if (group == null) {
			group = identityService.newGroup(groupKey);
		}
		group.setName(groupName);
		group.setType(groupType);
		identityService.saveGroup(group);
		return new Json(true, "修改成功!");
	}

	@Override
	public Json deleteGroup(String groupId) {
		identityService.deleteGroup(groupId);
		return new Json(true, "删除成功!");
	}

	@Override
	public List<User> findUsers(String groupId) {
		if (StringUtils.isBlank(groupId)) {
			return null;
		}
		return identityService.createUserQuery().memberOfGroup(groupId).list();
	}

	@Override
	public Json bindUser(String groupId, String emps) {
		if (StringUtils.isBlank(groupId)) {
			return new Json(false, "绑定失败!参数异常!");
		}
		
		List<User> users = findUsers(groupId);
		List<String> userIds = new ArrayList<String>();
		for (User user : users) {
			userIds.add(user.getId());
		}
		JSONArray jsonArray = JSONArray.parseArray(emps);
		List<String> newIds = new ArrayList<String>();
		// 要添加的 现在有原来没有
		List<User> addUser = new ArrayList<User>();
		for (Object object : jsonArray) {
			JSONObject jsonObject = JSONObject.parseObject(object.toString());
			String empId = jsonObject.getString("empId");
			newIds.add(empId);
			if (userIds.contains(empId)) {// 原来的保含现在的用户
				continue;
			} else {
				String empName = jsonObject.getString("empName");
				User user = identityService.newUser(empId);
				user.setFirstName(empName);
				addUser.add(user);
			}
		}

		// 找出要删除的 原来有现在没有
		List<String> delUser = new ArrayList<String>();
		for (String id : userIds) {
			if (newIds.contains(id)) {// 现在的包含原来的 不做处理
				continue;
			} else {
				delUser.add(id);
			}
		}

		if (addUser != null && addUser.size() > 0) {
			for (User user : addUser) {
				User oldUser = identityService.createUserQuery().userId(user.getId()).singleResult();
				if (oldUser == null) {
					identityService.saveUser(user);
				}
				identityService.createMembership(user.getId(), groupId);
			}
		}

		if (delUser != null && delUser.size() > 0) {
			for (String id : delUser) {
				identityService.deleteMembership(id, groupId);
				// identityService.deleteUser(id);
			}
		}
		return new Json(true, "绑定成功!");
	}

	@Override
	public Json bindUserByUserId(GrantGroupUsers groupUsers) {
		String groupId = groupUsers.getGroupId();
		
		if (StringUtils.isBlank(groupId)) {
			return new Json(false, "绑定失败!参数异常!");
		}
		
		List<User> oldUsers = findUsers(groupId);
		List<String> userIds = new ArrayList<String>();
		for (User user : oldUsers) {
			userIds.add(user.getId());
		}
		
		List<User> usersIn = groupUsers.getUsers();
		List<String> newIds = new ArrayList<String>();
		
		// 要添加的 现在有原来没有
		List<User> addUser = new ArrayList<User>();
		for (User object : usersIn) {
			JSONObject jsonObject = JSONObject.parseObject(object.toString());
			String empId = jsonObject.getString("id");
			newIds.add(empId);
			if (userIds.contains(empId)) {// 原来的保含现在的用户
				continue;
			} else {
				String empName = jsonObject.getString("firstName");
				User user = identityService.newUser(empId);
				user.setFirstName(empName);
				addUser.add(user);
			}
		}

		// 找出要删除的 原来有现在没有
		List<String> delUser = new ArrayList<String>();
		for (String id : userIds) {
			if (newIds.contains(id)) {// 现在的包含原来的 不做处理
				continue;
			} else {
				delUser.add(id);
			}
		}

		if (addUser != null && addUser.size() > 0) {
			for (User user : addUser) {
				User oldUser = identityService.createUserQuery().userId(user.getId()).singleResult();
				if (oldUser == null) {
					identityService.saveUser(user);
				}
				identityService.createMembership(user.getId(), groupId);
			}
		}

		if (delUser != null && delUser.size() > 0) {
			for (String id : delUser) {
				identityService.deleteMembership(id, groupId);
				// identityService.deleteUser(id);
			}
		}
		return new Json(true, "绑定成功!");
	}

}
