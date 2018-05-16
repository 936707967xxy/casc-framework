/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.sales;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hoomsun.app.api.model.AfreshProtoinfo;
import com.hoomsun.app.api.server.inter.AfreshProtoinfoServerI;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.AuthenticationServiceUtil;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.common.util.UploadPathUtil;
import com.hoomsun.contract.util.NumberToCN;
import com.hoomsun.core.model.Bankinfo;
import com.hoomsun.core.model.HxbRate;
import com.hoomsun.core.model.HxbReplaymentPlan;
import com.hoomsun.core.model.SysContract;
import com.hoomsun.core.model.SysProduct;
import com.hoomsun.core.model.SysRepaymentPlan;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.server.inter.BankinfoServerI;
import com.hoomsun.core.server.inter.HxbRateServerI;
import com.hoomsun.core.server.inter.HxbReplaymentPlanServerI;
import com.hoomsun.core.server.inter.SerialNumberServerI;
import com.hoomsun.core.server.inter.SysContractServerI;
import com.hoomsun.core.server.inter.SysProductServerI;
import com.hoomsun.core.server.inter.SysRepaymentPlanServerI;
import com.hoomsun.core.util.BillsDateRangeCfg;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.core.util.RepaymentMethods;
import com.hoomsun.core.util.RepaymentMethodsHXB;
import com.hoomsun.csas.audit.model.ContractCheck;
import com.hoomsun.csas.audit.model.FinalAudit;
import com.hoomsun.csas.audit.server.inter.ContractAuditServerI;
import com.hoomsun.csas.audit.server.inter.FinalAuditServerI;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.vo.MakingVo;
import com.hoomsun.csas.sales.api.server.inter.MakingOpinionServerI;
import com.hoomsun.csas.sales.api.server.inter.MakingServerI;
import com.hoomsun.csas.sales.api.server.inter.UserApplyServerI;
import com.hoomsun.pdf.CreatePdfFile;

/**
 * 作者：liusong <br>
 * 创建时间：2017年12月4日 <br>
 * 描述：协议拟制控制层
 */
@Controller
public class MakingController {
	@Autowired
	private BillsDateRangeCfg dateRangeCfg;
	@Autowired
	private MakingServerI makingServer;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FinalAuditServerI finalAuditServer;
	@Autowired
	private SysProductServerI productServer;
	@Autowired
	private UserApplyServerI userApplyServer;
	@Autowired
	private SysContractServerI contractServer;
	@Autowired
	private SysRepaymentPlanServerI repaymentPlanServer;
	@Autowired
	private SerialNumberServerI serialNumberServer;
	@Autowired
	private UploadPathUtil uploadPathUtil;
	@Autowired
	private HxbReplaymentPlanServerI hxbReplaymentPlanServer;
	@Autowired
	private BankinfoServerI bankinfoServer;
	@Autowired
	private AfreshProtoinfoServerI afreshProtoinfoServerI;
	@Autowired
	private HxbRateServerI hxbRateServer;
	@Autowired
	private MakingOpinionServerI makingOpinionServer;
	@Autowired
	private ContractAuditServerI contractAuditServer;

	@RequestMapping(value = "/sys/bank/banks.do")
	@ResponseBody
	public List<Bankinfo> selectApply() {
		return bankinfoServer.findAllData();
	}

	@RequestMapping(value = "/sys/opinion/remark.do")
	@ResponseBody
	public String selectRemark(String applyId) {
		return makingOpinionServer.selectByApplyId(applyId);
	}

	@RequestMapping(value = "/sys/rollback/handleRemarks.do")
	@ResponseBody
	public String handleRemarks(String applyId) {
		return makingOpinionServer.selectByRollBackOpinion(applyId);
	}

	@RequestMapping(value = "/sys/rollback/listremarks.do")
	@ResponseBody
	public List<ContractCheck> listRemarks(String applyId) {
		return contractAuditServer.findRollBack(applyId);
	}

