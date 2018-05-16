package com.hoomsun.csas.sales.server.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.SystemUtils;
import com.hoomsun.common.util.UploadPathUtil;
import com.hoomsun.core.dao.SysEmployeeMapper;
import com.hoomsun.core.model.SysProduct;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.server.inter.SysProductServerI;
import com.hoomsun.core.util.DateUtil;
import com.hoomsun.core.util.PrimaryKeyUtil;
import com.hoomsun.csas.sales.api.exception.AuditException;
import com.hoomsun.csas.sales.api.model.Annex;
import com.hoomsun.csas.sales.api.model.AssetInfo;
import com.hoomsun.csas.sales.api.model.NameAuthentication;
import com.hoomsun.csas.sales.api.model.OccupationalInfo;
import com.hoomsun.csas.sales.api.model.SupplementSubmit;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.UserContacts;
import com.hoomsun.csas.sales.api.server.inter.SupplementServerI;
import com.hoomsun.csas.sales.dao.ActivitiMapper;
import com.hoomsun.csas.sales.dao.AnnexMapper;
import com.hoomsun.csas.sales.dao.AssetInfoMapper;
import com.hoomsun.csas.sales.dao.NameAuthenticationMapper;
import com.hoomsun.csas.sales.dao.OccupationalInfoMapper;
import com.hoomsun.csas.sales.dao.SupplementSubmitMapper;
import com.hoomsun.csas.sales.dao.UserApplyMapper;
import com.hoomsun.csas.sales.dao.UserContactsMapper;
import com.hoomsun.csas.sales.util.ActivitiUtils;
import com.hoomsun.csas.sales.util.JumpTaskCmd;

/**
 * @author ygzhao 补充资料server
 */
@Service("supplementServer")
public class SupplementServerImpl implements SupplementServerI {
	private Logger log = LoggerFactory.getLogger(SupplementServerImpl.class);
	@Autowired
	private UserApplyMapper userApplyMapper;// 用户申请表
	@Autowired
	private AnnexMapper annexMapper;// 上传表
	@Autowired
	private UserContactsMapper userContactsMapper;// 联系人信息表
	@Autowired
	private UploadPathUtil uploadPathUtil;// 上传文档
	@Autowired
	private AssetInfoMapper assetInfoMapper;// 资产信息表
	@Autowired
	private OccupationalInfoMapper occupationalInfoMapper;// 职业信息表
	@Autowired
	private SysProductServerI sysProductServer;
	@Autowired
	private TaskService taskService;
	@Autowired
	private SupplementSubmitMapper supplementSubmitMapper;// 申请单提交表
	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;// 员工表
	@Autowired
	private NameAuthenticationMapper nameAuthenticationMapper;// 申请人主表
	@Autowired
	private ActivitiMapper bpmnMapper;

