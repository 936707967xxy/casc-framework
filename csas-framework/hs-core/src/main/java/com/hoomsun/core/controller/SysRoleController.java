/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.model.TreeNode;
import com.hoomsun.core.anno.LoggerAnnotation;
import com.hoomsun.core.anno.Permission;
import com.hoomsun.core.enums.OptionType;
import com.hoomsun.core.model.SysRole;
import com.hoomsun.core.server.inter.SysRoleServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月11日 <br>
 * 描述：
 */
@Controller
public class SysRoleController {
	private SysRoleServerI roleServer;

	@Autowired
	public void setRoleServer(SysRoleServerI roleServer) {
		this.roleServer = roleServer;
	}

	@Permission("role_query")
	@RequestMapping(value = "/sys/role/listview.do", method = { RequestMethod.GET })
	public String roleView() {
		return "role/listview";
	}

	@Permission("role_query")
	@RequestMapping(value = "/sys/role/pager.do", method = { RequestMethod.POST })
	@ResponseBody
	public Pager<SysRole> findDataGrid(HttpServletRequest request,Integer page, Integer rows, String roleName) {
		return roleServer.findPage(page, rows, roleName);
	}

	@RequestMapping(value = "/sys/role/query.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public SysRole findById(String roleId, HttpServletRequest request) {
		return roleServer.findById(roleId);
	}
	
	
	@LoggerAnnotation(moduleName = "角色管理", option = "添加角色", optionType = OptionType.CREATE)
	@Permission("role_add")
	@RequestMapping(value = "/sys/role/create.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json createRole(SysRole role, HttpServletRequest request) {
		return roleServer.createRole(role);
	}

	@LoggerAnnotation(moduleName = "角色管理", option = "修改角色", selectMethod = "findById", idName = "roleId", idIndex = 0, beanId = "roleServer", optionType = OptionType.UPDATE)
	@Permission("role_edit")
	@RequestMapping(value = "/sys/role/update.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json updateRole(SysRole role, HttpServletRequest request) {
		return roleServer.updateRole(role);
	}

	@LoggerAnnotation(moduleName = "角色管理", option = "删除多个角色", selectMethod = "findById", idName = "roleId", idIndex = 0, beanId = "roleServer", optionType = OptionType.DELETE)
	@Permission("role_delete")
	@RequestMapping(value = "/sys/role/removemulti.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json removeMultiRole(@RequestBody List<String> roleIds, HttpServletRequest request) {
		return roleServer.deleteMultiRole(roleIds);
	}
	
	@LoggerAnnotation(moduleName = "角色管理", option = "删除角色", selectMethod = "findById", idName = "roleId", idIndex = 0, beanId = "roleServer", optionType = OptionType.DELETE)
	@Permission("role_delete")
	@RequestMapping(value = "/sys/role/remove.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json removeRole(String roleId, HttpServletRequest request) {
		return roleServer.deleteRole(roleId);
	}
	
	
	
	@Permission("role_grant")
	@RequestMapping(value = "/sys/role/permiss.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public List<String> grantView(String id, HttpServletRequest request) {
		List<String> cptIds = roleServer.findRoleCpt(id);
		return cptIds;
	}

	@LoggerAnnotation(moduleName = "角色管理", option = "角色授权", optionType = OptionType.GRANT)
	@Permission("role_grant")
	@RequestMapping(value = "/sys/role/grant.do", method = { RequestMethod.POST },consumes="application/*")
	@ResponseBody
	public Json grant(String roleId, String cptKeys) {
		String[] cpts = cptKeys.split(",");
		return roleServer.grantCpt(roleId, cpts);
	}

	@RequestMapping(value = "/sys/role/treenode.do", method = { RequestMethod.POST })
	@ResponseBody
	public List<TreeNode> findTreeNode() {
		return roleServer.findTreeNode();
	}
}
