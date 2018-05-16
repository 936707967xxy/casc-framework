/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.model.TreeNode;
import com.hoomsun.common.security.RSA;
import com.hoomsun.common.util.SystemUtils;
import com.hoomsun.core.model.SysEmployee;
import com.hoomsun.core.model.SystemLoginLog;
import com.hoomsun.core.model.Token;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.server.inter.SysEmployeeServerI;
import com.hoomsun.core.util.LoginUtil;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月11日 <br>
 * 描述：员工管理的控制层
 */
@Controller
public class SysEmployeeController {
	private final static Logger log = LoggerFactory.getLogger(SysEmployeeController.class);
	private SysEmployeeServerI empServer;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	public void setEmpServer(SysEmployeeServerI empServer) {
		this.empServer = empServer;
	}

	// 登录页面
	@RequestMapping("/sys/index.do")
	public String loginView(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (null == session) {
			return "login";
		} else {
			request.setAttribute("LOGIN_ERROR_MESSAGE", "登录超时,请重新登录!");
			return "forward:/sys/main.do";
		}
	}

	// 转发到系统主页面
	@RequestMapping("/sys/main.do")
	public String mainView(HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (null == session) {
			return "login";
		} else {
			return "main";
		}
	}

	// 登出
	@RequestMapping("/sys/emp/loginout.do")
	@ResponseBody
	public Json loginOut(HttpServletRequest request) {
		request.getSession().removeAttribute(SessionRouter.KEY);
		request.getSession().invalidate();
		return new Json(true, "退出成功!");
	}

	// 员工管理列表
	//@Permission("emp_query")
	@RequestMapping(value = "/sys/emp/listview.do", method = { RequestMethod.GET })
	public String empView() {
		return "emp/listview";
	}

	//@Permission("emp_query")
	@RequestMapping(value = "/sys/emp/datagrid.do", method = { RequestMethod.POST })
	@ResponseBody
	public Pager<SysEmployee> findDataGrid(Integer page, Integer rows, String comId, String deptId, String empName, String jobId, String empWorkNum) {
		return empServer.findPageData(page, rows, empName, comId, deptId, jobId, empWorkNum);
	}

	//@Permission("emp_add")
	@RequestMapping(value = "/sys/emp/createview.do", method = { RequestMethod.GET })
	public String createView() {
		return "emp/addview";
	}

//	//@LoggerAnnotation(moduleName = "员工管理", option = "添加员工", optionType = OptionType.CREATE)
//	//@Permission("emp_add")
	@RequestMapping(value = "/sys/emp/create.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json create(SysEmployee employee, HttpServletRequest request) {
		String empId = LoginUtil.getLoginSession(request).getEmpId();
		employee.setAddEmp(empId);
		return empServer.createEmp(employee);
	}

//	//@Permission("emp_edit")
//	@RequestMapping(value = "/sys/emp/updateview.do", method = { RequestMethod.GET })
//	public String updateView(String empId, HttpServletRequest request) {
//		SysEmployee employee = empServer.findById(empId);
//		request.setAttribute("SYS_EMPLOYEE_KEY", employee);
//		return "emp/editview";
//	}

//	//@LoggerAnnotation(moduleName = "员工管理", option = "修改员工", selectMethod = "findById", idName = "empId", idIndex = 0, beanId = "empServer", optionType = OptionType.UPDATE)
//	//@Permission("emp_edit")
	@RequestMapping(value = "/sys/emp/update.do")
	@ResponseBody
	public Json update(@RequestBody SysEmployee employee, HttpServletRequest request) {
		String empId = LoginUtil.getLoginSession(request).getEmpId();
		employee.setModifyEmp(empId);
		return empServer.updateEmp(employee);
	}

//	//@LoggerAnnotation(moduleName = "员工管理", option = "删除员工", selectMethod = "findById", idName = "empId", idIndex = 0, beanId = "empServer", optionType = OptionType.DELETE)
//	//@Permission("emp_delete")
	@RequestMapping(value = "/sys/emp/remove.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json update(String empId, HttpServletRequest request) {
		return empServer.deleteEmpByAddFlag(empId);
	}

