package com.hoomsun.app.api.server.inter;


import com.hoomsun.app.api.model.AuthenticationUrl;

public interface AuthenticationUrlServerI {

	int insertSelective(AuthenticationUrl record);
	
	AuthenticationUrl selectByFkid(String fkId);
	
	int updateByPrimaryKeySelective(AuthenticationUrl record);
}
