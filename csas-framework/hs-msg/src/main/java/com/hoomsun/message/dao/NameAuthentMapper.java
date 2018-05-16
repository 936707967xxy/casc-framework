package com.hoomsun.message.dao;



import java.util.List;
import java.util.Map;

import com.hoomsun.message.model.NameAuthentication;

public interface NameAuthentMapper {
	int deleteByPrimaryKey(String id);

	int insert(NameAuthentication record);

	int insertSelective(NameAuthentication record);

	NameAuthentication selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(NameAuthentication record);

	int updateByPrimaryKey(NameAuthentication record);

	public NameAuthentication selectByPhone(String phone);

	public int updateByPhoneSelective(NameAuthentication record);

	List<NameAuthentication> selectAllData();	
	
	NameAuthentication selectByIdcard(String paperid);
	
	Map<String, Object> selectApplyByID(String applyId);
}