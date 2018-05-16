package com.hoomsun.csas.audit.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.csas.audit.model.vo.UserApplyVO;

public interface LendFailMapper {

	Integer findFinalAuditCount(Map<String, Object> param);

	List<UserApplyVO> findPager(Map<String, Object> param);

}