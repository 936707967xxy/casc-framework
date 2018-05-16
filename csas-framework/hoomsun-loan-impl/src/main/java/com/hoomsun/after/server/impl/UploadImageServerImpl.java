/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.server.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.after.api.model.param.ImageModel;
import com.hoomsun.after.api.model.table.Images;
import com.hoomsun.after.api.server.UploadImageServer;
import com.hoomsun.after.api.util.IDGenerator;
import com.hoomsun.after.dao.ImagesMapper;
import com.hoomsun.common.model.Json;
import com.hoomsun.common.util.SystemUtils;
import com.hoomsun.common.util.UploadPathUtil;
import com.hoomsun.core.util.PrimaryKeyUtil;

/**
 * 作者：zwliu <br>
 * 创建时间：2018年4月17日 <br>
 * 描述：影像资料上传
 */
@Service("UploadImageServer")
public class UploadImageServerImpl implements UploadImageServer {
	private final String SING = "UPIMG";
	@Autowired
	private UploadPathUtil uploadPathUtil;
	@Autowired
	private ImagesMapper imagesMapper;

	/**
	 * 影像资料上传
	 */
	@Override
	public Json saveImage(ImageModel imageModel, MultipartFile file, HttpServletRequest request) {
		if (file == null || file.isEmpty()) {
			return new Json(false, "上传附件为空！");
		}
		if (imageModel.getApplyId() == null || "".equals(imageModel.getApplyId())) {
			imageModel.setApplyId(IDGenerator.getNextID(SING));
		}
		if (imageModel.getApplyType() == null || "".equals(imageModel.getApplyType())) {
			return new Json(false, "申请类型有误！");
		}
		if (imageModel.getImageType() == null || "".equals(imageModel.getImageType())) {
			return new Json(false, "影像资料类型有误！");
		}
		// 生成文件名
		String fileName = PrimaryKeyUtil.getPrimaryKey() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		// 创建保存目录
		String saveFolder = uploadPathUtil.saveApplyPath() + File.separator + imageModel.getApplyId() + File.separator + imageModel.getImageType();
		File f = new File(saveFolder);
		if (!f.exists()) {
			f.mkdirs();
		}
		// 文件保存路径
		String savePath = saveFolder + File.separator + fileName;
		// 将文件保存至服务器磁盘
		try {
			FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(savePath));
		} catch (IOException e) {
			e.printStackTrace();
			return new Json(false, "影像资料上传异常！");
		}
		// 映射地址
		String fileUrl = request.getScheme() + "://" + SystemUtils.getLocalIp() + ":" + request.getServerPort();
		String viewPath = uploadPathUtil.applyUrl();
		String preView = fileUrl + viewPath + "/" + imageModel.getApplyId() + "/" + imageModel.getImageType() + "/" + fileName;
		Images images = new Images();
		String imgId = PrimaryKeyUtil.getPrimaryKey();
		images.setId(imgId);
		images.setApplicationId(imageModel.getApplyId());
		images.setApplyType(imageModel.getApplyType());
		images.setImageName(fileName);
		images.setImageType(imageModel.getImageType());
		images.setDiskUrl(savePath);
		images.setImageUrl(preView);
		images.setCreateTime(new Date());
		images.setUpdateDate(null);
		int row = imagesMapper.insert(images);
		if (row < 1) {
			return new Json(false, "影像资料上传失败！");
		}
		imageModel.setImageId(imgId);
		imageModel.setDiskPath(savePath);

		return new Json(true, "上传成功！", imageModel);

	}

	/**
	 * 影像资料删除
	 */
	@Override
	public Json deleteImage(ImageModel imageModel) {
		if (imageModel.getImageId() == null || "".equals(imageModel.getImageId())) {
			return new Json(false, "删除失败！");
		}
		if (imageModel.getDiskPath() == null || "".equals(imageModel.getDiskPath())) {
			return new Json(false, "删除失败！");
		}
		int row = imagesMapper.deleteByPrimaryKey(imageModel.getImageId());
		if (row < 1) {
			return new Json(false, "删除失败！");
		}
		return new Json(true, "影像资料删除成功！");

	}
	/**
	 * 影像资料回显
	 */
	@Override
	public  Json previewImage(ImageModel imageModel) {
		Images images = new Images();
		String applyId = imageModel.getApplyId();
		if(applyId !=null ){
			images.setApplicationId(applyId);
		}
		List<Images> imagesLis = imagesMapper.selectByApplicationId(images);
		
		return new Json(true,"查询成功！", imagesLis);
	}

}
