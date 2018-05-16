package com.hoomsun.after.dao;

import com.hoomsun.after.api.model.table.Allot;

public interface AllotMapper {
    int deleteByPrimaryKey(String id);

    int insert(Allot record);

    int insertSelective(Allot record);

    Allot selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Allot record);

    int updateByPrimaryKey(Allot record);
}