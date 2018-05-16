/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.sales.api.model.Visitor;

/**
 * 作者：liming <br>
 * 创建时间：2017年12月8日 <br>
 * 描述：回访
 */
public interface VisitorServerI {

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月8日 <br>
	 * 描述： 添加回访记录
	 * @param visitor
	 * @return
	 */
	Json addVisitor(Visitor visitor);
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月8日 <br>
	 * 描述： 修改回访记录
	 * @param visitor
	 * @return
	 */
	Json updateVisitor(Visitor visitor);
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月8日 <br>
	 * 描述： 删除回访记录
	 * @param visId
	 * @return
	 */
	Json deleteVisitor(String visId);
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 回访分页
	 * @param page
	 * @param rows
	 * @param visTime
	 * @return
	 */
	Pager<Visitor> findPage(Integer page, Integer rows, String visTime,String visFkid);
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 根据id查找
	 * @param visId
	 * @return
	 */
	Visitor findVisitorById(String visId);
	
}
