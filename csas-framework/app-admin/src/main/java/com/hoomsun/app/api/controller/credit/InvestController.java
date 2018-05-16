package com.hoomsun.app.api.controller.credit;

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
import com.hoomsun.app.api.enums.ApiEnum;
import com.hoomsun.app.api.enums.IpUrlEnum;
import com.hoomsun.app.api.server.inter.InvestServerI;
import com.hoomsun.common.util.HttpClientUtil;
import com.hoomsun.common.util.UploadPathUtil;
import com.hoomsun.csas.sales.api.model.NameAuthentication;
import com.hoomsun.csas.sales.api.model.UserApply;

/**
 * 
 * @author 作者：liudongliang <br>
 * @Date 创建时间：2017年11月30日 <br>
 * @Description 描述  实名认证接口 
 *           1. 实名认证
 *           192.168.3.19:8080/app-admin/web/invest/validation.do?ID=&name=&idCard=&PHONE=&UUID=&SALARY_MONTHLY&LONGITUDE=&LATITUDE=
 * 
 *
 */

@Controller
@RequestMapping("web/invest")
public class InvestController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static String validationurl =IpUrlEnum.DATA_IP.getIpUrl()+ ApiEnum.VALIDATIONURL.getApiUrl(); // 实名认证信息推送

	private static String locationurl = IpUrlEnum.DATA_IP.getIpUrl()+ApiEnum.LOCATIONURL.getApiUrl();    // 推送经纬度

	
    @Autowired
	private UploadPathUtil uploadPathUtil;

    @Autowired
   	private InvestServerI investServerI;

	
	/**
	 * @Description  验证身份信息        刘栋梁
	 * @param        ID,name,idCard,PHONE,LONGITUDE,LATITUDE
	 * @return       Map
	 * @Date         2017-12-01
	 * @LoggerAnnotation(moduleName = "实名认证", option = "实名认证推送接口")
	 */	
	@RequestMapping(value = "validation.do")
	@ResponseBody
	public Map<String, Object> validation(HttpServletRequest request, HttpServletResponse response) {

		long startTime = System.currentTimeMillis(); // 获取开始时间
		// ID
		String id = request.getParameter("ID");
		// 用户姓名
		// String name= new
		// String(request.getParameter("name").getBytes("ISO-8859-1"), "utf-8");
		String name = request.getParameter("name");
		// 身份证
		String idCard = request.getParameter("idCard");
		// 手机号
		String phone = request.getParameter("PHONE");
		// 收入
		// String SALARY_MONTHLY = request.getParameter("SALARY_MONTHLY");
		// 经度
		String longitude = request.getParameter("LONGITUDE");
		// 纬度
		String latitude = request.getParameter("LATITUDE");
		// 身份证照片
//		String url_photoget = request.getParameter("url_photoget"); // 身份证证件正面头像
//		String url_backcard = request.getParameter("url_backcard"); // 身份证证件背面路径
//		String url_frontcard = request.getParameter("url_frontcard"); // 身份证背面照路径
//		String url_photoliving = request.getParameter("url_photoliving"); // 活体截图

		Map<String, Object> realMap = new HashMap<String, Object>();
		realMap.put("ID", id);
		realMap.put("name", name);
		realMap.put("idCard", idCard);
		realMap.put("PHONE", phone);
		realMap.put("LONGITUDE", longitude);
		realMap.put("LATITUDE", latitude);
		logger.info(realMap.toString());

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// 头像一致，保存图像
//						String viewPath = uploadPathUtil.saveIdcardPath(); // 放到用户信息中
//						String staticViewPath = uploadPathUtil.userUrl(); // static数据
//						
//						String url_photogetpath = url_photoget;
//						String url_photogetsavepath = viewPath +"/"+ id + File.separator + "idcard" + File.separator + "url_photoget.jpg";
//						String static_url_photogetsavepath= staticViewPath+"/"+ id + File.separator + "idcard" + File.separator + "url_photoget.jpg";
//						
//						String url_backcardpath = url_backcard;
//						String url_backcardsavepath = viewPath +"/"+ id + File.separator + "idcard" + File.separator + "url_backcard.jpg";
//						String static_url_backcardsavepath = staticViewPath +"/"+ id + File.separator + "idcard" + File.separator + "url_backcard.jpg";
//						
//						String url_photolivingpath = url_photoliving;
//						String url_photolivingsavepath = viewPath +"/"+ id + File.separator + "idcard" + File.separator + "url_photoliving.jpg";
//						String static_url_photolivingsavepath = staticViewPath +"/"+ id + File.separator + "idcard" + File.separator + "url_photoliving.jpg";
//						
//						String url_frontcardpath = url_frontcard;
//						String url_frontcardsavepath = viewPath + "/"+id + File.separator + "idcard" + File.separator + "url_frontcard.jpg";
//						String static_url_frontcardsavepath = staticViewPath +"/"+id + File.separator + "idcard" + File.separator + "url_frontcard.jpg";
//						/*
//						 * logger.info("1"+url_photogetpath);
//						 * logger.info("2"+url_photogetsavepath);
//						 * logger.info("3"+url_backcardpath);
//						 * logger.info("4"+url_backcardsavepath);
//						 * logger.info("5"+url_photolivingpath);
//						 * logger.info("6"+url_frontcardsavepath);
//						 * logger.info("7"+url_frontcardpath);
//						 * logger.info("8"+url_photolivingsavepath);
//						 */
//						File f = new File(uploadPathUtil.saveIdcardPath() + File.separator + id + File.separator + "idcard");
//						if (!f.exists()) {
//							f.mkdirs();
//						}
//						DownloadHelper.downloadNet(url_photogetpath, url_photogetsavepath);
//						DownloadHelper.downloadNet(url_backcardpath, url_backcardsavepath);
//						DownloadHelper.downloadNet(url_photolivingpath, url_photolivingsavepath);
//						DownloadHelper.downloadNet(url_frontcardpath, url_frontcardsavepath);

			
			//推送信息
			
			logger.info("经纬度参数===" + longitude + "==========" + latitude);
			if ((longitude == null || latitude == null) || (Double.parseDouble(longitude) == 0.0 || Double.parseDouble(latitude) == 0.0)) {
				resultMap.put("errorInfo", "地址定位失败！");
				resultMap.put("errorCode", 1);
				return resultMap;
			}
			// 添加实名认证后的 给数据中心推送信息-----------------------------
			String posturl = validationurl;
			Map<String,Object>  paramdata=new HashMap<String, Object>();
			paramdata.put("phone", phone);
			paramdata.put("cardNumber", idCard);
			paramdata.put("userName", name);
			paramdata.put("state", "online");
			String str = HttpClientUtil.doPost(posturl, null, null,paramdata);
			
			logger.info("添加实名认证后的 给数据中心推送信息========" + str);
			JSONObject jsonObject = JSON.parseObject(str); // 阿里的对象转换
			if ("2222".equals(jsonObject.get("errorCode").toString())) {
				resultMap.put("errorInfo", "该身份证信息已锁定有手机号，请核实！！");
				resultMap.put("errorCode", 1);
				return resultMap;
			} else if ("3333".equals(jsonObject.get("errorCode").toString())) {
				resultMap.put("errorInfo", "信息格式异常，请核实！！");
				resultMap.put("errorCode", 1);
				return resultMap;
			}else if ("1111".equals(jsonObject.get("errorCode").toString())) {
				resultMap.put("errorInfo", "实名邦定失败！！");
				resultMap.put("errorCode", 1);
				return resultMap;
			}
			// 推送经纬度 --------------
			String locationUrl = locationurl;
			Map<String,Object>  locationData=new HashMap<String, Object>();
			locationData.put("cardNumber", idCard);
			locationData.put("logo", longitude);
			locationData.put("lat", latitude);
			String locationreult = HttpClientUtil.doPost(locationUrl, null, null,locationData);
			JSONObject locationjsonObjectmap = JSON.parseObject(locationreult);
			logger.info("======推送经纬度========" + locationjsonObjectmap.toString());
			if (!"0000".equals(locationjsonObjectmap.get("errorCode").toString())) {
				resultMap.put("errorInfo", "地址信息获取失败！！");
				resultMap.put("errorCode", 1);
				return resultMap;
			}
			
			
			
			Map<String, Object> datamap = new HashMap<String, Object>();		
			datamap.put("idCard", idCard);
			datamap.put("name", name);
			datamap.put("phone", phone);
			resultMap.put("data", datamap);
			resultMap.put("errorInfo", "实名认证成功！！！");
			resultMap.put("errorCode", 0);
					
			
			
			//添加图片--身份证		
//			AuthenticationUrl  authenticationUrl=new AuthenticationUrl();
//			authenticationUrl.setUrlBackcard(static_url_backcardsavepath);
//			authenticationUrl.setUrlFrontcard(static_url_frontcardsavepath);
//			authenticationUrl.setUrlPhotoget(static_url_photogetsavepath);
//			authenticationUrl.setUrlPhotoliving(static_url_photolivingsavepath);	
			//客户主表
			NameAuthentication nameAuth = new NameAuthentication();
			nameAuth.setId(id);
			nameAuth.setIsauthentication("1");
			nameAuth.setIsauthenticationVal("实名认证通过");
			nameAuth.setCustname(name);
			nameAuth.setPaperid(idCard);
			//客户门店单子补充信息
			UserApply record=new UserApply();
			record.setIdNumber(idCard);
			record.setCustId(id);
			
			investServerI.saveInvest(nameAuth, id, record);
			
			
			
//		}catch (Exception e) {
//			e.printStackTrace();
//			resultMap.put("errorInfo", "人脸识别图像不全，请联系业务员");
//			resultMap.put("errorCode", 1001);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("errorInfo", "数据异常，请联系业务员");
			resultMap.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("实名认证状态修改使用时间：" + (endTime - startTime));
		}
		logger.info("实名认证状态修改使用时间:" + resultMap);
		return resultMap;
	}

}
