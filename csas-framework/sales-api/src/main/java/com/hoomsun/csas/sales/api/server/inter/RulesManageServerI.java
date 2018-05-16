package com.hoomsun.csas.sales.api.server.inter;

import java.util.Map;

public interface RulesManageServerI {

	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-12-29
	 * @resource 
	 *  1.线上、线下产品均不允许同时申请，即客户一旦申请一笔产品，在该订单结束之前不允许申请第二笔产品。
	 *  查询不为PROC_STATUS    不为放款 不为据贷    放弃       包含为NULL
	 */
	Map<String,Object>  IsCredit(String idCard); 
	
	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-12-29
	 * @resource 
	 *  4、先拥有线上产品，状态结束（拒贷、通过、结清）之后允许申请门店产品
	 *  5、先申请门店产品，自申请之时到状态结束，不允许申请任何其他产品。该门店产品状态完结之后，进入封闭期，封闭期内不允许申请任何其他产品。
	 *  二者归为          查询 门店有放款成功的单子  未结清的不让贷 	
	 */
	Map<String,Object>  StoreCreditIsFinish(String idCard);
	
	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-12-29
	 * @resource 
	 *  4.线上产品逾期，则也不能进件
	 *  App产品逾期不能进件
	 */
	Map<String,Object>  AppCreditIsFinish(String idCard);
	
	
	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-12-29
	 * @resource 
	 *  4、先拥有线上产品，状态结束（拒贷、结清）之后允许申请门店产品,放款成功之后不允许申请门店产品,仍然可申请线上剩余额度		
	 *  是否存在app产品的单子
	 */
	
    Map<String,Object> AppIsExist(String idCard);
	/**
	 * 
	 * @author 刘栋梁
	 * @date 2017-12-29
	 * @resource 
	 *  身份证判断是否黑名单    /姓名,身份证,手机号
	 */
	Map<String,Object>  BlackByIdCard(String idCard); 
	
	
	/**
	 * 
	 * @author 刘栋梁
	 * @date 2018-01-15
	 * @resource 
	 *  封闭期-------线下产品才有封闭期
	 */
	Map<String,Object>  Closeperiod(String idCard);
	
}
