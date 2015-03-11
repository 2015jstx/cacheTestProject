/*  
 * @(#) RedisManager.java Create on 2015年3月7日 下午5:14:09   
 *   
 * Copyright 2015 hiveview.
 */


package com.util;

import java.util.Calendar;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @RedisManager.java
 * @created at 2015年3月7日 下午5:14:09 by zhanghongliang@hiveview.com
 *
 * @desc
 *
 * @author  zhanghongliang@hiveview.com
 * @version $Revision$
 * @update: $Date$
 */
public class RedisManager {

	private RedisTemplate<String,byte[]> redisTemplate;

	public RedisTemplate<String,byte[]> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String,byte[]> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void del(final String string) {
		this.redisTemplate.execute(new RedisCallback<Object>() {

			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.del(string.getBytes());
				return 0;
			}
		});
	}

	public void hdel(final byte[] bytes, final byte[] bytes2) {
		// TODO Auto-generated method stub
//		this.redisTemplate.
		this.redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.hDel(bytes, bytes2);
			}
		});
	}

	public byte[] hget(final byte[] bytes, final byte[] bytes2) {
		return this.redisTemplate.execute(new RedisCallback<byte[]>() {
			public byte[] doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.hGet(bytes, bytes2);
			}
		});
	}

	public void hset(final byte[] bytes, final  byte[] bytes2, final byte[] value) {
		// TODO Auto-generated method stub
		this.redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.hSet(bytes, bytes2, value);
				return null;
			}
		});
	}

	public void expire(byte[] bytes, int expireTime) {
		// TODO Auto-generated method stub
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.SECOND, expireTime);
		this.redisTemplate.expireAt(new String(bytes), ca.getTime());
	}
}
