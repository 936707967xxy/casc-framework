package com.hoomsun.app.api.controller.credit;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.app.api.enums.IpUrlEnum;
import com.hoomsun.app.api.model.Version;
import com.hoomsun.app.api.server.inter.VersionServerI;




/**
 * 
 * @author 作者：liudongliang <br>
 * @Date 创建时间：2017-12-04 <br>
 * @Description 1.版本控制
 *              192.168.3.19:8080/app-admin/web/version/update.do?type=&number=
 *           
 */
@Controller
public class VersionUpdateController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private VersionServerI versionServer;

	@Autowired
	public void setVersionServer(VersionServerI versionServer) {
		this.versionServer = versionServer;
	}

	
	/**
	 * @author 作者：liudongliang <br>
	 * @Description 强制更新 在登陆手机页面调 
	 * @param       type,number
	 * @return      Map
	 * @Date        2017-12-04
	 * @LoggerAnnotation(moduleName = "版本控制管理", option = "强制更新")
	 */
	
	@RequestMapping(value = "/web/version/update.do")
	@ResponseBody
	public Map<String, Object> insertUpdate(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间

		Map<String, Object> mapobj = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		String type = request.getParameter("type");
		String number = request.getParameter("number");
		mapobj.put("type", type);
		mapobj.put("number", number);
		logger.info("版本更新参数返回="+mapobj.toString());
		try {
			// 查询是否一致
			Version ver = versionServer.selectBytype(type);
			String APPROVAL = ver.getApproval();
			String MAXNUMBER = ver.getNumber(); // 最大版本
			String MINTYPE = ver.getMintype(); // 强制更新版本
		
			ver.setUrl(IpUrlEnum.HSFS_IP.getIpUrl() + ver.getUrl());
			MINTYPE = MINTYPE.replace(".", "");
			number = number.replace(".", "");
			MAXNUMBER = MAXNUMBER.replace(".", "");
			int numberint = Integer.parseInt(number);
			int MAXNUMBERint = Integer.parseInt(MAXNUMBER);
			int MINTYPEint = Integer.parseInt(MINTYPE);
			// 版本在审核中 不考虑大小
			if (APPROVAL.equals("true")) {
				map.put("errorInfo", "版本在审核中");
				map.put("errorCode", 0);
			} else {
				if (numberint < MINTYPEint) {
					map.put("errorInfo", "低于强制更新版本");
					map.put("errorCode", 2001);
				} else if (numberint >= MINTYPEint && numberint < MAXNUMBERint) {
					map.put("errorInfo", "高于等于强制更新版本，低于最高版本");
					map.put("errorCode", 2002);
				} else if (numberint >= MAXNUMBERint) {
					map.put("errorInfo", "最新版本");
					map.put("errorCode", 2003);
				}
			}
			map.put("data", ver);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", "网络异常，请稍后！");
			map.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("版本强制更新使用时间：" + (endTime - startTime));
		}
		logger.info("UpdateMap:" + map);
		return map;
	}

	
}
