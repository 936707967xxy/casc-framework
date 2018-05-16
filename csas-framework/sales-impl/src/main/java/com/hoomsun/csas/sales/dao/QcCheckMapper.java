/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.sales.api.model.QcCheck;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.vo.RollBackInfoVo;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年12月4日 <br>
 * 描述：
 */
public interface QcCheckMapper {
	
    int deleteByPrimaryKey(String qcId);

    int insert(QcCheck record);

    int insertSelective(QcCheck record);

    QcCheck selectByPrimaryKey(String qcId);

    int updateByPrimaryKeySelective(QcCheck record);

    int updateByPrimaryKey(QcCheck record);
	
	List<UserApply> findPager(Map<String, Object> param);
	
	int findPagerCount(Map<String, Object> param);
	
	//UserApply findById(String applyId);
	
	// 获取申请信息
	UserApply findApplyById(String applyId);
	
	// 获取质检复核的信息
	QcCheck findByApplyId(String applyId);
	
	List<RollBackInfoVo> selectRollBackInfo(String applyId);
	
	int deleteWhenWithDrow(@Param("applyId") String applyId, @Param("processId") String processId);
}
