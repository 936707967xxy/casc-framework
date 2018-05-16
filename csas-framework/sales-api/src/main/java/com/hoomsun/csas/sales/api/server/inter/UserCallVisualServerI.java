package com.hoomsun.csas.sales.api.server.inter;


import com.hoomsun.csas.sales.api.model.UserCallVisual;

public interface UserCallVisualServerI {

	UserCallVisual findByApply(String applyId);
}
