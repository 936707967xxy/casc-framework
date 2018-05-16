/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月10日 <br>
 * 描述：菜单的实体
 */
public class Menu {
	private String id;// 主键
	private String name;// 名字
	private String url;// 路径
	private String icon; // 图标
	private String parentId;// 父级
	private Integer sort;
	private List<Menu> children = new ArrayList<Menu>(); // 下级菜单

	public void addChildren(Menu menu) {
		this.children.add(menu);
	}

	public Menu() {

	}

	public Menu(String id) {
		this.id = id;
	}

	public Menu(String id, String name, String url, String icon, Integer sort) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.icon = icon;
		this.sort = sort;
	}

	public Menu(String id, String name, String url, String icon, List<Menu> children) {
		this.id = id;
		this.name = name;
		this.url = url;
		this.icon = icon;
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
