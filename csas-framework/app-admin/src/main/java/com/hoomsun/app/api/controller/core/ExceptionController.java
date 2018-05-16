/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.app.api.controller.core;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * 作者：liudongliang  <br>
 * 创建时间：2017年11月23日 <br>
 * 描述：
 */

//注解捕捉错误
@ControllerAdvice
public class ExceptionController {

  private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);
  

  @ExceptionHandler(Exception.class)     //报错进入
  @ResponseBody
  public Map<String, Object> exp(HttpServletRequest request, Exception ex) {
    Map<String, Object> result = new HashMap<String, Object>();
    log.error("####==系统异常:", ex.getMessage());
    ex.printStackTrace();
    result.put("errorInfo", "数据异常捕捉 ！！");
    result.put("errorCode", 1002);
    return result;
  }

}
