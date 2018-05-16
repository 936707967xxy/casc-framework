/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.app.api.server.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hoomsun.app.api.dao.BannerMapper;
import com.hoomsun.app.api.model.Banner;
import com.hoomsun.app.api.server.inter.BannerServerI;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.common.util.PrimaryKeyUtil;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年9月13日 <br>
 * 描述：
 */
@Service("bannerServer")
public class BannerServerImp implements BannerServerI {
	private final static Logger log = LoggerFactory.getLogger(BannerServerImp.class);
	private BannerMapper bannerMapper;

	@Autowired
	public void setBannerMapper(BannerMapper bannerMapper) {
		this.bannerMapper = bannerMapper;
	}

	
	@Override
	public Json createBanner(Banner banner) {
		if (StringUtils.isBlank(banner.getId())) {
			banner.setId(PrimaryKeyUtil.getPrimaryKey());
		}
		
		banner.setTime(DateUtil.getCurrentTime());
		
		int result = bannerMapper.insertSelective(banner);
		if (result > 0) {
			return new Json(true, "banner添加成功");
		} else {
			return new Json(false, "banner添加失败");
		}
	}

	


	@Override
	public Json updateBanner(Banner banner) {
		banner.setTime(DateUtil.getCurrentTime());
		
		int result = bannerMapper.updateByPrimaryKeySelective(banner);
		if (result > 0) {
			return new Json(true, "banner更新成功");
		} else {
			return new Json(false, "banner更新失败");
		}
	}

	// TODO 删除时候删除服务器上文件
	@Override
	public Json deleteBanner(String id) {
		int result = bannerMapper.deleteByPrimaryKey(id);
		if (result > 0) {
			return new Json(true, "banner删除成功");
		} else {
			return new Json(false, "banner删除失败");
		}
	}

	@Override
	public Banner findById(String id) {
		return bannerMapper.selectByPrimaryKey(id);
	}

	@Override
	public Pager<Banner> findPage(Integer page, Integer rows) {
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

		List<Banner> banners = bannerMapper.findPageData(param);
		int total = bannerMapper.findPageCount(param);
		return new Pager<Banner>(banners, total);
	}

	@Override
	public List<Banner> findAllData() {
		return bannerMapper.findAllData();
	}
	
	@Override
	public List<Banner> selectByisopen(){
		return bannerMapper.selectByisopen();
	}

}
