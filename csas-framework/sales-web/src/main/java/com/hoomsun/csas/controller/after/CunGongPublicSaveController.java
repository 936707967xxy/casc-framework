/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.after;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.hoomsun.after.api.model.param.PublicSaveReq;
import com.hoomsun.after.api.model.vo.CunGongPublicSaveResult;
import com.hoomsun.after.api.server.CunGongPublicSaveServer;
import com.hoomsun.after.api.util.RequestUtil;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年4月26日 <br>
 * 描述：存公记录查询
 */
@Controller
public class CunGongPublicSaveController {

	private static final Logger LogCvt = Logger.getLogger(CunGongPublicSaveController.class);
	@Autowired
	private CunGongPublicSaveServer gongPublicSaveServer;
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月26日 <br>
	 * 描述： 存公记录查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/cungong/queryCungongRecordService.do")
	public @ResponseBody Pager<CunGongPublicSaveResult> queryCungongRecord(HttpServletRequest request){
		PublicSaveReq req = (PublicSaveReq) RequestUtil.copyParam(PublicSaveReq.class, request);
		Pager<CunGongPublicSaveResult> pager=null;
		SessionRouter session = LoginUtil.getLoginSession(request);
		try {
			if(session!=null){
				if(req==null){
					req=new PublicSaveReq();
				}
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				List<CunGongPublicSaveResult> pageData=gongPublicSaveServer.queryCunGongPublicSave(req);
				if(pageData!=null){
					req.setPage(-1);
					pager = new Pager<CunGongPublicSaveResult>(pageData, gongPublicSaveServer.countCunGongPublicSave(req));
				}
				LogCvt.info("响应报文："+JSON.toJSONString(pager));
			}
			return pager;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("存公记录列表查询异常"+e.getMessage());
		}
		return pager;
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月26日 <br>
	 * 描述： 存公记录导出
	 * @param request
	 * @param response
	 */
	@RequestMapping("/after/cungong/downloanCungongRecordService.do")
	public void downloadExecute(HttpServletRequest request,HttpServletResponse response){
		PublicSaveReq req = (PublicSaveReq) RequestUtil.copyParam(PublicSaveReq.class, request);
		SessionRouter session = LoginUtil.getLoginSession(request);
		try {
			if(session!=null){
				if(req==null){
					req=new PublicSaveReq();
				}
				LogCvt.info("请求报文："+JSON.toJSONString(req));
				req.setResponse(response);
				gongPublicSaveServer.downloadCunGongPublicSave(req);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("存公记录列表导出异常"+e.getMessage());
		}
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月26日 <br>
	 * 描述： 存公导入
	 * @param request
	 * @param response
	 */
	@RequestMapping("/after/cungong/addCungongRecordService.do")
	public @ResponseBody  Json exportExcelExecute(HttpServletRequest request,@RequestParam("file") MultipartFile file){
		SessionRouter session = LoginUtil.getLoginSession(request);
		Json json = new Json();
		if (file.isEmpty() || null == file) {
			json.setMsg("上传附件为空!");
			json.setSuccess(false);
			return json;
		}
		InputStream inputStream = null;
		try {
			if(session!=null){
				inputStream = file.getInputStream();
				String fileName = file.getOriginalFilename();// 文件名
				String fileType = fileName.substring(fileName.lastIndexOf("."));// 后缀
				if (".xls".equals(fileType)) {
					json=gongPublicSaveServer.exportExcelCunGongPublicSave(inputStream, request);
				} else {
					json.setSuccess(false);
					json.setMsg("只能上传XLS格式文件！");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("上传文件异常！");
			LogCvt.error("存公记录导入异常"+e.getMessage());
		}
		return json;
	}
}
