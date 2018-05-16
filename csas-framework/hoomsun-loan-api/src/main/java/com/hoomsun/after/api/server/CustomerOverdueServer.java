/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import java.util.List;

import com.hoomsun.after.api.model.param.CustomerOverdueReq;
import com.hoomsun.after.api.model.vo.CustomerOverdueResult;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月21日 <br>
 * 描述：客户减免
 */
public interface CustomerOverdueServer {

    public List<CustomerOverdueResult>queryCustomerOverdue(CustomerOverdueReq req)throws Exception;
	
	public Integer countCustomerOverdue(CustomerOverdueReq req)throws Exception;
	
	public void downloadCustomerOverdue(CustomerOverdueReq req)throws Exception;
	
	@Deprecated
	public void updateOverdueTest(CustomerOverdueReq req)throws Exception;
	
}
