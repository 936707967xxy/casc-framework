/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.sales;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;
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



	//获取某类型的图片数据
	@RequestMapping("/sys/annex/query.do")
	@ResponseBody
	public List<Annex> findAnnex(@RequestBody Map<String,String> param, HttpServletRequest request) {
		String applyId = param.get("applyId");
		Integer imgType = Integer.parseInt(param.get("imgType"));
		return annexServer.findByApplyIdImgType(applyId, imgType);
	}
	
	@RequestMapping("/sys/annex/imgtype.do")
	@ResponseBody
	public List<Map<String, Object>> findAnnex(HttpServletRequest request) {
		AnnexType[] excludes = new AnnexType[] {AnnexType.ANNEX_AUDIT};
		return AnnexType.allAnnexType(excludes);
	}
	
	//上传文件回显
	@RequestMapping("/sys/annex/queryreview.do")
	@ResponseBody
	public List<Map<String,Object>> queryReView(@RequestBody Map<String,String> param, HttpServletRequest request) {
		if(param==null||StringUtils.isBlank(param.get("applyId"))||StringUtils.isBlank(param.get("imgType"))){
			return null;
		}
		String applyId = param.get("applyId");
		Integer imgType = Integer.parseInt(param.get("imgType"));
		return annexServer.queryReView(applyId, imgType);
	}
	
	//删除图片
	@RequestMapping("/sys/annex/deletebyanxid.do")
	@ResponseBody
	public Json deleteByanxId(@RequestBody Map<String,String> param, HttpServletRequest request) {
		if(param==null||StringUtils.isBlank(param.get("anxId"))){
			return new Json(false,"参数异常");
		}
		String anxId = param.get("anxId");
		return annexServer.deleteByanxId(anxId);
	}
}
