/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.controller;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.SerialNumber;
import com.hoomsun.core.server.inter.SerialNumberServerI;



/**
 * 作者：liming <br>
 * 创建时间：2017年11月29日 <br>
 * 描述：生成编号的控制层
 */
@Controller
public class SerialNumberController {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	private SerialNumberServerI serialnumberServer;
	
	@Autowired
	public void setSerialnumberServer(SerialNumberServerI serialnumberServer) {
		this.serialnumberServer = serialnumberServer;
	}
	
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月30日 <br>
	 * 描述： 生成编号的分页查询
	 * @param request
	 * @param page
	 * @param rows 
	 * @param typeval
	 * @return
	 */
	@RequestMapping(value = "sys/serialnumber/serialnumberPager.do", method = { RequestMethod.POST })
	@ResponseBody
	public Pager<SerialNumber> serialnumberPager(HttpServletRequest request, Integer page, Integer rows, String typeval) {
		log.info("生成编号的分页查询===============");
		return serialnumberServer.findPage(page, rows, typeval);
	}
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月29日 <br>
	 * 描述： 生成序列号  
	 * @param serialnumber
	 * @return
	 */
	@RequestMapping(value = "sys/serialnumber/create.do", method = RequestMethod.POST)
	@ResponseBody
	public Json createSerialNumber(@RequestBody SerialNumber serialnumber,HttpServletRequest request){
		
				
		return serialnumberServer.create(serialnumber);
		
	}
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月30日 <br>
	 * 描述： 修改规则
	 * @param request
	 * @param serialnumber
	 * @return
	 */
	@RequestMapping(value = "sys/serialnumber/update.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Json updateSerialNumber(@RequestBody SerialNumber serialnumber, HttpServletRequest request){
		
		return serialnumberServer.updateSerialNumber(serialnumber);
		
	}
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月30日 <br>
	 * 描述： 删除编号
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "sys/serialnumber/delete.do", method = RequestMethod.POST)
	@ResponseBody
	public Json deleteSerialNumber(HttpServletRequest request,String id){
		
		
		return serialnumberServer.deleteSerialNumber(id);
		
	}
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月30日 <br>
	 * 描述： 根据id查询
	 * @param request
	 * @param id
	 * @return 
	 */
	@RequestMapping(value = "sys/serialnumber/findbyid.do", method = RequestMethod.GET)
	@ResponseBody
	public SerialNumber findById(HttpServletRequest request,String id){
		return serialnumberServer.findById(id);
		
	}
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月30日 <br>
	 * 描述： 
	 * @param typeval 类型 比如 3: 合同
	 * @param prefix   前缀 xa
	 * @return
	 */
	@RequestMapping(value = "sys/serialnumber/num.do", method = RequestMethod.GET)
	@ResponseBody
	public String serialnumber(String type,String prefix){
		
		String serialnumber=serialnumberServer.createNumber(type,prefix);
		log.info("serialnumber返回的编号==========================："+serialnumber);
		return serialnumber;
		
	}
}
