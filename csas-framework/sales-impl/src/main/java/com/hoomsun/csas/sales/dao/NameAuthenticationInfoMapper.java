package com.hoomsun.csas.sales.dao;

import java.util.Map;

import com.hoomsun.csas.sales.api.model.NameAuthenticationInfo;
import com.hoomsun.csas.sales.api.model.NameAuthenticationInfoWithBLOBs;



public interface NameAuthenticationInfoMapper {
    int deleteByPrimaryKey(String authinfoPkId);

    int insert(NameAuthenticationInfoWithBLOBs record);

    int insertSelective(NameAuthenticationInfoWithBLOBs record);

    NameAuthenticationInfoWithBLOBs selectByPrimaryKey(String authinfoPkId);

    int updateByPrimaryKeySelective(NameAuthenticationInfoWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(NameAuthenticationInfoWithBLOBs record);

    int updateByPrimaryKey(NameAuthenticationInfo record);
    
    public    NameAuthenticationInfoWithBLOBs selectByFkid(String fkId);
    
    Map<String,Object>   selectDataByFkid(String fkId);
}