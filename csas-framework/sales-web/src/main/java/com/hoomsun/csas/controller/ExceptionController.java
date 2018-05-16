/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月23日 <br>
 * 描述：全局异常处理
 */
@ControllerAdvice
public class ExceptionController {

	private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

	@ExceptionHandler({ Exception.class, RuntimeException.class })
	@ResponseBody
	public Json exp(HttpServletRequest request, Exception ex) {
		log.error("####==系统异常:", ex.getMessage());
		ex.printStackTrace();
		String msg = ex.getMessage();
		if (msg.length() > 30) {
			msg = msg.substring(0, 30);
		}

		return new Json(false, msg);
	}

}
