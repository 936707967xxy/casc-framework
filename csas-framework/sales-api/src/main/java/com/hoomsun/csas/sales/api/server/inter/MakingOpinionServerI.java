/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import com.hoomsun.common.model.Json;
import com.hoomsun.csas.sales.api.model.MakingOpinion;

/**
 * 作者：liusong <br>
 * 创建时间：2017年12月8日 <br>
 * 描述：
 */
public interface MakingOpinionServerI {
	Json create(MakingOpinion makingOpinion);
	String selectByApplyId(String applyId);
	String selectByRollBackOpinion(String applyId);
}
