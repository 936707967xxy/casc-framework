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
import com.hoomsun.csas.settle.dao.DerateReportVoMapper;
import com.hoomsun.csas.settle.model.vo.DerateReportVo;
import com.hoomsun.csas.settle.server.inter.DerateReportVoServerI;

/**
 * 作者：liming <br>
 * 创建时间：2018年1月26日 <br>
 * 描述：减免报表
 */
@Service("derateReportVoServer")
public class DerateReportVoServerImpl implements DerateReportVoServerI{

	@Autowired
	private DerateReportVoMapper derateReportVoMapper;
	
	@Override
	public Pager<DerateReportVo> findAllData(Integer page, Integer rows, String castName, String cardNo, String conNo,String startDate,String endDate) {
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
		if (!StringUtils.isBlank(startDate)) {
			param.put("startDate",startDate);
		}
		if (!StringUtils.isBlank(endDate)) {
			param.put("endDate", endDate);
		}
		List<DerateReportVo> derateReportList=derateReportVoMapper.findPager(param);
		
		int count=derateReportVoMapper.findPagerCount(param);
		
		return new Pager<DerateReportVo>(derateReportList,count);
	}

	@Override
	public List<DerateReportVo> findDerateList(Map<String, Object> param) {
	
		return derateReportVoMapper.findDerateList(param);
	}

}
