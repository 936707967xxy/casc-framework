/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.act;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoomsun.risk.server.inter.ClaimAuthDataServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月25日 <br>
 * 描述：流程启动监听 主要认领数据
 */
@Component("startEventListener")
public class StartEventListener implements ExecutionListener {
	private static final long serialVersionUID = -2989801413830166726L;
	@Autowired
	private ClaimAuthDataServerI claimAuthDataServer;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String applyId = execution.getProcessBusinessKey();
		claimAuthDataServer.claimAuthData(applyId);
	}

}
