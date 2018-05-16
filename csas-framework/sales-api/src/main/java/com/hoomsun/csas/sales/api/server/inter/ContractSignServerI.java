/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.sales.api.model.UserApply;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月29日 <br>
 * 描述：合同签订
 */
public interface ContractSignServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月29日 <br>
	 * 描述： 质检复核列表数据
	 * 
	 * @param page
	 *            当前页码
	 * @param rows
	 *            每页显示数据
	 * @param idNo
	 *            客户证件号
	 * @param name
	 *            客户姓名
	 * @param empId
	 *            当前登录人id
	 * @return
	 */
	Pager<UserApply> findPager(Integer page, Integer rows, String idNo, String name, String empId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月29日 <br>
	 * 描述： 处理任务
	 * 
	 * @param applyId
	 *            申请ID
	 * @param audit
	 *            审核信息
	 * @return
	 */
	Json completeTask(String applyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月29日 <br>
	 * 描述： 签收任务
	 * 
	 * @param applyId
	 *            申请ID
	 * @param empId
	 *            当前登录人id 签收人
	 * @return
	 */
	Json claimTask(String applyId, String empId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月29日 <br>
	 * 描述： 退回任务
	 * 
	 * @param applyId
	 *            申请ID
	 * @return
	 */
	Json rollbackTask(String applyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月29日 <br>
	 * 描述： 撤回任务
	 * 
	 * @param applyId
	 *            申请ID
	 * @return
	 */
	Json withdrawTask(String applyId);
}
