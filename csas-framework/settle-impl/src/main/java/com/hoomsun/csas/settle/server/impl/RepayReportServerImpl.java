/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.settle.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.settle.dao.RepayReportMapper;
import com.hoomsun.csas.settle.model.vo.RepayReport;
import com.hoomsun.csas.settle.server.inter.RepayReportServerI;

/**
 * 作者：liming <br>
 * 创建时间：2018年1月16日 <br>
 * 描述：
 */
@Service("repayReportServer")
public class RepayReportServerImpl implements RepayReportServerI{
	
	@Autowired
	private RepayReportMapper repayReportMapper;

	@Override
	public Pager<RepayReport> findPage(Integer page, Integer rows,String castName,String cardNo,String conNo,String repayType,String startDate,String endDate) {
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
		if (!StringUtils.isBlank(castName)) {
			param.put("castName", "%" +castName + "%");
		}
		if (!StringUtils.isBlank(cardNo)) {
			param.put("cardNo", "%" +cardNo + "%");
		}
		if (!StringUtils.isBlank(conNo)) {
			param.put("conNo", "%" +conNo + "%");
		}
		if (!StringUtils.isBlank(repayType)) {
			param.put("repayType", repayType);
		}
		
		if (!StringUtils.isBlank(startDate)) {
			param.put("startDate",startDate);
		}
		if (!StringUtils.isBlank(endDate)) {
			param.put("endDate", endDate);
		}
		
		List<RepayReport> li=repayReportMapper.findPager(param);

		
		int count=repayReportMapper.findPagerCount(param);
		
		return new Pager<RepayReport>(li,count);
	}

	@Override
	public List<RepayReport> findRepayList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return repayReportMapper.findRepayList(param);
	}
	
	

}
