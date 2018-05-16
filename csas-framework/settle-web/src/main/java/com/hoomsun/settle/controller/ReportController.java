/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.settle.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
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

import com.hoomsun.after.api.model.AfterNomalDeduct;
import com.hoomsun.after.api.model.vo.MinOverdueRecordVo;
import com.hoomsun.after.api.server.AfterOverdueRecordServer;
import com.hoomsun.after.api.server.NomalServer;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.anno.Permission;
import com.hoomsun.csas.settle.model.vo.DerateReportVo;
import com.hoomsun.csas.settle.model.vo.LoanInfoReport;
import com.hoomsun.csas.settle.model.vo.RepayReport;
import com.hoomsun.csas.settle.model.vo.ShouldRepayReport;
import com.hoomsun.csas.settle.server.inter.DerateReportVoServerI;
import com.hoomsun.csas.settle.server.inter.RepayReportServerI;
import com.hoomsun.csas.settle.server.inter.ShouldRepayReportServerI;
import com.hoomsun.settle.util.ExcelUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * 作者：liming <br>
 * 创建时间：2018年1月16日 <br>
 * 描述：报表
 */
@Controller
public class ReportController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private NomalServer NomalServer;
	@Autowired
	private RepayReportServerI repayReportServer;

	@Autowired
	private AfterOverdueRecordServer afterOverdueRecordServer;

	@Autowired
	private DerateReportVoServerI derateReportVoServer;

	@Autowired
	private ShouldRepayReportServerI shouldRepayReportServer;
	
	@Autowired  
	private Configuration freemarkerConfiguration; // 注入bean，这里是属性注入，推荐构造注入  

	private static Configuration configuration;
	  
	private static Template  template;

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年1月16日 <br>
	 * 描述： 客户还款报表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sys/repay/page.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Pager<RepayReport> findRepay(HttpServletRequest request, Integer page, Integer rows, String castName, String cardNo, String conNo, String repayType,String startDate,String endDate) {
		return repayReportServer.findPage(page, rows, castName, cardNo, conNo, repayType,startDate,endDate);

	}

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年2月8日 <br>
	 * 描述： 客户还款报表导出
	 * @param request
	 * @param response
	 * @param startDate
	 * @param endDate
	 */
	@RequestMapping(value="/sys/repay/freemarker.do", method = { RequestMethod.GET, RequestMethod.POST }) 
	@ResponseBody
	public void findRepayReport(HttpServletRequest request,HttpServletResponse response,String startDate,String endDate){ 
		logger.info("客户还款报表导出=================================");
		try {  
    	    Map<String, Object> param = new HashMap<String, Object>();
	    	param.put("startDate", startDate);
			param.put("endDate", endDate);
			List<RepayReport> repay=repayReportServer.findRepayList(param);
	        Map<String,Object> data=new HashMap<String,Object>();
	        data.put("repay", repay);
	        // 导出  
	        request.setCharacterEncoding("UTF-8");  
	        response.setContentType("application/x-download;");  
	        response.setHeader("Content-disposition", "attachment; filename="+ new String("客户还款报表.xlsx".getBytes("gb2312"), "ISO8859-1"));  
	        freemarkerConfiguration.getTemplate("repay.ftl").process(data,response.getWriter());
	        
	    }catch (Exception e){  
	    	logger.error("文件下载异常", e);  
	        e.printStackTrace();  
	    }  
		
	}
	
	
	
	
	// 划扣报表
	@Permission("version_query")
	@RequestMapping(value = "/sys/postinfo/page.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Pager<AfterNomalDeduct> findPagerData(Integer page, Integer rows, String castName, String cardNo, String deductState, String deductDate) {
		return NomalServer.findPage(page, rows, castName, cardNo, deductState, deductDate);
	}

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年1月17日 <br>
	 * 描述： 逾期报表
	 * 
	 * @param request
	 * @param page
	 * @param rows
	 * @param castName
	 * @return
	 */
	@RequestMapping(value = "/sys/overdue/page.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Pager<MinOverdueRecordVo> findOverdue(HttpServletRequest request, Integer page, Integer rows, String castName, String cardNo, String bankPhone, String conNo,String startDate,String endDate) {

		return afterOverdueRecordServer.findPage(page, rows, castName, cardNo, bankPhone, conNo,startDate,endDate);

	}

	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年2月7日 <br>
	 * 描述： 逾期报表导出
	 * @param request
	 * @param response
	 * @param startDate
	 * @param endDate
	 */
	@RequestMapping(value="/sys/overdue/freemarker.do", method = { RequestMethod.GET, RequestMethod.POST }) 
	@ResponseBody
	public void findOverdueReport(HttpServletRequest request,HttpServletResponse response,String startDate,String endDate){ 
		logger.info("逾期报表导出=================================");
		try {  
    	    Map<String, Object> param = new HashMap<String, Object>();
	    	param.put("startDate", startDate);
			param.put("endDate", endDate);
			List<MinOverdueRecordVo> overdue=afterOverdueRecordServer.findMinOverdue(param);
	        Map<String,Object> data=new HashMap<String,Object>();
	        data.put("overdue", overdue);
	        // 导出  
	        request.setCharacterEncoding("UTF-8");  
	        response.setContentType("application/x-download;");  
	        response.setHeader("Content-disposition", "attachment; filename="+ new String("逾期客户明细报表.xlsx".getBytes("gb2312"), "ISO8859-1"));  
	        freemarkerConfiguration.getTemplate("overdue.ftl").process(data,response.getWriter());
	        
	    }catch (Exception e){  
	    	logger.error("文件下载异常", e);  
	        e.printStackTrace();  
	    }  
		
	}
	
	
	
	
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年1月26日 <br>
	 * 描述： 减免报表
	 * 
	 * @param request
	 * @param page
	 * @param rows
	 * @param castName
	 * @param cardNo
	 * @param conNo
	 * @return
	 */
	@RequestMapping(value = "/sys/deratereport/page.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Pager<DerateReportVo> findDerateReport(HttpServletRequest request, Integer page, Integer rows, String castName, String cardNo, String conNo,String startDate,String endDate) {

		return derateReportVoServer.findAllData(page, rows, castName, cardNo, conNo,startDate,endDate);

	}
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年2月7日 <br>
	 * 描述： 减免报表导出
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/sys/derate/freemarker.do", method = { RequestMethod.GET, RequestMethod.POST }) 
	@ResponseBody
	public void DerateReport( HttpServletRequest request,HttpServletResponse response,String startDate,String endDate){ 
		logger.info("减免报表导出=================================");
		try {  
    	    Map<String, Object> param = new HashMap<String, Object>();
	    	param.put("startDate", startDate);
			param.put("endDate", endDate);
	    	List<DerateReportVo> derateReport=derateReportVoServer.findDerateList(param);
	        Map<String,Object> data=new HashMap<String,Object>();
	        data.put("derateReport", derateReport);
	        // 导出  
	        request.setCharacterEncoding("UTF-8");  
	        response.setContentType("application/x-download;");  
	        response.setHeader("Content-disposition", "attachment; filename="+ new String("减免查询报表.xlsx".getBytes("gb2312"), "ISO8859-1"));  
	        freemarkerConfiguration.getTemplate("derate.ftl").process(data,response.getWriter());
	        
	    }catch (Exception e){  
	    	logger.error("文件下载异常", e);  
	        e.printStackTrace();  
	    }  
		
	}
	
	
	
	
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年1月26日 <br>
	 * 描述： 应还款客户统计报表
	 * 
	 * @param request
	 * @param page
	 * @param rows
	 * @param castName
	 * @param cardNo
	 * @param conNo
	 * @return
	 */
	@RequestMapping(value = "/sys/shouldrepay/page.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Pager<ShouldRepayReport> findShouldRepay(HttpServletRequest request, Integer page, Integer rows, String castName, String cardNo, String conNo,String loanId,String startDate,String endDate) {

		return shouldRepayReportServer.findPage(page, rows, castName, cardNo, conNo,loanId,startDate,endDate);

	}
	
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年2月1日 <br>
	 * excel导出
	 * 描述： 应还款客户统计报表
	 * @param request
	 */
	@RequestMapping(value="/sys/shouldrepay/report.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public void ShouldRepayReport(HttpServletRequest request,HttpServletResponse response,String startDate,String endDate ){
		try {
			request.setCharacterEncoding("UTF-8");
			String title="应还款客户统计报表_线上";
	        String[] rowsName=new String[]{"序号","POS类型","合同编号","进件编号","客户姓名","证件号码","性别","借款用途","所属机构","产品类型","期限","已还期数","当前期次","放款日期","还款日期","到期日期","月利率%","合同金额","放款金额","本期本金","本期利息","本期应收金额","还款状态","客户银行名称","客户银行帐号","客户银行预留手机号","区域","营业部","业务员姓名","团队经理姓名","账单日"}; 
	        Map<String, Object> param = new HashMap<String, Object>();
	    	param.put("startDate", startDate);
			param.put("endDate", endDate);
	        List<ShouldRepayReport> list= shouldRepayReportServer.findBalList(param);
	        List<Object[]>  dataList = new ArrayList<Object[]>(); 
            Object[] objs = null;  
	        for (int i = 0; i < list.size(); i++) {
	        	ShouldRepayReport srr=list.get(i);
	        	objs = new Object[rowsName.length]; 
	        	objs[0]=i;
	        	objs[1] = srr.getPostype();
                objs[2] = srr.getConNo();
                objs[3] = srr.getSerialId();
                objs[4] = srr.getCastName();
                objs[5]=srr.getCardNo();
                objs[6]=srr.getCustSex();
        		objs[7]=srr.getLoanUseVal();
        		objs[8]=srr.getDepartment();
        		objs[9]=srr.getProductName();
        		objs[10]=srr.getLoanPeriod();
        		objs[11]=srr.getRepayDate();
        		objs[12]=srr.getCurrentPeriod();
        		objs[13]=srr.getLoanDate();
        		objs[14]=srr.getReplaymentDate();
        		objs[15]=srr.getEndTime();
        		objs[16]=srr.getProductRate();
        		objs[17]=srr.getContractAccount();
        		objs[18]=srr.getLoanMoney();	
        		objs[19]=srr.getShouldCapi();
        		objs[20]=srr.getShouldInte();
        		objs[21]=srr.getAmt();
        		objs[22]=srr.getRepayType();
        		objs[23]=srr.getBank();
        		objs[24]=srr.getBankAccount();
        		objs[25]=srr.getBankPhone();
        		objs[26]=srr.getBigArea();
        		objs[27]=srr.getStoreName();
        		objs[28]=srr.getSalesEmpName();
        		objs[29]=srr.getTeamLeader();
        		objs[30]=srr.getStatementDate();
        		
                
                dataList.add(objs);
			}	
	        ExcelUtils eu=new ExcelUtils(title,rowsName, dataList,response);
	        eu.exportData();
		} catch (Exception e) {
			e.printStackTrace();  
		}
		
	}
	
	
	
	
	
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2018年2月5日 <br>
	 * freemarker
	 * 描述：  应还款客户统计报表导出excel
	 * http://blog.csdn.net/baidu_30809315/article/details/78589388?locationNum=10&fps=1
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/sys/shouldrepay/freemarker.do", method = { RequestMethod.GET, RequestMethod.POST }) 
	public void downloadExcel( HttpServletRequest request,HttpServletResponse response,String startDate,String endDate ){  

		OutputStream out = null;
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-download;");
			response.setHeader("Content-disposition", "attachment; filename="+ new String("应还款客户统计报表(线上).xlsx".getBytes("gb2312"), "ISO8859-1"));
			out = response.getOutputStream();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("startDate", startDate);
			param.put("endDate", endDate);
			List<ShouldRepayReport> shouldRepay = shouldRepayReportServer.findBalList(param);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("shouldRepay", shouldRepay);
			// 模版所在路径
			String root = request.getSession().getServletContext().getRealPath("/");

			// 模版名称
			String templateName = "template/shouldRepay.ftl";
			// 导出文件放置路径
			//String filePath = "D:\\Documents\\Downloads";
			//导出
			export(root, data, templateName, out);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}  

	 /**
	  * 
	  * 作者：liming<br>
	  * 创建时间：2018年2月5日 <br>
	  * 描述： freemarker
	  * @param templatePath 模版所在路径
	  * @param dataMap 导出数据
	  * @param templateName 模版名称
	  * @param fielName 导出文件的名称
	  * @return
	  */
	 public static <E> boolean export(String templatePath, Map<String,Object> dataMap, String templateName, OutputStream out) {
	        try {
	            configuration = new Configuration(Configuration.VERSION_2_3_23);
	            configuration.setDefaultEncoding("UTF-8");
	            configuration.setDirectoryForTemplateLoading(new File(templatePath));
	            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	            template = configuration.getTemplate(templateName);
	            //直接导出到固定目录
	           // Writer writer=new FileWriter(fielName);
				//如果是web项目中的导出，需要将其以流的方式写出,未测试
				Writer writer=new BufferedWriter(new OutputStreamWriter(out, Charset.forName("UTF-8")));

	            template.process(dataMap, writer);
	            writer.flush();
	            writer.close();
	            return true;
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (TemplateException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	
}
