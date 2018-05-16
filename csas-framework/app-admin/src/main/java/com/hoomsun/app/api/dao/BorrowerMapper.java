package com.hoomsun.app.api.dao;

import com.hoomsun.app.api.model.Borrower;

public interface BorrowerMapper {
    int deleteByPrimaryKey(String id);

    int insert(Borrower record);

    int insertSelective(Borrower record);

    Borrower selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Borrower record);

    int updateByPrimaryKey(Borrower record);
    
    Borrower selectByFkid(String fkId);
    
}