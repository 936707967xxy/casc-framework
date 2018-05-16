/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hoomsun.core.model.vo.SessionRouter;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月11日 <br>
 * 描述：登录工具
 */
public class LoginUtil {
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月11日 <br>
	 * 描述： 得到用户的登录信息
	 * @param request
	 * @return
	 */
	public static SessionRouter getLoginSession(HttpServletRequest request){
		HttpSession session = request.getSession();
		SessionRouter se = (SessionRouter) session.getAttribute(SessionRouter.KEY);
		if (se != null) {
			return (SessionRouter) session.getAttribute(SessionRouter.KEY);
		}
		return null;
	}
}
