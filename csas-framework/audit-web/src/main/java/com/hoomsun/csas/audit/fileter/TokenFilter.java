/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.fileter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.core.server.inter.SysFirewallServerI;
import com.hoomsun.core.util.SystemUtils;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月20日 <br>
 * 描述：登录拦截 分离 带黑名单名单
 */
public class TokenFilter implements Filter {
	private static Logger log = LoggerFactory.getLogger(SessionFilter.class);

	private String excludeMapping;

	@Override
	public void init(FilterConfig config) throws ServletException {
		log.info("====== Token Filter init! ====== ");
		this.excludeMapping = config.getInitParameter("excludeMapping");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = ((HttpServletRequest) req);
		HttpServletResponse response = (HttpServletResponse) res;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String contextPath = request.getContextPath();
		String IP = SystemUtils.getIpAddr(request);
		if (!StringUtils.isBlank(IP)) {
			ServletContext servletContext = request.getSession().getServletContext();
			ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			SysFirewallServerI firewallServer = ctx.getBean(SysFirewallServerI.class);
			List<String> blackList = firewallServer.findBlackList();
			if (null != blackList && blackList.size() > 0) {
				if (blackList.contains(IP)) {// 在黑名单中
					response.sendRedirect(contextPath + "/firewall.html");
					return;
				}
			}
		}

		String path = request.getRequestURI();
		if (contextPath.equals(path)) {
			chain.doFilter(req, res);
			return;
		}

		if (!StringUtils.isBlank(excludeMapping)) {
			String[] exclude = excludeMapping.split(",");
			boolean exl = false;
			for (String url : exclude) {
				if (url.endsWith("**")) {
					String start = url.substring(0, url.indexOf("**"));
					if (path.startsWith(contextPath + start)) {
						exl = true;
						break;
					}
				} else {
					if (path.endsWith(url)) {
						exl = true;
						break;
					}
				}
			}

			if (exl) {
				chain.doFilter(req, res);
				return;
			}
		}
		
		String tokenStr = request.getHeader("token");
		if (StringUtils.isBlank(tokenStr)) {
			PrintWriter writer = response.getWriter();
			JSONObject object = new JSONObject();
			object.put("success", "2000");
			object.put("msg", "登录超时");
			writer.write(object.toJSONString());
			writer.flush();
			writer.close();
			return;
		}
		
//		try {
//			tokenStr = RSA.encodePublic(tokenStr);
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("【###权限 Filter token 解密失败 ###】", e);
//		}
//		
//		Token token = JSONObject.parseObject(tokenStr, Token.class);
//		Date now = new Date();
//		if (now.getTime() > token.getExp().getTime()) {// token 超时
//
//		}

	}

	@Override
	public void destroy() {
		log.info("====== Token Filter destroy! ====== ");
	}

}
