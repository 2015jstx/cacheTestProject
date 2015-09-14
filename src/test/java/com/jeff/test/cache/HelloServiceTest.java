/*  
 * @(#) HelloServiceTest.java Create on 2015年3月9日 下午12:06:52   
 *   
 * Copyright 2015 hiveview.
 */


package com.jeff.test.cache;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jeff.test.cache.entity.HelloEntity;
import com.jeff.test.cache.service.HelloService;

/**
 * @HelloServiceTest.java
 * @created at 2015年3月9日 下午12:06:52 by zhanghongliang@hiveview.com
 *
 * @desc
 *
 * @author  zhanghongliang@hiveview.com
 * @version $Revision$
 * @update: $Date$
 */
public class HelloServiceTest {

	Logger logger = LoggerFactory.getLogger(HelloServiceTest.class);

	static HelloService  service =null;
	
	@BeforeClass
	public static void init() {
		String[] configLocation = new String[]{"spring/spring-redis.xml","spring/spring-cache.xml","spring/spring-context.xml"};
		ApplicationContext apc = new ClassPathXmlApplicationContext(configLocation);
		service = apc.getBean(HelloService.class);
	}
	
	@Test
	public void test_find() throws Exception {
		System.out.println("11111111111111111");
		logger.info("");
		logger.info("");
		logger.info("test find");
		logger.info("第一次查询");
		Integer id = 1;
		
		HelloEntity entity2 = service.findById(id);
		logger.info("查询结果：{}",entity2.toString());
		Assert.assertEquals(id, entity2.getId());
		logger.info("--------------------------------------------------------------------");
		
		logger.info("第二次查询");
		entity2 = service.findById(id);
		logger.info("查询结果：{}",entity2.toString());
		Assert.assertEquals(id, entity2.getId());
	}
	
	@Test
	public void test_delete() throws Exception {
//		HelloService service = apc.getBean(HelloService.class);
		logger.info("");
		logger.info("");
		logger.info("test delete");
		logger.info("第一次查询");
		Integer id = 2;
		
		HelloEntity entity2 = service.findById(id);
		logger.info("查询结果：{}",entity2.toString());
		Assert.assertEquals(id, entity2.getId());
		
		logger.info("--------------------------------------------------------------------");
		logger.info("删除，清除缓存");
		service.delete(entity2);
		
		logger.info("--------------------------------------------------------------------");
		
		logger.info("第二次查询");
		entity2 = service.findById(id);
		Assert.assertEquals(entity2, null);
		logger.info("查询结果：{}",entity2);
	}
	
	@Test
	public void test_update() throws Exception {
		
		logger.info("");
		logger.info("");
		logger.info("test update");
//		HelloService service = apc.getBean(HelloService.class);
		logger.info("第一次查询");
		
		Integer id = 3;
		HelloEntity entity2 = service.findById(id);
		logger.info("查询结果：{}",entity2.toString());
		Assert.assertEquals(id, entity2.getId());
		logger.info("--------------------------------------------------------------------");
		logger.info("更新，刷新缓存");
		String name = "更新测试。。。。";
		entity2.setName(name);
		service.update(entity2);
		
		logger.info("--------------------------------------------------------------------");
		
		logger.info("第二次查询");
		entity2 = service.findById(id);
		logger.info("查询结果：{}",entity2.toString());
		logger.info("--------------------------------------------------------------------");
		
		logger.info("第三次查询");
		entity2 = service.findById(id);
		logger.info("查询结果：{}",entity2.toString());
		Assert.assertEquals(id, entity2.getId());
		Assert.assertEquals(name, entity2.getName());
		logger.info("--------------------------------------------------------------------");

		logger.info("第四次查询");
		entity2 = service.findById(id);
		logger.info("查询结果：{}",entity2.toString());
		Assert.assertEquals(id, entity2.getId());
		Assert.assertEquals(name, entity2.getName());
		logger.info("--------------------------------------------------------------------");
	}
}
