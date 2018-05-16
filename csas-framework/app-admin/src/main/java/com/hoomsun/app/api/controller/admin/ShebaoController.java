package com.hoomsun.app.api.controller.admin;


import java.util.List;
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
import com.hoomsun.app.api.model.ProvincesHege;
import com.hoomsun.app.api.model.Social;
import com.hoomsun.app.api.server.inter.ProvincesHegeServerI;
import com.hoomsun.app.api.server.inter.SocialServerI;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.anno.Permission;


/**
 * 黑格社保查询流程
 * http://192.168.3.19:8080/hsfs-web/web/shebao/logintype.do?province_id=61
 * 获取登陆方式
 * http://192.168.3.19:8080/hsfs-web/web/shebao/logincode.do?login_type=18&unique_key=xian&login_name=142726199402252135
 * 获取社保=验证码
 * http://192.168.3.19:8080/hsfs-web/web/shebao/logincheck.do?params=142726199402252135,793811,7362&unique_key=xian&login_type=18
 * 社保密码授权登陆
 * http://192.168.3.19:8080/hsfs-web/web/shebao/getquery.do?query_code=&cardNumber=
 * 数据 查询：根token查 http://192.168.3.19:8080/hsfs-web/web/shebao/hegeprovince.do
 * 黑格省查询
 * 
 * @author 刘栋梁
 * @date 2017-09-11
 */
@Controller
@RequestMapping("sys/shebao")
public class ShebaoController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public static String apix_key = "91a1f130c7fb400b4f2a12e773f62d61";


	@Autowired
	private ProvincesHegeServerI provincesHegeServer;

	@Autowired
	private SocialServerI socialServerI;
	/**
	 * 获取所有支持城市 2017-10-26 刘栋梁
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("hegeprovince.do")
	@ResponseBody
	public JSONObject hegeProvince(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		JSONObject jsonObject = new JSONObject();
		try {
			List<ProvincesHege> list = provincesHegeServer.selectStoreCitysData(); // 现在只查询含有门店的省份
			jsonObject.put("errorInfo", "查询成功");
			jsonObject.put("errorCode", 0);
			jsonObject.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("errorInfo", "查询失败");
			jsonObject.put("errorCode", 1);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("获取所有支持城市：" + (endTime - startTime));
		}
		logger.info("获取所有支持城市:" + jsonObject.toJSONString());

		return jsonObject;
	}

	
	@RequestMapping(value = "create.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json addSocial(Social social) {
		return socialServerI.addSocial(social);
	}
	
	@Permission("version_query")
	@RequestMapping(value = "page.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Pager<Social> findPagerData(Integer page, Integer rows, String province) {
		return socialServerI.findPage(page, rows, province);
	}

	@RequestMapping(value = "query.do", method = { RequestMethod.GET })
	@ResponseBody
	public Social findById(String id) {
		return socialServerI.selectByPrimaryKey(id);
	}
	
	@RequestMapping(value = "/update.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json editSocial(Social social, HttpServletRequest request) {
		return socialServerI.updateSocial(social);
	}
	
	@RequestMapping(value = "/remove.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json removeSocial(String socialId, HttpServletRequest request) {
		return socialServerI.deleteSocial(socialId);
	}
	
	/**
	 * 获取登陆方式 2017-09-11 1.1 刘栋梁
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("logintype.do")
	@ResponseBody
	public JSONObject  loginType(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String province_id = request.getParameter("province_id");
		String str = "";
		try {
			String url = "http://e.apix.cn/apixanalysis/shebao/cities?apix-key=" + apix_key + "&province_id=" + province_id;
			str = HttpClientController.check(url);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("获取登陆方式：" + (endTime - startTime));
		}
		logger.info("获取登陆方式:" + str);
		JSONObject jsonObject = JSON.parseObject(str);
		
		long endTime=System.currentTimeMillis(); //获取结束时间  
		logger.info("获取登陆方式："+(endTime-startTime));  
		return jsonObject;
	}

	


	


}
