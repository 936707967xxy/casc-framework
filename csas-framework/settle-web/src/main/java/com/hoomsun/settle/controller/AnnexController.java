/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.settle.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.core.enums.AnnexType;
import com.hoomsun.core.server.inter.SysContractServerI;
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
	private SysContractServerI contractServer;

	@Autowired
	public void setAnnexServer(AnnexServerI annexServer) {
		this.annexServer = annexServer;
	}

	@Autowired
	public void setContractServer(SysContractServerI contractServer) {
		this.contractServer = contractServer;
	}

	// 获取某类型的图片数据
	@RequestMapping("/sys/annex/query.do")
	@ResponseBody
	public List<Annex> findAnnex(@RequestBody Map<String, String> param, HttpServletRequest request) {
		String conId = param.get("conId");
		Integer imgType = Integer.parseInt(param.get("imgType"));
		String applyId = contractServer.findApplyIdByConId(conId);
		return annexServer.findByApplyIdImgType(applyId, imgType);
	}

	@RequestMapping("/sys/annex/imgtype.do")
	@ResponseBody
	public List<Map<String, Object>> findAnnex(HttpServletRequest request) {
		return AnnexType.allAnnexType();
	}
}
