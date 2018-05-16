package com.hoomsun.app.api.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.app.api.dao.BorrowerMapper;
import com.hoomsun.app.api.model.Borrower;
import com.hoomsun.app.api.server.inter.BorrowerServerI;

@Service("borrowerserverimpl")
public class BorrowerServerImpl implements BorrowerServerI{

	@Autowired
	private BorrowerMapper   borrowerMapper;
	
	public int insertSelective(Borrower record){
		return  borrowerMapper.insertSelective(record);
	 }
	
	public int updateByPrimaryKeySelective(Borrower record){
		return borrowerMapper.updateByPrimaryKeySelective(record);
	}
	
	public Borrower selectByFkid(String fkId){
		return borrowerMapper.selectByFkid(fkId);
	}
	
	public int updateByFkid(Borrower record){
		return borrowerMapper.updateByPrimaryKey(record);
	}
}
