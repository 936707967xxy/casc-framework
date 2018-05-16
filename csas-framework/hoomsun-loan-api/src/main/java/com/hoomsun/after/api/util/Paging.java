/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.util;

import org.apache.ibatis.session.RowBounds;



/**
 * 作者：Administrator <br>
 * 创建时间：2018年4月8日 <br>
 * 描述： Mybatis分页插件
 *
 */
public class Paging extends RowBounds{
	private Integer page = 1; // 当前页的页码
	private Integer pageSize = 10; // 每页多少条数据
	private Integer recordCount; // 总记录数
	
	private Integer offset=0;//起始值
	private Integer limit=0;//结束值
	
	private Object pageData;


	public int getPageCount() {
		if (recordCount % pageSize == 0) {
			return recordCount / pageSize;
		}
		return recordCount / pageSize + 1;
	}

	public Paging() {
	}

	public Paging(Object  pageData, Integer recordCount) {
		this.pageData = pageData;
		this.recordCount = recordCount;
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


	public int getOffset() {
		//offset=((pageSize+1)*(this.page-1));
		if(this.page<=1){
			offset=0;
		}else{
			offset=this.pageSize*this.page-this.pageSize;
		}
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	
	public int getLimit() {
		//limit=((pageSize+1)*page-1);
		limit=this.pageSize;
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Object getPageData() {
		return pageData;
	}

	public void setPageData(Object pageData,Integer recordCount) {
		this.pageData = pageData;
		this.recordCount=recordCount;
	}
}
