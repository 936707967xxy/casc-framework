package com.hoomsun.app.api.controller.flow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.after.api.model.AfterNomalDeduct;
import com.hoomsun.after.api.server.NomalServer;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.anno.Permission;


@Controller
@RequestMapping("sys/postinfo")
public class PostInfoController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private NomalServer  NomalServer;
	
	
	@Permission("version_query")
	@RequestMapping(value = "/page.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Pager<AfterNomalDeduct> findPagerData(Integer page, Integer rows,String castName,
			String cardNo,String deductState, String deductDate) {
		return NomalServer.findPage(page,  rows,  castName, cardNo,deductState,deductDate);
	}
	
	
}
