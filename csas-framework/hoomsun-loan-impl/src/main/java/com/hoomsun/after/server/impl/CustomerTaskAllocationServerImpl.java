/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.server.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.after.api.model.param.CustomerTaskAllocationReq;
import com.hoomsun.after.api.model.param.DeliveryHistoryReq;
import com.hoomsun.after.api.model.param.NomalCustomerReq;
import com.hoomsun.after.api.model.table.Allot;
import com.hoomsun.after.api.model.table.RepaymentPlan;
import com.hoomsun.after.api.model.vo.CustomerTaskAllocationResult;
import com.hoomsun.after.api.server.CustomerTaskAllocationServer;
import com.hoomsun.after.api.util.StringUtil;
import com.hoomsun.after.api.util.autoCode;
import com.hoomsun.after.api.util.enums.CustomerLoanBalEnum;
import com.hoomsun.after.api.util.excel.secode.annotation.ExportExcel;
import com.hoomsun.after.api.util.excel.secode.excelUtil.ExcelUtils;
import com.hoomsun.after.dao.CustomerTaskAllocationMapper;
import com.hoomsun.after.dao.RepaymentPlanMapper;
import com.hoomsun.common.model.Json;
import com.hoomsun.core.dao.SysEmployeeMapper;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月14日 <br>
 * 描述：客户任务分配
 */
@Service("CustomerTaskAllocationServer")
public class CustomerTaskAllocationServerImpl implements CustomerTaskAllocationServer{
	
	private static final Logger LogCvt = Logger.getLogger(CustomerTaskAllocationServerImpl.class);

