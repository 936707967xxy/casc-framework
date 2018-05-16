/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.after;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.hoomsun.after.api.model.param.CustomerDeductReq;
import com.hoomsun.after.api.model.vo.CustomerDeductResult;
import com.hoomsun.after.api.server.CustomerDeductServer;
import com.hoomsun.after.api.util.RequestUtil;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;

/**
 * 作者：Administrator <br>
 * 创建时间：2021年3月16日 <br>
 * 描述：划扣记录查询
 */
@Controller
public class CustomerDeductController {
	
	private static final Logger LogCvt = Logger.getLogger(CustomerDeductController.class);
	
	@Autowired
	private CustomerDeductServer deductServer;

	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年3月21日 <br>
	 * 描述： 划扣记录查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/deduct/customerDeductService.do")
	public @ResponseBody Pager<CustomerDeductResult> execute(HttpServletRequest request){
		
		CustomerDeductReq req = (CustomerDeductReq) RequestUtil.copyParam(CustomerDeductReq.class, request);
		Pager<CustomerDeductResult> pager=null;
		SessionRouter session = LoginUtil.getLoginSession(request);
		long start=System.currentTimeMillis();
		LogCvt.info("客户划扣记录查询开始......");
		try {
			  if(session!=null){
				if(req==null){
					req=new CustomerDeductReq();
				}
				req.setEmpId(session.getEmpId());
				req.setDeptId(session.getDeptId());
				req.setManagerCastId(session.getEmpWorkNum());
				req.setLoanManagerCastId(session.getEmpWorkNum());
				req.setSalesDeptment(session.getStoreId());
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				List<CustomerDeductResult> pageData=deductServer.queryCustomerDeduct(req);
				if(pageData!=null){
					req.setPage(-1);
					pager = new Pager<CustomerDeductResult>(pageData, deductServer.countCustomerDeduct(req));
				}
			  }
			  long end=System.currentTimeMillis();
			  LogCvt.info("客户划扣记录查询结束......耗时："+(end-start)+"ms");
			  LogCvt.info("响应报文："+JSON.toJSONString(pager));
			return pager;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("客户划扣记录列表查询异常"+e.getMessage());
		}
		return pager;
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年3月21日 <br> 
	 * 描述： 划扣记录导出
	 * @param request
	 * @param response
	 */
	@RequestMapping("/after/deduct/downloadCusDeductService.do")
	public void downloadCustomerDeduct(HttpServletRequest request,HttpServletResponse response){
		CustomerDeductReq req = (CustomerDeductReq) RequestUtil.copyParam(CustomerDeductReq.class, request);
		SessionRouter session = LoginUtil.getLoginSession(request);
		try {
			  if(session!=null){
				if(req==null){
					req=new CustomerDeductReq();
				}
				req.setEmpId(session.getEmpId());
				req.setDeptId(session.getDeptId());
				req.setManagerCastId(session.getEmpWorkNum());
				req.setLoanManagerCastId(session.getEmpWorkNum());
				req.setSalesDeptment(session.getStoreId());
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				req.setResponse(response);
				deductServer.downloadCustomerDeduct(req);
			  }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("客户划扣记录列表导出异常"+e.getMessage());
		}
	}
}
