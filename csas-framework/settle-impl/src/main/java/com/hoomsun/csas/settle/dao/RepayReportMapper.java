/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.settle.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.csas.settle.model.vo.RepayReport;

/**
 * 作者：liming <br>
 * 创建时间：2018年1月16日 <br>
 * 描述：和客户还款报表
 */
public interface RepayReportMapper {

	List<RepayReport> findPager(Map<String, Object> param);

	int findPagerCount(Map<String, Object> param);
	
	List<RepayReport> findRepayList(Map<String, Object> param);
}