	/**
	 * 修改回显
	 */
	@Override
	public UserApply selectApplyTableByAppId(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			return null;
		}
		UserApply userApply = userApplyMapper.selectApplyTableByAppId(applyId);// 申请表
		if (userApply == null) {
			return null;
		}
		List<UserContacts> con = userContactsMapper.selectByApplyId(applyId, 1);// 联系人
		if (con == null) {
			con = new ArrayList<UserContacts>();
		}
		AssetInfo asset = assetInfoMapper.selectByApplyId(applyId);// 资产信息
		if (asset == null) {
			asset = new AssetInfo();
		}
		OccupationalInfo occ = occupationalInfoMapper.selectByApplyId(applyId);// 职业信息
		if (occ == null) {
			occ = new OccupationalInfo();
		}
		if (userApply != null) {
			userApply.setUserContacts(con);
			userApply.setAssetInfo(asset);
			userApply.setUserOccupationalInfo(occ);
		}
		return userApply;
	}

	/**
	 * 补充资料修改
	 */
	@Override
	public Json updateApplyTableByApplyId(UserApply userApply, SessionRouter sessionRouter) {
		if (StringUtils.isBlank(userApply.getApplyId())) {
			return new Json(false, "参数异常");
		}
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(userApply.getApplyId()).taskDefinitionKey("addData").singleResult();
		if (task == null) {
			return new Json(false, "无权处理任务!");
		}
		/**
		 * 默认不修改字段
		 */
		userApply.setLoanId(null);
		userApply.setCreateEmployee(null);// 创建人id
		userApply.setCreateEmployeeVal(null);
		userApply.setSubmitEmpId(null);//提交员工
		userApply.setSubmitEmpName(null);//提交姓名
		userApply.setSubmitTypeText(null);//提交文本
		userApply.setSubmitDate(null);//提交时间
		userApply.setStoreId(null);//门店id
		userApply.setStoreName(null);//门店名称
		userApply.setProcStatus(null);//流程字段
		userApply.setProcStatus(null);
		userApply.setProcInstId(null);
		userApply.setIsApp(null);
		userApply.setIsAppVal(null);
		userApply.setAuditType(null);//审核状态
		userApply.setAgreedProduct(null);//同意产品名称
		userApply.setAgreeProductId(null);//同意产品id
		userApply.setAgreePeriod(null);//同意期数
		userApply.setAgreedProductAlias(null);//同意产品别名
		userApply.setAgreedAmount(null);//同意额度
		userApply.setApplyDate(null);
		userApply.setIsTemp(0);//草稿标志
		userApply.setIsTempVal("否");//草稿结果
		String salesDept = userApply.getSalesEmpDeptId();//销售所在部门
		String serviceDept = sessionRouter.getDeptId();//客服所在部门
		String salesName = userApply.getSalesEmpName();//销售姓名
		if (StringUtils.isNotBlank(salesDept)
				&& StringUtils.isNotBlank(serviceDept)&&StringUtils.isNotBlank(salesName)
				&& serviceDept.equals(salesDept)) {//如果部门一致则销售就是客服
			userApply.setSalesEmpName(salesName+"-客服");

		}
		
		Timestamp currentTime = DateUtil.getTimestamp();
		/**
		 * 如果已实名认证，不允许进行修改身份证号和姓名
		 */
		UserApply ApplyBefore = userApplyMapper.selectApplyTableByAppId(userApply.getApplyId());
		String beforeIdnumber = ApplyBefore.getIdNumber();
		NameAuthentication nameAuth = nameAuthenticationMapper.selectByPaperId(beforeIdnumber);
		if(nameAuth!=null&&nameAuth.getIsauthentication()!=null&&nameAuth.getIsauthentication().equals("1")){
			userApply.setIdNumber(nameAuth.getPaperid());
			userApply.setCustName(nameAuth.getCustname());
		}
		userApply.setLastUpdateEmpId(sessionRouter.getEmpId());// 修改人id
		userApply.setLastUpdateEmpName(sessionRouter.getEmpName());
		userApply.setLastUpdateTime(currentTime);
		
		OccupationalInfo occupationalInfo = userApply.getUserOccupationalInfo();// 职业信息表
		AssetInfo assetInfo = userApply.getAssetInfo();// 资产信息表
		List<UserContacts> conlsst = userApply.getUserContacts();// 联系人
		if (userApply == null || conlsst == null || assetInfo == null || occupationalInfo == null) {
			return new Json(false, "参数对象为空，请检查!");
		}
		/**
		 * 产品表字段处理
		 */
		SysProduct susPro = sysProductServer.findById(userApply.getProductId());
		userApply.setProductRate(susPro.getMonthRate());// 月利率
		userApply.setProductFeeRate(susPro.getYearRate()); // 年利率
		userApply.setSuggestRate(susPro.getRealMonthRate());// 实际利率
		// userApply.setProductPay(susPro.getPayType());//申请产品还款方式ID
		userApply.setProductPayVal(susPro.getPayTypeVal());// 申请产品还款方式值
		Integer validId = userApply.getValidMailAddr();// 邮寄地址ID
		if (validId == null) {
			return new Json(false, "邮寄地址为空");
		}
		switch (validId) {
		case 1:// 1:同户口地址
			userApply.setValidMailAddrTxt(userApply.getRresidenceAddress());
			break;
		case 2:// 2:同居住地址
			userApply.setValidMailAddrTxt(userApply.getHouseAddress());
			break;
		case 3:// 3:同单位地址
			userApply.setValidMailAddrTxt(occupationalInfo.getCompanyAddress());
			break;
		case 4:// 4:同房产地址
			userApply.setValidMailAddrTxt(assetInfo.getPropertyAddress());
			break;
		}
		int uApply = userApplyMapper.updateByPrimaryKeySelective(userApply);// 申请表
		int asset = assetInfoMapper.updateByPrimaryKeySelective(assetInfo);// 资产信息表
		int occ = occupationalInfoMapper.updateByPrimaryKeySelective(occupationalInfo);
		userContactsMapper.deleteByApplyId(userApply.getApplyId());// 删除联系人仅删除线下
		int uCon = 0;// 新增联系人
		for (UserContacts con : conlsst) {
			String contactId2 = PrimaryKeyUtil.getPrimaryKey();// 联系人ID
			Integer reId = con.getRelationship();// 关系radio处理
			Integer isKnow = con.getIsKnow();
			con.setIsFillIn(1);
			con.setIsFillInVal("是");
			if (isKnow == null || reId == null) {
				return new Json(false, "联系人信息异常!");
			}
			switch (reId) {
			case 1:// 1:配偶
				con.setRelationshipVal("配偶");
				break;
			case 2:// 2:亲属
				con.setRelationshipVal("亲属");
				break;
			case 3:// 3:同事
				con.setRelationshipVal("同事");
				break;
			case 4:// 4:其他
				con.setRelationshipVal("其他");
				break;
			}
			switch (isKnow) {
			case 0:
				con.setIsKnowVal("否");
				break;
			case 1:
				con.setIsKnowVal("是");
				break;
			}
			con.setContId(contactId2);
			con.setApplyId(userApply.getApplyId());
			con.setName(con.getName());
			con.setAddDate(currentTime);
			uCon = userContactsMapper.insertSelective(con);
		}
		if (uApply > 0 && asset > 0 && occ > 0 && uCon > 0) {
			return new Json(true, "修改成功");
		}
		return new Json(false, "修改失败");
	}

	/**
	 * 提交流程
	 */
	@Override
	public Json supplementSubmit(String applyId, String submitTypeText, SessionRouter sessionRouter) {
		if (StringUtils.isBlank(applyId) || sessionRouter == null || StringUtils.isBlank(submitTypeText)) {
			return new Json(false, "参数有误!");
		}
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("addData").singleResult();
		if (task == null) {
			return new Json(false, "无权处理任务!");
		}
		taskService.complete(task.getId());
		String processId = task.getProcessInstanceId();
		// 提交表新增数据
		Json subJson = createSubmit(applyId, submitTypeText, sessionRouter, task, 4, "通过");
		// 修改申请主表状态
		Json upApplyJson = updateApplyTable(applyId, processId);
		if (upApplyJson.getSuccess() && subJson.getSuccess()) {
			return new Json(true, "提交成功");
		}
		return new Json(false, "提交失败");

	}

	/**
	 * 文件上传
	 * 
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@Override
	public Json createApplyUpload(String applyId, Integer applyTypeId, String applyTypeIdVal, MultipartFile multipartFile, SessionRouter session, HttpServletRequest request) throws IllegalStateException, IOException {
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数异常");
		}
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("addData").singleResult();
		if (task == null) {
			return new Json(false, "无权处理任务!");
		}
		String viewPath = uploadPathUtil.applyUrl();// 映射前缀
		String fileName = multipartFile.getOriginalFilename();// 文件名
		String fileType = fileName.substring(fileName.lastIndexOf("."));// 后缀
		List<String> filterFileType = new ArrayList<String>();
		filterFileType.add(".jpg");
		filterFileType.add(".png");
		filterFileType.add(".gif");
		filterFileType.add(".bmp");
		if (!StringUtils.isBlank(fileType) && !filterFileType.contains(fileType)) {
			return new Json(false, "图片格式不正确");
		}
		String saveName = UUID.randomUUID().toString().replace("-", "") + fileType;// 时间戳
		Long fSize = multipartFile.getSize();// 文件大小
		String fileUrl = request.getScheme() + "://" + SystemUtils.getLocalIp() + ":" + request.getServerPort();

		File f = new File(uploadPathUtil.saveApplyPath() + File.separator + applyId);
		if (!f.exists()) {
			f.mkdirs();
		}
		// 文件保存路径
		String savePath = uploadPathUtil.saveApplyPath() + File.separator + applyId + File.separator + saveName;
		// 映射地址
		String preView = viewPath + "/" + applyId + "/" + saveName;
		// 转存文件
		multipartFile.transferTo(new File(savePath));
		String annexId = PrimaryKeyUtil.getPrimaryKey();// 申请表ID

		Timestamp currentTime = DateUtil.getTimestamp();

		Annex annex = new Annex();
		annex.setAnxId(annexId);
		annex.setApplyId(applyId);
		annex.setFileName(fileName);
		annex.setSaveName(saveName);
		annex.setSavePath(savePath);
		annex.setPreView(preView);
		annex.setFileType(fileType);
		annex.setCreateDate(currentTime);
		annex.setEmpId(session.getEmpId());
		annex.setPreViewHost(fileUrl);
		annex.setApplyTypeId(applyTypeId);
		annex.setApplyTypeVal(applyTypeIdVal);
		annex.setFileSize(fSize);
		int aCount = annexMapper.insertSelective(annex);
		if (aCount > 0) {
			JSONObject obj = new JSONObject();
		      obj.put("preView", preView);
		      obj.put("anxId", annexId);
			return new Json(true, "上传成功",obj);
		}
		return new Json(false, "上传失败");
	}

	/**
	 * 判断当前节点
	 * 
	 * @param applyId
	 * @return
	 */
	@Override
	public Json isCurrentNode(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数异常!");
		}
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("addData").singleResult();
		if (task == null) {
			return new Json(false, "该申请已经进入下一节点，无法进行当前操作!");
		}
		return new Json(true, "ok");
	}

	/**
	 * 查询备注
	 * 
	 * @param applyId
	 * @return
	 */
	@Override
	public UserApply upSelectApplyById(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			return null;
		}
		return userApplyMapper.upSelectApplyById(applyId);
	}

	/**
	 * 补充资料列表
	 */
	@Override
	public Pager<UserApply> selectSupplementApply(Integer page, Integer rows, String custName, String loanId, String nodeStatus, String idNumber, String salesEmpName, String custMobile, SessionRouter sessionRouter) {
		String empId = sessionRouter.getEmpId();
		String storeId = sessionRouter.getStoreId();
		String deptId = sessionRouter.getDeptId();
		if (StringUtils.isBlank(empId) || StringUtils.isBlank(storeId) || StringUtils.isBlank(deptId)) {
			throw new AuditException("无法获取你的登录信息,请尝试重新登录!");
		}

		Map<String, Object> param = new HashMap<String, Object>();
		// 验证当前登录人是不是部门负责人
		String mgrId = sysEmployeeMapper.findDeptManager(deptId);
		Integer isMgr = 0;//
		if (empId.equals(mgrId)) {// 是部门的负责人
			isMgr = 1;
		}

		param.put("empId", empId);
		param.put("isMgr", isMgr);
		param.put("deptId", deptId);

		if (nodeStatus == null) {
			nodeStatus = "0";
		} else {
			param.put("nodeStatus", nodeStatus);
		}

		if (page == null || rows == null) {
			page = 1;
			rows = 30;
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

		if (!StringUtils.isBlank(salesEmpName)) {
			param.put("salesEmpName","%" +  salesEmpName + "%");
		}

		if (!StringUtils.isBlank(custMobile)) {
			param.put("custMobile", custMobile);
		}

		if (!StringUtils.isBlank(storeId)) {
			param.put("storeId", storeId);
		}

		List<UserApply> applyList = userApplyMapper.selectSupplementApply(param);
		int applyCount = userApplyMapper.selectSupplementCount(param);
		Pager<UserApply> applyPage = new Pager<UserApply>(applyList, applyCount);
		return applyPage;
	}

	@Override
	public SupplementSubmit submitFindById(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			return null;
		}
		return supplementSubmitMapper.findById(applyId);
	}

	/**
	 * 客户放弃
	 * 
	 * @param applyId
	 * @param submitTypeText
	 * @param sessionRouter
	 * @return
	 */
	@Override
	public Json supplementGiveUp(String applyId, String submitTypeText, SessionRouter sessionRouter) {
		if (StringUtils.isBlank(applyId) || StringUtils.isBlank(sessionRouter.getEmpId())) {
			return new Json(false, "参数有误!");
		}
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("addData").singleResult();
		if (task == null) {
			return new Json(false, "无权处理任务!");
		}
		/**
		 * 放弃流程
		 */
		JumpTaskCmd cmd = new JumpTaskCmd(task, "endEvent", JumpTaskCmd.WAIVER);
		TaskServiceImpl taskServiceImpl = (TaskServiceImpl) taskService;
		taskServiceImpl.getCommandExecutor().execute(cmd);

		String processId = task.getProcessInstanceId();
		Json upJson = updateApplyTable(applyId, processId);
		//更新主表状态
		userApplyMapper.updateProcessInstance(applyId, processId, JumpTaskCmd.WAIVER, "客户放弃");
		// 添加到补充资料
		Json createSupplement = createSubmit(applyId, submitTypeText, sessionRouter, task, 1, "客户放弃");
		if (!upJson.getSuccess()) {
			log.error(upJson.getMsg());
		}
		if (!createSupplement.getSuccess() && upJson.getSuccess()) {
			log.error(upJson.getMsg());
		}
		if (upJson.getSuccess() && createSupplement.getSuccess()) {
			return new Json(true, "客户放弃成功!");
		}
		return new Json(false, "客户放弃失败!");
	}

	private Json updateApplyTable(String applyId, String processId) {
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
		int applyCount = userApplyMapper.updateProcessInstance(applyId, processId, precessStatus, precessStatusVal);
		if (applyCount > 0) {
			return new Json(true, "保存成功");
		}
		return new Json(false, "保存失败");
	}

	private Json createSubmit(String applyId, String submitTypeText, SessionRouter sessionRouter, Task task, Integer handleId, String handleVal) {
		String pkey = PrimaryKeyUtil.getPrimaryKey();// 主键
		SupplementSubmit submitInfo = new SupplementSubmit();// 给提交表放入信息
		submitInfo.setApplyId(applyId);
		submitInfo.setSubPkId(pkey);
		submitInfo.setHandleOpinion(handleId);
		submitInfo.setHandleOpinionVal(handleVal);
		submitInfo.setSubmitDate(DateUtil.getTimestamp());
		submitInfo.setSubmitEmpId(sessionRouter.getEmpId());
		submitInfo.setSubmitEmpName(sessionRouter.getEmpName());
		submitInfo.setProcInstId(task.getProcessInstanceId());// 流程实例id
		submitInfo.setTaskId(task.getId());// 任务id
		submitInfo.setSubmitTypeText(submitTypeText);// 内容
		int suppCount = supplementSubmitMapper.insertSelective(submitInfo);
		if (suppCount > 0) {
			return new Json(true, "添加补充资料备注成功!");
		} else {
			return new Json(false, "添加补充资料备注失败！");
		}
	}

	@Override
	public Json claimTask(String applyId, String empId) {
		if (StringUtils.isBlank(applyId) || StringUtils.isBlank(empId)) {
			return new Json(false, "参数有误!");
		}
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("addData").singleResult();
		if (task == null) {
			return new Json(false, "不能处理,任务在其他节点!");
		}

		String assignee = task.getAssignee();
		if (!StringUtils.isBlank(assignee)) {//
			if (assignee.equals(empId)) {
				return new Json(true, "任务已签收成功!");
			} else {
				return new Json(false, "不能处理,任务已被他人签收!");
			}
		} else {
			if (ActivitiUtils.getClaimTaskSign(bpmnMapper, empId)){
				taskService.claim(task.getId(), empId);
			}else {
				return new Json(false, "不能签收，请先处理完手中的任务!");
			}
		}
		return new Json(true, "任务签收成功!");
	}

	@Override
	public List<Map<String,Object>> beforeSubmit(String applyId) {
		List<Map<String,Object>> beforeData = supplementSubmitMapper.beforeSubmit(applyId);
		return beforeData;
	}

	@Override
	public Json isConAudit(String applyId) {
		Map<String,Object> reNode = supplementSubmitMapper.isConAudit(applyId);
		if(reNode==null||reNode.get("TASK_DEF_KEY_")==null){
			return new Json(false,"数据有误!");
		}
		if("conAudit".equals(reNode.get("TASK_DEF_KEY_"))){//上一个节点为协议审批
			return new Json(false,"来源为协议审批退回，无法进行修改操作！");
		}
		return new Json(true,"上一个节点为协议审批之外");
	}

	@Override
	public Json multiUpload(MultipartFile[] file, String applyId,
			Integer applyTypeId, String applyTypeIdVal, SessionRouter session,
			HttpServletRequest request) {
		if (StringUtils.isBlank(applyId) || applyTypeId == null || StringUtils.isBlank(applyTypeIdVal)) {
			return new Json(false, "参数异常");
		}
		//判断单子节点
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("addData").singleResult();
		if (task == null) {
			return new Json(false, "无权处理任务!");
		}
		JSONArray resultArray = new JSONArray();//返回json结果集
		for (MultipartFile multipartFile : file) {
			String viewPath = uploadPathUtil.applyUrl();// 映射前缀
			String fileName = multipartFile.getOriginalFilename();// 文件名
			String fileType = fileName.substring(fileName.lastIndexOf("."));// 后缀
			List<String> filterFileType = new ArrayList<String>();
			filterFileType.add(".jpg");
			filterFileType.add(".png");
			filterFileType.add(".gif");
			filterFileType.add(".bmp");
			if (!StringUtils.isBlank(fileType) && !filterFileType.contains(fileType)) {
				return new Json(false, "图片格式不正确");
			}
			String saveName = UUID.randomUUID().toString().replace("-", "") + fileType;// 时间戳
			Long fSize = multipartFile.getSize();// 文件大小
			String fileUrl = request.getScheme() + "://" + SystemUtils.getLocalIp() + ":" + request.getServerPort();

			File f = new File(uploadPathUtil.saveApplyPath() + File.separator + applyId);
			if (!f.exists()) {
				f.mkdirs();
			}

			// 文件保存路径
			String savePath = uploadPathUtil.saveApplyPath() + File.separator + applyId + File.separator + saveName;
			// 映射地址
			String preView = viewPath + "/" + applyId + "/" + saveName;
			try {
				// 转存文件
				multipartFile.transferTo(new File(savePath));
				String annexId = PrimaryKeyUtil.getPrimaryKey();// 申请表ID
				Timestamp currentTime = DateUtil.getTimestamp();
				Annex annex = new Annex();
				annex.setAnxId(annexId);
				annex.setApplyId(applyId);
				annex.setFileName(fileName);
				annex.setSaveName(saveName);
				annex.setSavePath(savePath);
				annex.setPreView(preView);
				annex.setFileType(fileType);
				annex.setPreViewHost(fileUrl);
				annex.setCreateDate(currentTime);
				annex.setEmpId(session.getEmpId());
				annex.setApplyTypeId(applyTypeId);
				annex.setApplyTypeVal(applyTypeIdVal);
				annex.setFileSize(fSize);
				int aCount = annexMapper.insertSelective(annex);
				if (aCount > 0) {
					JSONObject obj = new JSONObject();
						obj.put("preView", preView);
						obj.put("anxId", annexId);
						obj.put("saveName", saveName);
						obj.put("oldName", fileName);
				      resultArray.add(obj);
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
				log.error("【补充资料 文件上传异常 批量上传】", e);
			} catch (IOException e) {
				e.printStackTrace();
				log.error("【补充资料 文件上传异常 批量上传】", e);
			}
		}
		return new Json(true, "保存成功！", resultArray);
	}
}