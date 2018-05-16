/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Menu;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.model.TreeNode;
import com.hoomsun.common.util.HttpClientUtil;
import com.hoomsun.common.util.MD5;
import com.hoomsun.core.dao.SysEmpRoleMapper;
import com.hoomsun.core.dao.SysEmployeeMapper;
import com.hoomsun.core.exception.CoreException;
import com.hoomsun.core.model.Session;
import com.hoomsun.core.model.SysComponents;
import com.hoomsun.core.model.SysEmpRole;
import com.hoomsun.core.model.SysEmployee;
import com.hoomsun.core.model.SysResources;
import com.hoomsun.core.model.SysRole;
import com.hoomsun.core.model.vo.OAStore;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.model.vo.VueRouterVo;
import com.hoomsun.core.server.inter.SysEmployeeServerI;
import com.hoomsun.core.util.DateUtil;
import com.hoomsun.core.util.Md5JiaMiUtil;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月10日 <br>
 * 描述：员工管理的业务实现
 */
@Service("empServer")
public class SysEmployeeSrverImpl implements SysEmployeeServerI {
	private final static Logger log = LoggerFactory.getLogger(SysEmployeeSrverImpl.class);
	
	@Value("${OA_SIGN}")
	private String oaSign;
	
	@Value("${OA_UPD_PWD}")
	private String oaAddr;
	
	
	
	private SysEmployeeMapper employeeMapper;
	private SysEmpRoleMapper empRoleMapper;

	@Value("${SYSTEM_NAME}")
	private String systemName;

	@Value("${HSOADB_NAME}")
	private String hsoaDB;

	@Autowired
	public void setEmployeeMapper(SysEmployeeMapper employeeMapper) {
		this.employeeMapper = employeeMapper;
	}

	@Autowired
	public void setEmpRoleMapper(SysEmpRoleMapper empRoleMapper) {
		this.empRoleMapper = empRoleMapper;
	}

	@Override
	public Json createEmp(SysEmployee employee) {
		if (StringUtils.isBlank(employee.getEmpId())) {
			employee.setEmpId(PrimaryKeyUtil.getPrimaryKey());
		}
		employee.setEmpStatus(1);
		if (StringUtils.isBlank(employee.getComId()) || "-1".equals(employee.getComId())) {
			employee.setComId(null);
		}
		if (StringUtils.isBlank(employee.getDeptId()) || "-1".equals(employee.getDeptId())) {
			employee.setDeptId(null);
		}
		if (StringUtils.isBlank(employee.getJobId()) || "-1".equals(employee.getJobId())) {
			employee.setJobId(null);
		}
		employee.setEmpPwd(Md5JiaMiUtil.md5JiaMi(employee.getEmpWorkNum() + "Hs@"));
		employee.setAddDate(DateUtil.getCurrentTime());
		employee.setEmpStatus(3);
		int result = employeeMapper.insertSelective(employee);
		if (result > 0) {
			return new Json(true, "员工添加成功!");
		} else {
			return new Json(false, "员工添加失败!");
		}
	}

	@Override
	public Json updateEmp(SysEmployee employee) {
		employee.setModifyDate(DateUtil.getCurrentTime());
		int result = employeeMapper.updateByPrimaryKeySelective(employee);
		if (result > 0) {
			return new Json(true, "员工修改成功!");
		} else {
			return new Json(false, "员工修改失败!");
		}
	}

	@Override
	public Json deleteEmpReal(String empId) {
		int result = empRoleMapper.deleteByEmpId(empId);
		result += employeeMapper.deleteByPrimaryKey(empId);
		if (result > 0) {
			return new Json(true, "员工删除成功!");
		} else {
			return new Json(false, "员工删除失败!");
		}
	}
	
	@Override
	public Json deleteEmpByAddFlag(String empId) {
		int result = empRoleMapper.deleteByEmpId(empId);
		result += employeeMapper.updateDelFlagByPrimaryKey(empId);
		if (result > 0) {
			return new Json(true, "员工删除成功!");
		} else {
			return new Json(false, "员工删除失败!");
		}
	}

