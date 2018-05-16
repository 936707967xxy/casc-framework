/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.common.model;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月8日 <br>
 * 描述：下拉列表框数据
 */
public class Combobox {
	/** 下拉框的实际值value */
	private String id; // 下拉框的实际值value
	/** 下拉框的页面显示值 */
	private String text; // 下拉框的页面显示值
	/** 是否选中 */
	private boolean selected = false; // 是否选中

	public Combobox() {
	}

	public Combobox(String id, String text) {
		this.id = id;
		this.text = text;
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

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
