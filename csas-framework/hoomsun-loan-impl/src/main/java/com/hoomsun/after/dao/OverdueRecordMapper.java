package com.hoomsun.after.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hoomsun.after.api.model.table.OverdueRecord;

public interface OverdueRecordMapper {
	int deleteByPrimaryKey(String id);

	int insert(OverdueRecord record);

	int insertSelective(OverdueRecord record);

	OverdueRecord selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(OverdueRecord record);

	int updateByPrimaryKey(OverdueRecord record);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月13日 <br>
	 * 描述： 获取当前应还的最小逾期期次的逾期数据
	 * 
	 * @param loanId
	 * @return
	 */
	OverdueRecord selectMinByLoanId(String loanId);

	void updateSettleByLoanIdAndStream(Map<String, Object> overParams);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年3月12日 <br>
	 * 描述： 根据LoanId查询逾期数据 未結清
	 * 
	 * @param loanId
	 * @return
	 */
	List<OverdueRecord> selectByLoanId(String loanId);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月3日 <br>
	 * 描述： 根据laonId和期次查询客户逾期信息
	 * 
	 * @param overParams
	 * @return
	 */
	OverdueRecord selectByLoanIdAndStream(Map<String, Object> overParams);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 查询逾期天数为0的最大一期逾期
	 * 
	 * @param loanId
	 * @return
	 */
	OverdueRecord selectMaxDaysZeroByLoanId(String loanId);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 根据期次删除此其次以及大于此其次的逾期数据
	 * 
	 * @param rollbackoverdueRecordParam
	 */
	void deleteRollbackByLoanIdAndStream(Map<String, Object> rollbackoverdueRecordParam);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月23日 <br>
	 * 描述： 根据LoanID 查询逾期总金额
	 * 
	 * @param loanId
	 * @return
	 */
	Map<String, BigDecimal> selSumByLoanID(String loanId);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月9日 <br>
	 * 描述： 根据LoanIDs 查询逾期数据
	 * 
	 * @param loanIds
	 * @return
	 */
	List<OverdueRecord> selectByLoanIds(List<String> loanIds);
}