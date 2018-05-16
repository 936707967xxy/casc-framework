package com.hoomsun.app.api.dao;

import com.hoomsun.app.api.model.CarrearInfo;

public interface CarrearInfoMapper {
    int deleteByPrimaryKey(String ocinfoPkId);

    int insert(CarrearInfo record);

    int insertSelective(CarrearInfo record);

    CarrearInfo selectByPrimaryKey(String ocinfoPkId);

    int updateByPrimaryKeySelective(CarrearInfo record);

    int updateByPrimaryKey(CarrearInfo record);
    
    CarrearInfo selectByfkId(String fkId);
    
}