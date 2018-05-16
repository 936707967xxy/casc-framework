/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.core.enums.AnnexType;
import com.hoomsun.csas.sales.api.model.Annex;
import com.hoomsun.csas.sales.api.server.inter.AnnexServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月5日 <br>
 * 描述：
 */
@Controller
public class AnnexController {
	private AnnexServerI annexServer;

	@Autowired
	public void setAnnexServer(AnnexServerI annexServer) {
		this.annexServer = annexServer;
	}

	// 获取某类型的图片数据
	@RequestMapping("/sys/annex/query.do")
	@ResponseBody
	public List<Annex> findAnnex(@RequestBody Map<String, String> param, HttpServletRequest request) {
		String applyId = param.get("applyId");
		Integer imgType = Integer.parseInt(param.get("imgType"));
		return annexServer.findByApplyIdImgType(applyId, imgType);
	}

	// 获取某图片类型
	@RequestMapping("/sys/annex/imgtype.do")
	@ResponseBody
	public List<Map<String, Object>> findAnnex(HttpServletRequest request) {
		return AnnexType.allAnnexType();
	}
}
