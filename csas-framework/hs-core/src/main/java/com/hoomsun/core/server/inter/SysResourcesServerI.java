/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.inter;

import java.util.List;

import com.hoomsun.common.model.ComboTree;
import com.hoomsun.common.model.DataGrid;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.TreeNode;
import com.hoomsun.core.model.SysResources;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月10日 <br>
 * 描述：
 */
public interface SysResourcesServerI {
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：添加系统资源
	 * @param resources 系统资源信息
	 * @return
	 */
	Json createResources(SysResources resources);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：修改系统资源 资源信息 根据主键进行修改
	 * @param resources
	 * @return
	 */
	Json updateResources(SysResources resources);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：删除系统资源 根据主键
	 * @param resId 资源的主键ID
	 * @return
	 */
	Json deleteResources(String resId);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：根据主键查询系统资源信息
	 * @param resId 资源的主键ID
	 * @return
	 */
	SysResources findById(String resId);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：分页查询系统资源
	 * @param page 当前页码
	 * @param rows 每页显示数据量
	 * @param resName 资源名称
	 * @return
	 */
	DataGrid<SysResources> findPageData(Integer page,Integer rows,String resName);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：获取系统资源的信息 以树形下拉列表数据格式返回数据
	 * @return
	 */
	List<ComboTree> findComboTree();
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：查询出所有的系统资源数据
	 * @return
	 */
	List<SysResources> findAllData();
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月11日 <br>
	 * 描述： 资源树
	 * @return
	 */
	List<TreeNode> findTreeNode();
}
