package com.hoomsun.after.dao;

import java.util.List;

import com.hoomsun.after.api.model.param.NomalCustomerReq;
import com.hoomsun.after.api.model.vo.NomalCustomerResult;

public interface NomalCustomerMapper {

	List<NomalCustomerResult>queryNomalCustomer(NomalCustomerReq req)throws Exception;
	
	Integer  countNomalCustomer(NomalCustomerReq req)throws Exception;
	
	NomalCustomerResult ovedueAdvNomalDetailDetails(NomalCustomerReq req)throws Exception;
	
	Integer getIsManagerCount(String empId);
	
	Integer getElectricStimulation(String empId);
}
