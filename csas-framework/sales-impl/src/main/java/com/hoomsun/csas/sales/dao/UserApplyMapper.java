package com.hoomsun.csas.sales.dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.vo.HistoricTaskVo;
import com.hoomsun.csas.sales.api.model.vo.NoticeMsg;
import com.hoomsun.csas.sales.api.model.vo.UserApplyVo;

public interface UserApplyMapper {
	int deleteByPrimaryKey(String applyId);

	int insert(UserApply record);

	int insertSelective(UserApply record);

	UserApply selectByPrimaryKey(String applyId);

	int updateByPrimaryKeySelective(UserApply record);

	int updateByPrimaryKey(UserApply record);

	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月11日<br>
	 * 描述：满足筛选条件的数据 分页
	 * 
	 * @param param
	 *            keys:pageIndex,pageSize,empName,comId,deptId
	 * @return
	 */
	List<UserApply> findPageData(Map<String, Object> param);

	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月11日<br>
	 * 描述：满足条件的数据量
	 * 
	 * @param param
	 *            筛选条件 keys:pageIndex,pageSize,roleName
	 * @return
	 */
	int findPageCount(Map<String, Object> param);

	/**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月11日<br>
	 * 描述：获取所有信息
	 * 
	 * @return
	 */
	List<UserApply> findAllData();

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月18日 <br>
	 * 描述： 修改流程 启动流程个申请单添加流程实例id
	 * 
	 * @param applyId
	 *            申请id
	 * @param processId
	 *            流程实例id
	 * @param precessStatus
	 *            流程状态 对应流程的节点定义的id
	 * @param precessStatusVal
	 *            流程状态 对应流程的节点名称
	 * @return
	 */
	int updateProcessInstance(@Param("applyId") String applyId,
			@Param("processId") String processId,
			@Param("precessStatus") String precessStatus,
			@Param("precessStatusVal") String precessStatusVal);

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
	 * 作者：liudongliang <br>
	 * 创建时间：2017年9月22日 <br>
	 * 描述： 查询客户要签约的列表
	 * 
	 * @param applyId
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
	 * 创建时间：2017年9月22日 <br>
	 * 描述： 查询客户还款计划的列表
	 * 
	 * @param applyId
	 * @return
	 */
	List<UserApply> selectAppLoanData(String custId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月21日 <br>
	 * 描述： 历史任务
	 * 
	 * @param applyId
	 * @return
	 */
	List<HistoricTaskVo> findHistoricTasks(@Param("applyId") String applyId,
			@Param("procInstId") String procInstId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月21日 <br>
	 * 描述： 产讯申请单的审批类型
	 * 
	 * @param applyId
	 * @return
	 */
	Integer selectAuditType(String applyId);

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年9月22日 <br>
	 * 描述： 查询用户已用额度
	 * 
	 * @param applyId
	 * @return
	 */
	Map<String, Object> selectUserHasused(String custId);

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年10月11日 <br>
	 * 描述： 查询客户已在还款中的单子 所有单子,没去处还清 和上面一样,后续添加
	 * 
	 * @param applyId
	 * @return
	 */
	List<UserApplyVo> selectAppPayLoanData(String custId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月23日 <br>
	 * 描述： 更具借款编号loanId 查询出申请ID
	 * 
	 * @param loanId
	 * @return
	 */
	String findApplyIdByLoanId(String loanId);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月27日 <br>
	 * 描述： 指定时间内的申请的信息 统计 走势
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Map<String, Object>> findApplyTrend(
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月27日 <br>
	 * 描述： 修改状态
	 * 
	 * @param applyId
	 * @param string
	 * @param string2
	 * @return
	 */
	int updateStatusByApply(@Param("applyId") String applyId,
			@Param("precessStatus") String precessStatus,
			@Param("precessStatusVal") String precessStatusVal);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月27日 <br>
	 * 描述： 更具申请ID获取申请状态
	 * 
	 * @param applyId
	 * @return
	 */
	UserApply findApplyStatusById(String applyId);

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
	 * 创建时间：2017年10月11日 <br>
	 * 描述：修改合同金额
	 * 
	 * @param applyId
	 * @return
	 */
	int updateAgreedAmount(UserApply record);

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年10月07日 <br>
	 * 描述：所有申请金额
	 * 
	 * @param applyId
	 * @return
	 */
	List<UserApply> selectUserHasusedList(String custId);

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年10月24 <br>
	 * 描述：查询线下推送数据
	 * 
	 * @param applyId
	 * @return
	 */
	Map<String, Object> findApplyToCredit(String applyId);

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年10月24 <br>
	 * 描述：查询客户id
	 * 
	 * @param applyId
	 * @return
	 */
	String selectCustIdByloanId(String loanId);

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月22日 <br>
	 * 描述：查询apply表
	 */
	List<UserApply> selectAllPage(Map<String, Object> param);

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月22日 <br>
	 * 描述：查询apply记录数
	 */
	int selectApplyCount(Map<String, Object> param);

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月27日 <br>
	 * 描述：修改回显
	 */
	UserApply selectApplyTableByAppId(String applyId);

	int deleteApplyTableByAppId(@Param("applyId") String applyId,
			@Param("empId") String empId, @Param("empName") String empName);

	UserApply findApplyById(String applyId);

	/**
	 * 查询补充资料列表
	 * 
	 * @param paramMap
	 * @return
	 */
	List<UserApply> selectSupplementApply(Map<String, Object> param);

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年11月22日 <br>
	 * 描述：查询补充资料列表记录数
	 */
	int selectSupplementCount(Map<String, Object> param);

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
	 * 上传回显字段 创建时间：2017年12月12日 <br>
	 * 
	 * @return
	 */
	UserApply upSelectApplyById(String applyId);

	/**
	 * 提交回显
	 * 
	 * @return
	 */
	UserApply submitFindById(String applyId);

	/**
	 * 判断是否为草稿
	 * 
	 * @param applyId
	 * @return
	 */
	Integer selectIsTempByApplyId(String applyId);

	/**
	 * 进件申请查询
	 * 
	 * @param applyId
	 * @return
	 */
	List<UserApply> selectApplyPage(Map<String, Object> param);

	int selectApplyinfoCount(Map<String, Object> param);

	/**
	 * 判断是否提交过
	 * 
	 * @param applyId
	 * @return
	 */
	Integer isAlreadySubmit(String applyId);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年12月29日 <br>
	 * 描述： 通过applyId获取申请的产品名称
	 * 
	 * @param applyId
	 * @return
	 */
	String selectProNameByApplyId(String applyId);

	NoticeMsg selectProAndAmountByApplyId(String applyId);

	/**
	 * 判断是否有单子在审批流程
	 * 
	 * @param idCard
	 * @return
	 */
	List<UserApply> selectIsCredit(String idCard);

	/**
	 * 判断是否有单子是门店,接着判断是否结清
	 * 
	 * @param idCard
	 * @return
	 */
	List<UserApply> selectStoreCreditIsFinish(String idCard);

	/**
	 * 判断是否有单子是app,接着判断是否逾期
	 * 
	 * @param idCard
	 * @return
	 */
	List<UserApply> selectAppCreditIsFinish(String idCard);
	
	/**
	 * 判断是否单子有据贷
	 * 
	 * @param idCard
	 * @return
	 */
	List<UserApply> selectStoreCreditIsReject(String idCard);

	/**
	 * 判断是否app单子排除拒贷/结清
	 * 
	 * @param idCard
	 * @return
	 */
	List<UserApply> selectAppIsExist(String idCard);
	
	/**
	 * 判断登录人与录单人是否一致
	 */
	UserApply loginCompareCreate(@Param("applyId") String applyId);
	
	/**
	 * 银行卡信息变更修改所有未结清的单子
	 */
	List<UserApply> selectChangeAcc(String custId);
	
	UserApply selectByAply(String applyId);
	
	/**
	 * 门店单子补充用户id
	 */
	int selectInfoAdd(UserApply record);
	
	/**
	 * 
	 * 作者：ygzhao<br>
	 * 创建时间：2018年1月5日 <br>
	 * 描述： 查询拒贷天数
	 * @param parentId
	 * @param hsoaDB
	 * @return
	 */
	Integer checkDeclineDate(String idNumber);
	/**
	 * 
	 * 作者：ygzhao<br>
	 * 创建时间：2018年1月23日 <br>
	 * 描述： 查询终审提交表
	 * @param parentId
	 * @param hsoaDB
	 * @return
	 */
	Map<String, Object> selectFinalSubmit(String idNumber);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年1月24日 <br>
	 * 描述： 查询录单人
	 * @param applyId
	 * @return
	 */
	String findCreateEmp(String applyId);

	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月30日 <br>
	 * 描述： 根据applyId获取身份证号码
	 * @param applyId
	 * @return
	 */
	String findIdNumberByApplyId(String applyId);

	/**
	 * 
	 * 作者：ygzhao <br>
	 * 创建时间：2018年2月2日 <br>
	 * 描述： 判断是否有流程状态
	 * @param applyId
	 * @return
	 */
	Integer isNotNullProcess(String applyId);
	
	
	List<UserApply>  findIdIsContract();
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2018年2月24日 <br>
	 * 描述： 查询进件渠道 
	 * @param applyId
	 * @return  0:线下PC, 1:app线上产品, 2:app线下产品
	 */
	Integer findIsApp(String applyId);
	
	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2018年2月26日 <br>
	 * 描述： 根据身份证号查询申请单  
	 * @param idNumber
	 * @return
	 */
	List<String> findByIdnumber(String idNumber);
}