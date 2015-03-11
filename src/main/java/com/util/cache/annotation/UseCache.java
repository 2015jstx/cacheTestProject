/*
 *  * 缓存自动生成策略<br/>
 * 当某个方法上打上这个注解时，代表自动缓存该方法返回的值。<br/>
 * 缓存KEY值默认为【head+当前方法所在类的名+foot + key】<br/>
 * 缓存中存在的内容应该为一个MAP，MAP的KEY应该为【方法名+参数类型】<br/>
 * MAP的内容为缓存的方法返回的值<br/>
 * 如果"defaulttype"设为false则key值生成策略变为head+foot<br/>
 */

package com.util.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * <pre>
 * 		主要设置head的值
 * 		
 * 		head 的值如果有"-" 则不加类全名，清除时规则一样
 * 
 * 
 * 		对象序列化后 缓存在redis的 hset    
 * 				key 	  默认为：className-head   
 * 				fieldkey 默认为：head + key + foot
 * 				value 	 为缓存对象，需要可以序列化
 * 
 * 				缓存时间：默认为1200s，可以通过UseCache 的time 设置。
 * 
 * 
 *   用spring aop实现，注意在当前类调用当前类方法时，aop无法拦截
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface UseCache {

	String head() default "";

	String foot() default "";

	boolean defaulttype() default true;

	int time() default 120;

	String key() default "";
}
