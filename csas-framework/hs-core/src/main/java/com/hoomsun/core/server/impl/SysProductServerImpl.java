/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.core.dao.SysProductAdvanceMapper;
import com.hoomsun.core.dao.SysProductMapper;
import com.hoomsun.core.dao.SysProductRateMapper;
import com.hoomsun.core.model.SysProduct;
import com.hoomsun.core.model.SysProductAdvance;
import com.hoomsun.core.model.SysProductRate;
import com.hoomsun.core.model.SysProductType;
import com.hoomsun.core.server.inter.SysProductServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月24日 <br>
 * 描述：
 */
@Service("productServer")
public class SysProductServerImpl implements SysProductServerI {

	private SysProductMapper productMapper;
	private SysProductRateMapper productRateMapper;
	private SysProductAdvanceMapper productAdvanceMapper;

	@Autowired
	public void setProductMapper(SysProductMapper productMapper) {
		this.productMapper = productMapper;
	}

	@Autowired
	public void setProductRateMapper(SysProductRateMapper productRateMapper) {
		this.productRateMapper = productRateMapper;
	}

	@Autowired
	public void setProductAdvanceMapper(
			SysProductAdvanceMapper productAdvanceMapper) {
		this.productAdvanceMapper = productAdvanceMapper;
	}

	@Override
	public Pager<SysProduct> findPageData(Integer page, Integer rows,
			String name) {
		if (null == page || rows == null) {
			return null;
		}
		rows = rows > 100 ? 100 : rows;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pageIndex", page);
		param.put("pageSize", rows);
		if (!StringUtils.isBlank(name)) {
			param.put("prodName", "%" + name + "%");
		}
		List<SysProduct> products = productMapper.findPager(param);

		Integer total = productMapper.findPagerCount(param);
		return new Pager<SysProduct>(products, total);
	}

	@Override
	public Json create(SysProduct product, String empId) {
		String id = product.getProdId();
		if (StringUtils.isBlank(id)) {
			id = PrimaryKeyUtil.getPrimaryKey();
		}
		product.setProdId(id);
		product.setCreateTime(new Date());
		product.setDelFlag(0);
		product.setCreateEmp(empId);
		initPrType(product.getProductRates());
		Integer result = productMapper.insertSelective(product);

		result += addProductRates(product);
		result += addProductAdvances(product);

		if (result > 0) {
			return new Json(true, "产品添加成功!");
		} else {
			return new Json(false, "产品添加失败!");
		}
	}

	@Override
	public Json update(SysProduct product, String empId) {
		String id = product.getProdId();
		if (StringUtils.isBlank(id)) {
			return new Json(false, "产品修改失败!参数有误!");
		}
		SysProduct oldPro = findById(id);
		if (product.equals(oldPro)) {
			if(product.getProdState() != oldPro.getProdState()){
				productMapper.updateByPrimaryKeySelective(product);
			}
			return new Json(true, "产品类型修改成功!");
		}else {
			// 先更新产品状态为失效
			productMapper.updateOldSign(id);

			// 再添加新产品
			product.setProdId(PrimaryKeyUtil.getPrimaryKey());
			initPrType(product.getProductRates());
			Json create = create(product, empId);

			// Integer result = productMapper.updateByPrimaryKeySelective(product);
			//
			// productRateMapper.deleteByProdId(product.getProdId());
			// result += addProductRates(product);
			// productAdvanceMapper.deleteByProdId(product.getProdId()); //
			// 更新的话先删除，后添加
			// result += addProductAdvances(product);

			if (create.getSuccess()) {
				return new Json(true, "产品类型修改成功!");
			} else {
				return new Json(false, "产品类型修改失败!");
			}
		}
		
	}

	
	@Override
	public Json delete(String prodId) {
		if (StringUtils.isBlank(prodId)) {
			return new Json(false, "产品类型删除失败!参数有误!");
		}

		Integer result = productMapper.updateDeleteSign(prodId);

		if (result > 0) {
			return new Json(true, "产品删除成功!");
		} else {
			return new Json(false, "产品删除失败!");
		}
	}

	@Override
	public SysProduct findById(String prodId) {
		if (StringUtils.isBlank(prodId)) {
			return null;
		}
		SysProduct product = productMapper.findById(prodId);
		return product;
	}
	
