/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.anno.Permission;
import com.hoomsun.core.model.SysProductType;
import com.hoomsun.core.server.inter.SysProductTypeServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月24日 <br>
 * 描述：产品类型
 */
@Controller
public class ProductTypeController {

	private SysProductTypeServerI productTypeServer;

	@Autowired
	public void setProductTypeServer(SysProductTypeServerI productTypeServer) {
		this.productTypeServer = productTypeServer;
	}

	@Permission("prodt_query")
	@RequestMapping("/sys/producttype/pager.do")
	@ResponseBody
	public Pager<SysProductType> findPager(Integer page, Integer rows, String name, HttpServletRequest request) {
		return productTypeServer.findPageData(page, rows, name);
	}

	@Permission("prodt_create")
	@RequestMapping("/sys/producttype/create.do")
	@ResponseBody
	public Json create(SysProductType productType, HttpServletRequest request) {
		return productTypeServer.create(productType);
	}

	@Permission("prodt_update")
	@RequestMapping("/sys/producttype/update.do")
	@ResponseBody
	public Json update(SysProductType productType, HttpServletRequest request) {
		return productTypeServer.update(productType);
	}

	@Permission("prodt_query")
	@RequestMapping("/sys/producttype/query.do")
	@ResponseBody
	public SysProductType findById(String id, HttpServletRequest request) {
		return productTypeServer.findById(id);
	}
	
	@Permission("prodt_delete")
	@RequestMapping("/sys/producttype/remove.do")
	@ResponseBody
	public Json remove(String id, HttpServletRequest request) {
		return productTypeServer.delete(id);
	}

	@RequestMapping("/sys/producttype/queryall.do")
	@ResponseBody
	public List<SysProductType> findAll(HttpServletRequest request) {
		return productTypeServer.findAll();
	}
	
	@RequestMapping("/sys/producttype/upfileurl.do")
	@ResponseBody
	public String getUpFileURL(MultipartFile file, HttpServletRequest request) {
		return productTypeServer.setProdUrlByUploadPath(file, request);
	}

	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月17日 <br>
	 * 描述： 返回产品类型列表
	 * @return
	 */
	@RequestMapping("/sys/producttype/queryalltype.do")
	@ResponseBody
	public List<SysProductType> findAllType(Short isonline) {
		return productTypeServer.findAllProType(isonline);
	}
	
	/**
	 * 
	 * 作者：yg.zhao <br>
	 * 创建时间：2018年1月17日 <br>
	 * 描述： 返回产品类型列表除了产品ID
	 * @return
	 */
	@RequestMapping("/sys/producttype/findtypeexcept.do")
	@ResponseBody
	public List<SysProductType> findTypeExcept(String except) {
		return productTypeServer.findTypeExcept(except);
	}
}
