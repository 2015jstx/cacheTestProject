/*  
 * @(#) HelloDao.java Create on 2015年3月7日 下午6:10:13   
 *   
 * Copyright 2015 hiveview.
 */


package com.hiveview.zt.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hiveview.zt.entity.HelloEntity;

/**
 * @HelloDao.java
 * @created at 2015年3月7日 下午6:10:13 by zhanghongliang@hiveview.com
 *
 * @desc
 *
 * @author  zhanghongliang@hiveview.com
 * @version $Revision$
 * @update: $Date$
 */
@Repository
public class HelloDao {

	private static Map<Integer,HelloEntity> map= new HashMap<Integer, HelloEntity>();
	
	static{
		map.put(1, new HelloEntity(1,"张三",new Date()));
		map.put(2, new HelloEntity(2,"李四",new Date()));
		map.put(3, new HelloEntity(3,"王五",new Date()));
		map.put(4, new HelloEntity(4,"赵六",new Date()));
	}
	
	public HelloEntity getById(int id) {
		return map.get(id);
	}

	public HelloEntity save(HelloEntity entity) {
		if(map.containsKey(entity.getId())){
			map.put(entity.getId(), entity);
		}
		
		return map.get(entity.getId());
	}

	public HelloEntity delete(HelloEntity entity) {
		map.remove(entity.getId());
		return entity;
	}
}
