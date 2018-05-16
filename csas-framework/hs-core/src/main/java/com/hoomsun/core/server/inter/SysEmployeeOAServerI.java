/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.inter;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.OAUser;
import com.hoomsun.core.model.vo.SessionRouter;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月4日 <br>
 * 描述：
 */
public interface SysEmployeeOAServerI {

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
	Pager<OAUser> findPageData(Integer page, Integer rows, String empName, String comId, String deptId, String jobId, String empWorkNum);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 授权
	 * 
	 * @param empId
	 * @param roleIds
	 * @return
	 */
	Json grant(String empId, String[] roleIds);
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 登录
	 * 
	 * @param loginName
	 * @param passWord
	 * @return
	 */
	OAUser login(String loginName, String passWord);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月16日 <br>
	 * 描述： 获取某用户的拥有的路由信息
	 * 
	 * @param empId
	 * @return
	 */
	SessionRouter findEmpRouters(String empId, String systemName);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月7日 <br>
	 * 描述：  得到某OA员工的角色ID
	 * @param empId
	 * @return
	 */
	List<String> findEmpRole(String empId);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月11日 <br>
	 * 描述： 审批组授权通过工号获取用户信息
	 * @param userCode
	 * @return
	 */
	OAUser findUserInfoByUserCode(String userCode);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 获取某员工所在部门的所有员工
	 * @param empId 
	 * @return
	 */
	List<OAUser> findDeptEmp(String empId);
	
	
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月18日 <br> 
	 * 描述： 获取部门下的员工数据  账号状态正常的员工   不为离职状态的
	 * @param deptId
	 * @param oadb
	 * @return
	 */
	Map<String, Object> findAppUserInfo(@Param("userCode") String userCode);
	
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月18日 <br> 
	 * 描述： 获取门店信息  
	 * @param parent_id
	 * @param oadb
	 * @return
	 */
	Map<String, Object> findAppUserStorInfo(@Param("deptId") String deptId);
	
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月18日 <br> 
	 * 描述： 获取所有门店信息  
	 * @param oadb
	 * @return
	 */
	List<Map<String, Object>> findAllStoreData();

	
	Map<String, Object> findStorebyId(@Param("storeId") String storeId);
	
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月26日 <br>
	 * 描述： 获取用户的职位
	 * 
	 * @param deptId
	 * @return
	 */
	Map<String, Object>  findUserPost(@Param("id") String id);
}
