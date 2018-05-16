package com.hoomsun.csas.sales.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.sales.api.model.Annex;


public interface AnnexMapper {
    int deleteByPrimaryKey(String anxId);

    int insert(Annex record);

    int insertSelective(Annex record);

    Annex selectByPrimaryKey(String anxId);

    int updateByPrimaryKeySelective(Annex record);

    int updateByPrimaryKey(Annex record);

	List<Annex> findByApplyIdImgType(@Param("applyId")String applyId, @Param("imgType")Integer imgType);
	
	Integer findByApplyIdImgTypeMulti(@Param("applyId")String applyId);
	
	/**
	 * 根据applyId查询上传表看是否唯一
	 * @return
	 */
	Integer findAnnexByApplyId(@Param("applyId")String applyId);

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2018年1月25日 <br>
	 * 描述：上传图片回显
	 */
	List<Map<String, Object>> queryReView(@Param("applyId")String applyId,@Param("imgType")Integer imgType);
}