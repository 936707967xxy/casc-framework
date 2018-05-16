package com.hoomsun.csas.sales.dao;

import java.util.Map;

import com.hoomsun.csas.sales.api.model.UserHouseFund;
/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月9日 <br>
 * 描述：公积金 
 *
 */
public interface UserHouseFundMapper {
    int deleteByPrimaryKey(String hfId);

    int insert(UserHouseFund record);

    int insertSelective(UserHouseFund record);

    UserHouseFund selectByPrimaryKey(String hfId);

    int updateByPrimaryKeySelective(UserHouseFund record);

    int updateByPrimaryKey(UserHouseFund record);
    
    UserHouseFund selectByApplyId(String applyId);
    
    /**
     * 作者：ywzou <br>
     * 创建时间：2017年12月9日 <br>
     * 描述：保存数据 
     * @param map
     * @return
     */
    @Deprecated
    int insertMap(Map<String, Object>  map);
    /**
	 * 
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 更具申请的ID查询公积金的认证数据信息是否存在
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