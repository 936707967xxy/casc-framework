/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.after.api.model.hxb.BadRepayParam;
import com.hoomsun.after.api.model.vo.BadRepayParamVo;
import com.hoomsun.common.util.CreateSerialNo;
import com.hoomsun.common.util.HttpRequest;

/**
 * 作者：zwLiu <br>
 * 创建时间：2018年5月4日 <br>
 * 描述：红小宝相关
 */
public class HxbRequest {
		
	private static final String URL = "http://123.126.19.2:8041";
	
	private static final Logger LOG = Logger.getLogger(HxbRequest.class);
	/**
	 * 
	 * 作者：zwLiu <br>
	 * 创建时间：2018年5月4日 <br>
	 * 描述：红小宝 5.7：平台代偿还款
	 * @throws Exception 
	 */
	public static BadRepayParamVo requestBadRepay(BadRepayParam badRepayParam){
		JSONObject js = new JSONObject();
		List<BadRepayParam> resList = new ArrayList<BadRepayParam>();
		resList.add(badRepayParam);
		js.put("generalInfo", CreateSerialNo.sign());
		js.put("loanRepayList", resList);
		LOG.info("请求数据"+ js.toJSONString());
		String result = null;
		try {
			result = HttpRequest.sendPost(URL + "/escrow/bad_repay", js.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return new BadRepayParamVo("2000", "请求异常！");
		};
		LOG.info("返回数据" + result);
		/**
		 * 解析红小宝的返回信息
		 */
		JSONObject resultObj = new JSONObject();
		resultObj = resultObj.parseObject(result);		
		boolean verify = CreateSerialNo.decode(resultObj.getJSONObject("generalInfo"));
		if(verify){
			return new BadRepayParamVo("2000", "本地验签失败！");
		}else{
			String status = resultObj.getString("status");	
			String errMsg = resultObj.getString("errMsg");
			//请求总条数
			String totalCount = resultObj.getString("totalCount");
			//成功总条数
			String successCount = resultObj.getString("successCount");
			//失败总条数
			String errCount = resultObj.getString("errCount");
			return new BadRepayParamVo(status, errMsg, totalCount, successCount, errCount);
			
		}
	}
	
	
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		BadRepayParam badRepayParam = new BadRepayParam();
		//至信借款的唯一编号
		badRepayParam.setLoanRefId("XAZM20171016007_01");
		//期序(提前还款时为空)
		badRepayParam.setPeriodNumber(1);
		//还款类型：IN_REPAY(提前还清)，COMMON_REPAY(正常还款)
		badRepayParam.setRepaidType("COMMON_REPAY");
		//是否逾期,是:1, 否:0
		badRepayParam.setOverdueFlag("1");
		//借款端应还款时间
		badRepayParam.setRepayTimePlan("20180430");
		//借款端至信方实际还款时间
		badRepayParam.setRepayTimeActual("20180501");
		//借款端本息(本金+利息), 仅记录, 红小宝不使用此金额
		badRepayParam.setAmount(new BigDecimal("1500"));
		//借款端本金, 仅记录, 红小宝不使用此金额
		badRepayParam.setPrincipal(new BigDecimal("1000"));
		//借款端利息, 仅记录, 红小宝不使用此金额
		badRepayParam.setInterest(new BigDecimal("500"));
		//逾期罚息（只有在逾期还款或还代偿款时需传入，减免后, 仅记录, 红小宝不使用此金额
		badRepayParam.setOverdueInterestFee(new BigDecimal("200"));
		//贷后服务费（只有在逾期还款或还代偿款时需传入，减免后, 仅记录, 红小宝不使用此金额）
		badRepayParam.setOverdueServiceFee(new BigDecimal("100"));
		//贷后管理费（指逾期违约金，只有在逾期还款或还代偿款时需传入，减免后, 仅记录, 红小宝不使用此金额）
		badRepayParam.setOverdueMgmtFee(new BigDecimal("100"));
		requestBadRepay(badRepayParam);
		System.out.println("----------");
	}
}
