package com.hoomsun.message.server.inter;

import java.util.List;
import java.util.Map;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.message.model.Notice;



public interface NoticeServerI {

	int insertSelective(Notice record);
	
	List<Notice>  selectByCustid(Map<String, Object> map);
	
	List<Notice> selectLatelynotice(String custid);
	
    List<Notice> selectallynotice(String custid);
    
    int updateAll(String custid);
	
	Map<String, Object> selectCountByCustid(Map<String, Object> map);
	    
	Notice selectDataByCustid(Map<String, Object> map);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018-01-30<br>
	 * 描述：删除 
	 * @param notice
	 * @return
	 */
    int deleteByPrimaryKey(String noticeid);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年11月1日 <br>
	 * 描述： 添加推送消息 
	 * @param notice
	 * @return
	 */
	Json createNotice(Notice notice);
	
	/**
	 * 
	 * 作者：刘栋梁 <br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： 添加推送消息--所以推送 
	 * @param notice
	 * @return
	 */
	Json createNoticeAll(Notice notice);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2017年11月1日 <br>
	 * 描述： 分页查询所有推送消息
	 * @param page 页码
	 * @param rows 每页行数
	 * @param custid 客户id
	 * @return 
	 */
	Pager<Notice> findPage(Integer page, Integer rows, String custid);
	
	Map<String, Object> selectSumByCustid(String custid);
	 
	int updateByPrimaryKeySelective(Notice record);
	
	int updateAllById(Notice record);
	
	/**
	 * 
	 * 作者：liudongliang <br>
	 * 创建时间：2017年12月29日 <br>
	 * 描述： 审核/贷后推送消息
	 * @param applyId    申请id
	 * @param message    信息
	 * @param flag       消息类型
	 * @return 
	 */
	Map<String, Object> sendMsg(String applyId,String message,Integer flag);
	
}
