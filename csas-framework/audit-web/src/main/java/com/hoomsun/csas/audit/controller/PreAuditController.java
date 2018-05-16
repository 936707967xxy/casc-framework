/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
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

import com.hoomsun.common.model.Combobox;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.csas.audit.model.PreAudit;
import com.hoomsun.csas.audit.model.vo.UserApplyVO;
import com.hoomsun.csas.audit.server.inter.PreAuditServerI;
import com.hoomsun.csas.sales.api.model.UserChsi;
import com.hoomsun.csas.sales.api.model.UserHouseFund;
import com.hoomsun.csas.sales.api.model.UserSocialsecurity;
import com.hoomsun.csas.sales.api.model.vo.CreditVo;
import com.hoomsun.csas.sales.api.model.vo.RollBackInfoVo;
import com.hoomsun.csas.sales.api.server.inter.OtherServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月1日 <br>
 * 描述：初审审核
 */
@Controller
public class PreAuditController {
	private PreAuditServerI preAuditServer;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private OtherServerI otherServer;

	@Autowired
	public void setPreAuditServer(PreAuditServerI preAuditServer) {
		this.preAuditServer = preAuditServer;
	}

	// @InitBinder
	// public void initBinder(WebDataBinder binder) {
	// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd
	// HH:mm:ss");
	// dateFormat.setLenient(false);
	// binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,
	// true));
	// }

	@RequestMapping("/sys/preaudit/pager.do")
	@ResponseBody
	public Pager<UserApplyVO> findPager(Integer rows, Integer page, String custName, String loanId, String nodeStatus, String idNumber, String node, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		if (sessionRouter == null) {
			return null;
		}
		String empId = sessionRouter.getEmpId();
		String deptId = sessionRouter.getDeptId();
		return preAuditServer.findPager(page, rows, custName, idNumber, loanId, empId, nodeStatus, node, deptId);
	}

	// 签收任务
	@RequestMapping("/sys/preaudit/claim.do")
	@ResponseBody
	public Json claimTask(String applyId, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String empId = sessionRouter.getEmpId();
		return preAuditServer.claimTask(applyId, empId);
	}

	// 保存审核意见(初审审核调用的这个)
	@RequestMapping("/sys/preaudit/save.do")
	@ResponseBody
	public Json saveAuditOption(PreAudit audit, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String empId = sessionRouter.getEmpId();
		String empName = sessionRouter.getEmpName();
		audit.setEmpId(empId);
		audit.setEmpName(empName);
		return preAuditServer.savePreAudit(audit, empId);
	}

	// 审核
	@RequestMapping("/sys/preaudit/complete.do")
	@ResponseBody
	public Json completeTask(String applyId, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String empId = sessionRouter.getEmpId();
		return preAuditServer.completeTask(applyId, empId);
	}

	// 退回
	@RequestMapping("/sys/preaudit/rollback.do")
	@ResponseBody
	public Json rollbackTask(String applyId, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String empId = sessionRouter.getEmpId();
		return preAuditServer.rollbackTask(applyId, empId);
	}

	// 撤销
	@RequestMapping("/sys/preaudit/withdraw.do")
	@ResponseBody
	public Json withdrawTask(String applyId, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String empId = sessionRouter.getEmpId();
		return preAuditServer.withdrawTask(applyId, empId);
	}

	// 转办数据列表
	@RequestMapping(value = "/sys/preaudit/groupuser.do", method = { RequestMethod.POST })
	@ResponseBody
	public List<Combobox> complaintComBox(String applyId, HttpServletRequest request) {
		String empId = LoginUtil.getLoginSession(request).getEmpId();
		Group group = identityService.createGroupQuery().groupMember(empId).groupId("PRE_AUDIT_GROUP").singleResult();
		String groupId = group.getId();
		List<User> users = identityService.createUserQuery().memberOfGroup(groupId).list();
		if (null != users && users.size() > 0) {
			List<Combobox> comboboxs = new ArrayList<Combobox>();
			for (User user : users) {
				Combobox combobox = new Combobox(user.getId(), user.getFirstName());
				comboboxs.add(combobox);
			}
			return comboboxs;
		}
		return null;
	}

