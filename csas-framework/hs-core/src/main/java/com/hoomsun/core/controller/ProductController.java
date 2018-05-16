/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.anno.Permission;
import com.hoomsun.core.model.SysProduct;
import com.hoomsun.core.model.SysProductType;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.server.inter.SysProductServerI;
import com.hoomsun.core.util.LoginUtil;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月24日 <br>
 * 描述：产品管理控制层
 */
@Controller
public class ProductController {
	private SysProductServerI productServer;

	@Autowired
	public void setProductServer(SysProductServerI productServer) {
		this.productServer = productServer;
	}

	@Permission("prod_query")
	@RequestMapping("/sys/product/pager.do")
	@ResponseBody
	public Pager<SysProduct> findPager(Integer page, Integer rows, String name, HttpServletRequest request) {
		return productServer.findPageData(page, rows, name);
	}

	@Permission("prod_create")
	@RequestMapping("/sys/product/create.do")
	@ResponseBody
	public Json create(@RequestBody SysProduct product, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if( session==null){
			return new Json(false, "登录异常，请刷新页面！");
		}
		String empId = session.getEmpId();
		return productServer.create(product, empId);
	}


	@Permission("prod_update")
	@RequestMapping("/sys/product/update.do")
	@ResponseBody
	public Json update(@RequestBody SysProduct product, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if( session==null){
			return new Json(false, "登录异常，请刷新页面！");
		}
		String empId = session.getEmpId();
		return productServer.update(product, empId);
	}

	@Permission("prod_query")
	@RequestMapping("/sys/product/query.do")
	@ResponseBody
	public SysProduct findById(String id, HttpServletRequest request) {
		return productServer.findById(id);
	}

	@Permission("prod_delete")
	@RequestMapping("/sys/product/remove.do")
	@ResponseBody
	public Json remove(String id, HttpServletRequest request) {
		return productServer.delete(id);
	}
	
	@Permission("prod_delete")
	@RequestMapping("/sys/product/removemulti.do")
	@ResponseBody
	public Json removeMulti(@RequestBody List<String> prodIds, HttpServletRequest request) {
		return productServer.multiDelete(prodIds);
	}
	
//	@RequestMapping("/sys/product/queryrate.do")
//	@ResponseBody
//	public SysProduct findByIdRate(String id, HttpServletRequest request) {
//		return productServer.findByIdRate(id);
//	}
	

	@RequestMapping("/sys/product/querybytype.do")
	@ResponseBody
	public List<SysProduct> findAllProByType(String prodTypeId) {
		return productServer.findByTypeId(prodTypeId);
	}
	
	// 小产品查大产品信息
	@RequestMapping("/sys/product/queryprotype.do")
	@ResponseBody
	public SysProductType findProTypeByProId(String prodId) {
		return productServer.findProTypeByProId(prodId);
	}
	
	// 小产品查同一类小产品list
	@RequestMapping("/sys/product/querysametypepros.do")
	@ResponseBody
	public List<SysProduct> findSameTypePros(String prodId) {
		return productServer.findSameTypePros(prodId);
	}
}
