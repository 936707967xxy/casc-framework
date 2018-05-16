/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.common.util.JumpTaskCmd;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.csas.audit.dao.BPMNMapper;
import com.hoomsun.csas.audit.dao.ReviewAuditMapper;
import com.hoomsun.csas.audit.exception.AuditException;
import com.hoomsun.csas.audit.model.ReviewAudit;
import com.hoomsun.csas.audit.model.vo.UserApplyVO;
import com.hoomsun.csas.audit.server.inter.ReviewAuditServerI;
import com.hoomsun.csas.audit.util.ActivitiUtils;
import com.hoomsun.csas.sales.api.model.ReviewApply;
import com.hoomsun.csas.sales.dao.ReviewApplyMapper;
import com.hoomsun.csas.sales.dao.UserApplyMapper;

/**
 * 作者：liushuai <br>
 * 创建时间：2018年1月16日 <br>
 * 描述：
 */
@Service("reviewAuditServer")
public class ReviewAuditServerImpl implements ReviewAuditServerI {

	@Autowired
	private ReviewAuditMapper reviewAuditMapper;
	@Autowired
	private UserApplyMapper userApplyMapper;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ReviewApplyMapper reviewApplyMapper;
	@Autowired
	private BPMNMapper bpmnMapper;
	
	
	@Override
	public Pager<UserApplyVO> findPager(Integer page, Integer rows, String custName, String idNumber, String loanId, String empId, String nodeStatus, String node, String deptId) {
		if (StringUtils.isBlank(empId) || StringUtils.isBlank(deptId)) {
			throw new AuditException("无法获取你的登录信息,请尝试重新登录!");
		}

		Map<String, Object> param = new HashMap<String, Object>();

		if (page == null || rows == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		rows = rows > 100 ? 100 : rows;

		if (!StringUtils.isBlank(node)) {
			param.put("node", node);// 默认未审核的信息
		}

		param.put("empId", empId);
		param.put("pageIndex", page);
		param.put("pageSize", rows);

		if (!StringUtils.isBlank(custName)) {
			param.put("custName", "%" + custName + "%");
		}

		if (!StringUtils.isBlank(idNumber)) {
			param.put("idNumber", idNumber);
		}

		if (!StringUtils.isBlank(nodeStatus)) {
			param.put("nodeStatus", nodeStatus);
		} else {
			param.put("nodeStatus", 1);// 待审核
		}

		if (!StringUtils.isBlank(loanId)) {
			param.put("loanId", loanId);
		}
		List<UserApplyVO> userApplyVOs = reviewAuditMapper.findPager(param);
		Integer total = reviewAuditMapper.findReviewAuditCount(param);
		return new Pager<UserApplyVO>(userApplyVOs, total);
	}

	@Override
	public Json completeTask(ReviewAudit reviewAudit) {
		String applyId = reviewAudit.getApplyId();
		Integer handleOpinion = reviewAudit.getHandleOpinion();
		String empId = reviewAudit.getAuditEmp();
		if (StringUtils.isBlank(applyId) || StringUtils.isBlank(empId) || handleOpinion == null) {
			return new Json(false, "参数有误!");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("reconsideration").taskAssignee(empId).singleResult();
		if (task == null) {
			return new Json(false, "无权处理任务!");
		}
		String processId = task.getProcessInstanceId();

		saveAudit(reviewAudit);
		switch (handleOpinion) {
		case 5:// 维持原判
			ReviewApply reviewApply = reviewApplyMapper.findByApplyId(applyId, processId);
			String taskKey = reviewApply.getTaskKey();

			if ("makeCon".equals(taskKey)) {
				((TaskServiceImpl) taskService).getCommandExecutor().execute(new JumpTaskCmd(task, "makeCon", JumpTaskCmd.COMPLETED));
			}

			if ("rejectPool".equals(taskKey)) {
				((TaskServiceImpl) taskService).getCommandExecutor().execute(new JumpTaskCmd(task, "rejectPool", JumpTaskCmd.COMPLETED));
			}

			break;
		case 4:// 通过
			taskService.complete(task.getId());
		default:
			break;
		}

		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processId).processInstanceBusinessKey(applyId, "creditSaleAuditSystem").singleResult();
		
		String precessStatus = "";
		String precessStatusVal = "";
		
		if (pi != null) {
			List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(applyId).processInstanceId(processId).list();
			boolean isFirst = true;
			for (Task tk : tasks) {
				if (isFirst) {
					precessStatus = tk.getTaskDefinitionKey();
					precessStatusVal = tk.getName();
					isFirst = false;
				} else {
					precessStatus = precessStatus + "," + tk.getTaskDefinitionKey();
					precessStatusVal = precessStatusVal + "," + tk.getName();
				}
			}
		} else {
			precessStatus = "error";
			precessStatusVal =  "未知";
		}

		userApplyMapper.updateProcessInstance(applyId, processId, precessStatus, precessStatusVal);
		return new Json(true, "处理成功!");
	}

	@Override
	public List<HistoricTaskInstance> findAuditHistory(String applyId) {
		List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(applyId).orderByTaskCreateTime().asc().list();
		return historicTaskInstances;
	}

	@Override
	public ReviewAudit findByApplyId(String applyId) {
		return reviewAuditMapper.selectByPrimaryKey(applyId);
	}

	private void saveAudit(ReviewAudit reviewAudit) {
		if(StringUtils.isBlank(reviewAudit.getReviewId())){
			reviewAudit.setReviewId(PrimaryKeyUtil.getPrimaryKey());
		}
		
		String empId = reviewAudit.getAuditEmp();
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(reviewAudit.getApplyId()).taskDefinitionKey("reconsideration").taskAssignee(empId).singleResult();
		String processId = task.getProcessInstanceId();
		reviewAudit.setAuditDate(DateUtil.getSqlDate());
		// 未绑定用户
		reviewAudit.setTaskId(task.getId());
		reviewAudit.setProcInstId(processId);

		reviewAuditMapper.insertSelective(reviewAudit);
	}

	@Override
	public Json checkClaim(String applyId, String empId) {
		if (StringUtils.isBlank(applyId) || StringUtils.isBlank(empId)) {
			return new Json(false, "员工未登录!");
		}
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("reconsideration").singleResult();
		if (null == task) {
			return new Json(false, "不能处理！任务已不在当前节点!");
		}

		String assignee = task.getAssignee();
		if (StringUtils.isBlank(assignee)) {// 没有签收 签收任务
			return claimTask(applyId, empId);
		} else {
			if (assignee.equals(empId)) {// 相同的人处理
				return new Json(true, "你已经签收成功!");
			} else {
				return new Json(false, "任务已经被他人签收!");
			}
		}
	}

	@Override
	public Json claimTask(String applyId, String empId) {
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("reconsideration").singleResult();
		if (task == null) {
			return new Json(false, "不能处理,任务在其他节点!");
		}

		String assignee = task.getAssignee();
		if (!StringUtils.isBlank(assignee)) {//
			if (assignee.equals(empId)) {
				return new Json(true, "任务已签收成功!");
			} else {
				return new Json(false, "不能处理,任务已被他人签收!");
			}
		} else {
			if (ActivitiUtils.getClaimTaskSign(bpmnMapper, empId)) {
				taskService.claim(task.getId(), empId);
			} else {
				return new Json(false, "不能签收，请先处理完手中的任务!");
			}
		}
		return new Json(true, "任务签收成功!");
	}
}
