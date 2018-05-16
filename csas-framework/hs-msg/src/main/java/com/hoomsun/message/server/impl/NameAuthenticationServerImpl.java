package com.hoomsun.message.server.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.message.dao.NameAuthentMapper;
import com.hoomsun.message.model.NameAuthentication;
import com.hoomsun.message.server.inter.NameAuthenticationServerI;


@Service("nameAuthServerI")
public class NameAuthenticationServerImpl implements NameAuthenticationServerI{

	private  NameAuthentMapper  nameAuthMapper;

	@Autowired
	public void setHsMapper(NameAuthentMapper nameAuthMapper) {
		this.nameAuthMapper = nameAuthMapper;
	}
	
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年10月24日<br>
	 * 描述：创建公司
	 * @param company 查询所有数据补全签名  
	 * @return
	 */
	@Override
	public List<NameAuthentication> selectAllData(){
		return nameAuthMapper.selectAllData();
	}
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年10月24日<br>
	 * 描述：身份证获取极光id
	 * 
	 * @param
	 * @return
	 */
	@Override
	public NameAuthentication selectByIdcard(String paperid) {
		return nameAuthMapper.selectByIdcard(paperid);
	}
}
