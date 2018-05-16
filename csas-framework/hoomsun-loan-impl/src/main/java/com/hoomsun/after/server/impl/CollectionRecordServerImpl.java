/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.server.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.after.api.model.param.CollectionRecordReq;
import com.hoomsun.after.api.model.param.VistTaskReq;
import com.hoomsun.after.api.model.table.Allot;
import com.hoomsun.after.api.model.table.Review;
import com.hoomsun.after.api.model.vo.CollectingReceivingCallResult;
import com.hoomsun.after.api.model.vo.CollectionRecordResult;
import com.hoomsun.after.api.model.vo.CustomerCollectionRemindingResult;
import com.hoomsun.after.api.server.CollectionRecordServer;
import com.hoomsun.after.api.server.VisitTaskOprationServer;
import com.hoomsun.after.api.util.DateUtils;
import com.hoomsun.after.api.util.StringUtil;
import com.hoomsun.after.api.util.SysConfig;
import com.hoomsun.after.api.util.autoCode;
import com.hoomsun.after.api.util.enums.CustomerLoanBalEnum;
import com.hoomsun.after.api.util.excel.secode.annotation.ExportExcel;
import com.hoomsun.after.api.util.excel.secode.excelUtil.ExcelUtils;
import com.hoomsun.after.dao.CollectionRecordMapper;
import com.hoomsun.after.dao.CustomerTaskAllocationMapper;
import com.hoomsun.after.dao.NomalCustomerMapper;
import com.hoomsun.common.model.Json;
import com.hoomsun.core.dao.SysEmployeeMapper;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月13日 <br>
 * 描述：催收任务
 */
@Service("CollectionRecordServer")
public class CollectionRecordServerImpl implements CollectionRecordServer{
	
	private static final Logger LogCvt = Logger.getLogger(CollectionRecordServerImpl.class);

	@Autowired
	private CollectionRecordMapper recordMapper;
	
	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;
	
	@Autowired
	private NomalCustomerMapper customerMapper;
	
	@Autowired
	private VisitTaskOprationServer visitTaskOprationServer;
	
	@Autowired
	private CustomerTaskAllocationMapper customerTaskAllocationMapper;
	
