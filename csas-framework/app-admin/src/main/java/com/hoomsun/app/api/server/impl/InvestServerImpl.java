package com.hoomsun.app.api.server.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.app.api.dao.AuthenticationUrlMapper;
import com.hoomsun.app.api.server.inter.InvestServerI;
import com.hoomsun.csas.sales.api.model.NameAuthentication;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.dao.NameAuthenticationMapper;
import com.hoomsun.csas.sales.dao.UserApplyMapper;

@Service("InvestServerImpl")
public class InvestServerImpl implements InvestServerI{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private NameAuthenticationMapper nameAuthenticationMapper;
	
	@Autowired
	private AuthenticationUrlMapper authenticationUrlMapper;

    @Autowired
   	private UserApplyMapper userApplyMapper;

	public void   saveInvest(NameAuthentication nameAuth,String  Id, UserApply record){
		
		int i = nameAuthenticationMapper.updateByPrimaryKeySelective(nameAuth);
		logger.info("实名认证状态修改条数=" + i);
		//修改之前单子
		int  Z=userApplyMapper.selectInfoAdd(record);
		
		
	}
}
