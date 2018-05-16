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
import com.hoomsun.after.api.model.param.SettleCustomerReq;
import com.hoomsun.after.api.model.vo.LoanBookCustomerResult;
import com.hoomsun.after.api.model.vo.SettleCustomerResult;
import com.hoomsun.after.api.server.SettleCustomerServer;
import com.hoomsun.after.api.util.RequestUtil;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月29日 <br>
 * 描述：结清客户列表
 */
@Controller
public class SettleCustomerController {

	private static final Logger LogCvt = Logger.getLogger(SettleCustomerController.class);
	
	@Autowired
	private SettleCustomerServer settlecustomerServer;
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年3月29日 <br>
	 * 描述： 结清客户列表查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/settle/settleCustomerService.do")
	public @ResponseBody Pager<SettleCustomerResult> execute(HttpServletRequest request){
		SettleCustomerReq req = (SettleCustomerReq) RequestUtil.copyParam(SettleCustomerReq.class, request);

		Pager<SettleCustomerResult> pager=null;
		SessionRouter session = LoginUtil.getLoginSession(request);
		try{
			if(session!=null){
				if(req==null){
					req=new SettleCustomerReq();
					
				}
				req.setSettleFlag("1");//结清切未逾期
				req.setDalayFlag("0");//未逾期
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				List<SettleCustomerResult>pageData=settlecustomerServer.querySettleCustomer(req);
				if(pageData!=null){
					req.setPage(-1);
					pager = new Pager<SettleCustomerResult>(pageData, settlecustomerServer.countSettleCustomer(req));
				}
				LogCvt.info("响应报文："+JSON.toJSONString(pager));
				return pager;
			}
		}catch(Exception e){
			LogCvt.error("系统异常"+e.getMessage());
		}
		return pager;
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年3月29日 <br>
	 * 描述： 导出结清客户列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/after/settle/downloadSettleCustomerService.do")
	public void downloadSettleCustomer(HttpServletRequest request,HttpServletResponse response){
		SettleCustomerReq req = (SettleCustomerReq) RequestUtil.copyParam(SettleCustomerReq.class, request);
		SessionRouter session = LoginUtil.getLoginSession(request);
		try {
			if(session!=null){
				if(req==null){
					req=new SettleCustomerReq();
				}
				req.setSettleFlag("1");
				req.setDalayFlag("0");//未逾期
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				req.setResponse(response);
				settlecustomerServer.downloadSettleCustomer(req);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("结清客户列表导出异常"+e.getMessage());
		}
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年3月29日 <br>
	 * 描述： 结清客户列表--->客户还款记录查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/settle/loanBookCustomerService.do")
	public @ResponseBody Json queryLoanBookCustomer(HttpServletRequest request){
		SettleCustomerReq req = (SettleCustomerReq) RequestUtil.copyParam(SettleCustomerReq.class, request);
		Json json=new Json();
		SessionRouter session = LoginUtil.getLoginSession(request);
		try{
			if(session!=null){
				if(req==null){
					req=new SettleCustomerReq();
				}
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				List<LoanBookCustomerResult>pageData=settlecustomerServer.queryLoanBookCustomerDetail(req);
				json.setData(pageData);
				json.setSuccess(true);
				json.setMsg("查询成功");
				LogCvt.info("响应报文："+JSON.toJSONString(pageData));
				return json;
			}
		}catch(Exception e){
			json.setSuccess(false);
			json.setMsg("查询异常");
			LogCvt.error("系统异常"+e.getMessage());
			return json;
		}
		return json;
	}
}
