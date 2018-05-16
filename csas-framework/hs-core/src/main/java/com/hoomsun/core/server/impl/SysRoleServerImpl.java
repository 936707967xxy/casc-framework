/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.DataGrid;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.model.TreeNode;
import com.hoomsun.core.dao.SysEmpRoleMapper;
import com.hoomsun.core.dao.SysRoleCptMapper;
import com.hoomsun.core.dao.SysRoleMapper;
import com.hoomsun.core.dao.SysRoleResourcesMapper;
import com.hoomsun.core.model.SysRole;
import com.hoomsun.core.model.SysRoleCpt;
import com.hoomsun.core.model.SysRoleResources;
import com.hoomsun.core.server.inter.SysRoleServerI;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月10日 <br>
 * 描述：
 */
@Service("roleServer")
public class SysRoleServerImpl implements SysRoleServerI {
	private SysRoleMapper roleMapper;
	private SysRoleResourcesMapper roleResourcesMapper;
	private SysEmpRoleMapper empRoleMapper;
	private SysRoleCptMapper roleCptMapper;

	@Value("${SYSTEM_NAME}")
	private String systemName;

	@Autowired
	public void setRoleMapper(SysRoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

	@Autowired
	public void setRoleResourcesMapper(SysRoleResourcesMapper roleResourcesMapper) {
		this.roleResourcesMapper = roleResourcesMapper;
	}

	@Autowired
	public void setEmpRoleMapper(SysEmpRoleMapper empRoleMapper) {
		this.empRoleMapper = empRoleMapper;
	}

	@Autowired
	public void setRoleCptMapper(SysRoleCptMapper roleCptMapper) {
		this.roleCptMapper = roleCptMapper;
	}

	@Override
	public Json createRole(SysRole role) {
		if (StringUtils.isBlank(role.getRoleId())) {
			role.setRoleId(PrimaryKeyUtil.getPrimaryKey());
		}
		role.setAscription(this.systemName); // 添加归属
		int result = roleMapper.insertSelective(role);
		if (result > 0) {
			return new Json(true, "角色添加成功!");
		} else {
			return new Json(true, "角色添加失败!");
		}
	}

	@Override
	public Json updateRole(SysRole role) {
		role.setAscription(this.systemName); // 添加归属
		int result = roleMapper.updateByPrimaryKeySelective(role);
		if (result > 0) {
			return new Json(true, "角色修改成功!");
		} else {
			return new Json(true, "角色修改失败!");
		}
	}

	@Override
	public Json deleteRole(String roleId) {
		int result = roleResourcesMapper.deleteByRoleId(roleId);
		result += roleCptMapper.deleteByRoleId(roleId);
		result += empRoleMapper.deleteByRoleId(roleId);
		result += roleMapper.deleteByPrimaryKey(roleId);
		if (result > 0) {
			return new Json(true, "角色删除成功!");
		} else {
			return new Json(true, "角色删除失败!");
		}
	}

	@Override
	public SysRole findById(String roleId) {
		return roleMapper.selectById(roleId);
	}

	@Override
	public DataGrid<SysRole> findPageData(Integer page, Integer rows, String roleName) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (null != page && rows != null) {
			param.put("pageIndex", (page - 1) * rows);
			param.put("pageSize", rows);
		}
		if (!StringUtils.isBlank(roleName)) {
			param.put("roleName", "%" + roleName + "%");
		}
		param.put("systemName", this.systemName);
		List<SysRole> roles = roleMapper.findPageData(param);
		int total = roleMapper.findPageCount(param);
		return new DataGrid<SysRole>(total, roles);
	}

	@Override
	public Pager<SysRole> findPage(Integer page, Integer rows, String roleName) {
		Map<String, Object> param = new HashMap<String, Object>();
		// if (null != page && rows != null) {
		// rows = rows > 200 ? 100 : rows;
		// param.put("pageIndex", page);
		// param.put("pageSize", rows);
		// }
		if (null != page && rows != null) {
			rows = rows > 200 ? 200 : rows;
		} else {
			page = 1; // 关于这里是否需要给出默认值，与前端框架有关系
			rows = 10;
		}
		param.put("pageIndex", page);
		param.put("pageSize", rows);

		if (!StringUtils.isBlank(roleName)) {
			param.put("roleName", "%" + roleName + "%");
		}
		param.put("systemName", this.systemName);
		List<SysRole> roles = roleMapper.findPageData(param);
		int total = roleMapper.findPageCount(param);
		return new Pager<SysRole>(roles, total);
	}

