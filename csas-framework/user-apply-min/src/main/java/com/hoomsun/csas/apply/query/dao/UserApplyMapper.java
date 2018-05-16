package com.hoomsun.csas.apply.query.dao;

import com.hoomsun.csas.apply.query.model.UserApply;

public interface UserApplyMapper {
	UserApply selectByPrimaryKey(String applyId);

	UserApply findApplyById(String applyId);
}