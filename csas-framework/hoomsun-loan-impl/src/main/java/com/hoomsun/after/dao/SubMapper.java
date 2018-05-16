package com.hoomsun.after.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.after.api.model.table.Sub;

public interface SubMapper {
	int deleteByPrimaryKey(String id);

	int insert(Sub record);

	int insertSelective(Sub record);

	Sub selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Sub record);

	int updateByPrimaryKey(Sub record);

	/**
	 * 
	 * 作者：JINSHIQIANG <br>
	 * 创建时间：2018年4月2日 <br>
	 * 描述： 根据LoanId和 期次，查询逾期减免明细中已经审批通过切可用（0）的数据
	 * 
	 * @param subParmas
	 * @return
	 */
	Sub selectByLoanIdAndStream(Map<String, Object> subParmas);

	/**
	 * 
	 * 作者：JINSHIQIANG <br>
	 * 创建时间：2018年4月2日 <br>
	 * 描述： 根据LoanId和 期次，查询逾期减免明细中审批中的数据（2）
	 * 
	 * @param subParmas
	 * @return
	 */
	Sub selectByLoanIdAndStream2(Map<String, Object> subParmas);

	/**
	 * 
	 * 作者：JINSHIQIANG <br>
	 * 创建时间：2018年4月2日 <br>
	 * 描述： 查询减免审批通过的最小一期逾期减免申请数据
	 * 
	 * @param subParmas
	 * @return
	 */
	Sub selectByLoanIdMin(String loanId);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月24日 <br>
	 * 描述：加载减免记录
	 * 
	 * @param applyId
	 * @return
	 */
	List<Sub> selectByApplyId(String applyId);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月24日 <br>
	 * 描述：加载减免记录
	 * 
	 * @param applyId
	 * @return
	 */
	List<Sub> selectByLoanId(String loanId);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月3日 <br>
	 * 描述：根据loanId subStream修改减免申请状态subStatus
	 * 
	 * @param subparam
	 */
	void updateSubStatusByLoanIdAndSubStream(Map<String, Object> subparam);

}