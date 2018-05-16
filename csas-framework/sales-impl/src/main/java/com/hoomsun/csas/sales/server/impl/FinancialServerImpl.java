/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.server.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.csas.sales.api.exception.AuditException;
import com.hoomsun.csas.sales.api.model.CreditCard;
import com.hoomsun.csas.sales.api.model.vo.FinancialVo;
import com.hoomsun.csas.sales.api.server.inter.FinancialServerI;
import com.hoomsun.csas.sales.dao.CreditCardMapper;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月7日 <br>
 * 描述：
 */
@Service("financialServer")
public class FinancialServerImpl implements FinancialServerI {
	
	private CreditCardMapper cardMapper;
	
	@Autowired
	public void setCardMapper(CreditCardMapper cardMapper) {
		this.cardMapper = cardMapper;
	}


	@Override
	public FinancialVo findByApplyId(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			throw new AuditException("参数异常! applyId is not null!"); 
		}
		List<CreditCard> creditCards = cardMapper.findByApplyId(applyId);
		
		FinancialVo vo = new FinancialVo();
		vo.setCreditCards(creditCards);
		return vo;
	}

	

}
