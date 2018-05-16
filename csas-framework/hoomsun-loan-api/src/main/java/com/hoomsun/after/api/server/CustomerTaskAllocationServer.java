/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import java.util.List;

import com.hoomsun.after.api.model.param.CustomerTaskAllocationReq;
import com.hoomsun.after.api.model.vo.CustomerTaskAllocationResult;
import com.hoomsun.common.model.Json;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月14日 <br>
 * 描述：客户任务分配
 */
public interface CustomerTaskAllocationServer {

	List<CustomerTaskAllocationResult> queryCustomerTask(CustomerTaskAllocationReq req);
	
	Integer countCustomerTask(CustomerTaskAllocationReq req);
	
	void downloadCustomerTask(CustomerTaskAllocationReq req);
	
	List<CustomerTaskAllocationResult>querySysEmployee(CustomerTaskAllocationReq req);
	
	Json updateCustomerTask(CustomerTaskAllocationReq req);
}
