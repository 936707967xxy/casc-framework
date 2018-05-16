package com.hoomsun.app.api.controller.admin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.hoomsun.app.api.model.BankInterface;
import com.hoomsun.app.api.server.inter.BankInterfaceServerI;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.model.Pager;
import com.hoomsun.common.util.UploadPathUtil;
import com.hoomsun.core.anno.Permission;


@Controller
@RequestMapping("sys/bankinterface")
public class BankInterFaceController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UploadPathUtil uploadPathUtil;
	
	@Autowired
	private BankInterfaceServerI bankInterfaceServerI;

	
	@RequestMapping(value = "upload.do", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> uploadFile( MultipartFile file) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (!file.isEmpty() && !StringUtils.isBlank(file.getOriginalFilename())) {
			try {
				String viewPath = uploadPathUtil.bankUrl();
				String fileName = file.getOriginalFilename();
				String fileDir = uploadPathUtil.saveBankPath() + File.separator ;
				logger.error("viewPath" +  viewPath);
				logger.error("fileDir" +  fileDir);
				File f = new File(fileDir);
				if (!f.exists()) {
					f.mkdirs();
				}
				// 文件保存路径
				String filePath =fileDir + File.separator + fileName;
				// 转存文件
				file.transferTo(new File(filePath));
				
				result.put("success", true);
				result.put("msg", "上传成功 ！！");
				result.put("data", viewPath +"/"+ fileName);
			} catch (Exception e) {
				logger.error("APP上传服务器没有成功!");
			}
		}
		return result;
	}
	
	@RequestMapping(value = "create.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json addBankInterFace(@RequestBody BankInterface BankInterFace) {
		return bankInterfaceServerI.addBankInterFace(BankInterFace);
	}
	
	@Permission("version_query")
	@RequestMapping(value = "page.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Pager<BankInterface> findPagerData(Integer page, Integer rows, String bankName) {
		return bankInterfaceServerI.findPage(page, rows, bankName);
	}
	
	@RequestMapping(value = "/update.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json editBankInterface(@RequestBody BankInterface bankItf) {
		return bankInterfaceServerI.updateBankItf(bankItf);
	}
	
	@RequestMapping(value = "/remove.do", method = { RequestMethod.POST })
	@ResponseBody
	public Json removeBankInterface(String bankinterId) {
		return   bankInterfaceServerI.deleteBankItf(bankinterId);
	}
}
