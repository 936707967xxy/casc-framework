/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.inter;



import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.model.SerialNumber;




/**
 * 作者：liming <br>
 * 创建时间：2017年11月29日 <br>
 * 描述：生成序列号的业务层
 */
public interface SerialNumberServerI {
	
	/**
	 *  
	 * 作者：liming<br>
	 * 创建时间：2017年11月29日 <br>
	 * 描述： 创建编号规则
	 * @param serialnumber
	 * @return
	 */
	Json create(SerialNumber serialnumber);
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月30日 <br>
	 * 描述： 修改规则
	 * @param serialnumber
	 * @return
	 */
	Json updateSerialNumber(SerialNumber serialnumber);
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月30日 <br>
	 * 描述： 删除
	 * @param serialnumber
	 * @return
	 */
	Json deleteSerialNumber(String id);
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月30日 <br>
	 * 描述：根据id查询
	 * @param id
	 * @return
	 */
	SerialNumber findById(String id);
	
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月21日 <br>
	 * 描述： 预约信息的分页
	 * 
	 * @param page
	 * @param rows
	 * @param typeval(合同)
	 * @return
	 */
	Pager<SerialNumber> findPage(Integer page, Integer rows, String typeval);
    
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年11月30日 <br>
	 * 描述： 生成编号
	 * @param typeval 类型如合同
	 * @param prefix  区域
	 * @return
	 */
    String createNumber(String type,String prefix);
    
    String createSerialNumber(String type,String prefix);
}

