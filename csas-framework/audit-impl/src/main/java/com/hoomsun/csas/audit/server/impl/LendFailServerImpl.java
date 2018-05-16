/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.CreateSerialNo;
import com.hoomsun.common.util.HttpClientUtil;
import com.hoomsun.common.util.JumpTaskCmd;
import com.hoomsun.csas.audit.dao.LendFailMapper;
import com.hoomsun.csas.audit.model.vo.UserApplyVO;
import com.hoomsun.csas.audit.server.inter.LendFailServerI;
import com.hoomsun.csas.sales.dao.UserApplyMapper;

/**
 * 作者：liusong <br>
 * 创建时间：2017年9月14日 <br>
 * 描述： 终审表信息的业务实现
 */
@Service("lendFailServer")
public class LendFailServerImpl implements LendFailServerI {
	@Autowired
	private UserApplyMapper userApplyMapper;
	@Autowired
	private LendFailMapper lendFailMapper;
	@Autowired
	private TaskService taskService;
	@Value("${LEND_FAIL}")
	private String lendFail;

	@Override
	public Pager<UserApplyVO> findPager(Integer page, Integer rows, String custName, String idNumber, String loanId) {

		Map<String, Object> param = new HashMap<String, Object>();
		if (page == null || rows == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		rows = rows > 100 ? 100 : rows;
		param.put("pageIndex", page);
		param.put("pageSize", rows);

		if (!StringUtils.isBlank(custName)) {
			param.put("custName", "%" + custName + "%");
		}

		if (!StringUtils.isBlank(idNumber)) {
			param.put("idNumber", idNumber);
		}

		if (!StringUtils.isBlank(loanId)) {
			param.put("loanId", loanId);
		}
		List<UserApplyVO> userApplyVOs = lendFailMapper.findPager(param);
		Integer total = lendFailMapper.findFinalAuditCount(param);
		return new Pager<>(userApplyVOs, total);
	}

	@Override
	public Json lendFail(String applyId) {
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("makeCon").singleResult();
		if (null == task) {
			return new Json(false, "无权流标!");
		}
		JSONObject object = new JSONObject();
		object.put("generalInfo", CreateSerialNo.sign());
		Map<String, Object> loan = new HashMap<String, Object>();
		String failReason = "此单废弃";
		loan.put("loanRefId", applyId);
		loan.put("failReason", failReason);
		object.put("loan", loan);
		String json = object.toJSONString();
		String url = lendFail;
		String result = HttpClientUtil.doPostJson(url, null, null, json);
		JSONObject resultObj = JSONObject.parseObject(result);
		int status = resultObj.getIntValue("status");
		if(status == 1000){
			((TaskServiceImpl) taskService).getCommandExecutor().execute(new JumpTaskCmd(task, "finalAudit", JumpTaskCmd.WITHDRAW));
			List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(applyId).processInstanceId(task.getProcessInstanceId()).list();
			String precessStatus = "";
			String precessStatusVal = "";
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
			userApplyMapper.updateProcessInstance(applyId, task.getProcessInstanceId(), precessStatus, precessStatusVal);
			return new Json(true,"流标成功");
		}else{
			return new Json(false,resultObj.getString("errMsg"));
		}
	}

}
