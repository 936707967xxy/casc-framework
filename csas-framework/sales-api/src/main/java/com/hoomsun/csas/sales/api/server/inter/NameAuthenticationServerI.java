package com.hoomsun.csas.sales.api.server.inter;

import java.util.List;

import com.hoomsun.csas.sales.api.model.NameAuthentication;

/**
 * 作者：liudongliang<br>
 * 创建时间：2017年9月12日<br>
 * 描述：创建认证表
 * @param 
 * @return
 */
public interface NameAuthenticationServerI {

	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月12日<br>
	 * 描述：创建认证表
	 * @param   创建认证表
	 * @return
	 */
	public   int insertSelective(NameAuthentication Hs);
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月12日<br>
	 * 描述：创建公司
	 * @param company 判断手机号是否存在 
	 * @return
	 */
	public  NameAuthentication selectByPhone(String phone);
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月12日<br>
	 * 描述：创建公司
	 * @param company 修改实名信息表
	 * @return
	 */
	public int updateByPrimaryKeySelective(NameAuthentication Hs);
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月12日<br>
	 * 描述：创建公司
	 * @param company 根据id查询
	 * @return
	 */
	public NameAuthentication selectByPrimaryKey(String id);
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月12日<br>
	 * 描述：创建公司
	 * @param company 根据phone修改
	 * @return
	 */
	public  int updateByPhoneSelective(NameAuthentication Hs);
	
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月20日<br>
	 * 描述：创建公司
	 * @param company 根据微信登陆
	 * @return
	 */
	NameAuthentication selectByWxtaken(String wxtaken);
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年9月28日<br>
	 * 描述：创建公司
	 * @param company 单设备登陆信息查询
	 * @return
	 */
	NameAuthentication selectUuid(NameAuthentication record);
	
	/**
	 * 作者：liushuai <br>
	 * 创建时间：2017年10月23日 <br>
	 * 描述： 根据签名参数获取签名所对应用户
	 * @param sign 签名参数
	 * @return
	 */
	NameAuthentication selectBySign(String sign);
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年10月24日<br>
	 * 描述：创建公司
	 * @param company 查询所有数据补全签名  
	 * @return
	 */
	List<NameAuthentication> selectAllData();
	
	/**
	 * 作者：liudongliang<br>
	 * 创建时间：2017年10月24日<br>
	 * 描述：身份证获取极光id
	 * @param 
	 * @return
	 */
	NameAuthentication selectByIdcard(String paperid);
}
