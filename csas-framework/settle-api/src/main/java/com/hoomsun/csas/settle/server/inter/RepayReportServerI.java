/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.settle.server.inter;


import java.util.List;
import java.util.Map;

import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.settle.model.vo.RepayReport;



/**
 * 作者：liming <br>
 * 创建时间：2018年1月16日 <br>
 * 描述：客户报表业务层
 */

public interface RepayReportServerI {
	
	Pager<RepayReport> findPage(Integer page, Integer rows,String castName,String cardNo,String conNo,String repayType,String startDate,String endDate);
	
	
	List<RepayReport> findRepayList(Map<String, Object> param);

}
