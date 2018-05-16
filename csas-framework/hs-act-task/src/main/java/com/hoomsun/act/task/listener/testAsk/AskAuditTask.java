/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.act.task.listener.testAsk;

import java.io.Serializable;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoomsun.common.util.JumpTaskCmd;

/**
 * 作者：liusong <br>
 * 创建时间：2018年2月13日 <br>
 * 描述：
 */
@Component("askAuditTask")
public class AskAuditTask implements Serializable, JavaDelegate {
	/**
	 * 作者：liusong <br>
	 * 创建时间：2018年2月13日 <br>
	 * 描述：
	 */
	private static final long serialVersionUID = 2859562227539440316L;
	@Autowired
	private TaskService taskService;
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("---------------------------------------------");  
        System.out.println();  
        System.out.println("Hello Service " + this.toString()  
                + "Is Saying Hello To Every One !");  
        System.out.println("---------------------------------------------");  
        System.out.println();  
        String taskId = execution.getCurrentActivityId();
        //走拒贷流程
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		JumpTaskCmd cmd = new JumpTaskCmd(task, "endEvent", JumpTaskCmd.REJECT);
		TaskServiceImpl taskServiceImpl = (TaskServiceImpl) taskService;
		taskServiceImpl.getCommandExecutor().execute(cmd);
	}

}
