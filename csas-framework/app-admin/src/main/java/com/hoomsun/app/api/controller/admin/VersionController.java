package com.hoomsun.app.api.controller.admin;


import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.hoomsun.app.api.model.Version;
import com.hoomsun.app.api.server.inter.VersionServerI;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.UploadPathUtil;
import com.hoomsun.core.anno.Permission;


/**
 * 
 * @author 作者：liudongliang <br>
 * @Date 创建时间：2017年11月30日 <br>
 * @Description 描述  版本更新后台 
 *           
 *
 */
@Controller
@RequestMapping("sys/version")
public class VersionController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private VersionServerI versionServer;
	
	@Autowired
	private UploadPathUtil uploadPathUtil;
	
	
	@RequestMapping(value = "upload.do", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> uploadFile( MultipartFile file) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		if (!file.isEmpty() && !StringUtils.isBlank(file.getOriginalFilename())) {
			try {
				String viewPath = uploadPathUtil.bankUrl();
				String fileName = file.getOriginalFilename();
				

				File f = new File(uploadPathUtil.saveBankPath());
				if (!f.exists()) {
					f.mkdirs();
				}
				// 文件保存路径
				String filePath = uploadPathUtil.saveBankPath() + File.separator + fileName;
				// 转存文件
				file.transferTo(new File(filePath));
				result.put("success", true);
				result.put("msg", "上传成功 ！！");
				result.put("data", viewPath +"/"+ fileName);
			} catch (Exception e) {
				logger.debug("文件上传服务器没有成功!");
			}
		}
		return result;
	}
	
	@Permission("version_query")
	@RequestMapping(value = "page.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Pager<Version> findPagerData(Integer page, Integer rows, String type) {
		return versionServer.findPage(page, rows, type);
	}
	
	@RequestMapping(value = "create.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json addVersion(Version version) {
		return versionServer.createVersion(version);
	}
	
	@RequestMapping(value = "/update.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json editVersion(Version version) {
		return versionServer.updateVersion(version);
	}

	
	@RequestMapping(value = "/remove.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json removeVersion(String Id) {
		return  versionServer.deleteVersion(Id);
	}
}
