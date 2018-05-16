/**
s * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.server.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.risk.dao.UserApplyMapperRisk;
import com.hoomsun.risk.dao.mongo.UserApplyDao;
import com.hoomsun.risk.dao.mongo.UserContactDao;
import com.hoomsun.risk.model.UserApply;
import com.hoomsun.risk.model.UserContact;
import com.hoomsun.risk.server.inter.ApplyHistoryServerI;
import com.hoomsun.risk.server.inter.BlackListMatchServerI;
import com.hoomsun.risk.server.inter.CallTopMatchServerI;
import com.hoomsun.risk.server.inter.CheatFunnyServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月8日 <br>
 * 描述：反欺诈数据的勾稽业务
 */
@Service("cheatFunnyServerRisk")
public class CheatFunnyServerImpl implements CheatFunnyServerI {
	private static final Logger log = LoggerFactory.getLogger(SynchronousServerImpl.class);
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	@Autowired
	private UserApplyMapperRisk userApplyMapper;
	@Autowired
	private UserApplyDao userApplyDao;
	@Autowired
	private UserContactDao userContactDao;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private ApplyHistoryServerI applyHistoryServer;
	@Autowired
	private BlackListMatchServerI blackListMatchServer;
	@Autowired
	private CallTopMatchServerI callTopMatchServer;

	@Override
	public Json cheatMatch(String applyId) {
		log.info("##########【反欺诈】开始###########");
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数异常!applyId is null!");
		}

		threadPoolTaskExecutor.execute(new CheatRunnable(applyId));

		return null;
	}

	class CheatRunnable implements Runnable {
		private String applyId;

		public CheatRunnable(String applyId) {
			this.applyId = applyId;
		}

		public String getApplyId() {
			return applyId;
		}

		public void setApplyId(String applyId) {
			this.applyId = applyId;
		}

		@Override
		public void run() {
			UserApply userApply = userApplyDao.findByApplyId(applyId);
			// 申请信息不存在 拉取申请数据
			if (userApply == null) {
				userApply = userApplyMapper.findUserApplyById(applyId);
				mongoTemplate.save(userApply);
			}

			if (userApply == null) {
				log.info("【反欺诈】没有获取申请信息");
				return;
			}
			// 联系人
			List<UserContact> contacts = userContactDao.findByApplyId(applyId);
			// 申请历史
			applyHistoryServer.matchApplyHistory(userApply, contacts);
			// 黑名单
			blackListMatchServer.matchApplyHistory(userApply, contacts);
			// 通话记录TOP-N
			callTopMatchServer.matchCallTop(userApply);
		}
	}

}
