/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.inter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.SysProductType;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月24日 <br>
 * 描述：产品类型
 */
public interface SysProductTypeServerI {

	Pager<SysProductType> findPageData(Integer page, Integer rows, String name);

	Json create(SysProductType productType);

	Json update(SysProductType productType);

	Json delete(String prodId);

	SysProductType findById(String prodId);

	List<SysProductType> findAll();
	
	String setProdUrlByUploadPath(MultipartFile prodFile, HttpServletRequest request);
	
	List<SysProductType> findAllProType(Short isonline);
	
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
	 * 创建时间：2017年12月15日 <br>
	 * 描述： 获取所有大产品信息(仅有类型和id)
	 * @return
	 */
	SysProductType selectByPrimaryKey(String prodId);
	
	
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
	List<SysProductType> findTypeExcept(String except);
}
