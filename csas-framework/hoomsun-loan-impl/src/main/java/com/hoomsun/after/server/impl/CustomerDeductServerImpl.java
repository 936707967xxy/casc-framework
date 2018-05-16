/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.server.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.after.api.model.param.CustomerDeductReq;
import com.hoomsun.after.api.model.vo.CustomerDeductResult;
import com.hoomsun.after.api.server.CustomerDeductServer;
import com.hoomsun.after.api.util.StringUtil;
import com.hoomsun.after.api.util.SysConfig;
import com.hoomsun.after.api.util.autoCode;
import com.hoomsun.after.api.util.enums.CustomerLoanBalEnum;
import com.hoomsun.after.api.util.excel.secode.annotation.ExportExcel;
import com.hoomsun.after.api.util.excel.secode.excelUtil.ExcelUtils;
import com.hoomsun.after.dao.CustomerDeductMapper;
import com.hoomsun.after.dao.NomalCustomerMapper;
import com.hoomsun.core.dao.SysEmployeeMapper;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月21日日 <br>
 * 描述：客户划扣记录
 */
@Service("CustomerDeductServer")
public class CustomerDeductServerImpl implements CustomerDeductServer{
	
	private static final Logger LogCvt = Logger.getLogger(CustomerDeductServerImpl.class);
	
	@Autowired
	private CustomerDeductMapper deductMapper;
	@Autowired
	private SysEmployeeMapper sysEmployeeMapper;
	@Autowired
	private NomalCustomerMapper customerMapper;

