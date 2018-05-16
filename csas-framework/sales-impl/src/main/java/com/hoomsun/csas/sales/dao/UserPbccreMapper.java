package com.hoomsun.csas.sales.dao;

import com.hoomsun.csas.sales.api.model.UserPbccre;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月9日 <br>
 * 描述： 征信
 *
 */
public interface UserPbccreMapper {
    int deleteByPrimaryKey(String crId);

    int insert(UserPbccre record);

    int insertSelective(UserPbccre record);

    UserPbccre selectByPrimaryKey(String crId);

    int updateByPrimaryKeySelective(UserPbccre record);

    int updateByPrimaryKey(UserPbccre record);
    
    UserPbccre selectByApplyId(String applyId);
    
    /**
     * 作者：ywzou <br>
     * 创建时间：2017年12月9日 <br>
     * 描述： 产看申请大是否存在征信数据
     * @param applyId
     * @return
     */
	Integer checkByApplyId(String applyId);
}