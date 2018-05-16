/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月8日 <br>
 * 描述：树形结构
 */
public class TreeNode {
	/** 实际值 */
	private String id;
	/** 显示值 */
	private String text; // 描述
	/** 显示样式 */
	private String iconCls;// 图标
	/** 是否选中 */
	private boolean checked;// 是否选中
	/** 状态 打开 关闭 */
	private String state = "open"; // 状态 打开 关闭
	/** 属性 */
	private Object attributes; // 属性
	/** 上级资源 */
	private String parentId; // 上级资源
	/** 下级资源 */
	private List<TreeNode> children = new ArrayList<TreeNode>(); // 子资源

	public TreeNode() {
	}

	public TreeNode(String id, String text, String parentId) {
		this.id = id;
		this.text = text;
		this.parentId = parentId;
	}

	public void addChild(TreeNode node) {
		children.add(node);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Object getAttributes() {
		return attributes;
	}

	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
