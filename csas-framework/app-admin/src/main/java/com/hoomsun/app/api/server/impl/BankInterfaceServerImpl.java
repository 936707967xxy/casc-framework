/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.app.api.server.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.app.api.dao.BankInterfaceMapper;
import com.hoomsun.app.api.model.BankInterface;
import com.hoomsun.app.api.model.BankInterfaceURL;
import com.hoomsun.app.api.server.inter.BankInterfaceServerI;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.common.util.UploadPathUtil;

/**
 * 作者：liudongliang  <br>
 * 创建时间：2017年12月7日 <br>
 * 描述：
 */

@Service("bankItfServer")
public class BankInterfaceServerImpl implements BankInterfaceServerI {
	private final static Logger log = LoggerFactory.getLogger(BankInterfaceServerImpl.class);
	private BankInterfaceMapper bankItfMapper;
	private UploadPathUtil uploadPathUtil;
	
	@Autowired
	public void setbankItfMapper(BankInterfaceMapper bankItfMapper) {
		this.bankItfMapper = bankItfMapper;
	}

	@Autowired
	public void setUploadPathUtil(UploadPathUtil uploadPathUtil) {
		this.uploadPathUtil = uploadPathUtil;
	}

	@Override
	public Json addBankInterFace(BankInterface bankItf) {
		if (StringUtils.isBlank(bankItf.getBankinterId())) {
			bankItf.setBankinterId(PrimaryKeyUtil.getPrimaryKey());
		}		
		String creditcardItf=JSONObject.toJSONString(bankItf.getCreditUrl());
		String depositcardItf=JSONObject.toJSONString(bankItf.getDepositUrl());	
		bankItf.setCreditcardItf(creditcardItf);
		bankItf.setDepositcardItf(depositcardItf);
		
		int result = bankItfMapper.insertSelective(bankItf);
		if(result > 0){
			return new Json(true, "银行接口添加成功!");
		}else{
			return new Json(false, "银行接口添加失败!");
		}
	}
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12-07日<br>
	 * 描述：分页
	 * @param page 当前页码
	 * @param rows 每页数据量
	 * @param type 类型
	 * @return
	 */
	@Override
	public Pager<BankInterface> findPage(Integer page,Integer rows,String bankName){
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

		if (!StringUtils.isBlank(bankName)) {
			param.put("bankName", "%"+bankName+"%");
		}
		
		List<BankInterface> bankinterface = bankItfMapper.findPageData(param);
		for(BankInterface bank:bankinterface){
			bank.setCreditUrl(JSON.parseObject(bank.getCreditcardItf(), BankInterfaceURL.class));
			bank.setDepositUrl(JSON.parseObject(bank.getDepositcardItf(), BankInterfaceURL.class));
		}
		
		int total = bankItfMapper.findPageCount(param);
		return new Pager<BankInterface>( bankinterface,total);

	}

	@Override
	public Json updateBankItf(BankInterface bankItf) {
		String creditcardItf=JSONObject.toJSONString(bankItf.getCreditUrl());
		String depositcardItf=JSONObject.toJSONString(bankItf.getDepositUrl());	
		bankItf.setCreditcardItf(creditcardItf);
		bankItf.setDepositcardItf(depositcardItf);
		
		int result = bankItfMapper.updateByPrimaryKeySelective(bankItf);
		if(result > 0){
			return new Json(true, "银行接口更新成功!");
		}else{
			return new Json(false, "银行接口更新失败!");
		}
	}
	
	


	private void setBannerUrlByUploadPath(MultipartFile bannerFile, BankInterface bankItf) {
		if (!bannerFile.isEmpty() && !StringUtils.isBlank(bannerFile.getOriginalFilename())) {
			try {
				String viewPath = uploadPathUtil.bankUrl();
				String fileName = bannerFile.getOriginalFilename();
				String fileType = fileName.substring(fileName.lastIndexOf("."));
				String saveName = System.currentTimeMillis() + fileType;

				File f = new File(uploadPathUtil.saveBankPath());
				if (!f.exists()) {
					f.mkdirs();
				}
				// 文件保存路径
				String filePath = uploadPathUtil.saveBankPath() + File.separator + saveName;
				// 转存文件
				bannerFile.transferTo(new File(filePath));

				bankItf.setBankUrl(viewPath + saveName);
			} catch (Exception e) {
				log.debug("文件上传服务器没有成功!");
			}
		}
	}
	
	@Override
	public Json deleteBankItf(String bankInterId) {
		int result = bankItfMapper.deleteByPrimaryKey(bankInterId);
		if(result > 0){
			return new Json(true, "银行接口删除成功!");
		}else{
			return new Json(false, "银行接口删除失败!");
		}
	}

	@Override
	public BankInterface findById(String bankinterId) {
		BankInterface bankItf = bankItfMapper.selectByPrimaryKey(bankinterId);
		return bankItf;
	}

	
	@Override
	public List<BankInterface> findAllData() {
		List<BankInterface> bankItfList = bankItfMapper.findAllData();
		return bankItfList;
	}

	@Override
	public List<BankInterface> findAppAllData() {
		List<BankInterface> bankItfList = bankItfMapper.findAppAllData();
		//listToObject(bankItfList);
		return bankItfList;
	}
	
	@Override
	public BankInterface findAppDataDetail(String bankInterId) {
		BankInterface bankItf = bankItfMapper.findAppDataDetail(bankInterId);
		//stringToObject(bankItf);
		return bankItf;
	}
	

	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月26日<br>
	 * 描述：查询出所有的银行信息--信用卡
	 * @return
	 */
	@Override
    public List<BankInterface> findAppCreAllData(){
    	return   bankItfMapper.findAppCreAllData();
    }
	
    
    /**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月26日<br>
	 * 描述：查询出所有的银行信息---储蓄卡
	 * @return
	 */
	@Override
    public List<BankInterface> findAppDepAllData(){
    	return   bankItfMapper.findAppDepAllData();
	}
}
