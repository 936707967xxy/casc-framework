package com.hoomsun.app.api.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.app.api.dao.HouseMapper;
import com.hoomsun.app.api.model.House;
import com.hoomsun.app.api.model.Social;
import com.hoomsun.app.api.server.inter.HouseServerI;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.PrimaryKeyUtil;

@Service("houseServerImpl")
public class HouseServerImpl implements HouseServerI{

	
	@Autowired
	private HouseMapper  HouseMapper;
	
	@Override
	public  Json addHouse(House house){
		
		if(StringUtils.isBlank(house.getHouseId())){		
			house.setHouseId(PrimaryKeyUtil.getPrimaryKey());
		}
		
		int result=HouseMapper.insertSelective(house);
		if(result > 0){
			return new Json(true, "公积金添加成功!");
		}else{
			return new Json(false, "公积金添加失败!");
		}
	}
	
	

	/**
	 * 作者：刘栋梁<br>
	 * 创建时间：2017年12月06日<br>
	 * 描述：分页查询公积金 数据信息
	 * @param page 当前页码
	 * @param rows 每页数据量
	 * @param type 类型
	 * @return
	 */
	@Override
	public Pager<House> findPage(Integer page,Integer rows,String province){
		Map<String, Object> param = new HashMap<String, Object>();
		if (page == null || rows == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 10;
		}
		rows = rows > 50 ? 50 : rows;
		
		param.put("pageIndex", page);
		param.put("pageSize", rows);

		if (!StringUtils.isBlank(province)) {
			param.put("province", "%"+province+"%");
		}
		
		List<House> house = HouseMapper.findPageData(param);
		int total = HouseMapper.findPageCount(param);
		return new Pager<House>( house,total);
	}
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12月6日<br>
	 * 描述：修gongjijin
	 * @param social 公积金修改数据信息
	 * @return
	 */
	@Override
	public Json updateHouse(House house){
		int result = HouseMapper.updateByPrimaryKeySelective(house);
		if(result > 0){
			return new Json(true, "公积金修改成功!");
		}else{
			return new Json(false, "公积金修改失败!");
		}
	}
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12月6日<br>
	 * 描述：删除公积金
	 * @param House 公积金删除数据信息
	 * @return
	 */
	@Override
	public Json deleteHouse(String houseId){
		int result = HouseMapper.deleteByPrimaryKey(houseId);
		if(result > 0){
			return new Json(true, "删除公积金成功!");
		}else{
			return new Json(false, "删除公积金失败!");
		}
	}
	
	@Override
	public List<House>  selectByProvince(String provinceid){
		return HouseMapper.selectByProvince(provinceid);
	}
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12月6日<br>
	 * 描述：获取公积金接口
	 * @param uniqueKey
	 * @return 
	 */
	@Override
	public House selectApiByUniqueKey(String uniqueKey){
		return HouseMapper.selectApiByUniqueKey(uniqueKey);
	}
}
