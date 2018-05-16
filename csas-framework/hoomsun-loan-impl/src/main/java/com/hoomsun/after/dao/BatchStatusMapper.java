package com.hoomsun.after.dao;

import com.hoomsun.after.api.model.table.BatchStatus;

public interface BatchStatusMapper {
    int deleteByPrimaryKey(String id);

    int insert(BatchStatus record);

    int insertSelective(BatchStatus record);

    BatchStatus selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BatchStatus record);

    int updateByPrimaryKey(BatchStatus record);
}