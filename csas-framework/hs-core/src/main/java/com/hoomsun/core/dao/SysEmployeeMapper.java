package com.hoomsun.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.core.model.Session;
import com.hoomsun.core.model.SysEmployee;
import com.hoomsun.core.model.SysResources;
import com.hoomsun.core.model.SysRole;
import com.hoomsun.core.model.vo.OAStore;

public interface SysEmployeeMapper {
	Integer deleteByPrimaryKey(String empId);

	Integer insert(SysEmployee record);

	Integer insertSelective(SysEmployee record);

	SysEmployee selectByPrimaryKey(String empId);

	Integer updateByPrimaryKeySelective(SysEmployee record);

	Integer updateByPrimaryKey(SysEmployee record);

	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：满足筛选条件的数据 分页
	 * 
	 * @param param
	 *            keys:pageIndex,pageSize,empName,comId,deptId
	 * @return
	 */
	List<SysEmployee> findPageData(Map<String, Object> param);

	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：满足筛选条件的数据量
	 * 
	 * @param param
	 * @return keys:pageIndex,pageSize,empName,comId,deptId
	 */
	Integer findPageCount(Map<String, Object> param);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月10日 <br>
	 * 描述：登陆的方法
	 * 
	 * @param params
	 * @return
	 */
	Session login(Map<String, Object> params);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月10日 <br>
	 * 描述： 某员工的角色和系统资源的信息
	 * 
	 * @param empId
	 * @return
	 */
	List<SysRole> findEmpResources(String empId);

	SysEmployee findEmpRoles(String empId);

	Integer updateStatus(@Param("empStatus") Integer empStatus, @Param("empId") String empId);

	Integer updatePassWord(@Param("passWord") String passWord, @Param("empId") String empId);

	List<SysResources> findResourcesByEmpId(String empId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月14日 <br>
	 * 描述： 某部门下的员工
	 * 
	 * @param deptId
	 * @param empId  加上empId 意思是部门下除过当前员工的所有员工
	 * @return
	 */
	List<SysEmployee> findByDept(@Param("deptId")String deptId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月16日 <br>
	 * 描述： 莫用户某系统拥有的组件
	 * 
	 * @param empId
	 *            用户id
	 * @param systemName
	 *            系统的名称
	 * @return
	 */
	SysEmployee findEmpComponents(@Param("empId") String empId, @Param("systemName") String systemName);

	SysEmployee simplLogin(@Param("loginName") String loginName, @Param("passWord") String passWord);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年11月23日 <br>
	 * 描述： 获取员工的角色id
	 * 
	 * @param empId
	 * @return
	 */
	List<String> findEmpRoleId(String empId);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月11日 <br>
	 * 描述： 审批组搜索用户工号用于授权
	 * 
	 * @param empWorkNum
	 * @return
	 */
	SysEmployee findUserInfoByUserCode(String empWorkNum);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月8日 <br>
	 * 描述： 伪删除调用，给flag更新为1
	 * 
	 * @param empId
	 * @return
	 */
	Integer updateDelFlagByPrimaryKey(String empId);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月11日 <br>
	 * 描述： 审批组搜索用户工号用于授权
	 * 
	 * @param empWorkNum
	 * @param hsoaDB
	 * @return
	 */
	SysEmployee findUserInfoByempWorkNum(String empWorkNum);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 获取某员工所在部门所有员工的数据 empId empName
	 * 
	 * @param empId
	 * @return empId empName comId
	 */
	List<SysEmployee> findDeptEmp(String empId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月13日 <br>
	 * 描述： 根据部门获取部门所在的门店
	 * 
	 * @param deptId
	 * @param hsoaDB
	 * @return
	 */
	OAStore findStoreByDeptId(String deptId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月13日 <br>
	 * 描述： 获取某门店下的所有的部门数据 也可获取 某部门的下级部门数据
	 * 
	 * @param deptId
	 * @param oadb
	 * @return
	 */
	List<OAStore> findSubDeptByStoreId(String deptId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月13日 <br>
	 * 描述： 获取部门下的员工数据 账号状态正常的员工 不为离职状态的
	 * 
	 * @param deptId
	 * @param oadb
	 * @return
	 */
	List<SysEmployee> findEmpInDept(String deptId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月21日 <br>
	 * 描述： 获取部门负责人的ID
	 * 
	 * @param deptId
	 * @return
	 */
	String findDeptManager(String deptId);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月15日 <br>
	 * 描述：员工修改的时候增加作用域
	 * 
	 * @param empId
	 * @return
	 */
	SysEmployee selectByPrimaryKeyAddScope(String empId);

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
	 * 创建时间：2018年1月19日 <br>
	 * 描述： 通过身份证更新密码
	 * @param iD
	 * @param pwd
	 * @param oldpwd 
	 * @return
	 */
	Integer updatePwdByID(@Param("cardNumber") String cardNumber, @Param("newpwd")String newpwd, @Param("oldpwd")String oldpwd);

	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月19日 <br>
	 * 描述： 提供个oa的重置密码的接口
	 * @param cardNumber
	 * @param newpwd
	 * @return
	 */
	Integer resetPwdByID(@Param("cardNumber") String cardNumber, @Param("newpwd")String newpwd);
	
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年2月11日 <br>
	 * 描述： 获取员工所在部门所有员工和其下（递归其下部门）所有部门的员工
	 * @param empId
	 * @return
	 */
	List<SysEmployee> selectEmpsByManager(String empId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月11日 <br>
	 * 描述： 某部门下的员工和该部门所有下级部门的员工
	 * @param deptId 部门ID
	 * @return
	 */
	List<SysEmployee> findEmpByDeptDescendants(String deptId);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年2月24日 <br>
	 * 描述： 修改用户状态
	 * @param employeeNo
	 * @param status
	 * @return
	 */
	int updateEmpStatus(@Param("cardNumber") String cardNumber, @Param("empStatus")String empStatus);
}