/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.server.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.activation.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;

import com.alibaba.fastjson.JSON;
import com.hoomsun.after.api.model.param.CustomerOverdueReq;
import com.hoomsun.after.api.model.vo.CustomerDeductResult;
import com.hoomsun.after.api.model.vo.CustomerOverdueResult;
import com.hoomsun.after.api.server.CustomerOverdueServer;
import com.hoomsun.after.api.util.Paging;
import com.hoomsun.after.api.util.StringUtil;
import com.hoomsun.after.api.util.autoCode;
import com.hoomsun.after.api.util.excel.secode.annotation.ExportExcel;
import com.hoomsun.after.dao.CustomerOverdueMapper;

/**
 * 作者：Administrator <br>
 * 创建时间：2018年3月21日 <br>
 * 描述：客户减免
 */
@Service("CustomerOverdueServer")
public class CustomerOverdueServerImpl implements CustomerOverdueServer {

	private static final Logger LogCvt = Logger.getLogger(CustomerOverdueServerImpl.class);

	@Autowired
	private CustomerOverdueMapper overdueMapper;

	/**
	 * 客户减免信息查询
	 */
	@Override
	public List<CustomerOverdueResult> queryCustomerOverdue(CustomerOverdueReq req) throws Exception {
		// TODO Auto-generated method stub
		List<CustomerOverdueResult> list = overdueMapper.queryCustomerOverdue(req);

		return list;
	}

	/**
	 * 客户减免信息总条数
	 */
	@Override
	public Integer countCustomerOverdue(CustomerOverdueReq req) throws Exception {
		// TODO Auto-generated method stub
		Integer count = overdueMapper.countCustomerOverdue(req);

		return count;
	}

	/**
	 * 客户减免导出
	 */
	@Override
	public void downloadCustomerOverdue(CustomerOverdueReq req) throws Exception {
		// TODO Auto-generated method stub
		boolean download = true;
		req.setPageSize(10000);// 导出一批10000条数据
		String fileName = "客户减免列表.xlsx";
		String title = "客户减免列表";
		ExportExcel excel = new ExportExcel(title, CustomerDeductResult.class);
		 Integer count=StringUtil.initConfigMaxRow("Max_row");
		if (download) {
			for (int i = 1; i < count; i++) {
				req.setPage(i);
				List<CustomerOverdueResult> list = overdueMapper.queryCustomerOverdue(req);
				excel = excel.setDataList(list);
			}
			excel.write(req.getResponse(), fileName);
			excel.dispose();
		} else {
			excel = excel.setDataList(null);
			excel.write(req.getResponse(), fileName);
			excel.dispose();
		}
	}

	public CustomerOverdueReq rqq = null;
    volatile int count=0;
	@Override
	public void updateOverdueTest(CustomerOverdueReq req) {
		// TODO Auto-generated method stub
		// Paging page=new Paging();
		// page.setPage(req.getPage());
		// page.setPageSize(req.getPageSize());
		 try {
			List<CustomerOverdueResult> list=overdueMapper.queryTempHoomsun(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// page.setPageData(list,10);
		// System.err.println(JSON.toJSONString(page));
		//
		//ExecutorService pool=Executors.newFixedThreadPool(3);
//		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//		def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
//		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//		TransactionStatus status = txManager.getTransaction(def);

		//List<CustomerOverdueReq> result = new ArrayList<CustomerOverdueReq>();
		
		/*
		int count = 0;
        try {
        	for (final CustomerOverdueReq customerOverdueReq : result) {
    			customerOverdueReq.setLoanId("aaa" + count);
    			customerOverdueReq.setConNo("ccc" + count);
    			overdueMapper.up123dateOverdueTest(customerOverdueReq);
    			count++;
    			if (count == 6) {
    				Integer sa = Integer.parseInt("sss");
    			}
    		}
        	System.err.println("=====提交");
        	txManager.commit(status);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("=====回滚");
			txManager.rollback(status);
		}
		*/

		/*
		 * for (final CustomerOverdueReq customerOverdueReq : result) {
		 * System.out.println("id " + Thread.currentThread().getName() +
		 * "---start>"); customerOverdueReq.setLoanId("zzz" + count);
		 * customerOverdueReq.setConNo("ccc" + count); count++;
		 * overdueMapper.up123dateOverdueTest(customerOverdueReq); if(count==3){
		 * Integer sa=Integer.parseInt("a"); } }
		 */

		System.out.println("MutiThreadException.main()------>end<------");
	}
	
}
