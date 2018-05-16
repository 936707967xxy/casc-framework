/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.inter;

import java.util.List;

import com.hoomsun.common.model.Json;
import com.hoomsun.core.model.SysRepaymentPlan;

/**
 * 作者：liusong <br>
 * 创建时间：2017年12月8日 <br>
 * 描述：
 */
public interface SysRepaymentPlanServerI {
	Json createPlan(SysRepaymentPlan sysRepaymentPlan);
	int deleteByApplyId(String applyId);
	Json updatePlan(SysRepaymentPlan sysRepaymentPlan);
	List<SysRepaymentPlan> findByApplyId(String applyId);
	int countFindByApplyId(String applyId);

    List<SysRepaymentPlan>  findPlanByApplyId(String applyId);
}
