/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.server.impl;

import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.core.dao.SysEmployeeMapper;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.vo.UpcomingTaskVo;
import com.hoomsun.csas.sales.api.server.inter.UpcomingTaskServerI;
import com.hoomsun.csas.sales.dao.ActivitiMapper;
import com.hoomsun.csas.sales.dao.UserApplyMapper;
import com.hoomsun.csas.sales.util.ActivitiUtils;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月22日 <br>
 * 描述：待办业务实现
 */
@Service("upcomingTaskServer")
public class UpcomingTaskServerImpl implements UpcomingTaskServerI {
	
	@Autowired
	private ActivitiMapper activitiMapper;
	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;// 员工表
	@Autowired
	private TaskService taskService;
	@Autowired
	private ActivitiMapper bpmnMapper;
	@Autowired
	private UserApplyMapper userApplyMapper;
	
	@Override
	public List<UpcomingTaskVo> findRunTask(String empId, String storeId, String deptId) {
		String mgrId = sysEmployeeMapper.findDeptManager(deptId);
		Integer isMgr = 0;//
		if (empId.equals(mgrId)) {// 是部门的负责人
			isMgr = 1;
		}
		List<UpcomingTaskVo> taskVos = activitiMapper.findRunTask(storeId, empId, deptId, isMgr);
		return taskVos;
	}

	@Override
	public Json claimTask(String applyId,SessionRouter session) {
		String empId = session.getEmpId();//当前登录人ID
		String deptId = session.getDeptId();//部门Id
		String mgrId = sysEmployeeMapper.findDeptManager(deptId);
		
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).singleResult();
		if (task == null) {
			return new Json(false, "不能处理,任务在其他节点!");
		}
		String assignee = task.getAssignee();
		//质检复核节点进行判断如果是部门负责人的话，自己可以签收自己的单子，如果不是部门负责人，自己不能签收自己的单子
		String taskKey = task.getTaskDefinitionKey();
		if(!StringUtils.isBlank(taskKey)){
			if (taskKey.equals("qcCheck")) {//质检复核节点进行判断如果是部门负责人的话，自己可以签收自己的单子，如果不是部门负责人，自己不能签收自己的单子
				// 判断是不是部门的负责人，可以签收自己的单子
				if (empId.equals(mgrId) && StringUtils.isBlank(assignee)) {// 是部门的负责人，可以签收所有单子
					if (ActivitiUtils.getClaimTaskSign(bpmnMapper, empId)) {
						taskService.claim(task.getId(), empId);
						return new Json(true, "签收成功!");
					} else {
						return new Json(false, "不能签收，请先处理完手中的任务!");
					}
				}else{// 如果不是部门的负责人，自己不可以签收自己的单子
					if(!StringUtils.isBlank(assignee)){
						if (assignee.equals(empId)) {
							return new Json(false, "任务不能被签收!");
						}else {
							return new Json(false, "不能处理,任务已被他人签收!");
						}
					}else{
						UserApply apply = userApplyMapper.selectByAply(applyId);
						String createId = apply.getCreateEmployee();
						if (createId.equals(empId)) {
							return new Json(false, "任务不能被签收!");
						} else {
							if (ActivitiUtils.getClaimTaskSign(bpmnMapper, empId)) {
								taskService.claim(task.getId(), empId);
								return new Json(true, "签收成功!");
							} else {
								return new Json(false, "不能签收，请先处理完手中的任务!");
							}
						}
					}
				}
			}
			else if(taskKey.equals("makeCon") || taskKey.equals("addData")){
				//其他节点
				if (!StringUtils.isBlank(assignee)) {
					if (assignee.equals(empId)) {
						return new Json(true, "任务已签收成功!");
					} else {
						return new Json(false, "不能处理,任务已被他人签收!");
					}
				} else {
					if (ActivitiUtils.getClaimTaskSign(bpmnMapper, empId)) {
						taskService.claim(task.getId(), empId);
						return new Json(true, "任务签收成功!");
					} else {
						return new Json(false, "不能签收，请先处理完手中的任务!");
					}
				}
			}else{
				return new Json(false, "不能被签收!");
			}
		}else{
			return new Json(false, "不能被处理!");
		}
	}

	@Override
	public Json checkClaim(String applyId,SessionRouter session) {
		String empId = session.getEmpId();//当前登录人ID
		String deptId = session.getDeptId();//部门Id
		String mgrId = sysEmployeeMapper.findDeptManager(deptId);
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).singleResult();
		if (task == null) {
			return new Json(false, "不能处理,任务在其他节点!");
		}
		//质检复核节点进行判断如果是部门负责人的话，自己可以签收自己的单子，如果不是部门负责人，自己不能签收自己的单子
		String taskKey = task.getTaskDefinitionKey();
		String assignee = task.getAssignee();
		if(!StringUtils.isBlank(taskKey)){
			if (taskKey.equals("qcCheck")) {//质检复核节点进行判断如果是部门负责人的话，自己可以签收自己的单子，如果不是部门负责人，自己不能签收自己的单子
				// 判断是不是部门的负责人，可以签收自己的单子
				if (empId.equals(mgrId) && StringUtils.isBlank(assignee)) {// 是部门的负责人，可以签收所有单子
					if (ActivitiUtils.getClaimTaskSign(bpmnMapper, empId)) {
						taskService.claim(task.getId(), empId);
						return new Json(true, "签收成功!");
					} else {
						return new Json(false, "不能审核，请先处理完手中的任务!");
					}
				}else{// 如果不是部门的负责人，自己不可以签收自己的单子
					if(!StringUtils.isBlank(assignee)){
						if (assignee.equals(empId) && !empId.equals(mgrId)) {
							return new Json(false, "任务不能被审核!");
						}else if(!assignee.equals(empId) || empId.equals(mgrId)){
							return new Json(true, "ok!");
						}else {
							return new Json(false, "不能处理,任务已被他人签收!");
						}
					}else{
						UserApply apply = userApplyMapper.selectByAply(applyId);
						String createId = apply.getCreateEmployee();
						if (createId.equals(empId)) {
							return new Json(false, "任务不能被签收!");
						} else {
							if (ActivitiUtils.getClaimTaskSign(bpmnMapper, empId)) {
								taskService.claim(task.getId(), empId);
								return new Json(true, "签收成功!");
							} else {
								return new Json(false, "不能审核，请先处理完手中的任务!");
							}
						}
					}
				}
			}
			else if(taskKey.equals("makeCon") || taskKey.equals("addData")){
				if (StringUtils.isBlank(assignee)) {// 没有签收 签收任务
					if (ActivitiUtils.getClaimTaskSign(bpmnMapper, empId)) {
						taskService.claim(task.getId(), empId);
						return new Json(true, "签收成功!");
					} else {
						return new Json(false, "不能审核，请先处理完手中的任务!");
					}
				} else {
					if (assignee.equals(empId)) {// 相同的人处理
						return new Json(true, "你已经签收成功!");
					} else {
						return new Json(false, "任务已经被他人签收!");
					}
				}
			}else{
				return new Json(false, "不能被处理!");
			}
		}else{
			return new Json(false, "不能被处理!");
		}
	}
}
	
