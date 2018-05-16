/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.sales;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.csas.sales.api.model.vo.SalesmanReportVo;
import com.hoomsun.csas.sales.api.server.inter.SalesChartServerI;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年12月21日 <br>
 * 描述：
 */
@Controller
public class SalesChartController {
	
	@Autowired
	private SalesChartServerI chartServer;

	//事业部
	@RequestMapping("/sys/saleschart/bigarea.do")
	@ResponseBody
	public Pager<SalesmanReportVo> findBigarea(HttpServletRequest request, String startDate, String endDate) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if( session==null){
			return null;
		}
		String empId=session.getEmpId();
		String deptId=session.getDeptId();
		return chartServer.findBigAreaChartInfo(empId, deptId, startDate, endDate);
//		return chartServer.findBigAreaChartInfo("639B4061B58C4127AC3C1DFF4F6029CA"); // 530
	}
	
	//大区
	@RequestMapping("/sys/saleschart/area.do")
	@ResponseBody
	public Pager<SalesmanReportVo> findArea(HttpServletRequest request, String startDate, String endDate) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if( session==null){
			return null;
		}
		String empId = session.getEmpId();
		String deptId=session.getDeptId();
		return	chartServer.findAreaChartInfo(empId, deptId, startDate, endDate);
//		return chartServer.findAreaChartInfo("61C3B5CA3C59415F87333D66CA853334", "普惠一区"); // 533
	}
	
	//分部经理--查询门店业绩
	@RequestMapping("/sys/saleschart/findcity.do")
	@ResponseBody
	public Pager<SalesmanReportVo> findcity(HttpServletRequest request, String startDate, String endDate) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if( session==null){
			return null;
		}
		String empId = session.getEmpId();
		String deptId=session.getDeptId();
		return chartServer.findcity(empId, deptId, startDate, endDate);
//			return chartServer.findcity("427474CC5286450CAA3AC399855D2698"); // 688
	}
	
	//门店经理--查询团队业绩
	@RequestMapping("/sys/saleschart/findstore.do")
	@ResponseBody
	public Pager<SalesmanReportVo> findStore(HttpServletRequest request, String startDate, String endDate) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if( session==null){
			return null;
		}
		String empId=session.getEmpId();
		String deptId=session.getDeptId();
		return chartServer.findStore(empId,deptId, startDate, endDate);
//		return chartServer.findStore("AA082505E18048F58DCDFE091429A843"); //2641
	}
	
	
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月22日 <br>
	 * 描述： 团队经理--查询客户经理业绩
	 * @param request
	 * @return
	 */
	@RequestMapping("/sys/saleschart/findteam.do")
	@ResponseBody
	public Pager<SalesmanReportVo> findteam(HttpServletRequest request, String startDate, String endDate) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if( session==null){
			return null;
		}
		String empId=session.getEmpId();
		String deptId=session.getDeptId();
		return chartServer.findTeam(empId,deptId, startDate, endDate); //2644
	}
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年1月12日 <br>
	 * 描述： 查询个人业绩
	 * @param request
	 * @return
	 */
	@RequestMapping("/sys/saleschart/findperson.do")
	@ResponseBody
	public Pager<SalesmanReportVo> findPerson(HttpServletRequest request, String startDate, String endDate) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if( session==null){
			return null;
		}
		
		String empId=session.getEmpId();
		String deptId=session.getDeptId();
		return chartServer.findPerson(empId, deptId, startDate, endDate); //2644
	}
}
