package com.hoomsun.app.api.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.anno.Permission;
import com.hoomsun.core.model.Bankinfo;
import com.hoomsun.core.server.inter.BankinfoServerI;



@Controller
@RequestMapping("sys/bankinfo")
public class BankinfoController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BankinfoServerI  bankinfoServerI;
	
	@Permission("version_query")
	@RequestMapping(value = "/page.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Pager<Bankinfo> findPagerData(Integer page, Integer rows, String bankname) {
		return bankinfoServerI.findPage(page, rows, bankname);
	}
	

	@RequestMapping(value = "/update.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json editBankinfo(Bankinfo bankinfo) {
		return bankinfoServerI.updateBankinfo(bankinfo);
	}
	
	
	@RequestMapping(value = "/create.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json addBankinfo(Bankinfo bankinfo) {
		return bankinfoServerI.addBankinfo(bankinfo);
	}
	
	@RequestMapping(value = "/remove.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json removeBankinfo(String pid) {
		return bankinfoServerI.deleteBankinfo(pid);
	}
}
