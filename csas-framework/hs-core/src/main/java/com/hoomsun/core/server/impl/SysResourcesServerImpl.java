/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.ComboTree;
import com.hoomsun.common.model.DataGrid;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.TreeNode;
import com.hoomsun.core.dao.SysResourcesMapper;
import com.hoomsun.core.dao.SysRoleResourcesMapper;
import com.hoomsun.core.model.SysResources;
import com.hoomsun.core.server.inter.SysResourcesServerI;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月10日 <br>
 * 描述：系统资源管理的业务实现
 */
@Service("resourcesServer")
public class SysResourcesServerImpl implements SysResourcesServerI{

	private SysResourcesMapper resourcesMapper;
	private SysRoleResourcesMapper roleResourcesMapper;

	@Autowired
	public void setResourcesMapper(SysResourcesMapper resourcesMapper) {
		this.resourcesMapper = resourcesMapper;
	}
	
	@Autowired
	public void setRoleResourcesMapper(SysRoleResourcesMapper roleResourcesMapper) {
		this.roleResourcesMapper = roleResourcesMapper;
	}



	@Override
	public Json createResources(SysResources resources) {
		if (StringUtils.isBlank(resources.getResId())) {
			resources.setResId(PrimaryKeyUtil.getPrimaryKey());
		} 
		int result = resourcesMapper.insertSelective(resources);
		if (result > 0) {
			return new Json(true, "系统资源添加成功!");
		} else {
			return new Json(true, "系统资源添加失败!");
		}
	}

	@Override
	public Json updateResources(SysResources resources) {
		int result = resourcesMapper.updateByPrimaryKeySelective(resources);
		if (result > 0) {
			return new Json(true, "系统资源修改成功!");
		} else {
			return new Json(true, "系统资源修改失败!");
		}
	}

	@Override
	public Json deleteResources(String resId) {
		int result = roleResourcesMapper.deleteByResId(resId);
		result += resourcesMapper.deleteByPrimaryKey(resId);
		if (result > 0) {
			return new Json(true, "系统资源删除成功!");
		} else {
			return new Json(true, "系统资源删除失败!");
		}
	}

	@Override
	public SysResources findById(String resId) {
		return resourcesMapper.selectByPrimaryKey(resId);
	}

	@Override
	public DataGrid<SysResources> findPageData(Integer page, Integer rows, String resName) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (page == null || rows == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		if (null != page && null != rows) {
			rows = rows > 50 ? 50 : rows;
			param.put("pageIndex", (page-1)*rows);
			param.put("pageSize", rows);
		}
		if (!StringUtils.isBlank(resName)) {
			param.put("resName", "%"+resName+"%");
		}
		List<SysResources> resources = resourcesMapper.findPageData(param);
		int total = resourcesMapper.findPageCount(param);
		return new DataGrid<SysResources>(total, resources);
	}

	@Override
	public List<ComboTree> findComboTree() {
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		comboTrees.add(new ComboTree("-1", "", "请选择"));
		List<SysResources> resources = findAllData();
		if (resources != null && comboTrees.size() > 0) {
			for (SysResources res : resources) {
				String parentId = res.getResParent();
				ComboTree ct;
				if (StringUtils.isBlank(parentId)) {
					ct = new ComboTree(res.getResId(), res.getResName());
				} else {
					ct = new ComboTree(res.getResId(), parentId, res.getResName());
				}
				comboTrees.add(ct);
			}
		}
		return comboTrees;
	}

	@Override
	public List<SysResources> findAllData() {
		return resourcesMapper.findAllData();
	}

	@Override
	public List<TreeNode> findTreeNode() {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		List<SysResources> resources = findAllData();
		for (SysResources res : resources) {
			TreeNode node = new TreeNode();
			node.setId(res.getResId());
			node.setIconCls(res.getResIcon());
			
			String type = res.getResType();
			type = SysResources.TYPE_MENU.equalsIgnoreCase(type) ? "菜单" :"操作";
			node.setText(res.getResName() + "<span style='color:#bbb'>["+ type +"]</span>");
			
			if (!StringUtils.isBlank(res.getResParent())) {
				node.setParentId(res.getResParent());
			}
			nodes.add(node);
		}
		return nodes;
	}
}
