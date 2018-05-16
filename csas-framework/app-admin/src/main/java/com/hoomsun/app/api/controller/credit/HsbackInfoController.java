package com.hoomsun.app.api.controller.credit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.app.api.model.HsBackinfo;
import com.hoomsun.app.api.server.inter.HsBackinfoServerI;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.common.util.PrimaryKeyUtil;

/**
 * @author 作者：liudongliang <br>
 * @Date 创建时间：2017年12月27日 <br>
 * @Description 添加反馈意见接口
 * @resource     
 *              添加反馈意见接口    
 *              http://192.168.3.19:8080/app-admin/web/hsbackinfo/insertbackinfo.do?phone= &backInfo  
 *             
 */


@Controller
@RequestMapping("web/hsbackinfo")
public class HsbackInfoController {

	@Autowired
	private HsBackinfoServerI  hsBackinfoServerI;
	
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	/**
	 * 添加反馈意见接口    2017-12-27   1.1 刘栋梁
	 * 
	 * 解决ajax异步返回参数 
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	
	@RequestMapping("insertbackinfo.do")
	public void insertBackinfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String phone = request.getParameter("phone");
		String backInfo = request.getParameter("backInfo");
		backInfo = new String(backInfo.getBytes("ISO8859-1"), "UTF-8");  
		String str = "callback(";
		Map<String, Object> result = new HashMap<String, Object>();		
		try {		
			HsBackinfo   HsBackinfo=new HsBackinfo();
			HsBackinfo.setPhone(phone);
			HsBackinfo.setBackInfo(backInfo);
			HsBackinfo.setAddData(DateUtil.getTimestamp());
			HsBackinfo.setId(PrimaryKeyUtil.getPrimaryKey());
			hsBackinfoServerI.insertSelective(HsBackinfo);
			result.put("errorCode", 0);
			result.put("errorInfo", "数据添加成功！！ ");
			result.put("data", HsBackinfo);
			String jason=JSON.toJSONString(result);
		    str =str+jason+")";
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
			String jason=JSON.toJSONString(result);
		    str =str+jason+")";
		} finally {
			// 获取结束时间
			long endTime = System.currentTimeMillis();
			logger.info("添加反馈意见接口使用时间：" + (endTime - startTime));
		}
		logger.info("添加反馈意见接口:" + str);
		//JSONObject jsonObject = JSON.parseObject(str);
		response.getWriter().print(str);  
	}
} 
