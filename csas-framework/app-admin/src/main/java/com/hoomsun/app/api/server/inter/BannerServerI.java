/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限banner
 */
package com.hoomsun.app.api.server.inter;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.app.api.model.Banner;
import com.hoomsun.common.model.DataGrid;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年9月13日 <br>
 * 描述：
 */
public interface BannerServerI {
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月13日<br>
	 * 描述：创建banner
	 * @param banner banner添加数据信息
	 * @return
	 */
	Json createBanner(Banner banner);
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月13日<br>
	 * 描述：修改banner
	 * @param banner banner修改数据信息
	 * @return
	 */
	Json updateBanner(Banner banner);
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月13日<br>
	 * 描述：删除banner bannerID
	 * @param id
	 * @return
	 */
	Json deleteBanner(String id);
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月13日<br>
	 * 描述：根据主键查询banner信息
	 * @param id bannerID
	 * @return
	 */
	Banner findById(String id);
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月13日<br>
	 * 描述：分页查询banner数据信息
	 * @param page 当前页码
	 * @param rows 每页数据量
	 * @return
	 */
	Pager<Banner> findPage(Integer page,Integer rows);
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月13日<br>
	 * 描述：查询出所有的banner信息
	 * @return
	 */
	List<Banner> findAllData();
	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：查询app需要展现数据
	 * 
	 * @return
	 */
	List<Banner> selectByisopen();
}
