package com.hoomsun.csas.sales.dao;

import java.util.Map;

import com.hoomsun.csas.sales.api.model.UserTaobao;
/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月9日 <br>
 * 描述：淘宝 
 */
public interface UserTaobaoMapper {
    int deleteByPrimaryKey(String tbId);

    int insert(UserTaobao record);

    int insertSelective(UserTaobao record);

    UserTaobao selectByPrimaryKey(String tbId);

    int updateByPrimaryKeySelective(UserTaobao record);

    int updateByPrimaryKey(UserTaobao record);
    /**
     * 作者：ywzou <br>
     * 创建时间：2017年12月9日 <br>
     * 描述：根据申请ID获取数据 
     * @param applyId
     * @return
     */
    UserTaobao selectByApplyId(String applyId);
    /**
     * 作者：ywzou <br>
     * 创建时间：2017年12月9日 <br>
     * 描述：数据的存储 
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
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月23日 <br>
	 * 描述： 更具申请ID获取主键
	 * @param applyId
	 * @return
	 */
	String findIdByApplyId(String applyId);
}