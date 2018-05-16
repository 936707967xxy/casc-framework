/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.util;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;

import com.hoomsun.common.model.Json;
import com.hoomsun.core.model.vo.SessionRouter;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年12月12日 <br>
 * 描述：流程相关的工具类
 */
public class TaskUtils {
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 转办调用
	 * @param applyId
	 * @param userId
	 * @param request
	 * @return
	 */
	public static Json complaint(String applyId, String userId, HttpServletRequest request, TaskService taskService) {
		if (StringUtils.isAllBlank(applyId, userId)) {
			return new Json(false, "参数异常,转办失败!");
		}
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String empId = sessionRouter.getEmpId();
		if (empId.equals(userId)) {
			return new Json(false, "相同员工不能转办!");
		} else {
			Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).singleResult();
			if (null != task) {
				taskService.setAssignee(task.getId(), userId);
				return new Json(true, "转办成功!");
			} else {
				return new Json(false, "任务已经处理,转办失败!");
			}
		}
	}
}
