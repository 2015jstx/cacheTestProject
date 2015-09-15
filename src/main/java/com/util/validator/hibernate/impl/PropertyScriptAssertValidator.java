package com.util.validator.hibernate.impl;

import javax.script.ScriptException;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.util.scriptengine.ScriptEvaluator;
import org.hibernate.validator.util.scriptengine.ScriptEvaluatorFactory;
import org.slf4j.Logger;

import com.util.validator.hibernate.PropertyScriptAssert;

public class PropertyScriptAssertValidator implements ConstraintValidator<PropertyScriptAssert, Object> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Process.class);

    private String script;
    private String languageName;
    private String alias;
    private String property;
    private String message;

    public void initialize(PropertyScriptAssert constraintAnnotation) {
        validateParameters( constraintAnnotation );

        this.script = constraintAnnotation.script();
        this.languageName = constraintAnnotation.lang();
        this.alias = constraintAnnotation.alias();
        this.property = constraintAnnotation.property();
        this.message = constraintAnnotation.message();
    }

    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

        Object evaluationResult = null;
        ScriptEvaluator scriptEvaluator;

        try {
            ScriptEvaluatorFactory evaluatorFactory = ScriptEvaluatorFactory.getInstance();
            scriptEvaluator = evaluatorFactory.getScriptEvaluatorByLanguageName( languageName );
        }
        catch ( ScriptException e ) {
            throw new ConstraintDeclarationException( e );
        }

        try {
            evaluationResult = scriptEvaluator.evaluate( script, value, alias );
        }
        catch ( Exception e ) {
            throw new RuntimeException( script,e);
        }

//        if ( evaluationResult == null ) {
//            throw log.getScriptMustReturnTrueOrFalseException( script );
//        }
//        if ( !( evaluationResult instanceof Boolean ) ) {
//            throw log.getScriptMustReturnTrueOrFalseException(
//                    script,
//                    evaluationResult,
//                    evaluationResult.getClass().getCanonicalName()
//            );
//        }

        if(Boolean.FALSE.equals(evaluationResult)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(message)
//                    .addPropertyNode(property)
                    .addConstraintViolation();
        }

        return Boolean.TRUE.equals( evaluationResult );
    }

    private void validateParameters(PropertyScriptAssert constraintAnnotation) {
    }
}
