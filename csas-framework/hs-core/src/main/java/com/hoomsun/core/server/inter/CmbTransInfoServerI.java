package com.hoomsun.core.server.inter;

import java.util.List;
import java.util.Map;

import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.CmbTransInfo;

public interface CmbTransInfoServerI {

	int insertMapSelective(List<Map<String, Object>> list);
	
	Pager<CmbTransInfo> findPageData(Integer page, Integer rows, String rpyacc);
}
