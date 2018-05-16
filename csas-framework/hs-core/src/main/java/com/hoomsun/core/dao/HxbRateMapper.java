package com.hoomsun.core.dao;

import com.hoomsun.core.model.HxbRate;

public interface HxbRateMapper {
    int deleteByPrimaryKey(String hoomxbId);

    int insert(HxbRate record);

    int insertSelective(HxbRate record);

    HxbRate selectByPrimaryKey(String hoomxbId);

    int updateByPrimaryKeySelective(HxbRate record);

    int updateByPrimaryKey(HxbRate record);
    
    HxbRate selectByApplyId(String applyId);
}