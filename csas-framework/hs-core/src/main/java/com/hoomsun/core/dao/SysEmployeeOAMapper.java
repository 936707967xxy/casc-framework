package com.hoomsun.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.core.model.SysRole;
import com.hoomsun.core.model.vo.OAStore;
import com.hoomsun.core.model.vo.OAUser;

public interface SysEmployeeOAMapper {
	/**
	 * 作者：liushua<br>
	 * 创建时间：2017年11月24日<br>
	 * 描述：满足筛选条件的数据 分页
	 * 
	 * @param param
	 *            keys:pageIndex,pageSize,empName,comId,deptId
	 * @return
	 */
	List<OAUser> findPageData(Map<String, Object> param);

	/**
	 * 作者：liushua<br>
	 * 创建时间：2017年11月24日<br>
	 * 描述：满足筛选条件的数据量
	 * 
	 * @param param
	 * @return keys:pageIndex,pageSize,empName,comId,deptId
	 */
	Integer findPageCount(Map<String, Object> param);

	OAUser simplLogin(@Param("loginName") String loginName, @Param("passWord") String passWord, @Param("hsoaDB") String hsoaDB);

	OAUser findUserInfo(@Param("empId") String empId, @Param("hsoaDB") String hsoaDB);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 获取用户的角色 组件 操作数据
	 * 
	 * @param empId
	 * @param hsoaDB
	 * @return
	 */
	List<SysRole> findEmpRoleCpt(@Param("empId") String empId, @Param("ascription") String systemName);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月7日 <br>
	 * 描述： 获取某OA员工的角色ID
	 * 
	 * @param empId
	 * @param hsoaDB
	 * @return
	 */
	List<String> findEmpRoleId(@Param("empId") String empId, @Param("hsoaDB") String hsoaDB);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月11日 <br>
	 * 描述： 审批组搜索用户工号用于授权
	 * 
	 * @param userCode
	 * @param hsoaDB
	 * @return
	 */
	OAUser findUserInfoByUserCode(@Param("userCode") String userCode, @Param("hsoaDB") String hsoaDB);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 获取某员工所在部门所有员工的数据 empId empName
	 * 
	 * @param empId
	 * @return empId empName comId
	 */
	List<OAUser> findDeptEmp(@Param("empId") String empId, @Param("OADB") String hsoaDB);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月13日 <br>
	 * 描述： 根据部门获取部门所在的门店
	 * 
	 * @param deptId
	 * @param hsoaDB
	 * @return
	 */
	OAStore findStoreByDeptId(@Param("deptId") String deptId, @Param("OADB") String oadb);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月13日 <br>
	 * 描述： 获取某门店下的所有的部门数据 也可获取 某部门的下级部门数据
	 * 
	 * @param deptId
	 * @param oadb
	 * @return
	 */
	List<OAStore> findSubDeptByStoreId(@Param("storeId") String deptId, @Param("OADB") String oadb);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月13日 <br>
	 * 描述： 获取部门下的员工数据 账号状态正常的员工 不为离职状态的
	 * 
	 * @param deptId
	 * @param oadb
	 * @return
	 */
	List<OAUser> findEmpInDept(@Param("deptId") String deptId, @Param("OADB") String oadb);

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月18日 <br>
	 * 描述： 获取部门下的员工数据 账号状态正常的员工 不为离职状态的
	 * 
	 * @param deptId
	 * @param oadb
	 * @return
	 */
	Map<String, Object> findAppUserInfo(@Param("userCode") String userCode, @Param("hsoaDB") String hsoaDB);

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月18日 <br>
	 * 描述： 获取门店信息
	 * 
	 * @param parent_id
	 * @param oadb
	 * @return
	 */
	Map<String, Object> findAppUserStorInfo(@Param("deptId") String deptId, @Param("hsoaDB") String hsoaDB);

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月18日 <br>
	 * 描述： 获取所有门店信息
	 * 
	 * @param oadb
	 * @return
	 */
	List<Map<String, Object>> findAllStoreData(@Param("hsoaDB") String hsoaDB);

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月18日 <br>
	 * 描述： 获取所有门店信息签约时
	 * 
	 * @param oadb
	 * @return
	 */
	Map<String, Object> findStorebyId(@Param("storeId") String storeId, @Param("hsoaDB") String hsoaDB);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月21日 <br>
	 * 描述： 获取部门负责人的ID
	 * 
	 * @param deptId
	 * @return
	 */
	String findDeptManager(@Param("deptId") String deptId, @Param("hsoaDB") String hsoaDB);
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月26日 <br>
	 * 描述： 获取用户的职位
	 * 
	 * @param deptId
	 * @return
	 */
	Map<String, Object>  findUserPost(@Param("id") String id, @Param("hsoaDB") String hsoaDB);

}