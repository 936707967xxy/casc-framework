package com.hoomsun.message.server.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.message.dao.NameAuthentMapper;
import com.hoomsun.message.dao.NoticeMapper;
import com.hoomsun.message.helper.PushHelper;
import com.hoomsun.message.model.NameAuthentication;
import com.hoomsun.message.model.Notice;
import com.hoomsun.message.server.inter.NoticeServerI;


@Service("NoticeServer")
public class NoticeServerImpl implements NoticeServerI {

	@Autowired
	private NoticeMapper noticeMapper;
	
	@Autowired
	private NameAuthentMapper  nameAuthentMapper;
	
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018-01-30<br>
	 * 描述：删除 
	 * @param notice
	 * @return
	 */
	public int deleteByPrimaryKey(String noticeid){
		return noticeMapper.deleteByPrimaryKey(noticeid);
    }
	
	public int insertSelective(Notice record){
		return noticeMapper.insertSelective(record);
	}
	
	public List<Notice>  selectByCustid(Map<String, Object> map){
		return noticeMapper.selectByCustid(map);
	}
	
	public  List<Notice> selectLatelynotice(String custid){
		return noticeMapper.selectLatelynotice(custid);
	}
	
	public List<Notice> selectallynotice(String custid){
		return noticeMapper.selectallynotice(custid);
	}
	
	public int updateAll(String custid){
		return noticeMapper.updateAll(custid);
	}
	public Map<String, Object> selectCountByCustid(Map<String, Object> map){
		return noticeMapper.selectCountByCustid(map);
	}
    
	public Notice selectDataByCustid(Map<String, Object> map){
		return noticeMapper.selectDataByCustid(map);
	}

	@Override
	public Json createNotice(Notice notice) {		
		if(StringUtils.isBlank(notice.getNoticeid())){
			notice.setNoticeid(PrimaryKeyUtil.getPrimaryKey());
		}
		notice.setConsult("0");
		notice.setNoticeData(DateUtil.getTimestamp());
		int result = noticeMapper.insertSelective(notice);
		if(result>0){
			return new Json(true, "推送消息添加成功");
		}else{
			return new Json(false, "推送消息添加失败");
		}
	}
	
	/**
	 * 
	 * 作者：刘栋梁 <br>
	 * 创建时间：2017年12月20日 <br>
	 * 描述： 添加推送消息--所有推送 
	 * @param notice
	 * @return
	 */
	@Override
	public Json createNoticeAll(Notice notice){
		if(notice==null){
			return new Json(false, "填写信息再发送");
		}
		int result =0;
		List<NameAuthentication> list = nameAuthentMapper.selectAllData();
		for (NameAuthentication hs : list) {
			notice.setNoticeid(PrimaryKeyUtil.getPrimaryKey());
			notice.setApplyid("");
			notice.setCustid(hs.getId());
			notice.setConsult("0");
			notice.setNoticeData(DateUtil.getTimestamp());
			result =noticeMapper.insertSelective(notice);
		}
		if(result>0){
			return new Json(true, "推送消息添加成功");
		}else{
			return new Json(false, "推送消息添加失败");
		}
	}
	
	
	@Override
	public Pager<Notice> findPage(Integer page, Integer rows, String custid) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(page == null || rows == null){
			page = 1;
		}
		if(rows == null){
			rows = 10;
		}
		rows = rows>50?50:rows;
		if(!StringUtils.isBlank(custid)){
			param.put("custid", "%" + custid + "%");
		}
		param.put("pageIndex", page);
		param.put("pageSize", rows);
		
		List<Notice> notices = noticeMapper.findPageData(param);
		int total = noticeMapper.findPageCount(param);
		return new Pager<Notice>(notices,total);
	}

	public Map<String, Object> selectSumByCustid(String custid){
		 return noticeMapper.selectSumByCustid(custid);
	 }
	
	public int updateByPrimaryKeySelective(Notice record){
		return noticeMapper.updateByPrimaryKeySelective(record);
	}
	
	public int updateAllById(Notice record){
		return noticeMapper.updateAllById(record);
	}
	
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
	@Override
	public Map<String, Object> sendMsg(String applyId,String message,Integer flag){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(StringUtils.isBlank(applyId) || StringUtils.isBlank(message)  ){
				result.put("errorInfo", "参数不全");
				result.put("errorCode", 1);
				return result;
			}
			
			Map<String, Object> apply = nameAuthentMapper.selectApplyByID(applyId);
			if(apply==null){
				result.put("errorInfo", "申请单查询不到");
				result.put("errorCode", 1);
				return result;
			}
			
			/*if(StringUtils.isBlank(apply.get("CUST_ID")+"")){
				result.put("errorInfo", "客户id查询不到");
				result.put("errorCode", 1);
				return result;
			}*/
			String Custid=apply.get("CUST_ID")+"";
			Notice notice=new Notice();
			notice.setNoticeid(PrimaryKeyUtil.getPrimaryKey());
			notice.setApplyid(applyId);
			notice.setCustid(Custid);
			notice.setConsult("0");
			notice.setNoticeData(DateUtil.getTimestamp());
			notice.setFlag(flag+"");
			String flagVal="";
			switch (flag) {
			case 1:
				flagVal="审核消息";
				break;
			case 2:
				flagVal="提醒消息";
				break;
			case 3:
				flagVal="系统消息";
				break;
			case 4:
				flagVal="额度消息";
				break;
			case 5:
				flagVal="精选活动";
				break;
			case 6:
				flagVal="认证消息";
				break;
			case 7:
				flagVal="签约消息";
				break;
			}
			notice.setFlagVal(flagVal);
			notice.setMessage(message);
		    noticeMapper.insertSelective(notice);
			
		    NameAuthentication  nameAuth=nameAuthentMapper.selectByPrimaryKey(Custid);
		    if(nameAuth==null){
		    	result.put("errorInfo", "客户查询不到");
				result.put("errorCode", 1);
				return result;
		    }
		    List<String> Relist=new ArrayList<String>();
		    Relist.add(nameAuth.getRegistrationid()+"");		
		    
		    // 信息推送
		 	PushHelper.SendPushWithTitle(Relist, message, flagVal, flag+"");
		} catch (Exception e) {
			e.printStackTrace();			
		}
	 	result.put("errorInfo", "发送完成");
		result.put("errorCode", 0);
		return result;
	}
	
}
