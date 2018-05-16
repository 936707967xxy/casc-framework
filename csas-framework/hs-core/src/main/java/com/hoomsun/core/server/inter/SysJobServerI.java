/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.inter;

import java.util.List;

import com.hoomsun.common.model.ComboTree;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.SysJob;
import com.hoomsun.core.model.vo.VueLazyTree;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月10日 <br>
 * 描述：职位管理业务接口
 */
public interface SysJobServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月10日 <br>
	 * 描述： 创建职位
	 * @param job
	 * @return
	 */
	Json createJob(SysJob job);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月10日 <br>
	 * 描述： 修改职位
	 * @param job
	 * @return
	 */
	Json updateJob(SysJob job);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月10日 <br>
	 * 描述： 删除职位
	 * @param jobId
	 * @return
	 */
	Json deleteJob(String jobId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月10日 <br>
	 * 描述： 根据主键查询职位信息
	 * @param jobId
	 * @return
	 */
	SysJob findById(String jobId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月10日 <br>
	 * 描述： 分页查询数据
	 * @param page 当前页码
	 * @param rows 每页显示多少数据
	 * @param name 职位名称 模糊
	 * @return
	 */
	Pager<SysJob> findPageData(Integer page, Integer rows, String name);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月10日 <br>
	 * 描述： 查询出所有的数据
	 * @return
	 */
	List<SysJob> findAllData();
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月10日 <br>
	 * 描述： 根据职位名称查询 返回职位的ID
	 * @param jobName
	 * @return
	 */
	SysJob findByName(String jobName);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月10日 <br>
	 * 描述： 树形下拉框的数据
	 * @return
	 */
	List<ComboTree> findComboTree();
	
	
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