	/**
	 * 作者：liusong <br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 查询协议拟制所有单子
	 * 
	 * @param page
	 * @param rows
	 * @param custName
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sys/making/pager.do")
	@ResponseBody
	public Pager<MakingVo> findPage(Integer page, Integer rows, String custName, String loanId, String nodeStatus, String idNumber, String node, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		if (sessionRouter == null) {
			return null;
		}
		String empId = sessionRouter.getEmpId();
		String deptId = sessionRouter.getDeptId();
		String storeId = sessionRouter.getStoreId();
		Pager<MakingVo> applyPager = makingServer.findPage(page, rows, custName, idNumber, loanId, empId, deptId, storeId, nodeStatus, node);
		return applyPager;
	}

	// 初始化页面数据
	@RequestMapping("/sys/making/init.do")
	@ResponseBody
	public SysContract findApply(String applyId, HttpServletRequest request) {
		if (StringUtils.isBlank(applyId)) {
			return null;
		}
		SysContract contract = contractServer.findByApplyId(applyId);
		if (contract == null || StringUtils.isBlank(contract.getConId())) {
			MakingVo makingVo = makingServer.selectByApplyId(applyId);
			if (makingVo != null) {
				contract = new SysContract();
				String bankId = contract.getBankId();
				if (bankId == null || bankId.equals("")) {
					AfreshProtoinfo info = afreshProtoinfoServerI.selectByFkIdAndIsDefault(applyId);
					if (info != null) {
						String accbankid = info.getAccbankid();
						String accbankname = info.getAccbankname();
						String accno = info.getAccno();
						String branchnameProvName = info.getBranchnameProvName();
						String branchnameCityName = info.getBranchnameCityName();
						String branchnameAddress = info.getBranchnameAddress();
						String mobile = info.getMobile();
						String branchnameProvCode = info.getBranchnameProvCode();
						String branchnameCityCode = info.getBranchnameCityCode();

						contract.setBankPhoneNo(mobile);
						contract.setBankId(accbankid);
						contract.setBank(accbankname);
						contract.setBankNo(accno);
						contract.setProName(branchnameProvName);
						contract.setProId(branchnameProvCode);
						contract.setCityId(branchnameCityCode);
						contract.setCityName(branchnameCityName);
						contract.setBackBranch(branchnameAddress);
					}
				}
				contract.setApplyMoney(makingVo.getApplyAmount());
				contract.setCustName(makingVo.getCustName());
				contract.setIdNumber(makingVo.getIdNumber());
				contract.setProductName(makingVo.getProductName());
				contract.setProductId(makingVo.getProductId());
				contract.setRealRate(makingVo.getRealRate());
				contract.setProductPeriod(makingVo.getProductPeriod());
				contract.setLoanAmount(makingVo.getApprovedAmount());
			}
		}
		return contract;
	}

	// 签收
	@RequestMapping("/sys/making/claim.do")
	@ResponseBody
	public Json claimTask(String applyId, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		String empId = session.getEmpId();
		return makingServer.claimTask(applyId, empId);
	}

	// 验证是否已经签收
	@RequestMapping("/sys/making/checkclaim.do")
	@ResponseBody
	public Json checkClaimTask(String applyId, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		String empId = session.getEmpId();
		return makingServer.checkClaim(applyId, empId);
	}

	// 撤回
	@RequestMapping(value = "/sys/making/withdraw.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json withdraw(String applyId, String remark, HttpServletRequest request) {
		if ("" != remark && "3".equals(remark)) {
			remark = "撤回";
		}
		SessionRouter session = LoginUtil.getLoginSession(request);
		return makingServer.withdraw(applyId, session, remark);
	}

	// 拒贷
	@RequestMapping(value = "/sys/making/reject.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json reject(String applyId, String remark, HttpServletRequest request) throws UnsupportedEncodingException {
		remark = new String(remark.getBytes("ISO8859-1"), "UTF-8");
		SessionRouter session = LoginUtil.getLoginSession(request);
		return makingServer.reject(applyId, session, remark);
	}

	// 退回
	@RequestMapping(value = "/sys/making/rollback.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json rollback(String applyId, String remark, HttpServletRequest request) throws UnsupportedEncodingException {
		remark = new String(remark.getBytes("ISO8859-1"), "UTF-8");
		SessionRouter session = LoginUtil.getLoginSession(request);
		return makingServer.rollback(applyId, session, remark);
	}

	// 客户放弃
	@RequestMapping(value = "/sys/making/waiver.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json waiver(String applyId, String remark, HttpServletRequest request) throws UnsupportedEncodingException {
		SessionRouter session = LoginUtil.getLoginSession(request);
		return makingServer.waiver(applyId, session, remark);
	}

	// 选择签约日期并生成还款计划
	@RequestMapping(value = "/sys/making/repayment.do", method = { RequestMethod.POST })
	@ResponseBody
	public SysContract repayment(String proId, String proName, String cityId, String cityName, String bankId, String backBranch, String backBranchAddr, String bankNo, String bankPhoneNo, String applyId, Date dates,
			HttpServletRequest request) {

		if (dates == null) {
			dates = new Date();
		}

		FinalAudit finalAudit = finalAuditServer.findByApplyId(applyId);
		BigDecimal auditMoney = finalAudit.getApprovedAmount();// 批准金额
		String prodId = finalAudit.getProductId();
		SysProduct product = productServer.findAllById(prodId);
		Integer isOnline = finalAudit.getIsOnline();
		Integer sterm = finalAudit.getProductPeriod();
		SysContract contract = new SysContract();
		List<HxbReplaymentPlan> planHxb = hxbReplaymentPlanServer.selectByApplyId(applyId);

		if (planHxb != null && planHxb.size() > 0 && isOnline == 1 && planHxb.size() == sterm) {// 确定是线上进来走红小宝的单子
			contract.setMonthMoney(planHxb.get(0).getAmount());
			HxbRate hxbRa = hxbRateServer.selectByApplyId(applyId);
			BigDecimal hxbServiceFee = hxbRa.getHxbServiceFee();// 服务费(红小宝服务费)
			BigDecimal channelServiceFee = hxbRa.getChannelServiceFee();// 服务费（线下服务费）
			BigDecimal creditServiceFee = hxbRa.getCreditServiceFee();// 审核费(第三方服务费)
			BigDecimal totalServiceFee = hxbServiceFee.add(creditServiceFee).add(channelServiceFee);
			BigDecimal total = auditMoney.add(totalServiceFee);
			contract.setConAmount(total);// 合同金额
			contract = RepaymentMethodsHXB.computeRepaymentPlan(total, planHxb.get(0).getAmount(), applyId, auditMoney, dates, product, dateRangeCfg, hxbReplaymentPlanServer, isOnline);
			contract.setSumFee(totalServiceFee);
		} else {// 线下进件
			contract = RepaymentMethods.computeRepaymentPlan(auditMoney, dates, product, dateRangeCfg, isOnline);
		}

		contract.setIsOnline(isOnline);
		contract.setIrrVal(finalAudit.getIrrVal());
		Bankinfo bankInfo = bankinfoServer.bankinfo(bankId);
		contract.setBankId(bankId);
		contract.setBank(bankInfo.getBankname());
		contract.setProId(proId);
		contract.setProName(proName);
		contract.setCityId(cityId);
		contract.setCityName(cityName);
		contract.setBackBranch(backBranch);
		contract.setBackBranchAddr(backBranchAddr);
		contract.setBankNo(bankNo);
		contract.setBankPhoneNo(bankPhoneNo);
		MakingVo makingVo = makingServer.selectByApplyId(applyId);
		if (makingVo != null) {
			contract.setApplyId(makingVo.getApplyId());
			contract.setLoanId(makingVo.getLoanId());
			contract.setApplyMoney(makingVo.getApplyAmount());
			contract.setCustName(makingVo.getCustName());
			contract.setIdNumber(makingVo.getIdNumber());
			contract.setProductName(makingVo.getProductName());
			contract.setProductId(makingVo.getProductId());
			contract.setProductPeriod(makingVo.getProductPeriod());
			contract.setLoanAmount(makingVo.getApprovedAmount());
		}
		return contract;
	}

	// 天行校验开户行和卡号是否匹配
	@RequestMapping(value = "/sys/making/tianxing.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json tianxing(String bankId, String bankNo) throws Exception {
		Bankinfo bankInfo = bankinfoServer.bankinfo(bankId);
		Map<String, String> resultMap = AuthenticationServiceUtil.requestAccInformation(bankNo);
		String ACCBANKNAME = resultMap.get("ACCBANKNAME");
		String accBankName = "";
		if (ACCBANKNAME.equals("建设银行")) {
			accBankName = "中国建设银行";
		}
		if (ACCBANKNAME.equals("中国银行")) {
			accBankName = "中国银行";
		}
		if (ACCBANKNAME.equals("工商银行")) {
			accBankName = "中国工商银行";
		}
		if (ACCBANKNAME.equals("农业银行")) {
			accBankName = "中国农业银行";
		}
		if (ACCBANKNAME.equals("邮政储蓄") || ACCBANKNAME.equals("邮储银行")) {
			accBankName = "中国邮政储蓄银行";
		}
		if (ACCBANKNAME.equals("光大银行")) {
			accBankName = "中国光大银行";
		}
		if (ACCBANKNAME.equals("民生银行")) {
			accBankName = "中国民生银行";
		}
		if (ACCBANKNAME.equals("广发银行")) {
			accBankName = "广发银行";
		}
		if (ACCBANKNAME.equals("招商银行")) {
			accBankName = "招商银行";
		}
		if (ACCBANKNAME.equals("兴业银行")) {
			accBankName = "兴业银行";
		}
		if (ACCBANKNAME.equals("恒丰银行")) {
			accBankName = "恒丰银行";
		}
		if (ACCBANKNAME.equals("浦东发展银行")) {
			accBankName = "浦发银行";
		}
		if (ACCBANKNAME.equals("交通银行")) {
			accBankName = "交通银行";
		}
		if (ACCBANKNAME.equals("平安银行")) {
			accBankName = "深圳平安银行";
		}
		if (ACCBANKNAME.equals("北京银行")) {
			accBankName = "北京银行";
		}
		if (ACCBANKNAME.equals("华夏银行")) {
			accBankName = "华夏银行";
		}
		String bankName = bankInfo.getBankname();
		if (bankName.equals(accBankName)) {
			return new Json(true, "银行卡号与开户行匹配");
		} else {
			return new Json(false, "银行卡号与开户行匹配失败，请重新确认！！！");
		}
	}

	// 生成合同数据
	@RequestMapping(value = "/sys/making/create.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json saveConstact(@RequestBody SysContract contract, HttpServletRequest request) throws Exception {
		if (null != contract) {
			SysProduct prod = productServer.findproductbyId(contract.getProductId());
			if (prod != null) {
				contract.setProdAlias(prod.getProdAlias());
			} else {
				contract.setProdAlias(contract.getProductName());
			}
			SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
			if (sessionRouter != null) {
				String conNo = serialNumberServer.createNumber("1", sessionRouter.getDeptNo());
				contract.setConNo(conNo);
				contract.setCreateEmpId(sessionRouter.getEmpId());
				contract.setCreateEmpName(sessionRouter.getEmpName());
			}
			contract.setConStatus(0);
			contract.setCreateTime(DateUtil.getSqlDate());
			Integer isOnline = contract.getIsOnline();
			if (isOnline != null && isOnline == 1) {// 表示线上进件，进行恒丰鉴权绑卡
				// TreeMap<String, String> map =
				// JQBKUtils.jqbka(contract.getCustName(),
				// contract.getIdNumber(), contract.getBankNo(),
				// contract.getBankPhoneNo(), RandomString.randomString(20));
				// String message = map.get("message");
				// String errorMessage = map.get("errorMessage");
				// if (message.equals("0")) {
				return contractServer.createContract(contract);
				// } else {
				// return new Json(false, "恒丰鉴权失败，" + errorMessage);
				// }
			} else {// 线下操作
				return contractServer.createContract(contract);
			}
		} else {
			return new Json(false, "参数异常!!!");
		}
	}

	// 确定保存合同数据并触发流程
	@RequestMapping(value = "/sys/making/constact.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json constact(String remark, String applyId, HttpServletRequest request) {
		int shu = makingServer.findByapplyIdAndAnnexType(applyId);
		if (shu == 0) {
			return new Json(false, "请先上传合同资料后进行提交!!!");
		}
		int count = contractServer.selectByApplyIdCount(applyId);
		if (count == 0) {
			return new Json(false, "请先拟制之后再执行提交操作!!!");
		}
		SessionRouter session = LoginUtil.getLoginSession(request);
		return makingServer.completeTask(applyId, session, remark);
	}

	// 详情页面合同展示
	@RequestMapping("/sys/making/details.do")
	@ResponseBody
	public SysContract contracts(String applyId, HttpServletRequest request) {
		SysContract contract = contractServer.selectByApplyId(applyId);
		return contract;
	}

	// 详情页面还款计划展示
	@RequestMapping("/sys/making/detailReplay.do")
	@ResponseBody
	public List<SysRepaymentPlan> repaymentPlan(String applyId, HttpServletRequest request) {
		List<SysRepaymentPlan> repaymentPlan = repaymentPlanServer.findByApplyId(applyId);
		return repaymentPlan;
	}

	// 转办方法执行
	@RequestMapping(value = "/sys/making/savecomplaint.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json complaint(String applyId, String userId, HttpServletRequest request) {
		if (StringUtils.isAllBlank(applyId, userId)) {
			return new Json(false, "参数异常,转办失败!");
		}

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("makeCon").singleResult();
		if (null != task) {
			taskService.setAssignee(task.getId(), userId);
			return new Json(true, "转办成功!");
		} else {
			return new Json(false, "任务已经处理,转办失败!");
		}
	}

	/**
	 * 作者：liusong <br>
	 * 创建时间：2017年12月22日 <br>
	 * 描述： 合同管理上传签约影像
	 * 
	 * @param file
	 * @param applyId
	 * @param request
	 * @return
	 */
	@RequestMapping("/sys/making/upload.do")
	@ResponseBody
	public synchronized Json upload(@RequestParam("file") MultipartFile file, String applyId, Integer imgId, String imgIdVal, HttpServletRequest request) {
		String context = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String loginEmp = sessionRouter.getEmpId();
		String loginName = sessionRouter.getEmpName();
		return makingServer.upload(file, applyId, imgId, imgIdVal, loginEmp, loginName, context);

	}
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月9日 <br>
	 * 描述： 文件批量上传
	 * @param files
	 * @param applyId
	 * @param imgId
	 * @param imgIdVal
	 * @param request
	 * @return
	 */
	@RequestMapping("/sys/making/multiupload.do")
	@ResponseBody
	public Json multiUpload(@RequestParam("file") MultipartFile[] files, String applyId, Integer imgId, String imgIdVal, HttpServletRequest request) {
		String context = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String loginEmp = sessionRouter.getEmpId();
		String loginName = sessionRouter.getEmpName();
		return makingServer.multiUpload(files, applyId, imgId, imgIdVal, loginEmp, loginName, context);
	}
	
	
	
