package com.hoomsun.csas.settle.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.csas.settle.model.LoanRecord;
import com.hoomsun.csas.settle.model.vo.LoanVo;

public interface LoanRecordMapper {
	int deleteByPrimaryKey(String loanId);

	int insert(LoanRecord record);

	int insertSelective(LoanRecord record);

	LoanRecord selectByPrimaryKey(String loanId);

	int updateByPrimaryKeySelective(LoanRecord record);

	int updateByPrimaryKey(LoanRecord record);

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月19日 <br>
	 * 描述： 获取分页数据
	 * 
	 * @param param
	 * @return
	 */
	List<LoanVo> findPager(Map<String, Object> param);

	int findPagerCount(Map<String, Object> param);
	
	LoanRecord selectByTaskIdAndApplyId(@Param("applyId")String applyId,@Param("taskId")String taskId);
	
	Integer checkByApplyId(String applyId);
	
	Integer checkByConId(String conId);
	
	/**
	 * 
	 * 作者：liming<br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述： 根据申请id查询放款数据
	 * @param applyId
	 * @return
	 */
	LoanVo selectByApplyId(String applyId);

	LoanRecord findByConId(String conId);
	
	LoanRecord findByApplyId(String applyId);
	
	LoanRecord findApplyIds(String applyId);
	
	
	
	
}