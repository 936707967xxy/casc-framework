/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.after;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.hoomsun.after.api.model.param.CGParam;
import com.hoomsun.after.api.model.table.PublicSave;
import com.hoomsun.after.api.model.vo.DeductServerResult;
import com.hoomsun.after.api.server.DeductCGServer;
import com.hoomsun.common.model.Json;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年4月28日 <br>
 * 描述：客户存公
 */

@Controller
public class DeductCGController {

	@Autowired
	private DeductCGServer deductCGServer;

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月28日 <br>
	 * 描述：查询存公数据
	 * 
	 * @param rpynam
	 * @param rpyacc
	 * @param naryur
	 * @param transactionDate
	 * @return
	 */
	@RequestMapping("/after/deductcg/cglist.do")
	public @ResponseBody List<PublicSave> getDeductList(String rpynam, String rpyacc, String naryur, String corporateBankAccount, Date transactionDate) {

		List<PublicSave> pulicSaves = deductCGServer.getDeductList(rpynam, rpyacc, naryur, corporateBankAccount, transactionDate);

		return pulicSaves;
	}

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月13日 <br>
	 * 描述： 正常月还
	 * 
	 * @return
	 */
	@RequestMapping("/after/deductcg/nomal.do")
	public @ResponseBody Json saveNomalDeduct(CGParam cGParam, HttpServletRequest req) {
		SessionRouter session = LoginUtil.getLoginSession(req);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();

		DeductServerResult dsr = deductCGServer.saveNomalDeduct(cGParam, applicationCastId, applicationCastName);

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
	@RequestMapping("/after/deductcg/overdue.do")
	public @ResponseBody Json saveOverdueDeduct(CGParam cGParam, HttpServletRequest req) {
		SessionRouter session = LoginUtil.getLoginSession(req);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();

		DeductServerResult dsr = deductCGServer.saveOverdueDeduct(cGParam, applicationCastId, applicationCastName);

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
	@RequestMapping("/after/deductcg/advance.do")
	public @ResponseBody Json saveAdvanceDeduct(CGParam cGParam, HttpServletRequest req) {

		SessionRouter session = LoginUtil.getLoginSession(req);
		String applicationCastId = session.getEmpWorkNum();
		String applicationCastName = session.getEmpName();

		DeductServerResult dsr = deductCGServer.saveAdvanceDeduct(cGParam, applicationCastId, applicationCastName);

		Json json = new Json();
		json.setSuccess(dsr.getSuccess());
		json.setMsg(dsr.getMsg());

		return json;
	}
	

}
