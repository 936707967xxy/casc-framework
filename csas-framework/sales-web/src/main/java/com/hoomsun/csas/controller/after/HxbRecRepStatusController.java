/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.after;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.after.api.server.HxbRecRepStatusServer;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年5月14日 <br>
 * 描述：hxb还款推送接口
 */
@Controller
public class HxbRecRepStatusController {

	private static final Logger log = LoggerFactory.getLogger(HxbRecRepStatusController.class);

	@Autowired
	private HxbRecRepStatusServer hxbRecRepStatusServer;

	@RequestMapping("/recRepStatus/send.do")
	public @ResponseBody JSONObject RecRepStatus(HttpServletRequest req) {
		log.info("---------进入还款状态推送接口-----------");

		JSONObject returnObj = hxbRecRepStatusServer.saveRecRepStatus(req);

		return returnObj;

	}

}
