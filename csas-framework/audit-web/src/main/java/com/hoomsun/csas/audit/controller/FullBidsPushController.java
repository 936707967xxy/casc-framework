/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.after.api.model.AfterLoanBal;
import com.hoomsun.after.dao.AfterLoanBalMapper;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.util.CreateSerialNo;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.core.dao.BankinfoMapper;
import com.hoomsun.core.dao.SysEmployeeMapper;
import com.hoomsun.core.model.Bankinfo;
import com.hoomsun.core.model.HxbReplaymentPlan;
import com.hoomsun.core.model.SysContract;
import com.hoomsun.core.model.SysEmployee;
import com.hoomsun.core.model.SysRepaymentPlan;
import com.hoomsun.core.server.inter.HxbReplaymentPlanServerI;
import com.hoomsun.core.server.inter.SysContractServerI;
import com.hoomsun.csas.audit.dao.HxbRecordMapper;
import com.hoomsun.csas.audit.model.HxbRecord;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.vo.NoticeMsg;
import com.hoomsun.csas.sales.dao.UserApplyMapper;
import com.hoomsun.csas.settle.model.LoanRecord;
import com.hoomsun.csas.settle.server.inter.LoanServerI;
import com.hoomsun.digest.DigestVerify;
import com.hoomsun.message.server.inter.NoticeServerI;
import com.hoomsun.util.ScGeneralInfo;

/**
 * 作者：liusong <br>
 * 创建时间：2017年12月21日 <br>
 * 描述：满标放款推送接口 小宝主动推送数据
 */
@Controller
@RequestMapping("/fullBidsPush")
public class FullBidsPushController {
	private static final Logger log = LoggerFactory.getLogger(FullBidsPushController.class);
	@Autowired
	private HxbReplaymentPlanServerI hxbReplaymentPlanServer;
	@Autowired
	private SysContractServerI sysContractServer;
	@Autowired
	private HxbRecordMapper hxbRecordMapper;
	@Autowired
	private TaskService taskService;
	@Autowired
	private UserApplyMapper userApplyMapper;
	@Autowired
	private AfterLoanBalMapper afterLoanBalMapper;
	@Autowired
	private BankinfoMapper bankinfoMapper;
	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;
	@Autowired
	private NoticeServerI noticeServer;
	@Autowired
	private LoanServerI loanServer;
	
