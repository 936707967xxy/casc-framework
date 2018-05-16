package com.hoomsun.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.core.model.SysProduct;
import com.hoomsun.core.model.SysProductType;

public interface SysProductMapper {
	int deleteByPrimaryKey(String prodId);

	int insert(SysProduct record);

	int insertSelective(SysProduct record);

	SysProduct selectByPrimaryKey(String prodId);

	int updateByPrimaryKeySelective(SysProduct record);

	int updateByPrimaryKey(SysProduct record);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年11月29日 <br>
	 * 描述：伪删除一个产品
	 * 
	 * @param prodId
	 * @return
	 */
	int updateDeleteSign(String prodId);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年11月29日 <br>
	 * 描述：伪删除多个产品
	 * 
	 * @param prodIds
	 * @return
	 */
	int updateMultiDeleteSign(@Param("prodIds") List<String> prodIds);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月24日 <br>
	 * 描述：
	 * 
	 * @param param
	 *            keys:prodName pageIndex pageSize
	 * @return
	 */
	List<SysProduct> findPager(Map<String, Object> param);

	Integer findPagerCount(Map<String, Object> param);

	List<SysProduct> findByTypeId(String prodType);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年11月29日 <br>
	 * 描述： 关联两张表查询
	 * 
	 * @param prodType
	 * @return
	 */
	SysProduct findById(String prodId);

	/**
	 * 
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： 关联两张表查询---app查询服务费
	 * 
	 * @param prodType
	 * @return
	 */
	SysProduct findByName(String findByName);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 通过大产品类型获取对应的小产品ID和小产品名称，用于下拉列表
	 * 
	 * @param prodId
	 * @return
	 */
	List<SysProduct> findByTypeIdShow(String prodId);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 修改产品的时候先更改当前产品状态，在将新修改的产品作为新产品添加
	 * 
	 * @param prodId
	 * @return
	 */
	int updateOldSign(String prodId);

	/**
	 * 
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 获取app小产品
	 * 
	 * @param prodId
	 * @return
	 */
	List<Map<String, Object>> findproductData(Map<String, Object> para);
	
	/**
	 * 
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 获取门店小产品
	 * 
	 * @param prodId
	 * @return
	 */
	List<Map<String, Object>> findproductCreData(Map<String, Object> para);

	SysProduct findByTypeIdFinalAudit(String prodId);

	/**
	 * 
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月14日 <br>
	 * 描述： 获取小产品--添加申请时
	 * 
	 * @param prodId
	 * @return
	 */
	SysProduct findproductbyId(String prodId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月26日 <br>
	 * 描述：获取产品的所有数据 包括费率 和 提前还款设置
	 * 
	 * @param prodId
	 * @return
	 */
	SysProduct findAllById(String prodId);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月19日 <br>
	 * 描述： 通过小产品id获取大产品信息
	 * @param prodId
	 * @return
	 */
	SysProductType findProTypeByProId(String prodId);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月19日 <br>
	 * 描述： 通过小产品id获取同一类型的产品
	 * @param prodId
	 * @return
	 */
	List<SysProduct> findSameTypePros(String prodId);

}