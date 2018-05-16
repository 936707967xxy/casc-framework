/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.dao;

import java.util.List;

/**
 * 作者：金世强 <br>
 * 创建时间：2018年3月13日 <br>
 * 描述：apply,查询客户id和进件编号的
 */
public interface ApplyMapper {
	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月13日 <br>
	 * 描述： 获取客户账户Id
	 * 
	 * @param loanId
	 *            进件编号
	 * @return
	 */
	String getCustIdByLoanId(String loanId);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月13日 <br>
	 * 描述： 获取此客户下的所有件
	 * 
	 * @param custId客户Id
	 * @return
	 */
	List<String> getLoanIdsByCustId(String custId);
}
