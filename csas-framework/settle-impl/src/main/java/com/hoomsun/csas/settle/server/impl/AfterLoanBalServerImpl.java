package com.hoomsun.csas.settle.server.impl;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.after.api.model.AfterLoanBal;
import com.hoomsun.after.dao.AfterLoanBalMapper;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.core.dao.SysContractMapper;
import com.hoomsun.core.model.SysContract;
import com.hoomsun.core.model.SysRepaymentPlan;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.dao.UserApplyMapper;

@Service("AfterLoanBal")
public class AfterLoanBalServerImpl {

	@Autowired
	private UserApplyMapper userApplyMapper;

	@Autowired
	private SysContractMapper sysContractMapper;

	@Autowired
	private AfterLoanBalMapper afterLoanBalMapper;

	public Json PushAfterLoanBal(String applyId) {

		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数有误!");
		}

		UserApply userApply = userApplyMapper.selectApplyTableByAppId(applyId);
		if (userApply == null) {
			return new Json(false, "未找到申请信息!");
		}

		AfterLoanBal afterLoanBal = new AfterLoanBal();
		afterLoanBal.setId(PrimaryKeyUtil.getPrimaryKey());
		afterLoanBal.setLoanId(userApply.getApplyId()); // 进件编号 (对应APPLYID)
		afterLoanBal.setCastName(userApply.getCustName()); // 客户姓名
		afterLoanBal.setTel(userApply.getCustMobile()); // 联系电话
		afterLoanBal.setCardNo(userApply.getIdNumber()); // 身份证号
		afterLoanBal.setSex(userApply.getCustSex()); // 性别
		afterLoanBal.setCastSource(userApply.getSourcesValue()); // 客户来源

		SysContract sysContract = sysContractMapper.findByApplyId(applyId);
		if (sysContract == null) {
			return new Json(false, "未找到合同信息!");
		}

		afterLoanBal.setBank(sysContract.getBank()); // 所属银行（例：招商银行）
		afterLoanBal.setBankPhone(sysContract.getBankPhoneNo());// 银行预留手机号
		afterLoanBal.setBankAccount(sysContract.getBankNo());// 银行账号
		afterLoanBal.setBankCode(""); // 开户行编码（例：ICBC，ABC）
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

		SysRepaymentPlan repaymentPlan = sysContract.getRepaymentPlans().get(0);
		afterLoanBal.setCurrentPeriod(repaymentPlan.getShouldTerm()); // 当前期数（默认：1）
		afterLoanBal.setShouldCapi(repaymentPlan.getShouldCapi()); // 应还本金
		afterLoanBal.setShouldInte(repaymentPlan.getShouldInte()); // 应还利息
		afterLoanBal.setSurplusPrincipal(repaymentPlan.getBal()); // 剩余本金
		afterLoanBal.setReplaymentDate(repaymentPlan.getShouldDate()); // 应还款日期
		afterLoanBal.setAdvanceShould(repaymentPlan.getAdvanceShould());// 提前还款应还总额a

		afterLoanBal.setBigArea("0"); // 所属大区（名称）
		afterLoanBal.setDepartment("0"); // 所属分部（名称）
		afterLoanBal.setSalesDeptment(userApply.getSalesEmpDeptId()); // 销售营业部id
		afterLoanBal.setTeamLeader("0"); // 团队经理姓名（名称）
		afterLoanBal.setCustomerOrLoan(userApply.getSalesEmpName()); // 客户经理姓名（名称）
		afterLoanBal.setBusinessDepartment(userApply.getStoreId());// 营业部所属部门ID
		afterLoanBal.setReviceCast("0");// 签约客服ID
		afterLoanBal.setManagerCast("0");// 管理客服姓名
		afterLoanBal.setManagerCastId("0");// 管理客服id
		afterLoanBal.setPublicAccountFour("8888");// 对应公户后四位
		afterLoanBal.setSettleFlag("0");// 结清标识 （0：未结清，1已结清）
		afterLoanBal.setDelayFlag("0");// 逾期标识（0：未逾期，1：已逾期）
		afterLoanBal.setUpdownStatus("0");// 线上线下标识（线下：0，线上：1）
		afterLoanBal.setmSection(0);// M段（默认0）
		afterLoanBal.setCustomerOrLoan("0");// 此单子在客服还是贷后手中（客服：0，贷后：1）
		afterLoanBal.setLoanManagerCastId("");// 贷后客服ID
		afterLoanBal.setLoanManagerCast("");// 贷后客服Name
		afterLoanBal.setBal(new BigDecimal(0));// 账户余额（默认0）
		afterLoanBal.setHangUp("0");// 是否挂起（默认0）

		int i = afterLoanBalMapper.insertSelective(afterLoanBal);
		if (i == 0) {
			return new Json(false, "推送失败!!!");
		} else {
			return new Json(true, "推送成功!!!");
		}

	}
}
