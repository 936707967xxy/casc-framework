/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.common.model.Result;
import com.hoomsun.risk.model.PhoneBook;
import com.hoomsun.risk.model.PhoneBook.Contact;
import com.hoomsun.risk.server.inter.PhoneBookServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年2月1日 <br>
 * 描述：通讯录/电话簿
 */
@Controller
public class PhoneBookController {
	@Autowired
	private PhoneBookServerI phoneBookServer;
	
	@RequestMapping(value="/api/risk/phonebook/add.do", method = RequestMethod.POST)
	@ResponseBody
	public Result pullPhoneBook(HttpServletRequest request,@RequestBody JSONObject object) {
		String idNumber = object.getString("idNumber");
		if (StringUtils.isBlank(idNumber)) {
			return new Result(1001, "参数异常! idNumber is null!");
		}
		
		PhoneBook phoneBook = new PhoneBook();
		String custName =  object.getString("custName");
		phoneBook.setIdNumber(idNumber);
		phoneBook.setCustName(custName);
		
		JSONArray array = object.getJSONArray("data");
		if (array == null || array.size() < 1) {
			return new Result(1002, "参数异常! data is null!");
		}
		
		for (Object obj : array) {
			JSONObject book = JSONObject.parseObject(JSONObject.toJSONString(obj));
			String name = book.getString("name");
			String phone = book.getString("phoneNumbers");
			Contact contact = phoneBook.new Contact();
			contact.setName(name);
			contact.setPhone(phone);
			phoneBook.addContacts(contact);
		}
		
		return phoneBookServer.createPhoneBook(phoneBook);
	}
	
}
