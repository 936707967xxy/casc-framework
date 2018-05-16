package com.hoomsun.app.api.dao;

import java.util.List;

import com.hoomsun.app.api.model.AuthenticationUrl;

public interface AuthenticationUrlMapper {
    int deleteByPrimaryKey(String id);

    int insert(AuthenticationUrl record);

    int insertSelective(AuthenticationUrl record);

    AuthenticationUrl selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuthenticationUrl record);

    int updateByPrimaryKey(AuthenticationUrl record);
    
    AuthenticationUrl selectByFkid(String fkId);
}