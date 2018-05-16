/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.hoomsun.core.model.Session;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月10日 <br>
 * 描述：权限控制自定义标签
 */
public class PermissionTag extends SimpleTagSupport{
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		// 从session作用域取出sessionInfo对象
		Session info = (Session) getJspContext().getAttribute(Session.KEY, PageContext.SESSION_SCOPE);
		if (info != null) {
			if (info.getActions().contains(value)) { // 拥有指定权限
				getJspBody().invoke(null); // 执行标签体
			}
		}
	}
}
