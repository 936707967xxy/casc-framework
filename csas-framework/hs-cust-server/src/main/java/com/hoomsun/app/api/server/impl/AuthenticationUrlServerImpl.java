package com.hoomsun.app.api.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.app.api.dao.AuthenticationUrlMapper;
import com.hoomsun.app.api.model.AuthenticationUrl;
import com.hoomsun.app.api.server.inter.AuthenticationUrlServerI;

@Service("authenticationUrlServer")
public class AuthenticationUrlServerImpl implements AuthenticationUrlServerI{

	@Autowired
	private AuthenticationUrlMapper AuthenticationUrlMapper;
	
	@Override
	public int insertSelective(AuthenticationUrl record){
		return AuthenticationUrlMapper.insertSelective(record);
	}
	
	@Override
	public  AuthenticationUrl selectByFkid(String fkId){
		return AuthenticationUrlMapper.selectByFkid(fkId);
	}
	
	@Override
	public  int updateByPrimaryKeySelective(AuthenticationUrl record){
		return AuthenticationUrlMapper.updateByPrimaryKeySelective(record);
	}
}
