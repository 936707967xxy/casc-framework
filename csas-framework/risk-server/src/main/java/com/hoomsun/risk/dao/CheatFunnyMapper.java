package com.hoomsun.risk.dao;

import com.hoomsun.risk.model.CheatFunny;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月6日 <br>
 * 描述： 欺诈数据控制
 */
public interface CheatFunnyMapper {
	int deleteByPrimaryKey(String cfId);

	int insert(CheatFunny record);

	int insertSelective(CheatFunny record);

	CheatFunny selectByPrimaryKey(String cfId);

	int updateByPrimaryKeySelective(CheatFunny record);

	int updateByPrimaryKey(CheatFunny record);
}