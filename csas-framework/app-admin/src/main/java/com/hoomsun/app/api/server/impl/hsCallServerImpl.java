package com.hoomsun.app.api.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.app.api.dao.hsCallMapper;
import com.hoomsun.app.api.model.hsCall;
import com.hoomsun.app.api.server.inter.hsCallServerI;
@Service("hsCallServer")
public class hsCallServerImpl implements hsCallServerI{

	@Autowired
	private hsCallMapper   hsCallMapper;
	
	@Override
	public hsCall selectByPrimaryKey(String id){
		return hsCallMapper.selectByPrimaryKey(id);
	}
}
