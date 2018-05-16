/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.core.dao.BaseMapper;
import com.hoomsun.core.server.inter.BaseServerI;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月22日 <br>
 * 描述：基础业务数据的实现
 */
@Service("baseServer")
public class BaseServerImpl implements BaseServerI {
	@Autowired
	private BaseMapper baseMapper;
	
	@Override
	public List<Map<String, String>> findAllProvinces() {
		return baseMapper.findAllProvinces();
	}

	@Override
	public List<Map<String, String>> findCitieByPro(String provinceId) {
		return baseMapper.findCitieByPro(provinceId);
	}

	@Override
	public List<Map<String, String>> findAreaByCity(String cityId) {
		return baseMapper.findAreaByCity(cityId);
	}

}
