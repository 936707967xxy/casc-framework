package com.hoomsun.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.core.model.SysProductType;

public interface SysProductTypeMapper {
	int deleteByPrimaryKey(String prodId);

	int insert(SysProductType record);

	int insertSelective(SysProductType record);

	SysProductType selectByPrimaryKey(String prodId);

	int updateByPrimaryKeySelective(SysProductType record);

	int updateByPrimaryKey(SysProductType record);

	List<SysProductType> findPager(Map<String, Object> param);

	Integer findPagerCount(Map<String, Object> param);

	List<SysProductType> findAllData();

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年11月27日 <br>
	 * 描述： 删除的时候不是真删除，更改标志位， 标志位值1为删除，0为不删除
	 * 
	 * @param prodId
	 * @param deleteSign
	 * @return
	 */
	int updateDeleteSign(String prodId);
	
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年11月29日 <br>
	 * 描述： 删除多个产品
	 * @param prodIds
	 * @return
	 */
	int updateMultiDeleteSign(@Param("prodIds")List<String> prodIds);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年11月30日 <br>
	 * 描述： 获取所有产品类型(仅有类型和id)
	 * @param isonline 
	 * @return
	 */
	List<SysProductType> findAllProType(@Param("isonline") Short isonline, @Param("prodName") String prodName);
	
	/**
	 * 
	 * 作者：刘栋梁 <br>
	 * 创建时间：2017年12月09日 <br>
	 * 描述： 获取所有线上产品类型(仅有类型和id)
	 * @return
	 */
	List<SysProductType> findOnlineData(String isonline);
	
	
	/**
	 * 
	 * 作者：刘栋梁 <br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述：额度接口返回产品信息
	 * @return
	 */
	SysProductType findProTypebyCode(String prodCode);

	/**
	 * 
	 * 作者：ygzhao <br>
	 * 创建时间：2018年01月18日 <br>
	 * 描述：参数：不展示产品ID
	 * @return
	 */
	List<SysProductType> findTypeExcept(@Param("except")String except);
}