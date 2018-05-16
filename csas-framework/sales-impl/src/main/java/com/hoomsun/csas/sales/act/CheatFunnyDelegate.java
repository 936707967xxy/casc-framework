/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.act;

import java.io.Serializable;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hoomsun.common.util.JumpTaskCmd;
import com.hoomsun.csas.sales.api.server.inter.CheatFunnyServerI;
import com.hoomsun.csas.sales.dao.UserApplyMapper;
import com.hoomsun.risk.dao.mongo.PhoneBookDao;
import com.hoomsun.risk.model.PhoneBook;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月24日 <br>
 * 描述：反欺诈的任务处理
 */
@Component("cheatFunnyDelegate")
public class CheatFunnyDelegate implements Serializable, JavaDelegate {
	private static final long serialVersionUID = 2308188016168828556L;
	@Autowired
	private CheatFunnyServerI cheatFunnyServer;
	@Autowired
	private UserApplyMapper userApplyMapper;
	@Autowired
	private PhoneBookDao phoneBookDao;
	@Autowired
	private TaskService taskService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// 申请编号
		String applyId = execution.getProcessBusinessKey();

		// 查询出单子的来源为APP提交
		Integer isapp = userApplyMapper.findIsApp(applyId);
		if (null != isapp) {
			if (!isapp.equals(0)) {
				// 查询通讯录匹配率
				PhoneBook phoneBook = phoneBookDao.findByApplyId(applyId);
				if(phoneBook != null){
					// 通讯录匹配率
					Double matchRate = phoneBook.getMatchRate();
					if (matchRate != null) {
						// 157、申请人1个月内通话记录的手机号码与通讯录号码匹配成功的数量<20%
						if (matchRate < 0.2) {// 拒贷
							String taskId = execution.getCurrentActivityId();
							// 走拒贷流程
							Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
							
							JumpTaskCmd cmd = new JumpTaskCmd(task, "endEvent", JumpTaskCmd.REJECT);
							TaskServiceImpl taskServiceImpl = (TaskServiceImpl) taskService;
							taskServiceImpl.getCommandExecutor().execute(cmd);
							return;
						}
					}
				}
				
			}
		}

		// 欺诈勾稽
		cheatFunnyServer.cheatMatch(applyId);
	}

}
