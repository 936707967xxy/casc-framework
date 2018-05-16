package com.hoomsun.after.server.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.after.api.model.param.NomalCustomerReq;
import com.hoomsun.after.api.model.vo.NomalCustomerResult;
import com.hoomsun.after.api.server.NomalCustomerServer;
import com.hoomsun.after.api.util.StringUtil;
import com.hoomsun.after.api.util.SysConfig;
import com.hoomsun.after.api.util.autoCode;
import com.hoomsun.after.api.util.enums.CustomerLoanBalEnum;
import com.hoomsun.after.api.util.excel.secode.annotation.ExportExcel;
import com.hoomsun.after.dao.CollectionRecordMapper;
import com.hoomsun.after.dao.NomalCustomerMapper;
import com.hoomsun.core.dao.SysEmployeeMapper;

@Service("NomalCustomerServer")
public class NomalCustomerServerImpl implements NomalCustomerServer{

	private static final Logger LogCvt = Logger.getLogger(NomalCustomerServerImpl.class);
	@Autowired
	private NomalCustomerMapper customerMapper;
	@Autowired
	private CollectionRecordMapper recordMapper;
	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;
	
	/**
	 * 正常客户列表查询
	 */
	@Override
	public List<NomalCustomerResult> queryNomalCustomer(NomalCustomerReq req) {
		// TODO Auto-generated method stub
		req.setPosType(StringUtil.getPosType(String.valueOf(req.getPosType()).trim()));
		if(autoCode.CUSTOMER_NEW_CREATE.equals(req.getPosType())){
			req.setPosType(autoCode.CUSTOMER_NEW_CREATE_VALUE);
		}
		
		//判断是前线OR 后援
		if (sysEmployeeMapper.findStoreByDeptId(req.getDeptId()) == null) {
			req.setQueryType(autoCode.CUSTOMER_BACKUP);
		}else{
			//前线
			req.setQueryType(autoCode.CUSTOMER_FRONT);
		}
		
		//读取配置文件结算标示
		String SettlementDeptId=SysConfig.getInstance().getProperty("SettlementDeptId");
		if(SettlementDeptId.equals(req.getDeptId())){
			//结算
			req.setManagerCastId(null);
			req.setSalesDeptment(null);
			req.setLoanManagerCastId(null);
			LogCvt.info("结算查询正常客户列表......");
		}else{
			/**
			 * 判断是否副理  assiManager大于0则副理
			 */
			int assiManager=customerMapper.getIsManagerCount(req.getEmpId());
			if(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode().equals(req.getQueryType())){
				if(assiManager>0){
					req.setManagerCastId(null);
					req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode());
					req.setLoanManagerCastId(null);
					LogCvt.info("前线副理查询正常客户列表......");
				}else{
					req.setSalesDeptment(null);
					req.setLoanManagerCastId(null);
					req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode());
					LogCvt.info("前线客服查询正常客户列表......");
				}
			}else if(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode().equals(req.getQueryType())){
				/**
				 * 判断是否电催专员
				 */
				Integer electric=customerMapper.getElectricStimulation(req.getDeptId());
				if(electric>0){
					req.setManagerCastId(null);
					req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode());
					LogCvt.info("电催专员查询正常客户列表......");
				}else{
					req.setSalesDeptment(null);
					req.setManagerCastId(null);
					req.setLoanManagerCastId(null);
					req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode());
					LogCvt.info("贷后主管查询正常客户列表......");
				}
			}else{
				LogCvt.error("查询类型未知......");
				return null;
			}
		}
		List<NomalCustomerResult>list=null;
		try {
			list=customerMapper.queryNomalCustomer(req);
			if(list!=null&&list.size()>0){
				for (NomalCustomerResult nomalCustomerResult : list) {
					if(autoCode.CUSTOMER_NEW_CREATE_VALUE.equals(nomalCustomerResult.getPosType())){
						nomalCustomerResult.setPosType(autoCode.CUSTOMER_NEW_CREATE);
					}
					nomalCustomerResult.setFrontOrbackup(req.getQueryType());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 正常客户列表导出
	 */
	@Override
	public void downloadNomalCustomer(NomalCustomerReq req) {
		// TODO Auto-generated method stub
		try {
			req.setPosType(StringUtil.getPosType(String.valueOf(req.getPosType()).trim()));
			if(autoCode.CUSTOMER_NEW_CREATE.equals(req.getPosType())){
				req.setPosType(autoCode.CUSTOMER_NEW_CREATE_VALUE);
			}
			//判断是前线OR 后援
			if (sysEmployeeMapper.findStoreByDeptId(req.getDeptId()) == null) {
				req.setQueryType(autoCode.CUSTOMER_BACKUP);
			}else{
				//前线
				req.setQueryType(autoCode.CUSTOMER_FRONT);
			}
			
			//读取配置文件结算标示
			String SettlementDeptId=SysConfig.getInstance().getProperty("SettlementDeptId");
			if(SettlementDeptId.equals(req.getDeptId())){
				//结算
				req.setManagerCastId(null);
				req.setSalesDeptment(null);
				req.setLoanManagerCastId(null);
				LogCvt.info("结算查询正常客户列表......");
			}else{
				/**
				 * 判断是否副理  assiManager大于0则副理
				 */
				int assiManager=customerMapper.getIsManagerCount(req.getEmpId());
				if(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode().equals(req.getQueryType())){
					if(assiManager>0){
						req.setManagerCastId(null);
						req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode());
						req.setLoanManagerCastId(null);
						LogCvt.info("前线副理查询正常客户列表......");
					}else{
						req.setSalesDeptment(null);
						req.setLoanManagerCastId(null);
						req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode());
						LogCvt.info("前线客服查询正常客户列表......");
					}
				}else if(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode().equals(req.getQueryType())){
					/**
					 * 判断是否电催专员
					 */
					Integer electric=customerMapper.getElectricStimulation(req.getDeptId());
					if(electric>0){
						req.setManagerCastId(null);
						req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode());
						LogCvt.info("电催专员查询正常客户列表......");
					}else{
						req.setSalesDeptment(null);
						req.setManagerCastId(null);
						req.setLoanManagerCastId(null);
						req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode());
						LogCvt.info("贷后主管查询正常客户列表......");
					}
				}else{
					LogCvt.error("查询类型未知......");
					return;
				}
			}
			
			req.setPageSize(10000);//导出一批10000条数据
			String fileName = "正常客户列表.xlsx";
            String title = "正常客户列表";
            ExportExcel excel=new ExportExcel(title, NomalCustomerResult.class);
            //最大导出条数为50000条   [10000*5]
            Integer count=StringUtil.initConfigMaxRow("Max_row");
			for (int i = 1; i < count; i++) {
				req.setPage(i);
				List<NomalCustomerResult>list=customerMapper.queryNomalCustomer(req);
				if(list!=null&&list.size()>0){
					for (NomalCustomerResult nomalCustomerResult : list) {
						if(autoCode.CUSTOMER_NEW_CREATE_VALUE.equals(nomalCustomerResult.getPosType())){
							nomalCustomerResult.setPosType(autoCode.CUSTOMER_NEW_CREATE);
						}
						if(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode().equals(req.getQueryType())){
							nomalCustomerResult.setLoanManagerCastName(null);
						}else{
							nomalCustomerResult.setManagerCast(null);
						}
					}
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
	 * 获取总条数
	 */
	@Override
	public Integer countNomalCustomer(NomalCustomerReq req) {
		// TODO Auto-generated method stub
		Integer count=0;
		try {
			count=customerMapper.countNomalCustomer(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 月还详情信息
	 */
	@Override
	public NomalCustomerResult ovedueAdvNomalDetailDetails(NomalCustomerReq req) throws Exception {
		// TODO Auto-generated method stub
		NomalCustomerResult result=null;
		if(autoCode.NOMAL_REPAYMENT.equals(req.getQueryType())||autoCode.ADVANCE_REPAYMENT.equals(req.getQueryType())){
			result=customerMapper.ovedueAdvNomalDetailDetails(req);
			//result.setDeductMoney(result.getOverdueSubMoney());
		}else if(autoCode.OVERDUE_REPAYMENT.equals(req.getQueryType())){
			result=recordMapper.queryOverdueRecordDetails(req);
			//result.setDeductMoney(result.getAdvancedSubMoney());
		}
		return result;
	}
}
