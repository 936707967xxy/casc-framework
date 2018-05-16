/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.inter;

import java.util.List;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.SysComponents;
import com.hoomsun.core.model.vo.Tree;

/**
 * 作者：liusong <br>
 * 创建时间：2017年11月16日 <br>
 * 描述：
 */
public interface SysComponentsServerI {
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：添加组件
	 * 
	 * @return
	 */
	Json createComponents(SysComponents record);

	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：修改组件
	 * 
	 * @return
	 */
	Json updateComponents(SysComponents record);

	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：删除组件
	 * 
	 * @return
	 */
	Json deleteComponents(String componentsId);

	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：根据主键查询组件
	 * 
	 * @return
	 */
	SysComponents findById(String componentsId);

	/**
	 * 作者：liusong <br>
	 * 创建时间：2017年11月16日 <br>
	 * 描述： 查询所有组件
	 * 
	 * @return
	 */
	List<SysComponents> findAll();

	Pager<SysComponents> findPageData(Integer page, Integer rows, String name);

	List<SysComponents> findByParent();

	List<Tree> findTree(String name);

	Json deleteBatchComponents(String[] ids);
}
