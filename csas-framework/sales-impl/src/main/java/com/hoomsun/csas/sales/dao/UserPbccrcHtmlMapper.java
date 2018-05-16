package com.hoomsun.csas.sales.dao;

import com.hoomsun.csas.sales.api.model.UserPbccrcHtml;

public interface UserPbccrcHtmlMapper {
    int deleteByPrimaryKey(String phId);

    int insert(UserPbccrcHtml record);

    int insertSelective(UserPbccrcHtml record);

    UserPbccrcHtml selectByPrimaryKey(String phId);

    int updateByPrimaryKeySelective(UserPbccrcHtml record);

    int updateByPrimaryKeyWithBLOBs(UserPbccrcHtml record);

    int updateByPrimaryKey(UserPbccrcHtml record);
    /**
     * 作者：ywzou <br>
     * 创建时间：2017年11月18日 <br>
     * 描述： 更具申请id查询
     * @param applyId
     * @return
     */
    UserPbccrcHtml selectByApplyId(String applyId);
    /**
     * 作者：ywzou <br>
     * 创建时间：2017年11月18日 <br>
     * 描述： 更具征信第获取
     * @param crId
     * @return
     */
    UserPbccrcHtml selectByCrId(String crId);
}