	@RequestMapping("/sys/makingannex/delete.do")
	@ResponseBody
	public Json delete(String id, HttpServletRequest request) {
		return makingServer.deleteAnnex(id);
	}

	// 判断是否在当前节点
	@RequestMapping(value = "/sys/making/selecttask.do", method = RequestMethod.POST)
	@ResponseBody
	public Json selectTask(String applyId) {
		return makingServer.selectTask(applyId);
	}

	// 处理小数
	public static BigDecimal scale(BigDecimal data, Integer scale) {
		scale = scale == null ? 2 : scale;
		return data.setScale(scale, BigDecimal.ROUND_HALF_UP);
	}

	// 下载合同之前判断数据库是否有数据存在
	@RequestMapping(value = "/sys/making/selectcontracts.do", method = RequestMethod.POST)
	@ResponseBody
	public Json selectcontracts(String applyId) {
		SysContract contract = contractServer.findByApplyId(applyId);
		if (null == contract) {
			return new Json(false, "请先协议拟制提交之后再下载合同！！！");
		} else {
			return new Json(true, "ok ！！！");
		}
	}

	// 还款管理书（线下）
	@RequestMapping(value = "/sys/making/downloadone.do", method = { RequestMethod.GET })
	@ResponseBody
	public void downloadone(String applyId, HttpServletRequest request, HttpServletResponse response) {
		SysContract contract = contractServer.findByApplyId(applyId);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("custName", contract.getCustName());
		BigDecimal payMonth = scale(contract.getMonthMoney(), 2);
		param.put("payMonth", payMonth);// 月还款额
		param.put("amt", payMonth);// 本息
		String conNo = contract.getConNo();
		List<Map<String, Object>> maps = new ArrayList<>();
		List<SysRepaymentPlan> plans = contract.getRepaymentPlans();
		for (SysRepaymentPlan pl : plans) {
			Map<String, Object> plan = new HashMap<String, Object>();
			BigDecimal shouldAmt = scale(pl.getShouldAmt(), 2);
			BigDecimal bal = scale(pl.getBal(), 2);
			BigDecimal advanceMoney = scale(pl.getAdvanceMoney(), 2);
			plan.put("shouldTerm", pl.getShouldTerm());
			plan.put("shouldDate", pl.getStrShouldDate());
			plan.put("shouldAmt", shouldAmt);
			plan.put("bal", bal);
			plan.put("advanceMoney", advanceMoney);
			maps.add(plan);
		}
		param.put("repaymentPlans", maps);
		Calendar now = Calendar.getInstance();
		String savePath = uploadPathUtil.saveContract() + File.separator + now.get(Calendar.YEAR) + File.separator + (now.get(Calendar.MONTH) + 1) + File.separator + now.get(Calendar.DAY_OF_MONTH);
		File savefile = new File(savePath);
		if (!savefile.exists()) {
			savefile.mkdirs();
		}
		String flag = "还款管理书";
		CreatePdfFile.createObjectToPDF(request.getSession().getServletContext().getRealPath("/model/contract_plan.xml"), savePath + File.separator + conNo + flag + ".pdf",
				request.getSession().getServletContext().getRealPath("/model/simfang.ttf"), "", param);
		downloadFile(conNo, flag, request, response);
	}

