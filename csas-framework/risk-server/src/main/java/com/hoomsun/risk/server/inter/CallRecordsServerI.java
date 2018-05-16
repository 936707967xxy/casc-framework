/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.server.inter;

import com.hoomsun.common.model.Result;
import com.hoomsun.risk.model.CallRecords;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月29日 <br>
 * 描述：通话记录的业务方法
 */
public interface CallRecordsServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月29日 <br>
	 * 描述： 添加通话记录  并分析出通话频次Top N  和 近三月的通话号码
	 * @param records
	 * @return
	 */
	Result createCallRecords(CallRecords records);
	
	
}
