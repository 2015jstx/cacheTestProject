package com.util.validator.hibernate;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintTarget;
import javax.validation.Payload;

import com.util.validator.hibernate.impl.CrossGroupValidator;

@Constraint(validatedBy = { CrossGroupValidator.class })
@Target({ TYPE, FIELD, PARAMETER, METHOD, CONSTRUCTOR, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
/**
 * @author zhanghongliang@ooyanjing.com
 * @date   2015年9月14日 下午4:21:14
 */
public @interface CrossGroup {
	// PropertyScriptAssert[] fileds() default {};
	CrossGroupFiled[] fileds() default {};

	String message() default "error";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String alias() default "";

	String property() default "";

	ConstraintTarget appliesTo() default ConstraintTarget.IMPLICIT;
}
