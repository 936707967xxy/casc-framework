package com.hoomsun.core.dao;

import com.hoomsun.core.model.SystemParam;

public interface SystemParamMapper {
	int deleteByPrimaryKey(String id);

	int insert(SystemParam record);

	int insertSelective(SystemParam record);

	SystemParam selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(SystemParam record);

	int updateByPrimaryKey(SystemParam record);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月5日 <br>
	 * 描述： 根据key获取参数
	 * 
	 * @param paramKey
	 * @return
	 */
	SystemParam findParamByKey(String paramKey);
}