/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.flow;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hoomsun.csas.audit.util.JumpTaskCmd;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月29日 <br>
 * 描述：流程测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-cfg.xml")
public class FlowTest {
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	
	
	// 启动流程 creditSaleAudit creditAudit
	@Test
	public void start() {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("creditSaleAuditSystem", "1083cfb812c54edab7ad72a1cd550b34");
		System.out.println("流程实例ID:" + processInstance.getProcessInstanceId()); // a8c7b548-d4ec-11e7-ad5c-408d5c38da5e
	}
	
	//处理任务 issupp  goto
	@Test
	public void completeTask() {
		Map<String, Object> var = new HashMap<String, Object>();
		var.put("issupp", "1");
		taskService.complete("d69b41f7-d4f3-11e7-b42d-408d5c38da5e",var);
//		taskService.complete("c35e06e5-d4f3-11e7-a473-408d5c38da5e");
//		Task task = taskService.createTaskQuery().taskAssignee("张三").processInstanceBusinessKey("88888888").singleResult();
//		System.out.println("任务ID " + task.getId());//b8cc8bd6-d4ed-11e7-898f-408d5c38da5e
		
	}

	//签收任务
	@Test
	public void claimTask() {
		taskService.claim("b8cc8bd6-d4ed-11e7-898f-408d5c38da5e", "张三");
	}
	
	//退回任务
	@Test
	public void rollbackTask() {
		Task task = taskService.createTaskQuery().taskId("da6701f6-d4f1-11e7-a31d-408d5c38da5e").processInstanceBusinessKey("88888888").singleResult();
		((TaskServiceImpl) taskService).getCommandExecutor().execute(new JumpTaskCmd(task, "approvalEvent", JumpTaskCmd.ROLLBACK));
	}
	
	//撤回任务
	@Test
	public void withdrawTask() {
		
	}

}
