/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.after.api.model.param.ImageModel;
import com.hoomsun.common.model.Json;

/**
 * 作者：zwLiu <br>
 * 创建时间：2018年4月17日 <br>
 * 描述：影像资料上传
 */
public interface UploadImageServer {
	public Json saveImage(ImageModel imageModel, MultipartFile file, HttpServletRequest request );

	public Json deleteImage(ImageModel imageModel);

	public Json previewImage(ImageModel imageModel);
}
