/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import java.util.Date;
import java.util.List;

import com.hoomsun.after.api.model.param.ApplyExamineParam;
import com.hoomsun.after.api.model.param.OutBoundParam;
import com.hoomsun.after.api.model.table.ApplyFo;
import com.hoomsun.after.api.model.table.OutBound;
import com.hoomsun.common.model.Json;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年4月25日 <br>
 * 描述：
 */
public interface OutBoundServer {

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月25日 <br>
	 * 描述： 加载外访提交信息
	 * 
	 * @param applyId
	 * @return
	 */
	OutBound getOutbound(String applyId);

	List<ApplyFo> getExamineList(Integer page, Integer pageSize, Integer status,String castName,String cardNo,String conditionCustID,String conditionCustName,String loanId,String conNo,Date createTime);

	int getExamineListSize(Integer status,String castName,String cardNo,String conditionCustID,String conditionCustName,String loanId,String conNo,Date createTime);

	List<ApplyFo> getExamineList2(Integer page, Integer pageSize, String applicationCastId, String applicationCastName, String empId, Integer status);

	int getExamineListSize2(String applicationCastId, String applicationCastName, String empId, Integer status);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月25日 <br>
	 * 描述：外仿申請
	 * 
	 * @param outBound
	 * @param applicationCastId
	 * @param applicationCastName
	 */
	Json saveOutboundApply(OutBoundParam outBoundParma, String applicationCastId, String applicationCastName);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月25日 <br>
	 * 描述：外访一级审批
	 * 
	 * @param messageUpdateApplyParam
	 * @param applicationCastId
	 * @param applicationCastName
	 */
	void saveOutboundApply1(ApplyExamineParam messageUpdateApplyParam, String applicationCastId, String applicationCastName);

	List<ApplyFo> getOutboundHistory(Integer page, Integer pageSize, String deptId, String applicationCastId,String loanId,String conNo,String castName,String cardNo,Date createTime,String applyStatus,String applyType);

	int getOutboundHistoryCount(String deptId, String applicationCastId,String loanId,String conNo,String castName,String cardNo,Date createTime,String applyStatus,String applyType);

}
