/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.after.api.model.param.DataAll;
import com.hoomsun.after.api.model.table.Deduct;
import com.hoomsun.after.api.model.table.Loanbal;
import com.hoomsun.after.api.model.table.Loanbook;
import com.hoomsun.after.api.model.table.OverdueRecord;
import com.hoomsun.after.api.model.table.UserCount;
import com.hoomsun.after.api.server.SendDateServer;
import com.hoomsun.after.dao.DeductMapper;
import com.hoomsun.after.dao.LoanbalMapper;
import com.hoomsun.after.dao.LoanbookMapper;
import com.hoomsun.after.dao.OverdueRecordMapper;
import com.hoomsun.after.dao.UserCountMapper;
import org.apache.log4j.Logger;

/**
 * 作者：金世强 <br>
 * 创建时间：2018年3月21日 <br>
 * 描述：数据迁移相关处理
 */
@Service("sendDateServer")
public class SendDateServerImpl implements SendDateServer {

	private static final Logger LOG = Logger.getLogger(SendDateServerImpl.class);

	
	@Autowired
	private UserCountMapper userCountMapper;

	@Autowired
	private LoanbalMapper loanbalMapper;

	@Autowired
	private DeductMapper deductMapper;

	@Autowired
	private OverdueRecordMapper overdueRecordMapper;

	@Autowired
	private LoanbookMapper loanbookMapper;

	@Override
	public Map<String, Object> saveDate(DataAll dataAll) {

	
		
		Map<String, Object> result = new HashMap<String, Object>();

		// userconut表
		UserCount userCount = dataAll.getUserCount();
		// loanbal表
		Loanbal loanbal = dataAll.getLoanbal();
		
		// 逾期记录表
		List<OverdueRecord> overdueRecords = dataAll.getOverdueRecord();
		// 划扣记录表
		List<Deduct> deducts = dataAll.getDeduct();
		// loanbook表
		List<Loanbook> loanBooks = dataAll.getLoanbook();

		// 客户Id
		String custId = userCount.getCastId();

		// 进件编号
		String loanId = loanbal.getLoanId();

		LOG.info("推送数据："+loanId);
		// 逾期标识
		String delayFlag = loanbal.getDelayFlag();

		if ("1".equals(delayFlag)) {
			if (overdueRecords == null || overdueRecords.size() <= 0) {
				result.put("loanId", loanId);
				result.put("success", 1);
				result.put("msg", "逾期客户没有逾期信息");
				return result;
			}

		}
		
		if (userCountMapper.selectByCustId(custId) == null) {
			userCountMapper.insert(userCount);
		}
		loanbalMapper.insert(loanbal);

		for (OverdueRecord overdueRecord : overdueRecords) {
			overdueRecordMapper.insert(overdueRecord);
		}
		for (Deduct deduct : deducts) {
			deductMapper.insert(deduct);
		}
		for (Loanbook loanbook : loanBooks) {
			loanbookMapper.insert(loanbook);
		}

		result.put("success", 0);
		result.put("msg", "OK");
		LOG.info("推送数据返回："+result);
		return result;
	}
}
