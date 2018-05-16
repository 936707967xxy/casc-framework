package com.hoomsun.app.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.app.api.model.AfreshProtoinfo;


public interface AfreshProtoinfoMapper {
    int deleteByPrimaryKey(String protoinfoId);

    int insert(AfreshProtoinfo record);

    int insertSelective(AfreshProtoinfo record);

    AfreshProtoinfo selectByPrimaryKey(String protoinfoId);

    int updateByPrimaryKeySelective(AfreshProtoinfo record);

    int updateByPrimaryKey(AfreshProtoinfo record);
    
    List<AfreshProtoinfo> selectByFkid(String fkId);
    
    int updateByType(String fkId);
    
    int updateByProtoinfoId(Map<String,Object>  para);
     
    int deleteByProtoinfoId(String protoinfoId);
    
    AfreshProtoinfo selectByFkIdAndIsDefault(String applyId);
    
    List<AfreshProtoinfo>  selectByAccno(String accno);
}