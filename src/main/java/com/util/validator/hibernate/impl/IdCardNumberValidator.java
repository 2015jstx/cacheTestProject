/*  
 * @(#) IdCardValidator.java Create on 2015年9月15日 下午3:51:15   
 *   
 * Copyright 2015 .ooyanjing
 */


package com.util.validator.hibernate.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.util.idcard.IdcardValidator;
import com.util.validator.hibernate.IdCardNumber;

/**
 * @IdCardValidator.java
 * @created at 2015年9月15日 下午3:51:15 by 253587517@qq.com
 *
 * @desc
 *
 * @author  zhanghongliang@ooyanjing.com
 * @version $Revision$
 * @update: $Date$
 */
public class IdCardNumberValidator implements ConstraintValidator<IdCardNumber, String> {
	private IdcardValidator idcardValidator = IdcardValidator.getInstall();
	@Override
	public void initialize(IdCardNumber constraintAnnotation) {
		idcardValidator = IdcardValidator.getInstall();
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if ( value == null ) {
			return true;
		}
		return idcardValidator.isValidatedAllIdcard(value);
	}
}
