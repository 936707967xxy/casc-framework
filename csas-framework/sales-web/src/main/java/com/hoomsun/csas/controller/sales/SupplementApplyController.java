package com.hoomsun.csas.controller.sales;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.anno.Permission;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.csas.sales.api.model.SupplementSubmit;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.server.inter.SupplementServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月12日 <br>
 * 描述：补充资料控制层
 */
@Controller
public class SupplementApplyController {
	
	@Autowired
	private SupplementServerI supplementServer;
	@Autowired
	private TaskService taskService;

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月22日 <br>
	 * 描述：根据节点查询补充资料所有单子
	 */
	@RequestMapping(value = "/sys/supplement/select.do")
	@ResponseBody
	public Pager<UserApply> selectSupplementApply(Integer page, Integer rows, String custName, String loanId, String nodeStatus, String idNumber, String salesEmpName, String custMobile, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		if (sessionRouter == null) {
			return null;
		}
		Pager<UserApply> applyPager = supplementServer.selectSupplementApply(page, rows, custName, loanId, nodeStatus, idNumber, salesEmpName, custMobile, sessionRouter);
		return applyPager;
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年12月10日 <br>
	 * 描述： 提交下一个流程流程
	 * 
	 * @param applyId
	 * @param request
	 * @return
	 */
	@Permission("suppl_submit")
	@RequestMapping(value = "/sys/supplement/nextprocess.do")
	@ResponseBody
	public Json nextProcess(String applyId, String submitTypeText, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		if (sessionRouter == null) {
			return new Json(false, "登录异常，请刷新页面");
		}
		return supplementServer.supplementSubmit(applyId, submitTypeText, sessionRouter);
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月27日 <br>
	 * 描述： 补充资料---修改回显
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/sys/supplement/selectapplybyid.do")
	@ResponseBody
	public UserApply selectApplyById(String applyId, HttpServletRequest request) throws UnsupportedEncodingException {
		UserApply applyModel = supplementServer.selectApplyTableByAppId(applyId);
		return applyModel;
	}

	/**
	 * 作者：zgzhao <br>
	 * 创建时间：2017年11月28日 <br>
	 * 描述： 补充资料---修改申请单
	 * 
	 * @param userApply
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@Permission("suppl_update")
	@RequestMapping(value = "/sys/supplement/updateapply.do")
	@ResponseBody
	public Json updateApply(@RequestBody UserApply userApply, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常，请刷新页面");
		}
		Json json = supplementServer.updateApplyTableByApplyId(userApply, session);
		return json;
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月27日 <br>
	 * 描述：补充资料-判断当前节点
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/sys/supplement/iscurrentnode.do")
	@ResponseBody
	public Json isCurrentNode(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数异常");
		}
		Json json = supplementServer.isCurrentNode(applyId);
		return json;
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月30日 <br>
	 * 描述：补充资料---申请单上传附件
	 * 
	 * @param applyId
	 * @return
	 */
	@RequestMapping(value = "/sys/supplement/createapplyupload.do", method = RequestMethod.POST)
	@ResponseBody
	public Json createApplyUpload(String applyId, Integer applyTypeId, String applyTypeIdVal, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常,请刷新页面!");
		}
		if (file.isEmpty() || null == file) {
			return new Json(false, "上传附件为空!");
		}
		Json json = new Json();
		try {
			json = supplementServer.createApplyUpload(applyId, applyTypeId, applyTypeIdVal, file, session, request);
		} catch (IOException e) {
			return new Json(false, "上传失败!");
		}
		return json;
	}
	
	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2018年2月11日 <br>
	 * 描述： 文件批量上传
	 * @param files
	 * @param applyId
	 * @param imgId
	 * @param imgIdVal
	 * @param request
	 * @return
	 */
	@RequestMapping("/sys/supplement/multiupload.do")
	@ResponseBody
	public Json multiUpload(@RequestParam("file") MultipartFile[] file, String applyId, Integer applyTypeId, String applyTypeIdVal, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常,请刷新页面!");
		}
		if (file==null||file.length<1) {
			return new Json(false, "上传附件为空!");
		}
		Json json = new Json();
			json = supplementServer.multiUpload(file, applyId, applyTypeId, applyTypeIdVal, session, request);
		return json;
	}
	

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年12月15日 <br>
	 * 描述：上传回显
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/sys/supplement/upselectapplybyid.do")
	@ResponseBody
	public UserApply upSelectApplyById(String applyId, HttpServletRequest request) {
		UserApply applyModel = supplementServer.upSelectApplyById(applyId);
		return applyModel;
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年12月18日 <br>
	 * 描述：提交回显
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/sys/supplement/submitfindbyid.do")
	@ResponseBody
	public SupplementSubmit submitFindById(String applyId, HttpServletRequest request) {
		if (StringUtils.isAllBlank(applyId)) {
			return null;
		}
		SupplementSubmit applyModel = supplementServer.submitFindById(applyId);
		return applyModel;
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年12月19日 <br>
	 * 描述： 客户放弃
	 * 
	 * @param applyId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sys/supplement/giveupapply.do")
	@ResponseBody
	public Json giveUpApply(String applyId, String submitTypeText, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		if (sessionRouter == null) {
			return new Json(false, "登录异常，请刷新页面");
		}
		return supplementServer.supplementGiveUp(applyId, submitTypeText, sessionRouter);
	}

	// 签收
	@RequestMapping("/sys/supplement/claim.do")
	@ResponseBody
	public Json claimTask(String applyId, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常，请刷新页面！");
		}
		String empId = session.getEmpId();
		return supplementServer.claimTask(applyId, empId);
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年12月29日 <br>
	 * 描述：回显上个节点备注内容
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/sys/supplement/beforesubmit.do")
	@ResponseBody
	public List<Map<String, Object>> beforeSubmit(String applyId, HttpServletRequest request) {
		if (StringUtils.isAllBlank(applyId)) {
			return null;
		}
		List<Map<String, Object>> suList = supplementServer.beforeSubmit(applyId);
		return suList;
	}

	// 转办方法执行
	@RequestMapping(value = "/sys/supplement/saveassignee.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json complaint(String applyId, String userId, HttpServletRequest request) {
		if (StringUtils.isAllBlank(applyId, userId)) {
			return new Json(false, "参数异常,转办失败!");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("addData").singleResult();
		if (null != task) {
			taskService.setAssignee(task.getId(), userId);
			return new Json(true, "转办成功!");
		} else {
			return new Json(false, "任务已经处理,转办失败!");
		}
	}
	
	// 修改判断是否是协议审核退回
	@RequestMapping(value = "/sys/supplement/isconaudit.do")
	@ResponseBody
	public Json isConAudit(String applyId, HttpServletRequest request) {
		if (StringUtils.isAllBlank(applyId)) {
			return new Json(false, "参数异常!");
		}
		return supplementServer.isConAudit(applyId);
	}
}
