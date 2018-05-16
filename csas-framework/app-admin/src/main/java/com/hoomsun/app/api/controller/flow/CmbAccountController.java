package com.hoomsun.app.api.controller.flow;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.after.api.model.SavePublic;
import com.hoomsun.after.api.server.SavePublicServer;
import com.hoomsun.app.api.helper.CmbHttpRequest;
import com.hoomsun.app.api.helper.XmlPacket;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.core.anno.Permission;
import com.hoomsun.core.model.CmbTransInfo;
import com.hoomsun.core.model.CmsAccount;
import com.hoomsun.core.server.inter.CmbTransInfoServerI;
import com.hoomsun.core.server.inter.CmsAccountServerI;




@Controller
@RequestMapping("sys/cmbaccount")
public class CmbAccountController {

	private final static Logger log = LoggerFactory.getLogger(CmbAccountController.class);
	
	@Autowired
	private CmsAccountServerI  cmsAccountServerI;
	
	@Autowired
	private CmbTransInfoServerI cmbTransInfoServerI;
	
	@Autowired
	private SavePublicServer savePublicServer;
	
	
	@ResponseBody
	@RequestMapping("addtransinfo.do")
	public Json AddTransInfo(){
		
		
		List<CmsAccount> list=cmsAccountServerI.selectAllData();
		
		//String begDate="20161120";    //查询开始时间
		//String endDate="20170120";    //查询结束时间
		
		String begDate="20170121";    //查询开始时间
		String endDate="20170121";    //查询结束时间
		
		String errorInfo="新增对公数据";
		int indexOfNum=0;
		try {
			for(CmsAccount cms:list){
				XmlPacket xml=CmbHttpRequest.getBankInfo(cms,begDate,endDate);
				if(xml.isError()){
					return new Json(false, xml.getERRMSG());
				}else{
					log.info("调用的借口"+xml.getFUNNAM());
					log.info("帐户名="+xml.getLGNNAM());
					log.info("返回参数编码="+xml.getRETCOD());
					log.info("数据条数="+xml.getSectionSize("NTQTSINFZ"));
					int index=xml.getSectionSize("NTQTSINFZ");
					List<Map<String, Object>> xmlList=new ArrayList<Map<String,Object>>();
					for(int i=0;i<index;i++){
						Map<String, Object> map=xml.getProperty("NTQTSINFZ", i);
						map.put("ID", PrimaryKeyUtil.getPrimaryKey());
						System.out.println(map.toString());
						xmlList.add(map);
						  
					}					
					indexOfNum+=cmbTransInfoServerI.insertMapSelective(xmlList);
					
				}
			}
			
		} catch (Exception e) {
			return new Json(false, "数据解析异常 ");
			// TODO: handle exception
		}
		
		return new Json(true, errorInfo+indexOfNum+"条");
	}
	
	
	@Permission("version_query")
	@RequestMapping(value = "/page.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Pager<SavePublic> findPagerData(Integer page, Integer rows, String rpyacc,String rrIdcard,String checkState) {
		return savePublicServer.findPageData( page,  rows,  rpyacc, rrIdcard,checkState);
	}
	
	@Permission("version_query")
	@RequestMapping(value = "/transpage.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Pager<CmbTransInfo> findPagerData(Integer page, Integer rows, String rpyacc) {
		return cmbTransInfoServerI.findPageData( page,  rows,  rpyacc);
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/changestatus", method = { RequestMethod.GET, RequestMethod.POST })
	public Json changeStatus(String id, String checkState,HttpServletRequest request) {
		if(StringUtils.isBlank(id)){
		}
		
		boolean flag=false;
		//2 同意 
		if("2".equals(checkState)){
			 flag=savePublicServer.publicSuccess(id,request );
		}
		
		//3  拒绝 
        if("3".equals(checkState)){
        	 flag=savePublicServer.publicDefail(id, request);
		}
        
        if(flag){
        	return new Json(true,"修改成功！！" );
        }else{
        	return new Json(false,"修改失败！！ " );
        }
		
	}
	
}
