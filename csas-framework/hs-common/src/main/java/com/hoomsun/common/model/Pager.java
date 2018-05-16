/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.common.model;

import java.util.List;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月8日 <br>
 * 描述：分页工具
 */
public class Pager<T> {
	private List<T> pageData; // 当前页的数据
	private Integer page = 1; // 当前页的页码
	private Integer pageSize = 10; // 每页多少条数据
	private Integer recordCount; // 总记录数

	// private int pageCount; //总页数

	public int getPageCount() {
		if (recordCount % pageSize == 0) {
			return recordCount / pageSize;
		}
		return recordCount / pageSize + 1;
	}

	public Pager(List<T> pageData, Integer recordCount) {
		this.pageData = pageData;
		this.recordCount = recordCount;
	}

	public Pager() {
	}

	public boolean isFirst() {
		return page == 1 || recordCount == 0;
	}

	public boolean isLast() {
		return recordCount == 0 || page >= getPageCount();
	}

	/**
	 * 有没有下一页
	 * 
	 * @return
	 */
	public boolean isHasNext() {
		return page < getPageCount();
	}

	public boolean isHasPrev() {
		return page > 1;
	}

	/**
	 * 返回下一页的页码
	 * 
	 * @return
	 */
	public Integer getNextPage() {
		if (page >= getPageCount()) {
			return getPageCount();
		}
		return page + 1;
	}

	public Integer getPrevPage() {
		if (page <= 1) {
			return 1;
		}

		return page - 1;
	}

	public List<T> getPageData() {
		return pageData;
	}

	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}

}
