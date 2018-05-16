package com.hoomsun.app.api.controller.admin;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.app.api.help.HttpClientController;
import com.hoomsun.app.api.model.House;
import com.hoomsun.app.api.server.inter.HouseServerI;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.anno.Permission;


@Controller
@RequestMapping("sys/housefund")
public class HouseFundController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	public static String apix_key = "e943ba8e54ad41b9534a56fd437f2379";
	
	@Autowired
	private HouseServerI houseServerI;
	
	
	/**
	 * 获取登陆方式 2017-09-11 1.1 刘栋梁
	 * 
	 * @param request
	 * @return
	 * @LoggerAnnotation(moduleName = "公积金接口", option = "获取公积金登陆方式")
	 */
	
	@RequestMapping("logintype.do")
	@ResponseBody
	public JSONObject loginType(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String province_id = request.getParameter("province_id");
		String str = "";
		try {
			String url = "http://e.apix.cn/apixanalysis/gjj/citys?apix-key=" + apix_key + "&province_id=" + province_id;
			str = HttpClientController.check(url);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("获取登陆方式：" + (endTime - startTime));
		}
		logger.info("获取登陆方式:" + str);

		JSONObject jsonObject = JSON.parseObject(str);

		return jsonObject;
	}


	@RequestMapping(value = "create.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json addHouse(House house) {
		return houseServerI.addHouse(house);
	}

	@Permission("version_query")
	@RequestMapping(value = "page.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Pager<House> findPagerData(Integer page, Integer rows, String province) {
		return houseServerI.findPage(page, rows, province);
	}
	
	@RequestMapping(value = "/update.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json editHouse(House house, HttpServletRequest request) {
		return houseServerI.updateHouse(house);
	}
	
	@RequestMapping(value = "/remove.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json removeHouse(String houseId, HttpServletRequest request) {
		return houseServerI.deleteHouse(houseId);
	}

}
