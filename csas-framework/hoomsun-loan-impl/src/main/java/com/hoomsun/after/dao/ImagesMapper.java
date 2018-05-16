package com.hoomsun.after.dao;

import java.util.List;

import com.hoomsun.after.api.model.table.Images;

public interface ImagesMapper {
    int deleteByPrimaryKey(String id);

    int insert(Images record);

    int insertSelective(Images record);

    Images selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Images record);

    int updateByPrimaryKey(Images record);
    
    List<Images> selectByApplicationId(Images record);
}