package com.hoomsun.app.api.controller.credit;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hoomsun.app.api.model.Borrower;
import com.hoomsun.app.api.server.inter.BorrowerServerI;
import com.hoomsun.common.util.PrimaryKeyUtil;



/**
 * 
 * @author 作者：liudongliang <br>
 * @Date   创建时间：2017-12-14 <br>
 * @Description 
 *              1.个人信息表
 *              192.168.3.19:8080/app-admin/web/borrower/addborrowerinfo.do
 *              2.查询个人信息
 *              192.168.3.19:8080/app-admin/web/borrower/borrowerinfo.do
 */
@Controller
@RequestMapping("web/borrower")
public class BorrowerController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BorrowerServerI  borrowerServerI;
	

	
	/**
	 * @Description     添加个人信息
	 * @param   实体 
	 * @return  Map
	 * @Date    2017-12-14
	 * 
	 */	
	@RequestMapping("addborrowerinfo.do")
	@ResponseBody
	public Map<String, Object> addBorrowerInfo(HttpServletRequest request,@RequestBody Borrower borrower) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			String fk_id=borrower.getFkId();
			if(StringUtils.isBlank(fk_id)){
				result.put("errorInfo", "未找到用户！！");
				result.put("errorCode", 1);
			}
			
			Borrower Borrower=borrowerServerI.selectByFkid(fk_id);
			if(Borrower!=null){
				borrower.setId(Borrower.getId());
				//修改户籍所在地          1-24
				/*borrower.setRresidenceAddress(borrower.getRresidenceAddress()+borrower.getRresidenceAddressDetail());
				borrower.setHouseAddress(borrower.getHouseAddress()+borrower.getHouseAddressDetail());*/
				int i=borrowerServerI.updateByPrimaryKeySelective(borrower);
				result.put("data", borrower);
				result.put("errorInfo", "修改联系人成功");
				result.put("errorCode", 0);
			}else{
				borrower.setId(PrimaryKeyUtil.getPrimaryKey());
				int i=borrowerServerI.insertSelective(borrower);		
				result.put("data", borrower);
				result.put("errorInfo", "添加联系人成功");
				result.put("errorCode", 0);
			}
			
						
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("添加个人信息使用时间：" + (endTime - startTime));
		}
		logger.info("添加个人信息:" + result);

		return result;
	}
	
	/**
	 * @Description     查询个人信息
	 * @param   实体 
	 * @return  Map
	 * @Date    2017-12-14
	 * 
	 */	
	@RequestMapping("borrowerinfo.do")
	@ResponseBody
	public Map<String, Object> BorrowerInfo(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String fk_id=request.getParameter("ID");
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			if(StringUtils.isBlank(fk_id)){
				result.put("errorInfo", "未找到用户！！");
				result.put("errorCode", 1);
			}			
			Borrower Borrower=borrowerServerI.selectByFkid(fk_id);
			result.put("data", Borrower);
			result.put("errorInfo", "查询个人信息");
			result.put("errorCode", 0);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("查询个人信息使用时间：" + (endTime - startTime));
		}
		logger.info("查询个人信息:" + result);

		return result;
	}

}
