/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.settle.server.inter;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.csas.settle.model.LoanRecord;
import com.hoomsun.csas.settle.model.vo.LoanVo;

/**
 * 作者：liming <br>
 * 创建时间：2017年12月16日 <br>
 * 描述：放款业务层
 */
public interface LoanServerI {
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述： 分页查询
	 * @param page
	 * @param rows
	 * @param custName
	 * @param custMobile
	 * @param loanId
	 * @param empId
	 * @param deptId
	 * @param nodeStatus 
	 * @return
	 */
	Pager<LoanVo> findAllData(Integer page, Integer rows, String custName, String custMobile, String conCode,String idNumber,String empId,String deptId, String nodeStatus);
	//通过
	Json completeTask(LoanRecord loanRecord, SessionRouter session);
	//退回
	Json rollback(LoanRecord loanRecord, SessionRouter session);
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月19日 <br>
	 * 描述： 根据申请id查询放款信息
	 * @param applyId
	 * @return
	 */
	LoanVo selectByApplyId(String applyId);
	//签收
	Json claimTask(String applyId, String empId);
	//验证是否签收
	Json checkClaim(String applyId, String empId);
	
	LoanRecord findByConId(String conId);
	
	LoanRecord findApplyIds(String applyId);
	
	int insert(LoanRecord record);

	int insertSelective(LoanRecord record);


	int updateByPrimaryKeySelective(LoanRecord record);

	int updateByPrimaryKey(LoanRecord record);
	
	
}
