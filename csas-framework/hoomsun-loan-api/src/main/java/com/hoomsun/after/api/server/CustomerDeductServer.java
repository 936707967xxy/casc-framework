/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import java.util.List;

import com.hoomsun.after.api.model.param.CustomerDeductReq;
import com.hoomsun.after.api.model.vo.CustomerDeductResult;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月21日 <br>
 * 描述：客户划扣记录
 * <p>1、客户划扣记录查询</p>
 * <p>2、客户划扣记录总条数查询</p>
 * <p>3、客户划扣记录查询记录导出</p>
 */
public interface CustomerDeductServer {

	public List<CustomerDeductResult>queryCustomerDeduct(CustomerDeductReq req);
	
	public Integer countCustomerDeduct(CustomerDeductReq req);
	
	public void downloadCustomerDeduct(CustomerDeductReq req);
	
}
