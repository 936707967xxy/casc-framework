/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.after.api.server;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

/**
 * 作者：shiqiangjin <br>
 * 创建时间：2018年5月9日 <br>
 * 描述：
 */
@Service("appServer")
public interface AppServer {

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月9日 <br>
	 * 描述： 还款记录 app端 我的-往来记录
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getPaymentHistory(String id);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月9日 <br>
	 * 描述：还款记录单条 app端 -邹哥位置跳转
	 * 
	 * @param applyId
	 * @return
	 */
	public Map<String, Object> getsinglepayment(String applyId);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月9日 <br>
	 * 描述： 近七日还款
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getSevenDaysRepayment(String id);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月9日 <br>
	 * 描述：APP还款按钮 得到此客户的所有单子 还款
	 * 
	 * @param custId
	 * @return
	 */
	public List<Map<String, Object>> getLetters(String custId);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月9日 <br>
	 * 描述：操作还款界面 还款点击单子加载数据 还款——单子点击
	 * 
	 * @param loanId
	 * @return
	 */
	public Map<String, Object> findLoanData(String loanId);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月9日 <br>
	 * 描述：正常月还
	 * 
	 * @param custId
	 * @param applyId
	 * @param Stream
	 * @param req
	 * @return
	 */
	public Map<String, Object> saveNomal(String custId, String loanId, Integer Stream, HttpServletRequest req);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月9日 <br>
	 * 描述： 提前还款
	 * 
	 * @param custId
	 * @param applyId
	 * @param Stream
	 * @param req
	 * @return
	 */
	public Map<String, Object> saveAdvenced(String custId, String loanId, Integer Stream, HttpServletRequest req);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月9日 <br>
	 * 描述：逾期还款
	 * 
	 * @param custId
	 * @param applyId
	 * @param Stream
	 * @param money
	 * @param req
	 * @return
	 */
	public Map<String, Object> saveOverdue(String custId, String loanId, Integer Stream, HttpServletRequest req);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月9日 <br>
	 * 描述：获取账户余额
	 * 
	 * @param custId
	 * @return
	 */
	public Map<String, Object> getbal(String custId);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月9日 <br>
	 * 描述：充值记录
	 * 
	 * @param custId
	 * @return
	 */
	public List<Map<String, Object>> getApppayment(String custId);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月9日 <br>
	 * 描述：App余额充值
	 * 
	 * @param custId
	 * @param bankName
	 * @param bankcode
	 * @param bankAccount
	 * @param bankPhone
	 * @param idCard
	 * @param money
	 * @param ip
	 * @param req
	 * @return
	 */
	public Map<String, Object> saveAppBal(String custId, String bankName, String bankcode, String bankAccount, String bankPhone, String idCard, BigDecimal money, String ip, HttpServletRequest req);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月9日 <br>
	 * 描述：App余额充值带验证码
	 * 
	 * @param custId
	 * @param money
	 * @param yzm
	 * @param businessNo
	 * @param req
	 * @return
	 */
	public Map<String, Object> saveAppBal2(String custId, BigDecimal money, String yzm, String businessNo,String orderNo, HttpServletRequest req);

	/**
	 * 
	 * 作者：shiqiangjin <br>
	 * 创建时间：2018年5月9日 <br>
	 * 描述：银行卡变更推送数据
	 * 
	 * @param custId
	 * @param bank
	 * @param bankPhone
	 * @param bankAccount
	 * @param bankCode
	 * @param bankCode2
	 * @param bankCode3
	 * @return
	 */
	public Map<String, Object> updateBank(String custId, String bank, String bankPhone, String bankAccount, String bankCode, String bankCode2, String bankCode3);
}
