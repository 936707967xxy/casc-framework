package com.hoomsun.app.api.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.app.api.dao.CarrearInfoMapper;
import com.hoomsun.app.api.model.CarrearInfo;
import com.hoomsun.app.api.server.inter.CarrearInfoServerI;

@Service("carrearInfoServerImpl")
public class CarrearInfoServerImpl implements CarrearInfoServerI{

	@Autowired
	private CarrearInfoMapper carrearInfoMapper;
	
	public int insertSelective(CarrearInfo record){
		return carrearInfoMapper.insertSelective(record);
	}
	
	
	public CarrearInfo selectByfkId(String fkId){
		return carrearInfoMapper.selectByfkId(fkId);
	}
    
	public int updateByfkId(CarrearInfo record){
		return carrearInfoMapper.updateByPrimaryKeySelective(record);
	}
}
