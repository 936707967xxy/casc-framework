package com.hoomsun.after.dao;

import com.hoomsun.after.api.model.table.Review;

public interface ReviewMapper {
    int deleteByPrimaryKey(String id);

    int insert(Review record);

    int insertSelective(Review record);

    Review selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Review record);

    int updateByPrimaryKey(Review record);
}