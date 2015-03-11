/*  
 * @(#) RedisManageTest.java Create on 2015年3月9日 上午11:46:43   
 *   
 * Copyright 2015 hiveview.
 */


package com.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @RedisManageTest.java
 * @created at 2015年3月9日 上午11:46:43 by zhanghongliang@hiveview.com
 *
 * @desc
 *
 * @author  zhanghongliang@hiveview.com
 * @version $Revision$
 * @update: $Date$
 */
public class RedisManageTest {
	@Test
	public void test_delete() throws Exception {
		ApplicationContext apc = new ClassPathXmlApplicationContext("spring/spring-redis.xml");
		
		RedisManager redis = apc.getBean(RedisManager.class);
	}
}
