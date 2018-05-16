/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hoomsun.core.anno.Permission;
import com.hoomsun.core.model.Session;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月12日 <br>
 * 描述：拦截器 
 */
public class AuthInterceptor implements HandlerInterceptor{
	private final static Logger log = LoggerFactory.getLogger(AuthInterceptor.class);
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		/*log.info("##### 拦截路径:" + request.getServletPath());
		Session session = (Session) request.getSession().getAttribute(Session.KEY);
		if (null == session) {
			response.sendRedirect("/");
			return false;
		}
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();//得到执行的方法
		//检查方法上有没有使用@Permission注解
		Permission anno = method.getAnnotation(Permission.class);
		if (anno != null) {
			String value = method.getAnnotation(Permission.class).value();//得到注解的值
			if (!session.getActions().contains(value)) { //没有权限
				   response.setStatus(403); 
				   response.setContentType("text/html;charset=utf-8");
				   PrintWriter out = response.getWriter();
				   out.println("<h2>你没有执行该操作的权限!</h2>");
				   out.flush();
				   out.close();
				   return false;
			}
		}*/
		return true;
	}

}
