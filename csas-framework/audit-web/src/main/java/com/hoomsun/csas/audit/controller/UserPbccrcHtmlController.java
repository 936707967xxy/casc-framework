/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hoomsun.csas.sales.api.model.UserPbccrcHtml;
import com.hoomsun.csas.sales.api.server.inter.UserPbccrcHtmlServerI;


/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月18日 <br>
 * 描述：
 */
@Controller
public class UserPbccrcHtmlController {
	@Autowired
	private UserPbccrcHtmlServerI userPbccrcHtmlServer;

	@RequestMapping("/sys/pbccrc/html.do")
	public void showHtmlData(String applyId, HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();

			if (StringUtils.isAllBlank(applyId)) {
				writer.write("<h1>数据为空！</h1>");
				writer.flush();
				writer.close();
				return;
			}

			UserPbccrcHtml pbccrcHtml = userPbccrcHtmlServer.findByApplyId(applyId);
			if (null == pbccrcHtml) {
				writer.write("<h1>数据为空！</h1>");
				writer.flush();
				writer.close();
				return;
			}

			String html = pbccrcHtml.getHtmlData();
			if (StringUtils.isAllBlank(html)) {
				writer.write("<h1>数据为空！</h1>");
				writer.flush();
				writer.close();
				return;
			}

			writer.write(html);
			writer.flush();
			writer.close();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
