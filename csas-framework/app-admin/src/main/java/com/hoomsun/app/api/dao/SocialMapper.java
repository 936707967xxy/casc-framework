package com.hoomsun.app.api.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.app.api.model.Social;


public interface SocialMapper {
    int deleteByPrimaryKey(String socialId);

    int insert(Social record);

    int insertSelective(Social record);

    Social selectByPrimaryKey(String socialId);

    int updateByPrimaryKeySelective(Social record);

    int updateByPrimaryKey(Social record);
    /**
     * 作者：liudongliang<br>
     * 创建时间：2017年12月5日<br>
     * 描述：满足筛选条件的数据 分页  
     * @param param keys:pageIndex,pageSize,type
     * @return
     */
	List<Social> findPageData(Map<String, Object> param);
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12月5日<br>
	 * 描述：满足筛选条件的数据量
	 * @param param
	 * @return keys:pageIndex,pageSize,type
	 */
	int findPageCount(Map<String, Object> param);
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12月5日<br>
	 * 描述：满足该省下的城市
	 * @param param
	 * @return keys:pageIndex,pageSize,type
	 */
	List<Social> selectByProvince(String provinceid);
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12月6日<br>
	 * 描述：查询接口值
	 * @param param
	 * @return keys:pageIndex,pageSize,type
	 */
	Social  selectApiByUniqueKey(String uniqueKey);
}