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
import com.hoomsun.message.model.Notice;
import com.hoomsun.message.server.inter.NoticeServerI;


@Controller
@RequestMapping("sys/notice")
public class NoticeController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private NoticeServerI  noticeServerI;
	

	@RequestMapping(value = "page.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Pager<Notice> findPager(Integer page, Integer rows, String custid) {
		return noticeServerI.findPage(page, rows, custid);
	}
	
	@RequestMapping(value = "create.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Json addNotice(Notice notice) {
		return noticeServerI.createNotice(notice);
	}
	
	@RequestMapping(value = "createall.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Json addNoticeAll(Notice notice) {
		return noticeServerI.createNoticeAll(notice);
	}
	
	
}
