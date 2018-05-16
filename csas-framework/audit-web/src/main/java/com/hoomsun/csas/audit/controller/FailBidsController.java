/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.util.CreateSerialNo;
import com.hoomsun.common.util.JumpTaskCmd;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.csas.audit.dao.HxbRecordMapper;
import com.hoomsun.csas.audit.model.HxbRecord;
import com.hoomsun.csas.sales.dao.UserApplyMapper;
import com.hoomsun.digest.DigestVerify;
import com.hoomsun.util.ScGeneralInfo;

/**
 * 作者：liusong <br>
 * 创建时间：2018年1月3日 <br>
 * 描述：流标状态推送
 */
@Controller
@RequestMapping("/failBids")
public class FailBidsController {
	private static final Logger log = LoggerFactory.getLogger(FailBidsController.class);
	@Autowired
	private TaskService taskService;
	@Autowired
	private HxbRecordMapper hxbRecordMapper;
	@Autowired
	private UserApplyMapper userApplyMapper;

	@RequestMapping(value = "/send.do")
	@ResponseBody
	public JSONObject fullBidsPushGet(HttpServletRequest req) {
		String postData = req.getParameter("postData");
		log.info("进入流标状态推送接口...");
		// 返回调用方的数据
		JSONObject returnObj = new JSONObject();
		try {
			// 获取头部参数信息得到HXB请求的JSON
//			BufferedReader bufferReader = req.getReader();
//			StringBuffer buffer = new StringBuffer();
//			String line = " ";
//			while ((line = bufferReader.readLine()) != null) {
//				buffer.append(line);
//			}
//			String postData = buffer.toString();
			log.info("流标状态推送接口红小宝发送的数据：" + postData);
			// 解析数据
			JSONObject RecRepStatusObj = JSONObject.parseObject(postData);
			JSONObject generalInfo = RecRepStatusObj.getJSONObject("generalInfo");
			ScGeneralInfo info = JSON.parseObject(generalInfo.toJSONString(), ScGeneralInfo.class);
			// 签名验证
			Boolean verify = new DigestVerify().verify(generalInfo.getString("sign"), info, "UTF-8", "debx-zhixin");
			if (!verify) {
				throw new RuntimeException("流标状态推送接口,验签失败！");
			}

			JSONArray loanList = new JSONArray();
			// 流标推送接口结果
			loanList = RecRepStatusObj.getJSONArray("loan");
			for (int i = 0; i < loanList.size(); i++) {
				String loanId = loanList.getJSONObject(i).getString("loanRefId");// 信贷唯一编号
				String applyId = userApplyMapper.findApplyIdByLoanId(loanId);
				String failReason = loanList.getJSONObject(i).getString("failReason");
				/**
				 * 流标后，在系统里面让这笔单子走到拒贷节点
				 */
				Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).singleResult();
				if(task != null){
					((TaskServiceImpl) taskService).getCommandExecutor().execute(new JumpTaskCmd(task, "rejectPool", JumpTaskCmd.COMPLETED));
					String processId = task.getProcessInstanceId();
					String precessStatus = "";
					String precessStatusVal = "";
					// 只有流程结束pi才是null (拒贷和客户放弃是结束流程)
					precessStatus = JumpTaskCmd.COMPLETED;
					precessStatusVal = "流标";
					userApplyMapper.updateProcessInstance(applyId, processId, precessStatus, precessStatusVal);
				}
				HxbRecord record = new HxbRecord();
				record.setApplyId(applyId);
				record.setRecordTime(new Date());
				record.setPushType("4");
				record.setPushTypeVal("流标推送接口");
				record.setFialReson(failReason);
				record.setRecordId(PrimaryKeyUtil.getPrimaryKey());
				hxbRecordMapper.insertSelective(record);
			}
			returnObj.put("generalInfo", CreateSerialNo.sign());
			returnObj.put("errMsg", "处理成功");
			returnObj.put("status", 1000);
			returnObj.put("handleTime", new Date());
		} catch (Exception e) {
			e.printStackTrace();
			log.info("流标状态推送接口发成异常：" + e.getMessage());
			returnObj.put("generalInfo", CreateSerialNo.sign());
			returnObj.put("errMsg", e.getMessage() == null ? "接口未知异常" : e.getMessage());
			returnObj.put("status", 2000);
			returnObj.put("handleTime", new Date());
		}
		return returnObj;
	}
}
