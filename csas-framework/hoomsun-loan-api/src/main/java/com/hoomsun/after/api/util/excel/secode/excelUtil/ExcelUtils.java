/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.util.excel.secode.excelUtil;

import java.util.List;

import com.hoomsun.after.api.model.param.CollectionRecordReq;
import com.hoomsun.after.api.model.param.CustomerDeductReq;
import com.hoomsun.after.api.model.param.CustomerTaskAllocationReq;
import com.hoomsun.after.api.model.vo.CollectingReceivingCallResult;
import com.hoomsun.after.api.model.vo.CollectionRecordResult;
import com.hoomsun.after.api.model.vo.CunGongPublicSaveResult;
import com.hoomsun.after.api.model.vo.CustomerDeductResult;
import com.hoomsun.after.api.model.vo.CustomerTaskAllocationResult;
import com.hoomsun.after.api.model.vo.SettleCustomerResult;
import com.hoomsun.after.api.model.vo.VistTaskResult;
import com.hoomsun.after.api.util.autoCode;
import com.hoomsun.after.api.util.enums.CollectionRecordEnum;
import com.hoomsun.after.api.util.enums.CustomerDeductEnum;
import com.hoomsun.after.api.util.enums.CustomerLoanBalEnum;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年4月4日 <br>
 * 描述：列表数据处理工具
 */
