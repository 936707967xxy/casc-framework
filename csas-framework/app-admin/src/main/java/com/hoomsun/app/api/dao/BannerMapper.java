package com.hoomsun.app.api.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.app.api.model.Banner;


public interface BannerMapper {
    int deleteByPrimaryKey(String id);

    int insert(Banner record);

    int insertSelective(Banner record);

    Banner selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Banner record);

    int updateByPrimaryKey(Banner record);
    
    /**
     * 作者：liushuai<br>
     * 创建时间：2017年9月13日<br>
     * 描述：满足筛选条件的数据 分页  
     * @param param keys:pageIndex,pageSize,comName
     * @return
     */
	List<Banner> findPageData(Map<String, Object> param);
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月13日<br>
	 * 描述：满足筛选条件的数据量
	 * @param param
	 * @return keys:pageIndex,pageSize,comName
	 */
	int findPageCount(Map<String, Object> param);
    
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月13日<br>
	 * 描述：查询出所有的数据
	 * 
	 * @return
	 */
	List<Banner> findAllData();
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：查询app需要展现数据
	 * 
	 * @return
	 */
	List<Banner> selectByisopen();
}