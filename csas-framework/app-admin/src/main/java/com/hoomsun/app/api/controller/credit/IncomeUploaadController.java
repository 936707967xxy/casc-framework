package com.hoomsun.app.api.controller.credit;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.app.api.enums.IpUrlEnum;
import com.hoomsun.app.api.model.Income;
import com.hoomsun.app.api.server.inter.IncomeServerI;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.common.util.UploadPathUtil;
import com.hoomsun.csas.sales.api.model.NameAuthentication;
import com.hoomsun.csas.sales.api.server.inter.NameAuthenticationServerI;


/**
 * 
 * @author 刘栋梁
 * @date 2017-11-01
 * @resource 1.收入证明上传
 *           192.168.3.19:8080/hsfs-web/web/incomeupload/upload.do?ID=&file=&option=
 *           2.查询所有已上传的图像
 *           192.168.3.19:8080/hsfs-web/web/incomeupload/incomefile.do?ID=
 *           3.删除收入认证信息
 *           192.168.3.19:8080/hsfs-web/web/incomeupload/deleteuploadfile.do?ID=   
 * 
 * 
 * 
 */

@Controller
@RequestMapping("/web/incomeupload")
public class IncomeUploaadController {

	private final static Logger log = LoggerFactory.getLogger(AppNoticeController.class);
	
	private IpUrlEnum ip = IpUrlEnum.HSFS_IP;

	@Autowired
	private IncomeServerI incomeServer;
	
	@Autowired
	private NameAuthenticationServerI  nameAuthenticationServer;

	private UploadPathUtil uploadPathUtil;

	@Autowired
	public void setUploadPathUtil(UploadPathUtil uploadPathUtil) {
		this.uploadPathUtil = uploadPathUtil;
	}

	/**
	 * 收入证明上传 2017-11-07 1.1 刘栋梁
	 * 
	 * @param request
	 * @return
	 * @LoggerAnnotation(moduleName = "添加收入认证信息", option = "收入证明上传")
	 */
	
	@RequestMapping("upload.do")
	@ResponseBody
	public Map<String, Object> IncomeUpload(@RequestParam("file") MultipartFile file, @RequestParam("ID") String ID,String option) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (!file.isEmpty() && !StringUtils.isBlank(file.getOriginalFilename())) {
			try {
				String viewPath = uploadPathUtil.userUrl();
				String fileName = file.getOriginalFilename();
				String fileType = fileName.substring(fileName.lastIndexOf("."));
				String saveName = new Date().getTime() + fileType;
				File f = new File(uploadPathUtil.saveUserPath() + File.separator + ID + File.separator + "Income");
				if (!f.exists()) {
					f.mkdirs();
				}
				// 文件保存路径
				String filePath = uploadPathUtil.saveUserPath() + File.separator + ID + File.separator + "Income" + File.separator + saveName;
				// 转存文件
				file.transferTo(new File(filePath));
				String returnpath = viewPath+ '/'+ ID + "/Income/" + saveName;

				Income income = new Income();
				income.setIncomeid(PrimaryKeyUtil.getPrimaryKey());
				income.setAddData(DateUtil.getTimestamp());
				income.setFkId(ID);
				income.setIncomepath(returnpath);
				int i = incomeServer.insertSelective(income);
				if (i == 0) {
					incomeServer.deleteByFkid(ID);
					result.put("errorCode", 1);
					result.put("errorInfo", "上传失败!! ");	
				} else {
					income.setIncomepath(ip.getIpUrl() + returnpath);
					result.put("data", income);
					result.put("errorCode", 0);
					result.put("errorInfo", "上传成功!! ");
					//修改收入标示
					if("1".equals(option)){
						NameAuthentication Hs = new NameAuthentication();
						Hs.setId(ID);
						Hs.setIncome("1");
						Hs.setIncomeTime(DateUtil.getCurrentTime()); // 默认芝麻起始时间
						nameAuthenticationServer.updateByPrimaryKeySelective(Hs);
					}
				}

			} catch (Exception e) {
			    incomeServer.deleteByFkid(ID);
			    NameAuthentication Hs = new NameAuthentication();
				Hs.setId(ID);
				Hs.setIncome("0");
				Hs.setIncomeTime(DateUtil.getCurrentTime()); // 默认芝麻起始时间
				nameAuthenticationServer.updateByPrimaryKeySelective(Hs);
				result.put("errorCode", 1001);
				result.put("errorInfo", "网络异常,请重试!!");
			}
		}
		log.info(ID + "收入证明上传   :" + result);
		return result;
	}

	/**
	 * 查询所有已上传的图像 2017-11-07 1.1 刘栋梁
	 * 
	 * @param request
	 * @return
	 * @LoggerAnnotation(moduleName = "添加收入认证信息", option = "查询所有已上传的图像")
	 */
	
	@RequestMapping("incomefile.do")
	@ResponseBody
	public Map<String, Object> IncomeFile(@RequestParam("ID") String ID) {
		long startTime = System.currentTimeMillis(); // 获取开始时间

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			List<Income> list = incomeServer.selectAllByFkid(ID);
			for (Income obj : list) {
				obj.setIncomepath(ip.getIpUrl() + obj.getIncomepath());
			}
			result.put("data", list);
			result.put("errorInfo", "查询成功！！！");
			result.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			log.info(ID + "查询所有已上传的图像 ：" + (endTime - startTime));
		}
		log.info(ID + "查询所有已上传的图像 :" + result);

		return result;
	}
	
	/**
	 * 删除收入认证信息     2017-11-10 1.1 刘栋梁
	 * 
	 * @param request
	 * @return
	 * @LoggerAnnotation(moduleName = "删除收入认证信息", option = "删除收入证明信息")
	 */
	
	@RequestMapping("deleteuploadfile.do")
	@ResponseBody
	public Map<String, Object> deleteUploadFile(@RequestParam("ID") String ID) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {	
			int i = incomeServer.deleteByFkid(ID);
			if (i == 0) {
				result.put("errorCode", 1);
				result.put("errorInfo", "删除失败 !! ");
			} else {
				result.put("errorCode", 0);
				result.put("errorInfo", "删除成功!! ");
			}

		} catch (Exception e) {
			result.put("errorCode", 1001);
			result.put("errorInfo", "网络异常,请重试!!");
		}
		log.info(ID + "删除收入认证信息   :" + result);
		return result;
	}
	
	
}
