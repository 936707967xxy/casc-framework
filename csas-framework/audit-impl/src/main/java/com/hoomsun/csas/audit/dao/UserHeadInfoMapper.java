package com.hoomsun.csas.audit.dao;

import com.hoomsun.csas.audit.model.UserHeadInfo;


public interface UserHeadInfoMapper {
    int deleteByPrimaryKey(String poId);

    int insert(UserHeadInfo record);

    int insertSelective(UserHeadInfo record);

    UserHeadInfo selectByPrimaryKey(String poId);

    int updateByPrimaryKeySelective(UserHeadInfo record);

    int updateByPrimaryKey(UserHeadInfo record);
}