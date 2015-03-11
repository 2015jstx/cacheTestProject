/*  
 * @(#) RedisCacheManager.java Create on 2015年3月9日 下午3:14:23   
 *   
 * Copyright 2015 hiveview.
 */

package com.util.springredis;

import java.util.Collection;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.Jedis;

/**
 * @RedisCacheManager.java
 * @created at 2015年3月9日 下午3:14:23 by zhanghongliang@hiveview.com
 *
 * @desc
 *
 * @author zhanghongliang@hiveview.com
 * @version $Revision$
 * @update: $Date$
 */
public class RedisCacheManager extends AbstractCacheManager {
		private Collection<? extends Cache> caches;
		
		private JedisConnectionFactory connectionFactory;
		
		public void setCaches(Collection<? extends Cache> caches) {
			this.caches = caches;
		}

		@Override
		protected Collection<? extends Cache> loadCaches() {
			// TODO Auto-generated method stub
//			int i = 0;

//			JedisPoolConfig config = new JedisPoolConfig();
//			config.setMaxActive(100);
//			config.setMaxIdle(20);
//			config.setMaxWait(1000);
//			config.setTestOnBorrow(false);
//
//			JedisPool pool = new JedisPool(config, "localhost");
//			for (Cache cache : caches) {
//				Jedis jedis = pool.getResource();
//				jedis.auth("wangqi");
//				jedis.select(i++);
//
//				RedisCache rc = (RedisCache) cache;
//				rc.setCache(jedis);
//			}

			for (Cache cache : caches) {
				Jedis jedis = getConnectionFactory().getShardInfo().createResource();
				RedisCache rc = (RedisCache) cache;
				rc.setCache(jedis);
			}
			return this.caches;
		}

		public JedisConnectionFactory getConnectionFactory() {
			return connectionFactory;
		}

		public void setConnectionFactory(JedisConnectionFactory connectionFactory) {
			this.connectionFactory = connectionFactory;
		}
	}
