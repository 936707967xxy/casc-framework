package com.hoomsun.core.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.core.model.SysResources;

public interface SysResourcesMapper {
	int deleteByPrimaryKey(String resId);

	int insert(SysResources record);

	int insertSelective(SysResources record);

	SysResources selectByPrimaryKey(String resId);

	int updateByPrimaryKeySelective(SysResources record);

	int updateByPrimaryKey(SysResources record);

	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：满足筛选条件的资源数据 分页
	 * 
	 * @param param
	 *            {keys:[pageIndex,pageSize,resName]}
	 * @return
	 */
	List<SysResources> findPageData(Map<String, Object> param);

	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：
	 * 
	 * @param param
	 *            {keys:[pageIndex,pageSize,resName]}
	 * @return
	 */
	int findPageCount(Map<String, Object> param);

	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：查询出所有的数据
	 * 
	 * @return
	 */
	List<SysResources> findAllData();
}