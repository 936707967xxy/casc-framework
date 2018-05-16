/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限银行接口
 */
package com.hoomsun.app.api.server.inter;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.app.api.model.BankInterface;
import com.hoomsun.common.model.DataGrid;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年9月26日 <br>
 * 描述：银行接口管理的业务接口
 */
public interface BankInterfaceServerI {

	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12-07日<br>
	 * 描述：创建银行接口
	 * @param bankItf 银行接口添加数据信息
	 * @param 
	 * @return
	 */
	Json addBankInterFace(BankInterface bankItf);
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12-07日<br>
	 * 描述：分页
	 * @param page 当前页码
	 * @param rows 每页数据量
	 * @param type 类型
	 * @return
	 */
	Pager<BankInterface> findPage(Integer page,Integer rows,String bankName);
	/**
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12-07日<br>
	 * 描述：修改银行接口
	 * @param bankItf 银行接口修改数据信息
	 * @return
	 */
	Json updateBankItf(BankInterface bankItf);
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月26日<br>
	 * 描述：删除银行接口 银行接口ID
	 * @param bankInterId
	 * @return
	 */
	Json deleteBankItf(String bankInterId);
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月26日<br>
	 * 描述：根据主键查询银行接口信息
	 * @param bankInterId 银行接口ID
	 * @return
	 */
	BankInterface findById(String bankinterId);

	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月26日<br>
	 * 描述：查询出所有的银行信息
	 * @return
	 */
	List<BankInterface> findAllData();
	
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月26日<br>
	 * 描述：查询出所有open状态的银行接口值信息
	 * @return
	 */
	List<BankInterface> findAppAllData();
	
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月26日<br>
	 * 描述：查询出当前 bankInterId open状态的银行接口值信息
	 * @return
	 */
	BankInterface findAppDataDetail(String bankInterId); 
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月26日<br>
	 * 描述：查询出所有的银行信息--信用卡
	 * @return
	 */
    List<BankInterface> findAppCreAllData();
	
    
    /**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月26日<br>
	 * 描述：查询出所有的银行信息---储蓄卡
	 * @return
	 */
	List<BankInterface> findAppDepAllData();
}

