package com.hoomsun.app.api.server.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.app.api.dao.AfreshProtoinfoMapper;
import com.hoomsun.app.api.model.AfreshProtoinfo;
import com.hoomsun.app.api.server.inter.AfreshProtoinfoServerI;

@Service("afreshProtoServer")
public class AfreshProtoinfoServerImpl implements AfreshProtoinfoServerI{
	
   
	private AfreshProtoinfoMapper afreshProtoMapper;

	@Autowired
	public void setAfreshProtoMapper(AfreshProtoinfoMapper afreshProtoMapper) {
		this.afreshProtoMapper = afreshProtoMapper;
	}
	
	@Override
	public int insertSelective(AfreshProtoinfo record){
    	return afreshProtoMapper.insertSelective(record);
    }
	 
	@Override
	public int updateByPrimaryKeySelective(AfreshProtoinfo record){
		return afreshProtoMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public List<AfreshProtoinfo>  selectByFkid(String fkId){
		return afreshProtoMapper.selectByFkid(fkId);
	}
	
	@Override
	public AfreshProtoinfo selectByPrimaryKey(String protoinfoId){
		return afreshProtoMapper.selectByPrimaryKey(protoinfoId);
	}
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2018年1月6日<br>
	 * 描述：将卡设置为还款卡
	 * @param 
	 * @return
	 */
	@Override
	public int updateByType(String fkId){
		return afreshProtoMapper.updateByType(fkId);
	}
	
	@Override
	public int updateByProtoinfoId(Map<String,Object>  para){
		return afreshProtoMapper.updateByProtoinfoId(para);
	 }
	
	
	@Override
	public  int deleteByProtoinfoId(String protoinfoId){
		return afreshProtoMapper.deleteByProtoinfoId(protoinfoId);
	}

	@Override
	public AfreshProtoinfo selectByFkIdAndIsDefault(String applyId) {
		// TODO Auto-generated method stub
		return afreshProtoMapper.selectByFkIdAndIsDefault(applyId);
	}
	
	@Override
	public List<AfreshProtoinfo>  selectByAccno(String accno){
		return afreshProtoMapper.selectByAccno(accno);
	}
	
}
