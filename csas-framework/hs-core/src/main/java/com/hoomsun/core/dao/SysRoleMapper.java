package com.hoomsun.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.core.model.SysRole;

public interface SysRoleMapper {
    int deleteByPrimaryKey(String roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(String roleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
    /**
     * 作者：ywzou<br>
     * 创建时间：2017年9月6日<br>
     * 描述：分页查询数据
     * @param param 筛选条件 keys:pageIndex,pageSize,roleName
     * @return
     */
	List<SysRole> findPageData(Map<String, Object> param);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：满足条件的数据量
	 * @param param 筛选条件 keys:pageIndex,pageSize,roleName
	 * @return
	 */
	int findPageCount(Map<String, Object> param);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：根据角色名称获取角色信息
	 * @param roleName
	 * @return
	 */
	SysRole findByName(String roleName);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：获取所有的角色信息
	 * @return
	 */
	List<SysRole> findAllData(@Param("systemName")String systemName);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月11日 <br>
	 * 描述：查询某角色的资源信息
	 * @param roleId
	 * @return
	 */
	SysRole findRoleResources(String roleId);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年11月22日 <br>
	 * 描述： 通过roleId查询数据，结果中不包含归属(ASCRIPTION)字段
	 * @param roleId
	 * @return
	 */
	SysRole selectById(String roleId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月23日 <br>
	 * 描述： 获取角色所拥有的所有的组件的唯一标识id
	 * @param roleId 角色的id
	 * @return
	 */
	List<String> findRoleCpt(@Param("roleId")String roleId,@Param("systemName") String systemName);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年11月29日 <br>
	 * 描述： 删除多个角色
	 * @param roleIds
	 * @return
	 */
	int deleteByMultiRoles(@Param("roleIds")List<String> roleIds);
}