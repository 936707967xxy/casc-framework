/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.inter;

import com.hoomsun.common.model.Json;

/**
 * 作者：liusong <br>
 * 创建时间：2018年2月2日 <br>
 * 描述：
 */
public interface LendImportServerI {
	//不带还款明细的推送
	Json lendImport(String applyId);
	//带还款明细的推送
	Json lendImportReplaymentPlan(String applyId);
			

}
