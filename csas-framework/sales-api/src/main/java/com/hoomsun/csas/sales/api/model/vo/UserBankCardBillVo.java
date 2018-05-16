/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.model.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月14日 <br>
 * 描述：用户银行卡账单
 */
public class UserBankCardBillVo {
	private String bankId;// 绑定银行记录id
	private String bankType;// 银行类型 简写
	private String bankName;// 银行名称
	private List<CardBillVo> cardBillVos = new ArrayList<CardBillVo>();

	public void addCardBillVos(CardBillVo billVo) {
		this.cardBillVos.add(billVo);
	}
	
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public List<CardBillVo> getCardBillVos() {
		return cardBillVos;
	}

	public void setCardBillVos(List<CardBillVo> cardBillVos) {
		this.cardBillVos = cardBillVos;
	}

	@Override
	public String toString() {
		return "UserBankCardBillVo [bankId=" + bankId + ", bankType=" + bankType + ", bankName=" + bankName + ", cardBillVos=" + cardBillVos + "]";
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月14日 <br>
	 * 描述： 账单汇总
	 */
	public class CardBillVo {
		private String creditLimit;// 额度
		private String statementDate;// 账单日
		private String paymentDueDate;// 还款日
		private String minimumAmountDue;// 最小还款
		private String currentAmountDue;// 当期应还款
		private List<PayRecord> payRecords = new ArrayList<PayRecord>();

		public void addPayRecords(PayRecord record) {
			this.payRecords.add(record);
		}

		public String getCreditLimit() {
			return creditLimit;
		}

		public void setCreditLimit(String creditLimit) {
			this.creditLimit = creditLimit;
		}

		public String getStatementDate() {
			return statementDate;
		}

		public void setStatementDate(String statementDate) {
			this.statementDate = statementDate;
		}

		public String getPaymentDueDate() {
			return paymentDueDate;
		}

		public void setPaymentDueDate(String paymentDueDate) {
			this.paymentDueDate = paymentDueDate;
		}

		public String getMinimumAmountDue() {
			return minimumAmountDue;
		}

		public void setMinimumAmountDue(String minimumAmountDue) {
			this.minimumAmountDue = minimumAmountDue;
		}

		public String getCurrentAmountDue() {
			return currentAmountDue;
		}

		public void setCurrentAmountDue(String currentAmountDue) {
			this.currentAmountDue = currentAmountDue;
		}

		public List<PayRecord> getPayRecords() {
			return payRecords;
		}

		public void setPayRecords(List<PayRecord> payRecords) {
			this.payRecords = payRecords;
		}

		@Override
		public String toString() {
			return "CardBillVo [creditLimit=" + creditLimit + ", statementDate=" + statementDate + ", paymentDueDate=" + paymentDueDate + ", minimumAmountDue=" + minimumAmountDue + ", currentAmountDue=" + currentAmountDue + ", payRecords="
					+ payRecords + "]";
		}

	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月14日 <br>
	 * 描述： 消费信息
	 */
	public class PayRecord {
		private String tranDate;
		private String tranDesc;
		private String postAmt;

		public String getTranDate() {
			return tranDate;
		}

		public void setTranDate(String tranDate) {
			this.tranDate = tranDate;
		}

		public String getTranDesc() {
			return tranDesc;
		}

		public void setTranDesc(String tranDesc) {
			this.tranDesc = tranDesc;
		}

		public String getPostAmt() {
			return postAmt;
		}

		public void setPostAmt(String postAmt) {
			this.postAmt = postAmt;
		}

		@Override
		public String toString() {
			return "PayRecord [tranDate=" + tranDate + ", tranDesc=" + tranDesc + ", postAmt=" + postAmt + "]";
		}

	}
}
