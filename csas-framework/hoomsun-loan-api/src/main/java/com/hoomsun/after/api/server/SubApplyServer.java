package com.hoomsun.after.api.server;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hoomsun.after.api.model.param.ApplyExamineParam;
import com.hoomsun.after.api.model.param.SubApplyParam;
import com.hoomsun.after.api.model.table.ApplyFo;
import com.hoomsun.after.api.model.table.OverdueRecord;
import com.hoomsun.after.api.model.table.Sub;
import com.hoomsun.common.model.Json;

public interface SubApplyServer {
	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月23日 <br>
	 * 描述： 获取此客户最小逾期期次的逾期数据
	 * 
	 * @param loanId
	 * @return
	 */
	OverdueRecord getMinOverdue(String loanId);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月23日 <br>
	 * 描述： 获取客户的所有逾期数据
	 * 
	 * @param loanId
	 * @return
	 */
	List<OverdueRecord> getAllOverdue(String loanId);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月23日 <br>
	 * 描述： 单月减免计算金额
	 * 
	 * @param penalty
	 * @param interest
	 * @param receiveTotalMoney
	 * @param custNenghuan
	 * @return
	 */
	Map<String, Object> getSingelReductionApplyMoney(String loanId, Date subDate, BigDecimal custNenghuan);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月23日 <br>
	 * 描述：多月减免违法金计算
	 * 
	 * @param loanId
	 * @param repaymentMoney
	 * @return
	 */
	Json getAllReductionApplyMoney(String loanId, Date subDate, BigDecimal repaymentMoney);

	List<ApplyFo> getExamineList(Integer page, Integer pageSize, Integer status,String castName,String cardNo,String applyType,String loanId,String conNo,String conditionCustID,String conditionCustName,Date createTime);


	int getExamineListSize(Integer status,String castName,String cardNo,String applyType,String loanId,String conNo,String conditionCustID,String conditionCustName,Date createTime);


	List<ApplyFo> getExamineList2(Integer page, Integer pageSize, Integer status, String applicationCastId, String applicationCastName, String empId,String castName,String cardNo,String applyType,String loanId,String conNo,String conditionCustID,String conditionCustName,Date createTime);

	int getExamineListSize2(Integer status, String applicationCastId, String applicationCastName, String empId,String castName,String cardNo,String applyType,String loanId,String conNo,String conditionCustID,String conditionCustName,Date createTime);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月24日 <br>
	 * 描述： 减免申请
	 * 
	 * @param subApplyParam
	 * @param applicationCastId
	 * @param applicationCastName
	 */
	Json saveSubapply(SubApplyParam subApplyParam, String applicationCastId, String applicationCastName);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月24日 <br>
	 * 描述： 加载客户逾期减免提交信息
	 * 
	 * @param applyId
	 * @return
	 */
	List<Sub> getSub(String applyId);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月24日 <br>
	 * 描述：逾期减免一审
	 * 
	 * @param subExamineParam
	 * @param applicationCastId
	 * @param applicationCastName
	 */
	void saveSubapply1(ApplyExamineParam subExamineParam, String applicationCastId, String applicationCastName);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月24日 <br>
	 * 描述：逾期减免二审
	 * 
	 * @param subExamineParam
	 * @param applicationCastId
	 * @param applicationCastName
	 */
	void saveSubapply2(ApplyExamineParam subExamineParam, String applicationCastId, String applicationCastName);
	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月24日 <br>
	 * 描述： 加载减免审批申请记录
	 * @param page
	 * @param pageSize
	 * @param applicationCastId
	 * @return
	 */
	List<ApplyFo> getSubapplyHistory(Integer page, Integer pageSize,String deptId, String applicationCastId,String castName,String cardNo,String applyType,String applyStatus,String loanId,String conNo,Date createTime);
	
	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月24日 <br>
	 * 描述：加载减免审批申请记录数
	 * @param applicationCastId
	 * @return
	 */
	int getSubapplyHistoryCount(String deptId,String applicationCastId,String castName,String cardNo,String applyType,String applyStatus,String loanId,String conNo,Date createTime);
	
	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月26日 <br>
	 * 描述： 否決减免申请所有数据
	 * @param applyId
	 * @param applicationCastId
	 * @param applicationCastName
	 */
	Json saveVoteDown(ApplyExamineParam subExamineParam, String applicationCastId, String applicationCastName);

}
