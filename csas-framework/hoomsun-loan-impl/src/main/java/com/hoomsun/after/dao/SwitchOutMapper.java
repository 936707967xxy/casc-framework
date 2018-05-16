package com.hoomsun.after.dao;

import com.hoomsun.after.api.model.table.SwitchOut;

public interface SwitchOutMapper {
    int deleteByPrimaryKey(String switchOut);

    int insert(SwitchOut record);

    int insertSelective(SwitchOut record);

    SwitchOut selectByPrimaryKey(String switchOut);

    int updateByPrimaryKeySelective(SwitchOut record);

    int updateByPrimaryKey(SwitchOut record);
}