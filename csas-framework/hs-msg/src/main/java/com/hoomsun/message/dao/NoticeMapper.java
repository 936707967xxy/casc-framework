package com.hoomsun.message.dao;

import java.util.List;
import java.util.Map;
import com.hoomsun.message.model.Notice;


public interface NoticeMapper {
    int deleteByPrimaryKey(String noticeid);

    int insert(Notice record);

    int insertSelective(Notice record);

    Notice selectByPrimaryKey(String noticeid);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKey(Notice record);
    
    List<Notice>  selectByCustid(Map<String, Object> map);
    
    List<Notice> selectLatelynotice(String custid);
    
    List<Notice> selectallynotice(String custid);
    
    int updateAll(String custid);
    
    Map<String, Object> selectCountByCustid(Map<String, Object> map);
    
    Notice selectDataByCustid(Map<String, Object> map);
    
    List<Notice>  findPageData(Map<String, Object> map);
    
    int findPageCount(Map<String, Object> map);
    
    Map<String, Object> selectSumByCustid(String custid);
    
    int updateAllById(Notice record);
}