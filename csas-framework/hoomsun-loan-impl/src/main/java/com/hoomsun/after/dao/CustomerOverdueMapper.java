/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hoomsun.after.api.model.param.CustomerOverdueReq;
import com.hoomsun.after.api.model.vo.CustomerOverdueResult;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月21日 <br>
 * 描述：客户减免
 * <p>1、查询客户减免信息</p>
 * <p>2、查询客户减免信息总条数</p>
 */
public interface CustomerOverdueMapper {

	public List<CustomerOverdueResult>queryCustomerOverdue(CustomerOverdueReq req)throws Exception;
	
	public Integer countCustomerOverdue(CustomerOverdueReq req)throws Exception;
	
	@Deprecated
	public List<CustomerOverdueResult> queryTempHoomsun(CustomerOverdueReq req)throws Exception;

	void up123dateOverdueTest(CustomerOverdueReq req);
}
