package com.hoomsun.csas.sales.dao;

import java.util.Map;

import com.hoomsun.csas.sales.api.model.UserSocialsecurity;
/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月9日 <br>
 * 描述： 社保
 */
public interface UserSocialsecurityMapper {
    int deleteByPrimaryKey(String siId);

    int insert(UserSocialsecurity record);

    int insertSelective(UserSocialsecurity record);

    UserSocialsecurity selectByPrimaryKey(String siId);

    int updateByPrimaryKeySelective(UserSocialsecurity record);

    int updateByPrimaryKey(UserSocialsecurity record);
    
    UserSocialsecurity selectByApplyId(String applyId);
    /**
     * 作者：ywzou <br>
     * 创建时间：2017年12月9日 <br>
     * 描述：数据存储 
     * @param map
     * @return
     */
    @Deprecated
    int insertMap(Map<String, Object>  map);
    /**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 更具申请的ID查询数据信息是否存在
	 * @param applyId
	 * @return
	 */
	Integer checkByApplyId(String applyId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 更具申请的ID验证数据是否已经存在
	 * @param applyId
	 * @return
	 */
	Integer checkById(String applyId);
}