/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.server.inter;

import java.util.List;
import java.util.Map;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月22日 <br>
 * 描述：集成的数据业务接口
 */
public interface BaseServerI {
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月22日 <br>
	 * 描述： 省份信息
	 * @return
	 */
	List<Map<String, String>> findAllProvinces();
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月22日 <br>
	 * 描述： 省下面的市
	 * @param provinceId
	 * @return
	 */
	List<Map<String, String>> findCitieByPro(String provinceId);
	/**
	 * 作者：ywzou <br>
	 * 创建时间：2017年9月22日 <br>
	 * 描述： 是下面的区
	 * @param cityId
	 * @return
	 */
	List<Map<String, String>> findAreaByCity(String cityId);
}
