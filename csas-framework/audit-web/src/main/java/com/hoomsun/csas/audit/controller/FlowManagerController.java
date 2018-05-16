/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.repository.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.audit.server.inter.FlowManagerServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月14日 <br>
 * 描述：流程管理的控制层
 */
@Controller
public class FlowManagerController {
	private final static Logger log = LoggerFactory.getLogger(FlowManagerController.class);
	private FlowManagerServerI flowManagerServer;

	@Autowired
	public void setFlowManagerServer(FlowManagerServerI flowManagerServer) {
		this.flowManagerServer = flowManagerServer;
	}

	@RequestMapping(value = "/sys/flow/pager.do", method = RequestMethod.POST)
	@ResponseBody
	public Pager<Model> findPager(Integer page, Integer rows, String modelName) {
		return flowManagerServer.findPager(modelName, page, rows);
	}

	@RequestMapping(value = "/sys/flow/create.do", method = RequestMethod.POST)
	@ResponseBody
	public Json createFlow(HttpServletRequest request, String name, String key, String category, String description, @RequestParam("file") MultipartFile file) {
		if (file.isEmpty() || null == file) {
			return new Json(false, "流程添加失败!");
		}
		InputStream inputStream = null;
		try {
			inputStream = file.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.error("【创建流程model 关闭流异常】");
				}
			}
		}
		return flowManagerServer.createFlow(name, key, category, description, inputStream);
	}

	@RequestMapping(value = "/sys/flow/deploy.do", method = RequestMethod.POST)
	@ResponseBody
	public Json deployFlow(HttpServletRequest request, String modelId) {
		return flowManagerServer.deployFlow(modelId);
	}

	@RequestMapping(value = "/sys/flow/remove.do", method = RequestMethod.POST)
	@ResponseBody
	public Json deleteFlow(HttpServletRequest request, String modelId) {
		return flowManagerServer.deleteFlow(modelId);
	}
	
	public String updateModel() {
		return "";
	}
}
