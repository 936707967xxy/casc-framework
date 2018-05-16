/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.server.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.app.api.dao.AuthenticationUrlMapper;
import com.hoomsun.app.api.dao.IncomeMapper;
import com.hoomsun.app.api.model.AuthenticationUrl;
import com.hoomsun.app.api.model.Income;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.SystemUtils;
import com.hoomsun.common.util.UploadPathUtil;
import com.hoomsun.core.dao.BlackInfoMapper;
import com.hoomsun.core.dao.SysDepartmentMapper;
import com.hoomsun.core.dao.SysEmployeeMapper;
import com.hoomsun.core.enums.AnnexType;
import com.hoomsun.core.model.SysContract;
import com.hoomsun.core.model.SysDepartment;
import com.hoomsun.core.model.SysEmployee;
import com.hoomsun.core.model.SysProduct;
import com.hoomsun.core.model.vo.OAStore;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.core.server.inter.SerialNumberServerI;
import com.hoomsun.core.server.inter.SysProductServerI;
import com.hoomsun.core.util.DateUtil;
import com.hoomsun.core.util.DownloadHelper;
import com.hoomsun.core.util.PrimaryKeyUtil;
import com.hoomsun.csas.sales.api.exception.AuditException;
import com.hoomsun.csas.sales.api.model.Annex;
import com.hoomsun.csas.sales.api.model.AssetInfo;
import com.hoomsun.csas.sales.api.model.CreditCard;
import com.hoomsun.csas.sales.api.model.CreditCardBills;
import com.hoomsun.csas.sales.api.model.CreditCardBillsInfo;
import com.hoomsun.csas.sales.api.model.Intention;
import com.hoomsun.csas.sales.api.model.NameAuthentication;
import com.hoomsun.csas.sales.api.model.OccupationalInfo;
import com.hoomsun.csas.sales.api.model.PbccrcChartView;
import com.hoomsun.csas.sales.api.model.QcCheck;
import com.hoomsun.csas.sales.api.model.UserAllAuthInfo;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.UserCallDetail;
import com.hoomsun.csas.sales.api.model.UserCallVisual;
import com.hoomsun.csas.sales.api.model.UserChsi;
import com.hoomsun.csas.sales.api.model.UserCis;
import com.hoomsun.csas.sales.api.model.UserContacts;
import com.hoomsun.csas.sales.api.model.UserHouseFund;
import com.hoomsun.csas.sales.api.model.UserPbccre;
import com.hoomsun.csas.sales.api.model.UserSocialsecurity;
import com.hoomsun.csas.sales.api.model.UserTaoBaoAddress;
import com.hoomsun.csas.sales.api.model.UserTaobao;
import com.hoomsun.csas.sales.api.model.UserTongDun;
import com.hoomsun.csas.sales.api.server.inter.UserApplyServerI;
import com.hoomsun.csas.sales.dao.AnnexMapper;
import com.hoomsun.csas.sales.dao.AssetInfoMapper;
import com.hoomsun.csas.sales.dao.CreditCardBillsInfoMapper;
import com.hoomsun.csas.sales.dao.CreditCardBillsMapper;
import com.hoomsun.csas.sales.dao.CreditCardMapper;
import com.hoomsun.csas.sales.dao.IntentionMapper;
import com.hoomsun.csas.sales.dao.NameAuthenticationMapper;
import com.hoomsun.csas.sales.dao.OccupationalInfoMapper;
import com.hoomsun.csas.sales.dao.PbccrcChartViewMapper;
import com.hoomsun.csas.sales.dao.QcCheckMapper;
import com.hoomsun.csas.sales.dao.UserAllAuthInfoMapper;
import com.hoomsun.csas.sales.dao.UserApplyMapper;
import com.hoomsun.csas.sales.dao.UserCallDetailMapper;
import com.hoomsun.csas.sales.dao.UserCallVisualMapper;
import com.hoomsun.csas.sales.dao.UserChsiMapper;
import com.hoomsun.csas.sales.dao.UserCisMapper;
import com.hoomsun.csas.sales.dao.UserContactsMapper;
import com.hoomsun.csas.sales.dao.UserHouseFundMapper;
import com.hoomsun.csas.sales.dao.UserPbccrcHtmlMapper;
import com.hoomsun.csas.sales.dao.UserPbccreMapper;
import com.hoomsun.csas.sales.dao.UserSocialsecurityMapper;
import com.hoomsun.csas.sales.dao.UserTaoBaoAddressMapper;
import com.hoomsun.csas.sales.dao.UserTaobaoMapper;
import com.hoomsun.csas.sales.dao.UserTongDunMapper;
import com.hoomsun.csas.sales.util.IntentionDate;

//java.lang.ClassCastException:   org.apache.commons.httpclient.util.HttpURLConnection
/**
 * 作者：yg.zhao <br>
 * 创建时间：2017年11月22日 <br>
 * 描述：申请表单server实现
 */
@Service("userApplyServer")
public class UserApplyServerImpl implements UserApplyServerI {
	private final static Logger log = LoggerFactory.getLogger(UserApplyServerImpl.class);

