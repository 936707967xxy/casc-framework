/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.common.util.SystemUtils;
import com.hoomsun.common.util.UploadPathUtil;
import com.hoomsun.core.dao.SysProductTypeMapper;
import com.hoomsun.core.model.SysProductType;
import com.hoomsun.core.server.inter.SysProductTypeServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月24日 <br>
 * 描述：产品类型管理的业务实现
 */
@Service("productTypeServer")
public class SysProductTypeServerImpl implements SysProductTypeServerI {

	private SysProductTypeMapper productTypeMapper;
	
	private UploadPathUtil uploadPathUtil;
	
	private Logger log = LoggerFactory.getLogger(SysProductTypeServerImpl.class);
	
	@Autowired
	public void setProductTypeMapper(SysProductTypeMapper productTypeMapper) {
		
		this.productTypeMapper = productTypeMapper;
	}
	
	@Autowired
	public void setUploadPathUtil(UploadPathUtil uploadPathUtil) {
		this.uploadPathUtil = uploadPathUtil;
	}

	@Override
	public Pager<SysProductType> findPageData(Integer page, Integer rows, String name) {
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

		List<SysProductType> productTypes = productTypeMapper.findPager(param);
		Integer total = productTypeMapper.findPagerCount(param);
		return new Pager<SysProductType>(productTypes, total);
	}

	@Override
	public Json create(SysProductType productType) {
		String name = productType.getProdName();
		System.err.println(productType);
		if (StringUtils.isBlank(name)) {
			return new Json(false, "产品类型创建失败!参数有误!");
		}
		
		String id = productType.getProdId();
		if (StringUtils.isBlank(id)) {
			id = PrimaryKeyUtil.getPrimaryKey();
		}
		productType.setProdId(id);
		productType.setDeleteSign((short)(0));
		Integer result = productTypeMapper.insertSelective(productType);
		if (result > 0) {
			return new Json(true, "产品类型添加成功!");
		} else {
			return new Json(false, "产品类型添加失败!");
		}
	}

	@Override
	public Json update(SysProductType productType) {
		String name = productType.getProdName();
		if (StringUtils.isBlank(name)) {
			return new Json(false, "产品类型修改失败!参数有误!");
		}
		
		String id = productType.getProdId();
		if (StringUtils.isBlank(id)) {
			return new Json(false, "产品类型修改失败!参数有误!");
		}
		Integer result = productTypeMapper.updateByPrimaryKeySelective(productType);
		if (result > 0) {
			return new Json(true, "产品类型修改成功!");
		} else {
			return new Json(false, "产品类型修改失败!");
		}
	}

	@Override
	public Json delete(String prodId) {
		if (StringUtils.isBlank(prodId)) {
			return new Json(false, "产品类型删除失败!参数有误!");
		}

		Integer result = productTypeMapper.updateDeleteSign(prodId);
		if (result > 0) {
			return new Json(true, "产品类型删除成功!");
		} else {
			return new Json(false, "产品类型删除失败!");
		}
	}

	@Override
	public SysProductType findById(String prodId) {
		if (StringUtils.isBlank(prodId)) {
			return null;
		}
		return productTypeMapper.selectByPrimaryKey(prodId);
	}

	@Override
	public List<SysProductType> findAll() {
		return productTypeMapper.findAllData();
	}

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年11月24日 <br>
	 * 描述： 文件上传 ,返回存储在服务器上的地址
	 * @param prodFile 上传的文件
	 */
	@Override
	public String setProdUrlByUploadPath(MultipartFile prodFile, HttpServletRequest request) {
		if (!prodFile.isEmpty() && !StringUtils.isBlank(prodFile.getOriginalFilename())) {
			try {
				String viewPath = uploadPathUtil.productUrl() + "/";
				String fileName = prodFile.getOriginalFilename();
				String fileType = fileName.substring(fileName.lastIndexOf("."));
				String saveName = new Date().getTime() + fileType;
//				String fileUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
				String fileUrl = request.getScheme()+"://"+SystemUtils.getLocalIp()+":"+request.getServerPort() + request.getContextPath(); 
				
				File f = new File(uploadPathUtil.saveProductPath());
				if (!f.exists()) {
					f.mkdirs();
				}
				// 文件保存路径
				String filePath = uploadPathUtil.saveProductPath() + File.separator + saveName;
				// 转存文件
				prodFile.transferTo(new File(filePath));
				fileUrl = fileUrl + viewPath + saveName;
				return fileUrl;
			} catch (Exception e) {
				log.debug("文件上传服务器没有成功!");
			}
		}
		return null;
	}

	@Override
	public List<SysProductType> findAllProType(Short isonline) {
		return productTypeMapper.findAllProType(isonline, null);
	}
	
	/**
	 * 
	 * 作者：刘栋梁 <br>
	 * 创建时间：2017年12月09日 <br>
	 * 描述： 获取所有线上产品类型(仅有类型和id)
	 * @return
	 */
	@Override
	public List<SysProductType> findOnlineData(String isonline){
		return productTypeMapper.findOnlineData( isonline);
	}
	
	/**
	 * 
	 * 作者：刘栋梁 <br>
	 * 创建时间：2017年12月15日 <br>
	 * 描述： 获取所有大产品信息(仅有类型和id)
	 * @return
	 */
	@Override
	public SysProductType selectByPrimaryKey(String prodId){
		return productTypeMapper.selectByPrimaryKey(prodId);
	}
	
	
	/**
	 * 
	 * 作者：刘栋梁 <br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述：额度接口返回产品信息
	 * @return
	 */
	@Override
	public SysProductType findProTypebyCode(String prodCode){
		return productTypeMapper.findProTypebyCode(prodCode);
	}

	@Override
	public List<SysProductType> findTypeExcept(String except) {
		return productTypeMapper.findTypeExcept(except);
	}
	
}
