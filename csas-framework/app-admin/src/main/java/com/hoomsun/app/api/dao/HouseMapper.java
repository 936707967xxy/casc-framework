package com.hoomsun.app.api.dao;

import java.util.List;
import java.util.Map;
import com.hoomsun.app.api.model.House;


public interface HouseMapper {

	    int deleteByPrimaryKey(String id);

	    int insert(House record);

	    int insertSelective(House record);

	    House selectByPrimaryKey(String id);

	    int updateByPrimaryKeySelective(House record);

	    int updateByPrimaryKey(House record);
	    
	    House selectBytype(String type);
	    
	    /**
	     * 作者：liudongliang<br>
	     * 创建时间：2017年12月5日<br>
	     * 描述：满足筛选条件的数据 分页  
	     * @param param keys:pageIndex,pageSize,type
	     * @return
	     */
		List<House> findPageData(Map<String, Object> param);
		
		/**
		 * 作者：liudongliang<br>
		 * 创建时间：2017年12月5日<br>
		 * 描述：满足筛选条件的数据量
		 * @param param
		 * @return keys:pageIndex,pageSize,type
		 */
		int findPageCount(Map<String, Object> param);
		
		
		List<House>  selectByProvince(String provinceid);
		
		/**
		 * 作者：liudongliang<br>
		 * 创建时间：2017年12月6日<br>
		 * 描述：获取公积金接口
		 * @param uniqueKey
		 * @return 
		 */
		House selectApiByUniqueKey(String uniqueKey);
		
}
