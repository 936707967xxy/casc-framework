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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.dao.SysComponentsMapper;
import com.hoomsun.core.model.SysComponents;
import com.hoomsun.core.model.vo.Tree;
import com.hoomsun.core.server.inter.SysComponentsServerI;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：liusong <br>
 * 创建时间：2017年11月16日 <br>
 * 描述：
 */
@Service("componentsServer")
public class SysComponentsServerImpl implements SysComponentsServerI {
	@Value("${SYSTEM_NAME}")
	private String systemName;
	
	private SysComponentsMapper componentsMapper;
	
	@Autowired
	public void setComponentsMapper(SysComponentsMapper componentsMapper) {
		this.componentsMapper = componentsMapper;
	}

	@Override
	public Json createComponents(SysComponents record) {
		if (StringUtils.isBlank(record.getCptId())) {
			record.setCptId(PrimaryKeyUtil.getPrimaryKey());
		}
		record.setAscription(systemName);
		int result = componentsMapper.insertSelective(record);
		if (result > 0) {
			return new Json(true, "组件添加成功!");
		} else {
			return new Json(true, "组件添加失败!");
		}
	}

	@Override
	public Json updateComponents(SysComponents record) {
		int result = componentsMapper.updateByPrimaryKeySelective(record);
		if (result > 0) {
			return new Json(true, "组件修改成功!");
		} else {
			return new Json(true, "组件修改失败!");
		}
	}

	@Override
	public Json deleteComponents(String componentsId) {
		int result = componentsMapper.deleteByPrimaryKey(componentsId);
		if (result > 0) {
			return new Json(true, "组件删除成功!");
		} else {
			return new Json(true, "组件删除失败!");
		}
	}

	@Override
	public SysComponents findById(String componentsId) {
		return componentsMapper.selectByCptId(componentsId);
	}

	@Override
	public List<SysComponents> findAll() {
		return componentsMapper.findAll(this.systemName);
	}

	@Override
	public Pager<SysComponents> findPageData(Integer page, Integer rows, String name) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (page == null || rows == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		rows = rows > 50 ? 50 : rows;

		param.put("pageIndex", (page - 1) * rows);
		param.put("pageSize", rows);

		if (!StringUtils.isBlank(name)) {
			param.put("name", "%" + name + "%");
		}
		param.put("systemName", this.systemName);
		List<SysComponents> components = componentsMapper.findPageData(param);
		int total = componentsMapper.findPageCount(param);
		return new Pager<SysComponents>(components, total);
	}

	@Override
	public List<SysComponents> findByParent() {
		List<SysComponents> result = new ArrayList<SysComponents>();
		List<SysComponents> findAll = componentsMapper.findAll(this.systemName);
		if (null != findAll && findAll.size() > 0) {
			for (SysComponents sysComponents : findAll) {
				String parentId = sysComponents.getParentId();
				if (StringUtils.isBlank(parentId)) {
					return findAll;
				} else {
					result.addAll(findAll);
					return result;
				}
			}
		}
		return null;
	}

	@Override
	public List<Tree> findTree(String name) {
		if (!StringUtils.isBlank(name)) {
			name = "%" + name + "%";
		}

		List<SysComponents> cpts = componentsMapper.findByName(name,this.systemName);
		if (null == cpts || cpts.size() < 1) {
			return null;
		}

		List<Tree> trees = new ArrayList<Tree>();
		List<Tree> children = new ArrayList<Tree>();
		for (SysComponents cpt : cpts) {
			String id = cpt.getCptId();
			String cptName = cpt.getName();

			Tree tree = new Tree();
			tree.setNodeKey(id);
			tree.setLabel(cptName);

			Integer hid = cpt.getHidden();
			if (hid == 1) {// 操作

			}

			String parentId = cpt.getParentId();
			if (StringUtils.isBlank(parentId)) {// 若为空则为一级
				trees.add(tree);
			} else {// 为子集
				tree.setParent(parentId);
				children.add(tree);
			}
		}
		buildChildren(trees, children);
		return trees;
	}

	public static void buildChildren(List<Tree> parent, List<Tree> children) {
		if (null == children || children.isEmpty() || children.size() < 1) {
			return;
		}

		List<Tree> childs = new ArrayList<Tree>();
		for (Tree cld : children) {
			childs.add(cld);
		}
		
		for (Tree tree : parent) {
			List<Tree> trees = new ArrayList<Tree>();
			String key = tree.getNodeKey();
			for (Tree cld : children) {
				String parentId = cld.getParent();
				if (key.equals(parentId)) {
					tree.addChildren(cld);
					trees.add(cld);
					childs.remove(cld);
				}
			}
			if (trees != null && trees.size() > 0) {
				buildChildren(trees, childs);
//				tree.setChildren(trees);
			}
		}
	}

	@Override
	public Json deleteBatchComponents(String[] ids) {
		for (String id : ids) {
			componentsMapper.deleteByPrimaryKey(id);
		}
		return new Json(true, "组件删除成功!");
	}
}
