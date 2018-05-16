/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.inter;

import java.util.List;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.audit.model.ContractCheck;
import com.hoomsun.csas.audit.model.vo.UserApplyConVO;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月12日 <br>
 * 描述：合同审核
 */
public interface ContractAuditServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 
	 * @param page
	 * @param rows
	 * @param custName
	 * @param idNumber
	 * @return
	 */
	Pager<UserApplyConVO> findPager(Integer page,Integer rows,String custName,String idNumber,String loanId,String emp,String nodeStatus,String node, String deptId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 签收任务
	 * @param applyId
	 * @param emp
	 * @return
	 */
	Json claimTask(String applyId,String emp);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 处理任务
	 * @param applyId
	 * @param emp
	 * @return
	 */
	Json completeTask(String applyId,String emp);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 退回任务
	 * @param applyId
	 * @param emp
	 * @return
	 */
	Json rollbackTask(String applyId,String emp);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月4日 <br>
	 * 描述： 撤回任务
	 * @param applyId
	 * @param emp
	 * @return
	 */
	Json withdrawTask(String applyId,String emp);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 任务转办
	 * @param loginEmp 当前操作人
	 * @param toEmpId 转移给谁
	 * @param applyId 转移的业务ID
	 * @return
	 */
	Json claimTask(String loginEmp,String toEmpId,String applyId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 保存审核的数据信息
	 * @param audit
	 * @param loginEmp
	 * @return
	 */
	Json saveAudit(ContractCheck audit,String loginEmp);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 验证任务是否已经签收
	 * @param applyId
	 * @param empId
	 * @return
	 */
	Json checkClaim(String applyId, String empId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月24日 <br>
	 * 描述： 根据申请ID获取最新的审核数据
	 * @param applyId
	 * @return
	 */
	ContractCheck findByApplyId(String applyId);
	
	List<ContractCheck> findRollBack(String applyId);
}
