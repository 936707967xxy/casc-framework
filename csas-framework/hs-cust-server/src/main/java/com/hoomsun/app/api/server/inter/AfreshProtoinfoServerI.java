package com.hoomsun.app.api.server.inter;

import java.util.List;
import java.util.Map;

import com.hoomsun.app.api.model.AfreshProtoinfo;


/**
 * 作者：liudongliang <br>
 * 创建时间：2017年9月14日<br>
 * 描述：银行卡添加 
 * 
 * @return
 */
public interface AfreshProtoinfoServerI {

	 int insertSelective(AfreshProtoinfo record);
	 
	 int updateByPrimaryKeySelective(AfreshProtoinfo record);
	
	 List<AfreshProtoinfo>  selectByFkid(String fkId);
	 
	 int updateByType(String fkId);
	 
	 int updateByProtoinfoId(Map<String,Object>  para);
	 
	 int deleteByProtoinfoId(String protoinfoId);
	 
	 
	 AfreshProtoinfo selectByPrimaryKey(String protoinfoId);
	 
	 AfreshProtoinfo selectByFkIdAndIsDefault(String applyId);
	 
	 List<AfreshProtoinfo>  selectByAccno(String accno);
}
