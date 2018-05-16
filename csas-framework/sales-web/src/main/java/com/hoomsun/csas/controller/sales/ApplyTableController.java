/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.sales;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.anno.Permission;
import com.hoomsun.core.model.SysEmployee;
import com.hoomsun.core.model.vo.OAStore;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.server.inter.BaseServerI;
import com.hoomsun.core.util.LoginUtil;
import com.hoomsun.csas.sales.api.model.Intention;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.server.inter.IntentionServerI;
import com.hoomsun.csas.sales.api.server.inter.UserApplyServerI;

/**
 * 作者：yg.zhao <br>
 * 创建时间：2017年11月16日 <br>
 * 描述：门店申请控制层
 */
@Controller
public class ApplyTableController {
	private static final Logger log = LoggerFactory.getLogger(ApplyTableController.class);

	@Autowired
	private UserApplyServerI userApplyServir;

	@Autowired
	private BaseServerI baseServer;

	@Autowired
	private IntentionServerI intentionServer;

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月22日 <br>
	 * 描述：新增申请单
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@Permission("apply_create")
	@RequestMapping(value = "/sys/applymodel/addapplymodel.do", method = RequestMethod.POST)
	@ResponseBody
	public Json addApplyTable(@RequestBody UserApply userApply, HttpServletRequest request) throws UnsupportedEncodingException {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常,请刷新页面!");
		}
		Json json = null;
		try {
			json = userApplyServir.addApplyTable(userApply, session,request);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月22日 <br>
	 * 描述：查询申请单(单表apply表)
	 */
	@RequestMapping(value = "/sys/applymodel/selectapply.do")
	@ResponseBody
	public Pager<UserApply> selectApply(Integer page, Integer rows, String custName, String loanId, String nodeStatus, String idNumber, String salesEmpName, String custMobile, String product, Integer source, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		if (sessionRouter == null) {
			return null;
		}

		Pager<UserApply> applyPager = userApplyServir.selectAllByParam(page, rows, custName, loanId, nodeStatus, idNumber, salesEmpName, custMobile, product, source, sessionRouter);
		return applyPager;
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月27日 <br>
	 * 描述：修改回显
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/sys/applymodel/selectapplybyid.do")
	@ResponseBody
	public UserApply selectApplyById(String applyId, HttpServletRequest request) throws UnsupportedEncodingException {
		UserApply applyModel = userApplyServir.selectApplyTableByAppId(applyId);
		return applyModel;
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月27日 <br>
	 * 描述：申请单判断当前节点
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/sys/applymodel/iscurrentnode.do")
	@ResponseBody
	public Json isCurrentNode(String applyId) throws UnsupportedEncodingException {
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数异常");
		}
		Json json = userApplyServir.isCurrentNode(applyId);
		return json;
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年12月25日 <br>
	 * 描述：申请单判断是否草稿
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/sys/applymodel/istemp.do")
	@ResponseBody
	public Json isTemp(String applyId) throws UnsupportedEncodingException {
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数异常");
		}
		Json json = userApplyServir.isTemp(applyId);
		return json;
	}

	/**
	 * 作者：zgzhao <br>
	 * 创建时间：2017年11月28日 <br>
	 * 描述： 修改申请单
	 * 
	 * @param userApply
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@Permission("apply_update")
	@RequestMapping(value = "/sys/applymodel/updateapply.do")
	@ResponseBody
	public Json updateApply(@RequestBody UserApply userApply, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常,请刷新页面!");
		}
		Json json = null;
		try {
			json = userApplyServir.updateApplyTableByApplyId(userApply, session);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月28日 <br>
	 * 描述： 删除表单（伪删除）
	 * 
	 * @param applyId
	 * @return
	 */
	@RequestMapping(value = "/sys/applymodel/deleteapply.do")
	@ResponseBody
	public Json deleteAppply(String applyId, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常,请刷新页面");
		}
		return userApplyServir.deleteApplyTable(applyId, session.getEmpId(), session.getEmpName());
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2018年2月2日 <br>
	 * 描述：申请提交拒贷（伪拒贷）
	 * 
	 * @param applyId
	 * @return
	 */
	@RequestMapping(value = "/sys/applymodel/refuseapply.do")
	@ResponseBody
	public Json refuseApply(String applyId, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常,请刷新页面");
		}
		return userApplyServir.refuseApply(applyId, session.getEmpId(), session.getEmpName());
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月30日 <br>
	 * 描述：申请单上传附件
	 * 
	 * @param applyId
	 * @return
	 */
	@Permission("apply_upload")
	@RequestMapping(value = "/sys/applymodel/createapplyupload.do", method = RequestMethod.POST)
	@ResponseBody
	public Json createApplyUpload(String applyId, Integer applyTypeId, String applyTypeIdVal, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常,请刷新页面!");
		}
		if (file.isEmpty() || null == file) {
			return new Json(false, "上传附件为空!");
		}
		Json json = new Json();
		try {
			json = userApplyServir.createApplyUpload(applyId, applyTypeId, applyTypeIdVal, file, session, request);
		} catch (IOException e) {
			return new Json(false, "上传失败!");
		}
		return json;
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2018年2月11日 <br>
	 * 描述： 文件批量上传
	 * 
	 * @param files
	 * @param applyId
	 * @param imgId
	 * @param imgIdVal
	 * @param request
	 * @return
	 */
	@RequestMapping("/sys/applymodel/multiupload.do")
	@ResponseBody
	public Json multiUpload(@RequestParam("file") MultipartFile[] file, String applyId, Integer applyTypeId, String applyTypeIdVal, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常,请刷新页面!");
		}
		if (file == null || file.length < 1) {
			return new Json(false, "上传附件为空!");
		}
		Json json = new Json();
		json = userApplyServir.multiUpload(file, applyId, applyTypeId, applyTypeIdVal, session, request);
		return json;
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月10日 <br>
	 * 描述： 提交启动流程
	 * 
	 * @param applyId
	 * @param request
	 * @return
	 */
	@Permission("apply_submit")
	@RequestMapping(value = "/sys/apply/start.do")
	@ResponseBody
	public Json startProcess(String applyId, String submitTypeText, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		if (sessionRouter == null) {
			return new Json(false, "登录异常，请刷新页面");
		}
		return userApplyServir.startProcess(applyId, submitTypeText, sessionRouter);
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月28日 <br>
	 * 描述： 获取省份
	 * 
	 * @param applyId
	 * @return
	 */
	@RequestMapping(value = "/sys/applymodel/findallprovinces.do")
	@ResponseBody
	public List<Map<String, String>> findAllProvinces() {
		return baseServer.findAllProvinces();
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月28日 <br>
	 * 描述： 获取市
	 * 
	 * @param applyId
	 * @return
	 */
	@RequestMapping(value = "/sys/applymodel/findcitiebypro.do")
	@ResponseBody
	public List<Map<String, String>> findCitieByPro(String provinceId) {
		return baseServer.findCitieByPro(provinceId);
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月28日 <br>
	 * 描述： 获取区
	 * 
	 * @param applyId
	 * @return
	 */
	@RequestMapping(value = "/sys/applymodel/findareabycity.do")
	@ResponseBody
	public List<Map<String, String>> findAreaByCity(String cityId) {
		return baseServer.findAreaByCity(cityId);
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年12月13日 <br>
	 * 描述：上传回显
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/sys/applymodel/upselectapplybyid.do")
	@ResponseBody
	public UserApply upSelectApplyById(String applyId, HttpServletRequest request) {
		UserApply applyModel = userApplyServir.upSelectApplyById(applyId);
		return applyModel;
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年12月13日 <br>
	 * 描述：门店下所有部门
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/sys/applymodel/selectdeptbystoreid.do")
	@ResponseBody
	public List<OAStore> selectDeptByStoreId(HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		if (sessionRouter == null) {
			return null;
		}
		String storeId = sessionRouter.getStoreId();
		List<OAStore> empStore = userApplyServir.selectDeptByStoreId(storeId);
		return empStore;
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年12月13日 <br>
	 * 描述：部门下的所有员工
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/sys/applymodel/selectempbydepid.do")
	@ResponseBody
	public List<SysEmployee> selectEmpByDepId(String depId, HttpServletRequest request) {
		if (StringUtils.isBlank(depId)) {
			return null;
		}
		List<SysEmployee> oaUser = userApplyServir.findEmpInDept(depId);
		return oaUser;
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年12月18日 <br>
	 * 描述：提交回显
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/sys/applymodel/submitfindbyid.do")
	@ResponseBody
	public UserApply submitFindById(String applyId, HttpServletRequest request) {
		UserApply applyModel = userApplyServir.submitFindById(applyId);
		return applyModel;
	}

	@RequestMapping(value = "sys/applymodel/intentionpager.do", method = { RequestMethod.POST })
	@ResponseBody
	public Pager<Intention> intentionPager(HttpServletRequest request, Integer page, Integer rows, String custname, String mobile) {
		log.info("预约信息展示带分页===============");
		SessionRouter sr = LoginUtil.getLoginSession(request);
		String deptId = sr.getDeptId();
		return intentionServer.findPageByStore(page, rows, custname, deptId, mobile);
	}

	/**
	 * 保存草稿 ygzhao 2017-12-25
	 * 
	 * @param request
	 * @param page
	 * @param rows
	 * @param custname
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "sys/applymodel/savetempapply.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json saveTempApply(@RequestBody UserApply userApply, HttpServletRequest request) throws UnsupportedEncodingException {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常,请刷新页面!");
		}
		return userApplyServir.saveTempApply(userApply, session,request);
	}

	/**
	 * 是否上传附件 ygzhao 2017-12-25
	 */
	@RequestMapping(value = "sys/applymodel/isalreadyupload.do")
	@ResponseBody
	public Json isAlreadyUpload(String applyId, HttpServletRequest request) {
		SessionRouter session = LoginUtil.getLoginSession(request);
		if (session == null) {
			return new Json(false, "登录异常,请刷新页面!");
		}
		return userApplyServir.isAlreadyUpload(applyId);
	}

	/**
	 * 判断登录人与录单人是否一致
	 */
	@RequestMapping(value = "sys/applymodel/logincomparecreate.do")
	@ResponseBody
	public Json loginCompareCreate(String applyId, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		if (sessionRouter == null) {
			return new Json(false, "登录异常,请刷新页面!");
		}

		return userApplyServir.loginCompareCreate(applyId, sessionRouter);
	}

	/**
	 * 添加进件规则校验添加规则 ygzhao 2017-01-18
	 */
	@RequestMapping(value = "sys/blackinfo/findbyidnumber.do")
	@ResponseBody
	public Json validIdNumber(String idnumber, HttpServletRequest request) {
		if (StringUtils.isBlank(idnumber)) {
			return new Json(false, "身份证号为空");
		}
		return userApplyServir.validIdNumber(idnumber, 0, null);
	}

	/**
	 * 修改进件规则校验添加规则 ygzhao 2017-01-18
	 */
	@RequestMapping(value = "sys/blackinfo/findbyidnumberupdate.do")
	@ResponseBody
	public Json validIdNumberUpdate(String idnumber, String applyId, HttpServletRequest request) {
		if (StringUtils.isBlank(idnumber)) {
			return new Json(false, "身份证号为空");
		}
		return userApplyServir.validIdNumber(idnumber, 1, applyId);
	}

	/**
	 * 长传附件、删除、修改检验拦截(是否当前节点+是否登录人与录单人一致) ygzhao 2017-01-21
	 */
	@RequestMapping(value = "sys/blackinfo/lduinterceptor.do")
	@ResponseBody
	public Json LDUInterceptor(String applyId, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		if (sessionRouter == null || StringUtils.isBlank(applyId)) {
			return new Json(false, "登录异常,请刷新页面!");
		}
		Json currentNode = userApplyServir.isCurrentNode(applyId);// 是否当前节点
		Json loginCompare = userApplyServir.loginCompareCreate(applyId, sessionRouter);// 当前登录人与录单人是否一致
		if (!currentNode.getSuccess()) {
			return currentNode;
		}
		if (!loginCompare.getSuccess()) {
			return loginCompare;
		}
		return new Json(true, "校验成功");
	}

	/**
	 * 申请单提交校验 ygzhao 2017-01-21
	 */
	@RequestMapping(value = "sys/blackinfo/submitinterceptor.do")
	@ResponseBody
	public Json submitInterceptor(String applyId, HttpServletRequest request) {
		SessionRouter sessionRouter = LoginUtil.getLoginSession(request);
		if (sessionRouter == null || StringUtils.isBlank(applyId)) {
			return new Json(false, "登录异常,请刷新页面!");
		}
		Json currentNode = userApplyServir.isCurrentNode(applyId);// 是否当前节点
		if (!currentNode.getSuccess()) {
			return currentNode;
		}
		Json loginCompare = userApplyServir.loginCompareCreate(applyId, sessionRouter);// 当前登录人与录单人是否一致
		if (!loginCompare.getSuccess()) {
			return loginCompare;
		}
		Json upload = userApplyServir.isAlreadyUpload(applyId);// 是否上传附件
		if (!upload.getSuccess()) {
			return upload;
		}
		Json temp = userApplyServir.isTemp(applyId);// 是否草稿
		if (!temp.getSuccess()) {
			return temp;
		}
		return new Json(true, "校验成功");
	}
}
