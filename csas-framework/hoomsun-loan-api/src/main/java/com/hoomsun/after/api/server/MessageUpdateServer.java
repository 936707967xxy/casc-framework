/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import java.util.Date;
import java.util.List;

import com.hoomsun.after.api.model.param.ApplyExamineParam;
import com.hoomsun.after.api.model.param.MessageUpdateParam;
import com.hoomsun.after.api.model.table.ApplyDetail;
import com.hoomsun.after.api.model.table.ApplyFo;
import com.hoomsun.after.api.model.table.ChangeInfo;
import com.hoomsun.after.api.model.vo.BankVo;
import com.hoomsun.common.model.Json;

/**
 * 作者：金世强 <br>
 * 创建时间：2018年4月18日 <br>
 * 描述：信息变更Server
 */
public interface MessageUpdateServer {
	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 加载审批流程
	 * 
	 * @param applyId
	 * @return
	 */
	List<ApplyDetail> getApplyDetail(String applyId);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 加载客户原信息
	 * 
	 * @param loanId
	 * @return
	 */
	BankVo getCustMessageMessage(String loanId);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 加载客户信息变更提交信息
	 * 
	 * @param applyId
	 * @return
	 */
	ChangeInfo getChangeInfo(String applyId);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 信息变更申请
	 * 
	 * @param messageUpdateParam
	 */
	Json saveMessageUpdate(MessageUpdateParam messageUpdateParam, String applicationCastId, String applicationCastName);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 加载信息变更审批列表
	 * 
	 * @param page
	 * @param pageSize
	 * @param status
	 * @return
	 */
	/**
	 * 信息变更，根据申请单状态
	 */
	List<ApplyFo> getExamineList(Integer page, Integer pageSize, Integer status, String castName,String cardNo,String conditionCustID,String conditionCustName, Date createTime);

	int getExamineListSize(Integer status, String castName,String cardNo,String conditionCustID,String conditionCustName, Date createTime);

	/**
	 * 信息变更，根据申请单状态及副理负责
	 */
	List<ApplyFo> getExamineList2(Integer page, Integer pageSize, Integer status, String applicationCastId, String applicationCastName, String empId, String castName,String cardNo,String conditionCustID,String conditionCustName,Date createTime);

	int getExamineListSize2(Integer status, String applicationCastId, String applicationCastName, String empId, String castName,String cardNo,String conditionCustID,String conditionCustName, Date createTime);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述： 信息变更一级审批
	 * 
	 * @param messageUpdateApplyParam
	 */
	void saveMessageUpdate1(ApplyExamineParam messageUpdateApplyParam, String applicationCastId, String applicationCastName);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述：信息变更二级审批
	 * 
	 * @param messageUpdateApplyParam
	 */
	void saveMessageUpdate2(ApplyExamineParam messageUpdateApplyParam, String applicationCastId, String applicationCastName);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述：加载信息变更审批申请记录
	 */
	List<ApplyFo> getMessageUpdateHistory(Integer page, Integer pageSize, String deptId, String applicationCastId, String castName,String cardNo ,String applyStatus, String applyType, Date createTime);

	/**
	 * 
	 * 作者：金世强 <br>
	 * 创建时间：2018年4月18日 <br>
	 * 描述：加载信息变更审批申请记录数
	 */
	int getMessageUpdateHistoryCount(String deptId, String applicationCastId, String castName, String cardNo ,String applyStatus, String applyType, Date createTime);

}
