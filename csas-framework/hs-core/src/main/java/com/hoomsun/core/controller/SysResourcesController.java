/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.ComboTree;
import com.hoomsun.common.model.DataGrid;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.TreeNode;
import com.hoomsun.core.anno.LoggerAnnotation;
import com.hoomsun.core.anno.Permission;
import com.hoomsun.core.enums.OptionType;
import com.hoomsun.core.model.SysResources;
import com.hoomsun.core.server.inter.SysResourcesServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月11日 <br>
 * 描述：系统资源管理的控制层
 */
@Controller
public class SysResourcesController {

	private SysResourcesServerI resourcesServer;

	@Autowired
	public void setResourcesServer(SysResourcesServerI resourcesServer) {
		this.resourcesServer = resourcesServer;
	}

	// 到资源列表页面
	@Permission("dataRes_query")
	@RequestMapping(value = "/sys/resource/listview.do", method = RequestMethod.GET)
	public String resourcesView() {
		return "resource/listview";
	}

	@Permission("dataRes_query")
	@RequestMapping(value = "/sys/resource/treegrid.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<SysResources> findDataGrid(String resName) {
		DataGrid<SysResources> dataGrid = resourcesServer.findPageData(null, null, resName);
		return dataGrid.getRows();
	}
	
	@Permission("dataRes_add")
	@RequestMapping(value = "/sys/resource/addview.do", method = { RequestMethod.GET })
	public String addView() {
		return "resource/addview";
	}

	@LoggerAnnotation(moduleName = "资源管理", option = "添加资源", optionType = OptionType.CREATE)
	@Permission("dataRes_add")
	@RequestMapping(value = "/sys/resource/create.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json addResources(SysResources resources, HttpServletRequest request) {
		return resourcesServer.createResources(resources);
	}

	@Permission("dataRes_edit")
	@RequestMapping(value = "/sys/resource/updateview.do", method = { RequestMethod.GET })
	public String editView(String resId, HttpServletRequest request) {
		SysResources resources = resourcesServer.findById(resId);
		request.setAttribute("SYS_RESOURCES_KEY", resources);
		return "resource/editview";
	}

	@LoggerAnnotation(moduleName = "资源管理", option = "修改资源", selectMethod = "findById", idName = "resId", idIndex = 0, beanId = "resourcesServer", optionType = OptionType.UPDATE)
	@Permission("dataRes_edit")
	@RequestMapping(value = "/sys/resource/update.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json editResources(SysResources resources, HttpServletRequest request) {
		return resourcesServer.updateResources(resources);
	}

	@LoggerAnnotation(moduleName = "资源管理", option = "删除资源", selectMethod = "findById", idName = "resId", idIndex = 0, beanId = "resourcesServer", optionType = OptionType.DELETE)
	@Permission("dataRes_delete")
	@RequestMapping(value = "/sys/resource/remove.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Json deleteResource(String resId, HttpServletRequest request) {
		return resourcesServer.deleteResources(resId);
	}

	@RequestMapping(value = "/sys/resource/combotree.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public List<ComboTree> findComboTree() {
		return resourcesServer.findComboTree();
	}

	@RequestMapping("/sys/resource/treenode.do")
	@ResponseBody
	public List<TreeNode> findTreeNode() {
		return resourcesServer.findTreeNode();
	}
}
