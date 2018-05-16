/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.sales.api.model.vo.SalesmanReportVo;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年12月21日 <br>
 * 描述：
 */
public interface SalesChartServerI {
	
	Pager<SalesmanReportVo> findBigAreaChartInfo(String empId, String deptId, String startDate, String endDate);
	
	Pager<SalesmanReportVo> findAreaChartInfo(String empId, String deptId, String startDate, String endDate);

	Pager<SalesmanReportVo> findStore(String empId, String deptId, String startDate, String endDate);

	Pager<SalesmanReportVo> findcity(String empId, String deptId,String startDate, String endDate);
	
	Pager<SalesmanReportVo> findTeam(String empId, String deptId, String startDate, String endDate);

	Pager<SalesmanReportVo> findPerson(String empId,String empName, String startDate, String endDate);
	
}
