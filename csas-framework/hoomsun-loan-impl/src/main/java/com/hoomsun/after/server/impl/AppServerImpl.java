/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.server.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.after.api.model.param.BFCertifiedBKParam;
import com.hoomsun.after.api.model.param.BFCertifiedPayCheckParam;
import com.hoomsun.after.api.model.param.BFdeductModel;
import com.hoomsun.after.api.model.param.BfBFCertifiedPayParam;
import com.hoomsun.after.api.model.table.AppBk;
import com.hoomsun.after.api.model.table.ApplyDetail;
import com.hoomsun.after.api.model.table.ApplyFo;
import com.hoomsun.after.api.model.table.ChangeInfo;
import com.hoomsun.after.api.model.table.Deduct;
import com.hoomsun.after.api.model.table.Export;
import com.hoomsun.after.api.model.table.Loanbal;
import com.hoomsun.after.api.model.table.OverdueRecord;
import com.hoomsun.after.api.model.table.RepaymentPlan;
import com.hoomsun.after.api.model.table.UserCount;
import com.hoomsun.after.api.model.vo.AppInterfaceLoanData;
import com.hoomsun.after.api.model.vo.AppInterfaceModel;
import com.hoomsun.after.api.model.vo.BFCertifiedBKVo;
import com.hoomsun.after.api.model.vo.BFCertifiedPayVo;
import com.hoomsun.after.api.model.vo.BFdeductResult;
import com.hoomsun.after.api.model.vo.DeductServerResult;
import com.hoomsun.after.api.model.vo.RepaymentServerResult;
import com.hoomsun.after.api.server.AppServer;
import com.hoomsun.after.api.server.DeductBFServer;
import com.hoomsun.after.api.util.DateUtils;
import com.hoomsun.after.api.util.EntityToMap;
import com.hoomsun.after.api.util.BFutil.BFdeductUtil;
import com.hoomsun.after.dao.AppBkMapper;
import com.hoomsun.after.dao.AppInterfaceLoanDataMapper;
import com.hoomsun.after.dao.ApplyDetailMapper;
import com.hoomsun.after.dao.ApplyFoMapper;
import com.hoomsun.after.dao.ChangeInfoMapper;
import com.hoomsun.after.dao.DeductMapper;
import com.hoomsun.after.dao.ExportMapper;
import com.hoomsun.after.dao.LoanbalMapper;
import com.hoomsun.after.dao.OverdueRecordMapper;
import com.hoomsun.after.dao.RepaymentPlanMapper;
import com.hoomsun.after.dao.UserCountMapper;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年5月9日 <br>
 * 描述：
 */
@Service("appServer")
public class AppServerImpl implements AppServer {

	@Autowired
	private DeductMapper deductMapper;

	@Autowired
	private ExportMapper exportMapper;

	@Autowired
	private LoanbalMapper loanbalMapper;

	@Autowired
	private RepaymentPlanMapper repaymentPlanMapper;

	@Autowired
	private OverdueRecordMapper overdueRecordMapper;

	@Autowired
	private UserCountMapper userCountMapper;

	@Autowired
	private DeductBFServer deductBFServer;

	@Autowired
	private ApplyDetailMapper applyDetailMapper;

	@Autowired
	private ChangeInfoMapper changeInfoMapper;

	@Autowired
	private ApplyFoMapper applyFoMapper;

	@Autowired
	private AppInterfaceLoanDataMapper appInterfaceLoanDataMapper;

	@Autowired
	private AppBkMapper appBkMapper;

	/**
	 * 还款记录 app端 我的-往来记录
	 */
	@Override
	public Map<String, Object> getPaymentHistory(String id) {

		Map<String, Object> result = new HashMap<String, Object>();
		try {

			List<Export> exports = exportMapper.selectByCustId(id);

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (Export export : exports) {
				// 查询loanbal
				Loanbal loanbal = loanbalMapper.selectByLoanId(export.getLoanId());
				// 查询还款明细表
				Map<String, Object> repaymentPlanparams = new HashMap<String, Object>();
				repaymentPlanparams.put("loanId", export.getLoanId());
				repaymentPlanparams.put("shouldTerm", export.getExpendStream());
				RepaymentPlan repaymentPlan = repaymentPlanMapper.selByLoanIdTerm(repaymentPlanparams);

				// 返回数据
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("deductDate", export.getCreateTime());// 划扣时间
				map.put("deductMoney", export.getExpendMoney());// 划扣金额
				map.put("conNo", export.getConNo());// 合同编号
				map.put("productalias", loanbal.getProductalias());// 产品别名
				map.put("productName", loanbal.getProductName());// 产品名称

				map.put("deductState", "0");// 划扣状态（成功：0，失败：-1，待定：2）
				map.put("amt", repaymentPlan.getShouldAmt());// 月还款额
				map.put("bank", null);// 划扣银行
				map.put("bankaccount", null);// 银行卡号
				map.put("dedutStream", export.getExpendStream());// 划扣期数
				map.put("loanPeriod", loanbal.getLoanPeriod());// 贷款期数
				map.put("type", "1");// 标识（1）
				list.add(map);
			}

			result.put("errorInfo", "查询成功");
			result.put("errorCode", "0");
			result.put("data", list);

		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "查询失败");
			result.put("errorCode", "-1");
			result.put("data", null);
		}

