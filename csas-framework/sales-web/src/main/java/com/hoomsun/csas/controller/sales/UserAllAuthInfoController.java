package com.hoomsun.csas.controller.sales;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.csas.sales.api.server.inter.UserAllAuthInfoServerI;

/**
 * 描述：认证清单控制层
 * @author ygzhao
 * @date 2018-01-08
 */
@Controller
public class UserAllAuthInfoController {
	
	@Autowired
	private UserAllAuthInfoServerI userAllAuthInfoServer;
	
	/**
	 * 根据applyId查询认证信息
	 * @return
	 */
	@RequestMapping(value = "/sys/userallauthinfo/selectallauthbyapplyid.do")
	@ResponseBody
	public List<Map<String,Object>> selectAllAuthByApplyId(HttpServletRequest request,String applyId) {
		if(StringUtils.isBlank(applyId)){
			return null;
		}
		return userAllAuthInfoServer.selectByApplyId(applyId);
	}
}
