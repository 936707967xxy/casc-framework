/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import java.util.List;

import com.hoomsun.after.api.model.param.CollectionRecordReq;
import com.hoomsun.after.api.model.table.Review;
import com.hoomsun.after.api.model.vo.CollectingReceivingCallResult;
import com.hoomsun.after.api.model.vo.CollectionRecordResult;
import com.hoomsun.after.api.model.vo.CustomerCollectionRemindingResult;
import com.hoomsun.common.model.Json;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月13日 <br>
 * 描述：催收任务
 */
public interface CollectionRecordServer {

	List<CollectionRecordResult> queryCollectionRecord(CollectionRecordReq req);
	
	Integer countCollectionRecord(CollectionRecordReq req);
	
	void downloadCollectionRecord(CollectionRecordReq req);
	
	CustomerCollectionRemindingResult queryCustomerCastInfo(CollectionRecordReq req);
	
	Integer addCustomerCollectionInfo(CollectingReceivingCallResult req);
	
		Json addCommentCollectionRecordInfo(Review req);
}
