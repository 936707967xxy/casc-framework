package com.hoomsun.app.api.dao;

import com.hoomsun.app.api.model.AfreshCareerInfo;



public interface AfreshCareerInfoMapper {
    int deleteByPrimaryKey(String rinfoPkId);

    int insert(AfreshCareerInfo record);

    int insertSelective(AfreshCareerInfo record);

    AfreshCareerInfo selectByPrimaryKey(String rinfoPkId);

    int updateByPrimaryKeySelective(AfreshCareerInfo record);

    int updateByPrimaryKey(AfreshCareerInfo record);
    
    AfreshCareerInfo selectByFkid(String fkId);
}