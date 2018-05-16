/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.server.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.core.util.PrimaryKeyUtil;
import com.hoomsun.csas.sales.api.exception.AuditException;
import com.hoomsun.csas.sales.api.model.SavingsBills;
import com.hoomsun.csas.sales.api.model.SavingsCard;
import com.hoomsun.csas.sales.api.server.inter.SavingsCardServerI;
import com.hoomsun.csas.sales.dao.SavingsBillsMapper;
import com.hoomsun.csas.sales.dao.SavingsCardMapper;

/**
 * 作者：liusong <br>
 * 创建时间：2017年12月27日 <br>
 * 描述：
 */
@Service("savingsCardServer")
public class SavingsCardServerImpl implements SavingsCardServerI {
	@Autowired
	private SavingsCardMapper savingsCardMaper;
	@Autowired
	private SavingsBillsMapper savingsBillsMapper;

	@Override
	public Json createCard(SavingsCard record) {
		if (StringUtils.isBlank(record.getScId())) {
			record.setScId(PrimaryKeyUtil.getPrimaryKey());
		}
		int total = 0;
		// 验证储蓄卡数据是否已经存在
		int result = savingsCardMaper.countCard(record.getApplyId());
		if (result > 0) {// 存在修改
			total += savingsCardMaper.updateByPrimaryKeySelective(record);
		} else {// 新增
			total += savingsCardMaper.insertSelective(record);
		}

		int count = savingsBillsMapper.countBills(record.getScId());
		if (count > 0) {
			total += savingsBillsMapper.deleteByScId(record.getScId());
		}

		for (SavingsBills bills : record.getSavingsBills()) {
			if (StringUtils.isBlank(bills.getSbId())) {
				bills.setSbId(PrimaryKeyUtil.getPrimaryKey());
			}
			bills.setScId(record.getScId());
			total += savingsBillsMapper.insertSelective(bills);
		}
		if (total > 0) {
			return new Json(true, "添加成功");
		} else {
			return new Json(false, "添加失败");
		}
	}


	@Override
	public Json addSavingsCard(SavingsCard savingsCard) {
		if (StringUtils.isBlank(savingsCard.getScId())) {
			savingsCard.setScId(PrimaryKeyUtil.getPrimaryKey());
		}
		int total = 0;
		// 验证储蓄卡数据是否已经存在
		int result = savingsCardMaper.countCard(savingsCard.getApplyId());
		if (result > 0) {// 存在修改
			total += savingsCardMaper.updateByPrimaryKeySelective(savingsCard);
		} else {// 新增
			savingsCard.setCreateDate(DateUtil.getSqlDate());
			total += savingsCardMaper.insertSelective(savingsCard);
		}
		int count = savingsBillsMapper.countBills(savingsCard.getScId());
		if (count > 0) {
			total += savingsBillsMapper.deleteByScId(savingsCard.getScId());
		}

		for (SavingsBills bills : savingsCard.getSavingsBills()) {
			if (StringUtils.isBlank(bills.getSbId())) {
				bills.setSbId(PrimaryKeyUtil.getPrimaryKey());
			}
			bills.setScId(savingsCard.getScId());
			total += savingsBillsMapper.insertSelective(bills);
		}
		
		if (total > 0) {
			return new Json(true, "添加成功");
		} else {
			return new Json(false, "添加失败");
		}
	}
	@Override
	public SavingsCard selectByApplyId(String applyId) {
		return savingsCardMaper.findByApplyId(applyId);
	}


	@Override
	public SavingsCard findApplyById(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			throw new AuditException("参数异常！applyId is null");
		} 
		return savingsCardMaper.findByApplyId(applyId);
	}

}
