package com.hoomsun.csas.apply.query.dao;

import com.hoomsun.csas.apply.query.model.AssetInfo;

/**
 * 作者：ygzhao <br>
 * 创建时间：2017年11月21日 <br>
 * 描述：资产信息表dao
 */
public interface AssetInfoMapper {
	AssetInfo selectByPrimaryKey(String asinfoPkId);
}