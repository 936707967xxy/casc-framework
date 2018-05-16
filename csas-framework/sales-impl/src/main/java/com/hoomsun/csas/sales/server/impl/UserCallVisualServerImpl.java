package com.hoomsun.csas.sales.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.csas.sales.api.model.UserCallVisual;
import com.hoomsun.csas.sales.api.server.inter.UserCallVisualServerI;
import com.hoomsun.csas.sales.dao.UserCallVisualMapper;

@Service("callVisualServer")
public class UserCallVisualServerImpl implements UserCallVisualServerI {
	
	@Autowired
	private UserCallVisualMapper callVisualMapper;

	@Override
	public UserCallVisual findByApply(String applyId) {
		return callVisualMapper.selectByApplyId(applyId);
	}
	
}