	// 借款协议书（线下）
	@RequestMapping(value = "/sys/making/downloadtwo.do", method = { RequestMethod.GET })
	@ResponseBody
	public void downloadtwo(String applyId, HttpServletRequest request, HttpServletResponse response) {
		// SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		// String nowtime = sf.format(new Date());
		SysContract contract = contractServer.selectByApplyId(applyId);
		UserApply userApply = userApplyServer.findApplyById(applyId);
		String conNo = contract.getConNo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String nowtime = sdf.format(contract.getSignTime());
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("conNo", contract.getConNo());// 合同编号
		dataMap.put("year", nowtime.substring(0, 4));// 年
		dataMap.put("month", nowtime.substring(4, 6));// 月
		dataMap.put("day", nowtime.substring(6, 8));// 日
		dataMap.put("signAddr", contract.getSignAddr());// 门店地址
		dataMap.put("idNumber", contract.getIdNumber());// 身份证号码
		dataMap.put("houseAddress", userApply.getHouseAddress());// 现地址
		dataMap.put("conAmount", NumberToCN.number2CNMontrayUnit(contract.getConAmount()));// 借款本金数额
		dataMap.put("shouldAmt", NumberToCN.number2CNMontrayUnit(contract.getMonthMoney()));// 每月还款额
		dataMap.put("productPeriod", contract.getProductPeriod());// 还款分期月数
		dataMap.put("conAmountNo", contract.getConAmount());// 借款本金数额（小写）
		Long loanTotalAmt = contract.getConAmount().multiply(new BigDecimal("100")).longValue();// 本金

		String loanTotalAmtStr = convertAmt(loanTotalAmt.toString());
		for (int i = 9; i >= 0; i--) {
			if (loanTotalAmtStr.length() - i >= 1) {
				char c = loanTotalAmtStr.charAt(loanTotalAmtStr.length() - i - 1);
				dataMap.put("con" + i, c);
			} else {
				dataMap.put("con" + i, " ");
			}
		}
		dataMap.put("con" + loanTotalAmtStr.length(), "￥");
		dataMap.put("shouldAmtNo", contract.getMonthMoney());// 每月还款额（小写）
		Long shouldAmtNo = contract.getMonthMoney().multiply(new BigDecimal("100")).longValue();// 本金
		String loanAmtStr = convertAmt(shouldAmtNo.toString());
		for (int i = 9; i >= 0; i--) {
			if (loanAmtStr.length() - i >= 1) {
				char c = loanAmtStr.charAt(loanAmtStr.length() - i - 1);
				dataMap.put("amt" + i, c);
			} else {
				dataMap.put("amt" + i, " ");
			}
		}
		dataMap.put("amt" + loanAmtStr.length(), "￥");
		dataMap.put("productPeriod", contract.getProductPeriod());// 还款分期月数
		dataMap.put("payDate", contract.getPayDate());// 还款日
		dataMap.put("startTime", contract.getStrStartDate());// 还款起始日期
		dataMap.put("endTime", contract.getStrEndDate());// 还款终止日期
		dataMap.put("custName", contract.getCustName());// 户名
		dataMap.put("bankNo", contract.getBankNo());// 账户
		dataMap.put("bank", contract.getBank());// 开户行
		Calendar now = Calendar.getInstance();
		String savePath = uploadPathUtil.saveContract() + File.separator + now.get(Calendar.YEAR) + File.separator + (now.get(Calendar.MONTH) + 1) + File.separator + now.get(Calendar.DAY_OF_MONTH);
		File savefile = new File(savePath);
		if (!savefile.exists()) {
			savefile.mkdirs();
		}
		String flag = "借款协议书";
		CreatePdfFile.createObjectToPDF(request.getSession().getServletContext().getRealPath("/model/contract_two.xml"), savePath + File.separator + conNo + flag + ".pdf",
				request.getSession().getServletContext().getRealPath("/model/simfang.ttf"), "", dataMap);
		downloadFile(conNo, flag, request, response);
	}

	// 委托扣款授权书（线下）
	@RequestMapping(value = "/sys/making/downloadthree.do", method = { RequestMethod.GET })
	@ResponseBody
	public void downloadthree(String applyId, HttpServletRequest request, HttpServletResponse response) {
		// SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		// String nowtime = sf.format(new Date());
		SysContract contract = contractServer.selectByApplyId(applyId);
		String conNo = contract.getConNo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String nowtime = sdf.format(contract.getSignTime());
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("conNo", contract.getConNo());// 合同编号
		data.put("year", nowtime.substring(0, 4));// 年
		data.put("month", nowtime.substring(4, 6));// 月
		data.put("day", nowtime.substring(6, 8));// 日
		data.put("custName", contract.getCustName());// 银行卡户名
		data.put("bank", contract.getBank());// 银行卡开户银行
		data.put("bankNo", contract.getBankNo());// 银行卡账号
		data.put("idNumber", contract.getIdNumber());// 身份证号码
		data.put("bankPhoneNo", contract.getBankPhoneNo());// 银行卡预留手机号
		Calendar now = Calendar.getInstance();
		String savePath = uploadPathUtil.saveContract() + File.separator + now.get(Calendar.YEAR) + File.separator + (now.get(Calendar.MONTH) + 1) + File.separator + now.get(Calendar.DAY_OF_MONTH);
		File savefile = new File(savePath);
		if (!savefile.exists()) {
			savefile.mkdirs();
		}
		String flag = "授权委托书";
		CreatePdfFile.createObjectToPDF(request.getSession().getServletContext().getRealPath("/model/contract_deductions.xml"), savePath + File.separator + conNo + flag + ".pdf",
				request.getSession().getServletContext().getRealPath("/model/simfang.ttf"), "", data);
		downloadFile(conNo, flag, request, response);
	}

