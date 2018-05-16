/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.util;

import java.util.List;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.task.Task;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月18日 <br>
 * 描述：简单流程的节点跳转 只能用于单线流程的节点跳转
 */
public class JumpTaskCmd implements Command<Void> {
	/**
	 * 通过
	 */
	public final static String COMPLETED = "completed";
	/**
	 * 撤回
	 */
	public final static String WITHDRAW = "withdraw";
	/**
	 * 退回
	 */
	public final static String ROLLBACK = "rollback";
	/**
	 * 结束
	 */
	public final static String END = "end";
	/**
	 * 拒贷
	 */
	public final static String REJECT = "reject";
	/**
	 * 客户放弃
	 */
	public final static String WAIVER = "waiver";
	/**
	 * 跳转
	 */
	public final static String JUMP = "jump";
	
	private Task currentTask;
	private String desActivityId;
	private String executionId;
	private String currentActivityId;
	private String processInstanceId;
	private String type;
	
	public JumpTaskCmd(Task currentTask, String desActivityId,String type) {
		this.currentTask = currentTask;
		this.desActivityId = desActivityId;
		this.executionId = currentTask.getExecutionId();
		this.currentActivityId = currentTask.getTaskDefinitionKey();
		this.processInstanceId = currentTask.getProcessInstanceId();
		this.type = type;
	}

	@Override
	public Void execute(CommandContext commandContext) {
		ExecutionEntityManager executionEntityManager = Context.getCommandContext().getExecutionEntityManager();
		ExecutionEntity executionEntity = executionEntityManager.findExecutionById(executionId);
		ProcessDefinitionImpl processDefinition = executionEntity.getProcessDefinition();
		executionEntity.setVariables(null);
		executionEntity.setExecutions(null);
		executionEntity.setEventSource(processDefinition.findActivity(this.currentActivityId));
		executionEntity.setActivity(processDefinition.findActivity(this.currentActivityId));

		List<TaskEntity> taskEntities = Context.getCommandContext().getTaskEntityManager().findTasksByProcessInstanceId(processInstanceId);
		for (TaskEntity taskEntity : taskEntities) {
			taskEntity.fireEvent("complete");
			Context.getCommandContext().getTaskEntityManager().deleteTask(taskEntity, this.type, false);
		}

		executionEntity.executeActivity(processDefinition.findActivity(this.desActivityId));
		return null;
	}

	public Task getCurrentTask() {
		return currentTask;
	}

	public void setCurrentTask(Task currentTask) {
		this.currentTask = currentTask;
	}

	public String getDesActivityId() {
		return desActivityId;
	}

	public void setDesActivityId(String desActivityId) {
		this.desActivityId = desActivityId;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getCurrentActivityId() {
		return currentActivityId;
	}

	public void setCurrentActivityId(String currentActivityId) {
		this.currentActivityId = currentActivityId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
