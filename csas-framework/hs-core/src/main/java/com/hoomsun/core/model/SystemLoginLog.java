/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.core.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 作者：ywzou <br>
 * 创建时间：2017年9月30日 <br>
 * 描述：系统登录日志
 * 
 * 
 * @Id -
 *     文档的唯一标识，在mongodb中为ObjectId，它是唯一的，通过时间戳+机器标识+进程ID+自增计数器（确保同一秒内产生的Id不会冲突）构成。
 * @Document -
 *           把一个java类声明为mongodb的文档，可以通过collection参数指定这个类对应的文档。@Document(collection="mongodb")
 *           mongodb对应表
 * @DBRef -
 *        声明类似于关系数据库的关联关系。ps：暂不支持级联的保存功能，当你在本实例中修改了DERef对象里面的值时，单独保存本实例并不能保存DERef引用的对象，它要另外保存，如下面例子的Person和Account。
 * @Indexed - 声明该字段需要索引，建索引可以大大的提高查询效率。
 * @CompoundIndex - 复合索引的声明，建复合索引可以有效地提高多字段的查询效率。
 * @GeoSpatialIndexed - 声明该字段为地理信息的索引。
 * @Transient - 映射忽略的字段，该字段不会保存到mongodb。
 * @PersistenceConstructor -
 *                         声明构造函数，作用是把从数据库取出的数据实例化为对象。该构造函数传入的值为从DBObject中取出的数据
 */
@Document(collection="SYSTEM_LOGIN_LOG")
public class SystemLoginLog {
	@Id
	private String id;// 主键
	private String loginName;// 登录账号
	private String empName;// 登录人
	private Date loginDate;// 登录时间
	private String loginIP;// 登录IP
	private String loginClient;// 登录终端

	public SystemLoginLog() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}

	public String getLoginClient() {
		return loginClient;
	}

	public void setLoginClient(String loginClient) {
		this.loginClient = loginClient;
	}

	@Override
	public String toString() {
		return "SystemLoginLog [id=" + id + ", loginName=" + loginName + ", empName=" + empName + ", loginDate=" + loginDate + ", loginIP=" + loginIP + ", loginClient=" + loginClient + "]";
	}

}
