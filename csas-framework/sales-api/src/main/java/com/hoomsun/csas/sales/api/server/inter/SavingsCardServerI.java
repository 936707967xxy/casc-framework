/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import com.hoomsun.common.model.Json;
import com.hoomsun.csas.sales.api.model.SavingsCard;

/**
 * 作者：liusong <br>
 * 创建时间：2017年12月27日 <br>
 * 描述：
 */
public interface SavingsCardServerI {
	 
	Json createCard(SavingsCard record);
	
//	Json updateCard(SavingsCard record);
//	
//	SavingsCard selectByApplyId(String applyId);//根据applyId查询数据
//    
//    int countCard(String applyId);//根据applyId查询总条数

	
//	Json updateCard(SavingsCard record);
//	
	SavingsCard selectByApplyId(String applyId);//根据applyId查询数据


	Json addSavingsCard(SavingsCard savingsCard);

	SavingsCard findApplyById(String applyId);
}
