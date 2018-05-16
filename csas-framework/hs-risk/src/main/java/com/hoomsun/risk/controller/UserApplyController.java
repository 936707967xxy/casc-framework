/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Result;
import com.hoomsun.risk.model.vo.UserApplyVo;
import com.hoomsun.risk.server.inter.UserApplyServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月30日 <br>
 * 描述：数据中心数据接口 给数据中心提供客户基础数据 用作评分
 */
@Controller
public class UserApplyController {

	@Autowired
	private UserApplyServerI userApplyServer;

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月30日 <br>
	 * 描述： 获取用户的基本申请数据 根据证件号
	 * 
	 * @param request
	 * @param idNumber
	 * @return
	 */
	@RequestMapping(value = "/api/risk/apply/{idNumber}/byidnumber.do", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Result findUserApply(HttpServletRequest request, @PathVariable("idNumber") String idNumber) {
		Result result = new Result();
		if (StringUtils.isBlank(idNumber)) {
			result.setCode(1001);
			result.setMsg("证件号不能为空!");
		} else {
			UserApplyVo apply = userApplyServer.findByIdNumber(idNumber);
			result.setCode(0000);
			result.setMsg("获取成功!");
			result.setData(apply);
		}
		return result;
	}

}
