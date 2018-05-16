package com.hoomsun.app.api.dao;

import com.hoomsun.app.api.model.hsCall;

public interface hsCallMapper {
    int deleteByPrimaryKey(String id);

    int insert(hsCall record);

    int insertSelective(hsCall record);

    hsCall selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(hsCall record);

    int updateByPrimaryKey(hsCall record);
}