	/**
	 * 客户划扣记录查询
	 */
	@Override
	public List<CustomerDeductResult> queryCustomerDeduct(CustomerDeductReq req){
		// TODO Auto-generated method stub
		List<CustomerDeductResult>list=null;
		try {
			//客户类型
			req.setPosType(StringUtil.getPosType(String.valueOf(req.getPosType()).trim()));
			if(autoCode.CUSTOMER_NEW_CREATE.equals(req.getPosType())){
				req.setPosType(autoCode.CUSTOMER_NEW_CREATE_VALUE);
			}
			
			//非客服人员
			if (sysEmployeeMapper.findStoreByDeptId(req.getDeptId()) == null) {
				req.setQueryType(autoCode.CUSTOMER_BACKUP);//后援
			}else{
				req.setQueryType(autoCode.CUSTOMER_FRONT);//前线
			}
			
			/**
			 * 请求参数处理
			 */
			req=ExcelUtils.setCustomerDeductVo(req);
			/**
			 * 结算
			 */
			String SettlementDeptId=SysConfig.getInstance().getProperty("SettlementDeptId");
			if(SettlementDeptId.equals(req.getDeptId())){
				req.setManagerCastId(null);
				req.setSalesDeptment(null);
				req.setLoanManagerCastId(null);
				LogCvt.info("结算查询划扣列表......");
			}else{
					/**
					 * 判断是否副理  assiManager大于0则副理
					 */
					int assiManager=customerMapper.getIsManagerCount(req.getEmpId());
					if(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode().equals(req.getQueryType())){
						if(assiManager>0){
							req.setManagerCastId(null);
							req.setLoanManagerCastId(null);
							req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode());
							LogCvt.info("前线副理查询划扣记录信息......");
						}else{
							req.setSalesDeptment(null);
							req.setLoanManagerCastId(null);
							req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode());
							LogCvt.info("前线客服查询划扣记录信息......");
						}
				}else if(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode().equals(req.getQueryType())){
					/**
					 * 判断是否电催专员
					 */
					Integer electric=customerMapper.getElectricStimulation(req.getDeptId());
					if(electric>0){
						req.setManagerCastId(null);
						req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode());
						LogCvt.info("电催专员查询划扣记录信息......");
					}else{
						req.setSalesDeptment(null);
						req.setManagerCastId(null);
						req.setLoanManagerCastId(null);
						req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode());
						LogCvt.info("贷后主管查询划扣记录信息......");
					}
				}else{
					LogCvt.error("查询类型未知......");
					return null;
				}
			}
			list=deductMapper.queryCustomerDeduct(req);
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("查询数据库异常"+e.getMessage());
		}
		return list;
	}

	/**
	 * 客户划扣记录总条数查询
	 */
	@Override
	public Integer countCustomerDeduct(CustomerDeductReq req){
		// TODO Auto-generated method stub
		Integer count=0;
		try {
			count=deductMapper.countCustomerDeduct(req);
		} catch (Exception e) {
			// TODO: handle exception
			LogCvt.error("查询数据库异常"+e.getMessage());
		}
		return count;
	}

	/**
	 * 客户划扣记录查询记录导出
	 */
	@Override
	public void downloadCustomerDeduct(CustomerDeductReq req){
		// TODO Auto-generated method stub
		
		try {
			req.setPosType(StringUtil.getPosType(String.valueOf(req.getPosType()).trim()));
			if(autoCode.CUSTOMER_NEW_CREATE.equals(req.getPosType())){
				req.setPosType(autoCode.CUSTOMER_NEW_CREATE_VALUE);
			}
			
			boolean download=true;
		        //非客服人员
				if (sysEmployeeMapper.findStoreByDeptId(req.getDeptId()) == null) {
					req.setQueryType(autoCode.CUSTOMER_BACKUP);
				}else{
					req.setQueryType(autoCode.CUSTOMER_FRONT);//前线
				}
				/**
				 * 请求参数处理
				 */
				req=ExcelUtils.setCustomerDeductVo(req);
				/**
				 * 结算
				 */
				String SettlementDeptId=SysConfig.getInstance().getProperty("SettlementDeptId");
				if(SettlementDeptId.equals(req.getDeptId())){
					req.setManagerCastId(null);
					req.setSalesDeptment(null);
					req.setLoanManagerCastId(null);
					LogCvt.info("结算查询划扣列表......");
				}else{
						/**
						 * 判断是否副理  assiManager大于0则副理
						 */
						int assiManager=customerMapper.getIsManagerCount(req.getEmpId());
						if(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode().equals(req.getQueryType())){
							if(assiManager>0){
								req.setManagerCastId(null);
								req.setLoanManagerCastId(null);
								req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode());
								LogCvt.info("前线副理查询划扣记录信息......");
							}else{
								req.setSalesDeptment(null);
								req.setLoanManagerCastId(null);
								req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_FRONT.getCode());
								LogCvt.info("前线客服查询划扣记录信息......");
							}
					}else if(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode().equals(req.getQueryType())){
						/**
						 * 判断是否电催专员
						 */
						Integer electric=customerMapper.getElectricStimulation(req.getDeptId());
						if(electric>0){
							req.setManagerCastId(null);
							req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode());
							LogCvt.info("电催专员查询划扣记录信息......");
						}else{
							req.setSalesDeptment(null);
							req.setManagerCastId(null);
							req.setLoanManagerCastId(null);
							req.setCustomerOrLoan(CustomerLoanBalEnum.QUERY_TYPE_BACKUP.getCode());
							LogCvt.info("贷后主管查询划扣记录信息......");
						}
					}else{
						LogCvt.error("查询类型未知......");
						return ;
					}
				}
				
				req.setPageSize(10000);//导出一批10000条数据
				String fileName = "客户划扣记录列表.xlsx";
		        String title = "客户划扣记录列表";
		        ExportExcel excel=new ExportExcel(title, CustomerDeductResult.class);
		        Integer count=StringUtil.initConfigMaxRow("Max_row");
				if(download){
					for (int i = 1; i < count; i++) {
						req.setPage(i);
						List<CustomerDeductResult>list=deductMapper.queryCustomerDeduct(req);
						excel=excel.setDataList(ExcelUtils.CustomerDeductExcel(list));
					}
					excel.write(req.getResponse(), fileName);
					excel.dispose();
				}else{
					excel=excel.setDataList(null);   
					excel.write(req.getResponse(), fileName);
					excel.dispose();
				}
		} catch (Exception e) {
			// TODO: handle exception
		}
	 }
}
