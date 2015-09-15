package com.util.validator.hibernate.impl;

import javax.script.ScriptException;
import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.util.scriptengine.ScriptEvaluator;
import org.hibernate.validator.util.scriptengine.ScriptEvaluatorFactory;
import org.springframework.util.StringUtils;

import com.util.validator.hibernate.CrossParameterScriptAssert;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-12-15
 * <p>Version: 1.0
 */
public class CrossParameterScriptAssertClassValidator implements ConstraintValidator<CrossParameterScriptAssert, Object> {

    private String script;
    private String languageName;
    private String alias;
    private String property;
    private String message;

    public void initialize(CrossParameterScriptAssert constraintAnnotation) {
        validateParameters( constraintAnnotation );

        this.script = constraintAnnotation.script();
        this.languageName = constraintAnnotation.lang();
        this.alias = constraintAnnotation.alias();
        this.property = constraintAnnotation.property();
        this.message = constraintAnnotation.message();
    }

    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

        Object evaluationResult;
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
//            throw log.getErrorDuringScriptExecutionException( script, e );
            throw new RuntimeException( script, e );
        }

       /* if ( evaluationResult == null ) {
            throw log.getScriptMustReturnTrueOrFalseException( script );
        }
        if ( !( evaluationResult instanceof Boolean ) ) {
            throw log.getScriptMustReturnTrueOrFalseException(
                    script,
                    evaluationResult,
                    evaluationResult.getClass().getCanonicalName()
            );
        }*/

        if(Boolean.FALSE.equals(evaluationResult) && StringUtils.hasLength(property)) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(property)
                    .addConstraintViolation();
        }

        return Boolean.TRUE.equals( evaluationResult );
    }

    private void validateParameters(CrossParameterScriptAssert constraintAnnotation) {
    }
}
