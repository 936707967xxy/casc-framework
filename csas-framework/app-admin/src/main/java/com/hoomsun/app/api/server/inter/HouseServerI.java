package com.hoomsun.app.api.server.inter;

import java.util.List;

import com.hoomsun.app.api.model.House;
import com.hoomsun.app.api.model.Social;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;

public interface HouseServerI {

	Json addHouse(House house);
	
	/**
	 * 作者：刘栋梁<br>
	 * 创建时间：2017年12月06日<br>
	 * 描述：分页查询公积金 数据信息
	 * @param page 当前页码
	 * @param rows 每页数据量
	 * @param type 类型
	 * @return
	 */
	Pager<House> findPage(Integer page,Integer rows,String province);
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12月6日<br>
	 * 描述：修社保
	 * @param House 公积金修改数据信息
	 * @return
	 */
	Json updateHouse(House house);
	
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12月6日<br>
	 * 描述：删除公积金
	 * @param House 公积金删除数据信息
	 * @return
	 */
	Json deleteHouse(String houseId);
	
	List<House>  selectByProvince(String provinceid);
	
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12月6日<br>
	 * 描述：获取公积金接口
	 * @param uniqueKey
	 * @return 
	 */
	House selectApiByUniqueKey(String uniqueKey);
}
