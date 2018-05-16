/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.server.inter;

import java.util.Date;

import com.hoomsun.common.model.Json;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月4日 <br>
 * 描述：同步数据的业务
 */
public interface SynchronousServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月8日 <br>
	 * 描述：同步申请数据
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param synType
	 *            同步类型 1:接口推送 2：自动同步 3：手动同步
	 * @return
	 */
	Json synchronousData(Date startTime, Date endTime, Integer synType);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月8日 <br>
	 * 描述：同步通话记录数据
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param synType
	 *            同步类型  1:接口推送 2：自动同步 3：手动同步
	 * @return
	 */
	Json synchronousLinkData(Date startTime, Date endTime, Integer synType);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月8日 <br>
	 * 描述： 根据申请ID拉取数据
	 * @param applyId
	 * @return
	 */
	Json pullByApply(String applyId);
	

}
