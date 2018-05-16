package com.hoomsun.after.dao;

import java.util.Map;

import com.hoomsun.after.api.model.param.NomalCustomerReq;
import com.hoomsun.after.api.model.table.RepaymentPlan;

public interface RepaymentPlanMapper {
	/**
	 * 根据LoanId和期次查询还款明细表数据
	 */
	RepaymentPlan selByLoanIdTerm(Map<String, Object> params);
	
	RepaymentPlan queryByLoanIdTerm(NomalCustomerReq req);
}