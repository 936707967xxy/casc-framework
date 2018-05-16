/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import java.util.List;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.csas.sales.api.model.QcCheck;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.vo.RollBackInfoVo;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年12月4日 <br>
 * 描述：
 */
public interface QcCheckServerI {

	Pager<UserApply> findPagerData(Integer page, Integer rows, String custName, String idNumber, String loanId, String nodeStatus, String salesEmpName, String custMobile,String node, SessionRouter session);

	UserApply findById(String applyId);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述：签收校验
	 * 
	 * @param applyId
	 * @param empId
	 * @return
	 */
	Json checkClaim(String applyId, String empId);

	/**
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 签收
	 * 
	 * @param applyId
	 * @param session
	 * @return
	 */
	Json claimTask(String applyId, String empId);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 通过
	 * 
	 * @param applyId
	 * @param empId
	 * @param empName
	 * @param remarks
	 * @return
	 */
	Json completeTask(String applyId, String empId, String empName, String remarks);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 撤销
	 * 
	 * @param applyId
	 * @param empId
	 * @return
	 */
	Json withdraw(String applyId, String empId);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 退回
	 * 
	 * @param applyId
	 * @param empId
	 * @param empName
	 * @param remarks
	 * @return
	 */
	Json rollback(String applyId, String empId, String empName, String remarks);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 拒贷
	 * 
	 * @param applyId
	 * @param empId
	 * @param empName
	 * @param remarks
	 * @return
	 */
	Json reject(String applyId, String empId, String empName, String remarks);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月19日 <br>
	 * 描述： 客户放弃
	 * 
	 * @param applyId
	 * @param empId
	 * @param empName
	 * @param remarks
	 * @return
	 */
	Json waiver(String applyId, String empId, String empName, String remarks);

	QcCheck findByApplyId(String applyId);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月25日 <br>
	 * 描述： 获取被初审退回的原因
	 * @param processId
	 * @return
	 */
	List<RollBackInfoVo> findPreRollBackHis(String applyId);
}
