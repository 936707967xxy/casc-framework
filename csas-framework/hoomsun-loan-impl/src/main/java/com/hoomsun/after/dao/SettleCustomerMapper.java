/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.dao;

import java.util.List;

import com.hoomsun.after.api.model.param.SettleCustomerReq;
import com.hoomsun.after.api.model.vo.LoanBookCustomerResult;
import com.hoomsun.after.api.model.vo.SettleCustomerResult;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月28日 <br>
 * 描述：清洁客户列表
 */
public interface SettleCustomerMapper {

	public List<SettleCustomerResult>querySettleCustomer(SettleCustomerReq req)throws Exception;
	
	public Integer countSettleCustomer(SettleCustomerReq req)throws Exception;
	
	public List<LoanBookCustomerResult>queryLoanBookCustomerDetail(SettleCustomerReq req)throws Exception;
	
}
