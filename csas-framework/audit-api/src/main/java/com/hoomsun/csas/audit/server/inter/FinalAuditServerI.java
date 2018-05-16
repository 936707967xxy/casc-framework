/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.inter;

import java.util.List;

import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.audit.model.FinalAudit;
import com.hoomsun.csas.audit.model.vo.UserApplyVO;

/**
 * 作者：liusong <br>
 * 创建时间：2017年9月14日 <br>
 * 描述：
 */
public interface FinalAuditServerI {
	public static final Integer PASSING = 1;//通过
	public static final Integer ROLLBACK = 2;//退回
	public static final Integer REJECT = 0;//拒贷
	
	Json createFinalAudit(FinalAudit finalAudit);
	Json updateFinalAudit(FinalAudit finalAudit);
	Json deleteFinalAudit(String finalId);
	FinalAudit findById(String finalId);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月11日 <br>
	 * 描述： 通过申请ID获取终审信息
	 * @param applyId
	 * @return
	 */
	FinalAudit findByApplyId(String applyId);
	FinalAudit selectExcessAudit(String applyId);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月11日 <br>
	 * 描述： 获取终审要审批的单子
	 * @param page
	 * @param rows
	 * @param custName
	 * @param idNumber
	 * @param loanId
	 * @param empId
	 * @param nodeStatus
	 * @return
	 */
	Pager<UserApplyVO> findPager(Integer page,Integer rows,String custName,String idNumber,String loanId,String empId,String nodeStatus,String node, String deptId);
	/**
	 * 作者：liusong <br>
	 * 创建时间：2017年9月15日 <br>
	 * 描述： 上传审批附件
	 * @param applyId
	 * @param multipartFiles
	 * @param empId
	 * @return
	 */
	Json uploadAnnex(String applyId, MultipartFile[] multipartFiles, String empId);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述：签收校验 
	 * @param applyId
	 * @param empId
	 * @return
	 */
	Json checkClaim(String applyId, String empId);
	/**
	 * 作者：liusong <br>
	 * 创建时间：2017年9月18日 <br>
	 * 描述： 签收认领任务
	 * @param applyId
	 * @param empId
	 * @return
	 */
	Json claimTask(String applyId, String empId);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月19日 <br>
	 * 描述： 处理终审信息
	 * @param finalAudit
	 * @return
	 */
	Json completeTask(FinalAudit finalAudit);
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月19日 <br>
	 * 描述： 退回
	 * @param applyId
	 * @param empId
	 * @return
	 */
	Json rollback(String applyId, String empId);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月11日 <br>
	 * 描述： 撤销已审批的任务
	 * @param applyId
	 * @param empId
	 * @return
	 */
	Json withdraw(String applyId, String empId);
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月29日 <br>
	 * 描述： 获取退回信息
	 * @param applyId
	 * @return
	 */
	List<FinalAudit> findRollBackByApplyId(String applyId);
	
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月29日 <br>
	 * 描述： 审批历史
	 * @param applyId
	 * @return
	 */
	List<HistoricTaskInstance> findAuditHistory(String applyId);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月24日 <br>
	 * 描述： 存储终审信息（草稿）
	 * @param finalAudit
	 * @return
	 */
	Json saveAuditInfo(FinalAudit finalAudit);
	
}
