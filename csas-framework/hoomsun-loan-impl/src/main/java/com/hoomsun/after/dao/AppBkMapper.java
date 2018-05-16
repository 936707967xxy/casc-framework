package com.hoomsun.after.dao;

import java.util.Map;

import com.hoomsun.after.api.model.table.AppBk;

public interface AppBkMapper {
	int deleteByPrimaryKey(String id);

	int insert(AppBk record);

	int insertSelective(AppBk record);

	AppBk selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(AppBk record);

	int updateByPrimaryKey(AppBk record);

	AppBk selectByCustIdAndBankAccount(Map<String, Object> parmas);
}