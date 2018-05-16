/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hoomsun.core.model.SysCompany;
import com.hoomsun.core.model.vo.Tree;

/**
 * 作者：liushuai <br>
 * 创建时间：2018年1月9日 <br>
 * 描述：treeData 工具， 要求Mapper中的返回值是tree格式
 */
public class TreeData {
	public <T> List<Tree> findTree(List<T> cpts) {
		if (null == cpts || cpts.size() < 1) {
			return null;
		}
		
		List<Tree> trees = new ArrayList<Tree>();
		List<Tree> children = new ArrayList<Tree>();
		for (T cpt : cpts) {
			try {
				String nodeKey = ((Tree) cpt).getNodeKey();
				String label =((Tree) cpt).getLabel();
				Tree tree = new Tree();
				tree.setNodeKey(nodeKey);
				tree.setLabel(label);

				String parentId = ((Tree) cpt).getParent();
				if (StringUtils.isBlank(parentId)) {// 若为空则为一级
					trees.add(tree);
				} else {// 为子集
					tree.setParent(parentId);
					children.add(tree);
				}
			} catch (Exception e) {
				return null;
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
			}
		}
	}
	
	public static void main(String[] args) {
		List<SysCompany> coms = new ArrayList<SysCompany>();
		coms.add(new SysCompany());
		coms.add(new SysCompany());
		coms.add(new SysCompany());
		List<Tree> findTree = (new TreeData()).findTree(coms);
		System.out.println(findTree);
	}
}
