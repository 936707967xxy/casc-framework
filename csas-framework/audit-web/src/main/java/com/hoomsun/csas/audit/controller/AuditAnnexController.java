/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.common.model.Json;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.csas.audit.model.AuditAnnex;
import com.hoomsun.csas.audit.server.inter.AuditAnnexServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月16日 <br>
 * 描述：审核数据
 */
@Controller
public class AuditAnnexController {
	@Autowired
	private AuditAnnexServerI auditAnnexServer;
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述： 
	 * @param file
	 * @param applyId
	 * @param request
	 * @return
	 */
	@RequestMapping("/sys/auditannex/upload.do")
	@ResponseBody
	public Json upload(@RequestParam("file")MultipartFile file, String applyId, HttpServletRequest request) {
		String context = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String loginEmp = sessionRouter.getEmpId();
		String loginName = sessionRouter.getEmpName();
		return auditAnnexServer.upload(file, applyId, loginEmp, loginName, context);
	}
	
	
	@RequestMapping("/sys/auditannex/query.do")
	@ResponseBody
	public List<AuditAnnex> findByApply(String applyId, HttpServletRequest request) {
		return auditAnnexServer.findByApplyId(applyId);
	}
	
	@RequestMapping("/sys/auditannex/delete.do")
	@ResponseBody
	public Json delete(String id, HttpServletRequest request) {
		return auditAnnexServer.deleteAnnex(id);
	}
	
}
