package com.hoomsun.after.dao;

import java.util.List;

import com.hoomsun.after.api.model.table.OutBound;

public interface OutBoundMapper {
    int deleteByPrimaryKey(String id);

    int insert(OutBound record);

    int insertSelective(OutBound record);

    OutBound selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OutBound record);

    int updateByPrimaryKey(OutBound record);
    /**
     * 
     * 作者：shiqiangjin <br>
     * 创建时间：2018年4月25日 <br>
     * 描述： 根据申请ID 查询外访申请详情
     * @param applyId
     * @return
     */
	OutBound getOutbound(String applyId);
	 /**
     * 
     * 作者：shiqiangjin <br>
     * 创建时间：2018年4月25日 <br>
     * 描述： 根据申请LOANID 查询外访申请详情
     * @param applyId
     * @return
     */
	List<OutBound> selectByLoanId(String loanId);
	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年4月26日 <br>
	 * 描述： 修改外访为催收完毕失效3
	 * @param loanId
	 */
	void updateOutBoundStatusToOne(String loanId);
}