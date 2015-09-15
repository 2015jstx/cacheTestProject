/*  
 * @(#) MallValidation.java Create on 2015年8月4日 下午3:34:37   
 *   
 * Copyright 2015 .ooyanjing
 */

package com.util.validator.hibernate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @MallValidation.java
 * @created at 2015年8月4日 下午3:34:37 by zhanghongliang@ooyanjing.com
 *
 * @desc
 *
 * @author zhanghongliang@ooyanjing.com
 * @version $Revision$
 * @update: $Date$
 */
@Target(value = { ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CrossGroupFiled {
	String key() default "";

	String vParams() default "";

	ValidEnum vType();

	String message() default "参数校验不通过";
}
