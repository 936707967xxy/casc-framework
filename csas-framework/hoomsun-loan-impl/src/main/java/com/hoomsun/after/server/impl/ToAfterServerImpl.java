/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.server.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hoomsun.after.api.model.param.ToAfterParam;
import com.hoomsun.after.api.model.table.Loanbal;
import com.hoomsun.after.api.model.table.UserCount;
import com.hoomsun.after.api.server.ToAfterServer;
import com.hoomsun.after.dao.LoanbalMapper;
import com.hoomsun.after.dao.UserCountMapper;
import com.hoomsun.common.util.PrimaryKeyUtil;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年4月28日 <br>
 * 描述：放款后推送贷后数据
 */
public class ToAfterServerImpl implements ToAfterServer {

	@Autowired
	private UserCountMapper userCountMapper;
	@Autowired
	private LoanbalMapper loanbalMapper;

	@Override
	public boolean toAfter(ToAfterParam toAfterParam) throws Exception {

		// 客户ID
		String custId = toAfterParam.getCastId();

		UserCount userCount = userCountMapper.selectByCustId(custId);

		if (userCount == null) {
			userCount = new UserCount();
			userCount.setId(PrimaryKeyUtil.getPrimaryKey());
			userCount.setCastId(custId);
			userCount.setCastName(toAfterParam.getCastName());
			userCount.setSex(toAfterParam.getSex());
			userCount.setTel(toAfterParam.getTel());
			userCount.setCardNo(toAfterParam.getCardNo());
			userCount.setCastSource(toAfterParam.getCastSource());
			userCount.setBank(toAfterParam.getBank());
			userCount.setBankPhone(toAfterParam.getBankPhone());
			userCount.setBankAccount(toAfterParam.getBankAccount());
			userCount.setBankCode(toAfterParam.getBankCode());
			userCount.setBankCode2(toAfterParam.getBankCode2());
			userCount.setBankCode3(toAfterParam.getBankCode3());
			userCount.setBal(new BigDecimal(0));
			userCount.setUpdateDate(null);
			userCount.setCreateTime(new Date());
			userCountMapper.insert(userCount);
		}

		Loanbal loanbal = new Loanbal();
		loanbal.setId(PrimaryKeyUtil.getPrimaryKey());
		loanbal.setLoanId(toAfterParam.getLoanId());
		loanbal.setConNo(toAfterParam.getConNo());
		loanbal.setApplyId(toAfterParam.getLoanId());
		loanbal.setCastName(toAfterParam.getCastName());
		loanbal.setCardNo(toAfterParam.getCardNo());
		loanbal.setTel(toAfterParam.getTel());
		loanbal.setRepayDate(toAfterParam.getRepayDate());
		loanbal.setCurrentPeriod(toAfterParam.getCurrentPeriod());
		loanbal.setLoanPeriod(toAfterParam.getLoanPeriod());
		loanbal.setLoanMoney(toAfterParam.getLoanMoney());
		loanbal.setLoanDate(toAfterParam.getLoanDate());
		loanbal.setDelayFlag("0");

		loanbal.setSettleFlag("0");

		loanbal.setUpdownStatus(toAfterParam.getUpdownStatus());
		loanbal.setPosType(toAfterParam.getPosType());
		loanbal.setProductName(toAfterParam.getProductName());
		loanbal.setProductalias(toAfterParam.getProductalias());

		loanbal.setCustomerOrLoan("0");

		loanbal.setManagerCastId(toAfterParam.getManagerCastId());
		loanbal.setManagerCast(toAfterParam.getManagerCast());
		loanbal.setLoanManagerCastId(null);
		loanbal.setLoanManagerCast(null);
		loanbal.setLaonLeave(null);
		loanbal.setOutboundLeave(null);
		loanbal.setHangUp("0");
		loanbal.setHangUpDate(null);
		loanbal.setHangUpDeduct("0");
		loanbal.setHangUpDeductDate(null);
		loanbal.setmSection(0);
		loanbal.setSalesDeptment(toAfterParam.getSalesDeptment());

		loanbal.setCurrentVersion("V2.0");

		loanbal.setUpdateDate(null);
		loanbal.setCreateTime(new Date());
		loanbal.setStatementDate(toAfterParam.getStatementDate());
		loanbal.setConMoney(toAfterParam.getConMoney());
		loanbal.setNormalSubMoney(null);
		loanbal.setNormalSubDate(null);
		loanbal.setNormalSubStream(null);
		loanbal.setOverdueSubMoney(null);
		loanbal.setOverdueSubDate(null);
		loanbal.setOverdueSubStream(null);
		loanbal.setAdvancedSubMoney(null);
		loanbal.setAdvancedSubDate(null);
		loanbal.setAdvancedSubStream(null);
		loanbal.setCastId(custId);
		loanbal.setProductId(toAfterParam.getProductId());

		loanbalMapper.insert(loanbal);
		return true;
	}

}
