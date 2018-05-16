/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.inter;

import java.util.List;

import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.audit.model.PhoneVerify;
import com.hoomsun.csas.audit.model.PreAudit;
import com.hoomsun.csas.audit.model.vo.UserApplyVO;
import com.hoomsun.csas.sales.api.model.Annex;
import com.hoomsun.csas.sales.api.model.vo.CreditVo;
import com.hoomsun.csas.sales.api.model.vo.FinancialVo;
import com.hoomsun.csas.sales.api.model.vo.OtherVo;
import com.hoomsun.csas.sales.api.model.vo.RollBackInfoVo;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年11月29日 <br>
 * 描述：初审审核业务接口
 */
public interface PreAuditServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述：
	 * 
	 * @param page
	 * @param rows
	 * @param custName
	 * @param idNumber
	 * @return
	 */
	Pager<UserApplyVO> findPager(Integer page, Integer rows, String custName, String idNumber, String loanId, String emp, String nodeStatus, String node, String deptId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 签收任务
	 * 
	 * @param applyId
	 * @param emp
	 * @return
	 */
	Json claimTask(String applyId, String emp);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 处理任务
	 * 
	 * @param applyId
	 * @param emp
	 * @return
	 */
	Json completeTask(String applyId, String emp);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 退回任务
	 * 
	 * @param applyId
	 * @param emp
	 * @return
	 */
	Json rollbackTask(String applyId, String emp);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 撤回任务
	 * 
	 * @param applyId
	 * @param emp
	 * @return
	 */
	Json withdrawTask(String applyId, String emp);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 任务转办
	 * 
	 * @param loginEmp
	 *            当前操作人
	 * @param toEmpId
	 *            转移给谁
	 * @param applyId
	 *            转移的业务ID
	 * @return
	 */
	Json claimTask(String loginEmp, String toEmpId, String applyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 保存审核的数据信息
	 * 
	 * @param audit
	 * @param loginEmp
	 * @return
	 */
	Json savePreAudit(PreAudit audit, String loginEmp);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 保存信用审核数据
	 * 
	 * @param creditVo
	 * @return
	 */
	Json saveCredit(CreditVo creditVo, String loginEmp);

	Json savePbccre(CreditVo creditVo, String loginEmp);

	Json saveCis(CreditVo creditVo, String loginEmp);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 保存财务数据信息
	 * 
	 * @param financialVo
	 * @return
	 */
	Json saveFinancial(FinancialVo financialVo, String applyId, String loginEmp);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 保存电话核查数据
	 * 
	 * @param telAudit
	 * @return
	 */
	Json saveTelAudit(PhoneVerify verify, String applyId, String loginEmp);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 保存其他的数据信息
	 * 
	 * @param otherVo
	 * @return
	 */
	Json saveOthor(OtherVo otherVo, String applyId, String loginEmp);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 验证签收
	 * 
	 * @param applyId
	 * @param empId
	 * @return
	 */
	Json checkClaim(String applyId, String empId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月13日 <br>
	 * 描述： 获取审核历史
	 * 
	 * @param applyId
	 */
	List<HistoricTaskInstance> findAuditHistory(String applyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述： 根据申请ID获取获取初审审核信息数据
	 * 
	 * @param applyId
	 * @return
	 */
	PreAudit finByApplyId(String applyId);

	PreAudit selectByEcxxess(String applyId);

	Json upload(MultipartFile file, String applyId, String loginEmp, String loginName, String context);

	Json multiUpload(MultipartFile[] files, String applyId, String loginEmp, String loginName, String context);

	Json deleteAnnex(String id);

	List<Annex> findByapplyIdAndAnnexType(String applyId, Integer anxType);

	List<RollBackInfoVo> findPreRollBackHis(String applyId);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月24日 <br>
	 * 描述： 保存草稿
	 * 
	 * @param audit
	 * @param empId
	 * @return
	 */
	Json saveAuditInfo(PreAudit audit, String empId);

}
