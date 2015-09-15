package com.util.validator.hibernate;


import javax.validation.Constraint;
import javax.validation.Payload;

import com.util.validator.hibernate.impl.CrossParameterValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Constraint(validatedBy = CrossParameterValidator.class)
@Target({ METHOD, CONSTRUCTOR, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface CrossParameter {

    String message() default "{password.confirmation.error}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}
