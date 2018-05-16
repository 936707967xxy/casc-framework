/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.common.util.UploadPathUtil;
import com.hoomsun.csas.audit.dao.AuditAnnexMapper;
import com.hoomsun.csas.audit.model.AuditAnnex;
import com.hoomsun.csas.audit.server.inter.AuditAnnexServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月16日 <br>
 * 描述：审核附件操作的业务实现
 */
@Service("auditAnnexServer")
public class AuditAnnexServerImpl implements AuditAnnexServerI {
	@Autowired
	private AuditAnnexMapper auditAnnexMapper;
	@Autowired
	private UploadPathUtil uploadPathUtil;
	@Autowired
	private TaskService taskService;

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

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskAssignee(loginEmp).singleResult();

		AuditAnnex annex = new AuditAnnex();
		String id = PrimaryKeyUtil.getPrimaryKey();
		annex.setAnxId(id);
		annex.setActId(task.getId());
		annex.setActNode(task.getTaskDefinitionKey());
		annex.setApplyId(applyId);
		annex.setEmpId(loginEmp);
		annex.setEmpName(loginName);
		annex.setFileSize(file.getSize());
		annex.setFileType(fileType);
		annex.setFileName(fileName);

		File savefile = new File(uploadPathUtil.saveAudit() + File.separator + applyId);
		if (!savefile.exists()) {
			savefile.mkdirs();
		}

		String saveName = new Date().getTime() + fileType;// 时间戳
		annex.setSaveName(saveName);
		// 文件保存路径
		String savePath = savefile.getAbsolutePath() + File.separator + saveName;
		// 映射地址
		String preView = context + uploadPathUtil.auditUrl() + "/" + applyId + "/" + saveName;
		// 转存文件
		try {
			file.transferTo(new File(savePath));
			annex.setSavePath(savePath);
			annex.setPreView(preView);
			auditAnnexMapper.insertSelective(annex);
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

	@Override
	public List<AuditAnnex> findByApplyId(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			return null;
		}
		return auditAnnexMapper.findByApplyId(applyId);
	}

	@Override
	public Json deleteAnnex(String id) {
		int result = auditAnnexMapper.deleteByPrimaryKey(id);
		if(result > 0) {
			return new Json(true, "附件删除成功!");
		}else {
			return new Json(false, "附件删除失败!");
		}
	}
	
}
