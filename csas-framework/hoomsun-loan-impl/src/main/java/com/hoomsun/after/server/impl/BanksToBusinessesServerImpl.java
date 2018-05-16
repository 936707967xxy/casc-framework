package com.hoomsun.after.server.impl;

import java.io.IOException;

import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;

import com.hoomsun.after.api.model.param.Bank2BusinessParam;
import com.hoomsun.after.api.model.vo.Bank2BusinessModel;
import com.hoomsun.after.api.server.BanksToBusinessesServer;
import com.hoomsun.after.api.util.bank2BusinessUtil.B2BHttpRequest;
/**
 * 
 * 作者：zwLiu <br>
 * 创建时间：2018年4月25日 <br>
 * 描述：针对银企直连 
 *
 */
@Service("banksToBusinessesServer")
public class BanksToBusinessesServerImpl implements BanksToBusinessesServer {	
	/**
	 * 获取区间交易信息
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	@Override
	public Bank2BusinessModel getTradingInformationInterval(Bank2BusinessParam b2b) throws IOException{

		return B2BHttpRequest.getTransInfo(b2b);
	}
	/**
	 *账户交易信息断点查询
	 * @throws DocumentException 
	 */
	@Override
	public Bank2BusinessModel getTransInfoEX(Bank2BusinessParam b2b) throws IOException{
		
		return B2BHttpRequest.getTransInfoEX(b2b);
	}
}