	  /**
     * 
     * 作者：liudongliang <br>
     * 创建时间：2017年12月20日 <br>
     * 描述： 关联两张表查询---app查询服务费
     * @param prodType
     * @return
     */
	@Override
	public SysProduct findByName(String findByName){
		if (StringUtils.isBlank(findByName)) {
			return null;
		}
		SysProduct product = productMapper.findByName(findByName);
		return product;
	}

	@Override
	public Json multiDelete(List<String> prodIds) {
		int result = productMapper.updateMultiDeleteSign(prodIds);
		if (result > 0) {
			return new Json(true, "删除多个产品成功！");
		} else {
			return new Json(false, "删除多个产品失败！");
		}
	}

	private int addProductAdvances(SysProduct product) {
		List<SysProductAdvance> productAdvances = product.getProductAdvances();
		int count = 0;
		if (null != productAdvances && productAdvances.size() > 0) {
			for (SysProductAdvance proAdv : productAdvances) {
				proAdv.setId(PrimaryKeyUtil.getPrimaryKey());
				proAdv.setProdId(product.getProdId());
				count += productAdvanceMapper.insertSelective(proAdv);
			}
		}
		return count;
	}

	private int addProductRates(SysProduct product) {
		List<SysProductRate> productRates = product.getProductRates();
		int count = 0;
		if (null != productRates && productRates.size() > 0) {
			for (SysProductRate proRat : productRates) {
				proRat.setPrId(PrimaryKeyUtil.getPrimaryKey());
				proRat.setProdId(product.getProdId());
				count += productRateMapper.insertSelective(proRat);
			}
		}
		return count;
	}

	@Override
	public List<SysProduct> findByTypeId(String prodTypeId) {
		return productMapper.findByTypeIdShow(prodTypeId);
	}

	// @Override
	// public SysProduct findByIdRate(String prodId) {
	// if (StringUtils.isBlank(prodId)) {
	// return null;
	// }
	// SysProduct product = productMapper.findById(prodId);
	// initProductAdvances(product);
	// return product;
	// }
	//
	//
	// private void initProductAdvances(SysProduct product){
	// List<SysProductAdvance> productAdvances = null;
	// //productAdvances =
	// productAdvanceMapper.selectByProdId(product.getProdId());
	// product.setProductAdvances(productAdvances);
	// }

	/**
	 * 
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 获取app小产品
	 * 
	 * @param prodId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findproductData(Map<String, Object> para) {
		return productMapper.findproductData(para);
	}

	
	/**
	 * 
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 获取门店小产品
	 * 
	 * @param prodId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findproductCreData(Map<String, Object> para){
		return productMapper.findproductCreData(para);
	}
	
	@Override
	public SysProduct findByTypeIdFinalAudit(String prodId) {
		return productMapper.findByTypeIdFinalAudit(prodId);
	}
	
	/**
     * 
     * 作者：liudongliang <br>
     * 创建时间：2017年12月14日 <br>
     * 描述： 获取小产品--添加申请时 
     * @param prodId
     * @return
     */
	@Override
	public SysProduct findproductbyId(String prodId){
		return productMapper.findproductbyId(prodId);
	}
	
	
	@Override
	public SysProduct findAllById(String prodId) {
		if (StringUtils.isBlank(prodId)) {
			return null;
		}
		SysProduct product = productMapper.findAllById(prodId);
		return product;
	}

	
	private void initPrType(List<SysProductRate> productRates){
		if (productRates != null && productRates.size()>0){
			for (int i=0; i<productRates.size(); i++) {
				switch (productRates.get(i).getPrType()) {
				case "audit":
					productRates.get(i).setPrName("审核费");
					break;
				case "service":
					productRates.get(i).setPrName("服务费");				
					break;
				case "consult":
					productRates.get(i).setPrName("咨询费");			
					break;
				default:
					productRates.get(i).setPrName("其他");		
					break;
				}
			}
		}
	}

	@Override
	public SysProductType findProTypeByProId(String prodId) {
		return productMapper.findProTypeByProId(prodId);
	}

	@Override
	public List<SysProduct> findSameTypePros(String prodId) {
		// TODO Auto-generated method stub
		return productMapper.findSameTypePros(prodId);
	}
}
