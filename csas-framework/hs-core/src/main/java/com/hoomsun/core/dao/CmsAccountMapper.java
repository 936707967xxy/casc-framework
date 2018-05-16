package com.hoomsun.core.dao;

import java.util.List;

import com.hoomsun.core.model.CmsAccount;

public interface CmsAccountMapper {
    int deleteByPrimaryKey(String id);

    int insert(CmsAccount record);

    int insertSelective(CmsAccount record);

    CmsAccount selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmsAccount record);

    int updateByPrimaryKey(CmsAccount record);
    
    List<CmsAccount>  selectAllData();
}