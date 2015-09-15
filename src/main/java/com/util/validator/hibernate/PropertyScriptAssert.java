package com.util.validator.hibernate;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.util.validator.hibernate.impl.PropertyScriptAssertValidator;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {PropertyScriptAssertValidator.class})
@Documented
public @interface PropertyScriptAssert {

    String message() default "{org.hibernate.validator.constraints.ScriptAssert.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String lang();

    String script();

    String alias() default "_this";

    String property();

    @Target({ TYPE })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        PropertyScriptAssert[] value();
    }
}
