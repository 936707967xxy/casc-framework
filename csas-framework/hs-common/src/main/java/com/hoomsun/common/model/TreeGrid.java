/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月8日 <br>
 * 描述：easyui 树形表格 数据结构
 */
public class TreeGrid {
	private String id;

	private String parentId;// 上级菜单

	private String name; // 资源名

	private String type; // 资源类型

	private String valu; // 资源值

	private String icon; // 图标

	private String url; // 地址

	private Integer levels; // 菜单层级

	private Integer sort;// 排序值

	private List<TreeGrid> children = new ArrayList<TreeGrid>();

	public TreeGrid() {

	}

	public TreeGrid(String id, String parentId, String name, String type, String valu, String icon, String url, Integer levels, Integer sort, List<TreeGrid> children) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.type = type;
		this.valu = valu;
		this.icon = icon;
		this.url = url;
		this.levels = levels;
		this.sort = sort;
		this.children = children;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValu() {
		return valu;
	}

	public void setValu(String valu) {
		this.valu = valu;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getLevels() {
		return levels;
	}

	public void setLevels(Integer levels) {
		this.levels = levels;
	}

	public List<TreeGrid> getChildren() {
		return children;
	}

	public void setChildren(List<TreeGrid> children) {
		this.children = children;
	}
}
