package com.hoomsun.csas.sales.dao;

import java.util.List;
import java.util.Map;

import com.hoomsun.csas.sales.api.model.CmrateProduct;


public interface CmrateProductMapper {
    int deleteByPrimaryKey(String cmrateId);

    int insert(CmrateProduct record);

    int insertSelective(CmrateProduct record);

    CmrateProduct selectByPrimaryKey(String cmrateId);

    int updateByPrimaryKeySelective(CmrateProduct record);

    int updateByPrimaryKey(CmrateProduct record);
    
    List<Map<String, Object>>   findAllDataApp(Map<String, Object> record);
    
    /**
     * 作者：ygzhao <br>
     * 创建时间：2017年11月23日 <br>
     * 描述：提交申请查询利率
     */
    CmrateProduct findDataApp(String prodid);
    
    Map<String, Object> findDataAppBill(String prodid);
    
    List<CmrateProduct> findPageData(Map<String,Object> param);
    /**
	 * 作者：liusong<br>
	 * 创建时间：2017年9月11日<br>
	 * 描述：满足条件的数据量
	 * @param param 筛选条件 keys:pageIndex,pageSize,roleName
	 * @return
	 */
	int findPageCount(Map<String, Object> param);
    
    /**
     * 作者：ywzou <br>
     * 创建时间：2017年9月18日 <br>
     * 描述： 得到所有的数据
     * @return
     */
    List<CmrateProduct> findAllData();
}