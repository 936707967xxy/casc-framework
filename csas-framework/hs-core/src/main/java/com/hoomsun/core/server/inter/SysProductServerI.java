/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.inter;

import java.util.List;
import java.util.Map;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.SysProduct;
import com.hoomsun.core.model.SysProductType;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月24日 <br>
 * 描述：产品管理业务接口
 */
public interface SysProductServerI {

	Pager<SysProduct> findPageData(Integer page, Integer rows, String name);

	Json create(SysProduct product, String empId);

	Json update(SysProduct product, String empId);

	Json delete(String prodId);

	SysProduct findById(String prodId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月26日 <br>
	 * 描述： 获取产品的所有数据   包括费率  和  提前还款设置
	 * @param prodId
	 * @return
	 */
	SysProduct findAllById(String prodId);
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

	// SysProduct findByIdRate(String prodId);

	Json multiDelete(List<String> prodIds);

	List<SysProduct> findByTypeId(String prodTypeId);

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

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 终审表插入的产品信息
	 * 
	 * @param prodId
	 * @return
	 */
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
	 * 描述： 通过下产品id获取所有同一类型的小产品
	 * @param prodId
	 * @return
	 */
	List<SysProduct> findSameTypePros(String prodId);
}
