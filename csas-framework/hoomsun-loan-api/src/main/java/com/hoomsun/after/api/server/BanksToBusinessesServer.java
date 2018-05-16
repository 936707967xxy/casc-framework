package com.hoomsun.after.api.server;

import java.io.IOException;

import com.hoomsun.after.api.model.param.Bank2BusinessParam;
import com.hoomsun.after.api.model.vo.Bank2BusinessModel;

/**
 * 
 * 作者：zwLiu <br>
 * 创建时间：2018年4月25日 <br>
 * 描述：银企直连 
 *
 */
public interface BanksToBusinessesServer {

	Bank2BusinessModel getTradingInformationInterval(Bank2BusinessParam b2b) throws IOException;

	Bank2BusinessModel getTransInfoEX(Bank2BusinessParam b2b) throws IOException;
}
