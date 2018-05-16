package com.hoomsun.app.api.server.inter;

import com.hoomsun.app.api.model.CarrearInfo;

public interface CarrearInfoServerI {

	 int insertSelective(CarrearInfo record);
	 
	 CarrearInfo selectByfkId(String fkId);
	    
	 int updateByfkId(CarrearInfo record);
	 
}
