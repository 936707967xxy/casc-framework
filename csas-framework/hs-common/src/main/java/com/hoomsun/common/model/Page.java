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
public class Page {
	private List<?> pageData; // 当前页的数据
	private int page = 1; // 当前页的页码
	private int pageSize = 10; // 每页多少条数据
	private int recordCount; // 总记录数

	// private int pageCount; //总页数

	/**
	 * 得到总页数
	 * 
	 * @return
	 */
	public int getPageCount() {
		if (recordCount % pageSize == 0) {
			return recordCount / pageSize;
		}
		return recordCount / pageSize + 1;
	}

	/**
	 * 判断是否是第一页
	 * 
	 * @return
	 */
	public boolean isFirst() {
		return page == 1 || recordCount == 0;
	}

	/**
	 * 是否是随后一页
	 * 
	 * @return
	 */
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

	/**
	 * 有没有上一页
	 * 
	 * @return
	 */
	public boolean isHasPrev() {
		return page > 1;
	}

	/**
	 * 返回下一页的页码
	 * 
	 * @return
	 */
	public int getNextPage() {
		if (page >= getPageCount()) {
			return getPageCount();
		}

		return page + 1;
	}

	/**
	 * 得到上一页的页码
	 * 
	 * @return
	 */
	public int getPrevPage() {
		if (page <= 1) {
			return 1;
		}

		return page - 1;
	}

	/**
	 * 得到页面展示数据对象的list
	 * 
	 * @return
	 */
	public List<?> getPageData() {
		return pageData;
	}

	/**
	 * 设置页面展示数据对象的list
	 * 
	 * @param pageData
	 */
	public void setPageData(List<?> pageData) {
		this.pageData = pageData;
	}

	/**
	 * 得到当前页码
	 * 
	 * @return
	 */
	public int getPage() {
		return page;
	}

	/**
	 * 设置当前页码
	 * 
	 * @param page
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * 得到每页显示数据条数
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页显示数据条数
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 得到总记录数
	 * 
	 * @return
	 */
	public int getRecordCount() {
		return recordCount;
	}

	/**
	 * 设置总记录数
	 * 
	 * @param recordCount
	 */
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
}
