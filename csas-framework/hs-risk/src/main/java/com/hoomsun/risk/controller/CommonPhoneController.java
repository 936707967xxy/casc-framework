/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.common.model.Result;
import com.hoomsun.risk.server.inter.CommonPhoneServerI;

/**
 * 作者：liusong <br>
 * 创建时间：2018年2月6日 <br>
 * 描述：
 */
@Controller
@RequestMapping("/common")
public class CommonPhoneController {

	@Autowired
	private CommonPhoneServerI commonPhoneServer;

	/**
	 * 上传Xls文件并解析到数据库
	 */
	@RequestMapping(value = "/activityImport.do", method = RequestMethod.POST)
	@ResponseBody
	public Result sendImport(HttpServletRequest request, @RequestParam("activityFile") MultipartFile fileUp, HttpServletResponse response) {
		if (fileUp == null || fileUp.isEmpty()) {
			return new Result(1001, "上传文件为空或没有数据");
		}
		Result result = null;
		try {
			result = commonPhoneServer.createCommonPhone(fileUp);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(1001, e.getMessage());
		}
		return result;
	}
}
