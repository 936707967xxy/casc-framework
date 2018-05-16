/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.server.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.common.util.UploadPathUtil;
import com.hoomsun.core.enums.AnnexType;
import com.hoomsun.core.util.DateUtil;
import com.hoomsun.csas.sales.api.model.Annex;
import com.hoomsun.csas.sales.api.model.ReviewApply;
import com.hoomsun.csas.sales.api.server.inter.ReviewApplyServerI;
import com.hoomsun.csas.sales.dao.AnnexMapper;
import com.hoomsun.csas.sales.dao.ReviewApplyMapper;
import com.hoomsun.csas.sales.dao.UserApplyMapper;
import com.hoomsun.csas.sales.util.JumpTaskCmd;

/**
 * 作者：liusong <br>
 * 创建时间：2018年1月16日 <br>
 * 描述：
 */
@Service("reviewApplyServer")
public class ReviewApplyServerImpl implements ReviewApplyServerI {

	@Autowired
	private ReviewApplyMapper reviewApplyMapper;
	@Autowired
	private TaskService taskService;
	@Autowired
	private UserApplyMapper userApplyMapper;// 用户申请表
	@Autowired
	private AnnexMapper annexMapper;// 上传表
	@Autowired
	private UploadPathUtil uploadPathUtil;

	@Override
	public Pager<ReviewApply> findPage(Integer page, Integer rows, String custName, String custMobile, String empId, String deptId, String storeId) {
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("empId", empId);
		param.put("storeId", storeId);
		param.put("deptId", deptId);
		if (page == null || rows == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		rows = rows > 100 ? 100 : rows;
		param.put("empId", empId);
		param.put("pageIndex", page);
		param.put("pageSize", rows);
		if (!StringUtils.isBlank(custName)) {
			param.put("custName", "%" + custName + "%");
		}
		if (!StringUtils.isBlank(custMobile)) {
			param.put("custMobile", "%" + custMobile + "%");
		}

		List<ReviewApply> findAllData = reviewApplyMapper.findPage(param);
		int applyCount = reviewApplyMapper.pageCount(param);
		Pager<ReviewApply> mapPager = new Pager<ReviewApply>(findAllData, applyCount);
		return mapPager;
	}

	// 提交申请
	@Override
	public Json createApply(ReviewApply reviewApply) {
		String applyId = reviewApply.getApplyId();
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数异常!applyId is null!");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).singleResult();

		if (null == task) {
			return new Json(false, "不能处理！任务已不在当前节点!");
		}

		String taskKey = task.getTaskDefinitionKey();
		if ("makeCon".equals(taskKey) || "rejectPool".equals(taskKey)) {// 跳转得到复议审核
			String taskId = task.getId();
			String procInstId = task.getProcessInstanceId();
			String taskName = task.getName();

			JumpTaskCmd cmd = new JumpTaskCmd(task, "reconsideration", JumpTaskCmd.JUMP);
			TaskServiceImpl taskServiceImpl = (TaskServiceImpl) taskService;
			taskServiceImpl.getCommandExecutor().execute(cmd);

			// 修改申请表状态
			updateApplyTable(applyId, procInstId);

			// 保存审核数据
			reviewApply.setReconId(PrimaryKeyUtil.getPrimaryKey());
			reviewApply.setTaskId(taskId);
			reviewApply.setTaskKey(taskKey);
			reviewApply.setTaskName(taskName);
			reviewApply.setProcId(procInstId);
			reviewApply.setReviewDate(DateUtil.getSqlDate());

			reviewApplyMapper.insertSelective(reviewApply);
			return new Json(true, "复议申请提交成功!");
		} else {
			return new Json(false, "该数据不能发起复议申请!");
		}

	}

	// 申请表更新
	private void updateApplyTable(String applyId, String processId) {
		// 修改申请主表状态
		List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(applyId).processInstanceId(processId).list();
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
		userApplyMapper.updateProcessInstance(applyId, processId, precessStatus, precessStatusVal);
	}

	@Override
	public Json upload(MultipartFile file, String applyId, String loginEmp, String loginName, String context) {
		if (null == file || file.isEmpty() || StringUtils.isBlank(applyId)) {
			return new Json(false, "上传失败,参数不能为空!");
		}

		String fileName = file.getOriginalFilename();
		if (StringUtils.isBlank(fileName)) {
			return new Json(false, "上传失败,参数不能为空!");
		}

		String fileType = fileName.substring(fileName.lastIndexOf("."));
		if (!StringUtils.isBlank(fileType) && (!".jpg".equalsIgnoreCase(fileType) && !".png".equalsIgnoreCase(fileType) && !".gif".equalsIgnoreCase(fileType) && !".bmp".equalsIgnoreCase(fileType) && !".jpeg".equalsIgnoreCase(fileType))) {
			return new Json(false, "图片格式不正确");
		}

		Annex annex = new Annex();
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskAssignee(loginEmp).singleResult();
		if (task != null) {
			annex.setActNode(task.getTaskDefinitionKey());
		}
		
		String id = PrimaryKeyUtil.getPrimaryKey();
		annex.setAnxId(id);
		annex.setApplyId(applyId);
		annex.setEmpId(loginEmp);
		annex.setFileSize(file.getSize());
		annex.setCreateDate(com.hoomsun.common.util.DateUtil.getSqlDate());
		annex.setFileType(fileType);
		annex.setFileName(fileName);
		annex.setPreViewHost(context);
		annex.setApplyTypeId(AnnexType.REVIEW_AUDIT.getType());
		annex.setApplyTypeVal(AnnexType.REVIEW_AUDIT.getName());

		File savefile = new File(uploadPathUtil.saveApplyPath() + File.separator + applyId);
		if (!savefile.exists()) {
			savefile.mkdirs();
		}

		String saveName = new Date().getTime() + fileType;// 时间戳
		annex.setSaveName(saveName);
		// 文件保存路径
		String savePath = savefile.getAbsolutePath() + File.separator + saveName;
		// 映射地址
		String preView = uploadPathUtil.applyUrl() + "/" + applyId + "/" + saveName;
		// 转存文件
		try {
			file.transferTo(new File(savePath));
			annex.setSavePath(savePath);
			annex.setPreView(preView);
			annexMapper.insertSelective(annex);
			JSONObject obj = new JSONObject();
			obj.put("preView", preView);
			obj.put("uid", id);
			return new Json(true, "保存成功！", obj);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Json(false, "上传失败！");
	}

	public Json deleteAnnex(String id) {
		int result = annexMapper.deleteByPrimaryKey(id);
		if (result > 0) {
			return new Json(true, "附件删除成功!");
		} else {
			return new Json(false, "附件删除失败!");
		}
	}

	@Override
	public ReviewApply selectByApplyId(String applyId) {
		return reviewApplyMapper.selectByApplyId(applyId);
	}

	@Override
	public Json compare(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数异常!applyId is null!");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).singleResult();

		if (null == task) {
			return new Json(false, "不能处理！任务已不在当前节点!");
		}
		String taskKey = task.getTaskDefinitionKey();
		if ("makeCon".equals(taskKey) || "rejectPool".equals(taskKey)) {
			return new Json(true, "ok!");
		} else {
			return new Json(false, "不能处理！任务已不在当前节点!");
		}
	}
}
