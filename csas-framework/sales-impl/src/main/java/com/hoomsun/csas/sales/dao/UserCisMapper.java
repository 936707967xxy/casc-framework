package com.hoomsun.csas.sales.dao;

import java.util.Map;

import com.hoomsun.csas.sales.api.model.UserCis;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月9日 <br>
 * 描述： 上海资信
 *
 */
public interface UserCisMapper {
    int deleteByPrimaryKey(String cisId);

    int insert(UserCis record);

    int insertSelective(UserCis record);

    UserCis selectByPrimaryKey(String cisId);

    int updateByPrimaryKeySelective(UserCis record);

    int updateByPrimaryKey(UserCis record);
    
    UserCis selectByApplyId(String applyId);
    
    int insertMap(Map<String, Object>  map);
    /**
     * 作者：ywzou <br>
     * 创建时间：2017年12月9日 <br>
     * 描述： 更具申请验证是否存在数据
     * @param applyId
     * @return
     */
	Integer checkByApplyId(String applyId);
}