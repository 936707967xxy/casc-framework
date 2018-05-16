package com.hoomsun.csas.audit.dao;

import java.util.Map;

import com.hoomsun.csas.audit.model.HxbRecord;

public interface HxbRecordMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(HxbRecord record);

    int insertSelective(HxbRecord record);

    HxbRecord selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(HxbRecord record);

    int updateByPrimaryKey(HxbRecord record);
    
    HxbRecord selectByApplyId(String applyId);
    
    Map<String,Object> findEmpId(String applyId);
}