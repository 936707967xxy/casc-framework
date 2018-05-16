package com.hoomsun.app.api.controller.credit;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.hoomsun.common.util.HttpClientUtil;


/**
 * @author 作者：liudongliang <br>
 * @Date 创建时间：2018年02月05日 <br>
 * @Description 描述：在人脸识别获取不到个人信息时,调第三方来判断
 * @resource 1.  银行卡二要素验证
 *           192.168.3.19:8080/app-admin/web/investtianxin/investcheck.do?name=&idCard
 * 
 */
@Controller
@RequestMapping("web/investtianxin")
public class InvestTianxingController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * @Description     银行卡二要素验证
	 * @param           
	 * @return  Map
	 * @Date    2018年02月05日 
	 * 
	 * 
	 */		
	@RequestMapping("investcheck.do")
	@ResponseBody
	public Map<String, Object> investCheck(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		//姓名
		String name = request.getParameter("name");
		// 身份证
		String idCard = request.getParameter("idCard");
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String url = "http://tianxingshuke.com/api/rest/common/organization/auth"; // 服务码
			Map<String, Object> querys=new HashMap<String, Object>();
			querys.put("account", "hsjr");
			querys.put("signature","49f12d56335d466da447591605e3087b");
			String str=HttpClientUtil.doPost(url, null, null, querys);
			logger.info("银行卡获取授权="+str);
			Map map = JSON.parseObject(str);
			// 判断是否成功获取机构编码
			String back = map.get("success") + "";
			Map datamap = JSON.parseObject(map.get("data")+"");
			if (back.equals("true")) {
				String checkUrl = "http://tianxingshuke.com/api/rest/police/identity";
				       
				String accessToken = (String) datamap.get("accessToken");
				Map<String, Object> checkQuerys=new HashMap<String, Object>();
				checkQuerys.put("account", "hsjr");
				checkQuerys.put("accessToken",accessToken);
				checkQuerys.put("name", name);
				checkQuerys.put("idCard", idCard);

				
				String checkStr=HttpClientUtil.doGet(checkUrl, null, checkQuerys);
				logger.info("银行卡二要素验证="+checkStr);
				Map checkMmap = JSON.parseObject(checkStr);
				String checkBack = checkMmap.get("success") + "";
				if("false".equals(checkBack)){
					result.put("errorInfo", "身份证与当前注册人不符!!");
					result.put("errorCode", 1);
				}else{
					//true  不一定是通过 
					Map data = JSON.parseObject(checkMmap.get("data") + "");
					if("SAME".equalsIgnoreCase(data.get("compareStatus")+"")){
						result.put("errorInfo", "身份证二要素验证通过!!");
						result.put("errorCode", 0);
					}else{
						result.put("errorInfo", "身份证号与当前注册人不符!!");
						result.put("errorCode", 1);
					}
					
				}
				
			} else {
				result.put("errorInfo", "天行二要素获取授权失败!!");
				result.put("errorCode", 1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
			e.printStackTrace();
		}finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("银行卡二要素验证使用时间：" + (endTime - startTime));
		}
		logger.info("银行卡二要素验证:" + result);
		
		return result;

	}
	
	
	
	

	
	
}
