package com.hoomsun.app.api.server.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.hoomsun.app.api.dao.VersionMapper;
import com.hoomsun.app.api.model.Version;
import com.hoomsun.app.api.server.inter.VersionServerI;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.common.util.UploadPathUtil;



@Service("versionServer")
public class VersionServerImpl implements VersionServerI{
	private final static Logger log = LoggerFactory.getLogger(VersionServerImpl.class);
	
	private VersionMapper verSionMapper;
	private UploadPathUtil uploadPathUtil;


	@Autowired
	public void setverSionMapper(VersionMapper verSionMapper) {
		this.verSionMapper = verSionMapper;
	}
	
	@Autowired
	public void setUploadPathUtil(UploadPathUtil uploadPathUtil) {
		this.uploadPathUtil = uploadPathUtil;
	}
	
	@Override
	public Version selectBytype(String type){
		return verSionMapper.selectBytype(type);
	}

	@Override
	public Json createVersion( Version version) {
		if (StringUtils.isBlank(version.getId())) {
			version.setId(PrimaryKeyUtil.getPrimaryKey());
		}
		int result = verSionMapper.insertSelective(version);
		if (result > 0) {
			return new Json(true, "版本信息添加成功");
		} else {
			return new Json(false, "版本信息添加失败");
		}
	}

	@Override
	public Json updateVersion( Version version) {
		int result = verSionMapper.updateByPrimaryKeySelective(version);
		if (result > 0) {
			return new Json(true, "版本信息更新成功");
		} else {
			return new Json(false, "版本信息更新失败");
		}
	}

	private void setBannerUrlByUploadPath(MultipartFile appFile, Version version) {
		if (!appFile.isEmpty() && !StringUtils.isBlank(appFile.getOriginalFilename())) {
			try {
				String viewPath = uploadPathUtil.versionUrl();
				String fileName = appFile.getOriginalFilename();
				//String saveAddPath = "" + new Date().getTime();
				//String fileDir = uploadPathUtil.saveVersionPath() + File.separator  +  saveAddPath;
				String fileDir = uploadPathUtil.saveVersionPath() + File.separator ;
				log.error("viewPath" +  viewPath);
				//log.error("saveAddPath" +  saveAddPath);
				log.error("fileDir" +  fileDir);
				File f = new File(fileDir);
				if (!f.exists()) {
					f.mkdirs();
				}
				// 文件保存路径
				String filePath =fileDir + File.separator + fileName;
				// 转存文件
				appFile.transferTo(new File(filePath));

				//version.setUrl(viewPath + saveAddPath + "/" + fileName);
				version.setUrl(viewPath  + fileName);
			} catch (Exception e) {
				log.error("APP上传服务器没有成功!");
			}
		}
	}
	
	@Override
	public Json deleteVersion(String id) {
		int result = verSionMapper.deleteByPrimaryKey(id);
		if (result > 0) {
			return new Json(true, "版本信息删除成功");
		} else {
			return new Json(false, "版本信息删除失败");
		}
	}

	@Override
	public Version findById(String id) {
		return verSionMapper.selectByPrimaryKey(id);
	}

	/**
	 * 作者：刘栋梁<br>
	 * 创建时间：2017年12月5日<br>
	 * 描述：分页查询社保数据信息
	 * @param page 当前页码
	 * @param rows 每页数据量
	 * @param type 类型
	 * @return
	 */
	
	@Override
	 public Pager<Version> findPage(Integer page,Integer rows,String type){
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

		if (!StringUtils.isBlank(type)) {
			param.put("type", "%"+type+"%");
		}

		List<Version> version = verSionMapper.findPageData(param);
		int total = verSionMapper.findPageCount(param);
		return new Pager<Version>( version,total);
	}

	@Override
	public List<Version> findAllData() {
		return verSionMapper.findAllData();
	}
	
}
