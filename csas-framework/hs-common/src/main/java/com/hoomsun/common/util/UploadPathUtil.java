/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.common.util;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月13日 <br>
 * 描述：文件上传路径处理 时间：2017年9月27日 liushuai 增加银行文件上传路径
 * 
 */
public class UploadPathUtil {
	private String uploadPath;// 上传基础路径
	private String bannerFolder;// banner路径
	private String productFolder;// 产品路径
	private String noticeFolder;// 通知路径
	private String bankFolder;// 银行卡路径
	private String userFolder;// 申请路径
	private String versionFolder;// APP包路径
	private String contextUrl;// 预览更路径
	private String idcardtUrl;// 身份正
	private String applyFolder;// 申请附件
	private String contractFolder;// 合同附件
	private String auditFolder;// 审核附件
	
	private String loadDate() {
		Date date = new Date();
		String str = DateUtil.yyyyMMddHH_NOT_.format(date);
		return str;
	}
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月13日 <br>
	 * 描述： banner保存路径
	 * 
	 * @return
	 */
	public String saveBannerPath() {
		if (StringUtils.isBlank(uploadPath)) {
			throw new RuntimeException("上传文件未配置!");
		}
		return uploadPath + "/" + bannerFolder + "/" + loadDate();
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月13日 <br>
	 * 描述： 产品保存路径
	 * 
	 * @return
	 */
	public String saveProductPath() {
		if (StringUtils.isBlank(uploadPath)) {
			throw new RuntimeException("上传文件未配置!");
		}
		return uploadPath + "/" + productFolder + "/" + loadDate();
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月13日 <br>
	 * 描述： 通知公告的存储路径
	 * 
	 * @return
	 */
	public String saveNoticePath() {
		if (StringUtils.isBlank(uploadPath)) {
			throw new RuntimeException("上传文件未配置!");
		}
		return uploadPath + "/" + noticeFolder + "/" + loadDate();
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月13日 <br>
	 * 描述： 用户的存储路径
	 * 
	 * @return
	 */
	public String saveUserPath() {
		if (StringUtils.isBlank(uploadPath)) {
			throw new RuntimeException("上传文件未配置!");
		}
		return uploadPath + "/" + userFolder + "/" + loadDate();
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月13日 <br>
	 * 描述： banner附件链接地址
	 * 
	 * @return
	 */
	public String bannerUrl() {
		if (StringUtils.isBlank(contextUrl)) {
			throw new RuntimeException("静态上下文未配置!");
		}
		return contextUrl + bannerFolder + "/" + loadDate();
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月13日 <br>
	 * 描述： 产品附件链接地址
	 * 
	 * @return
	 */
	public String productUrl() {
		if (StringUtils.isBlank(contextUrl)) {
			throw new RuntimeException("静态上下文未配置!");
		}
		return contextUrl + productFolder + "/" + loadDate();
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月13日 <br>
	 * 描述： 通知公告附件链接地址
	 * 
	 * @return
	 */
	public String noticeUrl() {
		if (StringUtils.isBlank(contextUrl)) {
			throw new RuntimeException("静态上下文未配置!");
		}
		return contextUrl + noticeFolder + "/" + loadDate();
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月13日 <br>
	 * 描述： 用户附件链接地址
	 * 
	 * @return
	 */
	public String userUrl() {
		if (StringUtils.isBlank(contextUrl)) {
			throw new RuntimeException("静态上下文未配置!");
		}
		return contextUrl + userFolder + "/" + loadDate();
	}

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年10月9日 <br>
	 * 描述： 用户的身份证照片
	 * 
	 * @return
	 */
	public String saveIdcardPath() {
		if (StringUtils.isBlank(uploadPath)) {
			throw new RuntimeException("上传文件未配置!");
		}
		return uploadPath + userFolder + "/" + loadDate();
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月13日 <br>
	 * 描述：文件上传的额根路径
	 * 
	 * @return
	 */
	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月13日 <br>
	 * 描述： banner 文件上传的存储文件夹
	 * 
	 * @return
	 */
	public String getBannerFolder() {
		return bannerFolder;
	}

	public void setBannerFolder(String bannerFolder) {
		this.bannerFolder = bannerFolder;
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月13日 <br>
	 * 描述： 产品存储文件夹
	 * 
	 * @return
	 */
	public String getProductFolder() {
		return productFolder;
	}

	public void setProductFolder(String productFolder) {
		this.productFolder = productFolder;
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月13日 <br>
	 * 描述： 通知公告文件夹
	 * 
	 * @return
	 */
	public String getNoticeFolder() {
		return noticeFolder;
	}

	public void setNoticeFolder(String noticeFolder) {
		this.noticeFolder = noticeFolder;
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月13日 <br>
	 * 描述： 用户相关资料文件夹
	 * 
	 * @param noticeFolder
	 */
	public String getUserFolder() {
		return userFolder;
	}

	public void setUserFolder(String userFolder) {
		this.userFolder = userFolder;
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月13日 <br>
	 * 描述： 静态资源的访问地址上下文
	 * 
	 * @param contextUrl
	 */
	public String getContextUrl() {
		return contextUrl;
	}

	public void setContextUrl(String contextUrl) {
		this.contextUrl = contextUrl;
	}

	/**
	 * 作者：liushuai <br>
	 * 创建时间：2017年9月27日 <br>
	 * 描述： 银行的存储路径
	 * 
	 * @return
	 */
	public String saveBankPath() {
		if (StringUtils.isBlank(uploadPath)) {
			throw new RuntimeException("上传文件未配置!");
		}
		return uploadPath + "/" + bankFolder + "/" + loadDate();
	}

	/**
	 * 作者：liushuai <br>
	 * 创建时间：2017年9月27日 <br>
	 * 描述： 银行附件链接地址
	 * 
	 * @return
	 */
	public String bankUrl() {
		if (StringUtils.isBlank(contextUrl)) {
			throw new RuntimeException("静态上下文未配置!");
		}
		return contextUrl + bankFolder + "/" + loadDate();
	}

	/**
	 * 作者：liushuai <br>
	 * 创建时间：2017年9月27日 <br>
	 * 描述： 银行附件存储目录
	 * 
	 * @return
	 */
	public String getBankFolder() {
		return bankFolder;
	}

	public void setBankFolder(String bankFolder) {
		this.bankFolder = bankFolder;
	}

	/**
	 * 作者：liushuai <br>
	 * 创建时间：2017年9月29日 <br>
	 * 描述： 安卓APP路径
	 * 
	 * @return
	 */
	public String saveVersionPath() {
		if (StringUtils.isBlank(uploadPath)) {
			throw new RuntimeException("上传文件未配置!");
		}
		return uploadPath + "/" + versionFolder + "/" + loadDate();
	}

	/**
	 * 作者：liushuai <br>
	 * 创建时间：2017年9月29日 <br>
	 * 描述： 安卓APP链接地址
	 * 
	 * @return
	 */
	public String versionUrl() {
		if (StringUtils.isBlank(contextUrl)) {
			throw new RuntimeException("静态上下文未配置!");
		}
		return contextUrl + versionFolder + "/" + loadDate();
	}

	/**
	 * 作者：liushuai <br>
	 * 创建时间：2017年9月29日 <br>
	 * 描述： 安卓APP存储路径
	 * 
	 * @return
	 */
	public String getVersionFolder() {
		return versionFolder;
	}

	public void setVersionFolder(String versionFolder) {
		this.versionFolder = versionFolder;
	}

	/**
	 * 作者：liudongliang <br>
	 * 创建时间：2017年10月9日 <br>
	 * 描述：身份证头像存储路径
	 * 
	 * @return
	 */
	public String getIdcardtUrl() {
		return idcardtUrl;
	}

	public void setIdcardtUrl(String idcardtUrl) {
		this.idcardtUrl = idcardtUrl;
	}

	public String getApplyFolder() {
		return applyFolder;
	}

	public void setApplyFolder(String applyFolder) {
		this.applyFolder = applyFolder;
	}

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2017年9月13日 <br>
	 * 描述：门店申请附件保存路径
	 * 
	 * @return
	 */
	public String saveApplyPath() {
		if (StringUtils.isBlank(uploadPath)) {
			throw new RuntimeException("上传文件未配置!");
		}
		return uploadPath + "/" + applyFolder + "/" + loadDate();
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月13日 <br>
	 * 描述： 门店申请附件链接地址
	 * 
	 * @return
	 */
	public String applyUrl() {
		if (StringUtils.isBlank(contextUrl)) {
			throw new RuntimeException("静态上下文未配置!");
		}
		return contextUrl + applyFolder + "/" + loadDate();
	}
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述： 合同附件
	 * @return
	 */
	public String getContractFolder() {
		return contractFolder;
	}
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述： 合同附件
	 * @param contractFolder
	 */
	public void setContractFolder(String contractFolder) {
		this.contractFolder = contractFolder;
	}
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述： 合同预览路径
	 * @return
	 */
	public String contractUrl() {
		if (StringUtils.isBlank(contextUrl)) {
			throw new RuntimeException("静态上下文未配置!");
		}
		return contextUrl + contractFolder + "/" + loadDate();
	}
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述： 合同存储路径
	 * @return
	 */
	public String saveContract() {
		if (StringUtils.isBlank(uploadPath)) {
			throw new RuntimeException("静态上下文未配置!");
		}
		return uploadPath + "/" + contractFolder + "/" + loadDate();
	}
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述： 审核附件
	 * @return
	 */
	public String getAuditFolder() {
		return auditFolder;
	}
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述： 审核附件
	 * @param auditFolder
	 */
	public void setAuditFolder(String auditFolder) {
		this.auditFolder = auditFolder;
	}
	
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述： 审核附件预览路径
	 * @return
	 */
	public String auditUrl() {
		if (StringUtils.isBlank(contextUrl)) {
			throw new RuntimeException("静态上下文未配置!");
		}
		return contextUrl + auditFolder + "/" + loadDate();
	}

	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年12月16日 <br>
	 * 描述： 合同存储路径
	 * @return
	 */
	public String saveAudit() {
		if (StringUtils.isBlank(uploadPath)) {
			throw new RuntimeException("静态上下文未配置!");
		}
		return uploadPath + "/" + auditFolder + "/" + loadDate();
	}
}
