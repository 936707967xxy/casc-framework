package com.hoomsun.csas.sales.server.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.csas.sales.api.model.NameAuthentication;
import com.hoomsun.csas.sales.api.server.inter.NameAuthenticationServerI;
import com.hoomsun.csas.sales.dao.NameAuthenticationMapper;



@Service("nameAuthServer")
public class NameAuthenticationServerImpl implements NameAuthenticationServerI{

	private  NameAuthenticationMapper  nameAuthMapper;

	@Autowired
	public void setHsMapper(NameAuthenticationMapper nameAuthMapper) {
		this.nameAuthMapper = nameAuthMapper;
	}
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月12日<br>
	 * 描述：创建公司
	 * @param company 注册添加数据信息
	 * @return
	 */
	@Override
	public   int insertSelective(NameAuthentication hs){
		return nameAuthMapper.insertSelective(hs);
	}
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月12日<br>
	 * 描述：创建公司
	 * @param company 判断手机号是否存在 
	 * @return
	 */
	@Override
	public  NameAuthentication selectByPhone(String phone){
		return nameAuthMapper.selectByPhone(phone);
	}
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月12日<br>
	 * 描述：创建公司
	 * @param company 修改实名信息表
	 * @return
	 */
	@Override
	public int updateByPrimaryKeySelective(NameAuthentication Hs){
		return nameAuthMapper.updateByPrimaryKeySelective(Hs);
	}
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月12日<br>
	 * 描述：创建公司
	 * @param company 根据id查询
	 * @return
	 */
	@Override
	public NameAuthentication selectByPrimaryKey(String id){
		return nameAuthMapper.selectByPrimaryKey(id);
	}
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月12日<br>
	 * 描述：创建公司
	 * @param company 根据phone修改
	 * @return
	 */
	@Override
	public  int updateByPhoneSelective(NameAuthentication Hs){
		return nameAuthMapper.updateByPhoneSelective(Hs);
	}
	
	@Override
	public NameAuthentication selectByWxtaken(String wxtaken){
		return nameAuthMapper.selectByWxtaken(wxtaken);
	}
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月28日<br>
	 * 描述：创建公司
	 * @param company 单设备登陆信息查询
	 * @return
	 */
	@Override
	public NameAuthentication selectUuid(NameAuthentication record){
		return nameAuthMapper.selectUuid(record);
	}

	@Override
	public NameAuthentication selectBySign(String sign) {
		return nameAuthMapper.selectBySign(sign);
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
