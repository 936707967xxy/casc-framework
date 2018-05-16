/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.security.RSA;
import com.hoomsun.core.anno.Permission;
import com.hoomsun.core.model.Token;
import com.hoomsun.core.model.vo.OAUser;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.server.inter.SysEmployeeOAServerI;
import com.hoomsun.core.util.LoginUtil;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月4日 <br>
 * 描述：
 */
@Controller
public class SysOAEmployeeController {
	private final static Logger log = LoggerFactory.getLogger(SysEmployeeController.class);

	@Autowired
	private SysEmployeeOAServerI employeeOAServer;
	
	// 测试账号：201504200003，密码：123456
	@RequestMapping("/sys/emp/oa/login.do")
	@ResponseBody
	public Json login(String loginName, String passWord, HttpServletRequest request) throws Exception {
		OAUser user = employeeOAServer.login(loginName, passWord);
		if (user != null) {
			String lock = user.getUserLock();
			if (StringUtils.isBlank(lock) || !"1".equals(lock)) {
				// 组装登录信息
				Token token = new Token();
				token.setUserId(user.getUserId());
				token.setUserName(user.getUserName());
				token.setUserCode(user.getUserCode());
				token.setAud("");
				Date now = new Date();
				token.setIat(now);
				// 过期时间
				// token.setExp(new Date(now.getTime() + (1000 * 60 * 30)));
				String tokenStr = JSONObject.toJSONString(token);

				// 获取session
				Json json = new Json(true, "登录成功!");
				json.setData(RSA.codePrivate(tokenStr));
				return json;
			}else {
				return new Json(false, "帐号被锁定!");
			}
//			if (!StringUtils.isBlank(lock)) {
//				return new Json(false, "帐号被锁定!");
//			} else {
//				
//			}
		} else {
			return new Json(false, "帐号密码错误!");
		}
	}

	@RequestMapping(value = "/sys/emp/oa/menu.do")
	@ResponseBody
	public Json getModule(HttpServletRequest request) {
		String tokenStr = request.getHeader("token");
		if (StringUtils.isBlank(tokenStr)) {
			return new Json(false, "权限获取失败!");
		}

		try {
			tokenStr = RSA.encodePublic(tokenStr);
			Token token = JSONObject.parseObject(tokenStr, Token.class);
			// Date now = new Date();
			// if (now.getTime() > token.getExp().getTime()) {// token 超时
			//
			// }

			String empId = token.getUserId();
			SessionRouter session = employeeOAServer.findEmpRouters(empId, null);
			request.getSession().setAttribute(SessionRouter.KEY, session);
			Json json = new Json(true, "权限获取成功!");
			json.setData(session);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("【###权限 token 解密失败 ###】", e);
		}
		return null;
	}

	@Permission("emp_query")
	@RequestMapping(value = "/sys/emp/oa/datagrid.do", method = { RequestMethod.POST })
	@ResponseBody
	public Pager<OAUser> findDataGrid(Integer page, Integer rows, String comId, String deptId, String empName,
			String jobId, String empWorkNum) {
		return employeeOAServer.findPageData(page, rows, empName, comId, deptId, jobId, empWorkNum);
	}

	@Permission("emp_grant")
	@RequestMapping(value = "/sys/emp/oa/roleid.do", method = { RequestMethod.POST })
	@ResponseBody
	public List<String> empRoleID(String empId, HttpServletRequest request) {
		return employeeOAServer.findEmpRole(empId);
	}

	@Permission("emp_query")
	@RequestMapping(value = "/sys/emp/oa/userinfo.do", method = { RequestMethod.POST })
	@ResponseBody
	public OAUser findUserInfoByUserCode(String userCode) {
		return employeeOAServer.findUserInfoByUserCode(userCode);
	}
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 获取当前登录人所在的部门的所有员工
	 * @param request
	 * @return
	 */
	@Permission("emp_query")
	@RequestMapping(value = "/sys/emp/oa/deptemp.do")
	@ResponseBody
	public List<OAUser> findDeptEmp(HttpServletRequest request){
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String empId = sessionRouter.getEmpId();
		return employeeOAServer.findDeptEmp(empId);
	}
}
