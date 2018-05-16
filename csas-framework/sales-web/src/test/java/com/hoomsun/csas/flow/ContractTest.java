/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.flow;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hoomsun.core.dao.SystemParamMapper;
import com.hoomsun.core.enums.SystemParamEnum;
import com.hoomsun.core.model.SysContract;
import com.hoomsun.core.model.SysProduct;
import com.hoomsun.core.model.SysRepaymentPlan;
import com.hoomsun.core.model.SystemParam;
import com.hoomsun.core.server.inter.SysContractServerI;
import com.hoomsun.core.server.inter.SysProductServerI;
import com.hoomsun.core.util.BillsDateRangeCfg;
import com.hoomsun.core.util.RepaymentMethods;
import com.hoomsun.pdf.CreatePdfFile;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月8日 <br>
 * 描述：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-cfg.xml")
public class ContractTest {
//	@Autowired
//	private FinalAuditServerI finalAuditServer;
	@Autowired
	private SysProductServerI productServer;
	@Autowired
	private BillsDateRangeCfg billsDateRangeUtil;
	@Autowired
	private SysContractServerI contractServer;
	@Autowired
	private SystemParamMapper systemParamMapper;
	
	@Test
	public void paramTest() {
		SystemParam param = systemParamMapper.findParamByKey(SystemParamEnum.PUSH_ONLINE.getKey());
		System.out.println(param);
	}
	
	@Test
	public void repay() {
////		FinalAudit finalAudit = finalAuditServer.findByApplyId("0147fcfd1c944e6d98c6cf97d599f799");
////		BigDecimal auditMoney = finalAudit.getApprovedAmount();// 批准金额
//		String prodId = "382b7b844dc2454ba90b62c545087349";
//		SysProduct product = productServer.findById(prodId);
////		SysContract contract = RepaymentMethods.computeRepaymentPlan(new BigDecimal(26000), new Date(), product, billsDateRangeUtil,null);
//		//1455.96
//		System.out.println("#######:" + contract + "\n");
//		System.out.println("");
//		System.out.println("");
//		for (SysRepaymentPlan plan : contract.getRepaymentPlans()) {
//			System.out.println(plan);
//		}
//		System.out.println("");
//		System.out.println("");
	}

	//还款计划 合同
	@Test
	public void repayPlan() {
		SysContract contract = contractServer.findByConId("MtTncBoKXaDIvrcKKRjl");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("custName", "789");
		param.put("shouldAmt", "3000");//月还款额
		param.put("amt", "2000");//本息
		String conNo = contract.getConNo();
		List<Map<String, Object>> maps = new ArrayList<>();
		List<SysRepaymentPlan> plans= contract.getRepaymentPlans();
		for (SysRepaymentPlan pl : plans) {
			Map<String, Object> plan = new HashMap<String, Object>();
			plan.put("shouldTerm", pl.getShouldTerm());
			plan.put("shouldDate", pl.getStrShouldDate());
			plan.put("shouldAmt", pl.getShouldAmt());
			plan.put("bal", pl.getBal());
			plan.put("advanceMoney", pl.getAdvanceMoney());
			maps.add(plan);
		}
		
		param.put("repaymentPlans", maps);
		
		CreatePdfFile.createObjectToPDF("D:\\Contract_Test\\model\\contract_plan.xml", "D:\\Contract_Test\\co\\" + conNo + ".pdf", "D:\\Contract_Test\\simfang.ttf", "D:\\Contract_Test\\456.png", param);
		
	}
}
