/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.audit.dao.TracingFlowMapper;
import com.hoomsun.csas.audit.model.vo.UserApplyVO;
import com.hoomsun.csas.audit.server.inter.TracingFlowServerI;

/**
 * 作者：liushuai <br>
 * 创建时间：2018年1月23日 <br>
 * 描述：
 */
@Service("tracingFlowServer")
public class TracingFlowServerImpl implements TracingFlowServerI {

	@Autowired
	private TracingFlowMapper tracingFlowMapper;
	
	@Override
	public Pager<UserApplyVO> findPager(Integer page, Integer rows, String custName, String idNumber, String loanId, String storeId) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		if (page == null || rows == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		rows = rows > 100 ? 100 : rows;
		param.put("pageIndex", page);
		param.put("pageSize", rows);

		if (!StringUtils.isBlank(custName)) {
			param.put("custName", "%" + custName + "%");
		}

		if (!StringUtils.isBlank(idNumber)) {
			param.put("idNumber", idNumber);
		}

		if (!StringUtils.isBlank(loanId)) {
			param.put("loanId", loanId);
		}
		
		if (!StringUtils.isBlank(storeId)) {
			param.put("storeId", storeId);
		}
		List<UserApplyVO> userApplyVOs = tracingFlowMapper.findPager(param);
		Integer total = tracingFlowMapper.findTracingFlowCount(param);
		return new Pager<UserApplyVO>(userApplyVOs, total);
	}

}
