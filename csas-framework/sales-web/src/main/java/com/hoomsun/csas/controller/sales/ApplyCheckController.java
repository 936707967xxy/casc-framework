package com.hoomsun.csas.controller.sales;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.server.inter.UserApplyServerI;

@Controller
public class ApplyCheckController {

	@Autowired
	private UserApplyServerI userApplyServer;

	@RequestMapping("/sys/apply/track.do")
	@ResponseBody
	public Pager<UserApply> selectApplyinfo(HttpServletRequest request, Integer page, Integer rows, String custName, String loanId, String idNumber, String orgId, String custMobile) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		Pager<UserApply> pager = userApplyServer.selectApplyPage(page, rows, custName, loanId, idNumber, custMobile, orgId, sessionRouter);
		return pager;

	}

}
