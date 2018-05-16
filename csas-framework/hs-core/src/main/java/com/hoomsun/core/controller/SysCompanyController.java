/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.ComboTree;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.anno.Permission;
import com.hoomsun.core.model.SysCompany;
import com.hoomsun.core.model.vo.VueLazyTree;
import com.hoomsun.core.server.inter.SysCompanyServerI;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年9月11日 <br>
 * 描述：公司管理的控制层
 */
@Controller
public class SysCompanyController {
	private SysCompanyServerI companyServer;

	@Autowired
	public void setComServer(SysCompanyServerI companyServer) {
		this.companyServer = companyServer;
	}

	// 到公司列表页面
	@Permission("com_query")
	@RequestMapping(value = "/sys/company/listview.do", method = RequestMethod.GET)
	public String companyView() {
		return "company/listview";
	}

	
	@RequestMapping(value = "/sys/company/page.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Pager<SysCompany> findPagerData(Integer page, Integer rows, String comName) {
		return companyServer.findPage(page, rows, comName);
	}

	
	@RequestMapping(value = "/sys/company/create.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json addCompany(SysCompany company, HttpServletRequest request) {
		return companyServer.createCom(company);
	}

	@RequestMapping(value = "/sys/company/query.do", method = { RequestMethod.GET })
	@ResponseBody
	public SysCompany findById(String id) {
		return companyServer.findById(id);
	}
	
	
	@RequestMapping(value = "/sys/company/update.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json editCompany(SysCompany company, HttpServletRequest request) {
		return companyServer.updateCom(company);
	}

	@Permission("com_delete")
	@RequestMapping(value = "/sys/company/remove.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Json deleteCompany(String comId, HttpServletRequest request) {
		return companyServer.deleteCom(comId);
	}

	@RequestMapping(value = "/sys/company/combotree.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public List<ComboTree> findComboTree() {
		return companyServer.findComboTree();
	}
	
	@RequestMapping(value = "/sys/company/findalldata.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public List<SysCompany> findalldata() {
		return companyServer.findAllData();
	}
	
	@RequestMapping(value = "/sys/company/treedata.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public List<Map<String, Object>> treeData() {
		return companyServer.treeData();
	}
	
	@RequestMapping(value = "/sys/company/vuetreedata.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public  List<VueLazyTree> vueTreeData(String parentId) {
		return companyServer.findVueTreeData(parentId);
	}
	
}
