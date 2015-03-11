package com.util.cache.aspect;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.util.RedisManager;
import com.util.cache.annotation.FlushCache;

/**
 * 实现缓存放在redis中 的 hset 中
 * 
 * @author zhanghl
 * @date 2014-5-9 上午9:32:31
 */
@Component
@Aspect
@SuppressWarnings("all")
public class FlushCacheAspect implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8533443832703404492L;
	protected final static Log log = LogFactory.getLog(FlushCacheAspect.class);

	private static final String JOIN_SPLITE = ",";
	public static final String CLASS_SPLITE = "-";
	
	@Autowired(required = false)
	private RedisManager redisManager;

	@Pointcut("@annotation(com.util.cache.annotation.FlushCache)")
	private void doMethod() {
	}

	// 定义缓存引擎
	@Around("doMethod()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		if (redisManager == null) {
			log.warn("缓存框架redis 没有注入");
			return pjp.proceed();
		}

		log.debug("开始刷新缓存数据……");
		long starttime = System.currentTimeMillis();
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		Method method = methodSignature.getMethod();

		Class[] methodParameterTypes = method.getParameterTypes();
		Class target = pjp.getTarget().getClass();

		Method meth = target.getMethod(method.getName(), methodParameterTypes);

		FlushCache fc = meth.getAnnotation(FlushCache.class);

		String hkey = getHkey(target, fc);
		String fieldKey = getFieldKey(target, fc);

		try {
			if (StringUtils.isBlank(fieldKey)) {
				log.debug(String.format("fieldKey为空，清除(hkey=%s)全部内容", hkey));
				redisManager.del(hkey);
			} else {
				log.debug(String
						.format("hkey=%s ; fieldKey=%s", hkey, fieldKey));
				redisManager.hdel(hkey.getBytes(), fieldKey.getBytes());
			}

			clearJoinsCache(target,fc.joins());

			log.debug("缓存刷新结束,总用时:" + (System.currentTimeMillis() - starttime)
					+ "毫秒.");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ExceptionUtils.getFullStackTrace(e));
		} finally {
			return pjp.proceed();
		}
	}

	private String getFieldKey(Class targetClass, FlushCache fc) {
		StringBuffer result = new StringBuffer();
		String key = fc.key();
		if (fc.defaulttype() && StringUtils.isNotBlank(key)) {
			/*result.append(fc.head());
			result.append(key);
			result.append(fc.foot());*/
		} else {

		}

		return result.toString();
	}

	private String getHkey(Class targetClass, FlushCache fc) {
		StringBuffer result = new StringBuffer();
		if (fc.defaulttype()) {
			String head = fc.head();
			if(!head.contains(CLASS_SPLITE)){
				result.append(targetClass.getName());
				result.append(CLASS_SPLITE);
				result.append(head);
			}else{
				result.append(head);
			}
		} else {
			result.append(fc.key());
		}
		return result.toString();
	}

	private void clearJoinsCache(String joins) throws Exception {
		if (StringUtils.isNotBlank(joins)) {
			log.debug("级联清除," + joins);

			String[] args = StringUtils.split(joins, JOIN_SPLITE);

			for (String string : args) {
				if(StringUtils.isBlank(string)){
					log.debug("级联清除:" + string);
					redisManager.del(string);
				}
			}
		}
	}
	
	private void clearJoinsCache(Class targetClass,String joins) throws Exception {
		if (StringUtils.isNotBlank(joins)) {
			String classStr = targetClass.getName();

			String[] args = StringUtils.split(joins, JOIN_SPLITE);

			for (String string : args) {
				if(StringUtils.isNotBlank(string)){
					log.debug("级联清除:" + string);
					if(!string.contains(CLASS_SPLITE)){
						string = classStr + CLASS_SPLITE + string;
					}
					
					log.debug("级联清除缓存," + joins);
					redisManager.del(string);
				}
			}
		}
	}
}
