package com.hoomsun.app.api.server.inter;

import com.hoomsun.app.api.model.hsCall;

public interface hsCallServerI {

	hsCall selectByPrimaryKey(String id);
}
