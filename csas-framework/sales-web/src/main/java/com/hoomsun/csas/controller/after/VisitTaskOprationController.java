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
import com.hoomsun.after.api.model.param.VistTaskReq;
import com.hoomsun.after.api.model.vo.CustomerTaskAllocationResult;
import com.hoomsun.after.api.model.vo.VisitRecordResult;
import com.hoomsun.after.api.model.vo.VistTaskResult;
import com.hoomsun.after.api.server.VisitTaskOprationServer;
import com.hoomsun.after.api.util.RequestUtil;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年5月7日 <br>
 * 描述：外访申请分配以及列表
 */
@Controller
public class VisitTaskOprationController {

	private static final Logger LogCvt = Logger.getLogger(VisitTaskOprationController.class);
	
	@Autowired
	private VisitTaskOprationServer taskOprationServer;
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年5月7日 <br>
	 * 描述： 外访申请列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/visit/queryVisitTaskService.do")
	public @ResponseBody Pager<VistTaskResult> execute(HttpServletRequest request){
		VistTaskReq req = (VistTaskReq) RequestUtil.copyParam(VistTaskReq.class, request);
		Pager<VistTaskResult> pager=null;
		SessionRouter session = LoginUtil.getLoginSession(request);
		try {
			if(session!=null){
				if(req==null){
					req=new VistTaskReq();
				}
				req.setOutboundStatus("0");//申请通过
				req.setOutboundId(session.getEmpWorkNum());
				req.setDeptId(session.getDeptId());
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				List<VistTaskResult> pageData=taskOprationServer.queryVisitTaskList(req);
				if(pageData!=null){
					req.setPage(-1);
					pager = new Pager<VistTaskResult>(pageData, taskOprationServer.countVisitTask(req));
				}
				LogCvt.info("响应报文："+JSON.toJSONString(pager));
			}
			return pager;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("外访申请列表查询异常"+e.getMessage());
		}
		return pager;
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年5月7日 <br>
	 * 描述： 导出外访申请列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/visit/downloanVisitTaskService.do")
	public @ResponseBody Pager<VistTaskResult> downloadVisitTask(HttpServletRequest request,
			HttpServletResponse response){
		VistTaskReq req = (VistTaskReq) RequestUtil.copyParam(VistTaskReq.class, request);
		Pager<VistTaskResult> pager=null;
		SessionRouter session = LoginUtil.getLoginSession(request);
		try {
			if(session!=null){
				if(req==null){
					req=new VistTaskReq();
				}
				req.setOutboundStatus("0");//申请通过
				req.setOutboundId(session.getEmpWorkNum());
				req.setDeptId(session.getDeptId());
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				req.setResponse(response);
				taskOprationServer.downloadVisitTask(req);
			}
			return pager;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("外访申请列表导出异常"+e.getMessage());
		}
		return pager;
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年5月7日 <br>
	 * 描述： 外访分配详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/visit/queryVisitTaskDetailsService.do")
	public @ResponseBody Pager<CustomerTaskAllocationResult> queryVisitTaskDetails(HttpServletRequest request){
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
				List<CustomerTaskAllocationResult> pageData=taskOprationServer.querySysEmployee(req);
				pager = new Pager<CustomerTaskAllocationResult>(pageData,pageData.size());
			  }
			LogCvt.info("响应报文："+JSON.toJSONString(pager));
			return pager;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("外访申请分配列表详情查询异常"+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年5月7日 <br>
	 * 描述： 外访申请资源分配
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/visit/visitTaskService.do")
	public @ResponseBody Json updateVisitTask(HttpServletRequest request){
		VistTaskReq req = (VistTaskReq) RequestUtil.copyParam(VistTaskReq.class, request);
		SessionRouter session = LoginUtil.getLoginSession(request);
		Json json=null;
		try {
			  if(session!=null){
				if(req==null){
					req=new VistTaskReq();
				}
				req.setOprationEmpName(session.getEmpName());
				req.setOprationEmpWorkNum(session.getEmpWorkNum());
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				json=taskOprationServer.updateVisiTask(req);
			  }
			return json;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("外访申请分配列表详情查询异常"+e.getMessage());
			json=new Json();
			json.setData(null);
			json.setMsg("外访申请分配列表详情查询异常");
			json.setSuccess(false);
		}
		return null;
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年5月7日 <br>
	 * 描述： 手动添加外访记录信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/visit/addVisitRecordService.do")
	public @ResponseBody Json addVisitRecord(HttpServletRequest request){
		VisitRecordResult req = (VisitRecordResult) RequestUtil.copyParam(VisitRecordResult.class, request);
		SessionRouter session = LoginUtil.getLoginSession(request);
		Json json=null;
		try {
			  if(session!=null){
				if(req==null){
					req=new VisitRecordResult();
				}
				req.setHostIp(request.getRemoteAddr());
				req.setOprationId(session.getEmpWorkNum());
				req.setOprationName(session.getEmpName());
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				json=taskOprationServer.addOutBoundLog(req);
			  }
			return json;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("手动添加外访记录信息异常"+e.getMessage());
			json=new Json();
			json.setMsg("手动添加外访记录信息异常");
			json.setSuccess(false);
		}
		return null;
	}
}
