/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.dao;

import java.util.List;

import com.hoomsun.after.api.model.param.VistTaskReq;
import com.hoomsun.after.api.model.vo.VisitRecordResult;
import com.hoomsun.after.api.model.vo.VistTaskResult;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年5月7日 <br>
 * 描述：外访申请列表以及分配
 */
public interface VisitTaskOprationMapper {

     List<VistTaskResult>queryVisitTaskList(VistTaskReq req)throws Exception;
	
	Integer countVisitTask(VistTaskReq req)throws Exception;
	
	Integer updateVisiTask(VistTaskReq req)throws Exception;
	
	List<VisitRecordResult>queryOutBoundLog(VistTaskReq req)throws Exception;
	
	Integer addOutBoundLog(VisitRecordResult req)throws Exception;
}
