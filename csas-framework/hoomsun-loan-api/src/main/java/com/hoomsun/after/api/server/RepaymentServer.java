/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import com.hoomsun.after.api.model.vo.RepaymentServerResult;

/**
 * 作者：金世强 <br>
 * 创建时间：2018年2月27日 <br>
 * 描述：还款相关操作接口
 */
public interface RepaymentServer {
	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年2月27日 <br>
	 * 描述：正常月还
	 * 
	 * @param loanId
	 * @param stream
	 * @param applicationCastId
	 * @param applicationCastName
	 * @return
	 */
	public RepaymentServerResult saveNomalRepayment(String custId, String loanId, Integer stream, String applicationCastId, String applicationCastName);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年2月27日 <br>
	 * 描述：逾期月还
	 * 
	 * @param loanId
	 * @param stream
	 * @param applicationCastId
	 * @param applicationCastName
	 * @return
	 */
	public RepaymentServerResult saveOverdueRepayment(String custId, String loanId, Integer stream, String applicationCastId, String applicationCastName);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年2月27日 <br>
	 * 描述：提前结清
	 * 
	 * @param loanId
	 * @param stream
	 * @param applicationCastId
	 * @param applicationCastName
	 * @return
	 */
	public RepaymentServerResult saveAdvanceRepayment(String custId, String loanId, Integer stream, String applicationCastId, String applicationCastName);

}
