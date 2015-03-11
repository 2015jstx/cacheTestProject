package com.util.cache.aspect;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import com.util.RedisManager;
import com.util.cache.annotation.UseCache;

@SuppressWarnings("all")
@Component
@Aspect
public class UseCacheAspect implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5111467072144324326L;

	protected final static Log log = LogFactory.getLog(UseCacheAspect.class);

	@Autowired(required = false)
	private RedisManager redisManager;

	private int expireTime = 1200;

	private static MessageDigest md5 = null;
	
	static{
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	
	@Pointcut("@annotation(com.util.cache.annotation.UseCache)")
	private void doMethod() {
	}

	/**
	 * 缓存
	 * 
	 * <pre>
	 * 		对象序列化后 缓存在redis的 hset    
	 * 				key 	  默认为：className+head   
	 * 				fieldkey 默认为：head + key + foot
	 * 				value 	 为缓存对象，需要可以序列化
	 * 
	 * 				缓存时间：默认为1200s，可以通过UseCache 的time 设置。
	 * </pre>
	 * 
	 * @Title: doAround
	 * @data:2014-5-9上午9:27:25
	 * @author:zhanghl
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("doMethod()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		if (redisManager == null) {
			log.warn("缓存框架redis 没有注入");
			return pjp.proceed();
		}

		log.debug("进入缓存区……");
		Method method = ((MethodSignature) pjp.getSignature()).getMethod();
		Class[] methodParameterTypes = method.getParameterTypes();
		Class target = pjp.getTarget().getClass();

		Method meth = target.getMethod(method.getName(), methodParameterTypes);

		UseCache uc = meth.getAnnotation(UseCache.class);

		Object[] objs = pjp.getArgs();

		String hkey = getHkey(method, pjp.getArgs(), uc, target);

		String fieldKey = getFieldKey(target.getName(), uc, objs);
		int ucTime = uc.time();
		int expireTime = ucTime > 0 ? ucTime : this.expireTime;
		Object result = null;

		try {
			byte[] cacheData = redisManager.hget(hkey.getBytes(),
					fieldKey.getBytes());

			if (cacheData == null) {
				result = pjp.proceed();
				if (result != null) {
					byte[] value = SerializationUtils.serialize(result);
					// 加入缓存
					log.debug(String.format("hkey=%s,fieldkey=%s,time=%d 加入缓存",
							hkey, fieldKey, expireTime));
					redisManager.hset(hkey.getBytes(), fieldKey.getBytes(),
							value);
				}
			} else {
				result = SerializationUtils.deserialize(cacheData);
				log.debug("从缓存中获取数据");
			}
			// 设置失效时间
			if (ucTime != -1) {
				redisManager.expire(hkey.getBytes(), expireTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ExceptionUtils.getFullStackTrace(e));
			return pjp.proceed();
		}
		return result;
	}

	/**
	 * 
	 * @Title: getHkey
	 * @data:2014-5-13下午6:22:38
	 * @author:zhanghl
	 *
	 * @param method
	 * @param args
	 * @param uc  head 中如果有- 就不拼加 类名
	 * @param target
	 * @return
	 */
	private String getHkey(Method method, Object[] args, UseCache uc,
			Class target) {
		StringBuffer result = new StringBuffer();
		if (uc.defaulttype()) {
			String head = uc.head();
			
			if(!head.contains(FlushCacheAspect.CLASS_SPLITE)){
				result.append(target.getName());
				result.append(FlushCacheAspect.CLASS_SPLITE);
				result.append(uc.head());
			}else{
				result.append(uc.head());
			}
		} else {
			result.append(uc.key());
		}

		return result.toString();
	}

	public String getFieldKey(String targetClassName, UseCache uc,
			Object[] args) throws SecurityException, NoSuchMethodException {
		StringBuffer key = new StringBuffer();
		if (uc.defaulttype()) {
			key.append(uc.head());
			key.append(uc.key());
			key.append(uc.foot());

			if (args != null && args.length > 0) {
				List<String> list = new ArrayList<String>();
				for (Object object : args) {
					list.add(ClassType.defaultType.execute(object));
				}

				Collections.sort(list);
				String md5 = getMd5Code(list);
				key.append(md5);
			}

		} else {
			key.append(uc.key());
		}

		// if (args != null && args.length > 0) {
		// key.append(String.valueOf(args[0]));
		// }

		return key.toString();
	}

	private String getMd5Code(List<String> list) {
		StringBuffer result = new StringBuffer();

		if (list != null && list.size() > 0) {
			for (String string : list) {
				result.append(string);
			}
		}

		byte[] bytearray = md5.digest(result.toString().getBytes());
		return new String(bytearray);
	}

	private String getKeyExpand(Method method, Object[] args) {

		String methodName = method.getName();

		StringBuffer key = new StringBuffer();

		key.append(methodName);
		for (Object c : args) {
			key.append(c);
		}
		return key.toString();
	}

	public void doThrowing(JoinPoint jp, Throwable ex) {
		log.info("throwing:" + jp.getTarget().getClass().getName() + "."
				+ jp.getSignature().getName() + " error!");
	}

	public static void main(String[] args) {
		args = new String[] { "3", "2", "1", "c" };

		System.out.println(args instanceof Object[]);

		List list = Arrays.asList(args);

		String str = ClassType.defaultType.execute(list);
		System.out.println(str);
	}

	public enum ClassType {
		collection() {
			@Override
			public String toString(Object obj) {
				StringBuffer result = new StringBuffer();
				if (obj instanceof Collection) {
					Collection<?> coll = (Collection<?>) obj;
					if (coll != null && coll.size() > 0) {
						List<String> list = new ArrayList<String>();

						for (Object item : coll) {
							list.add(execute(item));
						}

						Collections.sort(list);

						for (String string : list) {
							result.append(string);
						}
					}
				}
				return result.toString();
			}
		},
		map() {
			@Override
			public String toString(Object obj) {
				if (obj != null && obj instanceof Map) {
					Map map = (Map) obj;

					if (map.size() > 0) {
						List<Object> list = new ArrayList<Object>(map.size());
						for (Object key : map.keySet()) {
							Object value = map.get(key);
							if (value != null)
								list.add(map.get(key));
						}
						return execute(list);
					}
				}

				return "";
			}
		},
		BaseModel() {
			@Override
			public String toString(Object obj) {
				String result = "";
//				if (obj instanceof BaseModel && obj != null) {
//					BaseModel baseModel = (BaseModel) obj;
//					result = baseModel.getId();
//				}

				return result;
			}
		},
		array() {
			@Override
			public String toString(Object obj) {
				StringBuffer result = new StringBuffer();
				if (obj instanceof Object[]) {
					Object[] coll = (Object[]) obj;
					List lists = Arrays.asList(coll);
					result.append(execute(lists));
				}
				return result.toString();
			}
		},
		defaultType;

		public String toString(Object obj) {
			if (obj != null) {
				return String.valueOf(obj);
			}
			return "";
		}

		public String execute(Object obj) {
			StringBuffer resultBu = new StringBuffer();
			if (obj != null) {
				if (obj instanceof Collection) {
					resultBu.append(collection.toString(obj));
				} else if (obj instanceof Map) {
					resultBu.append(map.toString(obj));
//				} else if (obj instanceof BaseModel) {
//					resultBu.append(BaseModel.toString(obj));
				} else if (obj instanceof Object[]) {
					resultBu.append(array.toString(obj));
				} else {
					resultBu.append(defaultType.toString(obj));
				}
			}
			return resultBu.toString();
		}
	}
}
