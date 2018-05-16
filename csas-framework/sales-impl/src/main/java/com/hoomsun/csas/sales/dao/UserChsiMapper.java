package com.hoomsun.csas.sales.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.csas.sales.api.model.UserChsi;
/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月9日 <br>
 * 描述 ：学历 
 */
public interface UserChsiMapper {
	int deleteByPrimaryKey(String chsiId);

	int insert(UserChsi record);

	int insertSelective(UserChsi record);

	UserChsi selectByPrimaryKey(String chsiId);

	int updateByPrimaryKeySelective(UserChsi record);

	int updateByPrimaryKey(UserChsi record);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述：根据申请ID获取数据 
	 * @param applyId
	 * @return
	 */
	UserChsi selectByApplyId(String applyId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述：数据的存储 
	 * @param map
	 * @return
	 */
	@Deprecated
	int insertMap(Map<String, Object> map);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述：查询流程节点 
	 * @param map
	 * @return
	 */
	@Deprecated
	List<Map<String, Object>> selectActrivity(Map<String, Object> map);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 更具申请的ID查询学历的认证数据信息是否存在
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