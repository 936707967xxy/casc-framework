/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.settle.server.inter;

import java.util.List;
import java.util.Map;

import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.settle.model.vo.LoanInfoReport;

/**
 * 作者：liming <br>
 * 创建时间：2018年1月23日 <br>
 * 描述：放款报表
 */
public interface LoanInfoReportServerI {
	
	Pager<LoanInfoReport> findPage(Integer page, Integer rows,String custName,String bankPhoneNo,String conNo,String idNumber,String startDate,String endDate);

	List<LoanInfoReport> findLoanInfoList(Map<String, Object> param);


}
