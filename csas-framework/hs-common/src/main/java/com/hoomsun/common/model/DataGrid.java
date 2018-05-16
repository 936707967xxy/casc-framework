/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.common.model;

import java.util.List;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月8日 <br>
 * 描述：
 */
public class DataGrid<T> {
	private Integer total;
	private List<T> rows;

	public DataGrid() {
	}

	public DataGrid(Integer total, List<T> rows) {
		this.total = total;
		this.rows = rows;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
