/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.dao;

import java.util.List;

import com.hoomsun.after.api.model.param.CustomerTaskAllocationReq;
import com.hoomsun.after.api.model.param.DeliveryHistoryReq;
import com.hoomsun.after.api.model.param.NomalCustomerReq;
import com.hoomsun.after.api.model.table.Allot;
import com.hoomsun.after.api.model.vo.CustomerTaskAllocationResult;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月14日 <br>
 * 描述：客户任务分配
 */
public interface CustomerTaskAllocationMapper {

	List<CustomerTaskAllocationResult> queryCustomerTask(CustomerTaskAllocationReq req)throws Exception;
	
	Integer countCustomerTask(CustomerTaskAllocationReq req)throws Exception;
	
	List<CustomerTaskAllocationResult>findDeptEmp(CustomerTaskAllocationReq req)throws Exception;
	
	Integer updateCustomerTaskInfo(NomalCustomerReq req)throws Exception;
	
	Integer insertCustomerTaskInfo(DeliveryHistoryReq req)throws Exception;
	
	Integer insertCustomerAllot(Allot req)throws Exception;
	
	CustomerTaskAllocationResult queryCustomerLoanBalDetails(String loanId)throws Exception;
	
	Allot queryAllotDetails(String loanId)throws Exception;
}
