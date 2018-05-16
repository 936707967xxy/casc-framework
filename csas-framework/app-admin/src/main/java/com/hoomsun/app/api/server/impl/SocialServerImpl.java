package com.hoomsun.app.api.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.app.api.dao.SocialMapper;
import com.hoomsun.app.api.model.Social;
import com.hoomsun.app.api.server.inter.SocialServerI;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.core.util.PrimaryKeyUtil;

@Service("socialServerImpl")
public class SocialServerImpl implements SocialServerI{

	@Autowired
	private SocialMapper  socialMapper;
	
	@Override
	public Json addSocial(Social social) {
		if (StringUtils.isBlank(social.getSocialId())) {
			social.setSocialId(PrimaryKeyUtil.getPrimaryKey());
		}	
		int result = socialMapper.insertSelective(social);
		if(result > 0){
			return new Json(true, "社保添加成功!");
		}else{
			return new Json(false, "社保添加失败!");
		}
	}
	
	@Override
	 public Pager<Social> findPage(Integer page,Integer rows,String province){
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

		List<Social> version = socialMapper.findPageData(param);
		int total = socialMapper.findPageCount(param);
		return new Pager<Social>( version,total);
	}
	
	/**
	 * 作者：刘栋梁<br>
	 * 创建时间：2017年12月05日<br>
	 * 描述：查找修改数据
	 */
	@Override
	public Social selectByPrimaryKey(String socialId){
		return socialMapper.selectByPrimaryKey(socialId);
	}
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月6日<br>
	 * 描述：修社保
	 * @param social 社保修改数据信息
	 * @return
	 */
	@Override
	public Json updateSocial(Social social){
		int result = socialMapper.updateByPrimaryKeySelective(social);
		if(result > 0){
			return new Json(true, "社保修改成功!");
		}else{
			return new Json(false, "社保修改失败!");
		}
	}
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12月5日<br>
	 * 描述：删除社保
	 * @param  socialId 删除社保数据信息
	 * @return
	 */
	@Override
	public Json deleteSocial(String socialId){
		int result = socialMapper.deleteByPrimaryKey(socialId);
		if(result > 0){
			return new Json(true, "删除社保成功!");
		}else{
			return new Json(false, "删除社保失败!");
		}
	}
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12月5日<br>
	 * 描述：满足该省下的城市
	 * @param param
	 * @return keys:pageIndex,pageSize,type
	 */
	@Override
	public List<Social> selectByProvince(String provinceid){
		return socialMapper.selectByProvince( provinceid);
	}
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年12月6日<br>
	 * 描述：查询接口值
	 * @param uniqueKey
	 * @return 
	 */
	@Override
	public Social  selectApiByUniqueKey(String uniqueKey){
		return socialMapper.selectApiByUniqueKey(uniqueKey);
	}
}
