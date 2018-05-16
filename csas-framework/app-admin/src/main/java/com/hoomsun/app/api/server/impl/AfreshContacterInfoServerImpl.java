package com.hoomsun.app.api.server.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.app.api.dao.AfreshContacterInfoMapper;
import com.hoomsun.app.api.model.AfreshContacterInfo;
import com.hoomsun.app.api.server.inter.AfreshContacterInfoMapperServerI;





@Service("afreshContacterServer")
public class AfreshContacterInfoServerImpl implements AfreshContacterInfoMapperServerI{
	
	@Autowired
	private AfreshContacterInfoMapper  AfreshContactMapper;

	
	@Override
	public int insertSelective(AfreshContacterInfo record){
		 return AfreshContactMapper.insertSelective(record);
	}

	@Override
	public List<AfreshContacterInfo> selectByFkid(String fkId){
		 return AfreshContactMapper.selectByFkid(fkId);
	}
	
	@Override
	public int updateByPrimaryKey(AfreshContacterInfo record){
		 return AfreshContactMapper.updateByPrimaryKey(record);
	}
	
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月15日<br>
	 * 描述：删除存在的联系人  
	 * @return
	 */
	@Override
	public int deleteByfkId(String fkId){
		return AfreshContactMapper.deleteByfkId(fkId);
	}
}
