/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月8日 <br>
 * 描述：树形下拉框
 */
public class ComboTree {
	/** 主鍵 */
	private String id;
	/** 上級ID */
	private String parentId;
	/** 显示内容 */
	private String text;
	/** 下级 */
	private List<ComboTree> children = new ArrayList<ComboTree>();

	public void addChildren(ComboTree comboTree){
		this.children.add(comboTree);
	}
	
	public ComboTree() {
	}

	public ComboTree(String id, String text) {
		this.id = id;
		this.text = text;
	}
	
	public ComboTree(String id, String parentId, String text) {
		this.id = id;
		this.parentId = parentId;
		this.text = text;
	}

	public ComboTree(String id, String parentId, String text, List<ComboTree> children) {
		this.id = id;
		this.parentId = parentId;
		this.text = text;
		this.children = children;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<ComboTree> getChildren() {
		return children;
	}

	public void setChildren(List<ComboTree> children) {
		this.children = children;
	}
}
