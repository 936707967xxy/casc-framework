/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.settle.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.csas.settle.model.vo.ShouldRepayReport;

/**
 * 
 * 作者：liming<br>
 * 创建时间：2018年1月26日 <br>
 * 描述：  应还款客户统计报表
 *
 */
public interface ShouldRepayReportMapper {

	List<ShouldRepayReport> findPager(Map<String, Object> param);

	int findPagerCount(Map<String, Object> param);
	
	List<ShouldRepayReport>  findBalList(Map<String, Object> param);
}
