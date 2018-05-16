package com.hoomsun.app.api.dao;

import com.hoomsun.app.api.model.HsBackinfo;

public interface HsBackinfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(HsBackinfo record);

    int insertSelective(HsBackinfo record);

    HsBackinfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HsBackinfo record);

    int updateByPrimaryKey(HsBackinfo record);
}