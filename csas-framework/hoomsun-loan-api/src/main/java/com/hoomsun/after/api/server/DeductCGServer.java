/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import java.util.Date;
import java.util.List;

import com.hoomsun.after.api.model.param.CGParam;
import com.hoomsun.after.api.model.table.PublicSave;
import com.hoomsun.after.api.model.vo.DeductServerResult;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年4月28日 <br>
 * 描述：存公
 */
public interface DeductCGServer {

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月28日 <br>
	 * 描述： 查询客户存公数据（未认领）
	 * 
	 * @param rpynam
	 * @param rpyacc
	 * @param naryur
	 * @param transactionDate
	 * @return
	 */
	List<PublicSave> getDeductList(String rpynam, String rpyacc, String naryur,String corporateBankAccount, Date transactionDate);

	DeductServerResult saveNomalDeduct(CGParam cGParam, String applicationCastId, String applicationCastName);

	DeductServerResult saveOverdueDeduct(CGParam cGParam, String applicationCastId, String applicationCastName);

	DeductServerResult saveAdvanceDeduct(CGParam cGParam, String applicationCastId, String applicationCastName);

}
