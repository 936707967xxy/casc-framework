package com.hoomsun.app.api.server.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.app.api.dao.IncomeMapper;
import com.hoomsun.app.api.model.Income;
import com.hoomsun.app.api.server.inter.IncomeServerI;

@Service("incomeServer")
public class IncomeServerImpl implements IncomeServerI {

	@Autowired
	private IncomeMapper incomeMapper;

	public int insertSelective(Income record) {
		return incomeMapper.insertSelective(record);
	}

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年10月07日 <br>
	 * 描述： 查询所有已上传得收入图像
	 * 
	 * @param department
	 * @return
	 */
	public List<Income> selectAllByFkid(String fkId) {
		return incomeMapper.selectAllByFkid(fkId);
	}
	
	 /**
  	 * 作者：liudongliang <br>
  	 * 创建时间：2017年10月10日 <br>
  	 * 描述： 删除收入证明 
  	 * @param department
  	 * @return
  	 */
	public int deleteByFkid(String fkId){
		return incomeMapper.deleteByFkid(fkId);
	}
}