public class ExcelUtils {

	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月4日 <br>
	 * 描述： 客户划扣列表导出
	 * @param list
	 * @return
	 */
	public static List<CustomerDeductResult> CustomerDeductExcel(List<CustomerDeductResult>list){
		if(list!=null&&list.size()>0){
			for (CustomerDeductResult customerDeductResult : list) {
				/**
				 * 划扣状态
				 */
				if(CustomerDeductEnum.DEDUCT_STATUS_TRUE.getCode().equals(customerDeductResult.getDeductState())){
					customerDeductResult.setDeductState(CustomerDeductEnum.DEDUCT_STATUS_TRUE.getMess());
				}else if(CustomerDeductEnum.DEDUCT_STATUS_FALSE.getCode().equals(customerDeductResult.getDeductState())){
					customerDeductResult.setDeductState(CustomerDeductEnum.DEDUCT_STATUS_FALSE.getMess());
				}else if(CustomerDeductEnum.DEDUCT_STATUS_WAIT.getCode().equals(customerDeductResult.getDeductState())){
					customerDeductResult.setDeductState(CustomerDeductEnum.DEDUCT_STATUS_WAIT.getMess());
				}else{
					customerDeductResult.setDeductState("/");
				}
				
			}
		}
		return list;
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月4日 <br>
	 * 描述： 客户划扣列表请求参数处理
	 * @param req
	 * @return
	 */
	public static CustomerDeductReq setCustomerDeductVo(CustomerDeductReq req){
		/**
		 * 客户划扣渠道
		 */
		if(CustomerDeductEnum.DEDUCT_CHANNEL_XXBF.getCode().equals(req.getDeductChannel())){
			req.setDeductChannel(CustomerDeductEnum.DEDUCT_CHANNEL_XXBF.getMess());
		}else if(CustomerDeductEnum.DEDUCT_CHANNEL_XXFY.getCode().equals(req.getDeductChannel())){
			req.setDeductChannel(CustomerDeductEnum.DEDUCT_CHANNEL_XXFY.getMess());
		}else if(CustomerDeductEnum.DEDUCT_CHANNEL_XXCG.getCode().equals(req.getDeductChannel())){
			req.setDeductChannel(CustomerDeductEnum.DEDUCT_CHANNEL_XXCG.getMess());
		}else if(CustomerDeductEnum.DEDUCT_CHANNEL_XSBF.getCode().equals(req.getDeductChannel())){
			req.setDeductChannel(CustomerDeductEnum.DEDUCT_CHANNEL_XSBF.getMess());
		}else if(CustomerDeductEnum.DEDUCT_CHANNEL_XSFY.getCode().equals(req.getDeductChannel())){
			req.setDeductChannel(CustomerDeductEnum.DEDUCT_CHANNEL_XSFY.getMess());
		}else if(CustomerDeductEnum.DEDUCT_CHANNEL_XSCG.getCode().equals(req.getDeductChannel())){
			req.setDeductChannel(CustomerDeductEnum.DEDUCT_CHANNEL_XSCG.getMess());
		}else{
			req.setDeductChannel(null);
		}
		
		/**
		 * 划扣类型
		 */
		if(CustomerDeductEnum.DEDUCT_TYPE_ZC.getCode().equals(req.getDeductType())){
			req.setDeductType(CustomerDeductEnum.DEDUCT_TYPE_ZC.getMess());
		}else if(CustomerDeductEnum.DEDUCT_TYPE_TQ.getCode().equals(req.getDeductType())){
			req.setDeductType(CustomerDeductEnum.DEDUCT_TYPE_TQ.getMess());
		}else if(CustomerDeductEnum.DEDUCT_TYPE_YQ.getCode().equals(req.getDeductType())){
			req.setDeductType(CustomerDeductEnum.DEDUCT_TYPE_YQ.getMess());
		}else if(CustomerDeductEnum.DEDUCT_TYPE_YE.getCode().equals(req.getDeductType())){
			req.setDeductType(CustomerDeductEnum.DEDUCT_TYPE_YE.getMess());
		}else{
			req.setDeductType(null);
		}
		return req;
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月4日 <br>
	 * 描述： 客户催收记录导出部分字段转义
	 * @param list
	 * @return
	 */
	public static List<CollectionRecordResult> CollectionRecordExcel(List<CollectionRecordResult>list,CollectionRecordReq req){
		if(list!=null&&list.size()>0){
			for (CollectionRecordResult collectionRecordResult : list) {
				/**
				 * 新放款客户
				 */
				if(autoCode.CUSTOMER_NEW_CREATE_VALUE.equals(collectionRecordResult.getPosType())){
					collectionRecordResult.setPosType(autoCode.CUSTOMER_NEW_CREATE);
				}
				/**
				 * 客户是否逾期
				 */
				if(CustomerLoanBalEnum.LOADBAL_DALAYFLAG_TRUE.getCode().equals(collectionRecordResult.getDalayFlag())){
					collectionRecordResult.setDalayFlag(CustomerLoanBalEnum.LOADBAL_DALAYFLAG_TRUE.getMess());
				}else if(CustomerLoanBalEnum.LOADBAL_DALAYFLAG_FALSE.getCode().equals(collectionRecordResult.getDalayFlag())){
					collectionRecordResult.setDalayFlag(CustomerLoanBalEnum.LOADBAL_DALAYFLAG_FALSE.getMess());
				}else{
					collectionRecordResult.setDalayFlag("/");
				}
				/**
				 * 客户是否结清
				 */
				if(CustomerLoanBalEnum.CUSTOMER_SETTLE_FLAG_NO.getCode().equals(collectionRecordResult.getSettleFlag())){
					collectionRecordResult.setSettleFlag(CustomerLoanBalEnum.CUSTOMER_SETTLE_FLAG_NO.getMess());
				}else if(CustomerLoanBalEnum.CUSTOMER_SETTLE_FLAG_YES.getCode().equals(collectionRecordResult.getSettleFlag())){
					collectionRecordResult.setSettleFlag(CustomerLoanBalEnum.CUSTOMER_SETTLE_FLAG_YES.getMess());
				}else{
					collectionRecordResult.setSettleFlag("/");
				}
				/**
				 * 客户是否挂起
				 */
				if(CustomerLoanBalEnum.CUSTOMER_HANG_UP_NO.getCode().equals(collectionRecordResult.getHangUp())){
					collectionRecordResult.setHangUp(CustomerLoanBalEnum.CUSTOMER_HANG_UP_NO.getMess());
				}else if(CustomerLoanBalEnum.CUSTOMER_HANG_UP_YES.getCode().equals(collectionRecordResult.getHangUp())){
					collectionRecordResult.setHangUp(CustomerLoanBalEnum.CUSTOMER_HANG_UP_YES.getMess());
				}else{
					collectionRecordResult.setHangUp("/");
				}
				
				if(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode().equals(req.getQueryType())){
					collectionRecordResult.setLoanManagerCastName(null);
				}else{
					collectionRecordResult.setManagerCast(null);
				}
			}
		}
		return list;
	}
	
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月8日 <br>
	 * 描述： 结清客户列表导出字段转义
	 * @param list
	 * @return
	 */
	public static List<SettleCustomerResult> SettleCustomerExcel(List<SettleCustomerResult> list){
		if(list!=null&&list.size()>0){
			for (SettleCustomerResult settleCustomerResult : list) {
				/**
				 * 客户还款状态
				 */
				if(CustomerLoanBalEnum.CUSTOMER_SETTLE_FLAG_YES.getCode().equals(settleCustomerResult.getRepayStatus())){
					settleCustomerResult.setRepayStatus(CustomerLoanBalEnum.CUSTOMER_SETTLE_FLAG_YES.getMess());
				}else if(CustomerLoanBalEnum.CUSTOMER_SETTLE_FLAG_NO.getCode().equals(settleCustomerResult.getRepayStatus())){
					settleCustomerResult.setRepayStatus(CustomerLoanBalEnum.CUSTOMER_SETTLE_FLAG_NO.getMess());
				}else{
					settleCustomerResult.setRepayStatus("/");
				}
			}
		}
		return list;
	}
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月19日 <br>
	 * 描述： 催收提醒---》催收历史记录
	 * @param callResult
	 * @return
	 */
	public static List<CollectingReceivingCallResult>customerCallHistory(List<CollectingReceivingCallResult> callResult){
		if(callResult!=null&&callResult.size()>0){
			for (CollectingReceivingCallResult callResults : callResult) {
				/**
				 * 客户关系
				 */
				if(CollectionRecordEnum.COLL_CUS_RELATIONSHIP_MS.getCode().equals(callResults.getRelationship())){
					callResults.setRelationship(CollectionRecordEnum.COLL_CUS_RELATIONSHIP_MS.getMess());
				}else if(CollectionRecordEnum.COLL_CUS_RELATIONSHIP_SS.getCode().equals(callResults.getRelationship())){
					callResults.setRelationship(CollectionRecordEnum.COLL_CUS_RELATIONSHIP_SS.getMess());
				}else if(CollectionRecordEnum.COLL_CUS_RELATIONSHIP_RE.getCode().equals(callResults.getRelationship())){
					callResults.setRelationship(CollectionRecordEnum.COLL_CUS_RELATIONSHIP_RE.getMess());
				}else if(CollectionRecordEnum.COLL_CUS_RELATIONSHIP_FD.getCode().equals(callResults.getRelationship())){
					callResults.setRelationship(CollectionRecordEnum.COLL_CUS_RELATIONSHIP_FD.getMess());
				}else if(CollectionRecordEnum.COLL_CUS_RELATIONSHIP_CE.getCode().equals(callResults.getRelationship())){
					callResults.setRelationship(CollectionRecordEnum.COLL_CUS_RELATIONSHIP_CE.getMess());
				}else if(CollectionRecordEnum.COLL_CUS_RELATIONSHIP_OS.getCode().equals(callResults.getRelationship())){
					callResults.setRelationship(CollectionRecordEnum.COLL_CUS_RELATIONSHIP_OS.getMess());
				}else{
					callResults.setRelationship("");
				}
				
				/**
				 * 接听状态
				 */
				if(CollectionRecordEnum.COLL_RECEIVING_STATE_HBA.getCode().equals(callResults.getReceivingState())){
					callResults.setReceivingState(CollectionRecordEnum.COLL_RECEIVING_STATE_HBA.getMess());
				}else if(CollectionRecordEnum.COLL_RECEIVING_STATE_NA.getCode().equals(callResults.getReceivingState())){
					callResults.setReceivingState(CollectionRecordEnum.COLL_RECEIVING_STATE_NA.getMess());
				}else if(CollectionRecordEnum.COLL_RECEIVING_STATE_SD.getCode().equals(callResults.getReceivingState())){
					callResults.setReceivingState(CollectionRecordEnum.COLL_RECEIVING_STATE_SD.getMess());
			    }else if(CollectionRecordEnum.COLL_RECEIVING_STATE_EP.getCode().equals(callResults.getReceivingState())){
			    	callResults.setReceivingState(CollectionRecordEnum.COLL_RECEIVING_STATE_EP.getMess());
			    }else if(CollectionRecordEnum.COLL_RECEIVING_STATE_SP.getCode().equals(callResults.getReceivingState())){
			    	callResults.setReceivingState(CollectionRecordEnum.COLL_RECEIVING_STATE_SP.getMess());
			    }else if(CollectionRecordEnum.COLL_RECEIVING_STATE_RJ.getCode().equals(callResults.getReceivingState())){
			    	callResults.setReceivingState(CollectionRecordEnum.COLL_RECEIVING_STATE_RJ.getMess());
			    }else if(CollectionRecordEnum.COLL_RECEIVING_STATE_CTF.getCode().equals(callResults.getReceivingState())){
			    	callResults.setReceivingState(CollectionRecordEnum.COLL_RECEIVING_STATE_CTF.getMess());
			    }else if(CollectionRecordEnum.COLL_RECEIVING_STATE_UTC.getCode().equals(callResults.getReceivingState())){
			    	callResults.setReceivingState(CollectionRecordEnum.COLL_RECEIVING_STATE_UTC.getMess());
			    }else{
			    	callResults.setReceivingState("");
			    }
				/**
				 * 客户提醒状态
				 */
				if(CollectionRecordEnum.COLL_REMINDING_STATE_TRUE.getCode().equals(callResults.getRemindingState())){
					callResults.setRemindingState(CollectionRecordEnum.COLL_REMINDING_STATE_TRUE.getMess());
				}else if(CollectionRecordEnum.COLL_REMINDING_STATE_FALSE.getCode().equals(callResults.getRemindingState())){
					callResults.setRemindingState(CollectionRecordEnum.COLL_REMINDING_STATE_FALSE.getMess());
				}else{
					callResults.setRemindingState("");
				}
			}
		}
		return callResult;
	}
	/**
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月20日 <br>
	 * 描述： 客户任务分配
	 * @param list
	 * @return
	 */
	public static List<CustomerTaskAllocationResult>customerTask( List<CustomerTaskAllocationResult>list,CustomerTaskAllocationReq req){
		if(list!=null&&list.size()>0){
			for (CustomerTaskAllocationResult TaskResult : list) {
				/**
				 * 剩余期次
				 */
				Integer surPeriod=(Integer.parseInt(TaskResult.getLoanPeriod())-Integer.parseInt(TaskResult.getCurrentPeriod()));
				TaskResult.setSurplusPeriod(String.valueOf(surPeriod));
				/**
				 * 是否结清
				 */
				if(CustomerLoanBalEnum.CUSTOMER_SETTLE_FLAG_YES.getCode().equals(TaskResult.getSettleFlag())){
					TaskResult.setSettleFlag(CustomerLoanBalEnum.CUSTOMER_SETTLE_FLAG_YES.getMess());
				}else if(CustomerLoanBalEnum.CUSTOMER_SETTLE_FLAG_NO.getCode().equals(TaskResult.getSettleFlag())){
					TaskResult.setSettleFlag(CustomerLoanBalEnum.CUSTOMER_SETTLE_FLAG_NO.getMess());
				}else{
					TaskResult.setSettleFlag("");
				}
				/**
				 * 是否挂起
				 */
				if(CustomerLoanBalEnum.CUSTOMER_HANG_UP_YES.getCode().equals(TaskResult.getHangUp())){
					TaskResult.setHangUp(CustomerLoanBalEnum.CUSTOMER_HANG_UP_YES.getMess());
				}else if(CustomerLoanBalEnum.CUSTOMER_HANG_UP_NO.getCode().equals(TaskResult.getHangUp())){
					TaskResult.setHangUp(CustomerLoanBalEnum.CUSTOMER_HANG_UP_NO.getMess());
				}else{
					TaskResult.setHangUp("");
				}
				/**
				 * 是否逾期
				 */
				if(CustomerLoanBalEnum.LOADBAL_DALAYFLAG_TRUE.getCode().equals(TaskResult.getDalayFlag())){
					TaskResult.setDalayFlag(CustomerLoanBalEnum.LOADBAL_DALAYFLAG_TRUE.getMess());
				}else if(CustomerLoanBalEnum.LOADBAL_DALAYFLAG_FALSE.getCode().equals(TaskResult.getDalayFlag())){
					TaskResult.setDalayFlag(CustomerLoanBalEnum.LOADBAL_DALAYFLAG_FALSE.getMess());
				}else{
					TaskResult.setDalayFlag("");
				}
				
				if(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode().equals(req.getQueryType())){
					TaskResult.setLoanManagerCastName(null);
				}else{
					TaskResult.setManagerCast(null);
				}
			}
		}
		return list;
	}
	
	public static List<VistTaskResult>excelVisitTaskList(List<VistTaskResult> list){
		if(list!=null&&list.size()>0){
			for (VistTaskResult vistTaskResult : list) {
				if(CustomerLoanBalEnum.LOADBAL_DALAYFLAG_TRUE.getCode().equals(vistTaskResult.getDalayFlag())){
					vistTaskResult.setDalayFlag(CustomerLoanBalEnum.LOADBAL_DALAYFLAG_TRUE.getMess());
				}else if(CustomerLoanBalEnum.LOADBAL_DALAYFLAG_FALSE.getCode().equals(vistTaskResult.getDalayFlag())){
					vistTaskResult.setDalayFlag(CustomerLoanBalEnum.LOADBAL_DALAYFLAG_FALSE.getMess());
				}else{
					vistTaskResult.setDalayFlag("/");
				}
				
				if(CustomerLoanBalEnum.OUTBOUND_STATUS_0.getCode().equals(vistTaskResult.getOutboundStatus())){
					vistTaskResult.setOutboundStatus(CustomerLoanBalEnum.OUTBOUND_STATUS_0.getMess());
				}else if(CustomerLoanBalEnum.OUTBOUND_STATUS_1.getCode().equals(vistTaskResult.getOutboundStatus())){
					vistTaskResult.setOutboundStatus(CustomerLoanBalEnum.OUTBOUND_STATUS_1.getMess());
				}else if(CustomerLoanBalEnum.OUTBOUND_STATUS_2.getCode().equals(vistTaskResult.getOutboundStatus())){
					vistTaskResult.setOutboundStatus(CustomerLoanBalEnum.OUTBOUND_STATUS_2.getMess());
				}else if(CustomerLoanBalEnum.OUTBOUND_STATUS_3.getCode().equals(vistTaskResult.getOutboundStatus())){
					vistTaskResult.setOutboundStatus(CustomerLoanBalEnum.OUTBOUND_STATUS_3.getMess());
				}else{
					vistTaskResult.setOutboundStatus("");
				}
			}
		}
		return list;
	}
	
	public static List<CunGongPublicSaveResult>excelCunGongPublicSave(List<CunGongPublicSaveResult> list){
		if(list!=null&&list.size()>0){
			for (CunGongPublicSaveResult GPSResult : list) {
				if(CustomerLoanBalEnum.VERIFICATION_PEOPLE_ID_YES.getCode().equals(GPSResult.getVerificationStatus())){
					GPSResult.setVerificationStatus(CustomerLoanBalEnum.VERIFICATION_PEOPLE_ID_YES.getMess());
				}else if(CustomerLoanBalEnum.VERIFICATION_PEOPLE_ID_NO.getCode().equals(GPSResult.getVerificationStatus())){
					GPSResult.setVerificationStatus(CustomerLoanBalEnum.VERIFICATION_PEOPLE_ID_NO.getMess());
				}else{
					GPSResult.setVerificationStatus("/");
				}
			}
		}
		return list;
	}
}
