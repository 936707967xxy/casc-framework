/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.inter;

import java.util.List;
import java.util.Map;

import com.hoomsun.common.model.ComboTree;
import com.hoomsun.common.model.DataGrid;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.SysCompany;
import com.hoomsun.core.model.vo.VueLazyTree;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月10日 <br>
 * 描述：公司管理的业务接口
 */
public interface SysCompanyServerI {
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：创建公司
	 * @param company 公司添加数据信息
	 * @return
	 */
	Json createCom(SysCompany company);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：修改公司
	 * @param company 公司修改数据信息
	 * @return
	 */
	Json updateCom(SysCompany company);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：删除公司 公司ID
	 * @param comId
	 * @return
	 */
	Json deleteCom(String comId);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：根据主键查询公司信息
	 * @param comId 公司ID
	 * @return
	 */
	SysCompany findById(String comId);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：分页查询公司数据信息
	 * @param page 当前页码
	 * @param rows 每页数据量
	 * @param comName 公司名称  模糊
	 * @return
	 */
	DataGrid<SysCompany> findPageData(Integer page,Integer rows,String comName);
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：分页查询公司数据信息
	 * @param page 当前页码
	 * @param rows 每页数据量
	 * @param comName 公司名称  模糊
	 * @return
	 */
	Pager<SysCompany> findPage(Integer page,Integer rows,String comName);
	/**
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：查询出所有的公司信息
	 * @return
	 */
	List<SysCompany> findAllData();
	
	/**
	 * 作者：ywzou<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：查询出公司的信息 以树形菜单显示
	 * @return
	 */
	List<ComboTree> findComboTree();
	
	public List<Map<String, Object>> treeData();
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月9日 <br>
	 * 描述： vue treeData 点击一下加载后面数据
	 * @param parentId
	 * @return
	 */
	List<VueLazyTree>  findVueTreeData(String parentId);
}
