package com.hoomsun.app.api.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.app.api.model.Version;



public interface VersionMapper {
    int deleteByPrimaryKey(String id);

    int insert(Version record);

    int insertSelective(Version record);

    Version selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Version record);

    int updateByPrimaryKey(Version record);
    
    Version selectBytype(String type);
    
    /**
     * 作者：liushuai<br>
     * 创建时间：2017年9月29日<br>
     * 描述：满足筛选条件的数据 分页  
     * @param param keys:pageIndex,pageSize,type
     * @return
     */
	List<Version> findPageData(Map<String, Object> param);
	
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月29日<br>
	 * 描述：满足筛选条件的数据量
	 * @param param
	 * @return keys:pageIndex,pageSize,type
	 */
	int findPageCount(Map<String, Object> param);
    
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月29日<br>
	 * 描述：查询出所有的数据
	 * 
	 * @return
	 */
	List<Version> findAllData();
    
}