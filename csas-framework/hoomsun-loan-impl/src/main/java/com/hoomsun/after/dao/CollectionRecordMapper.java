/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.dao;

import java.util.List;

import com.hoomsun.after.api.model.param.CollectionRecordReq;
import com.hoomsun.after.api.model.param.NomalCustomerReq;
import com.hoomsun.after.api.model.table.Review;
import com.hoomsun.after.api.model.vo.CollectingReceivingCallResult;
import com.hoomsun.after.api.model.vo.CollectionRecordResult;
import com.hoomsun.after.api.model.vo.CustomerCollectionRemindingResult;
import com.hoomsun.after.api.model.vo.NomalCustomerResult;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月13日 <br>
 * 描述：催收记录
 */
public interface CollectionRecordMapper {

	List<CollectionRecordResult>queryCollectionRecord(CollectionRecordReq req)throws Exception;
	
	Integer countCollectionRecord(CollectionRecordReq req)throws Exception;
	
	NomalCustomerResult queryOverdueRecordDetails(NomalCustomerReq req)throws Exception;
	/**
	 * 查询客户案件信息
	 */
    CustomerCollectionRemindingResult queryCustomerCastInfo(CollectionRecordReq req)throws Exception;
    /**
	 * 逾期合计信息
	 */
    List<CollectionRecordResult> queryCustomerOverdueInfo(CollectionRecordReq req)throws Exception;
	/**
	 * 催单历史
	 */
	List<CollectingReceivingCallResult> queryCustomerCallHistory(CollectionRecordReq req)throws Exception;
	/**
	 * 通话详单
	 */
	void queryCustomerConversation(CollectionRecordReq req)throws Exception;
	/**
	 * 添加催收记录
	 */
	Integer addCustomerCollectionInfo(CollectingReceivingCallResult req)throws Exception;
	/**
	 * 
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月24日 <br>
	 * 描述： 添加点评
	 * @param req
	 * @return
	 */
	Integer addCommentCollectionRecordInfo(Review req);
	/**
	 * 点评之后标红
	 * 作者：Administrator <br>
	 * 创建时间：2018年4月25日 <br>
	 * 描述： 
	 * @param req
	 * @return
	 */
	Integer updateOverdueRocordFlag(CollectionRecordReq req);
}
