/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.model.vo;

/**
 * 作者：liushuai <br>
 * 创建时间：2018年1月9日 <br>
 * 描述：用于公司，部门，职位级联查询加载
 */
public class VueLazyTree {
	private String nodeKey; // 主键ID
	
	private String name; // ID对应名称
	
	public String getNodeKey() {
		return nodeKey;
	}
	
	public void setNodeKey(String nodeKey) {
		this.nodeKey = nodeKey;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
