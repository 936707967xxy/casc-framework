package com.hoomsun.after.api.server;

import java.util.List;

import com.hoomsun.after.api.model.param.NomalCustomerReq;
import com.hoomsun.after.api.model.vo.NomalCustomerResult;

public interface NomalCustomerServer {

	List<NomalCustomerResult> queryNomalCustomer(NomalCustomerReq req);
	
	Integer  countNomalCustomer(NomalCustomerReq req);
	
	void downloadNomalCustomer(NomalCustomerReq req);
	
	NomalCustomerResult ovedueAdvNomalDetailDetails(NomalCustomerReq req)throws Exception;
}
