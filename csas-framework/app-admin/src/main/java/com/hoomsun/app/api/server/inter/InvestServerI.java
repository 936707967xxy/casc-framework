package com.hoomsun.app.api.server.inter;

import com.hoomsun.csas.sales.api.model.NameAuthentication;
import com.hoomsun.csas.sales.api.model.UserApply;

public interface InvestServerI {

	void   saveInvest(NameAuthentication nameAuth, String  Id, UserApply record);
}
