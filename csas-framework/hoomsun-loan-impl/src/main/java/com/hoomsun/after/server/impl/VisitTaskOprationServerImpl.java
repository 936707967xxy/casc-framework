/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.server.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.after.api.model.param.CollectionRecordReq;
import com.hoomsun.after.api.model.param.CustomerTaskAllocationReq;
import com.hoomsun.after.api.model.param.NomalCustomerReq;
import com.hoomsun.after.api.model.param.VistTaskReq;
import com.hoomsun.after.api.model.table.Allot;
import com.hoomsun.after.api.model.vo.CollectingReceivingCallResult;
import com.hoomsun.after.api.model.vo.CollectionRecordResult;
import com.hoomsun.after.api.model.vo.CustomerCollectionRemindingResult;
import com.hoomsun.after.api.model.vo.CustomerTaskAllocationResult;
import com.hoomsun.after.api.model.vo.VisitRecordResult;
import com.hoomsun.after.api.model.vo.VistTaskResult;
import com.hoomsun.after.api.server.VisitTaskOprationServer;
import com.hoomsun.after.api.util.IDGenerator;
import com.hoomsun.after.api.util.StringUtil;
import com.hoomsun.after.api.util.SysConfig;
import com.hoomsun.after.api.util.autoCode;
import com.hoomsun.after.api.util.enums.CustomerLoanBalEnum;
import com.hoomsun.after.api.util.excel.secode.annotation.ExportExcel;
import com.hoomsun.after.api.util.excel.secode.excelUtil.ExcelUtils;
import com.hoomsun.after.dao.CollectionRecordMapper;
import com.hoomsun.after.dao.CustomerTaskAllocationMapper;
import com.hoomsun.after.dao.NomalCustomerMapper;
import com.hoomsun.after.dao.VisitTaskOprationMapper;
import com.hoomsun.common.model.Json;
import com.hoomsun.core.dao.SysEmployeeMapper;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年5月7日 <br>
 * 描述：外访申请列表以及分配Server
 */
@Service("VisitTaskOprationServer")
public class VisitTaskOprationServerImpl implements VisitTaskOprationServer{
	
	private static final Logger LogCvt = Logger.getLogger(VisitTaskOprationServerImpl.class);

