/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.SysEmployee;
import com.hoomsun.core.model.vo.OAStore;
import com.hoomsun.core.model.vo.SessionRouter;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.UserContacts;

/**
 * 作者：yg.zhao <br>
 * 创建时间：2017年11月22日 <br>
 * 描述：申请表server
 */
public interface UserApplyServerI {
	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月22日 <br>
	 * 描述：查询所有(分页)
	 * @param source 
	 * @param product 
	 * 
	 * @param string
	 * @param precessId
	 */
	Pager<UserApply> selectAllByParam(Integer page, Integer rows, String custName, String loanId, String nodeStatus, String idNumber, String salesEmpName, String custMobile, String product, Integer source, SessionRouter sessionRouter);

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月16日 <br>
	 * 描述：新增门店申请表所有数据
	 * 
	 * @param session
	 * @param request 
	 * @throws ParseException 
	 * 
	 * @throws UnsupportedEncodingException
	 */
	Json addApplyTable(UserApply userApply, SessionRouter session, HttpServletRequest request) throws ParseException;

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月16日 <br>
	 * 描述：删除门店申请表所有数据
	 * 
	 * @param empName
	 * @param empId
	 */
	Json deleteApplyTable(String applyId, String empId, String empName);

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月21日 <br>
	 * 描述：修改门店申请表页面做回显
	 * 
	 * @throws UnsupportedEncodingException
	 */
	UserApply selectApplyTableByAppId(String applyId) throws UnsupportedEncodingException;

	/**
	 * 作者：ygzhao<br>
	 * 创建时间：2017年11月28日 <br>
	 * 描述： 修改申请单
	 * 
	 * @param sessionRouter
	 * 
	 * @param applyId
	 * @return
	 * @throws ParseException 
	 * @throws UnsupportedEncodingException
	 */
	Json updateApplyTableByApplyId(UserApply userApply, SessionRouter sessionRouter) throws ParseException;

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年11月29日 <br>
	 * 描述： 启动流程
	 * 
	 * @param applyId
	 * @param sessionRouter
	 * @param submitText
	 * @return
	 */
	Json startProcess(String applyId, String submitTypeText, SessionRouter sessionRouter);

