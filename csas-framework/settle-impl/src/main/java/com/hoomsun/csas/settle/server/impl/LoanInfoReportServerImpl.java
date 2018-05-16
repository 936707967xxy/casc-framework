/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.settle.server.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.settle.dao.LoanInfoReportMapper;
import com.hoomsun.csas.settle.model.vo.LoanInfoReport;
import com.hoomsun.csas.settle.server.inter.LoanInfoReportServerI;

/**
 * 作者：liming <br>
 * 创建时间：2018年1月23日 <br>
 * 描述：放款报表业务
 */
@Service("loanInfoReportServer")
public class LoanInfoReportServerImpl  implements LoanInfoReportServerI {
	
	@Autowired
	private LoanInfoReportMapper loanInfoReportMapper;
	
	@Override
	public Pager<LoanInfoReport> findPage(Integer page, Integer rows,String custName,String bankPhoneNo,String conNo,String idNumber,String startDate,String endDate) {
		
		
		Map<String, Object> param = new HashMap<String, Object>();

		if (page == null || rows == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		rows = rows > 100 ? 50 : rows;

		param.put("pageIndex", page);
		param.put("pageSize", rows);
		if (!StringUtils.isBlank(custName)) {
			param.put("custName", "%" +custName + "%");
		}
		if (!StringUtils.isBlank(bankPhoneNo)) {
			param.put("bankPhoneNo", "%" +bankPhoneNo + "%");
		}
		if (!StringUtils.isBlank(conNo)) {
			param.put("conNo", "%" +conNo + "%");
		}
		
		if (!StringUtils.isBlank(idNumber)) {
			param.put("idNumber", "%" +idNumber + "%");
		}
		if (!StringUtils.isBlank(startDate)) {
			param.put("startDate",startDate);
		}
		if (!StringUtils.isBlank(endDate)) {
			param.put("endDate", endDate);
		}
		List<LoanInfoReport> loanInfo=loanInfoReportMapper.findPager(param);
		
		for (LoanInfoReport loanInfoReport : loanInfo) {
			String serviceFee=loanInfoReport.getServiceFee();
			if(serviceFee!=null&&serviceFee!=""){
				JSONObject json = JSONObject.parseObject(serviceFee);
				String service = json.getString("service");// 服务费
				BigDecimal services= new BigDecimal(service);
				loanInfoReport.setServiceFee(service);
			}
			
		}
		
		
		int count=loanInfoReportMapper.findPagerCount(param);
		return new Pager<LoanInfoReport>(loanInfo,count);
	}

	@Override
	public List<LoanInfoReport> findLoanInfoList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return loanInfoReportMapper.findLoanInfoList(param);
	}

	

}
