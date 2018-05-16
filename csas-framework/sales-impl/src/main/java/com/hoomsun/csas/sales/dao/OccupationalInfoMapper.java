package com.hoomsun.csas.sales.dao;

import com.hoomsun.csas.sales.api.model.OccupationalInfo;

public interface OccupationalInfoMapper {
    int deleteByPrimaryKey(String ocinfoPkId);

    int insert(OccupationalInfo record);

    int insertSelective(OccupationalInfo record);

    OccupationalInfo selectByPrimaryKey(String ocinfoPkId);

    int updateByPrimaryKeySelective(OccupationalInfo record);

    int updateByPrimaryKey(OccupationalInfo record);
    
    /**
     * 作者：ygzhao <br>
     * 创建时间：2017年11月21日 <br>
     * 描述：根据applyId删除职业信息
     */
    int deleteByApplyId(String applyId);
    
    /**
     * 作者：ygzhao <br>
     * 创建时间：2017年11月21日 <br>
     * 描述：根据applyId修改职业信息
     */
    int updateByApplyId(OccupationalInfo record);
    
    /**
     * 作者：ygzhao <br>
     * 创建时间：2017年11月21日 <br>
     * 描述：根据applyId查询资产信息
     */
    OccupationalInfo selectByApplyId(String applyId);
}