	//@Permission("emp_grant")
	@RequestMapping(value = "/sys/emp/roleid.do", method = { RequestMethod.POST  })
	@ResponseBody
	public  List<String> empRoleID(String empId, HttpServletRequest request) {
		return empServer.findEmpRole(empId);
	}

	//@LoggerAnnotation(moduleName = "员工管理", option = "员工授权", optionType = OptionType.GRANT)
	//@Permission("emp_edit")
	@RequestMapping(value = "/sys/emp/grant.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json update(String empId, String roleIds) {
		String[] idArr = new String[]{};
		if (!StringUtils.isBlank(roleIds)) {
			idArr = roleIds.split(","); // 空数组 split默认会给数组增加一个值
		}
		return empServer.grant(empId, idArr);
	}

			
	@RequestMapping(value = "/sys/emp/query.do")
	@ResponseBody
	public  SysEmployee findById(String empId) {
		return empServer.findById(empId);
	}
			
	
	//@Permission("emp_edit")
	@RequestMapping(value = "/sys/emp/lock.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json lock(String empId) {
		return empServer.updateStatus(0, empId);
	}

	//@Permission("emp_edit")
	@RequestMapping(value = "/sys/emp/unlock.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json unlock(String empId) {
		return empServer.updateStatus(1, empId);
	}

//	@RequestMapping(value = "/sys/emp/initpwd.do", method = { RequestMethod.POST })
//	@ResponseBody
//	public Json initPwd(String empId) {
//		return empServer.updatePassWord(null, empId);
//	}
//
//	@RequestMapping(value = "/sys/emp/updatepwdview.do", method = { RequestMethod.GET })
//	public String updatePwdView() {
//		return "emp/updatepwd";
//	}

	@RequestMapping(value = "/web/emp/updatepwd.do")
	@ResponseBody
	public Json updatePwd(HttpServletRequest request, String newpwd, String oldpwd) {
		if (StringUtils.isBlank(newpwd) || StringUtils.isBlank(oldpwd)) {
			return new Json(false, "密码不能为空!");
		}
		SessionRouter session = LoginUtil.getLoginSession(request);
		if( session==null){
			return new Json(false, "登录异常，请刷新页面！");
		}
		String empId = session.getEmpId();
		return empServer.updatePassWord(empId, newpwd, oldpwd);
	}

	@RequestMapping(value = "/sys/emp/roletree.do", method = { RequestMethod.POST })
	@ResponseBody
	public List<TreeNode> findRoleTreeNode(HttpServletRequest request) {
		String empId = LoginUtil.getLoginSession(request).getEmpId();
		return empServer.findRoleTreeNode(empId);
	}

	
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年11月26日 <br>
	 * 描述： 审批组选择部门后该部门下所有员工  的方法
	 * 
	 * @param loginName
	 * @param passWord
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sys/emp/bydept.do", method = { RequestMethod.GET })
	@ResponseBody
	public List<Map<String, Object>> findByDept(HttpServletRequest request, String deptId) {
		if (StringUtils.isBlank(deptId)) {
			deptId = LoginUtil.getLoginSession(request).getDeptId();
		}
		List<SysEmployee> list=empServer.findByDept(deptId);
		List<Map<String, Object>> returnlist=new ArrayList<Map<String,Object>>();
		for(SysEmployee emp:list){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", emp.getEmpId());
			map.put("firstName", emp.getEmpName());
			returnlist.add(map);
		}
		return returnlist;
	}