		return result;
	}

	/**
	 * 还款记录单条 app端 -邹哥位置跳转
	 */
	@Override
	public Map<String, Object> getsinglepayment(String applyId) {

		Map<String, Object> result = new HashMap<String, Object>();

		try {

			Loanbal loanbal = loanbalMapper.selectByApplyId(applyId);

			List<Export> exports = exportMapper.selectByLoanId(loanbal.getLoanId());

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (Export export : exports) {

				// 查询还款明细表
				Map<String, Object> repaymentPlanparams = new HashMap<String, Object>();
				repaymentPlanparams.put("loanId", export.getLoanId());
				repaymentPlanparams.put("shouldTerm", export.getExpendStream());
				RepaymentPlan repaymentPlan = repaymentPlanMapper.selByLoanIdTerm(repaymentPlanparams);

				// 返回数据
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("deductDate", export.getCreateTime());// 划扣时间
				map.put("deductMoney", export.getExpendMoney());// 划扣金额
				map.put("conNo", export.getConNo());// 合同编号
				map.put("productalias", loanbal.getProductalias());// 产品别名
				map.put("productName", loanbal.getProductName());// 产品名称

				map.put("deductState", "0");// 划扣状态（成功：0，失败：-1，待定：2）
				map.put("amt", repaymentPlan.getShouldAmt());// 月还款额
				map.put("bank", null);// 划扣银行
				map.put("bankaccount", null);// 银行卡号
				map.put("dedutStream", export.getExpendStream());// 划扣期数
				map.put("loanPeriod", loanbal.getLoanPeriod());// 贷款期数
				map.put("type", "1");// 标识（1）
				list.add(map);
			}

			result.put("errorInfo", "查询成功");
			result.put("errorCode", "0");
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "查询失败");
			result.put("errorCode", "-1");
			result.put("data", null);
		}
		return result;
	}

	/**
	 * 近七日还款
	 */
	@Override
	public Map<String, Object> getSevenDaysRepayment(String id) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

			List<Loanbal> loanbals = loanbalMapper.selectByCustId(id);

			// 拼接所有 LoanId
			List<String> loanIds = new ArrayList<String>();
			for (Loanbal loanbal : loanbals) {
				loanIds.add(loanbal.getLoanId());
			}

			if (loanIds == null || loanIds.size() <= 0) {
				result.put("errorInfo", "查询成功");
				result.put("errorCode", "0");
				result.put("data", null);
				return result;
			}

			// 获取7天后的日期
			Date date = DateUtils.getNextDate(new Date(), 7);
			Map<String, Object> loanbalParams = new HashMap<String, Object>();
			loanbalParams.put("loanIds", loanIds);
			loanbalParams.put("date", date);

			List<Loanbal> resultLoanbals = loanbalMapper.selectLessDate(loanbalParams);

			// 近七日应还总额
			BigDecimal SumMoney = new BigDecimal(0);
			for (Loanbal Loanbal : resultLoanbals) {

				// 查询还款明细表
				Map<String, Object> repaymentPlanparams = new HashMap<String, Object>();
				repaymentPlanparams.put("loanId", Loanbal.getLoanId());
				repaymentPlanparams.put("shouldTerm", Loanbal.getCurrentPeriod());
				RepaymentPlan repaymentPlan = repaymentPlanMapper.selByLoanIdTerm(repaymentPlanparams);

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("notype", "N");// 正常逾期标识N正常O逾期
				map.put("currentPeriod", Loanbal.getCurrentPeriod());// 当前期数
				map.put("loanPeriod", Loanbal.getLoanPeriod());// 贷款期数
				map.put("productName", Loanbal.getProductName());// 产品名称
				map.put("productalias", Loanbal.getProductalias());// 产品别名
				map.put("conNo", Loanbal.getConNo());// 合同编号
				map.put("amt", repaymentPlan.getShouldAmt());// 月还款额
				map.put("replaymentDate", Loanbal.getRepayDate());// 应还款日期
				map.put("shouldCapi", repaymentPlan.getShouldCapi());// 应还本金
				map.put("shouldInte", repaymentPlan.getShouldInte());// 应还利息

				list.add(map);
				SumMoney = SumMoney.add(repaymentPlan.getShouldAmt());
			}

			List<OverdueRecord> overdueRecords = overdueRecordMapper.selectByLoanIds(loanIds);

			int overCount = 0;
			for (OverdueRecord overdueRecord : overdueRecords) {

				// 查询loanbal
				Loanbal loanbal = loanbalMapper.selectByLoanId(overdueRecord.getLoanId());

				// 查询还款明细表
				Map<String, Object> repaymentPlanparams = new HashMap<String, Object>();
				repaymentPlanparams.put("loanId", overdueRecord.getLoanId());
				repaymentPlanparams.put("shouldTerm", overdueRecord.getOverdueNum());
				RepaymentPlan repaymentPlan = repaymentPlanMapper.selByLoanIdTerm(repaymentPlanparams);

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("notype", "O");// 正常逾期标识N正常O逾期
				map.put("overdueNum", overdueRecord.getOverdueNum());// 逾期期数
				map.put("overdueDays", overdueRecord.getOverdueDays());// 逾期天数
				map.put("loanPeriod", loanbal.getLoanPeriod());// 贷款期数
				map.put("productName", loanbal.getProductName());// 产品名称
				map.put("productalias", loanbal.getProductalias());// 产品别名
				map.put("conNo", loanbal.getConNo());// 合同编号
				map.put("createDate", repaymentPlan.getShouldDate());// 应还日期

				map.put("receiveMoney", overdueRecord.getReceiveMoney());// 应收总额
				map.put("receivedMoney", new BigDecimal(0));// 已收金额
				map.put("balanceDueMoney", overdueRecord.getReceiveMoney());// 结欠金额

				map.put("shouldCapi", overdueRecord.getReceiveCorpus());// 应收本金
				map.put("shouldInte", overdueRecord.getReceiveShouldinte());// 应收利息

				map.put("receivePenalty", overdueRecord.getReceivePenalty());// 应收违约金
				map.put("receiveInterest", overdueRecord.getReceiveInterest());// 应收罚息

				map.put("receivePenaltyInterest", overdueRecord.getReceivePenaltyInterest());// 剩余违罚金（应收违约金+应收罚息）
				map.put("amt", overdueRecord.getAmt());// 月还款

				list.add(map);
				SumMoney = SumMoney.add(overdueRecord.getReceiveMoney());
				overCount = overCount + overdueRecord.getOverdueDays();
			}

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("list", list);

			// 头部信息
			data.put("overCount", overCount);// 逾期天数
			data.put("allMoney", SumMoney);// 近七日应还总额

			result.put("errorInfo", "查询成功");
			result.put("errorCode", "0");
			result.put("data", data);
		} catch (Exception e) {
			result.put("errorInfo", "查询失败");
			result.put("errorCode", "-1");
			result.put("data", null);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getLetters(String custId) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Loanbal> loanbals = loanbalMapper.selectByCustId(custId);

		for (Loanbal loanbal : loanbals) {
			UserCount userCount = userCountMapper.selectByCustId(custId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("loanId", loanbal.getLoanId());
			map.put("castName", loanbal.getCastName());
			map.put("productalias", loanbal.getProductalias());
			map.put("contractAccount", loanbal.getConMoney());
			map.put("loanDate", loanbal.getLoanDate());
			map.put("BANK", userCount.getBank());
			map.put("bankAccount", userCount.getBankAccount());
			map.put("FLAG", "1");
			map.put("ADVANCEMONEY", null);
			map.put("ADVANCEDATE", null);
			result.add(map);
		}
		return result;
	}

	@Override
	public Map<String, Object> findLoanData(String loanId) {

		AppInterfaceModel appModel = new AppInterfaceModel();
		Loanbal loanBal = loanbalMapper.selectByLoanId(loanId);
		UserCount userCount = userCountMapper.selectByCustId(loanBal.getCastId());
		Map<String, Object> map = new HashMap<>();
		map.put("loanId", loanBal.getLoanId());
		map.put("shouldTerm", loanBal.getCurrentPeriod());
		RepaymentPlan repaymentPlan = repaymentPlanMapper.selByLoanIdTerm(map);
		BigDecimal totalBalance = new BigDecimal(0); // 待还款总额
		BigDecimal Interest = new BigDecimal(0);// 剩余利息
		BigDecimal totalReceive = new BigDecimal(0);// 应还违约金总额
		BigDecimal totalReceivePenaltyInterest = new BigDecimal(0);// 应还剩余违罚金总额
		List<AppInterfaceLoanData> appInter = appInterfaceLoanDataMapper.selectByLoanId(loanId);
		String earlyRepa = "1";// 是否可以提前还款
		String repayments = "";
		for (AppInterfaceLoanData appInterfaceLoanData : appInter) {
			String hangUp = loanBal.getHangUp();
			int curr = appInterfaceLoanData.getCurrentPeriod();// 期次
			if (curr < loanBal.getCurrentPeriod()) {// 当前期次之前
				String settleFlag = appInterfaceLoanData.getSettleFlag();

				switch (settleFlag) {
				case "0":
					if (!"1".equals(repayments) && "2".equals(hangUp)) {// 当挂起时状态为待查证
																		// 并无法还款
						appInterfaceLoanData.setRepayments("0");
						appInterfaceLoanData.setState("5");
					} else if (!"1".equals(repayments) && !"2".equals(hangUp)) {
						repayments = "1";
						appInterfaceLoanData.setRepayments(repayments);
						appInterfaceLoanData.setState("0");
					} else {
						appInterfaceLoanData.setRepayments("0");
						appInterfaceLoanData.setState("0");
					}
					earlyRepa = "0";
					totalReceive = totalReceive.add(appInterfaceLoanData.getReceivePenalty());
					totalBalance = totalBalance.add(appInterfaceLoanData.getReceiveMoney());
					Interest = Interest.add(appInterfaceLoanData.getShouldInte());
					totalReceivePenaltyInterest = totalReceivePenaltyInterest.add(appInterfaceLoanData.getReceivePenaltyInterest());
					break;
				case "1":
					appInterfaceLoanData.setRepayments("0");
					appInterfaceLoanData.setState("1");
					break;
				case "2":
					appInterfaceLoanData.setRepayments("0");
					appInterfaceLoanData.setState("4");
					break;
				}
			}

			if (curr == loanBal.getCurrentPeriod()) {// 当前期次
				if (!"1".equals(repayments) && "2".equals(hangUp)) {
					appInterfaceLoanData.setRepayments("0");
					appInterfaceLoanData.setState("5");
					earlyRepa = "0";
				} else if (!"1".equals(repayments) && !"2".equals(hangUp)) {
					appInterfaceLoanData.setRepayments("1");
					appInterfaceLoanData.setState("2");
				} else {
					appInterfaceLoanData.setRepayments("0");
					appInterfaceLoanData.setState("2");
				}
				Interest = Interest.add(appInterfaceLoanData.getShouldInte());
				totalBalance = totalBalance.add(appInterfaceLoanData.getReceiveMoney());
			}
			if (curr > loanBal.getCurrentPeriod()) {// 期数小于当前期次
				Interest = Interest.add(appInterfaceLoanData.getShouldInte());
				totalBalance = totalBalance.add(appInterfaceLoanData.getReceiveMoney());
				appInterfaceLoanData.setRepayments("0");
				appInterfaceLoanData.setState("3");
			}
		}

		appModel.setCurrent(loanBal.getCurrentPeriod().toString());// 当前期数
		appModel.setTotalCurrent(loanBal.getLoanPeriod().toString());// 总期数
		appModel.setListMap(appInter);
		appModel.setBigNum(loanBal.getConMoney().setScale(2, BigDecimal.ROUND_UP));// 总金额
		appModel.setTotalBalance(totalBalance.setScale(2, BigDecimal.ROUND_UP));// 应还剩余金额
		appModel.setInterest(Interest.setScale(2, BigDecimal.ROUND_UP));// 应还剩余利息
		appModel.setTotalReceive(totalReceive.setScale(2, BigDecimal.ROUND_UP));// 应还违约金总额
		appModel.setEarlyRepa(earlyRepa);// 是否可以提前还款
		appModel.setEarlyRepayment(repaymentPlan.getAdvanceShould().setScale(2, BigDecimal.ROUND_UP));// 提前还款应还总额
		appModel.setTotalReceivePenaltyInterest(totalReceivePenaltyInterest.setScale(2, BigDecimal.ROUND_UP));// 剩余违罚金总额
		appModel.setReplaymentDate(loanBal.getRepayDate());// 应还款日期
		appModel.setBank(userCount.getBank());// 银行名称
		appModel.setBankCode(userCount.getBankCode());// 银行code
		appModel.setBankAccount(userCount.getBankAccount());// 银行卡号
		appModel.setBankPhone(userCount.getBankPhone());// 银行预留手机号
		appModel.setCardNo(loanBal.getCardNo());// 身份证号码
		return EntityToMap.ConvertObjToMap(appModel);

	}

	@Override
	public Map<String, Object> saveNomal(String custId, String loanId, Integer stream, HttpServletRequest req) {

		String path = req.getSession().getServletContext().getRealPath("/WEB-INF/classes/bf");// 宝付相关文件路径

		UserCount userCount = userCountMapper.selectByCustId(custId);

		Loanbal loanbal = loanbalMapper.selectByLoanId(loanId);

		// 查询还款明细表
		Map<String, Object> repaymentPlanparams = new HashMap<String, Object>();
		repaymentPlanparams.put("loanId", loanId);
		repaymentPlanparams.put("shouldTerm", stream);
		RepaymentPlan repaymentPlan = repaymentPlanMapper.selByLoanIdTerm(repaymentPlanparams);

		Date noramlSubDate = loanbal.getNormalSubDate();
		BigDecimal normalSubMoney = loanbal.getNormalSubMoney();
		Integer normalSubStream = loanbal.getNormalSubStream();

		BigDecimal amt = repaymentPlan.getShouldAmt();
		BigDecimal bal = userCount.getBal();
		BigDecimal deductMoney = amt;

		// 如果有减免，判断使用减免金额
		if (normalSubMoney != null && normalSubMoney.compareTo(new BigDecimal(0)) > 0 && normalSubStream == stream && noramlSubDate.getTime() > new Date().getTime()) {
			amt = amt.subtract(normalSubMoney);
		}

		if (bal.compareTo(amt) > 0) {
			deductMoney = new BigDecimal(0);
		} else {
			deductMoney = amt.subtract(bal);
		}

		if (deductMoney.compareTo(new BigDecimal(100)) < 0) {
			deductMoney = new BigDecimal(100);

		}

		DeductServerResult deductServerResult = deductBFServer.saveNomalDeduct(loanId, stream, deductMoney, path, userCount.getCastId(), userCount.getCastName());

		Map<String, Object> returnmap = new HashMap<String, Object>();

		if (deductServerResult.getSuccess()) {
			returnmap.put("status", "0");
		} else {
			returnmap.put("status", "-1");
		}
		returnmap.put("msg", deductServerResult.getSuccess());

		return returnmap;
	}

	@Override
	public Map<String, Object> saveAdvenced(String custId, String loanId, Integer stream, HttpServletRequest req) {

		String path = req.getSession().getServletContext().getRealPath("/WEB-INF/classes/bf");// 宝付相关文件路径

		UserCount userCount = userCountMapper.selectByCustId(custId);

		Loanbal loanbal = loanbalMapper.selectByLoanId(loanId);

		// 查询还款明细表
		Map<String, Object> repaymentPlanparams = new HashMap<String, Object>();
		repaymentPlanparams.put("loanId", loanId);
		repaymentPlanparams.put("shouldTerm", stream);
		RepaymentPlan repaymentPlan = repaymentPlanMapper.selByLoanIdTerm(repaymentPlanparams);

		BigDecimal advancedSubMoney = loanbal.getAdvancedSubMoney();
		Integer advancedSubStream = loanbal.getAdvancedSubStream();
		Date advancedSubDate = loanbal.getAdvancedSubDate();

		BigDecimal advanceMoney = repaymentPlan.getAdvanceMoney();
		BigDecimal bal = userCount.getBal();
		BigDecimal deductMoney = advanceMoney;

		// 如果有减免，判断使用减免金额
		if (advancedSubMoney != null && advancedSubMoney.compareTo(new BigDecimal(0)) > 0 && advancedSubStream == stream && advancedSubDate.getTime() > new Date().getTime()) {
			advanceMoney = advanceMoney.subtract(advancedSubMoney);
		}

		if (bal.compareTo(advanceMoney) > 0) {
			deductMoney = new BigDecimal(0);
		} else {
			deductMoney = advanceMoney.subtract(bal);
		}

		if (deductMoney.compareTo(new BigDecimal(100)) < 0) {
			deductMoney = new BigDecimal(100);

		}

		DeductServerResult deductServerResult = deductBFServer.saveAdvanceDeduct(loanId, stream, deductMoney, path, userCount.getCastId(), userCount.getCastName());

		Map<String, Object> returnmap = new HashMap<String, Object>();

		if (deductServerResult.getSuccess()) {
			returnmap.put("status", "0");
		} else {
			returnmap.put("status", "-1");
		}
		returnmap.put("msg", deductServerResult.getSuccess());

		return returnmap;
	}

	@Override
	public Map<String, Object> saveOverdue(String custId, String loanId, Integer stream, HttpServletRequest req) {
		String path = req.getSession().getServletContext().getRealPath("/WEB-INF/classes/bf");// 宝付相关文件路径

		UserCount userCount = userCountMapper.selectByCustId(custId);

		Loanbal loanbal = loanbalMapper.selectByLoanId(loanId);

		// 查询还款明细表
		Map<String, Object> repaymentPlanparams = new HashMap<String, Object>();
		repaymentPlanparams.put("loanId", loanId);
		repaymentPlanparams.put("shouldTerm", stream);
		RepaymentPlan repaymentPlan = repaymentPlanMapper.selByLoanIdTerm(repaymentPlanparams);

		Map<String, Object> overParams = new HashMap<String, Object>();
		overParams.put("loanId", loanId);
		overParams.put("stream", stream);
		OverdueRecord overdueRecord = overdueRecordMapper.selectByLoanIdAndStream(overParams);

		BigDecimal overdueSubMoney = loanbal.getOverdueSubMoney();
		Integer overdueSubStream = loanbal.getOverdueSubStream();
		Date overdueSubDate = loanbal.getOverdueSubDate();

		BigDecimal receiveMoney = overdueRecord.getReceiveMoney();
		BigDecimal bal = userCount.getBal();
		BigDecimal deductMoney = receiveMoney;

		// 如果有减免，判断使用减免金额
		if (overdueSubMoney != null && overdueSubMoney.compareTo(new BigDecimal(0)) > 0 && overdueSubStream == stream && overdueSubDate.getTime() > new Date().getTime()) {
			receiveMoney = receiveMoney.subtract(overdueSubMoney);
		}

		if (bal.compareTo(receiveMoney) > 0) {
			deductMoney = new BigDecimal(0);
		} else {
			deductMoney = receiveMoney.subtract(bal);
		}

		if (deductMoney.compareTo(new BigDecimal(100)) < 0) {
			deductMoney = new BigDecimal(100);

		}

		DeductServerResult deductServerResult = deductBFServer.saveOverdueDeduct(loanId, stream, deductMoney, path, userCount.getCastId(), userCount.getCastName());

		Map<String, Object> returnmap = new HashMap<String, Object>();

		if (deductServerResult.getSuccess()) {
			returnmap.put("status", "0");
		} else {
			returnmap.put("status", "-1");
		}
		returnmap.put("msg", deductServerResult.getSuccess());

		return returnmap;
	}

	@Override
	public Map<String, Object> getbal(String custId) {

		UserCount userCount = userCountMapper.selectByCustId(custId);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bal", userCount.getBal());

		return map;
	}

	@Override
	public List<Map<String, Object>> getApppayment(String custId) {
		List<Export> exports = exportMapper.selectByCustId(custId);
		List<Deduct> deducts = deductMapper.selectByCustId(custId);

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		for (Export export : exports) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", " ");// 订单号
			map.put("custId", export.getCastId());// 客户ID
			map.put("payType", "1");// 充值状态 （成功：1，失败：-1，待定：2， 待定不查证：4）
			map.put("payMoney", export.getExpendMoney().multiply(new BigDecimal(-1)));// 充值金额
			map.put("stream", export.getExpendStream());// 期次
			map.put("creatTime", export.getCreateTime());
			map.put("exportLoanId", export.getLoanId());

			result.add(map);
		}
		for (Deduct deduct : deducts) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", deduct.getOrderNo());// 订单号
			map.put("custId", deduct.getCastId());// 客户ID

			String status = deduct.getDeductState();
			if ("0".equals(status)) {
				map.put("payType", "1");// 充值状态 （成功：1，失败：-1，待定：2， 待定不查证：4）
			} else if ("2".equals(status)) {
				map.put("payType", "4");// 充值状态 （成功：1，失败：-1，待定：2， 待定不查证：4）
			} else {
				map.put("payType", "-1");// 充值状态 （成功：1，失败：-1，待定：2， 待定不查证：4）

			}

			map.put("payMoney", deduct.getDeductMoney());// 充值金额
			map.put("stream", deduct.getDedutStream());// 期次
			map.put("creatTime", deduct.getCreateTime());
			map.put("exportLoanId", deduct.getLoanId());
			result.add(map);
		}

		Collections.sort(result, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				if (o1.get("creatTime") == null && o2.get("creatTime") == null) {
					return 0;
				}
				if (o1.get("creatTime") == null) {
					return -1;
				}
				if (o2.get("creatTime") == null) {
					return 1;
				}

				Date d1 = (Date) o1.get("creatTime");
				Date d2 = (Date) o2.get("creatTime");

				if (d1.before(d2)) {
					return -1;
				} else {
					return 1;
				}

			}
		});

		return result;

	}

	@Override
	public Map<String, Object> saveAppBal(String custId, String bankName, String bankcode, String bankAccount, String bankPhone, String idCard, BigDecimal money, String ip, HttpServletRequest req) {
		String path = req.getSession().getServletContext().getRealPath("/WEB-INF/classes/bf");// 宝付相关文件路径

		List<Loanbal> loanbals = loanbalMapper.selectByCustId(custId);

		// 线上线下标识，只要此客户存在线下未结清的单子，即此客户属于下线客户否则属于线上客户
		String updownStatus = "1";
		for (Loanbal loanbal : loanbals) {

			if ("0".equals(loanbal.getUpdownStatus())) {
				updownStatus = "0";
				break;
			}

		}

		/**
		 * 判断传入的划扣金额不能小于100元
		 */

		if (money == null || money.compareTo(new BigDecimal(100)) < 0) {
			Map<String, Object> rmap = new HashMap<String, Object>();
			rmap.put("msg", "充值金额必须大于100");
			rmap.put("status", "-1");
			rmap.put("isyzm", "-1");
			return rmap;
		}

		// 查询账户表
		UserCount userCount = userCountMapper.selectByCustId(custId);

		// 充值
		if ("0".equals(updownStatus)) {
			// 线下充值

			/**
			 * 根据线上线下标识选择不同的划扣通道，划扣成功！修改账户余额
			 */

			BFdeductModel deductModel = new BFdeductModel();
			// 配置文件路径
			deductModel.setPath(path);
			// 银行卡编码
			deductModel.setPayCode(bankcode);
			// 银行卡号
			deductModel.setAccNo(bankAccount);
			// 身份证号
			deductModel.setIdCard(idCard);
			// 持卡人姓名
			deductModel.setIdHolder(userCount.getCastName());
			// 银行卡预留手机号
			deductModel.setMobile(bankPhone);
			// 交易金额
			deductModel.setTxnAmt(money);

			deductModel.setFlag(updownStatus);

			BFdeductResult bfdeductResult = BFdeductUtil.deduct(deductModel);
			// 划扣状态(0000 成功，1111 失败,2222查证)
			String deductStatus = bfdeductResult.getDeductStatus();
			String deductstatus_01;
			// 订单号
			String transId = bfdeductResult.getTransId();
			// 划扣时间
			Date deductDate = bfdeductResult.getDeductDate();
			// 返回码
			String respCode = bfdeductResult.getRespCode();
			// 返回信息
			String respMsg = bfdeductResult.getRespMsg();
			// 划扣金额
			BigDecimal bfdeductMoney = bfdeductResult.getDeductMoney();
			// 划扣渠道
			String additionalInfo = bfdeductResult.getAdditionalInfo();
			// 划扣手续费
			BigDecimal feesMoney = bfdeductResult.getFeesMoney();
			/**
			 * 保存划扣记录
			 */
			if ("0000".equals(deductStatus)) {
				// 划扣成功
				deductstatus_01 = "0";
				Map<String, Object> userCountparams = new HashMap<String, Object>();

				userCountparams.put("custId", custId);
				userCountparams.put("bal", userCount.getBal().add(bfdeductMoney));
				userCountMapper.updatebal(userCountparams);

			} else if ("1111".equals(deductStatus)) {
				// 划扣失败
				deductstatus_01 = "1";

			} else if ("2222".equals(deductStatus)) {
				// 划扣结果待定
				deductstatus_01 = "2";

			} else {
				Map<String, Object> rmap = new HashMap<String, Object>();
				rmap.put("msg", "网络异常...");
				rmap.put("status", "-1");
				rmap.put("isyzm", "-1");
				return rmap;
			}
			Deduct deduct = new Deduct();

			deduct.setId(PrimaryKeyUtil.getPrimaryKey());
			// 进件编号
			deduct.setLoanId("");
			// 合同编号
			deduct.setConNo("");
			// 客户姓名
			deduct.setCastName(userCount.getCastName());
			// 身份证号码
			deduct.setCardNo(userCount.getCardNo());
			// 银行名称
			deduct.setBank(userCount.getBank());
			// 银行预留手机号
			deduct.setBankPhone(userCount.getBankPhone());
			// 银行卡号
			deduct.setBankAccount(userCount.getBankAccount());
			// 银行编码
			deduct.setBankCode(userCount.getBankCode());
			// 划扣渠道（线上/线下宝付，线上/线下富友等）
			deduct.setDeductChannel(additionalInfo);
			// 划扣金额
			deduct.setDeductMoney(bfdeductMoney);
			// 划扣状态（成功0，失败1，待定2）
			deduct.setDeductState(deductstatus_01);
			// 划扣结果信息
			deduct.setDeductStateVal(respMsg);
			// 划扣结果CODE
			deduct.setDeductStateCode(respCode);
			// 划扣手续费
			deduct.setDeductServerMoney(feesMoney);
			// 划扣时客户所处期次
			deduct.setDedutStream(null);
			// 划扣类型 1 2 3 4
			deduct.setDedutType("4");
			// 划扣类型 值：正常月还，提前还款，逾期月还 余额充值
			deduct.setDedutTypeVal("余额充值");
			// 划扣时间
			deduct.setDedutDate(deductDate);
			// 查证时间
			deduct.setCheckDate(null);
			// 订单号
			deduct.setOrderNo(transId);
			// 划扣人员ID
			deduct.setApplicationCastId(custId);
			// 划扣人员Name
			deduct.setApplicationCastName(userCount.getCastName());
			// 创建日期
			deduct.setCreateTime(new Date());
			// 修改时间
			deduct.setUpdateDate(null);
			// 划扣前账户存在余额
			deduct.setDeductBal(userCount.getBal());
			// 客户ID
			deduct.setCastId(custId);
			deductMapper.insert(deduct);

			Map<String, Object> rmap = new HashMap<String, Object>();
			rmap.put("msg", respMsg);
			if ("0".equals(deductstatus_01)) {
				rmap.put("status", "0");
			} else if ("1".equals(deductstatus_01)) {
				rmap.put("status", "-1");
			} else {
				rmap.put("status", "2");
			}
			rmap.put("businessNo", transId);
			rmap.put("isyzm", "-1");
			return rmap;
		} else if ("1".equals(updownStatus)) {
			// 线上充值

			/**
			 * 查询客户是否绑卡
			 */
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("custId", custId);
			params.put("bankAccoun", bankAccount);
			AppBk appBK = appBkMapper.selectByCustIdAndBankAccount(params);

			if (appBK == null) {
				appBK = this.bfbk(custId, userCount.getCastName(), idCard, bankPhone, bankName, bankcode, bankAccount, "1", req);
			}

			if ("0".equals(appBK.getStatus())) {
				/**
				 * 认证支付 发送验证码
				 */

				BfBFCertifiedPayParam bfBFCertifiedPay = new BfBFCertifiedPayParam();
				bfBFCertifiedPay.setBindId(appBK.getBindId());
				bfBFCertifiedPay.setTxnAmt(money);
				bfBFCertifiedPay.setClientIp(ip);
				bfBFCertifiedPay.setFlag("1");
				bfBFCertifiedPay.setPath(path);

				BFCertifiedPayVo bFCertifiedPayVo = BFdeductUtil.BfBFCertifiedPay(bfBFCertifiedPay);

				String businessNo = bFCertifiedPayVo.getBusinessNo();

				String orderNo = bFCertifiedPayVo.getOrderNo();

				String deductStatus = bFCertifiedPayVo.getDeductStatus();

				String statusMsg = bFCertifiedPayVo.getStatusMsg();

				if ("0000".equals(deductStatus)) {
					Map<String, Object> rmap = new HashMap<String, Object>();
					rmap.put("msg", "验证码发送成功");
					rmap.put("status", "0");
					rmap.put("businessNo", businessNo);
					rmap.put("orderNo", orderNo);
					rmap.put("isyzm", "1");
					return rmap;
				} else {
					Map<String, Object> rmap = new HashMap<String, Object>();
					rmap.put("msg", statusMsg);
					rmap.put("status", "-1");
					rmap.put("isyzm", "-1");
					return rmap;
				}

			} else {
				Map<String, Object> rmap = new HashMap<String, Object>();
				rmap.put("msg", appBK.getStatusVal());
				rmap.put("status", "-1");
				rmap.put("isyzm", "-1");
				return rmap;
			}

		} else {

			Map<String, Object> rmap = new HashMap<String, Object>();
			rmap.put("msg", "无法获取您的支付通道，请联系客服");
			rmap.put("status", "-1");
			rmap.put("isyzm", "-1");
			return rmap;

		}

	}

	@Override
	public Map<String, Object> saveAppBal2(String custId, BigDecimal money, String yzm, String businessNo, String orderNo, HttpServletRequest req) {
		String path = req.getSession().getServletContext().getRealPath("/WEB-INF/classes/bf");// 宝付相关文件路径

		// 认证支付确认 存入余额 保存划扣记录
		// 查询账户表
		UserCount userCount = userCountMapper.selectByCustId(custId);

		BFCertifiedPayCheckParam bfCertifiedPayCheck = new BFCertifiedPayCheckParam();
		bfCertifiedPayCheck.setPath(path);
		bfCertifiedPayCheck.setSmsCode(yzm);
		bfCertifiedPayCheck.setBusinessNo(businessNo);
		bfCertifiedPayCheck.setFlag("1");// 线上
		bfCertifiedPayCheck.setOrderNo(orderNo);

		BFCertifiedPayVo bFCertifiedPayVo = BFdeductUtil.BfBFCertifiedPayCheck(bfCertifiedPayCheck);

		// 划扣状态(0000 成功，1111 失败,2222查证)
		String deductStatus = bFCertifiedPayVo.getDeductStatus();
		String deductstatus_01;
		// 订单号
		String transId = bFCertifiedPayVo.getOrderNo();
		// 划扣时间
		Date deductDate = bFCertifiedPayVo.getDeductDate();
		// 返回码
		String respCode = null;
		// 返回信息
		String respMsg = bFCertifiedPayVo.getStatusMsg();
		// 划扣金额
		BigDecimal bfdeductMoney = money;
		// 划扣渠道
		String additionalInfo = bFCertifiedPayVo.getAdditionalInfo();
		// 划扣手续费
		BigDecimal feesMoney = null;
		/**
		 * 保存划扣记录
		 */
		if ("0000".equals(deductStatus)) {
			// 划扣成功
			deductstatus_01 = "0";
			Map<String, Object> userCountparams = new HashMap<String, Object>();

			userCountparams.put("custId", custId);
			userCountparams.put("bal", userCount.getBal().add(bfdeductMoney));
			userCountMapper.updatebal(userCountparams);

		} else if ("1111".equals(deductStatus)) {
			// 划扣失败
			deductstatus_01 = "1";

		} else if ("2222".equals(deductStatus)) {
			// 划扣结果待定
			deductstatus_01 = "2";

		} else {
			Map<String, Object> rmap = new HashMap<String, Object>();
			rmap.put("msg", "充值失败...");
			rmap.put("status", "-1");
			return rmap;
		}
		Deduct deduct = new Deduct();

		deduct.setId(PrimaryKeyUtil.getPrimaryKey());
		// 进件编号
		deduct.setLoanId("");
		// 合同编号
		deduct.setConNo("");
		// 客户姓名
		deduct.setCastName(userCount.getCastName());
		// 身份证号码
		deduct.setCardNo(userCount.getCardNo());
		// 银行名称
		deduct.setBank(userCount.getBank());
		// 银行预留手机号
		deduct.setBankPhone(userCount.getBankPhone());
		// 银行卡号
		deduct.setBankAccount(userCount.getBankAccount());
		// 银行编码
		deduct.setBankCode(userCount.getBankCode());
		// 划扣渠道（线上宝付认证支付）
		deduct.setDeductChannel(additionalInfo);
		// 划扣金额
		deduct.setDeductMoney(bfdeductMoney);
		// 划扣状态（成功0，失败1，待定2）
		deduct.setDeductState(deductstatus_01);
		// 划扣结果信息
		deduct.setDeductStateVal(respMsg);
		// 划扣结果CODE
		deduct.setDeductStateCode(respCode);
		// 划扣手续费
		deduct.setDeductServerMoney(feesMoney);
		// 划扣时客户所处期次
		deduct.setDedutStream(null);
		// 划扣类型 1 2 3 4
		deduct.setDedutType("4");
		// 划扣类型 值：正常月还，提前还款，逾期月还 余额充值
		deduct.setDedutTypeVal("APP线上余额充值");
		// 划扣时间
		deduct.setDedutDate(deductDate);
		// 查证时间
		deduct.setCheckDate(null);
		// 订单号
		deduct.setOrderNo(transId);
		// 划扣人员ID
		deduct.setApplicationCastId(custId);
		// 划扣人员Name
		deduct.setApplicationCastName(userCount.getCastName());
		// 创建日期
		deduct.setCreateTime(new Date());
		// 修改时间
		deduct.setUpdateDate(null);
		// 划扣前账户存在余额
		deduct.setDeductBal(userCount.getBal());
		// 客户ID
		deduct.setCastId(custId);
		deductMapper.insert(deduct);

		Map<String, Object> rmap = new HashMap<String, Object>();
		rmap.put("msg", respMsg);
		if ("0".equals(deductstatus_01)) {
			rmap.put("status", "0");
		} else if ("1".equals(deductstatus_01)) {
			rmap.put("status", "-1");
		} else {
			rmap.put("status", "2");
		}
		return rmap;
	}

	private AppBk bfbk(String custId, String custName, String idCard, String phoneNumber, String bankName, String bankCode, String bankAccount, String updownStatus, HttpServletRequest req) {

		String path = req.getSession().getServletContext().getRealPath("/WEB-INF/classes/bf");// 宝付相关文件路径

		BFCertifiedBKParam bFCertifiedBKParam = new BFCertifiedBKParam();
		bFCertifiedBKParam.setCustName(custName);
		bFCertifiedBKParam.setIdCard(idCard);
		bFCertifiedBKParam.setPhoneNumber(phoneNumber);
		bFCertifiedBKParam.setBankName(bankName);
		bFCertifiedBKParam.setBankAccount(bankAccount);
		bFCertifiedBKParam.setBankCode(bankCode);
		bFCertifiedBKParam.setFlag(updownStatus);
		bFCertifiedBKParam.setPath(path);

		BFCertifiedBKVo bFCertifiedBKVo = BFdeductUtil.BfBindCard(bFCertifiedBKParam);

		String bindStatus = bFCertifiedBKVo.getBindStatus();
		String orderNo = bFCertifiedBKVo.getOrderNo();
		String bindId = bFCertifiedBKVo.getBindId();
		String statusMsg = bFCertifiedBKVo.getStatusMsg();

		/**
		 * 保存绑卡信息
		 */
		AppBk abk = new AppBk();
		abk.setBankAccount(bankAccount);
		abk.setBankCode(bankCode);
		abk.setBankName(bankName);
		abk.setCreateTime(new Date());
		abk.setCustId(custId);
		abk.setCustName(custName);
		abk.setId(PrimaryKeyUtil.getPrimaryKey());
		abk.setIdCard(idCard);
		abk.setOrderNo(orderNo);// 订单号
		abk.setPhoneNumber(phoneNumber);
		abk.setStatus(bindStatus);// 绑卡状态
		abk.setBindId(bindId);// bindID
		abk.setStatusVal(statusMsg);
		appBkMapper.insert(abk);

		return abk;
	}

	@Override
	public Map<String, Object> updateBank(String custId, String bank, String bankPhone, String bankAccount, String bankCode, String bankCode2, String bankCode3) {

		UserCount userCount = userCountMapper.selectByCustId(custId);

		/**
		 * 插入信息变更资料表
		 */
		ChangeInfo changeInfo = new ChangeInfo();

		changeInfo.setId(PrimaryKeyUtil.getPrimaryKey());
		changeInfo.setApplyId("");
		changeInfo.setApplyType("bankChange_app");
		changeInfo.setCustId(custId);
		changeInfo.setSex(userCount.getSex());
		changeInfo.setCastName(userCount.getCastName());
		changeInfo.setCardNo(userCount.getCardNo());
		// changeInfo.setOldTel(messageUpdateParam.getOldTel());
		// changeInfo.setNewTel(messageUpdateParam.getNewTel());
		changeInfo.setOldBank(userCount.getBank());
		changeInfo.setOldBankPhone(userCount.getBankPhone());
		changeInfo.setOldBankAccount(userCount.getBankAccount());
		changeInfo.setOldBankCode(userCount.getBankCode());
		changeInfo.setNewBank(bank);
		changeInfo.setNewBankPhone(bankPhone);
		changeInfo.setNewBankAccount(bankAccount);
		changeInfo.setNewBankCode(bankCode);
		changeInfo.setChangeinfoStatus("0");
		changeInfo.setCreateTime(new Date());
		changeInfo.setUpdateDate(null);
		changeInfoMapper.insert(changeInfo);

		/**
		 * 插入申请单表
		 */
		ApplyFo applyFo = new ApplyFo();
		applyFo.setId(PrimaryKeyUtil.getPrimaryKey());
		applyFo.setApplyId("");
		applyFo.setApplyType("bankChange");
		applyFo.setApplicationCastId(custId);
		applyFo.setApplicationCastName(userCount.getCastName());
		applyFo.setApplicationDesc("app端变更银行卡");
		applyFo.setCastName(userCount.getCastName());
		applyFo.setCardNo(userCount.getCardNo());
		applyFo.setConNo(null);
		applyFo.setLoanId(null);
		applyFo.setApplyStatus("0000");
		applyFo.setApplyReturn(null);
		applyFo.setCreateTime(new Date());
		applyFo.setUpdateDate(null);
		applyFoMapper.insert(applyFo);

		/**
		 * 插入流程环节表
		 */
		ApplyDetail applyDetail = new ApplyDetail();
		applyDetail.setId(PrimaryKeyUtil.getPrimaryKey());
		applyDetail.setApplyId("");
		applyDetail.setApplyType("bankChange_app");
		applyDetail.setApplyStatus("2");
		applyDetail.setApproveId(userCount.getCastId());
		applyDetail.setApproveName(userCount.getCastName());
		applyDetail.setApproveDesc("app端变更银行卡");
		applyDetail.setApproveOpinion("0");
		applyDetail.setCreateTime(new Date());
		applyDetailMapper.insert(applyDetail);

		userCount.setBank(bankCode3);
		userCount.setBankAccount(bankAccount);
		userCount.setBankPhone(bankPhone);
		userCount.setBankCode(bankCode3);
		userCount.setBankCode2(bankCode3);
		userCount.setBankCode3(bankCode3);
		userCountMapper.updateByPrimaryKeySelective(userCount);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("mag", "银行卡变更推送成功");

		return result;
	}

}
