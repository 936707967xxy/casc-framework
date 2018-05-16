package com.hoomsun.app.api.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.app.api.dao.HsBackinfoMapper;
import com.hoomsun.app.api.model.HsBackinfo;
import com.hoomsun.app.api.server.inter.HsBackinfoServerI;

@Service("hsbackinfoserverimpl")
public class HsBackinfoServerImpl implements HsBackinfoServerI{

	@Autowired
	private HsBackinfoMapper hsBackinfoMapper;
	
	public int insertSelective(HsBackinfo record){
		return hsBackinfoMapper.insertSelective(record);
	}
	
}
