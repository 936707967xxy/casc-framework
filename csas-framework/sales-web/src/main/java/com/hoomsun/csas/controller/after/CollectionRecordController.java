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
import com.hoomsun.after.api.model.param.CollectionRecordReq;
import com.hoomsun.after.api.model.table.Review;
import com.hoomsun.after.api.model.vo.CollectingReceivingCallResult;
import com.hoomsun.after.api.model.vo.CollectionRecordResult;
import com.hoomsun.after.api.model.vo.CustomerCollectionRemindingResult;
import com.hoomsun.after.api.server.CollectionRecordServer;
import com.hoomsun.after.api.util.RequestUtil;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月13日 <br>
 * 描述：催收任务列表
 */
@Controller
public class CollectionRecordController {

	private static final Logger LogCvt = Logger.getLogger(CollectionRecordController.class);
	
	@Autowired
	private CollectionRecordServer recordServer;
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年3月13日 <br>
	 * 描述： 催收任务列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/collection/collectionRecordService.do")
	public @ResponseBody Pager<CollectionRecordResult> execute(HttpServletRequest request){
		
		CollectionRecordReq req = (CollectionRecordReq) RequestUtil.copyParam(CollectionRecordReq.class, request);
		Pager<CollectionRecordResult> pager=null;
		SessionRouter session = LoginUtil.getLoginSession(request);
		try {
			  if(session!=null){
				if(req==null){
					req=new CollectionRecordReq();
				}
				req.setEmptId(session.getEmpId());
				req.setDeptId(session.getDeptId());
				req.setManagerCastId(session.getEmpWorkNum());
				req.setLoanManagerCastId(session.getEmpWorkNum());
				req.setSalesDeptment(session.getStoreId());
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				List<CollectionRecordResult> pageData=recordServer.queryCollectionRecord(req);
				if(pageData!=null){
					req.setPage(-1);
					pager = new Pager<CollectionRecordResult>(pageData, recordServer.countCollectionRecord(req));
				}
			  }
			LogCvt.info("响应报文："+JSON.toJSONString(pager));
			return pager;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("催收任务列表查询异常"+e.getMessage());
		}
		return pager;
	}
	
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年3月14日 <br>
	 * 描述： 催收任务列表导出
	 * @param request
	 * @param response
	 */
	@RequestMapping("/after/collection/downloadCollectionRecordService.do")
	public void downloadExecute(HttpServletRequest request,HttpServletResponse response){
		CollectionRecordReq req = (CollectionRecordReq) RequestUtil.copyParam(CollectionRecordReq.class, request);
		SessionRouter session = LoginUtil.getLoginSession(request);
		try {
			if(session!=null){
				if(req==null){
					req=new CollectionRecordReq();
				}
				req.setEmptId(session.getEmpId());
				req.setDeptId(session.getDeptId());
				req.setManagerCastId(session.getEmpWorkNum());
				req.setLoanManagerCastId(session.getEmpWorkNum());
				req.setSalesDeptment(session.getStoreId());
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				req.setResponse(response);
				recordServer.downloadCollectionRecord(req);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("催收任务列表导出异常"+e.getMessage());
		}
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 催收记录提醒
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/after/collection/collectionRecordRemindingService.do")
	public @ResponseBody Json collectionRocordReminding(HttpServletRequest request,HttpServletResponse response){
		CollectionRecordReq req = (CollectionRecordReq) RequestUtil.copyParam(CollectionRecordReq.class, request);
		SessionRouter session = LoginUtil.getLoginSession(request);
		CustomerCollectionRemindingResult result=null;
		Json json=new Json();
		try {
			if(session!=null){
				if(req==null){
					req=new CollectionRecordReq();
				}
				req.setLoanManagerCast(session.getEmpName());
				req.setLoanManagerCastId(session.getEmpWorkNum());
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				result=recordServer.queryCustomerCastInfo(req);
				json.setData(result);
				json.setMsg("查询成功");
				json.setSuccess(true);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			json.setMsg("催收提醒查询异常");
			json.setSuccess(false);
			json.setData(result);
			LogCvt.error("催收提醒查询异常"+e.getMessage());
		}
		return json;
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月24日 <br>
	 * 描述： 添加点评信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/after/collection/addCommentCollectionRecordService.do")
	public @ResponseBody Json addCommentCollectionRocord(HttpServletRequest request,HttpServletResponse response){
		Review req = (Review) RequestUtil.copyParam(Review.class, request);
		SessionRouter session = LoginUtil.getLoginSession(request);
		Json json=new Json();
		try {
			if(session!=null){
				if(req==null){
					req=new Review();
				}
				req.setApplicationCastId(session.getEmpWorkNum());
				req.setApplicationCastName(session.getEmpName());
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				json=recordServer.addCommentCollectionRecordInfo(req);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			json.setMsg("点评异常");
			json.setSuccess(false);
			LogCvt.error("点评异常"+e.getMessage());
		}
		return json;
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 添加催收记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/after/collection/addcollectionRecordRemService.do")
	public @ResponseBody Json addcollectionRocordRem(HttpServletRequest request,HttpServletResponse response){
		CollectingReceivingCallResult req = (CollectingReceivingCallResult) RequestUtil.copyParam(CollectingReceivingCallResult.class, request);
		SessionRouter session = LoginUtil.getLoginSession(request);
		Json json=new Json();
		try {
			if(session!=null){
				if(req==null){
					req=new CollectingReceivingCallResult();
				}
				req.setNoteTaker(session.getEmpWorkNum());
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				Integer num=recordServer.addCustomerCollectionInfo(req);
				if(num>0){
					json.setMsg("添加记录成功");
					json.setSuccess(true);
				}else{
					json.setMsg("添加记录失败");
					json.setSuccess(false);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			json.setMsg("添加记录异常");
			json.setSuccess(false);
			LogCvt.error("添加记录异常"+e.getMessage());
		}
		return json;
	}
}
