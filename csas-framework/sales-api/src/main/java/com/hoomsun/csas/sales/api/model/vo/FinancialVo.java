/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.model.vo;

import java.util.List;

import com.hoomsun.csas.sales.api.model.CreditCard;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月7日 <br>
 * 描述：审核财务信息
 */
public class FinancialVo {
	// 信用卡账单
	private List<CreditCard> creditCards;
	// 银行卡账单

	public List<CreditCard> getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(List<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}
	
}
