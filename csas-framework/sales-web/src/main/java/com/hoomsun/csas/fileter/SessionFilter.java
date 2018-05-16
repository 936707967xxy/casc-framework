/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.fileter;

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
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.server.inter.SysFirewallServerI;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.core.util.SystemUtils;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月26日 <br>
 * 描述：登录信息拦截
 */
public class SessionFilter implements Filter {
	private static Logger log = LoggerFactory.getLogger(SessionFilter.class);

	private String excludeMapping;

	@Override
	public void destroy() {
		log.info("====== Session Filter destroy! ====== ");
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

		SessionRouter session = LoginUtil.getLoginSession(request);
		if (null != session) {
			chain.doFilter(req, res);
			return;
		} else {
//			response.setStatus(4004); // "登录超时!
//			response.setContentType("text/html;charset=UTF-8");
//			PrintWriter out = response.getWriter();
//			out.println("<h2>登录超时!</h2>");
//			out.flush();
//			out.close();
			JSONObject obj = new JSONObject();
			obj.put("code", 4004);
			obj.put("msg", "登录超时!");
			PrintWriter out = response.getWriter();
			out.println(obj.toJSONString());
			out.flush();
			out.close();
			return;
			// HttpServletResponse response = (HttpServletResponse) res;
			// request.setAttribute("LOGIN_ERROR_MESSAGE", "登录超时!");
			// request.getRequestDispatcher("/").forward(req, res);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		log.info("====== Session Filter init! ====== ");
		this.excludeMapping = config.getInitParameter("excludeMapping");
	}

	@SuppressWarnings("unused")
	private String getErrorHtml(String contextPath) {
		StringBuilder html = new StringBuilder("<html><head>");
		html.append("<title>红上金融信用审核系统</title>");
		html.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		html.append("<link rel=\"shortcut icon\" href=\"" + contextPath + "/resources/images/hsfs_favicon.ico\" type=\"image/x-icon\" />");
		html.append("<style type=\"text/css\">");
		html.append("*{padding: 0;margin: 0}");
		html.append("body{background-image:url(" + contextPath + "/resources/images/firewall.png);}");
		html.append(".copyright{height:60px;margin:0 auto;position:absolute;bottom:0;line-height:60px;width:100%;text-align:center;}");
		html.append("</style></head>");
		html.append("<body>");
		html.append("<div class=\"copyright\">");
		html.append("<h4>Copyright&copy;红上金融信息服务（上海）有限公司</h4>");
		html.append("</div>");
		html.append("</body></html>");
		return html.toString();
	}

}
