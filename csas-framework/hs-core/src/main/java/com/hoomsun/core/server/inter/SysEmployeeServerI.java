/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.inter;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.model.TreeNode;
import com.hoomsun.core.model.SysEmployee;
import com.hoomsun.core.model.vo.SessionRouter;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月10日 <br>
 * 描述：员工管理的业务接口
 */
public interface SysEmployeeServerI {
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：创建员工信息
	 * 
	 * @param employee
	 *            员工信息
	 * @return
	 */
	Json createEmp(SysEmployee employee);

	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：修改员工信息 根据主键修改
	 * 
	 * @param employee
	 *            员工信息
	 * @return
	 */
	Json updateEmp(SysEmployee employee);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月8日 <br>
	 * 描述： 真的删除员工
	 * @param empId
	 * @return
	 */
	Json deleteEmpReal(String empId);
	
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：伪删除员工信息
	 * 
	 * @param empId
	 *            员工ID
	 * @return
	 */
	Json deleteEmpByAddFlag(String empId);

	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：根据主键查询员工信息
	 * 
	 * @param empId
	 *            员工ID
	 * @return
	 */
	SysEmployee findById(String empId);

	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：分页查询员工的数据
	 * 
	 * @param page
	 *            当前页码
	 * @param rows
	 *            每页显示数据量
	 * @param empName
	 *            员工姓名 模糊
	 * @param comId
	 *            公司ID
	 * @param deptId
	 *            部门ID
	 * @return
	 */
	Pager<SysEmployee> findPageData(Integer page, Integer rows, String empName, String comId, String deptId,String jobId, String empWorkNum);

	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：给用户授权
	 * 
	 * @param empId
	 *            员工ID
	 * @param roleIds
	 *            角色ID 数组
	 * @return
	 */
	Json grant(String empId, String[] roleIds);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月10日 <br>
	 * 描述： 员工登陆的方法
	 * @param loginName
	 * @param pwd
	 * @param request
	 * @return
	 */
	Json login(String loginName,String pwd,HttpServletRequest request);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月12日 <br>
	 * 描述： 得到某员工的角色ID
	 * @param empId
	 * @return
	 */
	List<String> findEmpRole(String empId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月14日 <br>
	 * 描述： 修改账户状态
	 * @param empStatus
	 * @param empId
	 * @return
	 */
	Json updateStatus(Integer empStatus,String empId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月14日 <br>
	 * 描述： 修改密码
	 * @param pwd
	 * @param empId
	 * @return
	 */
	Json updatePassWord(String empId, String newpwd, String oldpwd);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月 14日 <br>
	 * 描述： 某员工的角色树 资源树
	 * @param empId
	 * @return
	 */
	List<TreeNode> findRoleTreeNode(String empId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月14日 <br>
	 * 描述： 某部门下的员工信息
	 * @param deptId
	 * @return {empId:"",value:"xx部门-xx职位-xx"}
	 */
	List<SysEmployee> findByDept(String deptId);  
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月16日 <br>
	 * 描述： 获取某用户的拥有的路由信息
	 * @param empId
	 * @return
	 */
	SessionRouter findEmpRouters(String empId,String systemName);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月17日 <br>
	 * 描述： 登录
	 * @param loginName
	 * @param passWord
	 * @return
	 */
	SysEmployee login(String loginName, String passWord);

	
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月18日 <br>
	 * 描述： 获取部门下的员工数据 账号状态正常的员工 不为离职状态的
	 * 
	 * @param deptId
	 * @param oadb
	 * @return
	 */
	Map<String, Object> findAppUserInfo(String empWorkNum);
	
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月18日 <br>
	 * 描述： 获取门店信息
	 * 
	 * @param parent_id
	 * @param oadb
	 * @return
	 */
	Map<String, Object> findAppUserStorInfo(String deptId);
	
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月18日 <br>
	 * 描述： 获取所有门店信息
	 * 
	 * @param oadb
	 * @return
	 */
	List<Map<String, Object>> findAllStoreData();
	
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月18日 <br>
	 * 描述： 获取所有门店信息签约时
	 * 
	 * @param oadb
	 * @return
	 */
	Map<String, Object> findStorebyId(String deptId);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月11日 <br>
	 * 描述： 审批组授权通过工号获取用户信息
	 * @param userCode
	 * @return
	 */
	SysEmployee findUserInfoByUserCode(String empWorkNum);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月14日 <br>
	 * 描述： 获取某部门下的员工
	 * @param deptId
	 * @param empId 
	 * @return
	 */
	List<SysEmployee> findDeptEmp(String deptId);

	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月19日 <br>
	 * 描述： 根据身份证更改密码
	 * @param cardNumber
	 * @param userPass
	 * @param oldpwd 
	 * @return
	 */
	Json updatePwdByID(String cardNumber, String newpwd, String oldpwd);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月19日 <br>
	 * 描述： 重置密码
	 * @param employeeNo
	 * @param newPass
	 * @return
	 */
	Json resetPwdByID(String employeeNo, String newPass);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年2月24日 <br>
	 * 描述： 更改员工状态
	 * @param employeeNo
	 * @param status
	 * @return
	 */
	Json updateEmpStatus(String employeeNo, String status);
}
