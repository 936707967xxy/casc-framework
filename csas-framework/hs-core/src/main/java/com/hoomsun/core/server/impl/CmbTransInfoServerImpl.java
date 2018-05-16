package com.hoomsun.core.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.dao.CmbTransInfoMapper;
import com.hoomsun.core.model.CmbTransInfo;
import com.hoomsun.core.server.inter.CmbTransInfoServerI;

@Service("CmbTransInfoServerImpl")
public class CmbTransInfoServerImpl implements CmbTransInfoServerI{

	@Autowired
	private CmbTransInfoMapper cmbTransInfoMapper;
	
	@Override
	public int insertMapSelective(List<Map<String, Object>> list){
		int i=0;
		for(Map<String, Object> map :list){
			//判断是否已存在该条数据 
			CmbTransInfo  cmbTransInfo=cmbTransInfoMapper.selectByMap(map);
			if(cmbTransInfo==null){
				i+=cmbTransInfoMapper.insertMapSelective(map);
			}	
		}
		return i;
	}
	
	@Override
	public Pager<CmbTransInfo> findPageData(Integer page, Integer rows, String rpyacc){
		Map<String, Object> param = new HashMap<String, Object>();
		if (page == null || rows == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		rows = rows > 50 ? 50 : rows;
		
		param.put("pageIndex", page);
		param.put("pageSize", rows);

		if (!StringUtils.isBlank(rpyacc)) {
			param.put("rpyacc", rpyacc);
		}

		List<CmbTransInfo> version = cmbTransInfoMapper.findPageData(param);
		int total = cmbTransInfoMapper.findPageCount(param);
		return new Pager<CmbTransInfo>( version,total);
	}
}
