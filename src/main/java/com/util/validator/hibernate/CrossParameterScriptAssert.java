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

import com.util.validator.hibernate.impl.CrossParameterScriptAssertClassValidator;
import com.util.validator.hibernate.impl.CrossParameterScriptAssertParameterValidator;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-12-15
 * <p>Version: 1.0
 */
@Constraint(validatedBy = {
        CrossParameterScriptAssertClassValidator.class,
        CrossParameterScriptAssertParameterValidator.class
})
@Target({ TYPE, FIELD, PARAMETER, METHOD, CONSTRUCTOR, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface CrossParameterScriptAssert {
    String message() default "error";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    String script();
    String lang();
    String alias() default "";
    String property() default "";
    ConstraintTarget validationAppliesTo() default ConstraintTarget.IMPLICIT;
}
