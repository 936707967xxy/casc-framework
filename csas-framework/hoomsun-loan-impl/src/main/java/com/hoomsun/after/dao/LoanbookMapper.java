package com.hoomsun.after.dao;

import com.hoomsun.after.api.model.table.Loanbook;

public interface LoanbookMapper {
    int deleteByPrimaryKey(String id);

    int insert(Loanbook record);

    int insertSelective(Loanbook record);

    Loanbook selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Loanbook record);

    int updateByPrimaryKey(Loanbook record);
}