/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.core.enums.AnnexType;
import com.hoomsun.core.model.vo.AnnexTypeVo;

/**
 * 作者：liushuai <br>
 * 创建时间：2018年1月8日 <br>
 * 描述：获取影像类型
 */
@Controller
public class AnnexTypeController {

	@RequestMapping("/sys/AnnexType/alltype.do")
	@ResponseBody
	public List<AnnexTypeVo> getAllAnnexType(){
		return AnnexType.allAnnexTypeList();
	}
	
}
