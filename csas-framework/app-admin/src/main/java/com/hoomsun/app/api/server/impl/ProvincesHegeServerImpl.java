package com.hoomsun.app.api.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.app.api.dao.ProvincesHegeMapper;
import com.hoomsun.app.api.model.ProvincesHege;
import com.hoomsun.app.api.server.inter.ProvincesHegeServerI;
import com.hoomsun.common.model.ComboTree;
@Service("ProvincesHegeServer")
public class ProvincesHegeServerImpl implements ProvincesHegeServerI{

	@Autowired
	private ProvincesHegeMapper  provincesHegeMapper;
	
	@Override
	public List<ProvincesHege>  selectAllData(){
		 return provincesHegeMapper.selectAllData();
	}

	@Override
	public List<ProvincesHege> selectStoreCitysData() {
		return provincesHegeMapper.selectStoreCitysData();
	}
	
	@Override
	public List<ComboTree> findComboTree() {
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		comboTrees.add(new ComboTree("-1", "", "请选择"));
		List<ProvincesHege> provinces = selectAllData();
		if (provinces != null && provinces.size() > 0) {
			for (ProvincesHege pro : provinces) {
				ComboTree ct;
				ct = new ComboTree(pro.getProvinceid(), pro.getProvince());
				comboTrees.add(ct);
			}
		}
		return comboTrees;
	}
	
}
