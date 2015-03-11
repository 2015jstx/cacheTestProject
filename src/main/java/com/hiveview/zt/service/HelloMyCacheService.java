/*  
 * @(#) HelloService.java Create on 2015年3月7日 下午6:09:55   
 *   
 * Copyright 2015 hiveview.
 */

package com.hiveview.zt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.hiveview.zt.dao.HelloDao;
import com.hiveview.zt.entity.HelloEntity;
import com.util.cache.annotation.FlushCache;
import com.util.cache.annotation.UseCache;

/**
 * @HelloService.java
 * @created at 2015年3月7日 下午6:09:55 by zhanghongliang@hiveview.com
 *
 * @desc
 *
 * @author zhanghongliang@hiveview.com
 * @version $Revision$
 * @update: $Date$
 */
@Service
public class HelloMyCacheService {

	static Logger logger = LoggerFactory.getLogger(HelloMyCacheService.class);
	@Autowired
	private HelloDao dao;

	
	/**
	 * 加缓存
	 * @Title: findById
	 * @data:2015年3月8日下午6:42:28
	 * @author:zhanghongliang@hiveview.com
	 *
	 * @param id
	 * @return
	 */
	@UseCache(head="hello")
	public HelloEntity findById(int id) {
		logger.info("service  获取hello entity");
		HelloEntity result = dao.getById(id);
		return result;
	}

	/**
	 * 清楚缓存
	 * @Title: save
	 * @data:2015年3月8日下午6:42:23
	 * @author:zhanghongliang@hiveview.com
	 *
	 * @param entity
	 * @return
	 */
	@FlushCache(head="hello")
	public HelloEntity delete(HelloEntity entity) {
		logger.info("service delete hello entity");
		return this.dao.delete(entity);
	}

	/**
	 * 更新缓存
	 * @Title: update
	 * @data:2015年3月8日下午6:42:15
	 * @author:zhanghongliang@hiveview.com
	 *
	 * @param entity
	 * @return
	 */
	@FlushCache(head="hello")
	public HelloEntity update(HelloEntity entity) {
		logger.info("service save hello entity");
		return this.dao.save(entity);
	}
}
