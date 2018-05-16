package com.hoomsun.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hoomsun.core.model.SysJob;
import com.hoomsun.core.model.vo.VueLazyTree;

public interface SysJobMapper {
    int deleteByPrimaryKey(String jobId);

    int insert(SysJob record);

    int insertSelective(SysJob record);

    SysJob selectByPrimaryKey(String jobId);

    int updateByPrimaryKeySelective(SysJob record);

    int updateByPrimaryKey(SysJob record);
    /**
     * 作者：ywzou <br>
     * 创建时间：2017年9月12日 <br>
     * 描述： 查询满足条件的数据
     * @param param
     * @return
     */
	List<SysJob> findPageData(Map<String, Object> param);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月12日 <br>
	 * 描述： 查询满足条件的数据的量
	 * @param param
	 * @return
	 */
	Integer findPageCount(Map<String, Object> param);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月12日 <br>
	 * 描述： 查询出所有的数据
	 * @return
	 */
	List<SysJob> findAll();
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月12日 <br>
	 * 描述： 根据名称查询
	 * @param jobName
	 * @return
	 */
	SysJob findByName(String jobName);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月9日 <br>
	 * 描述： vue tree数据
	 * @param jobParent
	 * @return
	 */
	List<VueLazyTree> findVueTreeData(@Param("jobParent") String jobParent);
	
	/**
	 * 
	 * 作者：liushuai <br>
	 * 创建时间：2018年1月10日 <br>
	 * 描述： 职位修改显示
	 * @param jobId
	 * @return
	 */
	SysJob selectByJobId(String jobId);
}