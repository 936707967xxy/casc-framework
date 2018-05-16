/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.flow;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hoomsun.csas.audit.dao.BPMNMapper;

/**
 * 作者：liusong <br>
 * 创建时间：2018年1月18日 <br>
 * 描述：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-cfg.xml")
public class ActiTest {

	@Autowired
	private HistoryService historyService;
	@Autowired
	private BPMNMapper bpmnMapper;
	
	@Test
	public void notifyTest() {
//		StringBuilder sql = new StringBuilder("SELECT hi.ASSIGNEE_,MAX(hi.END_TIME_) END_TIME_ FROM ACT_HI_TASKINST hi ");
//		sql.append("LEFT JOIN ACT_HI_PROCINST hp on hi.PROC_INST_ID_ = hp.ID_ ");
//		sql.append("WHERE hp.BUSINESS_KEY_=#{applyId} and hi.ASSIGNEE_ is not null and hi.END_TIME_ is not null ");
//		sql.append("and hp.PROC_INST_ID_=#{procInstId} and hi.TASK_DEF_KEY_='makingEvent'");
//		sql.append("GROUP by hi.ASSIGNEE_,hi.TASK_DEF_KEY_");
//
//		HistoricTaskInstance historicTaskInstance = historyService
//				.createNativeHistoricTaskInstanceQuery()
//				.sql(sql.toString())
//				.parameter("applyId", "c1b4a48ec90c431792541a1f5cc72c58")
//				.parameter("procInstId", "295ec6a1-da52-11e7-84cc-408d5c38dfc1")
//				.singleResult();
//		System.out.println();
//		System.out.println("**************" + historicTaskInstance.getAssignee());
//		System.out.println(bpmnMapper.findHiTaskAssignee("c1b4a48ec90c431792541a1f5cc72c58", "295ec6a1-da52-11e7-84cc-408d5c38dfc1","makingEvent"));
		
		
		StringBuilder sql = new StringBuilder("SELECT CREATE_EMPLOYEE ASSIGNEE_ FROM HS_APPLY WHERE APPLY_ID=#{applyId}");
		HistoricTaskInstance historicTaskInstance = historyService.createNativeHistoricTaskInstanceQuery().sql(sql.toString()).parameter("applyId", "ebbcec166c9e4b739ad5952be1688225").singleResult();

		if (historicTaskInstance != null) {
			String assignee = historicTaskInstance.getAssignee();
			if (!StringUtils.isBlank(assignee)) {
				System.out.println("**************" + assignee);
			}
		}
		
	}

}