	@Autowired
	private VisitTaskOprationMapper oprationMapper;
	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;
	@Autowired
	private NomalCustomerMapper customerMapper;
	@Autowired
	private CustomerTaskAllocationMapper allocationMapper;
	@Autowired
	private CollectionRecordMapper recordMapper;
	
	
	/**
	 * 查询外访申请列表
	 */
	@Override
	public List<VistTaskResult> queryVisitTaskList(VistTaskReq req) {
		// TODO Auto-generated method stub
		List<VistTaskResult>list=null;
		try {
		//判断是前线OR 后援
			 /*
			if (sysEmployeeMapper.findStoreByDeptId(req.getDeptId()) == null) {
				req.setQueryType(autoCode.CUSTOMER_BACKUP);
			}else{
				req.setQueryType(autoCode.CUSTOMER_FRONT);
			}
			
			if(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode().equals(req.getQueryType())){
				 Integer electric=customerMapper.getElectricStimulation(req.getDeptId());
					if(electric>0){
						req.setIsTask(null);
						req.setOutboundStatus(null);
						LogCvt.info("电催专员查询外访申请列表......");
					}else{
						req.setOutboundId(null);
						LogCvt.info("贷后主管查询外访申请列表......");
					}
					
			 }else{
				 LogCvt.info("只有贷后有权限查询!");
				 return list;
			 }
			 */
				if("0".equals(req.getQueryVisitType())){
					//查询总分配信息
					req.setOutboundId(null);
				}
			list=oprationMapper.queryVisitTaskList(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("数据库操作异常......");
		}
		return list;
	}

	/**
	 * 查询总条数
	 */
	@Override
	public Integer countVisitTask(VistTaskReq req) {
		// TODO Auto-generated method stub
		Integer count=0;
		try {
			count=oprationMapper.countVisitTask(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("数据库操作异常......");
		}
		return count;
	}

	/**
	 * 导出列表
	 */
	@Override
	public void downloadVisitTask(VistTaskReq req) {
		// TODO Auto-generated method stub
		try {
			/*
			if (sysEmployeeMapper.findStoreByDeptId(req.getDeptId()) == null) {
				req.setQueryType(autoCode.CUSTOMER_BACKUP);
			}else{
				req.setQueryType(autoCode.CUSTOMER_FRONT);
			}
			 if(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode().equals(req.getQueryType())){
				 Integer electric=customerMapper.getElectricStimulation(req.getDeptId());
					if(electric>0){
						req.setIsTask(null);
						req.setOutboundStatus(null);
						LogCvt.info("电催专员查询外访申请列表......");
					}else{
						req.setOutboundId(null);
						LogCvt.info("贷后主管查询外访申请列表......");
					}
			 }else{
				 LogCvt.info("只有贷后有权限查询!");
				 return;
			 }
			 */
			 if("0".equals(req.getQueryVisitType())){
					//查询总分配信息
					req.setOutboundId(null);
				}
				req.setPageSize(10000);//导出一批10000条数据
				String fileName = "外访申请列表.xlsx";
		        String title = "外访申请列表";
		        ExportExcel excel=new ExportExcel(title, VistTaskResult.class);
		        Integer count=StringUtil.initConfigMaxRow("Max_row");
				for (int i = 1; i < count; i++) {
					req.setPage(i);
					List<VistTaskResult> list;
					list = oprationMapper.queryVisitTaskList(req);
					if(list!=null&&list.size()>0){
						list=ExcelUtils.excelVisitTaskList(list);
					}else{
						break;
					}
					excel=excel.setDataList(list);
				}
				excel.write(req.getResponse(), fileName);
				excel.dispose();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	/**
	 * 申请列表分配
	 */
	@Override
	public Json updateVisiTask(VistTaskReq req) {
		// TODO Auto-generated method stub
		Json json=new Json();
		Allot log=null;
		try {
			List<NomalCustomerReq>loanIdList=req.getLoanIdList();
			if(loanIdList!=null&&loanIdList.size()>0){
				for (NomalCustomerReq nomalCustomerReq : loanIdList) {
					   req.setLoanId(nomalCustomerReq.getLoanId());
					   req.setOutboundId(req.getEmpWorkNum());
					   req.setOutboundName(req.getEmpName());
					    oprationMapper.updateVisiTask(req);
					    log=new Allot();
					    log.setId(PrimaryKeyUtil.getPrimaryKey());
						log.setAllotType("3");
						log.setLaonId(req.getLoanId());
						log.setConNo("");
						log.setOldManagerCastid("");
						log.setOldManagerCast("");
						log.setNewManagerCastid(req.getEmpWorkNum());
						log.setNewManagerCast(req.getEmpName());
						log.setOperationId(req.getOprationEmpWorkNum());
						log.setOperationName(req.getOprationEmpName());
						allocationMapper.insertCustomerAllot(log);
				}
				json.setMsg("分配成功");
				json.setSuccess(true);
			}else{
				LogCvt.info("进件编号集合为空......");
				json.setMsg("至少选择一个客户进行分配");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.setMsg("操作数据库异常");
			json.setSuccess(false);
		}
		return json;
	}

	/**
	 * 查询副理所属员工信息
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
	 * 查询外访记录列表信息
	 */
	@Override
	public List<VisitRecordResult> queryOutBoundLog(VistTaskReq req) {
		// TODO Auto-generated method stub
		List<VisitRecordResult>list=null;
		try {
			list=oprationMapper.queryOutBoundLog(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 手动添加外访记录信息
	 */
	@Override
	public Json addOutBoundLog(VisitRecordResult req) {
		// TODO Auto-generated method stub
		Json json=new Json();
		try {
			//String hostAddress = request.getRemoteAddr();
			req.setId(PrimaryKeyUtil.getPrimaryKey());
			if(req.getApplyId().isEmpty()){
				req.setApplyId(IDGenerator.getNextID("UPIMG"));
			}
			if(oprationMapper.addOutBoundLog(req)>0){
				json.setMsg("添加成功");
				json.setSuccess(true);
			}else{
				json.setMsg("添加失败");
				json.setSuccess(false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.setMsg("操作数据库异常");
			json.setSuccess(false);
		}
		return json;
	}
}