	@Autowired
	private CustomerTaskAllocationMapper allocationMapper;
	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;
	@Autowired
	private RepaymentPlanMapper planMapper;
	/**
	 * 查询客户任务分配
	 */
	@Override
	public List<CustomerTaskAllocationResult> queryCustomerTask(CustomerTaskAllocationReq req) {
		// TODO Auto-generated method stub
		List<CustomerTaskAllocationResult>list=null;
		try {
			//非客服人员
			if (sysEmployeeMapper.findStoreByDeptId(req.getDeptId()) == null) {
				req.setQueryType(autoCode.CUSTOMER_BACKUP);
			}else{
				req.setQueryType(autoCode.CUSTOMER_FRONT);//前线
			}
			
			if(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode().equals(req.getQueryType())){
				/**
				 * 前线
				 * 必须前线；必须属于当前用户销售营业部
				 */
				req.setCustomerOrLoan(CustomerLoanBalEnum.LOADBAL_CUSTOMER_FRONT.getCode());
				req.setLoanManagerCastId(null);
				req.setManagerCastId(null);
				req.setSettleFlag("0");
				LogCvt.info("查询前线任务分配列表......");
			}else if(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode().equals(req.getQueryType())){
				/**
				 * 后援
				 * 必须后援(查询全部)
				 */
				req.setStoreId("");
				req.setCustomerOrLoan(CustomerLoanBalEnum.LOADBAL_CUSTOMER_BACKUP.getCode());
				req.setManagerCastId(null);
				req.setLoanManagerCastId(null);
				req.setSettleFlag("0");
				LogCvt.info("查询后援任务分配列表......");
			}else{
				LogCvt.error("查询类型未知......");
				return null;
			}
			list=allocationMapper.queryCustomerTask(req);
			if(list!=null&&list.size()>0){
				for (CustomerTaskAllocationResult TaskResult : list) {
					/**
					 * 剩余期次
					 */
					Integer surPeriod=(Integer.parseInt(TaskResult.getLoanPeriod())-Integer.parseInt(TaskResult.getCurrentPeriod()));
					TaskResult.setSurplusPeriod(String.valueOf(surPeriod));
					TaskResult.setFrontOrbackup(req.getQueryType());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogCvt.error("任务分配查询数据库异常......"+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 客户任务分配导出
	 */
	@Override
	public void downloadCustomerTask(CustomerTaskAllocationReq req) {
		// TODO Auto-generated method stub
		boolean download=true;
		try {
			if (sysEmployeeMapper.findStoreByDeptId(req.getDeptId()) == null) {
				req.setQueryType(autoCode.CUSTOMER_BACKUP);
			}else{
				req.setQueryType(autoCode.CUSTOMER_FRONT);//前线
			}
			if(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode().equals(req.getQueryType())){
				/**
				 * 前线
				 * 必须前线；必须属于当前用户销售营业部
				 */
				req.setCustomerOrLoan(CustomerLoanBalEnum.LOADBAL_CUSTOMER_FRONT.getCode());
				req.setLoanManagerCastId(null);
				req.setManagerCastId(null);
				req.setSettleFlag("0");
				LogCvt.info("查询前线任务分配列表......");
			}else if(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode().equals(req.getQueryType())){
				/**
				 * 后援
				 * 必须后援(查询全部)
				 */
				req.setStoreId("");
				req.setCustomerOrLoan(CustomerLoanBalEnum.LOADBAL_CUSTOMER_BACKUP.getCode());
				req.setManagerCastId(null);
				req.setLoanManagerCastId(null);
				req.setSettleFlag("0");
				LogCvt.info("查询后援任务分配列表......");
			}else{
				download=false;
				LogCvt.error("查询类型未知......");
			}
			
			req.setPageSize(10000);//导出一批10000条数据
			String fileName = "客户任务分配列表.xlsx";
            String title = "客户任务分配列表";
            ExportExcel excel=new ExportExcel(title, CustomerTaskAllocationResult.class);
            Integer count=StringUtil.initConfigMaxRow("Max_row");
			if(download){
				for (int i = 1; i < count; i++) {
					req.setPage(i);
					List<CustomerTaskAllocationResult>list=allocationMapper.queryCustomerTask(req);
					excel=excel.setDataList(ExcelUtils.customerTask(list,req));
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
			LogCvt.error("任务分配查询数据库异常......"+e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 计算总条数
	 */
	@Override
	public Integer countCustomerTask(CustomerTaskAllocationReq req) {
		// TODO Auto-generated method stub
		Integer count=0;
		try {
			count=allocationMapper.countCustomerTask(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 根据empId获取所有员工信息(分配对象)
	 */
	@Override
	public List<CustomerTaskAllocationResult> querySysEmployee(CustomerTaskAllocationReq req) {
		// TODO Auto-generated method stub
		
		List<CustomerTaskAllocationResult>taskAll=null;
		try {
			taskAll=allocationMapper.findDeptEmp(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return taskAll;
	}

	/**
	 * 确认委托分配
	 */
	@Override
	public Json updateCustomerTask(CustomerTaskAllocationReq req) {
		// TODO Auto-generated method stub
		Json json=new Json();
		try {
		if (sysEmployeeMapper.findStoreByDeptId(req.getDeptId()) == null) {
			req.setQueryType(autoCode.CUSTOMER_BACKUP);
		}else{
			req.setQueryType(autoCode.CUSTOMER_FRONT);
		}
		
		if(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode().equals(req.getQueryType())){
			LogCvt.info("前线任务分配......");
			/**
			 * 前线有且仅有多个或者一个件分配给一个客户
			 */
			List<NomalCustomerReq>loanIdList=req.getLoanIdList();//被分派件loanId
			List<CustomerTaskAllocationResult>appointPeopleId=req.getAppointPeopleId();//被委托客户信息
			if(appointPeopleId.size()>1){
				json.setSuccess(false);
				json.setMsg("只能分配给一个客户");
				return json;
			}
			 NomalCustomerReq nomalReq=null;
			 DeliveryHistoryReq hisReq=null;
			 CustomerTaskAllocationResult result=null;
			 Allot allot=null;
			if(loanIdList!=null&&loanIdList.size()>0){
				for (NomalCustomerReq value : loanIdList) {
					allot=new Allot();
					allot.setId(PrimaryKeyUtil.getPrimaryKey());
					allot.setAllotType(req.getRoleName());
					allot.setLaonId(value.getLoanId());
					result=allocationMapper.queryCustomerLoanBalDetails(allot.getLaonId());
					if(result!=null){
						allot.setConNo(result.getConNo());
						allot.setOldManagerCast(result.getManagerCast());
						allot.setOldManagerCastid(result.getManagerCastId());
						allot.setNewManagerCast(appointPeopleId.get(0).getEmpName());
						allot.setNewManagerCastid(appointPeopleId.get(0).getEmpWorkNum());
						allot.setOperationId(req.getLoanManagerCastId());
						allot.setOperationName(req.getLoanManagerCast());
					}
					nomalReq=new NomalCustomerReq();
					nomalReq.setLoanId(value.getLoanId());
					nomalReq.setManagerCast(appointPeopleId.get(0).getEmpName());
					nomalReq.setManagerCastId(appointPeopleId.get(0).getEmpWorkNum());
					nomalReq.setLoanManagerCastId(null);
					nomalReq.setSalesDeptment(req.getStoreId());
					LogCvt.info("更新======>"+value.getLoanId());
					allocationMapper.updateCustomerTaskInfo(nomalReq);
					LogCvt.info("添加记录=======>"+value.getLoanId());
					if(appointPeopleId!=null&&appointPeopleId.size()>0){
						hisReq=new DeliveryHistoryReq();
						hisReq.setId(PrimaryKeyUtil.getPrimaryKey());
						hisReq.setAppointPeopleId(appointPeopleId.get(0).getEmpWorkNum());
						hisReq.setAppointPeopleName(appointPeopleId.get(0).getEmpName());
						hisReq.setOptionId(req.getLoanManagerCastId());
						hisReq.setOptionName(req.getLoanManagerCast());
						hisReq.setCreateTime(new Date());
						hisReq.setLoanId(value.getLoanId());
						hisReq.setDeliveryType(req.getRoleName());
						hisReq.setUpdateDate(null);
						allocationMapper.insertCustomerTaskInfo(hisReq);
					}else{
						throw new  RuntimeException("委托客户信息不能为空");
					}
					LogCvt.info("添加任务分配记录信息......");
					allocationMapper.insertCustomerAllot(allot);
				}
				json.setSuccess(true);
				json.setMsg("委托确认成功......");
			}else{
				json.setSuccess(false);
				json.setMsg("派件不能为空......");
				LogCvt.error("派件不能为空......");
			}
		}else if(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode().equals(req.getQueryType())){
			LogCvt.info("后援任务分配......");
			/**
			 * 后援既可以一个件分配给一个客户，
			 * 也可以多个件分配给多个客户
			 * （分配原则是：按照每个件的剩余本金从大到小排序，再依次分配）
			 */
			List<NomalCustomerReq>loanIdList=req.getLoanIdList();//被分派件loanId
			List<CustomerTaskAllocationResult>appointPeopleId=req.getAppointPeopleId();//被委托客户信息
			 CustomerTaskAllocationResult result=null;
			 Allot allot=null;
			if(loanIdList!=null&&loanIdList.size()>0){
				if(appointPeopleId!=null&&appointPeopleId.size()>0){
					if(loanIdList.size()<appointPeopleId.size()){
						json.setSuccess(false);
						json.setMsg("委托客户数量不能大于件数......");
						LogCvt.error("委托客户数量不能大于件数......");
					}else{
						/**
						 * 业务分派
						 */
						NomalCustomerReq nomalReq=null;
						DeliveryHistoryReq hisReq=null;
						RepaymentPlan plan=null;
						List<RepaymentPlan>repaymentList=new ArrayList<RepaymentPlan>();
						for (NomalCustomerReq loanId : loanIdList) {
							nomalReq=new NomalCustomerReq();
							nomalReq.setLoanId(loanId.getLoanId());
							nomalReq.setShouldTerm(loanId.getCurrentPeriod());
							plan= planMapper.queryByLoanIdTerm(nomalReq);
							repaymentList.add(plan);
						}
						
						/**
						 * 剩余本金排序
						 */
						Collections.sort(repaymentList,new Comparator<RepaymentPlan>() {
				            public int compare(RepaymentPlan o1, RepaymentPlan o2) {
				                // TODO Auto-generated method stub
				                return o1.getBal().compareTo(o2.getBal());
				            }
				        });
						
						if(repaymentList!=null&&repaymentList.size()>0){
							int batchNum=StringUtil.getTotalNumber(repaymentList.size(), appointPeopleId.size());
							/**
							 * 填充数据
							 */
							for (int i = 0; i < (repaymentList.size()-(appointPeopleId.size()*batchNum)); i++) {
								plan=new RepaymentPlan();
								nomalReq.setLoanId("@@");
								repaymentList.add(plan);
							}
							/**
							 * 分配
							 */
							int count=0,num=0;
							for (int a = 0; a < batchNum; a++) {
								int temp=-1;
								for (int i= count ; i < (count+appointPeopleId.size()); i++) {
									num++;
									if(num>repaymentList.size()){
										break;
									}
									temp++;
									if(!"@@".equals(repaymentList.get(i).getLoanId())){
										allot=new Allot();
										allot.setId(PrimaryKeyUtil.getPrimaryKey());
										allot.setAllotType(req.getRoleName());
										allot.setLaonId(repaymentList.get(i).getLoanId());
										result=allocationMapper.queryCustomerLoanBalDetails(allot.getLaonId());
										if(result!=null){
											allot.setConNo(result.getConNo());
											allot.setOldManagerCast(result.getLoanManagerCastName());
											allot.setOldManagerCastid(result.getLoanManagerCastId());
											allot.setNewManagerCast(appointPeopleId.get(temp).getEmpName());
											allot.setNewManagerCastid(appointPeopleId.get(temp).getEmpWorkNum());
											allot.setOperationId(req.getLoanManagerCastId());
											allot.setOperationName(req.getLoanManagerCast());
										}
										nomalReq=new NomalCustomerReq();
										nomalReq.setLoanId(repaymentList.get(i).getLoanId());
										nomalReq.setUpdateDate(new Date());
										nomalReq.setLoanManagerCast(appointPeopleId.get(temp).getEmpName());
										nomalReq.setLoanManagerCastId(appointPeopleId.get(temp).getEmpWorkNum());
										nomalReq.setManagerCastId(null);
										nomalReq.setSalesDeptment(req.getStoreId());
										LogCvt.info("更新======>"+repaymentList.get(i).getLoanId());
										allocationMapper.updateCustomerTaskInfo(nomalReq);
										LogCvt.info("添加记录=======>"+repaymentList.get(i).getLoanId());
										if(appointPeopleId!=null&&appointPeopleId.size()>0){
											hisReq=new DeliveryHistoryReq();
											hisReq.setId(PrimaryKeyUtil.getPrimaryKey());
											hisReq.setAppointPeopleId(appointPeopleId.get(temp).getEmpWorkNum());
											hisReq.setAppointPeopleName(appointPeopleId.get(temp).getEmpName());
											hisReq.setOptionId(req.getLoanManagerCastId());
											hisReq.setOptionName(req.getLoanManagerCast());
											hisReq.setLoanId(repaymentList.get(i).getLoanId());
											hisReq.setDeliveryType(req.getRoleName());
											allocationMapper.insertCustomerTaskInfo(hisReq);
										}else{
											throw new  RuntimeException("委托客户信息不能为空");
										}
										LogCvt.info("添加任务分配记录信息......");
										allocationMapper.insertCustomerAllot(allot);
									}
								}
								count=count+appointPeopleId.size();
							}
							json.setSuccess(true);
							json.setMsg("委托确认成功......");
						}else{
							json.setSuccess(false);
							json.setMsg("分派异常");
							LogCvt.error("分派异常......");
						}
					}
				}else{
					json.setSuccess(false);
					json.setMsg("委托客户不能为空......");
					LogCvt.error("委托客户不能为空......");
				}
			}else{
				json.setSuccess(false);
				json.setMsg("派件不能为空......");
				LogCvt.error("派件不能为空......");
			}
		}else{
			LogCvt.error("查询类型未知......");
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
