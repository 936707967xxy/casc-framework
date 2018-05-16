package com.hoomsun.csas.sales.dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.sales.api.model.NameAuthentication;


public interface NameAuthenticationMapper {
	int deleteByPrimaryKey(String id);

	int insert(NameAuthentication record);

	int insertSelective(NameAuthentication record);

	NameAuthentication selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(NameAuthentication record);

	int updateByPrimaryKey(NameAuthentication record);

	public NameAuthentication selectByPhone(String phone);

	public int updateByPhoneSelective(NameAuthentication record);

	NameAuthentication selectByWxtaken(String wxtaken);

	NameAuthentication selectUuid(NameAuthentication record);

	NameAuthentication selectBySign(String sign);

	List<NameAuthentication> selectAllData();

	public List<Map<String, Object>> selectRegCountByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	public NameAuthentication selectByPaperId(String paperid);
	
	public int updateByPrimaryKeyApply(NameAuthentication record);
	
	Map<String,Object> selectByPriKeyMap(String id);
	
	NameAuthentication selectByIdcard(String paperid);
}