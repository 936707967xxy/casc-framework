/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.server.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.after.api.model.param.SettleCustomerReq;
import com.hoomsun.after.api.model.vo.LoanBookCustomerResult;
import com.hoomsun.after.api.model.vo.SettleCustomerResult;
import com.hoomsun.after.api.server.SettleCustomerServer;
import com.hoomsun.after.api.util.StringUtil;
import com.hoomsun.after.api.util.autoCode;
import com.hoomsun.after.api.util.excel.secode.annotation.ExportExcel;
import com.hoomsun.after.api.util.excel.secode.excelUtil.ExcelUtils;
import com.hoomsun.after.dao.SettleCustomerMapper;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月28日 <br>
 * 描述：结清客户查询
 */
@Service("SettleCustomerServer")
public class SettleCustomerServerImpl implements SettleCustomerServer{
	
	private static final Logger LogCvt = Logger.getLogger(SettleCustomerServerImpl.class);
	
	@Autowired
	private SettleCustomerMapper settleCustomerMapper;

	/**
	 * 查询结清客户列表
	 */
	@Override
	public List<SettleCustomerResult> querySettleCustomer(SettleCustomerReq req) {
		// TODO Auto-generated method stub
		List<SettleCustomerResult> list = null;
		try {
			list = settleCustomerMapper.querySettleCustomer(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("查询结清客户列表查询异常");
		}
		
		return list;
	}

	/**
	 * 查询结清客户总条数
	 */
	@Override
	public Integer countSettleCustomer(SettleCustomerReq req) {
		// TODO Auto-generated method stub
		Integer count=0;
		try {
			 count=settleCustomerMapper.countSettleCustomer(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("查询结清客户总条数查询异常");
		}
		return count;
	}

	/**
	 * 结清客户导出
	 */
	@Override
	public void downloadSettleCustomer(SettleCustomerReq req) {
		// TODO Auto-generated method stub
		try {
			req.setPageSize(10000);//导出一批10000条数据
			String fileName = "结清客户列表.xlsx";
            String title = "结清客户列表";
            ExportExcel excel=new ExportExcel(title, SettleCustomerResult.class);
            Integer count=StringUtil.initConfigMaxRow("Max_row");
			for (int i = 1; i < count; i++) {
				req.setPage(i);
				List<SettleCustomerResult> list = settleCustomerMapper.querySettleCustomer(req);
				excel=excel.setDataList(ExcelUtils.SettleCustomerExcel(list));
			}
			excel.write(req.getResponse(), fileName);
			excel.dispose();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("结清客户列表导出异常");
		}
	}

	/**
	 * 客户还款记录详细信息
	 */
	@Override
	public List<LoanBookCustomerResult> queryLoanBookCustomerDetail(SettleCustomerReq req) {
		// TODO Auto-generated method stub
		List<LoanBookCustomerResult>list=null;
		try {
			list=settleCustomerMapper.queryLoanBookCustomerDetail(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogCvt.error("客户还款记录详细信息查询异常");
		}
		return list;
	}

}
