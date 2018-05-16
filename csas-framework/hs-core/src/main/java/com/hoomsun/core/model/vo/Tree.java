/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.model.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月21日 <br>
 * 描述：
 */
public class Tree {
	private String nodeKey;
	private String label;
	private String parent;
	private List<Tree> children = new ArrayList<Tree>();

	public void addChildren(Tree tree) {
		this.children.add(tree);
	}

	public String getNodeKey() {
		return nodeKey;
	}

	public void setNodeKey(String nodeKey) {
		this.nodeKey = nodeKey;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "Tree [nodeKey=" + nodeKey + ", label=" + label + ", children=" + children + "]";
	}

}
