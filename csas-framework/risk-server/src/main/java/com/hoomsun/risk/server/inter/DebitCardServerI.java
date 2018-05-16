/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.server.inter;

import com.hoomsun.common.model.Result;
import com.hoomsun.risk.model.DebitCard;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月6日 <br>
 * 描述：储蓄银行流水
 */
public interface DebitCardServerI {
	
	public Result saveDebitCard(DebitCard debitCard);
}
