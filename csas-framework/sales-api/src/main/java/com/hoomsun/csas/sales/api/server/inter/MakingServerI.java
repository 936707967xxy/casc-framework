/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.csas.sales.api.model.vo.MakingVo;

/**
 * 作者：liusong <br>
 * 创建时间：2017年12月4日 <br>
 * 描述：
 */
public interface MakingServerI {
	MakingVo selectByApplyId(String applyId);

	Json completeTask(String applyId, SessionRouter session, String remark);

	Json claimTask(String applyId, String empId);

	Json checkClaim(String applyId, String empId);

	Json withdraw(String applyId, SessionRouter session, String remark);

	Json reject(String applyId, SessionRouter session, String remark);

	Json rollback(String applyId, SessionRouter session, String remark);

	Json waiver(String applyId, SessionRouter session, String remark);

	Pager<MakingVo> findPage(Integer page, Integer rows, String custName, String custMobile, String loanId, String empId, String deptId, String storeId, String nodeStatus, String node);

	Json selectTask(String applyId);

	Json upload(MultipartFile file, String applyId, Integer imgId, String imgIdVal, String loginEmp, String loginName, String context);

	Json multiUpload(MultipartFile[] file, String applyId, Integer imgId, String imgIdVal, String loginEmp, String loginName, String context);

	Json deleteAnnex(String id);

	Integer findByapplyIdAndAnnexType(String applyId);
}
