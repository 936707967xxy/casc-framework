///**
// * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
// */
//package com.hoomsun.core.controller;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.hoomsun.core.model.Json;
//
//
///**
// * 作者：liusong <br>
// * 创建时间：2017年10月12日 <br>
// * 描述：
// */
//@ControllerAdvice
//public class ExceptionController {
//
//	private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);
//
//	@ExceptionHandler(Exception.class)
//	@ResponseBody
//	public Json exp(HttpServletRequest request, Exception ex) {
//		log.error("####【统一异常处理】" + ex.getMessage());
//
//		Json json = new Json(false, "系统异常");
//		return json;
//	}
//
//}