	@Override
	public SysEmployee findById(String empId) {

		return employeeMapper.selectByPrimaryKeyAddScope(empId);
	}

	@Override
	public Pager<SysEmployee> findPageData(Integer page, Integer rows, String empName, String comId, String deptId, String jobId, String empWorkNum) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (null != page && rows != null) {
			rows = rows > 200 ? 200 : rows;
		} else {
			page = 1; // 关于这里是否需要给出默认值，与前端框架有关系
			rows = 10;
		}
		param.put("pageIndex", page);
		param.put("pageSize", rows);

		if (!StringUtils.isBlank(empName)) {
			param.put("empName", "%" + empName + "%");
		}
		if (!StringUtils.isBlank(comId)) {
			param.put("comId", comId);
		}
		if (!StringUtils.isBlank(deptId)) {
			param.put("deptId", deptId);
		}
		if (!StringUtils.isBlank(jobId)) {
			param.put("jobId", jobId);
		}
		if (!StringUtils.isBlank(empWorkNum)) {
			param.put("empWorkNum", empWorkNum);
		}

		List<SysEmployee> employees = employeeMapper.findPageData(param);
		int total = employeeMapper.findPageCount(param);
		return new Pager<SysEmployee>(employees, total);
	}

	// 授权
	@Override
	public Json grant(String empId, String[] roleIds) {
		
		// 获取要授权人员的原有ID
		List<SysEmpRole> empRoles = empRoleMapper.findByEmpId(empId);
		List<String> oldRoleIds = new ArrayList<String>();
		List<String> newRoleIds = Arrays.asList(roleIds);

		List<String> removeResIds = new ArrayList<String>();
		// 原来有现在没有 删除
		for (SysEmpRole empRole : empRoles) {
			String roleId = empRole.getRoleId();
			oldRoleIds.add(roleId);
			if (newRoleIds.contains(roleId)) {// 现在的包含原来的 不做处理
				continue;
			} else {
				removeResIds.add(roleId);
			}
		}

		// 现在有原来没有添加
		List<SysEmpRole> addEmpRole = new ArrayList<SysEmpRole>();
		for (String roleId : newRoleIds) {
			if (oldRoleIds.contains(roleId) || "-1".equals(roleId)) {// 原来包含现在的
				continue;
			} else {
				SysEmpRole empRole = new SysEmpRole();
				empRole.setEmpId(empId);
				empRole.setRoleId(roleId);
				addEmpRole.add(empRole);
			}
		}
		int result = 0;
		if (null != removeResIds && removeResIds.size() > 0) {
			result += empRoleMapper.batchDeleteByRoleId(removeResIds);
		}
		if (null != addEmpRole && addEmpRole.size() > 0) {
			result += empRoleMapper.batchIntser(addEmpRole);
		}
		if (result > 0) {
			return new Json(true, "授权成功!");
		} else {
			return new Json(false, "授权失败!");
		}
	}

	@Override
	public Json login(String loginName, String pwd, HttpServletRequest request) {
		try {
			pwd = Md5JiaMiUtil.md5JiaMi(pwd);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("loginName", loginName);
			params.put("pwd", pwd);
			Session session = employeeMapper.login(params);

			if (null == session) {
				return new Json(false, "账号密码错误!");
			}

			Integer empStatus = session.getEmpStatus();
			if (null != empStatus) {
				if (1 != empStatus) {
					return new Json(false, "你的账户已经被锁定,不能登录!");
				}
			}

			if (null != session.getDeptStatus()) {
				if (1 != session.getDeptStatus()) {
					return new Json(false, "你所在的部门已被锁定,不能登录!");
				}
			}

			if (null != session.getComStatus()) {
				if (1 != session.getComStatus()) {
					return new Json(false, "你所在的公司已被锁定,不能登录!");
				}
			}

			createSession(session, request);
			return new Json(true, "登录成功!");
		} catch (Exception e) {
			log.error("####系统用户登录异常{}", e);
			return new Json(false, "登录异常!");
		}
	}

	private void createSession(Session session, HttpServletRequest request) {
		String empId = session.getEmpId();
		List<SysRole> sysRoles = employeeMapper.findEmpResources(empId);
		List<SysResources> resources = new ArrayList<SysResources>();
		for (SysRole role : sysRoles) {
			SysRole newRole = new SysRole();
			newRole.setRoleId(role.getRoleId());
			newRole.setRoleName(role.getRoleName());
			session.addRole(newRole);
			resources.addAll(role.getResources());
		}

		HashMap<String, Menu> menus = new HashMap<String, Menu>(); // 采用HasMap避免重复
		for (SysResources res : resources) {
			if (res == null) {
				continue;
			}

			if (res.getResType().equalsIgnoreCase(SysResources.TYPE_ACTION)) {// 如果是action
				session.addAction(res.getResValue());
			} else {
				if (res.getResLevel() == 1) { // 一级菜单
					Menu menu = new Menu(res.getResId(), res.getResName(), res.getResUrl(), res.getResIcon(), res.getResSort());
					session.addMenu(menu);
					menus.put(menu.getId(), menu);
				}
			}
		}

		for (SysResources res : resources) {
			if (res == null) {
				continue;
			}

			if (res.getResType().equalsIgnoreCase(SysResources.TYPE_MENU)) {// 如果是action
				if (res.getResLevel() == 2 && res.getResParent() != null) { // 二级菜单
					Menu menu = new Menu(res.getResId(), res.getResName(), res.getResUrl(), res.getResIcon(), res.getResSort());
					// 挂靠到上级菜单 也即是一级菜单
					Menu parentMenu = menus.get(res.getResParent()); // 得到上级菜单
					if (parentMenu != null) {
						parentMenu.addChildren(menu); // 挂靠到一级菜单
					}
				}
			}
		}

		request.getSession().setAttribute(Session.KEY, session);
	}

	@Override
	public List<String> findEmpRole(String empId) {
		if (StringUtils.isBlank(empId)) {
			return null;
		}
		return employeeMapper.findEmpRoleId(empId);
	}

	@Override
	public Json updateStatus(Integer empStatus, String empId) {
		if (null == empStatus || StringUtils.isBlank(empId)) {
			return new Json(false, "参数有误！不能为空!");
		}
		int result = employeeMapper.updateStatus(empStatus, empId);
		if (result > 0) {
			return new Json(true, "状态修改成功!");
		} else {
			return new Json(false, "状态修改失败!");
		}
	}

	
	@Override
	public Json updatePassWord(String empId, String newpwd, String oldpwd) {
		String cardNumber = "";
		if (!StringUtils.isBlank(empId)) {
			SysEmployee employee = employeeMapper.selectByPrimaryKey(empId);
			cardNumber = employee.getEmpCert();
		}
		updatePwdToOa(cardNumber, newpwd, oldpwd); // 更改oa密码
		return updatePwdByID(cardNumber, newpwd, oldpwd);
	}
	
	
	@Override
	public Json updatePwdByID(String cardNumber, String newpwd, String oldpwd){
		if (newpwd.equals(oldpwd)){
			return new Json(true, "修改前后密码一致!");
		}
		newpwd = MD5.encodeByMd5AndSalt(newpwd);
		oldpwd = MD5.encodeByMd5AndSalt(oldpwd); 
		int result =  employeeMapper.updatePwdByID(cardNumber, newpwd, oldpwd);
		if (result > 0) {
			return new Json(true, "修改密码成功!");
		} else {
			return new Json(false, "修改密码失败!");
		}
		
	}
	
	
	// 8261	201605100032 庆绍峰  离职	2016-05-10	身份证	320122199203222412	男	1992-03-22	1992-03-22	25	汉族	未婚	13236516915			972365245@qq.com	否	无	1	是	江苏省	本市农村	南京市浦口区石桥镇王村林庄组3号	210000	南京市浦口区大桥北路旭日上城三区21栋1605室	210000	无	一般群众	是	劳动合同	2016-11-09	2016-11-10	2019-05-09	2019-04-09	1	正常	内部渠道		王荣	其他	15366096075	441	90801170	1	2017-01-04 14:32:16			2017-01-04 14:32:16			41	40F99E3F6967057C44DD6A7F89390B64						/			8	销售	259	招商银行上海分行杨思支行	6214852105909150			-	红上至信	普惠六区	南京	天时商贸中心营业部		0																				
	private boolean updatePwdToOa(String cardNumber, String newpwd, String oldpwd){
		Map<String,Object>  paramdata=new HashMap<String, Object>();
		paramdata.put("employeeNo", cardNumber);
		paramdata.put("newPass", newpwd);
		paramdata.put("oldPass", oldpwd);
		Map<String,Object>  oAparamdata=new HashMap<String, Object>();
		oAparamdata.put("param", paramdata);
		oAparamdata.put("signa", oaSign); 
		String str = HttpClientUtil.doPostJson(oaAddr, null, null, JSONObject.toJSONString(oAparamdata));
		log.debug("oa pwd retutn=" + str);
		JSONObject parse = JSONObject.parseObject(str);
		boolean ret = false;
		try {
			ret = parse.getBooleanValue("success");
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}
	

	@Override
	public List<TreeNode> findRoleTreeNode(String empId) {
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		nodes.add(new TreeNode("-1", "我的权限", null));
		List<SysResources> resources = employeeMapper.findResourcesByEmpId(empId);
		for (SysResources res : resources) {
			String type = res.getResType();
			String name = res.getResName();
			if (type.equalsIgnoreCase(SysResources.TYPE_MENU)) {// 菜单
				name = "&nbsp;&nbsp;<span style='color:#aaa'>" + name + "[菜单]</span>";
			} else if (type.equalsIgnoreCase(SysResources.TYPE_ACTION)) {
				name = "&nbsp;&nbsp;<span style='color:#aaa'>" + name + "[操作]</span>";
			}
			TreeNode tn = new TreeNode(res.getResId(), res.getResName(), "-1");
			if (!StringUtils.isBlank(res.getResParent())) {
				tn.setParentId(res.getResParent());
			}
			nodes.add(tn);
		}
		return nodes;
	}

	@Override
	public List<SysEmployee> findByDept(String deptId) {
		if (StringUtils.isEmpty(deptId)) {
			return null;
		}
		List<SysEmployee> employees = employeeMapper.findByDept(deptId);
		return employees;
	}

	@Override
	public SessionRouter findEmpRouters(String empId, String systemName) {
		SysEmployee employee = null;
		
		if (StringUtils.isAllBlank(systemName, this.systemName)) {
			employee = employeeMapper.findEmpComponents(empId, null);
		} else {
			if (StringUtils.isBlank(systemName) && !StringUtils.isBlank(this.systemName)) {
				employee = employeeMapper.findEmpComponents(empId, this.systemName);
			} else {
				employee = employeeMapper.findEmpComponents(empId, systemName);
			}
		}

		if (employee == null) {
			return null;
		}
		OAStore store  = employeeMapper.findStoreByDeptId(employee.getDeptId());
		
		SessionRouter session = new SessionRouter();	
		session.setEmpId(employee.getEmpId());
		session.setEmpName(employee.getEmpName());
		session.setEmpWorkNum(employee.getEmpWorkNum());
		session.setComId(employee.getComId());
		session.setComName(employee.getComName());
		session.setDeptId(employee.getDeptId());
		session.setDeptName(employee.getDeptName());
		// 设置营业部数据
		if (store!=null) {
			// 设置营业部数据
			session.setStoreId(store.getStoreId());
			session.setStoreName(store.getStoreName());
			if (StringUtils.isBlank(store.getDeptNo())){
				session.setDeptNo("");
			}else {
				session.setDeptNo(store.getDeptNo());
			}
			session.setStoreCityName(store.getStoreCityName());
		}else {
			session.setStoreId("-1");
			session.setStoreName("");
			session.setDeptNo("");
			session.setStoreCityName("");
		}
		
		List<SysRole> roles = employee.getRoles();
		session.setRoles(roles);
		buildRouter(session, roles);
		log.info("###【权限】### : " + session.toString());
		return session;
	}

	private void buildRouter(SessionRouter session, List<SysRole> roles) {
		if (null == roles || roles.size() < 1) {
			return;	
		}
		// 得到所有的组件
		HashMap<String, SysComponents> map = new HashMap<String, SysComponents>();
		for (SysRole role : roles) {
			List<SysComponents> components = role.getComponents();
			if (null == components || components.size() < 1) {
				continue;
			}

			for (SysComponents cpt : components) {
				if (cpt != null && !cpt.equals(new SysComponents())) {
					map.put(cpt.getCptId(), cpt);
				}
			}
		}

		for (Map.Entry<String, SysComponents> en : map.entrySet()) {
			SysComponents cpt = en.getValue();
			if (cpt != null && !cpt.equals(new SysComponents())) {
				String compon = cpt.getComponent();
				String value = cpt.getCptValue();
				String type = cpt.getCptType();
				
				if ("menu".equals(type)) {
					session.addMenuRouter(value);
				} else if ("action".equals(type)) {
					if (!StringUtils.isBlank(compon)) {
						session.addMenuRouter(compon);
					}
					session.addAction(value);
				}
			}
		}
	}

	// 加载路由数据
	public void buildRouter(SessionRouter session, List<VueRouterVo> routerVos, List<SysRole> roles) {
		if (null == roles || roles.size() < 1) {
			return;
		}

		// 得到所有的组件
		HashMap<String, SysComponents> map = new HashMap<String, SysComponents>();
		for (SysRole role : roles) {
			List<SysComponents> components = role.getComponents();
			if (null == components || components.size() < 1) {
				continue;
			}

			for (SysComponents cpt : components) {
				if (cpt != null && !cpt.equals(new SysComponents())) {
					map.put(cpt.getCptId(), cpt);
				}
			}
		}

		// 一级菜单
		List<VueRouterVo> menu = new ArrayList<VueRouterVo>();
		// 组装路由数据 递归树
		List<VueRouterVo> childrenMenu = new ArrayList<VueRouterVo>();
		for (Map.Entry<String, SysComponents> en : map.entrySet()) {
			SysComponents cpt = en.getValue();
			if (cpt != null && !cpt.equals(new SysComponents())) {
				VueRouterVo router = new VueRouterVo();
				boolean hid = cpt.getHidden() == null || cpt.getHidden() == 0;
				router.setKey(cpt.getCptId());
				router.setName(cpt.getName());
				router.setPath(cpt.getPath());
				router.setComponent(cpt.getComponent());
				router.setHidden(hid);
				router.setIconCls(cpt.getIconcls());

				// 不隐藏并且上级为空 一级菜单
				if (hid && StringUtils.isBlank(cpt.getParentId())) {
					menu.add(router);
				}

				// 是隐藏的和没有上级的 为操作
				if (!hid && StringUtils.isBlank(cpt.getParentId())) {
					routerVos.add(router);
				}

				// 上级不为空的为子菜单
				if (!StringUtils.isBlank(cpt.getParentId())) {// 不隐藏 为菜单
					router.setParent(cpt.getParentId());
					childrenMenu.add(router);
				}
			}
		}
		// 递归菜单数据
		buildChildren(menu, childrenMenu);
		log.info("###【权限 组件资源】### : " + routerVos);
		routerVos.addAll(menu);
	}

	private void buildChildren(List<VueRouterVo> menu, List<VueRouterVo> childrenMenu) {
		// 递归结束条件
		if (childrenMenu == null || childrenMenu.size() < 1) {
			return;
		}

		List<VueRouterVo> par = new ArrayList<VueRouterVo>();
		List<VueRouterVo> child = new ArrayList<VueRouterVo>();

		Iterator<VueRouterVo> iterator = menu.iterator();
		while (iterator.hasNext()) {
			VueRouterVo mt = iterator.next();
			String key = mt.getKey();
			for (VueRouterVo rt : childrenMenu) {
				String parent = rt.getParent();
				if (key.equals(parent)) {
					par.add(rt);
					mt.addChildren(rt);
				} else {
					child.add(rt);
				}
			}
		}

		buildChildren(par, child);
	}

	// 登录需要登录成功的数据 用户的所有的资源
	@Override
	public SysEmployee login(String loginName, String passWord) {

		if (StringUtils.isBlank(loginName)) {
			throw new CoreException("登录帐号不能为空！");
		}
		
		if (StringUtils.isBlank(passWord)) {
			throw new CoreException("登录密码不能为空");
		}
		
//		if (loginName.equals("88888888")) {
//			if ("admin2017".equals(passWord)) {
//				SysEmployee emp = new SysEmployee();
//				emp.setEmpId("014D3DE894E14871BFAF2495A6BFDA75");
////				emp.setEmpId("888888");
//				emp.setEmpWorkNum("88888888");
//				emp.setEmpName("admin");
//				return emp;
//			}
//		}
		passWord = MD5.encodeByMd5AndSalt(passWord); // HSOA pwd Md5加密
		SysEmployee emp = employeeMapper.simplLogin(loginName, passWord);
		return emp;
	}
	
	
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月18日 <br>
	 * 描述： 获取部门下的员工数据 账号状态正常的员工 不为离职状态的
	 * 
	 * @param deptId
	 * @param oadb
	 * @return
	 */
	@Override
	public Map<String, Object> findAppUserInfo(String empWorkNum){
		return employeeMapper.findAppUserInfo(empWorkNum);
	}
	
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月18日 <br>
	 * 描述： 获取门店信息
	 * 
	 * @param parent_id
	 * @param oadb
	 * @return
	 */
	@Override
	public Map<String, Object> findAppUserStorInfo(String deptId){
		return employeeMapper.findAppUserStorInfo(deptId);
	}
	
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月18日 <br>
	 * 描述： 获取所有门店信息
	 * 
	 * @param oadb
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findAllStoreData(){
		return employeeMapper.findAllStoreData();
	}
	
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月18日 <br>
	 * 描述： 获取所有门店信息签约时
	 * 
	 * @param oadb
	 * @return
	 */
	@Override
	public Map<String, Object> findStorebyId(String deptId){
		return employeeMapper.findStorebyId(deptId);
	}

	@Override
	public SysEmployee findUserInfoByUserCode(String empWorkNum) {
		return employeeMapper.findUserInfoByUserCode(empWorkNum);
	}

	@Override
	public List<SysEmployee> findDeptEmp(String deptId) {
		return employeeMapper.findEmpByDeptDescendants(deptId);
	}

	

	@Override
	public Json resetPwdByID(String employeeNo, String newpwd) {
		newpwd = MD5.encodeByMd5AndSalt(newpwd);
		int result =  employeeMapper.resetPwdByID(employeeNo, newpwd);
		if (result > 0) {
			return new Json(true, "重置密码成功!");
		} else {
			return new Json(false, "重置密码失败!");
		}
	}
	
	@Override
	public Json updateEmpStatus(String employeeNo, String status) {
		int result =  employeeMapper.updateEmpStatus(employeeNo, status);
		if (result > 0) {
			return new Json(true, "修改用户状态成功!");
		} else {
			return new Json(false, "修改用户状态失败!");
		}
	}
	
	// Hs000000 123456
	public static void main(String[] args) {
		new SysEmployeeSrverImpl().updatePwdToOa("610423198901224412", "123456", "Hs000000");
	}

	
}
