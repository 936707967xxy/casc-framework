/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import com.hoomsun.common.model.Json;
import com.hoomsun.csas.sales.api.model.UserPbccrcHtml;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月18日 <br>
 * 描述：征信电子版业务方法
 */
public interface UserPbccrcHtmlServerI {
	
	Json create(UserPbccrcHtml pbccrcHtml);
	
	UserPbccrcHtml findById(String id);
	
	UserPbccrcHtml findByApplyId(String applyId);
	
	UserPbccrcHtml findByCrId(String crId);
}
