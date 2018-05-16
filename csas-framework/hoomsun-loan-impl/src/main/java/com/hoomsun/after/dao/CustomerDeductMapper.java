/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.dao;

import java.util.List;

import com.hoomsun.after.api.model.param.CustomerDeductReq;
import com.hoomsun.after.api.model.vo.CustomerDeductResult;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月21日 <br>
 * 描述：客户划扣记录
 */
public interface CustomerDeductMapper {

	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年3月21日 <br>
	 * 描述： 客户划扣记录查询
	 * @param req
	 * @return
	 * @throws Exception
	 */
    public List<CustomerDeductResult>queryCustomerDeduct(CustomerDeductReq req)throws Exception;
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年3月21日 <br>
	 * 描述： 客户划扣记录总条数查询
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public Integer countCustomerDeduct(CustomerDeductReq req)throws Exception;
	
	
}
