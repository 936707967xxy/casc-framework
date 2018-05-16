/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.after;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.after.api.model.vo.DeductServerResult;
import com.hoomsun.after.api.server.DeductBFServer;
import com.hoomsun.common.model.Json;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;

/**
 * 作者：金世强 <br>
 * 创建时间：2018年3月13日 <br>
 * 描述：宝付划扣
 */
@Controller
public class DeductBFController {

	@Autowired
	private DeductBFServer deductBfServer;

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月13日 <br>
	 * 描述： 正常月还
	 * 
	 * @return
	 */
	@RequestMapping("/after/deductbf/nomal.do")
	public @ResponseBody Json saveNomalDeduct(String loanId, Integer stream, BigDecimal deductMoney, HttpServletRequest req) {
		SessionRouter session = LoginUtil.getLoginSession(req);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();
		// 调用划扣接口
		String path = req.getSession().getServletContext().getRealPath("/WEB-INF/classes/bf");// 宝付相关文件路径

		DeductServerResult dsr = deductBfServer.saveNomalDeduct(loanId, stream, deductMoney, path, applicationCastId, applicationCastName);

		Json json = new Json();
		json.setSuccess(dsr.getSuccess());
		json.setMsg(dsr.getMsg());
		return json;
	}

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月13日 <br>
	 * 描述：逾期月还
	 * 
	 * @return
	 */
	@RequestMapping("/after/deductbf/overdue.do")
	public @ResponseBody Json saveOverdueDeduct(String loanId, Integer stream, BigDecimal deductMoney, HttpServletRequest req) {
		SessionRouter session = LoginUtil.getLoginSession(req);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();
		// 调用划扣接口
		String path = req.getSession().getServletContext().getRealPath("/WEB-INF/classes/bf");// 宝付相关文件路径

		DeductServerResult dsr = deductBfServer.saveOverdueDeduct(loanId, stream, deductMoney, path, applicationCastId, applicationCastName);

		Json json = new Json();
		json.setSuccess(dsr.getSuccess());
		json.setMsg(dsr.getMsg());
		return json;
	}

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月13日 <br>
	 * 描述：提前还款
	 * 
	 * @return
	 */
	@RequestMapping("/after/deductbf/advance.do")
	public @ResponseBody Json saveAdvanceDeduct(String loanId, Integer stream, BigDecimal deductMoney, HttpServletRequest req) {

		SessionRouter session = LoginUtil.getLoginSession(req);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();
		// 调用划扣接口
		String path = req.getSession().getServletContext().getRealPath("/WEB-INF/classes/bf");// 宝付相关文件路径

		DeductServerResult dsr = deductBfServer.saveAdvanceDeduct(loanId, stream, deductMoney, path, applicationCastId, applicationCastName);

		Json json = new Json();
		json.setSuccess(dsr.getSuccess());
		json.setMsg(dsr.getMsg());

		return json;
	}

}
