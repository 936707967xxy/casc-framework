package com.hoomsun.core.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.dao.BankinfoMapper;
import com.hoomsun.core.model.Bankinfo;
import com.hoomsun.core.server.inter.BankinfoServerI;


@Service("bankServer")
public class BankinfoServerImpl implements BankinfoServerI{
	

	private BankinfoMapper bankMapper;
	
	
	@Autowired
	public void setBankMapper(BankinfoMapper bankMapper) {
		this.bankMapper = bankMapper;
	}

	@Override
	public List<Bankinfo> findAllData(){
		return  bankMapper.findAllData();
	}
	
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月26日<br>
	 * 描述：满足筛选条件的数据 分页
	 * 
	 * @param param
	 *            keys:pageIndex,pageSize,comName
	 * @return
	 */
	@Override
	public Pager<Bankinfo> findPage(Integer page,Integer rows,String bankname){
		Map<String, Object> param = new HashMap<String, Object>();
		if (page == null || rows == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		rows = rows > 50 ? 50 : rows;
		param.put("pageIndex", page);
		param.put("pageSize", rows);

		if (!StringUtils.isBlank(bankname)) {
			param.put("bankname", "%" + bankname + "%");
		}
		List<Bankinfo> bankinfo = bankMapper.findPageData(param);
		Integer total = bankMapper.findPageCount(param);
		return new Pager<Bankinfo>(bankinfo, total);
	}

	
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12-07日<br>
	 * 描述：修改银行
	 * @param bankinfo   修改银行
	 * @return
	 */
	@Override
	public  Json updateBankinfo(Bankinfo bankinfo){
		int result=bankMapper.updateByPrimaryKeySelective(bankinfo);
		if (result > 0) {
			return new Json(true, "修改银行数据修改成功!");
		} else {
			return new Json(false, "修改银行数据修改失败!");
		}
	}
	
	@Override
	public Json addBankinfo(Bankinfo bankinfo){
		int result=bankMapper.insertSelective(bankinfo);
		if (result > 0) {
			return new Json(true, "添加银行数据修改成功!");
		} else {
			return new Json(false, "添加银行数据修改失败!");
		}
	}
	
	@Override
	public Json deleteBankinfo(String pid){
		int result=bankMapper.deleteByPrimaryKey(pid);
		if (result > 0) {
			return new Json(true, "银行数据删除成功!");
		} else {
			return new Json(false, "银行数据删除失败!");
		}
	}

	@Override
	public Bankinfo bankinfo(String bankId) {
		// TODO Auto-generated method stub
		return bankMapper.selectByPrimaryKey(bankId);
	}
}
