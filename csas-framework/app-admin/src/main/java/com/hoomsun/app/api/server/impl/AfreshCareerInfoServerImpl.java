package com.hoomsun.app.api.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.app.api.dao.AfreshCareerInfoMapper;
import com.hoomsun.app.api.model.AfreshCareerInfo;
import com.hoomsun.app.api.server.inter.AfreshCareerInfoServerI;


@Service("afreshCareerServer")
public class AfreshCareerInfoServerImpl implements AfreshCareerInfoServerI{
	
    private AfreshCareerInfoMapper careerInfoMapper;
    
    
	@Autowired
	public void setCareerInfoMapper(AfreshCareerInfoMapper careerInfoMapper) {
		this.careerInfoMapper = careerInfoMapper;
	}

	@Override
	public int insertSelective(AfreshCareerInfo record){
		return careerInfoMapper.insertSelective(record);
	}
	
	@Override
	public int updateByPrimaryKeySelective(AfreshCareerInfo record){
		return careerInfoMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public AfreshCareerInfo selectByFkid(String fkId){
		return careerInfoMapper.selectByFkid(fkId);
	}
}