	@Override
	public Json grant(String roleId, String[] resIds) {
		if (resIds == null || resIds.length < 1) {// 没有授权数据 删除
			roleCptMapper.deleteByRoleId(roleId);
			return new Json(true, "已取消该角色的所有权限!");
		}

		List<SysRoleResources> roleResources = roleResourcesMapper.findByRoleId(roleId);
		List<String> oldResIds = new ArrayList<String>();
		List<String> newResIds = Arrays.asList(resIds);
		// 现在有原来没有添加 现在有原来有不做处理 原来有现在没有删除
		List<String> removeResIds = new ArrayList<String>();
		for (SysRoleResources res : roleResources) {
			String resId = res.getResId();
			oldResIds.add(resId);
			if (newResIds.contains(resId)) {// 如果新授权的资源集合中有原有的资源信息
				continue;
			} else {
				removeResIds.add(resId);
			}
		}

		List<SysRoleResources> addRoleRes = new ArrayList<SysRoleResources>();
		for (String resId : newResIds) {
			if (oldResIds.contains(resId)) {
				continue;
			} else {
				SysRoleResources sysRoleResources = new SysRoleResources();
				sysRoleResources.setResId(resId);
				sysRoleResources.setRoleId(roleId);
				addRoleRes.add(sysRoleResources);
			}
		}
		
		int result = 0;
		if (null != removeResIds && removeResIds.size() > 0) {
			result += roleResourcesMapper.batchDeleteByResIds(removeResIds);
		}
		if (null != addRoleRes && addRoleRes.size() > 0) {
			result += roleResourcesMapper.batchInsert(addRoleRes);
		}
		if (result > 0) {
			return new Json(true, "授权成功!");
		} else {
			return new Json(true, "授权失败!");
		}
	}

	@Override
	public SysRole findByName(String roleName) {
		return roleMapper.findByName(roleName);
	}

	@Override
	public List<TreeNode> findTreeNode() {
		List<SysRole> roles = roleMapper.findAllData(this.systemName);
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		// nodes.add(new TreeNode("-1", "所有角色", null));
		for (SysRole role : roles) {
			nodes.add(new TreeNode(role.getRoleId(), role.getRoleName(), "-1"));
		}
		return nodes;
	}

	@Override
	public SysRole findRoleResources(String roleId) {
		return roleMapper.findRoleResources(roleId);
	}

	// 角色的组件的唯一标识集合
	@Override
	public List<String> findRoleCpt(String id) {
		return roleMapper.findRoleCpt(id, this.systemName);
	}

	// 组件授权方式
	@Override
	public Json grantCpt(String roleId, String[] cpts) {
		List<SysRoleCpt> roleCpts = roleCptMapper.findByRoleId(roleId);
		List<String> oldCptIds = new ArrayList<String>();
		List<String> newCptIds = Arrays.asList(cpts);

		// 现在有原来没有添加 现在有原来有不做处理 原来有现在没有删除
		List<String> removeCptIds = new ArrayList<String>();
		for (SysRoleCpt res : roleCpts) {
			String cptId = res.getCptId();
			if (!StringUtils.isBlank(cptId)) {
				oldCptIds.add(cptId);
				if (newCptIds.contains(cptId)) {// 如果新授权的资源集合中有原有的资源信息
					continue;
				} else {
					removeCptIds.add(cptId);
				}
			}
		}

		List<SysRoleCpt> addCpts = new ArrayList<SysRoleCpt>();
		for (String resId : newCptIds) {
			if (!StringUtils.isBlank(resId)) {
				if (oldCptIds.contains(resId)) {
					continue;
				} else {
					SysRoleCpt roleCpt = new SysRoleCpt();
					roleCpt.setCptId(resId);
					roleCpt.setRoleId(roleId);
					addCpts.add(roleCpt);
				}
			}
		}

		int result = 0;
		if (null != removeCptIds && removeCptIds.size() > 0) {
			result += roleCptMapper.batchDeleteByCptIds(removeCptIds);
		}

		if (null != addCpts && addCpts.size() > 0) {
			result += roleCptMapper.batchInsert(addCpts);
		}
		if (result > 0) {
			return new Json(true, "授权成功!");
		} else {
			return new Json(true, "授权失败!");
		}
	}

	@Override
	public Json deleteMultiRole(List<String> roleIds) {
		int result = roleMapper.deleteByMultiRoles(roleIds);
		if (result > 0) {
			return new Json(true, "删除多个角色成功!");
		} else {
			return new Json(false, "删除多个角色失败!");
		}
	}
}
