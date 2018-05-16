/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.inter;

import java.util.List;

import com.hoomsun.common.model.Json;
import com.hoomsun.core.model.HxbReplaymentPlan;

/**
 * 作者：liusong <br>
 * 创建时间：2018年1月4日 <br>
 * 描述：
 */
public interface HxbReplaymentPlanServerI {
	
	Json deleteByApplyId(String applyId);
	
	Json createPlan(HxbReplaymentPlan plan);
	
	Json updatePlan(HxbReplaymentPlan plan);
	
	List<HxbReplaymentPlan> selectByApplyId(String applyId);
	
	HxbReplaymentPlan selectByApplyIdAndPhaseNumber(String applyId,Integer phaseNumber);

}
