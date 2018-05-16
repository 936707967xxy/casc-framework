package com.hoomsun.csas.sales.dao;

import com.hoomsun.csas.sales.api.model.AssetInfo;

/**
 * 作者：ygzhao <br>
 * 创建时间：2017年11月21日 <br>
 * 描述：资产信息表dao
 */
public interface AssetInfoMapper {
    int deleteByPrimaryKey(String asinfoPkId);

    int insert(AssetInfo record);

    int insertSelective(AssetInfo record);

    AssetInfo selectByPrimaryKey(String asinfoPkId);

    int updateByPrimaryKeySelective(AssetInfo record);

    int updateByPrimaryKey(AssetInfo record);
    
    /**
     * 作者：ygzhao <br>
     * 创建时间：2017年11月21日 <br>
     * 描述：通过appId修改资产信息
     */
    int deleteByApplyId(String applyId);
    
    /**
     * 作者：ygzhao <br>
     * 创建时间：2017年11月21日 <br>
     * 描述：根据applyId修改资产信息
     */
    int updateByApplyId(AssetInfo record);
    
    /**
     * 作者：ygzhao <br>
     * 创建时间：2017年11月21日 <br>
     * 描述：根据applyId查询资产信息
     */
    AssetInfo selectByApplyId(String applyId);
}