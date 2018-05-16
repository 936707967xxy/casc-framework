/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.inter;

import java.util.List;

import org.activiti.engine.history.HistoricTaskInstance;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.audit.model.ReviewAudit;
import com.hoomsun.csas.audit.model.vo.UserApplyVO;

/**
 * 作者：liushuai <br>
 * 创建时间：2018年1月16日 <br>
 * 描述：复议审核接口
 */
public interface ReviewAuditServerI {

	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月16日 <br>
	 * 描述： 获取复议列表
	 * @param page
	 * @param rows
	 * @param custName
	 * @param idNumber
	 * @param loanId
	 * @param empId
	 * @param nodeStatus
	 * @param node
	 * @param deptId
	 * @return
	 */
	Pager<UserApplyVO> findPager(Integer page,Integer rows,String custName,String idNumber,String loanId,String empId,String nodeStatus,String node, String deptId);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月16日 <br>
	 * 描述： 复议审核处理
	 * @param reviewAudit
	 * @return
	 */
	Json completeTask(ReviewAudit reviewAudit);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月16日 <br>
	 * 描述： 审核历史节点
	 * @param applyId
	 * @return
	 */
	List<HistoricTaskInstance> findAuditHistory(String applyId);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月16日 <br>
	 * 描述： 获取复议审核详情
	 * @param applyId
	 * @return
	 */
	ReviewAudit findByApplyId(String applyId);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月16日 <br>
	 * 描述： 签收验证
	 * @param applyId
	 * @param empId
	 * @return
	 */
	Json checkClaim(String applyId, String empId);

	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月16日 <br>
	 * 描述： 签收
	 * @param applyId
	 * @param empId
	 * @return
	 */
	Json claimTask(String applyId, String empId);
	
}
