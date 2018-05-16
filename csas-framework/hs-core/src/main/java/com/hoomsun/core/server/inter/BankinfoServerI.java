package com.hoomsun.core.server.inter;

import java.util.List;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.Bankinfo;



/**
 * 作者：liudongliang <br>
 * 创建时间：2017年9月14日<br>
 * 描述：APP银行卡信息
 * @return
 */
public interface BankinfoServerI {
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：添加银行卡时下拉列表
	 * 
	 * @return
	 */
	List<Bankinfo> findAllData();

	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12-07日<br>
	 * 描述：分页
	 * @param page 当前页码
	 * @param rows 每页数据量
	 * @param type 类型
	 * @return
	 */
	Pager<Bankinfo> findPage(Integer page,Integer rows,String bankname);

	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12-07日<br>
	 * 描述：修改银行
	 * @param bankinfo   修改银行
	 * @return
	 */
	Json updateBankinfo(Bankinfo bankinfo);
	

    Json addBankinfo(Bankinfo bankinfo);
    
    Json deleteBankinfo(String pid);
    
    Bankinfo bankinfo(String bankId);
    
}
