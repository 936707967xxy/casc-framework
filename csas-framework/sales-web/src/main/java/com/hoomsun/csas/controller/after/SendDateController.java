/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.after;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.hoomsun.after.api.model.param.DataAll;
import com.hoomsun.after.api.server.SendDateServer;

/**
 * 作者：金世强 <br>
 * 创建时间：2018年3月21日 <br>
 * 描述：数据迁移，根据客户
 */
@Controller
public class SendDateController {

	@Autowired
	private SendDateServer sendDateServer;

	@RequestMapping("after/senddate.do")
	public @ResponseBody Map<String, Object> sendDate(@RequestBody JSONObject jb) {
		DataAll dataAll = JSONObject.toJavaObject(jb, DataAll.class);

		return sendDateServer.saveDate(dataAll);
	}
}
