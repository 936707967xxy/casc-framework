package com.hoomsun.app.api.server.inter;

import com.hoomsun.app.api.model.Borrower;


public interface BorrowerServerI {

	 int insertSelective(Borrower record);
	 
	 int updateByPrimaryKeySelective(Borrower record);
	 
	 Borrower selectByFkid(String fkId);
	 
	 int  updateByFkid(Borrower record);
}
