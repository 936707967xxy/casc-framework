package com.hoomsun.app.api.dao;

import java.util.List;

import com.hoomsun.app.api.model.ProvincesHege;

public interface ProvincesHegeMapper {
    int deleteByPrimaryKey(String provinceid);

    int insert(ProvincesHege record);

    int insertSelective(ProvincesHege record);

    ProvincesHege selectByPrimaryKey(String provinceid);

    int updateByPrimaryKeySelective(ProvincesHege record);

    int updateByPrimaryKey(ProvincesHege record);
    
    List<ProvincesHege>  selectAllData();
    
    /**
     * 
     * 作者：liushuai <br>
     * 创建时间：2017年11月7日 <br>
     * 描述：仅查询门店所在的省份 
     * @return
     */
    List<ProvincesHege>  selectStoreCitysData();
    
    
    
}