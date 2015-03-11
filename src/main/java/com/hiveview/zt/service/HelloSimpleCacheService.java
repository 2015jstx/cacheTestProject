/*  
 * @(#) HelloSimpleService.java Create on 2015年3月9日 上午11:23:14   
 *   
 * Copyright 2015 hiveview.
 */

package com.hiveview.zt.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiveview.zt.dao.HelloDao;
import com.hiveview.zt.entity.HelloEntity;

/**
 * @HelloSimpleService.java
 * @created at 2015年3月9日 上午11:23:14 by zhanghongliang@hiveview.com
 *
 * @desc
 *
 * @author zhanghongliang@hiveview.com
 * @version $Revision$
 * @update: $Date$
 */
@Service
public class HelloSimpleCacheService {

	Logger logger = LoggerFactory.getLogger(HelloService.class);
	@Autowired
	private HelloDao dao;

	private static Map<Integer, HelloEntity> cache = new HashMap<Integer, HelloEntity>();
	private Object obj = new Object();

	/**
	 * 加缓存
	 * 
	 * @Title: findById
	 * @data:2015年3月8日下午6:42:28
	 * @author:zhanghongliang@hiveview.com
	 *
	 * @param id
	 * @return
	 */
	public HelloEntity findById(int id) {
		HelloEntity result = cache.get(id);
		if (result == null) {
			synchronized (obj) {
				result = cache.get(id);
				logger.info("service  获取hello entity");
				if (result == null) {
					result = this.dao.getById(id);
					if (result != null) {
						cache.put(id, result);
					}
				}
			}
		}
		
		return result;

	}

	/**
	 * 清楚缓存
	 * 
	 * @Title: save
	 * @data:2015年3月8日下午6:42:23
	 * @author:zhanghongliang@hiveview.com
	 *
	 * @param entity
	 * @return
	 */
	public HelloEntity delete(HelloEntity entity) {
		cache.remove(entity.getId());
		
		logger.info("service delete hello entity");
		return this.dao.delete(entity);
	}

	/**
	 * 更新缓存
	 * 
	 * @Title: update
	 * @data:2015年3月8日下午6:42:15
	 * @author:zhanghongliang@hiveview.com
	 *
	 * @param entity
	 * @return
	 */
	public HelloEntity update(HelloEntity entity) {
		cache.put(entity.getId(), entity);
		
		return this.dao.save(entity);
	}

	public static void main(String[] args) {

	}

}
