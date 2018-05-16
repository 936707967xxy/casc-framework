/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.settle.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Pager;
import com.hoomsun.core.server.inter.SerialNumberServerI;
import com.hoomsun.csas.settle.model.vo.LoanInfoReport;
import com.hoomsun.csas.settle.server.inter.LoanInfoReportServerI;
import com.hoomsun.csas.settle.server.inter.ShouldRepayReportServerI;

import freemarker.template.Configuration;

/**
 * 作者：liming <br>
 * 创建时间：2018年1月23日 <br>
 * 描述：放款报表
 * 
 */
@Controller
public class LoanInfoController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private LoanInfoReportServerI loanInfoReportServer;
	
	@Autowired
	private SerialNumberServerI serialnumberServer;
	
	
	@Autowired
	private  ShouldRepayReportServerI  shouldRepayReportServer;
	
	
	
	@Autowired  
	private Configuration freemarkerConfiguration; // 注入bean，这里是属性注入，推荐构造注入  
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年1月23日 <br>
	 * 描述： 放款报表
	 * @param request
	 * @param page
	 * @param rows
	 * @param custName
	 * @param bankPhoneNo
	 * @param conNo
	 * @param idNumber
	 * @return
	 */
	@RequestMapping(value="/sys/loaninfo/page.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Pager<LoanInfoReport> LoanInfo(HttpServletRequest request,Integer page, Integer rows,String custName,String bankPhoneNo,String conNo,String idNumber,String startDate,String endDate){
		return loanInfoReportServer.findPage(page, rows, custName,bankPhoneNo,conNo,idNumber,startDate,endDate);
		
	}
	
	
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年2月6日 <br>
	 * 描述： 放款报表导出
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/sys/loaninfo/freemarker.do", method = { RequestMethod.GET, RequestMethod.POST }) 
	@ResponseBody
	public void downloadExcel( HttpServletRequest request,HttpServletResponse response,String startDate,String endDate){ 
		logger.info("放款报表导出=================================");
	    try {  
	    	
	    	Map<String, Object> param = new HashMap<String, Object>();
			param.put("startDate", startDate);
			param.put("endDate", endDate);
	        List<LoanInfoReport> LoanInfo= loanInfoReportServer.findLoanInfoList(param);
	        
	        Map<String,Object> data=new HashMap<String,Object>();
	        data.put("LoanInfo", LoanInfo);
	        // 导出  
	        request.setCharacterEncoding("UTF-8");  
	        response.setContentType("application/x-download;");  
	        response.setHeader("Content-disposition", "attachment; filename="+ new String("客户放款统计报表.xlsx".getBytes("gb2312"), "ISO8859-1"));  
	        freemarkerConfiguration.getTemplate("loanInfo.ftl").process(data,response.getWriter());
	        
	    }catch (Exception e){  
	    	logger.error("文件下载异常", e);  
	        e.printStackTrace();  
	    }  
		
	}
	
	//测试编号
	@RequestMapping(value = "/number/test.do")
	@ResponseBody
	public String no(String type,String prefix){
		String serialnumber = serialnumberServer.createNumber(type, prefix);
//		String serialnumber = serialnumberServer.createSerialNumber(type, prefix);
		logger.info("生成测试编号=============================:"+serialnumber);
		return serialnumber;
		
	}
}
