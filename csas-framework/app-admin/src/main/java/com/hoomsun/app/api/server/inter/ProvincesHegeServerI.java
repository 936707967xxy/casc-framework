package com.hoomsun.app.api.server.inter;

import java.util.List;

import com.hoomsun.app.api.model.ProvincesHege;
import com.hoomsun.common.model.ComboTree;

public interface ProvincesHegeServerI {

	 List<ProvincesHege>  selectAllData();
	 
	 List<ProvincesHege>  selectStoreCitysData();
	 
	 List<ComboTree> findComboTree();
}
