/**
 * Copyright www.hoomsun.com 红上金融信息服务（上海）有限公司
 */
package com.hoomsun.risk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hoomsun.risk.dao.mongo.UserApplyDao;
import com.hoomsun.risk.model.UserApply;

/**
 * 作者：ywzou <br>
 * 创建时间：2018年1月12日 <br>
 * 描述：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-cfg.xml")
public class UserApplyTest {
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private UserApplyDao userApplyDao;
	
	@Test
	public void findCode() {
		UserApply apply = userApplyDao.findByIdNumber("230805198708133015");
		System.out.println("##############:" + apply.getApplyCode());
	}
}
