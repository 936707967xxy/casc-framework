/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.audit.server.inter;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.common.model.Json;
import com.hoomsun.csas.audit.model.AuditAnnex;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月16日 <br>
 * 描述：审核的附件
 */
public interface AuditAnnexServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述： 文件上传
	 * @param file
	 * @param applyId
	 * @param loginEmp
	 * @param loginName
	 * @return
	 */
	Json upload(MultipartFile file,String applyId,String loginEmp,String loginName,String context);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述： 根据申请ID获取审核附件数据
	 * @param applyId
	 * @return
	 */
	List<AuditAnnex> findByApplyId(String applyId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述： 
	 * @return
	 */
	Json deleteAnnex(String id);
}
