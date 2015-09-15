/*  
 * @(#) a.java Create on 2015年9月15日 下午3:53:13   
 *   
 * Copyright 2015 .ooyanjing
 */


package com.util.validator.hibernate;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.impl.CreditCardNumberValidator;

import com.util.validator.hibernate.impl.IdCardNumberValidator;

/**
 * @a.java
 * @created at 2015年9月15日 下午3:53:13 by 253587517@qq.com
 *
 * @desc
 *
 * @author  zhanghongliang@ooyanjing.com
 * @version $Revision$
 * @update: $Date$
 */
@Documented
@Constraint(validatedBy = IdCardNumberValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
public @interface IdCardNumber {
	String message() default "{org.hibernate.validator.constraints.CreditCardNumber.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	/**
	 * Defines several {@code @CreditCardNumber} annotations on the same element.
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		IdCardNumber[] value();
	}
}