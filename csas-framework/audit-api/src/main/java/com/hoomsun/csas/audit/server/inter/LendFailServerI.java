/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.inter;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.audit.model.vo.UserApplyVO;

/**
 * 作者：liusong <br>
 * 创建时间：2017年9月14日 <br>
 * 描述：
 */
public interface LendFailServerI {
	Pager<UserApplyVO> findPager(Integer page, Integer rows, String custName, String idNumber, String loanId);

	Json lendFail(String applyId);
}
