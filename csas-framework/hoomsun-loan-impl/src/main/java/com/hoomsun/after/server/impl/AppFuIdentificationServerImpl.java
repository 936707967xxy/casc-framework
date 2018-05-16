/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.server.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.after.api.model.param.FYProjectEntryParam;
import com.hoomsun.after.api.model.param.FYdeductModel;
import com.hoomsun.after.api.model.table.Loanbal;
import com.hoomsun.after.api.model.table.UserCount;
import com.hoomsun.after.api.server.AppFuIdentificationServer;
import com.hoomsun.after.api.util.FYutil.FYdeductUtil;
import com.hoomsun.after.api.util.property.BankCode;
import com.hoomsun.after.dao.LoanbalMapper;
import com.hoomsun.after.dao.UserCountMapper;
import com.hoomsun.common.model.Json;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年5月14日 <br>
 * 描述：
 */
@Service("appFuIdentificationServer")
public class AppFuIdentificationServerImpl implements AppFuIdentificationServer {

	@Autowired
	private LoanbalMapper loanbalMapper;

	@Autowired
	private UserCountMapper userCountMapper;

	@Override
	public Map<String, Object> getIdentification(String custId, HttpServletRequest req) {
		List<Loanbal> loanbals = loanbalMapper.selectByCustId(custId);
		/**
		 * 查询loanbal
		 */
		if (loanbals == null || loanbals.size() <= 0) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("flag", 0);
			result.put("msg", "不需要富友认证");
			return result;
		}

		/**
		 * 查询需要认证富有的客户
		 */
		List<String> lists = new ArrayList<String>();
		for (Loanbal loanbal : loanbals) {
			if ("0".equals(loanbal.getUpdownStatus())) {
				if (loanbal.getProductId() == null || "".equals(loanbal.getProductId())) {
					lists.add(loanbal.getLoanId());
				}
			}

		}

		/**
		 * 返回需要认证富有的客户的LoanId
		 */
		if (lists == null || lists.size() <= 0) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("flag", 0);
			result.put("msg", "不需要富友认证");
			return result;

		} else {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("flag", 1);
			result.put("msg", "需要富友认证");
			result.put("loanIds", lists);
			result.put("size", lists.size());
			result.put("custId", custId);
			return result;
		}

	}

	@Override
	public Map<String, Object> saveProduct(String custId, String loanId, HttpServletRequest req) {
		String path = req.getSession().getServletContext().getRealPath("/WEB-INF/classes/fu");// 宝付相关文件路径

		UserCount userCount = userCountMapper.selectByCustId(custId);

		Loanbal loanbal = loanbalMapper.selectByLoanId(loanId);

		FYProjectEntryParam fyProjectEntry = new FYProjectEntryParam("0", loanbal.getConMoney(), loanbal.getConNo(), String.valueOf(loanbal.getLoanPeriod() * 30), userCount.getCastName(), userCount.getCardNo(), userCount.getBankAccount(),
				userCount.getBankPhone());

		Map<String, String> fyProhectEntryResult = FYdeductUtil.FyProhectEntry(fyProjectEntry);

		// 订单号
		String retOrderno = fyProhectEntryResult.get("retOrderno");
		// 项目ID
		String projectId = fyProhectEntryResult.get("projectId");
		// 返回信息
		String memo = fyProhectEntryResult.get("memo");
		// 是否成功
		String ret = fyProhectEntryResult.get("ret");

		if (!"0000".equals(ret)) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("flag", 0);
			result.put("msg", memo);

			return result;
		}
		FYdeductModel fYdeduct = new FYdeductModel();
		// 账号
		fYdeduct.setAccntno(userCount.getBankAccount());
		// 账户名称
		fYdeduct.setAccntnm(userCount.getBank());
		fYdeduct.setAmt(null);
		fYdeduct.setMobile(userCount.getBankPhone());
		fYdeduct.setCertno(userCount.getCardNo());
		fYdeduct.setBankno(userCount.getBankCode3());
		fYdeduct.setVerifyCode(null);
		fYdeduct.setUpdownStatus("0");
		fYdeduct.setPrijectid(projectId);
		fYdeduct.setPath(path);
		Json j = FYdeductUtil.elements5Check(fYdeduct);

		if (!j.getSuccess()) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("flag", 0);
			result.put("msg", j.getMsg());

			return result;
		}

		Map<String, Object> result = new HashMap<String, Object>();

		if ("0".equals(j.getData())) {
			result.put("flag", 1);// 不在需要验证码，直接OK了

			/**
			 * 插入相应字段中
			 */
			Map<String, Object> loanbalParams = new HashMap<String, Object>();
			loanbalParams.put("loanId", loanId);
			loanbalParams.put("projectId", projectId);
			loanbalMapper.updateProjectId(loanbalParams);

		} else {
			result.put("flag", 2);// 需要验证码
		}
		result.put("msg", "验证码发送成功");
		result.put("custId", custId);
		result.put("loanId", loanId);
		result.put("projectId", projectId);

		return result;
	}

	@Override
	public Map<String, Object> saveIdentification(String custId, String loanId, String projectId, String yzm, HttpServletRequest req) {

		String path = req.getSession().getServletContext().getRealPath("/WEB-INF/classes/fu");// 宝付相关文件路径

		UserCount userCount = userCountMapper.selectByCustId(custId);

		Loanbal loanbal = loanbalMapper.selectByLoanId(loanId);

		FYdeductModel fYdeduct = new FYdeductModel();
		// 账号
		fYdeduct.setAccntno(userCount.getBankAccount());
		// 账户名称
		fYdeduct.setAccntnm(userCount.getBank());
		fYdeduct.setAmt(null);
		fYdeduct.setMobile(userCount.getBankPhone());
		fYdeduct.setCertno(userCount.getCardNo());
		fYdeduct.setBankno(userCount.getBankCode3());
		fYdeduct.setVerifyCode(yzm);// 验证码
		fYdeduct.setUpdownStatus("0");
		fYdeduct.setPrijectid(projectId);
		fYdeduct.setPath(path);
		Json j = FYdeductUtil.elements5Msg(fYdeduct);

		if (!j.getSuccess()) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("flag", 0);
			result.put("msg", j.getMsg());
			return result;
		}

		/**
		 * 插入相应字段中
		 */
		Map<String, Object> loanbalParams = new HashMap<String, Object>();
		loanbalParams.put("loanId", loanId);
		loanbalParams.put("projectId", projectId);
		loanbalMapper.updateProjectId(loanbalParams);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("flag", 1);
		result.put("msg", j.getMsg());
		return result;
	}

}
