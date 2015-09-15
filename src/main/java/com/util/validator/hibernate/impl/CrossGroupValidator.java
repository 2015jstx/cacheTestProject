package com.util.validator.hibernate.impl;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

import org.hibernate.validator.util.scriptengine.ScriptEvaluator;
import org.hibernate.validator.util.scriptengine.ScriptEvaluatorFactory;

import com.util.validator.hibernate.CrossGroup;
import com.util.validator.hibernate.CrossGroupFiled;

/**
 * 
 * @author zhanghongliang@ooyanjing.com
 * @date   2015年9月14日 下午4:24:37
 */
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class CrossGroupValidator implements ConstraintValidator<CrossGroup, Object> {

	private static String languageName = "javascript";
	private static String alias = "_this";
	private ScriptEvaluatorFactory evaluatorFactory = ScriptEvaluatorFactory.getInstance();
	private ScriptEvaluator scriptEvaluator =null;
	
	private List<CrossGroupFieldC> groupFieldCList = new ArrayList<CrossGroupValidator.CrossGroupFieldC>();
	
	public CrossGroupValidator() throws ScriptException {
		try {
			scriptEvaluator = evaluatorFactory.getScriptEvaluatorByLanguageName( languageName  );
		} catch (ScriptException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
    public void initialize(CrossGroup constraintAnnotation) {
    	for (CrossGroupFiled filed : constraintAnnotation.fileds()) {
			String key = filed.key();
			String msg = filed.message();
			String params = filed.vParams();
			ConstraintValidator<Annotation, Object> validator = filed.vType().getValidator(params);
			
			groupFieldCList.add(new CrossGroupFieldC(key, msg, validator));
		}
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean result = true;
		if(value==null){
			return result;
		}
		
		Map<String,Object> valueMap = new HashMap<String, Object>();
		try {
			for (CrossGroupFieldC crossGroupFieldC : groupFieldCList) {
				String key = crossGroupFieldC.fieldkey;
				String msg = crossGroupFieldC.message;
				ConstraintValidator<Annotation, Object> validator = crossGroupFieldC.validator;
				
				if(!valueMap.containsKey(key)){
					Object v = scriptEvaluator.evaluate(alias + "." + key, value, alias );
					valueMap.put(key, v);
				}
				
//				LengthValidator
				Object v = valueMap.get(key);
				if(Boolean.FALSE.equals(validator.isValid(v, context))){
					context.disableDefaultConstraintViolation();
					context.buildConstraintViolationWithTemplate(key + "$$" + msg).addConstraintViolation();
					result = false;
				}
			}
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		
		return result;
    }
    
    class CrossGroupFieldC{
    	private String fieldkey;
    	private String message;
    	private ConstraintValidator<Annotation, Object> validator;
		
		public CrossGroupFieldC() {
			super();
		}
		public CrossGroupFieldC(String fieldkey, String message,
				ConstraintValidator<Annotation, Object> validator) {
			super();
			this.fieldkey = fieldkey;
			this.message = message;
			this.validator = validator;
		}
    }
}
