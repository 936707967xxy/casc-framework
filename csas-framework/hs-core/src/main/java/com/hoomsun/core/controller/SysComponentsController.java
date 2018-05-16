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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.SysComponents;
import com.hoomsun.core.model.vo.Tree;
import com.hoomsun.core.server.inter.SysComponentsServerI;

/**
 * 作者：liusong <br>
 * 创建时间：2017年11月16日 <br>
 * 描述：
 */
@Controller
public class SysComponentsController {
	private SysComponentsServerI componentsServer;

	@Autowired
	public void setComponentsServer(SysComponentsServerI componentsServer) {
		this.componentsServer = componentsServer;
	}

	// add
	@RequestMapping(value = "/sys/components/create.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json addComponents(SysComponents record, HttpServletRequest request) {
		return componentsServer.createComponents(record);
	}

	// delete
	@RequestMapping(value = "/sys/components/remove.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json deleteComponents(String id, HttpServletRequest request) {
		return componentsServer.deleteComponents(id);
	}
	
	@RequestMapping(value = "/sys/components/removes.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json deleteComponents(@RequestBody String[] ids, HttpServletRequest request) {
		return componentsServer.deleteBatchComponents(ids);
	}
	
	// update
	@RequestMapping(value = "/sys/components/update.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json editComponents(SysComponents record, HttpServletRequest request) {
		return componentsServer.updateComponents(record);
	}

	// page
	@RequestMapping(value = "/sys/components/page.do", method = { RequestMethod.POST })
	@ResponseBody
	public Pager<SysComponents> findPageData(Integer page, Integer rows, String name) {
		return componentsServer.findPageData(page, rows, name);
	}

	@RequestMapping(value = "/sys/components/tree.do", method = { RequestMethod.POST })
	@ResponseBody
	public List<Tree> findPageData(String modelName) {
		return componentsServer.findTree(modelName);
	}

	@RequestMapping(value = "/sys/components/query.do", method = { RequestMethod.GET })
	@ResponseBody
	public SysComponents findById(String id) {
		return componentsServer.findById(id);
	}
}
