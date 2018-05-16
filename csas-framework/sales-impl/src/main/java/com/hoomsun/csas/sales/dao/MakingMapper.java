/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.csas.sales.api.model.vo.MakingVo;

/**
 * 作者：liusong <br>
 * 创建时间：2017年12月4日 <br>
 * 描述：协议拟制查询数据列表
 */
public interface MakingMapper {
	List<MakingVo> findPage(Map<String, Object> record);

	int pageCount(Map<String, Object> param);

	MakingVo selectByApplyId(String applyId);
}
