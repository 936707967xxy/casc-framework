/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.server.inter;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.hoomsun.common.model.Result;
/**
 * 作者：liusong <br>
 * 创建时间：2018年2月6日 <br>
 * 描述：
 */
public interface CommonPhoneServerI {
	
	Result createCommonPhone(MultipartFile fileUp) throws IOException;

}
