/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.app.api.server.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoomsun.app.api.dao.HotHeadlineMapper;
import com.hoomsun.app.api.model.Banner;
import com.hoomsun.app.api.model.HotHeadline;
import com.hoomsun.app.api.server.inter.HotHeadlineServerI;
import com.hoomsun.common.model.DataGrid;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.CreateHtml;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.common.util.UploadPathUtil;

/**
 * 作者：liushuai <br>
 * 创建时间：2017年9月19日 <br>
 * 描述：通知公告业务实现
 */
@Service("hotHeadlineServer")
public class HotHeadlineServerImpl implements HotHeadlineServerI {
	private final static Logger log = LoggerFactory.getLogger(BankInterfaceServerImpl.class);
	private HotHeadlineMapper hotHeadlineMapper;
	private UploadPathUtil uploadPathUtil;
	
	@Autowired
	public void setHotHeadlineMapper(HotHeadlineMapper hotHeadlineMapper) {
		this.hotHeadlineMapper = hotHeadlineMapper;
	}
	
	@Autowired
	public void setUploadPathUtil(UploadPathUtil uploadPathUtil) {
		this.uploadPathUtil = uploadPathUtil;
	}


	@Override
	public Json createHotHeadline(String path, HotHeadline hotHeadLine) {
		if (StringUtils.isBlank(hotHeadLine.getHotId())) {
			hotHeadLine.setHotId(PrimaryKeyUtil.getPrimaryKey());
		}
		createHtmlFromTmp(path, hotHeadLine);
		hotHeadLine.setVisitCount(0); // 设置访问次数为0
		int result = hotHeadlineMapper.insertSelective(hotHeadLine);
		if(result > 0){
			return new Json(true, "通知公告添加成功!");
		}else{
			log.error("####:通知公告添加失败!");
			return new Json(false, "通知公告添加失败!");
		}
	}

	@Override
	public Json updateHotHeadline(String path, HotHeadline hotHeadLine) {
		createHtmlFromTmp(path, hotHeadLine);
		int result = hotHeadlineMapper.updateByPrimaryKeySelective(hotHeadLine);
		if(result > 0){
			return new Json(true, "通知公告更新成功!");
		}else{
			return new Json(false, "通知公告更新失败!");
		}
	}

	@Override
	public Json deleteHotHeadline(String hotId) {
		int result = hotHeadlineMapper.deleteByPrimaryKey(hotId);
		if(result > 0){
			return new Json(true, "通知公告删除成功!");
		}else{
			return new Json(false, "通知公告删除失败!");
		}
	}

	@Override
	public HotHeadline findById(String hotId) {
		return hotHeadlineMapper.selectByPrimaryKey(hotId);
	}

	@Override
	public List<HotHeadline> findAllData() {
		return hotHeadlineMapper.findAllData();
	}
	
	@Override
	public List<HotHeadline> findAllOpenData() {
		return hotHeadlineMapper.findAllOpenData();
	}
	@Override
	public Pager<HotHeadline> findPage(Integer page, Integer rows,String headline) {
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

		if (!StringUtils.isBlank(headline)) {
			param.put("headline", "%" + headline + "%");
		}

		List<HotHeadline> headlines = hotHeadlineMapper.findPageData(param);
		int total = hotHeadlineMapper.findPageCount(param);
		
		return new Pager<HotHeadline>(headlines, total);
	}
	
	/**
	 * 作者：liushuai <br>
	 * 创建时间：2017年9月20日 <br>
	 * 描述： 通过模板生成html并对通知公告进行相应的赋值
	 * @param path
	 * @param editor
	 * @param hotHeadLine
	 */
	@SuppressWarnings("static-access")
	private void createHtmlFromTmp(String path, HotHeadline hotHeadLine){
		String time = DateUtil.getCurrentTime();
		String viewPath = uploadPathUtil.noticeUrl();
		String saveName = new Date().getTime() + ".html"; // 以时间戳的形式存储文件名
		String setpathDir = uploadPathUtil.saveNoticePath()  + File.separator + "html";
		File f = new File(setpathDir);
		if (!f.exists()) {
			f.mkdirs();
		}
		String setpath = setpathDir + File.separator + saveName;
		new CreateHtml().create(path, setpath, hotHeadLine.getContent(), hotHeadLine.getHeadline() ,time);
		hotHeadLine.setTime(time);
		hotHeadLine.setContentUrl(viewPath + "/html/" + saveName);
	}
	
	
	@SuppressWarnings("static-access")
	@Override
	public String createHtmlFromTmp(String path, String headline, String content){
		String time = DateUtil.getCurrentTime();
		String saveName = new Date().getTime() + ".html"; // 以时间戳的形式存储文件名
		String setpathDir = uploadPathUtil.saveNoticePath()  + File.separator + "html";
		File f = new File(setpathDir);
		if (!f.exists()) {
			f.mkdirs();
		}
		String setpath = setpathDir + File.separator + saveName;
		new CreateHtml().create(path, setpath, content,headline,time);
		return saveName;
	}

	public List<HotHeadline> findAllAppBannerData(){
		return  hotHeadlineMapper.findAllAppBannerData();
	}
	
}
