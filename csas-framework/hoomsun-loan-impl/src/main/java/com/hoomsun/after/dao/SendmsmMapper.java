package com.hoomsun.after.dao;

import com.hoomsun.after.api.model.table.Sendmsm;

public interface SendmsmMapper {
    int deleteByPrimaryKey(String id);

    int insert(Sendmsm record);

    int insertSelective(Sendmsm record);

    Sendmsm selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Sendmsm record);

    int updateByPrimaryKey(Sendmsm record);
}