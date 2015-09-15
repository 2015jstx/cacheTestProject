/*  
 * @(#) StdServiceTest.java Create on 2015年9月15日 下午2:00:20   
 *   
 * Copyright 2015 .ooyanjing
 */


package com.jeff.test.validator;

import java.util.Date;
import java.util.Set;

import org.hibernate.validator.constraints.impl.LuhnValidator;
import org.hibernate.validator.method.MethodConstraintViolation;
import org.hibernate.validator.method.MethodConstraintViolationException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jeff.test.cache.entity.HelloEntity;

/**
 * @StdServiceTest.java
 * @created at 2015年9月15日 下午2:00:20 by 253587517@qq.com
 *
 * @desc
 *
 * @author  zhanghongliang@ooyanjing.com
 * @version $Revision$
 * @update: $Date$
 */
public class StdServiceTest {

	static StdService service =null;
	
	@BeforeClass
	public static void init() {
		String[] configLocation = new String[]{"spring/spring-redis.xml","spring/spring-cache.xml","spring/spring-context.xml"};
		ApplicationContext apc = new ClassPathXmlApplicationContext(configLocation);
		service = apc.getBean(StdService.class);
	}
	
	@Test
	public void test_validatorGroup() throws Exception {
		HelloEntity h = new HelloEntity(1, "", new Date());
		h.setIdcard("130481");
		try {
			service.saveModel(h, "h");
		} catch (MethodConstraintViolationException e) {
			Set<MethodConstraintViolation<?>> a = e.getConstraintViolations();
			for (MethodConstraintViolation<?> methodConstraintViolation : a) {
				System.out.println("message:" + methodConstraintViolation.getMessage());
				System.out.println("name:" + methodConstraintViolation.getParameterName());
			}
		}
		System.out.println("1111111111");
	}
	
	@Test
	public void test_careValid() throws Exception {
	}
	public static void main(String[] args) {
		LuhnValidator luhnValidator = new LuhnValidator( 2 );
		String value = "62270000132231";
		boolean boo = luhnValidator.passesLuhnTest(value );
		System.out.println(boo);
	}
}