	/*********************************************/
	/**************** 组 件 测 试 ****************/
	/*********************************************/
	@RequestMapping("/sys/emp/login.do")
	@ResponseBody
	public Json login(String loginName, String passWord, HttpServletRequest request) throws Exception {
		SysEmployee user = empServer.login(loginName, passWord);
		if (user != null) {
			Integer status = user.getEmpStatus();
			if (status == null || 3 == status) {
				// 组装登录信息
				Token token = new Token();
				token.setUserId(user.getEmpId());
				token.setUserName(user.getEmpName());
				token.setUserCode(user.getEmpWorkNum());
				token.setAud("");
				Date now = new Date();
				token.setIat(now);
				// 过期时间
				// token.setExp(new Date(now.getTime() + (1000 * 60 * 30)));
				String tokenStr = JSONObject.toJSONString(token);
				
				// 用户登录日志
				SystemLoginLog log = new SystemLoginLog();
				log.setEmpName(loginName);  // 登录名
				log.setLoginName(user.getEmpName()); // 登录人
				log.setLoginDate(new Date()); // 登录时间
				log.setLoginIP(SystemUtils.getIpAddr(request)); // 客户端IP
				log.setLoginClient(SystemUtils.getClientInfo(request)); // 获取系统和浏览器名称
				mongoTemplate.insert(log);
				
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

	@RequestMapping(value = "/sys/emp/menu.do")
	@ResponseBody
	public Json getModule(HttpServletRequest request) {
		String tokenStr = request.getHeader("token");
		if (StringUtils.isBlank(tokenStr)) {
			return new Json(false, "权限获取失败!");
		}

		try {
			tokenStr = RSA.encodePublic(tokenStr);
			Token token = JSONObject.parseObject(tokenStr, Token.class);
			String empId = token.getUserId();
			SessionRouter session = empServer.findEmpRouters(empId, null);
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

	
	@RequestMapping(value = "/sys/emp/userinfo.do", method = { RequestMethod.POST })
	@ResponseBody
	public SysEmployee findUserInfoByUserCode(String empWorkNum) {
		return empServer.findUserInfoByUserCode(empWorkNum);
	}
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 获取当前登录人所在的部门的所有员工
	 * @param request
	 * @param deptOtherEmp 代表查询部门下除过登录人（用于质检复核转办）
	 * @return
	 */
	@RequestMapping(value = "/sys/emp/deptemp.do")
	@ResponseBody
	public List<SysEmployee> findDeptEmp(HttpServletRequest request, boolean deptOtherEmp){
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String deptId = sessionRouter.getDeptId();
		return empServer.findDeptEmp(deptId);
	}
	
	// 传递的新旧密码都是加密后的
	@RequestMapping(value = "/web/emp/updatepwdbyoa.do")
	@ResponseBody
	public Json updatePwdByOa(String employeeNo, String newPass, String oldPass){
		if(StringUtils.isBlank(employeeNo) || StringUtils.isBlank(newPass) || StringUtils.isBlank(oldPass)){
			return new Json(false, "参数错误");
		}
		return empServer.updatePwdByID(employeeNo, newPass, oldPass);
	}
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年2月24日 <br>
	 * 描述：  
	 * @param employeeNo 身份证号码
	 * @param newPass
	 * @return
	 */
	// 传递的新旧密码都是加密后的
	@RequestMapping(value = "/web/emp/resetpwdbyoa.do")
	@ResponseBody
	public Json resetPwdByOa(String employeeNo, String newPass){
		if(StringUtils.isBlank(employeeNo) || StringUtils.isBlank(newPass)){
			return new Json(false, "参数错误");
		}
		return empServer.resetPwdByID(employeeNo, newPass);
	}
	
	
	// Oa修改离职状态
	@RequestMapping(value = "/web/emp/updatestatusbyoa.do")
	@ResponseBody
	public Json updatePwdByOa(String employeeNo, String status){
		if(StringUtils.isBlank(employeeNo) || StringUtils.isBlank(status)){
			return new Json(false, "参数错误");
		}else {
			try{
				Integer.parseInt(status);
			}catch(NumberFormatException e){
				return new Json(false, "参数错误");
			}
		}
		return empServer.updateEmpStatus(employeeNo, status);
	}
}
