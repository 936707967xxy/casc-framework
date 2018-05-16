package com.hoomsun.csas.sales.server.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.app.api.dao.AuthenticationUrlMapper;
import com.hoomsun.app.api.model.AuthenticationUrl;
import com.hoomsun.csas.sales.api.model.NameAuthenticationInfoWithBLOBs;
import com.hoomsun.csas.sales.api.server.inter.NameAuthenticationInfoServerI;
import com.hoomsun.csas.sales.dao.NameAuthenticationInfoMapper;


@Service("nameAuthenInfoServer")
public class NameAuthenticationInfoServerImpl implements NameAuthenticationInfoServerI{
 
	private  NameAuthenticationInfoMapper  nameAuthInfoMapper;

	@Autowired
	private AuthenticationUrlMapper authenticationUrlMapper;
	
	@Autowired
	public void setHsMapper(NameAuthenticationInfoMapper nameAuthInfoMapper) {
		this.nameAuthInfoMapper = nameAuthInfoMapper;
	}
	
	@Override
	public int saveNameAuthInfoAndUrlInfo(String id, NameAuthenticationInfoWithBLOBs nameAuth, AuthenticationUrl  authenticationUrl){
		NameAuthenticationInfoWithBLOBs NameAuthOne = selectByFkid(id);
		int i = 0;
		if (NameAuthOne == null) {
			i = insertSelective(nameAuth);
//			System.out.println("实名认证信息添加条数=" + i);
		} else {
			nameAuth.setAuthinfoPkId(NameAuthOne.getAuthinfoPkId());
			i = updateByPrimaryKeySelective(nameAuth);
//			System.out.println("实名认证信息修改条数=" + i);
		}
		//添加图片--身份证
		AuthenticationUrl  AuthenticationUrl=authenticationUrlMapper.selectByFkid(id);			
		if(AuthenticationUrl==null){
			i += authenticationUrlMapper.insertSelective(authenticationUrl);
		}else{
			authenticationUrl.setFkId(AuthenticationUrl.getFkId());
			authenticationUrl.setId(AuthenticationUrl.getId());
			i += authenticationUrlMapper.updateByPrimaryKeySelective(authenticationUrl);
		}
		return i;
	}
	
	@Override
	public  int insertSelective(NameAuthenticationInfoWithBLOBs record){
		return nameAuthInfoMapper.insertSelective(record);
	}
	
	@Override
	public   NameAuthenticationInfoWithBLOBs selectByFkid(String fkId){
		return nameAuthInfoMapper.selectByFkid(fkId);
	}
	
	@Override
	public int updateByPrimaryKeySelective(NameAuthenticationInfoWithBLOBs record){
		return nameAuthInfoMapper.updateByPrimaryKeySelective(record);
	}
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年10-26日<br>
	 * 描述：线下单子推送实名数据 
	 * @param  NameAuthenticationInfoWithBLOBs
	 * @return 
	 */
	@Override
	public Map<String,Object>   selectDataByFkid(String fkId){
		return nameAuthInfoMapper.selectDataByFkid(fkId);
	}
}
