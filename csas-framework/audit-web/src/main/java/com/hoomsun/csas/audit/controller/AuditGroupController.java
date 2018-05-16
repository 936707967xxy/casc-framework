/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.anno.Permission;
import com.hoomsun.csas.audit.model.vo.GrantGroupUsers;
import com.hoomsun.csas.audit.server.inter.AuditGroupServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月14日 <br>
 * 描述：审批组的控制层
 */
@Controller
public class AuditGroupController {

	private AuditGroupServerI groupServer;

	@Autowired
	public void setGroupServer(AuditGroupServerI groupServer) {
		this.groupServer = groupServer;
	}

	@Permission("group_query")
	@RequestMapping(value = "/sys/auditgroup/page.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Pager<Group> findPagerData(Integer page, Integer rows, String groupName) {
		return groupServer.findPage(page, rows, groupName);
	}

	@Permission("group_add")
	@RequestMapping(value = "/sys/auditgroup/create.do", method = RequestMethod.POST)
	@ResponseBody
	public Json createGroup(String id, String name, String type, HttpServletRequest request) {
		return groupServer.createGroup(id, name, type);
	}

	@Permission("group_edit")
	@RequestMapping(value = "/sys/auditgroup/update.do", method = RequestMethod.POST)
	@ResponseBody
	public Json updateGroup(String id, String name, String type, HttpServletRequest request) {
		return groupServer.updateGroup(id, name, type);
	}

	@Permission("group_delete")
	@RequestMapping(value = "/sys/auditgroup/remove.do", method = RequestMethod.POST)
	@ResponseBody
	public Json deleteGroup(String groupId, HttpServletRequest request) {
		return groupServer.deleteGroup(groupId);
	}

	@Permission("group_bind")
	@RequestMapping(value = "/sys/auditgroup/bindview.do", method = RequestMethod.GET)
	@ResponseBody
	public List<User> bindView(String groupId, HttpServletRequest request) {
		List<User> users = groupServer.findUsers(groupId);
		return users;
	}
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述：之前支持部门添加的的(dongliang) 
	 * @param groupUsers
	 * @param request
	 * @return
	 */
//	@Permission("group_bind")
//	@RequestMapping(value = "/sys/auditgroup/binduser.do", method = RequestMethod.GET)
//	@ResponseBody
//	public Json bindUser(String groupId, String emps, HttpServletRequest request) {
//		return groupServer.bindUser(groupId, emps);
//	}
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 搜索员工添加的
	 * @param groupUsers
	 * @param request
	 * @return
	 */
	@Permission("group_bind")
	@RequestMapping(value = "/sys/auditgroup/binduser.do", method = RequestMethod.POST)
	@ResponseBody
	public Json bindUser(@RequestBody GrantGroupUsers groupUsers, HttpServletRequest request) {
		return groupServer.bindUserByUserId(groupUsers);
	}

}
