package com.hoomsun.core.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.core.model.CmbTransInfo;

public interface CmbTransInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(CmbTransInfo record);

    int insertSelective(CmbTransInfo record);

    CmbTransInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CmbTransInfo record);

    int updateByPrimaryKey(CmbTransInfo record);
    
    int insertMapSelective(Map<String, Object> map);
    
    CmbTransInfo selectByMap(Map<String, Object> map);
    
    /**
     * 
     * 作者：刘栋梁<br>
     * 创建时间：2018年1月11日 <br>
     * 描述： 数据的分页查询
     * @param param
     * @return
     */
	List<CmbTransInfo> findPageData(Map<String, Object> param);
	/**
	 * 
	 * 作者：刘栋梁<br>
	 * 创建时间：2018年1月11日 <br>
	 * 描述： 数据的分页查询的条数
	 * @param param
	 * @return
	 */
	Integer findPageCount(Map<String, Object> param);
}