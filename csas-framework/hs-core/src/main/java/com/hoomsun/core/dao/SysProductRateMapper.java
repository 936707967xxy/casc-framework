package com.hoomsun.core.dao;

import com.hoomsun.core.model.SysProductRate;

public interface SysProductRateMapper {
//    int deleteByPrimaryKey(String prId);
//
//    int insert(SysProductRate record);
//
	int insertSelective(SysProductRate record);
//
//    SysProductRate selectByPrimaryKey(String prId);
//
//    int updateByPrimaryKeySelective(SysProductRate record);
//
//    int updateByPrimaryKey(SysProductRate record);

    
	Integer deleteByProdId(String prodId);
}