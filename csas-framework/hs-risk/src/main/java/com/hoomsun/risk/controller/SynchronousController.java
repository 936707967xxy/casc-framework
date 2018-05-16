/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;
import com.hoomsun.risk.server.inter.SynchronousServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月8日 <br>
 * 描述：同步数据
 */
@Controller
public class SynchronousController {
	@Autowired
	private SynchronousServerI synchronousServer;

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月8日 <br>
	 * 描述： 同步申请数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/risk/synchronous/apply.do")
	@ResponseBody
	public Json synchronousApply(HttpServletRequest request) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2018);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date startTime = calendar.getTime();
		Date endTime = new Date();
		return synchronousServer.synchronousData(new java.sql.Date(startTime.getTime()), new java.sql.Date(endTime.getTime()),3);
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月8日 <br>
	 * 描述： 同步联系人 通话详单数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/risk/synchronous/link.do")
	@ResponseBody
	public Json synchronousApplyLink(HttpServletRequest request) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2018);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date startTime = calendar.getTime();
		Date endTime = new Date();
		return synchronousServer.synchronousLinkData(new java.sql.Date(startTime.getTime()), new java.sql.Date(endTime.getTime()),3);
	}

}
