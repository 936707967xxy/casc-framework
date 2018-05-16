/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.app.api.server.inter;

import java.util.List;

import com.hoomsun.app.api.model.HotHeadline;
import com.hoomsun.common.model.DataGrid;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;

/**
 * 作者：liudongliang <br>
 * 创建时间：2017年9月19日 <br>
 * 描述：通知公告业务接口
 */
public interface HotHeadlineServerI {
	
	Pager<HotHeadline> findPage(Integer page, Integer rows,String headline);
	
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：创建通知公告
	 * @param hotHeadLine 要添加的通知公告信息
	 * @return
	 */
	Json createHotHeadline(String path, HotHeadline hotHeadLine);
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：修改通知公告
	 * @param hotHeadLine 修改通知公告数据信息
	 * @return
	 */
	Json updateHotHeadline(String path, HotHeadline hotHeadLine);
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：删除通知公告
	 * @param hotId 通知公告主键
	 * @return
	 */
	Json deleteHotHeadline(String hotId);
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：根据主键查询通知公告信息
	 * @param hotId 通知公告主键
	 * @return
	 */
	HotHeadline findById(String hotId);
	
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：查询出所有的通知公告信息
	 * @return
	 */
	List<HotHeadline> findAllData();
	
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月14日<br>
	 * 描述：查询出所有打开状态通知公告信息
	 * @return
	 */
	List<HotHeadline> findAllOpenData();
	
	/**
	 * 作者：liushuai<br>
	 * 创建时间：2017年9月20日<br>
	 * 描述：将页面通知公告信息存入模板
	 * @param path 模板路径
	 * @param headline 模板标题
	 * @param content, 模板内容
	 */
	String createHtmlFromTmp(String path, String headline, String content);
	
	
	List<HotHeadline> findAllAppBannerData();
	
    
}
