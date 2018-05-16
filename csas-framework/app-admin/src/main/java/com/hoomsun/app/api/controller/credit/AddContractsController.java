package com.hoomsun.app.api.controller.credit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hoomsun.app.api.enums.ApiEnum;
import com.hoomsun.app.api.enums.IpUrlEnum;
import com.hoomsun.app.api.help.HttpClientController;
import com.hoomsun.common.util.DateUtil;
import com.hoomsun.common.util.HttpClientUtil;
import com.hoomsun.common.util.PrimaryKeyUtil;
import com.hoomsun.csas.sales.api.model.UserApply;
import com.hoomsun.csas.sales.api.model.UserCallDetail;
import com.hoomsun.csas.sales.api.model.UserCallVisual;
import com.hoomsun.csas.sales.api.model.UserContacts;
import com.hoomsun.csas.sales.dao.UserApplyMapper;
import com.hoomsun.csas.sales.dao.UserCallDetailMapper;
import com.hoomsun.csas.sales.dao.UserCallVisualMapper;
import com.hoomsun.csas.sales.dao.UserContactsMapper;


@Controller
@RequestMapping("sys/AddContracts")
public class AddContractsController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static String contactinfourl = IpUrlEnum.DATA_IP.getIpUrl() + ApiEnum.CONTACTINFOURL.getApiUrl(); // 通话
	
	@Autowired
	private UserCallDetailMapper userCallDetailMapper;
	
	
	@Autowired
	private UserContactsMapper userContactsMapper;         // 联系人信息表
	
	@Autowired
	private UserApplyMapper userApplyMapper;         // 主表 
	
	@Autowired
	private UserCallVisualMapper   UserCallVisualMapper;
	
	/**
	 * 
	 * 添加客户信息------通话详单
	 * @param request
	 * @return
	 * 
	 */
	
	@RequestMapping("addindo.do")
	@ResponseBody
	public Map<String, Object> addIndo(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		String apply_id = request.getParameter("apply_id");
		String cardNumber = request.getParameter("cardNumber");

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// -----

						NameValuePair[] contactdata = { new NameValuePair("cardNumber", cardNumber)};
						String contactreult = HttpClientController.postcheck(contactinfourl, contactdata);
						logger.info("通讯录信息" + contactreult);
						JSONObject contactjason = JSON.parseObject(contactreult);
						if ("2222".equals(contactjason.get("errorCode").toString())) {
							result.put("errorInfo", "通讯录校验失败,请使用自己手机");
							result.put("errorCode", 1);
							return result;
						}
						if ("1111".equals(contactjason.get("errorCode").toString())) {
							result.put("errorInfo", "通话详单录入失败！！");
							result.put("errorCode", 1);
							return result;
						}
						
						List<UserContacts> contacts = null;
						if("0000".equals(contactjason.get("errorCode").toString())){
							if (contactjason.get("data") != null) {
								contacts = new ArrayList<UserContacts>();
								JSONArray list = JSON.parseArray(contactjason.get("data").toString());
								// System.out.println(list.size());
								// List<UserContacts> contacts = new ArrayList<UserContacts>();
								for (int i = 0; i < list.size(); i++) {
									String mapstr = list.get(i).toString();
									JSONObject map = JSON.parseObject(mapstr);
									String PHONE = map.get("phone")+"";
									String CALLCOUNTS = map.get("callCounts")+"";
									String calltime = map.get("callTime")+"";
									String NAME = map.get("name")+"";
									String MOBILEADDRESS = map.get("MobileAddress")+"";
									String phoneid = PrimaryKeyUtil.getPrimaryKey();
									UserContacts phone = new UserContacts();

									phone.setContId(phoneid);
									phone.setPhone(PHONE);
									//phone.setName(URLEncoder.encode(NAME, "utf-8"));
									phone.setName(NAME);
									phone.setCallcounts(StringUtils.isBlank(CALLCOUNTS) ? 0 : Integer.parseInt(CALLCOUNTS));
									phone.setCallTime(calltime);
									phone.setMobileAddress(MOBILEADDRESS);
									phone.setRelationship(4);
									phone.setRelationshipVal("其他");
									phone.setIsKnow(0);
									phone.setIsKnowVal("否");
									phone.setIsFillIn(0);
									phone.setIsFillInVal("否");
									phone.setAddDate(DateUtil.getTimestamp());
									phone.setApplyId(apply_id);

									String callMap = map.get("callMap").toString();
									JSONArray detaillist = JSON.parseArray(callMap);
									List<UserCallDetail> userCallDetails = new ArrayList<UserCallDetail>();
									for (int z = 0; z < detaillist.size(); z++) {
										String phonedetail = detaillist.get(z).toString();
										// System.out.println("======"+phonedetail);
										JSONObject detail = JSON.parseObject(phonedetail);
										String CALLMONEY = detail.get("CallMoney")+"";
										String CALLADDRESS = detail.get("CallAddress")+"";
										String CALLTYPE = detail.get("CallType")+"";
										String CALLTIME = detail.get("CallTime")+"";
										String CALLDURATION = detail.get("CallDuration")+"";
										String CALLWAY = detail.get("CallWay")+"";
										String CALLNUMBER = detail.get("CallNumber")+"";

										UserCallDetail callDetail = new UserCallDetail();
										callDetail.setPdid(PrimaryKeyUtil.getPrimaryKey());
										callDetail.setContId(phoneid);
										callDetail.setCallmoney(CALLMONEY);
										callDetail.setCalladdress(CALLADDRESS);
										callDetail.setCalltype(CALLTYPE);
										callDetail.setCalltime(CALLTIME);
										callDetail.setCallduration(CALLDURATION);
										callDetail.setCallway(CALLWAY);
										callDetail.setCallnumber(CALLNUMBER);
										userCallDetails.add(callDetail);
									}
									phone.setCallDetails(userCallDetails);

									contacts.add(phone);
								}
							}				
					}	
						
						if (null != contacts && contacts.size() > 0) {
							for (UserContacts cs : contacts) {
								cs.setApplyId(apply_id);
								userContactsMapper.insertSelective(cs);
								List<UserCallDetail> callDetails = cs.getCallDetails();
								for (UserCallDetail cd : callDetails) {
									userCallDetailMapper.insertSelective(cd);
								}

							}
						}	
						
				result.put("errorInfo", "添加成功！");
				result.put("errorCode", 0);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("添加客户信息使用时间：" + (endTime - startTime));
		}
		logger.info("添加客户信息单个:" + result);

		return result;
	}


	
	/**
	 * 
	 * 添加客户信息批量------通话详单
	 * @param request
	 * @return
	 * 
	 */
	
	@RequestMapping("addallcontractsinfo.do")
	@ResponseBody
	public Map<String, Object> addAllContractsInfo(HttpServletRequest request) {
		long startTime = System.currentTimeMillis(); // 获取开始时间
		
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// -----
            int index=0;
			List<UserApply>   UserApplylist=userApplyMapper.findIdIsContract();
			for(UserApply userApply:UserApplylist){
				String apply_id=userApply.getApplyId();
				List<UserContacts> UserContactslist=userContactsMapper.findByApplyId(apply_id,0);
				if(UserContactslist.size()==0){
					//if(index==0){
						NameValuePair[] contactdata = { new NameValuePair("cardNumber", userApply.getIdNumber())};
						String contactreult = HttpClientController.postcheck("http://113.200.105.37:8080/HSDC/Contact/ContactInfo", contactdata);
						logger.info("通讯录信息" + contactreult);
						JSONObject contactjason = JSON.parseObject(contactreult);
						/*if ("2222".equals(contactjason.get("errorCode").toString())) {
							result.put("errorInfo", "通讯录校验失败,请使用自己手机");
							result.put("errorCode", 1);
							return result;
						}*/
						if ("1111".equals(contactjason.get("errorCode").toString())) {
							result.put("errorInfo", "通话详单录入失败！！");
							result.put("errorCode", 1);
							return result;
						}
						
						List<UserContacts> contacts = null;
						if("0000".equals(contactjason.get("errorCode").toString())){
							if (contactjason.get("data") != null) {
								contacts = new ArrayList<UserContacts>();
								JSONArray list = JSON.parseArray(contactjason.get("data").toString());
								// System.out.println(list.size());
								// List<UserContacts> contacts = new ArrayList<UserContacts>();
								for (int i = 0; i < list.size(); i++) {
									String mapstr = list.get(i).toString();
									JSONObject map = JSON.parseObject(mapstr);
									String PHONE = map.get("phone")+"";
									String CALLCOUNTS = map.get("callCounts")+"";
									String calltime = map.get("callTime")+"";
									String NAME = map.get("name")+"";
									String MOBILEADDRESS = map.get("MobileAddress")+"";
									String phoneid = PrimaryKeyUtil.getPrimaryKey();
									UserContacts phone = new UserContacts();

									phone.setContId(phoneid);
									phone.setPhone(PHONE);
									//phone.setName(URLEncoder.encode(NAME, "utf-8"));
									phone.setName(NAME);
									phone.setCallcounts(StringUtils.isBlank(CALLCOUNTS) ? 0 : Integer.parseInt(CALLCOUNTS));
									phone.setCallTime(calltime);
									phone.setMobileAddress(MOBILEADDRESS);
									phone.setRelationship(4);
									phone.setRelationshipVal("其他");
									phone.setIsKnow(0);
									phone.setIsKnowVal("否");
									phone.setIsFillIn(0);
									phone.setIsFillInVal("否");
									phone.setAddDate(DateUtil.getTimestamp());
									phone.setApplyId(apply_id);

									String callMap = map.get("callMap").toString();
									JSONArray detaillist = JSON.parseArray(callMap);
									List<UserCallDetail> userCallDetails = new ArrayList<UserCallDetail>();
									for (int z = 0; z < detaillist.size(); z++) {
										String phonedetail = detaillist.get(z).toString();
										// System.out.println("======"+phonedetail);
										JSONObject detail = JSON.parseObject(phonedetail);
										String CALLMONEY = detail.get("CallMoney")+"";
										String CALLADDRESS = detail.get("CallAddress")+"";
										String CALLTYPE = detail.get("CallType")+"";
										String CALLTIME = detail.get("CallTime")+"";
										String CALLDURATION = detail.get("CallDuration")+"";
										String CALLWAY = detail.get("CallWay")+"";
										String CALLNUMBER = detail.get("CallNumber")+"";

										UserCallDetail callDetail = new UserCallDetail();
										callDetail.setPdid(PrimaryKeyUtil.getPrimaryKey());
										callDetail.setContId(phoneid);
										callDetail.setCallmoney(CALLMONEY);
										callDetail.setCalladdress(CALLADDRESS);
										callDetail.setCalltype(CALLTYPE);
										callDetail.setCalltime(CALLTIME);
										callDetail.setCallduration(CALLDURATION);
										callDetail.setCallway(CALLWAY);
										callDetail.setCallnumber(CALLNUMBER);
										userCallDetails.add(callDetail);
									}
									phone.setCallDetails(userCallDetails);

									contacts.add(phone);
								}
							}	
							
							if (null != contacts && contacts.size() > 0) {
								for (UserContacts cs : contacts) {
									cs.setApplyId(apply_id);
									userContactsMapper.insertSelective(cs);
									List<UserCallDetail> callDetails = cs.getCallDetails();
									for (UserCallDetail cd : callDetails) {
										userCallDetailMapper.insertSelective(cd);
									}

								}
							}
							
							index++;
					}	
						
						
				}
					
						
				}
			//}
			logger.info("#################总数量 "+index);
			
			result.put("errorInfo", "添加成功！");
			result.put("errorCode", 0);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errorInfo", "网络异常，请稍后！");
			result.put("errorCode", 1001);
		} finally {
			long endTime = System.currentTimeMillis(); // 获取结束时间
			logger.info("添加客户信息批量------通话详单使用时间：" + (endTime - startTime));
		}
		logger.info("添加客户信息批量------通话详单:" + result);

		return result;
	}

	
	@RequestMapping("getcallvisual.do")
	public  Map<String, Object> getCallvisual(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		String apply_id = request.getParameter("apply_id");
		String cardNumber = request.getParameter("cardNumber");
		//通讯可视化信息添加失败   
		String usercallvisual_url="http://113.200.105.37:8089/HsDataMode/calculate/getVisualModel";
		Map<String, Object> userCallVisualPara = new HashMap<String, Object>();
		userCallVisualPara.put("cardNumber",  cardNumber);
		String userCallVisualData = HttpClientUtil.doPost(usercallvisual_url, null, null, userCallVisualPara);
		Map<String, Object> userCallVisualJson = JSON.parseObject(userCallVisualData); // 阿里的对象转换

		UserCallVisual userCallVisual=null;
		if (!"0000".equals(userCallVisualJson.get("errorCode").toString())) {
			result.put("errorInfo", "通讯可视化信息添加失败！！");
			result.put("errorCode", 1);
			return result;
		}else{
		    userCallVisual=JSON.parseObject(userCallVisualData,UserCallVisual.class);
			userCallVisual.setApplyId(apply_id);
			userCallVisual.setAddData(DateUtil.getTimestamp());
			userCallVisual.setId(PrimaryKeyUtil.getPrimaryKey());
		}
		UserCallVisualMapper.insertSelective(userCallVisual);
		result.put("errorInfo", "通讯可视化信息成功");
		result.put("errorCode", 0);
		
		
		return result;
	}

}
