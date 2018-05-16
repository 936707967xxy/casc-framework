package com.hoomsun.csas.sales.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.sales.api.model.UserCallDetail;


public interface UserCallDetailMapper {
    int deleteByPrimaryKey(String pdid);

    int insert(UserCallDetail record);

    int insertSelective(UserCallDetail record);

    UserCallDetail selectByPrimaryKey(String pdid);

    int updateByPrimaryKeySelective(UserCallDetail record);

    int updateByPrimaryKey(UserCallDetail record);
    
    int batchCreate(@Param("callDetails")List<UserCallDetail> callDetails);
}