	// 信用咨询及管理服务协议（线下）
	@RequestMapping(value = "/sys/making/downloadfour.do", method = { RequestMethod.GET })
	@ResponseBody
	public void downloadfour(String applyId, HttpServletRequest request, HttpServletResponse response) {
		// SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		// String nowtime = sf.format(new Date());
		SysContract contract = contractServer.selectByApplyId(applyId);
		UserApply userApply = userApplyServer.findApplyById(applyId);
		String conNo = contract.getConNo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String nowtime = sdf.format(contract.getSignTime());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("year", nowtime.substring(0, 4));// 年
		map.put("month", nowtime.substring(4, 6));// 月
		map.put("day", nowtime.substring(6, 8));// 日
		map.put("idNumber", contract.getIdNumber());// 身份证号码
		map.put("houseAddress", userApply.getHouseAddress());// 现地址
		BigDecimal loanAmount = scale(contract.getLoanAmount(), 2);
		map.put("loanAmount", loanAmount);// 借款金额
		map.put("loanId", contract.getLoanId());// 借款编号
		map.put("conNo", conNo);
		String serviceFee = contract.getServiceFee();
		JSONObject json = JSONObject.parseObject(serviceFee);
		if (json.size() == 3 && json.getString("service") != null && json.getString("audit") != null && json.getString("consult") != null) {
			String service = json.getString("service");// 服务费
			BigDecimal aa = new BigDecimal(service);
			String audit = json.getString("audit");// 审核费
			BigDecimal bb = new BigDecimal(audit);
			String consult = json.getString("consult");// 咨询费
			BigDecimal cc = new BigDecimal(consult);
			map.put("consulfee", NumberToCN.number2CNMontrayUnit(cc));// 咨询费（大写）
			map.put("auditfee", NumberToCN.number2CNMontrayUnit(bb));// 审核费（大写）
			map.put("servicefee", NumberToCN.number2CNMontrayUnit(aa));// 服务费（大写）
			map.put("consul", cc);// 咨询费（大写）
			map.put("audit", bb);// 审核费（大写）
			map.put("service", aa);// 服务费（大写）
			BigDecimal totalFee = aa.add(bb).add(cc);// 死值
			map.put("totalFee", NumberToCN.number2CNMontrayUnit(totalFee));// 总费用（大写）
			map.put("total", totalFee);
		} else {
			map.put("consulfee", "");// 咨询费（大写）
			map.put("auditfee", "");// 审核费（大写）
			map.put("servicefee", "");// 服务费（大写）
			map.put("consul", "");// 咨询费（大写）
			map.put("audit", "");// 审核费（大写）
			map.put("service", "");// 服务费（大写）
			map.put("totalFee", "");// 总费用（大写）
			map.put("total", "");
		}
		map.put("custName", userApply.getCustName());// 户名
		map.put("bank", contract.getBank() + contract.getBackBranch() + contract.getBackBranchAddr());// 开户银行
		map.put("bankNo", contract.getBankNo());// 账号
		Calendar now = Calendar.getInstance();
		String savePath = uploadPathUtil.saveContract() + File.separator + now.get(Calendar.YEAR) + File.separator + (now.get(Calendar.MONTH) + 1) + File.separator + now.get(Calendar.DAY_OF_MONTH);
		File savefile = new File(savePath);
		if (!savefile.exists()) {
			savefile.mkdirs();
		}
		String flag = "信用咨询与管理服务协议书";
		CreatePdfFile.createObjectToPDF(request.getSession().getServletContext().getRealPath("/model/contract_four.xml"), savePath + File.separator + conNo + flag + ".pdf",
				request.getSession().getServletContext().getRealPath("/model/simfang.ttf"), "", map);
		downloadFile(conNo, flag, request, response);
	}

	/***
	 * 
	 * 
	 * 线上合同下载代码区域
	 * 
	 * 
	 * 作者：liusong <br>
	 * 创建时间：2018年1月5日 <br>
	 * 描述：
	 * 
	 * @param amt
	 * @return
	 */

	// 还款管理服务说明书(线上-红小宝)
	@RequestMapping(value = "/sys/making/downloadhxbone.do", method = { RequestMethod.GET })
	@ResponseBody
	public void downloadhxbone(String applyId, HttpServletRequest request, HttpServletResponse response) {
		SysContract contract = contractServer.findByApplyId(applyId);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("custName", contract.getCustName());
		BigDecimal payMonth = scale(contract.getMonthMoney(), 2);
		param.put("payMonth", payMonth);// 月还款额
		param.put("payDate", contract.getPayDate());// 还款日
		Integer term = contract.getProductPeriod();// 期数
		// 垫付服务费用(1天)
		BigDecimal fOneMoney = (payMonth.multiply(new BigDecimal(term)).multiply(new BigDecimal(5)).divide(new BigDecimal(10000))).setScale(2, BigDecimal.ROUND_HALF_UP);
		// 垫付服务费用(16天)
		BigDecimal fSixteenMoney = (payMonth.multiply(new BigDecimal(term)).multiply(new BigDecimal(5)).multiply(new BigDecimal(16)).divide(new BigDecimal(10000))).setScale(2, BigDecimal.ROUND_HALF_UP);
		// 逾期违约金(1天,16天)
		BigDecimal oveOneMoney = (payMonth.multiply(new BigDecimal(10)).divide(new BigDecimal(100))).setScale(2, BigDecimal.ROUND_HALF_UP);
		param.put("fOneMoney", fOneMoney);
		param.put("fSixteenMoney", fSixteenMoney);
		param.put("oveOneMoney", oveOneMoney);
		String conNo = contract.getConNo();
		List<Map<String, Object>> maps = new ArrayList<>();
		List<SysRepaymentPlan> plans = contract.getRepaymentPlans();
		for (SysRepaymentPlan pl : plans) {
			Map<String, Object> plan = new HashMap<String, Object>();
			BigDecimal shouldAmt = scale(pl.getShouldAmt(), 2);
			BigDecimal bal = scale(pl.getEndBal(), 2);
			BigDecimal advanceMoney = scale(pl.getAdvanceMoney(), 2);
			plan.put("shouldTerm", pl.getShouldTerm());
			plan.put("shouldDate", pl.getStrShouldDate());
			plan.put("shouldAmt", shouldAmt);
			plan.put("bal", bal);
			plan.put("advanceMoney", advanceMoney);
			maps.add(plan);
		}
		param.put("repaymentPlans", maps);
		// List<Map<String, Object>> maps = new ArrayList<>();
		// List<HxbReplaymentPlan> plans=
		// hxbReplaymentPlanServer.selectByApplyId(applyId);
		// for (HxbReplaymentPlan pl : plans) {
		// Map<String, Object> plan = new HashMap<String, Object>();
		// BigDecimal shouldAmt = scale(pl.getAmount(), 2);
		// BigDecimal bal = scale(pl.getInitialPrincipal(), 2);
		// BigDecimal advanceMoney = scale(pl.getInRepayTotalAmount(), 2);
		// plan.put("shouldTerm", pl.getPhaseNumber());//期数
		// plan.put("shouldDate", pl.getRepayDate());//还款日期
		// plan.put("shouldAmt", shouldAmt);//月还款额
		// plan.put("bal", bal);//剩余本金
		// plan.put("advanceMoney", advanceMoney);//当期一次性还款金额
		// maps.add(plan);
		// }
		// param.put("repaymentPlans", maps);
		Calendar now = Calendar.getInstance();
		String savePath = uploadPathUtil.saveContract() + File.separator + now.get(Calendar.YEAR) + File.separator + (now.get(Calendar.MONTH) + 1) + File.separator + now.get(Calendar.DAY_OF_MONTH);
		File savefile = new File(savePath);
		if (!savefile.exists()) {
			savefile.mkdirs();
		}
		String flag = "还款管理书";
		CreatePdfFile.createObjectToPDF(request.getSession().getServletContext().getRealPath("/hxb_model/contract_plan.xml"), savePath + File.separator + conNo + flag + ".pdf",
				request.getSession().getServletContext().getRealPath("/hxb_model/simfang.ttf"), "", param);
		downloadFile(conNo, flag, request, response);
	}