	// 转办执行
	@RequestMapping(value = "/sys/preaudit/complaint.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json complaint(String applyId, String userId, HttpServletRequest request) {
		if (StringUtils.isAllBlank(applyId, userId)) {
			return new Json(false, "参数异常,转办失败!");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("preAudit").singleResult();
		if (null != task) {
			taskService.setAssignee(task.getId(), userId);
			return new Json(true, "转办成功!");
		} else {
			return new Json(false, "任务已经处理,转办失败!");
		}
	}

	// 验证是否已经签收
	@RequestMapping(value = "/sys/preaudit/checkclaim.do")
	@ResponseBody
	public Json checkClaim(String applyId, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String empId = sessionRouter.getEmpId();
		return preAuditServer.checkClaim(applyId, empId);
	}

	@RequestMapping(value = "/sys/preaudit/query.do")
	@ResponseBody
	public PreAudit findByApplyId(String applyId, HttpServletRequest request) {
		if (StringUtils.isBlank(applyId)) {
			return new PreAudit();
		}
		PreAudit audit = preAuditServer.finByApplyId(applyId);
		if (audit == null) {
			audit = new PreAudit();
		}
		return audit;
	}

	// 审核历史
	@RequestMapping(value = "/sys/preaudit/audithistory.do")
	@ResponseBody
	public void findAuditHistory(String applyId, HttpServletRequest request) {
		preAuditServer.findAuditHistory(applyId);
	}

	// 征信数据的保存
	@RequestMapping("/sys/preaudit/savecredit.do")
	@ResponseBody
	public Json saveCredit(@RequestBody CreditVo creditVo, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String empId = sessionRouter.getEmpId();
		return preAuditServer.saveCredit(creditVo, empId);
	}

	// 征信数据的保存
	@RequestMapping("/sys/preaudit/savepbccre.do")
	@ResponseBody
	public Json savePbccre(@RequestBody CreditVo creditVo, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String empId = sessionRouter.getEmpId();
		return preAuditServer.savePbccre(creditVo, empId);
	}

	// 征信数据的保存
	@RequestMapping("/sys/preaudit/savecis.do")
	@ResponseBody
	public Json saveCis(@RequestBody CreditVo creditVo, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String empId = sessionRouter.getEmpId();
		return preAuditServer.saveCis(creditVo, empId);
	}

	// 保存社保
	@RequestMapping("/sys/preaudit/si/save.do")
	@ResponseBody
	public Json saveSI(@RequestBody UserSocialsecurity socialsecurity, HttpServletRequest request) {
		return otherServer.saveOrUpadteSocialsecurity(socialsecurity);
	}

	// 保存公积金
	@RequestMapping("/sys/preaudit/housefund/save.do")
	@ResponseBody
	public Json saveHouseFund(@RequestBody UserHouseFund houseFund, HttpServletRequest request) {
		return otherServer.saveOrUpadteHouseFund(houseFund);
	}

	// 保存学历
	@RequestMapping("/sys/preaudit/chsi/save.do")
	@ResponseBody
	public Json saveChsi(@RequestBody UserChsi chsi, HttpServletRequest request) {
		return otherServer.saveOrUpadteChsi(chsi);
	}

	@RequestMapping("/sys/preaudit/upload.do")
	@ResponseBody
	public Json upload(@RequestParam("file") MultipartFile file, String applyId, HttpServletRequest request) {
		String context = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String loginEmp = sessionRouter.getEmpId();
		String loginName = sessionRouter.getEmpName();
		return preAuditServer.upload(file, applyId, loginEmp, loginName, context);
	}

	@RequestMapping("/sys/preaudit/multiupload.do")
	@ResponseBody
	public Json multiUpload(@RequestParam("file") MultipartFile[] files, String applyId, HttpServletRequest request) {
		String context = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String loginEmp = sessionRouter.getEmpId();
		String loginName = sessionRouter.getEmpName();
		return preAuditServer.multiUpload(files, applyId, loginEmp, loginName, context);
	}

	@RequestMapping("/sys/preaudit/deleteAnnex.do")
	@ResponseBody
	public Json delete(String id, HttpServletRequest request) {
		return preAuditServer.deleteAnnex(id);
	}

	// 终审退回信息列表
	@RequestMapping(value = "/sys/pre/finalrollbackhis.do")
	@ResponseBody
	public List<RollBackInfoVo> getPreRollBackInfo(String applyId) {
		return preAuditServer.findPreRollBackHis(applyId);
	}

	// 保存草稿
	@RequestMapping("/sys/preaudit/saveInfo.do")
	@ResponseBody
	public Json saveAuditInfo(PreAudit audit, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String empId = sessionRouter.getEmpId();
		String empName = sessionRouter.getEmpName();
		audit.setEmpName(empName);
		return preAuditServer.saveAuditInfo(audit, empId);
	}
}
