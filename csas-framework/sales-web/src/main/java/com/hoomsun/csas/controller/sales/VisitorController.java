/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.sales;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.sales.api.model.Visitor;
import com.hoomsun.csas.sales.api.server.inter.VisitorServerI;

/**
 * 作者：liming <br>
 * 创建时间：2017年12月8日 <br>
 * 描述：回访控制层
 */
@Controller
public class VisitorController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private VisitorServerI visitorServer;

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 回访的分页查询
	 * 
	 * @param request
	 * @param page
	 * @param rows
	 * @param visTime
	 * @return
	 */
	@RequestMapping(value = "sys/visitor/findpagevisitor.do", method = RequestMethod.POST)
	@ResponseBody
	public Pager<Visitor> findPageVisitor(HttpServletRequest request, Integer page, Integer rows, String visTime, String visFkid) {
		log.info("回访分页查询=============");

		return visitorServer.findPage(page, rows, visTime, visFkid);

	}

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月8日 <br>
	 * 描述： 添加回访记录
	 * 
	 * @param visitor
	 * @return
	 */
	@RequestMapping(value = "sys/visitor/add.do", method = RequestMethod.POST)
	@ResponseBody
	public Json add(@RequestBody Visitor visitor) {
		log.info("进入添加回访记录=============");
		return visitorServer.addVisitor(visitor);

	}

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月8日 <br>
	 * 描述： 修改回访记录
	 * 
	 * @param visitor
	 * @return
	 */
	@RequestMapping(value = "sys/visitor/update.do", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody Visitor visitor) {

		return visitorServer.updateVisitor(visitor);
	}

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 删除回访记录
	 * 
	 * @param visId
	 * @return
	 */
	@RequestMapping(value = "sys/visitor/delete.do", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String visId) {
		return visitorServer.deleteVisitor(visId);

	}

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 根据id查找
	 * 
	 * @param visId
	 * @return
	 */
	@RequestMapping(value = "sys/visitor/findvisitorbyid.do", method = RequestMethod.GET)
	@ResponseBody
	public Visitor findVisitorById(String visId) {

		return visitorServer.findVisitorById(visId);
	}

}
