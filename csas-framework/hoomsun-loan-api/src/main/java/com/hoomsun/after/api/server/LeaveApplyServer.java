/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import java.util.List;

import com.hoomsun.after.api.model.param.ApplyExamineParam;
import com.hoomsun.after.api.model.param.LeaveApplyParams;
import com.hoomsun.after.api.model.param.MessageUpdateParam;
import com.hoomsun.after.api.model.table.ApplyFo;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年4月27日 <br>
 * 描述：留案申请
 */
public interface LeaveApplyServer {

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月27日 <br>
	 * 描述： 留案申请，根据申请单状态
	 * 
	 * @param page
	 * @param pageSize
	 * @param status
	 * @return
	 */
	List<ApplyFo> getExamineList(Integer page, Integer pageSize, Integer status);

	List<ApplyFo> getExamineList2(Integer page, Integer pageSize, Integer status, String applicationCastId, String applicationCastName, String empId);

	int getExamineListSize(Integer status);

	int getExamineListSize2(Integer status, String applicationCastId, String applicationCastName, String empId);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月27日 <br>
	 * 描述：留案申请历史记录
	 * 
	 * @param page
	 * @param pageSize
	 * @param deptId
	 * @param applicationCastId
	 * @return
	 */
	List<ApplyFo> getLeaveapplyHistory(Integer page, Integer pageSize, String deptId, String applicationCastId);

	int getLeaveapplyHistoryCount(String deptId, String applicationCastId);
	
	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月27日 <br>
	 * 描述： 留案申请
	 * @param messageUpdateParam
	 * @param applicationCastId
	 * @param applicationCastName
	 * @return
	 */
	String saveLeaveApply(LeaveApplyParams leaveApplyParams, String applicationCastId, String applicationCastName);
	
	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月27日 <br>
	 * 描述： 留案一级审批
	 * @param messageUpdateApplyParam
	 * @param applicationCastId
	 * @param applicationCastName
	 */
	void saveLeaveApply1(ApplyExamineParam leaveApplyParam, String applicationCastId, String applicationCastName);
	
	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月27日 <br>
	 * 描述： 留案二级审批
	 * @param messageUpdateApplyParam
	 * @param applicationCastId
	 * @param applicationCastName
	 */
	void saveLeaveApply2(ApplyExamineParam leaveApplyParam, String applicationCastId, String applicationCastName);

}
