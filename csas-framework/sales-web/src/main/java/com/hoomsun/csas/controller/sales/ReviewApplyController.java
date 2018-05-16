/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.sales;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.csas.sales.api.model.ReviewApply;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.server.inter.ReviewApplyServerI;
import com.hoomsun.csas.sales.api.server.inter.UserApplyServerI;

/**
 * 作者：liusong <br>
 * 创建时间：2018年1月16日 <br>
 * 描述：
 */
@Controller
public class ReviewApplyController {
	@Autowired
	private ReviewApplyServerI reviewApplyServer;
	@Autowired
	private UserApplyServerI userApplyServer;

	@RequestMapping(value = "/sys/review/pager.do")
	@ResponseBody
	public Pager<ReviewApply> findPage(Integer page, Integer rows, String custName, String custMobile, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		if (sessionRouter == null) {
			return null;
		}
		String empId = sessionRouter.getEmpId();
		String deptId = sessionRouter.getDeptId();
		String storeId = sessionRouter.getStoreId();
		Pager<ReviewApply> applyPager = reviewApplyServer.findPage(page, rows, custName, custMobile, empId, deptId, storeId);
		return applyPager;
	}

	@RequestMapping("/sys/review/upload.do")
	@ResponseBody
	public Json upload(@RequestParam("file") MultipartFile file, String applyId, HttpServletRequest request) {
		String context = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		String loginEmp = sessionRouter.getEmpId();
		String loginName = sessionRouter.getEmpName();
		return reviewApplyServer.upload(file, applyId, loginEmp, loginName, context);
	}

	@RequestMapping("/sys/review/delete.do")
	@ResponseBody
	public Json delete(String id, HttpServletRequest request) {
		return reviewApplyServer.deleteAnnex(id);
	}

	@RequestMapping(value = "/sys/review/save.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json complete(String applyId, String remark, String reviewType, String reviewTypeVal, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		ReviewApply reviewApply = new ReviewApply();
		UserApply apply = userApplyServer.selectByAply(applyId);
		reviewApply.setCustName(apply.getCustName());
		reviewApply.setCustMobile(apply.getCustMobile());
		reviewApply.setApplyAmount(apply.getApplyAmount());
		reviewApply.setApplyId(apply.getApplyId());
		reviewApply.setProductId(apply.getProductId());
		reviewApply.setProductName(apply.getProductName());
		reviewApply.setLoanId(apply.getLoanId());
		reviewApply.setIdNumber(apply.getIdNumber());
		reviewApply.setRemark(remark);
		reviewApply.setReviewType(reviewTypeVal);
		reviewApply.setReviewTypeVal(reviewTypeVal);
		reviewApply.setEmployeeId(sessionRouter.getEmpId());
		reviewApply.setEmployeeName(sessionRouter.getEmpName());
		return reviewApplyServer.createApply(reviewApply);
	}

	@RequestMapping("/sys/review/compare.do")
	@ResponseBody
	public Json compare(String applyId, HttpServletRequest request) {

		return reviewApplyServer.compare(applyId);
	}

}