	@Autowired
	private UserApplyMapper userApplyMapper;// 用户申请表
	@Autowired
	private NameAuthenticationMapper nameAuthenticationMapper;// 申请人主表
	@Autowired
	private UserContactsMapper userContactsMapper;// 联系人信息表
	@Autowired
	private AssetInfoMapper assetInfoMapper;// 资产信息表
	@Autowired
	private OccupationalInfoMapper occupationalInfoMapper;// 职业信息表
	@Autowired
	private IncomeMapper incomeMapper;// 收入图片上传
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private AnnexMapper annexMapper;// 上传表
	@Autowired
	private UploadPathUtil uploadPathUtil;// 上传文档
	@Autowired
	private UserCisMapper userCisMapper;
	@Autowired
	private UserTaobaoMapper userTaobaoMapper;
	@Autowired
	private UserTaoBaoAddressMapper userTaoBaoAddressMapper;
	@Autowired
	private UserChsiMapper userChsiMapper;
	@Autowired
	private UserPbccreMapper userPbccreMapper;
	@Autowired
	private UserPbccrcHtmlMapper userPbccrcHtmlMapper;
	@Autowired
	private UserHouseFundMapper userHouseFundMapper;
	@Autowired
	private UserSocialsecurityMapper userSocialsecurityMapper;
	@Autowired
	private CreditCardMapper creditCardMapper;
	@Autowired
	private CreditCardBillsMapper creditCardBillsMapper;
	@Autowired
	private CreditCardBillsInfoMapper creditCardBillsInfoMapper;
	@Autowired
	private UserCallDetailMapper userCallDetailMapper;
	@Autowired
	private SerialNumberServerI serialnumberServer;
	@Autowired
	private SysProductServerI sysProductServer;// 产品
	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;// 员工表
	@Autowired
	private QcCheckMapper qcCheckMapper;
	@Autowired
	private IntentionMapper intentionMaper;// 预约mappper
	@Autowired
	private UserCallVisualMapper userCallVisualMapper;  // 通话详单可视化
	@Autowired
	private UserAllAuthInfoMapper userAllAuthInfoMapper;
	@Autowired
	private BlackInfoMapper blackinfomapper;
	@Autowired
	private AuthenticationUrlMapper authenticationUrlMapper;
	@Autowired
	private PbccrcChartViewMapper pbccrcChartViewMapper; // 征信可视化
	@Autowired
	private UserTongDunMapper userTongDunMapper;// 同盾
	@Autowired
	private SysDepartmentMapper sysDepartmentMapper;
	
	
	/**
	 * 添加申请单
	 * 
	 * @throws ParseException
	 */
	@Override
	public Json addApplyTable(UserApply userApply, SessionRouter sessionRouter,HttpServletRequest request) throws ParseException {
		/**
		 * 生成主键
		 */
		String applyId = PrimaryKeyUtil.getPrimaryKey();// 申请表ID
		String nameAuthId = null;// 客户主表ID
		String assetId = PrimaryKeyUtil.getPrimaryKey();// 资产表ID
		String occupationalId = PrimaryKeyUtil.getPrimaryKey();// 职业表ID
		Timestamp currentTime = DateUtil.getTimestamp();
		/**
		 * 生成客户唯一编号
		 */
		String loanId = serialnumberServer.createNumber("2", sessionRouter.getDeptNo());
		userApply.setLoanId(loanId);
		/**
		 * 获取各参数对象
		 */
		List<UserContacts> afreshContacterInfo = userApply.getUserContacts();// 联系人信息
		AssetInfo assetInfo = userApply.getAssetInfo();// 资产信息
		OccupationalInfo occupationalInfo = userApply.getUserOccupationalInfo();// 职业信息表
		if (userApply == null || afreshContacterInfo == null || assetInfo == null || occupationalInfo == null) {
			return new Json(false, "参数对象为空，请检查!");
		}

		/**
		 * 根据身份证号查找主表看是否实名认证
		 */
		String acceptPaperId = userApply.getIdNumber();// 接收-身份证号
		if (StringUtils.isBlank(acceptPaperId)) {
			return new Json(false, "身份证号为空!");
		}
		/**
		 * 判断客户类型
		 */
		List<String> procStatusList = userApplyMapper.findByIdnumber(acceptPaperId);//所有流程单子
		if(procStatusList!=null&&procStatusList.size()>0){
			boolean isCycle = false;//是否循环贷
			for (String proc : procStatusList) {
				if(StringUtils.isNotBlank(proc)&&"success".equals(proc)){
					isCycle = true;
					break;
				}
			}
			if(isCycle){
				userApply.setCustTypeId(2);
				userApply.setCustTypeVal("循环贷");
			}else{
				userApply.setCustTypeId(1);
				userApply.setCustTypeVal("老客户");
			}
		}else{
			userApply.setCustTypeId(0);
			userApply.setCustTypeVal("新客户");
		}
		
		NameAuthentication nameAuth = nameAuthenticationMapper.selectByPaperId(acceptPaperId);
		// 已实名存id,否则存null,等栋梁后期补申请、联系人主表字段;
		if (nameAuth != null && nameAuth.getIsauthentication() != null && nameAuth.getIsauthentication().equals("1")) {
			nameAuthId = nameAuth.getId();
			userApply.setCustName(nameAuth.getCustname());
		}
		/**
		 * 预约表信息处理
		 */
		int idCount = intentionMaper.checkIdNumber(acceptPaperId);
		int phoneCount = intentionMaper.checkPhone(userApply.getCustMobile());
		if (idCount < 1 && phoneCount < 1) {
			Intention intention = new Intention();
			String deptId = sessionRouter.getDeptId();// 部门id
			String deptname = sessionRouter.getDeptName();// 部门name
			String empId = sessionRouter.getEmpId();// 客服id
			String storeId = sessionRouter.getStoreId();// 门店ID
			String storeName = sessionRouter.getStoreName();// 门店名称
			String createEmpId = sessionRouter.getEmpId();// 登录员工id
			String createEmpName = sessionRouter.getEmpName();// 登录员工name
			/**
			 * 新增字段2018-1-10
			 */
			intention.setCreateEmployee(createEmpId);// 登录员工id
			intention.setCreateEmployeeVal(createEmpName);// 登录员工name
			intention.setOrgname(storeName);// 门店名称

			intention.setTeamid(deptId);// 部门编号
			intention.setDeptname(deptname);// 部门名称
			intention.setCustname(userApply.getCustName());// 姓名
			intention.setPaperid(userApply.getIdNumber());// 身份证
			intention.setSexval(userApply.getCustSex());// 性别
			intention.setMobile(userApply.getCustMobile());// 手机号
			intention.setProdid(userApply.getProductTypeId());// 产品id
			intention.setProdname(userApply.getProductTypeName());// 产品类型
			// intention.setCertvaliddate("");//证件有效期
			intention.setAppoperid(userApply.getSalesEmpId());// 销售编号
			intention.setAppopername(userApply.getSalesEmpName());// 销售姓名
			// intention.setAppoperphone(appoperphone);//销售手机号
			intention.setSource(userApply.getSources() + "");// 客户来源id
			intention.setSourceval(userApply.getSourcesValue());
			intention.setOperid(empId);// 客服编号
			intention.setOrgid(storeId);// 门店id
			intention.setLoanpurpose(userApply.getLoanUse() + "");// 借款用途
			intention.setLoanpurposeval(userApply.getLoanUseVal());// 借款用途值
			intention.setLoanuseexp(userApply.getLoanUseText());// 借款用途其他值
			intention.setLoanquota(userApply.getApplyAmount().longValue());// 申请额度
			intention.setLoanterms(userApply.getApplyPeriod() + "");// 申请期限
			intention.setIdcerttype("0");
			intention.setIdcerttypeval("身份证");

			if (StringUtils.isBlank(intention.getOrdplyId())) {
				Timestamp time = new Timestamp(System.currentTimeMillis());
				intention.setApplydate(time);
				intention.setOrdplyId(PrimaryKeyUtil.getPrimaryKey());
				intention.setApplyid(serialnumberServer.createNumber("3", sessionRouter.getDeptNo()));
			}

			// 查询申请日期
			String mobile = intention.getMobile();

			// 日期计算类
			IntentionDate sad = new IntentionDate();
			// 查询申请日期
			String applydate = intentionMaper.selectApplyDate(mobile);
			// 判断封申请日期是否大于闭日期
			long day;
			if (applydate == null || applydate == "") {
				day = 0;
				// 添加数据
				intentionMaper.insertSelective(intention);
			} else {
				day = sad.ApplyDate(applydate);

				long closeDate = 7;// 封闭日期

				if (day <= closeDate) {
					return new Json(false, "申请日期小于封闭日期，7天以后添加咨询!");
				} else {
					// 添加数据
					intentionMaper.insertSelective(intention);
				}
			}
		}
		userApply.setCreateEmployee(sessionRouter.getEmpId());// 创建人id
		userApply.setCreateEmployeeVal(sessionRouter.getEmpName());
		userApply.setLastUpdateEmpId(sessionRouter.getEmpId());// 修改人id
		userApply.setLastUpdateEmpName(sessionRouter.getEmpName());
		userApply.setStoreId(sessionRouter.getStoreId());// 门店id
		userApply.setStoreName(sessionRouter.getStoreName());
		SysDepartment sysDepart = sysDepartmentMapper.selectByPrimaryKey(sessionRouter.getStoreId());
		userApply.setApplyAddress(sysDepart.getDeptWorkAddr());//申请地址
		userApply.setIpAddress(SystemUtils.getIpAddr(request));//申请ip
		userApply.setIsTemp(0);// 草稿标志
		userApply.setIsTempVal("否");// 草稿结果
		String salesDept = userApply.getSalesEmpDeptId();//销售所在部门
		String serviceDept = sessionRouter.getDeptId();//客服所在部门
		String salesName = userApply.getSalesEmpName();//销售姓名
		if (StringUtils.isNotBlank(salesDept)
				&& StringUtils.isNotBlank(serviceDept)&&StringUtils.isNotBlank(salesName)
				&& serviceDept.equals(salesDept)) {//如果部门一致则销售就是客服
			userApply.setSalesEmpName(salesName+"-客服");

		}
		/**
		 * 申请表处理
		 */
		int applyCount = 0;
		if (userApply != null) {
			/**
			 * 产品表字段处理
			 */
			SysProduct susPro = sysProductServer.findById(userApply.getProductId());
			userApply.setProductRate(susPro.getMonthRate());// 月利率
			userApply.setProductFeeRate(susPro.getYearRate()); // 年利率
			userApply.setSuggestRate(susPro.getRealMonthRate());// 实际利率
			userApply.setProductPay(susPro.getPayType());// 申请产品还款方式ID
			userApply.setProductPayVal(susPro.getPayTypeVal());// 申请产品还款方式值
			Integer validId = userApply.getValidMailAddr();// 邮寄地址ID
			if (validId == null) {
				return new Json(false, "邮寄地址未选择");
			}
			switch (validId) {
			case 1:// 1:同户口地址
				userApply.setValidMailAddrTxt(userApply.getRresidenceAddress());
				break;
			case 2:// 2:同居住地址
				userApply.setValidMailAddrTxt(userApply.getHouseAddress());
				break;
			case 3:// 3:同单位地址
				userApply.setValidMailAddrTxt(occupationalInfo.getCompanyAddress());
				break;
			case 4:// 4:同房产地址
				userApply.setValidMailAddrTxt(assetInfo.getPropertyAddress());
				break;
			}
			userApply.setApplyId(applyId);
			userApply.setCustId(nameAuthId);
			userApply.setDelStatu(0);// 删除状态
			userApply.setIdType(0);
			userApply.setIdTypeVal("身份证");
			/**
			 * app需要补字段
			 */
			userApply.setIsApp(0);
			userApply.setIsAppVal("线下");
			userApply.setAuditType(0);// 审核状态
			userApply.setAgreedProduct("0");// 同意产品名称
			userApply.setAgreeProductId("0");// 同意产品id
			userApply.setAgreePeriod(0);// 同意期数
			userApply.setAgreedProductAlias("0");// 同意产品别名
			userApply.setAgreedAmount(new BigDecimal(0));// 同意额度
			userApply.setApplyDate(currentTime);
			userApply.setLastUpdateTime(currentTime);
			applyCount = userApplyMapper.insertSelective(userApply);
		}
		/**
		 * 联系人表处理
		 */
		int contactCount = 0;
		for (UserContacts contact : afreshContacterInfo) {
			String contactId2 = PrimaryKeyUtil.getPrimaryKey();// 联系人ID;
			Integer reId = contact.getRelationship();
			Integer isKnow = contact.getIsKnow();
			contact.setIsFillIn(1);
			contact.setIsFillInVal("是");
			if (isKnow == null || reId == null) {
				return new Json(false, "联系人信息异常!");
			}
			switch (reId) {
			case 1:// 1:配偶
				contact.setRelationshipVal("配偶");
				break;
			case 2:// 2:亲属
				contact.setRelationshipVal("亲属");
				break;
			case 3:// 3:同事
				contact.setRelationshipVal("同事");
				break;
			case 4:// 4:其他
				contact.setRelationshipVal("其他");
				break;
			}
			switch (isKnow) {
			case 0:
				contact.setIsKnowVal("否");
				break;
			case 1:
				contact.setIsKnowVal("是");
				break;
			}
			contact.setContId(contactId2);
			contact.setAddDate(currentTime);
			contact.setName(contact.getName());
			contact.setApplyId(applyId);
			contactCount = userContactsMapper.insertSelective(contact);
		}

		/**
		 * 资产信息表处理
		 */
		int assetCount = 0;
		assetInfo.setAsinfoPkId(assetId);
		assetInfo.setApplyId(applyId);
		assetInfo.setCreateTime(currentTime);
		assetCount = assetInfoMapper.insertSelective(assetInfo);

		/**
		 * 职业信息表处理
		 */
		int occCount = 0;
		occupationalInfo.setOcinfoPkId(occupationalId);
		occupationalInfo.setApplyId(applyId);
		occupationalInfo.setCreateTime(currentTime);
		occCount = occupationalInfoMapper.insertSelective(occupationalInfo);

		if (applyCount > 0 && contactCount > 0 && assetCount > 0 && occCount > 0) {
			return new Json(true, "添加成功!");
		}

		return new Json(false, "添加失败!");

	}

