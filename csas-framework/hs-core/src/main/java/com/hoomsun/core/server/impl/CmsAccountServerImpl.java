package com.hoomsun.core.server.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.core.dao.CmsAccountMapper;
import com.hoomsun.core.model.CmsAccount;
import com.hoomsun.core.server.inter.CmsAccountServerI;

@Service("CmsAccountServerI")
public class CmsAccountServerImpl implements CmsAccountServerI{

	@Autowired
	private CmsAccountMapper  cmsAccountMapper;
	
	@Autowired
	public List<CmsAccount>  selectAllData(){
		return cmsAccountMapper.selectAllData();
	}
}
