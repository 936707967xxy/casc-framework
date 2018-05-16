package com.hoomsun.app.api.dao;

import java.util.List;

import com.hoomsun.app.api.model.AfreshContacterInfo;

public interface AfreshContacterInfoMapper {
    int deleteByPrimaryKey(String cninfoPkId);

    int insert(AfreshContacterInfo record);

    int insertSelective(AfreshContacterInfo record);

    AfreshContacterInfo selectByPrimaryKey(String cninfoPkId);

    int updateByPrimaryKeySelective(AfreshContacterInfo record);

    int updateByPrimaryKey(AfreshContacterInfo record);
    
    List<AfreshContacterInfo> selectByFkid(String fkId);
    
    int deleteByfkId(String fkId);
}