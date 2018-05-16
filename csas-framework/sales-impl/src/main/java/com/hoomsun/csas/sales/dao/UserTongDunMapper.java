package com.hoomsun.csas.sales.dao;

import com.hoomsun.csas.sales.api.model.UserTongDun;

public interface UserTongDunMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserTongDun record);

    int insertSelective(UserTongDun record);

    UserTongDun selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserTongDun record);

    int updateByPrimaryKey(UserTongDun record);
}