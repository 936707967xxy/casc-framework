package com.hoomsun.csas.audit.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.csas.audit.model.ReviewAudit;
import com.hoomsun.csas.audit.model.vo.UserApplyVO;

public interface ReviewAuditMapper {
    int deleteByPrimaryKey(String reviewId);

    int insert(ReviewAudit record);

    int insertSelective(ReviewAudit record);

    ReviewAudit selectByPrimaryKey(String reviewId);

    int updateByPrimaryKeySelective(ReviewAudit record);

    int updateByPrimaryKey(ReviewAudit record);

	List<UserApplyVO> findPager(Map<String, Object> param);
	 
	Integer findReviewAuditCount(Map<String, Object> param);
}