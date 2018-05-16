/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.controller.after;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.after.api.model.param.ImageModel;
import com.hoomsun.after.api.server.UploadImageServer;
import com.hoomsun.common.model.Json;

/**
 * 作者：zwLiu<br>
 * 创建时间：2018年4月17日 <br>
 * 描述：针对影像资料上传、删除及回显
 */
@Controller
public class ImageUploadController {
	@Autowired
	private UploadImageServer UploadImage;
	/**
	 * 
	 * 创建时间：2018年4月18日 <br>
	 * 描述：影像资料上传 
	 * @param imageModel
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/upload/uploadImage.do")
	public @ResponseBody Json uploadImage(ImageModel imageModel, @RequestParam("file") MultipartFile file, HttpServletRequest request ){
		return UploadImage.saveImage( imageModel,file, request );
	
	}
	/**
	 * 
	 * 创建时间：2018年4月18日 <br>
	 * 描述：影像资料删除 
	 * @param imageModel
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/upload/deleteImage.do")
	public @ResponseBody Json deleteImage(ImageModel imageModel,HttpServletRequest request){
		
		return UploadImage.deleteImage(imageModel);
		
	}
	/**
	 * 
	 * 创建时间：2018年4月18日 <br>
	 * 描述：影像资料回显 
	 * @param imageModel
	 * @param request
	 * @return
	 */
	@RequestMapping("/after/upload/previewImage.do")
	public @ResponseBody Json  previewImage(ImageModel imageModel, HttpServletRequest request){
		return UploadImage.previewImage(imageModel);
		
	}
}
