package com.hoomsun.csas.sales.api.server.inter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.csas.sales.api.model.SupplementSubmit;
import com.hoomsun.csas.sales.api.model.UserApply;

public interface SupplementServerI {
	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 查询补充资料所有单子
	 * 
	 * @param storeId
	 */
	Pager<UserApply> selectSupplementApply(Integer page, Integer rows, String custName, String loanId, String nodeStatus, String idNumber, String salesEmpName, String custMobile, SessionRouter sessionRouter);

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月21日 <br>
	 * 描述：修改门店申请表页面做回显
	 * 
	 * @throws UnsupportedEncodingException
	 */
	UserApply selectApplyTableByAppId(String applyId);

	/**
	 * 作者：ygzhao<br>
	 * 创建时间：2017年11月28日 <br>
	 * 描述： 修改申请单
	 * 
	 * @param session
	 * 
	 * @param applyId
	 * @return
	 * @throws UnsupportedEncodingException
	 */

	Json updateApplyTableByApplyId(UserApply userApply, SessionRouter session);

	/**
	 * 补充资料提交进入下一个流程
	 * 
	 * @param applyId
	 * @param empId
	 * @param sessionRouter
	 * @return
	 */
	Json supplementSubmit(String applyId, String submitTypeText, SessionRouter sessionRouter);

	/**
	 * 作者：ygzhao<br>
	 * 创建时间：2017年11月30日 <br>
	 * 描述： 上传申请单附件
	 * 
	 * @param applyId
	 * @param empId
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	Json createApplyUpload(String applyId, Integer applyTypeId, String applyTypeIdVal, MultipartFile multipartFile, SessionRouter session, HttpServletRequest request) throws IllegalStateException, IOException;

	/**
	 * 作者：ygzhao<br>
	 * 创建时间：2017年12月15日 <br>
	 * 描述： 上传申请单附件
	 * 
	 * @param applyId
	 * @param empId
	 * @return
	 */
	Json isCurrentNode(String applyId);

	/**
	 * 作者：ygzhao<br>
	 * 创建时间：2017年12月15日 <br>
	 * 描述： 上传回显示
	 * 
	 * @param applyId
	 * @param empId
	 * @return
	 */
	UserApply upSelectApplyById(String applyId);

	/**
	 * 提交页面回显
	 * 
	 * @param storeId
	 * @return
	 */
	SupplementSubmit submitFindById(String applyId);

	/**
	 * 客户放弃
	 * 
	 * @param applyId
	 * @param submitTypeText
	 * @param sessionRouter
	 * @return
	 */
	Json supplementGiveUp(String applyId, String submitTypeText, SessionRouter sessionRouter);

	/**
	 * 签收
	 * @param applyId
	 * @param empId
	 * @return
	 */
	Json claimTask(String applyId, String empId);

	/**
	 * 回显员工意见信息
	 * @param applyId
	 * @return
	 */
	List<Map<String,Object>> beforeSubmit(String applyId);

	/**
	 * 是否是协议审核退回
	 * @param applyId
	 * @return
	 */
	Json isConAudit(String applyId);

	/**
	 * 补充资料批量上传附件
	 * @param file
	 * @param applyId
	 * @param applyTypeId
	 * @param applyTypeIdVal
	 * @param session
	 * @param request
	 * @return
	 */
	Json multiUpload(MultipartFile[] file, String applyId, Integer applyTypeId,
			String applyTypeIdVal, SessionRouter session,
			HttpServletRequest request);
}
