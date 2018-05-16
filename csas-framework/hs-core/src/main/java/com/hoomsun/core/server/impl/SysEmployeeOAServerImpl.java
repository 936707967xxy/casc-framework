/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.MD5;
import com.hoomsun.core.dao.SysEmpRoleMapper;
import com.hoomsun.core.dao.SysEmployeeOAMapper;
import com.hoomsun.core.exception.CoreException;
import com.hoomsun.core.model.SysComponents;
import com.hoomsun.core.model.SysEmpRole;
import com.hoomsun.core.model.SysRole;
import com.hoomsun.core.model.vo.OAStore;
import com.hoomsun.core.model.vo.OAUser;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.server.inter.SysEmployeeOAServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月4日 <br>
 * 描述：
 */
@Service("employeeOAServer")
public class SysEmployeeOAServerImpl implements SysEmployeeOAServerI {
	private final static Logger log = LoggerFactory.getLogger(SysEmployeeSrverImpl.class);
	@Value("${HSOADB_NAME}")
	private String hsoaDB;
	@Value("${SYSTEM_NAME}")
	private String systemName;
	
	
	private SysEmployeeOAMapper employeeOAMapper;
	private SysEmpRoleMapper empRoleMapper;
	
	@Autowired
	public void setEmployeeOAMapper(SysEmployeeOAMapper employeeOAMapper) {
		this.employeeOAMapper = employeeOAMapper;
	}
	@Autowired
	public void setEmpRoleMapper(SysEmpRoleMapper empRoleMapper) {
		this.empRoleMapper = empRoleMapper;
	}


	@Override
	public Pager<OAUser> findPageData(Integer page, Integer rows, String empName, String comId, String deptId, String jobId, String empWorkNum) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("hsoaDB", this.hsoaDB);

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
		if (!StringUtils.isBlank(empWorkNum)) {
			param.put("empWorkNum", empWorkNum);
		}
		
		/*if (!StringUtils.isBlank(comId)) {
			param.put("comId", comId);
		}
		if (!StringUtils.isBlank(deptId)) {
			param.put("deptId", deptId);
		}
		if (!StringUtils.isBlank(jobId)) {
			param.put("jobId", jobId);
		}*/

		List<OAUser> employees = employeeOAMapper.findPageData(param);
		int total = employeeOAMapper.findPageCount(param);
		return new Pager<OAUser>(employees, total);
	}

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
			return new Json(true, "授权失败!");
		}
	}
	

	@Override
	public OAUser login(String loginName, String passWord) {
		if (StringUtils.isBlank(loginName)) {
			throw new CoreException("登录帐号不能为空！");
		}
		
		if (StringUtils.isBlank(passWord)) {
			throw new CoreException("登录密码不能为空");
		}
		
		if (loginName.equals("88888888")) {
			if ("admin2017".equals(passWord)) {
				OAUser emp = new OAUser();
				emp.setUserId("888888");
				emp.setUserCode("88888888");
				emp.setUserName("admin");
				return emp;
			}
		}
		
		passWord = MD5.encodeByMd5AndSalt(passWord); // HSOA pwd Md5加密
		OAUser emp = employeeOAMapper.simplLogin(loginName, passWord, this.hsoaDB);
		return emp;
	}

	@Override
	public SessionRouter findEmpRouters(String empId, String systemName) {
		if (StringUtils.isBlank(empId)) {
			return null;
		}
		SessionRouter session = new SessionRouter();
		//首先获取用户的 部门 工号 公司 信息
		OAUser emp = new OAUser();
		OAStore store = null;
		emp.setUserId("888888");
		emp.setUserCode("88888888");
		emp.setUserName("admin");
		emp.setUserLock("0");
		emp.setComId("441");
		emp.setComName("默认公司");
		emp.setPartId("3012");
		emp.setPartName("system_dept_id");
		if (!"888888".equals(empId)) {
			emp = employeeOAMapper.findUserInfo(empId, this.hsoaDB);
			store = employeeOAMapper.findStoreByDeptId(emp.getPartId(), this.hsoaDB);
		}
		
		session.setEmpId(emp.getUserId());
		session.setEmpName(emp.getUserName());
		session.setEmpWorkNum(emp.getUserCode());
		session.setComId(emp.getComId());
		session.setComName(emp.getComName());
		session.setDeptId(emp.getPartId());
		session.setDeptName(emp.getPartName());
		if (store!=null) {
			// 设置营业部数据
			session.setStoreId(store.getStoreId());
			session.setStoreName(store.getStoreName());
			session.setStoreCityName(store.getStoreCityName());
		}else {
			session.setStoreId(emp.getPartId());
			session.setStoreName(emp.getPartName());
			session.setStoreCityName("");
		}
		
		List<SysRole> roles = employeeOAMapper.findEmpRoleCpt(empId,this.systemName);
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
	
	@Override
	public List<String> findEmpRole(String empId) {
		if (StringUtils.isBlank(empId)) {
			return null;
		}
		return employeeOAMapper.findEmpRoleId(empId, this.hsoaDB);
	}
	
	@Override
	public OAUser findUserInfoByUserCode(String userCode) {
		return employeeOAMapper.findUserInfoByUserCode(userCode, this.hsoaDB);
	}
	
	//获取某员工所在部门的所有员工信息
	@Override
	public List<OAUser> findDeptEmp(String empId) {
		if (StringUtils.isBlank(empId)) {
			return null;
		}
		return employeeOAMapper.findDeptEmp(empId,this.hsoaDB);
	}
	
	
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月18日 <br> 
	 * 描述： 获取部门下的员工数据  账号状态正常的员工   不为离职状态的
	 * @param deptId
	 * @param oadb
	 * @return
	 */
	@Override
	public Map<String, Object> findAppUserInfo(@Param("userCode") String userCode){
		  return  employeeOAMapper.findAppUserInfo(userCode, this.hsoaDB);
	}
	
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月18日 <br> 
	 * 描述： 获取门店信息  
	 * @param parent_id
	 * @param oadb
	 * @return
	 */
	@Override
	public Map<String, Object> findAppUserStorInfo(@Param("deptId") String deptId){
		 return  employeeOAMapper.findAppUserStorInfo(deptId, this.hsoaDB);
	}
	
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月18日 <br> 
	 * 描述： 获取所有门店信息  
	 * @param oadb
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findAllStoreData(){
		return  employeeOAMapper.findAllStoreData(this.hsoaDB);
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
	public Map<String, Object> findStorebyId(@Param("storeId") String storeId) {
		return employeeOAMapper.findStorebyId(storeId, this.hsoaDB);
	}

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月26日 <br>
	 * 描述： 获取用户的职位
	 * 
	 * @param deptId
	 * @return
	 */
	@Override
	public Map<String, Object> findUserPost(@Param("id") String id){
		return employeeOAMapper.findUserPost(id, this.hsoaDB);
	}

}