	@Override
	public Json deleteApplyTable(String applyId, String empId, String empName) {
		if (StringUtils.isBlank(applyId) || StringUtils.isBlank(empId) || StringUtils.isBlank(empName)) {
			return new Json(false, "参数异常");
		}
		// 启动前前验证是否已经启动了该流程实例
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(applyId).singleResult();
		if (processInstance != null) {
			return new Json(false, "该条数据已启动流程,无法进行删除操作!");
		}
		int aCou = userApplyMapper.deleteApplyTableByAppId(applyId, empId, empName);
		if (aCou > 0) {
			return new Json(true, "删除成功");
		}
		return new Json(false, "删除失败");
	}

	/**
	 * 修改回显
	 */
	@Override
	public UserApply selectApplyTableByAppId(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			return null;
		}
		UserApply userApply = userApplyMapper.selectApplyTableByAppId(applyId);// 申请表
		if (userApply == null) {
			return null;
		}
		List<UserContacts> con = userContactsMapper.selectByApplyId(applyId, 1);// 联系人
		if (con == null) {
			con = new ArrayList<UserContacts>();
		}
		AssetInfo asset = assetInfoMapper.selectByApplyId(applyId);// 资产信息
		if (asset == null) {
			asset = new AssetInfo();
		}
		OccupationalInfo occ = occupationalInfoMapper.selectByApplyId(applyId);// 职业信息
		if (occ == null) {
			occ = new OccupationalInfo();
		}
		if (userApply != null) {
			userApply.setUserContacts(con);
			userApply.setAssetInfo(asset);
			userApply.setUserOccupationalInfo(occ);
		}
		return userApply;
	}

	/**
	 * 修改申请单
	 * 
	 * @throws ParseException
	 */
	@Override
	public Json updateApplyTableByApplyId(UserApply userApply, SessionRouter sessionRouter) throws ParseException {
		if (StringUtils.isBlank(userApply.getApplyId())) {
			return new Json(false, "参数异常");
		}

		// 启动前前验证是否已经启动了该流程实例
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(userApply.getApplyId()).singleResult();
		if (processInstance != null) {
			return new Json(false, "该条数据已启动流程,不支持修改操作!");
		}
		/**
		 * 默认不修改字段
		 */
		userApply.setLoanId(null);
		userApply.setCreateEmployee(null);// 创建人id
		userApply.setCreateEmployeeVal(null);
		userApply.setSubmitEmpId(null);// 提交员工
		userApply.setSubmitEmpName(null);// 提交姓名
		userApply.setSubmitTypeText(null);// 提交文本
		userApply.setSubmitDate(null);// 提交时间
		userApply.setStoreId(null);// 门店id
		userApply.setStoreName(null);// 门店名称
		userApply.setProcStatus(null);// 流程字段
		userApply.setProcStatus(null);
		userApply.setProcInstId(null);
		userApply.setIsApp(null);
		userApply.setIsAppVal(null);
		userApply.setAuditType(null);// 审核状态
		userApply.setAgreedProduct(null);// 同意产品名称
		userApply.setAgreeProductId(null);// 同意产品id
		userApply.setAgreePeriod(null);// 同意期数
		userApply.setAgreedProductAlias(null);// 同意产品别名
		userApply.setAgreedAmount(null);// 同意额度
		userApply.setApplyDate(null);
		String salesDept = userApply.getSalesEmpDeptId();//销售所在部门
		String serviceDept = sessionRouter.getDeptId();//客服所在部门
		String salesName = userApply.getSalesEmpName();//销售姓名
		if (StringUtils.isNotBlank(salesDept)
				&& StringUtils.isNotBlank(serviceDept)&&StringUtils.isNotBlank(salesName)
				&& serviceDept.equals(salesDept)) {//如果部门一致则销售就是客服
			userApply.setSalesEmpName(salesName+"-客服");

		}
		Timestamp currentTime = DateUtil.getTimestamp();
		/**
		 * 如果已实名认证，不允许进行修改身份证号和姓名
		 */
		UserApply applyBefore = userApplyMapper.selectApplyTableByAppId(userApply.getApplyId());
		if (applyBefore != null) {
			String beforeIdnumber = applyBefore.getIdNumber();
			NameAuthentication nameAuth = nameAuthenticationMapper.selectByPaperId(beforeIdnumber);
			if (nameAuth != null && nameAuth.getIsauthentication() != null && nameAuth.getIsauthentication().equals("1")) {
				userApply.setIdNumber(nameAuth.getPaperid());
				userApply.setCustName(nameAuth.getCustname());
			}

			/**
			 * 如果该申请单为草稿
			 */
			if (applyBefore.getIsTemp() != null && applyBefore.getIsTemp() == 1) {
				userApply.setIsTemp(0);// 草稿标志
				userApply.setIsTempVal("否");// 草稿结果
				/**
				 * 预约表信息处理
				 */
				if (StringUtils.isBlank(userApply.getCustMobile())) {
					return new Json(false, "手机号为不能为空!");
				}
				int idCount = intentionMaper.checkIdNumber(beforeIdnumber);
				int phoneCount = intentionMaper.checkPhone(userApply.getCustMobile());
				if (idCount < 1 && phoneCount < 1) {
					Intention intention = new Intention();
					String deptId = sessionRouter.getDeptId();// 部门id
					String deptname = sessionRouter.getDeptName();// 部门name
					String empId = sessionRouter.getEmpId();// 客服id
					String storeId = sessionRouter.getStoreId();// 门店ID
					String storeName = sessionRouter.getStoreName();// 门店名称
					String createEmpId = sessionRouter.getEmpId();// 登录员工id
					String createEmpName = sessionRouter.getEmpName();// 登录员工name

					/**
					 * 新增字段2018-1-10
					 */
					intention.setCreateEmployee(createEmpId);// 登录员工id
					intention.setCreateEmployeeVal(createEmpName);// 登录员工name
					intention.setOrgname(storeName);// 门店名称

					intention.setTeamid(deptId);// 部门编号
					intention.setDeptname(deptname);// 部门名称
					intention.setCustname(userApply.getCustName());// 姓名
					intention.setPaperid(userApply.getIdNumber());// 身份证
					intention.setSexval(userApply.getCustSex());// 性别
					intention.setMobile(userApply.getCustMobile());// 手机号
					intention.setProdid(userApply.getProductTypeId());// 产品id
					intention.setProdname(userApply.getProductTypeName());// 产品类型
					// intention.setCertvaliddate("");//证件有效期
					intention.setAppoperid(userApply.getSalesEmpId());// 销售编号
					intention.setAppopername(userApply.getSalesEmpName());// 销售姓名
					// intention.setAppoperphone(appoperphone);//销售手机号
					intention.setSource(userApply.getSources() + "");// 客户来源id
					intention.setSourceval(userApply.getSourcesValue());
					intention.setOperid(empId);// 客服编号
					intention.setOrgid(storeId);// 门店id
					intention.setLoanpurpose(userApply.getLoanUse() + "");// 借款用途
					intention.setLoanpurposeval(userApply.getLoanUseVal());// 借款用途值
					intention.setLoanuseexp(userApply.getLoanUseText());// 借款用途其他值
					intention.setLoanquota(userApply.getApplyAmount().longValue());// 申请额度
					intention.setLoanterms(userApply.getApplyPeriod() + "");// 申请期限
					intention.setIdcerttype("0");
					intention.setIdcerttypeval("身份证");

					if (StringUtils.isBlank(intention.getOrdplyId())) {
						Timestamp time = new Timestamp(System.currentTimeMillis());
						intention.setApplydate(time);
						intention.setOrdplyId(PrimaryKeyUtil.getPrimaryKey());
						intention.setApplyid(serialnumberServer.createNumber("3", sessionRouter.getDeptNo()));
					}
					// 查询申请日期
					String mobile = intention.getMobile();

					// 日期计算类
					IntentionDate sad = new IntentionDate();
					// 查询申请日期
					String applydate = intentionMaper.selectApplyDate(mobile);
					// 判断封申请日期是否大于闭日期
					long day;
					if (applydate == null || applydate == "") {
						day = 0;
						// 添加数据
						intentionMaper.insertSelective(intention);
					} else {
						day = sad.ApplyDate(applydate);

						long closeDate = 7;// 封闭日期

						if (day <= closeDate) {
							return new Json(false, "申请日期小于封闭日期，7天以后添加咨询!");
						} else {
							// 添加数据
							intentionMaper.insertSelective(intention);
							;
						}
					}
				}
			}
		}

		userApply.setLastUpdateEmpId(sessionRouter.getEmpId());// 修改人id
		userApply.setLastUpdateEmpName(sessionRouter.getEmpName());
		userApply.setLastUpdateTime(currentTime);

		OccupationalInfo occupationalInfo = userApply.getUserOccupationalInfo();// 职业信息表
		AssetInfo assetInfo = userApply.getAssetInfo();// 资产信息表
		List<UserContacts> conlsst = userApply.getUserContacts();// 联系人
		if (userApply == null || conlsst == null || assetInfo == null || occupationalInfo == null) {
			return new Json(false, "参数对象为空，请检查!");
		}
		/**
		 * 产品表字段处理
		 */
		SysProduct susPro = sysProductServer.findById(userApply.getProductId());
		userApply.setProductRate(susPro.getMonthRate());// 月利率
		userApply.setProductFeeRate(susPro.getYearRate()); // 年利率
		userApply.setSuggestRate(susPro.getRealMonthRate());// 实际利率
		// userApply.setProductPay(susPro.getPayType());//申请产品还款方式ID
		userApply.setProductPayVal(susPro.getPayTypeVal());// 申请产品还款方式值
		Integer validId = userApply.getValidMailAddr();// 邮寄地址ID
		if (validId == null) {
			return new Json(false, "邮寄地址为空");
		}
		switch (validId) {
		case 1:// 1:同户口地址
			userApply.setValidMailAddrTxt(userApply.getRresidenceAddress());
			break;
		case 2:// 2:同居住地址
			userApply.setValidMailAddrTxt(userApply.getHouseAddress());
			break;
		case 3:// 3:同单位地址
			userApply.setValidMailAddrTxt(occupationalInfo.getCompanyAddress());
			break;
		case 4:// 4:同房产地址
			userApply.setValidMailAddrTxt(assetInfo.getPropertyAddress());
			break;
		}
		int uApply = userApplyMapper.updateByPrimaryKeySelective(userApply);// 申请表
		int asset = assetInfoMapper.updateByPrimaryKeySelective(assetInfo);// 资产信息表
		int occ = occupationalInfoMapper.updateByPrimaryKeySelective(occupationalInfo);
		userContactsMapper.deleteByApplyId(userApply.getApplyId());// 删除联系人仅删除线下
		int uCon = 0;// 新增联系人
		for (UserContacts con : conlsst) {
			String contactId2 = PrimaryKeyUtil.getPrimaryKey();// 联系人ID
			Integer reId = con.getRelationship();// 关系radio处理
			Integer isKnow = con.getIsKnow();
			con.setIsFillIn(1);
			con.setIsFillInVal("是");
			if (isKnow == null || reId == null) {
				return new Json(false, "联系人信息异常!");
			}
			switch (reId) {
			case 1:// 1:配偶
				con.setRelationshipVal("配偶");
				break;
			case 2:// 2:亲属
				con.setRelationshipVal("亲属");
				break;
			case 3:// 3:同事
				con.setRelationshipVal("同事");
				break;
			case 4:// 4:其他
				con.setRelationshipVal("其他");
				break;
			}
			switch (isKnow) {
			case 0:
				con.setIsKnowVal("否");
				break;
			case 1:
				con.setIsKnowVal("是");
				break;
			}
			con.setContId(contactId2);
			con.setApplyId(userApply.getApplyId());
			con.setName(con.getName());
			con.setAddDate(currentTime);
			uCon = userContactsMapper.insertSelective(con);
		}
		if (uApply > 0 && asset > 0 && occ > 0 && uCon > 0) {
			return new Json(true, "修改成功");
		}
		return new Json(false, "修改失败");
	}

	/**
	 * 获取申请数据信息
	 */

	@Override
	public UserApply findApplyById(String applyId) {
		UserApply apply = userApplyMapper.findApplyById(applyId);
		return apply;
	}

	/**
	 * 保存数据认证信息
	 * 
	 * @throws IOException
	 */
	@Override
	public Json saveAuthData(UserApply userApply, List<UserContacts> Contactslist, JSONArray imageUrlList, String context) throws IOException {
		String applyId = userApply.getApplyId();
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数异常！applyId is not null！");
		}

		// 启动前前验证是否已经启动了该流程实例
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(applyId).singleResult();
		if (processInstance != null) {
			return new Json(false, "该申请已经进入下一节点，不能再次提交!");
		}

		UserCis userCis = userApply.getUserCis();// 上海资信
		if (null != userCis) {
			userCis.setApplyId(applyId);
			userCisMapper.insertSelective(userCis);
		}

		UserTaobao userTaobao = userApply.getUserTaobao();// 淘宝
		if (null != userTaobao) {
			userTaobao.setApplyId(applyId);
			userTaobaoMapper.insertSelective(userTaobao);
			if (userTaobao.getAddresses().size() > 0) {
				for (UserTaoBaoAddress address : userTaobao.getAddresses()) {
					userTaoBaoAddressMapper.insertSelective(address);
				}
			}
		}

		UserChsi userChsi = userApply.getUserChsi();// 学信
		if (null != userChsi) {
			userChsi.setApplyId(applyId);
			userChsiMapper.insertSelective(userChsi);
		}

		if (imageUrlList != null && imageUrlList.size() > 0) { // 学信图像
			for (int i = 0; i < imageUrlList.size(); i++) {
				String imageUrl = imageUrlList.get(i).toString();
				Annex annex = new Annex();
				annex.setAnxId(PrimaryKeyUtil.getPrimaryKey());
				annex.setApplyId(applyId);
				// 拆分图片路径 刘栋梁 ---2018-1-23
				String PreViewHost = imageUrl.split("/HSDCFILE")[0];
				String PreView = "/HSDCFILE" + imageUrl.split("/HSDCFILE")[1];
				annex.setPreView(PreView);
				annex.setPreViewHost(PreViewHost);
				annex.setCreateDate(DateUtil.getTimestamp());
				annex.setApplyTypeId(AnnexType.ANNEX_EDUCATIONAL_CERTIFICATE.getType());
				annex.setApplyTypeVal(AnnexType.ANNEX_EDUCATIONAL_CERTIFICATE.getName());
				annexMapper.insertSelective(annex);
			}
		}

		String fkId = userApply.getCustId();
		AuthenticationUrl idCardUrl = authenticationUrlMapper.selectByFkid(fkId);
		for (int i = 0; i < 4; i++) { // 身份证明
			Annex annex = new Annex();
			annex.setAnxId(PrimaryKeyUtil.getPrimaryKey());
			annex.setApplyId(applyId);
			annex.setCreateDate(DateUtil.getTimestamp());
			annex.setPreViewHost(context);
			annex.setApplyTypeId(AnnexType.ANNEX_PROOF_IDENTITY.getType());
			annex.setApplyTypeVal(AnnexType.ANNEX_PROOF_IDENTITY.getName());
			// 头像一致，保存图像
			// String viewPath = uploadPathUtil.userUrl(); // 放到用户信息中

			switch (i) {
			case 0:// 0:证件照正面
					// String url_photogetsavepath = viewPath + "/" + id + "/" +
					// "idcard" + "/" + "url_photoget.jpg";
				annex.setPreView(idCardUrl.getUrlFrontcard());
				break;
			case 1:// 1:证件照反面
					// String url_backcardsavepath = viewPath + "/" + id + "/" +
					// "idcard" + "/" + "url_backcard.jpg";
				annex.setPreView(idCardUrl.getUrlBackcard());
				break;
			case 2:// 2:头像
					// String url_photolivingsavepath = viewPath + "/" + id +
					// "/" + "idcard" + "/" + "url_photoliving.jpg";
				annex.setPreView(idCardUrl.getUrlPhotoget());
				break;
			case 3:// 3:头像捷图
					// String url_frontcardsavepath = viewPath + "/" + id + "/"
					// + "idcard" + "/" + "url_frontcard.jpg";
				annex.setPreView(idCardUrl.getUrlPhotoliving());
				break;
			}
			// 判断图片是否为空
			if (DownloadHelper.isImagesTrue(annex.getPreViewHost() + annex.getPreView())) {
				annexMapper.insertSelective(annex);
			}

		}

		// 收入图片上传 2017-01-31 补充
		List<Income> incomeList = incomeMapper.selectAllByFkid(fkId);
		for (Income Income : incomeList) {
			Annex annex = new Annex();
			annex.setAnxId(PrimaryKeyUtil.getPrimaryKey());
			annex.setApplyId(applyId);
			annex.setCreateDate(DateUtil.getTimestamp());
			annex.setPreViewHost(context);
			annex.setApplyTypeId(AnnexType.ANNEX_INCOME_CERTIFICATE.getType());
			annex.setApplyTypeVal(AnnexType.ANNEX_INCOME_CERTIFICATE.getName());
			annex.setPreView(Income.getIncomepath());
			// 判断图片是否为空
			if (DownloadHelper.isImagesTrue(context + Income.getIncomepath())) {
				annexMapper.insertSelective(annex);
			}

		}

		UserPbccre userPbccrcc = userApply.getUserPbccre();// 征信
		if (null != userPbccrcc) {
			userPbccrcc.setApplyId(applyId);
			userPbccreMapper.insertSelective(userPbccrcc);
			if (userPbccrcc.getUserPbccrcHtml() != null) {
				userPbccrcHtmlMapper.insertSelective(userPbccrcc.getUserPbccrcHtml());
			}
		}
		
		PbccrcChartView pbccrcChartView = userApply.getPbccrcChartView();// 征信可视化
		if (null != pbccrcChartView) {
			pbccrcChartViewMapper.insertSelective(pbccrcChartView);
		}
		
		
		UserHouseFund userHouseFund = userApply.getUserHouseFund();// 公积金
		if (null != userHouseFund) {
			userHouseFund.setApplyId(applyId);
			userHouseFundMapper.insertSelective(userHouseFund);
		}

		UserSocialsecurity userSocialsecurity = userApply.getUserSocialsecurity();// 社保
		if (null != userSocialsecurity) {
			userSocialsecurity.setApplyId(applyId);
			userSocialsecurityMapper.insertSelective(userSocialsecurity);
		}

		List<CreditCard> creditCards = userApply.getCreditCards();// 银行卡账单
		if (null != creditCards && creditCards.size() > 0) {
			for (CreditCard cb : creditCards) {
				cb.setApplyId(applyId);
				creditCardMapper.insertSelective(cb);
				List<CreditCardBills> creditCardBillsList = cb.getCardBills();
				for (CreditCardBills cbb : creditCardBillsList) {
					creditCardBillsMapper.insertSelective(cbb);
					List<CreditCardBillsInfo> CreditCardBillsInfoList = cbb.getCardBillsInfos();
					for (CreditCardBillsInfo cbbb : CreditCardBillsInfoList) {
						creditCardBillsInfoMapper.insertSelective(cbbb);
					}
				}

			}
		}

		UserAllAuthInfo UserAllAuthInfo = userApply.getUserAllAuthInfo();
		if (null != UserAllAuthInfo) {
			userAllAuthInfoMapper.insertSelective(UserAllAuthInfo);
		}

		if (null != Contactslist && Contactslist.size() > 0) { // 手提案联系人通话详细单
			for (UserContacts cs : Contactslist) {
				cs.setApplyId(applyId);
				userContactsMapper.insertSelective(cs);
			}
		}

		List<UserContacts> userContacts = userApply.getUserContacts();// 通话详细单
		if (null != userContacts && userContacts.size() > 0) {
			for (UserContacts cs : userContacts) {
				cs.setApplyId(applyId);
				userContactsMapper.insertSelective(cs);
				List<UserCallDetail> callDetails = cs.getCallDetails();
				for (UserCallDetail cd : callDetails) {
					userCallDetailMapper.insertSelective(cd);
				}

			}
		}

		UserCallVisual userCallVisual = userApply.getUserCallVisual();
		if (null != userCallVisual) { // 通讯可视化信息添加失败
			userCallVisualMapper.insertSelective(userCallVisual);
		}

		// 同盾信息添加
		UserTongDun userTongdun = userApply.getUserTongDun();
		if (null != userTongdun) { // 同盾信息添加
			userTongDunMapper.insert(userTongdun);
		}
		
		
		AssetInfo AssetInfo = userApply.getAssetInfo(); // 资产信息 无资产信息
		if (null != AssetInfo) {
			AssetInfo.setApplyId(applyId);
			assetInfoMapper.insertSelective(AssetInfo);
		}

		OccupationalInfo userOccupationalInfo = userApply.getUserOccupationalInfo(); // 职业信息
		if (null != userOccupationalInfo) {
			userOccupationalInfo.setApplyId(applyId);
			occupationalInfoMapper.insertSelective(userOccupationalInfo);
		}

		userApplyMapper.insertSelective(userApply);

		// processInstance =
		// runtimeService.startProcessInstanceByKey("creditSaleAuditSystem",
		// applyId);// 开启流程
		// String processId = processInstance.getProcessInstanceId();

		if (userApply.getSources() == 1 && userApply.getAuditType() == 0) {
			processInstance = runtimeService.startProcessInstanceByKey("creditSaleAuditSystem", applyId);// 开启流程
			Task Qctask = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("qcCheck").singleResult();
			// TODO 跳转补充资料
			Map<String, Object> var = new HashMap<String, Object>();
			var.put("qctoadd", "0");
			taskService.complete(Qctask.getId(), var);
			String QcprocessId = Qctask.getProcessInstanceId();

			// 添加质检复核信息到业务数据库
			creatQcCheck(applyId, Qctask.getId(), QcprocessId, "app提交", 4, "", ""); // TODO
			updateApplyTable(applyId, Qctask.getProcessInstanceId());
		} else if (userApply.getSources() == 1 && userApply.getAuditType() == 1) {
			processInstance = runtimeService.startProcessInstanceByKey("creditSaleAuditSystem", applyId);// 开启流程
			Task Qctask = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("qcCheck").singleResult();
			// TODO 跳转补充资料
			Map<String, Object> var = new HashMap<String, Object>();
			var.put("qctoadd", "0");
			taskService.complete(Qctask.getId(), var);
			String QcprocessId = Qctask.getProcessInstanceId();

			// 添加质检复核信息到业务数据库
			creatQcCheck(applyId, Qctask.getId(), QcprocessId, "app提交", 4, "", ""); // TODO

			// 跳初审
			Task preAudittask = taskService.createTaskQuery().processInstanceBusinessKey(applyId).taskDefinitionKey("preAudit").singleResult();
			if (null == preAudittask) {
				return new Json(false, "不能处理！任务已不在当前节点!");
			}

			// 首先判断审核数据时候已经存在 初审意见不用补充

			Map<String, Object> preAuditvar = new HashMap<String, Object>();
			preAuditvar.put("presupp", 0);
			taskService.complete(preAudittask.getId(), preAuditvar);
			updateApplyTable(applyId, preAudittask.getProcessInstanceId());

		}

		return new Json(true, "保存成功!");
	}

	private Json creatQcCheck(String applyId, String taskId, String processId, String remarks, int handleIndex, String empId, String empName) {
		QcCheck qc = new QcCheck();
		qc.setApplyId(applyId);
		qc.setQcId(PrimaryKeyUtil.getPrimaryKey());
		qc.setQcDate(new Date());
		qc.setQcEmp(empId);
		qc.setQcEmpName(empName);
		qc.setProcInstId(processId);
		qc.setTaskId(taskId);
		qc.setHandleOpinion((short) handleIndex);
		qc.setHandleOpinionVal("app通过");
		qc.setRemarks(remarks);
		int count = qcCheckMapper.insertSelective(qc);
		if (count > 0) {
			return new Json(true, "添加质检复核意见成功!");
		} else {
			return new Json(false, "添加质检复核意见失败！");
		}
	}

	private void updateApplyTable(String applyId, String processId) {
		// 修改申请主表状态
		List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(applyId).processInstanceId(processId).list();
		String precessStatus = "";
		String precessStatusVal = "";
		boolean isFirst = true;
		for (Task tk : tasks) {
			if (isFirst) {
				precessStatus = tk.getTaskDefinitionKey();
				precessStatusVal = tk.getName();
				isFirst = false;
			} else {
				precessStatus = precessStatus + "," + tk.getTaskDefinitionKey();
				precessStatusVal = precessStatusVal + "," + tk.getName();
			}
		}
		userApplyMapper.updateProcessInstance(applyId, processId, precessStatus, precessStatusVal);
	}

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 查询用户已用额度
	 * 
	 * @param applyId
	 * @return
	 */
	@Override
	public Map<String, Object> selectUserHasused(String custId) {
		return userApplyMapper.selectUserHasused(custId);
	}

	/**
	 * 开启流程
	 */
	@Override
	public Json startProcess(String applyId, String submitTypeText, SessionRouter sessionRouter) {
		if (StringUtils.isBlank(applyId) || StringUtils.isBlank(submitTypeText)) {
			return new Json(false, "提交失败!参数异常!");
		}
		Integer flag = userApplyMapper.selectIsTempByApplyId(applyId);
		if (flag != null && flag == 1) {
			return new Json(false, "提交失败,该数据为草稿，请先补全数据!");
		}
		Integer anCount = annexMapper.findAnnexByApplyId(applyId);
		if (anCount < 1) {
			return new Json(false, "提交失败,该条申请为上传附件,请先上传附件!");
		}
		// 启动前前验证是否已经启动了该流程实例
		Integer suCount = userApplyMapper.isAlreadySubmit(applyId);
		Integer prCount = userApplyMapper.isNotNullProcess(applyId);
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(applyId).singleResult();
		if (processInstance != null || suCount > 0 || prCount > 0) {
			return new Json(false, "该申请已经进入下一节点，不能再次提交!");
		}
		UserApply apply = new UserApply();
		apply.setApplyId(applyId);
		apply.setSubmitDate(DateUtil.getTimestamp());// 提交时间
		apply.setSubmitEmpId(sessionRouter.getEmpId());// 提交员工
		apply.setSubmitEmpName(sessionRouter.getEmpName());
		apply.setSubmitTypeText(submitTypeText);// 内容
		int submitText = userApplyMapper.updateByPrimaryKeySelective(apply);
		processInstance = runtimeService.startProcessInstanceByKey("creditSaleAuditSystem", applyId);// 开启流程
		String processId = processInstance.getProcessInstanceId();
		// 修改主表状态
		List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(applyId).processInstanceId(processId).list();
		String precessStatus = "";
		String precessStatusVal = "";
		boolean isFirst = true;
		for (Task task : tasks) {
			if (isFirst) {
				precessStatus = task.getTaskDefinitionKey();
				precessStatusVal = task.getName();
				isFirst = false;
			} else {
				precessStatus = precessStatus + "," + task.getTaskDefinitionKey();
				precessStatusVal = precessStatusVal + "," + task.getName();
			}
		}
		int submitCount = userApplyMapper.updateProcessInstance(applyId, processId, precessStatus, precessStatusVal);
		if (submitCount > 0 && submitText > 0) {
			return new Json(true, "提交成功!");
		}
		return new Json(false, "提交提交失败!");

	}

	@Override
	public List<UserApply> findUContractById(String applyId) {
		List<UserApply> list = userApplyMapper.findUContractById(applyId);
		return list;
	}

	@Override
	public Json isCurrentNode(String applyId) {
		// 启动前前验证是否已经启动了该流程实例
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(applyId).singleResult();
		Integer suCount = userApplyMapper.isAlreadySubmit(applyId);
		Integer prCount = userApplyMapper.isNotNullProcess(applyId);
		if (processInstance != null || suCount > 0 || prCount > 0) {
			return new Json(false, "该申请已经进入下一节点，无法进行当前操作!");
		}
		return new Json(true, "ok");
	}

	@Override
	public UserApply upSelectApplyById(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			return null;
		}
		UserApply userApply = userApplyMapper.upSelectApplyById(applyId);
		return userApply;
	}

	/**
	 * 查询门店下部门
	 */
	@Override
	public List<OAStore> selectDeptByStoreId(String storeId) {
		if (StringUtils.isBlank(storeId)) {
			return null;
		}
		List<OAStore> empList = sysEmployeeMapper.findSubDeptByStoreId(storeId);
		return empList;
	}

	/**
	 * 查询部门下员工
	 */
	@Override
	public List<SysEmployee> findEmpInDept(String depId) {
		if (StringUtils.isBlank(depId)) {
			return null;
		}
		List<SysEmployee> UserList = sysEmployeeMapper.findEmpInDept(depId);
		return UserList;
	}

	/**
	 * 申请单列表 客服专员 查看自己的单子 服务经理查看所有的单子
	 */
	@Override
	public Pager<UserApply> selectAllByParam(Integer page, Integer rows, String custName, String loanId, String nodeStatus, String idNumber, String salesEmpName, String custMobile, String product, Integer source,
			SessionRouter sessionRouter) {
		String empId = sessionRouter.getEmpId();
		String storeId = sessionRouter.getStoreId();
		String deptId = sessionRouter.getDeptId();
		if (StringUtils.isBlank(empId) || StringUtils.isBlank(storeId) || StringUtils.isBlank(deptId)) {
			throw new AuditException("无法获取你的登录信息,请尝试重新登录!");
		}
		Map<String, Object> param = new HashMap<String, Object>();
		// 验证当前登录人是不是部门负责人
		String mgrId = sysEmployeeMapper.findDeptManager(deptId);
		Integer isMgr = 0;//
		if (empId.equals(mgrId)) {// 是部门的负责人
			isMgr = 1;
		}

		param.put("empId", empId);
		param.put("isMgr", isMgr);
		param.put("deptId", deptId);

		if (nodeStatus == null) {
			nodeStatus = "0";
		} else {
			param.put("nodeStatus", nodeStatus);
		}

		if (page == null || rows == null) {
			page = 1;
			rows = 30;
		}
		rows = rows > 100 ? 100 : rows;

		param.put("pageIndex", page);
		param.put("pageSize", rows);

		if (!StringUtils.isBlank(custName)) {
			param.put("custName", "%" + custName + "%");
		}

		if (!StringUtils.isBlank(idNumber)) {
			param.put("idNumber", idNumber);
		}

		if (!StringUtils.isBlank(loanId)) {
			param.put("loanId", loanId);
		}

		if (!StringUtils.isBlank(loanId)) {
			param.put("loanId", loanId);
		}

		if (!StringUtils.isBlank(salesEmpName)) {
			param.put("salesEmpName", "%" + salesEmpName + "%");
		}

		if (!StringUtils.isBlank(custMobile)) {
			param.put("custMobile", custMobile);
		}

		if (!StringUtils.isBlank(storeId)) {
			param.put("storeId", storeId);
		}
		if (StringUtils.isNotBlank(product)) {
			param.put("product", product);
		}
		if (source != null) {
			param.put("source", source);
		}

		List<UserApply> applyList = userApplyMapper.selectAllPage(param);
		int applyCount = userApplyMapper.selectApplyCount(param);
		Pager<UserApply> applyPager = new Pager<UserApply>(applyList, applyCount);
		return applyPager;
	}

	/**
	 * 2017-12-16 ygzhao 上传附件
	 * 
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@Override
	public Json createApplyUpload(String applyId, Integer applyTypeId, String applyTypeIdVal, MultipartFile multipartFile, SessionRouter session, HttpServletRequest request) throws IllegalStateException, IOException {
		if (StringUtils.isBlank(applyId) || applyTypeId == null || StringUtils.isBlank(applyTypeIdVal)) {
			return new Json(false, "参数异常");
		}
		// 启动前前验证是否已经启动了该流程实例
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(applyId).singleResult();
		if (processInstance != null) {
			return new Json(false, "该条数据已经启动流程,目前不支持上传附件!");
		}

		String viewPath = uploadPathUtil.applyUrl();// 映射前缀
		String fileName = multipartFile.getOriginalFilename();// 文件名
		String fileType = fileName.substring(fileName.lastIndexOf("."));// 后缀
		List<String> filterFileType = new ArrayList<String>();
		filterFileType.add(".jpg");
		filterFileType.add(".png");
		filterFileType.add(".gif");
		filterFileType.add(".bmp");
		if (!StringUtils.isBlank(fileType) && !filterFileType.contains(fileType)) {
			return new Json(false, "图片格式不正确");
		}
		String saveName = UUID.randomUUID().toString().replace("-", "") + fileType;// 时间戳
		Long fSize = multipartFile.getSize();// 文件大小
		String fileUrl = request.getScheme() + "://" + SystemUtils.getLocalIp() + ":" + request.getServerPort();

		File f = new File(uploadPathUtil.saveApplyPath() + File.separator + applyId);
		if (!f.exists()) {
			f.mkdirs();
		}

		// 文件保存路径
		String savePath = uploadPathUtil.saveApplyPath() + File.separator + applyId + File.separator + saveName;
		// 映射地址
		String preView = viewPath + "/" + applyId + "/" + saveName;
		// 转存文件
		multipartFile.transferTo(new File(savePath));
		String annexId = PrimaryKeyUtil.getPrimaryKey();// 申请表ID

		Timestamp currentTime = DateUtil.getTimestamp();

		Annex annex = new Annex();
		annex.setAnxId(annexId);
		annex.setApplyId(applyId);
		annex.setFileName(fileName);
		annex.setSaveName(saveName);
		annex.setSavePath(savePath);
		annex.setPreView(preView);
		annex.setFileType(fileType);
		annex.setPreViewHost(fileUrl);
		annex.setCreateDate(currentTime);
		annex.setEmpId(session.getEmpId());
		annex.setApplyTypeId(applyTypeId);
		annex.setApplyTypeVal(applyTypeIdVal);
		annex.setFileSize(fSize);
		int aCount = annexMapper.insertSelective(annex);
		if (aCount > 0) {
			JSONObject obj = new JSONObject();
			obj.put("preView", preView);
			obj.put("anxId", annexId);
			return new Json(true, "上传成功", obj);
		}
		return new Json(false, "上传失败");
	}

	/**
	 * 提交回显，合同loanId共用
	 */
	@Override
	public UserApply submitFindById(String applyId) {
		if (StringUtils.isBlank(applyId)) {
			return null;
		}
		return userApplyMapper.submitFindById(applyId);
	}

	/**
	 * 作者：刘栋梁 <br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： app查询app进件
	 * 
	 * @param custId
	 *            客户id
	 * @return
	 */
	@Override
	public List<UserApply> selectAppAllData(String custId) {
		return userApplyMapper.selectAppAllData(custId);
	}

	/**
	 * 作者：刘栋梁 <br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： app查询门店进件
	 * 
	 * @param custId
	 *            客户id
	 * @return
	 */
	@Override
	public List<UserApply> selectAppAllCreData(String custId) {
		return userApplyMapper.selectAppAllCreData(custId);
	}

	/**
	 * 作者：刘栋梁 <br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： app查询待签约的记录
	 * 
	 * @param custId
	 *            客户id
	 * @return
	 */
	@Override
	public List<UserApply> selectAppSignData(String custId) {
		return userApplyMapper.selectAppSignData(custId);
	}

	/**
	 * 
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： 流程信息
	 * 
	 * @param applyId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectActrivity(Map<String, Object> map) {
		return userApplyMapper.selectActrivity(map);
	}

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年9月29日 <br>
	 * 描述：人脸识别，签约确认接口
	 * 
	 * @param applyId
	 * @return
	 */
	@Override
	public int updateAppSignconfirm(UserApply record) {
		return userApplyMapper.updateAppSignconfirm(record);
	}

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年9月22日 <br>
	 * 描述： 查询客户还款计划的列表
	 * 
	 * @param applyId
	 * @return
	 */
	@Override
	public List<UserApply> selectAppLoanData(String custId) {
		return userApplyMapper.selectAppLoanData(custId);
	}

	/**
	 * 保存草稿
	 */
	@Override
	public Json saveTempApply(UserApply userApply, SessionRouter sessionRouter,HttpServletRequest request) {
		/**
		 * 生成主键
		 */
		String applyId = PrimaryKeyUtil.getPrimaryKey();// 申请表ID
		String nameAuthId = null;// 客户主表ID
		String assetId = PrimaryKeyUtil.getPrimaryKey();// 资产表ID
		String occupationalId = PrimaryKeyUtil.getPrimaryKey();// 职业表ID
		Timestamp currentTime = DateUtil.getTimestamp();
		/**
		 * 生成客户唯一编号
		 */
		String loanId = serialnumberServer.createNumber("2", sessionRouter.getDeptNo());
		userApply.setLoanId(loanId);
		/**
		 * 获取各参数对象
		 */
		List<UserContacts> afreshContacterInfo = userApply.getUserContacts();// 联系人信息
		AssetInfo assetInfo = userApply.getAssetInfo();// 资产信息
		OccupationalInfo occupationalInfo = userApply.getUserOccupationalInfo();// 职业信息表

		/**
		 * 根据身份证号查找主表看是否实名认证
		 */
		String acceptPaperId = userApply.getIdNumber();// 接收-身份证号
		if (StringUtils.isBlank(acceptPaperId)) {
			return new Json(false, "身份证号不能为空!");
		}
		/**
		 * 判断客户类型
		 */
		List<String> procStatusList = userApplyMapper.findByIdnumber(acceptPaperId);//所有流程单子
		if(procStatusList!=null&&procStatusList.size()>0){
			boolean isCycle = false;//是否循环贷
			for (String proc : procStatusList) {
				if(StringUtils.isNotBlank(proc)&&"success".equals(proc)){
					isCycle = true;
					break;
				}
			}
			if(isCycle){
				userApply.setCustTypeId(2);
				userApply.setCustTypeVal("循环贷");
			}else{
				userApply.setCustTypeId(1);
				userApply.setCustTypeVal("老客户");
			}
		}else{
			userApply.setCustTypeId(0);
			userApply.setCustTypeVal("新客户");
		}
		userApply.setCreateEmployee(sessionRouter.getEmpId());// 创建人id
		userApply.setCreateEmployeeVal(sessionRouter.getEmpName());
		userApply.setLastUpdateEmpId(sessionRouter.getEmpId());// 修改人id
		userApply.setLastUpdateEmpName(sessionRouter.getEmpName());
		userApply.setStoreId(sessionRouter.getStoreId());// 门店id
		SysDepartment sysDepart = sysDepartmentMapper.selectByPrimaryKey(sessionRouter.getStoreId());
		userApply.setApplyAddress(sysDepart.getDeptWorkAddr());//申请地址
		userApply.setIpAddress(SystemUtils.getIpAddr(request));//申请ip
		userApply.setIsTemp(1);// 草稿标志
		userApply.setIsTempVal("是");// 草稿结果
		userApply.setStoreName(sessionRouter.getStoreName());
		NameAuthentication nameAuth = nameAuthenticationMapper.selectByPaperId(acceptPaperId);
		// 已实名存id,否则存null,等栋梁后期补申请、联系人主表字段;
		if (nameAuth != null && nameAuth.getIsauthentication() != null && nameAuth.getIsauthentication().equals("1")) {
			nameAuthId = nameAuth.getId();
			userApply.setCustName(nameAuth.getCustname());
		}
		String salesDept = userApply.getSalesEmpDeptId();//销售所在部门
		String serviceDept = sessionRouter.getDeptId();//客服所在部门
		String salesName = userApply.getSalesEmpName();//销售姓名
		if (StringUtils.isNotBlank(salesDept)
				&& StringUtils.isNotBlank(serviceDept)&&StringUtils.isNotBlank(salesName)
				&& serviceDept.equals(salesDept)) {//如果部门一致则销售就是客服
			userApply.setSalesEmpName(salesName+"-客服");

		}
		/**
		 * 申请表处理
		 */
		int applyCount = 0;
		if (userApply != null) {
			/**
			 * 产品表字段处理
			 */
			SysProduct susPro = sysProductServer.findById(userApply.getProductId());
			if (susPro != null) {
				userApply.setProductRate(susPro.getMonthRate());// 月利率
				userApply.setProductFeeRate(susPro.getYearRate()); // 年利率
				userApply.setSuggestRate(susPro.getRealMonthRate());// 实际利率
				userApply.setProductPay(susPro.getPayType());// 申请产品还款方式ID
				userApply.setProductPayVal(susPro.getPayTypeVal());// 申请产品还款方式值
			}
			Integer validId = userApply.getValidMailAddr();// 邮寄地址ID
			if (validId != null) {
				switch (validId) {
				case 1:// 1:同户口地址
					userApply.setValidMailAddrTxt(userApply.getRresidenceAddress());
					break;
				case 2:// 2:同居住地址
					userApply.setValidMailAddrTxt(userApply.getHouseAddress());
					break;
				case 3:// 3:同单位地址
					userApply.setValidMailAddrTxt(occupationalInfo.getCompanyAddress());
					break;
				case 4:// 4:同房产地址
					userApply.setValidMailAddrTxt(assetInfo.getPropertyAddress());
					break;
				}
			}
			userApply.setApplyId(applyId);
			userApply.setCustId(nameAuthId);
			userApply.setDelStatu(0);// 删除状态
			userApply.setIdType(0);
			userApply.setIdTypeVal("身份证");
			/**
			 * app需要补字段
			 */
			userApply.setIsApp(0);
			userApply.setIsAppVal("线下");
			userApply.setAuditType(0);// 审核状态
			userApply.setAgreedProduct("0");// 同意产品名称
			userApply.setAgreeProductId("0");// 同意产品id
			userApply.setAgreePeriod(0);// 同意期数
			userApply.setAgreedProductAlias("0");// 同意产品别名
			userApply.setAgreedAmount(new BigDecimal(0));// 同意额度
			userApply.setApplyDate(currentTime);
			userApply.setLastUpdateTime(currentTime);
			applyCount = userApplyMapper.insertSelective(userApply);
		}
		/**
		 * 联系人表处理
		 */
		int contactCount = 0;
		if (afreshContacterInfo != null) {
			for (UserContacts contact : afreshContacterInfo) {
				String contactId2 = PrimaryKeyUtil.getPrimaryKey();// 联系人ID;
				Integer reId = contact.getRelationship();
				Integer isKnow = contact.getIsKnow();
				contact.setIsFillIn(1);
				contact.setIsFillInVal("是");
				if (isKnow != null && reId != null) {
					switch (reId) {
					case 1:// 1:配偶
						contact.setRelationshipVal("配偶");
						break;
					case 2:// 2:亲属
						contact.setRelationshipVal("亲属");
						break;
					case 3:// 3:同事
						contact.setRelationshipVal("同事");
						break;
					case 4:// 4:其他
						contact.setRelationshipVal("其他");
						break;
					}
					switch (isKnow) {
					case 0:
						contact.setIsKnowVal("否");
						break;
					case 1:
						contact.setIsKnowVal("是");
						break;
					}
				}
				contact.setContId(contactId2);
				contact.setAddDate(currentTime);
				contact.setName(contact.getName());
				contact.setApplyId(applyId);
				contactCount = userContactsMapper.insertSelective(contact);
			}
		}
		/**
		 * 资产信息表处理
		 */
		int assetCount = 0;
		assetInfo.setAsinfoPkId(assetId);
		assetInfo.setApplyId(applyId);
		assetInfo.setCreateTime(currentTime);
		assetCount = assetInfoMapper.insertSelective(assetInfo);

		/**
		 * 职业信息表处理
		 */
		int occCount = 0;
		occupationalInfo.setOcinfoPkId(occupationalId);
		occupationalInfo.setApplyId(applyId);
		occupationalInfo.setCreateTime(currentTime);
		occCount = occupationalInfoMapper.insertSelective(occupationalInfo);
		if (applyCount > 0 && contactCount > 0 && assetCount > 0 && occCount > 0) {
			return new Json(true, "保存草稿成功!");
		}
		return new Json(false, "保存草稿失败!");
	}

	@Override
	public Json isTemp(String applyId) {
		Integer flag = userApplyMapper.selectIsTempByApplyId(applyId);
		if (flag != null && flag == 1) {
			return new Json(false, "提交失败,该数据为草稿，请先补全数据!");
		}
		return new Json(true, "校验成功");
	}

	@Override
	public Json isAlreadyUpload(String applyId) {
		Integer anCount = annexMapper.findAnnexByApplyId(applyId);
		if (anCount < 1) {
			return new Json(false, "提交失败,该条申请未上传附件,请先上传附件!");
		}
		return new Json(true, "拥有附件");
	}

	/**
	 * 进件申请查询
	 * 
	 * @param applyId
	 * @return
	 */
	@Override
	public Pager<UserApply> selectApplyPage(Integer page, Integer rows, String custName, String loanId, String idNumber, String custMobile, String orgId, SessionRouter sessionRouter) {
		if (null == sessionRouter) {
			throw new AuditException("登录超时!");
		}

		Map<String, Object> param = new HashMap<String, Object>();

		if (page == null || rows == null) {
			page = 1;
			rows = 30;
		}
		rows = rows > 100 ? 100 : rows;

		param.put("pageIndex", page);
		param.put("pageSize", rows);

		if (!StringUtils.isBlank(custName)) {
			param.put("custName", "%" + custName + "%");
		}

		if (!StringUtils.isBlank(idNumber)) {
			param.put("idNumber", idNumber);
		}

		if (!StringUtils.isBlank(loanId)) {
			param.put("loanId", loanId);
		}

		if (!StringUtils.isBlank(custMobile)) {
			param.put("custMobile", custMobile);
		}

		if (!StringUtils.isBlank(orgId)) {
			param.put("orgId", orgId);
		}

		// 登录人的ID
		String empId = sessionRouter.getEmpId();
		param.put("empId", empId);
		// 登录人所在的部门
		String deptId = sessionRouter.getDeptId();
		param.put("deptId", deptId);
		// 登录人所在的门店
		String storeId = sessionRouter.getStoreId();
		param.put("storeId", storeId);

		Integer permissions = checkPermissions(empId, deptId, storeId);
		param.put("permissions", permissions);

		List<UserApply> applyList = userApplyMapper.selectApplyPage(param);
		int applyCount = userApplyMapper.selectApplyinfoCount(param);
		Pager<UserApply> applyPager = new Pager<UserApply>(applyList, applyCount);
		return applyPager;
	}

	private Integer checkPermissions(String empId, String deptId, String storeId) {
		Integer result = -1;
		// 门店ID不为空 并且不为-1的情况 登录人是门店级门店一下的人员
		if (StringUtils.isNotBlank(storeId) && !"-1".equals(storeId)) {
			if (deptId.equals(storeId)) {// 门店经理
				result = 3;
			} else {
				// 验证当前登录人是不是部门负责人
				String mgrId = sysEmployeeMapper.findDeptManager(deptId);
				if (mgrId.equals(empId)) {// 是部门负责人
					result = 2;// 团队经理
				} else {
					result = 1;// 销售
				}
			}
		} else {// 门店以上的人员
			result = 4;
		}
		return result;
	}

	@Override
	public Json loginCompareCreate(String applyId, SessionRouter sessionRouter) {
		if (StringUtils.isBlank(applyId)) {
			return new Json(false, "参数异常");
		}
		UserApply userApply = userApplyMapper.loginCompareCreate(applyId);
		String agoName = userApply.getCreateEmployee();
		String newName = sessionRouter.getEmpId();
		if (userApply != null && StringUtils.isBlank(userApply.getCreateEmployee())) {
			userApply.setCreateEmployee(sessionRouter.getEmpId());
			userApply.setCreateEmployeeVal(sessionRouter.getEmpName());
			userApplyMapper.updateByPrimaryKeySelective(userApply);
			return new Json(true, "补充录单人成功！");
		} else {
			if (userApply != null && !StringUtils.isBlank(agoName) && !StringUtils.isBlank(newName) && agoName.equals(newName)) {
				return new Json(true, "登录人与录单人一致");
			}
			// 验证当前登录人是不是部门负责人
			String mgrId = sysEmployeeMapper.findDeptManager(sessionRouter.getDeptId());
			if (newName.equals(mgrId)) {// 是部门的负责人
				return new Json(true, "当前登录人为部门负责人");
			}
		}
		return new Json(false, "登录人与录单人不一致，无法进行操作！");
	}

	/**
	 * 银行卡信息变更修改所有未结清的单子
	 */
	@Override
	public List<UserApply> selectChangeAcc(String custId) {
		return userApplyMapper.selectChangeAcc(custId);
	}

	@Override
	public UserApply selectByAply(String applyId) {
		return userApplyMapper.selectByAply(applyId);
	}

	@Override
	public Json validIdNumber(String idNumber, int flag, String applyId) {
		int black = blackinfomapper.findByIdNumber(idNumber);// 黑名单校验
		if (black > 0) {
			return new Json(false, "客户身份证" + idNumber + "出现在黑名单,请联系客服核对");
		}

		Integer refuse = userApplyMapper.checkDeclineDate(idNumber);// 拒贷校验
		if (refuse != null) {
			Map<String, Object> finalSubmit = userApplyMapper.selectFinalSubmit(idNumber);// 查询终审提交表
			if (finalSubmit.get("REJECT_TYPE") != null) {
				Integer rejectType = Integer.parseInt(finalSubmit.get("REJECT_TYPE").toString());// 拒贷原因
				switch (rejectType) {
				case 0:// 虚假信息365
					if (refuse != null && refuse <= 365) {
						return new Json(false, "尊敬的客户，由于您尚处在封闭期间，暂时无法提交本次申请。");
					}
					break;
				case 1:// 信息异常180
					if (refuse != null && refuse <= 180) {
						return new Json(false, "尊敬的客户，由于您尚处在封闭期间，暂时无法提交本次申请。");
					}
					break;
				default:// 其他30
					if (refuse != null && refuse <= 30) {
						return new Json(false, "尊敬的客户，由于您尚处在封闭期间，暂时无法提交本次申请。");
					}
					break;
				}
			}
		}

		List<UserApply> applyIng = userApplyMapper.selectIsCredit(idNumber);// 申请中校验
		if (applyIng.size() > 0 && flag == 0) {// 添加方法校验
			return new Json(false, "尊敬的客户，您已提交的申请正在审核当中，请您耐心等待勿重复提交。");
		} else {
			boolean isRepeat = false;// 申请表中除了自己有重复单子
			if (applyIng != null && applyId != null) {
				for (UserApply userApply : applyIng) {
					if (!applyId.equals(userApply.getApplyId())) {// 不是之前单子
						isRepeat = true;
						break;
					}
				}
			}
			if (isRepeat) {
				return new Json(false, "尊敬的客户，您已提交的申请正在审核当中，请您耐心等待勿重复提交。");
			}
		}

		List<UserApply> running = userApplyMapper.selectStoreCreditIsFinish(idNumber);// 放款成功且未结清不让贷
		for (UserApply UserApply : running) {
			SysContract contract = UserApply.getCon();
			// 合同状态 0 未放款 1 放款 2 结清 3 未结清 4 提前结清 5 逾期
			if (contract != null && contract.getConStatus() != 2 && contract.getConStatus() != 4) {
				return new Json(false, "尊敬的客户，由于您目前仍有一笔贷款尚未结清，暂时无法提交本次申请。");
			}
		}
		return new Json(true, "此客户是正常客户！");
	}

	/**
	 * 门店单子补充用户id
	 */
	public int selectInfoAdd(UserApply record) {
		return userApplyMapper.selectInfoAdd(record);
	}

	@Override
	public String findCreateEmp(String applyId) {
		return userApplyMapper.findCreateEmp(applyId);
	}

	@Override
	public Json findByIdNumber(String idNumber) {
		int black = blackinfomapper.findByIdNumber(idNumber);// 黑名单校验
		if (black > 0) {
			return new Json(false, "客户身份证" + idNumber + "出现在黑名单,请联系客服核对");
		}
		return new Json(true, "黑名单校验成功");
	}

	@Override
	public Json checkDeclineDate(String idNumber) {
		Integer refuse = userApplyMapper.checkDeclineDate(idNumber);// 拒贷校验
		if (refuse != null) {
			Map<String, Object> finalSubmit = userApplyMapper.selectFinalSubmit(idNumber);// 查询终审提交表
			if (finalSubmit.get("REJECT_TYPE") != null) {
				Integer rejectType = Integer.parseInt(finalSubmit.get("REJECT_TYPE").toString());// 拒贷原因
				switch (rejectType) {
				case 0:// 虚假信息365
					if (refuse != null && refuse <= 365) {
						return new Json(false, "尊敬的客户，由于您尚处在封闭期间，暂时无法提交本次申请。");
					}
					break;
				case 1:// 信息异常180
					if (refuse != null && refuse <= 180) {
						return new Json(false, "尊敬的客户，由于您尚处在封闭期间，暂时无法提交本次申请。");
					}
					break;
				default:// 其他30
					if (refuse != null && refuse <= 30) {
						return new Json(false, "尊敬的客户，由于您尚处在封闭期间，暂时无法提交本次申请。");
					}
					break;
				}
			}
		}
		return new Json(true, "封闭期校验成功");
	}

	@Override
	public Json selectIsCredit(String idNumber, int flag, String applyId) {
		List<UserApply> applyIng = userApplyMapper.selectIsCredit(idNumber);// 申请中校验
		if (applyIng.size() > 0 && flag == 0) {// 添加方法校验
			return new Json(false, "尊敬的客户，您已提交的申请正在审核当中，请您耐心等待勿重复提交。");
		} else {
			boolean isRepeat = false;// 申请表中除了自己有重复单子
			if (applyIng != null && applyId != null) {
				for (UserApply userApply : applyIng) {
					if (!applyId.equals(userApply.getApplyId())) {// 不是之前单子
						isRepeat = true;
						break;
					}
				}
			}
			if (isRepeat) {
				return new Json(false, "尊敬的客户，您已提交的申请正在审核当中，请您耐心等待勿重复提交。");
			}
		}
		return new Json(true, "申请中校验成功");
	}

	@Override
	public Json selectStoreCreditIsFinish(String idNumber) {
		List<UserApply> running = userApplyMapper.selectStoreCreditIsFinish(idNumber);// 放款成功且未结清不让贷
		for (UserApply UserApply : running) {
			SysContract contract = UserApply.getCon();
			// 合同状态 0 未放款 1 放款 2 结清 3 未结清 4 提前结清 5 逾期
			if (contract != null && contract.getConStatus() != 2 && contract.getConStatus() != 4) {
				return new Json(false, "尊敬的客户，由于您目前仍有一笔贷款尚未结清，暂时无法提交本次申请。");
			}
		}
		return new Json(true, "未结清校验成功");
	}

	@Override
	public String findIdNumber(String applyId) {

		return userApplyMapper.findIdNumberByApplyId(applyId);
	}

	@Override
	public Json refuseApply(String applyId, String empId, String empName) {
		Timestamp currentTime = DateUtil.getTimestamp();
		UserApply apply = new UserApply();
		apply.setApplyId(applyId);
		apply.setLastUpdateEmpId(empId);
		apply.setLastUpdateEmpName(empName);
		apply.setLastUpdateTime(currentTime);
		apply.setProcStatus("reject");
		apply.setProcStatusVal("拒贷");
		int count = userApplyMapper.updateByPrimaryKeySelective(apply);
		if (count > 0) {
			return new Json(true, "拒贷成功！");
		}
		return new Json(false, "拒贷失败！");
	}

	@Override
	public Json multiUpload(MultipartFile[] file, String applyId, Integer applyTypeId, String applyTypeIdVal, SessionRouter session, HttpServletRequest request) {
		if (StringUtils.isBlank(applyId) || applyTypeId == null || StringUtils.isBlank(applyTypeIdVal)) {
			return new Json(false, "参数异常");
		}
		// 启动前前验证是否已经启动了该流程实例
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(applyId).singleResult();
		if (processInstance != null) {
			return new Json(false, "该条数据已经启动流程,目前不支持上传附件!");
		}

		JSONArray resultArray = new JSONArray();// 返回json结果集
		for (MultipartFile multipartFile : file) {
			String viewPath = uploadPathUtil.applyUrl();// 映射前缀
			String fileName = multipartFile.getOriginalFilename();// 文件名
			String fileType = fileName.substring(fileName.lastIndexOf("."));// 后缀
			List<String> filterFileType = new ArrayList<String>();
			filterFileType.add(".jpg");
			filterFileType.add(".png");
			filterFileType.add(".gif");
			filterFileType.add(".bmp");
			if (!StringUtils.isBlank(fileType) && !filterFileType.contains(fileType)) {
				return new Json(false, "图片格式不正确");
			}
			String saveName = UUID.randomUUID().toString().replace("-", "") + fileType;// 时间戳
			Long fSize = multipartFile.getSize();// 文件大小
			String fileUrl = request.getScheme() + "://" + SystemUtils.getLocalIp() + ":" + request.getServerPort();

			File f = new File(uploadPathUtil.saveApplyPath() + File.separator + applyId);
			if (!f.exists()) {
				f.mkdirs();
			}

			// 文件保存路径
			String savePath = uploadPathUtil.saveApplyPath() + File.separator + applyId + File.separator + saveName;
			// 映射地址
			String preView = viewPath + "/" + applyId + "/" + saveName;
			try {
				// 转存文件
				multipartFile.transferTo(new File(savePath));
				String annexId = PrimaryKeyUtil.getPrimaryKey();// 申请表ID
				Timestamp currentTime = DateUtil.getTimestamp();
				Annex annex = new Annex();
				annex.setAnxId(annexId);
				annex.setApplyId(applyId);
				annex.setFileName(fileName);
				annex.setSaveName(saveName);
				annex.setSavePath(savePath);
				annex.setPreView(preView);
				annex.setFileType(fileType);
				annex.setPreViewHost(fileUrl);
				annex.setCreateDate(currentTime);
				annex.setEmpId(session.getEmpId());
				annex.setApplyTypeId(applyTypeId);
				annex.setApplyTypeVal(applyTypeIdVal);
				annex.setFileSize(fSize);
				int aCount = annexMapper.insertSelective(annex);
				if (aCount > 0) {
					JSONObject obj = new JSONObject();
					obj.put("preView", preView);
					obj.put("anxId", annexId);
					obj.put("saveName", saveName);
					obj.put("oldName", fileName);
					resultArray.add(obj);
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
				log.error("【门店申请 文件上传异常 批量上传】", e);
			} catch (IOException e) {
				e.printStackTrace();
				log.error("【门店申请 文件上传异常 批量上传】", e);
			}
		}
		return new Json(true, "保存成功！", resultArray);
	}

}
