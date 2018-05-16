/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.inter;

import java.util.List;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.model.TreeNode;
import com.hoomsun.core.model.SysDepartment;
import com.hoomsun.core.model.vo.VueLazyTree;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月10日 <br>
 * 描述：
 */
public interface SysDepartmentServerI {
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：创建部门
	 * @param department
	 * @return
	 */
	Json createDept(SysDepartment department);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：修改部门
	 * @param department
	 * @return
	 */
	Json updateDept(SysDepartment department);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：根据主键删除部门
	 * @param deptId
	 * @return
	 */
	Json deleteDept(String deptId);
	
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：根据主键删除多个部门
	 * @param roleId
	 * @return
	 */
	Json deleteMultiRole(List<String> deptIds);
	
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：根据主键查询部门
	 * @param deptId
	 * @return
	 */
	SysDepartment findById(String deptId);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：分页查询部门信息
	 * @param page 当前页码
	 * @param rows 每页显示数据量
	 * @param comId 部门所在公司ID
	 * @param deptName
	 * @return
	 */
	Pager<SysDepartment> findPageData(Integer page,Integer rows,String comId,String deptName);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：查询出所有的部门信息
	 * @return
	 */
	List<SysDepartment> findAllData();
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：部门信息按下拉宽数据格式展示
	 * @return
	 */
	List<TreeNode> findTreeNode(String comId);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月9日 <br>
	 * 描述： vue treeData 点击一下加载后面数据
	 * @param parentId
	 * @return
	 */
	List<VueLazyTree>  findVueTreeData(String parentId);
	
	/**
	 * 
	 * 作者：liudongliang <br>
	 * 创建时间：2018年1月15日 <br>
	 * 描述：查询门店编号
	 * @param parentId
	 * @return
	 */
	SysDepartment selectByPrimaryKey(String deptId);
	
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月23日 <br>
	 * 描述： 获取所有营业部的名称，用于显示
	 * @return
	 */
	List<SysDepartment> findAllStoreShow();
}
