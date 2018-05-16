package com.hoomsun.csas.settle.server.inter;

import com.hoomsun.common.model.Json;

public interface AfterLoanBalServerI {

	Json PushAfterLoanBal(String applyId);
	
}
