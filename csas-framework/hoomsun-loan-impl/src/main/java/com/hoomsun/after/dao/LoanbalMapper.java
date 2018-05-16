package com.hoomsun.after.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hoomsun.after.api.model.table.Loanbal;

public interface LoanbalMapper {
	int deleteByPrimaryKey(String id);

	int insert(Loanbal record);

	int insertSelective(Loanbal record);

	Loanbal selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Loanbal record);

	int updateByPrimaryKey(Loanbal record);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月9日 <br>
	 * 描述：查询loanbal表根据loanId
	 * 
	 * @param loanId
	 * @return
	 */
	Loanbal selectByLoanId(String loanId);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月12日 <br>
	 * 描述： 修改正常月还减免为空
	 * 
	 * @param loanId
	 */
	void updateNomalSubToNull(String loanId);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月12日 <br>
	 * 描述： 修改逾期月还减免金额为null
	 * 
	 * @param loanId
	 */
	void updateOverduelSubToNull(String loanId);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月12日 <br>
	 * 描述： 修改逾期月还减免相关字段
	 * 
	 * @param loanId
	 */
	void updateOverduelSub(Map<String, Object> overdue);

	/**
	 * 
	 * 作者：金世强<br>
	 * 创建时间：2018年3月12日 <br>
	 * 描述： 修改提前还款减免金额为null
	 * 
	 * @param loanId
	 */
	void updateAdvanceSubToNull(String loanId);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月12日 <br>
	 * 描述： 修改loanbal结清标识为已结清
	 * 
	 * @param loanId
	 */
	void updateSettleFlagByLoanId(String loanId);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月12日 <br>
	 * 描述： 修改loanbal的应还期次以及应还款日期
	 * 
	 * @param loanbalparams
	 */
	void updateNextPaymentByLoanId(Map<String, Object> loanbalparams);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月12日 <br>
	 * 描述： 修改M段 逾期标识
	 * 
	 * @param mSessionParams
	 */
	void updateMSectionDelayFlagByLoanId(Map<String, Object> mSessionParams);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月12日 <br>
	 * 描述： 修改此单子在贷后还是客服
	 * 
	 * @param customerOrLoanParams
	 */
	void updatecustomerOrLoanByloanId(Map<String, Object> customerOrLoanParams);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月12日 <br>
	 * 描述： 修改贷后管理客服为空
	 * 
	 * @param loanId
	 */
	void updateLoanManagerTonull(String loanId);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 根据custId修改 客户联系手机号
	 * 
	 * @param balparams
	 */
	void updateTelByCustId(Map<String, Object> balparams);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月24日 <br>
	 * 描述： 根据loanId 修改挂起标识(逾期跑批挂起)
	 * 
	 * @param balparams
	 */
	void updateHangUpByLoanId(Map<String, Object> balparams);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月10日 <br>
	 * 描述：根据loanId 修改挂起标识(划扣挂起)
	 * 
	 * @param balparams
	 */
	void updateHangUpDeductByLoanId(Map<String, Object> balparams);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月27日 <br>
	 * 描述：根据LoanId修改电催留案状态
	 * 
	 * @param overdueLeave
	 */
	void updateOverdueLeaveByLoanId(Map<String, Object> overdueLeave);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月27日 <br>
	 * 描述：根据LoanId修改外访留案状态
	 * 
	 * @param outBoundLeave
	 */
	void updateOutBoundByLoanId(Map<String, Object> outBoundLeave);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月9日 <br>
	 * 描述：根据客户ID查询looanbal 数据
	 * 
	 * @param id
	 * @return
	 */
	List<Loanbal> selectByCustId(String custId);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月9日 <br>
	 * 描述：根据客户applyID查询looanbal 数据
	 * 
	 * @param applyId
	 * @return
	 */
	Loanbal selectByApplyId(String applyId);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月9日 <br>
	 * 描述：获取 loanIds 所有还款日小于此日期的数据
	 * 
	 * @param loanbalParams
	 * @return
	 */
	List<Loanbal> selectLessDate(Map<String, Object> loanbalParams);

	void updateProjectId(Map<String, Object> loanbalParams);

}