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
import com.hoomsun.app.api.model.Banner;
import com.hoomsun.app.api.server.inter.BannerServerI;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.UploadPathUtil;

@Controller
@RequestMapping("sys/banner")
public class BannerController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UploadPathUtil uploadPathUtil;
	
	@Autowired
	private BannerServerI  bannerServerI;
	
	
	@RequestMapping(value = "upload.do", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> uploadFile( MultipartFile file) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (!file.isEmpty() && !StringUtils.isBlank(file.getOriginalFilename())) {
			try {
				String viewPath = uploadPathUtil.bannerUrl();
				String fileName = file.getOriginalFilename();

				File f = new File(uploadPathUtil.saveBannerPath());
				if (!f.exists()) {
					f.mkdirs();
				}
				// 文件保存路径
				String filePath = uploadPathUtil.saveBannerPath() + File.separator + fileName;
				// 转存文件
				file.transferTo(new File(filePath));

				result.put("success", true);
				result.put("msg", "上传成功 ！！");
				result.put("data", viewPath + "/" + fileName);
			} catch (Exception e) {
				logger.debug("文件上传服务器没有成功!");
			}
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "page.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Pager<Banner> findPagerData(Integer page, Integer rows) {
		return bannerServerI.findPage(page, rows);
	}
	
	@RequestMapping(value = "create.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json addBanner(Banner banner) {
		return bannerServerI.createBanner(banner);
	}
	
	@RequestMapping(value = "/remove.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json removeBanner(String id) {
		return bannerServerI.deleteBanner(id);
	}
	
	@RequestMapping(value = "/update.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json editBanner(Banner banner) {
		return bannerServerI.updateBanner(banner);
	}
}
