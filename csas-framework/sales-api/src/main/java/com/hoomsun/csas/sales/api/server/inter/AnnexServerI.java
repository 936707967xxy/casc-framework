/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.csas.sales.api.server.inter;

import java.util.List;
import java.util.Map;

import com.hoomsun.common.model.Json;
import com.hoomsun.csas.sales.api.model.Annex;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年12月7日 <br>
 * 描述：
 */
public interface AnnexServerI {
	Annex selectByPrimaryKey(String anxId);

	List<Annex> findByApplyIdImgType(String applyId, Integer imgType);

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2018年1月25日 <br>
	 * 描述：长传图片回显
	 */
	List<Map<String, Object>> queryReView(String applyId, Integer imgType);

	/**
	 * 作者：ygzhao <br>
	 * 创建时间：2018年1月25日 <br>
	 * 描述：删除附件
	 */
	Json deleteByanxId(String anxId);
}
