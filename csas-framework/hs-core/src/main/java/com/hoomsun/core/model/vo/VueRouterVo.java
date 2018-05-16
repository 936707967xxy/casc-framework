/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.model.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月16日 <br>
 * 描述：界面的路由数据展示
 */
public class VueRouterVo {
	private String key;
	private String path = "/";
	private String component = "Home";
	private String name;
	private boolean hidden = false;
	private String iconCls;
	private String parent;
	private boolean dropdown = false;
	private String redirect;
	private String type;
	private List<VueRouterVo> children = new ArrayList<VueRouterVo>();

	public void addChildren(VueRouterVo router) {
		this.children.add(router);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public List<VueRouterVo> getChildren() {
		return children;
	}

	public void setChildren(List<VueRouterVo> children) {
		this.children = children;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public boolean isDropdown() {
		return dropdown;
	}

	public void setDropdown(boolean dropdown) {
		this.dropdown = dropdown;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
