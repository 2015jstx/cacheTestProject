/*  
 * @(#) RedisCacheFactoryBean.java Create on 2015年3月9日 下午3:15:42   
 *   
 * Copyright 2015 hiveview.
 */


package com.util.cache.springredis;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @RedisCacheFactoryBean.java
 * @created at 2015年3月9日 下午3:15:42 by zhanghongliang@hiveview.com
 *
 * @desc
 *
 * @author  zhanghongliang@hiveview.com
 * @version $Revision$
 * @update: $Date$
 */
public class RedisCacheFactoryBean implements FactoryBean<RedisCache>, BeanNameAware, InitializingBean{
	private String name = "";
	private RedisCache cache;
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public RedisCache getObject() throws Exception {
		// TODO Auto-generated method stub
				
		return this.cache;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		
		return cache.getClass();
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public void setBeanName(String arg0) {
		// TODO Auto-generated method stub
		if(arg0!=null && arg0.trim().length()>0){
//			setName(arg0);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub		
		if (cache == null){
			cache = new RedisCache(name);
		}
	}
}