	@RequestMapping(value = "/send.do")
	@ResponseBody
	public JSONObject fullBidsPushGet(HttpServletRequest req) {
		String postData = req.getParameter("postData");
		log.info("进入满标状态推送接口...");
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
			log.info("接收小宝数据：" + postData);
			// 解析数据
			JSONObject RecRepStatusObj = JSONObject.parseObject(postData);
			JSONObject generalInfo = RecRepStatusObj.getJSONObject("generalInfo");
			ScGeneralInfo info = JSON.parseObject(generalInfo.toJSONString(), ScGeneralInfo.class);
			// 签名验证
			Boolean verify = new DigestVerify().verify(generalInfo.getString("sign"), info, "UTF-8", "debx-zhixin");
			if (!verify) {
				throw new RuntimeException("满标状态推送接口,验签失败！");
			}
			JSONArray lendResultList = new JSONArray();
			// 借款结果
			lendResultList = RecRepStatusObj.getJSONArray("lendResult");
			if (lendResultList == null || lendResultList.size() <= 0) {
				throw new RuntimeException("满标状态推送接口,借款结果数据List为空！");
			}
			for (int i = 0; i < lendResultList.size(); i++) {
				// 至信贷款唯一编号
				String loanId = lendResultList.getJSONObject(i).getString("loanRefId");
				String applyId = userApplyMapper.findApplyIdByLoanId(loanId);
				JSONArray repaymentPlanList = new JSONArray();
				// 客户还款计划列表
				SysContract contract = sysContractServer.selectByApplyId(applyId);
				repaymentPlanList = lendResultList.getJSONObject(i).getJSONArray("repaymentPlanList");
				/*
				 * 更新还款明细表的还款日期
				 */
				for (int j = 0; j < repaymentPlanList.size(); j++) {
					// 获取单条还款明细的数据
					JSONObject repaymentPlan = repaymentPlanList.getJSONObject(j);
					Integer phaseNumber = repaymentPlan.getInteger("phaseNumber");
					HxbReplaymentPlan plan = hxbReplaymentPlanServer.selectByApplyIdAndPhaseNumber(applyId, phaseNumber);
					Date repayDate = repaymentPlan.getDate("repayDate");
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					plan.setRepayDate(repayDate);
					plan.setConId(contract.getConId());
					hxbReplaymentPlanServer.updatePlan(plan);
				}
				HxbRecord record = new HxbRecord();
				record.setApplyId(applyId);
				record.setRecordTime(new Date());
				record.setPushType("3");
				record.setPushTypeVal("满标放款推送接口");
				record.setRecordId(PrimaryKeyUtil.getPrimaryKey());
				hxbRecordMapper.insertSelective(record);
				//存入放款记录表数据
				LoanRecord loan = loanServer.findApplyIds(applyId);
				if(loan == null){
					loan = insertData(applyId);
					loan.setLoanId(PrimaryKeyUtil.getPrimaryKey());
					loanServer.insertSelective(loan);
				}else{
					loan = insertData(applyId);
					loanServer.updateByPrimaryKeySelective(loan);
				}
				//插入接口记录表数据
				returnObj.put("generalInfo", CreateSerialNo.sign());
				returnObj.put("errMsg", "处理成功");
				returnObj.put("status", 1000);
				returnObj.put("handleTime", new Date());
				//放款完成之后，把这笔单子的推送到放款节点
				Task task = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("loanAudit").singleResult();
				if (task != null) {
					taskService.complete(task.getId());
					String processId = task.getProcessInstanceId();
					// 修改申请主表状态
					List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(applyId).processInstanceId(processId).list();
					if (tasks == null || tasks.size() < 1) {
						userApplyMapper.updateProcessInstance(applyId, processId, "success", "撮配成功");
					}else{
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
				}
				/**
				 * 贷后推送数据
				 */
				pushAfterLoanBal(applyId);
				sysContractServer.updateConStatus(applyId);
				/**
				 * 消息推送
				 */
				NoticeMsg noticeMsg = userApplyMapper.selectProAndAmountByApplyId(applyId);
				if(noticeMsg != null){
					String agreeProAlias = noticeMsg.getAgreeProAlias();// 产品别名
					String loanAmount = contract.getLoanAmount().toString();
					String message = "您申请的" + agreeProAlias + "已成功放款，放款金额：" + loanAmount + "，您可以1-3个工作日在收款银行账户中查询明细，实际到账时间请以银行短信为准。";// 推送消息
					noticeServer.sendMsg(applyId, message, 1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("满标状态推送接口发送异常：" + e.getMessage());
			returnObj.put("generalInfo", CreateSerialNo.sign());
			returnObj.put("errMsg", e.getMessage() == null ? "接口未知异常" : e.getMessage());
			returnObj.put("status", 2000);
			returnObj.put("handleTime", new Date());
		} finally {
		}
		return returnObj;
	}
	
	private Json pushAfterLoanBal(String applyId) {
		AfterLoanBal afterLoanBal = afterLoanBalMapper.selectByLoanID(applyId);
		int i =0;
		if(afterLoanBal == null){
			afterLoanBal = saveData(applyId);
			if(afterLoanBal != null){
				afterLoanBal.setId(PrimaryKeyUtil.getPrimaryKey());
				i = afterLoanBalMapper.insertSelective(afterLoanBal);
			}
		}else{
			afterLoanBal = saveData(applyId);
			if(afterLoanBal != null){
				i = afterLoanBalMapper.updateByPrimaryKeySelective(afterLoanBal);
			}
		}
		if (i == 0) {
			return new Json(false, "推送失败!!!");
		} else {
			return new Json(true, "推送成功!!!");
		}
	}
	
	public AfterLoanBal saveData(String applyId){
		if (!StringUtils.isBlank(applyId)) {
			AfterLoanBal afterLoanBal = new AfterLoanBal();
			UserApply userApply = userApplyMapper.selectApplyTableByAppId(applyId);
			if (userApply != null) {
				afterLoanBal.setLoanId(userApply.getApplyId()); // 主键(对应APPLYID)
				afterLoanBal.setCastName(userApply.getCustName()); // 客户姓名
				afterLoanBal.setTel(userApply.getCustMobile()); // 联系电话
				afterLoanBal.setCardNo(userApply.getIdNumber()); // 身份证号
				afterLoanBal.setSex(userApply.getCustSex()); // 性别
				afterLoanBal.setCastSource(userApply.getSourcesValue()); // 客户来源
				afterLoanBal.setSalesDeptment(userApply.getStoreId()); // 销售营业部id
				afterLoanBal.setCustomerOrLoan(userApply.getSalesEmpName()); // 客户经理姓名（名称）
				afterLoanBal.setBusinessDepartment(null);// 营业部所属部门ID
				afterLoanBal.setSerialId(userApply.getLoanId());// 进件编号
			}
			SysContract sysContract = sysContractServer.findByApplyId(applyId);
			if (sysContract != null) {
				SysRepaymentPlan repaymentPlan = sysContract.getRepaymentPlans().get(0);
				afterLoanBal.setCurrentPeriod(repaymentPlan.getShouldTerm()); // 当前期数（默认：1）1
				afterLoanBal.setShouldCapi(repaymentPlan.getShouldCapi()); // 应还本金
				afterLoanBal.setShouldInte(repaymentPlan.getShouldInte()); // 应还利息
				afterLoanBal.setSurplusPrincipal(repaymentPlan.getBal()); // 剩余本金
				afterLoanBal.setReplaymentDate(repaymentPlan.getShouldDate()); // 应还款日期
				afterLoanBal.setAdvanceShould(repaymentPlan.getAdvanceShould());// 提前还款应还总额a
				afterLoanBal.setBank(sysContract.getBank()); // 所属银行（例：招商银行）
				afterLoanBal.setBankPhone(sysContract.getBankPhoneNo());// 银行预留手机号
				afterLoanBal.setBankAccount(sysContract.getBankNo());// 银行账号
				afterLoanBal.setPosType("0");// POS类型
				afterLoanBal.setProductId(sysContract.getProductId()); // 产品id
				afterLoanBal.setProductName(sysContract.getProductName());// 产品名称
				afterLoanBal.setContractAccount(sysContract.getConAmount());// 合同金额
				afterLoanBal.setLoanMoney(sysContract.getLoanAmount()); // 放款金额
				afterLoanBal.setLoanDate(DateUtil.getTimestamp()); // 放款日期
				afterLoanBal.setAmt(sysContract.getMonthMoney()); // 月还金额
				afterLoanBal.setStartTime(sysContract.getStartTime()); // 起始还款日期
				afterLoanBal.setEndTime(sysContract.getEndTime()); // 结束还款日期
				afterLoanBal.setLoanPeriod(sysContract.getProductPeriod());// 贷款期数
				afterLoanBal.setSurplusPeriod(sysContract.getProductPeriod());// 剩余期数
				afterLoanBal.setStatementDate(new BigDecimal(sysContract.getPayDate())); // 账单日
				Bankinfo bankinfo = bankinfoMapper.selectByBankName(sysContract.getBank());
				if (bankinfo != null) {
					afterLoanBal.setBankCode(bankinfo.getRemark()); // 开户行编码（例：ICBC，ABC）
				}
				SysEmployee employee =sysEmployeeMapper.selectByPrimaryKey(sysContract.getCreateEmpId());
				if (employee != null) {
					afterLoanBal.setManagerCast(sysContract.getCreateEmpName());// 管理客服姓名(申请表的提交员工)
					afterLoanBal.setManagerCastId(employee.getEmpWorkNum());// 管理客服工号
				}
			}
			//根据申请表的客服id查询员工工号
			afterLoanBal.setBigArea("0"); // 所属大区（名称）
			afterLoanBal.setDepartment("0"); // 所属分部（名称）
			afterLoanBal.setTeamLeader("0"); // 团队经理姓名（名称）
			afterLoanBal.setReviceCast("0");// 签约客服ID
			afterLoanBal.setPublicAccountFour("8888");// 对应公户后四位
			afterLoanBal.setSettleFlag("0");// 结清标识 （0：未结清，1已结清）
			afterLoanBal.setDelayFlag("0");// 逾期标识（0：未逾期，1：已逾期）
			afterLoanBal.setUpdownStatus("1");// 线上线下标识（线下：0，线上：1）
			afterLoanBal.setmSection(0);// M段（默认0）
			afterLoanBal.setCustomerOrLoan("0");// 此单子在客服还是贷后手中（客服：0，贷后：1）
			afterLoanBal.setLoanManagerCastId("");// 贷后客服ID
			afterLoanBal.setLoanManagerCast("");// 贷后客服Name
			afterLoanBal.setBal(new BigDecimal(0));// 账户余额（默认0）
			afterLoanBal.setHangUp("0");// 是否挂起（默认0）
			NoticeMsg noticeMsg = userApplyMapper.selectProAndAmountByApplyId(applyId);
			if(noticeMsg != null){
				afterLoanBal.setProductalias(noticeMsg.getAgreeProAlias());// 产品别名
				afterLoanBal.setConNo(sysContract.getConNo());// 合同编号
			}
			return afterLoanBal;
		}else{
			
		}
		return null;
	}
	
	
	public LoanRecord insertData(String applyId){
		LoanRecord loan = new LoanRecord();
		SysContract sysContract = sysContractServer.findByApplyId(applyId);
		if(sysContract != null){
			loan.setLoanCode(sysContract.getConNo());
			loan.setLoanAmount(sysContract.getLoanAmount());
			loan.setConAmount(sysContract.getConAmount());
			loan.setLoanTerm(sysContract.getProductPeriod());
			loan.setInterestRate(sysContract.getProductRate());
			loan.setProductId(sysContract.getProductId());
			loan.setProductName(sysContract.getProductName());
			loan.setPayType(sysContract.getProductPay());
//			loan.setPayDate(sysContract.getPayDate());
			loan.setConId(sysContract.getConId());
		}
		loan.setHandleOption(4);
		loan.setHandleOptionVal("同意");
		loan.setApplyId(applyId);
		loan.setHandleRemark("线上红小宝放款");
		loan.setHandleEmpName("hxb");
		loan.setLoanDate(new Date());
		loan.setHandleDate(new Date());
		loan.setPayChannel(2);
		loan.setPayChannelVal("线上");
		return loan;
		
	}
}

