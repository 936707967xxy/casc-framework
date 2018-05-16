/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import java.util.List;

import com.hoomsun.after.api.model.param.CollectionRecordReq;
import com.hoomsun.after.api.model.param.CustomerTaskAllocationReq;
import com.hoomsun.after.api.model.param.VistTaskReq;
import com.hoomsun.after.api.model.vo.CustomerCollectionRemindingResult;
import com.hoomsun.after.api.model.vo.CustomerTaskAllocationResult;
import com.hoomsun.after.api.model.vo.VisitRecordResult;
import com.hoomsun.after.api.model.vo.VistTaskResult;
import com.hoomsun.common.model.Json;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年5月7日 <br>
 * 描述：外访申请列表以及分配API
 */
public interface VisitTaskOprationServer {

	List<VistTaskResult>queryVisitTaskList(VistTaskReq req);
	
	Integer countVisitTask(VistTaskReq req);
	
	void downloadVisitTask(VistTaskReq req);
	
	Json updateVisiTask(VistTaskReq req);
	
	List<CustomerTaskAllocationResult>querySysEmployee(CustomerTaskAllocationReq req);
	
    List<VisitRecordResult>queryOutBoundLog(VistTaskReq req);
	
    Json addOutBoundLog(VisitRecordResult req);
    
}
