package com.hoomsun.app.api.controller.credit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.app.api.model.AfreshContacterInfo;
import com.hoomsun.app.api.model.Borrower;
import com.hoomsun.app.api.server.inter.AfreshContacterInfoMapperServerI;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.csas.sales.api.model.NameAuthentication;
import com.hoomsun.csas.sales.api.server.inter.NameAuthenticationServerI;



/**
 * 
 * @author 作者：liudongliang <br>
 * @Date   创建时间：2017-12-04 <br>
 * @Description 1.添加联系人信息
 *              192.168.3.19:8080/app-admin/web/personalcontact/addcontacterinfo.do?ID=&list
 */
@Controller
@RequestMapping("web/personalcontact")
public class PersonalContactController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AfreshContacterInfoMapperServerI afreshContacterServer;
	
	@Autowired
	private NameAuthenticationServerI hsServerI;
	
	
	/**
	 * @Description     添加联系人信息
	 * @param 
	 * @return  Map
	 * @Date    2017-12-04
	 * @LoggerAnnotation(moduleName = "基本信息管理", option = "添加联系人信息")
	 */	
	@RequestMapping("addcontacterinfo.do")
	@ResponseBody
	public Map<String, Object> addContacterInfo(@RequestBody List<AfreshContacterInfo> list,HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		Map<String, Object> result = new HashMap<String, Object>();
      
		try {
			if(list.size()==0){
				result.put("data", list);
				result.put("errorInfo", "未提交数据！！");
				result.put("errorCode", 1);
				return result;
			}
			String fk_id=list.get(0).getFkId();  //获取id 
			//删除旧数据			
			afreshContacterServer.deleteByfkId(fk_id);
			// 添加联系人
			int  i=0;
			for(AfreshContacterInfo  contacterInfo:list){
				contacterInfo.setCninfoPkId(PrimaryKeyUtil.getPrimaryKey());
				i+=afreshContacterServer.insertSelective(contacterInfo);
			}			
			if(i>0){
				NameAuthentication nameAuth =new NameAuthentication();  
				nameAuth.setId(fk_id);
				nameAuth.setContact("1");
				nameAuth.setContactTime(DateUtil.getCurrentTime());
				int j = hsServerI.updateByPrimaryKeySelective(nameAuth);
				if(j==1){
					result.put("data", list);
					result.put("errorInfo", "添加联系人成功");
					result.put("errorCode", 0);
				}else{
					result.put("data", list);
					result.put("errorInfo", "添加联系人失败");
					result.put("errorCode", 1);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("添加联系人信息使用时间：" + (endTime - startTime));
		}
		logger.info("添加联系人信息:" + result);

		return result;
	}
	
	
}
