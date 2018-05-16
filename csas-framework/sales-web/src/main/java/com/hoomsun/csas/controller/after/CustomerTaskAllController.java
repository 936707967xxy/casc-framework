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
import com.hoomsun.after.api.model.param.CustomerTaskAllocationReq;
import com.hoomsun.after.api.model.vo.CustomerTaskAllocationResult;
import com.hoomsun.after.api.server.CustomerTaskAllocationServer;
import com.hoomsun.after.api.util.RequestUtil;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月14日 <br>
 * 描述：客户任务分配
 */

@Controller
public class CustomerTaskAllController {
	
	private static final Logger LogCvt = Logger.getLogger(CustomerTaskAllController.class);

	@Autowired
	private CustomerTaskAllocationServer allocationServer;
	
	
	@RequestMapping("/after/task/customerTaskAllService.do")
	public @ResponseBody Pager<CustomerTaskAllocationResult> execute(HttpServletRequest request){
		
		CustomerTaskAllocationReq req = (CustomerTaskAllocationReq) RequestUtil.copyParam(CustomerTaskAllocationReq.class, request);
		Pager<CustomerTaskAllocationResult> pager=null;
		SessionRouter session = LoginUtil.getLoginSession(request);
		try {
			  if(session!=null){
				if(req==null){
					req=new CustomerTaskAllocationReq();
				}
				req.setDeptId(session.getDeptId());
				req.setManagerCastId(session.getEmpWorkNum());
				req.setStoreId(session.getStoreId());
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				List<CustomerTaskAllocationResult> pageData=allocationServer.queryCustomerTask(req);
				if(pageData!=null){
					req.setPage(-1);
					pager = new Pager<CustomerTaskAllocationResult>(pageData, allocationServer.countCustomerTask(req));
				}
			  }
			LogCvt.info("响应报文："+JSON.toJSONString(pager));
			return pager;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("客户任务分配列表查询异常"+e.getMessage());
		}
		return pager;
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年3月14日 <br>
	 * 描述： 客户任务分配列表导出
	 * @param request
	 */
	@RequestMapping("/after/task/downloadCustomerTaskAllService.do")
	public  void downloadExecute(HttpServletRequest request,HttpServletResponse response){
		CustomerTaskAllocationReq req = (CustomerTaskAllocationReq) RequestUtil.copyParam(CustomerTaskAllocationReq.class, request);
		SessionRouter session = LoginUtil.getLoginSession(request);
		try {
			if(session!=null){
				if(req==null){
					req=new CustomerTaskAllocationReq();
				}
				req.setDeptId(session.getDeptId());
				req.setManagerCastId(session.getEmpWorkNum());
				req.setStoreId(session.getStoreId());
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				req.setResponse(response);
				allocationServer.downloadCustomerTask(req);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("客户任务分配列表导出异常"+e.getMessage());
		}
	}

	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月4日 <br>
	 * 描述： 任务分配对象列表信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/task/customerTaskDetailsService.do")
	public @ResponseBody Pager<CustomerTaskAllocationResult> queryCustomerTaskDetails(HttpServletRequest request){
		CustomerTaskAllocationReq req = (CustomerTaskAllocationReq) RequestUtil.copyParam(CustomerTaskAllocationReq.class, request);
		Pager<CustomerTaskAllocationResult> pager=null;
		SessionRouter session = LoginUtil.getLoginSession(request);
		try {
			  if(session!=null){
				if(req==null){
					req=new CustomerTaskAllocationReq();
				}
				req.setEmpId(session.getEmpId());
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				List<CustomerTaskAllocationResult> pageData=allocationServer.querySysEmployee(req);
				pager = new Pager<CustomerTaskAllocationResult>(pageData,pageData.size());
			  }
			LogCvt.info("响应报文："+JSON.toJSONString(pager));
			return pager;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("任务分配列表详情查询异常"+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月20日 <br>
	 * 描述： 确认委托
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/task/customerTaskOprationService.do")
	public  @ResponseBody Json updateCustomerTask(HttpServletRequest request){
		CustomerTaskAllocationReq req = (CustomerTaskAllocationReq) RequestUtil.copyParam(CustomerTaskAllocationReq.class, request);
		Json json=new Json();
		SessionRouter session = LoginUtil.getLoginSession(request);
		try {
			  if(session!=null){
				if(req==null){
					req=new CustomerTaskAllocationReq();
				}
				req.setDeptId(session.getDeptId());
				req.setLoanManagerCastId(session.getEmpWorkNum());
				req.setLoanManagerCast(session.getEmpName());
				req.setRoleName(session.getRoles().get(0).getRoleName());
				req.setStoreId(session.getStoreId());
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				json=allocationServer.updateCustomerTask(req);
			  }
			LogCvt.info("响应报文："+JSON.toJSONString(json));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			json.setSuccess(false);
			json.setMsg("系统异常");
			e.printStackTrace();
			LogCvt.error("任务分配异常"+e.getMessage());
		}
		return json;
	}
}
