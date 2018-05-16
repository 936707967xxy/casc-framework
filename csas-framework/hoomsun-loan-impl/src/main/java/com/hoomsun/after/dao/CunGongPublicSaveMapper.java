/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.dao;

import java.util.List;

import com.hoomsun.after.api.model.param.PublicSaveReq;
import com.hoomsun.after.api.model.table.PublicSave;
import com.hoomsun.after.api.model.table.PublicSaveLog;
import com.hoomsun.after.api.model.vo.CunGongPublicSaveResult;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年4月26日 <br>
 * 描述：存公记录功能
 */
public interface CunGongPublicSaveMapper {

    List<CunGongPublicSaveResult>queryCunGongPublicSave(PublicSaveReq req)throws Exception;
	
	Integer  countCunGongPublicSave(PublicSaveReq req)throws Exception;
	
	Integer insertCunGongPublicSaveLog(PublicSaveLog req)throws Exception;
	
	Integer insertCunGongPublicSave(PublicSave req)throws Exception;
	
	CunGongPublicSaveResult queryPublicSaveDetails(PublicSave log);
}