	/**
	 * 作者：ygzhao<br>
	 * 创建时间：2017年11月30日 <br>
	 * 描述： 上传申请单附件
	 * 
	 * @param applyId
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	Json createApplyUpload(String applyId, Integer applyTypeId, String applyTypeIdVal, MultipartFile multipartFile, SessionRouter session, HttpServletRequest request) throws IllegalStateException, IOException;

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月1日 <br>
	 * 描述： 根据组件查询申请数据 申请表信息
	 * 
	 * @param applyId
	 *            申请ID
	 * @return
	 */
	public UserApply findApplyById(String applyId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月7日 <br>
	 * 描述： 保存认证数据信息
	 * 
	 * @param userApply
	 * @return
	 */
	public Json saveAuthData (UserApply userApply, List<UserContacts> Contactslist,JSONArray imageUrlList,String context) throws IOException;

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月9日 <br>
	 * 描述： 查询用户已用额度
	 * 
	 * @param applyId
	 * @return
	 */
	Map<String, Object> selectUserHasused(String custId);

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月12日 <br>
	 * 描述： 放款信息查询
	 * 
	 * @param applyId
	 * @return
	 */
	List<UserApply> findUContractById(String applyId);

	/**
	 * 判断是否启动流程
	 * 
	 * @param applyId
	 * @return
	 */
	Json isCurrentNode(String applyId);

	/**
	 * 上传回显提醒字段
	 * 
	 * @param applyId
	 * @return
	 */
	UserApply upSelectApplyById(String applyId);

	/**
	 * 根据门店Id获取所有部门
	 * 
	 * @param storeId
	 * @return
	 */
	List<OAStore> selectDeptByStoreId(String storeId);

	/**
	 * 根据部门Id获取所有员工
	 * 
	 * @param storeId
	 * @return
	 */
	List<SysEmployee> findEmpInDept(String depId);

	/**
	 * 提交页面回显
	 * 
	 * @param storeId
	 * @return
	 */
	UserApply submitFindById(String applyId);

	/**
	 * 作者：刘栋梁 <br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： app查询app进件
	 * 
	 * @param custId
	 *            客户id
	 * @return
	 */
	List<UserApply> selectAppAllData(String custId);

	/**
	 * 作者：刘栋梁 <br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： app查询门店进件
	 * 
	 * @param custId
	 *            客户id
	 * @return
	 */
	List<UserApply> selectAppAllCreData(String custId);

	/**
	 * 作者：刘栋梁 <br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： app查询待签约的记录
	 * 
	 * @param custId
	 *            客户id
	 * @return
	 */
	List<UserApply> selectAppSignData(String custId);

	/**
	 * 
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： 流程信息
	 * 
	 * @param applyId
	 * @return
	 */
	List<Map<String, Object>> selectActrivity(Map<String, Object> map);

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年9月29日 <br>
	 * 描述：人脸识别，签约确认接口
	 * 
	 * @param applyId
	 * @return
	 */
	int updateAppSignconfirm(UserApply record);
	
	
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年9月22日 <br>
	 * 描述： 查询客户还款计划的列表    
	 * @param applyId
	 * @return
	 */
	List<UserApply> selectAppLoanData(String custId); 
	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年12月25日 <br>
	 * 描述： 保存草稿
	 * @param userApply
	 * @param session
	 * @param request 
	 * @return
	 */
	Json saveTempApply(UserApply userApply, SessionRouter session, HttpServletRequest request);

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年12月25日 <br>
	 * 描述： 判断是否为草稿
	 * @param userApply
	 * @param session
	 * @return
	 */
	Json isTemp(String applyId);

	/**
	 * 判断是否上传附件
	 * @param applyId
	 * @return
	 */
	Json isAlreadyUpload(String applyId);
	
	/**
	 * 进件查询
	 * @param applyId
	 * @return
	 */
	Pager<UserApply> selectApplyPage(Integer page, Integer rows, String custName, String loanId, String idNumber, String custMobile,String orgId,SessionRouter sessionRouter);

	/**
	 * 判断登录人与录单人是否一致
	 * @param sessionRouter 
	 */
	Json loginCompareCreate(String applyId, SessionRouter sessionRouter);
	
	
	/**
	 * 银行卡信息变更修改所有未结清的单子
	 */
	List<UserApply> selectChangeAcc(String custId);
	UserApply selectByAply(String applyId);

	/**
	 * 进件规则
	 * @param inNumber
	 * @param flag 
	 * @param applyId 
	 * @return
	 */
	Json validIdNumber(String idNumber, int flag, String applyId);
	
	/**
	 * 门店单子补充用户id
	 */
	int selectInfoAdd(UserApply record);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月24日 <br>
	 * 描述： 查询申请单的创建人
	 * @param applyId
	 * @return
	 */
	String findCreateEmp(String applyId);
	
	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2018年1月25日 <br>
	 * 描述： 查询黑名单
	 * @param applyId
	 * @return
	 */
	Json findByIdNumber(String idNumber);
	
	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2018年1月25日 <br>
	 * 描述： 封闭期校验
	 * @param applyId
	 * @return
	 */
	Json checkDeclineDate(String idNumber);	
	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2018年1月25日 <br>
	 * 描述： 申请中校验
	 * @param applyId
	 * @return
	 */
	Json selectIsCredit(String idNumber,int flag, String applyId);	
	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2018年1月25日 <br>
	 * 描述： 未结清校验
	 * @param applyId
	 * @return
	 */
	Json selectStoreCreditIsFinish(String idNumber);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月30日 <br>
	 * 描述： 查询
	 * @param applyId
	 * @return
	 */
	String findIdNumber(String applyId);

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2018年2月2日 <br>
	 * 描述： 伪拒贷
	 * @param applyId
	 * @return
	 */
	Json refuseApply(String applyId, String empId, String empName);

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2018年2月11日 <br>
	 * 描述：批量上传
	 * @param file
	 * @param applyId
	 * @param applyTypeId
	 * @param applyTypeIdVal
	 * @param session
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	Json multiUpload(MultipartFile[] file, String applyId, Integer applyTypeId,
			String applyTypeIdVal, SessionRouter session,
			HttpServletRequest request) ;	
}
