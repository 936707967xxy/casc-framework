package com.hoomsun.after.dao;

import com.hoomsun.after.api.model.table.HxbRepaySend;

public interface HxbRepaySendMapper {
    int insert(HxbRepaySend record);

    int insertSelective(HxbRepaySend record);
}