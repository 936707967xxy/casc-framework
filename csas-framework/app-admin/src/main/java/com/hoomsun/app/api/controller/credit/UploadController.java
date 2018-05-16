/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.app.api.controller.credit;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.app.api.enums.IpUrlEnum;
import com.hoomsun.common.util.UploadPathUtil;
import com.hoomsun.csas.sales.api.model.NameAuthentication;
import com.hoomsun.csas.sales.api.server.inter.NameAuthenticationServerI;


/**
 * @author 作者：liudongliang <br>
 * @Date 创建时间：2017年12月01日 <br>
 * @Description 主要用于图片上传
 * @resource        
 *              http://192.168.3.19:8080/app-admin/web/upload/uploadimg.do?ID=&file
 */
@Controller
@RequestMapping("web/upload")
public class UploadController {

	@Autowired
	private NameAuthenticationServerI hsServer;

	@Autowired
	private UploadPathUtil uploadPathUtil;

	
	/**
	 * @Description     单个文件上传
	 * @param file 单个文件上传，ID
	 * @return  Map
	 * @Date    2017-12-01
	 * @LoggerAnnotation(moduleName = "上传接口", option = "文件上传")
	 */
	
	@RequestMapping(value = "uploadimg.do", method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> uploadImgFile(@RequestParam("file") MultipartFile file, @RequestParam("ID") String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (!file.isEmpty() && !StringUtils.isBlank(file.getOriginalFilename())) {
			try {
				String viewPath = uploadPathUtil.userUrl();
				String fileName = file.getOriginalFilename();
				String fileType = fileName.substring(fileName.lastIndexOf("."));
				String saveName = System.currentTimeMillis() + fileType;

				File f = new File(uploadPathUtil.saveUserPath() + File.separator + id + File.separator + "head");
				if (!f.exists()) {
					f.mkdirs();
				}
				// 文件保存路径
				String filePath = uploadPathUtil.saveUserPath() + File.separator + id + File.separator + "head" + File.separator + saveName;
				// 转存文件
				file.transferTo(new File(filePath));
				String photopath = viewPath+"/" + id + "/head/" + saveName;
				NameAuthentication nameAuthen = new NameAuthentication();
				nameAuthen.setId(id);
				nameAuthen.setPhotopath(photopath);
				int i = hsServer.updateByPrimaryKeySelective(nameAuthen);
				// 路径获取
				nameAuthen.setPhotopath(IpUrlEnum.HSFS_IP.getIpUrl() + photopath);
				if (i == 0) {
					result.put("errorCode", 1);
					result.put("errorInfo", "上传失败!! ");
				} else {
					result.put("data", nameAuthen);
					result.put("errorCode", 0);
					result.put("errorInfo", "上传成功!! ");
				}

			} catch (Exception e) {
				result.put("errorCode", 1001);
				result.put("errorInfo", "网络异常,请重试!!");
			}
		}
		return result;
	}

	/**
	 * 作者：liushuai <br>
	 * 创建时间：2017年9月18日<br>
	 * 描述：多个文件上传
	 * 
	 * @param files
	 *            多个文件
	 */
	/*
	 * @RequestMapping(value="/web/test/uploadImgs.do",method=
	 * {RequestMethod.POST})
	 * 
	 * @ResponseBody public List<Json> uploadImgFiles(MultipartFile[]
	 * files,String ID) { List<Json> results = new ArrayList<Json>();
	 * //判断file数组不能为空并且长度大于0 if(files!=null&&files.length>0){ //循环获取file数组中得文件
	 * for(int i = 0;i<files.length;i++){ MultipartFile file = files[i]; //保存文件
	 * results.add(uploadImgFile(file,ID)); } } return results; }
	 */

}
