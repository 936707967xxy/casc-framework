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
import com.hoomsun.after.api.model.param.NomalCustomerReq;
import com.hoomsun.after.api.model.vo.NomalCustomerResult;
import com.hoomsun.after.api.server.NomalCustomerServer;
import com.hoomsun.after.api.util.RequestUtil;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;

@Controller
public class NomalCustomerController {

	private static final Logger LogCvt = Logger.getLogger(NomalCustomerController.class);
	
	@Autowired
	private NomalCustomerServer customerServer;
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年3月13日 <br>
	 * 描述： 正常客户列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/customer/nomalCustomerService.do")
	public @ResponseBody Pager<NomalCustomerResult> execute(HttpServletRequest request){
		
		/**
		 * @RequestBody需要把所有请求参数作为json解析，
		 * 因此，不能包含key=value这样的写法在请求url中，所有的请求参数都是一个json
		 * 直接通过浏览器输入url时，@RequestBody获取不到json对象，
		 * 需要用java编程或者基于ajax的方法请求，将Content-Type设置为application/json
		 * NomalCustomerReq req = jsonObject.toJavaObject(NomalCustomerReq.class);
		 */
		NomalCustomerReq req = (NomalCustomerReq) RequestUtil.copyParam(NomalCustomerReq.class, request);
		Pager<NomalCustomerResult> pager=null;
		SessionRouter session = LoginUtil.getLoginSession(request);
		try {
			if(session!=null){
				if(req==null){
					req=new NomalCustomerReq();
				}
				req.setDalayFlag("0");//未逾期
				req.setSettleFlag("0");//未结清
				req.setDeptId(session.getDeptId());
				req.setEmpId(session.getEmpId());
				req.setManagerCastId(session.getEmpWorkNum());
				req.setLoanManagerCastId(session.getEmpWorkNum());
				req.setSalesDeptment(session.getStoreId());
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				List<NomalCustomerResult> pageData=customerServer.queryNomalCustomer(req);
				if(pageData!=null){
					req.setPage(-1);
					pager = new Pager<NomalCustomerResult>(pageData, customerServer.countNomalCustomer(req));
				}
				LogCvt.info("响应报文："+JSON.toJSONString(pager));
			}
			return pager;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("正常客户列表查询异常"+e.getMessage());
		}
		return pager;
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年3月14日 <br>
	 * 描述： 导出正常客户列表
	 * @param request
	 */
	@RequestMapping("/after/customer/downloadNomalCustomerService.do")
	public void downloadExecute(HttpServletRequest request,HttpServletResponse response){
		NomalCustomerReq req = (NomalCustomerReq) RequestUtil.copyParam(NomalCustomerReq.class, request);
		SessionRouter session = LoginUtil.getLoginSession(request);
		try {
			if(session!=null){
				if(req==null){
					req=new NomalCustomerReq();
				}
				
				req.setDalayFlag("0");//未逾期
				req.setSettleFlag("0");//未结清
				req.setDeptId(session.getDeptId());
				req.setEmpId(session.getEmpId());
				req.setManagerCastId(session.getEmpWorkNum());
				req.setLoanManagerCastId(session.getEmpWorkNum());
				req.setSalesDeptment(session.getStoreId());
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				req.setResponse(response);
				customerServer.downloadNomalCustomer(req);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("正常客户列表导出异常"+e.getMessage());
		}
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年3月28日 <br>
	 * 描述： 月还金额明细
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/deduct/ovedueAdvNomalDetail.do")
	public @ResponseBody Json executeDeduct(HttpServletRequest request){
		Json json=new Json();
		NomalCustomerReq req = (NomalCustomerReq) RequestUtil.copyParam(NomalCustomerReq.class, request);
		SessionRouter session = LoginUtil.getLoginSession(request);
		try {
			if(session!=null){
				if(req==null){
					req=new NomalCustomerReq();
				}
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				NomalCustomerResult pageData=customerServer.ovedueAdvNomalDetailDetails(req);
				json.setData(pageData);
				json.setSuccess(true);
				json.setMsg("查询成功");
				LogCvt.info("响应报文："+JSON.toJSONString(json));
				return json;
			}else{
				json.setSuccess(false);
				json.setMsg("请重新登录");
				return json;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("系统异常");
			LogCvt.error("正常客户列表查询异常"+e.getMessage());
			return json;
		}
	}
	
}
