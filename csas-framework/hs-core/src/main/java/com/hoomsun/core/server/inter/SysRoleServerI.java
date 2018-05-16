/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.inter;

import java.util.List;

import com.hoomsun.common.model.DataGrid;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.model.TreeNode;
import com.hoomsun.core.model.SysRole;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月10日 <br>
 * 描述：
 */
public interface SysRoleServerI {
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：创建系统角色
	 * @param role
	 * @return
	 */
	Json createRole(SysRole role);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：修改系统角色  根据主键
	 * @param role
	 * @return
	 */
	Json updateRole(SysRole role);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：根据主键删除角色信息
	 * @param roleId
	 * @return
	 */
	Json deleteRole(String roleId);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：根据主键删除多个角色信息
	 * @param roleId
	 * @return
	 */
	Json deleteMultiRole(List<String> roleIds);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：根据主键查询角色信息
	 * @param roleId
	 * @return
	 */
	SysRole findById(String roleId);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：分页查询角色的信息
	 * @param page 当前页码
	 * @param rows 每页显示数据
	 * @param roleName 角色名称  模糊
	 * @return
	 */
	DataGrid<SysRole> findPageData(Integer page,Integer rows,String roleName);
	
	Pager<SysRole> findPage(Integer page,Integer rows,String roleName);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：给角色授权 
	 * @param roleId 角色ID
	 * @param resIds 系统资源ID 数组
	 * @return
	 */
	Json grant(String roleId,String[] resIds);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：根据角色名称查询系统角色信息
	 * @param roleName 角色名称
	 * @return
	 */
	SysRole findByName(String roleName);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：角色树
	 * @return
	 */
	List<TreeNode> findTreeNode();
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月11日 <br>
	 * 描述： 根據角色ID獲取角色資源的信息
	 * @param roleId
	 * @return
	 */
	SysRole findRoleResources(String roleId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月23日 <br>
	 * 描述： 更具角色的Id查询出该角色所拥有的所有的组件的唯一标识id
	 * @param id
	 * @return
	 */
	List<String> findRoleCpt(String id);
	
	Json grantCpt(String roleId, String[] cpts);
}
