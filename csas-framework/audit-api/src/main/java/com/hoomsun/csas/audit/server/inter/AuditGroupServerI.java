/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.inter;

import java.util.List;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.audit.model.vo.GrantGroupUsers;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月14日 <br>
 * 描述：审批组的业务业务接口
 */
public interface AuditGroupServerI {

	public Pager<Group> findPage(Integer page, Integer rows, String gropName);

	Json createGroup(String groupKey, String groupName, String groupType);

	Json updateGroup(String groupKey, String groupName, String groupType);

	Json deleteGroup(String groupId);

	List<User> findUsers(String groupId);

	Json bindUser(String groupId, String emps);
	
	Json bindUserByUserId(GrantGroupUsers groupUsers);
}
