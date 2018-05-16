/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk.model;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月8日 <br>
 * 描述：数据同步日志
 */
@Document(collection = "RK_SYNCHRONOUS_LOG")
public class SynchronousLog {
	@Id
	private String Id;
	@Indexed
	private Date doTime;// 同步时间
	@Indexed
	private Date startTime;// 参数 开始时间
	@Indexed
	private Date endTime;// 参数 结束时间
	@Indexed
	private String objId;// 同步ID
	private Integer success;// 成功条数
	private Integer error;// 失败条数
	private String errorMsg;// 失败信息
	private Long queryDuration;// 耗时
	private Long insertDuration;// 耗时
	private Integer dataType;// 数据类型 1:申请数据 2:通话详单数据
	private String dataTypeVal;
	private String handleEmp;
	private String handleEmpName;
	private Integer synType;// 1:接口推送 2：自动同步 3：手动同步 4:被动拉取
	private String synTypeVal;// 1:接口推送 2：自动同步 3：手动同步 4:被动拉取
	private String pushIp;//推送数据的IP
	
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public Date getDoTime() {
		return doTime;
	}

	public void setDoTime(Date doTime) {
		this.doTime = doTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public Integer getError() {
		return error;
	}

	public void setError(Integer error) {
		this.error = error;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Long getQueryDuration() {
		return queryDuration;
	}

	public void setQueryDuration(Long queryDuration) {
		this.queryDuration = queryDuration;
	}

	public Long getInsertDuration() {
		return insertDuration;
	}

	public void setInsertDuration(Long insertDuration) {
		this.insertDuration = insertDuration;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getDataTypeVal() {
		if (StringUtils.isBlank(dataTypeVal)) {
			if (null != dataType) {
				if (1 == dataType) {
					setDataTypeVal("申请数据");
				}else if (2 == dataType) {
					setDataTypeVal("通话详单");
				}else if (3 == dataType) {
					setDataTypeVal("申请数据&通话详单");
				}else {
					setDataTypeVal("未知");
				}
			}
		}
		return dataTypeVal;
	}

	public void setDataTypeVal(String dataTypeVal) {
		this.dataTypeVal = dataTypeVal;
	}

	public String getHandleEmp() {
		return handleEmp;
	}

	public void setHandleEmp(String handleEmp) {
		this.handleEmp = handleEmp;
	}

	public String getHandleEmpName() {
		return handleEmpName;
	}

	public void setHandleEmpName(String handleEmpName) {
		this.handleEmpName = handleEmpName;
	}

	public Integer getSynType() {
		return synType;
	}

	public void setSynType(Integer synType) {
		this.synType = synType;
	}

	public String getPushIp() {
		return pushIp;
	}

	public void setPushIp(String pushIp) {
		this.pushIp = pushIp;
	}

	public String getSynTypeVal() {
//		synTypeVal;// 1:接口推送 2：自动同步 3：手动同步
		if (StringUtils.isBlank(synTypeVal)) {
			if (null != synType) {
				if (1 == synType) {
					setDataTypeVal("接口推送");
				}else if (2 == synType) {
					setDataTypeVal("自动同步");
				}else if (3 == synType) {
					setDataTypeVal("手动同步");
				}else if (4 == synType) {
					setDataTypeVal("被动拉取");
				}else{
					setDataTypeVal("未知");
				}
			}
		}
		return synTypeVal;
	}

	public void setSynTypeVal(String synTypeVal) {
		this.synTypeVal = synTypeVal;
	}

}
