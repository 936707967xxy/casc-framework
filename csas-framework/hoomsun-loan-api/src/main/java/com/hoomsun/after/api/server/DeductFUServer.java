/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import java.math.BigDecimal;

import com.hoomsun.after.api.model.vo.DeductServerResult;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年5月4日 <br>
 * 描述：
 */
public interface DeductFUServer {

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月13日 <br>
	 * 描述： 客户正常月还划扣
	 * 
	 * @param loanId
	 *            进件编号
	 * @param stream
	 *            还款期次
	 * @param req
	 * @return
	 */
	DeductServerResult saveNomalDeduct(String loanId, Integer stream, BigDecimal deductMoney, String path, String applicationCastId, String applicationCastName);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月13日 <br>
	 * 描述：逾期还款划扣
	 * 
	 * @param loanId
	 *            进件编号
	 * @param stream
	 *            期次
	 * @param req
	 * @return
	 */
	DeductServerResult saveOverdueDeduct(String loanId, Integer stream, BigDecimal deductMoney, String path, String applicationCastId, String applicationCastName);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月13日 <br>
	 * 描述： 提前还款划扣
	 * 
	 * @param loanId
	 *            进件编号
	 * @param stream
	 *            期次
	 * @param req
	 * @return
	 */
	DeductServerResult saveAdvanceDeduct(String loanId, Integer stream, BigDecimal deductMoney, String path, String applicationCastId, String applicationCastName);

}
