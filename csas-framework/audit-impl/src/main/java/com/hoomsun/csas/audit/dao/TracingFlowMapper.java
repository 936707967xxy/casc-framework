/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.csas.audit.model.vo.UserApplyVO;

/**
 * 作者：liushuai <br>
 * 创建时间：2018年1月23日 <br>
 * 描述：
 */
public interface TracingFlowMapper {

	List<UserApplyVO> findPager(Map<String, Object> param);

	Integer findTracingFlowCount(Map<String, Object> param);

}
