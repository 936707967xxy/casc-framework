/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.inter;

import java.io.InputStream;

import org.activiti.engine.repository.Model;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月14日 <br>
 * 描述：流程管理的业务
 */
public interface FlowManagerServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月28日 <br>
	 * 描述： 分页查询数据
	 * 
	 * @param modelName
	 *            流程名称
	 * @param page
	 *            当前页
	 * @param rows
	 *            没页显示数据
	 * @return
	 */
	Pager<Model> findPager(String modelName, Integer page, Integer rows);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月28日 <br>
	 * 描述： 创建流程对象
	 * @param name 流程名称
	 * @param key 流程的key
	 * @param category 类型
	 * @param description 描述
	 * @param inputStream 上传的流信息
	 * @return
	 */
	Json createFlow(String name, String key, String category, String description, InputStream inputStream);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月28日 <br>
	 * 描述： 部署流程
	 * @param modelId 流程modelid
	 * @return
	 */
	Json deployFlow(String modelId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月28日 <br>
	 * 描述： 删除流程model
	 * @param modelId
	 * @return
	 */
	Json deleteFlow(String modelId);
}
