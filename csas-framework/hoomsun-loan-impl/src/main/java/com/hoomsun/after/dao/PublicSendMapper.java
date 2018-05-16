package com.hoomsun.after.dao;

import com.hoomsun.after.api.model.table.PublicSend;

public interface PublicSendMapper {
    int deleteByPrimaryKey(String id);

    int insert(PublicSend record);

    int insertSelective(PublicSend record);

    PublicSend selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PublicSend record);

    int updateByPrimaryKey(PublicSend record);
}