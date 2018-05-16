/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.after;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hoomsun.after.api.model.param.CustomerOverdueReq;
import com.hoomsun.after.api.model.vo.CustomerOverdueResult;
import com.hoomsun.after.api.server.CustomerOverdueServer;
import com.hoomsun.after.api.util.RequestUtil;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月21日 <br>
 * 描述：客户减免
 */
@Controller
public class CustomerOverdueController {
	
	private static final Logger LogCvt = Logger.getLogger(CustomerOverdueController.class);

	@Autowired
	private CustomerOverdueServer overdueServer;
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年3月21日 <br>
	 * 描述： 客户减免信息查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/overdue/customerOverdueService.do")
	public @ResponseBody Pager<CustomerOverdueResult> execute(HttpServletRequest request){
		CustomerOverdueReq req = (CustomerOverdueReq) RequestUtil.copyParam(CustomerOverdueReq.class, request);
		Pager<CustomerOverdueResult> pager=null;
		SessionRouter session = LoginUtil.getLoginSession(request);
		long start=System.currentTimeMillis();
		LogCvt.info("客户减免信息查询开始......");
		try {
			  if(session!=null){
				if(req==null){
					req=new CustomerOverdueReq();
				}
				req.setManagerCastId(session.getEmpWorkNum());
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				List<CustomerOverdueResult> pageData=overdueServer.queryCustomerOverdue(req);
				if(pageData!=null){
					req.setPage(-1);
					pager = new Pager<CustomerOverdueResult>(pageData, overdueServer.countCustomerOverdue(req));
				}
			  }
			  long end=System.currentTimeMillis();
			  LogCvt.info("减免信息查询结束......耗时："+(end-start)+"ms");
			  LogCvt.info("响应报文："+JSON.toJSONString(pager));
			return pager;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("客户减免信息查询异常"+e.getMessage());
		}
		return pager;
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年3月21日 <br>
	 * 描述： 客户减免信息导出
	 * @param request
	 * @param response
	 */
	@RequestMapping("/after/overdue/downloadCusOverdueService.do")
	public void downloadCustomerDeduct(HttpServletRequest request,HttpServletResponse response){
		CustomerOverdueReq req = (CustomerOverdueReq) RequestUtil.copyParam(CustomerOverdueReq.class, request);
		SessionRouter session = LoginUtil.getLoginSession(request);
		try {
			  if(session!=null){
				if(req==null){
					req=new CustomerOverdueReq();
				}
				req.setManagerCastId(session.getEmpWorkNum());
				req.setResponse(response);
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				overdueServer.downloadCustomerOverdue(req);
			  }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("减免信息导出异常"+e.getMessage());
		}
	}
	
	@Deprecated
	@RequestMapping("/after/overdue/updateOverdueService.do")
	public void updateOverdue(HttpServletRequest request,HttpServletResponse response){
		CustomerOverdueReq req = (CustomerOverdueReq) RequestUtil.copyParam(CustomerOverdueReq.class, request);
		try {
				if(req==null){
					req=new CustomerOverdueReq();
				}
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				overdueServer.updateOverdueTest(req);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("减免信息导出异常"+e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
