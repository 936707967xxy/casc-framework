package com.hoomsun.csas.sales.server.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.csas.sales.api.model.UserAllAuthInfo;
import com.hoomsun.csas.sales.api.server.inter.UserAllAuthInfoServerI;
import com.hoomsun.csas.sales.dao.UserAllAuthInfoMapper;

@Service("userAllAuthInfoServer")
public class UserAllAuthInfoServerImpl implements UserAllAuthInfoServerI {

	@Autowired
	private UserAllAuthInfoMapper userAllAuthInfoMapper;
	
	
	/**
	 * 查询认证清单
	 */
	@Override
	public List<Map<String,Object>> selectByApplyId(String applyId) {
		UserAllAuthInfo userAllAuth = userAllAuthInfoMapper.selectByApplyId(applyId);
		//返回list
		List<Map<String,Object>> auList = new ArrayList<Map<String,Object>>();
		
		//列表字段
		Map<String,String> auNameMap = new TreeMap<String, String>();
		auNameMap.put("creditInvestigation","个人征信");//征信
		auNameMap.put("errorCode","实名认证");//实名认证
		auNameMap.put("callLog","运营商");//运营商
		auNameMap.put("socialSecurity","社保");//社保
		auNameMap.put("chsi","学信网");//学信网
		auNameMap.put("accumulationFund","公积金");//公积金
		auNameMap.put("zhimaCode","芝麻分");//芝麻分
		auNameMap.put("protoinfoCode","绑定银行卡");//绑定银行卡
		auNameMap.put("savings","储蓄卡");//储蓄卡
		auNameMap.put("incomeCode","收入认证");//收入认证
		auNameMap.put("taobao","淘宝");//淘宝
		auNameMap.put("bankBillFlow","信用卡");//信用卡
		auNameMap.put("borrowerCode","个人信息认证");//个人信息认证
		auNameMap.put("contacterCode","单位联系人认证");//单位联系人认证
		auNameMap.put("careerCode","职业信息认证");//职业信息认证

		if(userAllAuth==null){//如果认证清单没有--赋值1，未认证
			for (String key : auNameMap.keySet()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("auName", auNameMap.get(key));
				map.put("auValue", 1);
				map.put("auKey", key);
				auList.add(map);
			}
		}else{//如果认证清单有--赋值本身，已认证	
			for (String key : auNameMap.keySet()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("auName", auNameMap.get(key));
				map.put("auValue", userAllAuth.getValue(key));
				map.put("auKey", key);
				auList.add(map);
			}	

		}
		return auList;
	}

}
