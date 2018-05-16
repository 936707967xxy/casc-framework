/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hoomsun.after.api.model.param.PublicSaveReq;
import com.hoomsun.after.api.model.table.PublicSave;
import com.hoomsun.after.api.model.vo.CunGongPublicSaveResult;
import com.hoomsun.common.model.Json;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年4月26日 <br>
 * 描述：存公记录
 */
public interface CunGongPublicSaveServer {

	List<CunGongPublicSaveResult>queryCunGongPublicSave(PublicSaveReq req);
	
	Integer  countCunGongPublicSave(PublicSaveReq req);
	
	void downloadCunGongPublicSave(PublicSaveReq req);
	
	Json exportExcelCunGongPublicSave(InputStream is,HttpServletRequest request);
	
}
