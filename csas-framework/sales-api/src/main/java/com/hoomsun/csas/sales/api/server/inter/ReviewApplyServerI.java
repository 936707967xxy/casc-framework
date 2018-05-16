/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import com.hoomsun.common.model.Json;

import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.common.model.Pager;
import com.hoomsun.csas.sales.api.model.ReviewApply;

/**
 * 作者：liusong <br>
 * 创建时间：2018年1月16日 <br>
 * 描述：
 */
public interface ReviewApplyServerI {

	Pager<ReviewApply> findPage(Integer page, Integer rows, String custName, String custMobile, String empId, String deptId, String storeId);

	Json upload(MultipartFile file, String applyId, String loginEmp, String loginName, String context);

	Json deleteAnnex(String id);

	public Json createApply(ReviewApply reviewApply);
	
	ReviewApply selectByApplyId(String applyId);
	
	Json compare(String applyId);

}