	/**
	 * 催收记录列表查询
	 */
	@Override
	public List<CollectionRecordResult> queryCollectionRecord(CollectionRecordReq req) {
		List<CollectionRecordResult>list=null;
		// TODO Auto-generated method stub
		req.setSettleFlag(CustomerLoanBalEnum.CUSTOMER_SETTLE_FLAG_NO.getCode());
		try {
			req.setPosType(StringUtil.getPosType(String.valueOf(req.getPosType()).trim()));
			if(autoCode.CUSTOMER_NEW_CREATE.equals(req.getPosType())){
				req.setPosType(autoCode.CUSTOMER_NEW_CREATE_VALUE);
			}
			//非客服人员
			if (sysEmployeeMapper.findStoreByDeptId(req.getDeptId()) == null) {
				req.setQueryType(autoCode.CUSTOMER_BACKUP);
			}else{
				//前线
				req.setQueryType(autoCode.CUSTOMER_FRONT);
			}
			/**
			 * 结算查询全部
			 */
			//读取配置文件结算标示
			String SettlementDeptId=SysConfig.getInstance().getProperty("SettlementDeptId");
			if(SettlementDeptId.equals(req.getDeptId())||"".equals("B23B9B7F0EEC41B0A89A19EC24D702C8")){
				req.setManagerCastId(null);
				req.setSalesDeptment(null);
				req.setLoanManagerCastId(null);
				req.setDalayFlag(CustomerLoanBalEnum.LOADBAL_DALAYFLAG_FALSE.getCode());
				LogCvt.info("结算查询催收任务列表......");
			}else{
				/**
				 * 判断是否副理  assiManager大于0则副理
				 */
				int assiManager=customerMapper.getIsManagerCount(req.getEmptId());
				if(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode().equals(req.getQueryType())){
					/**
					 * 前线
					 * 必须M1段；必须逾期；必须前线
					 */
					if(assiManager>0){
						req.setManagerCastId(null);
						req.setLoanManagerCastId(null);
						LogCvt.info("前线副理查询催收任务列表......");
					}else{
						req.setSalesDeptment(null);
						req.setLoanManagerCastId(null);
						LogCvt.info("前线客服查询催收任务列表......");
					}
					req.setmSection("1");
					req.setDalayFlag(CustomerLoanBalEnum.LOADBAL_DALAYFLAG_FALSE.getCode());
					req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode());
				}else if(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode().equals(req.getQueryType())){
					/**
					 * 后援
					 * 必须逾期；必须后援
					 */
					 //判断是否电催专员
					Integer electric=customerMapper.getElectricStimulation(req.getDeptId());
					if(electric>0){
						req.setManagerCastId(null);
						req.setDalayFlag(CustomerLoanBalEnum.LOADBAL_DALAYFLAG_FALSE.getCode());
						req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode());
						LogCvt.info("电催专员查询催收任务列表.....");
					}else{
						req.setSalesDeptment(null);
						req.setManagerCastId(null);
						req.setLoanManagerCastId(null);
						req.setDalayFlag(CustomerLoanBalEnum.LOADBAL_DALAYFLAG_FALSE.getCode());
						req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode());
						LogCvt.info("贷后主管查询催收任务列表......");
					}
				}else{
					LogCvt.error("查询类型未知......");
					return null;
				}
			}
			
			list=recordMapper.queryCollectionRecord(req);
			if(list!=null&&list.size()>0){
				for (CollectionRecordResult collectionRecordResult : list) {
					if(autoCode.CUSTOMER_NEW_CREATE_VALUE.equals(collectionRecordResult.getPosType())){
						collectionRecordResult.setPosType(autoCode.CUSTOMER_NEW_CREATE);
					}
					collectionRecordResult.setFrontOrbackup(req.getQueryType());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 催收记录列表导出
	 */
	@Override
	public void downloadCollectionRecord(CollectionRecordReq req) {
		// TODO Auto-generated method stub
		boolean download=true;
		try {
			req.setSettleFlag(CustomerLoanBalEnum.CUSTOMER_SETTLE_FLAG_NO.getCode());
			req.setPosType(StringUtil.getPosType(String.valueOf(req.getPosType()).trim()));
			if(autoCode.CUSTOMER_NEW_CREATE.equals(req.getPosType())){
				req.setPosType(autoCode.CUSTOMER_NEW_CREATE_VALUE);
			}
			//非客服人员
			if (sysEmployeeMapper.findStoreByDeptId(req.getDeptId()) == null) {
				req.setQueryType(autoCode.CUSTOMER_BACKUP);
			}else{
				//前线
				req.setQueryType(autoCode.CUSTOMER_FRONT);
			}
			/**
			 * 结算查询全部
			 */
			//读取配置文件结算标示
			String SettlementDeptId=SysConfig.getInstance().getProperty("SettlementDeptId");
			if(SettlementDeptId.equals(req.getDeptId())||"".equals("B23B9B7F0EEC41B0A89A19EC24D702C8")){
				req.setManagerCastId(null);
				req.setSalesDeptment(null);
				req.setLoanManagerCastId(null);
				req.setDalayFlag(CustomerLoanBalEnum.LOADBAL_DALAYFLAG_FALSE.getCode());
				LogCvt.info("结算查询催收任务列表......");
			}else{
				/**
				 * 判断是否副理  assiManager大于0则副理
				 */
				int assiManager=customerMapper.getIsManagerCount(req.getEmptId());
				if(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode().equals(req.getQueryType())){
					/**
					 * 前线
					 * 必须M1段；必须逾期；必须前线
					 */
					if(assiManager>0){
						req.setManagerCastId(null);
						req.setLoanManagerCastId(null);
						LogCvt.info("前线副理查询催收任务列表......");
					}else{
						req.setSalesDeptment(null);
						req.setLoanManagerCastId(null);
						LogCvt.info("前线客服查询催收任务列表......");
					}
					req.setmSection("1");
					req.setDalayFlag(CustomerLoanBalEnum.LOADBAL_DALAYFLAG_FALSE.getCode());
					req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode());
				}else if(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode().equals(req.getQueryType())){
					/**
					 * 后援
					 * 必须逾期；必须后援
					 */
					 //判断是否电催专员
					Integer electric=customerMapper.getElectricStimulation(req.getDeptId());
					if(electric>0){
						req.setManagerCastId(null);
						req.setDalayFlag(CustomerLoanBalEnum.LOADBAL_DALAYFLAG_FALSE.getCode());
						req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode());
						LogCvt.info("电催专员查询催收任务列表.....");
					}else{
						req.setSalesDeptment(null);
						req.setManagerCastId(null);
						req.setLoanManagerCastId(null);
						req.setDalayFlag(CustomerLoanBalEnum.LOADBAL_DALAYFLAG_FALSE.getCode());
						req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode());
						LogCvt.info("贷后主管查询催收任务列表......");
					}
				}else{
					LogCvt.error("查询类型未知......");
					return;
				}
			}
			
			req.setPageSize(10000);//导出一批10000条数据
			String fileName = "客户催收记录导出列表.xlsx";
            String title = "客户催收记录列表";
            Integer count=StringUtil.initConfigMaxRow("Max_row");
            ExportExcel excel=new ExportExcel(title, CollectionRecordResult.class);
			if(download){
				for (int i = 1; i < count; i++) {
					req.setPage(i);
					List<CollectionRecordResult>list=recordMapper.queryCollectionRecord(req);
					excel=excel.setDataList(ExcelUtils.CollectionRecordExcel(list,req));
				}
				excel.write(req.getResponse(), fileName);
				excel.dispose();
			}else{
				excel=excel.setDataList(null);   
				excel.write(req.getResponse(), fileName);
				excel.dispose();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查询总条数
	 */
	@Override
	public Integer countCollectionRecord(CollectionRecordReq req) {
		// TODO Auto-generated method stub
		Integer count=0;
		try {
			count=recordMapper.countCollectionRecord(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 催收提醒
	 */
	@Override
	public CustomerCollectionRemindingResult queryCustomerCastInfo(CollectionRecordReq req){
		// TODO Auto-generated method stub
		CustomerCollectionRemindingResult result=null;
		try {
			result=recordMapper.queryCustomerCastInfo(req);
			List<CollectionRecordResult>reminList=recordMapper.queryCustomerOverdueInfo(req);
			/**
			 * 计算应收总金额、逾期总期数
			 */
			double overdueTotalAmt=0;//应收总金额
			Integer overduePeriod=0;//逾期总期数
			if(reminList!=null&&reminList.size()>0){
				for (CollectionRecordResult colleResult : reminList) {
					overdueTotalAmt=StringUtil.amountAdd(overdueTotalAmt, Double.parseDouble(colleResult.getReceiveMoney()), 2);
					//overdueTotalAmt=overdueTotalAmt+Double.parseDouble(colleResult.getReceiveMoney());
					//overduePeriod=overduePeriod+Integer.parseInt(colleResult.getOverdueNum());
				}
			}
			result.setOverdueTotalAmt(String.valueOf(overdueTotalAmt));
			result.setOverduePeriod(String.valueOf(reminList.size()));
			
			/**
			 * 操作人
			 */
			result.setOprationId(req.getLoanManagerCastId());
			result.setOprationName(req.getLoanManagerCast());
			//逾期合计
			result.setOverdueDetailsList(reminList);
			//催收记录
			List<CollectingReceivingCallResult> callResult=recordMapper.queryCustomerCallHistory(req);
			result.setCollectingHistoryList(ExcelUtils.customerCallHistory(callResult));
			//通话详单
			result.setCallDetailsList(null);
			/**********2018.05.07新增外访负责人和催收负责人信息+外访记录**************************************************************************************/
			Allot allot=customerTaskAllocationMapper.queryAllotDetails(req.getLoanId());
			result.setCollectionCastId(result.getLoanManagerCast());
			result.setCollectionCastName(result.getLoanManagerCast());
			if(allot!=null){
				result.setVisitCastId(allot.getNewManagerCastid());
				result.setVisitCastName(allot.getNewManagerCast());
			}
			//外访记录
			VistTaskReq vistReq=new VistTaskReq();
			vistReq.setLoanId(req.getLoanId());
			result.setVisitRecordList(visitTaskOprationServer.queryOutBoundLog(vistReq));
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("查询异常"+e.getMessage());
		}
		return result;
	}

	/**
	 * 添加催收客户记录信息
	 */
	@Override
	public Integer addCustomerCollectionInfo(CollectingReceivingCallResult req) {
		// TODO Auto-generated method stub
		Integer num=0;
		try {
			req.setId(PrimaryKeyUtil.getPrimaryKey());
			req.setCreateTimeDate(new Date());
			req.setUpdateTime(new Date());
			req.setNextRemindingTimeDate(DateUtils.stringToDate(req.getNextRemindingTime(), DateUtils.DATE_19));
			req.setMindingNum(StringUtil.noMindMum(req.getCreateTimeDate(), req.getNextRemindingTimeDate()));//未提醒天=下次提醒天数-当前时间-1
			req.setRemindingState("0");//提醒状态默认已提醒
			LogCvt.info("创建时间"+DateUtils.dateToString(req.getCreateTimeDate(), DateUtils.DATE_19));
			LogCvt.info("下次提醒时间"+DateUtils.dateToString(req.getNextRemindingTimeDate(),DateUtils.DATE_19));
			num=recordMapper.addCustomerCollectionInfo(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}

	/**
	 * 催收任务-----添加点评信息
	 */
	@Override
	public Json addCommentCollectionRecordInfo(Review req) {
		// TODO Auto-generated method stub
		Json json=new Json();
		Integer num=0;
		try {
			req.setId(PrimaryKeyUtil.getPrimaryKey());
			num=recordMapper.addCommentCollectionRecordInfo(req);
			if(num>0){
				CollectionRecordReq reqq=new CollectionRecordReq();
				reqq.setLoanId(req.getLoanId());
				reqq.setOverdueNum(req.getOverdueNum());
				//点评标红
				recordMapper.updateOverdueRocordFlag(reqq);
				json.setMsg("点评成功");
				json.setSuccess(true);
			}else{
				json.setMsg("点评失败");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			// TODO: handle exception
			json.setMsg("操作数据库异常");
			json.setSuccess(false);
			LogCvt.error("操作数据库异常"+e.getMessage());
		}
		return json;
	}

}
