/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.sales;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.csas.sales.api.model.vo.UpcomingTaskVo;
import com.hoomsun.csas.sales.api.server.inter.UpcomingTaskServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月22日 <br>
 * 描述：待办任务
 */
@Controller
public class UpcomingTaskController {

	@Autowired
	private UpcomingTaskServerI upcomingTaskServer;

	@RequestMapping("/sys/upcomingtask/page.do")
	@ResponseBody
	public List<UpcomingTaskVo> findPage(HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		String empId = session.getEmpId();
		String storeId = session.getStoreId();
		String deptId = session.getDeptId();
		List<String> menuRouter = session.getMenuRouters();
		if (!menuRouter.contains("ApplyListView") && !menuRouter.contains("QcCheck") && !menuRouter.contains("MakingList")) {
			return new ArrayList<UpcomingTaskVo>();
		}else {
			return upcomingTaskServer.findRunTask(empId, storeId, deptId);
		}
	}

	// 签收
	@RequestMapping("/sys/upcomingtask/claim.do")
	@ResponseBody
	public Json claimTask(String applyId, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		return upcomingTaskServer.claimTask(applyId, session);
	}

	// 验证是否已经签收
	@RequestMapping("/sys/upcomingtask/checkclaim.do")
	@ResponseBody
	public Json checkClaimTask(String applyId, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		return upcomingTaskServer.checkClaim(applyId, session);
	}
}
