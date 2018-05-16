package com.hoomsun.app.api.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.app.api.model.HotHeadline;

public interface HotHeadlineMapper {
    int deleteByPrimaryKey(String hotId);

    int insert(HotHeadline record);

    int insertSelective(HotHeadline record);

    HotHeadline selectByPrimaryKey(String hotId);

    int updateByPrimaryKeySelective(HotHeadline record);

    int updateByPrimaryKey(HotHeadline record);
    
    /**
     * 作者：liushuai<br>
     * 创建时间：2017年9月19日<br>
     * 描述：满足筛选条件的数据 分页  
     * @param param keys:pageIndex,pageSize,comName
     * @return
     */
	List<HotHeadline> findPageData(Map<String, Object> param);
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月19日<br>
	 * 描述：满足筛选条件的数据量
	 * @param param
	 * @return keys:pageIndex,pageSize,comName
	 */
	int findPageCount(Map<String, Object> param);
    
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月19日<br>
	 * 描述：查询出所有的数据
	 * 
	 * @return
	 */
	List<HotHeadline> findAllData();
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年9月20日 <br>
	 * 描述： 查询出所有打开状态的数据
	 * @return
	 */
	List<HotHeadline> findAllOpenData();
	
	
	List<HotHeadline> findAllAppBannerData();
	
}