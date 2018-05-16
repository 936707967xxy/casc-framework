/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import java.util.Date;

/**
 * 作者：金世强 <br>
 * 创建时间：2018年3月7日 <br>
 * 描述：修改逾期违约金
 */
public interface UpdateOverdueMoneyServer {

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月7日 <br>
	 * 描述： 根据日期修改此客户所有逾期违约金罚息，返回0成功，1失败
	 * 
	 * @param loanId
	 *            进件编号
	 * @param date
	 *            应逾期的当前日期
	 * @return 0成功 1失败
	 */
	public String updateOverdueMoney(String loanId, Date date);

}
