package com.hoomsun.csas.sales.dao;

import com.hoomsun.csas.sales.api.model.UserCallVisual;

public interface UserCallVisualMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserCallVisual record);

    int insertSelective(UserCallVisual record);

    UserCallVisual selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserCallVisual record);

    int updateByPrimaryKey(UserCallVisual record);
    
    UserCallVisual selectByApplyId(String applyId);
}