	// 借款人声明(线上-红小宝)
	@RequestMapping(value = "/sys/making/downloadhxbtwo.do", method = { RequestMethod.GET })
	@ResponseBody
	public void downloadhxbtwo(String applyId, HttpServletRequest request, HttpServletResponse response) {
		SysContract contract = contractServer.findByApplyId(applyId);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("custName", contract.getCustName());
		String conNo = contract.getConNo();
		param.put("idNumber", contract.getIdNumber());
		Calendar now = Calendar.getInstance();
		String savePath = uploadPathUtil.saveContract() + File.separator + now.get(Calendar.YEAR) + File.separator + (now.get(Calendar.MONTH) + 1) + File.separator + now.get(Calendar.DAY_OF_MONTH);
		File savefile = new File(savePath);
		if (!savefile.exists()) {
			savefile.mkdirs();
		}
		String flag = "借款人声明";
		CreatePdfFile.createObjectToPDF(request.getSession().getServletContext().getRealPath("/hxb_model/contract_jkrsm.xml"), savePath + File.separator + conNo + flag + ".pdf",
				request.getSession().getServletContext().getRealPath("/hxb_model/simfang.ttf"), "", param);
		downloadFile(conNo, flag, request, response);
	}

	// 委托扣款授权书(线上-红小宝)
	@RequestMapping(value = "/sys/making/downloadhxbthree.do", method = { RequestMethod.GET })
	@ResponseBody
	public void downloadhxbthree(String applyId, HttpServletRequest request, HttpServletResponse response) {
		// SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		// String nowtime = sf.format(new Date());
		SysContract contract = contractServer.selectByApplyId(applyId);
		String conNo = contract.getConNo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String nowtime = sdf.format(contract.getSignTime());
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("conNo", contract.getConNo());// 合同编号
		data.put("year", nowtime.substring(0, 4));// 年
		data.put("month", nowtime.substring(4, 6));// 月
		data.put("day", nowtime.substring(6, 8));// 日
		data.put("custName", contract.getCustName());// 银行卡户名
		data.put("bank", contract.getBank());// 银行卡开户银行
		data.put("bankNo", contract.getBankNo());// 银行卡账号
		data.put("idNumber", contract.getIdNumber());// 身份证号码
		data.put("bankPhoneNo", contract.getBankPhoneNo());// 银行卡预留手机号
		Calendar now = Calendar.getInstance();
		String savePath = uploadPathUtil.saveContract() + File.separator + now.get(Calendar.YEAR) + File.separator + (now.get(Calendar.MONTH) + 1) + File.separator + now.get(Calendar.DAY_OF_MONTH);
		File savefile = new File(savePath);
		if (!savefile.exists()) {
			savefile.mkdirs();
		}
		String flag = "授权委托书";
		CreatePdfFile.createObjectToPDF(request.getSession().getServletContext().getRealPath("/hxb_model/contract_sqwts.xml"), savePath + File.separator + conNo + flag + ".pdf",
				request.getSession().getServletContext().getRealPath("/hxb_model/simfang.ttf"), "", data);
		downloadFile(conNo, flag, request, response);
	}

