/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import java.util.List;

import com.hoomsun.after.api.model.param.SettleCustomerReq;
import com.hoomsun.after.api.model.vo.LoanBookCustomerResult;
import com.hoomsun.after.api.model.vo.SettleCustomerResult;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月28日 <br>
 * 描述：结清客户查询
 */
public interface SettleCustomerServer {

    public List<SettleCustomerResult>querySettleCustomer(SettleCustomerReq req);
	
	public Integer countSettleCustomer(SettleCustomerReq req);
	
	public void downloadSettleCustomer(SettleCustomerReq req);
	
	public List<LoanBookCustomerResult>queryLoanBookCustomerDetail(SettleCustomerReq req);
}