	// 信用咨询及管理服务协议（线上）
	@RequestMapping(value = "/sys/making/downloadhxbfour.do", method = { RequestMethod.GET })
	@ResponseBody
	public void downloadhxbfour(String applyId, HttpServletRequest request, HttpServletResponse response) {
		// SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		// String nowtime = sf.format(new Date());
		SysContract contract = contractServer.selectByApplyId(applyId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String nowtime = sdf.format(contract.getSignTime());
		// Date d =new Date();
		// 把日期转换为字符串
		UserApply userApply = userApplyServer.findApplyById(applyId);
		String conNo = contract.getConNo();
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("conNo", conNo);// 协议编号：
		map.put("year", nowtime.substring(0, 4));// 年
		map.put("month", nowtime.substring(4, 6));// 月
		map.put("day", nowtime.substring(6, 8));// 日
		if (sessionRouter != null) {
			if (sessionRouter.getStoreCityName() != null) {
				map.put("city", sessionRouter.getStoreCityName());// 市区值
			} else {
				map.put("city", "");// 市区值
			}
		} else {
			map.put("city", "");// 市区值
		}
		map.put("custName", userApply.getCustName());// 借款人：
		map.put("idNumber", userApply.getIdNumber());// 身份证号：
		map.put("houseAddress", userApply.getHouseAddress());// 现地址
		map.put("email", userApply.getEmail());// 邮箱地址
		map.put("phone", userApply.getCustMobile());// 联系电话：
		map.put("loanId", "HXB_" + conNo);// 协议编号(HXB_)
		BigDecimal loanAmount = scale(contract.getLoanAmount(), 2);
		map.put("loanAmountTotal", NumberToCN.number2CNMontrayUnit(loanAmount));// 借款金额(大写)
		map.put("loanAmount", loanAmount);// 借款金额(小写)
		BigDecimal conAmount = scale(contract.getConAmount(), 2);
		map.put("conAmountTotalaa", NumberToCN.number2CNMontrayUnit(conAmount));// 借款金额(大写)
		map.put("conAmountaa", conAmount);// 借款金额(小写)
		Integer strem = contract.getProductPeriod();
		map.put("trem", strem);// 借款期限
		map.put("amtTotal", NumberToCN.number2CNMontrayUnit(contract.getMonthMoney()));// 每月还款金额(大写)
		map.put("amt", contract.getMonthMoney());// 每月还款金额(小写)

		HxbRate rate = hxbRateServer.selectByApplyId(applyId);
		BigDecimal hxbServiceFee = rate.getHxbServiceFee();// 服务费(红小宝服务费)
		BigDecimal channelServiceFee = rate.getChannelServiceFee();// 服务费（线下服务费）
		BigDecimal creditServiceFee = rate.getCreditServiceFee();// 审核费(第三方服务费)
		BigDecimal totalServiceFee = hxbServiceFee.add(creditServiceFee).add(channelServiceFee);
		map.put("totalServiceFee", NumberToCN.number2CNMontrayUnit(totalServiceFee));// 总服务费（大写）
		map.put("totalService", totalServiceFee);// 总服务费(小写)
		map.put("hxbServiceFee", NumberToCN.number2CNMontrayUnit(hxbServiceFee));// 服务费(红小宝服务费)(大写)
		map.put("hxbService", hxbServiceFee);// 服务费(红小宝服务费)(小写)
		map.put("channelServiceFee", NumberToCN.number2CNMontrayUnit(channelServiceFee));// 服务费（线下服务费）(大写)
		map.put("channelService", channelServiceFee);// 服务费（线下服务费）(小写)
		map.put("creditServiceFee", NumberToCN.number2CNMontrayUnit(creditServiceFee));// 审核费(第三方服务费)(大写)
		map.put("creditService", creditServiceFee);// 审核费(第三方服务费)(小写)

		int i = 0;
		int k = 0;
		int w = 0;
		String stermHxb = contract.getProductPeriod().toString();
		if (stermHxb.equals("12")) {
			i = 1;
		} else if (stermHxb.equals("18")) {
			i = 2;
		} else if (stermHxb.equals("24")) {
			i = 3;
		} else if (stermHxb.equals("36")) {
			i = 4;
		} else if (stermHxb.equals("48")) {
			i = 4;
		}
		List<Map<String, Object>> listHxb = new ArrayList<Map<String, Object>>();
		map.put("lineNumber", "1");
		BigDecimal all = new BigDecimal(0.00);// 合计服务费
		String sumOneYuan = "";// 合计服务费(大写)
		BigDecimal sumOneNumMoney = new BigDecimal(0.00);// 合计服务费（小写）
		for (int j = 1; j <= i; j++) {
			Map<String, Object> mapHxb = new HashMap<String, Object>();
			// 12
			if (i == 1) {
				HxbReplaymentPlan hxb12 = hxbReplaymentPlanServer.selectByApplyIdAndPhaseNumber(applyId, 1);

				BigDecimal serviceChannel = hxb12.getChannelServiceFee();

				BigDecimal serviceHxb = hxb12.getHxbServiceFee();

				BigDecimal serviceCredit = hxb12.getCreditServiceFee();
				all = serviceChannel.add(serviceHxb).add(serviceCredit);
				sumOneYuan = NumberToCN.number2CNMontrayUnit(all);
				sumOneNumMoney = all;
				w = 3;
			}
			// 18
			if (i == 2) {
				if (j == 1) {
					HxbReplaymentPlan hxb12 = hxbReplaymentPlanServer.selectByApplyIdAndPhaseNumber(applyId, 1);

					BigDecimal serviceChannel = hxb12.getChannelServiceFee();

					BigDecimal serviceHxb = hxb12.getHxbServiceFee();

					BigDecimal serviceCredit = hxb12.getCreditServiceFee();
					all = serviceChannel.add(serviceHxb).add(serviceCredit);
					sumOneYuan = NumberToCN.number2CNMontrayUnit(all);
					sumOneNumMoney = all;
					w = 3;
				}
				if (j == 2) {
					HxbReplaymentPlan hxb12 = hxbReplaymentPlanServer.selectByApplyIdAndPhaseNumber(applyId, 4);
					BigDecimal serviceChannel = hxb12.getChannelServiceFee();

					BigDecimal serviceHxb = hxb12.getHxbServiceFee();

					BigDecimal serviceCredit = hxb12.getCreditServiceFee();
					BigDecimal all1 = serviceChannel.add(serviceHxb).add(serviceCredit);
					mapHxb.put("sumOneYuan", NumberToCN.number2CNMontrayUnit(all1));
					mapHxb.put("sumOneNumMoney", all1);
					mapHxb.put("sOneTerm", "3");
					mapHxb.put("eOneTerm", "6");
					mapHxb.put("secLineNumber", j);
					w = 6;
					listHxb.add(mapHxb);
				}
			}
			// 24
			if (i == 3) {
				if (j == 1) {
					HxbReplaymentPlan hxb12 = hxbReplaymentPlanServer.selectByApplyIdAndPhaseNumber(applyId, 1);
					BigDecimal serviceChannel = hxb12.getChannelServiceFee();

					BigDecimal serviceHxb = hxb12.getHxbServiceFee();

					BigDecimal serviceCredit = hxb12.getCreditServiceFee();
					all = serviceChannel.add(serviceHxb).add(serviceCredit);
					sumOneYuan = NumberToCN.number2CNMontrayUnit(all);
					sumOneNumMoney = all;
					w = 3;
				}
				if (j == 2) {
					HxbReplaymentPlan hxb12 = hxbReplaymentPlanServer.selectByApplyIdAndPhaseNumber(applyId, 4);
					BigDecimal serviceChannel = hxb12.getChannelServiceFee();

					BigDecimal serviceHxb = hxb12.getHxbServiceFee();

					BigDecimal serviceCredit = hxb12.getCreditServiceFee();
					BigDecimal all2 = serviceChannel.add(serviceHxb).add(serviceCredit);
					mapHxb.put("sumOneYuan", NumberToCN.number2CNMontrayUnit(all2));
					mapHxb.put("sumOneNumMoney", all2);
					mapHxb.put("sOneTerm", "3");
					mapHxb.put("eOneTerm", "6");
					mapHxb.put("secLineNumber", j);
					w = 6;
					listHxb.add(mapHxb);
				}
				if (j == 3) {
					HxbReplaymentPlan hxb12 = hxbReplaymentPlanServer.selectByApplyIdAndPhaseNumber(applyId, 7);
					BigDecimal serviceChannel = hxb12.getChannelServiceFee();

					BigDecimal serviceHxb = hxb12.getHxbServiceFee();

					BigDecimal serviceCredit = hxb12.getCreditServiceFee();
					BigDecimal all3 = serviceChannel.add(serviceHxb).add(serviceCredit);
					mapHxb.put("sumOneYuan", NumberToCN.number2CNMontrayUnit(all3));
					mapHxb.put("sumOneNumMoney", all3);
					mapHxb.put("sOneTerm", "6");
					mapHxb.put("eOneTerm", "12");
					mapHxb.put("secLineNumber", j);
					w = 12;
					listHxb.add(mapHxb);
				}
			}
			// 36
			if (i == 4) {
				if (j == 1) {
					HxbReplaymentPlan hxb12 = hxbReplaymentPlanServer.selectByApplyIdAndPhaseNumber(applyId, 1);
					BigDecimal serviceChannel = hxb12.getChannelServiceFee();

					BigDecimal serviceHxb = hxb12.getHxbServiceFee();

					BigDecimal serviceCredit = hxb12.getCreditServiceFee();
					all = serviceChannel.add(serviceHxb).add(serviceCredit);
					sumOneYuan = NumberToCN.number2CNMontrayUnit(all);
					sumOneNumMoney = all;
					w = 3;
				}
				if (j == 2) {
					HxbReplaymentPlan hxb12 = hxbReplaymentPlanServer.selectByApplyIdAndPhaseNumber(applyId, 4);
					BigDecimal serviceChannel = hxb12.getChannelServiceFee();

					BigDecimal serviceHxb = hxb12.getHxbServiceFee();

					BigDecimal serviceCredit = hxb12.getCreditServiceFee();
					BigDecimal all4 = serviceChannel.add(serviceHxb).add(serviceCredit);
					mapHxb.put("sumOneYuan", NumberToCN.number2CNMontrayUnit(all4));
					mapHxb.put("sumOneNumMoney", all4);
					mapHxb.put("sOneTerm", "3");
					mapHxb.put("eOneTerm", "6");
					mapHxb.put("secLineNumber", j);
					w = 6;
					listHxb.add(mapHxb);
				}
				if (j == 3) {
					HxbReplaymentPlan hxb12 = hxbReplaymentPlanServer.selectByApplyIdAndPhaseNumber(applyId, 7);
					BigDecimal serviceChannel = hxb12.getChannelServiceFee();

					BigDecimal serviceHxb = hxb12.getHxbServiceFee();

					BigDecimal serviceCredit = hxb12.getCreditServiceFee();
					BigDecimal all5 = serviceChannel.add(serviceHxb).add(serviceCredit);
					mapHxb.put("sumOneYuan", NumberToCN.number2CNMontrayUnit(all5));
					mapHxb.put("sumOneNumMoney", all5);
					mapHxb.put("sOneTerm", "6");
					mapHxb.put("eOneTerm", "12");
					mapHxb.put("secLineNumber", j);
					w = 12;
					listHxb.add(mapHxb);
				}
				if (j == 4) {
					HxbReplaymentPlan hxb12 = hxbReplaymentPlanServer.selectByApplyIdAndPhaseNumber(applyId, 13);
					BigDecimal serviceChannel = hxb12.getChannelServiceFee();

					BigDecimal serviceHxb = hxb12.getHxbServiceFee();

					BigDecimal serviceCredit = hxb12.getCreditServiceFee();
					BigDecimal all6 = serviceChannel.add(serviceHxb).add(serviceCredit);
					mapHxb.put("sumOneYuan", NumberToCN.number2CNMontrayUnit(all6));
					mapHxb.put("sumOneNumMoney", all6);
					mapHxb.put("sOneTerm", "12");
					mapHxb.put("eOneTerm", "18");
					mapHxb.put("secLineNumber", j);
					w = 18;
					listHxb.add(mapHxb);
				}
			}

			// 48
			if (i == 5) {
				if (j == 1) {
					HxbReplaymentPlan hxb12 = hxbReplaymentPlanServer.selectByApplyIdAndPhaseNumber(applyId, 1);
					BigDecimal serviceChannel = hxb12.getChannelServiceFee();

					BigDecimal serviceHxb = hxb12.getHxbServiceFee();

					BigDecimal serviceCredit = hxb12.getCreditServiceFee();
					all = serviceChannel.add(serviceHxb).add(serviceCredit);
					sumOneYuan = NumberToCN.number2CNMontrayUnit(all);
					sumOneNumMoney = all;
					w = 3;
				}
				if (j == 2) {
					HxbReplaymentPlan hxb12 = hxbReplaymentPlanServer.selectByApplyIdAndPhaseNumber(applyId, 4);
					BigDecimal serviceChannel = hxb12.getChannelServiceFee();

					BigDecimal serviceHxb = hxb12.getHxbServiceFee();

					BigDecimal serviceCredit = hxb12.getCreditServiceFee();
					BigDecimal all4 = serviceChannel.add(serviceHxb).add(serviceCredit);
					mapHxb.put("sumOneYuan", NumberToCN.number2CNMontrayUnit(all4));
					mapHxb.put("sumOneNumMoney", all4);
					mapHxb.put("sOneTerm", "3");
					mapHxb.put("eOneTerm", "6");
					mapHxb.put("secLineNumber", j);
					w = 6;
					listHxb.add(mapHxb);
				}
				if (j == 3) {
					HxbReplaymentPlan hxb12 = hxbReplaymentPlanServer.selectByApplyIdAndPhaseNumber(applyId, 7);
					BigDecimal serviceChannel = hxb12.getChannelServiceFee();

					BigDecimal serviceHxb = hxb12.getHxbServiceFee();

					BigDecimal serviceCredit = hxb12.getCreditServiceFee();
					BigDecimal all5 = serviceChannel.add(serviceHxb).add(serviceCredit);
					mapHxb.put("sumOneYuan", NumberToCN.number2CNMontrayUnit(all5));
					mapHxb.put("sumOneNumMoney", all5);
					mapHxb.put("sOneTerm", "6");
					mapHxb.put("eOneTerm", "12");
					mapHxb.put("secLineNumber", j);
					w = 12;
					listHxb.add(mapHxb);
				}
				if (j == 4) {
					HxbReplaymentPlan hxb12 = hxbReplaymentPlanServer.selectByApplyIdAndPhaseNumber(applyId, 13);
					BigDecimal serviceChannel = hxb12.getChannelServiceFee();

					BigDecimal serviceHxb = hxb12.getHxbServiceFee();

					BigDecimal serviceCredit = hxb12.getCreditServiceFee();
					BigDecimal all6 = serviceChannel.add(serviceHxb).add(serviceCredit);
					mapHxb.put("sumOneYuan", NumberToCN.number2CNMontrayUnit(all6));
					mapHxb.put("sumOneNumMoney", all6);
					mapHxb.put("sOneTerm", "12");
					mapHxb.put("eOneTerm", "18");
					mapHxb.put("secLineNumber", j);
					w = 18;
					listHxb.add(mapHxb);
				}

			}

			k = j + 1;
		}
		map.put("term", 3);
		map.put("lastTerm", w);
		map.put("lastLineNumber", k);
		map.put("repayment", listHxb);
		map.put("tqYuan", sumOneYuan);
		map.put("tqNumMoney", sumOneNumMoney);
		Calendar now = Calendar.getInstance();
		String savePath = uploadPathUtil.saveContract() + File.separator + now.get(Calendar.YEAR) + File.separator + (now.get(Calendar.MONTH) + 1) + File.separator + now.get(Calendar.DAY_OF_MONTH);
		File savefile = new File(savePath);
		if (!savefile.exists()) {
			savefile.mkdirs();
		}
		String flag = "信用咨询与管理服务协议书";
		CreatePdfFile.createObjectToPDF(request.getSession().getServletContext().getRealPath("/hxb_model/contract_four.xml"), savePath + File.separator + conNo + flag + ".pdf",
				request.getSession().getServletContext().getRealPath("/hxb_model/simfang.ttf"), request.getSession().getServletContext().getRealPath("/hxb_model/ht.png"), map);
		downloadFile(conNo, flag, request, response);
	}

	public String convertAmt(String amt) {
		String[] cnNumbers = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		StringBuffer sf = new StringBuffer();
		for (int i = 0; i < amt.length(); i++) {
			sf.append(cnNumbers[Integer.parseInt(amt.charAt(i) + "")]);
		}
		return sf.toString();
	}

	// 下载方法
	public void downloadFile(String conNo, String flag, HttpServletRequest request, HttpServletResponse response) {
		Calendar now = Calendar.getInstance();
		String savePath = uploadPathUtil.saveContract() + File.separator + now.get(Calendar.YEAR) + File.separator + (now.get(Calendar.MONTH) + 1) + File.separator + now.get(Calendar.DAY_OF_MONTH) + File.separator + conNo + flag + ".pdf";
		File file = new File(savePath);
		try {
			flag = new String(flag.getBytes("UTF-8"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (file.exists()) {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/force-download;charset=utf-8");// 设置强制下载不打开
			response.addHeader("Content-Disposition", "attachment;fileName=" + conNo + flag + ".pdf");// 设置文件名
			byte[] buffer = new byte[1024];
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				OutputStream os = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
					os.write(buffer, 0, i);
					i = bis.read(buffer);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

}
